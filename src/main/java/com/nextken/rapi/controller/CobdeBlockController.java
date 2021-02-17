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

    @CrossOrigin(origins = {"null", "http://localhost:63342", "https://instantfunction.com","localhost:63342"})
    @PostMapping(path = "/formation/codeblock2", produces = "application/json")
    public ResponseEntity<CBResponse> postFormationController2(
            @RequestBody String cbRequestIn,
            @RequestHeader Map<String, String> headers) throws Exception {
        System.out.println("Received codeblock2 request");
        headers.keySet().forEach(k-> {
            System.out.println("key: " + k + " " + headers.get(k));
        });
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
            throw new IllegalArgumentException("Incorrect compiler " + e.toString());
        }

        CBRequest cbRequest = new CBRequest(cbRequestIn, "primaryKey","secondaryKey",compiler);

        cbResponse = codeBlockService.create(cbRequest);

        System.out.println("Returning response");
        return ResponseEntity.ok()
                .body(cbResponse);
    }
}