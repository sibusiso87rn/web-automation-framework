package starter.testing.core.util.environment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import starter.testing.core.CoreConstants;
import starter.testing.core.util.file.PropertiesUtil;

import java.util.Properties;

@Component
@Lazy
public class TestConfigurationProperty {

    private static final ThreadLocal<Properties> driverProperties = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(TestConfigurationProperty.class);

    public static Properties getThreadLocalProperties(){
        return driverProperties.get();
    }

    public static void createTestConfigProperties(String testConfiguration){
        //Read properties
        String testPropertiesLocation = EnvironmentConfig.getConfigValue(CoreConstants.BROWSER_LOCATION)+testConfiguration;
        logger.info("Adding testing configuration from location {} ",testPropertiesLocation);
        driverProperties.set(PropertiesUtil.mergeProperties(System.getProperties(),PropertiesUtil.getProperties(testPropertiesLocation)));
        PropertiesUtil.printProperties(driverProperties.get());
    }

    public static void cleanup(){
        logger.debug("Cleaning up....");
        driverProperties.remove();
    }

}
