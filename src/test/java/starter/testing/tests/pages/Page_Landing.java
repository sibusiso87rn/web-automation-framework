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

    @FindBy(css = "h1")
    WebElement lblPageTitle;

    @FindBy(css = "p")
    WebElement lblPageParagraph;

    public WebElement getLblPageTitle() {
        return lblPageTitle;
    }

    public WebElement getLblPageParagraph() {
        return lblPageParagraph;
    }
}
