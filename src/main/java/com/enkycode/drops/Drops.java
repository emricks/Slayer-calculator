package com.enkycode.drops;

import com.enkycode.Slayers;

import java.util.List;

public interface Drops {
    void printResults(int progress, double mf, Slayers slayer);

    boolean isRngActive();
    List<Item> getItems();
}
