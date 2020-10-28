package starter.testing.tests.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.util.ApplicationContext;
import starter.testing.tests.actions.Action_LandingPage;

@Component
@Lazy
@Scope("prototype")
public class StepDef_LandingPage {


    Action_LandingPage landing = (Action_LandingPage) ApplicationContext.getComponent(Action_LandingPage.class);

    //Givens
    @Given("The user is on the google search page")
    public void theUserIsOnTheLandingPage() {
        landing.validateGoogleLogo();
    }

    //Then
    @Then("The search button is visible")
    public void theSearchButtonIsVisible() {
        landing.validateGoogleSearchButton("Google Search");
    }

    @And("The I'm feeling lucky button is visible")
    public void theIMFeelingLuckyButtonIsVisible() {
        landing.validateImFeelingLuckyButton("I'm Feeling Lucky");
    }
}
