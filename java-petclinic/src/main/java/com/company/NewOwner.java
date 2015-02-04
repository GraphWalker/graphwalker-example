package com.company;


import org.apache.commons.lang3.RandomStringUtils;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.HashMap;

@GraphWalker(value = "random(edge_coverage(100))")
public class NewOwner extends ExecutionContext implements NewOwnerSharedState {

    HashMap<String,String> owner = new HashMap<>();

    @Override
    public void v_OwnerInformation() {
        String str = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", str.contains("Owner Information"));
    }

    @Override
    public void e_CorrectData() {
        MyWebDriver.WaitFor(By.id("firstName")).clear();
        owner.put("firstName", RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("firstName")).sendKeys(owner.get("firstName"));

        MyWebDriver.WaitFor(By.id("lastName")).clear();
        owner.put("lastName", RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("lastName")).sendKeys(owner.get("lastName"));

        MyWebDriver.WaitFor(By.id("address")).clear();
        owner.put("address", RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("address")).sendKeys(owner.get("address"));

        MyWebDriver.WaitFor(By.id("city")).clear();
        owner.put("city", RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("city")).sendKeys(owner.get("city"));

        MyWebDriver.WaitFor(By.id("telephone")).clear();
        owner.put("telephone", RandomStringUtils.randomNumeric(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("telephone")).sendKeys(owner.get("telephone"));

        MyWebDriver.WaitFor(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void e_IncorrectData() {
        MyWebDriver.WaitFor(By.id("firstName")).clear();
        owner.put("firstName", RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("firstName")).sendKeys(owner.get("firstName"));

        MyWebDriver.WaitFor(By.id("lastName")).clear();
        owner.put("lastName", RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("lastName")).sendKeys(owner.get("lastName"));

        MyWebDriver.WaitFor(By.id("address")).clear();
        owner.put("address", RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("address")).sendKeys(owner.get("address"));

        MyWebDriver.WaitFor(By.id("city")).clear();
        owner.put("city", RandomStringUtils.randomAlphabetic(MyWebDriver.getRandomInt(10)));
        MyWebDriver.WaitFor(By.id("city")).sendKeys(owner.get("city"));

        MyWebDriver.WaitFor(By.id("telephone")).clear();
        owner.put("telephone", RandomStringUtils.randomNumeric(20));
        MyWebDriver.WaitFor(By.id("telephone")).sendKeys(owner.get("telephone"));

        MyWebDriver.WaitFor(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_IncorrectData() {
        String str = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", str.contains("New Owner"));

        str = MyWebDriver.WaitFor(By.cssSelector("div.control-group.error > div.controls > span.help-inline")).getText();
        Assert.assertTrue("Text not found!", str.contains("numeric value out of bounds (<10 digits>.<0 digits> expected"));
    }

    @Override
    public void v_NewOwner() {
        String bodyText = MyWebDriver.WaitFor(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("New Owner"));
    }
}
