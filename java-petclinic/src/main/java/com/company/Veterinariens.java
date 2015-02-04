package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

@GraphWalker(value = "random(edge_coverage(100))")
public class Veterinariens extends ExecutionContext implements VeterinariensSharedState {

    @Override
    public void e_Search() {
        MyWebDriver.WaitFor(By.cssSelector("input[type=\"text\"]")).clear();
        MyWebDriver.WaitFor(By.cssSelector("input[type=\"text\"]")).sendKeys("helen");

    }

    @Override
    public void v_SearchResult() {
        String bodyText = MyWebDriver.WaitFor(By.xpath("//table[@id='vets']/tbody/tr/td")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Helen Leary"));
    }

    @Override
    public void v_Veterinarians() {
        String bodyText = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Veterinarians"));
    }
}
