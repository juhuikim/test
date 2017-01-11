package com.kis4883.JSONPaserExam;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JSONParser Class : .json 파일을 읽어 String으로 변환 후, 필요한 token을 분리하여
 *                     LinkedHashMap 자료구조를 이용하여 Name-Value 형태로 저장 및 출력하는 클래스
 * Created by kis4883 on 2017-01-09.
 */
public class JSONParser {

    private FileReader  in;                                     // 파일을 읽는 FileReader 변수
    private String      strContextFile;                         // 읽은 .json 파일을 String으로 변환한 값을 저장하는 String 변수
    private Matcher     m;                                      // 정규식에 맞게 추출된 값(token)을 저장하는 변수
    private int         tokenCount;                             // 정규식에 맞게 추출된 token의 총 갯수를 저장하는 변수
    private String[]    allToken;                               // 정규식에 맞게 추출된 token을 String 형태로 저장하는 배열
    private LinkedHashMap<String, JSONValue> objectValue;       // Name과 Value를 한쌍으로 저장하는 자료구조

    JSONParser(String fileName) {
        objectValue = new LinkedHashMap<String, JSONValue>();
        fileReader(fileName);
    }


    private void fileReader(String fileName) {
        strContextFile = null;
        try {
            in = new FileReader(fileName);
            StringBuilder sb = new StringBuilder();
            char c;

            while((c = (char)in.read()) != (char)-1) {
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

    private boolean isStringInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void parse() {
        boolean hasName = false;
        boolean hasValue = false;
        String strName = null;
        JSONValue strValue = null;
        int totalIteration = 0;

        seperateToken();

        for(String nowToken : allToken) {
            totalIteration += 1;
            if (hasName == false) {
                strName = nowToken;
                hasName = true;
            } else {
                if (hasValue == false) {
                    strValue = new JSONValue(nowToken);
                    hasValue = true;
                }
            }

            if (hasName == true && hasValue == true) {
                objectValue.put(strName, strValue);
                hasName = false;
                hasValue = false;
                strName = null;
                strValue = null;
            }

            if(tokenCount <= totalIteration) break;
        }
    }

    public void printAll() {
        Set<String> set = objectValue.keySet();
        Iterator<String> iter = set.iterator();
        while(iter.hasNext()) {
            String key = ((String)iter.next());
            JSONValue value = objectValue.get(key);
            System.out.print(key + " : ");
            value.print();
        }
    }

    public void get(String strKey) {
        boolean result = false;
        Set<String> set = objectValue.keySet();
        Iterator<String> iter = set.iterator();
        while(iter.hasNext()) {
            String key = ((String)iter.next());

            if(key.equals(strKey)) {
                JSONValue value = objectValue.get(key);
                value.print();
                result = true;
                break;
            }
        }

        if(result == false) {
            System.out.println("해당 Name이 존재하지 않습니다!");
        }
    }

    private void seperateToken() {
        String strPattern = "\"[^\"]*\"|\\d";          // "Content"인 String과 Integer만 추출하게 하는 정규식
        Pattern p = Pattern.compile(strPattern);
        m = p.matcher(strContextFile);

        allToken = new String[100];

        tokenCount = 0;
        while(m.find()) {
            String stringTemp = (String)m.group();

            if(isStringInteger(stringTemp) == false) {                      // 정규식으로 추출할 때 doubleQuotation도 같이 추출
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
