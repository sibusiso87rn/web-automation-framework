package starter.testing.core.util.file;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;


/**
 * Created by Sibusiso Radebe
 */

public class PropertiesUtil {

    private static final Logger logger = LogManager.getLogger(PropertiesUtil.class);


    public static Properties getProperties(String propertiesFileName){
        Properties properties       = new Properties();
        FileInputStream inputStream = null;
        try {
            inputStream  = new FileInputStream(FilesUtil.getFileAbsolutePath(propertiesFileName));
        }catch (Exception e){
            throw new RuntimeException("Failed to read file on path : " + propertiesFileName);
        }
        if(inputStream == null) {
            throw new RuntimeException("Property file '" + propertiesFileName + "' not found in the classpath");
        } else {
            try {
                properties.load(inputStream);
                inputStream.close();
            } catch (IOException var4) {
                throw new RuntimeException("Failed to load Properties File", var4);
            }
        }

        return properties;
    }

    public static Properties importToSystemProperties(Properties properties) {
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.setProperty((String)entry.getKey(), (String)entry.getValue());
        }
        return System.getProperties();
    }

    public static Properties getPropertyByVersion(String version){
        Properties properties                  = new Properties();
        Set<Map.Entry<Object, Object>> entries = System.getProperties().entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            String key   = (String)entry.getKey();
            String value = (String)entry.getValue();
            if(key.startsWith(version)){
                properties.setProperty((String)entry.getKey(), (String)entry.getValue());
            }
        }
        return properties;
    }

    public static void printProperties(Properties properties){
        logger.debug("");
        logger.debug("===Current Properties===");
        properties.entrySet().forEach(entry -> logger.debug("{}={}",entry.getKey(),entry.getValue()));
        logger.debug("===Current Properties===");
        logger.debug("");
    }

    public static String getProperty(String key) {
        logger.debug("Getting property value : " + key);
        String propertyValue = System.getProperty(key);
        if(propertyValue==null){
            logger.error(key +" not found value is null");
        }else{
            logger.debug("Property found key is ["+key+"] : value ["+propertyValue+"]");
        }
        return propertyValue;
    }

    public static void importAppiumSetting(String fileName) throws Exception{
        Properties appiumProperties = getProperties(fileName);
        importToSystemProperties(appiumProperties);
        logger.info("Finished importing appium properties");
        //Print properties file for debugging
        printProperties(System.getProperties());
    }

    public static Properties mergeProperties(Properties... properties) {
        logger.debug("Merging props");
        return Stream.of(properties)
                .collect(Properties::new, Map::putAll, Map::putAll);
    }

}
