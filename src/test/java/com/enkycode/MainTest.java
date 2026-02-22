package com.enkycode;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MainTest {
    @Test
    @DisplayName("Testing getSlayer() works correctly.")
    public void testGetSlayer() throws Exception {
        String output = SystemLambda.tapSystemOut(() -> Main.getSlayer(true, "iajwfj0w001-"));
        Assertions.assertEquals("Invalid slayer. Please try again.", output.split("\n")[7]);
        Assertions.assertEquals(Slayers.S, Main.getSlayer(true, "S"));
        Assertions.assertEquals(Slayers.B, Main.getSlayer(false, "B"));
    }

    @ParameterizedTest
    @MethodSource("testArgs")
    public void testGetTier(int input, Slayers slayer, boolean works) throws Exception {
        if (!works) {
            String output = SystemLambda.tapSystemOut(() -> Main.getTier(slayer, true, input));
            Assertions.assertEquals("Invalid tier for this slayer type. Please try again.", output.split("\n")[1]);
        } else {
            Assertions.assertEquals(input, Main.getTier(slayer, true, input));
        }
    }
    public static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(100, Slayers.Z, false),
                Arguments.of(5, Slayers.E, false),
                Arguments.of(2, Slayers.V, true),
                Arguments.of(5, Slayers.S, true)
        );
    }
}
