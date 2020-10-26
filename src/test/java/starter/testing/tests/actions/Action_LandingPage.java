package starter.testing.tests.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import starter.testing.core.util.actions.UserActions;
import starter.testing.tests.pages.Page_Landing;

@Component
@Lazy
@Scope("prototype")
public class Action_LandingPage {

    @Autowired
    Page_Landing landingPage;

    public void validatePageTitle(String expectedPageTitle){
        UserActions.validateText(landingPage.getLblPageTitle(),expectedPageTitle);
    }

    public void validatePageParagraph(String expectedPageParagraph){
        UserActions.validateText(landingPage.getLblPageParagraph(),expectedPageParagraph);
    }

}
