package starter.testing.tests.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.bean.ApplicationContext;
import starter.testing.tests.actions.TestExceptionsActions;

@Component
@Lazy
@Scope("prototype")
public class StepDef_TestExceptions {

    final TestExceptionsActions exceptionsActionsPage = (TestExceptionsActions) ApplicationContext.getComponent(TestExceptionsActions.class);

    //Givens
    @Given("The user is on the test exceptions page")
    public void theUserIsOnTheTestExceptionsPage() {
        exceptionsActionsPage.validatePageHeader("Test Exceptions");
    }

    //Then
    @Then("The user clicks the add button")
    public void theUserClicksTheAddButton() {
        exceptionsActionsPage.clickTheAddButton();
    }

    @And("The a new row is added")
    public void theANewRowIsAdded() {
        exceptionsActionsPage.validateNewRowAdded();
    }

}
