package starter.testing.core.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import starter.testing.core.driver.remote.RemoteWebDriverManager;
import starter.testing.core.driver.local.LocalDriverManager;

import java.net.URL;
import java.util.Properties;

public class WebDriverFactory {

    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);

    private static WebDriverFactory webDriverFactoryInstance     = null;
    private static final ThreadLocal<WebDriver> webDriverThreadLocal   = new ThreadLocal<>();

    private WebDriverFactory(){

    }

    public static WebDriverFactory getInstance(){
        if(webDriverFactoryInstance==null){
            logger.info("WebDriver factory is instance is null, creating new instance");
            webDriverFactoryInstance = new WebDriverFactory();
        }
        return webDriverFactoryInstance;
    }

    public WebDriver getThreadLocalWebDriver(){
        return webDriverThreadLocal.get();
    }

    protected static void setThreadLocalWebDriver(WebDriver driver) {
        webDriverThreadLocal.set(driver);
    }

    public void createThreadLocalDriver(Properties driverProperties) throws Exception {
        logger.info("Creating Web Driver for Thread Id {}, running on {} environment",Thread.currentThread().getId(),driverProperties.getProperty("browser.run.environment").toUpperCase());
        BrowserRunEnvironment browserRunEnvironment = BrowserRunEnvironment.valueOf(driverProperties.getProperty("browser.run.environment").toUpperCase());

        WebDriver driver = null;
        switch (browserRunEnvironment){
            case LOCAL:
                LocalDriverManager localDriverManager = LocalDriverManager.valueOf(driverProperties.getProperty("browser").toUpperCase());
                logger.info("Creating web driver for browser {}", driverProperties.getProperty("browser"));
                DesiredCapabilities desiredCapabilities = localDriverManager.getDesiredCapabilities();
                driver              = localDriverManager.getLocalWebDriverObject(desiredCapabilities, localDriverManager.getDriverLocation());
                break;
            case GRID:
                logger.info("Creating remote web driver for browser {}, them remote url is {}", driverProperties.getProperty("browser"),driverProperties.getProperty("grid.remote.url"));
                RemoteWebDriverManager remoteWebDriverManager = RemoteWebDriverManager.valueOf(driverProperties.getProperty("browser").toUpperCase());
                desiredCapabilities = remoteWebDriverManager.getDesiredCapabilities(driverProperties);
                driver                  = remoteWebDriverManager.getWebDriverObject(new URL(driverProperties.getProperty("grid.remote.url")), desiredCapabilities);
                break;
            default:
        }
        webDriverThreadLocal.set(driver);
    }
}

