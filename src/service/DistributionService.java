package service;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DistributionService {

    ConnectionHelper connectionHelper = new ConnectionHelper();
    Scanner scanner = new Scanner(System.in);
    ResultSetService resultSetService = new ResultSetService();

    public void run(final Connection connection) {

        try {

            while (true) {
                System.out.println("\n\nDISTRIBUTOR:");
                System.out.println("1. Insert new Distributor");
                System.out.println("2. Update Distributor information");
                System.out.println("3. Delete Distributor");

                System.out.println("ORDERS:");
                System.out.println("4. Place a new Order");
                System.out.println("5. Mark an order Completed");

                System.out.println("BILLING:");
                System.out.println("6. Bill Distributor for his orders");
                System.out.println("7. Update Distributor's outstanding balance");

                System.out.println("8. Return to Main Menu\n");

                System.out.println("Enter your choice:");

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        insertNewDistributor(connection);
                        break;
                    case 2:
                        updateDistributor(connection);
                        break;
                    case 3:
                        deleteDistributor(connection);
                        break;
                    case 4:
                        placeNewOrder(connection);
                        break;
                    case 5:
                        markOrderCompleted(connection);
                        break;
                    case 6:
                        insertNewBillDistributor(connection);
                        break;
                    case 7:
                        updateDistributorOutstandingBalance(connection);
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("Invalid Input");

                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    public void markOrderCompleted(final Connection connection) {
        try {
            resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM orders where status = 'IN_PROGRESS';");

            System.out.println("Enter the Order Id you want to mark as Delivered:");
            final int orderId = scanner.nextInt();

            final String sqlQuery = "UPDATE `orders` SET `status` = 'COMPLETED' WHERE (`orderId` = ?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, orderId);
            int updatedRows = statement.executeUpdate();

            System.out.println(updatedRows + " row(s) have been updated.");

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }
    }

    public boolean insertNewDistributor(final Connection connection) {

        System.out.println("Enter the Distributor Name: ");
        final String name = scanner.nextLine();

        System.out.println("Enter the Distributor Type(Valid Distributor Types:Wholesale/Bookstore/Library): ");
        final String type = scanner.nextLine();

        System.out.println("Enter the Distributor Street Address: ");
        final String streetAddress = scanner.nextLine();

        System.out.println("Enter the Distributor City: ");
        final String city = scanner.nextLine();

        System.out.println("Enter the Distributor Phone Number: ");
        final String phoneNum = scanner.nextLine();

        System.out.println("Enter the Distributor Contact: ");
        final String contact = scanner.nextLine();

        System.out.println("Enter Distributor Initial Balance: ");
        final int balanceAmount = scanner.nextInt();

        try {
            connection.setAutoCommit(false);
            try {
                final String sqlQuery = "INSERT INTO `distributor` (`name`, `type`, `streetAddress`, `city`,`phoneNum`, `contact`, `balanceAmount` ) VALUES (?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, name);
                statement.setString(2, type);
                statement.setString(3, streetAddress);
                statement.setString(4, city);
                statement.setString(5, phoneNum);
                statement.setString(6, contact);
                statement.setInt(7, balanceAmount );

                statement.executeUpdate();

                connection.commit();

                System.out.println("Successfully inserted new Distributor details");
                connection.setAutoCommit(true);
            } catch(Exception e) {
                connection.rollback();
                System.out.println("Transaction rolled back - Exception occurred: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean updateDistributor(final Connection connection) {

        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM orders");
        System.out.println("Enter the Distributor ID you want to update: ");
        final int distributorId = scanner.nextInt();

        System.out.println("What do you want to update?");
        System.out.println("1. Name");
        System.out.println("2. Type");
        System.out.println("3. Street Address");
        System.out.println("4. City");
        System.out.println("5. Phone Number");
        System.out.println("6. Contact\n");
        System.out.println("Enter your choice: \t");
        final int choice = scanner.nextInt();
        scanner.nextLine();
        int updatedRows = 0;

        try {
            connection.setAutoCommit(false);
            try {

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new Name: \t");
                        final String newName = scanner.nextLine();
                        final String nameUpdateSqlQuery = "UPDATE distributor SET name = ? WHERE distributorId = ?;";
                        PreparedStatement nameUpdateStatement = connection.prepareStatement(nameUpdateSqlQuery);
                        nameUpdateStatement.setString(1, newName);
                        nameUpdateStatement.setInt(2, distributorId);
                        updatedRows = nameUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 2:
                        System.out.println("Enter the new Type:Wholesale/Bookstore/Library \t");
                        final String newType = scanner.nextLine();
                        final String typeUpdateSqlQuery = "UPDATE publication SET type = ? WHERE distributorId = ?;";
                        PreparedStatement typeUpdateStatement = connection.prepareStatement(typeUpdateSqlQuery);
                        typeUpdateStatement.setString(1, newType);
                        typeUpdateStatement.setInt(2, distributorId);
                        updatedRows = typeUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 3:
                        System.out.println("Enter the new Street Address: \t");
                        final String newAddress = scanner.nextLine();
                        final String addressUpdateSqlQuery = "UPDATE publication SET streetAddress = ? WHERE distributorId = ?;";
                        PreparedStatement addressUpdateStatement = connection.prepareStatement(addressUpdateSqlQuery);
                        addressUpdateStatement.setString(1, newAddress);
                        addressUpdateStatement.setInt(2, distributorId);
                        updatedRows = addressUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 4:
                        System.out.println("Enter the new City: \t");
                        final int newCity = scanner.nextInt();
                        final String cityUpdateSqlQuery = "UPDATE book SET city = ? WHERE distributorId = ?;";
                        PreparedStatement cityUpdateStatement = connection.prepareStatement(cityUpdateSqlQuery);
                        cityUpdateStatement.setInt(1, newCity);
                        cityUpdateStatement.setInt(2, distributorId);
                        updatedRows = cityUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 5:
                        System.out.println("Enter the Phone Number: \t");
                        final int newPhone = scanner.nextInt();
                        final String phoneUpdateSqlQuery = "UPDATE book SET phoneNum = ? WHERE distributorId = ?;";
                        PreparedStatement phoneUpdateStatement = connection.prepareStatement(phoneUpdateSqlQuery);
                        phoneUpdateStatement.setInt(1, newPhone);
                        phoneUpdateStatement.setInt(2, distributorId);
                        updatedRows = phoneUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 6:
                        System.out.println("Enter the new Contact: \t");
                        final int newContact = scanner.nextInt();
                        final String contactUpdateSqlQuery = "UPDATE book SET contact = ? WHERE distributorId = ?;";
                        PreparedStatement contactUpdateStatement = connection.prepareStatement(contactUpdateSqlQuery);
                        contactUpdateStatement.setInt(1, newContact);
                        contactUpdateStatement.setInt(2, distributorId);
                        updatedRows = contactUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    default:
                        System.out.println("Invalid Input. Please try again");

                }

                connection.setAutoCommit(true);

            } catch (Exception e) {
                connection.rollback();
                System.out.println("Exception Occurred: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    private boolean deleteDistributor(Connection connection) {

       ResultSetService resultSetService = new ResultSetService();
       resultSetService.runQueryAndPrintOutput(connection, "select distributorId, name, type, contact from distributor;");

        System.out.println("Enter the Distributor ID: ");
        final int distributorId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {

                final String sqlQuery = "DELETE FROM `distributor` WHERE `distributorId` = ?;";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, distributorId);

                int updatedRows = statement.executeUpdate();
                connection.commit();

                if(updatedRows == 0) {
                    System.out.println("0 rows deleted. No tuple found with the mentioned details.");
                } else {
                    System.out.println("Successfully deleted " + updatedRows + " row(s).");
                }

                connection.setAutoCommit(true);

            } catch (Exception e) {
                connection.rollback();
                System.out.println("Transaction rolled back - Exception Occurred: " + e.getMessage());
                return false;
            }


        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean placeNewOrder(final Connection connection) {

        try {
            System.out.println("Distributors in the database:");
            resultSetService.runQueryAndPrintOutput(connection, "Select * from distributor;");

            System.out.println("Enter the Distributor ID: ");
            final int distributorId = scanner.nextInt();

            System.out.println("What are you placing order for ?:");
            System.out.println("1. Book Edition");
            System.out.println("2. Issue\n");
            System.out.println("Enter your choice: \t");

            final int choice = scanner.nextInt();

            LocalDate todayObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
            String today = todayObj.format(formatter);


            switch (choice) {
                case 1:
                    System.out.println("Book Editions in the database:");
                    resultSetService.runQueryAndPrintOutput(connection, "select publication.title, editions.*  from editions NATURAL JOIN book NATURAL JOIN publication;");

                    System.out.println("Enter the Publication ID:");
                    final int pid = scanner.nextInt();

                    System.out.println("Enter the Edition Number:");
                    final String editionNumber = scanner.nextLine();

                    System.out.println("Enter the number of copies:");
                    final int numberOfCopies = scanner.nextInt();

                    final String bookEditionPriceQuery = "SELECT price FROM editions where pid=? AND edition_number = ?;";
                    PreparedStatement bookEditionPriceStatement = connection.prepareStatement(bookEditionPriceQuery);
                    bookEditionPriceStatement.setInt(1, pid);
                    bookEditionPriceStatement.setString(2, editionNumber);
                    final ResultSet bookResultSet = bookEditionPriceStatement.executeQuery();

                    double price = 0;
                    if(bookResultSet.next()) {
                        price = bookResultSet.getDouble(1);
                    } else {
                        System.out.println("Could not find a book edition with the provided details. Please try again.");
                        return false;
                    }

                    System.out.println("Enter the Shipping Cost:");
                    final double shippingCost = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter the delivery date (yyyy-mm-dd): ");
                    final String deliveryDate = scanner.nextLine();

                    connection.setAutoCommit(false);

                    try {

                        final String sqlQuery = "INSERT INTO `orders` (`distributorId`, `shipCost`, `orderDate`, `price`,`deliveryDate`, `status`) VALUES (?, ?, ?, ?, ?, ?);";
                        PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                        statement.setInt(1, distributorId);
                        statement.setDouble(2, shippingCost);

                        statement.setString(3, today);
                        statement.setDouble(4, price*numberOfCopies);

                        statement.setString(5, deliveryDate);
                        statement.setString(6, "IN_PROGRESS");
                        statement.executeUpdate();

                        ResultSet rs = statement.getGeneratedKeys();

                        int orderId = -1;

                        if (rs.next()) {
                            orderId = rs.getInt(1);
                        } else {
                            throw new SQLException("Could not insert into table orders");
                        }

                        final String includesSqlQuery = "INSERT INTO `includes` (`orderId`, `pid`, `edition_number`, `number_of_copies`) VALUES (?, ?, ?, ?);";
                        PreparedStatement includesStatement = connection.prepareStatement(includesSqlQuery);
                        includesStatement.setInt(1, orderId);
                        includesStatement.setInt(2, pid);
                        includesStatement.setString(3, editionNumber);
                        includesStatement.setInt(4, numberOfCopies);
                        includesStatement.executeUpdate();

                        final double totalPrice = (price*numberOfCopies) + shippingCost;

                        final String DistributorQuery = "SELECT * FROM distributor where distributorId=?;";
                        PreparedStatement DistributorStatement = connection.prepareStatement(DistributorQuery);
                        DistributorStatement.setInt(1, distributorId);
                        final ResultSet bookResultSet2 = DistributorStatement.executeQuery();

                        double did = 0;
                        if(bookResultSet2.next()) {
                            price = bookResultSet2.getDouble(1);
                        } else {
                            System.out.println("Could not find a book edition with the provided details. Please try again.");
                            return false;
                        }
                        resultSetService.runQueryAndPrintOutput(connection, "Select * from distributor;");

                        final String updateBalanceAmountQuery = "UPDATE `distributor` SET `balanceAmount` = `balanceAmount` + ? WHERE (`distributorId` = ?);";
                        final PreparedStatement updateBalanceAmountStatement = connection.prepareStatement(updateBalanceAmountQuery);
                        updateBalanceAmountStatement.setDouble(1, totalPrice);
                        updateBalanceAmountStatement.setInt(2, distributorId);
                        updateBalanceAmountStatement.executeUpdate();


                        connection.commit();

                        System.out.println("Order Successfully placed and the details are as follows:");
                        System.out.println("Order Id: " + orderId);
                        System.out.println("Total Price: " + totalPrice);
                        System.out.println("Order Placed On: " + today);
                        System.out.println("Delivered On: " + deliveryDate);

                    } catch (SQLException e) {
                        connection.rollback();
                        System.out.println("Exception Occurred. Transaction Rolled Back.");
                        return false;
                    }

                    break;
                case 2:
                    System.out.println("Issues in the database:");
                    resultSetService.runQueryAndPrintOutput(connection, "select publication.title, issues.*, periodic_publication.periodicity from issues NATURAL JOIN periodic_publication NATURAL JOIN publication;");

                    System.out.println("Enter the Publication ID:");
                    final int pid2 = scanner.nextInt();

                    System.out.println("Enter the Issue ID:");
                    final int issueId = scanner.nextInt();

                    System.out.println("Enter the number of copies:");
                    final int numberOfCopies2 = scanner.nextInt();

                    final String issuePriceQuery = "SELECT price FROM issues where pid=? AND issueId = ?;";
                    PreparedStatement issuePriceStatement = connection.prepareStatement(issuePriceQuery);
                    issuePriceStatement.setInt(1, pid2);
                    issuePriceStatement.setInt(2, issueId);
                    final ResultSet issueResultSet = issuePriceStatement.executeQuery();

                    double price2 = 0;
                    if(issueResultSet.next()) {
                        price2 = issueResultSet.getDouble(1);
                    } else {
                        System.out.println("Could not find a book edition with the provided details. Please try again.");
                        return false;
                    }

                    System.out.println("Enter the Shipping Cost:");
                    final double shippingCost2 = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter the delivery date (yyyy-mm-dd): ");
                    final String deliveryDate2 = scanner.nextLine();

                    connection.setAutoCommit(false);

                    try {

                        final String sqlQuery = "INSERT INTO `orders` (`distributorId`, `shipCost`, `orderDate`, `price`,`deliveryDate`, `status`) VALUES (?, ?, ?, ?, ?, ?);";
                        PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                        statement.setInt(1, distributorId);
                        statement.setDouble(2, shippingCost2);

                        statement.setString(3, today);
                        statement.setDouble(4, price2*numberOfCopies2);

                        statement.setString(5, deliveryDate2);
                        statement.setString(6, "IN_PROGRESS");
                        statement.executeUpdate();

                        ResultSet rs = statement.getGeneratedKeys();

                        int orderId = -1;

                        if (rs.next()) {
                            orderId = rs.getInt(1);
                        } else {
                            throw new SQLException("Could not insert into table orders");
                        }

                        final String includesSqlQuery = "INSERT INTO `consists` (`orderId`, `pid`, `issueId`, `number_of_copies`) VALUES (?, ?, ?, ?);";
                        PreparedStatement includesStatement = connection.prepareStatement(includesSqlQuery);
                        includesStatement.setInt(1, orderId);
                        includesStatement.setInt(2, pid2);
                        includesStatement.setInt(3, issueId);
                        includesStatement.setInt(4, numberOfCopies2);
                        includesStatement.executeUpdate();

                        final double totalPrice2 = (price2*numberOfCopies2) + shippingCost2;

                        resultSetService.runQueryAndPrintOutput(connection, "Select * from distributor;");

                        final String updateBalanceAmountQuery = "UPDATE `distributor` SET `balanceAmount` = `balanceAmount` + ? WHERE (`distributorId` = ?);";
                        final PreparedStatement updateBalanceAmountStatement = connection.prepareStatement(updateBalanceAmountQuery);
                        updateBalanceAmountStatement.setDouble(1, totalPrice2);
                        updateBalanceAmountStatement.setInt(2, distributorId);
                        updateBalanceAmountStatement.executeUpdate();

                        connection.commit();

                        System.out.println("Order Successfully placed and the details are as follows:");
                        System.out.println("Order Id: " + orderId);
                        System.out.println("Total Price: " + totalPrice2);
                        System.out.println("Order Placed On: " + today);
                        System.out.println("Delivered On: " + deliveryDate2);

                    } catch (SQLException e) {
                        connection.rollback();
                        System.out.println("Exception Occurred. Transaction Rolled Back.");
                        return false;
                    }
                    break;
                default:
                    System.out.println("Invalid Option. Please try again");
                    return true;

            }

            return true;

        } catch (Exception e) {
            System.out.println("Exception Occurred: "  + e.getMessage());
            return false;
        }


    }

    public boolean insertNewBillDistributor(final Connection connection) {

        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from staff where title = 'Billing Staff';");

        System.out.println("Enter the Billing Staff ID:");
        final int sid = scanner.nextInt();

        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from distributor where title = 'Billing Staff';");
        System.out.println("Enter the distributor ID:");
        final int distributorId = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Enter Bill Start Date (yyyy-mm-dd):");
        final String billStartDate = scanner.nextLine();

        System.out.println("Enter Bill End Date (yyyy-mm-dd):");
        final String billEndDate = scanner.nextLine();

        LocalDate todayObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = todayObj.format(formatter);

        try {
            final String sqlQuery = "SELECT SUM(price) + SUM(shipCost) as 'totalPrice' from orders where distributorId = ? AND orderDate BETWEEN ? AND ?;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, distributorId);
            statement.setString(2, billStartDate);
            statement.setString(3, billEndDate);

            final ResultSet resultSet = statement.executeQuery();

            double price = 0;
            if(resultSet.next()) {
                price = resultSet.getDouble(1);
            } else {
                System.out.println("Could not find orders with the provided details. Please try again.");
                return false;
            }

            final String billQuery = "INSERT INTO `bills` (`totalBill`, `billDate`, `sid`, `distributorId`) VALUES (?, ?, ?, ?);";
            PreparedStatement statement2 = connection.prepareStatement(billQuery);
            statement2.setDouble(1, price);
            statement2.setString(2, today);
            statement2.setInt(3, sid);
            statement2.setInt(4, distributorId);
            statement2.executeUpdate();

            System.out.println("Bill generated and sent to the client.");

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }
        return true;
    }

    public boolean updateDistributorOutstandingBalance(final Connection connection) {

        System.out.println("Enter the Distributor ID you want to update: ");
        final int distributorId = scanner.nextInt();

        System.out.println("How would you like to update the Balance?");
        System.out.println("1. Add New Total Balance");
        System.out.println("2. Add to existing Balance");
        System.out.println("3. Deduct from existing Balance\n");
        System.out.println("Enter your choice: \t");
        final int choice = scanner.nextInt();
        scanner.nextLine();
        int updatedRows = 0;

        try {
            connection.setAutoCommit(false);
            try {

                switch (choice) {
                    case 1:
                        resultSetService.runQueryAndPrintOutput(connection, "Select * from distributor;");
                        System.out.println("Enter the new Total Balance: \t");
                        final String newBalance = scanner.nextLine();
                        final String balanceUpdateSqlQuery = "UPDATE distributor SET balanceAmount = ? WHERE distributorId = ?;";
                        PreparedStatement balanceUpdateStatement = connection.prepareStatement(balanceUpdateSqlQuery);
                        balanceUpdateStatement.setString(1, newBalance);
                        balanceUpdateStatement.setInt(2, distributorId );
                        updatedRows = balanceUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 2:
                        System.out.println("Enter the amount to be added to the Balance: \t");
                        final String addBalance = scanner.nextLine();
                        final String  addnewBalance = "SELECT balanceAmount FROM distributor where distributorId=?;";
                        PreparedStatement statement = connection.prepareStatement(addnewBalance);
                        statement.setInt(1, distributorId);
                        ResultSet resultSet = statement.executeQuery();
                        double amount = getAmount(resultSet);
                        final String baladdUpdateSqlQuery = "UPDATE distributor set balanceAmount = ? WHERE distributorId = ?;";
                        PreparedStatement baladdUpdateStatement = connection.prepareStatement(baladdUpdateSqlQuery);
                        double total = amount + Integer.parseInt(addBalance);
                        baladdUpdateStatement.setDouble(1, total);
                        baladdUpdateStatement.setInt(2, distributorId);
                        updatedRows = baladdUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 3:
                        System.out.println("Enter the amount to be deducted from the Balance: \t");
                        final String subBalance = scanner.nextLine();
                        final String  subnewBalance = "SELECT balanceAmount FROM distributor where distributorId=?;";
                        PreparedStatement statement1 = connection.prepareStatement(subnewBalance);
                        statement1.setInt(1, distributorId);
                        ResultSet resultSet1 = statement1.executeQuery();
                        double amount1 = getAmount(resultSet1);
                        final String balsubUpdateSqlQuery = "UPDATE distributor set balanceAmount = ? WHERE distributorId = ?;";
                        PreparedStatement balsubUpdateStatement = connection.prepareStatement(balsubUpdateSqlQuery);
                        double total1 = amount1 - Integer.parseInt(subBalance);
                        balsubUpdateStatement.setDouble(1, total1);
                        balsubUpdateStatement.setInt(2, distributorId);
                        updatedRows = balsubUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    default:
                        System.out.println("Invalid Input. Please try again");

                }

                connection.setAutoCommit(true);

            } catch (Exception e) {
                connection.rollback();
                System.out.println("Exception Occurred: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    private double getAmount(ResultSet resultSet) throws SQLException {

        List<String> headerColumns = getHeaderColumns(resultSet);
        String columnValue = "";
        while(resultSet.next()) {
            for(int i=1; i<=headerColumns.size(); i++) {
                 columnValue = resultSet.getString(i);
            }
        }
        return Double.parseDouble(columnValue);
    }

    private List<String> getHeaderColumns(final ResultSet resultSet) throws SQLException {
        List<String> headerColumns = new ArrayList<>();
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        int count = rsMetaData.getColumnCount();
        for(int i=1; i<=count; i++) {
            headerColumns.add(rsMetaData.getColumnLabel(i));
        }
        return headerColumns;
    }


}
