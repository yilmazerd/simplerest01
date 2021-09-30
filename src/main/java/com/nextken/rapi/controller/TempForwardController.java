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
import java.util.logging.Level;

@RestController
public class TempForwardController {


    @Autowired
    RunService runService;
    @Autowired
    CodeBlockService codeBlockService;

    private static final String DELAY_HEADER = "responsedelay";

    // All controllers return the same
    @PostMapping(path = "/fw/{id}", produces = "application/json")
    public ResponseEntity<Object> postController(
            @Nullable @RequestBody String requestedCode,
            @RequestParam(name = DELAY_HEADER, required = false) String delayHeader,
            @PathVariable("id") UUID codeId) throws Exception{

        RunRequest runRequest = new RunRequest(codeId, requestedCode);

        CodeRunResponse codeRunResponse  = runService.run(runRequest);

        CodeBlock codeBlock = codeBlockService.read(runRequest.getCodeBlockId());

        String codeBulk = codeBlock.getCode();

        /*

         */


        KTRunnerRequest ktRunnerRequest = new KTRunnerRequest(codeBulk);
        JsonNode jsonNode = ktRunnerRequest.getJson();
        RestTemplate restTemplate = new RestTemplate();

        URI uri = new URI("https://a4m4a452gc.execute-api.us-east-1.amazonaws.com/QA");


        //HttpEntity<JsonNode> requestEntity = new HttpEntity<JsonNode>(null, jsonNode);
        HttpEntity<JsonNode> entity = new HttpEntity<>(jsonNode);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        String s2 = result.getBody().toString();
        String s2_3 = s2.substring(1,s2.length()-1).replace("\\","");

        try {
            // edit response

            JsonNode jsonNodeResponse = objectMapper.readTree(s2_3);

            return ResponseEntity
                    .status(200)
                    .body(jsonNodeResponse);

            //return newNode;
        } catch (Exception e) {
            return ResponseEntity
                    .status(200)
                    .body(s2_3);
        }

        /*

         */


    }
}
