package com.enkycode.drops;

public class Item {
    private final String name;
    private double weight;
    private final String table;
    public Item(String name, double weight, String table) {
        this.name = name;
        this.weight = weight;
        this.table = table;
    }
    public String getName() {
        return name;
    }
    public double getWeight() {
        return weight;
    }
    public String getTable() {
        return table;
    }
    public void multiplyWeight(double factor) {
        weight *= factor;
    }
    public boolean equals(Item other) {
        return other.getName().equals(name) && other.getWeight() == weight && other.getTable().equals(table);
    }
}

