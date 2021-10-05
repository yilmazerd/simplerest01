package com.nextken.rapi.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class KTRunnerRequest {

    String codeBulk;
    String ktRequest;
    ObjectMapper objectMapper;

    public String getCodeBulk() {
        return codeBulk;
    }

    public String getKtRequest() {
        return ktRequest;
    }

    public KTRunnerRequest(String codeBulk){
        this.codeBulk = codeBulk;
        ktRequest = setRequest();
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    };

    public KTRunnerRequest(){
        this.codeBulk = null;
    };

    public JsonNode getJson() {

        try {
            return  objectMapper.readTree(ktRequest);
        } catch (Exception e) {
            throw new RuntimeException("Can not parse ktl, invalid request, contact support");
        }

    }

    public static String codeCleanup(String codeBulk) {
            char[] myChars = codeBulk.toCharArray();
            String newString = "";
            Map<Character, String> map = new HashMap<>();
            map.put('\b', "\\b");
            map.put('\f', "\\f");
            map.put('\n', "\\n");
            map.put('\r', "\\r");
            map.put('\t', "\\t");
            map.put('\'', "\\'");
            map.put('\"', "\\\"");
            map.put('\\', "\\\\");

            for (char c:myChars) {

                if (map.get(c)!=null) {
                    newString += map.get(c);
                } else {
                    newString += c;
                }
            }

            return newString;
    }

    private String setRequest() {
        char[] myChars = codeBulk.toCharArray();
        String newString = "";
        Map<Character, String> map = new HashMap<>();
        map.put('\b', "\\b");
        map.put('\f', "\\f");
        map.put('\n', "\\n");
        map.put('\r', "\\r");
        map.put('\t', "\\t");
        map.put('\'', "\\'");
        map.put('\"', "\\\"");
        map.put('\\', "\\\\");

        for (char c:myChars) {

            if (map.get(c)!=null) {
                newString += map.get(c);
            } else {
                newString += c;
            }
        }

        String json = "{ \"code\" : \"" + newString + "\" } ";
        return json;
    }
}
