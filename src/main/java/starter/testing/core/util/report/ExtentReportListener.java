package starter.testing.core.util.report;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.model.Attribute;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.service.ExtentService;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.testing.core.util.ApplicationContext;

import java.util.*;

public class ExtentReportListener implements ConcurrentEventListener{

    private static final Logger logger = LoggerFactory.getLogger(ExtentReportListener.class);

    private static ExtentSparkReporter spark;
    private static Boolean runStarted       = Boolean.FALSE;
    private Map<String, ExtentTest> feature = new HashMap<String, ExtentTest>();
    private ExtentTest scenario;
    private ExtentTest step;

    public ExtentReportListener() {
        logger.info("Creating Listener {}",Thread.currentThread());
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        // TODO Auto-generated method stub
        /*
         * :: is method reference , so this::collecTag means collectTags method in
         * 'this' instance. Here we says runStarted method accepts or listens to
         * TestRunStarted event type
         */
        publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::runFinished);
        publisher.registerHandlerFor(TestSourceRead.class, this::featureRead);
        publisher.registerHandlerFor(TestCaseStarted.class, this::ScenarioStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::stepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
        publisher.registerHandlerFor(EmbedEvent.class, this::embedEvent);
    };
    /*
     * Here we set argument type as TestRunStarted if you set anything else then the
     * corresponding register shows error as it doesn't have a listner method that
     * accepts the type specified in TestRunStarted.class
     */
    // Here we create the reporter
    private void runStarted(TestRunStarted event) {

        spark  = new ExtentSparkReporter("target/test-output/");
        //extent = new ExtentReports();
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Automation Report");
        //Create extent report instance with spark reporter
        ExtentService.getInstance().attachReporter(spark);

        synchronized (runStarted){
            if(!runStarted){
                ExtentService.getInstance().setSystemInfo("Application Name", "ExtentReport");
                ExtentService.getInstance().setSystemInfo("Platform", System.getProperty("os.name"));
                ExtentService.getInstance().setSystemInfo("Environment", "QA");
                runStarted = true;
            }
        }
    };
    // TestRunFinished event is triggered when all feature file executions are
    // completed
    private void runFinished(TestRunFinished event) {
        ExtentService.getInstance().flush();
    };
    // This event is triggered when feature file is read
    // here we create the feature node
    private void featureRead(TestSourceRead event) {
        String featureSource = event.getUri().toString();
        String featureName   = featureSource.split(".*/")[1];
        if (feature.get(featureSource) == null) {
            feature.putIfAbsent(featureSource, ExtentService.getInstance().createTest(featureName));
        }
    };
    // This event is triggered when Test Case is started
    // here we create the scenario node
    private void ScenarioStarted(TestCaseStarted event) {
        String featureName   = event.getTestCase().getUri().toString();
        String featureDevice = ApplicationContext.getTestBean().getThreadLocalProperties().getProperty("browser");
        scenario = feature.get(featureName).createNode(event.getTestCase().getName());
        scenario.assignDevice(featureDevice);

        //Assign device
        for(ExtentTest test: feature.values()){
            if(canAssignDevice(test, featureDevice)){
                test.assignDevice(featureDevice);
            }
        }
        event.getTestCase().getTags().forEach(tag->scenario.assignCategory(tag));
    };

    private void stepStarted(TestStepStarted event) {
        String stepName = " ";
        String keyword  = "Triggered the hook :";
        // We checks whether the event is from a hook or step
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
            stepName = steps.getStep().getText();
            keyword  = steps.getStep().getKeyword();
            logger.debug("Logging hooks on the report stepName [{}] stepKeyword [{}]",stepName,keyword);
            step = scenario.createNode(Given.class, keyword + " " + stepName);
        } else {
            // Same with HookTestStep
            logger.debug("Determine if will log hooks-before on the report hookStep is {}",event.getTestStep().getCodeLocation());
            if(isHooksAfterScenario(event.getTestStep().getCodeLocation())){
                step     = scenario.createNode(Given.class, "After Scenario");
                logger.debug("Logging hooks step {}",event.getTestStep());
            }else{
                step = null;
            }
        }
    };
    // This is triggered when TestStep is finished
    private void stepFinished(TestStepFinished event) {
        logger.debug("Step finished event {}",event.getResult().toString());
        if(step!=null){
            if (event.getResult().getStatus().toString() == "PASSED") {
                step.log(Status.PASS, "This passed");
            } else if (event.getResult().getStatus().toString() == "SKIPPED"){
                step.log(Status.SKIP, "This step was skipped ");
            } else {
                step.log(Status.FAIL, event.getResult().getError());
            }
        }
    };

    // This is triggered when TestStep is finished
    private void embedEvent(EmbedEvent event) {
        step.addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(event.getData()),"Attachment");
    };

    private boolean canAssignDevice(ExtentTest test, String device){
         for(Attribute deviceAttribute: test.getModel().getDeviceContext().getAll()){
             if (deviceAttribute.getName().equalsIgnoreCase(device))
                 return false;
         }
         return true;
    }

    private boolean isHooksAfterScenario(String hooksStepName){
        return hooksStepName.contains("Hooks.afterScenario(io.cucumber.java.Scenario");
    }

}
