package com.nextken.rapi.models;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class CodeBlock {

    private String code;
    private UUID codeBlockId;
    private CBCompiler compiler;
    private Instant timeStamp;
    private final String INSTANT_FUNCTION_STRING_ARRANGER = "##INSTANTFUNCTION##";

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

    public CodeBlock(String code, CBCompiler cbCompiler, Instant timestamp, UUID codeBlockId) {
        this.code = code;
        this.compiler = cbCompiler;
        this.timeStamp = timestamp;
        this.codeBlockId = codeBlockId;
    }

    @Deprecated
    public CodeBlock() {
        this.timeStamp = Instant.now();
        this.codeBlockId = UUID.randomUUID();
    }

    public CodeBlock addUsersCode(String usersCode) {
        String newCode = this.getCode();
        if (StringUtils.isNotBlank(usersCode)) {
            //String usersCodeCleaned = cleanCode(usersCode);
            newCode = this.getCode().replace(INSTANT_FUNCTION_STRING_ARRANGER, usersCode);
        };
        //CodeBlock(String code, CBCompiler cbCompiler, Instant timestamp, UUID codeBlockId)
        return new CodeBlock(newCode, this.getCompiler(), this.getTimeStamp(), this.getCodeBlockId());
    }

    private String cleanCode(String str) {
        str = str.replace("\"", "\\\"");
        str = str.replaceAll("[\\t\\n\\r]+"," ");
        return str;
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