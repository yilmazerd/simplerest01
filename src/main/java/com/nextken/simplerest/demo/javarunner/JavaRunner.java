package com.nextken.simplerest.demo.javarunner;

import java.io.*;

public class JavaRunner {

    public static final String requestData = "3";

    public static void main(String[] args) throws IOException {
        // 1. Create a file
        try {
            File myObj = new File("filename.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // 2. Write request data to that file
        try {
            FileWriter myWriter = new FileWriter("src/main/java/com/nextken/simplerest/demo/javarunner/request.txt");
            myWriter.write(requestData);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // 3. Run a command line that runs 1 page java code, that code should read from a text file and output to a text file as well
        ProcessBuilder processBuilder = new ProcessBuilder();
        //processBuilder.command("bash", "-c", "java /Users/erdemyilmaz/Desktop/JAVAPROJECTS/simpleRest0/simplerest01/src/main/java/com/nextken/simplerest/demo/javarunner/HelloUniverse.java ");
        processBuilder.command("bash", "-c", "java src/main/java/com/nextken/simplerest/demo/javarunner/HelloUniverse.java ");
        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 3. Read the text file and return the result here
    }
}
