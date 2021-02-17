package com.nextken.rapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextken.rapi.models.CodeRunResponse;
import com.nextken.rapi.models.RunRequest;
import com.nextken.rapi.service.RunService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class MockController {

    private static final int PAGE_SIZE = 3000;
    private static final String DEFAULT_RESPONSE = "200";
    private static final String DEFAULT_DELAY = "0";

    /*
    This is the response code of the response, such as 200, 502
    responsecode is an allowed header
     */
    private static final String RESPONSE_CODE_HEADER = "responsecode";

    /*
    Delay in millisecond
    responsedelay is an allowed header
     */
    private static final String DELAY_HEADER = "responsedelay";

    private static final String URL_HEADER = "urlheader";
    private static final String CONTENT_TYPE = "contenttype";
    private static final String RESPONSE_CONTENT = "responsecontent";
    private static int MAX_RESPONSE_DELAY = 35000;

    @PostMapping(path = "/mock")
    public ResponseEntity<Object> postController(@RequestBody String request,
                                                 @RequestHeader Map<String, String> headers,
                                                 @RequestParam(value = RESPONSE_CODE_HEADER, required = false) String responseCode,
                                                 @RequestParam(value = DELAY_HEADER, required = false) String responseDelay
                                                 ) throws Exception{

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(RESPONSE_CODE_HEADER, responseCode);
        queryParams.put(RESPONSE_CODE_HEADER, responseDelay);

        return getResponse(request, headers, queryParams);
    }

    private ResponseEntity<Object> getResponse(String request,
                                               Map<String, String> headers,
                                               Map<String, String> queryParams) {

        // Get response code
        //String resourceURL = ObjectUtils.firstNonNull(headers.get(URL_HEADER),request.getParameter(URL_HEADER));
        //String contentType = ObjectUtils.firstNonNull(request.getHeader(CONTENT_TYPE),request.getParameter(CONTENT_TYPE));
        //String responseFromUrl = null;

        // Get response code
        String responseCode = ObjectUtils.firstNonNull(headers.get(RESPONSE_CODE_HEADER),queryParams.get(RESPONSE_CODE_HEADER),DEFAULT_RESPONSE);
        Integer responseCodeInt = Integer.valueOf(responseCode);

        // Get delay
        String responseDelay = ObjectUtils.firstNonNull(headers.get(DELAY_HEADER),queryParams.get(DELAY_HEADER),DEFAULT_DELAY);
        Integer responseDelayInt = 0;
        try { responseDelayInt = Integer.valueOf(responseDelay); } catch (Exception e) { }
        if (responseDelayInt>0) {
            try {
                Thread.sleep(responseDelayInt);
            } catch (InterruptedException ie) { } }

        return ResponseEntity
                .status(responseCodeInt)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request);
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