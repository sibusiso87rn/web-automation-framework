package starter.testing.core.util.report;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkReporterService {

    private static SparkReporterService sparkReporterService = null;
    private static ExtentSparkReporter spark  = null;

    private static final Logger logger  = LoggerFactory.getLogger(SparkReporterService.class);

    private SparkReporterService(){
       spark = new ExtentSparkReporter("target/test-output/");
       spark.config().setTheme(Theme.STANDARD);
       spark.config().setDocumentTitle("Automation Report");
    }

    public static SparkReporterService getInstance(){
        if(sparkReporterService==null){
            logger.info("SparkReporterService service is null, creating new instance");
            sparkReporterService = new SparkReporterService();
        }
        return sparkReporterService;
    }

    public ExtentSparkReporter getSparkReport(){
        return spark;
    }

}
