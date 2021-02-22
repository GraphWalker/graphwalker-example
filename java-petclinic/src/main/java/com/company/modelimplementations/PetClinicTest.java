package com.company.modelimplementations;


import com.company.PetClinic;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Selenide.*;

/**
 * Implements the model (and interface) PetClinicSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 * The start element in the model is e_StartBrowser.
 */
@GraphWalker(value = "random(edge_coverage(100))", start = "e_StartBrowser")
public class PetClinicTest extends ExecutionContext implements PetClinic {

    @Override
    public void v_FindOwners() {
    }

    @Override
    public void e_HomePage() {
        $("[title='home page']").click();
    }

    @Override
    public void e_Veterinarians() {
        $("[title='veterinarians']").click();
    }

    @Override
    public void v_Veterinarians() {
    }

    @Override
    public void e_FindOwners() {
        $("[title='find owners']").click();
    }

    @Override
    public void v_HomePage() {
        $("h2").shouldHave(text("Welcome"));
        $("h2").shouldBe(visible);
        $("img.img-responsive").shouldBe(visible);
    }

    @Override
    public void e_StartBrowser() {
        open("http://localhost:8080");
    }

    @BeforeExecution
    public void setup() {
        System.out.println("PetClinic: Any setup steps happens here. " +
                           "The annotation @BeforeExecution makes sure that before any elements in the " +
                           "model is called, this method is called first");
        browser = "chrome";
    }

    @AfterExecution
    public void cleanup() {
        System.out.println("PetClinic: Any cleanup  steps happens here. " +
                           "The annotation @AfterExecution makes sure that after the test is done, " +
                           "this method is called last.");
    }

    @BeforeElement
    public void printBeforeElement() {
        System.out.println("Before element " + getCurrentElement().getName());
    }

    @AfterElement
    public void printAfterElement() {
        System.out.println("After element " + getCurrentElement().getName());
    }
}
