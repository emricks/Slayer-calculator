package com.enkycode.drops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ItemTest {
    @ParameterizedTest
    @MethodSource("testMultiplyWeightArgs")
    public void testMultiplyWeight(double factor, int tier, double expected) {
        Item item = new Item("Example", new double[]{12, 74, 123, 96, 59}, "Main", 11111, 4);
        item.multiplyWeight(factor, tier);
        Assertions.assertEquals(expected, item.getWeight(tier), 0.000001);
        if (tier == 5) {
            Assertions.assertEquals(123, item.getWeight(3));
        } else {
            Assertions.assertEquals(59, item.getWeight(5));
        }
    }
    private static Stream<Arguments> testMultiplyWeightArgs() {
        return Stream.of(
                Arguments.of(1.034, 2, 76.516),
                Arguments.of(2.3056, 4, 221.3376),
                Arguments.of(0.798, 5, 47.082)
        );
    }

    @ParameterizedTest
    @MethodSource("testEqualsArgs")
    public void testEquals(Item compare, boolean expected) {
        Item item = new Item("Example", new double[]{12, 74, 123, 96, 59}, "Main", 11111, 4);
        Assertions.assertEquals(expected, compare.equals(item));
    }
    private static Stream<Arguments> testEqualsArgs() {
        Item itemSame = new Item("Example", new double[]{12, 74, 123, 96, 59}, "Main", 11111, 4);
        Item itemName = new Item("Java", new double[]{12, 74, 123, 96, 59}, "Main", 11111, 4);
        Item itemWeights = new Item("Example", new double[]{0, 74, 321, 96, 59}, "Main", 11111, 4);
        Item itemTable = new Item("Example", new double[]{12, 74, 123, 96, 59}, "Extra", 11111, 4);
        Item itemRNG = new Item("Example", new double[]{12, 74, 123, 96, 59}, "Main", 22222, 4);
        Item itemLevel = new Item("Example", new double[]{12, 74, 123, 96, 59}, "Main", 11111, 2);
        return Stream.of(
                Arguments.of(itemSame, true),
                Arguments.of(itemName, false),
                Arguments.of(itemWeights, false),
                Arguments.of(itemTable, false),
                Arguments.of(itemRNG, false),
                Arguments.of(itemLevel, false)
        );
    }

    @Test
    public void testToString() {
        Item item = new Item("Example", new double[]{12, 74, 123, 96, 59}, "Main", 11111, 4);
        Assertions.assertEquals("\nName: Example, Weights: [12.0, 74.0, 123.0, 96.0, 59.0], Table: Main, RNGRequired: 11111, Level: 4", item.toString());
    }
}
