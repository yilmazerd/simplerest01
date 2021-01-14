package com.nextken.rapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class CBResponseError {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorContext;

    public CBResponseError(String errorContext){
        this.errorContext = errorContext;
    }

    public boolean hasErrors(){
        return this.errorContext != null;
    }

    public String getErrorContext(){
        return this.errorContext;
    }
}
