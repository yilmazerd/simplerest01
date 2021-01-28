package com.nextken.rapi.repo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.nextken.rapi.models.CBCompiler;
import com.nextken.rapi.models.CodeBlock;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

@Repository
public class CodeBlockRepository {

    //TODO: Return no error if the error node is null


    private AmazonDynamoDB client;
    private DynamoDB dynamoDB;
    private static String tableName = "codeblocks";

    public CodeBlockRepository() {
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        dynamoDB = new DynamoDB(client);
    }

    public void create(CodeBlock codeBlock){

        Table table = dynamoDB.getTable(tableName);
        try {

            Item item = new Item()
                    .withPrimaryKey("codeBlockId", codeBlock.getCodeBlockId().toString())
                    .withString("code", codeBlock.getCode())
                    .withString("compiler",codeBlock.getCompiler().toString())
                    .withString("timeStamp",codeBlock.getTimeStamp().toString());
            table.putItem(item);

        }
        catch (Exception e) {
            System.err.println("Create items failed.");
            System.err.println(e.getMessage());

        }
    }

    public CodeBlock read(UUID codeBlockId){
        Table table = dynamoDB.getTable(tableName);
        CodeBlock codeBlock = new CodeBlock();

        try {

            Item item = table.getItem("codeBlockId", codeBlockId.toString());
            String code = item.getString("code");
            String compiler = item.getString("compiler");
            String timeStamp = item.getString("timeStamp");

            System.out.println("Printing item after retrieving it....");
            System.out.println(item.toJSONPretty());

            codeBlock = new CodeBlock(code, CBCompiler.JAVA11, Instant.parse(timeStamp),codeBlockId);

        }
        catch (Exception e) {
            System.err.println("GetItem failed.");
            System.err.println(e.getMessage());
        }

        return codeBlock;
    }

    public void createOld(CodeBlock codeBlock){
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
        FileWriter myWriter = null;

        try {
            myWriter = new FileWriter(fileName);
            myWriter.write(codeBlock.getCode());

            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } finally {
            if (myWriter != null ) {
                try {
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred closing file " + e.toString());
                }
            }
        }
    }

    public CodeBlock readOld(UUID codeBlockId){
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
