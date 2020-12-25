package com.nextken.rapi.models;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class CodeBlock {

    private String code;
    private UUID codeBlockId;
    private CBCompiler compiler;
    private Instant timeStamp;

    public UUID getCodeBlockId() {
        return codeBlockId;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public CBCompiler getCompiler() {
        return compiler;
    }

    public String getCode(){
        return this.code;
    }

    public CodeBlock(String code, CBCompiler cbCompiler) {
        this.code = code;
        this.compiler = cbCompiler;
        this.timeStamp = Instant.now();
        this.codeBlockId = UUID.randomUUID();
    }

    @Deprecated
    public CodeBlock() {
        this.timeStamp = Instant.now();
        this.codeBlockId = UUID.randomUUID();
    }

//TODO: Add builder

}

/*
class CodeBlock {
  - String : code
  - UUID: codeBlockId
  - Compiler <Enum>: compiler
  - Instant: timeStamp
  -- Methods --
  + String getCustomerCode()
  + UUID getId()
  -- Builder --
  + withCode (String code) <Required>
  + withId (UUID id) <Optional>
  + withCompiler (Compiler c) <Required>
  + withTimeStamp (Instant i) <Optional>
}
 */