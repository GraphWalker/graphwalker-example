package com.company.runners;

import com.company.modelimplementations.*;
import com.company.observers.GraphStreamObserver;
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
        Executor executor = new TestExecutor(PetClinic.class,
                FindOwners.class,
                NewOwner.class,
                OwnerInformation.class,
                Veterinariens.class);
        Observer observer = new GraphStreamObserver(executor.getMachine().getContexts());
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
