package utils;

import java.util.Scanner;

import domain.model.User;
import domain.services.SocialMediaService;

public class Helper {
    public static void printLine() {
        System.out.println("--------------------------------------------------");
    }

    public static int getChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return choice;
    }

    public static void addConnectionRecommendationsDemo(SocialMediaService socialMediaService) {

        User adam = socialMediaService.register("Adam");
        User yazid = socialMediaService.register("Yazid");
        User azmi = socialMediaService.register("Azmi");
        User davin = socialMediaService.register("Davin");
        User aji = socialMediaService.register("Aji");
        User ikmal = socialMediaService.register("Ikmal");
        User akib = socialMediaService.register("Akib");

        socialMediaService.requestConnection(adam, yazid);
        socialMediaService.requestConnection(azmi, yazid);
        socialMediaService.requestConnection(davin, aji);
        socialMediaService.requestConnection(ikmal, yazid);
        socialMediaService.requestConnection(ikmal, davin);
        socialMediaService.requestConnection(ikmal, akib);
        socialMediaService.requestConnection(akib, aji);
        socialMediaService.requestConnection(aji, yazid);
        socialMediaService.requestConnection(adam, davin);

        socialMediaService.acceptRequestConnection(yazid, adam);
        socialMediaService.acceptRequestConnection(yazid, azmi);
        socialMediaService.acceptRequestConnection(aji, davin);
        socialMediaService.acceptRequestConnection(yazid, ikmal);
        socialMediaService.acceptRequestConnection(davin, ikmal);
        socialMediaService.acceptRequestConnection(akib, ikmal);
        socialMediaService.acceptRequestConnection(aji, akib);
        socialMediaService.acceptRequestConnection(yazid, aji);
        socialMediaService.acceptRequestConnection(davin, adam);
    }
}
