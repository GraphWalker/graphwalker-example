package com.company;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Model;
import org.graphwalker.core.model.Vertex;
import org.graphwalker.java.annotation.GraphWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Nils Olsson
 */
@GraphWalker(pathGenerator = MyPathGenerator.class, stopCondition = EdgeCoverage.class)
public class MyTest extends ExecutionContext {

	private Logger LOG = LoggerFactory.getLogger(MyTest.class);

	public MyTest() {
		Model model = new Model();
		Vertex vertex = new Vertex();
		for (int i = 0; i < 10; i++) {
			model.addEdge(new Edge()
					.setSourceVertex(vertex)
					.setTargetVertex(vertex).setId(String.valueOf(i))
					.setName("execute1"));
		}
		setNextElement(vertex);
		setModel(model.build());
	}

	public void execute1() {
		LOG.info(getCurrentElement().getId());
	}
}
