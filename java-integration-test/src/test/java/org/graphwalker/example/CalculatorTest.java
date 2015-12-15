package org.graphwalker.example;

import org.graphwalker.core.condition.ReachedVertex;
import org.graphwalker.core.generator.AStarPath;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

@GraphWalker(pathGenerator = AStarPath.class, stopCondition = ReachedVertex.class, stopConditionValue = "verifyResult")
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
