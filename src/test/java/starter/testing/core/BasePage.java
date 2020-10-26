package starter.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import starter.core.util.ApplicationTestContext;

public abstract class BasePage {

    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(){
        driver = ApplicationTestContext.getWebDriver();
        logger.info("Creating page object ["+this.getClass().getSimpleName()+"]");
        PageFactory.initElements(driver,this);
    }

    public WebDriver getDriver(){
        return driver;
    }

}
