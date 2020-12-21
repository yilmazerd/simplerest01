package com.nextken.rapi.models;

public class CBResponse {
    private String url;
    private CBResponseError error;

    public String getUrl() {
        return url;
    }

    public CBResponseError getError() {
        return error;
    }

    public CBResponse(String url, CBResponseError cbResponseError) {
        this.url = url;
        this.error = cbResponseError;
    }

    public CBResponse(String url) {
        this.url = url;
    }

    public CBResponse(CBResponseError error) {
        this.error = error;
    }

    @Deprecated
    public CBResponse(String url, String errorMessage) {
        this.url = url;
        this.error = new CBResponseError(errorMessage);
    }



}

/*
class CBResponse <POJO> {
  - String : url
  - CBResponseError : cbResponseError
}
 */