package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = {"stepdefinitions.web"},
        tags = "@web",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/web",
                "json:target/cucumber-reports/web/cucumber.json"
        }
)
public class WebTestRunner {
}
