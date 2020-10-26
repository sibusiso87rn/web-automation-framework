package starter.testing.core.interfaces;

import org.aeonbits.owner.Config;

//Reads the system properties and looks for the "environment" property
@Config.Sources({
        "classpath:test.properties"
})

//Service Properties
public interface IEnvironmentProperties extends Config {
    @Key("test.execution.environment")
    String getExecutionEnvironment();

    @Key("test.config.path")
    String getConfigPath();

}
