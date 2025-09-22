Feature: Dummy API Testing

  Background:
    Given I have valid app-id "63a804408eb0cb069b57e43a"

  @api
  Scenario: Get list of users - positive
    Given I have API endpoint "https://dummyapi.io/data/v1/user?limit=5"
    When I send GET request
    Then I should get status code 200
    And response should contain list of users

  @api
  Scenario: Get user by ID (dynamic)
    Given I have API endpoint "https://dummyapi.io/data/v1/user?limit=1"
    When I send GET request
    Then I should get status code 200
    And I extract first user id from response
    When I send GET request for user by id
    Then I should get status code 200
    And response should contain user data

  @api
  Scenario: Create new user (positive)
    Given I have API endpoint "https://dummyapi.io/data/v1/user/create"
    And I have valid user data payload
    When I send POST request
    Then I should get status code 201
    And response should confirm user creation

  @api
  Scenario: Update user data (positive)
    Given I have API endpoint "https://dummyapi.io/data/v1/user/{userId}"
    And I have updated user data payload
    When I send PUT request
    Then I should get status code 200
    And response should confirm update

  @api
  Scenario: Get user with invalid ID (negative test)
    Given I have invalid user ID endpoint
    When I send GET request with invalid ID
    Then I should get status code 404
    And response should contain error message
