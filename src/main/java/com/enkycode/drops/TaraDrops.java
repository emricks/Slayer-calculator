package com.enkycode.drops;

import com.enkycode.ConfigLoader;
import com.enkycode.Slayers;

public class TaraDrops extends SlayerDrops {
    public TaraDrops(int t) {
        tier = t;
        makeDrops();
        printDrops();
    }
    private void makeDrops() {
        ConfigLoader cLoader = new ConfigLoader(Slayers.S, tier);
        items = cLoader.loadItems();
    }
}
