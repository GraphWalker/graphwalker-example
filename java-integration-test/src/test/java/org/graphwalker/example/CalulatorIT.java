package org.graphwalker.example;

import org.graphwalker.java.test.TestExecutor;
import org.junit.Test;

public class CalulatorIT {

    @Test
    public void runCalculatorModel() throws Exception {
        new TestExecutor(CalculatorTest.class).execute();
    }
}
