package service;

import com.mysql.cj.jdbc.SuspendableXAConnection;

import java.sql.*;
import java.util.Scanner;

public class PaymentService {

    Scanner scanner = new Scanner(System.in);
    ResultSetService resultSetService = new ResultSetService();

    public void run(final Connection connection) {
        try {

            while (true) {
                System.out.println("Choose from the following:");
                System.out.println("1. Input amount from Distributor");
                System.out.println("2. View all transactions\n");
                System.out.println("3. Return to Main Menu\n");
                System.out.println("Enter your choice: \t");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        insertTransaction(connection);
                        break;
                    case 2:
                        resultSetService.runQueryAndPrintOutput(connection, "Select distributor.name, transactions.* from transactions NATURAL JOIN distributor;");
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

    public boolean insertTransaction(final Connection connection) {

        try {
            try {

                connection.setAutoCommit(false);

                System.out.println("Distributors in the database:");
                resultSetService.runQueryAndPrintOutput(connection, "Select * from distributor;");

                System.out.println("Enter the Distributor ID:");
                final int distributorId = scanner.nextInt();

                System.out.println("Enter the amount given by the distributor:");
                final double amount = scanner.nextDouble();

                scanner.nextLine();

                System.out.println("Enter the Transaction Date(yyyy-mm-dd):");
                final String date = scanner.nextLine();

                final String sqlQuery = "INSERT INTO `transactions` (`distributorId`, `amount`, `transaction_date`) VALUES (?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, distributorId);
                statement.setDouble(2, amount);
                statement.setString(3, date);

                statement.executeUpdate();

                final String updateBalanceAmountQuery = "UPDATE `distributor` SET `balanceAmount` = `balanceAmount` - ? WHERE (`distributorId` = ?);";
                final PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalanceAmountQuery);
                updateBalanceStatement.setDouble(1, amount);
                updateBalanceStatement.setInt(2, distributorId);
                updateBalanceStatement.executeUpdate();

                connection.commit();

                System.out.println("Added transaction inside the database");

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
}
