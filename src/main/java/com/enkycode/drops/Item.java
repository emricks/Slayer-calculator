package com.enkycode.drops;

import java.util.Arrays;

public class Item {
    private final String name;
    private final double[] tierWeights;
    private final int RNGRequired;
    private final String table;
    private final int level;

    public Item(String name, double[] tierWeights, String table, int RNGRequired, int level) {
        this.name = name;
        this.tierWeights = tierWeights;
        this.table = table;
        this.RNGRequired = RNGRequired;
        this.level = level;
    }
    public String getName() {
        return name;
    }
    public double getWeight(int tier) {
        return tierWeights[tier-1];
    }
    public String getTable() {
        return table;
    }
    public int getRNGRequired() {
        return RNGRequired;
    }
    public int getLevel() {
        return level;
    }
    public void multiplyWeight(double factor, int tier) {
        tierWeights[tier-1] *= factor;
    }
    public boolean equals(Item other) {
        return other.name.equals(this.name)
                && Arrays.equals(other.tierWeights, this.tierWeights)
                && other.table.equals(this.table)
                && other.RNGRequired == this.RNGRequired
                && other.level == this.level;
    }
    public String toString() {
        return "\nName: " + name +
                ", Weights: " +  Arrays.toString(tierWeights) +
                ", Table: " + table +
                ", RNGRequired: " + RNGRequired +
                ", Level: " + level;
    }
}

