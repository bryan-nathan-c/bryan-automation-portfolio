package stepdefinitions.api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiStepDefinitions {

    private final Map<String, String> headers = new HashMap<>();
    private String endpoint;
    private String userId;
    private String payloadJson;
    private Response response;

    // ---- Helpers ----
    private String ensureUserId() {
        if (userId != null && !userId.isEmpty()) return userId;
        Response r = RestAssured.given().headers(headers)
                .get("https://dummyapi.io/data/v1/user?limit=1");
        Assertions.assertEquals(200, r.statusCode(), "Failed to fetch a user id");
        List<Map<String, Object>> data = r.jsonPath().getList("data");
        Assertions.assertNotNull(data, "data array is null");
        Assertions.assertFalse(data.isEmpty(), "data array is empty");
        userId = String.valueOf(data.get(0).get("id"));
        return userId;
    }

    private String materializeEndpointIfNeeded() {
        if (endpoint != null && endpoint.contains("{userId}")) {
            return endpoint.replace("{userId}", ensureUserId());
        }
        return endpoint;
    }

    private boolean isCreateEndpoint() {
        return endpoint != null && endpoint.endsWith("/user/create");
    }

    // ---- Steps ----
    @Given("I have valid app-id {string}")
    public void iHaveValidAppId(String appId) {
        headers.put("app-id", appId);
        headers.put("Accept", "application/json");
    }

    @Given("I have API endpoint {string}")
    public void iHaveApiEndpoint(String url) {
        this.endpoint = url;
    }

    @When("I send GET request")
    public void iSendGetRequest() {
        String url = materializeEndpointIfNeeded();
        response = RestAssured.given().headers(headers).get(url);
    }

    @Then("I should get status code {int}")
    public void iShouldGetStatusCode(int expected) {
        int actual = response.statusCode();
        if (expected == 201 && isCreateEndpoint()) {
            // DummyAPI allows status 200 or 201 for create
            Assertions.assertTrue(actual == 201 || actual == 200,
                    "Expected 201 (or 200 for DummyAPI), but got " + actual);
        } else {
            Assertions.assertEquals(expected, actual,
                    "Unexpected HTTP status: " + response.statusLine());
        }
    }

    @And("response should contain list of users")
    public void responseShouldContainListOfUsers() {
        List<Map<String, Object>> users = response.jsonPath().getList("data");
        Assertions.assertNotNull(users, "User list missing in response");
        Assertions.assertFalse(users.isEmpty(), "User list is empty");
    }

    @And("I extract first user id from response")
    public void iExtractFirstUserIdFromResponse() {
        List<Map<String, Object>> data = response.jsonPath().getList("data");
        Assertions.assertNotNull(data, "data array missing");
        Assertions.assertFalse(data.isEmpty(), "no users returned");
        userId = String.valueOf(data.get(0).get("id"));
        Assertions.assertNotNull(userId, "user id is null");
    }

    @When("I send GET request for user by id")
    public void iSendGetRequestForUserById() {
        Assertions.assertNotNull(userId, "userId not set");
        String url = "https://dummyapi.io/data/v1/user/" + userId;
        response = RestAssured.given().headers(headers).get(url);
    }

    @And("response should contain user data")
    public void responseShouldContainUserData() {
        String id = response.jsonPath().getString("id");
        Assertions.assertNotNull(id, "id missing in user response");
        if (userId != null) {
            Assertions.assertEquals(userId, id, "returned id does not match stored userId");
        }
        Assertions.assertNotNull(response.jsonPath().getString("firstName"), "firstName missing");
        Assertions.assertNotNull(response.jsonPath().getString("lastName"), "lastName missing");
    }

    @And("I have valid user data payload")
    public void iHaveValidUserDataPayload() {
        String uniqueEmail = "john" + System.currentTimeMillis() + "@example.com";
        payloadJson = "{"
                + "\"firstName\":\"John\","
                + "\"lastName\":\"Doe\","
                + "\"email\":\"" + uniqueEmail + "\""
                + "}";
    }

    @When("I send POST request")
    public void iSendPostRequest() {
        String url = materializeEndpointIfNeeded();
        response = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .post(url);
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            String id = response.jsonPath().getString("id");
            if (id != null) userId = id;
        }
    }

    @And("response should confirm user creation")
    public void responseShouldConfirmUserCreation() {
        String id = response.jsonPath().getString("id");
        Assertions.assertNotNull(id, "id missing after creation");
        String email = response.jsonPath().getString("email");
        Assertions.assertNotNull(email, "email missing after creation");
    }

    @And("I have updated user data payload")
    public void iHaveUpdatedUserDataPayload() {
        payloadJson = "{"
                + "\"firstName\":\"Updated\","
                + "\"lastName\":\"User\""
                + "}";
    }

    @When("I send PUT request")
    public void iSendPutRequest() {
        String url = materializeEndpointIfNeeded();
        response = RestAssured.given()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .put(url);
    }

    @And("response should confirm update")
    public void responseShouldConfirmUpdate() {
        String id = response.jsonPath().getString("id");
        Assertions.assertNotNull(id, "id missing in update response");
        if (userId != null) {
            Assertions.assertEquals(userId, id, "updated user id mismatch");
        }
        Assertions.assertEquals("Updated", response.jsonPath().getString("firstName"), "firstName not updated");
        Assertions.assertEquals("User", response.jsonPath().getString("lastName"), "lastName not updated");
    }

    @Given("I have invalid user ID endpoint")
    public void iHaveInvalidUserIdEndpoint() {
        endpoint = "https://dummyapi.io/data/v1/user/000000000000000000000000";
    }

    @When("I send GET request with invalid ID")
    public void iSendGetRequestWithInvalidID() {
        String url = materializeEndpointIfNeeded();
        response = RestAssured.given().headers(headers).get(url);
    }

    @And("response should contain error message")
    public void responseShouldContainErrorMessage() {
        int status = response.statusCode();
        String contentType = response.getHeader("Content-Type");
        String body = response.asString();

        if (status == 404) {
            if (contentType != null && contentType.contains("application/json")) {
                String error = response.jsonPath().getString("error");
                String message = response.jsonPath().getString("message");
                Map<String, Object> data = response.jsonPath().getMap("data");
                boolean hasError =
                        (error != null && !error.isBlank()) ||
                                (message != null && !message.isBlank()) ||
                                (data != null && !data.isEmpty());
                Assertions.assertTrue(hasError || body == null || body.isBlank(),
                        "Expected error/message or empty body for 404");
            } else {
                Assertions.assertTrue(body == null || body.isBlank() ||
                                body.toLowerCase().contains("not found"),
                        "404 should have empty or 'not found' body");
            }
            return;
        }

        if (contentType != null && contentType.contains("application/json")) {
            String error = response.jsonPath().getString("error");
            String message = response.jsonPath().getString("message");
            Assertions.assertTrue(
                    (error != null && !error.isBlank()) || (message != null && !message.isBlank()),
                    "Missing error/message in JSON error response");
        } else {
            Assertions.assertTrue(body != null && !body.isBlank(), "Missing error body");
        }
    }
}
