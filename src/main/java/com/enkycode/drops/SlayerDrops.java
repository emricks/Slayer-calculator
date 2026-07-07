package com.enkycode.drops;

import com.enkycode.ConfigLoader;
import com.enkycode.Slayers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SlayerDrops{
    private List<Item> items;
    private Item rngItem;
    private Item selectedItem;
    private boolean rngActive = true;
    private final int tier;
    private final Slayers slayer;
    private final int level;

    private Item rngItemCopy;
    private Item selectedItemCopy;
    private List<Item> itemsCopy;

    private void copyItems() {
        itemsCopy = items.stream().map(item -> {
            Item copiedItem = item.getCopy();
            if (item.getName().equals(rngItem.getName())) {
                rngItemCopy = copiedItem;
            }
            if (item.getName().equals(selectedItem.getName())) {
                selectedItemCopy = copiedItem;
            }
            return copiedItem;
        }).toList();

    }

    public boolean isRngActive() {
        return rngActive;
    }

    public SlayerDrops(Slayers slayer, int tier, int level) {
        this.tier = tier;
        this.slayer = slayer;
        this.level = level;
        makeDrops();
        printDrops();
    }

    public SlayerDrops(Slayers slayer, int tier, int level, String calculateItem, String meteredItem) {
        this.tier = tier;
        this.slayer = slayer;
        this.level = level;
        makeDrops();
        selectedItem = parseItem(calculateItem);
        rngItem = parseItem(meteredItem);
    }

    public Item parseItem(String str) {
        Item foundItem = null;
        for (Item item : items) {
            if (stringsLooselyMatch(str, item.getName())) {
                foundItem = item;
            }
        }
        return foundItem;
    }

    private void makeDrops() {
        ConfigLoader cLoader = new ConfigLoader(slayer);
        List<Item> totalItems = cLoader.loadItems();
        items = new ArrayList<>();
        totalItems.subList(0, slayer.numDrops(tier)).forEach(item -> {
            if (level >= item.getLevel()) {
                items.add(item);
            }
        });
    }

    private void printDrops() {
        for (Item item : items) {
            System.out.println(item.getName());
        }
        selectedItem = getItemFromUser("Choose a drop to calculate.");
        rngItem = getItemFromUser("Which drop is your RNG meter selected on? (Enter 'Nothing' if it's not selected on any drop.)");
    }

    public static boolean stringsLooselyMatch(String a, String b) {
        return a.toLowerCase().replaceAll(" ", "").contains(b.toLowerCase().replaceAll(" ", ""))
                || b.toLowerCase().replaceAll(" ", "").contains(a.toLowerCase().replaceAll(" ", ""));
    }

    private Item getItemFromUser(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.println(prompt);
        String choice;
        boolean found = false;
        Item chosenItem = null;
        while (chosenItem == null) {
            choice = input.nextLine();
            for (Item item : items) {
                if (stringsLooselyMatch(choice, "Nothing")) {
                    rngActive = false;
                    return item;
                }
                if (stringsLooselyMatch(choice, item.getName())) {
                    chosenItem = item;
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Invalid Drop Choice.");
            }
        }
        return chosenItem;
    }


    public double calculateChance(int progress, double mf) {
        copyItems();

        if (selectedItemCopy.getTable().equals("Token") || progress >= selectedItemCopy.getRNGRequired()) {
            return 1;
        }
        //System.out.println(selectedItemCopy.getName());
        //System.out.println("Original weight: " + selectedItemCopy.getWeight(tier));
        //System.out.println("Original total weight: " + calculateTotalWeight(tier));
        int required = rngItemCopy.getRNGRequired();
        //System.out.println("Required RNG meter: " + required);
        //System.out.println("Factor:"+ (1+Math.min(2*(double)progress/required, 2)));
        rngItemCopy.multiplyWeight(1+Math.min(2*(double)progress/required, 2), tier);
        //System.out.println("Weight after RNG meter applied: " + rngItemCopy.getWeight(tier));
        double weightSum = calculateTotalWeight(tier);
        //System.out.println("Total weight after RNG meter applied: " + weightSum);
        for (Item item : itemsCopy) {
            if (item.getWeight(tier)/weightSum < 0.05) {
                item.multiplyWeight(1 + mf / 100, tier);
            }
        }
        //System.out.println("Weight after MF applied: " + selectedItemCopy.getWeight(tier));
        //System.out.println("Total weight after MF applied: " + calculateTotalWeight(tier));
        //System.out.println("Chance: " + selectedItemCopy.getWeight(tier)/calculateTotalWeight(tier));
        return selectedItemCopy.getWeight(tier)/calculateTotalWeight(tier);
    }

    public double calculateMultipleChance(int numBosses, int progress, double mf, boolean xpBoost) {
        int xpPerBoss = (int) (switch(slayer) {
            case V -> switch(tier) {
                case 1 -> 10;
                case 2 -> 25;
                case 3 -> 60;
                case 4 -> 120;
                case 5 -> 180;
                default -> 0;
            };
            case Z, S, W, E, B -> switch(tier) {
                case 1 -> 5;
                case 2 -> 25;
                case 3 -> 100;
                case 4 -> 500;
                case 5 -> 1500;
                default -> 0;
            };
        } * (xpBoost ? 1.25 : 1.0));
        double accumulatedChanceToFailAll = 1;
        for (int currentXP = progress; currentXP < progress+numBosses*xpPerBoss; currentXP += xpPerBoss) {
            double chanceToGet = calculateChance(currentXP, mf);
            accumulatedChanceToFailAll *= 1-chanceToGet;
        }
        return 1-accumulatedChanceToFailAll;
    }

    public void printResults(int progress, double mf) {
        double chance = (double) Math.round(calculateChance(progress, mf) * 1000000) /10000;
        System.out.println("You have a " + chance + "% chance of receiving " + selectedItem.getName());
    }
    public void printResultsMultiple(int numBosses, int progress, double mf, boolean xpBoost) {
        double chance = (double) Math.round(calculateMultipleChance(numBosses, progress, mf, xpBoost) * 1000000)/10000;
        System.out.println("You have a " + chance + "% chance of receiving at least one " + selectedItem.getName() + " after " + numBosses + " bosses.");
    }


    private double calculateTotalWeight(int t) {
        // Effectively sets returned chance to 100% for token items
        if (selectedItemCopy.getTable().equals("Token")) {
            return selectedItemCopy.getWeight(t);
        }

        double weightSum = 0;
        for (Item item : itemsCopy) {
            if (selectedItemCopy.getTable().equals("Extra") || selectedItemCopy.getTable().equals("Main") && !item.getTable().equals("Extra")) {
                //System.out.println("    Item: "+item);
                weightSum += item.getWeight(t);
                //System.out.println("    Weight Sum after "+item.getName()+": " + weightSum);
            }
        }
        //System.out.println("Weight sum: " + weightSum);
        return weightSum;
    }

    public List<Item> getItems() {
        return items;
    }
}
