package com.mycompany.lib;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public Model(){}
    private String name;
    private String id;
    private String startElementId;
    private String generator;
    private List<String> predefinedPath = new ArrayList<>();
    private List<Vertex> vertices;
    private List<Edge> edges;

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<String> getPredefinedPath() {
        return predefinedPath;
    }

    public void setPredefinedPath(List<String> predefinedPath) {
        this.predefinedPath = predefinedPath;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getStartElementId() {
        return startElementId;
    }

    public void setStartElementId(String startElementId) {
        this.startElementId = startElementId;
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
