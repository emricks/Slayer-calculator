package com.enkycode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SlayersTest {
    @Test
    @DisplayName("Testing fromEnumFormat returns correct value.")
    public void testFromEnumFormat() {
        Assertions.assertEquals("S - Spider Slayer", Slayers.S.fromEnumFormat());
        Assertions.assertEquals("V - Vampire Slayer", Slayers.V.fromEnumFormat());
        Assertions.assertEquals("E - Enderman Slayer", Slayers.E.fromEnumFormat());
    }
}
