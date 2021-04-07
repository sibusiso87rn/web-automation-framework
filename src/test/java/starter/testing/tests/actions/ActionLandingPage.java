package starter.testing.tests.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.util.actions.UserActions;
import starter.testing.tests.pages.Page_Landing;

@Lazy
@Scope("prototype")
@Component
public class ActionLandingPage {

    @Autowired
    Page_Landing landingPage;

    public void validateGoogleSearchButton(String expectedButtonText){
        UserActions.validateText(landingPage.getBtnSearchButton(),expectedButtonText);
    }

    public void validateImFeelingLuckyButton(String expectedButtonText){
        UserActions.validateText(landingPage.getBtnImFeelingLucky(),expectedButtonText);
    }

    public void validateGoogleLogo(){
        UserActions.isElementPresent(landingPage.getLblLogo());
    }

}
