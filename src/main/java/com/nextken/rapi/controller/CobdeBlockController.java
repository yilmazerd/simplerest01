package com.nextken.rapi.controller;

import com.nextken.rapi.models.CBCompiler;
import com.nextken.rapi.models.CBRequest;
import com.nextken.rapi.models.CBResponse;
import com.nextken.rapi.models.CBResponseError;
import com.nextken.rapi.service.CodeBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@RestController
public class CobdeBlockController {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private static final int INPUT_CODE_CHAR_LIMIT = 10000;

    @Autowired
    CodeBlockService codeBlockService;

    @GetMapping({"/","/hello"})
    public String helloWorld(@RequestParam(required = false, defaultValue = "World") String name) {
        return "hello-world";
    }


    //@CrossOrigin(origins = {"null", "http://localhost:63342", "https://instantfunction.com","localhost:63342"})
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/code", produces = "application/json")
    public ResponseEntity<CBResponse> codeController(
            @RequestBody String cbRequestIn,
            @RequestHeader Map<String, String> headers) throws Exception {

        return codeControllerResponse(cbRequestIn, headers);
    }

    @Deprecated
    //@CrossOrigin(origins = {"null", "http://localhost:63342", "https://instantfunction.com","localhost:63342"})
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/formation/codeblock2", produces = "application/json")
    public ResponseEntity<CBResponse> postFormationController2(
            @RequestBody String cbRequestIn,
            @RequestHeader Map<String, String> headers) throws Exception {

        return codeControllerResponse(cbRequestIn, headers);

    }


    public ResponseEntity<CBResponse> codeControllerResponse(
            @RequestBody String cbRequestIn,
            @RequestHeader Map<String, String> headers) throws Exception {


        if ( cbRequestIn.length() > INPUT_CODE_CHAR_LIMIT) {
            throw new IllegalArgumentException("Input can not exceed " + INPUT_CODE_CHAR_LIMIT + " characters");
        }
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
        if (headers.get("compiler") == null)
            compiler = CBCompiler.python_3_9;
        else {
            try {
                compiler = CBCompiler.valueOf(headers.get("compiler"));
            } catch (Exception e) {
                compiler = CBCompiler.python_3_9;
            }
        }

        CBRequest cbRequest = new CBRequest(cbRequestIn, "primaryKey","secondaryKey",compiler);

        cbResponse = codeBlockService.create(cbRequest);

        return ResponseEntity.ok()
                .body(cbResponse);
    }
}