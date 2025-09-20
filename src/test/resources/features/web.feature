@web
Feature: DemoBlaze Web UI Testing

  Background:
    Given I open DemoBlaze homepage

  @web
  Scenario: Verify homepage title
    When I check the page title
    Then The page title should contain "STORE"

  @web
  Scenario: Add product to cart
    When I click on "Samsung galaxy s6" product
    And I click Add to cart button
    Then I should see success message "Product added"
    When I go to Cart page
    Then I should see "Samsung galaxy s6" in cart
