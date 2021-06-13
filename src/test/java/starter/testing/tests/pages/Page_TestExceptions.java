package starter.testing.tests.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.BasePage;


@Component
@Lazy
@Scope("prototype")
public class Page_TestExceptions extends BasePage{

    @FindBy(tagName = "h2")
    private WebElement pageHeader;

    private Page_RowList foodRowList;
    private static final Logger logger = LogManager.getLogger(Page_TestExceptions.class);


    Page_TestExceptions(){
        super();
        foodRowList = new Page_RowList();
        logger.info("Found row list is {}",foodRowList.rowList());
    }

    public WebElement getPageHeader() {
        return pageHeader;
    }

    public Page_Row getPageRow(int index){
        return new Page_Row(foodRowList.getRowList().get(index));
    }

    public void waitForLoadingToComplete(){
        logger.info("Waiting for element to disappear");
        new WebDriverWait(driver, 15).until(ExpectedConditions.or(
                ExpectedConditions.invisibilityOf(driver.findElement(By.id("loading")))
        ));
    }

}
