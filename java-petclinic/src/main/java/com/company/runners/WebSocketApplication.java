package com.company.runners;

import com.company.modelimplementations.*;
import com.company.observers.WebSocketObserver;
import org.graphwalker.java.test.Executor;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutor;

import java.net.UnknownHostException;

/**
 * @author Nils Olsson
 */
public class WebSocketApplication {

    public static void main(String[] args) {
        WebSocketObserver observer = null;
        try {
            observer = new WebSocketObserver(8887);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Executor executor = new TestExecutor(PetClinic.class,
                FindOwners.class,
                NewOwner.class,
                OwnerInformation.class,
                Veterinariens.class);

        observer.start();
        executor.getMachine().addObserver(observer);
        Result result = executor.execute();
        System.out.println("Done: [" + result.getCompletedCount() + "," + result.getFailedCount() + "]");
    }
}
