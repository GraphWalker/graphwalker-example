package org.graphwalker.example;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutionException;
import org.graphwalker.java.test.TestExecutor;
import org.junit.Test;

import java.io.File;
import java.util.Date;

public class CalulatorIT {

    @Test
    public void runCalculatorModel() throws Exception {
        execute(CalculatorTest.class);
    }

    @Test
    public void runOptimizedCalculatorModel() throws Exception {
        execute(CalculatorAStarTest.class);
    }

    private void execute(Class<? extends ExecutionContext> test) {
        TestExecutor executor = new TestExecutor(test);
        Date startTime = new Date();
        Result result = executor.execute(false);
        executor.reportResults(new File("target/reports"), startTime, System.getProperties());
        if (result.hasErrors()) {
            throw new TestExecutionException();
        }
    }
}
