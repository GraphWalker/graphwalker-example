package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

/**
 * Implements the model (and interface) PetClinicSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 * The start element in the model is e_StartBrowser.
 */
@GraphWalker(value = "random(edge_coverage(100))", start = "e_StartBrowser")
public class PetClinic extends ExecutionContext implements PetClinicSharedState {

    @Override
    public void v_FindOwners() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("Find Owners"));
    }

    @Override
    public void e_HomePage() {
        Helper.WaitForElement(By.className("icon-home")).click();
    }

    @Override
    public void e_Veterinarians() {
        Helper.WaitForElement(By.className("icon-th-list")).click();
    }

    @Override
    public void v_Veterinarians() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("Veterinarians"));
    }

    @Override
    public void e_FindOwners() {
        Helper.WaitForElement(By.className("icon-search")).click();
    }

    @Override
    public void v_HomePage() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("Welcome"));
    }

    @Override
    public void e_StartBrowser() {
        Helper.getInstance().get("http://localhost:9966/petclinic/");
    }
}
