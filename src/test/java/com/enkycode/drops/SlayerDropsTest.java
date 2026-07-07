package com.enkycode.drops;

import com.enkycode.Slayers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assertions;

import java.util.stream.Stream;

public class SlayerDropsTest {
    private String getPlaceholderDrop(Slayers slayer) {
        return switch(slayer) {
            case Z -> "Revenant Flesh";
            case S -> "Tarantula Web";
            case W -> "Wolf Tooth";
            case E -> "Null Sphere";
            case V -> "Coven Seal";
            case B -> "Derelict Ashe";
        };
    }

    @ParameterizedTest
    @MethodSource("testParseItemArgs")
    public void testParseItem(String input, String expected, Slayers slayer, int tier) {
        String placeholderDrop = getPlaceholderDrop(slayer);
        SlayerDrops calculator = new SlayerDrops(slayer, tier, 9, placeholderDrop, placeholderDrop);
        Item parsedItem = calculator.parseItem(input);
        Assertions.assertEquals(expected, parsedItem == null ? "null" : parsedItem.getName());
    }
    private static Stream<Arguments> testParseItemArgs() {
        return Stream.of(
                Arguments.of("paiwfpawapwjf a9wfaw 7", "null", Slayers.Z, 1), // garbage
                Arguments.of("Spider Catalyst", "Spider Catalyst", Slayers.S, 3), // correct
                Arguments.of("scythe blade", "null", Slayers.W, 4), // wrong slayer
                Arguments.of("Judgement Core", "null", Slayers.E, 2), // Out of range for tier
                Arguments.of("ch oCO lATechI p", "Chocolate Chip", Slayers.V, 5) // Badly typed
        );
    }

    @ParameterizedTest
    @MethodSource("testMakeDropsArgs")
    public void testMakeDrops(int tier, int level, Slayers slayer, int expectedLength) {
        String placeholderDrop = getPlaceholderDrop(slayer);
        SlayerDrops calculator = new SlayerDrops(slayer, tier, level, placeholderDrop, placeholderDrop);
        Assertions.assertEquals(expectedLength, calculator.getItems().size());
    }
    private static Stream<Arguments> testMakeDropsArgs() {
        return Stream.of(
                Arguments.of(4, 9, Slayers.Z, 11),
                Arguments.of(5, 6, Slayers.Z, 10),
                Arguments.of(3, 5, Slayers.Z, 7)
        );
    }

    @ParameterizedTest
    @MethodSource("testStringsMatchArgs")
    public void testStringsLooselyMatch(String a, String b, boolean expected) {
        Assertions.assertEquals(expected, SlayerDrops.stringsLooselyMatch(a, b));
    }
    public static Stream<Arguments> testStringsMatchArgs() {
        return Stream.of(
                Arguments.of("qwertyuiop", "asdfghjkl", false), // completely different
                Arguments.of("undertale", "deltarune", false), // anagrams
                Arguments.of("HELLO", "hello", true), // different cases
                Arguments.of("jAvA", "JaVa", true), // mixed cases
                Arguments.of("slayer calculator", "SlayerCalculator", true), // space vs. no space
                Arguments.of("s lay ercal cul ato     r  ", "SLAYER CALCULATOR", true), // wrong spacing
                Arguments.of("s lAyE R   CaL cU LAT oR", "s l a y er calc ulator", true) // just weird
        );
    }

    /*
    @ParameterizedTest
    @MethodSource("testCalculateChanceArgs")
    public void testCalculateChance(String expected, String input, int progress, int mf, int tier, int level, Slayers slayer) throws Exception {
        // TODO: Add test code
    }
    private static Stream<Arguments> testCalculateChanceArgs() {
        return Stream.of(
                // TODO: Add testcases
        );
    }

     */


}
