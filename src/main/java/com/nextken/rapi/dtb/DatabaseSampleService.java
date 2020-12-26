package com.nextken.rapi.dtb;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DatabaseSampleService {

    //https://contactsunny.medium.com/integrate-aws-dynamodb-with-spring-boot-687cfaabfaa0

    private AmazonDynamoDB client;
    private DynamoDB dynamoDB;
    private static String tableName = "ProductCatalog";

    public DatabaseSampleService() {
        client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        dynamoDB = new DynamoDB(client);
    }
//
//    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
//    static DynamoDB dynamoDB = new DynamoDB(client);
//    static String tableName = "ProductCatalog";

    public void createItems(String items) {

        Random rand = new Random(); //instance of random class
        int upperbound = 999999;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);

        Table table = dynamoDB.getTable(tableName);
        try {

            Item item = new Item()
                    .withPrimaryKey("Id", int_random)
                    .withString("request", items);
            table.putItem(item);


        }
        catch (Exception e) {
            System.err.println("Create items failed.");
            System.err.println(e.getMessage());

        }
    }
}
