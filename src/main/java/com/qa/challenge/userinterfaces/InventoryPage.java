package com.qa.challenge.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class InventoryPage {

    public static final Target LBL_TITLE = Target.the("Label Title")
            .located(By.className("title"));

    public static final Target BTN_CART = Target.the("Button Cart")
            .located(By.cssSelector("[data-test='shopping-cart-link']"));

    // Botones Add to Cart
    public static final Target BTN_ADD_BACKPACK = Target.the("Button Add Backpack")
            .located(By.id("add-to-cart-sauce-labs-backpack"));

    public static final Target BTN_ADD_BIKELIGHT = Target.the("Button Add Bike Light")
            .located(By.id("add-to-cart-sauce-labs-bike-light"));

    public static final Target BTN_ADD_BOLT_TSHIRT = Target.the("Button Add Bolt T-Shirt")
            .located(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));

    public static final Target BTN_ADD_FLEECE_JACKET = Target.the("Button Add Fleece Jacket")
            .located(By.id("add-to-cart-sauce-labs-fleece-jacket"));

    public static final Target BTN_ADD_ONESIE = Target.the("Button Add Onesie")
            .located(By.id("add-to-cart-sauce-labs-onesie"));
}
