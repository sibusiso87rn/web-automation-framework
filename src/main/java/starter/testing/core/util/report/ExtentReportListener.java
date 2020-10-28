package starter.testing.core.util.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.testng.annotations.AfterTest;
import starter.testing.core.util.ApplicationContext;
import starter.testing.core.util.environment.TestConfigurationProperty;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtentReportListener implements ConcurrentEventListener{

    private static ExtentSparkReporter spark;
    private static ExtentReports extent;

    Map<String, ExtentTest> feature = new HashMap<String, ExtentTest>();
    ExtentTest scenario;
    ExtentTest step;

    public ExtentReportListener() {
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
        extent = new ExtentReports();
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("My Tittle");
        //Create extent report instance with spark reporter
        extent.attachReporter(spark);
        extent.setSystemInfo("Application Name", "ExtentReport");
        extent.setSystemInfo("Platform", System.getProperty("os.name"));
        extent.setSystemInfo("Environment", "QA");
    };
    // TestRunFinished event is triggered when all feature file executions are
    // completed
    private void runFinished(TestRunFinished event) {
        extent.flush();
    };
    // This event is triggered when feature file is read
    // here we create the feature node
    private void featureRead(TestSourceRead event) {
        String featureSource = event.getUri().toString();
        String featureName = featureSource.split(".*/")[1];
        if (feature.get(featureSource) == null) {
            feature.putIfAbsent(featureSource, extent.createTest(featureName));
        }
    };
    // This event is triggered when Test Case is started
    // here we create the scenario node
    private void ScenarioStarted(TestCaseStarted event) {
        String featureName = event.getTestCase().getUri().toString();
        scenario = feature.get(featureName).createNode(event.getTestCase().getName());
        scenario.assignDevice(ApplicationContext.getTestConfiguration().getProperties().getProperty("browser"));
    };
    // step started event
    // here we creates the test node
    private void stepStarted(TestStepStarted event) {
        String stepName = " ";
        String keyword  = "Triggered the hook :";
    // We checks whether the event is from a hook or step
        if (event.getTestStep() instanceof PickleStepTestStep) {
    // TestStepStarted event implements PickleStepTestStep interface
    // Which have additional methods to interact with the event object
    // So we have to cast TestCase object to get those methods
            PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
            stepName = steps.getStep().getText();
            keyword  = steps.getStep().getKeyword();
        } else {
    // Same with HoojTestStep
            HookTestStep hoo = (HookTestStep) event.getTestStep();
            stepName = hoo.getHookType().name();
        }
        step = scenario.createNode(Given.class, keyword + " " + stepName);
    };
    // This is triggered when TestStep is finished
    private void stepFinished(TestStepFinished event) {
        if (event.getResult().getStatus().toString() == "PASSED") {
            step.log(Status.PASS, "This passed");
        } else if (event.getResult().getStatus().toString() == "SKIPPED"){
            step.log(Status.SKIP, "This step was skipped ");
        } else {
            step.log(Status.FAIL, "This failed");
        }
    };

    // This is triggered when TestStep is finished
    private void embedEvent(EmbedEvent event) {
        step.addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(event.getData()),"evidence");
    };

}
