package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        String mainName;
        List<String> alsoKnownAs;
        String origin;
        String description;
        List<String> ingredients;
        String image;

        try{
            //get the main JSONObject
            JSONObject mainSandwichesJSON = new JSONObject(json);

            JSONObject nameProperties = mainSandwichesJSON.getJSONObject("name");

            mainName = nameProperties.getString("mainName");
            JSONArray alsoKnownAsArray = nameProperties.getJSONArray("alsoKnownAs");
            alsoKnownAs = new ArrayList<>();
            //iterate through the array of names using JSONArray
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(i, alsoKnownAsArray.getString(i));

            }


            JSONArray ingredientsArray = mainSandwichesJSON.getJSONArray("ingredients");
            ingredients = new ArrayList<>();
            //iterate through the array of ingredients
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(i, ingredientsArray.getString(i));

            }

            origin = mainSandwichesJSON.getString("placeOfOrigin");
            description = mainSandwichesJSON.getString("description");
            image = mainSandwichesJSON.getString("image");

        }catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //initializing the main Sandwich object with data from the JSONObject we got from here and returning it
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs ,origin, description, image, ingredients);
        return sandwich;

    }
}
