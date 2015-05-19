package com.company;

import org.graphwalker.core.algorithm.Algorithm;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.model.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Nils Olsson
 */
public class MyReverseAlgorithm implements Algorithm {

	private final Context context;
	private List<Edge.RuntimeEdge> edges = new ArrayList<>();

	public MyReverseAlgorithm(Context context) {
		this.context = context;
		doSomeWork();
	}

	public void doSomeWork() {
		edges.addAll(context.getModel().getEdges());
		Collections.sort(edges, new Comparator<Edge.RuntimeEdge>() {
			@Override
			public int compare(Edge.RuntimeEdge edge1, Edge.RuntimeEdge edge2) {
				return edge2.getId().compareTo(edge1.getId());
			}
		});
	}

	public List<Edge.RuntimeEdge> getEdges() {
		return edges;
	}
}
