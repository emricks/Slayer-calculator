package com.enkycode.drops;

import com.enkycode.ConfigLoader;
import com.enkycode.Slayers;

public class RevDrops extends SlayerDrops implements Drops {

    public RevDrops(int t) {
        tier = t;
        makeDrops();
        printDrops();
    }

    private void makeDrops() {
        ConfigLoader cLoader = new ConfigLoader(Slayers.Z, tier);
        items = cLoader.loadItems();
    }
}
