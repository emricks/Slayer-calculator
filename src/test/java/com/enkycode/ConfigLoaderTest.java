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
        ConfigLoader configLoader = new ConfigLoader(slayer, tier);
        List<Item> items = configLoader.loadItems();
        Item actual = items.get(idx);

        Assertions.assertTrue(expected.equals(actual));
    }
    public static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(13, 5, Slayers.Z, new Item("Warden Heart", 2, "Main")),
                Arguments.of(2, 2, Slayers.Z, new Item("Pestilence Rune", 83, "Extra")),
                Arguments.of(6, 4, Slayers.Z, new Item("Beheaded Horror", 20, "Main")),

                Arguments.of(5, 3, Slayers.S, new Item("Bane 6", 25, "Main")),
                Arguments.of(0, 1, Slayers.S, new Item("Tarantula Web", 10000, "Token")),
                Arguments.of(8, 4, Slayers.S, new Item("Darkness Within Rune", 50, "Extra"))
        );
    }
}
