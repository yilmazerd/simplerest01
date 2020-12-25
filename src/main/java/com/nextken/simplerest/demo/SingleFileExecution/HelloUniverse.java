package com.nextken.simplerest.demo.SingleFileExecution;

public class HelloUniverse{
    public static void main(String[] args) {
        Person person = new Person("Thomas","Edison");
        InstantFunctionResponse.responseBuilder(200,person.toString());
    }
}

class Person {
    String firstName;
    String lastName;

    Person (String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "{\\r\\n\\t\\\"firstName\\\": \\\"" + firstName + "\\\",\\r\\n\\t\\\"lastName\\\": \\\"" + lastName + "\\\"\\r\\n}";
    }
}

class InstantFunctionResponse {
    public static void responseBuilder(int httpStatusCode, String responseObject) {
        System.out.println("httpStatusCode:"+httpStatusCode);
        System.out.println("responseObject:"+responseObject);
    }

    public static void responseBuilder(String responseObject) {
        responseBuilder(200, responseObject);
    }

}