package net.katsuster.strview.test.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;

public class UIntTest {
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
    public final void testUIntLongLongInt() {
        String msg1 = "UInt(long, long, int) failed.";
        String msg2 = "UInt(long, long, int) illegal arguments check failed.";
        LargeBitList la = new MemoryBitList(64);
        UInt va = new UInt(la, 2, 3);

        assertEquals(msg1, 0, va.getRaw());
        assertEquals(msg1, 2, va.getRange().getStart());
        assertEquals(msg1, 3, va.getRange().getLength());
    }

    @Test
    public final void testUIntUInt() {
        String msg1 = "UInt(UInt) failed.";
        String msg2 = "UInt.clone() failed.";
        LargeBitList la = new MemoryBitList(64);
        UInt va = new UInt(la, 2, 3);
        UInt vb = new UInt(va);

        assertEquals(msg1, va.getRaw(), vb.getRaw());
        assertEquals(msg1, va.getRange().getStart(), vb.getRange().getStart());
        assertEquals(msg1, va.getRange().getEnd(), vb.getRange().getEnd());

        vb.setRaw(10);
        vb.getRange().setStart(20);
        vb.getRange().setEnd(30);
        assertNotEquals(msg1, va.getRaw(), vb.getRaw());
        assertNotEquals(msg1, va.getRange().getStart(), vb.getRange().getStart());
        assertNotEquals(msg1, va.getRange().getEnd(), vb.getRange().getEnd());

        try {
            UInt vc = (UInt)va.clone();

            assertEquals(msg2, va.getRaw(), vc.getRaw());
            assertEquals(msg2, va.getRange().getStart(), vc.getRange().getStart());
            assertEquals(msg2, va.getRange().getEnd(), vc.getRange().getEnd());

            vc.setRaw(100);
            vc.getRange().setStart(4);
            vc.getRange().setEnd(10);
            assertNotEquals(msg2, va.getRaw(), vc.getRaw());
            assertNotEquals(msg2, va.getRange().getStart(), vc.getRange().getStart());
            assertNotEquals(msg2, va.getRange().getEnd(), vc.getRange().getEnd());
        } catch (Exception ex) {
            fail(msg2);
        }
    }

    @Test
    public final void testUIntEquals() {
        String msg1 = "UInt.equals(Object) failed.";
        UInt vz0_1 = new UInt(0L, 64);
        UInt vz0_2 = new UInt(0L, 64);
        UInt vp1_1 = new UInt(1L, 64);
        UInt vp1_2 = new UInt(1L, 64);
        UInt vp2_1 = new UInt(2L, 64);
        UInt vp2_2 = new UInt(2L, 64);
        UInt vm1_1 = new UInt(-1L, 64);
        UInt vm1_2 = new UInt(-1L, 64);
        UInt vm2_1 = new UInt(-2L, 64);
        UInt vm2_2 = new UInt(-2L, 64);
        UInt vh1_1 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh1_2 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh2_1 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh2_2 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh3_1 = new UInt(0x8000000000000000L, 64);
        UInt vh3_2 = new UInt(0x8000000000000000L, 64);
        UInt vh4_1 = new UInt(0x8000000000000001L, 64);
        UInt vh4_2 = new UInt(0x8000000000000001L, 64);
        UInt vsp1_1 = new UInt(1L, 64);
        UInt vsp1_2 = new UInt(1L, 32);
        UInt vsp2_1 = new UInt(2L, 64);
        UInt vsp2_2 = new UInt(2L, 32);
        UInt vsm1_1 = new UInt(-1L, 64);
        UInt vsm1_2 = new UInt(-1L, 32);
        UInt vsm2_1 = new UInt(-2L, 64);
        UInt vsm2_2 = new UInt(-2L, 32);

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

        assertEquals(msg1, true, vsp1_1.equals(vsp1_2));
        assertEquals(msg1, true, vsp2_1.equals(vsp2_2));
        assertEquals(msg1, false, vsm1_1.equals(vsm1_2));
        assertEquals(msg1, false, vsm2_1.equals(vsm2_2));
    }

    @Test
    public final void testUIntCompareTo() {
        String msg1 = "UInt.compareTo(UInt) failed.";
        UInt vz0_1 = new UInt(0L, 64);
        UInt vz0_2 = new UInt(0L, 64);
        UInt vp1_1 = new UInt(1L, 64);
        UInt vp1_2 = new UInt(1L, 64);
        UInt vp2_1 = new UInt(2L, 64);
        UInt vp2_2 = new UInt(2L, 64);
        UInt vm1_1 = new UInt(-1L, 64);
        UInt vm1_2 = new UInt(-1L, 64);
        UInt vm2_1 = new UInt(-2L, 64);
        UInt vm2_2 = new UInt(-2L, 64);
        UInt vh1_1 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh1_2 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh2_1 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh2_2 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh3_1 = new UInt(0x8000000000000000L, 64);
        UInt vh3_2 = new UInt(0x8000000000000000L, 64);
        UInt vh4_1 = new UInt(0x8000000000000001L, 64);
        UInt vh4_2 = new UInt(0x8000000000000001L, 64);
        UInt vsp1_1 = new UInt(1L, 64);
        UInt vsp1_2 = new UInt(1L, 32);
        UInt vsp2_1 = new UInt(2L, 64);
        UInt vsp2_2 = new UInt(2L, 32);
        UInt vsm1_1 = new UInt(-1L, 64);
        UInt vsm1_2 = new UInt(-1L, 32);
        UInt vsm2_1 = new UInt(-2L, 64);
        UInt vsm2_2 = new UInt(-2L, 32);

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

        assertEquals(msg1, true, vz0_1.compareTo(vh4_2) < 0);
        assertEquals(msg1, true, vp1_1.compareTo(vz0_2) > 0);
        assertEquals(msg1, true, vp2_1.compareTo(vp1_2) > 0);
        assertEquals(msg1, true, vm1_1.compareTo(vp2_2) > 0);
        assertEquals(msg1, true, vm2_1.compareTo(vm1_2) < 0);
        assertEquals(msg1, true, vh1_1.compareTo(vm2_2) < 0);
        assertEquals(msg1, true, vh2_1.compareTo(vh1_2) > 0);
        assertEquals(msg1, true, vh3_1.compareTo(vh2_2) > 0);
        assertEquals(msg1, true, vh4_1.compareTo(vh3_2) > 0);

        assertEquals(msg1, true, vz0_1.compareTo(vp1_2) < 0);
        assertEquals(msg1, true, vp1_1.compareTo(vz0_2) > 0);
        assertEquals(msg1, true, vm1_1.compareTo(vz0_2) > 0);
        assertEquals(msg1, true, vz0_1.compareTo(vm1_2) < 0);

        assertEquals(msg1, true, vsp1_1.compareTo(vsp1_2) == 0);
        assertEquals(msg1, true, vsp2_1.compareTo(vsp2_2) == 0);
        assertEquals(msg1, true, vsm1_1.compareTo(vsm1_2) > 0);
        assertEquals(msg1, true, vsm2_1.compareTo(vsm2_2) > 0);
    }

    @Test
    public final void testUIntByteValue() {
        String msg1 = "UInt.byteValue() failed.";
        UInt vz0 = new UInt(0L, 64);
        UInt vp1 = new UInt(1L, 64);
        UInt vp2 = new UInt(2L, 64);
        UInt vm1 = new UInt(-1L, 64);
        UInt vm2 = new UInt(-2L, 64);
        UInt vh1 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh2 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh3 = new UInt(0x8000000000000000L, 64);
        UInt vh4 = new UInt(0x8000000000000001L, 64);
        UInt vsp1 = new UInt(1L, 32);
        UInt vsp2 = new UInt(2L, 32);
        UInt vsm1 = new UInt(-1L, 32);
        UInt vsm2 = new UInt(-2L, 32);

        assertEquals(msg1, (byte)0x00, vz0.byteValue());
        assertEquals(msg1, (byte)0x01, vp1.byteValue());
        assertEquals(msg1, (byte)0x02, vp2.byteValue());
        assertEquals(msg1, (byte)0xff, vm1.byteValue());
        assertEquals(msg1, (byte)0xfe, vm2.byteValue());
        assertEquals(msg1, (byte)0xfe, vh1.byteValue());
        assertEquals(msg1, (byte)0xff, vh2.byteValue());
        assertEquals(msg1, (byte)0x00, vh3.byteValue());
        assertEquals(msg1, (byte)0x01, vh4.byteValue());

        assertEquals(msg1, (byte)0x01, vsp1.byteValue());
        assertEquals(msg1, (byte)0x02, vsp2.byteValue());
        assertEquals(msg1, (byte)0xff, vsm1.byteValue());
        assertEquals(msg1, (byte)0xfe, vsm2.byteValue());
    }

    @Test
    public final void testUIntShortValue() {
        String msg1 = "UInt.shortValue() failed.";
        UInt vz0 = new UInt(0L, 64);
        UInt vp1 = new UInt(1L, 64);
        UInt vp2 = new UInt(2L, 64);
        UInt vm1 = new UInt(-1L, 64);
        UInt vm2 = new UInt(-2L, 64);
        UInt vh1 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh2 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh3 = new UInt(0x8000000000000000L, 64);
        UInt vh4 = new UInt(0x8000000000000001L, 64);
        UInt vsp1 = new UInt(1L, 32);
        UInt vsp2 = new UInt(2L, 32);
        UInt vsm1 = new UInt(-1L, 32);
        UInt vsm2 = new UInt(-2L, 32);

        assertEquals(msg1, (short)0x0000, vz0.shortValue());
        assertEquals(msg1, (short)0x0001, vp1.shortValue());
        assertEquals(msg1, (short)0x0002, vp2.shortValue());
        assertEquals(msg1, (short)0xffff, vm1.shortValue());
        assertEquals(msg1, (short)0xfffe, vm2.shortValue());
        assertEquals(msg1, (short)0xfffe, vh1.shortValue());
        assertEquals(msg1, (short)0xffff, vh2.shortValue());
        assertEquals(msg1, (short)0x0000, vh3.shortValue());
        assertEquals(msg1, (short)0x0001, vh4.shortValue());

        assertEquals(msg1, (short)0x0001, vsp1.shortValue());
        assertEquals(msg1, (short)0x0002, vsp2.shortValue());
        assertEquals(msg1, (short)0xffff, vsm1.shortValue());
        assertEquals(msg1, (short)0xfffe, vsm2.shortValue());
    }

    @Test
    public final void testUIntIntValue() {
        String msg1 = "UInt.intValue() failed.";
        UInt vz0 = new UInt(0L, 64);
        UInt vp1 = new UInt(1L, 64);
        UInt vp2 = new UInt(2L, 64);
        UInt vm1 = new UInt(-1L, 64);
        UInt vm2 = new UInt(-2L, 64);
        UInt vh1 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh2 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh3 = new UInt(0x8000000000000000L, 64);
        UInt vh4 = new UInt(0x8000000000000001L, 64);
        UInt vsp1 = new UInt(1L, 32);
        UInt vsp2 = new UInt(2L, 32);
        UInt vsm1 = new UInt(-1L, 32);
        UInt vsm2 = new UInt(-2L, 32);

        assertEquals(msg1, 0x00000000, vz0.intValue());
        assertEquals(msg1, 0x00000001, vp1.intValue());
        assertEquals(msg1, 0x00000002, vp2.intValue());
        assertEquals(msg1, 0xffffffff, vm1.intValue());
        assertEquals(msg1, 0xfffffffe, vm2.intValue());
        assertEquals(msg1, 0xfffffffe, vh1.intValue());
        assertEquals(msg1, 0xffffffff, vh2.intValue());
        assertEquals(msg1, 0x00000000, vh3.intValue());
        assertEquals(msg1, 0x00000001, vh4.intValue());

        assertEquals(msg1, 0x00000001, vsp1.intValue());
        assertEquals(msg1, 0x00000002, vsp2.intValue());
        assertEquals(msg1, 0xffffffff, vsm1.intValue());
        assertEquals(msg1, 0xfffffffe, vsm2.intValue());
    }

    @Test
    public final void testUIntLongValue() {
        String msg1 = "UInt.longValue() failed.";
        UInt vz0 = new UInt(0L, 64);
        UInt vp1 = new UInt(1L, 64);
        UInt vp2 = new UInt(2L, 64);
        UInt vm1 = new UInt(-1L, 64);
        UInt vm2 = new UInt(-2L, 64);
        UInt vh1 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh2 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh3 = new UInt(0x8000000000000000L, 64);
        UInt vh4 = new UInt(0x8000000000000001L, 64);
        UInt vsp1 = new UInt(1L, 32);
        UInt vsp2 = new UInt(2L, 32);
        UInt vsm1 = new UInt(-1L, 32);
        UInt vsm2 = new UInt(-2L, 32);

        assertEquals(msg1, 0x0000000000000000L, vz0.longValue());
        assertEquals(msg1, 0x0000000000000001L, vp1.longValue());
        assertEquals(msg1, 0x0000000000000002L, vp2.longValue());
        assertEquals(msg1, 0xffffffffffffffffL, vm1.longValue());
        assertEquals(msg1, 0xfffffffffffffffeL, vm2.longValue());
        assertEquals(msg1, 0x7ffffffffffffffeL, vh1.longValue());
        assertEquals(msg1, 0x7fffffffffffffffL, vh2.longValue());
        assertEquals(msg1, 0x8000000000000000L, vh3.longValue());
        assertEquals(msg1, 0x8000000000000001L, vh4.longValue());

        assertEquals(msg1, 0x0000000000000001L, vsp1.longValue());
        assertEquals(msg1, 0x0000000000000002L, vsp2.longValue());
        assertEquals(msg1, 0x00000000ffffffffL, vsm1.longValue());
        assertEquals(msg1, 0x00000000fffffffeL, vsm2.longValue());
    }

    @Test
    public final void testUIntFloatValue() {
        String msg1 = "UInt.floatValue() failed.";
        UInt vz0 = new UInt(0L, 64);
        UInt vp1 = new UInt(1L, 64);
        UInt vp2 = new UInt(2L, 64);
        UInt vm1 = new UInt(-1L, 64);
        UInt vm2 = new UInt(-2L, 64);
        UInt vh1 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh2 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh3 = new UInt(0x8000000000000000L, 64);
        UInt vh4 = new UInt(0x8000000000000001L, 64);
        UInt vsp1 = new UInt(1L, 32);
        UInt vsp2 = new UInt(2L, 32);
        UInt vsm1 = new UInt(-1L, 32);
        UInt vsm2 = new UInt(-2L, 32);

        assertTrue(msg1,                    0.0F <= vz0.floatValue());
        assertTrue(msg1,                    1.0F <= vp1.floatValue());
        assertTrue(msg1,                    2.0F <= vp2.floatValue());
        assertTrue(msg1, 18446744073709551615.0F <= vm1.floatValue());
        assertTrue(msg1, 18446744073709551614.0F <= vm2.floatValue());
        assertTrue(msg1,  9223372036854775806.0F <= vh1.floatValue());
        assertTrue(msg1,  9223372036854775807.0F <= vh2.floatValue());
        assertTrue(msg1,  9223372036854775808.0F <= vh3.floatValue());
        assertTrue(msg1,  9223372036854775809.0F <= vh4.floatValue());

        assertTrue(msg1,                    1.0F <= vsp1.floatValue());
        assertTrue(msg1,                    2.0F <= vsp2.floatValue());
        assertTrue(msg1,           4294967295.0F <= vsm1.floatValue());
        assertTrue(msg1,           4294967294.0F <= vsm2.floatValue());
    }

    @Test
    public final void testUIntDoubleValue() {
        String msg1 = "UInt.doubleValue() failed.";
        UInt vz0 = new UInt(0L, 64);
        UInt vp1 = new UInt(1L, 64);
        UInt vp2 = new UInt(2L, 64);
        UInt vm1 = new UInt(-1L, 64);
        UInt vm2 = new UInt(-2L, 64);
        UInt vh1 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh2 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh3 = new UInt(0x8000000000000000L, 64);
        UInt vh4 = new UInt(0x8000000000000001L, 64);
        UInt vsp1 = new UInt(1L, 32);
        UInt vsp2 = new UInt(2L, 32);
        UInt vsm1 = new UInt(-1L, 32);
        UInt vsm2 = new UInt(-2L, 32);

        assertTrue(msg1,                    0.0D <= vz0.doubleValue());
        assertTrue(msg1,                    1.0D <= vp1.doubleValue());
        assertTrue(msg1,                    2.0D <= vp2.doubleValue());
        assertTrue(msg1, 18446744073709551615.0D <= vm1.doubleValue());
        assertTrue(msg1, 18446744073709551614.0D <= vm2.doubleValue());
        assertTrue(msg1,  9223372036854775806.0D <= vh1.doubleValue());
        assertTrue(msg1,  9223372036854775807.0D <= vh2.doubleValue());
        assertTrue(msg1,  9223372036854775808.0D <= vh3.doubleValue());
        assertTrue(msg1,  9223372036854775809.0D <= vh4.doubleValue());

        assertTrue(msg1,                    1.0D <= vsp1.doubleValue());
        assertTrue(msg1,                    2.0D <= vsp2.doubleValue());
        assertTrue(msg1,           4294967295.0D <= vsm1.doubleValue());
        assertTrue(msg1,           4294967294.0D <= vsm2.doubleValue());
    }

    @Test
    public final void testUIntToString() {
        String msg1 = "UInt.toString() failed.";
        UInt vz0 = new UInt(0L, 64);
        UInt vp1 = new UInt(1L, 64);
        UInt vp2 = new UInt(2L, 64);
        UInt vm1 = new UInt(-1L, 64);
        UInt vm2 = new UInt(-2L, 64);
        UInt vh1 = new UInt(0x7ffffffffffffffeL, 64);
        UInt vh2 = new UInt(0x7fffffffffffffffL, 64);
        UInt vh3 = new UInt(0x8000000000000000L, 64);
        UInt vh4 = new UInt(0x8000000000000001L, 64);
        UInt vsp1 = new UInt(1L, 32);
        UInt vsp2 = new UInt(2L, 32);
        UInt vsm1 = new UInt(-1L, 32);
        UInt vsm2 = new UInt(-2L, 32);

        assertEquals(msg1,                    "0", vz0.toString());
        assertEquals(msg1,                    "1", vp1.toString());
        assertEquals(msg1,                    "2", vp2.toString());
        assertEquals(msg1, "18446744073709551615", vm1.toString());
        assertEquals(msg1, "18446744073709551614", vm2.toString());
        assertEquals(msg1,  "9223372036854775806", vh1.toString());
        assertEquals(msg1,  "9223372036854775807", vh2.toString());
        assertEquals(msg1,  "9223372036854775808", vh3.toString());
        assertEquals(msg1,  "9223372036854775809", vh4.toString());

        assertEquals(msg1,                    "1", vsp1.toString());
        assertEquals(msg1,                    "2", vsp2.toString());
        assertEquals(msg1,           "4294967295", vsm1.toString());
        assertEquals(msg1,           "4294967294", vsm2.toString());
    }
}
