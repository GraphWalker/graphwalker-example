package com.prestashop.modelimplementation;


import com.prestashop.PrestoShop;
import com.prestashop.helper.Helper;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PrestoShotImpl extends ExecutionContext implements PrestoShop {

    @BeforeExecution
    public void setup() {
        Helper.setup();
    }

    @AfterExecution
    public void cleanup() {
        Helper.tearDown();
    }

    @Override
    public void v_ConfirmOrder() {
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Personal Information')]")));
    }

    @Override
    public void e_Checkout() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'Proceed to checkout')]"))).click();
    }

    @Override
    public void v_Product() {
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='h1'][contains(.,'Hummingbird printed t-shirt')]")));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='h1'][contains(.,'Hummingbird printed t-shirt')]")));
    }

    @Override
    public void e_Select_Product() {
        WebElement myelement = Helper.getInstance().findElement(By.xpath("//img[@alt='Hummingbird printed t-shirt']"));
        JavascriptExecutor jse2 = (JavascriptExecutor)Helper.getInstance();
        jse2.executeScript("arguments[0].scrollIntoView()", myelement);
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@alt='Hummingbird printed t-shirt']"))).click();
    }

    @Override
    public void v_HomePage() {
        Helper.getWaiter().until(ExpectedConditions.titleIs("SAST Shop"));
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Hummingbird printed t-shirt']")));
    }

    @Override
    public void e_Cart() {
        Helper.getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(.,'Cart')]"))).click();
    }

    @Override
    public void v_Cart() {
        Helper.getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='h1'][contains(.,'Shopping Cart')]")));
    }

    @Override
    public void e_Start() {
        Helper.getInstance().get("http://localhost:8001");
    }
}
