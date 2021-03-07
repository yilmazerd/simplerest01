package com.nextken.rapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nextken.rapi.models.CodeRunResponse;
import com.nextken.rapi.models.RunRequest;
import com.nextken.rapi.service.RunService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
    private static ObjectMapper mapper = new ObjectMapper();

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

    private static final String DEFAULT_MEDIA_TYPE = "application/json";

    private static final String URL_HEADER = "urlheader";
    private static final String MEDIA_TYPE = "mediatype";
    private static final String RESPONSE_CONTENT = "responsecontent";
    private static final String DEFAULT_RESPONSE_CONTENT = "{}";
    private static int MAX_RESPONSE_DELAY = 35000;

    @RequestMapping(path = "/mock")
    public ResponseEntity<Object> reqController(@Nullable @RequestBody String request,
                                                 @RequestHeader Map<String, String> headers,
                                                 @RequestParam(value = RESPONSE_CODE_HEADER, required = false) String responseCode,
                                                 @RequestParam(value = DELAY_HEADER, required = false) String responseDelay,
                                                 @RequestParam(value = MEDIA_TYPE, required = false) String mediaType,
                                                 @RequestParam(value = RESPONSE_CONTENT, required = false) String responseCotent

    ) throws Exception{

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(RESPONSE_CODE_HEADER, responseCode);
        queryParams.put(DELAY_HEADER, responseDelay);
        queryParams.put(RESPONSE_CONTENT, responseCotent);
        queryParams.put(MEDIA_TYPE, mediaType);

        return getResponse(request, headers, queryParams);
    }

    /*
    Use https://www.browserling.com/tools/remove-all-whitespace for removing whitespace
     */
    private ResponseEntity<Object> getResponse(String request,
                                               Map<String, String> headers,
                                               Map<String, String> queryParams) {

        //Get response content
        String responseContent = ObjectUtils.firstNonNull(headers.get(RESPONSE_CONTENT),queryParams.get(RESPONSE_CONTENT),DEFAULT_RESPONSE_CONTENT);
        JsonNode jsonNode = mapper.createObjectNode();
        try {
            jsonNode = mapper.readValue(responseContent, JsonNode.class);
        } catch (Exception e) {
            jsonNode = null;
        }

        // Get response code
        String responseCode = ObjectUtils.firstNonNull(headers.get(RESPONSE_CODE_HEADER),queryParams.get(RESPONSE_CODE_HEADER),DEFAULT_RESPONSE);
        Integer responseCodeInt = Integer.valueOf(responseCode);

        // Get return type
        MediaType mediaType = MediaType.APPLICATION_JSON;
        String mediaTypeString = responseCode = ObjectUtils.firstNonNull(headers.get(MEDIA_TYPE),queryParams.get(MEDIA_TYPE),DEFAULT_MEDIA_TYPE);
        try {
            mediaType = MediaType.valueOf(mediaTypeString);
        } catch (Exception e) {}

        // Get delay
        String responseDelay = ObjectUtils.firstNonNull(headers.get(DELAY_HEADER),queryParams.get(DELAY_HEADER),DEFAULT_DELAY);
        Integer responseDelayInt = 0;
        try { responseDelayInt = Integer.valueOf(responseDelay); } catch (Exception e) { }
        if (responseDelayInt < 0 || responseDelayInt>MAX_RESPONSE_DELAY) {
            throw new IllegalArgumentException("Response delay can not be negative or more than max response delay of " + MAX_RESPONSE_DELAY );
        }
        if (responseDelayInt>0) {
            try {
                Thread.sleep(responseDelayInt);
            } catch (InterruptedException ie) { } }

        return ResponseEntity
                .status(responseCodeInt)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ObjectUtils.firstNonNull(jsonNode,responseContent,DEFAULT_RESPONSE_CONTENT));
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