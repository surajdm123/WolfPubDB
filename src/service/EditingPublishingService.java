package service;

import constants.MYSQL_CONSTANTS;

import java.sql.*;
import java.util.Scanner;

public class EditingPublishingService {

    ConnectionHelper connectionHelper = new ConnectionHelper();
    Scanner scanner = new Scanner(System.in);

    public void run(final Connection connection) {

        try {

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

                System.out.println("12. Return to Main Menu\n");

                System.out.println("Enter your choice:");

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        insertNewBookPublication(connection);
                        break;
                    case 2:
                        updateBookPublication(connection);
                        break;

                    case 12:
                        break;

                }

                if (choice == 12) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occured: " + e.getMessage());
        }

    }

    public boolean updateBookPublication(Connection connection) {
        System.out.println("Enter Book Publication ID you want to update: ");
        final int pid = scanner.nextInt();

        System.out.println("What do you want to update?");
        System.out.println("1. Title");
        System.out.println("2. Publication Date");
        System.out.println("3. Genre");
        System.out.println("4. Number of Pages\n");
        System.out.println("Enter your choice: \t");
        final int choice = scanner.nextInt();
        scanner.nextLine();
        int updatedRows = 0;

        try {
            connection.setAutoCommit(false);
            try {

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new Title: \t");
                        final String newTitle = scanner.nextLine();
                        final String titleUpdateSqlQuery = "UPDATE publication SET title = ? WHERE pid = ?;";
                        PreparedStatement titleUpdateStatement = connection.prepareStatement(titleUpdateSqlQuery);
                        titleUpdateStatement.setString(1, newTitle);
                        titleUpdateStatement.setInt(2, pid);
                        updatedRows = titleUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 2:
                        System.out.println("Enter the new Publication Date (yyyy-mm-dd): \t");
                        final String newDate = scanner.nextLine();
                        final String dateUpdateSqlQuery = "UPDATE publication SET publication_date = ? WHERE pid = ?;";
                        PreparedStatement dateUpdateStatement = connection.prepareStatement(dateUpdateSqlQuery);
                        dateUpdateStatement.setString(1, newDate);
                        dateUpdateStatement.setInt(2, pid);
                        updatedRows = dateUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 3:
                        System.out.println("Enter the new genre: \t");
                        final String newGenre = scanner.nextLine();
                        final String genreUpdateSqlQuery = "UPDATE publication SET genre = ? WHERE pid = ?;";
                        PreparedStatement genreUpdateStatement = connection.prepareStatement(genreUpdateSqlQuery);
                        genreUpdateStatement.setString(1, newGenre);
                        genreUpdateStatement.setInt(2, pid);
                        updatedRows = genreUpdateStatement.executeUpdate();
                        connection.commit();
                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 4:
                        System.out.println("Enter the new number of pages: \t");
                        final int newNumberOfPages = scanner.nextInt();
                        final String pagesUpdateSqlQuery = "UPDATE book SET number_of_pages = ? WHERE pid = ?;";
                        PreparedStatement pagesUpdateStatement = connection.prepareStatement(pagesUpdateSqlQuery);
                        pagesUpdateStatement.setInt(1, newNumberOfPages);
                        pagesUpdateStatement.setInt(2, pid);
                        updatedRows = pagesUpdateStatement.executeUpdate();
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

    public boolean insertNewBookPublication(final Connection connection) {

        System.out.println("Enter Publication Title: ");
        final String title = scanner.nextLine();
        System.out.println("Enter Publication Date (yyyy-mm-dd): ");
        final String publicationDate = scanner.nextLine();
        System.out.println("Enter Genre: ");
        final String genre = scanner.nextLine();
        final String publicationType = MYSQL_CONSTANTS.BOOK;
        System.out.println("Enter Number of Pages: ");
        final int numberOfPages = scanner.nextInt();
        System.out.println("Enter Author Id:");
        final int authorId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);
            try {

                String sqlStatement1 = "INSERT INTO `publication` (`title`, `publication_date`, `genre`,`publication_type`) VALUES (?,?,?,?);";
                PreparedStatement statement1 = connection.prepareStatement(sqlStatement1, Statement.RETURN_GENERATED_KEYS);
                statement1.setString(1, title);
                statement1.setString(2, publicationDate);
                statement1.setString(3, genre);
                statement1.setString(4, publicationType);

                statement1.executeUpdate();

                ResultSet rs = statement1.getGeneratedKeys();

                int pid = -1;

                if (rs.next()) {
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
                System.out.println("Book Publication successfully inserted (pid=" + pid + ").\n\n");
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
