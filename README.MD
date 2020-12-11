### MVC Application
#### Software used for this sample of MVC application
* **JERSEY** **https://eclipse-ee4j.github.io/jersey/** to develop RESTful web service
* **mySQL** **https://www.mysql.com/it/** as a database service
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