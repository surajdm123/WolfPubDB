import service.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        ConnectionHelper connectionHelper = new ConnectionHelper();
        Connection connection = null;

        try {
            try {

                connection = connectionHelper.getConnection();

                Scanner scanner = new Scanner(System.in);

                System.out.println("-----Welcome to WolfCity Publishing House-----\n\n");

                while (true) {
                    System.out.println("Choose an operation you want to perform:");
                    System.out.println("1. Editing and Publishing");
                    System.out.println("2. Production");
                    System.out.println("3. Distribution");
                    System.out.println("4. Reports");
                    System.out.println("5. Staff Processing");
                    System.out.println("6. Exit\n");

                    System.out.println("Enter your choice: \t");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            EditingPublishingService editingPublishingService = new EditingPublishingService();
                            editingPublishingService.run(connection);
                            break;

                        case 3:
                            DistributionService distributionService = new DistributionService();
                            distributionService.run(connection);
                            break;

                        case 4:
                            ReportsService reportsService = new ReportsService();
                            reportsService.run(connection);
                            break;

                        case 5:
                            StaffService staffService = new StaffService();
                            staffService.run(connection);
                            break;

                        case 6:
                            System.exit(1);
                            break;

                        default:
                            System.out.println("Invalid Input, Please try again.");
                    }

                }
            } catch (SQLException e) {
                System.out.println("Exception Occurred: " + e.getMessage());
            } finally {
                if(connection != null) {
                    connection.close();
                    System.out.println("Connection closed successfully");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }

    }

}
