package com.qa.challenge.stepdefinitions;

import com.qa.challenge.models.ModelPost;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.util.EnvironmentVariables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.qa.challenge.utils.Constans.ACTOR;
import static com.qa.challenge.utils.Constans.URL_BASE_BOOKER;

public class HappyPathStepDefinitions {
    private String theRestApiBaseUrl;
    private EnvironmentVariables environmentVariables;
    private Actor user;

    @Before
    public void setTheStage() {

        OnStage.setTheStage(new OnlineCast());
        theRestApiBaseUrl = environmentVariables.optionalProperty("restapi.baseurl").orElse(URL_BASE_BOOKER);

    }
    @Given("the Booking API is available")
    public void the_booking_api_is_available() {
        System.out.println("given");
        user = Actor.named(ACTOR).whoCan(CallAnApi.at(theRestApiBaseUrl));
    }




    @Then("the response status code should be success:")
    public void the_response_status_code_should_be_success(io.cucumber.datatable.DataTable dataTable) {

    }
    @When("the user creates a booking with {string} {string}")
    public void the_user_creates_a_booking_with(String firstname, String lastname) {
        System.out.println(" When ->"+firstname);
    }

    @When("the user creates a booking with:")
    public void the_user_creates_a_booking_with(DataTable dataTable) {
        System.out.println(" When ");
        ModelPost dataPost = new ModelPost(dataTable);
        String nombre = dataPost.getFirstname();


        System.out.println("este es el When hola: "+nombre);
    }


    @When("the user creates a booking")
    public void the_user_creates_a_booking(DataTable dataTable) {
        ModelPost dataPost = new ModelPost(dataTable);
        String nombre = dataPost.getFirstname();


        System.out.println("este es el When hola: "+nombre);

    }
    @Then("the response status code should be success")
    public void the_response_status_code_should_be_success() {

    }
    @Then("the response should contain a bookingid")
    public void the_response_should_contain_a_bookingid() {

    }
}
