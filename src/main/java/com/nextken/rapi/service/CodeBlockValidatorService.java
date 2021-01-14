package com.nextken.rapi.service;

import com.amazonaws.services.dynamodbv2.model.DeleteGlobalSecondaryIndexAction;
import com.nextken.rapi.models.CBRequest;
import com.nextken.rapi.models.CBResponse;
import com.nextken.rapi.models.CBResponseError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CodeBlockValidatorService {

    public CodeBlockValidatorService() {

    };

    public CBResponseError validate(CBRequest cbRequest){
        //return new CodeBlock();
        String errorContext = "";
        // if codeBlock is empty there will be an error;
        if (StringUtils.isBlank(cbRequest.getCode())) {
            errorContext += "Code can not be empty;  ";
        }
        if (cbRequest.getCompiler()==null) {
            errorContext += "Compiler can not be empty/n";
        }
        if (StringUtils.isBlank(errorContext)) {
            return new CBResponseError(null);
        }
        return new CBResponseError(errorContext);
    }
}
