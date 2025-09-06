package cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/java/cucumber"}, glue = {"StepDefinitions"}, 
plugin = {
	    "pretty",
	    "html:reports/cucumber-html-report",
	    "json:reports/cucumber.json",
	    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class TestNGTestRunner extends AbstractTestNGCucumberTests {
    // No extra code needed, TestNG runner will pick up the features
}
