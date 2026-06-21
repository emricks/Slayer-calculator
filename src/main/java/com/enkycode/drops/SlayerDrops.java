package com.enkycode.drops;

import com.enkycode.ConfigLoader;
import com.enkycode.Slayers;

import java.util.List;
import java.util.Scanner;

public class SlayerDrops implements Drops{
    private List<Item> totalItems;
    private List<Item> items;
    private Item rngItem;
    private Item selectedItem;
    private boolean rngActive = true;
    private final int tier;
    private final Slayers slayer;

    public boolean isRngActive() {
        return rngActive;
    }

    public SlayerDrops(Slayers slayer, int tier) {
        this.tier = tier;
        this.slayer = slayer;
        makeDrops();
        printDrops();
    }

    public SlayerDrops(Slayers slayer, int tier, String calculateItem, String meteredItem) {
        this.tier = tier;
        this.slayer = slayer;
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
        totalItems = cLoader.loadItems();
        items = totalItems.subList(0, slayer.numDrops(tier));
    }

    private void printDrops() {
        for (Item item : items) {
            System.out.println(item.getName());
        }
        selectedItem = getItemFromUser("Choose a drop to calculate.");
        rngItem = getItemFromUser("Which drop is your RNG meter selected on? (Enter 'Nothing' if it's not selected on any drop.)");
    }

    public static boolean stringsLooselyMatch(String a, String b) {
        return a.toLowerCase().replaceAll(" ", "").contains(b.toLowerCase().replaceAll(" ", ""));
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


    private double calculateChance(int progress, double mf, Slayers s) {
        if (selectedItem.getTable().equals("Token") || progress >= selectedItem.getRNGRequired()) {
            return 1;
        }
        //System.out.println(selectedItem.getName());
        //System.out.println("Original weight: " + selectedItem.getWeight(tier));
        //System.out.println("Original total weight: " + calculateTotalWeight(tier));
        int required = rngItem.getRNGRequired();
        //System.out.println("Required RNG meter: " + required);
        rngItem.multiplyWeight(1+Math.min(2*(double)progress/required, 2), tier);
        //System.out.println("Weight after RNG meter applied: " + selectedItem.getWeight(tier));
        double weightSum = calculateTotalWeight(tier);
        //dSystem.out.println("Total weight after RNG meter applied: " + weightSum);
        for (Item item : items) {
            if (item.getWeight(tier)/weightSum < 0.05) {
                item.multiplyWeight(1 + mf / 100, tier);
            }
        }
        //System.out.println("Weight after MF applied: " + selectedItem.getWeight(tier));
        //System.out.println("Total weight after MF applied: " + calculateTotalWeight(tier));
        //System.out.println("Chance: " + selectedItem.getWeight(tier)/calculateTotalWeight(tier));
        return selectedItem.getWeight(tier)/calculateTotalWeight(tier);
    }

    public void printResults(int progress, double mf, Slayers s) {
        double chance = (double) Math.round(calculateChance(progress, mf, s) * 100000) /1000;
        System.out.println("You have a " + chance + "% chance of receiving " + selectedItem.getName());
    }


    private double calculateTotalWeight(int t) {
        // Effectively sets returned chance to 100% for token items
        List<Item> theseItems = totalItems.subList(0, slayer.numDrops(t));
        if (selectedItem.getTable().equals("Token")) {
            return selectedItem.getWeight(t);
        }

        double weightSum = 0;
        for (Item item : theseItems) {
            if (selectedItem.getTable().equals("Extra") || selectedItem.getTable().equals("Main") && !item.getTable().equals("Extra")) {
                //System.out.println("    Item: "+item);
                weightSum += item.getWeight(t);
                //System.out.println("    Weight Sum after "+item.getName()+": " + weightSum);
            }
        }
        return weightSum;
    }

    public List<Item> getItems() {
        return items;
    }
}
