package com.credera;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by kevinaud on 11/27/16.
 */
public class UserPreferences {

    private HashMap<String, Integer> preferences = new HashMap<String, Integer>();

    UserPreferences(String userPreferencesString) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode preferencesObj;

        try {

            preferencesObj = mapper.readTree(userPreferencesString);

        } catch(IOException error) {
            System.out.println(error.getMessage());
            System.out.println(error.getStackTrace());
            return;
        }

        Iterator<String> keysIter = preferencesObj.fieldNames();
        while(keysIter.hasNext()) {

            String key = keysIter.next();
            Object node = preferencesObj.get(key);

            if (node instanceof IntNode) {

                int value = ((IntNode) node).intValue();
                this.preferences.put(key, value);

            }

        }

    }

    public String toJsonString() {

        String jsonString = "{";
        Iterator iter = this.preferences.entrySet().iterator();

        boolean firstIteration = true;
        while(iter.hasNext()) {

            if (!firstIteration) {
                jsonString += ",";
            }
            firstIteration = false;

            HashMap.Entry preference = (HashMap.Entry)iter.next();
            jsonString += "\n\t" + "\"" + preference.getKey().toString() + "\": " + preference.getValue().toString();
        }

        jsonString += "\n}";

        return jsonString;
    };

    public String toTermsArray() {

        String jsonString = "[";
        Iterator iter = this.preferences.entrySet().iterator();

        boolean firstIteration = true;
        while(iter.hasNext()) {

            if (!firstIteration) {
                jsonString += ",";
            }
            firstIteration = false;

            HashMap.Entry preference = (HashMap.Entry)iter.next();

            jsonString += "\n\t{\n";
            jsonString += "\t\t\"term\": {\n";
            jsonString += "\t\t\t\"tags\": {\n";
            jsonString += "\t\t\t\t\"value\": \"" + preference.getKey().toString() + "\",\n";
            jsonString += "\t\t\t\t\"boost\": " + preference.getValue().toString() + "\n";
            jsonString += "\t\t\t}\n";
            jsonString += "\t\t}\n";
            jsonString += "\t}";

            /*{
                "term": {
                    "tags": {
                        "value": "Health",
                        "boost": 1.0
                    }
                }
            }*/

        }

        jsonString += "\n]";

        return jsonString;

    };

}
