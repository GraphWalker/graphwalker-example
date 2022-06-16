package com.mycompany.lib;

import java.util.List;

public class PredefinedPath {
    public PredefinedPath(List<String> predefinedPath) {
        this.predefinedPath = predefinedPath;
        this.current = 0;
    }

    public int GetCurrentIndex(){
        return current;
    }

    public String GetCurrentEdge(){
        return predefinedPath.get(current);
    }

    public double GetFulfillment(){
        return (double)current/(predefinedPath.size()-1);
    }

    public Boolean HasNextStep(){
        return (current < predefinedPath.size());
    } 

    public void IncrementCurrent(){
        if(current==predefinedPath.size()){
            throw new IllegalStateException("Current can not be more than the size of the predefined path!");
        }
        current++;
    }

    private List<String> predefinedPath;

    private int current;

}
