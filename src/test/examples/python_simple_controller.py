#!/usr/bin/env python
from flask import Flask, request
import subprocess
import time
### make sure to run this with python3
app = Flask(__name__)

@app.route('/user', methods=['POST'])
def get_user():
    f = open("demofile2.py", "a")
    print("printing request")
    print(request)
    request_data_str = str(request.json['code'])
    #    request_data_str.decode("utf-8", "strict")
    print(request_data_str)
    f.write(request_data_str)
    f.close()

    string="python demofile2.py"
    result = subprocess.getoutput(string)
    subprocess.getoutput("rm demofile2.py")
    return result

if __name__ == '__main__':
    app.run(host="localhost", port=8000)
