package com.qa.challenge.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CartPage {

    public static final Target LBL_TITLE = Target.the("Label Title Cart")
            .located(By.className("title"));

    public static final Target BTN_CHECKOUT = Target.the("Button Checkout")
            .located(By.id("checkout"));

    public static final Target BTN_CONTINUE_SHOPPING = Target.the("Button Continue Shopping")
            .located(By.id("continue-shopping"));

    public static final Target LIST_CART_ITEMS = Target.the("List Cart Items")
            .located(By.className("cart_item"));
}
