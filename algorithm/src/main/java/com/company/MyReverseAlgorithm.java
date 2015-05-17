package com.company;

import org.graphwalker.core.algorithm.Algorithm;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.model.Edge;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nils Olsson
 */
public class MyReverseAlgorithm implements Algorithm {

	private final Context context;
	private List<Edge.RuntimeEdge> edges;

	public MyReverseAlgorithm(Context context) {
		this.context = context;
		doSomeWork();
	}

	public void doSomeWork() {
		edges = context.getModel().getEdges().stream()
				.sorted((edge1, edge2) -> edge2.getId().compareTo(edge1.getId()))
				.collect(Collectors.toList());
	}

	public List<Edge.RuntimeEdge> getEdges() {
		return edges;
	}
}
