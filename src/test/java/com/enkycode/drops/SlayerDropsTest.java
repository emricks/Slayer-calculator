package com.enkycode.drops;

import com.enkycode.Slayers;
import com.github.stefanbirkner.systemlambda.SystemLambda;
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

    @ParameterizedTest
    @MethodSource("testCalculateChanceArgs")
    public void testCalculateChance(String expected, String input, int progress, int mf, int tier, Slayers slayer) throws Exception {
        Drops calculator = new SlayerDrops(slayer, tier, 9, input, input);

        String output = SystemLambda.tapSystemOut(() -> calculator.printResults(progress, mf, slayer));
        Assertions.assertEquals(expected, output);
    }
    private static Stream<Arguments> testCalculateChanceArgs() {
        return Stream.of(
                Arguments.of("You have a 100.0% chance of receiving Revenant Flesh\n", "Revenant Flesh", 6000, 20, 1, Slayers.Z),
                Arguments.of("You have a 23.451% chance of receiving Foul Flesh\n", "Foul Flesh", 1000, 100, 2, Slayers.Z),
                Arguments.of("You have a 0.351% chance of receiving Scythe Blade\n", "Scythe Blade", 300000, 200, 4, Slayers.Z),
                Arguments.of("You have a 3.876% chance of receiving Undead Catalyst\n", "Undead Catalyst", 4000, 80, 5, Slayers.Z),
                Arguments.of("You have a 100.0% chance of receiving Pestilence Rune\n", "Pestilence Rune", 9000, 200, 3, Slayers.Z),

                Arguments.of("You have a 100.0% chance of receiving Tarantula Web\n", "Tarantula Web", 12345, 12345, 1, Slayers.S),
                Arguments.of("You have a 19.349% chance of receiving Toxic Arrow Poison\n", "Toxic Arrow Poison", 600, 100, 2, Slayers.S),
                Arguments.of("You have a 0.235% chance of receiving Digested Mosquito\n", "Digested Mosquito", 410000, 90, 4, Slayers.S),
                Arguments.of("You have a 0.587% chance of receiving Spider Catalyst\n", "Spider Catalyst", 5700, 130, 5, Slayers.S),
                Arguments.of("You have a 100.0% chance of receiving Bite Rune\n", "Bite Rune", 7657, 222, 3, Slayers.S),

                Arguments.of("You have a 1.11% chance of receiving Couture Rune\n", "Couture Rune", 123456, 140, 4, Slayers.W),
                Arguments.of("You have a 2.007% chance of receiving Furball\n", "Furball", 7000, 70, 3, Slayers.W),
                Arguments.of("You have a 34.783% chance of receiving Hamster Wheel\n", "Hamster Wheel", 2500, 45, 2, Slayers.W),
                Arguments.of("You have a 100.0% chance of receiving Wolf Tooth\n", "Wolf Tooth", 300, 40, 1, Slayers.W),
                Arguments.of("You have a 0.134% chance of receiving Overflux Capacitor\n", "Overflux Capacitor", 123000, 190, 4, Slayers.W),

                Arguments.of("You have a 100.0% chance of receiving Null Sphere\n", "Null Sphere", 0, 0, 1, Slayers.E),
                Arguments.of("You have a 1.07% chance of receiving Summoning Eye\n", "Summoning Eye", 5500, 39, 2, Slayers.E),
                Arguments.of("You have a 100.0% chance of receiving Null Atom\n", "Null Atom", 60000, 45, 3, Slayers.E),
                Arguments.of("You have a 2.41% chance of receiving End Rune\n", "End Rune", 34400, 136, 4, Slayers.E),
                Arguments.of("You have a 0.099% chance of receiving Exceedingly Rare Ender Artifact Upgrade\n", "Exceedingly Rare Ender Artifact Upgrade", 1000000, 90, 4, Slayers.E)
        );
    }


}
