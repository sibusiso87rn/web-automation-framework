package starter.testing.core.driver.local;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import starter.testing.core.interfaces.ILocalWebDriverSetup;
import starter.testing.core.bean.ApplicationContext;
import starter.testing.core.util.environment.EnvironmentConfig;

public enum LocalDriverManager implements ILocalWebDriverSetup {

    CHROME {
        public DesiredCapabilities getDesiredCapabilities(){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browser", "chrome");
            capabilities.setCapability("browser_version", ApplicationContext.getTestBean().getThreadLocalProperties().getProperty("browser.version"));
            return capabilities;
        }

        public WebDriver getLocalWebDriverObject(DesiredCapabilities capabilities,String binaryLocation){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.merge(capabilities);
            return new ChromeDriver(options);
        }

        public String getDriverLocation() {
            return EnvironmentConfig.getInstance().getDriverLocation()+ ApplicationContext.getTestBean().getThreadLocalProperties().getProperty("browser.name");
        }
    },
    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities(){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browser", "firefox");
            capabilities.setCapability("browser_version", ApplicationContext.getTestBean().getThreadLocalProperties().getProperty("browser.version"));
            return capabilities;
        }

        public WebDriver getLocalWebDriverObject(DesiredCapabilities capabilities,String binaryLocation) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            options.merge(capabilities);
            return new FirefoxDriver(options);
        }

        public String getDriverLocation() {
            return EnvironmentConfig.getInstance().getDriverLocation()+ ApplicationContext.getTestBean().getThreadLocalProperties().getProperty("browser.name");
        }
    },
    IE {
        public DesiredCapabilities getDesiredCapabilities(){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("os", ApplicationContext.getTestBean().getThreadLocalProperties().getProperty("os",""));
            capabilities.setCapability("os_version", ApplicationContext.getTestBean().getThreadLocalProperties().getProperty("os_version",""));
            capabilities.setCapability("browser", "chrome");
            capabilities.setCapability("browser_version", ApplicationContext.getTestBean().getThreadLocalProperties().getProperty("browser_version",""));
            return capabilities;
        }

        //TODO - Complete Details for WebDriver
        public WebDriver getLocalWebDriverObject(DesiredCapabilities capabilities,String binaryLocation) {
            return null;
        }

        public String getDriverLocation() {
            return EnvironmentConfig.getInstance().getDriverLocation()+ ApplicationContext.getTestBean().getThreadLocalProperties().getProperty("browser.name");
        }
    }
}