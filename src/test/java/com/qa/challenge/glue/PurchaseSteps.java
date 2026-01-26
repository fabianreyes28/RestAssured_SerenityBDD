package com.qa.challenge.glue;

import com.qa.challenge.tasks.*;
import com.qa.challenge.userinterfaces.CheckoutPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class PurchaseSteps {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el usuario esta en la pagina de login de SauceDemo")
    public void elUsuarioEstaEnLaPaginaDeLoginDeSauceDemo() {
        theActorCalled("Usuario").attemptsTo(
                OpenPage.theSauceDemoLoginPage()
        );
    }

    @When("inicia sesion con usuario {string} y password {string}")
    public void iniciaSesionConUsuarioYPassword(String username, String password) {
        theActorInTheSpotlight().attemptsTo(
                Login.withCredentials(username, password)
        );
    }

    @And("agrega el producto {string} al carrito")
    public void agregaElProductoAlCarrito(String producto) {
        theActorInTheSpotlight().attemptsTo(
                AddProduct.toCart(producto)
        );
    }

    @And("visualiza el carrito")
    public void visualizaElCarrito() {
        theActorInTheSpotlight().attemptsTo(
                ViewCart.items()
        );
    }

    @And("completa el checkout con nombre {string} apellido {string} y codigo postal {string}")
    public void completaElCheckoutConDatos(String firstName, String lastName, String postalCode) {
        theActorInTheSpotlight().attemptsTo(
                Checkout.withInformation(firstName, lastName, postalCode)
        );
    }

    @And("finaliza la compra")
    public void finalizaLaCompra() {
        theActorInTheSpotlight().attemptsTo(
                FinishPurchase.successfully()
        );
    }

    @Then("deberia ver el mensaje de confirmacion {string}")
    public void deberiaVerElMensajeDeConfirmacion(String mensaje) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(CheckoutPage.LBL_COMPLETE_HEADER).text().containsIgnoringCase(mensaje)
        );
    }
}
