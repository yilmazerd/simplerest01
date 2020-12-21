package com.nextken.rapi.service;

import com.nextken.rapi.models.*;
import com.nextken.rapi.repo.CodeBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class CodeBlockService {

    @Autowired
    CodeBlockRepository codeBlockRepository;

    @Autowired
    CodeBlockValidatorService codeBlockValidatorService;

    public CodeBlockService() {

    };

    public CBResponse create(CBRequest cbRequest){

        // Validation
        CBResponseError cbResponseError = codeBlockValidatorService.validate(cbRequest);
        if (cbResponseError.hasErrors()) {
            return new CBResponse(cbResponseError);
        }

        // Construction
        String code = cbRequest.getCode();
        CBCompiler cbCompiler = cbRequest.getCompiler();
        CodeBlock codeBlock = new CodeBlock(code, cbCompiler);

        // Repo
        codeBlockRepository.create(codeBlock);

        // Response
        return new CBResponse("null", new CBResponseError("none"));
    }
}
