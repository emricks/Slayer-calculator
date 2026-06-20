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
}
