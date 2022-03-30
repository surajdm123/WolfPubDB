package service;

import constants.MYSQL_CONSTANTS;

import java.sql.*;
import java.util.Scanner;

public class EditingPublishingService {

    ConnectionHelper connectionHelper = new ConnectionHelper();
    Scanner scanner = new Scanner(System.in);

    public void run() {

        /*
         * Try with resources will automatically close the connection once
         * the control goes over the try-catch block.
         */
        try (final Connection connection = connectionHelper.getConnection()) {

            while (true) {
                System.out.println("BOOK PUBLICATION:");
                System.out.println("1. Insert new Book publication");
                System.out.println("2. Update Book publication");
                System.out.println("3. Add Chapter");
                System.out.println("4. Delete Chapter\n");

                System.out.println("PERIODIC PUBLICATION:");
                System.out.println("5. Insert new periodic publication");
                System.out.println("6. Update periodic publication");
                System.out.println("7. Add Article");
                System.out.println("8. Delete Article\n");

                System.out.println("9. Assign Editor to Publication");
                System.out.println("10. Get all publications assigned to editor");
                System.out.println("11. Get Publication information\n");

                System.out.println("12. Return to Main Menu");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Enter Publication Title: ");
                        final String title = scanner.next();
                        System.out.println("Enter Publication Date (yyyy-mm-dd): ");
                        final String publicationDate = scanner.next();
                        System.out.println("Enter Genre: ");
                        final String genre = scanner.next();
                        final String publicationType = MYSQL_CONSTANTS.BOOK;
                        System.out.println("Enter Number of Pages: ");
                        final int numberOfPages = scanner.nextInt();
                        System.out.println("Enter Author Id:");
                        final int authorId = scanner.nextInt();

                        insertNewBookPublication(connection, title, publicationDate, genre, publicationType, numberOfPages, authorId);
                        break;

                    case 12:
                        break;

                }

                if(choice == 12) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occured: " + e.getMessage());
        }

    }

    public boolean insertNewBookPublication(final Connection connection, final String title, final String publicationDate, final String genre, final String publicationType, final int numberOfPages, final int authorId) {

        try {
            connection.setAutoCommit(false);
            String sqlStatement1 = "INSERT INTO `publication` (`title`, `publication_date`, `genre`,`publication_type`) VALUES (?,?,?,?);";
            PreparedStatement statement1 = connection.prepareStatement(sqlStatement1, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, title);
            statement1.setString(2, publicationDate);
            statement1.setString(3, genre);
            statement1.setString(4, publicationType);

            statement1.executeUpdate();

            ResultSet rs = statement1.getGeneratedKeys();

            int pid = -1;

            if(rs.next()) {
                pid = rs.getInt(1);
            } else {
                throw new SQLException("Could not insert into table publication");
            }

            String sqlStatement2 = "INSERT INTO `book` (`pid`, `number_of_pages`) VALUES (?,?);";
            PreparedStatement statement2 = connection.prepareStatement(sqlStatement2);
            statement2.setInt(1, pid);
            statement2.setInt(2, numberOfPages);
            statement2.executeUpdate();

            String sqlStatement3 = "INSERT INTO `writes` (`sid`, `pid`) VALUES (?,?);";
            PreparedStatement statement3 = connection.prepareStatement(sqlStatement3);
            statement3.setInt(1, authorId);
            statement3.setInt(2, pid);
            statement3.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        System.out.println("Book Publication successfully inserted.");
        return true;
    }
}
