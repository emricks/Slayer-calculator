package com.enkycode.drops;

import com.enkycode.Slayers;

import java.util.List;

public interface Drops {
    void printResults(int progress, double mf, Slayers slayer);


    List<Item> getItems();
}
