package presentation;

import domain.model.User;
import domain.services.SocialMediaService;
import utils.Helper;

import java.io.Console;
import java.util.HashMap;
import java.util.Scanner;

public class MainMenuView implements ICLIView {
    SocialMediaService socialMediaService;

    public MainMenuView(SocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }

    @Override
    public void showMenu() {
        System.out.println("""

                 .d8888b.                                               888    8888888
                d88P  Y88b                                              888      888
                888    888                                              888      888
                888         .d88b.  88888b.  88888b.   .d88b.   .d8888b 888888   888   88888b.
                888        d88''88b 888 '88b 888 '88b d8P  Y8b d88P'    888      888   888 '88b
                888    888 888  888 888  888 888  888 88888888 888      888      888   888  888
                Y88b  d88P Y88..88P 888  888 888  888 Y8b.     Y88b.    Y88b.    888   888  888
                 'Y8888P'   'Y88P'  888  888 888  888  'Y8888   'Y8888P  'Y888 8888888 888  888
                                """);
        while (true) {
            Helper.printLine();
            System.out.println("Welcome to ConnectIn");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = Helper.getChoice();
            Helper.printLine();
            this.action(choice);
        }

    }

    @Override
    public void action(int choice) {
        switch (choice) {
            case 1: {
                System.out.println("Login");
                System.out.print("Enter your name: ");
                Scanner scanner = new Scanner(System.in);
                String name = scanner.nextLine();
                if (isUserExists(name)) {
                    Console console = System.console();
                    char[] password = console.readPassword("Enter your password: ");
                    User you = socialMediaService.login(name, new String(password));
                    if (you == null) {
                        System.out.println("Invalid password!");
                        break;
                    }
                    UserMenuView userMenuView = new UserMenuView(you, socialMediaService);
                    userMenuView.showMenu();
                } else {
                    System.out.println("User does not exist!");
                }
                break;
            }
            case 2: {
                System.out.println("Register");
                System.out.print("Enter your name: ");
                Scanner scanner = new Scanner(System.in);
                String name = scanner.nextLine();
                if (!isUserExists(name)) {
                    Console console = System.console();
                    char[] password = console.readPassword("Enter your password: ");
                    char[] confirmPassword = console.readPassword("Confirm your password: ");
                    if (!new String(password).equals(new String(confirmPassword))) {
                        System.out.println("Passwords do not match!");
                        break;
                    }
                    User you = socialMediaService.register(name, new String(password));
                    UserMenuView userMenuView = new UserMenuView(you, socialMediaService);
                    userMenuView.showMenu();
                } else {
                    System.out.println("User already exists!");
                }
            }
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }

    }

    private boolean isUserExists(String name) {
        HashMap<User, HashMap<User, Integer>> tempDb = socialMediaService.tempDb;
        for (User user : tempDb.keySet()) {
            if (user.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
