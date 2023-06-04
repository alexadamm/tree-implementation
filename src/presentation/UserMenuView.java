package presentation;

import domain.model.User;
import domain.services.SocialMediaService;
import utils.Helper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class UserMenuView implements ICLIView {
    boolean isLoggedIn;
    User currentUser;
    SocialMediaService socialMediaService;

    public UserMenuView(User user, SocialMediaService socialMediaService) {
        this.currentUser = user;
        this.socialMediaService = socialMediaService;
    }

    @Override
    public void showMenu() {
        this.isLoggedIn = true;
        while (isLoggedIn) {
            Helper.printLine();
            System.out.println("1. Add new connection");
            System.out.println("2. Show connection list");
            System.out.println("3. Show connection recommendations");
            System.out.println("4. Show connection requests");
            System.out.println("5. Send message");
            System.out.println("6. Open inbox");
            System.out.println("7. Logout");
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
                System.out.println("Add new connection");
                this.addNewConnection();
            }
                break;
            case 2: {
                System.out.println("Show connection list");
                this.showConnectionsList();
            }
                break;
            case 3: {
                System.out.println("Show connection recommendations");
                this.showRecommendedConnections();
            }
                break;
            case 4: {
                System.out.println("Show connection requests");
                this.showConnectionRequests();
            }
                break;
            case 5: {
                System.out.println("Send message");
                this.sendMessage();
            }
                break;
            case 6: {
                System.out.println("Open inbox");
                this.openInbox();
            }
                break;
            case 7: {
                this.isLoggedIn = false;
            }
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }

    }

    private void addNewConnection() {
        System.out.print("Enter a name to add as connection: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        User result = this.socialMediaService.getUserByName(name);
        if (result == null || result == this.currentUser) {
            System.out.println("User not found");
        } else {
            this.socialMediaService.requestConnection(this.currentUser, result);
            System.out.println("Request sent");
        }
    }

    private LinkedList<User> showConnectionsList() {
        LinkedList<User> connections = this.socialMediaService.getConnections(this.currentUser);
        System.out.println("Your connections: ");
        for (User user : connections) {
            System.out.println(connections.indexOf(user) + 1 + ". " + user.name);
        }
        return connections;
    }

    private void showRecommendedConnections() {
        LinkedList<User> connectionRecommendations = this.socialMediaService.getFriendRecommendations(this.currentUser);
        System.out.println("Choose to send connection request: ");
        System.out.println("0, Back");
        for (User user : connectionRecommendations) {
            System.out.println(connectionRecommendations.indexOf(user) + 1 + ". " + user.name);
        }
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice2 = scanner.nextInt();
        if (choice2 <= 0 || choice2 > connectionRecommendations.size()) {
            return;
        }
        this.socialMediaService.requestConnection(currentUser, connectionRecommendations.get(choice2 - 1));
        System.out.println("Request sent");
    }

    private void showConnectionRequests() {
        LinkedList<User> connectionRequests = this.socialMediaService.getRequestConnections(this.currentUser);
        System.out.println("Choose to accept: ");
        System.out.println("0, Back");
        for (User user : connectionRequests) {
            System.out.println(connectionRequests.indexOf(user) + 1 + ". " + user.name);
        }
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice2 = scanner.nextInt();
        if (choice2 <= 0 || choice2 > connectionRequests.size()) {
            return;
        }
        this.socialMediaService.acceptRequestConnection(currentUser, connectionRequests.get(choice2 - 1));
    }

    private void sendMessage() {
        LinkedList<User> connections = this.showConnectionsList();
        System.out.print("Enter your friend's name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for (User user : connections) {
            if (user.name.equals(name)) {
                System.out.print("Enter your message: ");
                Scanner scanner2 = new Scanner(System.in);
                String message = scanner2.nextLine();
                this.socialMediaService.sendMessage(currentUser, user, message);
                return;
            }
        }
        System.out.println("User not found");
    }

    private void openInbox() {
        LinkedList<User> senders = this.socialMediaService.getInboxSenders(this.currentUser);

        int choice2 = 1;
        while (choice2 >= 1) {
            System.out.println("Choose inbox to open");
            System.out.println("0, Back");
            for (User user : senders) {
                System.out.println(senders.indexOf(user) + 1 + ". " + user.name);
            }
            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            choice2 = scanner.nextInt();
            if (choice2 <= 0 || choice2 > senders.size()) {
                return;
            }
            LinkedList<String> messages = this.socialMediaService.readMessage(currentUser, senders.get(choice2 - 1));
            for (String msg : messages) {
                System.out.println(msg);
            }
            System.out.println("Reply message?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice: ");
            Scanner scanner2 = new Scanner(System.in);
            int choice3 = scanner2.nextInt();
            if (choice3 == 1) {
                System.out.print("Enter your message: ");
                Scanner scanner3 = new Scanner(System.in);
                String message = scanner3.nextLine();
                this.socialMediaService.sendMessage(currentUser, senders.get(choice2 - 1), message);
            }
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
