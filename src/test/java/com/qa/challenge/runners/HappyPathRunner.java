package com.qa.challenge.runners;


import com.qa.challenge.readexcel.BeforeSuite;
import com.qa.challenge.readexcel.DataToFeature;


import customrunner.CustomRunner;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static com.qa.challenge.utils.Constans.CLASE_RUNNER;

@RunWith(CustomRunner.class)
//@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.qa.challenge.stepdefinitions",
        tags = "@getBooking",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        }
)
public class HappyPathRunner {
    private HappyPathRunner() {
        throw new IllegalStateException(CLASE_RUNNER);
    }
    //@BeforeClass
    @BeforeSuite
        public static void preProcessFeatures() throws Exception {

                DataToFeature.overrideFeatureFiles(
                        "src/test/resources/features");
        }


}
