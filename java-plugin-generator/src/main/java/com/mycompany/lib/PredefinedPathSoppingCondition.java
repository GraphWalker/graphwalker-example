package com.mycompany.lib;

import org.graphwalker.core.condition.StopConditionBase;

public class PredefinedPathSoppingCondition extends StopConditionBase {
    private final PredefinedPath predefinedPath;

    public PredefinedPathSoppingCondition(PredefinedPath predefinedPath) {
        super("");
        this.predefinedPath=predefinedPath;
    }

    @Override
    public double getFulfilment() {
        return predefinedPath.GetFulfillment();
    }
    
}
