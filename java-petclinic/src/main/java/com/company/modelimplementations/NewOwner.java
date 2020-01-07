package com.company.modelimplementations;


import com.company.NewOwnerSharedState;
import com.github.javafaker.Faker;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Implements the model (and interface) NewOwnerSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(edge_coverage(100))")
public class NewOwner extends ExecutionContext implements NewOwnerSharedState {

    @Override
    public void v_OwnerInformation() {
        $(By.tagName("h2")).shouldHave(text("Owner Information"));
        $x("/html/body/div/table[last()]/tbody/tr/td[2]/img").shouldBe(visible);
    }

    @Override
    public void e_CorrectData() {
        fillOwnerData();
        $(By.id("telephone")).sendKeys(String.valueOf(new Faker().number().digits(10)));
        $("button[type=\"submit\"]").click();
    }

    @Override
    public void e_IncorrectData() {
        fillOwnerData();
        $(By.id("telephone")).sendKeys(String.valueOf(new Faker().number().digits(20)));
        $("button[type=\"submit\"]").click();
    }

    @Override
    public void v_IncorrectData() {
        $(By.cssSelector("div.control-group.error > div.controls > span.help-inline"))
                .shouldHave(text("numeric value out of bounds (<10 digits>.<0 digits> expected)"));
    }

    @Override
    public void v_NewOwner() {
        $(By.tagName("h2")).shouldHave(text("New Owner"));
        $x("/html/body/table/tbody/tr/td[2]/img").shouldBe(visible);
    }

    private void fillOwnerData() {
        $(By.id("firstName")).clear();
        $(By.id("firstName")).sendKeys(new Faker().name().firstName());

        $(By.id("lastName")).clear();
        $(By.id("lastName")).sendKeys(new Faker().name().lastName());

        $(By.id("address")).clear();
        $(By.id("address")).sendKeys(new Faker().address().fullAddress());

        $(By.id("city")).clear();
        $(By.id("city")).sendKeys(new Faker().address().city());

        $(By.id("telephone")).clear();
    }
}
