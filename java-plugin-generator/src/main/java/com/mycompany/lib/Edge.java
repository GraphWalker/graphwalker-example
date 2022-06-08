package com.mycompany.lib;

public class Edge {

    private String name;
    private String id;
    private String sourceVertexId;
    private String targetVertexId;

    public String getSourceVertexId() {
        return sourceVertexId;
    }

    public String getTargetVertexId() {
        return targetVertexId;
    }

    public void setTargetVertexId(String targetVertexId) {
        this.targetVertexId = targetVertexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
