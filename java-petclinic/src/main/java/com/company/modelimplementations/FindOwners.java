package com.company.modelimplementations;


import com.company.FindOwnersSharedState;
import com.company.helper.Helper;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Implements the model (and interface) FindOwnersSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(edge_coverage(100))")
public class FindOwners extends ExecutionContext implements FindOwnersSharedState {

    @Override
    public void v_Owners() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Owners"));
        WebElement table = Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.id("owners")));
        org.junit.Assert.assertTrue(table.findElements(By.xpath("id('owners')/tbody/tr")).size() >= 10);
    }

    @Override
    public void e_AddOwner() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.linkText("Add Owner"))).click();
    }

    @Override
    public void v_FindOwners() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Find Owners"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table/tbody/tr/td[2]/img")));
    }

    @Override
    public void e_Search() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]"))).click();
    }

    @Override
    public void e_FindOwners() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.className("icon-search"))).click();
    }

    @Override
    public void v_NewOwner() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "New Owner"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/table/tbody/tr/td[2]/img")));
    }
}
