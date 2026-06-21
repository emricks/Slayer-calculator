package com.enkycode;

import java.util.*;
import com.enkycode.drops.*;
public class Main {
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Slayers slayer = getSlayer();
        int level = getLevel(slayer);
        int tier = getTier(slayer);

        Drops calculator = new SlayerDrops(slayer, tier, level);
        int progress = 0;
        if (calculator.isRngActive()) {
            System.out.println("How much RNG Meter XP do you have?");
            progress = input.nextInt();
        }
        System.out.println("How much magic find do you have?");
        double mf = input.nextInt();
        input.nextLine();
        boolean mfBoost = getMayor();
        mf *= mfBoost ? (mf*1.2+20) : 1;

        calculator.printResults(progress, mf, slayer);
    }

    public static Slayers getSlayer() {
        Slayers slayer;
        while (true) {
            System.out.println("Choose a slayer:");
            for (Slayers s : Slayers.values()) {
                System.out.println(s.fromEnumFormat());
            }
            try {
                slayer = Slayers.valueOf(input.nextLine());
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

    public static int getTier(Slayers slayer) {
        int tier;
        while (true) {
            if (slayer == Slayers.Z || slayer == Slayers.S || slayer == Slayers.V) {

                System.out.println("Choose a tier 1-5.");
                String in = input.nextLine();

                try {
                    tier = Integer.parseInt(in);
                    if (tier >= 1 && tier <= 5) {
                        return tier;
                    } else {
                        System.out.println("Invalid tier. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid tier. Please try again.");
                }

            } else {

                System.out.println("Choose a tier 1-4.");
                String in = input.nextLine();

                try {
                    tier = Integer.parseInt(in);
                    if (tier >= 1 && tier <= 4) {
                        return tier;
                    } else {
                        System.out.println("Invalid tier. Please try again.");
                    }
                }  catch (NumberFormatException e) {
                    System.out.println("Invalid tier. Please try again.");
                }

            }
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

    public static int getLevel(Slayers slayer) {
        int level;
        while (true) {
            System.out.println("What slayer level are you?");
            String in = input.nextLine();
            if (slayer == Slayers.V) {
                try {
                    level = Integer.parseInt(in);
                    if (level >= 0 && level <= 5) {
                        return level;
                    } else {
                        System.out.println("Invalid level for this slayer. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid level for this slayer. Please try again.");
                }
            } else {
                try {
                    level = Integer.parseInt(in);
                    if (level >= 0 && level <= 9) {
                        return level;
                    } else {
                        System.out.println("Invalid level for this slayer. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid level for this slayer. Please try again.");
                }
            }
        }
    }
}