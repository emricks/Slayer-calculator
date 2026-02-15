package com.enkycode.drops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemTest {
    @Test
    @DisplayName("Testing multiplyWeight returns correct value")
    public void testMultiplyWeight() {
        Item item = new Item("Example", 123, "Main");
        item.multiplyWeight(1.05921);
        Assertions.assertEquals(130.28283, item.getWeight(), 0.000001);
    }
}
