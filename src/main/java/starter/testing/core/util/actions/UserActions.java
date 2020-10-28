package starter.testing.core.util.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import starter.testing.core.util.ApplicationContext;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Common user actions using the screen
 */

public class UserActions {

    private static final Logger logger = LogManager.getLogger(UserActions.class);

    //Reads the wait time from the system props, defaults to 30 seconds if not specified
    public static int OBJECT_WAIT_TIME = Integer.parseInt(System.getProperty("object.wait.time","30"));

    public static void click(WebElement element) {
        click(element, ApplicationContext.getWebDriver());
    }

    public static void clear(WebElement element) {
        clear(element, ApplicationContext.getWebDriver());
    }

    public static void isElementSelected(WebElement element) {
        logger.info("Checking if the element is selected");
        checkSelected(element, true, ApplicationContext.getWebDriver());
    }

    public static void isElementUnSelected(WebElement element) {
        logger.info("Checking if the element is un selected");
        checkSelected(element, false, ApplicationContext.getWebDriver());
    }

    public static void isElementEnabled(WebElement element){
        isElementEnabled(element, ApplicationContext.getWebDriver());
    }

    public static void isElementDisabled(WebElement element){
        isElementDisabled(element, ApplicationContext.getWebDriver());
    }

    public static void isElementPresent(WebElement element) {
        isElementPresent(element, ApplicationContext.getWebDriver());
    }

    public static void isElementPresent(WebElement element, WebDriver driver) {
        waitForElementToBeClickable(element,driver);
    }

    public static void isElementEnabled(WebElement element, WebDriver driver) {
        try {
            isElementPresent(element,driver);
            assertThat(element.isEnabled(), is(equalTo(true)));
            logger.info("Element is enabled " + element.toString());
        } catch (Exception e) {
            logger.error("Element is not enabled " + element.toString());
            assertThat(false, is(equalTo(true)));
        }
    }

    public static void isElementDisabled(WebElement element, WebDriver driver) {
        try {
            isElementPresent(element,driver);
            logger.info("Element is disabled " + element.toString());
            assertThat(element.isEnabled(), is(equalTo(false)));
        } catch (Exception e) {
            logger.error("Element is not disabled ");
            e.printStackTrace();
            assertThat(false, is(equalTo(true)));
        }
    }

    public static void isElementInVisible(WebElement element, String failureMessage){
        try{
            //Check if the element is displayed
            assertThat(failureMessage,element.isDisplayed(), is(equalTo(false)));
        }catch (NoSuchElementException e){
            //Catch the exception and if the element is not found, meaning its not there
            assertThat(true, is(equalTo(true)));
        }
    }

    public static void waitForElementToBeVisible(WebElement element) {
        waitForElementToBeVisible(element, ApplicationContext.getWebDriver());
    }

    public static void waitForElementToInVisible(WebElement element){
        waitForElementToInVisible(element, ApplicationContext.getWebDriver());
    }

    public static void input(WebElement element, String data) {
        logger.info("Ready to input data : " + data);
        input(element, ApplicationContext.getWebDriver(),data);
        logger.info("Input data complete : success");

    }

    public static void input(WebElement element, WebDriver driver, String data) {
        try {
            click(element);
            logger.debug("Sending keys to element " + element.toString());
            element.sendKeys(data);
        } catch (Exception e) {
            logger.error("Failed to input data on element :" + e.getMessage());
            assertThat(false, is(equalTo(true)));
        }
    }

    public static void waitForElementToBeVisible(WebElement element, WebDriver driver) {
        try {
            Wait wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(OBJECT_WAIT_TIME))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);
            wait.until((Function) ExpectedConditions.visibilityOfAllElements(element));
        } catch (Exception e) {
            logger.error("Failed to locate element after 30 seconds :" + e.getMessage());
            assertThat(false, is(equalTo(true)));        }
    }

    public static void waitForElementToInVisible(WebElement element, WebDriver driver) {
        try {

            //First check if the element is present for a limited amount of time OBJECT_WAIT_TIME
            Wait wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(OBJECT_WAIT_TIME))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);
            wait.until((Function) ExpectedConditions.invisibilityOf(element));

            //If the element is still visible after OBJECT_WAIT_TIME then we assume it did not disappear
            logger.warn("Waited for "+OBJECT_WAIT_TIME+" seconds and the element is present");

            //Will enforce a failure since the element is still displayed
            assertThat(element.isDisplayed(), is(equalTo(false)));
        } catch (Exception e) {
            //If the element is no present
            logger.warn(e.getMessage());
            assertThat(false, is(equalTo(false)));
        }
    }

    public static void waitForElementToBeClickable(WebElement element, WebDriver driver){
        try {
            logger.info("Checking if the element is clickable...the wait time is " + OBJECT_WAIT_TIME + " seconds");
            Wait wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(OBJECT_WAIT_TIME))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);
            wait.until((Function) ExpectedConditions.elementToBeClickable(element));
            logger.info("Checking if the element is clickable successful, element found " + element.toString());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to locate element after 30 seconds :" + e.getMessage());
            assertThat(false, is(equalTo(true)));
        }
    }

    private static void checkSelected(WebElement element,boolean expectedSelectedStatus, WebDriver driver) {
        try {
            //Check if the element first
            isElementPresent(element,driver);

            logger.info("Checking if element " + element.toString() + " is selected");
            //logger.info("Element checked status is " + element.isSelected() + " expecting "+ expectedSelectedStatus + " status");
            logger.info("Element value is " + element.getText());

            //Check if the element is selected
            assertThat(element.isSelected(), is(equalTo(expectedSelectedStatus)));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Element interacting with element : " + e.getMessage());
            assertThat(false, is(equalTo(true)));
        }
    }

    public static void click(WebElement element, WebDriver driver) {
        try {
            waitForElementToBeClickable(element,driver);
            logger.debug("About to click on element "+ element.toString());
            element.click();
            logger.debug("Success clicking on element " + element.toString());
        } catch (Exception e) {
            logger.error("Failed to click on element :" + e.getMessage());
            assertThat(false, is(equalTo(true)));        }
    }

    public static void clear(WebElement element, WebDriver driver) {
        try {
            element.clear();
        } catch (Exception e) {
            logger.error("Failed to locate element :" + e.getMessage());
            assertThat(false, is(equalTo(true)));        }
    }

    //Combines texts from objects into 1
    public static StringBuffer getTextFromElements(List<WebElement> elementList){
        StringBuffer elementText = new StringBuffer();
        logger.info("Appending text for elements, list size is " + elementList.size());
        for(WebElement element: elementList) {
            logger.debug("Appending element text for element " + element.toString());
            logger.debug("Element text is " + element.getText());
            logger.debug("");
            elementText.append(element.getText());
        }
        return elementText;
    }

    //Adds text to each of the elements
    public static void addTextToElements(List<WebElement> elementList,String content){
        StringBuffer elementText = new StringBuffer();
        logger.info("Adding string text to elements, list size is " + elementList.size() + ", the string contents are " + content);
        for(int i = 0; i < elementList.size(); i++){
            String data = Character.toString(content.charAt(i));
            elementList.get(i).click();
            elementList.get(i).sendKeys(data);
            elementText.append(data);
        }
        logger.info("Added text " + elementText );
    }

    public static StringBuffer getTextFromElement(WebElement element){
        waitForElementToBeVisible(element);
        logger.info("Getting text from element " + element.getText());
        return new StringBuffer().append(element.getText());
    }

    public static void validateText(WebElement element,String expectedText){
        logger.info("Validating object text");
        waitForElementToBeVisible(element);
        logger.info("Element text is " +  element.getText());
        validateText(element.getText(),expectedText);
    }

    public static void validateText(String actualText,String expectedText){
        logger.info("Executing text compare Actual ["+actualText+"] Expecting [" + expectedText+"]");
        assertThat(actualText, is(equalTo(expectedText)));
    }

    public void waitForExistence(final WebElement element) {
        for (int attempt = 0; attempt < OBJECT_WAIT_TIME; attempt++) {
            try {
                waitForElementToBeVisible(element);
                break;
            } catch (Exception e) {
                getDriver().manage().timeouts().implicitlyWait(1, SECONDS);
            }
        }
    }

    private static WebDriver getDriver(){
        return ApplicationContext.getWebDriver();
    }

    public static void elementTextMatchesPattern(WebElement element, Pattern expectedPattern){
        elementTextMatchesPattern(element, getDriver(),expectedPattern);
    }

    public static void elementTextMatchesPattern(WebElement element, WebDriver driver, Pattern expectedPattern){
        logger.info("Checking if element text " + element.getText() + " matches " + expectedPattern.toString());
        Matcher matcher = expectedPattern.matcher(element.getText());
        assertThat("Expected result does match regular expression format : " + expectedPattern.toString(),
                matcher.matches(),
                is(true));
    }

    public static void debugElementProperties(WebElement element){

        String[] elementProps = {"UID",
                "accessibilityContainer",
                "accessible",
                "enabled",
                "frame",
                "label",
                "name",
                "rect",
                "type",
                "value",
                "visible",
                "wdAccessibilityContainer",
                "wdAccessible",
                "wdEnabled",
                "wdFrame",
                "wdLabel",
                "wdName",
                "wdRect",
                "wdType",
                "wdUID",
                "wdValue",
                "wdVisible"};

        for (String property: elementProps){
            try {
                logger.debug("Element Prop [" + property + "] value ["+element.getAttribute(property)+"]");
            }catch (Exception e){

            }
        }
        logger.debug("Element Prop [text] \tvalue ["+element.getText()+"]");
        logger.debug("Element Prop [css] \tvalue ["+element.getCssValue("display")+"]");

    }

}
