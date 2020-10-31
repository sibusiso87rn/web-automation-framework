package starter.testing.core.util.report;

import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.testing.core.bean.MongoConfig;

public class KlovReporterService {

    private static KlovReporterService klovReporterService = null;
    private static ExtentKlovReporter klov  = null;
    private static final Logger logger  = LoggerFactory.getLogger(SparkReporterService.class);

    private KlovReporterService(){
        klov = new ExtentKlovReporter("target/test-output/");
        klov.initMongoDbConnection("localhost", 27017);
        klov.setProjectName("Project");
        klov.setReportName("2.0");
        klov.initKlovServerConnection("http://localhost");
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

}
