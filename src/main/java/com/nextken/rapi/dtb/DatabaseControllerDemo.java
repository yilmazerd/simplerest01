package com.nextken.rapi.dtb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DatabaseControllerDemo {

    @Autowired
    DatabaseSampleService databaseSampleService;

    @PostMapping(path = "/database1", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> runController(@RequestBody String runRequest) throws Exception{

        databaseSampleService.createItems(runRequest);
        return ResponseEntity.ok()
                .body("test");
    }
}
