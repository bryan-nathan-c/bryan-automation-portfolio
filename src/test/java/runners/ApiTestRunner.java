package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = {"stepdefinitions.api"},
        tags = "@api",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api",
                "json:target/cucumber-reports/api/cucumber.json"
        }
)
public class ApiTestRunner {
}
