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

    private static ThreadLocal<Properties> driverProperties = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(TestConfigurationProperty.class);

    public Properties getProperties(){
        return driverProperties.get();
    }

    public void createTestConfigProperties(String browser,String browserVersion,String browserLocation,String browserName){
        //Read properties
        driverProperties = ThreadLocal.withInitial(() -> PropertiesUtil.getProperties(EnvironmentConfig.getConfigValue(CoreConstants.BROWSER_CONF_KEY)+"remote.properties"));
        driverProperties.get().setProperty("browser",browser);
        driverProperties.get().setProperty("browser.version",browserVersion);
        driverProperties.get().setProperty("browser.run.environment",browserLocation);
        driverProperties.get().setProperty("browser.name",browserName);
        PropertiesUtil.printProperties(driverProperties.get());
    }

}
