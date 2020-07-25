@query
Feature: Test GraphQL Find Players

# Background is executed before each scenario
# Scenarios run in parallel
# To turn off parallel you can use the tag:
# @parallel=false

    Background:
        Given url baseUrl

    Scenario: Find a player by ID

    # Read GraphQL file
    Given def query = karate.call('classpath:queries/FindPlayerByID.js')

    # Declare variables for the request (in JSON format)
    And def variables = {id: 4}

    # Prepare the request
    And request { query: '#(query)', variables: '#(variables)' }

    # Call in POST
    When method POST

    # Assert status is 200
    Then status 200

    # Parse string as integer & assert with existing variable
    And match parseInt(response.data.FindPlayerByID.id) == variables.id

    # Traverse deep to assert
    # This is one of the best features on karate
    And match response.data.FindPlayerByID.team.league.name == "Bundesliga"

    # Assert that there is no error
    And match response.errors == '#notpresent'