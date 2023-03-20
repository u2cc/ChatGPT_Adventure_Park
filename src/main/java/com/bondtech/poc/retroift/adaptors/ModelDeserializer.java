package com.bondtech.poc.retroift.adaptors;

import com.bondtech.poc.retroift.model.Model;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModelDeserializer implements JsonDeserializer<List<Model>> {
    Gson gson = new Gson();
    @Override
    public List<Model> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("data").getAsJsonArray();
        ArrayList<Model> list = new ArrayList<>();
//        for(int i=0;i<jsonArray.size();i++){
//            JsonElement modelJson = jsonArray.get(i);
//            JsonObject modelJsonAsJsonObject = modelJson.getAsJsonObject();
//            if("model".equals(modelJsonAsJsonObject.get("object").getAsString())){
//               list.add(jsonDeserializationContext.deserialize(modelJson, Model.class));
//            }
//        }
        Type founderListType = new TypeToken<ArrayList<Model>>(){}.getType();
        List<Model> models = jsonDeserializationContext.deserialize(jsonArray,founderListType);

        return models;
    }
}
