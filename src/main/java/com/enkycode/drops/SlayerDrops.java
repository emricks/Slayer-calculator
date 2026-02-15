package com.enkycode.drops;

import com.enkycode.Slayers;

import java.util.List;
import java.util.Scanner;

public class SlayerDrops implements Drops{
    protected List<Item> items;
    protected Item selectedItem;
    protected int tier;

    protected void printDrops() {
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
                if (choice.toLowerCase().contains(item.getName().toLowerCase())) {
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

    protected double calculateChance(int progress, double mf, Slayers s) {

        int required = (int) Math.round( (s == Slayers.V ? 250.0 : 500.0)/(selectedItem.getWeight()/calculateTotalWeight()) );
        selectedItem.multiplyWeight(1+Math.min(2*(double)progress/required, 2));
        double weightSum = calculateTotalWeight();
        for (Item item : items) {
            if (item.getWeight()/weightSum < 0.05) {
                item.multiplyWeight(1 + (double) mf /100);
            }
        }
        return selectedItem.getWeight()/calculateTotalWeight();
    }

    public void printResults(int progress, double mf, Slayers s) {
        double chance = (double) Math.round(calculateChance(progress, mf, s)*100000)/1000;
        System.out.println("You have a " + chance + "% chance of receiving your chosen drop.");
    }

    protected double calculateTotalWeight() {
        if (selectedItem.getTable().equals("Token")) {
            return selectedItem.getWeight();
        }
        double weightSum = 0;
        for (Item item : items) {
            if (selectedItem.getTable().equals("Extra") || selectedItem.getTable().equals("Main") && !item.getTable().equals("Extra")) {
                weightSum += item.getWeight();
            }
        }
        return weightSum;
    }

    public List<Item> getItems() {
        return items;
    }
}
