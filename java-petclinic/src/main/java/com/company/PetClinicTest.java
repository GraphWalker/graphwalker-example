package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

@GraphWalker(value = "random(edge_coverage(100))", start = "v_HomePage")
public class PetClinicTest extends ExecutionContext implements PetClinicSharedState {

    @Override
    public void v_FindOwners() {

    }

    @Override
    public void e_HomePage() {

    }

    @Override
    public void e_Veterinarians() {

    }

    @Override
    public void v_Veterinarians() {

    }

    @Override
    public void e_FindOwners() {

    }

    @Override
    public void v_HomePage() {

    }
}
