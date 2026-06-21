package com.enkycode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class SlayersTest {
    @ParameterizedTest
    @MethodSource("testArgs")
    public void testFromEnumFormat(String expected, Slayers slayer) {
        Assertions.assertEquals(expected, slayer.fromEnumFormat());
    }
    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of("Z - Zombie Slayer", Slayers.Z),
                Arguments.of("S - Spider Slayer", Slayers.S),
                Arguments.of("W - Wolf Slayer", Slayers.W),
                Arguments.of("E - Enderman Slayer", Slayers.E),
                Arguments.of("V - Vampire Slayer", Slayers.V),
                Arguments.of("B - Blaze Slayer", Slayers.B)
        );
    }

    @ParameterizedTest
    @MethodSource("testArgsDrops")
    public void testNumDrops(int tier, int expected, Slayers slayer) {
        Assertions.assertEquals(expected, slayer.numDrops(tier));
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
                Arguments.of(4, 19, Slayers.E),
                Arguments.of(1, 1, Slayers.V),
                Arguments.of(2, 5, Slayers.V),
                Arguments.of(3, 6, Slayers.V),
                Arguments.of(4, 8, Slayers.V),
                Arguments.of(5, 9, Slayers.V)
        );
    }
}
