package com.company.modelimplementations;


import com.company.VeterinariensSharedState;
import com.company.helper.Helper;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Implements the model (and interface) VeterinariensSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(edge_coverage(100))")
public class Veterinariens extends ExecutionContext implements VeterinariensSharedState {

    @Override
    public void e_Search() {
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type=\"search\"]"))).clear();
        Helper.getWaiter().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type=\"search\"]"))).sendKeys("helen");
    }

    @Override
    public void v_SearchResult() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.xpath("//table[@id='vets']/tbody/tr/td"), "Helen Leary"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/table[last()]/tbody/tr/td[2]/img")));
    }

    @Override
    public void v_Veterinarians() {
        Helper.getWaiter().until(ExpectedConditions.textToBe(By.tagName("h2"), "Veterinarians"));
        WebElement table = Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.id("vets")));
        org.junit.Assert.assertTrue(table.findElements(By.xpath("id('vets')/tbody/tr")).size() >= 1);
    }
}
