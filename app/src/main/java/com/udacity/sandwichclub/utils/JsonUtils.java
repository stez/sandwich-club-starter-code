package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject name = root.getJSONObject("name");
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            JSONArray ingredients = root.getJSONArray("ingredients");

            sandwich.setPlaceOfOrigin(root.getString("placeOfOrigin"));
            sandwich.setDescription(root.getString("description"));
            sandwich.setImage(root.getString("image"));
            sandwich.setMainName(name.getString("mainName"));

            List<String> names = new ArrayList<>();
            for (int i=0; i < alsoKnownAs.length(); i++) {
                names.add(alsoKnownAs.getString(i));
            }
            sandwich.setAlsoKnownAs(names);

            List<String> ingr = new ArrayList<>();
            for (int i=0; i < ingredients.length(); i++) {
                ingr.add(ingredients.getString(i));
            }
            sandwich.setIngredients(ingr);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return sandwich;
    }
}
