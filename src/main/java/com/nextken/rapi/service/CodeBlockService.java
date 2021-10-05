package com.nextken.rapi.service;

import com.nextken.rapi.models.*;
import com.nextken.rapi.repo.CodeBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CodeBlockService {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

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
        try {
            codeBlockRepository.create(codeBlock);
            //LOGGER.log(Level.INFO, "Created codeBlock : " + codeBlock.getCode() + ", with Id: " + codeBlock.getCodeBlockId());
            LOGGER.log(Level.INFO, "Created codeBlock with Id: " + codeBlock.getCodeBlockId());
        } catch (Exception e) {
            return new CBResponse(e.toString());
        }

        // Response
        return new CBResponse(codeBlock.getCodeBlockId().toString());
    }

    /*
    Read CodeBlock from repository
     */
    public CodeBlock read(UUID codeBlockId) {
        // Repo
        return codeBlockRepository.read(codeBlockId);
    }
}
