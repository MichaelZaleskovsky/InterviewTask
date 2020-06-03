# Interview Task Application

Test Java application for store and management of records.


Created using **SpringBoot** framework.

Clone application as **Maven** project.

Run **InterviewtaskApplication.java** to start server.

Test using **Junit** and **Rest-assured** library.

Run **InterviewtaskApplicationTests.java** for test.


# API List

------------------------------------------------------------------
## 1. ADD RECORDS  

### REQUEST

Type - POST

URL - http://localhost:8080/client/records

Body
```
text file
```
### RESPONSE

**if Success**

  Status: 201,

  Body: 

  message: "All records added sucessfully"

**if Error**

Status:  400: 
message = "Inner mistake";

Status:  406: 
message = "Wrong data format";

Status:  409: 
message = "Record contain not unique PRIMARY_KEY";


------------------------------------------------------------------
## 2. GET ONE RECORD  

### REQUEST

Type - GET

URL - http://localhost:8080/client/records/{id}

Headers - Content-type : application/json

Parameters -
```
{id} number, unique id of record
```

### RESPONSE

**if Success**

  Status: 200,

  Body: 
```
{
    "primaryKey": number,
    "name": string,
    "description": string,
    "timeStamp": number
}
```
**if Error**

  Status: 404,

  Body:

  Error message "Record with ID {id} not found"

------------------------------------------------------------------
## 3. GET RECORDS BY INTERVAL BY PAGE

### REQUEST

Type - GET

URL - http://localhost:8080/client/records/{timeFrom}/{timeTo}?pageSize={pageSize}&pageNumber={pageNumber}

Headers - Content-type : application/json

Parameters -
```
{timeFrom} timestamp, begin of interval, included
{timeTo} timestamp, end of interval, included
{pageSize} number of records by page, optional, default value - 25
{pageNumber} number of page, started from 1, optional, default value - 1
```
### RESPONSE

**if Success**

  Status: 200,

  Body: 
```
[
	{
	    "primaryKey": number,
	    "name": string,
	    "description": string,
	    "timeStamp": number
	},
	...
]
```

------------------------------------------------------------------
## 4. DELETE ONE RECORD  

### REQUEST

Type - DELETE

URL - http://localhost:8080/owner/records/{id}

Headers - Content-type : application/json

Parameters -
```
{id} number, unique id of record
```

### RESPONSE

**if Success**

  Status: 200,

  Body: 
```
{
    "primaryKey": number,
    "name": string,
    "description": string,
    "timeStamp": number
}
```
**if Error**

  Status: 404,

  Body:

  Error message "Record with ID {id} not found"

------------------------------------------------------------------
