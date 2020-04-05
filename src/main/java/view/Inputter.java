package view;

import java.util.Scanner;
public class Inputter{
    private static Scanner scanner = new Scanner(System.in);

    public static String input() {

        return scanner.nextLine();
    }


    public static String inputShortNameOfPrj(){
        System.out.println("please enter short name of project");
        return scanner.nextLine();
    }

    public static String inputAccToken(){
        System.out.println("please enter permanent access token");
        return scanner.nextLine();
    }
}
