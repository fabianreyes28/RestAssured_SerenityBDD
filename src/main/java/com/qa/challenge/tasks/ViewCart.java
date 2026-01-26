package com.qa.challenge.tasks;

import com.qa.challenge.userinterfaces.CartPage;
import com.qa.challenge.userinterfaces.InventoryPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ViewCart implements Task {

    public static ViewCart items() {
        return instrumented(ViewCart.class);
    }

    @Override
    @Step("{0} visualiza el carrito de compras")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(InventoryPage.BTN_CART, isClickable())
                        .forNoMoreThan(30).seconds(),
                Click.on(InventoryPage.BTN_CART),
                WaitUntil.the(CartPage.LBL_TITLE, isVisible())
                        .forNoMoreThan(30).seconds()
        );
    }
}
