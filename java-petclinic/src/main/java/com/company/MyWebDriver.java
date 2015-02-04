package com.company;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by krikar on 2015-02-01.
 */
public class MyWebDriver {

    private static class WebDriverHolder {
        private static final WebDriver INSTANCE = new FirefoxDriver();
    }

    public static WebDriver getInstance() {
        return WebDriverHolder.INSTANCE;
    }
}
