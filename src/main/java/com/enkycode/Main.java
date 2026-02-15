package com.enkycode;

import java.util.*;
import com.enkycode.drops.*;
public class Main {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Slayers slayer = getSlayer(false, "");
        int tier = getTier(slayer, false, 0);

        assert slayer != null;
        Drops calculator = switch (slayer) {
            case Z -> new RevDrops(tier);
            case S -> new TaraDrops(tier);
            default -> new RevDrops(tier);
        };
        System.out.println("How much RNG Meter XP do you have? (Enter 0 if your meter is not selected on the drop.)");
        int progress = input.nextInt();
        System.out.println("How much magic find do you have?");
        double mf = input.nextInt();
        input.nextLine();
        boolean mfBoost = getMayor();
        mf *= mfBoost ? (mf*1.2)+20 : 1;
        calculator.printResults(progress, mf, slayer);
    }

    public static Slayers getSlayer(boolean testing, String in) {
        Slayers slayer;
        while (true) {
            System.out.println("Choose a Slayer:");
            for (Slayers s : Slayers.values()) {
                System.out.println(s.fromEnumFormat());
            }
            try {
                slayer = Slayers.valueOf(in.isEmpty() ? input.next() : in);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid slayer. Please try again.");
                if (testing) {return null;}
            }
        }
        return slayer;
    }

    public static int getTier(Slayers slayer, boolean testing, int in) {
        int tier;
        while (true) {
            if (slayer == Slayers.Z || slayer == Slayers.S || slayer == Slayers.V) {
                System.out.println("Choose a tier 1-5.");
                tier = in == 0 ? input.nextInt() : in;
                if (1 <= tier && tier <= 5) {
                    break;
                } else {
                    System.out.println("Invalid tier for this slayer type. Please try again.");
                    if (testing) {return 0;}
                }
            } else {
                System.out.println("Choose a tier 1-4.");
                tier = in == 0 ? input.nextInt() : in;
                if (1 <= tier && tier <= 4) {
                    break;
                } else {
                    System.out.println("Invalid tier for this slayer type. Please try again.");
                    if (testing) {return 0;}
                }
            }
        }
        return tier;
    }

    public static boolean getMayor() {
        String in;
        while (true) {
            System.out.println("Is Aatrox mayor?");
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
}