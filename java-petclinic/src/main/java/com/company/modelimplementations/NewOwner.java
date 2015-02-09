package com.company.modelimplementations;


import com.company.NewOwnerSharedState;
import com.company.helper.Helper;
import org.apache.commons.lang3.RandomStringUtils;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

/**
 * Implements the model (and interface) NewOwnerSharedState
 * The default path generator is Random Path.
 * Stop condition is 100% coverage of all edges.
 */
@GraphWalker(value = "random(edge_coverage(100))")
public class NewOwner extends ExecutionContext implements NewOwnerSharedState {

    @Override
    public void v_OwnerInformation() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("Owner Information"));
    }

    @Override
    public void e_CorrectData() {
        Helper.WaitForElement(By.id("firstName")).clear();
        Helper.WaitForElement(By.id("firstName")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));

        Helper.WaitForElement(By.id("lastName")).clear();
        Helper.WaitForElement(By.id("lastName")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));

        Helper.WaitForElement(By.id("address")).clear();
        Helper.WaitForElement(By.id("address")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));

        Helper.WaitForElement(By.id("city")).clear();
        Helper.WaitForElement(By.id("city")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));

        Helper.WaitForElement(By.id("telephone")).clear();
        Helper.WaitForElement(By.id("telephone")).sendKeys(RandomStringUtils.randomNumeric(Helper.getRandomInt(10)));

        Helper.WaitForElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void e_IncorrectData() {
        Helper.WaitForElement(By.id("firstName")).clear();
        Helper.WaitForElement(By.id("firstName")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));

        Helper.WaitForElement(By.id("lastName")).clear();
        Helper.WaitForElement(By.id("lastName")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));

        Helper.WaitForElement(By.id("address")).clear();
        Helper.WaitForElement(By.id("address")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));

        Helper.WaitForElement(By.id("city")).clear();
        Helper.WaitForElement(By.id("city")).sendKeys(RandomStringUtils.randomAlphabetic(Helper.getRandomInt(10)));

        Helper.WaitForElement(By.id("telephone")).clear();
        Helper.WaitForElement(By.id("telephone")).sendKeys(RandomStringUtils.randomNumeric(20));

        Helper.WaitForElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_IncorrectData() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("New Owner"));

        String str = Helper.WaitForElement(By.cssSelector("div.control-group.error > div.controls > span.help-inline")).getText();
        Assert.assertTrue("Text not found!", str.contains("numeric value out of bounds (<10 digits>.<0 digits> expected"));
    }

    @Override
    public void v_NewOwner() {
        Assert.assertTrue(Helper.WaitForElement(By.tagName("h2")).getText().matches("New Owner"));
    }
}
