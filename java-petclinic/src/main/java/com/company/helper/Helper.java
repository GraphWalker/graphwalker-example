package com.company.helper;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

/**
 * Created by krikar on 2015-02-01.
 */
public class Helper {
    private static final Logger log = LoggerFactory.getLogger(Helper.class);

    /**
     * Random number generator.
     * Will be used to create random data used for input in test.
     */
    static private Random random = new Random(System.currentTimeMillis());

    /**
     * Timeout time in seconds used for waiting for element(s) to show up.
     */
    final static int timeOut = 10;

    /**
     * Generates a random number with 1 to max digits.
     *
     * @param max Maximum length of digits.
     * @return The random number
     */
    public static int getRandomInt(int max) {
        return random.nextInt(max) + 1;
    }

    /**
     * Creates an instance of the Firefox WebDriver.
     */
    private static class WebDriverHolder {
        private static final WebDriver INSTANCE = new FirefoxDriver();
    }

    /**
     * If not already created, creates the singleton webdriver object.
     *
     * @return the singleton webdriver object
     */
    public static WebDriver getInstance() {
        return WebDriverHolder.INSTANCE;
    }

    /**
     * Will wait for a specified web element to appear. If not found
     * an assertion will fail.
     *
     * @param by The description of the element
     * @return The matching element if found.
     */
    public static WebElement WaitForElement(By by) {
        for (int second = 0; ; second++) {
            if (second >= timeOut) {
                Assert.fail("Timeout occurred while waiting for: " + by.toString());
            }
            try {
                return getInstance().findElement(by);
            } catch (Exception e1) {
                log.debug(e1.getMessage());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e2) {
                    log.debug(e2.getMessage());
                }
            }
        }
    }

    /**
     * Will wait for a specified web element(s) to appear. If not found
     * an assertion will fail.
     *
     * @param by The description of the element
     * @return A list of matching element(s) if found.
     */
    public static List<WebElement> WaitForElements(By by) {
        for (int second = 0; ; second++) {
            if (second >= timeOut) {
                Assert.fail("timeout");
            }
            List<WebElement> elements = null;
            try {
                elements = getInstance().findElements(by);
                return elements;
            } catch (Exception e) {
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
