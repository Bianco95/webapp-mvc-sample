DROP DATABASE IF EXISTS masterdb;

CREATE DATABASE masterdb;

USE masterdb;

CREATE TABLE customers(
	customerID int NOT NULL AUTO_INCREMENT,
	firstName varchar(255) NOT NULL,
	lastName varchar(255) NOT NULL,
	username varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	balance float NOT NULL,
	PRIMARY KEY (customerID),
	UNIQUE KEY (username)
);

CREATE TABLE transactions(
	transactionID int NOT NULL AUTO_INCREMENT,
	customerID int NOT NULL,
	amount int NOT NULL,
	purchase_date TIMESTAMP NOT NULL,
	PRIMARY KEY (transactionID),
	FOREIGN KEY (customerID) REFERENCES customers(customerID)
);

CREATE TABLE superadmins(
	adminID int NOT NULL AUTO_INCREMENT,
	username varchar(255) NOT NULL,
	PRIMARY KEY (adminID),
	FOREIGN KEY (username) REFERENCES customers(username)
);

CREATE TABLE loggedCustomers(
	token varchar(255) NOT NULL,
	username varchar(255) NOT NULL
);


INSERT INTO customers VALUES (NULL,"Giulio", "Bianchini", "admin", "123456", "0");
INSERT INTO customers VALUES (NULL,"Dippy", "Dawg", "Pippo", "123456", "100");
INSERT INTO customers VALUES (NULL,"Donald", "Duck", "Paperino", "123456", "200");

INSERT INTO transactions VALUES (NULL,2,10,CURRENT_TIMESTAMP());
INSERT INTO transactions VALUES (NULL,2,20,CURRENT_TIMESTAMP());

INSERT INTO transactions VALUES (NULL,3,50,CURRENT_TIMESTAMP());
INSERT INTO transactions VALUES (NULL,3,70,CURRENT_TIMESTAMP());

INSERT INTO superadmins VALUES (1, "admin");
