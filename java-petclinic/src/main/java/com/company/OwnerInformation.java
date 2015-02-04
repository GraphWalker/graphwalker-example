package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

@GraphWalker(value = "random(edge_coverage(100))")
public class OwnerInformation extends ExecutionContext implements OwnerInformationSharedState {

    @Override
    public void v_OwnerInformation() {
        String bodyText = MyWebDriver.getInstance().findElement(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Owner Information"));
    }

    @Override
    public void e_UpdatePet() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void v_FindOwners() {
        String bodyText = MyWebDriver.getInstance().findElement(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Find Owners"));
    }

    @Override
    public void e_EditPet() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void e_AddNewPet() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void e_AddVisit() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void e_FindOwners() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void e_AddPetSuccessfully() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void v_NewPet() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void e_VisitAddedSuccessfully() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void v_NewVisit() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void v_Pet() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void e_AddPetFailed() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void e_VisitAddedFailed() {
        throw new RuntimeException("Not implemented");
    }
}
