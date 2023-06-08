import domain.model.User;
import domain.services.SocialMediaService;
import presentation.ICLIView;
import presentation.MainMenuView;
import utils.Helper;

import java.util.HashMap;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        HashMap<User, HashMap<User, Integer>> tempDb = new HashMap<>();

        SocialMediaService socialMediaService = new SocialMediaService(tempDb);
        Helper.addConnectionRecommendationsDemo(socialMediaService);
        ICLIView mainMenuView = new MainMenuView(socialMediaService);
        mainMenuView.showMenu();
    }
}