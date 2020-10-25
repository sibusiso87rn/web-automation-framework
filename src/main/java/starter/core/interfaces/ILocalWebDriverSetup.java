package starter.core.interfaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import starter.core.util.environment.TestConfigurationProperty;

import java.net.URL;
import java.util.Properties;

public interface ILocalWebDriverSetup {
    WebDriver getLocalWebDriverObject(DesiredCapabilities desiredCapabilities,String binaryLocation);
    DesiredCapabilities getDesiredCapabilities();
    String getDriverLocation();
}