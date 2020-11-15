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
    ObjectMapper mapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/greeting")
    public ResponseEntity<Object> getController() {
        JSONObject jsonObject = new JSONObject();

        //https://raw.githubusercontent.com/yilmazerd/simplerest01/nextken-000/src/main/resources/template1.json

        String resourceURL = "https://raw.githubusercontent.com/yilmazerd/simplerest01/nextken-000/src/main/resources/template1.json";
        ResponseEntity<String> response  = restTemplate.getForEntity(resourceURL, String.class);
        JsonNode root = null;
        try {

            LOGGER.log(Level.INFO,"Converted object");
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


