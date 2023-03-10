package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
    @Test
    public void test_cood_and_orient() {
        Coordinate c1 = new Coordinate("D3");
        Coordinate c2 = new Coordinate("D3");
        Coordinate c3 = new Coordinate("N6");
        Coordinate c4 = new Coordinate("N6");
        Placement p1 = new Placement(c1, 'H');
        Placement p2 = new Placement(c2, 'h');
        Placement p3 = new Placement(c3, 'V');
        Placement p4 = new Placement(c4, 'v');
        Placement p5 = new Placement(c4, 'x');
        assertEquals(c2, p1.getCoordinate());
        assertEquals('H', p1.getOrientation());
        assertEquals(c1, p2.getCoordinate());
        assertEquals('H', p2.getOrientation());
        assertEquals(c4, p3.getCoordinate());
        assertEquals('V', p3.getOrientation());
        assertEquals(c3, p4.getCoordinate());
        assertEquals('V', p4.getOrientation());
        assertEquals(c4, p5.getCoordinate());
        assertEquals('X', p5.getOrientation());
    }

    @Test
    public void test_string_constructor() {
        Placement p1 = new Placement("D2H");
        Placement p2 = new Placement("H4h");
        Placement p3 = new Placement("M6V");
        Placement p4 = new Placement("Q8v");
        Placement p5 = new Placement("Z9z");
        assertEquals(new Coordinate("D2"), p1.getCoordinate());
        assertEquals('H', p1.getOrientation());
        assertEquals(new Coordinate("H4"), p2.getCoordinate());
        assertEquals('H', p2.getOrientation());
        assertEquals(new Coordinate("M6"), p3.getCoordinate());
        assertEquals('V', p3.getOrientation());
        assertEquals(new Coordinate("Q8"), p4.getCoordinate());
        assertEquals('V', p4.getOrientation());
        assertEquals(new Coordinate("Z9"), p5.getCoordinate());
        assertEquals('Z', p5.getOrientation());
    }

    @Test
    public void test_string_constructor_invalid_cases() {
        assertThrows(IllegalArgumentException.class, () -> new Placement("D2Hh"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("D12H"));
        assertThrows(IllegalArgumentException.class, () -> new Placement(""));
        assertThrows(IllegalArgumentException.class, () -> new Placement("D2"));
    }

    @Test
    public void test_equals() {
        Placement p1 = new Placement(new Coordinate("D2"), 'h');
        Placement p2 = new Placement("D2H");
        Placement p3 = new Placement("D2v");
        Placement p4 = new Placement("Q8h");
        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertEquals(p2, p1);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(p3, p4);
        assertNotEquals(p1, "D2H");
        assertNotEquals(p1, null);
    }

    @Test
    public void test_hashCode() {
        Placement p1 = new Placement(new Coordinate("D2"), 'h');
        Placement p2 = new Placement("D2H");
        Placement p3 = new Placement("D2v");
        Placement p4 = new Placement("Q8h");
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p4.hashCode());
        assertNotEquals(p3.hashCode(), p4.hashCode());
    }

    @Test
    public void test_toString() {
        Placement p1 = new Placement(new Coordinate("D2"), 'h');
        Placement p2 = new Placement("D2H");
        Placement p3 = new Placement("D2v");
        Placement p4 = new Placement("Q8h");
        assertEquals("D2H", p1.toString());
        assertEquals("D2H", p2.toString());
        assertEquals("D2V", p3.toString());
        assertEquals("Q8H", p4.toString());
    }

    @Test
    public void test_rotateQuarterClockwise() {
        Placement p1 = new Placement("a0h");
        Placement p2 = new Placement("a0u");
        Placement p3 = new Placement("d7X");

        assertEquals('H', p1.getOrientation());
        p1 = p1.rotateQuarterClockwise();
        assertEquals('V', p1.getOrientation());
        p1 = p1.rotateQuarterClockwise();
        assertEquals('_', p1.getOrientation());
        p1 = p1.rotateQuarterClockwise();
        assertEquals('-', p1.getOrientation());
        p1 = p1.rotateQuarterClockwise();
        assertEquals('H', p1.getOrientation());

        assertEquals('U', p2.getOrientation());
        p2 = p2.rotateQuarterClockwise();
        assertEquals('R', p2.getOrientation());
        p2 = p2.rotateQuarterClockwise();
        assertEquals('D', p2.getOrientation());
        p2 = p2.rotateQuarterClockwise();
        assertEquals('L', p2.getOrientation());
        p2 = p2.rotateQuarterClockwise();
        assertEquals('U', p2.getOrientation());

        assertThrows(IllegalArgumentException.class, () -> p3.rotateQuarterClockwise());
    }
}
