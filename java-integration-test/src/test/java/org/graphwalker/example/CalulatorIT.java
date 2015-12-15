package org.graphwalker.example;

import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutionException;
import org.graphwalker.java.test.TestExecutor;
import org.junit.Test;

import java.io.File;
import java.util.Date;

public class CalulatorIT {

    @Test
    public void runCalculatorModel() throws Exception {
        TestExecutor executor = new TestExecutor(CalculatorTest.class);
        Date startTime = new Date();
        Result result = executor.execute(false);
        executor.reportResults(new File("target/reports"), startTime, System.getProperties());
        if (result.hasErrors()) {
            throw new TestExecutionException();
        }
    }
}
