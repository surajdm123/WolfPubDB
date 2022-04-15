package service;

import constants.MYSQL_CONSTANTS;

import java.sql.*;
import java.util.Scanner;

//Class definition for EditingPublishingService
public class EditingPublishingService {

    Scanner scanner = new Scanner(System.in);
    ResultSetService resultSetService = new ResultSetService();

    //Run function to handle main option for all functionalities offered by EditingPublishingService
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

                //Switch cases to call functions involved in EditingPublishingService
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
                        break;
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
            //catch Exception when error occurs
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    //Function to view Publication Information
    public boolean getPublicationInformation(final Connection connection) {

        //Criterion to view publication information on
        System.out.println("1. Find by ID");
        System.out.println("2. Find by Title\n");
        System.out.println("3. Return to Main Menu\n");
        System.out.println("Enter your choice: ");

        int choice = scanner.nextInt();
        try {


            switch (choice) {
                case 1:
                    //Take publication ID as input.
                    System.out.println("Enter Publication ID: ");
                    int publicationId = scanner.nextInt();
                    // Displays publication table details, author involved, for a specific type of publication where staff's id is same as author's id. This display is grouped by publication id.
                    final String sqlQuery = "SELECT publication.*, GROUP_CONCAT(staff.name) as 'Author Name(s)' FROM publication NATURAL JOIN writes JOIN staff ON writes.sid = staff.sid WHERE pid=? GROUP BY publication.pid;";
                    PreparedStatement statement = connection.prepareStatement(sqlQuery);
                    statement.setInt(1, publicationId);
                    ResultSet resultSet = statement.executeQuery();
                    resultSetService.viewFromResultSet(resultSet);
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("Enter Publication Title: ");
                    String title = scanner.nextLine();
                    // Displays publication table details, author involved, for a specific type of publication where staff's id is same as author's id. This display is grouped by publication id.
                    final String query = "SELECT publication.*, GROUP_CONCAT(staff.name) as 'Author Name(s)' FROM publication NATURAL JOIN writes JOIN staff ON writes.sid = staff.sid WHERE publication.title=? GROUP BY publication.pid;";
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

    //Function to get Publication details assigned to a specific editor
    public boolean getAllPublicationsAssignedToEditor(final Connection connection) {

        System.out.println("Details about all the Editors: \n");
        //Displays staff details for staff whose title equals "Editor"
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM staff where title = 'Editor';");

        System.out.println("Enter the editor ID: ");
        final int editorId = scanner.nextInt();

        try {
            //Displays publication details,status of publication being edited by editor
            final String sqlQuery = "SELECT publication.*, edits.status FROM publication NATURAL JOIN edits where sid=?;";
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

    //Function to add chapter of  book edition
    public boolean addChapter(final Connection connection) {

        System.out.println("Publications in the database:");
        // Displays publication details, number of pages of the book, edition details for an edition of a book
        resultSetService.runQueryAndPrintOutput(connection, "SELECT publication.*, book.number_of_pages, editions.* from publication NATURAL JOIN book NATURAL JOIN editions");

        System.out.println("Enter the Chapter Number: ");
        final int chapterNumber = scanner.nextInt();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Enter the edition number: ");
        final String editionNumber = scanner.nextLine();

        System.out.println("Enter the chapter name: ");
        final String chapterName = scanner.nextLine();

        System.out.println("Enter the chapter text: ");
        final String chapterText = scanner.nextLine();

        System.out.println("Enter the chapter date (yyyy-mm-dd): ");
        final String chapterDate = scanner.nextLine();

        try {
            connection.setAutoCommit(false);
            try {
                // Insert chapter details into chapter table including chapter nummber, publication id, edition number, chapter name, text of the chapter
                final String sqlQuery = "INSERT INTO `chapters` (`chapter_number`, `pid`, `edition_number`, `chapter_name`, `text`, `chapter_date`) VALUES (?, ?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, chapterNumber);
                statement.setInt(2, publicationId);
                statement.setString(3, editionNumber);
                statement.setString(4, chapterName);
                statement.setString(5, chapterText);
                statement.setString(6, chapterDate);

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

    // Function to add article to a periodic publication
    public boolean addArticle(final Connection connection) {

        System.out.println("Periodic publications present in the database:");
        // Displays publication details, issue id, issue title for an issue belonging to a publication
        resultSetService.runQueryAndPrintOutput(connection, "SELECT publication.*, issues.issueId, issues.issue_title from publication NATURAL JOIN periodic_publication JOIN issues ON issues.pid = periodic_publication.pid;");

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
                //Insert article details into article table including publication id, issue id, text of the article, article date, article name
                final String sqlQuery = "INSERT INTO `articles` (`pid`, `issueId`, `text`, `date`, `name`) VALUES (?, ?, ?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, publicationId);
                statement.setInt(2, issueId);
                statement.setString(3, articleText);
                statement.setString(4, articleDate);
                statement.setString(5, articleName);

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

    //Function to assign an editor to a specific publication
    public boolean assignEditorToPublication(final Connection connection) {

        System.out.println("Details about all the Editors: \n");
        // Displays details of the staff for Editor type only
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM staff where title = 'Editor';");

        System.out.println("\nDetails about all the publications: \n");
        // Displays all publication details
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM publication;");

        System.out.println("\nEnter the publication ID: ");
        final int publicationId = scanner.nextInt();

        System.out.println("Enter the editor Id: ");
        final int editorId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {
                //Insert details of editor responsible for editing a publication
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

    //Function to delete a chapter from an edition of a book
    public boolean deleteChapter(final Connection connection) {

        System.out.println("Chapters of publications in the database:");
        //Display all chapter details
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * from chapters;");

        System.out.println("\nEnter the following details to delete a chapter.\n");

        System.out.println("Enter the Chapter Number: ");
        final int chapterNumber = scanner.nextInt();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Enter the edition number: ");
        final String editionNumber = scanner.nextLine();

        try {
            connection.setAutoCommit(false);

            try {
                //Delete a specific chapter with a chapter name, publication id and edition number.
                final String sqlQuery = "DELETE FROM `chapters` WHERE (`chapter_number` = ?) and (`pid` = ?) and (`edition_number` = ?);";
                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, chapterNumber);
                statement.setInt(2,publicationId);
                statement.setString(3, editionNumber);

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

    //Function to delete article from a periodic publication
    public boolean deleteArticle(final Connection connection) {

        System.out.println("Articles present in the database:");
        //Display details of all articles
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM articles;");

        System.out.println("\nEnter the following information to delete the article.\n");

        System.out.println("Enter the Article Id: ");
        final int articleNumber = scanner.nextInt();

        System.out.println("Enter the publication ID: ");
        final int publicationId = scanner.nextInt();

        System.out.println("Enter the issue ID: ");
        final int issueId = scanner.nextInt();

        try {
            connection.setAutoCommit(false);

            try {
                //Delete entries of articles for a specific article id and pid and issueId
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

    //Function to update book publication information: title/publication date/genre/number of pages
    public boolean updateBookPublication(Connection connection) {

        System.out.println("Books in the database:");
        //Displays publication details, number of pages of a book for a book under a specific publication
        resultSetService.runQueryAndPrintOutput(connection, "SELECT publication.*, book.number_of_pages FROM publication NATURAL JOIN book;");

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
                        //Update publication details while setting a new Title for a publication
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
                        // Update publication details by setting publication date to a new date for a specific publication
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
                        // Update publication and set a new genre for a specific publication
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
                        //Update the number of pages for a specific publication id
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

    //Function to insert new book publication with details for each publication being entered by the DB user.
    public boolean insertNewBookPublication(final Connection connection) {

        // Get book publication details from the user
        System.out.println("Enter Publication Title: ");
        final String title = scanner.nextLine();
        System.out.println("Enter Publication Date (yyyy-mm-dd): ");
        final String publicationDate = scanner.nextLine();
        System.out.println("Enter Genre: ");
        final String genre = scanner.nextLine();
        final String publicationType = MYSQL_CONSTANTS.BOOK;
        System.out.println("Enter Number of Pages: ");
        final int numberOfPages = scanner.nextInt();

        System.out.println("Authors in the database:");
        //Display details for all staff members who are of "author" type.
        resultSetService.runQueryAndPrintOutput(connection, "SELECT * FROM staff where title='Author'");

        System.out.println("Enter Author Id:");
        final int authorId = scanner.nextInt();

        try {
            connection.setAutoCommit(false); // Set auto commit property of the connection to false
            try {
                // insert publication details including title, publication date, genre, publication type
                // into publication table.
                String sqlStatement1 =
                        "INSERT INTO `publication` (`title`, `publication_date`, `genre`,`publication_type`) " +
                                "VALUES (?,?,?,?);";
                PreparedStatement statement1 =
                        connection.prepareStatement(sqlStatement1, Statement.RETURN_GENERATED_KEYS);
                statement1.setString(1, title);
                statement1.setString(2, publicationDate);
                statement1.setString(3, genre);
                statement1.setString(4, publicationType);

                statement1.executeUpdate();

                // Get the resultset
                ResultSet rs = statement1.getGeneratedKeys();

                int pid = -1;

                // Get the auto incremented pid from the publication table
                if (rs.next()) {
                    pid = rs.getInt(1);
                } else {
                    throw new SQLException("Could not insert into table publication");
                }

                //Insert book details like publication id and number of pages
                String sqlStatement2 = "INSERT INTO `book` (`pid`, `number_of_pages`) VALUES (?,?);";
                PreparedStatement statement2 = connection.prepareStatement(sqlStatement2);
                statement2.setInt(1, pid);
                statement2.setInt(2, numberOfPages);
                statement2.executeUpdate();

                //Insert author details responsible for writing a specific publication type.category
                String sqlStatement3 = "INSERT INTO `writes` (`sid`, `pid`) VALUES (?,?);";
                PreparedStatement statement3 = connection.prepareStatement(sqlStatement3);
                statement3.setInt(1, authorId);
                statement3.setInt(2, pid);
                statement3.executeUpdate();

                // Commit the transaction
                connection.commit();
                System.out.println("Book Publication successfully inserted (pid=" + pid + ").\n\n");
                connection.setAutoCommit(true); // Set auto commit property to true

            } catch (Exception e) {
                // Rollback the transaction if there is an exception
                connection.rollback();
                System.out.println("Exception Occurred: " + e.getMessage());
                return false;
            }
        } catch (Exception e) {
            // Displays the error message if there is an exception
            System.out.println("Exception Occurred: " + e.getMessage());
            return false;
        }
        return true;
    }

    //Function to insert new periodic publication information
    public boolean insertNewPeriodicPublication(final Connection connection) {

        /* Request user input for new periodic publication information */
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

        System.out.println("Authors in the database");

        /* display authors list from staff table */
        resultSetService.runQueryAndPrintOutput(connection, "SELECT sid, name from staff where title='Author';");

        System.out.println("Enter Author Id:");
        final int authorId = scanner.nextInt();

        try {
            /* disable autocommit mode */
            connection.setAutoCommit(false);
            try{
                /* Insert publication details like title, publication date, genre, publication type into publication table entries. */
                String sqlStatement1 = "INSERT INTO `publication` (`title`, `publication_date`, `genre`,`publication_type`) VALUES (?,?,?,?);";
                PreparedStatement statement1 = connection.prepareStatement(sqlStatement1, Statement.RETURN_GENERATED_KEYS);
                statement1.setString(1, title);
                statement1.setString(2, publicationDate);
                statement1.setString(3, genre);
                statement1.setString(4, publicationType);
                /* execute query */
                statement1.executeUpdate();

                ResultSet rs = statement1.getGeneratedKeys();

                int pid = -1;

                if (rs.next()) {
                    pid = rs.getInt(1);
                } else {
                    throw new SQLException("Could not insert into table publication");
                }

                /* Insert periodic publication details like publication id and periodicity into the publication table. */
                String sqlStatement2 = "INSERT INTO `periodic_publication` (`pid`, `periodicty`) VALUES (?,?);";
                PreparedStatement statement2 = connection.prepareStatement(sqlStatement2);
                statement2.setInt(1, pid);
                statement2.setString(2, periodicity);
                statement2.executeUpdate();

                /* Insert author details responsible for writing a specific publication type/category */
                String sqlStatement3 = "INSERT INTO `writes` (`sid`, `pid`) VALUES (?,?);";
                PreparedStatement statement3 = connection.prepareStatement(sqlStatement3);
                statement3.setInt(1, authorId);
                statement3.setInt(2, pid);
                statement3.executeUpdate();

                /* commit transaction */
                connection.commit();
                System.out.println("Periodic Publication successfully inserted (pid=" + pid + ").\n\n");

                /* enable autocommit mode */
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

    //Function to update periodic publication on the basis of title/publication date/genre/periodicity
    public boolean updatePeriodicPublication(Connection connection) {

        System.out.println("Periodic Publications in the database:");
        //Display publication, periodicity for a periodic publication type of publication entry
        resultSetService.runQueryAndPrintOutput(connection, "SELECT publication.*, periodic_publication.periodicty FROM publication NATURAL JOIN periodic_publication;");

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
                        //Update the title of the publication for a specific publication id.
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
                        //Update the date of the publication for a specific publication id.
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
                        //Update the genre of the publication for a specific publication id.
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
                        //Update the periodicity of the periodic publication for a specific publication id.
                        final String periodicityUpdateSqlQuery = "UPDATE periodic_publication SET periodicty = ? WHERE pid = ?;";
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
