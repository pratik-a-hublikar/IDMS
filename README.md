# IDMS
IDMS is a java spring boot microservice which will fetch the data from the Downstream service and store the data into the table and with another API using valid Auth token we can fetch the store data  
## Environment variables:
    jwt.token.expiry-in-min=5
    idms.base.url=https://idms.dealersocket.com
    idms.auth.user-name=testerAPI@drivesoft.tech
    idms.auth.password=THis!1sAT3mp0?r@ryAC>CT
    idms.layout-id=2006084
    idms.account-status=a
    idms.institution-id=107007
    idms.page-number=1
    
    #Set the DB configuration properties
    spring.datasource.url=jdbc:mysql://localhost:3306/idms
    spring.datasource.username=Admin
    spring.datasource.password=Admin@123

## Libraries:
    Lombork, spring cloud (for feign client), JWT (for getting the token),mysql

## User Flow:
#### 1. Authenticate the user for this application.Call following API to get the token.
        Request:
            METHOD: POST
            URL: {{baseServerURL}}/api/public/auth/user
            Request Body:
            {
                "username":"admin",
                "password":"DriveSoft@@!"
            }
            # as of now we have a default user in user table with hardcoded user name password.
        Response: 
           #If the credentials are correct then we should receive 200 HTTP response.
            Success:
            {
                "status": 200,
                "token": "Bearer eyJhbGciOiJIUzM4NCJ9.eyJ1c2VyTmFtZSI6ImFkbWluIiwic3ViIjoiTE9HSU5fVE9LRU4iLCJqdGkiOiIyYjczNDk2ZC04ZTE0LTQ3YWItYjQzMy03MGFjODMxNzlmMmIiLCJpYXQiOjE3MjkwODE1OTgsImV4cCI6MTcyOTA4MTg5OH0.nM7zPDjxHEBH9uOtwbPIISFhaYltZPCXBDHJROsceFeJ0qyhIpC8rExYbm670YsW"
            }
            Failure:
            {
                "status": 401,
                "message": "Username or Password is wrong"
            }
#### 2. Call loadIDMSData API to fetch the data from IDMS and store the data into the database.
    Request:
        METHOD: GET
        URL: {{baseServerURL}}/api/idms/loadIDMSData
        #set the token that we received from the api/public/auth/user in the request header   
    Response:
        Success:
        {
            "status": 200,
            "message": "Success"
        }
        Failure:
        {
            "status": 200,
            "message": "Failure"
        }
#### 3.Retrieve the data from the DB:
        Request:
        METHOD: POST
        URL: {{baseServerURL}}/api/idms/getData
        RequestBody:
            {
                "acctIdList":[],
                "acctType":""
            }
                OR
            {
                "acctIdList":["12345","8984"],
                "acctType":""
            }   
                OR
            {
                "acctIdList":[],
                "acctType":"I"
            }   
                OR
            {
                "acctIdList":["12345","8984"],
                "acctType":"O"
            }
        #set the token that we received from the api/public/auth/user in the request header   
    Response:
        {
            "status": 200,
            "message": "Success",
            "data": [
                #List of data
            ]
        }
