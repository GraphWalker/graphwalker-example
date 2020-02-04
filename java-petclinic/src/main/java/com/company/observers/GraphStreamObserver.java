package com.company.observers;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphwalker.core.event.EventType;
import org.graphwalker.core.event.Observer;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.machine.Machine;
import org.graphwalker.core.model.Edge.RuntimeEdge;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Vertex.RuntimeVertex;

import java.util.List;

/**
 * @author Nils Olsson
 */
public class GraphStreamObserver implements Observer {

    private final Graph graph;
    private Element lastElement = null;
    private Context lastContext = null;

    private static String stylesheet = "" +
            "node {" +
            " shape: rounded-box;" +
            " fill-color: lightblue;" +
            " fill-mode: dyn-plain;" +
            " size-mode: fit;" +
            "}" +
            "node.active {" +
            " fill-color: green;" +
            " size:40px;" +
            "}" +
            "node.visited {" +
            " fill-color: lightgray;" +
            "}" +
            "edge {" +
            " shape: cubic-curve;" +
            " fill-color: lightblue;" +
            " size: 2px;" +
            " arrow-shape: arrow;" +
            "}" +
            "edge.active {" +
            " fill-color: green;" +
            " size: 5px;" +
            "}" +
            "edge.visited {" +
            " fill-color: lightgray;" +
            " size: 1px;" +
            "}";

    public GraphStreamObserver(List<Context> contexts) {
        System.setProperty("org.graphstream.ui.renderer",
                "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph = new MultiGraph("GraphWalker MeetUp");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet", stylesheet);

        graph.display(true);

        for (Context context : contexts) {
            for (Element element : context.getModel().getElements()) {
                if (element instanceof RuntimeVertex) {
                    RuntimeVertex vertex = (RuntimeVertex) element;
                    Node node = graph.addNode(getId(context, vertex));
                    node.addAttribute("ui.label", vertex.getName());
                } else {
                    RuntimeEdge edge = (RuntimeEdge) element;
                    String source;
                    if (null == edge.getSourceVertex() && null == graph.getEdge("Start")) {
                        Node node = graph.addNode("Start");
                        node.addAttribute("ui.label", "Start");
                        node.addAttribute("ui.class", "visited");
                        source = "Start";
                    } else {
                        source = getId(context, edge.getSourceVertex());
                    }
                    Edge arc = graph.addEdge(getId(context, edge), source, getId(context, edge.getTargetVertex()), true);
                    arc.addAttribute("layout.weight", 2);
                    arc.addAttribute("ui.label", edge.getName());
                }
            }
        }
    }

    @Override
    public void update(Machine machine, Element element, EventType eventType) {
        if (EventType.BEFORE_ELEMENT.equals(eventType)) {

            if (lastElement != null) {
                if (lastElement instanceof RuntimeVertex) {
                    graph.getNode(getId(lastContext, lastElement)).setAttribute("ui.class", "visited");
                } else {
                    graph.getEdge(getId(lastContext, lastElement)).setAttribute("ui.class", "visited");
                }
            }

            if (element instanceof RuntimeVertex) {
                RuntimeVertex vertex = (RuntimeVertex) element;
                graph.getNode(getId(machine.getCurrentContext(), vertex)).addAttribute("ui.class", "active");
            } else {
                RuntimeEdge edge = (RuntimeEdge) element;
                graph.getEdge(getId(machine.getCurrentContext(), edge)).addAttribute("ui.class", "active");
            }
            lastElement = element;
            lastContext = machine.getCurrentContext();
        }
    }

    private String getId(Context context, Element element) {
        return context.toString() + element.getId();
    }
}
