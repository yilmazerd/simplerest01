package com.nextken.rapi.controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextken.rapi.models.KTRunnerRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class ForwardController {

    private static Logger logger = Logger.getLogger(ForwardController.class.getName());
    private static ObjectMapper objectMapper;

    @PostConstruct
    private void postConstruct() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, false);
    }

    // All controllers return the same


    @PostMapping(path = "/forward")
    public Object postController(
            @RequestBody String codeBulk,
            @RequestHeader Map<String, String> headers
            ) throws Exception{

        logger.log(Level.INFO, "received request succesfully\n");

        KTRunnerRequest ktRunnerRequest = new KTRunnerRequest(codeBulk);
        JsonNode jsonNode = ktRunnerRequest.getJson();
        RestTemplate restTemplate = new RestTemplate();

        URI uri = new URI("https://a4m4a452gc.execute-api.us-east-1.amazonaws.com/QA");


        //HttpEntity<JsonNode> requestEntity = new HttpEntity<JsonNode>(null, jsonNode);
        HttpEntity<JsonNode> entity = new HttpEntity<>(jsonNode);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        try {
            // edit response
            String s2 = result.getBody().toString();
            String s2_3 = s2.substring(1,s2.length()-1).replace("\\","");
            JsonNode jsonNodeResponse = objectMapper.readTree(s2_3);
            logger.log(Level.INFO, "json is succesfull");
            return jsonNodeResponse;
            //return newNode;
        } catch (Exception e) {
            logger.log(Level.INFO, "Can not cast to jSON");
        }

        return result;

    }

}
