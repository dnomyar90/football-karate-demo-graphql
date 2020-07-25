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

      Given def query = karate.call('classpath:queries/FindAllPlayers.js')

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
        And match each response.data.FindAllPlayers[*].team.is_league_winner == '#boolean'

        # Assert at least a data is returned
        And match response.data.FindAllPlayers != '#null'

        # Assert each of the current_competition of each player is returned
        # Super intuitive and simple (if you ask me)
        And match each response.data.FindAllPlayers[*].team.current_competitions[*] != '#null'

        # Assert any of the team competes in "La Liga" competition
        And match response.data.FindAllPlayers[*].team.current_competitions[*] contains "La Liga"

        # Assert any of the team competes in "La Liga" and "Copa Del Rey" competition
        * def competitions = ["Copa Del Rey", "La Liga"]
        And match response.data.FindAllPlayers[*].team.current_competitions[*] contains competitions

         # Assert none of the team competes in "La Liga" and "Bundesliga" competition
         # This is one of karate's own style shortcuts
         # It might look unfamiliar. But you will get to it in no time
        * def competitions = ["La Liga", "Bundesliga"]
        And match response.data.FindAllPlayers[*].team.current_competitions[*] !contains '#(^competitions)'

        # Assert no error response is returned
        And match response.errors == '#notpresent'