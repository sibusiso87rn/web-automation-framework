package starter.testing.core.interfaces;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Properties;

public interface IRemoteWebDriverSetup {
    RemoteWebDriver getWebDriverObject(URL appiumServerURL, DesiredCapabilities desiredCapabilities);
    DesiredCapabilities getDesiredCapabilities(Properties appiumProperties);
}