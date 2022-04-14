package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ViewingService {

    Scanner scanner = new Scanner(System.in);
    ResultSetService resultSetService = new ResultSetService();

    public void run(final Connection connection) {

        try {

            while (true) {
                System.out.println("Choose from the following:");
                System.out.println("1. View Staff Members");
                System.out.println("2. View All Orders");
                System.out.println("3. View All Distributors");
                System.out.println("4. View all Payments");
                System.out.println("5. View All Publications");
                System.out.println("\n6. Return to Main Menu\n");
                System.out.println("Enter your choice: \t");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewStaffMembers(connection);
                        break;
                    case 2:
                        System.out.println("1. All Orders");
                        System.out.println("2. Order Details by ID\n");
                        System.out.println("3. Return to Previous Menu");
                        System.out.println("Enter your choice:\t");

                        int choice2 = scanner.nextInt();

                        switch (choice2) {
                            case 1:
                                resultSetService.runQueryAndPrintOutputRowFormat(connection, "SELECT orders.*,(orders.shipCost + orders.price) AS 'Total Order Amount',distributor.name AS 'Distributor Name', distributor.type AS 'Distributor Type', publication.publication_type, COALESCE(includes.number_of_copies, consists.number_of_copies) AS 'Number of Copies', COALESCE(includes.pid, consists.pid) AS 'Publication Id', COALESCE(includes.edition_number, consists.issueId) AS 'Edition/Issue', publication.title FROM orders NATURAL JOIN distributor LEFT OUTER JOIN includes ON includes.orderId = orders.orderId LEFT OUTER JOIN consists ON consists.orderId = orders.orderId LEFT OUTER JOIN publication ON (consists.pid = publication.pid OR includes.pid = publication.pid);");
                                break;
                            case 2:
                                try {
                                    System.out.println("Enter the OrderId:");
                                    final int orderId = scanner.nextInt();
                                    final String sqlQuery = "SELECT orders.*,(orders.shipCost + orders.price) AS 'Total Order Amount',distributor.name AS 'Distributor Name', distributor.type AS 'Distributor Type', publication.publication_type, COALESCE(includes.number_of_copies, consists.number_of_copies) AS 'Number of Copies', COALESCE(includes.pid, consists.pid) AS 'Publication Id', COALESCE(includes.edition_number, consists.issueId) AS 'Edition/Issue', publication.title FROM orders NATURAL JOIN distributor LEFT OUTER JOIN includes ON includes.orderId = orders.orderId LEFT OUTER JOIN consists ON consists.orderId = orders.orderId LEFT OUTER JOIN publication ON (consists.pid = publication.pid OR includes.pid = publication.pid) WHERE orders.orderId = ?;";
                                    PreparedStatement statement = connection.prepareStatement(sqlQuery);
                                    statement.setInt(1, orderId);
                                    ResultSet resultSet = statement.executeQuery();
                                    resultSetService.viewFromResultSet(resultSet);
                                }  catch (Exception e) {
                                    System.out.println("Exception Occurred: " + e.getMessage());
                                }

                                break;
                            case 3:
                                return;
                            default:
                                System.out.println("Incorrect input. Please try again.");

                        }


                        break;
                    case 3:
                        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM distributor;");
                        break;
                    case 4:
                        resultSetService.runQueryAndPrintOutput(connection, "SELECT payment.*, staff.name, staff.title FROM payment NATURAL JOIN staff;");
                        break;
                    case 5:
                        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM publication;");
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid Input, Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }

    }

    public void viewStaffMembers(final Connection connection) throws SQLException {

        System.out.println("1. ALL staff members");
        System.out.println("2. Authors");
        System.out.println("3. Editors");
        System.out.println("4. Management");
        System.out.println("5. Bill Staff");
        System.out.println("6. Administrators");

        System.out.println("7. Return to Main Menu");

        final int choice = scanner.nextInt();

        final String sqlQuery = "SELECT * FROM staff where title=";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);

        switch (choice) {
            case 1:
                resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM staff;");
                break;
            case 2:
                resultSetService.runQueryAndPrintOutput(connection, sqlQuery + "'Author'");
                break;
            case 3:
                resultSetService.runQueryAndPrintOutput(connection, sqlQuery + "'Editor'");
                break;
            case 4:
                resultSetService.runQueryAndPrintOutput(connection, sqlQuery + "'Manager'");
                break;
            case 5:
                resultSetService.runQueryAndPrintOutput(connection, sqlQuery + "'Billing Staff'");
                break;
            case 6:
                resultSetService.runQueryAndPrintOutput(connection, sqlQuery + "'Administrator'");
                break;
            case 7:
                return;
            default:
                System.out.println("Invalid Input. Returning to Main Menu");
                return;
        }
    }

}
