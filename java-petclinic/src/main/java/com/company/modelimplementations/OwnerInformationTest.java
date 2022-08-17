package com.company.modelimplementations;


import com.company.OwnerInformation;
import com.github.javafaker.Faker;
import org.graalvm.polyglot.Value;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Implements the model (and interface) OwnerInformationSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(edge_coverage(100))")
public class OwnerInformationTest extends ExecutionContext implements OwnerInformation {

    private static final Logger log = LoggerFactory.getLogger(OwnerInformationTest.class);

    /**
     * This method does not only implement the verification of the vertex, it also
     * fetches the value representing number of pets for the owner, and passes it
     * on to GraphWalker. In the model OwnerInformationSharedState.graphml the attribute
     * numOfPets decides whether the guards for the edges:
     * e_AddVisit
     * e_EditPet
     * are opened or closed.
     * <p/>
     * setAttribute is inherited from org.graphwalker.core.machine.ExecutionContext
     */
    @Override
    public void v_OwnerInformation() {
        $(By.tagName("h2")).shouldHave(text("Owner Information"));
        setAttribute("numOfPets", Value.asValue($$x("//table/tbody/tr/td//dl").size()));
        log.info("Number of pets: " + getAttribute("numOfPets"));
    }

    @Override
    public void e_UpdatePet() {
        $("button[type=\"submit\"]").click();
    }

    @Override
    public void v_FindOwners() {
        $(By.tagName("h2")).shouldHave(text("Find Owners"));
        $(By.tagName("h2")).shouldBe(visible);
    }

    @Override
    public void e_EditPet() {
        $(By.linkText("Edit Pet")).click();
    }

    @Override
    public void e_AddNewPet() {
        $(By.linkText("Add New Pet")).click();
    }

    @Override
    public void e_AddVisit() {
        $(By.linkText("Add Visit")).click();
    }

    @Override
    public void e_FindOwners() {
        $("[title='find owners']").click();
    }

    @Override
    public void e_AddPetSuccessfully() {
        Date date = new Faker().date().past( 365 * 20, TimeUnit.DAYS);
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String birthData = sdf.format(date);
        $(By.id("birthDate")).clear();
        $(By.id("birthDate")).sendKeys(birthData + Keys.ENTER);

        $(By.id("name")).clear();
        $(By.id("name")).sendKeys(new Faker().name().fullName());

        $(By.id("type")).selectOption(new Faker().number().numberBetween(0,5));
        $(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_NewPet() {
        $(By.tagName("h2")).shouldHave(text("New Pet"));
        $(".has-feedback").shouldBe(visible);
    }

    @Override
    public void e_VisitAddedSuccessfully() {
        $(By.id("description")).clear();
        $(By.id("description")).sendKeys(new Faker().lorem().word());
        $("button[type=\"submit\"]").click();
    }

    @Override
    public void v_NewVisit() {
        $(By.tagName("h2")).shouldHave(text("New Visit"));
    }

    @Override
    public void v_Pet() {
        $(By.tagName("h2")).shouldHave(text("Pet"));
    }

    @Override
    public void e_AddPetFailed() {
        $(By.id("name")).clear();
        $(By.id("birthDate")).clear();
        $(By.id("birthDate")).sendKeys("2015/02/05" + Keys.ENTER);
        $(By.id("ui-datepicker-div")).shouldBe(not(visible));
        $(By.id("type")).selectOption("dog");
        $("button[type=\"submit\"]").click();
    }

    @Override
    public void e_VisitAddedFailed() {
        $(By.id("description")).clear();
        $("button[type=\"submit\"]").click();
    }
}
