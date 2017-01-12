package com.kis4883.JSONPaserExam;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JSONParser Class : .json 파일을 읽어 String으로 변환 후, 필요한 token을 분리하여
 *                     LinkedHashMap 자료구조를 이용하여 Name-Value 형태로 저장 및 출력하는 클래스
 * Created by kis4883 on 2017-01-09.
 */
public class JsonParser {

    private FileReader in;                                     // 파일을 읽는 FileReader 변수
    private String      strContextFile;                         // 읽은 .json 파일을 String으로 변환한 값을 저장하는 String 변수
    private Matcher m;                                      // 정규식에 맞게 추출된 값(token)을 저장하는 변수
    private int         tokenCount;                             // 정규식에 맞게 추출된 token의 총 갯수를 저장하는 변수
    private String[] allToken;                               // 정규식에 맞게 추출된 token을 String 형태로 저장하는 배열
    private LinkedHashMap<String, JsonObject> objectValue;       // Name과 Value를 한쌍으로 저장하는 자료구조

    JSONParser(String fileName) {
        objectValue = new LinkedHashMap<String, JsonObject>();
        fileReader(fileName);
    }

    private void fileReader(String fileName) {
        strContextFile = null;
        try {
            in = new FileReader(fileName);
            StringBuilder sb = new StringBuilder();
            char c;

            while((c = (char)in.read()) != (char)-1) {
                if()
                    sb.append(c);
            }

            String res = sb.toString();
            System.out.println("Json Context\n" + res);

            strContextFile = res.replaceAll("\\s",  "");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse() {
        boolean hasName = false;
        boolean hasValue = false;
        String strName = null;
        JsonObject objValue = null;
        int totalIteration = 0;

        seperateToken();

        for(String nowToken : allToken) {
            totalIteration += 1;
            if (hasName == false) {
                strName = nowToken;
                hasName = true;
            } else {
                if (hasValue == false) {
                    objValue = new JsonObject(nowToken);
                    hasValue = true;
                }
            }

            if (hasName == true && hasValue == true) {
                objectValue.put(strName, objValue);
                hasName = false;
                hasValue = false;
                strName = null;
                objValue = null;
            }

            if(tokenCount <= totalIteration) break;
        }
    }

    // 이부분 구현하기
    private boolean isString(String str) {
        return false;
    }

    private void seperateToken() {
        String strPattern = "\"[^\"]*\"|\\d";          // "Content"인 String과 Integer만 추출하게 하는 정규식
        Pattern p = Pattern.compile(strPattern);
        m = p.matcher(strContextFile);

        allToken = new String[1000];

        tokenCount = 0;
        while(m.find()) {
            String stringTemp = (String)m.group();

            if(isString(stringTemp) == true) {                      // 정규식으로 추출할 때 doubleQuotation도 같이 추출
                allToken[tokenCount] = removeDoubleQuotes(stringTemp);      // 그러므로 이를 제거해서 token[] 형태로 저장
            }
            else {
                allToken[tokenCount] = stringTemp;
            }

            tokenCount += 1;
        }
    }

    private String removeDoubleQuotes(String stringInput) {
        String strRet = "";

        for(int idx = 0; idx < stringInput.length(); idx++) {
            if(stringInput.charAt(idx) != '\"') {
                strRet += stringInput.charAt(idx);
            }
        }
        return strRet;
    }
}
