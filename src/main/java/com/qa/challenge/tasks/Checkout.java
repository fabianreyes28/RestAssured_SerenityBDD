package com.qa.challenge.tasks;

import com.qa.challenge.userinterfaces.CartPage;
import com.qa.challenge.userinterfaces.CheckoutPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Checkout implements Task {

    private String firstName;
    private String lastName;
    private String postalCode;

    public Checkout(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public static Checkout withInformation(String firstName, String lastName, String postalCode) {
        return instrumented(Checkout.class, firstName, lastName, postalCode);
    }

    @Override
    @Step("{0} completa el checkout con nombre #firstName #lastName")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // Click en Checkout desde el carrito
                WaitUntil.the(CartPage.BTN_CHECKOUT, isClickable())
                        .forNoMoreThan(30).seconds(),
                Click.on(CartPage.BTN_CHECKOUT),

                // Llenar formulario de información
                WaitUntil.the(CheckoutPage.INP_FIRST_NAME, isVisible())
                        .forNoMoreThan(30).seconds(),
                Enter.theValue(firstName).into(CheckoutPage.INP_FIRST_NAME),
                Enter.theValue(lastName).into(CheckoutPage.INP_LAST_NAME),
                Enter.theValue(postalCode).into(CheckoutPage.INP_POSTAL_CODE),

                // Click en Continue
                Click.on(CheckoutPage.BTN_CONTINUE)
        );
    }
}
