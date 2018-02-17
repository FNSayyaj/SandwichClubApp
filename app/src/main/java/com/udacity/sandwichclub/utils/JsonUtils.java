package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";


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

            JSONObject nameProperties = mainSandwichesJSON.optJSONObject(KEY_NAME);

            mainName = nameProperties.optString(KEY_MAIN_NAME);
            JSONArray alsoKnownAsArray = nameProperties.optJSONArray(KEY_KNOWN_AS);
            alsoKnownAs = new ArrayList<>();
            //iterate through the array of names using JSONArray
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(i, alsoKnownAsArray.optString(i));

            }


            JSONArray ingredientsArray = mainSandwichesJSON.optJSONArray(KEY_INGREDIENTS);
            ingredients = new ArrayList<>();
            //iterate through the array of ingredients
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(i, ingredientsArray.optString(i));

            }

            origin = mainSandwichesJSON.optString(KEY_ORIGIN);
            description = mainSandwichesJSON.optString(KEY_DESCRIPTION);
            image = mainSandwichesJSON.optString(KEY_IMAGE);

        }catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //returning data got from JSON
        return new Sandwich(mainName, alsoKnownAs ,origin, description, image, ingredients);

    }
}
