package com.kis4883;

/**
 * Created by kis4883 on 2017-01-10.
 */
public class JSONValue {
    private int intValue;
    private String stringValue = null;
    private boolean isInteger;
    private boolean isString;

    JSONValue(String str){
        if(isStringInteger(str) == true) {
            intValue = Integer.parseInt(str);
            isInteger = true;
            isString = false;
        }
        else {
            stringValue = str;
            isString = true;
            isInteger = false;
        }
    }

    boolean isStringInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    void print(){
        if(isInteger == true) {
            System.out.println(intValue);
        }
        else {
            System.out.println(stringValue);
        }
    }
}
