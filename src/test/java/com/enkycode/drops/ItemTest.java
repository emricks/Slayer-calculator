package com.enkycode.drops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemTest {
    @Test
    @DisplayName("Testing multiplyWeight returns correct value")
    public void testMultiplyWeight() {
        Item item = new Item("Example", new double[]{12, 74, 123, 96, 59}, "Main", 11111);
        item.multiplyWeight(1.05921, 3);
        Assertions.assertEquals(130.28283, item.getWeight(3), 0.000001);
        Assertions.assertEquals(59, item.getWeight(5));
    }
}
