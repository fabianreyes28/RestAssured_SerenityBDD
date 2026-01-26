package com.qa.challenge.stepdefinitions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.util.EnvironmentVariables;

import static com.qa.challenge.utils.Constans.ACTOR;
import static com.qa.challenge.utils.Constans.URL_BASE_BOOKER;

public class EstablishService {
    protected static final Actor actor = new Actor(ACTOR);


    protected void establishService(){
        OnStage.setTheStage(new OnlineCast());

    }
}
