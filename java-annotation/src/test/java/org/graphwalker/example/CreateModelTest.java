package org.graphwalker.example;

/*
 * #%L
 * GraphWalker Example
 * %%
 * Copyright (C) 2011 - 2014 GraphWalker
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */


import org.graalvm.polyglot.Value;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Model;
import org.graphwalker.core.model.Vertex;
import org.graphwalker.java.annotation.GraphWalker;
import org.junit.Assert;

import java.util.ArrayList;


/**
 * @author Nils Olsson
 */
@GraphWalker(start = "init")
public class CreateModelTest extends ExecutionContext implements CreateModel {

    private Model model = new Model();
    private Edge edge;
    private Vertex vertex;

    @Override
    public void createVertex() {
        vertex = new Vertex();
    }

    @Override
    public void validateEdge() {

    }

    @Override
    public void addGuard() {

    }

    @Override
    public void init() {

    }

    @Override
    public void removeAction() {

    }

    @Override
    public void addSharedState() {

    }

    @Override
    public void validateModel() {
        Assert.assertEquals(get("edges"), model.getEdges().size());
        Assert.assertEquals(get("vertices"), model.getVertices().size());
    }

    @Override
    public void removeRequirement() {

    }

    @Override
    public void validateVertex() {

    }

    @Override
    public void validateResult() {

    }

    @Override
    public void createEdge() {
        edge = new Edge();
    }

    @Override
    public void addRequirement() {

    }

    @Override
    public void buildModel() {

    }

    @Override
    public void execute() {

    }

    @Override
    public void removeGuard() {

    }

    @Override
    public void addAction() {

    }

    @Override
    public void addEdge() {
        model.addEdge(edge);
    }

    @Override
    public void addVertex() {
        model.addVertex(vertex);
    }

    @Override
    public void removeSharedState() {

    }

    @Override
    public void validateRuntimeModel() {

    }

    private int get(String name) {
        if (null != getAttribute(name)) {
            return getAttribute(name).asInt();
        }
        return 0;
    }

}
