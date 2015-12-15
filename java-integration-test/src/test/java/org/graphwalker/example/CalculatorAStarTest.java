package org.graphwalker.example;

import org.graphwalker.core.condition.ReachedVertex;
import org.graphwalker.core.generator.AStarPath;
import org.graphwalker.java.annotation.GraphWalker;

@GraphWalker(pathGenerator = AStarPath.class, stopCondition = ReachedVertex.class, stopConditionValue = "verifyResult")
public class CalculatorAStarTest extends CalculatorTest {
}
