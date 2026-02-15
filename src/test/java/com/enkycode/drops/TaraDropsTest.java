package com.enkycode.drops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Stream;

public class TaraDropsTest {
    @ParameterizedTest
    @MethodSource("testArgs")
    public void testMakeDrops(String drop, String table, int weight, int idx, int tier) {
        System.out.println(drop);
        String d = "Tarantula Web";
        ByteArrayInputStream bais = new ByteArrayInputStream(d.getBytes());
        System.setIn(bais);
        Drops calc = new TaraDrops(tier);

        List<Item> items = calc.getItems();
        Assertions.assertTrue(items.get(idx).equals(new Item(drop, weight, table)));
    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of("Bane 6", "Main", 25, 5, 3),
                Arguments.of("Bite Rune", "Extra", 333, 3, 3),
                Arguments.of("Tarantula Catalyst", "Main", 30, 4, 3),
                Arguments.of("Primordial Eye", "Main", 2, 14, 5),
                Arguments.of("Shriveled Wasp", "Main", 20, 12, 5),
                Arguments.of("Tarantula Web", "Token", 10000, 0, 5)
        );
    }
}
