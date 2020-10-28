package starter.testing.core.util.report;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import starter.testing.core.util.ApplicationContext;
import starter.testing.core.util.environment.EnvironmentConfig;
import starter.testing.core.util.file.FilesUtil;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sibusiso Radebe on 2020/02/20.
 */

public class CucumberReport {

    private static final Logger logger = LoggerFactory.getLogger(CucumberReport.class);
    private String trendsPathName;
    private String projectName = "Automation Project";
    private int trendsLimit;


    public CucumberReport(){
        this.trendsLimit    = 10;
        this.trendsPathName = "target/testing-trends.json";
    }

    public void createReport(){

        logger.info("******************Building Report******************");

        File reportOutputDirectory = new File("target");
        List<String> jsonFiles = new ArrayList<>();

        jsonFiles.add("target/cucumber-report.json");
        logger.info("Setting report target to : target/cucumber-report.json");

        String projectName = "Web Automation";

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        String buildNumber = "Build Number 1";
        configuration.setBuildNumber(buildNumber);

        //Trends this will file will need to maintained and not deleted in order to keep the trend
        try {
            logger.info("Saving trends file on location: "+ trendsPathName);
            configuration.setTrends(FilesUtil.getFile(trendsPathName),this.trendsLimit);
        }catch (Exception e){
            logger.warn("Trends file not found, creating new one",e);
            configuration.setTrends(new File(trendsPathName),this.trendsLimit);
        }

        try{
            configuration.addClassifications("Sprint Name", "Sprint 1");
            configuration.addClassifications("Test Environment", EnvironmentConfig.getEnvironment().toUpperCase());
            configuration.addClassifications("Machine IP",   InetAddress.getLocalHost().getHostAddress());
            configuration.addClassifications("Machine Name", InetAddress.getLocalHost().getHostName());
        }catch(UnknownHostException e){
            logger.error("Failed to get localhost address", e);
        }

        configuration.addClassifications("Username", System.getProperty("user.name"));
        configuration.addClassifications("Browser",  ApplicationContext.getTestConfiguration().getProperties().getProperty("browser").toUpperCase());
        configuration.addClassifications("Browser Version",ApplicationContext.getTestConfiguration().getProperties().getProperty("browser.version",""));
        configuration.addClassifications("Platform Version", System.getProperty("os.name"));

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);

        try {
            Reportable result = reportBuilder.generateReports();
        }catch (Exception e){
            logger.error("Report fails to generate as there are no features were available to execute:",e);
        }
    }
}
