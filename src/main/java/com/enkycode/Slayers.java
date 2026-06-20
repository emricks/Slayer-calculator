package com.enkycode;

public enum Slayers {
    Z,
    S,
    W,
    E,
    V,
    B;

    public String fromEnumFormat() {
        return switch(this) {
            case Z -> "Z - Zombie Slayer";
            case S -> "S - Spider Slayer";
            case W -> "W - Wolf Slayer";
            case E -> "E - Enderman Slayer";
            case V -> "V - Vampire Slayer";
            case B -> "B - Blaze Slayer";
        };
    }

    public int numDrops(int tier) {
        return (int) Math.round(switch (this) {
            case Z -> (3.4*tier-2.2);
            case S -> (3.5*tier-2.7);
            case W -> (2.7*tier-2.0);
            case E -> (2.25*tier*tier-5.35*tier+4.25);
            case V -> (1.14+4.87*Math.log(tier));
            case B -> (1.83*tier*tier*tier-14.5*tier*tier+40.7*tier-26);
        });
    }
}
