package starter.testing;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;
import starter.testing.core.bean.ApplicationContext;
import starter.testing.core.util.environment.TestConfigurationProperty;
import starter.testing.core.util.report.CucumberReport;
import starter.testing.core.util.environment.EnvironmentConfig;
import starter.testing.tests.TestConstants;

/**
 * Created by Sibusiso Radebe on 2020/02/20.
 */
@CucumberOptions(
        plugin  = {
                "starter.testing.core.util.report.ExtentReportListener",
                "pretty",
                "json:target/cucumber-report.json",
        },
        features    = {"src/test/resources/features" },
        glue        = {""},
        tags        = "@Dev"
)
@ContextConfiguration(locations = {"classpath:spring-bean.xml"})
public class ExecutionEngine extends AbstractTestNGSpringContextTests {

    private TestNGCucumberRunner testNGCucumberRunner;
    private static final Logger logger = LogManager.getLogger(ExecutionEngine.class);


    @BeforeClass(alwaysRun = true)
    @Parameters({"environment","test.configuration","run.environment"})
    public void setTestNGProperties(String environment,String testConfiguration,String runEnvironment) throws Exception{

        String baseUrl;

        //Read device details from the testcase on testng xml
        System.setProperty("environment",environment);

        //Initialize environment configs
        EnvironmentConfig.getInstance();

        //Initialize driver properties
        logger.info("Creating test configuration using {}",testConfiguration);
        TestConfigurationProperty.createTestConfigProperties(testConfiguration);

        //Read device details from the testcase on testng xml
        System.setProperty("configs.set","true");

        ApplicationContext.getTestBean().createDriver();
        baseUrl = EnvironmentConfig.getInstance().getConfigValue(TestConstants.BASE_URL);

        //Launch
        ApplicationContext.getTestBean().getWebDriver().get(baseUrl);
        logger.debug("Driver connecting to [{}] for scenario",ApplicationContext.getTestBean().getWebDriver().getCurrentUrl());
        logger.info("Finished setting the TestNG properties.");

        //Maximise Window
        logger.info("Maximising browser window");
        ApplicationContext.getTestBean().getWebDriver().manage().window().maximize();

    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass(){
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber scenarios", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickleEvent, FeatureWrapper cucumberFeature) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEvent.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();

        //Quit appium driver
        ApplicationContext.getTestBean().quitDriver();

        //Create and finalize the report - This is done once, only after the tests have been completed.
        new CucumberReport().createReport();
    }

    @AfterSuite(alwaysRun = true)
    public void tearCreateReport() {
    }
}
