package starter.testing.core.util.environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import starter.testing.core.interfaces.IEnvironmentProperties;
import starter.testing.core.util.environment.config.Config;
import starter.testing.core.util.environment.config.ConfigValue;
import starter.testing.core.util.environment.config.DataBaseConfig;
import starter.testing.core.util.environment.config.RestServiceConfig;
import starter.testing.core.util.file.FilesUtil;


public class EnvironmentConfig {

    private static Config config;

    //Singleton instance
    private static EnvironmentConfig environmentConfig   = null;

    private static final Logger logger = LogManager.getLogger(EnvironmentConfig.class);

    private EnvironmentConfig() throws Exception{
        initConfig();
    }

    private static void initConfig() throws Exception{

        String configfilepath;
        String environment;
        try {
            environment = System.getProperty("environment").toLowerCase();
        }catch (NullPointerException e){
            throw new Exception("Test environment variable is null, please select environment");
        }

        IEnvironmentProperties environmentProperties = ConfigFactory.create(IEnvironmentProperties.class);

        logger.info("Loading test environment configuration for : "+ environment.toUpperCase());
        configfilepath = environmentProperties.getConfigPath()+environment.toLowerCase()+".json";

        logger.info("Using config path : "+ configfilepath);

        //Loads the configs from file
        config = loadEnvironmentConfigsFromFile(configfilepath);

    }

    private EnvironmentConfig(String path) throws Exception{
        config = loadEnvironmentConfigsFromFile(path);
    }

    //Load configuration from a csv
    private static Config loadEnvironmentConfigsFromFile(String path) throws Exception{
        Config config;
        Gson gson  = new GsonBuilder().create();
        logger.debug("Loading json from path " + path);
        config  = gson.fromJson( FilesUtil.getStringContent(path), Config.class);
        return config;
    }

    private static Config getCurrentConfiguration(){
       return config;
    }

    public static EnvironmentConfig getInstance(){
        try {
            if(environmentConfig==null){
                environmentConfig = new EnvironmentConfig();
            }else{
                return environmentConfig;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return environmentConfig;
    }

    private static ConfigValue getConfig(String key){

        for(int i = 0; i < getCurrentConfiguration().getConfigValues().size(); i ++){
            if(getCurrentConfiguration().getConfigValues().get(i).getKey().equals(key)){
                logger.debug("Found ConfigValue with key " + key);
                return getCurrentConfiguration().getConfigValues().get(i);
            }
        }
        logger.error("ConfigValue with key " + key + " not found");
        return null;
    }

    public static String getConfigValue(String key){
        ConfigValue results = getConfig(key);
        logger.debug("Searching for config key {}",key);
        if(results!=null){
            logger.debug("[{}] key value found, the value is [{}]",key,results.getValue());
            return resolveEnvironment(results.getValue());
        }else{
            logger.error("[{}] key value not found, the value is [{}]",key,null);
            return null;
        }
    }

    public static Boolean getBooleanValue(String key){
       return Boolean.parseBoolean(getConfigValue(key));
    }

    public static DataBaseConfig getDatabaseConfigsByKey(String key){
        for(int i = 0; i < getCurrentConfiguration().getDataBaseList().size(); i ++){
            if(getCurrentConfiguration().getDataBaseList().get(i).getKey().equals(key)){
                logger.debug("Found DatabaseConfigValue with key " + key);
                return getCurrentConfiguration().getDataBaseList().get(i);
            }
        }
        logger.error("Database config with key " + key + " not found");
        return null;
    }

    public static RestServiceConfig getRestServiceConfigByKey(String key){
        for(int i = 0; i < getCurrentConfiguration().getRestServiceList().size(); i ++){
            if(getCurrentConfiguration().getRestServiceList().get(i).getKey().equals(key)){
                logger.debug("Found DatabaseConfigValue with key " + key);
                return getCurrentConfiguration().getRestServiceList().get(i);
            }
        }
        logger.error("Database config with key " + key + " not found");
        return null;
    }

    private static String resolveEnvironment(String element){
        return element.replace("${environment}",getEnvironment());
    }

    public static String getEnvironment(){
        return getCurrentConfiguration().getEnvironment().getEnvironmentName();
    }

    public static String getApplicationName(){
        return getConfigValue("application.name");
    }

    public static String getDriverLocation(){
        return getConfigValue("web.driver.location").replace("${currentos}",getCurrentConfiguration().getUserOs().getOS());
    }

    public String toString(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(config);
    }

 }
