package com.company.modelimplementations;


import com.company.FindOwners;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Implements the model (and interface) FindOwnersSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(edge_coverage(100))")
public class FindOwnersTest extends ExecutionContext implements FindOwners {

    @Override
    public void v_Owners() {
        $(By.tagName("h2")).shouldHave(text("Owners"));
        org.junit.Assert.assertTrue($$x("id('owners')/tbody/tr").size() >= 10);
    }

    @Override
    public void e_AddOwner() {
        $(By.linkText("Add Owner")).click();
    }

    @Override
    public void v_FindOwners() {
        $(By.tagName("h2")).shouldHave(text("Find Owners"));
        $x("/html/body/div/table/tbody/tr/td[2]/img").shouldBe(visible);
    }

    @Override
    public void e_Search() {
        $("button[type=\"submit\"]").click();
    }

    @Override
    public void e_FindOwners() {
        $(By.className("icon-search")).click();
    }

    @Override
    public void v_NewOwner() {
        $(By.tagName("h2")).shouldHave(text("New Owner"));
        $x("/html/body/table/tbody/tr/td[2]/img").shouldBe(visible);
    }
}
