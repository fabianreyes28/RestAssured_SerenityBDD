package com.qa.challenge.tasks;

import com.qa.challenge.userinterfaces.InventoryPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class AddProduct implements Task {

    private String productName;

    public AddProduct(String productName) {
        this.productName = productName;
    }

    public static AddProduct toCart(String productName) {
        return instrumented(AddProduct.class, productName);
    }

    @Override
    @Step("{0} agrega el producto #productName al carrito")
    public <T extends Actor> void performAs(T actor) {
        Target addButton = getAddButtonForProduct(productName);

        actor.attemptsTo(
                WaitUntil.the(addButton, isClickable())
                        .forNoMoreThan(30).seconds(),
                Click.on(addButton)
        );
    }

    private Target getAddButtonForProduct(String productName) {
        switch (productName) {
            case "Sauce Labs Backpack":
                return InventoryPage.BTN_ADD_BACKPACK;
            case "Sauce Labs Bike Light":
                return InventoryPage.BTN_ADD_BIKELIGHT;
            case "Sauce Labs Bolt T-Shirt":
                return InventoryPage.BTN_ADD_BOLT_TSHIRT;
            case "Sauce Labs Fleece Jacket":
                return InventoryPage.BTN_ADD_FLEECE_JACKET;
            case "Sauce Labs Onesie":
                return InventoryPage.BTN_ADD_ONESIE;
            default:
                throw new IllegalArgumentException("Producto no encontrado: " + productName);
        }
    }
}
