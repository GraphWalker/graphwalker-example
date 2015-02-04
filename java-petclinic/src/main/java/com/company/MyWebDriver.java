package com.company;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Random;

/**
 * Created by krikar on 2015-02-01.
 */
public class MyWebDriver {

    static private Random random = new Random(System.currentTimeMillis());

    public static int getRandomInt(int max) {
        return random.nextInt(max) + 1;
    }

    private static class WebDriverHolder {
        private static final WebDriver INSTANCE = new FirefoxDriver();
    }

    public static WebDriver getInstance() {
        return WebDriverHolder.INSTANCE;
    }

    public static WebElement WaitFor(By by) {
        for (int second = 0; ; second++) {
            if (second >= 60) Assert.fail("timeout");
            WebElement element = null;
            try {
                element = getInstance().findElement(by);
                return element;
            } catch (Exception e) {
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
