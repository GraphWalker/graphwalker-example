package com.company.runners;

import com.company.modelimplementations.*;
import com.company.observers.GraphStreamObserver;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphwalker.core.event.Observer;
import org.graphwalker.java.test.Executor;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutor;

import java.io.IOException;

/**
 * @author Nils Olsson
 */
public class GraphStreamApplication {

    public static void main(String[] args) throws IOException {
        System.setProperty("org.graphstream.ui.renderer",
                "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new MultiGraph("GraphWalker MeetUp");
        graph.display(true);
        Executor executor = new TestExecutor(PetClinicTest.class,
                FindOwnersTest.class,
                NewOwnerTest.class,
                OwnerInformationTest.class,
                VeterinariansTest.class);
        Observer observer = new GraphStreamObserver(graph);
        executor.getMachine().addObserver(observer);

        Result result = executor.execute(true);
        if (result.hasErrors()) {
            for (String error : result.getErrors()) {
                System.out.println(error);
            }
        }
        System.out.println("Done: [" + result.getResults().toString(2) + "]");
    }
}
