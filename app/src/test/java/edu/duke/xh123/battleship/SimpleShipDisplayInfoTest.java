package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
    @Test
    void test_constructor_and_getInfo() {
        SimpleShipDisplayInfo<Character> char_info = new SimpleShipDisplayInfo<>('s', '*');
        assertEquals('s', char_info.getInfo(new Coordinate(1, 1), false));
        assertEquals('*', char_info.getInfo(new Coordinate(1, 1), true));
        SimpleShipDisplayInfo<Integer> int_info = new SimpleShipDisplayInfo<>(1, 9);
        assertEquals(1, int_info.getInfo(new Coordinate(1, 1), false));
        assertEquals(9, int_info.getInfo(new Coordinate(1, 1), true));
    }
}
