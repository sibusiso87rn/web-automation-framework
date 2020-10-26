package starter.testing.core.driver.interfaces;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public interface IWebDriverInstance {
    public WebDriver getDriverInstance(Capabilities capabilities);
}
