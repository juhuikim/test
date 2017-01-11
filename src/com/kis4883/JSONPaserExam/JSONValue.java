package com.kis4883.JSONPaserExam;

/**
 * JSONValue Class : Value에 대한 자료형에 대한 클래스
 * created by kis4883 on 2017-01-10.
 */
public class JSONValue {

    private int     intValue;               // value가 integer -> intValue에 값이 들어간다.
    private String  stringValue = null;     // value가 String -> stringValue에 값이 들어간다.
    private boolean isInteger;              // intValue에 값이 들어가면 true가 된다.
    private boolean isString;               // stringValue에 값이 들어가면 false가 된다.

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

    // 값이 integer인지 String인지 확인하는 메소드
    private boolean isStringInteger(String s) {
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
