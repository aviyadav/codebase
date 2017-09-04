CREATE TABLE Address(
   id INT NOT NULL AUTO_INCREMENT,
   street VARCHAR(100),
   city VARCHAR(20) NOT NULL,
   province VARCHAR(50),
   country VARCHAR(20) NOT NULL,
   postcode VARCHAR(6) NOT NULL,
   PRIMARY KEY ( id )
   );