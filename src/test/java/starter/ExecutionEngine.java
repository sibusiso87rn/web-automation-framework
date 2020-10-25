package starter;

import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;
import starter.core.util.ApplicationContext;
import starter.core.util.CucumberReport;
import starter.core.util.environment.EnvironmentConfig;
import java.util.concurrent.TimeUnit;
import starter.core.tests.TestConstants;

/**
 * Created by Sibusiso Radebe on 2020/02/20.
 */
@CucumberOptions(
        plugin  = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/cucumber-report.json",
        },
        features    = {"src/test/resources/features" },
        glue        = {""},
        tags        = {"@Dev"}
)
@ContextConfiguration(locations = {"classpath*:spring-bean.xml"})
public class ExecutionEngine extends AbstractTestNGSpringContextTests {

    private TestNGCucumberRunner testNGCucumberRunner;
    private static final Logger logger = LogManager.getLogger(ExecutionEngine.class);


    @BeforeClass(alwaysRun = true)
    @Parameters({"environment","browser","browser.version","browser.run.environment","browser.name"})
    public void setTestNGProperties(String environment,String browser,String browserVersion,String browserLocation,String browserName) throws Exception{

        String baseUrl = null;

        //Read device details from the testcase on testng xml
        System.setProperty("environment",environment);

        //Initialize environment configs
        EnvironmentConfig.getEnvironmentConfigInstance();

        //Initialize driver properties
        ApplicationContext.getTestConfiguration().createTestConfigProperties(browser,browserVersion,browserLocation,browserName);

        //Read device details from the testcase on testng xml
        System.setProperty("configs.set","true");

        logger.info("Creating driver for scenario");
        ApplicationContext.getTestBean().createDriver();

        baseUrl = EnvironmentConfig.getEnvironmentConfigInstance().getConfigValue(TestConstants.BASE_URL);

        //Launch
        ApplicationContext.getTestBean().getWebDriver().get(baseUrl);
        ApplicationContext.getTestBean().getWebDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        logger.info("Finished setting the TestNG properties.");
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass(){
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber scenarios", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEvent.getPickleEvent());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();

        //Create and finalize the report - This is done once, only after the tests have been completed.
        new CucumberReport().createReport();

        //Quit appium driver
        ApplicationContext.getWebDriver().quit();
    }



}
