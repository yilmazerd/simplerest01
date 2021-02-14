package com.nextken.rapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextken.rapi.models.CBCompiler;
import com.nextken.rapi.models.CBRequest;
import com.nextken.rapi.models.CBResponse;
import com.nextken.rapi.models.CBResponseError;
import com.nextken.rapi.service.CodeBlockService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class CobdeBlockController {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @Autowired
    CodeBlockService codeBlockService;

    @GetMapping({"/","/hello"})
    public String helloWorld(@RequestParam(required = false, defaultValue = "World") String name) {
        return "hello-world";
    }

//    @CrossOrigin(origins = {"null", "https://instantfunction.com"})
//    @PostMapping(path = "/formation/codeblock", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<CBResponse> postFormationController(@RequestBody CBRequest cbRequest) throws Exception{
//        CBResponse cbResponse;
//        String errorStatement = null;
//        if (cbRequest == null) {
//            errorStatement += "Invalid entry/n";
//        } else {
//            if (cbRequest.getCode() == null) {
//                errorStatement += "Invalid input/n";
//            }
//        }
//
//        if (errorStatement!=null){
//            CBResponseError cbResponseError = new CBResponseError(errorStatement);
//            cbResponse = new CBResponse(cbResponseError);
//            return ResponseEntity.ok()
//                    .body(cbResponse);
//        }
//
//        cbResponse = codeBlockService.create(cbRequest);
//
//        return ResponseEntity.ok()
//                .body(cbResponse);
//    }

    @CrossOrigin(origins = {"null", "https://instantfunction.com"})
    @PostMapping(path = "/formation/codeblock2", produces = "application/json")
    public ResponseEntity<CBResponse> postFormationController2(
            @RequestBody String cbRequestIn,
            @RequestHeader Map<String, String> headers) throws Exception {
        System.out.println("Received codeblock2 request");
        CBResponse cbResponse;
        String errorStatement = null;
        if (cbRequestIn == null) {
            errorStatement += "Invalid entry/n";
        }

        if (errorStatement != null) {
            CBResponseError cbResponseError = new CBResponseError(errorStatement);
            cbResponse = new CBResponse(cbResponseError);
            return ResponseEntity.ok()
                    .body(cbResponse);
        }


        CBCompiler compiler;
        try {
                compiler = CBCompiler.valueOf(headers.get("compiler"));
        } catch (Exception e) {
            throw new IllegalAccessException("Incorrect compiler " + e.toString());
        }

        CBRequest cbRequest = new CBRequest(cbRequestIn, "primaryKey","secondaryKey",compiler);

        cbResponse = codeBlockService.create(cbRequest);

        System.out.println("Returning response");
        return ResponseEntity.ok()
                .body(cbResponse);
    }

    // TODO: Erase everything below this line
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    ObjectMapper mapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();
    JSONObject jsonObject = new JSONObject();
    JsonNode root = null;
    HttpHeaders responseHeaders = new HttpHeaders();

    @GetMapping("/greeting")
    public ResponseEntity<Object> getController() {

        //String resourceURL = "https://raw.githubusercontent.com/yilmazerd/simplerest01/nextken-000/src/main/resources/template1.json";
        //String resourceURL = "https://tools.learningcontainer.com/sample-json.json";
        String resourceURL = "http://www.sci.utah.edu/~macleod/docs/txt2html/sample.txt";
        ResponseEntity<String> response  = restTemplate.getForEntity(resourceURL, String.class);
// Set the response for JSON BODY
        Object responseBody = getResponseBody(response, ResponseType.TEXT);

// Set Response headers
        responseHeaders.set(
                "Example-Header-Sample", "This-is-my-sample"
        );

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(responseBody);
    }



    @PostMapping("/greetPost")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> postController() {

        //String resourceURL = "https://raw.githubusercontent.com/yilmazerd/simplerest01/nextken-000/src/main/resources/template1.json";
        //String resourceURL = "https://tools.learningcontainer.com/sample-json.json";
        String resourceURL = "http://help.websiteos.com/websiteos/example_of_a_simple_html_page.htm";
        ResponseEntity<String> response  = restTemplate.getForEntity(resourceURL, String.class);
// Set the response for JSON BODY
        Object responseBody = getResponseBody(response, ResponseType.TEXT);

// Set Response headers
        responseHeaders.set(
                "Example-Header-Sample", "This-is-my-sample"
        );

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

}