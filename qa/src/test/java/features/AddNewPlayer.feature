@mutation
Feature: Test GraphQL Insert Player (mutation)

# Background is executed before each scenario
# Scenarios run in parallel
# To turn off parallel you can use the tag:
# @parallel=false

    Background:
        Given url baseUrl

    Scenario: Insert a player

    # Read GraphQL file
     Given def query = karate.call('classpath:mutations/AddNewPlayer.js')

    # Declare variables for the request (in JSON format)
    # Notes: Example of using faker to generate randomized data
    And def variables = {last_name: '#(faker.randomLastName())',first_name:'#(faker.randomFirstName())', team_id:2}

    # Prepare the request
    And request { query: '#(query)', variables: '#(variables)' }

    # Call in POST
    When method POST

    # Assert status is 200
    Then status 200

    # Parse string as integer & assert with existing variable
    And match response.data.AddNewPlayer.first_name == variables.first_name
    And match response.data.AddNewPlayer.last_name == variables.last_name

    # Assert that there is no error
    And match response.errors == '#notpresent'

    # Negative case input first name with number (invalid input)
    @negative
    Scenario: Insert a player with invalid first name

    # Read GraphQL file
    Given def query = karate.call('classpath:mutations/AddNewPlayer.js')

    # Declare variables for the request (in JSON format)
    # Note that the first name is invalid since it contains number
    And def variables = {last_name: "Ljungberg",first_name:"Freddie333", team_id:2}

    # Prepare the request
    And request { query: '#(query)', variables: '#(variables)' }

    # Call in POST
    When method POST

    # Assert status is 200
    Then status 200

    # Assert that there is an error
    And match response.errors[0].message == 'Error: Name cannot contain number'