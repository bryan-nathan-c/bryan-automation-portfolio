package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions"},
        plugin = {
                "pretty",
                "html:build/reports/cucumber/all/html",
                "json:build/reports/cucumber/all/json/cucumber.json"
        }
)
public class AllTestRunner { }

