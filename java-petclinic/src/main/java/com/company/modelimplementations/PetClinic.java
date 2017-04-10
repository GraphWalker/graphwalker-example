package com.company.modelimplementations;


import com.company.PetClinicSharedState;
import com.company.helper.Helper;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterElement;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeElement;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Find Owners"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_HomePage() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-home"))).click();
    }

    @Override
    public void e_Veterinarians() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-th-list"))).click();
    }

    @Override
    public void v_Veterinarians() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Veterinarians"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table[last()]/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_FindOwners() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-search"))).click();
    }

    @Override
    public void v_HomePage() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Welcome"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table/tbody/tr/td[2]/img")));
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
        Helper.setup();
    }

    @AfterExecution
    public void cleanup() {
        System.out.println("PetClinic: Any cleanup  steps happens here. " +
                           "The annotation @AfterExecution makes sure that after the test is done, " +
                           "this method is called last.");
        Helper.tearDown();
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
