package com.enkycode.drops;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Stream;

public class RevDropsTest {
    @ParameterizedTest
    @MethodSource("testArgs")
    public void testDrops(String drop, String table, int weight, int idx, int tier) {
        String d = "Revenant Flesh";
        ByteArrayInputStream bais = new ByteArrayInputStream(d.getBytes());
        System.setIn(bais);
        Drops calc = new RevDrops(tier);

        List<Item> items = calc.getItems();
        Assertions.assertTrue(items.get(idx).equals(new Item(drop, weight, table)));
    }
    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of("Beheaded Horror", "Main", 10, 6, 3),
                Arguments.of("Pestilence Rune", "Extra", 333, 2, 3),
                Arguments.of("Revenant Catalyst", "Main", 125, 4, 3),
                Arguments.of("Warden Heart", "Main", 2, 13, 5),
                Arguments.of("Festering Maggot", "Extra", 20, 9, 5),
                Arguments.of("Revenant Flesh", "Token", 10000, 0, 5)
        );
    }
}
