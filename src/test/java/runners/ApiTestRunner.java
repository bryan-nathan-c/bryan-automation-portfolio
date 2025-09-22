package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions"},
        tags = "@api",
        plugin = {
            "pretty",
            "html:build/reports/cucumber/api/index.html",
            "json:build/reports/cucumber/api/cucumber.json"
        }
)
public class ApiTestRunner { }
