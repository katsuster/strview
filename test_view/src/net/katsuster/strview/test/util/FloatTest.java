package net.katsuster.strview.test.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.io.*;

public class FloatTest {
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
    public final void testFloat32IntLongInt() {
        String msg1 = "Float32(int, long, int) failed.";
        String msg2 = "Float32(int, long, int) illegal arguments check failed.";
        LargeBitList la = new MemoryBitList(64);
        Float32 va = new Float32("", la, 2, 3);

        assertEquals(msg1, 0, va.getRaw());
        assertEquals(msg1, 2, va.getRange().getStart());
        assertEquals(msg1, 3, va.length());
    }

    @Test
    public final void testFloat32Float32() {
        String msg1 = "Float32(Float32) failed.";
        String msg2 = "Float32.clone() failed.";
        LargeBitList la = new MemoryBitList(64);
        Float32 va = new Float32("", la, 2, 3);
        Float32 vb = new Float32(va);

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
            Float32 vc = (Float32)va.clone();

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
    public final void testFloat32ByteValue() {
        String msg1 = "Float32.byteValue() failed.";
        Float32 vz0 = new Float32("", 0.0F);
        Float32 vp1 = new Float32("", 1.0F);
        Float32 vp2 = new Float32("", 2.0F);
        Float32 vm1 = new Float32("", -1.0F);
        Float32 vm2 = new Float32("", -2.0F);
        Float32 vh1 = new Float32("", 8388606.0F);
        Float32 vh2 = new Float32("", 8388607.0F);
        Float32 vh3 = new Float32("", 8388608.0F);
        Float32 vh4 = new Float32("", 8388609.0F);

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
    public final void testFloat32ShortValue() {
        String msg1 = "Float32.shortValue() failed.";
        Float32 vz0 = new Float32("", 0.0F);
        Float32 vp1 = new Float32("", 1.0F);
        Float32 vp2 = new Float32("", 2.0F);
        Float32 vm1 = new Float32("", -1.0F);
        Float32 vm2 = new Float32("", -2.0F);
        Float32 vh1 = new Float32("", 8388606.0F);
        Float32 vh2 = new Float32("", 8388607.0F);
        Float32 vh3 = new Float32("", 8388608.0F);
        Float32 vh4 = new Float32("", 8388609.0F);

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
    public final void testFloat32IntValue() {
        String msg1 = "Float32.intValue() failed.";
        Float32 vz0 = new Float32("", 0.0F);
        Float32 vp1 = new Float32("", 1.0F);
        Float32 vp2 = new Float32("", 2.0F);
        Float32 vm1 = new Float32("", -1.0F);
        Float32 vm2 = new Float32("", -2.0F);
        Float32 vh1 = new Float32("", 8388606.0F);
        Float32 vh2 = new Float32("", 8388607.0F);
        Float32 vh3 = new Float32("", 8388608.0F);
        Float32 vh4 = new Float32("", 8388609.0F);

        assertEquals(msg1, 0x00000000, vz0.intValue());
        assertEquals(msg1, 0x00000001, vp1.intValue());
        assertEquals(msg1, 0x00000002, vp2.intValue());
        assertEquals(msg1, 0xffffffff, vm1.intValue());
        assertEquals(msg1, 0xfffffffe, vm2.intValue());
        assertEquals(msg1, 0x007ffffe, vh1.intValue());
        assertEquals(msg1, 0x007fffff, vh2.intValue());
        assertEquals(msg1, 0x00800000, vh3.intValue());
        assertEquals(msg1, 0x00800001, vh4.intValue());
    }

    @Test
    public final void testFloat32LongValue() {
        String msg1 = "Float32.longValue() failed.";
        Float32 vz0 = new Float32("", 0.0F);
        Float32 vp1 = new Float32("", 1.0F);
        Float32 vp2 = new Float32("", 2.0F);
        Float32 vm1 = new Float32("", -1.0F);
        Float32 vm2 = new Float32("", -2.0F);
        Float32 vh1 = new Float32("", 8388606.0F);
        Float32 vh2 = new Float32("", 8388607.0F);
        Float32 vh3 = new Float32("", 8388608.0F);
        Float32 vh4 = new Float32("", 8388609.0F);

        assertEquals(msg1, 0x0000000000000000L, vz0.longValue());
        assertEquals(msg1, 0x0000000000000001L, vp1.longValue());
        assertEquals(msg1, 0x0000000000000002L, vp2.longValue());
        assertEquals(msg1, 0xffffffffffffffffL, vm1.longValue());
        assertEquals(msg1, 0xfffffffffffffffeL, vm2.longValue());
        assertEquals(msg1, 0x00000000007ffffeL, vh1.longValue());
        assertEquals(msg1, 0x00000000007fffffL, vh2.longValue());
        assertEquals(msg1, 0x0000000000800000L, vh3.longValue());
        assertEquals(msg1, 0x0000000000800001L, vh4.longValue());
    }

    @Test
    public final void testFloat32FloatValue() {
        String msg1 = "Float32.floatValue() failed.";
        Float32 vz0 = new Float32("", 0.0F);
        Float32 vp1 = new Float32("", 1.0F);
        Float32 vp2 = new Float32("", 2.0F);
        Float32 vm1 = new Float32("", -1.0F);
        Float32 vm2 = new Float32("", -2.0F);
        Float32 vh1 = new Float32("", 8388606.0F);
        Float32 vh2 = new Float32("", 8388607.0F);
        Float32 vh3 = new Float32("", 8388608.0F);
        Float32 vh4 = new Float32("", 8388609.0F);

        assertTrue(msg1,       0.0F <= vz0.floatValue());
        assertTrue(msg1,       1.0F <= vp1.floatValue());
        assertTrue(msg1,       2.0F <= vp2.floatValue());
        assertTrue(msg1,      -1.0F <= vm1.floatValue());
        assertTrue(msg1,      -2.0F <= vm2.floatValue());
        assertTrue(msg1, 8388606.0F <= vh1.floatValue());
        assertTrue(msg1, 8388607.0F <= vh2.floatValue());
        assertTrue(msg1, 8388608.0F <= vh3.floatValue());
        assertTrue(msg1, 8388609.0F <= vh4.floatValue());
    }

    @Test
    public final void testFloat32DoubleValue() {
        String msg1 = "Float32.doubleValue() failed.";
        Float32 vz0 = new Float32("", 0.0F);
        Float32 vp1 = new Float32("", 1.0F);
        Float32 vp2 = new Float32("", 2.0F);
        Float32 vm1 = new Float32("", -1.0F);
        Float32 vm2 = new Float32("", -2.0F);
        Float32 vh1 = new Float32("", 8388606.0F);
        Float32 vh2 = new Float32("", 8388607.0F);
        Float32 vh3 = new Float32("", 8388608.0F);
        Float32 vh4 = new Float32("", 8388609.0F);

        assertTrue(msg1,       0.0D <= vz0.doubleValue());
        assertTrue(msg1,       1.0D <= vp1.doubleValue());
        assertTrue(msg1,       2.0D <= vp2.doubleValue());
        assertTrue(msg1,      -1.0D <= vm1.doubleValue());
        assertTrue(msg1,      -2.0D <= vm2.doubleValue());
        assertTrue(msg1, 8388606.0D <= vh1.doubleValue());
        assertTrue(msg1, 8388607.0D <= vh2.doubleValue());
        assertTrue(msg1, 8388608.0D <= vh3.doubleValue());
        assertTrue(msg1, 8388609.0D <= vh4.doubleValue());
    }

    @Test
    public final void testFloat32ToString() {
        String msg1 = "Float32.toString() failed.";
        Float32 vz0 = new Float32("", 0.0F);
        Float32 vp1 = new Float32("", 1.0F);
        Float32 vp2 = new Float32("", 2.0F);
        Float32 vm1 = new Float32("", -1.0F);
        Float32 vm2 = new Float32("", -2.0F);
        Float32 vh1 = new Float32("", 8388606.0F);
        Float32 vh2 = new Float32("", 8388607.0F);
        Float32 vh3 = new Float32("", 8388608.0F);
        Float32 vh4 = new Float32("", 8388609.0F);

        assertEquals(msg1,       "0.0", vz0.toString());
        assertEquals(msg1,       "1.0", vp1.toString());
        assertEquals(msg1,       "2.0", vp2.toString());
        assertEquals(msg1,      "-1.0", vm1.toString());
        assertEquals(msg1,      "-2.0", vm2.toString());
        assertEquals(msg1, "8388606.0", vh1.toString());
        assertEquals(msg1, "8388607.0", vh2.toString());
        assertEquals(msg1, "8388608.0", vh3.toString());
        assertEquals(msg1, "8388609.0", vh4.toString());
    }

    @Test
    public final void testFloat64LongLongInt() {
        String msg1 = "Float64(long, long, int) failed.";
        String msg2 = "Float64(long, long, int) illegal arguments check failed.";
        LargeBitList la = new MemoryBitList(64);
        Float64 va = new Float64("", la, 2, 3);

        assertEquals(msg1, 0, va.getRaw());
        assertEquals(msg1, 2, va.getRange().getStart());
        assertEquals(msg1, 3, va.length());
    }

    @Test
    public final void testFloat64Float64() {
        String msg1 = "Float64(Float64) failed.";
        String msg2 = "Float64.clone() failed.";
        LargeBitList la = new MemoryBitList(64);
        Float64 va = new Float64("", la, 2, 3);
        Float64 vb = new Float64(va);

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
            Float64 vc = (Float64)va.clone();

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
    public final void testFloat64ByteValue() {
        String msg1 = "Float64.byteValue() failed.";
        Float64 vz0 = new Float64("", 0.0D);
        Float64 vp1 = new Float64("", 1.0D);
        Float64 vp2 = new Float64("", 2.0D);
        Float64 vm1 = new Float64("", -1.0D);
        Float64 vm2 = new Float64("", -2.0D);
        Float64 vh1 = new Float64("", 4503599627370494.0D);
        Float64 vh2 = new Float64("", 4503599627370495.0D);
        Float64 vh3 = new Float64("", 4503599627370496.0D);
        Float64 vh4 = new Float64("", 4503599627370497.0D);

        assertEquals(msg1, (byte)0x00, vz0.byteValue());
        assertEquals(msg1, (byte)0x01, vp1.byteValue());
        assertEquals(msg1, (byte)0x02, vp2.byteValue());
        assertEquals(msg1, (byte)0xff, vm1.byteValue());
        assertEquals(msg1, (byte)0xfe, vm2.byteValue());
        assertEquals(msg1, (byte)0xff, vh1.byteValue());
        assertEquals(msg1, (byte)0xff, vh2.byteValue());
        assertEquals(msg1, (byte)0xff, vh3.byteValue());
        assertEquals(msg1, (byte)0xff, vh4.byteValue());
    }

    @Test
    public final void testFloat64ShortValue() {
        String msg1 = "Float64.shortValue() failed.";
        Float64 vz0 = new Float64("", 0.0D);
        Float64 vp1 = new Float64("", 1.0D);
        Float64 vp2 = new Float64("", 2.0D);
        Float64 vm1 = new Float64("", -1.0D);
        Float64 vm2 = new Float64("", -2.0D);
        Float64 vh1 = new Float64("", 4503599627370494.0D);
        Float64 vh2 = new Float64("", 4503599627370495.0D);
        Float64 vh3 = new Float64("", 4503599627370496.0D);
        Float64 vh4 = new Float64("", 4503599627370497.0D);

        assertEquals(msg1, (short)0x0000, vz0.shortValue());
        assertEquals(msg1, (short)0x0001, vp1.shortValue());
        assertEquals(msg1, (short)0x0002, vp2.shortValue());
        assertEquals(msg1, (short)0xffff, vm1.shortValue());
        assertEquals(msg1, (short)0xfffe, vm2.shortValue());
        assertEquals(msg1, (short)0xffff, vh1.shortValue());
        assertEquals(msg1, (short)0xffff, vh2.shortValue());
        assertEquals(msg1, (short)0xffff, vh3.shortValue());
        assertEquals(msg1, (short)0xffff, vh4.shortValue());
    }

    @Test
    public final void testFloat64IntValue() {
        String msg1 = "Float64.intValue() failed.";
        Float64 vz0 = new Float64("", 0.0D);
        Float64 vp1 = new Float64("", 1.0D);
        Float64 vp2 = new Float64("", 2.0D);
        Float64 vm1 = new Float64("", -1.0D);
        Float64 vm2 = new Float64("", -2.0D);
        Float64 vh1 = new Float64("", 4503599627370494.0D);
        Float64 vh2 = new Float64("", 4503599627370495.0D);
        Float64 vh3 = new Float64("", 4503599627370496.0D);
        Float64 vh4 = new Float64("", 4503599627370497.0D);

        assertEquals(msg1, 0x00000000, vz0.intValue());
        assertEquals(msg1, 0x00000001, vp1.intValue());
        assertEquals(msg1, 0x00000002, vp2.intValue());
        assertEquals(msg1, 0xffffffff, vm1.intValue());
        assertEquals(msg1, 0xfffffffe, vm2.intValue());
        //int に変換するには値が大きすぎるので、
        //キャスト結果はすべて Integer.MAX_VALUE になる。
        //short, byte については、
        //double -> int -> short, byte と変換されるため、
        //Integer.MAX_VALUE の下位ビット値である、0xffff, 0xff となる。
        //※Java 言語規定「5.1.3 Narrowing Primitive Conversions」を参照
        assertEquals(msg1, 0x7fffffff, vh1.intValue());
        assertEquals(msg1, 0x7fffffff, vh2.intValue());
        assertEquals(msg1, 0x7fffffff, vh3.intValue());
        assertEquals(msg1, 0x7fffffff, vh4.intValue());
    }

    @Test
    public final void testFloat64LongValue() {
        String msg1 = "Float64.longValue() failed.";
        Float64 vz0 = new Float64("", 0.0D);
        Float64 vp1 = new Float64("", 1.0D);
        Float64 vp2 = new Float64("", 2.0D);
        Float64 vm1 = new Float64("", -1.0D);
        Float64 vm2 = new Float64("", -2.0D);
        Float64 vh1 = new Float64("", 4503599627370494.0D);
        Float64 vh2 = new Float64("", 4503599627370495.0D);
        Float64 vh3 = new Float64("", 4503599627370496.0D);
        Float64 vh4 = new Float64("", 4503599627370497.0D);

        assertEquals(msg1, 0x0000000000000000L, vz0.longValue());
        assertEquals(msg1, 0x0000000000000001L, vp1.longValue());
        assertEquals(msg1, 0x0000000000000002L, vp2.longValue());
        assertEquals(msg1, 0xffffffffffffffffL, vm1.longValue());
        assertEquals(msg1, 0xfffffffffffffffeL, vm2.longValue());
        assertEquals(msg1, 0x000ffffffffffffeL, vh1.longValue());
        assertEquals(msg1, 0x000fffffffffffffL, vh2.longValue());
        assertEquals(msg1, 0x0010000000000000L, vh3.longValue());
        assertEquals(msg1, 0x0010000000000001L, vh4.longValue());
    }

    @Test
    public final void testFloat64FloatValue() {
        String msg1 = "Float64.floatValue() failed.";
        Float64 vz0 = new Float64("", 0.0D);
        Float64 vp1 = new Float64("", 1.0D);
        Float64 vp2 = new Float64("", 2.0D);
        Float64 vm1 = new Float64("", -1.0D);
        Float64 vm2 = new Float64("", -2.0D);
        Float64 vh1 = new Float64("", 4503599627370494.0D);
        Float64 vh2 = new Float64("", 4503599627370495.0D);
        Float64 vh3 = new Float64("", 4503599627370496.0D);
        Float64 vh4 = new Float64("", 4503599627370497.0D);

        assertTrue(msg1,                0.0F <= vz0.floatValue());
        assertTrue(msg1,                1.0F <= vp1.floatValue());
        assertTrue(msg1,                2.0F <= vp2.floatValue());
        assertTrue(msg1,               -1.0F <= vm1.floatValue());
        assertTrue(msg1,               -2.0F <= vm2.floatValue());
        assertTrue(msg1, 4503599627370494.0F <= vh1.floatValue());
        assertTrue(msg1, 4503599627370495.0F <= vh2.floatValue());
        assertTrue(msg1, 4503599627370496.0F <= vh3.floatValue());
        assertTrue(msg1, 4503599627370497.0F <= vh4.floatValue());
    }

    @Test
    public final void testFloat64DoubleValue() {
        String msg1 = "Float64.doubleValue() failed.";
        Float64 vz0 = new Float64("", 0.0D);
        Float64 vp1 = new Float64("", 1.0D);
        Float64 vp2 = new Float64("", 2.0D);
        Float64 vm1 = new Float64("", -1.0D);
        Float64 vm2 = new Float64("", -2.0D);
        Float64 vh1 = new Float64("", 4503599627370494.0D);
        Float64 vh2 = new Float64("", 4503599627370495.0D);
        Float64 vh3 = new Float64("", 4503599627370496.0D);
        Float64 vh4 = new Float64("", 4503599627370497.0D);

        assertTrue(msg1,                0.0D <= vz0.doubleValue());
        assertTrue(msg1,                1.0D <= vp1.doubleValue());
        assertTrue(msg1,                2.0D <= vp2.doubleValue());
        assertTrue(msg1,               -1.0D <= vm1.doubleValue());
        assertTrue(msg1,               -2.0D <= vm2.doubleValue());
        assertTrue(msg1, 4503599627370494.0D <= vh1.doubleValue());
        assertTrue(msg1, 4503599627370495.0D <= vh2.doubleValue());
        assertTrue(msg1, 4503599627370496.0D <= vh3.doubleValue());
        assertTrue(msg1, 4503599627370497.0D <= vh4.doubleValue());
    }

    @Test
    public final void testFloat64ToString() {
        String msg1 = "Float64.toString() failed.";
        Float64 vz0 = new Float64("", 0.0D);
        Float64 vp1 = new Float64("", 1.0D);
        Float64 vp2 = new Float64("", 2.0D);
        Float64 vm1 = new Float64("", -1.0D);
        Float64 vm2 = new Float64("", -2.0D);
        Float64 vh1 = new Float64("", 4503599627370494.0D);
        Float64 vh2 = new Float64("", 4503599627370495.0D);
        Float64 vh3 = new Float64("", 4503599627370496.0D);
        Float64 vh4 = new Float64("", 4503599627370497.0D);

        assertEquals(msg1,                  "0.0", vz0.toString());
        assertEquals(msg1,                  "1.0", vp1.toString());
        assertEquals(msg1,                  "2.0", vp2.toString());
        assertEquals(msg1,                 "-1.0", vm1.toString());
        assertEquals(msg1,                 "-2.0", vm2.toString());
        assertEquals(msg1, "4.503599627370494E15", vh1.toString());
        assertEquals(msg1, "4.503599627370495E15", vh2.toString());
        assertEquals(msg1, "4.503599627370496E15", vh3.toString());
        assertEquals(msg1, "4.503599627370497E15", vh4.toString());
    }
}
