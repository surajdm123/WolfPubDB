package service;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Public class for Production Service testing
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
                System.out.println("19. Get payment claimed details");
                System.out.println("20. Claim a payment\n");

                System.out.println("21. Return to Main Menu\n");

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
                        claimPayment(connection);
                        break;
                    case 21:
                        break;
                    default:
                        System.out.println("Invalid Input");
                }

                if (choice == 21) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    //Function name to Claim payment
    public boolean claimPayment(final Connection connection){

        try {
            System.out.println("Here is a list of payments: \n");
            //SQL query to display payment details
            resultSetService.runQueryAndPrintOutput(connection, "SELECT payment.*, staff.name as 'Name' from payment NATURAL JOIN staff;");

            System.out.println("Enter the payId you want to claim:");
            final int payId = scanner.nextInt();

            scanner.nextLine();

            System.out.println("Enter the claim date (yyyy-mm-dd):");
            final String claimDate = scanner.nextLine();

            //SQL query to update the payment claim date for a specific publication
            final String claimUpdateSqlQuery = "UPDATE payment SET claimDate = ? WHERE (`pId` = ?)";
            final PreparedStatement statement = connection.prepareStatement(claimUpdateSqlQuery);
            statement.setString(1, claimDate);
            statement.setInt(2, payId);
            int updatedRows = statement.executeUpdate();

            System.out.println("Updated " + updatedRows + " row(s).");

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;
    }

    //Function to insert New Book Edition functionality
    public boolean insertNewBookEdition(final Connection connection){

        System.out.println("Here is a list of books in the database:");
        //SQL query to display list of books present in the database
        resultSetService.runQueryAndPrintOutput(connection, "SELECT pid, title, publication_date, genre from publication where publication_type='Book';");

        System.out.println("Enter the publication ID of the book for which you want to add an edition: ");
        final int pubID = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Enter the edition number: ");
        final String edNum = scanner.nextLine();

        System.out.println("Enter the price of the new edition: ");
        final double price = scanner.nextDouble();

        scanner.nextLine();

        System.out.println("Enter the ISBN of the new edition: ");
        final String isbn = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            try {
                //SQL query to insert edition details in edition table
                final String sqlQuery = "INSERT INTO `editions` (`edition_number`, `pid`, `price`, `isbn`) VALUES (?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, edNum);
                statement.setInt(2, pubID);
                statement.setDouble(3, price);
                statement.setString(4, isbn);

                statement.executeUpdate();

                connection.commit();

                System.out.println("Successfully inserted new edition");
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

    //Function to update book edition information
    public boolean updateBookEdition(final Connection connection){

        System.out.println("Here is a list of book editions and their details:");
        //SQL query to list books editions and the details corresponding
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM editions;");

        System.out.println("Enter the edition number you want to update: ");
        final String eNum = scanner.nextLine();

        System.out.println("Enter the publication ID of the above edition: ");
        final int pid = scanner.nextInt();

        System.out.println("What would you like to update for the above selected edition?");
        System.out.println("1. Price");
        System.out.println("2. ISBN");

        System.out.println("Enter your choice: \t");
        final int choice = scanner.nextInt();

        scanner.nextLine();

        int updatedRows = 0;

        try {
            connection.setAutoCommit(false);
            try {

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new price of the edition: \t");
                        final double newPrice = scanner.nextDouble();

                        //SQL query to update editions tables and set new price for an edition of a publication
                        final String priceUpdateSqlQuery = "UPDATE editions SET price = ? WHERE (`edition_number` = ?) AND (`pid` = ?);";
                        PreparedStatement priceUpdateStatement = connection.prepareStatement(priceUpdateSqlQuery);
                        priceUpdateStatement.setDouble(1, newPrice);
                        priceUpdateStatement.setString(2, eNum);
                        priceUpdateStatement.setInt(3, pid);

                        updatedRows = priceUpdateStatement.executeUpdate();
                        connection.commit();

                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 2:
                        System.out.println("Enter the new ISBN of the edition: \t");
                        final String newISBN = scanner.nextLine();

                        //SQL query to update new ISBN number to an existing edition
                        final String isbnUpdateSqlQuery = "UPDATE editions SET isbn = ? WHERE (`edition_number` = ?) AND (`pid` = ?);";
                        PreparedStatement isbnUpdateStatement = connection.prepareStatement(isbnUpdateSqlQuery);
                        isbnUpdateStatement.setString(1, newISBN);
                        isbnUpdateStatement.setString(2, eNum);
                        isbnUpdateStatement.setInt(3, pid);

                        updatedRows = isbnUpdateStatement.executeUpdate();
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
    //Function to delete a book edition from the database
    public boolean deleteBookEdition(final Connection connection){

        System.out.println("Here are the editions of all books in the database:");
        //SQL query to display editions of all books in the database
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from editions;");

        System.out.println("\nEnter the following details to delete an edition.\n");

        System.out.println("Enter the Edition Number: ");
        final String editionNumber = scanner.nextLine();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {

                //SQL query to delete edition from a publication
                final String sqlQuery = "DELETE FROM `editions` WHERE (`edition_number` = ?) and (`pid` = ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, editionNumber);
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

    //Function to Insert a New Issie into the database
    public boolean insertNewIssue(final Connection connection){

        System.out.println("Here is a list of magazines/journals in the database:");
        //SQL query to display list of magazines/journals present in the database
        resultSetService.runQueryAndPrintOutput(connection, "SELECT pid, title, publication_date, genre, publication_type from publication where publication_type='Magazine' OR publication_type='Journal';");

        System.out.println("Enter the publication ID of the magazine/journal for which you want to add an issue: ");
        final int pubID = scanner.nextInt();

        System.out.println("List of issues present for the publication:");
        //SQL query to list the issues present in the publications
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM issues where pid=" + pubID+";");

        System.out.println("Enter the Issue ID of the new issue to be added: ");
        final int issueID = scanner.nextInt();

        System.out.println("Enter the price of the new issue: ");
        final double issuePrice = scanner.nextDouble();

        scanner.nextLine();

        System.out.println("Enter the title of the new issue: ");
        final String issueTitle = scanner.nextLine();

        System.out.println("Enter the issue date (yyyy-mm-dd): ");
        final String issueDate = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            try {
                //SQL query to insert issue details into the issue table
                final String sqlQuery = "INSERT INTO `issues` (`issueID`, `pid`, `price`, `issue_title`, `issue_date`) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, issueID);
                statement.setInt(2, pubID);
                statement.setDouble(3, issuePrice);
                statement.setString(4, issueTitle);
                statement.setString(5, issueDate);

                statement.executeUpdate();

                connection.commit();

                System.out.println("Successfully inserted new issue");
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

    //SQL query to update an issue details into the database
    public boolean updateIssue(final Connection connection){

        System.out.println("Here is a list of magazine/journal issues and their details:");
        //SQL query to list magazine/jounral issues and their details
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM issues;");

        System.out.println("Enter the issue ID you want to update: ");
        final int issueID = scanner.nextInt();

        System.out.println("Enter the publication ID of the above issue: ");
        final int pid = scanner.nextInt();

        System.out.println("What would you like to update for the above selected issue?");
        System.out.println("1. Price");
        System.out.println("2. Issue Title");
        System.out.println("3. Issue Date");

        System.out.println("Enter your choice: \t");
        final int choice = scanner.nextInt();

        scanner.nextLine();

        int updatedRows = 0;

        try {
            connection.setAutoCommit(false);
            try {

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new price of the issue: \t");
                        final double newPrice = scanner.nextDouble();

                        //SQL query to update issues and to set a new price for an issue
                        final String priceUpdateSqlQuery = "UPDATE issues SET price = ? WHERE (`issueId` = ?) AND (`pid` = ?);";
                        PreparedStatement priceUpdateStatement = connection.prepareStatement(priceUpdateSqlQuery);
                        priceUpdateStatement.setDouble(1, newPrice);
                        priceUpdateStatement.setInt(2, issueID);
                        priceUpdateStatement.setInt(3, pid);

                        updatedRows = priceUpdateStatement.executeUpdate();
                        connection.commit();

                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 2:
                        System.out.println("Enter the new title of the issue: \t");
                        final String newTitle = scanner.nextLine();

                        //SQL query to update issues and set new title for the issue
                        final String titleUpdateSqlQuery = "UPDATE issues SET issue_title = ? WHERE (`issueId` = ?) AND (`pid` = ?);";
                        PreparedStatement titleUpdateStatement = connection.prepareStatement(titleUpdateSqlQuery);
                        titleUpdateStatement.setString(1, newTitle);
                        titleUpdateStatement.setInt(2, issueID);
                        titleUpdateStatement.setInt(3, pid);

                        updatedRows = titleUpdateStatement.executeUpdate();
                        connection.commit();

                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 3:
                        System.out.println("Enter the new date (yyyy-mm-dd) of the issue: \t");
                        final String newDate = scanner.nextLine();

                        //SQL query to update issues and set new issue date for an issue of a periodic publication
                        final String dateUpdateSqlQuery = "UPDATE issues SET issue_date = ? WHERE (`issueId` = ?) AND (`pid` = ?);";
                        PreparedStatement dateUpdateStatement = connection.prepareStatement(dateUpdateSqlQuery);
                        dateUpdateStatement.setString(1, newDate);
                        dateUpdateStatement.setInt(2, issueID);
                        dateUpdateStatement.setInt(3, pid);

                        updatedRows = dateUpdateStatement.executeUpdate();
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

    //Function to delete an issue entry from the database
    public boolean deleteIssue(final Connection connection){

        System.out.println("Here are all the issues of periodic publications in the database:");
        //SQL query to list all issues of a periodic publication in the database
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from issues;");

        System.out.println("\nEnter the following details to delete an issue.\n");

        System.out.println("Enter the Issue ID: ");
        final int issueID = scanner.nextInt();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {
                //SQL query to delete an issue
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

    //Function to insert a New Chapter into the database
    public boolean insertNewBookChapter(final Connection connection){

        System.out.println("Here is a list of book publications in the database:");
        //SQL query to list book publications in the database
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from publication where publication_type='Book';");

        System.out.println("Enter the publication ID of the book for which you want to add a chapter: ");
        final int pubID = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Here is a list of editions and their details for the book in the database:");
        //SQL query to list editions of a book of a publication
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from editions where pid=" + pubID +";");

        System.out.println("Enter the edition number of the above book for which you want to add a chapter: ");
        final String edNum = scanner.nextLine();

        System.out.println("Here is a list of chapters and their details for the book edition in the database:");
        //SQL query to list chapter details for book edition
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from chapters where pid=" + pubID +" AND edition_number= '" + edNum + "';");

        System.out.println("Enter the new chapter number: ");
        final int chapNum = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Enter the chapter name: ");
        final String chapName = scanner.nextLine();

        System.out.println("Enter the text/content of the chapter: ");
        final String chapText = scanner.nextLine();

        System.out.println("Enter the chapter date (yyyy-mm-dd): ");
        final String chapterDate = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            try {
                //SQL query to insert chapter details into chapters table
                final String sqlQuery = "INSERT INTO `chapters` (`chapter_number`, `pid`, `edition_number`, `chapter_name`, `text`, `chapter_date`) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, chapNum);
                statement.setInt(2, pubID);
                statement.setString(3, edNum);
                statement.setString(4, chapName);
                statement.setString(5, chapText);
                statement.setString(6, chapterDate);

                statement.executeUpdate();

                connection.commit();

                System.out.println("Successfully inserted new chapter");
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

    //Function to update a Chapter of a book in the database
    public boolean updateBookChapter(final Connection connection){

        System.out.println("Here is a list of book chapters and their details:");
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM chapters;");

        System.out.println("Enter the chapter number you want to update: ");
        final int chapNum = scanner.nextInt();

        System.out.println("Enter the publication ID of the above chapter: ");
        final int pid = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Enter the edition number of the above chapter: ");
        final String edNum = scanner.nextLine();

        System.out.println("What would you like to update for the above selected chapter?");
        System.out.println("1. Chapter Name");
        System.out.println("2. Chapter Text/Content");
        System.out.println("3. Chapter Date");

        System.out.println("Enter your choice: \t");
        final int choice = scanner.nextInt();

        scanner.nextLine();

        int updatedRows = 0;

        try {
            connection.setAutoCommit(false);
            try {

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new name of the chapter: \t");
                        final String newName = scanner.nextLine();
                        //SQL statement to update chapter name for a chapter present in and edition of a book
                        final String nameUpdateSqlQuery = "UPDATE chapters SET chapter_name = ? WHERE (`chapter_number` = ?) AND (`pid` = ?) AND (`edition_number` = ?);";
                        PreparedStatement nameUpdateStatement = connection.prepareStatement(nameUpdateSqlQuery);
                        nameUpdateStatement.setString(1, newName);
                        nameUpdateStatement.setInt(2, chapNum);
                        nameUpdateStatement.setInt(3, pid);
                        nameUpdateStatement.setString(4, edNum);

                        updatedRows = nameUpdateStatement.executeUpdate();
                        connection.commit();

                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 2:
                        System.out.println("Enter the new text/content of the chapter: \t");
                        final String newText = scanner.nextLine();

                        //SQL query ti update a new text or contect into an existing chapter
                        final String textUpdateSqlQuery = "UPDATE chapters SET text = ? WHERE (`chapter_number` = ?) AND (`pid` = ?) AND (`edition_number` = ?);";
                        PreparedStatement textUpdateStatement = connection.prepareStatement(textUpdateSqlQuery);
                        textUpdateStatement.setString(1, newText);
                        textUpdateStatement.setInt(2, chapNum);
                        textUpdateStatement.setInt(3, pid);
                        textUpdateStatement.setString(4, edNum);

                        updatedRows = textUpdateStatement.executeUpdate();
                        connection.commit();

                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;
                    case 3:
                        System.out.println("Enter the new date of the chapter: \t");
                        final String newDate = scanner.nextLine();

                        //SQL query to update a new date for an existing chapter
                        final String dateUpdateSqlQuery = "UPDATE chapters SET chapter_date = ? WHERE (`chapter_number` = ?) AND (`pid` = ?) AND (`edition_number` = ?);";
                        PreparedStatement dateUpdateStatement = connection.prepareStatement(dateUpdateSqlQuery);
                        dateUpdateStatement.setString(1, newDate);
                        dateUpdateStatement.setInt(2, chapNum);
                        dateUpdateStatement.setInt(3, pid);
                        dateUpdateStatement.setString(4, edNum);

                        updatedRows = dateUpdateStatement.executeUpdate();
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

    //Function to insert a new article into the database
    public boolean insertNewArticle(final Connection connection){

        System.out.println("Here is a list of magazine/journal issues:");
        //SQL query to list all the magazines and journals or periodic publication
        resultSetService.runQueryAndPrintOutput(connection, "SELECT issues.*,periodic_publication.periodicty as 'Periodicity', publication.title as 'Publication Name'   from issues NATURAL JOIN periodic_publication NATURAL JOIN publication;");

        System.out.println("Enter the issue ID of the issue for which you want to add an article: ");
        final int issueID = scanner.nextInt();

        System.out.println("Enter the publication ID of the above issue: ");
        final int pubID = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Enter the name of the new article: ");
        final String articleName = scanner.nextLine();

        System.out.println("Enter the text/content of the new article: ");
        final String articleText = scanner.nextLine();

        System.out.println("Enter the article date (yyyy-mm-dd): ");
        final String articleDate = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            try {
                //SQL query to insert article details into the article
                final String sqlQuery = "INSERT INTO `articles` (`pid`, `issueId`, `text`, `date`, `name`) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, pubID);
                statement.setInt(2, issueID);
                statement.setString(3, articleText);
                statement.setString(4, articleDate);
                statement.setString(5, articleName);

                statement.executeUpdate();

                connection.commit();

                System.out.println("Successfully inserted new article");
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

    //Function to update an article details into the database
    public boolean updateArticle(final Connection connection){

        System.out.println("Here is a list of articles present in various issues of magazines/journals:");
        //SQL query to list articles present in various issues of periodic publications (either magazines/ journals)
        resultSetService.runQueryAndPrintOutput(connection, "SELECT articles.*, publication.title, periodic_publication.periodicty as 'Periodicity', issues.issue_title FROM articles NATURAL JOIN issues NATURAL JOIN periodic_publication NATURAL JOIN publication;");

        System.out.println("Enter the article ID you want to update: ");
        final int articleID = scanner.nextInt();

        System.out.println("Enter the publication ID of the above article: ");
        final int pid = scanner.nextInt();

        System.out.println("Enter the issue ID of the above article: ");
        final int issueID = scanner.nextInt();

        System.out.println("What would you like to update for the above selected article?");
        System.out.println("1. Article Text/Content");
        System.out.println("2. Article Date");
        System.out.println("3. Article Name");

        System.out.println("Enter your choice: \t");
        final int choice = scanner.nextInt();

        scanner.nextLine();

        int updatedRows = 0;

        try {
            connection.setAutoCommit(false);
            try {

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new text/content of the article: \t");
                        final String newText = scanner.nextLine();

                        //SQL query to update a new text or content into an existing article
                        final String textUpdateSqlQuery = "UPDATE articles SET text = ? WHERE (`articleId` = ?) AND (`pid` = ?) AND (`issueId` = ?);";
                        PreparedStatement textUpdateStatement = connection.prepareStatement(textUpdateSqlQuery);
                        textUpdateStatement.setString(1, newText);
                        textUpdateStatement.setInt(2, articleID);
                        textUpdateStatement.setInt(3, pid);
                        textUpdateStatement.setInt(4, issueID);

                        updatedRows = textUpdateStatement.executeUpdate();
                        connection.commit();

                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 2:
                        System.out.println("Enter the new date (yyyy-mm-dd) of the article: \t");
                        final String newDate = scanner.nextLine();

                        //SQL query to update a new date for an existing article
                        final String dateUpdateSqlQuery = "UPDATE articles SET date = ? WHERE (`articleId` = ?) AND (`pid` = ?) AND (`issueId` = ?);";
                        PreparedStatement dateUpdateStatement = connection.prepareStatement(dateUpdateSqlQuery);
                        dateUpdateStatement.setString(1, newDate);
                        dateUpdateStatement.setInt(2, articleID);
                        dateUpdateStatement.setInt(3, pid);
                        dateUpdateStatement.setInt(4, issueID);

                        updatedRows = dateUpdateStatement.executeUpdate();
                        connection.commit();

                        System.out.println("Successfully updated " + updatedRows + "row(s).");
                        break;

                    case 3:
                        System.out.println("Enter the new name of the article: \t");
                        final String newName = scanner.nextLine();

                        //SQL query to update a new name for an exiting article
                        final String nameUpdateSqlQuery = "UPDATE articles SET name = ? WHERE (`articleId` = ?) AND (`pid` = ?) AND (`issueId` = ?);";
                        PreparedStatement nameUpdateStatement = connection.prepareStatement(nameUpdateSqlQuery);
                        nameUpdateStatement.setString(1, newName);
                        nameUpdateStatement.setInt(2, articleID);
                        nameUpdateStatement.setInt(3, pid);
                        nameUpdateStatement.setInt(4, issueID);

                        updatedRows = nameUpdateStatement.executeUpdate();
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

    //Function to find a Book by topic and display the results 
    public boolean findBookByTopic(final Connection connection){

        System.out.println("We currently have books of the following topics/genres: \n");
        //SQL query to list the books for a specific topic/genre
        resultSetService.runQueryAndPrintOutput(connection, "SELECT DISTINCT(genre) FROM publication where publication_type = 'Book';");

        System.out.println("Enter the topic/genre for which you would like to find books: ");
        String genre = scanner.nextLine();

        try {
            //SQL query to list a book for a specific topic or genre
            final String sqlQuery = "SELECT publication.*, GROUP_CONCAT(staff.name) as 'Author Name(s)' FROM publication NATURAL JOIN writes LEFT OUTER JOIN staff ON writes.sid = staff.sid where publication_type='Book' and (`genre` = ?) GROUP BY publication.pid;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, genre);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    //Function to find a booj by Date and display the reuslts 
    public boolean findBookByDate(final Connection connection){

        System.out.println("Enter the date (yyyy-mm-dd) for which you would like to find book(s): ");
        String bookDate = scanner.nextLine();

        try {
            //SQL query to list the book for a specific date
            final String sqlQuery = "SELECT publication.*, GROUP_CONCAT(staff.name) as 'Author Name(s)' FROM publication NATURAL JOIN writes LEFT OUTER JOIN staff ON writes.sid = staff.sid where publication_type='Book' and (`publication_date` = ?) GROUP BY publication.pid;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, bookDate);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    //Function to find a book by author and display the results
    public boolean findBookByAuthor(final Connection connection){

        System.out.println("Here is a list of authors: \n");
        //SQL query to list the authors present in the publishing house
        resultSetService.runQueryAndPrintOutput(connection, "SELECT sid, name FROM staff where title = 'Author';");

        System.out.println("Enter the name of the author whose books you would like to find: ");
        String authorName = scanner.nextLine();

        try {
            //SQL query to find the book based on an author's name
            final String sqlQuery = "SELECT publication.* FROM publication, writes where publication.pid = writes.pid AND writes.sid IN (SELECT sid from staff where (`name` = ?) AND title='Author');";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, authorName);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    //Function to find an article by topic and display the results 
    public boolean findArticleByTopic(final Connection connection){

        System.out.println("We currently have magazines/journals of the following topics/genres: \n");
        //SQL query to list the magazines/journals for specific topics/genres
        resultSetService.runQueryAndPrintOutput(connection, "SELECT DISTINCT(genre) FROM publication where publication_type = 'Magazine' or publication_type = 'Journal';");

        System.out.println("Enter the topic/genre for which you would like to find articles in a magazine/journal: ");
        String genre = scanner.nextLine();

        try {
            //SQL query to find articles in magazine/ journal based on a topic/genre
            final String sqlQuery = "SELECT a.*, p.title FROM publication p, articles a where p.pid=a.pid and (`genre` = ?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, genre);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    //Function to find an article by date and display the results 
    public boolean findArticleByDate(final Connection connection){

        System.out.println("Enter the date (yyyy-mm-dd) for which you would like to find article(s): ");
        String articleDate = scanner.nextLine();

        try {
            //SQL query to display articles based on a specific date entry given by DB user
            final String sqlQuery = "SELECT articles.*, publication.title FROM articles NATURAL JOIN publication where (`date` = ?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, articleDate);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    //Function to find an article by author details and display results
    public boolean findArticleByAuthor(final Connection connection){

        System.out.println("Here is a list of authors: \n");
        //SQL query to list all the authors present as Staff
        resultSetService.runQueryAndPrintOutput(connection, "SELECT name FROM staff where title = 'Author';");

        System.out.println("Enter the name of the author whose articles you would like to find: ");
        String authorName = scanner.nextLine();

        try {
            //SQL query to list articles for a specific author ( by name)
            final String sqlQuery = "SELECT a.*, p.title FROM publication p, articles a where p.pid = a.pid AND p.pid IN (SELECT publication.pid from publication, writes where publication.pid = writes.pid AND writes.sid IN (SELECT sid from staff where (`name` = ?) and title='Author'));";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, authorName);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    // Function to set payment for an editor
    public boolean setPaymentForEditor(final Connection connection){

        try {
            System.out.println("List of Editors in the database:");
            //SQL query ti list the editors present in the database
            resultSetService.runQueryAndPrintOutput(connection, "SELECT * from staff NATURAL JOIN editor where title='Editor';");

            System.out.println("Enter SID for the Editor to add payment: ");
            final int sid = scanner.nextInt();

            scanner.nextLine();

            System.out.println("Enter Work Type for the Editor to add payment: ");
            final String work_type = scanner.nextLine();

            System.out.println("Enter payment amount: ");
            double amount = scanner.nextDouble();

            scanner.nextLine();

            System.out.println("Enter Pay Date (yyyy-mm-dd): ");
            final String date = scanner.nextLine();

            //SQL query to insert payment details into the database
            final String sqlQuery = "INSERT INTO `payment` (`payDate`, `work_type`, `amount`,`sid`) VALUES (?, ?, ?, ?);";
            PreparedStatement statement1 = connection.prepareStatement(sqlQuery);
            statement1.setString(1, date);
            statement1.setString(2, work_type);
            statement1.setDouble(3, amount);
            statement1.setInt(4, sid);

            statement1.executeUpdate();

            System.out.println("Payment has been successfully made.");

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;
    }

    //Function to set a payment for an author 
    public boolean setPaymentForAuthor(final Connection connection){

        try {
            System.out.println("List of Authors in the database:");
            //SQL query to list the authors in the database
            resultSetService.runQueryAndPrintOutput(connection, "SELECT * from staff NATURAL JOIN author where title='Author';");

            System.out.println("Enter SID for the Author to add payment: ");
            final int sid = scanner.nextInt();

            scanner.nextLine();

            System.out.println("Enter Work Type for the Author to add payment: ");
            final String work_type = scanner.nextLine();

            System.out.println("Enter payment amount: ");
            double amount = scanner.nextDouble();

            scanner.nextLine();

            System.out.println("Enter Pay Date (yyyy-mm-dd): ");
            final String date = scanner.nextLine();

            //SQL query to insert payment details into the database
            final String sqlQuery = "INSERT INTO `payment` (`payDate`, `work_type`, `amount`,`sid`) VALUES (?, ?, ?, ?);";
            PreparedStatement statement1 = connection.prepareStatement(sqlQuery);
            statement1.setString(1, date);
            statement1.setString(2, work_type);
            statement1.setDouble(3, amount);
            statement1.setInt(4, sid);

            statement1.executeUpdate();

            System.out.println("Payment has been successfully made.");

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    //Function to get payment claim details 
    public boolean getPaymentClaimDetails(final Connection connection){

        System.out.println("Here is a list of authors and editors: \n");
        //SQL query to list the authors and editors present as staff of the WolfPubDb Publishing house
        resultSetService.runQueryAndPrintOutput(connection, "SELECT sid, name FROM staff where title = 'Author' OR title = 'Editor';");

        System.out.println("Enter the staff ID of the author/editor whose payment claim details you would like to see: ");
        final int staffID = scanner.nextInt();

        try {
            //SQL query to display payent details made by a specific staff
            final String sqlQuery = "SELECT * FROM payment where (`sid` = ?);";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, staffID);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    private double getAmount(ResultSet resultSet) throws SQLException {

        List<String> headerColumns = getHeaderColumns(resultSet);
        String columnValue = "";

        if (!resultSet.isBeforeFirst() ) {

            throw new SQLException("No author/editor found.");
        }

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