package com.nextken.simplerest.demo.javarunner;

import java.io.File;
import java.io.IOException;

public class HelloUniverse {
    public static void main(String[] args) {
        // 1. Read the request

        // 2. Pass the request into the custom java module, get the response
        File requestObject;


        // 3. save the response to a file
        File myObj; // response object
        try {
            myObj = new File("src/main/java/com/nextken/simplerest/demo/javarunner/ssample12312.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}