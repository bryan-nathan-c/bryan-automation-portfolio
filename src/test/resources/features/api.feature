@api
Feature: Dummy API Testing

  Background:
    Given I have valid app-id "63a804408eb0cb069b57e43a"

  @api
  Scenario: Get list of tags
    Given I have API endpoint "https://dummyapi.io/data/v1/tag"
    When I send GET request
    Then I should get status code 200
    And Response should contain tags list

  @api
  Scenario: Get user by ID (dynamic)
    Given I have API endpoint "https://dummyapi.io/data/v1/user?limit=1"
    When I send GET request
    Then I should get status code 200
    And I extract first user id from response
    When I send GET request for user by id
    Then I should get status code 200
    And Response should contain user data
