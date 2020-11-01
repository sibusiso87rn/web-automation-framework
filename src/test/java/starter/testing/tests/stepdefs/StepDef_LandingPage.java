package starter.testing.tests.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.bean.ApplicationContext;
import starter.testing.tests.actions.ActionLandingPage;

@Component
@Lazy
@Scope("prototype")
public class StepDef_LandingPage {

    final ActionLandingPage actionLandingPage = (ActionLandingPage) ApplicationContext.getComponent(ActionLandingPage.class);

    //Givens
    @Given("The user is on the google search page")
    public void theUserIsOnTheLandingPage() {
        actionLandingPage.validateGoogleLogo();
    }

    //Then
    @Then("The search button is visible")
    public void theSearchButtonIsVisible() {
        actionLandingPage.validateGoogleSearchButton("Google Search");
    }

    @And("The I'm feeling lucky button is visible")
    public void theIMFeelingLuckyButtonIsVisible() {
        actionLandingPage.validateImFeelingLuckyButton("I'm Feeling Lucky");
    }
}
