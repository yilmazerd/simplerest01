package com.nextken.rapi.models;

public class CBRequest {

    private String code;
    private String userPrimaryKey;
    private String userSecondaryKey;
    private CBCompiler compiler;

    public String getCode() {
        return code;
    }

    public String getUserPrimaryKey() {
        return userPrimaryKey;
    }

    public String getUserSecondaryKey() {
        return userSecondaryKey;
    }

    public CBCompiler getCompiler() {
        return compiler;
    }

    public CBRequest(String code, String userPrimaryKey, String userSecondaryKey, CBCompiler compiler) {
        this.code = code;
        this.userPrimaryKey = userPrimaryKey;
        this.userSecondaryKey = userSecondaryKey;
        this.compiler = compiler;
    }


}

/*
class CBRequest <POJO> {
  - String : code
  - String : userKey
  - String : userSecondary
  - String : Compiler
}

 */