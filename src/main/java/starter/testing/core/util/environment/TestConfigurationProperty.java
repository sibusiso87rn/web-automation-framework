package starter.testing.core.util.environment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import starter.testing.core.CoreConstants;
import starter.testing.core.driver.WebDriverFactory;
import starter.testing.core.util.file.PropertiesUtil;

import java.util.Properties;

@Component
@Lazy
public class TestConfigurationProperty {

    private static ThreadLocal<Properties> driverProperties = new ThreadLocal<>();
    private static TestConfigurationProperty testConfigurationProperty = null;
    private static final Logger logger = LogManager.getLogger(TestConfigurationProperty.class);

    private TestConfigurationProperty(){
    }

    public Properties getThreadLocalProperties(){
        return driverProperties.get();
    }

    public static TestConfigurationProperty getInstance(){
        if(testConfigurationProperty==null){
            logger.info("TestConfigurationProperty is instance is null, creating new instance");
            testConfigurationProperty = new TestConfigurationProperty();
        }
        return testConfigurationProperty;
    }

    public void createTestConfigProperties(String testConfiguration){
        //Read properties
        String gridPropertiesLocation = EnvironmentConfig.getConfigValue(CoreConstants.BROWSER_CONF_KEY)+EnvironmentConfig.getConfigValue(CoreConstants.GRID_SETTINGS_CONF_KEY);
        String testPropertiesLocation = EnvironmentConfig.getConfigValue(CoreConstants.TEST_SETTINGS_CONF_KEY)+testConfiguration;
        logger.info("Adding grid properties from location {} ",gridPropertiesLocation);
        logger.info("Adding testing configuration from location {} ",testPropertiesLocation);
        driverProperties = ThreadLocal.withInitial(() -> PropertiesUtil.mergeProperties(PropertiesUtil.getProperties(gridPropertiesLocation),PropertiesUtil.getProperties(testPropertiesLocation)));
        PropertiesUtil.printProperties(driverProperties.get());
    }

}
