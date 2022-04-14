package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ReportsService {

    Scanner scanner = new Scanner(System.in);
    ResultSetService resultSetService = new ResultSetService();

    public void run(final Connection connection) {

        try {
            /* Display list of operations for user to select */
            while (true) {
                System.out.println("\n\nREPORTS:");
                System.out.println("1. Get Purchase Summary per Distributor per month");
                System.out.println("2. Get total revenue of the Publishing house");
                System.out.println("3. Get total expenses of the publishing house");
                System.out.println("4. Get total number of Distributors\n");

                System.out.println("REVENUE:");
                System.out.println("5. Get total revenue per city");
                System.out.println("6. Get total revenue per distributor");
                System.out.println("7. Get total revenue per location\n");

                System.out.println("PAYMENTS:");
                System.out.println("8. Get total payment for Authors per time period");
                System.out.println("9. Get total payment for Editors per time period");
                System.out.println("10. Get total payment for Authors per work type");
                System.out.println("11. Get total payment for Editors per work type\n");

                System.out.println("12. Return to Main Menu\n");

                System.out.println("Enter your choice:");

                /* store choice in a variable */
                int choice = scanner.nextInt();
                scanner.nextLine();

                /* execute operation based on the choice */
                switch (choice) {
                    case 1:
                        getPurchaseSummary(connection);
                        break;
                    case 2:
                        getRevenuePublishingHouse(connection);
                        break;
                    case 3:
                        getExpensePublishingHouse(connection);
                        break;
                    case 4:
                        getDistributorsCount(connection);
                        break;
                    case 5:
                        getRevenuePerCity(connection);
                        break;
                    case 6:
                        getRevenuePerDistributor(connection);
                        break;
                    case 7:
                        getRevenuePerLocation(connection);
                        break;
                    case 8:
                        getAuthorPaymentPerTimePeriod(connection);
                        break;
                    case 9:
                        getEditorPaymentPerTimePeriod(connection);
                    case 10:
                        getAuthorPaymentPerWorkType(connection);
                        break;
                    case 11:
                        getEditorPaymentPerWorkType(connection);
                        break;
                    case 12:
                        break;
                    default:
                        System.out.println("Invalid Input");

                }

                if (choice == 12) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occured: " + e.getMessage());
        }

    }


    /* Method to generate monthly reports: number and total price of copies of each publication bought per distributor per month */
    private boolean getPurchaseSummary(Connection connection) {

        try {

            /* query to fetch number and total price of copies of each publication bought per distributor per month */
            final String sqlQuery = "SELECT monthname(orderDate) as month, distributorId, pid, sum(price) as total_price, sum(number_of_copies) as total_no_of_copies from orders, ((select c.orderId,c.pid,  c.number_of_copies from consists c left join includes i on c.orderId =  i.orderId) UNION (select i.orderId,i.pid, i.number_of_copies from includes i left join consists c on i.orderId = c.orderId)) as t where  orders.orderId = t.orderId group by  month(date(orderDate)), distributorId, pid;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve total revenue of the publishing house */
    private boolean getRevenuePublishingHouse(Connection connection) {
        try {

            /* query to get total revenue of the publishing house */
            final String sqlQuery = "SELECT SUM(price) as revenue FROM orders;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve total expenses of the publishing house (i.e., shipping costs and salaries) */
    private boolean getExpensePublishingHouse(Connection connection) {
        try {
            /* query to get total expenses of the publishing house */
            final String sqlQuery = "SELECT  COALESCE((SELECT SUM(amount) FROM payment),0) + COALESCE((SELECT SUM(shipCost) FROM  orders),0) as expense;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve the total current number of distributors */
    private boolean getDistributorsCount(Connection connection) {
        try {
            /* query to get distributors count*/
            final String sqlQuery = "SELECT count(distributorId) as 'Distributor count' from distributor;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve the total revenue (since inception) per city */
    private boolean getRevenuePerCity(Connection connection) {
        try {
            /* query to get total revenue per city */
            final String sqlQuery = "SELECT d.city, SUM(o.price) as revenue from orders o, distributor d where  o.distributorId = d.distributorId group by city;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve total revenue per distributor */
    private boolean getRevenuePerDistributor(Connection connection) {
        try {
            /* query to get total revenue per distributor */
            final String sqlQuery = "SELECT distributorId, SUM(price) as price from orders GROUP BY  distributorId;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve total revenue per location */
    private boolean getRevenuePerLocation(Connection connection) {
        try {
            /* query to get total revenue per location */
            final String sqlQuery = "SELECT streetAddress, SUM(price) as revenue from orders o, distributor  d where o.distributorId = d.distributorId group by streetAddress;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve the total payments to the authors per month */
    private boolean getAuthorPaymentPerTimePeriod(Connection connection) {
        try {
            /* query to get total payment to authors per month */
            final String sqlQuery = "SELECT monthname(payDate) as month, SUM(amount) as total_payment from payment,  staff where payment.sid = staff.sid and payment.sid IN (SELECT sid FROM  staff where title='Author') group by MONTH(DATE(payDate));";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve the total payments to the editors per time period */
    private boolean getEditorPaymentPerTimePeriod(Connection connection) {
        try {
            /* query to get total payment to editors per month */
            final String sqlQuery = "SELECT monthname(payDate) as month, SUM(amount) as total_payment from payment,  staff where payment.sid = staff.sid and payment.sid IN (SELECT sid FROM  staff where title='Editor') group by MONTH(DATE(payDate));";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve the total payments to the authors per work type */
    private boolean getAuthorPaymentPerWorkType(Connection connection) {
        try {
            /* query to get total payment to authors per work type */
            final String sqlQuery = "SELECT work_type, SUM(amount) as total_payment from payment, staff where  payment.sid = staff.sid and payment.sid IN (SELECT sid FROM staff where  title='Author') group by work_type;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    /* Method to retrieve the total payments to the editors per work type */
    private boolean getEditorPaymentPerWorkType(Connection connection) {
        try {
            /* query to get total payment to editors per work type */
            final String sqlQuery = "SELECT work_type, SUM(amount) as total_payment from payment, staff where  payment.sid = staff.sid and payment.sid IN (SELECT sid FROM staff where  title='Editor') group by work_type;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }
}
