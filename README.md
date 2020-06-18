# Demo GraphQL for karate tests
###### Feature shows football players belongs to a team and a team belongs to a league

![Alt text](/relative/path/to/img.jpg?raw=true "Optional Title")

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

## Start the GraphQL server
`node server.js`

## Karate tests
```
cd qa
./run-test.sh @tag
```