package examples;

/*
Example class, use https://www.freeformatter.com/java-dotnet-escape.html#ad-output for converting Java to string
 */
public class remo{
    public static void main(String[] args) {
        Person person = new Person("Thomas21","Edison");
        System.out.println(args);
        String allArgs = "";
        for (int i = 0; i<args.length; i++) {
            allArgs+=args[i];
        }
        InstantFunctionResponse.responseBuilder(502,person.toString() + allArgs + args.length);
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
        //return "{\\r\\n\\t\\\"firstName\\\": \\\"" + firstName + "\\\",\\r\\n\\t\\\"lastName\\\": \\\"" + lastName + "\\\"\\r\\n}";
        return "{ \"firstName\" : "+firstName+", \"lastName\" : "+lastName+" }";
    }
}

class InstantFunctionResponse {
    public static void responseBuilder(int httpStatusCode, String responseObject) {
        System.out.println("httpStatusCode:"+httpStatusCode);
        System.out.println("responseObject:"+responseObject);
    }

    public static void responseBuilder(String responseObject) {
        responseBuilder(502, responseObject);
    }

}
