package starter.testing.core.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import starter.testing.core.driver.WebDriverFactory;
import starter.testing.core.util.environment.TestConfigurationProperty;

import java.util.Properties;

@Component
@Configuration
@ConditionalOnProperty(
        value="configs.set",
        havingValue = "true"
)
public class TestBean {

    private static final Logger logger = LogManager.getLogger(TestBean.class);

    @Autowired
    private TestConfigurationProperty testConfigurationProperty;

    //Set the driver before the test starts
    @Bean
    public TestBean createTestBean(){
        return new TestBean();
    }

    public WebDriver getWebDriver(){
       return WebDriverFactory.getInstance().getThreadLocalWebDriver();
    }

    public void createThreadLocalDriver(Properties appiumProperties) throws Exception {
        WebDriverFactory.getInstance().createThreadLocalDriver(appiumProperties);
    }

    public void createDriver() throws Exception {
        logger.info("Creating thread local driver");
        createThreadLocalDriver(testConfigurationProperty.getProperties());
    }

}
