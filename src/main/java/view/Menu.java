package view;

import logic.PercentileCalculator;
import logic.createIssue.IssueCreator;
import logic.getIssue.IssueGetter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Menu {

    private static final int MAX_NUM_OF_TIMES = 10;

    private static Scanner scanner = new Scanner(System.in);

    public static void menu() throws IOException, InterruptedException {
        long[] timesOfGetSleepSec = new long[MAX_NUM_OF_TIMES];
        long[] timesOfGetSleepFiveSec = new long[MAX_NUM_OF_TIMES];
        long[] timesOfGetSleepLessSec = new long[MAX_NUM_OF_TIMES];
        boolean menu = true;
        while (menu) {
            appereance();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":

                    List info = IssueGetter.getIssuesOfProject();
                    for (int i = 0; i < 10; i++) {

                        timesOfGetSleepSec[i] = IssueGetter.getIssueById(info);
                        Thread.sleep(1000);
                    }
                    Printer.print("endpoint: " + "getting issue " + "interval: 1000 ms " +
                            "90th procentile:\t"
                            + PercentileCalculator.CalculatePercentile(timesOfGetSleepSec));

                    for (int i = 0; i < 10; i++) {
                        timesOfGetSleepFiveSec[i] = IssueGetter.getIssueById(info);
                        Thread.sleep(5000);
                    }
                    Printer.print("endpoint: " + "getting issue " + "interval: 1000 ms " +
                            "90th procentile:\t"
                            + PercentileCalculator.CalculatePercentile(timesOfGetSleepFiveSec));

                    for (int i = 0; i < 10; i++) {

                        timesOfGetSleepLessSec[i] = IssueGetter.getIssueById(info);

                        Thread.sleep(800);
                    }
                    Printer.print("endpoint: " + "getting issue " + "interval: 1000 ms " +
                            "90th procentile:\t"
                            + PercentileCalculator.CalculatePercentile(timesOfGetSleepLessSec));

                    continue;
                case "2":
                    IssueCreator.createIssue();
                    break;
                case "3":
                    menu = false;
            }


        }


    }

    private static void appereance() {
        System.out.println("\t\tmenu");
        System.out.println("1--test getting issue endpoint by project short name");
        System.out.println("2--test creating issue endpoint by project short name");
        System.out.println("3-- exit");
    }
}

