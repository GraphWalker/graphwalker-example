package com.company.modelimplementations;


import com.company.VeterinariensSharedState;
import com.company.helper.Helper;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

/**
 * Implements the model (and interface) VeterinariensSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(edge_coverage(100))")
public class Veterinariens extends ExecutionContext implements VeterinariensSharedState {

    @Override
    public void e_Search() {
        Helper.WaitForElement(By.cssSelector("input[type=\"search\"]")).clear();
        Helper.WaitForElement(By.cssSelector("input[type=\"search\"]")).sendKeys("helen");

    }

    @Override
    public void v_SearchResult() {
        String bodyText = Helper.WaitForElement(By.xpath("//table[@id='vets']/tbody/tr/td")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Helen Leary"));
    }

    @Override
    public void v_Veterinarians() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("Veterinarians"));
    }
}
