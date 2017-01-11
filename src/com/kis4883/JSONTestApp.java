package com.kis4883;

/**
 * Created by kis4883 on 2017-01-10.
 */
public class JSONTestApp {

    public static void main(String[] args) {
        JSONParser parser = new JSONParser("C:\\Users\\kis4883\\IdeaProjects\\JSONTEST\\src\\com\\kis4883\\sample.json");
        parser.parse();
        parser.printAll();
        parser.get("a");
        parser.get("b");
        parser.get("c");
    }
}