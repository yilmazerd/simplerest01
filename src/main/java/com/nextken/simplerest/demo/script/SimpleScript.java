package com.nextken.simplerest.demo.script;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextken.simplerest.demo.GreetingController;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleScript {




    public static void main(String[] args) {
        System.out.println("Test\n");
        System.out.println("Test\n");
        System.out.println("Test\n");
        Somet s = new Somet();
        s.getController();
    }
}

class Somet {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    public static final Logger LOGGER = Logger.getLogger(GreetingController.class.getName());
    ObjectMapper mapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();
    JSONObject jsonObject = new JSONObject();
    JsonNode root = null;
    HttpHeaders responseHeaders = new HttpHeaders();
    public ResponseEntity<Object> getController() {

        String resourceURL = "http://www.sci.utah.edu/~macleod/docs/txt2html/sample.txt";
        ResponseEntity<String> response  = restTemplate.getForEntity(resourceURL, String.class);
        Object responseBody = getResponseBody(response, ResponseType.TEXT);

        responseHeaders.set(
                "Example-Header-Sample", "This-is-my-sample"
        );

        System.out.println(responseBody.toString());

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(responseBody);
    }

    Object getResponseBody(ResponseEntity<String> response, ResponseType responseType) {
        Object object = new Object();
        switch (responseType) {
            case JSON:
                try {
                    LOGGER.log(Level.INFO,"Converted object");
                    root = mapper.readTree(response.getBody());
                    object = root;
                    LOGGER.log(Level.INFO,"Got JSON");
                    LOGGER.log(Level.INFO,jsonObject.toString());
                } catch (Exception e) {
                    LOGGER.log(Level.INFO,"Got Exception" + e.toString());
                }
            case TEXT:
                try {
                    object = response.getBody();
                    LOGGER.log(Level.INFO,"Using Text");
                } catch (Exception e) {
                    LOGGER.log(Level.INFO,"Error converting text" + e.toString());
                }
        }

        return object;
    }
};



enum ResponseType{
    JSON,
    TEXT
}

class Content {

    private String contentUrl;
    private ResponseType responseType;
    private int httpStatus = 200;
    private Map<String, String> headers;

    Content(String contentUrl, ResponseType responseType) {
        this.contentUrl = contentUrl;
        this.responseType = responseType;
    }
    Content(String contentUrl, ResponseType responseType, int httpStatus){
        this.contentUrl = contentUrl;
        this.responseType = responseType;
        this.httpStatus = httpStatus;
    }

    Content(String contentUrl, ResponseType responseType, int httpStatus, Map<String, String> stringStringMap){
        this.contentUrl = contentUrl;
        this.responseType = responseType;
        this.httpStatus = httpStatus;
        this.headers = stringStringMap;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}


