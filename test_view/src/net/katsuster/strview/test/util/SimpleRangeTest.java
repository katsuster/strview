package net.katsuster.strview.test.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.katsuster.strview.util.*;

public class SimpleRangeTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSimpleRange() throws Exception {

    }

    @Test
    public void testSimpleRangeLongLong() throws Exception {
        String msg1 = "SimpleRange(long, long) failed.";
        String msg2 = "SimpleRange(long, long) illegal arguments check failed.";
        SimpleRange va = new SimpleRange(2, 3);
        SimpleRange vb = new SimpleRange(0x500000000L, 0x10);
        SimpleRange vc = new SimpleRange(1000, 0);
        SimpleRange vu = new SimpleRange(100, Range.LENGTH_UNKNOWN);

        assertEquals(msg1, 2, va.getStart());
        assertEquals(msg1, 5, va.getEnd());
        assertEquals(msg1, 0x500000000L, vb.getStart());
        assertEquals(msg1, 0x500000010L, vb.getEnd());
        assertEquals(msg1, 1000, vc.getStart());
        assertEquals(msg1, 1000, vc.getEnd());

        assertEquals(msg1, 100, vu.getStart());
        assertEquals(msg1, Range.LENGTH_UNKNOWN, vu.getEnd());
        assertEquals(msg1, Range.LENGTH_UNKNOWN, vu.getLength());

        try {
            new SimpleRange(Range.LENGTH_UNKNOWN, 10);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            new SimpleRange(100, -300);
            fail(msg2);
        } catch (NegativeArraySizeException ex) {
            //OK
        }
    }

    @Test
    public void testSimpleRangeSimpleRange() throws Exception {
        String msg1 = "SimpleRange(SimpleRange) failed.";
        SimpleRange va = new SimpleRange(2, 3);
        SimpleRange va_a = new SimpleRange(va);
        SimpleRange va_b = new SimpleRange(va);

        va_b.setStart(100);
        va_b.setLength(200);

        assertTrue(va != va_a);
        assertTrue(va != va_b);
        assertTrue(va_a != va_b);

        assertEquals(msg1, 2, va.getStart());
        assertEquals(msg1, 5, va.getEnd());
        assertEquals(msg1, 2, va_a.getStart());
        assertEquals(msg1, 5, va_a.getEnd());
        assertEquals(msg1, 100, va_b.getStart());
        assertEquals(msg1, 300, va_b.getEnd());
    }

    @Test
    public void testClone() throws Exception {
        String msg1 = "clone() failed.";
        SimpleRange va = new SimpleRange(20, 30);
        SimpleRange va_a = (SimpleRange)va.clone();
        SimpleRange va_b = (SimpleRange)va.clone();

        va_b.setStart(100);
        va_b.setLength(200);

        assertTrue(va != va_a);
        assertTrue(va != va_b);
        assertTrue(va_a != va_b);

        assertEquals(msg1, 20, va.getStart());
        assertEquals(msg1, 50, va.getEnd());
        assertEquals(msg1, 20, va_a.getStart());
        assertEquals(msg1, 50, va_a.getEnd());
        assertEquals(msg1, 100, va_b.getStart());
        assertEquals(msg1, 300, va_b.getEnd());
    }

    @Test
    public void testEquals() throws Exception {
        String msg1 = "equals(SimpleRange) failed.";
        SimpleRange va = new SimpleRange(200, 3);
        SimpleRange va_a = new SimpleRange(va);
        SimpleRange va_b = new SimpleRange(va);

        va_b.setStart(100);
        va_b.setLength(200);

        assertEquals(msg1, va, va);
        assertEquals(msg1, va, va_a);
        assertNotEquals(msg1, va, va_b);
        assertNotEquals(msg1, va_a, va_b);
    }

    @Test
    public void testHashCode() throws Exception {
        String msg1 = "hashCode() failed.";
        SimpleRange va = new SimpleRange(2, 300);
        SimpleRange va_a = new SimpleRange(va);
        SimpleRange va_b = new SimpleRange(va);

        va_b.setStart(100);
        va_b.setLength(200);

        assertEquals(msg1, va.hashCode(), va.hashCode());
        assertEquals(msg1, va.hashCode(), va_a.hashCode());
        assertNotEquals(msg1, va.hashCode(), va_b.hashCode());
        assertNotEquals(msg1, va_a.hashCode(), va_b.hashCode());
    }

    @Test
    public void testGetStart() throws Exception {
        String msg1 = "getStart() failed.";
        SimpleRange va = new SimpleRange(2000, 30000);

        assertEquals(msg1, 2000, va.getStart());
    }

    @Test
    public void testSetStartLong() throws Exception {
        String msg1 = "setStart(long) failed.";
        String msg2 = "setStart(long) illegal arguments check failed.";
        SimpleRange va = new SimpleRange(10, 100);

        va.setStart(0);
        assertEquals(msg1, 0, va.getStart());
        assertEquals(msg1, 100, va.getLength());

        va.setStart(50);
        assertEquals(msg1, 50, va.getStart());
        assertEquals(msg1, 100, va.getLength());

        va.setStart(0x100000000L);
        assertEquals(msg1, 0x100000000L, va.getStart());
        assertEquals(msg1, 100, va.getLength());

        try {
            va.setStart(Range.LENGTH_UNKNOWN);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            va.setStart(-1000);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testGetEnd() throws Exception {
        String msg1 = "getEnd() failed.";
        SimpleRange va = new SimpleRange(2000, 3000);
        SimpleRange vb = new SimpleRange(2000, Range.LENGTH_UNKNOWN);

        assertEquals(msg1, 5000, va.getEnd());
        assertEquals(msg1, Range.LENGTH_UNKNOWN, vb.getEnd());
    }

    @Test
    public void testSetEnd() throws Exception {
        String msg1 = "setEnd(long) failed.";
        String msg2 = "setEnd(long) illegal arguments check failed.";
        SimpleRange va = new SimpleRange(100, 100);

        va.setEnd(150);
        assertEquals(msg1, 100, va.getStart());
        assertEquals(msg1, 50, va.getLength());

        va.setEnd(100);
        assertEquals(msg1, 100, va.getStart());
        assertEquals(msg1, 0, va.getLength());

        try {
            va.setEnd(50);
            fail(msg2);
        } catch (NegativeArraySizeException ex) {
            //OK
        }

        try {
            va.setEnd(Range.LENGTH_UNKNOWN);
            fail(msg2);
        } catch (NegativeArraySizeException ex) {
            //OK
        }

        try {
            va.setEnd(-1000);
            fail(msg2);
        } catch (NegativeArraySizeException ex) {
            //OK
        }
    }

    @Test
    public void testGetLength() throws Exception {
        String msg1 = "getLength() failed.";
        SimpleRange va = new SimpleRange(2000, 300);
        SimpleRange vb = new SimpleRange(2000, Range.LENGTH_UNKNOWN);

        assertEquals(msg1, 300, va.getLength());
        assertEquals(msg1, Range.LENGTH_UNKNOWN, vb.getLength());
    }

    @Test
    public void testSetLength() throws Exception {
        String msg1 = "setLength(long) failed.";
        String msg2 = "setLength(long) illegal arguments check failed.";
        SimpleRange va = new SimpleRange(100, 1000);

        va.setLength(150);
        assertEquals(msg1, 100, va.getStart());
        assertEquals(msg1, 250, va.getEnd());

        va.setLength(0);
        assertEquals(msg1, 100, va.getStart());
        assertEquals(msg1, 100, va.getEnd());

        va.setLength(Range.LENGTH_UNKNOWN);
        assertEquals(msg1, 100, va.getStart());
        assertEquals(msg1, Range.LENGTH_UNKNOWN, va.getEnd());

        try {
            va.setLength(-1000);
            fail(msg2);
        } catch (NegativeArraySizeException ex) {
            //OK
        }
    }

    @Test
    public void testIsHitLong() throws Exception {
        String msg1 = "isHit(long) failed.";
        SimpleRange va = new SimpleRange(200, 300);

        assertFalse(msg1, va.isHit(-1));
        assertTrue(msg1, va.isHit(200));
        assertTrue(msg1, va.isHit(201));
        assertTrue(msg1, va.isHit(300));
        assertTrue(msg1, va.isHit(499));
        assertFalse(msg1, va.isHit(500));
        assertFalse(msg1, va.isHit(501));
        assertFalse(msg1, va.isHit(600));
    }
}