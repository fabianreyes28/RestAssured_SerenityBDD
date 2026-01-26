package com.qa.challenge.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPage {

    public static final String URL = "https://www.saucedemo.com";

    public static final Target INP_USERNAME = Target.the("Input Username")
            .located(By.id("user-name"));

    public static final Target INP_PASSWORD = Target.the("Input Password")
            .located(By.id("password"));

    public static final Target BTN_LOGIN = Target.the("Button Login")
            .located(By.id("login-button"));
}
