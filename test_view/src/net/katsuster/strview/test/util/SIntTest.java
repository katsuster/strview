package net.katsuster.strview.test.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.katsuster.strview.util.*;

public class SIntTest {
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
    public final void testSIntLongLongInt() {
        String msg1 = "SInt(long, long, int) failed.";
        String msg2 = "SInt(long, long, int) illegal arguments check failed.";
        SInt va = new SInt(1, 2, 3);

        assertEquals(msg1, 1, va.getBitsValue());
        assertEquals(msg1, 2, va.getRange().getStart());
        assertEquals(msg1, 3, va.getRange().getLength());
    }

    @Test
    public final void testSIntSInt() {
        String msg1 = "SInt(SInt) failed.";
        String msg2 = "SInt.clone() failed.";
        SInt va = new SInt(1, 2, 3);
        SInt vb = new SInt(va);

        assertEquals(msg1, va.getBitsValue(), vb.getBitsValue());
        assertEquals(msg1, va.getRange().getStart(), vb.getRange().getStart());
        assertEquals(msg1, va.getRange().getEnd(), vb.getRange().getEnd());

        vb.setBitsValue(10);
        vb.getRange().setStart(20);
        vb.getRange().setEnd(30);
        assertNotEquals(msg1, va.getBitsValue(), vb.getBitsValue());
        assertNotEquals(msg1, va.getRange().getStart(), vb.getRange().getStart());
        assertNotEquals(msg1, va.getRange().getEnd(), vb.getRange().getEnd());

        try {
            SInt vc = (SInt)va.clone();

            assertEquals(msg2, va.getBitsValue(), vc.getBitsValue());
            assertEquals(msg2, va.getRange().getStart(), vc.getRange().getStart());
            assertEquals(msg2, va.getRange().getEnd(), vc.getRange().getEnd());

            vc.setBitsValue(100);
            vc.getRange().setStart(200);
            vc.getRange().setEnd(300);
            assertNotEquals(msg2, va.getBitsValue(), vc.getBitsValue());
            assertNotEquals(msg2, va.getRange().getStart(), vc.getRange().getStart());
            assertNotEquals(msg2, va.getRange().getEnd(), vc.getRange().getEnd());
        } catch (Exception ex) {
            fail(msg2);
        }
    }

    @Test
    public final void testSIntEquals() {
        String msg1 = "SInt.equals(Object) failed.";
        SInt vz0_1 = new SInt(0L, 0, 0);
        SInt vz0_2 = new SInt(0L, 0, 0);
        SInt vp1_1 = new SInt(1L, 0, 0);
        SInt vp1_2 = new SInt(1L, 0, 0);
        SInt vp2_1 = new SInt(2L, 0, 0);
        SInt vp2_2 = new SInt(2L, 0, 0);
        SInt vm1_1 = new SInt(-1L, 0, 0);
        SInt vm1_2 = new SInt(-1L, 0, 0);
        SInt vm2_1 = new SInt(-2L, 0, 0);
        SInt vm2_2 = new SInt(-2L, 0, 0);
        SInt vh1_1 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh1_2 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh2_1 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh2_2 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh3_1 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh3_2 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh4_1 = new SInt(0x8000000000000001L, 0, 0);
        SInt vh4_2 = new SInt(0x8000000000000001L, 0, 0);

        assertEquals(msg1, true, vz0_1.equals(vz0_1));
        assertEquals(msg1, true, vp1_1.equals(vp1_1));
        assertEquals(msg1, true, vp2_1.equals(vp2_1));
        assertEquals(msg1, true, vm1_1.equals(vm1_1));
        assertEquals(msg1, true, vm2_1.equals(vm2_1));
        assertEquals(msg1, true, vh1_1.equals(vh1_1));
        assertEquals(msg1, true, vh2_1.equals(vh2_1));
        assertEquals(msg1, true, vh3_1.equals(vh3_1));
        assertEquals(msg1, true, vh4_1.equals(vh4_1));

        assertEquals(msg1, true, vz0_1.equals(vz0_2));
        assertEquals(msg1, true, vp1_1.equals(vp1_2));
        assertEquals(msg1, true, vp2_1.equals(vp2_2));
        assertEquals(msg1, true, vm1_1.equals(vm1_2));
        assertEquals(msg1, true, vm2_1.equals(vm2_2));
        assertEquals(msg1, true, vh1_1.equals(vh1_2));
        assertEquals(msg1, true, vh2_1.equals(vh2_2));
        assertEquals(msg1, true, vh3_1.equals(vh3_2));
        assertEquals(msg1, true, vh4_1.equals(vh4_2));

        assertEquals(msg1, false, vz0_1.equals(vh4_2));
        assertEquals(msg1, false, vp1_1.equals(vz0_2));
        assertEquals(msg1, false, vp2_1.equals(vp1_2));
        assertEquals(msg1, false, vm1_1.equals(vp2_2));
        assertEquals(msg1, false, vm2_1.equals(vm1_2));
        assertEquals(msg1, false, vh1_1.equals(vm2_2));
        assertEquals(msg1, false, vh2_1.equals(vh1_2));
        assertEquals(msg1, false, vh3_1.equals(vh2_2));
        assertEquals(msg1, false, vh4_1.equals(vh3_2));
    }

    @Test
    public final void testSIntCompareTo() {
        String msg1 = "SInt.compareTo(SInt) failed.";
        SInt vz0_1 = new SInt(0L, 0, 0);
        SInt vz0_2 = new SInt(0L, 0, 0);
        SInt vp1_1 = new SInt(1L, 0, 0);
        SInt vp1_2 = new SInt(1L, 0, 0);
        SInt vp2_1 = new SInt(2L, 0, 0);
        SInt vp2_2 = new SInt(2L, 0, 0);
        SInt vm1_1 = new SInt(-1L, 0, 0);
        SInt vm1_2 = new SInt(-1L, 0, 0);
        SInt vm2_1 = new SInt(-2L, 0, 0);
        SInt vm2_2 = new SInt(-2L, 0, 0);
        SInt vh1_1 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh1_2 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh2_1 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh2_2 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh3_1 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh3_2 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh4_1 = new SInt(0x8000000000000001L, 0, 0);
        SInt vh4_2 = new SInt(0x8000000000000001L, 0, 0);

        assertEquals(msg1, true, vz0_1.compareTo(vz0_1) == 0);
        assertEquals(msg1, true, vp1_1.compareTo(vp1_1) == 0);
        assertEquals(msg1, true, vp2_1.compareTo(vp2_1) == 0);
        assertEquals(msg1, true, vm1_1.compareTo(vm1_1) == 0);
        assertEquals(msg1, true, vm2_1.compareTo(vm2_1) == 0);
        assertEquals(msg1, true, vh1_1.compareTo(vh1_1) == 0);
        assertEquals(msg1, true, vh2_1.compareTo(vh2_1) == 0);
        assertEquals(msg1, true, vh3_1.compareTo(vh3_1) == 0);
        assertEquals(msg1, true, vh4_1.compareTo(vh4_1) == 0);

        assertEquals(msg1, true, vz0_1.compareTo(vz0_2) == 0);
        assertEquals(msg1, true, vp1_1.compareTo(vp1_2) == 0);
        assertEquals(msg1, true, vp2_1.compareTo(vp2_2) == 0);
        assertEquals(msg1, true, vm1_1.compareTo(vm1_2) == 0);
        assertEquals(msg1, true, vm2_1.compareTo(vm2_2) == 0);
        assertEquals(msg1, true, vh1_1.compareTo(vh1_2) == 0);
        assertEquals(msg1, true, vh2_1.compareTo(vh2_2) == 0);
        assertEquals(msg1, true, vh3_1.compareTo(vh3_2) == 0);
        assertEquals(msg1, true, vh4_1.compareTo(vh4_2) == 0);

        assertEquals(msg1, true, vz0_1.compareTo(vh4_2) > 0);
        assertEquals(msg1, true, vp1_1.compareTo(vz0_2) > 0);
        assertEquals(msg1, true, vp2_1.compareTo(vp1_2) > 0);
        assertEquals(msg1, true, vm1_1.compareTo(vp2_2) < 0);
        assertEquals(msg1, true, vm2_1.compareTo(vm1_2) < 0);
        assertEquals(msg1, true, vh1_1.compareTo(vm2_2) > 0);
        assertEquals(msg1, true, vh2_1.compareTo(vh1_2) > 0);
        assertEquals(msg1, true, vh3_1.compareTo(vh2_2) < 0);
        assertEquals(msg1, true, vh4_1.compareTo(vh3_2) > 0);

        assertEquals(msg1, true, vz0_1.compareTo(vp1_2) < 0);
        assertEquals(msg1, true, vp1_1.compareTo(vz0_2) > 0);
        assertEquals(msg1, true, vm1_1.compareTo(vz0_2) < 0);
        assertEquals(msg1, true, vz0_1.compareTo(vm1_2) > 0);
    }

    @Test
    public final void testSIntByteValue() {
        String msg1 = "SInt.byteValue() failed.";
        SInt vz0 = new SInt(0L, 0, 0);
        SInt vp1 = new SInt(1L, 0, 0);
        SInt vp2 = new SInt(2L, 0, 0);
        SInt vm1 = new SInt(-1L, 0, 0);
        SInt vm2 = new SInt(-2L, 0, 0);
        SInt vh1 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh2 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh3 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh4 = new SInt(0x8000000000000001L, 0, 0);

        assertEquals(msg1, (byte)0x00, vz0.byteValue());
        assertEquals(msg1, (byte)0x01, vp1.byteValue());
        assertEquals(msg1, (byte)0x02, vp2.byteValue());
        assertEquals(msg1, (byte)0xff, vm1.byteValue());
        assertEquals(msg1, (byte)0xfe, vm2.byteValue());
        assertEquals(msg1, (byte)0xfe, vh1.byteValue());
        assertEquals(msg1, (byte)0xff, vh2.byteValue());
        assertEquals(msg1, (byte)0x00, vh3.byteValue());
        assertEquals(msg1, (byte)0x01, vh4.byteValue());
    }

    @Test
    public final void testSIntShortValue() {
        String msg1 = "SInt.shortValue() failed.";
        SInt vz0 = new SInt(0L, 0, 0);
        SInt vp1 = new SInt(1L, 0, 0);
        SInt vp2 = new SInt(2L, 0, 0);
        SInt vm1 = new SInt(-1L, 0, 0);
        SInt vm2 = new SInt(-2L, 0, 0);
        SInt vh1 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh2 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh3 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh4 = new SInt(0x8000000000000001L, 0, 0);

        assertEquals(msg1, (short)0x0000, vz0.shortValue());
        assertEquals(msg1, (short)0x0001, vp1.shortValue());
        assertEquals(msg1, (short)0x0002, vp2.shortValue());
        assertEquals(msg1, (short)0xffff, vm1.shortValue());
        assertEquals(msg1, (short)0xfffe, vm2.shortValue());
        assertEquals(msg1, (short)0xfffe, vh1.shortValue());
        assertEquals(msg1, (short)0xffff, vh2.shortValue());
        assertEquals(msg1, (short)0x0000, vh3.shortValue());
        assertEquals(msg1, (short)0x0001, vh4.shortValue());
    }

    @Test
    public final void testSIntIntValue() {
        String msg1 = "SInt.intValue() failed.";
        SInt vz0 = new SInt(0L, 0, 0);
        SInt vp1 = new SInt(1L, 0, 0);
        SInt vp2 = new SInt(2L, 0, 0);
        SInt vm1 = new SInt(-1L, 0, 0);
        SInt vm2 = new SInt(-2L, 0, 0);
        SInt vh1 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh2 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh3 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh4 = new SInt(0x8000000000000001L, 0, 0);

        assertEquals(msg1, 0x00000000, vz0.intValue());
        assertEquals(msg1, 0x00000001, vp1.intValue());
        assertEquals(msg1, 0x00000002, vp2.intValue());
        assertEquals(msg1, 0xffffffff, vm1.intValue());
        assertEquals(msg1, 0xfffffffe, vm2.intValue());
        assertEquals(msg1, 0xfffffffe, vh1.intValue());
        assertEquals(msg1, 0xffffffff, vh2.intValue());
        assertEquals(msg1, 0x00000000, vh3.intValue());
        assertEquals(msg1, 0x00000001, vh4.intValue());
    }

    @Test
    public final void testSIntLongValue() {
        String msg1 = "SInt.longValue() failed.";
        SInt vz0 = new SInt(0L, 0, 0);
        SInt vp1 = new SInt(1L, 0, 0);
        SInt vp2 = new SInt(2L, 0, 0);
        SInt vm1 = new SInt(-1L, 0, 0);
        SInt vm2 = new SInt(-2L, 0, 0);
        SInt vh1 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh2 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh3 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh4 = new SInt(0x8000000000000001L, 0, 0);

        assertEquals(msg1, 0x0000000000000000L, vz0.longValue());
        assertEquals(msg1, 0x0000000000000001L, vp1.longValue());
        assertEquals(msg1, 0x0000000000000002L, vp2.longValue());
        assertEquals(msg1, 0xffffffffffffffffL, vm1.longValue());
        assertEquals(msg1, 0xfffffffffffffffeL, vm2.longValue());
        assertEquals(msg1, 0x7ffffffffffffffeL, vh1.longValue());
        assertEquals(msg1, 0x7fffffffffffffffL, vh2.longValue());
        assertEquals(msg1, 0x8000000000000000L, vh3.longValue());
        assertEquals(msg1, 0x8000000000000001L, vh4.longValue());
    }

    @Test
    public final void testSIntFloatValue() {
        String msg1 = "SInt.floatValue() failed.";
        SInt vz0 = new SInt(0L, 0, 0);
        SInt vp1 = new SInt(1L, 0, 0);
        SInt vp2 = new SInt(2L, 0, 0);
        SInt vm1 = new SInt(-1L, 0, 0);
        SInt vm2 = new SInt(-2L, 0, 0);
        SInt vh1 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh2 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh3 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh4 = new SInt(0x8000000000000001L, 0, 0);

        assertTrue(msg1,                    0.0F <= vz0.floatValue());
        assertTrue(msg1,                    1.0F <= vp1.floatValue());
        assertTrue(msg1,                    2.0F <= vp2.floatValue());
        assertTrue(msg1,                   -1.0F >= vm1.floatValue());
        assertTrue(msg1,                   -2.0F >= vm2.floatValue());
        assertTrue(msg1,  9223372036854775806.0F <= vh1.floatValue());
        assertTrue(msg1,  9223372036854775807.0F <= vh2.floatValue());
        assertTrue(msg1, -9223372036854775808.0F >= vh3.floatValue());
        assertTrue(msg1, -9223372036854775807.0F >= vh4.floatValue());
    }

    @Test
    public final void testSIntDoubleValue() {
        String msg1 = "SInt.doubleValue() failed.";
        SInt vz0 = new SInt(0L, 0, 0);
        SInt vp1 = new SInt(1L, 0, 0);
        SInt vp2 = new SInt(2L, 0, 0);
        SInt vm1 = new SInt(-1L, 0, 0);
        SInt vm2 = new SInt(-2L, 0, 0);
        SInt vh1 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh2 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh3 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh4 = new SInt(0x8000000000000001L, 0, 0);

        assertTrue(msg1,                    0.0D <= vz0.doubleValue());
        assertTrue(msg1,                    1.0D <= vp1.doubleValue());
        assertTrue(msg1,                    2.0D <= vp2.doubleValue());
        assertTrue(msg1,                   -1.0D >= vm1.doubleValue());
        assertTrue(msg1,                   -2.0D >= vm2.doubleValue());
        assertTrue(msg1,  9223372036854775806.0D <= vh1.doubleValue());
        assertTrue(msg1,  9223372036854775807.0D <= vh2.doubleValue());
        assertTrue(msg1, -9223372036854775808.0D >= vh3.doubleValue());
        assertTrue(msg1, -9223372036854775807.0D >= vh4.doubleValue());
    }

    @Test
    public final void testSIntToString() {
        String msg1 = "SInt.toString() failed.";
        SInt vz0 = new SInt(0L, 0, 0);
        SInt vp1 = new SInt(1L, 0, 0);
        SInt vp2 = new SInt(2L, 0, 0);
        SInt vm1 = new SInt(-1L, 0, 0);
        SInt vm2 = new SInt(-2L, 0, 0);
        SInt vh1 = new SInt(0x7ffffffffffffffeL, 0, 0);
        SInt vh2 = new SInt(0x7fffffffffffffffL, 0, 0);
        SInt vh3 = new SInt(0x8000000000000000L, 0, 0);
        SInt vh4 = new SInt(0x8000000000000001L, 0, 0);

        assertEquals(msg1,                    "0", vz0.toString());
        assertEquals(msg1,                    "1", vp1.toString());
        assertEquals(msg1,                    "2", vp2.toString());
        assertEquals(msg1,                   "-1", vm1.toString());
        assertEquals(msg1,                   "-2", vm2.toString());
        assertEquals(msg1,  "9223372036854775806", vh1.toString());
        assertEquals(msg1,  "9223372036854775807", vh2.toString());
        assertEquals(msg1, "-9223372036854775808", vh3.toString());
        assertEquals(msg1, "-9223372036854775807", vh4.toString());
    }
}
