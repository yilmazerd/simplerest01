# simplerest01
UI
1 - Get familiar with githost.io pages. No need to deal with aws cloud front

API
-- Limit the time a function can execture to 1 second
- Use AWS Lambda for executing java code
-- Add mock endpoint and explanation

SMP-003, Lambda code can run parameter (DONE)

SMP-004, Clear controllers (DONE)

SMP-005, Limit users request to 300 characters and ignore rest because the command line is argument passing is limited (DONE)

SMP-006, Make all the verbs work (GET, PATCH, DELETE, PUT) (DONE)

JXA4, Amazon reported bug fixes and GET works on the browser 

SMP-007, Investigate how to install python into the environment

SMP-007, Run Python

SMP-008, Add mock endpoint

SMP-009, Fix Mock Endpoint in prod (fix some loging as well)

SMP-010, Client can enter any string and in the fucntion there is a String variable that the client can change

SMP-011, User adds delay and response code as a query parameter

SMP-011-a, User adds delay as query parameter but response code stays in the code

SMP-012, Create examples. Simple Java examples to mock response and simply python examples to mock response

SMP-013, Develop a new controller that takes text and posts to py server as json

SMP-014, Use the code in the repo and call the lambda with that code

SMP-015, Refactor the code, add tests, use the runner service to use aws lambda

SMP-015a, When the code is invalid it returns
File "/tmp/demofile2.py", line 1n    for (int i = 0; i< 10; ++) {n             ^nSyntaxError: invalid syntax
catch that and throw bad request exception

SMP-15b, make a custom bad request exception and catch that as 400 with meaningful error
new controller doesn't support get.. it needs to support get
lambda function making htppq requests, i'm not sure about this part

Code cleanup
Validation