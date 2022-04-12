package service;

import constants.MYSQL_CONSTANTS;

import java.sql.*;
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
                System.out.println("4. Insert new order for a Book Edition\n");
                System.out.println("5. Insert new order for an Issue");

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
                        insertNewBillDistributor(connection);
                        break;
                    case 6:
                        updateDistributorOutstandingBalance(connection);
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Invalid Input");

                }

                if (choice == 7) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
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
       resultSetService.runQueryAndPrintOutput(connection, "select distributorId, name, type, contact from distributor");

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

        System.out.println("Enter the Distributor ID you want to update: ");
        final int distributorId = scanner.nextInt();

        System.out.println("What are you placing order for ?:");
        System.out.println("1. Book Edition");
        System.out.println("2. Issue\n");
        System.out.println("Enter your choice: \t");
        final int choice = scanner.nextInt();
        scanner.nextLine();
        int updatedRows = 0;

        return true;
    }

    public boolean insertNewBillDistributor(final Connection connection) {

        System.out.println(("Do you want to place order for edition of a book or an issue of article? "));
        System.out.println("1. Place order for Book Edition");
        System.out.println(("2. Place order for Issue of Article"));
        System.out.println("Enter your choice : \t");

        final int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the Distributor ID for whom you want to place the order \t");
        final String distributorId = scanner.nextLine();

        System.out.println("Enter the Shipping cost for the distributor location: ");
        final int shipCost = scanner.nextInt();

        System.out.println("Enter the order date (yyyy-mm-dd):");
        final String orderDate = scanner.nextLine();

        System.out.println("Enter the price ");
        final int price = scanner.nextInt();

        System.out.println("Enter the number of copies you want to place order for:");
        final int number_of_copies = scanner.nextInt();

        System.out.println("Enter the Delivery Date (yyyy-mm-dd): ");
        final String deliveryDate = scanner.nextLine();

        System.out.println("Enter the Status: ");
        final String status = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            try {

                switch (choice) {
                    case 1:
                        ResultSetService resultSetService = new ResultSetService();
                        resultSetService.runQueryAndPrintOutput(connection, "select * from editions");

                        System.out.println(("Enter the edition number of the book for current order:"));
                        final int edition_number = scanner.nextInt();

                        final String pid = "SELECT pid FROM editions where edition_number = ?;";
                        PreparedStatement e_statement = connection.prepareStatement(pid);
                        e_statement.setInt(1, edition_number);

                        final String sqlQuery = "INSERT INTO `orders` (`distributorId`, `shipCost`, `orderDate`, `price`,`deliveryDate`, `status`) VALUES (?, ?, ?, ?, ?, ?);";
                        PreparedStatement statement = connection.prepareStatement(sqlQuery);
                        statement.setString(1, distributorId);
                        statement.setInt(2, shipCost);
                        statement.setString(3, orderDate);
                        statement.setInt(4, price);
                        statement.setString(5, deliveryDate);
                        statement.setString(6, status);
                        statement.executeUpdate();

                        ResultSet rs = statement.getGeneratedKeys();

                        int orderId = -1;

                        if (rs.next()) {
                            orderId = rs.getInt(1);
                        } else {
                            throw new SQLException("Could not insert into table orders");
                        }

                        final String sqlQuery1 = "INSERT INTO `includes` (`orderId`, `pid`, `edition_number`, `number_of_copies`) VALUES (?, ?, ?, ?);";
                        PreparedStatement statement1 = connection.prepareStatement(sqlQuery1);
                        statement1.setInt(1, orderId);
                        statement1.setInt(2, Integer.parseInt(pid));
                        statement1.setInt(3, edition_number);
                        statement1.setInt(4, number_of_copies);
                        statement1.executeUpdate();

                        final int total = (price * number_of_copies)+shipCost;
                        connection.commit();

                        System.out.printf("Successfully placed order for the Book Edition. The Total Bill amount for ths order is : %d", total );
                        connection.setAutoCommit(true);
                        break;

                    case 2:
                        ResultSetService resultSetService1 = new ResultSetService();
                        resultSetService1.runQueryAndPrintOutput(connection, "select * from issues");

                        System.out.println(("Enter the issue id of the article for current order:"));
                        final int issueId = scanner.nextInt();

                        final String pid1 = "SELECT pid FROM issues where issueId = ?;";
                        PreparedStatement i_statement = connection.prepareStatement(pid1);
                        i_statement.setInt(1, issueId);

                        final String sqlQuery2 = "INSERT INTO `orders` (`distributorId`, `shipCost`, `orderDate`, `price`,`deliveryDate`, `status`) VALUES (?, ?, ?, ?, ?, ?);";
                        PreparedStatement statement2 = connection.prepareStatement(sqlQuery2);
                        statement2.setString(1, distributorId);
                        statement2.setInt(2, shipCost);
                        statement2.setString(3, orderDate);
                        statement2.setInt(4, price);
                        statement2.setString(5, deliveryDate);
                        statement2.setString(6, status);
                        statement2.executeUpdate();

                        ResultSet res = statement2.getGeneratedKeys();

                        int order_id = -1;

                        if (res.next()) {
                            order_id = res.getInt(1);
                        } else {
                            throw new SQLException("Could not insert into table orders");
                        }

                        final String sqlQuery3 = "INSERT INTO `consists` (`orderId`, `pid`, `issueId`, `number_of_copies`) VALUES (?, ?, ?, ?);";
                        PreparedStatement statement3 = connection.prepareStatement(sqlQuery3);
                        statement3.setInt(1, order_id);
                        statement3.setInt(2, Integer.parseInt(pid1));
                        statement3.setInt(3, issueId);
                        statement3.setInt(4, number_of_copies);
                        statement3.executeUpdate();

                        final int total1 = (price * number_of_copies)+shipCost;
                        connection.commit();

                        System.out.printf("Successfully placed order for the Issue of Article. The Total Bill amount for ths order is : %d", total1 );
                        connection.setAutoCommit(true);
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
