const express = require('express')
const graphqlHTTP = require('express-graphql')
const graphql = require('graphql')
const joinMonster = require('join-monster')
const bodyParser = require('body-parser')
const pjson = require('./package.json');

// Connect to database
const { Client } = require('pg')
const client = new Client({
  host: pjson.dbConfig.dbHost,
  user: pjson.dbConfig.dbUser,
  password: pjson.dbConfig.dbPassword,
  database: pjson.dbConfig.dbName,
})
client.connect()

// Define the schema
const Player = new graphql.GraphQLObjectType({
  name: 'Player',
  fields: () => ({
    id: { type: graphql.GraphQLString },
    first_name: { type: graphql.GraphQLString },
    last_name: { type: graphql.GraphQLString },
    team: {
      type: Team,
      sqlJoin: (playerTable, teamTable, args) => `${playerTable}.team_id = ${teamTable}.id`
    }
  })
});

Player._typeConfig = {
  sqlTable: 'player',
  uniqueKey: 'id',
}

var Team = new graphql.GraphQLObjectType({
  name: 'Team',
  fields: () => ({
    id: { type: graphql.GraphQLInt },
    name: { type: graphql.GraphQLString },
    players: {
      type: graphql.GraphQLList(Player),
      sqlJoin: (teamTable, playerTable, args) => `${teamTable}.id = ${playerTable}.team_id`
    },
    is_league_winner: {type: graphql.GraphQLBoolean},
    current_competitions:{type:graphql.GraphQLList(graphql.GraphQLString)},
    league:{
      type: League,
      sqlJoin: (teamTable, leagueTable, args) => `${leagueTable}.id = ${teamTable}.league_id`}
  })
})

Team._typeConfig = {
  sqlTable: 'team',
  uniqueKey: 'id'
}

var League = new graphql.GraphQLObjectType({
  name: 'League',
  fields: () => ({
    id: { type: graphql.GraphQLInt },
    name: { type: graphql.GraphQLString },
    continent: { type: graphql.GraphQLString },
    teams: {
      type: graphql.GraphQLList(Team),
      sqlJoin: (leagueTable, teamTable, args) => `${leagueTable}.id = ${teamTable}.league_id`
    }
  })
})

League._typeConfig = {
  sqlTable: 'league',
  uniqueKey: 'id'
}

const MutationRoot = new graphql.GraphQLObjectType({
  name: 'Mutation',
  fields: () => ({
    player: {
      type: Player,
      args: {
        first_name: { type: graphql.GraphQLNonNull(graphql.GraphQLString) },
        last_name: { type: graphql.GraphQLNonNull(graphql.GraphQLString) },
        team_id: { type: graphql.GraphQLNonNull(graphql.GraphQLInt) },
      },
      resolve: async (parent, args, context, resolveInfo) => {
        try {
          // Throw error if name contains number
          if(!/\d/.test(args.first_name)){
            return (await client.query("INSERT INTO player (first_name, last_name, team_id) VALUES ($1, $2, $3) RETURNING *", [args.first_name, args.last_name, args.team_id])).rows[0]
          } else {
            throw new Error("Name cannot contain number")
          }
        } catch (err) {
          throw new Error(err)
        }
      }
    }
  })
})

const QueryRoot = new graphql.GraphQLObjectType({
  name: 'Query',
  fields: () => ({
    players: {
      type: new graphql.GraphQLList(Player),
      resolve: (parent, args, context, resolveInfo) => {
        return joinMonster.default(resolveInfo, {}, sql => {
          return client.query(sql)
        })
      }
    },
    player: {
      type: Player,
      args: { id: { type: graphql.GraphQLNonNull(graphql.GraphQLInt) } },
      where: (playerTable, args, context) => `${playerTable}.id = ${args.id}`,
      resolve: (parent, args, context, resolveInfo) => {
        return joinMonster.default(resolveInfo, {}, sql => {
          return client.query(sql)
        })
      }
    },
    teams: {
      type: new graphql.GraphQLList(Team),
      resolve: (parent, args, context, resolveInfo) => {
        return joinMonster.default(resolveInfo, {}, sql => {
          return client.query(sql)
        })
      }
    },
    team: {
      type: Team,
      args: { id: { type: graphql.GraphQLNonNull(graphql.GraphQLInt) } },
      where: (teamTable, args, context) => `${teamTable}.id = ${args.id}`,
      resolve: (parent, args, context, resolveInfo) => {
        return joinMonster.default(resolveInfo, {}, sql => {
          return client.query(sql)
        })
      }
    },
    leagues: {
      type: new graphql.GraphQLList(League),
      resolve: (parent, args, context, resolveInfo) => {
        return joinMonster.default(resolveInfo, {}, sql => {
          return client.query(sql)
        })
      }
    },
    league: {
      type: League,
      args: { id: { type: graphql.GraphQLNonNull(graphql.GraphQLInt) } },
      where: (leagueTable, args, context) => `${leagueTable}.id = ${args.id}`,
      resolve: (parent, args, context, resolveInfo) => {
        return joinMonster.default(resolveInfo, {}, sql => {
          return client.query(sql)
        })
      }
    },
  })
})

const schema = new graphql.GraphQLSchema({
  query: QueryRoot,
  mutation: MutationRoot
});

// Create the Express app
const app = express();
app.use('/graphql', graphqlHTTP({
  schema: schema,
  graphiql: true
}));
app.use(bodyParser.json());
app.listen(4000,()=>{
   console.log("Running GraphQL at 4000 port");
});
