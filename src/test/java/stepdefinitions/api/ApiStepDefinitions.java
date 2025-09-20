package stepdefinitions.api;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class ApiStepDefinitions {
    private String baseEndpoint;
    private String appId;
    private Response response;
    private String lastUserId;

    @Given("I have valid app-id {string}")
    public void iHaveValidAppId(String appId) {
        this.appId = appId;
    }

    @Given("I have API endpoint {string}")
    public void iHaveApiEndpoint(String endpoint) {
        this.baseEndpoint = endpoint;
    }

    @When("I send GET request")
    public void iSendGetRequest() {
        response = RestAssured.given()
                .header("app-id", appId)
                .when()
                .get(baseEndpoint);
    }

    @Then("I should get status code {int}")
    public void iShouldGetStatusCode(int expectedCode) {
        Assertions.assertEquals(expectedCode, response.getStatusCode(),
                "Expected status code " + expectedCode + " but got " + response.getStatusCode());
    }

    @Then("Response should contain tags list")
    public void responseShouldContainTagsList() {
        String body = response.getBody().asString();
        Assertions.assertTrue(body.contains("\"data\""), "Response should contain data array");
        Assertions.assertTrue(body.contains("\"id\""), "Tag objects should contain id");
        Assertions.assertTrue(body.contains("\"name\""), "Tag objects should contain name");
    }

    @Then("I extract first user id from response")
    public void iExtractFirstUserIdFromResponse() {
        JsonPath json = response.getBody().jsonPath();
        lastUserId = json.getString("data[0].id");
        Assertions.assertNotNull(lastUserId, "First user id should not be null");
    }

    @When("I send GET request for user by id")
    public void iSendGetRequestToUser() {
        response = RestAssured.given()
                .header("app-id", appId)
                .when()
                .get("https://dummyapi.io/data/v1/user/" + lastUserId);
    }

    @Then("Response should contain user data")
    public void responseShouldContainUserData() {
        String body = response.getBody().asString();
        Assertions.assertTrue(body.contains("\"id\""), "Response should contain id");
        Assertions.assertTrue(body.contains("\"firstName\""), "Response should contain firstName");
        Assertions.assertTrue(body.contains("\"lastName\""), "Response should contain lastName");
    }
}
