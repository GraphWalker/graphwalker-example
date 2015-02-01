package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

@GraphWalker(value = "random(edge_coverage(100))")
public class NewOwnerTest extends ExecutionContext implements NewOwnerSharedState {

    @Override
    public void v_OwnerInformation() {

    }

    @Override
    public void e_CorrectData() {

    }

    @Override
    public void e_IncorrectData() {

    }

    @Override
    public void v_IncorrectData() {

    }

    @Override
    public void v_NewOwner() {

    }
}
