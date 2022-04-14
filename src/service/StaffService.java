package service;

import java.sql.*;
import java.util.Scanner;

public class StaffService {

    Scanner scanner = new Scanner(System.in);
    ResultSetService resultSetService = new ResultSetService();

    public void run(final Connection connection) {
        try {

            while (true) {
                System.out.println("Choose from the following:");
                System.out.println("1. Insert a new Staff member");
                System.out.println("2. Delete a Staff member");
                System.out.println("3. Return to Main Menu\n");
                System.out.println("Enter your choice: \t");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        insertNewStaffMember(connection);
                        break;
                    case 2:
                        deleteStaffMember(connection);
                        break;
                    case 3:
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

    private int getChoice() {
        System.out.println("Which role is the staff member in?");
        System.out.println("1. Admin");
        System.out.println("2. Billing Staff");
        System.out.println("3. Manager");
        System.out.println("4. Editor");
        System.out.println("5. Author\n");

        System.out.println("6. Return to Previous Menu\n");

        System.out.println("Enter your choice: \t");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public void deleteStaffMember(final Connection connection) {
        System.out.println("Staff members present in the database:");
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM staff;");

        System.out.println("Enter the sid of the staff member you want to delete:");
        final int sid = scanner.nextInt();

        try {
            final String sqlQuery = "DELETE FROM `staff` WHERE (`sid` = ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, sid);

            int updatedRows = statement.executeUpdate();

            if(updatedRows == 0) {
                System.out.println("0 rows deleted. No tuple found with the mentioned details.");
            } else {
                System.out.println("Successfully deleted " + updatedRows + " row(s).");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public void insertNewStaffMember(final Connection connection) {


        final int choice = getChoice();

        if(choice == 6) {
            return;
        }

        System.out.println("Enter the Name: ");
        final String name = scanner.nextLine();

        System.out.println("Enter the date of birth (yyyy-mm-dd): ");
        final String dob = scanner.nextLine();

        System.out.println("Enter the address: ");
        final String address = scanner.nextLine();

        System.out.println("Enter the Phone Number: ");
        final String phoneNumber = scanner.nextLine();

        System.out.println("Enter the emailId: ");
        final String emailId = scanner.nextLine();

        System.out.println("Enter the hire date (yyyy-mm-dd): ");
        final String hiredate = scanner.nextLine();

        String title = "";

        try {
            try {
                connection.setAutoCommit(false);

                final String staffAdminQuery = "INSERT INTO `staff` (`name`, `dob`, `address`, `phone`, `emailID`, `hireDate`, `title`) VALUES (?,?,?,?,?,?,?);";
                PreparedStatement statement1 = connection.prepareStatement(staffAdminQuery, Statement.RETURN_GENERATED_KEYS);
                statement1.setString(1, name);
                statement1.setString(2, dob);
                statement1.setString(3, address);
                statement1.setString(4, phoneNumber);
                statement1.setString(5, emailId);
                statement1.setString(6, hiredate);

                int sid = -1;

                switch (choice) {
                    case 1:
                        title = "Administrator";
                        System.out.println("Enter the Staff SSN: ");
                        final String ssn = scanner.nextLine();

                        statement1.setString(7, title);
                        statement1.executeUpdate();
                        ResultSet resultSet = statement1.getGeneratedKeys();

                        if (resultSet.next()) {
                            sid = resultSet.getInt(1);
                        } else {
                            throw new SQLException("Could not insert into table staff");
                        }

                        final String adminQuery = "INSERT INTO `admin` (`ssn`, `sid`) VALUES (?,?);";
                        final PreparedStatement statement2 = connection.prepareStatement(adminQuery);
                        statement2.setString(1, ssn);
                        statement2.setInt(2, sid);
                        statement2.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully Inserted new staff member with sid=" + sid);
                        break;
                    case 2:
                        title = "Billing Staff";
                        System.out.println("Enter the Counter Number: ");
                        final int counterNumber = scanner.nextInt();

                        statement1.setString(7, title);
                        statement1.executeUpdate();
                        ResultSet resultSet2 = statement1.getGeneratedKeys();

                        if (resultSet2.next()) {
                            sid = resultSet2.getInt(1);
                        } else {
                            throw new SQLException("Could not insert into table staff");
                        }

                        final String billQuery = "INSERT INTO `bill_staff` (`sid`, `counter_number`) VALUES (?,?);";
                        final PreparedStatement billStaffStatement = connection.prepareStatement(billQuery);
                        billStaffStatement.setInt(1, sid);
                        billStaffStatement.setInt(2, counterNumber);
                        billStaffStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully Inserted new staff member with sid=" + sid);
                        break;
                    case 3:
                        title = "Manager";
                        System.out.println("Enter the Grade: ");
                        final String grade = scanner.nextLine();

                        statement1.setString(7, title);
                        statement1.executeUpdate();
                        ResultSet resultSet3 = statement1.getGeneratedKeys();

                        if (resultSet3.next()) {
                            sid = resultSet3.getInt(1);
                        } else {
                            throw new SQLException("Could not insert into table staff");
                        }

                        final String managementQuery = "INSERT INTO `management` (`sid`, `grade`) VALUES (?,?);";
                        final PreparedStatement managementStatement = connection.prepareStatement(managementQuery);
                        managementStatement.setInt(1, sid);
                        managementStatement.setString(2, grade);
                        managementStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully Inserted new staff member with sid=" + sid);
                        break;
                    case 4:
                        title = "Editor";
                        System.out.println("Is he Invited? ");
                        System.out.println("1. Yes ");
                        System.out.println("2. No ");
                        System.out.println("Any other input will be considered as Yes\n");

                        System.out.println("Enter your choice: \t");
                        int isInvitedChoice = scanner.nextInt();

                        int isInvited = 1;
                        switch (isInvitedChoice) {
                            case 1:
                                isInvited = 1;
                                break;
                            case 2:
                                isInvited = 0;
                                break;
                        }

                        System.out.println("Enter the person's salary/toPay amount: \t");
                        double toPay = scanner.nextDouble();

                        scanner.nextLine();

                        System.out.println("Enter the pay-due/paycheck date (yyyy-mm-dd):");
                        final String date = scanner.nextLine();


                        statement1.setString(7, title);
                        statement1.executeUpdate();
                        ResultSet resultSet4 = statement1.getGeneratedKeys();

                        if (resultSet4.next()) {
                            sid = resultSet4.getInt(1);
                        } else {
                            throw new SQLException("Could not insert into table staff");
                        }

                        final String editorQuery = "INSERT INTO `editor` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (?,?,?,?);";
                        final PreparedStatement editorStatement = connection.prepareStatement(editorQuery);
                        editorStatement.setInt(1, sid);
                        editorStatement.setInt(2, isInvited);
                        editorStatement.setDouble(3, toPay);
                        editorStatement.setString(4, date);
                        editorStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully Inserted new staff member with sid=" + sid);
                        break;
                    case 5:
                        title = "Author";
                        System.out.println("Is he Invited? ");
                        System.out.println("1. Yes ");
                        System.out.println("2. No ");
                        System.out.println("Any other input will be considered as Yes\n");

                        System.out.println("Enter your choice: \t");
                        int isInvitedChoice2 = scanner.nextInt();

                        int isInvited2 = 1;
                        switch (isInvitedChoice2) {
                            case 1:
                                isInvited2 = 1;
                                break;
                            case 2:
                                isInvited2 = 0;
                                break;
                        }

                        System.out.println("Enter the person's salary/toPay amount: \t");
                        double toPay2 = scanner.nextDouble();

                        scanner.nextLine();

                        System.out.println("Enter the pay-due/paycheck date (yyyy-mm-dd):");
                        final String date2 = scanner.nextLine();


                        statement1.setString(7, title);
                        statement1.executeUpdate();
                        ResultSet resultSet5 = statement1.getGeneratedKeys();

                        if (resultSet5.next()) {
                            sid = resultSet5.getInt(1);
                        } else {
                            throw new SQLException("Could not insert into table staff");
                        }

                        final String authorQuery = "INSERT INTO `author` (`sid`, `isInvited`, `toPay`, `payDueDate`) VALUES (?,?,?,?);";
                        final PreparedStatement authorStatement = connection.prepareStatement(authorQuery);
                        authorStatement.setInt(1, sid);
                        authorStatement.setInt(2, isInvited2);
                        authorStatement.setDouble(3, toPay2);
                        authorStatement.setString(4, date2);
                        authorStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully Inserted new staff member with sid=" + sid);
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid Entry, please try again.");
                }
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Exception Occurred: " + e.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }
    }
}
