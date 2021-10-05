package com.nextken.rapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CBResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String codeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CBResponseError error;

    public String getCodeId() {
        return codeId;
    }

    public CBResponseError getError() {
        return error;
    }

    public CBResponse(String codeId, CBResponseError cbResponseError) {
        this.codeId = codeId;
        this.error = cbResponseError;
    }

    public CBResponse(String codeId) {
        this.codeId = codeId;
    }

    public CBResponse(CBResponseError error) {
        this.error = error;
    }

    @Deprecated
    public CBResponse(String codeId, String errorMessage) {
        this.codeId = codeId;
        this.error = new CBResponseError(errorMessage);
    }



}

/*
class CBResponse <POJO> {
  - String : url
  - CBResponseError : cbResponseError
}
 */