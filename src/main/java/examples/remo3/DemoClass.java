package examples.remo3;
/*
Example
Returns a very simple JSON structure and 502 HTTP code
Follow this example. Do not log or stdout, if you'd like to use the custom http code and json.
*/
public class DemoClass{
    public static void main(String[] args) {
        Person person = new Person("Thomas21","Edison");
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
