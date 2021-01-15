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

@RestController
public class RunController {

    @Autowired
    RunService runService;

    @PostMapping(path = "/run", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> runController(@RequestBody RunRequest runRequest) throws Exception{

        //Object codeRunResult = runService.run(runRequest);
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