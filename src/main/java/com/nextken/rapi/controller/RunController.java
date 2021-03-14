package com.nextken.rapi.controller;

import com.nextken.rapi.models.*;
import com.nextken.rapi.service.RunService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class RunController {

    @Autowired
    RunService runService;

    private static final String DELAY_HEADER = "responsedelay";
    private static final String RESPONSE_CODE_HEADER = "responsecode";

    // All controllers return the same

    @PostMapping(path = "/run/{id}", produces = "application/json")
    public ResponseEntity<Object> postController(
            @Nullable @RequestBody String requestedCode,
            @RequestParam (name = DELAY_HEADER, required = false) String delayHeader,
            @PathVariable("id") UUID codeId) throws Exception{

        return controllerProcess(codeId, requestedCode, delayHeader);
    }

    @PatchMapping(path = "/run/{id}", produces = "application/json")
    public ResponseEntity<Object> patchController(
            @Nullable @RequestBody String requestedCode,
            @RequestParam (name = DELAY_HEADER, required = false) String delayHeader,
            @PathVariable("id") UUID codeId) throws Exception{

        return controllerProcess(codeId, requestedCode, delayHeader);
    }

    @GetMapping(path = "/run/{id}", produces = "application/json")
    public ResponseEntity<Object> getController(
            @PathVariable("id") UUID codeId,
            @RequestParam (name = DELAY_HEADER, required = false) String delayHeader
            ) throws Exception{

        return controllerProcess(codeId, "", delayHeader);
    }

    @DeleteMapping(path = "/run/{id}", produces = "application/json")
    public ResponseEntity<Object> deleteController(
            @Nullable @RequestBody String requestedCode,
            @RequestParam (name = DELAY_HEADER, required = false) String delayHeader,
            @PathVariable("id") UUID codeId) throws Exception{

        return controllerProcess(codeId, "", delayHeader);
    }

    @PutMapping(path = "/run/{id}", produces = "application/json")
    public ResponseEntity<Object> putController(
            @Nullable @RequestBody String requestedCode,
            @RequestParam (name = DELAY_HEADER, required = false) String delayHeader,
            @PathVariable("id") UUID codeId) throws Exception{

        return controllerProcess(codeId, "", delayHeader);
    }

    private ResponseEntity<Object> controllerProcess(UUID codeId, String requestedCode, String delayHeader){

        delayHeader = ObjectUtils.firstNonNull(delayHeader,"0");
        int delay = Integer.valueOf(delayHeader);

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

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}