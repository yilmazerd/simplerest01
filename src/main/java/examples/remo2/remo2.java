package examples.remo2;

/*
This example can process a JSON input that looks like this
{
		"data1":"I should be able to put anything here",
		"data2":"Example data"
}
it can then process data1 as the user wishes
 */
public class remo2{
    public static void main(String[] args) {
        Person person = new Person("Thomas21","Edison");
        String allArgs = "";
        for (int i = 0; i<args.length; i++) {
            allArgs+=args[i];
        }

        int ssint1 = allArgs.indexOf("data1:");
        int ssint2 = allArgs.indexOf("data2:");
        String all2 = allArgs.substring(ssint1+6,ssint2);

        person = new Person("Thomas",(String) "\"" + all2 + "\"");
        InstantFunctionResponse.responseBuilder(502,person.toString());
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
