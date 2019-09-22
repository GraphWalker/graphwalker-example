package com.prestashop.helper;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * Firefox profile and options
     */
    static FirefoxProfile profile = new FirefoxProfile();
    static FirefoxOptions firefoxOptions = new FirefoxOptions();

    /**
     * Generates a random number with 1 to max digits.
     *
     * @param max Maximum length of digits.
     * @return The random number
     */
    public static int getRandomInt(int max) {
        return random.nextInt(max) + 1;
    }

    public static void setup() {
        FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        profile.setPreference("intl.accept_languages","en-US, en");
        firefoxOptions.setProfile(profile);
    }

    public static void tearDown() {
        getInstance().quit();
    }

    /**
     * Creates an instance of the Firefox WebDriver.
     */
    private static class WebDriverHolder {
        private static final WebDriver INSTANCE = new FirefoxDriver(firefoxOptions);
    }

    public static WebDriverWait getWaiter() {
        return new WebDriverWait(getInstance(), 10);
    }

    /**
     * If not already created, creates the singleton webdriver object.
     *
     * @return the singleton webdriver object
     */
    public static WebDriver getInstance() {
        return WebDriverHolder.INSTANCE;
    }
}