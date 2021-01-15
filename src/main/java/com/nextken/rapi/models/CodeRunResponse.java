package com.nextken.rapi.models;

public class CodeRunResponse {
    Object response;

    public CodeRunResponse(Object response, int responseCode) {
        this.response = response;
        this.responseCode = responseCode;
    }

    public CodeRunResponse() {
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Object getResponse() {
        return response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    int responseCode;

}
