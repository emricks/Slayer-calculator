package com.enkycode;

import java.util.*;
import com.enkycode.drops.*;
public class Main {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- Slayer Calculator ---");
        System.out.println("Choose a calculation mode:");
        System.out.println("1. Single boss");
        System.out.println("2. Multiple bosses");
        int choice = getIntegerResponse(1, 2, null);

        Slayers slayer = getSlayer();
        int level = getIntegerResponse(1, slayer==Slayers.V?5:9, "What slayer level are you?");
        int tier = getIntegerResponse(1, slayer==Slayers.W||slayer==Slayers.E||slayer==Slayers.B?4:5, "What tier of slayer are you doing?");

        SlayerDrops calculator = new SlayerDrops(slayer, tier, level);

        int progress = 0;
        if (calculator.isRngActive()) {
            progress = getIntegerResponse(0, Integer.MAX_VALUE, "How much RNG Meter XP do you have?");
        }
        double mf = getIntegerResponse(0, Integer.MAX_VALUE, "How much magic find do you have?");
        boolean mfBoost = getBooleanResponse("Is Aatrox's Pathfinder perk active?");
        mf = mfBoost ? (mf*1.2+20) : mf;

        if (choice == 2) {
            boolean xpBoost = getBooleanResponse("Is Aatrox's Slayer XP Boost perk active?");
            int numBosses = getIntegerResponse(1, Integer.MAX_VALUE, "How many bosses will you do?");

            calculator.printResultsMultiple(numBosses, progress, mf, xpBoost);
        } else {
            calculator.printResults(progress, mf);
        }


    }

    public static int getIntegerResponse(int min, int max, String prompt) {
        int response = 0;
        System.out.print(prompt == null ? "" : prompt+"\n");
        do {
            String in = input.nextLine();
            try {
                response = Integer.parseInt(in);
                if (response < min || response > max) {
                    System.out.println("Invalid number. Please try again.");
                    response = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        } while (response == 0);
        return response;
    }
    public static boolean getBooleanResponse(String prompt) {
        String in;
        while (true) {
            System.out.print(prompt == null ? "" : prompt+"\n");
            in = input.nextLine();
            in = in.toLowerCase();
            if (in.contains("y") || in.contains("t")) {
                return true;
            } else if (in.contains("n") || in.contains("f")) {
                return false;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static Slayers getSlayer() {
        Slayers slayer;
        while (true) {
            System.out.println("Choose a slayer:");
            for (Slayers s : Slayers.values()) {
                System.out.println(s.fromEnumFormat());
            }
            try {
                slayer = Slayers.valueOf(input.nextLine().toUpperCase().substring(0, 1));
                return slayer;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid slayer. Please try again.");
            }
        }
    }
    public static Slayers getSlayerTesting(String in) {
        Slayers slayer;
        try {
            slayer = Slayers.valueOf(in.toUpperCase().substring(0, 1));
            return slayer;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static int getTierTesting(Slayers slayer, String in) {
        int tier;
        if (slayer == Slayers.Z || slayer == Slayers.S || slayer == Slayers.V) {
            try {
                tier = Integer.parseInt(in);
            } catch (NumberFormatException e) {
                return -1;
            }
            if (1 <= tier && tier <= 5) {
                return tier;
            } else {
                return -1;
            }
        } else {
            try {
                tier = Integer.parseInt(in);
            } catch (NumberFormatException e) {
                return -1;
            }
            if (1 <= tier && tier <= 4) {
                return tier;
            } else {
                return -1;
            }
        }
    }

}