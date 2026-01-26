package com.qa.challenge.tasks;

import com.qa.challenge.userinterfaces.CheckoutPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class FinishPurchase implements Task {

    public static FinishPurchase successfully() {
        return instrumented(FinishPurchase.class);
    }

    @Override
    @Step("{0} finaliza la compra")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CheckoutPage.BTN_FINISH, isClickable())
                        .forNoMoreThan(30).seconds(),
                Click.on(CheckoutPage.BTN_FINISH),
                WaitUntil.the(CheckoutPage.LBL_COMPLETE_HEADER, isVisible())
                        .forNoMoreThan(30).seconds()
        );
    }
}
