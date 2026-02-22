package com.enkycode.drops;

import com.enkycode.ConfigLoader;
import com.enkycode.Slayers;

import java.util.List;
import java.util.Scanner;

public class SlayerDrops implements Drops{
    private List<Item> totalItems;
    private List<Item> items;
    private Item selectedItem;
    private final int tier;
    private final Slayers slayer;

    public SlayerDrops(Slayers slayer, int tier) {
        this.tier = tier;
        this.slayer = slayer;
        makeDrops();
        printDrops();
    }

    private void makeDrops() {
        ConfigLoader cLoader = new ConfigLoader(slayer);
        totalItems = cLoader.loadItems();
        items = totalItems.subList(0, slayer.numDrops(tier));
    }

    private void printDrops() {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose a drop to calculate:");
        for (Item item : items) {
            System.out.println(item.getName());
        }
        String choice;
        boolean found = false;
        while (selectedItem == null) {
            choice = input.nextLine();
            for (Item item : items) {
                if (choice.toLowerCase().trim().contains(item.getName().toLowerCase())) {
                    selectedItem = item;
                    found = true;
                    break;
                }
            }
            if (found) {break;} else {
                System.out.println("Invalid drop! Please try again.");
            }
        }
    }

    private double calculateChance(int progress, double mf, Slayers s) {
        //System.out.println(selectedItem.getName());
        //System.out.println("Original weight: " + selectedItem.getWeight(tier));
        //System.out.println("Original total weight: " + calculateTotalWeight(tier));
        int required = selectedItem.getRNGRequired();
        //System.out.println("Required RNG meter: " + required);
        selectedItem.multiplyWeight(1+Math.min(2*(double)progress/required, 2), tier);
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
