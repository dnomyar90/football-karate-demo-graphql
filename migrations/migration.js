exports.up = (pgm) => {
  pgm.createTable('league', {
    id: 'id',
    name: { type: 'varchar(255)', notNull: false },
    continent: { type: 'varchar(255)', notNull: false },
    createdAt: {
      type: 'timestamp',
      notNull: true,
      default: pgm.func('current_timestamp'),
    },
  })
  pgm.createTable('team', {
    id: 'id',
    name: { type: 'varchar(255)', notNull: false },
    is_league_winner: { type: 'boolean' },
    current_competitions: { type: 'text[]' },
    league_id: {
      type: 'integer',
      notNull: true,
      references: '"league"',
    },
    createdAt: {
      type: 'timestamp',
      notNull: true,
      default: pgm.func('current_timestamp'),
    },
  })
  pgm.createTable('player', {
    id: 'id',
    first_name: { type: 'varchar(255)', notNull: false },
    last_name: { type: 'varchar(255)', notNull: false },
    is_league_winner: { type: 'boolean' },
    team_id: {
      type: 'integer',
      notNull: true,
      references: '"team"',
    },
    createdAt: {
      type: 'timestamp',
      notNull: true,
      default: pgm.func('current_timestamp'),
    },
  })
  pgm.sql(`INSERT INTO "public"."league" ("id", "name", "continent") VALUES ('1', 'English Premier League', 'Europe'),
      ('2', 'Bundesliga', 'Europe'),
      ('3', 'Liga 1 Shopee', 'Asia'),
      ('4', 'J League', 'Asia'),
      ('5', 'Major League Soccer', 'America'),
      ('6', 'La Liga', 'Europe');`),
    pgm.sql(`INSERT INTO "public"."team" ("id", "name", "league_id", "is_league_winner", "current_competitions") VALUES ('1', 'Barcelona', '6', 't', '{"Copa Del Rey","La Liga"}'),
      ('2', 'Arsenal', '1', 't', '{"English Premier League","Carling Cup"}'),
      ('3', 'Persija Jakarta', '3', 'f', '{"Shopee Liga 1","Piala Presiden"}'),
      ('4', 'Borussia Dortmund', '2', 'f', '{Bundesliga,"DFB Pokal"}'),
      ('5', 'Bayern Munich', '2', 't', '{Bundesliga,"DFB Pokal","Champions League"}');`),
    pgm.sql(`INSERT INTO "public"."player" ("id", "first_name", "last_name", "team_id") VALUES ('1', 'Messi', 'Lionel', '1'),
      ('2', 'Henry', 'Thierry', '2'),
      ('3', 'Simic', 'Marco', '3'),
      ('4', 'Haaland', 'Erling', '4'),
      ('129', 'Freddie', 'Ljungberg', '2'),
      ('130', 'Freddie', 'Ljungberg', '2'),
      ('131', 'Hanh', 'Hammes', '2');`)

}