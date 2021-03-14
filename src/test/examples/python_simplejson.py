# Sample function to return a simple JSON response regarding a customer. Age of the customer is a random integer between 35 and 55, credit score is a random number between 450 and 810

# Result will be in the following structure
# {
#    "age": 38,
#    "creditScore": 740,
#    "name": "John",
#    "zipCode": "02138"
# }

import json
import random

age = random.randint(35,55);
creditScore = random.randint(450,810);
response = {
    "name": "John",
    "age": age,
    "zipCode": "02138",
    "creditScore": creditScore,
}

# convert into JSON:
result = json.dumps(response)

# print result which is the response of the function
print(result)
