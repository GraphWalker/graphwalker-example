package com.prestashop.runners;

import com.prestashop.modelimplementation.SeleniumImpl;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutor;

import java.io.IOException;

public class SeleniumRunner {
    public static void main(String[] args) throws IOException {
        TestExecutor executor = new TestExecutor(
                SeleniumImpl.class
        );

        Result result = executor.execute(true);
        if (result.hasErrors()) {
            for (String error : result.getErrors()) {
                System.out.println(error);
            }
        }
        System.out.println("Done: [" + result.getResults().toString(2) + "]");
    }
}
