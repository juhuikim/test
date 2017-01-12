package com.kis4883.JSONPaserExam;

/**
 * JSONTestApp Class : Json Parser가 돌아가는지 확인하는 클래스
 * Created by kis4883 on 2017-01-10.
 */
public class JSONTestApp {

    public static void main(String[] args) {
        JsonParser parser = new JsonParser("sample.json");
        parser.parse();
    }
}