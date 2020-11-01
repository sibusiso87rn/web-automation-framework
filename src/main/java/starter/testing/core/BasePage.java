package starter.testing.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import starter.testing.core.bean.ApplicationContext;

public abstract class BasePage {

    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(){
        driver = ApplicationContext.getWebDriver();
        logger.info("Creating page object ["+this.getClass().getSimpleName()+"]");
        PageFactory.initElements(driver,this);
    }

    public WebDriver getDriver(){
        return driver;
    }

}
