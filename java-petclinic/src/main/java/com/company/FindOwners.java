package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

import static org.graphwalker.core.Assert.expect;

@GraphWalker(value = "random(edge_coverage(100))")
public class FindOwners extends ExecutionContext implements FindOwnersSharedState {

    @Override
    public void e_AddOwner() {
        MyWebDriver.getInstance().findElement(By.linkText("Add Owner")).click();
    }

    @Override
    public void v_FindOwners() {
        String bodyText = MyWebDriver.getInstance().findElement(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Find Owners"));
    }

    @Override
    public void e_FindOwners() {
        MyWebDriver.getInstance().findElement(By.className("icon-search")).click();
    }

    @Override
    public void v_NewOwner() {
        String bodyText = MyWebDriver.getInstance().findElement(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("New Owner"));
    }
}
