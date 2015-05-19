package com.company;

import org.graphwalker.core.condition.StopCondition;
import org.graphwalker.core.generator.PathGeneratorBase;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.model.Element;

import static org.graphwalker.core.model.Edge.RuntimeEdge;
import static org.graphwalker.core.model.Vertex.RuntimeVertex;

/**
 * @author Nils Olsson
 */
public class MyPathGenerator extends PathGeneratorBase<StopCondition> {

	private int index = 0;

	public MyPathGenerator(StopCondition stopCondition) {
		setStopCondition(stopCondition);
	}

	@Override
	public Context getNextStep() {
		Context context = getContext();
		Element element = context.getCurrentElement();
		if (null == element) {
			element = context.getNextElement();
		}
		if (element instanceof RuntimeVertex) {
			return context.setCurrentElement(getContext().getAlgorithm(MyReverseAlgorithm.class).getEdges().get(index++));
		} else {
			return context.setCurrentElement(((RuntimeEdge) element).getTargetVertex());
		}
	}

	@Override
	public boolean hasNextStep() {
		return !getStopCondition().isFulfilled();
	}
}
