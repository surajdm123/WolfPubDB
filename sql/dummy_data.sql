-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: classdb2.csc.ncsu.edu    Database: sdevath
-- ------------------------------------------------------
-- Server version	5.5.68-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `admin`
--
-- ORDER BY:  `ssn`,`sid`

/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` (`ssn`, `sid`) VALUES ('1234100001',10);
INSERT INTO `admin` (`ssn`, `sid`) VALUES ('1234100002',12);
INSERT INTO `admin` (`ssn`, `sid`) VALUES ('1234101002',13);
INSERT INTO `admin` (`ssn`, `sid`) VALUES ('1237651000',1);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;

--
-- Dumping data for table `articles`
--
-- ORDER BY:  `articleId`,`pid`,`issueId`

/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` (`articleId`, `pid`, `issueId`, `text`, `date`, `name`) VALUES (101,24,1,'This is the first article in the pilot issue of Reader’s digest!','2020-02-01','Pilot Article');
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;

--
-- Dumping data for table `author`
--
-- ORDER BY:  `sid`

/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (5,1,0,'2022-04-15');
INSERT INTO `author` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (7,1,0,'2022-04-15');
INSERT INTO `author` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (8,1,2500,'2022-04-15');
INSERT INTO `author` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (14,0,250,'2022-04-01');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;

--
-- Dumping data for table `bill_staff`
--
-- ORDER BY:  `sid`

/*!40000 ALTER TABLE `bill_staff` DISABLE KEYS */;
INSERT INTO `bill_staff` (`sid`, `counter_number`) VALUES (3,11);
INSERT INTO `bill_staff` (`sid`, `counter_number`) VALUES (18,2);
INSERT INTO `bill_staff` (`sid`, `counter_number`) VALUES (19,5);
INSERT INTO `bill_staff` (`sid`, `counter_number`) VALUES (20,8);
/*!40000 ALTER TABLE `bill_staff` ENABLE KEYS */;

--
-- Dumping data for table `bills`
--
-- ORDER BY:  `billId`,`distributorId`,`sid`

/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
INSERT INTO `bills` (`billId`, `totalBill`, `billDate`, `sid`, `distributorId`) VALUES (4723006,8758.33,'2022-01-15',3,3104);
INSERT INTO `bills` (`billId`, `totalBill`, `billDate`, `sid`, `distributorId`) VALUES (4723007,624.99,'2021-12-21',18,3212);
INSERT INTO `bills` (`billId`, `totalBill`, `billDate`, `sid`, `distributorId`) VALUES (4723008,4567.95,'2022-02-14',19,3213);
INSERT INTO `bills` (`billId`, `totalBill`, `billDate`, `sid`, `distributorId`) VALUES (4723009,7434.65,'2022-01-07',20,3233);
INSERT INTO `bills` (`billId`, `totalBill`, `billDate`, `sid`, `distributorId`) VALUES (4723010,4945.87,'2022-03-11',3,3243);
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;

--
-- Dumping data for table `book`
--
-- ORDER BY:  `pid`

/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` (`pid`, `number_of_pages`) VALUES (20,500);
INSERT INTO `book` (`pid`, `number_of_pages`) VALUES (21,350);
INSERT INTO `book` (`pid`, `number_of_pages`) VALUES (22,189);
INSERT INTO `book` (`pid`, `number_of_pages`) VALUES (23,250);
INSERT INTO `book` (`pid`, `number_of_pages`) VALUES (32,555);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

--
-- Dumping data for table `chapters`
--
-- ORDER BY:  `chapter_number`,`pid`,`edition_number`

/*!40000 ALTER TABLE `chapters` DISABLE KEYS */;
INSERT INTO `chapters` (`chapter_number`, `pid`, `edition_number`, `chapter_name`, `text`) VALUES (1,20,1,'Introduction','This chapter introduces you to the basics of Parallel Programming.');
/*!40000 ALTER TABLE `chapters` ENABLE KEYS */;

--
-- Dumping data for table `consists`
--
-- ORDER BY:  `orderId`,`pid`,`issueId`

/*!40000 ALTER TABLE `consists` DISABLE KEYS */;
INSERT INTO `consists` (`orderId`, `pid`, `issueId`, `number_of_copies`) VALUES (15612,24,1,100);
INSERT INTO `consists` (`orderId`, `pid`, `issueId`, `number_of_copies`) VALUES (15613,26,1,200);
INSERT INTO `consists` (`orderId`, `pid`, `issueId`, `number_of_copies`) VALUES (15614,27,1,300);
/*!40000 ALTER TABLE `consists` ENABLE KEYS */;

--
-- Dumping data for table `distributor`
--
-- ORDER BY:  `distributorId`

/*!40000 ALTER TABLE `distributor` DISABLE KEYS */;
INSERT INTO `distributor` (`distributorId`, `name`, `type`, `streetAddress`, `city`, `phoneNum`, `contact`, `balanceAmount`) VALUES (3104,'James B. Hunt Jr.','library','Partners Way','Raleigh','9197876420','Jasti',8758.33);
INSERT INTO `distributor` (`distributorId`, `name`, `type`, `streetAddress`, `city`, `phoneNum`, `contact`, `balanceAmount`) VALUES (3212,'Turn the Page','bookstore','Brigadoon Drive','Cary','9196236400','Timothy',624.99);
INSERT INTO `distributor` (`distributorId`, `name`, `type`, `streetAddress`, `city`, `phoneNum`, `contact`, `balanceAmount`) VALUES (3213,'A New Chapter','bookstore','Varsity Drive','Durham','9196235698','Randall',4567.95);
INSERT INTO `distributor` (`distributorId`, `name`, `type`, `streetAddress`, `city`, `phoneNum`, `contact`, `balanceAmount`) VALUES (3233,'Bulk Demand','wholesale','Levine Drive','Charlotte','9891116090','Barney Stinson',7434.65);
INSERT INTO `distributor` (`distributorId`, `name`, `type`, `streetAddress`, `city`, `phoneNum`, `contact`, `balanceAmount`) VALUES (3243,'Glorious Goods','wholesale','Monty Smith','Ashville','9891116765','Ted Mosby',9891.74);
INSERT INTO `distributor` (`distributorId`, `name`, `type`, `streetAddress`, `city`, `phoneNum`, `contact`, `balanceAmount`) VALUES (3410,'Fresh Reads','bookstore','Hillsborough Street','Raleigh','9196378476','Larry',2850.5);
/*!40000 ALTER TABLE `distributor` ENABLE KEYS */;

--
-- Dumping data for table `editions`
--
-- ORDER BY:  `edition_number`,`pid`

/*!40000 ALTER TABLE `editions` DISABLE KEYS */;
INSERT INTO `editions` (`edition_number`, `pid`, `price`, `isbn`) VALUES (1,21,15,'853211');
INSERT INTO `editions` (`edition_number`, `pid`, `price`, `isbn`) VALUES (1,22,40,'498320');
INSERT INTO `editions` (`edition_number`, `pid`, `price`, `isbn`) VALUES (1,32,30,'674560');
INSERT INTO `editions` (`edition_number`, `pid`, `price`, `isbn`) VALUES (2,20,10,'434561');
/*!40000 ALTER TABLE `editions` ENABLE KEYS */;

--
-- Dumping data for table `editor`
--
-- ORDER BY:  `sid`

/*!40000 ALTER TABLE `editor` DISABLE KEYS */;
INSERT INTO `editor` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (4,0,500,'2022-04-01');
INSERT INTO `editor` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (9,1,150,'2022-03-15');
INSERT INTO `editor` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (11,1,3000,'2022-03-15');
INSERT INTO `editor` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (15,1,300,'2022-03-15');
/*!40000 ALTER TABLE `editor` ENABLE KEYS */;

--
-- Dumping data for table `edits`
--
-- ORDER BY:  `sid`,`pid`

/*!40000 ALTER TABLE `edits` DISABLE KEYS */;
INSERT INTO `edits` (`sid`, `pid`) VALUES (4,20);
INSERT INTO `edits` (`sid`, `pid`) VALUES (4,25);
INSERT INTO `edits` (`sid`, `pid`) VALUES (4,29);
INSERT INTO `edits` (`sid`, `pid`) VALUES (9,21);
INSERT INTO `edits` (`sid`, `pid`) VALUES (9,26);
INSERT INTO `edits` (`sid`, `pid`) VALUES (9,28);
INSERT INTO `edits` (`sid`, `pid`) VALUES (9,32);
INSERT INTO `edits` (`sid`, `pid`) VALUES (11,22);
INSERT INTO `edits` (`sid`, `pid`) VALUES (11,24);
INSERT INTO `edits` (`sid`, `pid`) VALUES (11,31);
INSERT INTO `edits` (`sid`, `pid`) VALUES (15,23);
INSERT INTO `edits` (`sid`, `pid`) VALUES (15,27);
INSERT INTO `edits` (`sid`, `pid`) VALUES (15,30);
/*!40000 ALTER TABLE `edits` ENABLE KEYS */;

--
-- Dumping data for table `includes`
--
-- ORDER BY:  `orderId`,`pid`,`edition_number`

/*!40000 ALTER TABLE `includes` DISABLE KEYS */;
INSERT INTO `includes` (`orderId`, `pid`, `edition_number`, `number_of_copies`) VALUES (15615,21,1,100);
INSERT INTO `includes` (`orderId`, `pid`, `edition_number`, `number_of_copies`) VALUES (15616,22,1,200);
INSERT INTO `includes` (`orderId`, `pid`, `edition_number`, `number_of_copies`) VALUES (15617,32,1,150);
INSERT INTO `includes` (`orderId`, `pid`, `edition_number`, `number_of_copies`) VALUES (15618,20,2,300);
/*!40000 ALTER TABLE `includes` ENABLE KEYS */;

--
-- Dumping data for table `issues`
--
-- ORDER BY:  `issueId`,`pid`

/*!40000 ALTER TABLE `issues` DISABLE KEYS */;
INSERT INTO `issues` (`issueId`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (1,24,30,'Pet Pals','2020-02-01');
INSERT INTO `issues` (`issueId`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (1,26,30,'Italian delicacies','2018-07-14');
INSERT INTO `issues` (`issueId`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (1,27,15,'Shoot for the stars','2017-09-14');
INSERT INTO `issues` (`issueId`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (1,28,40,'Bengal Tigers','2021-11-14');
INSERT INTO `issues` (`issueId`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (1,29,25,'Brain Ballads','1871-03-01');
INSERT INTO `issues` (`issueId`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (1,30,35,'Gorgeous Galaxy','2015-05-02');
INSERT INTO `issues` (`issueId`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (1,31,20,'Snow Storms','1872-10-01');
INSERT INTO `issues` (`issueId`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (1,33,25,'Elephants','2018-02-01');
/*!40000 ALTER TABLE `issues` ENABLE KEYS */;

--
-- Dumping data for table `management`
--
-- ORDER BY:  `sid`

/*!40000 ALTER TABLE `management` DISABLE KEYS */;
INSERT INTO `management` (`sid`, `grade`) VALUES (2,'Senior');
INSERT INTO `management` (`sid`, `grade`) VALUES (6,'General');
INSERT INTO `management` (`sid`, `grade`) VALUES (16,'General');
INSERT INTO `management` (`sid`, `grade`) VALUES (17,'General');
/*!40000 ALTER TABLE `management` ENABLE KEYS */;

--
-- Dumping data for table `orders`
--
-- ORDER BY:  `orderId`,`distributorId`

/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`orderId`, `distributorId`, `shipCost`, `orderDate`, `price`, `deliveryDate`, `status`) VALUES (15612,3104,25,'2022-01-15 00:00:00',8758.33,'2022-01-22','COMPLETED');
INSERT INTO `orders` (`orderId`, `distributorId`, `shipCost`, `orderDate`, `price`, `deliveryDate`, `status`) VALUES (15613,3212,25,'2021-12-21 00:00:00',624.99,'2021-12-28','COMPLETED');
INSERT INTO `orders` (`orderId`, `distributorId`, `shipCost`, `orderDate`, `price`, `deliveryDate`, `status`) VALUES (15614,3213,35,'2022-02-14 00:00:00',4567.95,'2022-02-21','COMPLETED');
INSERT INTO `orders` (`orderId`, `distributorId`, `shipCost`, `orderDate`, `price`, `deliveryDate`, `status`) VALUES (15615,3233,35,'2022-01-07 00:00:00',7434.65,'2022-01-14','COMPLETED');
INSERT INTO `orders` (`orderId`, `distributorId`, `shipCost`, `orderDate`, `price`, `deliveryDate`, `status`) VALUES (15616,3243,50,'2022-03-09 00:00:00',4945.87,'2022-03-16','IN_PROGRESS');
INSERT INTO `orders` (`orderId`, `distributorId`, `shipCost`, `orderDate`, `price`, `deliveryDate`, `status`) VALUES (15617,3104,30,'2022-01-22 00:00:00',15432.33,'2022-01-29','COMPLETED');
INSERT INTO `orders` (`orderId`, `distributorId`, `shipCost`, `orderDate`, `price`, `deliveryDate`, `status`) VALUES (15618,3212,40,'2022-03-10 00:00:00',20000.33,'2022-03-17','IN_PROGRESS');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

--
-- Dumping data for table `payment`
--
-- ORDER BY:  `pId`

/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` (`pId`, `payDate`, `work_type`, `amount`, `sid`) VALUES (1122,'2022-04-15','BOOK_AUTHORSHIP',1000,8);
INSERT INTO `payment` (`pId`, `payDate`, `work_type`, `amount`, `sid`) VALUES (1123,'2022-04-01','BOOK_AUTHORSHIP',250,14);
INSERT INTO `payment` (`pId`, `payDate`, `work_type`, `amount`, `sid`) VALUES (1124,'2022-03-15','EDITORIAL',150,9);
INSERT INTO `payment` (`pId`, `payDate`, `work_type`, `amount`, `sid`) VALUES (1125,'2022-04-15','BOOK_AUTHORSHIP',0,7);
INSERT INTO `payment` (`pId`, `payDate`, `work_type`, `amount`, `sid`) VALUES (1224,'2022-03-15','BOOK_AUTHORSHIP',500,8);
INSERT INTO `payment` (`pId`, `payDate`, `work_type`, `amount`, `sid`) VALUES (1225,'2022-03-15','BOOK_AUTHORSHIP',500,8);
INSERT INTO `payment` (`pId`, `payDate`, `work_type`, `amount`, `sid`) VALUES (1226,'2022-03-15','EDITORIAL',500,4);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;

--
-- Dumping data for table `periodic_publication`
--
-- ORDER BY:  `pid`

/*!40000 ALTER TABLE `periodic_publication` DISABLE KEYS */;
INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (24,'Monthly');
INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (25,'Monthly');
INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (26,'Weekly');
INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (27,'Weekly');
INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (28,'Monthly');
INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (29,'Weekly');
INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (30,'Weekly');
INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (31,'Monthly');
INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (33,'Weekly');
/*!40000 ALTER TABLE `periodic_publication` ENABLE KEYS */;

--
-- Dumping data for table `publication`
--
-- ORDER BY:  `pid`

/*!40000 ALTER TABLE `publication` DISABLE KEYS */;
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (20,'Parallel Systems','2019-07-23','Computer Science','Book');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (21,'Basics of Python','2018-06-13','Programming','Book');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (22,'Think Like A Monk','2020-04-05','Self-help','Book');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (23,'Moneyball','2011-06-04','Sports','Book');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (24,'Reader\'s Digest','2020-01-01','General','Magazine');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (25,'Vogue','2019-03-15','Fashion','Magazine');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (26,'Food & Wine','2018-07-01','Food','Magazine');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (27,'Us Weekly','2017-09-01','Entertainment','Magazine');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (28,'Nature','1869-11-14','Science','Journal');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (29,'Swiss Medical Weekly','1871-03-01','Medicine','Journal');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (30,'The Astronomical Journal','2015-05-02','Science','Journal');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (31,'Monthly Weather Review','1872-10-01','Science','Journal');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (32,'Java','2018-07-23','Educational','Book');
INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (33,'Animal Planet','2018-01-01','Nature','Magazine');
/*!40000 ALTER TABLE `publication` ENABLE KEYS */;

--
-- Dumping data for table `staff`
--
-- ORDER BY:  `sid`

/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (1,'Suraj','1997-12-08','2001 Gorman Street','9199191033','suraj@gmail.com','2015-01-03','Administrator');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (2,'Manvita','1996-09-11','3132 Avent Ferry Road','9195341234','manvi@gmail.com','2016-06-11','Manager');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (3,'Prakruthi','1996-08-22','3132 Avent Ferry Road','9191367283','prakruthi@gmail.com','2017-04-04','Billing Staff');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (4,'Radhika','1998-03-03','1011 Octavia Street','9193456798','radhika@gmail.com','2022-01-03','Editor');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (5,'Prakash','2000-04-09','3451 Cal street','9874321234','prakash@gmail.cim','2022-01-03','Author');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (6,'Ram','2000-06-06','2001 Gorman Street','9196541945','ramcool@gmail.com','2011-11-11','Manager');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (7,'Vignesh','2000-08-11','2003 Gorman Street','7651234778','vignesh@gmail.com','2009-11-11','Author');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (8,'Krishika','1999-03-11','2004 Gorman Street','9871234098','krishika@gmail.com','2001-01-05','Author');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (9,'Mansi','1998-05-07','2455 Octavia Street','7174561234','mansi@gmail.com','2009-09-11','Editor');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (10,'Arpitha','1995-07-08','3000 Kings Court','9198765432','arpitha@gmail.com','2007-09-09','Administrator');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (11,'Ashwini','1996-12-12','2003 Gorman Street','9197650937','ashwinirocks@gmail.com','2022-01-07','Editor');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (12,'Anusha','1997-02-14','2321 Gorman Street','9849191033','anusha@gmail.com','2015-02-03','Administrator');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (13,'Ajith','1999-03-21','1551 University Commons','9849876033','ajith@gmail.com','2016-02-03','Administrator');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (14,'Jinu','1996-12-13','1553 University Commons','9849876546','jinu@gmail.com','2020-02-03','Author');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (15,'Aniketh','1999-07-13','4200 University Commons','9863836546','aniketh@gmail.com','2020-07-03','Editor');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (16,'Kishan','1994-01-15','3002 Gorman Street','9863822226','kishan@gmail.com','2018-07-15','Manager');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (17,'Gagana','1999-06-15','3102 Gorman Street','9863822237','gagana@gmail.com','2019-09-05','Manager');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (18,'Saravanan','1995-08-15','3103 Gorman Street','9863834337','saravanan@gmail.com','2020-09-01','Billing Staff');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (19,'Supriya','1995-10-15','3103 Octavia Street','9863834657','supriya@gmail.com','2020-10-11','Billing Staff');
INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (20,'Seeya','1995-05-08','3103 Avent Ferry','9195834657','seeya@gmail.com','2021-05-22','Billing Staff');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;

--
-- Dumping data for table `writes`
--
-- ORDER BY:  `sid`,`pid`

/*!40000 ALTER TABLE `writes` DISABLE KEYS */;
INSERT INTO `writes` (`sid`, `pid`) VALUES (5,20);
INSERT INTO `writes` (`sid`, `pid`) VALUES (5,24);
INSERT INTO `writes` (`sid`, `pid`) VALUES (5,28);
INSERT INTO `writes` (`sid`, `pid`) VALUES (7,21);
INSERT INTO `writes` (`sid`, `pid`) VALUES (7,25);
INSERT INTO `writes` (`sid`, `pid`) VALUES (7,29);
INSERT INTO `writes` (`sid`, `pid`) VALUES (8,22);
INSERT INTO `writes` (`sid`, `pid`) VALUES (8,26);
INSERT INTO `writes` (`sid`, `pid`) VALUES (8,30);
INSERT INTO `writes` (`sid`, `pid`) VALUES (8,32);
INSERT INTO `writes` (`sid`, `pid`) VALUES (8,33);
INSERT INTO `writes` (`sid`, `pid`) VALUES (14,23);
INSERT INTO `writes` (`sid`, `pid`) VALUES (14,27);
INSERT INTO `writes` (`sid`, `pid`) VALUES (14,31);
/*!40000 ALTER TABLE `writes` ENABLE KEYS */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-29 23:57:21
