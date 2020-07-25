@query
Feature: Test GraphQL Find All Leagues

# Background is executed before each scenario
# Scenarios run in parallel
# To turn off parallel you can use the tag:
# @parallel=false

    Background:
        Given url baseUrl

    Scenario: Find All Leagues

    # Define GraphQL query
    # In karate you can use multiline declaration

      Given def query = karate.call('classpath:queries/FindAllLeagues.js')

        # Request the query
        And request { query: '#(query)'}

        # Use method POST (GraphQL always uses POST)
        When method POST

        # Assert status code is OK
        Then status 200

        # Print response
        And print response

        And match each response.data.FindAllLeagues[*].id != '#null'

        # Assert no error response is returned
        And match response.errors == '#notpresent'