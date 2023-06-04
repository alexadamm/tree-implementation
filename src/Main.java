import domain.model.User;
import domain.services.SocialMediaService;
import presentation.MainMenuView;

import java.util.HashMap;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        HashMap<User, HashMap<User, Integer>> tempDb = new HashMap<>();
        MainMenuView mainMenuView = new MainMenuView(new SocialMediaService(tempDb));
        mainMenuView.showMenu();
    }
}