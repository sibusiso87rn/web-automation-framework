package starter.testing.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.BasePage;

@Component
@Lazy
@Scope("prototype")
public class Page_Row extends BasePage {

    private WebElement currentRow;

    Page_Row(WebElement currentRow){
        super();
        this.currentRow = currentRow;
    }

    public WebElement getBtnAddButton() {
        return currentRow.findElement(By.id("add_btn"));
    }

    public WebElement getBtnSaveButton() {
        return currentRow.findElement(By.id("save_btn"));
    }

    public WebElement getBtnRemoveButton() {
        return currentRow.findElement(By.id("remove_btn"));
    }

    public WebElement getBtnEditButton() {
        return currentRow.findElement(By.id("edit_btn"));
    }

    public WebElement getTxtFoodInput() {
        return currentRow.findElement(By.className("input-field"));
    }

    public WebElement getLblRowLabel() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("loading"))
        ));
        return currentRow.findElement(By.tagName("label"));
    }

}
