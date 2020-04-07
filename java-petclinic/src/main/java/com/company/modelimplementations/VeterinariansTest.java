package com.company.modelimplementations;


import com.company.Veterinarians;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Implements the model (and interface) VeterinariensSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(edge_coverage(100))")
public class VeterinariansTest extends ExecutionContext implements Veterinarians {

    @Override
    public void e_Search() {
        $("input[type=\"search\"]").clear();
        $("input[type=\"search\"]").sendKeys("helen");
    }

    @Override
    public void v_SearchResult() {
        $x("//table[@id='vets']/tbody/tr/td").shouldHave(text("Helen Leary"));
        $x("/html/body/div/table[last()]/tbody/tr/td[2]/img").shouldBe(visible);
    }

    @Override
    public void v_Veterinarians() {
        $(By.tagName("h2")).shouldHave(text("Veterinarians"));
        Assert.assertTrue($(By.id("vets")).$$x("id('vets')/tbody/tr").size() >= 1);
    }
}
