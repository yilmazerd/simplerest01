package com.nextken.rapi.service;

import com.nextken.rapi.models.*;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

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
        // 1.2 Add users request (if any) to the codeblock
        codeBlock = codeBlock.addUsersCode(runRequest.getCodeString());
        // 2. Out of raw code, create the executable code
        createExecutableCode(codeBlock);
        // 3. Depening on the compiler, execute the new code
        String logs = executeCode(codeBlock);
        // 4. Collect the results and return
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        // TODO: throw an error if the client is not logging status code

        CodeRunResponse codeRunResponse = new CodeRunResponse();

        // TODO: Convert to a method
        // Find status code
        int statusCodeInt;

        try {
            int statusCodePointer = logs.indexOf("statusCode");
            String statusCode = logs.substring(statusCodePointer+11,statusCodePointer+14);
            statusCodeInt = Integer.parseInt(statusCode);
            logs = logs.substring(0,statusCodePointer) + logs.substring(statusCodePointer+15, logs.length());
        } catch (Exception e) {
            statusCodeInt = 200;
        }

        //FIXME: CodeRunResponse should have a constructor that works with the String ClientCode and those needs to be methods in that object
        codeRunResponse.setResponseCode(statusCodeInt);

        // TODO: Throw error if logs are not long enough or if the don't have the log statement for response

        try {
            String newLogs = logs.substring(logs.indexOf("responseObject")+15);
            json = (JSONObject) parser.parse(newLogs);
            codeRunResponse.setResponse(json);
        } catch (Exception e) {
            codeRunResponse.setResponse(logs);
        }

        return codeRunResponse;
    }

    private void createExecutableCode(CodeBlock codeBlock){
        String extension = getExtension(codeBlock);

        String fileName = codeBlock.getCodeBlockId().toString() + "." +  extension;
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

        FileWriter myWriter = null;

        try {
            myWriter = new FileWriter(fileName);
            myWriter.write(codeBlock.getCode());
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            if (myWriter != null) {
                try { myWriter.close(); }
                catch (IOException e) {
                    System.out.println("Issue closing the file");
                }
            }
        }
    }

    private String executeCode(CodeBlock codeBlock) {

        String fileName = codeBlock.getCodeBlockId().toString() + "." + getExtension(codeBlock);
        String executor = getExecutor(codeBlock);

        ProcessBuilder processBuilder = new ProcessBuilder();
        if (executor == "java") {
            processBuilder.command("bash", "-c", executor + " " + fileName );
        }
         else {
            processBuilder.command("bash", "-c", executor + " " + fileName );
        }

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

            processBuilder.command("bash", "-c", "rm " +fileName + " ");
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

    private String getExtension(CodeBlock codeBlock) {
        String extension = null;

        switch (codeBlock.getCompiler()) {
            case JAVA9:
            case JAVA11:
                extension = "java";
                break;

            case PYTHON37:
                extension = "py";
                break;

        }

        return extension;
    }

    private String getExecutor(CodeBlock codeBlock) {
        String executor = null;

        switch (codeBlock.getCompiler()) {
            case JAVA9:
            case JAVA11:
                executor = "java";
                break;

            case PYTHON37:
                executor = "python";
                break;

        }

        return executor;
    }
}
