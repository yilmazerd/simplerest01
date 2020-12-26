package com.nextken.rapi.service;

import com.nextken.rapi.models.*;
import com.nextken.rapi.repo.CodeBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Service
public class RunService {

    //TODO: Be able to take request command
    //TODO: Be able to return status code as well as response

    @Autowired
    CodeBlockService codeBlockService;

    public RunService() { };

    public Object run(RunRequest runRequest){

        // 1. Read raw code from repository
        CodeBlock codeBlock = codeBlockService.read(runRequest.getCodeBlockId());
        // 2. Out of raw code, create the executable code
        createExecutableCode(codeBlock);
        // 3. Depening on the compiler, execute the new code
        String logs = executeCode(codeBlock.getCodeBlockId());
        // 4. Collect the results and return
        return logs;
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

    private String executeCode(UUID fileName) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "java " +fileName + ".java ");
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
