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

import java.time.Instant;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class CodeBlockRepository {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private AmazonDynamoDB client;
    private DynamoDB dynamoDB;
    private static String tableName = "codeblocks";
    private static final String CODEBLOCKID = "codeBlockId";
    private static final String CODE = "code";
    private static final String COMPILER = "compiler";
    private static final String TIMESTAMP = "timeStamp";

    public CodeBlockRepository() {
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        dynamoDB = new DynamoDB(client);
    }

    public void create(CodeBlock codeBlock){

        Table table = dynamoDB.getTable(tableName);
        try {

            Item item = new Item()
                    .withPrimaryKey(CODEBLOCKID, codeBlock.getCodeBlockId().toString())
                    .withString(CODE, codeBlock.getCode())
                    .withString(COMPILER,codeBlock.getCompiler().toString())
                    .withString(TIMESTAMP,codeBlock.getTimeStamp().toString());
            table.putItem(item);


        }
        catch (Exception e) {

            LOGGER.log(Level.SEVERE, "Create items failed");
            LOGGER.log(Level.INFO, e.getMessage());

        }
    }

    public CodeBlock read(UUID codeBlockId){
        Table table = dynamoDB.getTable(tableName);
        CodeBlock codeBlock = new CodeBlock();

        try {

            Item item = table.getItem(CODEBLOCKID, codeBlockId.toString());
            String code = item.getString(CODE);
            String compiler = item.getString(COMPILER);
            String timeStamp = item.getString(TIMESTAMP);

            codeBlock = new CodeBlock(code, CBCompiler.valueOf(compiler), Instant.parse(timeStamp),codeBlockId);

        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "GetItem failed");
            LOGGER.log(Level.INFO, e.getMessage());
        }

        return codeBlock;
    }


}
