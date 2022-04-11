CREATE TABLE `staff` (
     `sid` INT NOT NULL AUTO_INCREMENT,
     `name` VARCHAR(45) NOT NULL,
     `dob` DATE NOT NULL,
     `address` VARCHAR(150) NOT NULL,
     `phone` VARCHAR(45) NOT NULL,
     `emailID` VARCHAR(45) NOT NULL,
     `hireDate` DATE NOT NULL,
     `title` VARCHAR(45) NOT NULL,
     UNIQUE(emailID),
     PRIMARY KEY (`sid`)
);

CREATE TABLE `bill_staff` (
  `sid` INT NOT NULL,
  `counter_number` INT NOT NULL,
  PRIMARY KEY (`sid`),
  CONSTRAINT `sid`
     FOREIGN KEY (`sid`)
     REFERENCES `staff` (`sid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;


CREATE TABLE `management` (
  `sid` INT NOT NULL,
  `grade` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`sid`),
     FOREIGN KEY (`sid`)
     REFERENCES `staff` (`sid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;

CREATE TABLE `editor` (
  `sid` INT NOT NULL,
  `isInvited` SMALLINT(1) NOT NULL,
  `toPay` DOUBLE NOT NULL,
  `payDueDate` DATE,
  PRIMARY KEY (`sid`),
     FOREIGN KEY (`sid`)
     REFERENCES `staff` (`sid`)
ON DELETE CASCADE
     ON UPDATE CASCADE)
;

CREATE TABLE `author` (
  `sid` INT NOT NULL,
  `isInvited` SMALLINT(1) NOT NULL,
  `toPay` DOUBLE NOT NULL,
  `payDueDate` DATE NOT NULL,
  PRIMARY KEY (`sid`),
     FOREIGN KEY (`sid`)
     REFERENCES `staff` (`sid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;

CREATE TABLE `admin` (
  `ssn` VARCHAR(10),
  `sid` INT NOT NULL,
  UNIQUE(ssn),
  PRIMARY KEY (`ssn`, `sid`),
     FOREIGN KEY (`sid`)
     REFERENCES `staff` (`sid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;


CREATE TABLE `distributor` (
  `distributorId` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `streetAddress` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `phoneNum` VARCHAR(45) NOT NULL,
  `contact` VARCHAR(45) NOT NULL,
  `balanceAmount` DOUBLE DEFAULT 0,
 
PRIMARY KEY (`distributorId`))
;


CREATE TABLE `orders` (
  `orderId` INT NOT NULL AUTO_INCREMENT,
  `distributorId` INT NOT NULL,
  `shipCost` DOUBLE NOT NULL DEFAULT 0,
  `orderDate` DATE NOT NULL,
  `price` DOUBLE NOT NULL,
  `deliveryDate` DATE NOT NULL,
  `status` VARCHAR(45),
  PRIMARY KEY (`orderId`),
     FOREIGN KEY (`distributorId`)
     REFERENCES `distributor` (`distributorId`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;


CREATE TABLE `payment` (
  `pId` INT NOT NULL AUTO_INCREMENT,
  `payDate` DATE NOT NULL,
  `work_type` VARCHAR(45) NOT NULL,
  `amount` DOUBLE NOT NULL,
  `sid` INT NOT NULL,
  PRIMARY KEY (`pId`),
     FOREIGN KEY (`sid`)
     REFERENCES `staff` (`sid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;


CREATE TABLE `bills` (
  `billId` INT NOT NULL AUTO_INCREMENT,
`totalBill` DOUBLE NOT NULL,
  `billDate` DATE NOT NULL,
  `sid` INT NOT NULL,
  `distributorId` INT NOT NULL,
  PRIMARY KEY (`billId`, `distributorId`, `sid`),
     FOREIGN KEY (`sid`)
     REFERENCES `bill_staff` (`sid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE,
     FOREIGN KEY (`distributorId`)
     REFERENCES `distributor` (`distributorId`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;



CREATE TABLE `publication` (
  `pid` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `publication_date` DATE NOT NULL,
  `genre` VARCHAR(45),
`publication_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`pid`))
;

CREATE TABLE `edits` (
  `sid` INT NOT NULL,
  `pid` INT NOT NULL,
  status VARCHAR(50) NOT NULL DEFAULT "IN_PROGRESS"
  PRIMARY KEY (`sid`, `pid`),
     FOREIGN KEY (`sid`)
     REFERENCES `editor` (`sid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE,
     FOREIGN KEY (`pid`)
     REFERENCES `publication` (`pid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;


CREATE TABLE `writes` (
  `sid` INT NOT NULL,
  `pid` INT NOT NULL,
  PRIMARY KEY (`sid`, `pid`),
     FOREIGN KEY (`sid`)
     REFERENCES `author` (`sid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE,
     FOREIGN KEY (`pid`)
     REFERENCES `publication` (`pid`)
ON DELETE CASCADE
     ON UPDATE CASCADE)
;

CREATE TABLE `book` (
  `pid` INT NOT NULL,
  `number_of_pages` INT,
  PRIMARY KEY (`pid`),
     FOREIGN KEY (`pid`)
     REFERENCES `publication` (`pid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;

CREATE TABLE `periodic_publication` (
  `pid` INT NOT NULL,
  `periodicty` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`pid`),
     FOREIGN KEY (`pid`)
     REFERENCES `publication` (`pid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;

CREATE TABLE `editions` (
  `edition_number` INT NOT NULL,
`pid` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `isbn` VARCHAR(45) NOT NULL,
  UNIQUE(isbn),
  PRIMARY KEY (`edition_number`, `pid`),
     FOREIGN KEY (`pid`)
     REFERENCES `book` (`pid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;

CREATE TABLE `chapters` (
  `chapter_number` INT NOT NULL,
  `pid` INT NOT NULL,
  `edition_number` INT NOT NULL,
  `chapter_name` VARCHAR(45) NOT NULL,
  `text` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`chapter_number`, `pid`, `edition_number`),
     FOREIGN KEY (`pid`)
     REFERENCES `editions` (`pid`)
 
ON DELETE CASCADE
     ON UPDATE CASCADE,
     FOREIGN KEY (`edition_number`)
     REFERENCES `editions` (`edition_number`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;

CREATE TABLE `issues` (
  `issueId` INT NOT NULL,
  `pid` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `issue_title` VARCHAR(45) NOT NULL,
  `issue_date` DATE NOT NULL,
  PRIMARY KEY (`issueId`, `pid`),
     FOREIGN KEY (`pid`)
     REFERENCES `periodic_publication` (`pid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;


CREATE TABLE `articles` (
  `articleId` INT NOT NULL,
  `pid` INT NOT NULL,
  `issueId` INT NOT NULL,
  `text` VARCHAR(200) NOT NULL,
  `date` DATE NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`articleId`, `pid`, `issueId`),
     FOREIGN KEY (`pid`)
     REFERENCES `issues` (`pid`)
ON DELETE CASCADE
     ON UPDATE CASCADE,
     FOREIGN KEY (`issueId`)
     REFERENCES `issues` (`issueId`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;


CREATE TABLE  `includes` (
  `orderId` INT NOT NULL,
  `pid` INT NOT NULL,
  `edition_number` INT NOT NULL,
  `number_of_copies` INT NOT NULL,
  PRIMARY KEY (`orderId`, `pid`, `edition_number`),
     FOREIGN KEY (`pid`)
     REFERENCES `editions` (`pid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE,
     FOREIGN KEY (`orderId`)
     REFERENCES `orders` (`orderId`)
     ON DELETE CASCADE
 
ON UPDATE CASCADE,
     FOREIGN KEY (`edition_number`)
     REFERENCES `editions` (`edition_number`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;


CREATE TABLE  `consists` (
  `orderId` INT NOT NULL,
  `pid` INT NOT NULL,
  `issueId` INT NOT NULL,
  `number_of_copies` INT NOT NULL,
  PRIMARY KEY (`orderId`, `pid`, `issueId`),
     FOREIGN KEY (`orderId`)
     REFERENCES `orders` (`orderId`)
     ON DELETE CASCADE
     ON UPDATE CASCADE,
     FOREIGN KEY (`pid`)
REFERENCES `issues` (`pid`)
     ON DELETE CASCADE
     ON UPDATE CASCADE,
     FOREIGN KEY (`issueId`)
     REFERENCES `issues` (`issueId`)
     ON DELETE CASCADE
     ON UPDATE CASCADE)
;