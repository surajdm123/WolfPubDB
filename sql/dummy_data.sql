INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (3001,'John','1982-10-10','21 ABC St, NC 27','9391234567','3001@gmail.com','2018-10-10','Editor'),(3002,'Ethen','1988-02-16','21 ABC St, NC 27606','9491234567','3002@gmail.com','2020-12-11','Editor'),(3003,'Cathy','1988-08-23','3300 AAA St, NC 27606','9591234567','3003@gmail.com','2019-01-01','Author');

INSERT INTO `author` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (3003,1,1200,'2020-04-01');

INSERT INTO `editor` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (3001,0,1000,'2020-04-01'),(3002,0,1000,'2020-04-01');

INSERT INTO `publication` (`pid`, `title`, `publication_date`, `genre`, `publication_type`) VALUES (1001,'introduction to database','2018-10-10','technology','Book'),(1002,'Healthy Diet','2020-02-24','health','Magazine'),(1003,'Animal Science','2020-03-01','science','Journal');

INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (1002,'Weekly'),(1003,'Monthly');

INSERT INTO `book` (`pid`, `number_of_pages`) VALUES (1001,100);

INSERT INTO `editions` (`edition_number`, `pid`, `price`, `isbn`, `edition_date`) VALUES ('2ed',1001,20,'12345','2018-10-10');

INSERT INTO `issues` (`issueId`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (1,1002,20,'Pilot Issue','2020-02-24'),(1,1003,10,'Pilot Issue','2020-03-01');

INSERT INTO `articles` (`articleId`, `pid`, `issueId`, `text`, `date`, `name`) VALUES (1,1002,1,'ABC','2020-02-24','Pilot Article'),(1,1003,1,'AAA','2020-03-01','Pilot Article');

INSERT INTO `writes` (`sid`, `pid`) VALUES (3003,1001),(3003,1002);

INSERT INTO `edits` (`sid`, `pid`, `status`) VALUES (3001,1001,'IN_PROGRESS'),(3002,1002,'IN_PROGRESS');

INSERT INTO `distributor` (`distributorId`, `name`, `type`, `streetAddress`, `city`, `phoneNum`, `contact`, `balanceAmount`) VALUES (2001,'BookSell','bookstore','2200, A Street, NC','charlotte','9191234567','Jason',215),(2002,'BookDist','wholesaler','2200, B Street, NC','Raleigh','9291234568','Alex',0);

INSERT INTO `orders` (`orderId`, `distributorId`, `shipCost`, `orderDate`, `price`, `deliveryDate`, `status`) VALUES (4001,2001,30,'2020-01-02',600,'2020-01-15','COMPLETED'),(4002,2001,15,'2020-02-05',200,'2020-02-15','COMPLETED'),(4003,2002,15,'2020-02-10',100,'2020-02-25','COMPLETED');

INSERT INTO `includes` (`orderId`, `pid`, `edition_number`, `number_of_copies`) VALUES (4001,1001,'2ed',30),(4002,1001,'2ed',10);

INSERT INTO `consists` (`orderId`, `pid`, `issueId`, `number_of_copies`) VALUES (4003,1003,1,10);

INSERT INTO `staff` (`sid`, `name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES ('3000', 'Muniyappa', '1996-09-11', '3232 ABC St, NC 27606', '9195552367', '3000@gmail.com', '2020-04-01', 'Billing Staff');
INSERT INTO `sdevath`.`bill_staff` (`sid`, `counter_number`) VALUES ('3000', '1');


