package com.onesignal;

import java.util.Iterator;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class JSONUtils {
    JSONUtils() {
    }

    static JSONObject generateJsonDiff(JSONObject cur, JSONObject changedTo, JSONObject baseOutput, Set<String> includeFields) {
        if (cur == null) {
            return null;
        }
        if (changedTo == null) {
            return baseOutput;
        }
        JSONObject output;
        Iterator<String> keys = changedTo.keys();
        if (baseOutput != null) {
            output = baseOutput;
        } else {
            output = new JSONObject();
        }
        while (keys.hasNext()) {
            try {
                String key = (String) keys.next();
                Object value = changedTo.get(key);
                if (cur.has(key)) {
                    if (value instanceof JSONObject) {
                        JSONObject curValue = cur.getJSONObject(key);
                        JSONObject outValue = null;
                        if (baseOutput != null && baseOutput.has(key)) {
                            outValue = baseOutput.getJSONObject(key);
                        }
                        String returnedJsonStr = generateJsonDiff(curValue, (JSONObject) value, outValue, includeFields).toString();
                        if (!returnedJsonStr.equals("{}")) {
                            output.put(key, new JSONObject(returnedJsonStr));
                        }
                    } else if (value instanceof JSONArray) {
                        handleJsonArray(key, (JSONArray) value, cur.getJSONArray(key), output);
                    } else if (includeFields == null || !includeFields.contains(key)) {
                        Object curValue2 = cur.get(key);
                        if (!value.equals(curValue2)) {
                            if (!(curValue2 instanceof Integer) || "".equals(value)) {
                                output.put(key, value);
                            } else if (((Number) curValue2).doubleValue() != ((Number) value).doubleValue()) {
                                output.put(key, value);
                            }
                        }
                    } else {
                        output.put(key, value);
                    }
                } else if (value instanceof JSONObject) {
                    output.put(key, new JSONObject(value.toString()));
                } else if (value instanceof JSONArray) {
                    handleJsonArray(key, (JSONArray) value, null, output);
                } else {
                    output.put(key, value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    private static void handleJsonArray(String key, JSONArray newArray, JSONArray curArray, JSONObject output) throws JSONException {
        if (key.endsWith("_a") || key.endsWith("_d")) {
            output.put(key, newArray);
            return;
        }
        int i;
        String arrayStr = toStringNE(newArray);
        JSONArray newOutArray = new JSONArray();
        JSONArray remOutArray = new JSONArray();
        String curArrayStr = curArray == null ? null : toStringNE(curArray);
        for (i = 0; i < newArray.length(); i++) {
            String arrayValue = (String) newArray.get(i);
            if (curArray == null || !curArrayStr.contains(arrayValue)) {
                newOutArray.put(arrayValue);
            }
        }
        if (curArray != null) {
            for (i = 0; i < curArray.length(); i++) {
                arrayValue = curArray.getString(i);
                if (!arrayStr.contains(arrayValue)) {
                    remOutArray.put(arrayValue);
                }
            }
        }
        if (!newOutArray.toString().equals("[]")) {
            output.put(key + "_a", newOutArray);
        }
        if (!remOutArray.toString().equals("[]")) {
            output.put(key + "_d", remOutArray);
        }
    }

    static String toStringNE(JSONArray jsonArray) {
        String strArray = "[";
        int i = 0;
        while (i < jsonArray.length()) {
            try {
                strArray = strArray + "\"" + jsonArray.getString(i) + "\"";
                i++;
            } catch (Throwable th) {
            }
        }
        return strArray + "]";
    }

    static JSONObject getJSONObjectWithoutBlankValues(JSONObject jsonObject, String getKey) {
        if (!jsonObject.has(getKey)) {
            return null;
        }
        JSONObject toReturn = new JSONObject();
        JSONObject keyValues = jsonObject.optJSONObject(getKey);
        Iterator<String> keys = keyValues.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            try {
                Object value = keyValues.get(key);
                if (!"".equals(value)) {
                    toReturn.put(key, value);
                }
            } catch (Throwable th) {
            }
        }
        return toReturn;
    }
}
