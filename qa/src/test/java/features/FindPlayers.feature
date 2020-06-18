@query
Feature: Test GraphQL Find Players

# Background is executed before each scenario
# Scenarios run in parallel
# To turn off parallel you can use the tag:
# @parallel=false

    Background:
        Given url baseUrl

    Scenario: Find All Players

    # Define GraphQL query
    # In karate you can use multiline declaration

    Given text query =
    """
    {
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
    """

        # Request the query
        And request { query: '#(query)'}

        # Use method POST (GraphQL always uses POST)
        When method POST

        # Assert status code is OK
        Then status 200

        # Print response
        And print response

        # Assert each record of is_league_winner is boolean
        # Utilize [*] a.k.a wildcard
        And match each response.data.players[*].team.is_league_winner == '#boolean'

        # Assert at least a data is returned
        And match response.data.players != '#null'

        # Assert each of the current_competition of each player is returned
        # Super intuitive and simple (if you ask me)
        And match each response.data.players[*].team.current_competitions[*] != '#null'

        # Assert any of the team competes in "La Liga" competition
        And match response.data.players[*].team.current_competitions[*] contains "La Liga"

        # Assert any of the team competes in "La Liga" and "Copa Del Rey" competition
        * def competitions = ["Copa Del Rey", "La Liga"]
        And match response.data.players[*].team.current_competitions[*] contains competitions

         # Assert none of the team competes in "La Liga" and "Bundesliga" competition
         # This is one of karate's own style shortcuts
         # It might look unfamiliar. But you will get to it in no time
        * def competitions = ["La Liga", "Bundesliga"]
        And match response.data.players[*].team.current_competitions[*] !contains '#(^competitions)'

        # Assert no error response is returned
        And match response.errors == '#notpresent'

    Scenario: Find a player by ID

    # Read GraphQL file
    Given def query = read('graphql/player.graphql')

    # Declare variables for the request (in JSON format)
    And def variables = {id: 4}

    # Prepare the request
    And request { query: '#(query)', variables: '#(variables)' }

    # Call in POST
    When method POST

    # Assert status is 200
    Then status 200

    # Parse string as integer & assert with existing variable
    And match parseInt(response.data.player.id) == variables.id

    # Traverse deep to assert
    # This is one of the best features on karate
    And match response.data.player.team.league.name == "Bundesliga"

    # Assert that there is no error
    And match response.errors == '#notpresent'