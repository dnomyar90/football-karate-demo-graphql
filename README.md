# Demo GraphQL for karate tests
Feature shows football players belongs to a team and a team belongs to a league

![alt text](https://github.com/dnomyar90/football-karate-demo-graphql/blob/master/asset/gettyImageAsset.jpg?raw=true)

## Sample query:
```
query{
  players{
    id
    first_name
    last_name
    team{
      id
      name
      is_league_winner
      current_competitions
      league{
        id
        name
        continent
      }
    }
  }
}
```

## Query returns:
```
{
  "data": {
    "players": [
      {
        "id": "1",
        "first_name": "Messi",
        "last_name": "Lionel",
        "team": {
          "id": 1,
          "name": "Barcelona",
          "is_league_winner": true,
          "current_competitions": [
            "Copa Del Rey",
            "La Liga"
          ],
          "league": {
            "id": 6,
            "name": "La Liga",
            "continent": "Europe"
          }
        }
      },
      {
        "id": "2",
        "first_name": "Henry",
        "last_name": "Thierry",
        "team": {
          "id": 2,
          "name": "Arsenal",
          "is_league_winner": true,
          "current_competitions": [
            "English Premier League",
            "Carling Cup"
          ],
          "league": {
            "id": 1,
            "name": "English Premier League",
            "continent": "Europe"
          }
        }
      },
      {
        "id": "4",
        "first_name": "Haaland",
        "last_name": "Erling",
        "team": {
          "id": 4,
          "name": "Borussia Dortmund",
          "is_league_winner": false,
          "current_competitions": [
            "Bundesliga",
            "DFB Pokal"
          ],
          "league": {
            "id": 2,
            "name": "Bundesliga",
            "continent": "Europe"
          }
        }
      }
    ]
  }
}
```

## Install dependencies
`npm install`

## Migrate database and inserting sample datas
- First you need to install a postgresql first. You can check how to install here: https://www.postgresql.org/download/
- Connect to your database: `psql -h localhost -U {YOUR_USERNAME}`
- Create a database with the name of your liking
- E.g. `CREATE DATABASE soccer;`
- Specify the dbName on `package.json` based on the database name you create. Also you can specify `dbUser`, `dbPassword`, `dbPort`, `dbHost` here too
- Run `npm run migrate_up`


## Start the GraphQL server
`node server.js`

## Open the GraphQL interface
Open the GraphQL interface based on the port you choose. By default the port is `4000`. 
You can try to open: http://localhost:4000/graphql

## Karate tests
More detailed information you can find on Readme inside the folder `qa`
```
cd qa
./run-test.sh @tag
```