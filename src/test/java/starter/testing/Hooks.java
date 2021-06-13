package starter.testing;

import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import starter.testing.core.bean.ApplicationContext;
import starter.testing.core.util.string.UuidUtil;

import java.io.IOException;

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
    }

    @Before
    public void beforeScenario(Scenario scenario)  throws Exception{
        logger.info("------------------------------");
        logger.info(scenario.getName() + " - Status - " + scenario.getStatus());
        logger.info("------------------------------");
    }


    //If there is a need to take a screenshot after each step :(
    @AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
        takeScreenshot(scenario);
    }

    private void takeScreenshotOnFailure(Scenario scenario) throws IOException {
        logger.info("Taking screenshot IF Test Failed");
        if (scenario.isFailed()) {
            takeScreenshot(scenario);
        }
    }

    private void takeScreenshot(Scenario scenario) {
        logger.info("Taking screenshot...");
            try {
                byte[] screenShot = ((TakesScreenshot) ApplicationContext.getTestBean().getWebDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenShot,"image/png", UuidUtil.getUuidNospecialChar()+".png");
            } catch (Exception e) {
                logger.error("Failed to take screenshot , {}", e.getMessage());
            }
    }

    //Terminates and restart the application
    private void restartApplication(){

    }
}
