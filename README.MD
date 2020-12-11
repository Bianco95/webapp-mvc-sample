### MVC Application
#### Software used for this sample of MVC application
* **JERSEY** **https://eclipse-ee4j.github.io/jersey/** to develop RESTful web service
* **mySQL** **https://www.mysql.com/it/** as a database service
* **TOMCAT** **http://tomcat.apache.org/** as server web
* **ECLIPSE EE** **https://www.eclipse.org/webtools/jee/** as IDE to develop the whole mvc application
* **DOCKER** **https://www.docker.com/** to run mysql on a container
* **POSTMAN** **https://www.postman.com/** to test rest-api 

#### Steps to run the application


1. Set up docker

    * First is necessary to run mySQL docker container
    ```
    docker run --name mysql -e MYSQL_ROOT_PASSWORD=password_secret -d mysql -h
    docker start mysql
    ```
    * run inside the container and check ip address, in my case ip is *172.17.0.2*

    * connect to db with
    ```
    mysql -h 172.17.0.2 -u root -p
    ```
    * init the db with
    ```
    mysql -h 172.17.0.2 -u root -p masterdb < initDB.sql
    ```
    
    initDB.sql initialize the database and it will create the relational tables needed to store the data. Some data will also be added. <br >
    **customers**
    customerID | firstName | lastName | username | password | balance |
    --- | --- | --- | --- |--- |--- |--- |--- |--- |--- |--- |---
    1 | Giulio | Bianchini | admin | 123456 | 0 |
    2 | Dippy | Dawg | Pippo | 123456 | 100 |
    3 | Donald | Duck | Paperino | 123456 | 200 |

    <br>

    **transactions**

    transactionID | customerID | amount | purchase_date |
    --- | --- | --- | --- |--- |--- |--- |--- |--- |--- |--- |---
    1 | 2 | 10 | CURRENT_TIMESTAMP | 
    2 | 2 | 20 | CURRENT_TIMESTAMP |  
    3 | 3 | 50 | CURRENT_TIMESTAMP | 
    4 | 3 | 70 | CURRENT_TIMESTAMP | 

    <br>

    **superadmins**

    adminID | username | 
    --- | --- | 
    1 | admin |  

    <br>

2. Open the project with eclipse EE

    * right click on the project name
    * select **run as** and then **run on server** in order to start the web server


####REST-API

#### Login

```http
POST /rest-api/login
```

| Header | Type | Description |
| :--- | :--- | :--- |
| `Authorization` | `string` | **Required**. base64 encoding of username and password|

#### Responses

```javascript
{
    "message": string,
    "code": number
}
```
***i.e. response of a correct login***
``` javascript
{
    "message": "success",
    "code": 200
}
```
######if the request fails, code 401 is returned
---
#### Transactions
```http
GET rest-api/transactions
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `page` | `number` | **Not Required**. Use to get 25 element in order to paginate|


#### Responses

```javascript
{
    "message": string,
    "code": number,
    "page": number,
    "pages": number,
    "contents": Object[]
}
```
***i.e. response of a correct get***
``` javascript
{
    "message": "transactions",
    "code": 200,
    "page": 1,
    "pages": 1,
    "contents": [
        {
            "transactionID": 1,
            "customerID": 2,
            "amount": 10,
            "purchaseDate": "2020-12-10 23:29:49"
        },
        {
            "transactionID": 2,
            "customerID": 2,
            "amount": 20,
            "purchaseDate": "2020-12-10 23:29:49"
        }
    ]
}
```
######if the request fails, code 500 is returned
---
```http
POST rest-api/transactions
```
**Body**
```javascript
{
    "transactionID": number,
    "customerID": string,
    "amount": string,
    "purchaseDate": number
}
```

#### Responses

```javascript
{
    "message": string,
    "code": number,
}
```
***i.e. response of a correct post***
``` javascript
{
    "message": "transaction created",
    "code": 201
}
```
######if the request fails, code 500 is returned
---
```http
PUT rest-api/transactions
```
**Body**
```javascript
{
    "transactionID": number,
    "customerID": string,
    "amount": string,
    "purchaseDate": number
}
```
#### Responses

```javascript
{
    "message": string,
    "code": number,
}
```
***i.e. response of a correct put***
``` javascript
{
    "message": "transaction updated",
    "code": 201
}
```
######if the request fails, code 404 is returned if the transaction does not exist, otherwise 500
---
```http
DELETE rest-api/transactions
```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `id` | `number` | **Required** Id of the transaction to delete|


#### Responses

```javascript
{
    "message": string,
    "code": number,
}
```
***i.e. response of a correct delete***
``` javascript
{
    "message": "transaction deleted",
    "code": 200
}
```
---
######if the request fails, code 404 is returned if the transaction does not exist, otherwise 500

