import service.EditingPublishingService;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("-----Welcome to WolfCity Publishing House-----\n\n");

            while(true) {
                System.out.println("Choose an operation you want to perform:");
                System.out.println("1. Editing and Publishing");
                System.out.println("2. Production");
                System.out.println("3. Distribution");
                System.out.println("4. Reports");
                System.out.println("5. Exit\n");

                System.out.println("Enter your choice: \t");
                int choice = scanner.nextInt();

                switch(choice) {
                    case 1:
                        EditingPublishingService editingPublishingService = new EditingPublishingService();
                        editingPublishingService.run();
                        break;

                    case 5:
                        System.exit(1);
                        break;

                    default:
                        System.out.println("Invalid Input, Please try again.");
                }

            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }


    }

}
