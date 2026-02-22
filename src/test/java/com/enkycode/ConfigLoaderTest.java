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
        Assertions.assertTrue(expected.equals(actual));
    }
    public static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(13, 5, Slayers.Z, new Item("Warden Heart", new double[]{0, 0, 0, 0, 2}, "Main", 3631749)),
                Arguments.of(2, 2, Slayers.Z, new Item("Pestilence Rune", new double[]{0, 83, 333, 833, 833}, "Extra", 7977)),
                Arguments.of(6, 4, Slayers.Z, new Item("Beheaded Horror", new double[]{0, 0, 10, 20, 20}, "Main", 310925)),

                Arguments.of(5, 3, Slayers.S, new Item("Bane 6", new double[]{0, 0, 25, 50, 50}, "Main", 120268)),
                Arguments.of(0, 1, Slayers.S, new Item("Tarantula Web", new double[]{10000, 10000, 10000, 10000, 10000}, "Token", 500)),
                Arguments.of(8, 4, Slayers.S, new Item("Darkness Within Rune", new double[]{0, 0, 0, 50, 100}, "Extra", 74930)),

                Arguments.of(1, 2, Slayers.W, new Item("Hamster Wheel", new double[]{0, 2000, 2000, 2000}, "Main", 3000)),
                Arguments.of(7, 4, Slayers.W, new Item("Overflux Capacitor", new double[]{0, 0, 0, 5}, "Main", 1232700)),
                Arguments.of(2, 3, Slayers.W, new Item("Spirit Rune", new double[]{0, 83, 333, 833}, "Extra", 7914)),

                Arguments.of(2, 3, Slayers.E, new Item("Summoning Eye", new double[]{0, 80, 80, 80}, "Main", 74250)),
                Arguments.of(9, 4, Slayers.E, new Item("Smarty Pants", new double[]{0, 0, 0, 250}, "Main", 28338)),
                Arguments.of(3, 3, Slayers.E, new Item("Endersnake Rune", new double[]{0, 0, 333, 800}, "Extra", 9438))
        );
    }
}
