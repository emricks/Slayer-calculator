package com.enkycode.drops;

import com.enkycode.Slayers;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Stream;

public class SlayerDropsTest {
    @ParameterizedTest
    @MethodSource("testArgsChance")
    public void testCalculateChance(String expected, String input, int progress, int mf, int tier, Slayers slayer) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
        System.setIn(bais);

        Drops calculator = new SlayerDrops(slayer, tier);

        String output = SystemLambda.tapSystemOut(() -> {
            calculator.printResults(progress, mf, slayer);
        });
        Assertions.assertEquals(expected, output);
    }

    private static Stream<Arguments> testArgsChance() {
        return Stream.of(
                Arguments.of("You have a 100.0% chance of receiving Revenant Flesh\n", "Revenant Flesh", 6000, 20, 1, Slayers.Z),
                Arguments.of("You have a 23.451% chance of receiving Foul Flesh\n", "Foul Flesh", 1000, 100, 2, Slayers.Z),
                Arguments.of("You have a 0.351% chance of receiving Scythe Blade\n", "Scythe Blade", 300000, 200, 4, Slayers.Z),
                Arguments.of("You have a 3.876% chance of receiving Undead Catalyst\n", "Undead Catalyst", 4000, 80, 5, Slayers.Z),
                Arguments.of("You have a 6.841% chance of receiving Pestilence Rune\n", "Pestilence Rune", 9000, 200, 3, Slayers.Z),

                Arguments.of("You have a 100.0% chance of receiving Tarantula Web\n", "Tarantula Web", 12345, 12345, 1, Slayers.S),
                Arguments.of("You have a 19.349% chance of receiving Toxic Arrow Poison\n", "Toxic Arrow Poison", 600, 100, 2, Slayers.S),
                Arguments.of("You have a 0.235% chance of receiving Digested Mosquito\n", "Digested Mosquito", 410000, 90, 4, Slayers.S),
                Arguments.of("You have a 0.587% chance of receiving Spider Catalyst\n", "Spider Catalyst", 5700, 130, 5, Slayers.S),
                Arguments.of("You have a 7.215% chance of receiving Bite Rune\n", "Bite Rune", 11111, 222, 3, Slayers.S),

                Arguments.of("You have a 1.11% chance of receiving Couture Rune\n", "Couture Rune", 123456, 140, 4, Slayers.W),
                Arguments.of("You have a 2.007% chance of receiving Furball\n", "Furball", 7000, 70, 3, Slayers.W),
                Arguments.of("You have a 34.783% chance of receiving Hamster Wheel\n", "Hamster Wheel", 2500, 45, 2, Slayers.W),
                Arguments.of("You have a 100.0% chance of receiving Wolf Tooth\n", "Wolf Tooth", 300, 40, 1, Slayers.W),
                Arguments.of("You have a 0.134% chance of receiving Overflux Capacitor\n", "Overflux Capacitor", 123000, 190, 4, Slayers.W),

                Arguments.of("You have a 100.0% chance of receiving Null Sphere\n", "Null Sphere", 0, 0, 1, Slayers.E),
                Arguments.of("You have a 1.07% chance of receiving Summoning Eye\n", "Summoning Eye", 5500, 39, 2, Slayers.E),
                Arguments.of("You have a 10.051% chance of receiving Null Atom\n", "Null Atom", 60000, 45, 3, Slayers.E),
                Arguments.of("You have a 2.41% chance of receiving End Rune\n", "End Rune", 34400, 136, 4, Slayers.E),
                Arguments.of("You have a 0.099% chance of receiving Exceedingly Rare Ender Artifact Upgrade\n", "Exceedingly Rare Ender Artifact Upgrade", 1000000, 90, 4, Slayers.E)
        );
    }

    @ParameterizedTest
    @MethodSource("testArgsDrops")
    public void testMakeDrops(int tier, int numExpected, Slayers slayer) {
        String input = switch (slayer) {
            case Z -> "Revenant Flesh";
            case S -> "Tarantula Web";
            case W -> "Wolf Tooth";
            case E -> "Null Sphere";
            case V -> "Coven Seal";
            case B -> "Derelict Ashe";
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
        System.setIn(bais);
        Drops calculator = new SlayerDrops(slayer, tier);
        List<Item> itemList = calculator.getItems();
        Assertions.assertEquals(numExpected, itemList.size());
    }
    private static Stream<Arguments> testArgsDrops() {
        return Stream.of(
                Arguments.of(1, 1, Slayers.Z),
                Arguments.of(2, 5, Slayers.Z),
                Arguments.of(3, 8, Slayers.Z),
                Arguments.of(4, 11, Slayers.Z),
                Arguments.of(5, 15, Slayers.Z),
                Arguments.of(1, 1, Slayers.S),
                Arguments.of(2, 4, Slayers.S),
                Arguments.of(3, 8, Slayers.S),
                Arguments.of(4, 11, Slayers.S),
                Arguments.of(5, 15, Slayers.S),
                Arguments.of(1, 1, Slayers.W),
                Arguments.of(2, 3, Slayers.W),
                Arguments.of(3, 6, Slayers.W),
                Arguments.of(4, 9, Slayers.W),
                Arguments.of(1, 1, Slayers.E),
                Arguments.of(2, 3, Slayers.E),
                Arguments.of(3, 8, Slayers.E),
                Arguments.of(4, 19, Slayers.E)
        );
    }
}
