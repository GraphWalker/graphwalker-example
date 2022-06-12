package com.mycompany.lib;

import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Vertex;
import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.condition.ReachedEdge;
import org.graphwalker.core.condition.StopCondition;
import org.graphwalker.core.generator.NoPathFoundException;
import org.graphwalker.core.generator.PathGeneratorBase;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.model.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class PredefinedPathGenerator extends PathGeneratorBase<StopCondition> {

    private static final Logger LOG = LoggerFactory.getLogger(PredefinedPathGenerator.class);
    private List<String> predefinedPath;
    private final String pathToModels=System.getProperty("user.dir");
    private int currentPathIndex = 0;

    public PredefinedPathGenerator(StopCondition stopCondition) {
      
        String filePath=pathToModels+"\\"+stopCondition.getValue()+".json";
        System.out.println(filePath);
        setPredefinedPath(filePath);
        setStopCondition(new EdgeCoverage(100));
    }

    @Override
    public Context getNextStep() {
        Context context = super.getNextStep();
        Element currentElement = context.getCurrentElement();
        List<Element> reachableElements = context.filter(context.getModel().getElements(currentElement));

        if (reachableElements.isEmpty()) {
            LOG.error("currentElement: " + currentElement);
            LOG.error("context.getModel().getElements(): " + context.getModel().getElements());
            throw new NoPathFoundException(context.getCurrentElement());
        }
        Element nextElement;

        if (currentElement instanceof Edge.RuntimeEdge) {
            nextElement = getNextElementFromEdge(context, reachableElements, (Edge.RuntimeEdge) currentElement);
        } else if (currentElement instanceof Vertex.RuntimeVertex) {
            nextElement = getNextElementFromVertex(context, reachableElements, (Vertex.RuntimeVertex) currentElement);
        } else {
            LOG.error("Current element is neither an edge or a vertex");
            throw new NoPathFoundException(context.getCurrentElement());
        }
        context.setCurrentElement(nextElement);
        return context;
    }

    @Override
    public boolean hasNextStep() {
        return (currentPathIndex < predefinedPath.size());
    }

    private Element getNextElementFromEdge(Context context, List<Element> reachableElements,
            Edge.RuntimeEdge currentElement) {
        if (reachableElements.size() != 1) {
            LOG.error("Next vertex of predefined path is ambiguous (after step " + currentPathIndex
                    + ", from edge with id \"" + currentElement.getId() + "\")");
            throw new NoPathFoundException(currentElement);
        }
        currentPathIndex++;
        return reachableElements.get(0);
    }

    private Element getNextElementFromVertex(Context context, List<Element> reachableElements,
            Vertex.RuntimeVertex currentElement) {
        for (Element elem : reachableElements) {
            if (elem.getId().equals(predefinedPath.get(currentPathIndex))) {
                return elem;
            }
        }
        LOG.error("Next edge with id \"" + predefinedPath.get(currentPathIndex)
                + "\" from predefined path is unreachable (either the guarding condition was not met or the edge has a different source vertex.");
        throw new NoPathFoundException(currentElement);
    }

    private void setPredefinedPath(String path) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(path));
            JSONObject fullFile = (JSONObject) obj;
            Gson gson = new Gson();
            Models models = gson.fromJson(fullFile.toJSONString(), Models.class);
            predefinedPath = models.getModels().get(0).getPredefinedPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}