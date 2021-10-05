package com.nextken.rapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextken.rapi.models.CodeBlock;
import com.nextken.rapi.models.CodeRunResponse;
import com.nextken.rapi.models.KTRunnerRequest;
import com.nextken.rapi.models.RunRequest;
import com.nextken.rapi.service.CodeBlockService;
import com.nextken.rapi.service.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@RestController
public class TempForwardController {


    @Autowired
    RunService runService;
    @Autowired
    CodeBlockService codeBlockService;

    private static final String DELAY_HEADER = "responsedelay";

    // All controllers return the same
    @RequestMapping(path = "/v1/run/{id}", produces = "application/json")
    public ResponseEntity<Object> postController(
            @Nullable @RequestBody String requestedCode,
            @RequestParam(name = DELAY_HEADER, required = false) String delayHeader,
            @PathVariable("id") UUID codeId) throws Exception{

        return runService.runKT(codeId, requestedCode);

    }
}
