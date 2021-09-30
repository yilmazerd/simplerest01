package com.nextken.rapi.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class ForwardController {

    private static Logger logger = Logger.getLogger(ForwardController.class.getName());

    // All controllers return the same


    @PostMapping(path = "/forward", produces = "application/json", consumes = "text/plain")
    public Object postController(
            @RequestBody String codeBulk,
            @RequestHeader Map<String, String> headers
            ) throws Exception{


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

        //URI uri = new URI("http://localhost:8000/user");
        URI uri = new URI("https://a4m4a452gc.execute-api.us-east-1.amazonaws.com/QA");


        //HttpEntity<JsonNode> requestEntity = new HttpEntity<JsonNode>(null, jsonNode);
        HttpEntity<JsonNode> entity = new HttpEntity<>(jsonNode);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        try {
            objectMapper = new ObjectMapper();
            objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, false);

            // edit response
            String s2 = result.getBody().toString();
            String s2_3 = s2.substring(1,s2.length()-1).replace("\\","");
            JsonNode jsonNodeResponse = objectMapper.readTree(s2_3);
            logger.log(Level.INFO, "json is succesfull");
            String s3 = "{\"nick\": \"cowtowncoder\"}";
            logger.log(Level.INFO, s3);
            logger.log(Level.INFO, result.getBody().toString());
            logger.log(Level.INFO, s2);
            logger.log(Level.INFO, s2_3);
            JsonNode newNode = objectMapper.readTree(s2);

            return jsonNodeResponse;
            //return newNode;
        } catch (Exception e) {
            logger.log(Level.INFO, "Can not cast to jSON");
        }

        return result;

    }

}
