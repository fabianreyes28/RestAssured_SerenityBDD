package com.qa.challenge.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.thucydides.model.util.EnvironmentVariables;

import static com.qa.challenge.utils.Constans.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;


public class GetUserByIdStepDefinitions {
    private String theRestApiBaseUrl;
    private EnvironmentVariables environmentVariables;
    private Actor user;


    @Before
    public void setTheStage() {

        OnStage.setTheStage(new OnlineCast());
        theRestApiBaseUrl = environmentVariables.optionalProperty("restapi.baseurl").orElse(URL_BASE_BOOKER);

    }

    @Given("the user needs to perform a search by id")
    public void the_user_needs_to_perform_a_search_by_id() {
        System.out.println("given");
        user = Actor.named(ACTOR).whoCan(CallAnApi.at(theRestApiBaseUrl));
    }


    @When("User request users by {string}")
    public void user_request_users_by(String id) {
        System.out.println("when");
        user.attemptsTo(
                Get.resource(EDNPOINT_ID + id)
        );
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int status_code) {
        System.out.println("then: "+status_code);
        user.should(
                seeThatResponse("the response status code should be ",
                        response -> response.statusCode(status_code))
        );
    }

}
