package starter.testing.core.util.report;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.testing.core.bean.ApplicationContext;
import starter.testing.core.util.report.config.ReportConfig;

public class SparkReporterService {

    private static SparkReporterService sparkReporterService = null;
    private static ExtentSparkReporter spark  = null;
    private static final Logger logger  = LoggerFactory.getLogger(SparkReporterService.class);

    private SparkReporterService(){
        ReportConfig reportConfig = (ReportConfig) ApplicationContext.getComponent(ReportConfig.class);
        spark = new ExtentSparkReporter(reportConfig.getReportOutPutDir());
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle(reportConfig.getProjectName());
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
