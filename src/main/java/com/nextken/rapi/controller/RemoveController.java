package com.nextken.rapi.controller;

import com.nextken.rapi.models.CBCompiler;
import com.nextken.rapi.models.CBRequest;
import com.nextken.rapi.models.CBResponse;
import com.nextken.rapi.models.CBResponseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RemoveController {

    @CrossOrigin(origins = {"null", "http://localhost:63342", "https://instantfunction.com","localhost:63342"})
    @PostMapping(path = "/forwardss222", produces = "application/json")
    public ResponseEntity<Object> postFormationController2(
            @RequestBody String cbRequestIn,
            @RequestHeader Map<String, String> headers) throws Exception {

        CBResponse cbResponse = new CBResponse("hahaha");
        return ResponseEntity.ok()
                .body(cbResponse);

    }
}
