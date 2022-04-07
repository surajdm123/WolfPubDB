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



}