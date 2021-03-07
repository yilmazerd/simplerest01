package com.nextken.rapi.models;
import java.util.UUID;

public class RunRequest {

    private Object runRequestBlock;
    private UUID codeBlockId;
    private String codeString;

    public UUID getCodeBlockId() {
        return codeBlockId;
    }

    public Object getRunRequestBlock() {
        return runRequestBlock;
    }

    public String getCodeString() { return codeString;}

    public RunRequest(UUID codeBlockId, String codeString) {
        this.codeBlockId = codeBlockId;
        this.codeString = codeString;
    }


}
