package runners;

import io.cucumber.junit.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = {"stepdefinitions.api"},
        tags = "@api",
        plugin = {
                "pretty",
                "html:build/reports/cucumber/api/html",
                "json:build/reports/cucumber/api/json/cucumber.json"
        }
)
public class ApiTestRunner { }

