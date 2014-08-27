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

import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.model.Model;
import org.graphwalker.java.annotation.GraphWalker;

import static org.graphwalker.core.Assert.expect;

/**
 * @author Nils Olsson
 */
@GraphWalker
public class CreateModelTest extends ExecutionContext implements CreateModel {

    private Model model = new Model();

    @Override
    public void createVertex() {

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
        expect(model).to.have.property("edges").that.have.size(getEdgesCount());
        expect(model).to.have.property("vertices").that.have.size(getVertexCount());
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

    }

    @Override
    public void addVertex() {

    }

    @Override
    public void removeSharedState() {

    }

    @Override
    public void validateRuntimeModel() {

    }

    private int getEdgesCount() {
        return Integer.parseInt((String)getAttribute("edges"));
    }

    private int getVertexCount() {
        return Integer.parseInt((String)getAttribute("vertices"));
    }
}
