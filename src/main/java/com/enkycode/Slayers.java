package com.enkycode;

public enum Slayers {
    Z,
    S,
    W,
    E,
    V,
    B;

    String fromEnumFormat() {
        return switch(this) {
            case Z -> "Z - Zombie Slayer";
            case S -> "S - Spider Slayer";
            case W -> "W - Wolf Slayer";
            case E -> "E - Enderman Slayer";
            case V -> "V - Vampire Slayer";
            case B -> "B - Blaze Slayer";
        };
    }
}
