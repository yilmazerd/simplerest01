package com.nextken.simplerest.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Math;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    public static final Logger LOGGER = Logger.getLogger(GreetingController.class.getName());

    @GetMapping("/greeting")
    public ResponseEntity<Object> getController() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        //https://raw.githubusercontent.com/yilmazerd/simplerest01/nextken-000/src/main/resources/template1.json
        RestTemplate restTemplate = new RestTemplate();
        String resourceURL = "https://raw.githubusercontent.com/yilmazerd/simplerest01/nextken-000/src/main/resources/template1.json";
        ResponseEntity<String> response  = restTemplate.getForEntity(resourceURL, String.class);
        JsonNode root = null;
        try {
            //Object obj = parser.parse(new FileReader("/Users/erdemyilmaz/Desktop/JAVAPROJECTS/simpleRest0/simplerest01/src/main/resources/template1.json"));
            //Object obj = mapper.readTree(response.getBody());
            LOGGER.log(Level.INFO,"Converted object");
            //jsonObject = (JSONObject) obj;
            root = mapper.readTree(response.getBody());
            LOGGER.log(Level.INFO,"Got JSON");
            LOGGER.log(Level.INFO,jsonObject.toString());
        } catch (Exception e) {
            LOGGER.log(Level.INFO,"Got Exception" + e.toString());
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(
                "Example-Header-Sample", "This-is-my-sample"
        );

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(root);

    }
}


