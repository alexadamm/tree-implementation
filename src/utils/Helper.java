package utils;

import java.util.Scanner;

public class Helper {
    public static void printLine() {
        System.out.println("--------------------------------------------------");
    }

    public static int getChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return choice;
    }
}
