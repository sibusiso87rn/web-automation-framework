package starter.testing.tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.BasePage;

@Component
@Lazy
@Scope("prototype")
public class Page_Landing extends BasePage {

    @FindBy(name = "btnK")
    WebElement btnSearchButton;

    @FindBy(name = "btnI")
    WebElement btnImFeelingLucky;

    @FindBy(id = "hplogo")
    WebElement lblLogo;

    public WebElement getBtnSearchButton() {
        return btnSearchButton;
    }

    public WebElement getBtnImFeelingLucky() {
        return btnImFeelingLucky;
    }

    public WebElement getLblLogo() {
        return lblLogo;
    }
}
