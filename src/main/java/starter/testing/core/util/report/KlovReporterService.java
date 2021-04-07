package starter.testing.core.util.report;

import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.testing.core.bean.ApplicationContext;
import starter.testing.core.util.report.config.ReportConfig;

import java.util.Date;

public class KlovReporterService {

    private static KlovReporterService klovReporterService = null;
    private static ExtentKlovReporter klov  = null;
    private static final Logger logger  = LoggerFactory.getLogger(SparkReporterService.class);

    private KlovReporterService(){
        ReportConfig reportConfig = (ReportConfig) ApplicationContext.getComponent(ReportConfig.class);
        klov = new ExtentKlovReporter(reportConfig.getReportOutPutDir());
        logger.info("Connecting to mongo database ");
        klov.initMongoDbConnection(reportConfig.getKlovDatabaseHostame(), reportConfig.getKlovDatabasePort());
        klov.setProjectName(reportConfig.getProjectName());
        klov.setReportName(getRunId());
        klov.initKlovServerConnection(reportConfig.getKlovServerName());
    }

    public static KlovReporterService getInstance(){
        if(klovReporterService==null) {
            logger.info("SparkReporterService service is null, creating new instance");
            klovReporterService = new KlovReporterService();
        }
        return klovReporterService;
    }

    public ExtentKlovReporter getKlovReport(){
        return klov;
    }

    public static String getRunId(){
        return new Date().toString()+"-Run";
    }

}
