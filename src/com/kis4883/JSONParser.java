package com.kis4883;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kis4883 on 2017-01-09.
 */
public class JSONParser {

    private int curPos;
    private FileReader in;
    private String strContextFile;
    private Matcher m;
    private int tokenCount;
    private String[] allToken;
    private LinkedHashMap<String, JSONValue> objectValue;

    JSONParser(String fileName) {
        curPos = 0;
        objectValue = new LinkedHashMap<String, JSONValue>();
        fileReader(fileName);
    }


    void fileReader(String fileName) {
        strContextFile = null;
        try {
            in = new FileReader(fileName);
            StringBuilder sb = new StringBuilder();
            char c;

            while((c = (char)in.read()) != (char)-1) {
                sb.append(c);
            }

            String res = sb.toString();
            strContextFile = res.replaceAll("\\s",  "");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int peekStatus(LinkedList statusStack) {
        if(statusStack.size() == 0) {
            return -1;
        }

        Integer status = (Integer)statusStack.getFirst();
        return status.intValue();
    }

    boolean isStringInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    void parse() {
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
        Set<String> set = objectValue.keySet();
        Iterator<String> iter = set.iterator();
        while(iter.hasNext()) {
            String key = ((String)iter.next());

            if(key.equals(strKey)) {
                JSONValue value = objectValue.get(key);
                value.print();
            }
        }
    }

    private void seperateToken() {
        String pattern = "\"[^\"]*\"|\\d";
        Pattern p = Pattern.compile(pattern);
        m = p.matcher(strContextFile);

        allToken = new String[100];

        tokenCount = 0;
        while(m.find()) {
            String stringTemp = (String)m.group();

            if(isStringInteger(stringTemp) == false) {
                allToken[tokenCount] = removeDoubleQuotes(stringTemp);
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

    private String getNextToken() {
        String res = null;
        res = allToken[curPos];
        curPos += 1;

        return res;
    }
}
