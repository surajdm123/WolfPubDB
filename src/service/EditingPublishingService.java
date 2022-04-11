package service;

import constants.MYSQL_CONSTANTS;

import java.sql.*;
import java.util.Scanner;

public class EditingPublishingService {

    ConnectionHelper connectionHelper = new ConnectionHelper();
    Scanner scanner = new Scanner(System.in);
    ResultSetService resultSetService = new ResultSetService();

    public void run(final Connection connection) {

        try {

            while (true) {
                System.out.println("\n\nBOOK PUBLICATION:");
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
                    case 3:
                        addChapter(connection);
                        break;
                    case 4:
                        deleteChapter(connection);
                        break;
                    case 5:
                        insertNewPeriodicPublication(connection);
                        break;
                    case 6:
                        updatePeriodicPublication(connection);
                        break;
                    case 7:
                        addArticle(connection);
                        break;
                    case 8:
                        deleteArticle(connection);
                        break;
                    case 9:
                        assignEditorToPublication(connection);
                    case 10:
                        getAllPublicationsAssignedToEditor(connection);
                        break;
                    case 11:
                        getPublicationInformation(connection);
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
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    public boolean getPublicationInformation(final Connection connection) {

        System.out.println("1. Find by ID");
        System.out.println("2. Find by Title\n");
        System.out.println("3. Return to Main Menu\n");
        System.out.println("Enter your choice: ");

        int choice = scanner.nextInt();
        try {

            switch (choice) {
                case 1:
                    System.out.println("Enter Publication ID: ");
                    int publicationId = scanner.nextInt();
                    final String sqlQuery = "SELECT * FROM publication where pid=?;";
                    PreparedStatement statement = connection.prepareStatement(sqlQuery);
                    statement.setInt(1, publicationId);
                    ResultSet resultSet = statement.executeQuery();
                    resultSetService.viewFromResultSet(resultSet);
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("Enter Publication Title: ");
                    String title = scanner.nextLine();
                    final String query = "SELECT * FROM publication where title=?;";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, title);
                    ResultSet rs = preparedStatement.executeQuery();
                    resultSetService.viewFromResultSet(rs);
                    break;
                case 3:
                    return true;
                default:
                    System.out.println("Invalid Input");
            }

        } catch (Exception e) {

        }
        return true;
    }


    public boolean getAllPublicationsAssignedToEditor(final Connection connection) {

        System.out.println("Enter the editor ID: ");
        final int editorId = scanner.nextInt();

        try {
            final String sqlQuery = "SELECT * FROM publication NATURAL JOIN edits where sid=?;";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, editorId);

            ResultSet resultSet = statement.executeQuery();
            resultSetService.viewFromResultSet(resultSet);

        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }

        return true;

    }

    public boolean addChapter(final Connection connection) {

        System.out.println("Enter the Chapter Number: ");
        final int chapterNumber = scanner.nextInt();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        System.out.println("Enter the edition number: ");
        final int editionNumber = scanner.nextInt();

        scanner.nextLine();
        System.out.println("Enter the chapter name: ");
        final String chapterName = scanner.nextLine();

        System.out.println("Enter the chapter text: ");
        final String chapterText = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            try {
                final String sqlQuery = "INSERT INTO `chapters` (`chapter_number`, `pid`, `edition_number`, `chapter_name`, `text`) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, chapterNumber);
                statement.setInt(2, publicationId);
                statement.setInt(3, editionNumber);
                statement.setString(4, chapterName);
                statement.setString(5, chapterText);

                statement.executeUpdate();

                connection.commit();

                System.out.println("Successfully inserted new Chapter");
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

    public boolean addArticle(final Connection connection) {

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        System.out.println("Enter the issue ID: ");
        final int issueId = scanner.nextInt();

        scanner.nextLine();
        System.out.println("Enter the Article name: ");
        final String articleName = scanner.nextLine();

        System.out.println("Enter the article text: ");
        final String articleText = scanner.nextLine();

        System.out.println("Enter the article date (yyyy-mm-dd): ");
        final String articleDate = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            try {
                final String sqlQuery = "INSERT INTO `articles` (`pid`, `issueId`, `text`, `date`, `name`) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, publicationId);
                statement.setInt(2, issueId);
                statement.setString(3, articleText);
                statement.setString(4, articleDate);
                statement.setString(4, articleName);

                statement.executeUpdate();

                connection.commit();

                System.out.println("Successfully inserted new Article");
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

    public boolean assignEditorToPublication(final Connection connection) {

        System.out.println("Details about all the Editors: \n");

        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM staff where title = 'Editor';");

        System.out.println("\nDetails about all the publications: \n");

        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM publication;");

        System.out.println("\nEnter the publication ID: ");
        final int publicationId = scanner.nextInt();

        System.out.println("Enter the editor Id: ");
        final int editorId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {

                final String sqlQuery = "INSERT INTO `edits` (`sid`, `pid`) VALUES (?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, editorId);
                statement.setInt(2,publicationId);

                statement.executeUpdate();
                connection.commit();

                System.out.println("Successfully Assigned editor to a publication.");

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

    public boolean deleteChapter(final Connection connection) {

        System.out.println("Enter the Chapter Number: ");
        final int chapterNumber = scanner.nextInt();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        System.out.println("Enter the edition number: ");
        final int editionNumber = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {

                final String sqlQuery = "DELETE FROM `chapters` WHERE (`chapter_number` = ?) and (`pid` = ?) and (`edition_number` = ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, chapterNumber);
                statement.setInt(2,publicationId);
                statement.setInt(3, editionNumber);

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

    public boolean deleteArticle(final Connection connection) {

        System.out.println("Enter the Article Id: ");
        final int articleNumber = scanner.nextInt();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        System.out.println("Enter the issue ID: ");
        final int issueId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {

                final String sqlQuery = "DELETE FROM `articles` WHERE (`articleId` = ?) and (`pid` = ?) and (`issueId` = ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, articleNumber);
                statement.setInt(2,publicationId);
                statement.setInt(3, issueId);

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

    public boolean insertNewPeriodicPublication(final Connection connection) {

        System.out.println("Enter Publication Title: ");
        final String title = scanner.nextLine();
        System.out.println("Enter Publication Date (yyyy-mm-dd): ");
        final String publicationDate = scanner.nextLine();
        System.out.println("Enter Genre: ");
        final String genre = scanner.nextLine();
        System.out.println("Enter Publication Type (Journal, Magazine): ");
        final String publicationType = scanner.nextLine();
        System.out.println("Enter Periodicity (Weekly, Monthly, Daily): ");
        final String periodicity = scanner.nextLine();
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

                String sqlStatement2 = "INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (?,?);";
                PreparedStatement statement2 = connection.prepareStatement(sqlStatement2);
                statement2.setInt(1, pid);
                statement2.setString(2, periodicity);
                statement2.executeUpdate();

                String sqlStatement3 = "INSERT INTO `writes` (`sid`, `pid`) VALUES (?,?);";
                PreparedStatement statement3 = connection.prepareStatement(sqlStatement3);
                statement3.setInt(1, authorId);
                statement3.setInt(2, pid);
                statement3.executeUpdate();

                connection.commit();
                System.out.println("Periodic Publication successfully inserted (pid=" + pid + ").\n\n");
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

    public boolean updatePeriodicPublication(Connection connection) {
        System.out.println("Enter Periodic Publication ID you want to update: ");
        final int pid = scanner.nextInt();

        System.out.println("What do you want to update?");
        System.out.println("1. Title");
        System.out.println("2. Publication Date");
        System.out.println("3. Genre");
        System.out.println("4. Periodicity\n");
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
                        System.out.println("Enter the new Periodicity (Daily, Weekly, Monthly): \t");
                        final String periodicity = scanner.nextLine();
                        final String periodicityUpdateSqlQuery = "UPDATE periodic_publication SET periodicity = ? WHERE pid = ?;";
                        PreparedStatement periodicityUpdateStatement = connection.prepareStatement(periodicityUpdateSqlQuery);
                        periodicityUpdateStatement.setString(1, periodicity);
                        periodicityUpdateStatement.setInt(2, pid);
                        updatedRows = periodicityUpdateStatement.executeUpdate();
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
}
