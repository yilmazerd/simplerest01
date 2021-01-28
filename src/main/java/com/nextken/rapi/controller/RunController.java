package com.nextken.rapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextken.rapi.models.*;
import com.nextken.rapi.service.CodeBlockService;
import com.nextken.rapi.service.RunService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class RunController {

    @Autowired
    RunService runService;

    // All controllers return the same

    @PostMapping(path = "/run/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> postController(@RequestBody JsonNode requestedCode, @PathVariable("id") UUID codeId) throws Exception{

        return controllerProcess(codeId, requestedCode);
    }

    @PatchMapping(path = "/run/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> patchController(@RequestBody JsonNode requestedCode, @PathVariable("id") UUID codeId) throws Exception{

        return controllerProcess(codeId, requestedCode);
    }

    @GetMapping(path = "/run/{id}", produces = "application/json")
    public ResponseEntity<Object> getController(@PathVariable("id") UUID codeId) throws Exception{

        String json = "{}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        return controllerProcess(codeId, jsonNode);
    }

    @DeleteMapping(path = "/run/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> deleteController(@RequestBody JsonNode requestedCode, @PathVariable("id") UUID codeId) throws Exception{

        return controllerProcess(codeId, requestedCode);
    }

    @PutMapping(path = "/run/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> putController(@RequestBody JsonNode requestedCode, @PathVariable("id") UUID codeId) throws Exception{

        return controllerProcess(codeId, requestedCode);
    }

    private ResponseEntity<Object> controllerProcess(UUID codeId, JsonNode requestedCode){

        RunRequest runRequest = new RunRequest(codeId, requestedCode.toString());

        CodeRunResponse codeRunResponse  = runService.run(runRequest);

        return ResponseEntity.status(codeRunResponse.getResponseCode())
                .body(codeRunResponse.getResponse());

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