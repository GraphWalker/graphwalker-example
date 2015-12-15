package org.graphwalker.example;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

@GraphWalker
public class CalculatorTest extends ExecutionContext implements CalculatorModel {

    @Override
    public void addNumbers() {
        // use the calculator and add some numbers
    }

    @Override
    public void verifyResult() {
        // verify that the added numbers is correct
    }
}
