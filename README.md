## Demo GraphQL for karate tests
## Feature shows football players belongs to a team and a team belongs to a league

# Sample query:
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

# Query returns:
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
        "id": "3",
        "first_name": "Simic",
        "last_name": "Marco",
        "team": {
          "id": 3,
          "name": "Persija Jakarta",
          "is_league_winner": false,
          "current_competitions": [
            "Shopee Liga 1",
            "Piala Presiden"
          ],
          "league": {
            "id": 3,
            "name": "Liga 1 Shopee",
            "continent": "Asia"
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
      },
      {
        "id": "129",
        "first_name": "Freddie",
        "last_name": "Ljungberg",
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
        "id": "130",
        "first_name": "Freddie",
        "last_name": "Ljungberg",
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
        "id": "131",
        "first_name": "Hanh",
        "last_name": "Hammes",
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
      }
    ]
  }
}
```

## Install dependencies
`npm install`

## Start the GraphQL server
`node server.js`

## Karate tests
`cd qa`
`./run-test.sh @tag`