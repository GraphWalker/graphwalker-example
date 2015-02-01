package com.company;


import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

@GraphWalker(value = "random(edge_coverage(100))")
public class OwnerInformationTest extends ExecutionContext implements OwnerInformationSharedState {

    @Override
    public void v_OwnerInformation() {

    }

    @Override
    public void e_UpdatePet() {

    }

    @Override
    public void v_FindOwners() {

    }

    @Override
    public void e_EditPet() {

    }

    @Override
    public void e_AddNewPet() {

    }

    @Override
    public void e_AddVisit() {

    }

    @Override
    public void e_FindOwners() {

    }

    @Override
    public void e_AddPetSuccessfully() {

    }

    @Override
    public void v_NewPet() {

    }

    @Override
    public void e_VisitAddedSuccessfully() {

    }

    @Override
    public void v_NewVisit() {

    }

    @Override
    public void v_Pet() {

    }

    @Override
    public void e_AddPetFailed() {

    }

    @Override
    public void e_VisitAddedFailed() {

    }
}
