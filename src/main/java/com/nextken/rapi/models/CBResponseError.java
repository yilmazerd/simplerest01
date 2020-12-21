package com.nextken.rapi.models;

public class CBResponseError {
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
