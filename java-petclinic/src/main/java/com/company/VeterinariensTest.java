package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

@GraphWalker(value = "random(edge_coverage(100))")
public class VeterinariensTest extends ExecutionContext implements VeterinariensSharedState {

    @Override
    public void e_Search() {

    }

    @Override
    public void v_SearchResult() {

    }

    @Override
    public void v_Veterinarians() {

    }
}
