package com.enkycode.drops;

import com.enkycode.Slayers;
import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.util.stream.Stream;

public class SlayerDropsTest {
    @ParameterizedTest
    @MethodSource("testArgs")
    public void testChance(String expected, String input, int progress, int mf, int tier, Slayers slayer) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
        System.setIn(bais);

        Drops calculator = switch (slayer) {
            case Z -> new RevDrops(tier);
            case S -> new TaraDrops(tier);
            default -> Assertions.fail();
        };

        String output = SystemLambda.tapSystemOut(() -> {
            calculator.printResults(progress, mf, slayer);
        });
        Assertions.assertEquals(expected, output);
    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of("You have a 100.0% chance of receiving your chosen drop.\n", "Revenant Flesh", 6000, 20, 1, Slayers.Z),
                Arguments.of("You have a 23.448% chance of receiving your chosen drop.\n", "Foul Flesh", 1000, 100, 2, Slayers.Z),
                Arguments.of("You have a 0.265% chance of receiving your chosen drop.\n", "Scythe Blade", 300000, 200, 4, Slayers.Z),
                Arguments.of("You have a 3.733% chance of receiving your chosen drop.\n", "Undead Catalyst", 4000, 80, 5, Slayers.Z),
                Arguments.of("You have a 12.422% chance of receiving your chosen drop.\n", "Pestilence Rune", 9000, 200, 3, Slayers.Z),

                Arguments.of("You have a 100.0% chance of receiving your chosen drop.\n", "Tarantula Web", 12345, 12345, 1, Slayers.S),
                Arguments.of("You have a 19.304% chance of receiving your chosen drop.\n", "Toxic Arrow Poison", 600, 100, 2, Slayers.S),
                Arguments.of("You have a 0.212% chance of receiving your chosen drop.\n", "Digested Mosquito", 410000, 90, 4, Slayers.S),
                Arguments.of("You have a 0.416% chance of receiving your chosen drop.\n", "Spider Catalyst", 5700, 130, 5, Slayers.S),
                Arguments.of("You have a 5.367% chance of receiving your chosen drop.\n", "Bite Rune", 11111, 222, 3, Slayers.S)
        );
    }
}
