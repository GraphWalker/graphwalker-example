package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.graphwalker.java.test.TestExecutor;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.graphwalker.core.Assert.expect;

@GraphWalker(value = "random(edge_coverage(100))", start = "e_StartBrowser")
public class PetClinic extends ExecutionContext implements PetClinicSharedState {

    @Override
    public void v_FindOwners() {
        String bodyText = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Find Owners"));
    }

    @Override
    public void e_HomePage() {
        MyWebDriver.WaitFor(By.className("icon-home")).click();
    }

    @Override
    public void e_Veterinarians() {
        MyWebDriver.WaitFor(By.className("icon-th-list")).click();
    }

    @Override
    public void v_Veterinarians() {
        String bodyText = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Veterinarians"));
    }

    @Override
    public void e_FindOwners() {
        MyWebDriver.WaitFor(By.className("icon-search")).click();
    }

    @Override
    public void v_HomePage() {
        String bodyText = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Welcome"));
    }

    @Override
    public void e_StartBrowser() {
        MyWebDriver.getInstance().get("http://localhost:9966/petclinic/");
    }
}
