package com.company.runners;

import com.company.modelimplementations.*;
import com.company.observers.GraphStreamObserver;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphwalker.core.event.Observer;
import org.graphwalker.java.test.Executor;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutor;

/**
 * @author Nils Olsson
 */
public class GraphStreamApplication {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer",
                "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new SingleGraph("GraphWalker MeetUp");
        graph.display(true);
        Executor executor = new TestExecutor(PetClinic.class,
                FindOwners.class,
                NewOwner.class,
                OwnerInformation.class,
                Veterinariens.class);
        Observer observer = new GraphStreamObserver(graph);
        executor.getMachine().addObserver(observer);
        Result result = executor.execute();
        System.out.println("Done: [" + result.getCompletedCount() + "," + result.getFailedCount() + "]");
    }
}
