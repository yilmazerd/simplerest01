package com.nextken.rapi.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import util.CharEscaperBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class ForwardController {


    // All controllers return the same
    @PostMapping(path = "/forward",
            produces = "application/json")
    public Object postController(
            @Nullable @RequestBody String codeBulk) throws Exception{


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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        JsonNode jsonNode = objectMapper.readTree(json);

        RestTemplate restTemplate = new RestTemplate();

        URI uri = new URI("http://localhost:8000/user");


        //HttpEntity<JsonNode> requestEntity = new HttpEntity<JsonNode>(null, jsonNode);
        HttpEntity<JsonNode> entity = new HttpEntity<>(jsonNode);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        try {
            JsonNode jsonNodeResponse = objectMapper.readTree(result.getBody());
            return jsonNodeResponse;
        } catch (Exception e) {
            //
        }

        return result;

    }

}
