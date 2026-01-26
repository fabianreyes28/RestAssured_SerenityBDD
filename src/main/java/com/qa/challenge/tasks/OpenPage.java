package com.qa.challenge.tasks;

import com.qa.challenge.userinterfaces.LoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class OpenPage implements Task {

    public static OpenPage theSauceDemoLoginPage() {
        return instrumented(OpenPage.class);
    }

    @Override
    @Step("{0} abre la pagina de login de SauceDemo")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(LoginPage.URL)
        );
    }
}
