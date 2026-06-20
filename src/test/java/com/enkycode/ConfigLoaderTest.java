package com.enkycode;

import com.enkycode.drops.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class ConfigLoaderTest {
    @ParameterizedTest
    @MethodSource("testArgs")
    public void testLoadItems(int idx, int tier, Slayers slayer, Item expected) {
        ConfigLoader configLoader = new ConfigLoader(slayer);
        List<Item> items = configLoader.loadItems();
        items = items.subList(0, slayer.numDrops(tier));
        Item actual = items.get(idx);
        if (!expected.equals(actual)) {
            System.out.println(actual);
            System.out.println(expected);
        }
        Assertions.assertTrue(expected.equals(actual));
    }
    public static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(14, 5, Slayers.Z, new Item("Revenant Viscera", new double[]{0, 0, 0, 0, 2000}, "Main", 3674, 7)),
                Arguments.of(7, 3, Slayers.Z, new Item("Revenant Shard", new double[]{0, 0, 100, 150, 200}, "Main", 36742, 0)),
                Arguments.of(2, 2, Slayers.Z, new Item("Pestilence Rune", new double[]{0, 83, 333, 833, 833}, "Extra", 7977, 2)),

                Arguments.of(14, 5, Slayers.S, new Item("Primordial Eye", new double[]{0, 0, 0, 0, 2}, "Main", 3513250, 7)),
                Arguments.of(7, 3, Slayers.S, new Item("Tarantula Talisman", new double[]{0, 0, 10, 20, 30}, "Main", 234216, 6)),
                Arguments.of(3, 2, Slayers.S, new Item("Bite Rune", new double[]{0, 83, 333, 833, 833}, "Extra", 7657, 0)),

                Arguments.of(8, 4, Slayers.W, new Item("Grizzly Salmon", new double[]{0, 0, 0, 7}, "Main", 880500, 7)),
                Arguments.of(5, 3, Slayers.W, new Item("Red Claw Egg", new double[]{0, 0, 5, 15}, "Main", 410900, 5)),
                Arguments.of(2, 2, Slayers.W, new Item("Spirit Rune", new double[]{0, 83, 333, 833}, "Extra", 7917, 2)),

                Arguments.of(18, 4, Slayers.E, new Item("Ender Slayer 7", new double[]{0, 0, 0, 2}, "Main", 3542250, 7)),
                Arguments.of(7, 3, Slayers.E, new Item("Hazmat Enderman", new double[]{0, 0, 140, 220}, "Main", 32202, 4)),
                Arguments.of(0, 1, Slayers.E, new Item("Null Sphere", new double[]{10000, 10000, 10000, 10000}, "Token", 500, 0)),

                Arguments.of(8, 5, Slayers.V, new Item("The One", new double[]{0, 0, 0, 0, 3}, "Main", 12525, 5)),
                Arguments.of(5, 3, Slayers.V, new Item("Guardian Lucky Block", new double[]{0, 0, 10, 10, 10}, "Main", 3600, 4)),
                Arguments.of(0, 1, Slayers.V, new Item("Coven Seal", new double[]{100, 100, 100, 100, 100}, "Token", 250, 0))
        );
    }
}
