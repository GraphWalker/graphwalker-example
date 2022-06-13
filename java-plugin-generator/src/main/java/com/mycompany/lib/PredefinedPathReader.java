package com.mycompany.lib;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import java.io.FileReader;
import java.util.List;

public class PredefinedPathReader {

    public PredefinedPath Read(String pathToModel){
        List<String> predefinedPath=null;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(pathToModel));
            JSONObject fullFile = (JSONObject) obj;
            Gson gson = new Gson();
            Models models = gson.fromJson(fullFile.toJSONString(), Models.class);
            predefinedPath= models.getModels().get(0).getPredefinedPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PredefinedPath(predefinedPath);
    }
}
