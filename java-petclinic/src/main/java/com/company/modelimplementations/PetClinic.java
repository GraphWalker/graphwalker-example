package com.company.modelimplementations;


import com.company.PetClinicSharedState;
import com.company.helper.Helper;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
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

    @BeforeExecution
    public void setup() {
        System.out.println("PetClinic: Any setup steps happens here. " +
                "The annotation @BeforeExecution makes sure that before any elements in the " +
                "model is called, this method is called first");
    }

    @AfterExecution
    public void cleanup() {
        System.out.println("PetClinic: Any cleanup  steps happens here. " +
                "The annotation @AfterExecution makes sure that after the test is done, " +
                "this method is called last.");
    }
}
