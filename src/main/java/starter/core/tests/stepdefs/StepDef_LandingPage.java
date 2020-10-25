package starter.core.tests.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import starter.core.util.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.core.tests.actions.Action_LandingPage;

@Component
@Lazy
@Scope("prototype")
public class StepDef_LandingPage {

    //Has all the user actions and validations that can be done on the qualifying criteria screen
    //@Autowired
    Action_LandingPage contactsPage = (Action_LandingPage)ApplicationContext.getComponent(Action_LandingPage.class);

    //Givens
    @Given("The user is on the landing page")
    public void theUserIsOnTheLandingPage() {

    }

    //Then
    @Then("The landing page paragraph reads {string}")
    public void theContactsPageLabelReads(String expectedParagraphMessage) {
        contactsPage.validatePageParagraph(expectedParagraphMessage);
    }
}
