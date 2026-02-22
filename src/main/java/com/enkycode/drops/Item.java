package com.enkycode.drops;

import java.util.Arrays;

public class Item {
    private final String name;
    private final double[] tierWeights;
    private final int RNGRequired;
    private final String table;

    public Item(String name, double[] tierWeights, String table, int RNGRequired) {
        this.name = name;
        this.tierWeights = tierWeights;
        this.table = table;
        this.RNGRequired = RNGRequired;
    }
    public String getName() {
        return name;
    }
    public double getWeight(int tier) {
        return tierWeights[tier-1];
    }
    public double[] getTierWeights() {
        return tierWeights;
    }
    public String getTable() {
        return table;
    }
    public int getRNGRequired() {
        return RNGRequired;
    }
    public void multiplyWeight(double factor, int tier) {
        tierWeights[tier-1] *= factor;
    }
    public boolean equals(Item other) {
        return other.getName().equals(name) && Arrays.equals(other.getTierWeights(), getTierWeights()) && other.getTable().equals(table);
    }
    public String toString() {
        return "\nName: " + name +
                ", Weights: " +  Arrays.toString(tierWeights) +
                ", Table: " + table;
    }
}

