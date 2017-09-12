package org.graphwalker.example;

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@GraphWalker
public class CalculatorTest extends ExecutionContext implements CalculatorModel {

    private Calculator calculator = new Calculator();

    @Override
    public void addNumbers() {
        // use the calculator and add some numbers
        calculator.add(2, 2);
    }

    @Override
    public void verifyResult() {
        // verify that the added numbers is correct
        assertThat(calculator.getResult(), is(4));
    }
}
