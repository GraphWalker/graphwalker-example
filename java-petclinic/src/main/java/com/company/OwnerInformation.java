package com.company;


import org.apache.commons.lang3.RandomStringUtils;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.UUID;

@GraphWalker(value = "random(edge_coverage(100))")
public class OwnerInformation extends ExecutionContext implements OwnerInformationSharedState {

    @Override
    public void v_OwnerInformation() {
        String bodyText = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Owner Information"));
    }

    @Override
    public void e_UpdatePet() {
        MyWebDriver.WaitFor(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_FindOwners() {
        String bodyText = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Find Owners"));
    }

    @Override
    public void e_EditPet() {
        MyWebDriver.WaitFor(By.linkText("Edit Pet")).click();
    }

    @Override
    public void e_AddNewPet() {
        MyWebDriver.WaitFor(By.linkText("Add New Pet")).click();
    }

    @Override
    public void e_AddVisit() {
        MyWebDriver.WaitFor(By.linkText("Add Visit")).click();
    }

    @Override
    public void e_FindOwners() {
        MyWebDriver.WaitFor(By.className("icon-search")).click();
    }

    @Override
    public void e_AddPetSuccessfully() {
        MyWebDriver.WaitFor(By.id("name")).clear();
        MyWebDriver.WaitFor(By.id("name")).sendKeys(RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("birthDate")).clear();
        MyWebDriver.WaitFor(By.id("birthDate")).sendKeys("2015/02/05");
        new Select(MyWebDriver.WaitFor(By.id("type"))).selectByVisibleText("dog");
        MyWebDriver.WaitFor(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_NewPet() {
        String bodyText = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("New Pet"));
    }

    @Override
    public void e_VisitAddedSuccessfully() {
        MyWebDriver.WaitFor(By.id("description")).clear();
        MyWebDriver.WaitFor(By.id("description")).sendKeys(RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_NewVisit() {
        String bodyText = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("New Visit"));
    }

    @Override
    public void v_Pet() {
        MyWebDriver.WaitFor((By.cssSelector("button[type=\"submit\"]"))).click();
    }

    @Override
    public void e_AddPetFailed() {
        MyWebDriver.WaitFor(By.id("name")).clear();
        MyWebDriver.WaitFor(By.id("birthDate")).clear();
        MyWebDriver.WaitFor(By.id("birthDate")).sendKeys("2015/02/05");
        new Select(MyWebDriver.WaitFor(By.id("type"))).selectByVisibleText("dog");
        MyWebDriver.WaitFor(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void e_VisitAddedFailed() {
        MyWebDriver.WaitFor(By.id("description")).clear();
        MyWebDriver.WaitFor(By.cssSelector("button[type=\"submit\"]")).click();
    }
}
