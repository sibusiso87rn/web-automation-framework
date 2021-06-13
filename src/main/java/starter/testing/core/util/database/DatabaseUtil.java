package starter.testing.core.util.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import starter.testing.core.util.environment.config.DataBaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final Logger logger      = LogManager.getLogger(DatabaseUtil.class);

    public static Connection getDatabaseConnection(final String dataBase, final String server,
                                         final String port, final String dbUserName , final String dbPassword){
        Connection connection = null;

        try{
            Class.forName("org.postgresql.Driver").newInstance();
            String conUrl = "jdbc:postgresql://" + server + ":" + port+ "/" + dataBase ;

            logger.debug("DB connection string "+ conUrl);
            connection = DriverManager.getConnection(conUrl, dbUserName ,dbPassword );

        }catch(SQLException e){
            logger.error(e.getMessage());
        }catch(ClassNotFoundException e){
            logger.error(e.getMessage());
        }catch(InstantiationException e){
            logger.error(e.getMessage());
        }catch(IllegalAccessException e){
            logger.error(e.getMessage());
        }

        logger.info("Found connection " + (connection!=null));

        return connection;
    }

    public static Connection getDatabaseConnection(DataBaseConfig dataBaseConfig){
        logger.debug("Connecting to database with props");
        logger.debug(dataBaseConfig.toString());
        return DatabaseUtil.getDatabaseConnection(dataBaseConfig.getDatabaseName(),dataBaseConfig.getDatabaseServer(),dataBaseConfig.getDatabasePort(),dataBaseConfig.getUser().getUsername(),dataBaseConfig.getUser().getPassword());
    }

    public static Connection getCVSDataConnection(String csvpath) throws Exception{
        return DriverManager.getConnection("jdbc:relique:csv:"+csvpath);
    }

}
