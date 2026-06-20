package com.enkycode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MainTest {
    @ParameterizedTest
    @MethodSource("testSlayerArgs")
    public void testGetSlayer(String input, Slayers expected) {
        Slayers actual = Main.getSlayerTesting(input);
        Assertions.assertEquals(expected, actual);
    }
    private static Stream<Arguments> testSlayerArgs() {
        return Stream.of(
                Arguments.of("pa9w8hf", null), // Random garbage
                Arguments.of("H", null), // Nonexistent slayer
                Arguments.of("s", Slayers.S), // Lowercase
                Arguments.of("blaze", Slayers.B), // Full word
                Arguments.of("Z", Slayers.Z) // Normal
        );
    }

    @ParameterizedTest
    @MethodSource("testTierArgs")
    public void testGetTier(String input, Slayers slayer, int expected) {
        int tier = Main.getTierTesting(slayer, input);
        Assertions.assertEquals(expected, tier);
    }
    private static Stream<Arguments> testTierArgs() {
        return Stream.of(
                Arguments.of("100", Slayers.Z, -1), // Too big
                Arguments.of("-2", Slayers.E, -1), // Too small
                Arguments.of("3.7", Slayers.V, -1), // Decimal
                Arguments.of("5", Slayers.W, -1), // Too big for some
                Arguments.of("1", Slayers.B, 1), // Correct for all
                Arguments.of("5", Slayers.S, 5) // Correct for some
        );
    }
}
