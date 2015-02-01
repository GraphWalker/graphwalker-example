package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

@GraphWalker(value = "random(edge_coverage(100))")
public class FindOwnersTest extends ExecutionContext implements FindOwnersSharedState {

    @Override
    public void e_AddOwner() {

    }

    @Override
    public void v_FindOwners() {

    }

    @Override
    public void e_FindOwners() {

    }

    @Override
    public void v_NewOwner() {

    }
}
