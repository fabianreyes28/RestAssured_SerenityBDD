package com.qa.challenge.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CheckoutPage {

    // Checkout Step One - Your Information
    public static final Target INP_FIRST_NAME = Target.the("Input First Name")
            .located(By.id("first-name"));

    public static final Target INP_LAST_NAME = Target.the("Input Last Name")
            .located(By.id("last-name"));

    public static final Target INP_POSTAL_CODE = Target.the("Input Postal Code")
            .located(By.id("postal-code"));

    public static final Target BTN_CONTINUE = Target.the("Button Continue")
            .located(By.id("continue"));

    public static final Target BTN_CANCEL = Target.the("Button Cancel")
            .located(By.id("cancel"));

    // Checkout Step Two - Overview
    public static final Target LBL_SUMMARY_TOTAL = Target.the("Label Summary Total")
            .located(By.className("summary_total_label"));

    public static final Target BTN_FINISH = Target.the("Button Finish")
            .located(By.id("finish"));

    // Checkout Complete
    public static final Target LBL_COMPLETE_HEADER = Target.the("Label Complete Header")
            .located(By.className("complete-header"));

    public static final Target LBL_COMPLETE_TEXT = Target.the("Label Complete Text")
            .located(By.className("complete-text"));

    public static final Target BTN_BACK_HOME = Target.the("Button Back Home")
            .located(By.id("back-to-products"));
}
