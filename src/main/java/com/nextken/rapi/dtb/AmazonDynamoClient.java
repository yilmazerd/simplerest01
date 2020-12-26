package com.nextken.rapi.dtb;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DatabaseSampleService.class)
class AmazonDynamoClient {
    @Bean
    //static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    public AmazonDynamoDB getDynamodB(){
     return AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }
}
