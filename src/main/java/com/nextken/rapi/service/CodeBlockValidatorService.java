package com.nextken.rapi.service;

import com.nextken.rapi.models.CBRequest;
import com.nextken.rapi.models.CBResponse;
import com.nextken.rapi.models.CBResponseError;
import org.springframework.stereotype.Service;

@Service
public class CodeBlockValidatorService {

    public CodeBlockValidatorService() {

    };

    public CBResponseError validate(CBRequest cbRequest){
        //return new CodeBlock();
        return new CBResponseError(null);
    }
}
