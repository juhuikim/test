package com.kis4883.JSONPaserExam;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by kis4883 on 2017-01-12.
 */
public class JsonObject implements JsonValue{

    private LinkedHashMap<String, JsonObject> objectValue;

    JsonObject() {
        objectValue = new LinkedHashMap<String, JsonObject>();
    }

    @Override
    public void print() {
        Iterator<String> keys = objectValue.keySet().iterator();

        for( String key : objectValue.keySet() ){
            System.out.print(key + " : ");
            JsonObject value = objectValue.get(key);
        }
    }
}