package starter.testing.core.util.report;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Attribute;
import com.aventstack.extentreports.service.ExtentService;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import starter.testing.core.util.environment.EnvironmentConfig;
import starter.testing.core.util.environment.TestConfigurationProperty;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class ExtentReportListener implements ConcurrentEventListener{

    private static final Logger logger  = LoggerFactory.getLogger(ExtentReportListener.class);
    private static Boolean runStarted                            = Boolean.FALSE;
    private static ThreadLocal<Map<String, ExtentTest>> feature  = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> scenario = new InheritableThreadLocal();
    private static ThreadLocal<ExtentTest> step     = new InheritableThreadLocal();

    public ExtentReportListener() {
        logger.info("Creating Listener {}",Thread.currentThread());
        SparkReporterService.getInstance();
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class,   this::runStarted);
        publisher.registerHandlerFor(TestRunFinished.class,  this::runFinished);
        publisher.registerHandlerFor(TestSourceRead.class,   this::featureRead);
        publisher.registerHandlerFor(TestCaseStarted.class,  this::scenarioStarted);
        publisher.registerHandlerFor(TestStepStarted.class,  this::stepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
        publisher.registerHandlerFor(EmbedEvent.class,this::embedEvent);
    };

    //Here we create the reporter
    private synchronized void runStarted(TestRunStarted event) {
        ExtentService.getInstance().attachReporter(SparkReporterService.getInstance().getSparkReport());
        synchronized (runStarted){
            if(!runStarted){
                ExtentService.getInstance().setSystemInfo("Application Name", "ExtentReport");
                ExtentService.getInstance().setSystemInfo("Test Environment", EnvironmentConfig.getEnvironment().toUpperCase());
                ExtentService.getInstance().setSystemInfo("Author", System.getProperty("user.name"));
                try{
                    ExtentService.getInstance().setSystemInfo("Machine IP",   InetAddress.getLocalHost().getHostAddress());
                }catch (UnknownHostException exception){
                    logger.error("Failed to get IP address {}", exception.getMessage());
                }
                runStarted = true;
            }
        }
    };

    private synchronized void runFinished(TestRunFinished event) {
        ExtentService.getInstance().flush();
    };

    private synchronized void featureRead(TestSourceRead event) {
        String featureSource = event.getUri().toString();
        String featureName   = featureSource.split(".*/")[1];
        if(feature.get()==null){
            logger.debug("Feature map is null, creating new instance");
            feature.set(new HashMap<String, ExtentTest>());
        }
        if (feature.get().get(featureSource) == null) {
            logger.debug("Adding feature as test {}",featureName);
            feature.get().putIfAbsent(featureSource, ExtentService.getInstance().createTest(featureName));
        }
    };

    private synchronized void scenarioStarted(TestCaseStarted event) {
        String featureName   = event.getTestCase().getUri().toString();
        String featureDevice = TestConfigurationProperty.getThreadLocalProperties().getProperty("browser");
        logger.debug("Embedding device {}",featureDevice);
        ExtentTest extentTest = feature.get().get(featureName).createNode(event.getTestCase().getName());
        scenario.set(extentTest);
        scenario.get().assignDevice(featureDevice);

        //Assign the feature a device
        for(ExtentTest test: feature.get().values()){
            if(canAssignDevice(test, featureDevice)){
                test.assignDevice(featureDevice);
            }
        }
        //Assign the test tags
        for(ExtentTest test: feature.get().values()){
            for(String tag: event.getTestCase().getTags()){
                if(canAssignTag(test,tag)){
                    test.assignCategory(tag);
                }
            }
        }
        //Assign the scenario tags
        event.getTestCase().getTags().forEach(tag->scenario.get().assignCategory(tag));
    };

    private synchronized void stepStarted(TestStepStarted event) {
        String stepName = " ";
        String keyword  = "Triggered the hook :";
        // We checks whether the event is from a hook or step
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
            stepName = steps.getStep().getText();
            keyword  = steps.getStep().getKeyword();
            logger.debug("Logging hooks on the report stepName [{}] stepKeyword [{}]",stepName,keyword);
            ExtentTest extentTest = (scenario.get().createNode(Given.class, keyword + " " + stepName));
            step.set(extentTest);
        } else {
            // Same with HookTestStep
            logger.debug("Determine if will log hooks-before on the report hookStep is {}",event.getTestStep().getCodeLocation());
            if(isHooksAfterScenario(event.getTestStep().getCodeLocation())){
                ExtentTest extentTest = (scenario.get().createNode(Given.class, "After Scenario"));
                step.set(extentTest);
                logger.debug("Logging hooks step {}",event.getTestStep());
            }else{
                step.set(null);
            }
        }
    };

    private synchronized void stepFinished(TestStepFinished event) {
        logger.debug("Step finished event {}",event.getTestStep().getCodeLocation());
        if(step.get()!=null){
            logger.debug("Step finished with {}",event.getResult().getStatus().toString());
            switch (event.getResult().getStatus()) {
                case PASSED:
                    step.get().pass("Pass");
                    break;
                case FAILED:
                    step.get().fail("Fail");
                    break;
                case SKIPPED:
                    step.get().skip("Skipped");
                    break;
                case PENDING:
                    step.get().warning("Pending");
                    break;
                default:
                    step.get().warning("Warning");
                    break;
            }
        }
    };

    private synchronized void embedEvent(EmbedEvent event) {
        step.get().addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(event.getData()),"Attachment");
    };

    private boolean canAssignDevice(final ExtentTest test,final String device){
         for(Attribute deviceAttribute: test.getModel().getDeviceContext().getAll()){
             if (deviceAttribute.getName().equalsIgnoreCase(device))
                 return false;
         }
         return true;
    }

    private boolean canAssignTag(final ExtentTest test,final String tag){
        for(Attribute tagAttribute: test.getModel().getCategoryContext().getAll()){
            if (tagAttribute.getName().equalsIgnoreCase(tag))
                return false;
        }
        return true;
    }

    private boolean isHooksAfterScenario(String hooksStepName){
        return hooksStepName.contains("Hooks.afterScenario(io.cucumber.java.Scenario");
    }

}
