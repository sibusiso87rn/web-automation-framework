package starter.testing.core.util.report.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import starter.testing.core.util.environment.EnvironmentConfig;
import starter.testing.core.util.environment.config.DataBaseConfig;
import starter.testing.core.util.environment.config.RestServiceConfig;

import static starter.testing.core.CoreConstants.*;

@Configuration
@Lazy
public class ReportConfig {

    private final String reportOutPutDir;
    private final RestServiceConfig klovServerConfig;
    private final DataBaseConfig klovMongoDatabase;
    private final String projectName;
    private final String trendsPathName;
    private final String cucumberReportPathName;
    private final String environment;
    private final boolean createKlovReport;
    private final boolean createSparkReport;

    public ReportConfig(){
        EnvironmentConfig.getInstance();
        reportOutPutDir   = EnvironmentConfig.getConfigValue(REPORT_OUTPUT_DIR);
        klovServerConfig  = EnvironmentConfig.getRestServiceConfigByKey(KLOV_REPORT_SERVER_KEY);
        klovMongoDatabase = EnvironmentConfig.getDatabaseConfigsByKey(KLOV_MONGO_SERVER_KEY);
        projectName    = EnvironmentConfig.getApplicationName();
        trendsPathName = EnvironmentConfig.getConfigValue(CUCUMBER_TRENDS_OUTPUT_DIR);
        cucumberReportPathName = EnvironmentConfig.getConfigValue(CUCUMBER_REPROT_OUTPUT_DIR);
        environment            = EnvironmentConfig.getEnvironment().toUpperCase();

        createKlovReport  = EnvironmentConfig.getBooleanValue(KLOV_REPORT);
        createSparkReport = EnvironmentConfig.getBooleanValue(SPARK_REPORT);
    }

    private RestServiceConfig getKlovServerConfig() {
        return klovServerConfig;
    }

    public String getKlovServerName(){
        return getKlovServerConfig().getFullPath();
    }

    private DataBaseConfig getKlovMongoDatabase() {
        return this.klovMongoDatabase;
    }

    public String getKlovDatabaseName(){
        return this.getKlovMongoDatabase().getDatabaseName();
    }

    public int getKlovDatabasePort(){
        return Integer.parseInt(getKlovMongoDatabase().getDatabasePort());
    }

    public String getReportOutPutDir() {
        return reportOutPutDir;
    }

    public String getProjectName(){
        return projectName;
    }

    public String getCucumberReportPathName() {
        return cucumberReportPathName;
    }

    public String getTrendsPathName() {
        return trendsPathName;
    }

    public String getEnvironment(){
        return environment;
    }

    public boolean isCreateKlovReport() {
        return createKlovReport;
    }

    public boolean isCreateSparkReport() {
        return createSparkReport;
    }
}
