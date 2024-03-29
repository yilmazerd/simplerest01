package com.nextken.rapi.controller;

import com.nextken.rapi.service.CodeBlockService;
import com.nextken.rapi.service.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class RunController {


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

/*
    @PutMapping(path = "/runold/{id}", produces = "application/json")
    public ResponseEntity<Object> putController(
            @Nullable @RequestBody String requestedCode,
            @RequestParam (name = DELAY_HEADER, required = false) String delayHeader,
            @PathVariable("id") UUID codeId) throws Exception{

        return controllerProcess(codeId, "", delayHeader);
    }

    private ResponseEntity<Object> controllerProcess(UUID codeId, String requestedCode, String delayHeader){

        delayHeader = ObjectUtils.firstNonNull(delayHeader,"0");
        int delay = 0;
        try {
            delay = Integer.valueOf(delayHeader);
        } catch (NumberFormatException e) {
            System.out.println("Invalid delay");
        }


        if (delay>0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ie) {
                System.out.println("InterruptedException in Thread sleep");
            }
        }
        RunRequest runRequest = new RunRequest(codeId, requestedCode);

        CodeRunResponse codeRunResponse  = runService.run(runRequest);

        return ResponseEntity
                .status(codeRunResponse.getResponseCode())
                .body(codeRunResponse.getResponse());

    }
 */