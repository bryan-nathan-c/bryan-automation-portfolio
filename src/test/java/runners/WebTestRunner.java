package runners;

import io.cucumber.junit.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = {"stepdefinitions.web"},
        tags = "@web",
        plugin = {
                "pretty",
                "html:build/reports/cucumber/web/html",
                "json:build/reports/cucumber/web/json/cucumber.json"
        }
)
public class WebTestRunner { }

