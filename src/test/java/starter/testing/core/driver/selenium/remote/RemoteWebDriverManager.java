package starter.testing.core.driver.selenium.remote;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import starter.testing.core.interfaces.IRemoteWebDriverSetup;

import java.net.URL;
import java.util.Properties;

public enum RemoteWebDriverManager implements IRemoteWebDriverSetup {
    CHROME {
        public DesiredCapabilities getDesiredCapabilities(Properties browserProperties){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("driver.version", browserProperties.getProperty("driver.version"));
            capabilities.setCapability("browserName", "chrome");
            capabilities.setCapability("platform", browserProperties.getProperty("platform"));
            capabilities.setCapability("platformName", browserProperties.getProperty("platform.name"));
            capabilities.setCapability("version",browserProperties.getProperty("browser.version"));
            return capabilities;
        }

        public RemoteWebDriver getWebDriverObject(URL appiumServerURL, DesiredCapabilities capabilities) {
            return new RemoteWebDriver(appiumServerURL, capabilities);
        }
    },
    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities(Properties browserProperties){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "firefox");
            capabilities.setCapability("platform", "LINUX");
            capabilities.setCapability("platformName", "LINUX");
            capabilities.setCapability("version","76.0.1");
            capabilities.setCapability("driver.version", "3.141.59");

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            capabilities.merge(options);

            return capabilities;
        }

        public RemoteWebDriver getWebDriverObject(URL appiumServerURL, DesiredCapabilities capabilities) {
            return new RemoteWebDriver(appiumServerURL, capabilities);
        }
    },
    IE {
        public DesiredCapabilities getDesiredCapabilities(Properties browserProperties){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("os", browserProperties.getProperty("os",""));
            capabilities.setCapability("os.version", browserProperties.getProperty("os.version",""));
            capabilities.setCapability("browser", "ie");
            capabilities.setCapability("browser.version", browserProperties.getProperty("browser.version",""));
            return capabilities;
        }

        public RemoteWebDriver getWebDriverObject(URL appiumServerURL, DesiredCapabilities capabilities) {
            return new RemoteWebDriver(appiumServerURL, capabilities);
        }
    }
}