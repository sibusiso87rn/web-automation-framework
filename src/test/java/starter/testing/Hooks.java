package starter.testing;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import starter.testing.core.util.ApplicationContext;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @After
    public void afterScenario(Scenario scenario)  throws Exception{
        logger.info("------------------------------");
        logger.info(scenario.getName() + " - Status - " + scenario.getStatus());
        logger.info("------------------------------");

        //Take a screenshot if there's a failure
        takeScreenshotOnFailure(scenario);

        //Quit appium driver after each scenario
        logger.info("Quiting driver after scenario " + scenario.getName());

        //Each scenario is independent from each other and always starts from the log on page, we restart each time
        restartApplication();
    }

    @Before
    public void beforeScenario(Scenario scenario)  throws Exception{
        logger.info("------------------------------");
        logger.info(scenario.getName() + " - Status - " + scenario.getStatus());
        logger.info("------------------------------");
    }


    //If there is a need to take a screenshot after each step
    @AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
        takeScreenshot(scenario);
    }


    private void takeScreenshotOnFailure(Scenario scenario) {
        logger.info("Taking screenshot IF Test Failed");
        if (scenario.isFailed()) {
            takeScreenshot(scenario);
        }
    }

    private void takeScreenshot(Scenario scenario) {
        logger.info("Taking screenshot...");
            try {
                byte[] screenShot = ((TakesScreenshot) ApplicationContext.getWebDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenShot, "image/png");
            } catch (Exception e) {
                logger.error("Failed to take screenshot , ", e.getMessage());
            }
    }

    //Terminates and restart the application
    private void restartApplication(){

    }
}
