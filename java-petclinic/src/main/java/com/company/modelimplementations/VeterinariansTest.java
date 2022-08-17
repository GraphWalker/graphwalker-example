package com.company.modelimplementations;


import com.company.Veterinarians;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

/**
 * Implements the model (and interface) VeterinariensSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(vertex_coverage(100))")
public class VeterinariansTest extends ExecutionContext implements Veterinarians {

    @Override
    public void v_Veterinarians() {
        $(By.tagName("h2")).shouldHave(text("VETERINARIANS"));
        Assert.assertTrue($(By.id("vets")).$$x("id('vets')/tbody/tr").size() == 6);
    }
}
