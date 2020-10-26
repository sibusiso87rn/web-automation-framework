package starter.core.interfaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface ILocalWebDriverSetup {
    WebDriver getLocalWebDriverObject(DesiredCapabilities desiredCapabilities,String binaryLocation);
    DesiredCapabilities getDesiredCapabilities();
    String getDriverLocation();
}