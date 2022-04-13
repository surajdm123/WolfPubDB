package service;

import java.sql.Connection;
import java.util.Scanner;

public class ProductionService {

    Scanner scanner = new Scanner(System.in);
    ResultSetService resultSetService = new ResultSetService();

    public void run(final Connection connection) {

        try {

            while (true) {

                System.out.println("\n\nBOOK EDITION:");
                System.out.println("1. Enter/Insert new book edition");
                System.out.println("2. Update book edition");
                System.out.println("3. Delete book edition\n");

                System.out.println("ISSUE OF A PERIODIC PUBLICATION:");
                System.out.println("4. Enter/Insert new issue of a periodic publication");
                System.out.println("5. Update an issue of a periodic publication");
                System.out.println("6. Delete an issue of a periodic publication\n");

                System.out.println("CHAPTERS OF A BOOK:");
                System.out.println("7. Enter/Insert new chapter of a book");
                System.out.println("8. Update a chapter of a book\n");

                System.out.println("ARTICLES IN A PUBLICATION ISSUE:");
                System.out.println("9. Enter/Insert new article");
                System.out.println("10. Update an article\n");

                System.out.println("SEARCH FOR BOOKS:");
                System.out.println("11. Find a book by topic/genre");
                System.out.println("12. Find a book by date");
                System.out.println("13. Find a book by author name\n");

                System.out.println("SEARCH FOR ARTICLES:");
                System.out.println("14. Find an article by topic/genre");
                System.out.println("15. Find an article by date");
                System.out.println("16. Find an article by author name\n");

                System.out.println("PAYMENT:");
                System.out.println("17. Set payment for an editor");
                System.out.println("18. Set payment for an author");
                System.out.println("19. Get payment claimed details\n");

                System.out.println("20. Return to Main Menu\n");

                System.out.println("Enter your choice:");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice){
                    case 1:
                        insertNewBookEdition(connection);
                        break;
                    case 2:
                        updateBookEdition(connection);
                        break;
                    case 3:
                        deleteBookEdition(connection);
                        break;
                    case 4:
                        insertNewIssue(connection);
                        break;
                    case 5:
                        updateIssue(connection);
                        break;
                    case 6:
                        deleteIssue(connection);
                        break;
                    case 7:
                        insertNewBookChapter(connection);
                        break;
                    case 8:
                        updateBookChapter(connection);
                        break;
                    case 9:
                        insertNewArticle(connection);
                        break;
                    case 10:
                        updateArticle(connection);
                        break;
                    case 11:
                        findBookByTopic(connection);
                        break;
                    case 12:
                        findBookByDate(connection);
                        break;
                    case 13:
                        findBookByAuthor(connection);
                        break;
                    case 14:
                        findArticleByTopic(connection);
                        break;
                    case 15:
                        findArticleByDate(connection);
                        break;
                    case 16:
                        findArticleByAuthor(connection);
                        break;
                    case 17:
                        setPaymentForEditor(connection);
                        break;
                    case 18:
                        setPaymentForAuthor(connection);
                        break;
                    case 19:
                        getPaymentClaimDetails(connection);
                        break;
                    case 20:
                        break;
                    default:
                        System.out.println("Invalid Input");
                }

                if (choice == 20) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    public boolean insertNewBookEdition(final Connection connection){

    }

    public boolean updateBookEdition(final Connection connection){

    }

    public boolean deleteBookEdition(final Connection connection){

       System.out.println("Here are the editions of all books in the database:");
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from editions;");

        System.out.println("\nEnter the following details to delete an edition.\n");

        System.out.println("Enter the Edition Number: ");
        final int editionNumber = scanner.nextInt();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {

                final String sqlQuery = "DELETE FROM `editions` WHERE (`edition_number` = ?) and (`pid` = ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, editionNumber);
                statement.setInt(2,publicationId);

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

    public boolean insertNewIssue(final Connection connection){

    }

    public boolean updateIssue(final Connection connection){

    }

    public boolean deleteIssue(final Connection connection){

        System.out.println("Here are all the issues of periodic publications in the database:");
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from issues;");

        System.out.println("\nEnter the following details to delete an issue.\n");

        System.out.println("Enter the Issue ID: ");
        final int issueID = scanner.nextInt();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {

                final String sqlQuery = "DELETE FROM `issues` WHERE (`issueId` = ?) and (`pid` = ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, issueID);
                statement.setInt(2,publicationId);

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

    public boolean insertNewBookChapter(final Connection connection){

    }

    public boolean updateBookChapter(final Connection connection){

    }

    public boolean insertNewArticle(final Connection connection){

    }

    public boolean updateArticle(final Connection connection){

    }

    public boolean findBookByTopic(final Connection connection){

        System.out.println("We currently have books of the following topics/genres: \n");
        resultSetService.runQueryAndPrintOutput(connection, "SELECT DISTINCT(genre) FROM publication where publication_type = 'Book';");

        System.out.println("Enter the topic/genre for which you would like to find books: ");
        String genre = scanner.nextLine();

        try {
            final String sqlQuery = "SELECT title FROM publication where publication_type='Book' and (`genre` = ?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, genre);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    public boolean findBookByDate(final Connection connection){

        System.out.println("Enter the date (yyyy-mm-dd) for which you would like to find book(s): ");
        String bookDate = scanner.nextLine();

        try {
            final String sqlQuery = "SELECT title FROM publication where publication_type='Book' and (`publication_date` = ?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, bookDate);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    public boolean findBookByAuthor(final Connection connection){

        System.out.println("Here is a list of authors: \n");
        resultSetService.runQueryAndPrintOutput(connection, "SELECT name FROM staff where title = 'Author';");

        System.out.println("Enter the name of the author whose books you would like to find: ");
        String authorName = scanner.nextLine();

        try {
            final String sqlQuery = "SELECT title FROM publication, writes where publication.pid = writes.pid AND writes.sid IN (SELECT sid from staff where (`name` = ?) AND title='Author');";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, authorName);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    public boolean findArticleByTopic(final Connection connection){

    }

    public boolean findArticleByDate(final Connection connection){

    }

    public boolean findArticleByAuthor(final Connection connection){

    }

    public boolean setPaymentForEditor(final Connection connection){

    }

    public boolean setPaymentForAuthor(final Connection connection){

    }

    public boolean getPaymentClaimDetails(final Connection connection){

    }

}