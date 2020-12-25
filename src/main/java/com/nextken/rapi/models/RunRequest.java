package com.nextken.rapi.models;
import java.util.UUID;

public class RunRequest {

    private Object runRequestBlock;
    private UUID codeBlockId;

    public UUID getCodeBlockId() {
        return codeBlockId;
    }

    public Object getRunRequestBlock() {
        return runRequestBlock;
    }

    RunRequest(UUID codeBlockId, Object runRequestBlock) {
        this.codeBlockId = codeBlockId;
        this.runRequestBlock = runRequestBlock;
    }


}
