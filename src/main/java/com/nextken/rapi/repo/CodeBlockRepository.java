package com.nextken.rapi.repo;

import com.nextken.rapi.models.CBCompiler;
import com.nextken.rapi.models.CodeBlock;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

@Repository
public class CodeBlockRepository {

    public void create(CodeBlock codeBlock){
        // do nothing for now;
        String fileName = codeBlock.getCodeBlockId().toString() + ".txt";
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

    public CodeBlock read(UUID codeBlockId){
        String code = "";
        String compiler;
        try {
            File myObj = new File(codeBlockId.toString() + ".txt");
            Scanner myReader = new Scanner(myObj);
            compiler = myReader.nextLine();
            while (myReader.hasNextLine()) {
                code += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("An error occurred. Exception was " + e.toString());
            e.printStackTrace();
        }

        // TODO: GET COMPILER FROM DATA
        return new CodeBlock(code, CBCompiler.JAVA11);
    }

}
