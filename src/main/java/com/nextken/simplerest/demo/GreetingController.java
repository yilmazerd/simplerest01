package com.nextken.simplerest.demo;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    public static final Logger LOGGER = Logger.getLogger(GreetingController.class.getName());

    @GetMapping("/greeting")
    public JSONObject greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return new Greeting(counter.incrementAndGet(), String.format(template, name));
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            Object obj = parser.parse(new FileReader("/Users/erdemyilmaz/Desktop/JAVAPROJECTS/simpleRest0/simplerest01/src/main/resources/template1.json"));

            LOGGER.log(Level.INFO,"Converted object");
            jsonObject = (JSONObject) obj;
            LOGGER.log(Level.INFO,"Got JSON");
            LOGGER.log(Level.INFO,jsonObject.toString());
        } catch (Exception e) {
            LOGGER.log(Level.INFO,"Got Exception" + e.toString());
        }
        return jsonObject;
    }
}


