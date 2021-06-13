package starter.testing.tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.BasePage;

import java.util.List;

@Component
@Lazy
@Scope("prototype")
public class Page_RowList extends BasePage {

    @FindBys({@FindBy(className = "row")})
    private List<WebElement> rowList;

    public List<WebElement> getRowList() {
        return rowList;
    }

    public int rowList(){
        return getRowList().size();
    }

}
