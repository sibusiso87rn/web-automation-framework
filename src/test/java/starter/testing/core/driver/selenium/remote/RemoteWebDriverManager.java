package starter.core.driver.selenium.remote;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import starter.core.interfaces.IRemoteWebDriverSetup;

import java.net.URL;
import java.util.Properties;

public enum RemoteWebDriverManager implements IRemoteWebDriverSetup {
    CHROME {
        public DesiredCapabilities getDesiredCapabilities(Properties browserProperties){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("os", browserProperties.getProperty("os",""));
            capabilities.setCapability("os_version", browserProperties.getProperty("os_version",""));
            capabilities.setCapability("browser", "chrome");
            capabilities.setCapability("browser_version", browserProperties.getProperty("browser_version",""));
            return capabilities;
        }

        public RemoteWebDriver getWebDriverObject(URL appiumServerURL, DesiredCapabilities capabilities) {
            return new RemoteWebDriver(appiumServerURL, capabilities);
        }
    },
    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities(Properties browserProperties){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("os", browserProperties.getProperty("os",""));
            capabilities.setCapability("os_version", browserProperties.getProperty("os_version",""));
            capabilities.setCapability("browser", "firefox");
            capabilities.setCapability("browser_version", browserProperties.getProperty("browser_version",""));
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
            capabilities.setCapability("os_version", browserProperties.getProperty("os_version",""));
            capabilities.setCapability("browser", "ie");
            capabilities.setCapability("browser_version", browserProperties.getProperty("browser_version",""));
            return capabilities;
        }

        public RemoteWebDriver getWebDriverObject(URL appiumServerURL, DesiredCapabilities capabilities) {
            return new RemoteWebDriver(appiumServerURL, capabilities);
        }
    }
}