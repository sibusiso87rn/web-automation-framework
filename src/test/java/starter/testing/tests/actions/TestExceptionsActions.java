package starter.testing.tests.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.util.actions.UserActions;
import starter.testing.tests.pages.Page_TestExceptions;

@Lazy
@Scope("prototype")
@Component
public class TestExceptionsActions {

    @Autowired
    private Page_TestExceptions testExceptionsPage;
    private int currentRow = 0;

    public void validatePageHeader(String expectedPageHeader){
        UserActions.validateText(testExceptionsPage.getPageHeader(),expectedPageHeader);
    }

    public void clickTheAddButton(){
        UserActions.click(testExceptionsPage.getPageRow(this.getCurrentRow()).getBtnAddButton());
        ++currentRow;
    }

    public void clickTheRemoveButton(){
        UserActions.click(testExceptionsPage.getPageRow(this.getCurrentRow()).getBtnRemoveButton());
        --currentRow;
    }

    private int getCurrentRow(){
        return currentRow;
    }

    public void validateNewRowAdded(){
        testExceptionsPage.waitForLoadingToComplete();
        UserActions.validateText(testExceptionsPage.getPageRow(this.getCurrentRow()).getLblRowLabel(),"Row "+getCurrentRow());
    }

}
