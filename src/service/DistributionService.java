package service;

import java.sql.Connection;
import java.util.Scanner;

public class DistributionService {

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
                        insertNewOrderBookEdition(connection);
                        break;
                    case 5:
                        insertNewOrderIssue(connection);
                        break;
                    case 6:
                        insertNewBillDistributor(connection);
                        break;
                    case 7:
                        updateDistributorOutstandingBalance(connection);
                        break;
                    case 8:
                        break;
                    default:
                        System.out.println("Invalid Input");

                }

                if (choice == 8) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occured: " + e.getMessage());
        }

    }

    private boolean insertNewDistributor(Connection connection) {
        return true;
    }

    private boolean updateDistributor(Connection connection) {
        return true;
    }

    private boolean deleteDistributor(Connection connection) {
        return true;
    }

    private boolean insertNewOrderBookEdition(Connection connection) {
        return true;
    }

    private boolean insertNewOrderIssue(Connection connection) {
        return true;
    }

    private boolean insertNewBillDistributor(Connection connection) {
        return true;
    }

    private boolean updateDistributorOutstandingBalance(Connection connection) {
        return true;
    }


}
