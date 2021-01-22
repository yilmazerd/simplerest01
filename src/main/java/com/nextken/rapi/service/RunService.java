package com.nextken.rapi.service;

import com.nextken.rapi.models.*;
import com.nextken.rapi.repo.CodeBlockRepository;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Service
public class RunService {

    //TODO: Be able to take request command
    //TODO: Be able to return status code as well as response
    //TODO: Limit runtime to 1 second

    @Autowired
    CodeBlockService codeBlockService;

    public RunService() { };

    public CodeRunResponse run(RunRequest runRequest){

        // 1. Read raw code from repository
        CodeBlock codeBlock = codeBlockService.read(runRequest.getCodeBlockId());
        // 2. Out of raw code, create the executable code
        createExecutableCode(codeBlock);
        // 3. Depening on the compiler, execute the new code
        String logs = executeCode(codeBlock.getCodeBlockId(), runRequest.getCodeString());
        // 4. Collect the results and return
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        // TODO: throw an error if the client is not logging status code

        CodeRunResponse codeRunResponse = new CodeRunResponse();

        // TODO: Convert to a method
        // Find status code
        int statusCodeInt = 200;

        try {
            int statusCodePointer = logs.indexOf("statusCode");
            String statusCode = logs.substring(statusCodePointer+15,statusCodePointer+18);
            statusCodeInt = Integer.parseInt(statusCode);
        } catch (Exception e) {
            statusCodeInt = 200;
        }

        codeRunResponse.setResponseCode(statusCodeInt);

        // TODO: Throw error if logs are not long enough or if the don't have the log statement for response

        try {
            logs = logs.substring(logs.indexOf("responseObject")+15);
            json = (JSONObject) parser.parse(logs);
            codeRunResponse.setResponse(json);
        } catch (Exception e) {
            codeRunResponse.setResponse(logs);
        }

        return codeRunResponse;
    }

    private void createExecutableCode(CodeBlock codeBlock){
        String fileName = codeBlock.getCodeBlockId().toString() + ".java";
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(codeBlock.getCode());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private String executeCode(UUID fileName, String runRequestBody) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "java " +fileName + ".java " + runRequestBody);
        StringBuilder output = new StringBuilder();
        try {

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();

            processBuilder.command("bash", "-c", "rm " +fileName + ".java ");
            processBuilder.start();

            if (exitVal == 0 ) {
                System.out.println("Success!");
                System.out.println(output);
                //System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
