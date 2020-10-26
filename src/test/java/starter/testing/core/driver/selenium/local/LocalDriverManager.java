package starter.core.driver.selenium.local;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import starter.core.interfaces.ILocalWebDriverSetup;
import starter.core.util.ApplicationTestContext;
import starter.core.util.environment.EnvironmentConfig;

public enum LocalDriverManager implements ILocalWebDriverSetup {

    CHROME {
        public DesiredCapabilities getDesiredCapabilities(){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("os", ApplicationTestContext.getTestConfiguration().getProperties().getProperty("os",""));
            capabilities.setCapability("os_version", ApplicationTestContext.getTestConfiguration().getProperties().getProperty("os.version",""));
            capabilities.setCapability("browser", "chrome");
            capabilities.setCapability("browser_version", ApplicationTestContext.getTestConfiguration().getProperties().getProperty("browser.version",""));
            return capabilities;
        }

        public WebDriver getLocalWebDriverObject(DesiredCapabilities capabilities,String binaryLocation){
            System.setProperty("webdriver.chrome.driver", binaryLocation);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.merge(capabilities);
            return new ChromeDriver(options);
        }

        public String getDriverLocation() {
            return EnvironmentConfig.getEnvironmentConfigInstance().getDriverLocation()+ ApplicationTestContext.getTestConfiguration().getProperties().getProperty("browser.name");
        }
    },
    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities(){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("os", ApplicationTestContext.getTestConfiguration().getProperties().getProperty("os",""));
            capabilities.setCapability("os_version", ApplicationTestContext.getTestConfiguration().getProperties().getProperty("os_version",""));
            capabilities.setCapability("browser", "chrome");
            capabilities.setCapability("browser_version", ApplicationTestContext.getTestConfiguration().getProperties().getProperty("browser_version",""));
            return capabilities;
        }

        public WebDriver getLocalWebDriverObject(DesiredCapabilities capabilities,String binaryLocation) {
            System.setProperty("webdriver.gecko.driver", binaryLocation);
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            options.merge(capabilities);
            return new FirefoxDriver(options);
        }

        public String getDriverLocation() {
            return EnvironmentConfig.getEnvironmentConfigInstance().getDriverLocation()+ ApplicationTestContext.getTestConfiguration().getProperties().getProperty("browser.name");
        }
    },
    IE {
        public DesiredCapabilities getDesiredCapabilities(){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("os", ApplicationTestContext.getTestConfiguration().getProperties().getProperty("os",""));
            capabilities.setCapability("os_version", ApplicationTestContext.getTestConfiguration().getProperties().getProperty("os_version",""));
            capabilities.setCapability("browser", "chrome");
            capabilities.setCapability("browser_version", ApplicationTestContext.getTestConfiguration().getProperties().getProperty("browser_version",""));
            return capabilities;
        }

        //TODO - Complete Details for WebDriver
        public WebDriver getLocalWebDriverObject(DesiredCapabilities capabilities,String binaryLocation) {
            return null;
        }

        public String getDriverLocation() {
            return EnvironmentConfig.getEnvironmentConfigInstance().getDriverLocation()+ ApplicationTestContext.getTestConfiguration().getProperties().getProperty("browser.name");
        }
    }
}