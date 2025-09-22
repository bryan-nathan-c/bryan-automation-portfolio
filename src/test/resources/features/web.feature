Feature: SauceDemo Web UI Testing and Complete Checkout Flow

  Background:
    Given I open SauceDemo homepage

  @web
  Scenario: Verify homepage title after login
    And I login with valid credentials
    Then The page title should contain "Swag Labs"

  @web
  Scenario: Add product to cart
    And I login with valid credentials
    When I add product "Sauce Labs Backpack" to cart
    And I go to Cart page
    Then I should see "Sauce Labs Backpack" in cart

  @web
  Scenario: Remove product from cart
    And I login with valid credentials
    When I add product "Sauce Labs Backpack" to cart
    And I go to Cart page
    And I remove "Sauce Labs Backpack" from cart
    Then "Sauce Labs Backpack" should not be visible in cart

  @web
  Scenario: Invalid login attempt (negative test)
    When I login with invalid username and password
    Then I should see error message "Epic sadface: Username and password do not match any user in this service"

  @web
  Scenario: Successful checkout process
    And I login with valid credentials
    When I add product "Sauce Labs Backpack" to cart
    And I go to Cart page
    And I click Checkout button
    And I enter checkout information with first name "John", last name "Doe", postal code "12345"
    And I submit order
    Then I should see order confirmation message
