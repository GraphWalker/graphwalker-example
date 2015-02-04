package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;

@GraphWalker(value = "random(edge_coverage(100))")
public class NewOwner extends ExecutionContext implements NewOwnerSharedState {

    @Override
    public void v_OwnerInformation() {
        String str = MyWebDriver.getInstance().findElement(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", str.contains("Owner Information"));

        str = MyWebDriver.getInstance().findElement(By.cssSelector("b")).getText();
        Assert.assertTrue("Text not found!", str.contains("Henry Larsson"));

        str = MyWebDriver.getInstance().findElement(By.xpath("//tr[2]/td")).getText();
        Assert.assertTrue("Text not found!", str.contains("13, Unlucky St"));

        str = MyWebDriver.getInstance().findElement(By.xpath("//tr[3]/td")).getText();
        Assert.assertTrue("Text not found!", str.contains("Some City"));

        str = MyWebDriver.getInstance().findElement(By.xpath("//tr[4]/td")).getText();
        Assert.assertTrue("Text not found!", str.contains("1234567"));
    }

    @Override
    public void e_CorrectData() {
        MyWebDriver.getInstance().findElement(By.id("firstName")).clear();
        MyWebDriver.getInstance().findElement(By.id("firstName")).sendKeys("Henry");
        MyWebDriver.getInstance().findElement(By.id("lastName")).clear();
        MyWebDriver.getInstance().findElement(By.id("lastName")).sendKeys("Larsson");
        MyWebDriver.getInstance().findElement(By.id("address")).clear();
        MyWebDriver.getInstance().findElement(By.id("address")).sendKeys("13, Unlucky St");
        MyWebDriver.getInstance().findElement(By.id("city")).clear();
        MyWebDriver.getInstance().findElement(By.id("city")).sendKeys("Some City");
        MyWebDriver.getInstance().findElement(By.id("telephone")).clear();
        MyWebDriver.getInstance().findElement(By.id("telephone")).sendKeys("1234567");
        MyWebDriver.getInstance().findElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void e_IncorrectData() {
        MyWebDriver.getInstance().findElement(By.id("firstName")).clear();
        MyWebDriver.getInstance().findElement(By.id("firstName")).sendKeys("Henry");
        MyWebDriver.getInstance().findElement(By.id("lastName")).clear();
        MyWebDriver.getInstance().findElement(By.id("lastName")).sendKeys("Larsson");
        MyWebDriver.getInstance().findElement(By.id("address")).clear();
        MyWebDriver.getInstance().findElement(By.id("address")).sendKeys("13, Unlucky St");
        MyWebDriver.getInstance().findElement(By.id("city")).clear();
        MyWebDriver.getInstance().findElement(By.id("city")).sendKeys("Some City");
        MyWebDriver.getInstance().findElement(By.id("telephone")).clear();
        MyWebDriver.getInstance().findElement(By.id("telephone")).sendKeys("12345679000000000000");
        MyWebDriver.getInstance().findElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    @Override
    public void v_IncorrectData() {
        String str = MyWebDriver.getInstance().findElement(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", str.contains("New Owner"));

        str = MyWebDriver.getInstance().findElement(By.id("firstName")).getAttribute("value");
        Assert.assertTrue("Text not found!", str.contains("Henry"));

        str = MyWebDriver.getInstance().findElement(By.id("lastName")).getAttribute("value");
        Assert.assertTrue("Text not found!", str.contains("Larsson"));

        str = MyWebDriver.getInstance().findElement(By.id("address")).getAttribute("value");
        Assert.assertTrue("Text not found!", str.contains("13, Unlucky St"));

        str = MyWebDriver.getInstance().findElement(By.id("city")).getAttribute("value");
        Assert.assertTrue("Text not found!", str.contains("Some City"));

        str = MyWebDriver.getInstance().findElement(By.cssSelector("div.control-group.error > div.controls > span.help-inline")).getText();
        Assert.assertTrue("Text not found!", str.contains("numeric value out of bounds (<10 digits>.<0 digits> expected"));
    }

    @Override
    public void v_NewOwner() {
        String bodyText = MyWebDriver.getInstance().findElement(By.tagName("h2")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("New Owner"));
    }
}
