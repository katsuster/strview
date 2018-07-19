package net.katsuster.strview.test.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.io.*;

public class SFixedTest {
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
    public final void testSFixed16_16IntLongInt() {
        String msg1 = "SFixed16_16(int, long, int) failed.";
        String msg2 = "SFixed16_16(int, long, int) illegal arguments check failed.";
        LargeBitList la = new MemoryBitList(64);
        SFixed16_16 va = new SFixed16_16("", la, 2, 3);

        assertEquals(msg1, 0, va.getRaw());
        assertEquals(msg1, 2, va.getRange().getStart());
        assertEquals(msg1, 3, va.length());
    }

    @Test
    public final void testSFixed16_16SFixed16_16() {
        String msg1 = "SFixed16_16(SFixed16_16) failed.";
        String msg2 = "SFixed16_16.clone() failed.";
        LargeBitList la = new MemoryBitList(64);
        SFixed16_16 va = new SFixed16_16("", la, 2, 3);
        SFixed16_16 vb = new SFixed16_16(va);

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
            SFixed16_16 vc = (SFixed16_16)va.clone();

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
    public final void testSFixed16_16ByteValue() {
        String msg1 = "SFixed16_16.byteValue() failed.";
        SFixed16_16 vz0 = new SFixed16_16("", 0x00000000);
        SFixed16_16 vp1 = new SFixed16_16("", 0x00010000);
        SFixed16_16 vp2 = new SFixed16_16("", 0x00020000);
        SFixed16_16 vm1 = new SFixed16_16("", 0xffff0000);
        SFixed16_16 vm2 = new SFixed16_16("", 0xfffe0000);
        SFixed16_16 vh1 = new SFixed16_16("", 0x7ffe8000);
        SFixed16_16 vh2 = new SFixed16_16("", 0x7fff8000);
        SFixed16_16 vh3 = new SFixed16_16("", 0x8000c000);
        SFixed16_16 vh4 = new SFixed16_16("", 0x8001c000);

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
    public final void testSFixed16_16ShortValue() {
        String msg1 = "SFixed16_16.shortValue() failed.";
        SFixed16_16 vz0 = new SFixed16_16("", 0x00000000);
        SFixed16_16 vp1 = new SFixed16_16("", 0x00010000);
        SFixed16_16 vp2 = new SFixed16_16("", 0x00020000);
        SFixed16_16 vm1 = new SFixed16_16("", 0xffff0000);
        SFixed16_16 vm2 = new SFixed16_16("", 0xfffe0000);
        SFixed16_16 vh1 = new SFixed16_16("", 0x7ffe8000);
        SFixed16_16 vh2 = new SFixed16_16("", 0x7fff8000);
        SFixed16_16 vh3 = new SFixed16_16("", 0x8000c000);
        SFixed16_16 vh4 = new SFixed16_16("", 0x8001c000);

        assertEquals(msg1, (short)0x0000, vz0.shortValue());
        assertEquals(msg1, (short)0x0001, vp1.shortValue());
        assertEquals(msg1, (short)0x0002, vp2.shortValue());
        assertEquals(msg1, (short)0xffff, vm1.shortValue());
        assertEquals(msg1, (short)0xfffe, vm2.shortValue());
        assertEquals(msg1, (short)0x7ffe, vh1.shortValue());
        assertEquals(msg1, (short)0x7fff, vh2.shortValue());
        assertEquals(msg1, (short)0x8000, vh3.shortValue());
        assertEquals(msg1, (short)0x8001, vh4.shortValue());
    }

    @Test
    public final void testSFixed16_16IntValue() {
        String msg1 = "SFixed16_16.intValue() failed.";
        SFixed16_16 vz0 = new SFixed16_16("", 0x00000000);
        SFixed16_16 vp1 = new SFixed16_16("", 0x00010000);
        SFixed16_16 vp2 = new SFixed16_16("", 0x00020000);
        SFixed16_16 vm1 = new SFixed16_16("", 0xffff0000);
        SFixed16_16 vm2 = new SFixed16_16("", 0xfffe0000);
        SFixed16_16 vh1 = new SFixed16_16("", 0x7ffe8000);
        SFixed16_16 vh2 = new SFixed16_16("", 0x7fff8000);
        SFixed16_16 vh3 = new SFixed16_16("", 0x8000c000);
        SFixed16_16 vh4 = new SFixed16_16("", 0x8001c000);

        assertEquals(msg1, 0x00000000, vz0.intValue());
        assertEquals(msg1, 0x00000001, vp1.intValue());
        assertEquals(msg1, 0x00000002, vp2.intValue());
        assertEquals(msg1, 0xffffffff, vm1.intValue());
        assertEquals(msg1, 0xfffffffe, vm2.intValue());
        assertEquals(msg1, 0x00007ffe, vh1.intValue());
        assertEquals(msg1, 0x00007fff, vh2.intValue());
        assertEquals(msg1, 0xffff8000, vh3.intValue());
        assertEquals(msg1, 0xffff8001, vh4.intValue());
    }

    @Test
    public final void testSFixed16_16LongValue() {
        String msg1 = "SFixed16_16.longValue() failed.";
        SFixed16_16 vz0 = new SFixed16_16("", 0x00000000);
        SFixed16_16 vp1 = new SFixed16_16("", 0x00010000);
        SFixed16_16 vp2 = new SFixed16_16("", 0x00020000);
        SFixed16_16 vm1 = new SFixed16_16("", 0xffff0000);
        SFixed16_16 vm2 = new SFixed16_16("", 0xfffe0000);
        SFixed16_16 vh1 = new SFixed16_16("", 0x7ffe8000);
        SFixed16_16 vh2 = new SFixed16_16("", 0x7fff8000);
        SFixed16_16 vh3 = new SFixed16_16("", 0x8000c000);
        SFixed16_16 vh4 = new SFixed16_16("", 0x8001c000);

        assertEquals(msg1, 0x0000000000000000L, vz0.longValue());
        assertEquals(msg1, 0x0000000000000001L, vp1.longValue());
        assertEquals(msg1, 0x0000000000000002L, vp2.longValue());
        assertEquals(msg1, 0xffffffffffffffffL, vm1.longValue());
        assertEquals(msg1, 0xfffffffffffffffeL, vm2.longValue());
        assertEquals(msg1, 0x0000000000007ffeL, vh1.longValue());
        assertEquals(msg1, 0x0000000000007fffL, vh2.longValue());
        assertEquals(msg1, 0xffffffffffff8000L, vh3.longValue());
        assertEquals(msg1, 0xffffffffffff8001L, vh4.longValue());
    }

    @Test
    public final void testSFixed16_16FloatValue() {
        String msg1 = "SFixed16_16.floatValue() failed.";
        SFixed16_16 vz0 = new SFixed16_16("", 0x00000000);
        SFixed16_16 vp1 = new SFixed16_16("", 0x00010000);
        SFixed16_16 vp2 = new SFixed16_16("", 0x00020000);
        SFixed16_16 vm1 = new SFixed16_16("", 0xffff0000);
        SFixed16_16 vm2 = new SFixed16_16("", 0xfffe0000);
        SFixed16_16 vh1 = new SFixed16_16("", 0x7ffe8000);
        SFixed16_16 vh2 = new SFixed16_16("", 0x7fff8000);
        SFixed16_16 vh3 = new SFixed16_16("", 0x8000c000);
        SFixed16_16 vh4 = new SFixed16_16("", 0x8001c000);

        assertTrue(msg1,       0.0F == vz0.floatValue());
        assertTrue(msg1,       1.0F == vp1.floatValue());
        assertTrue(msg1,       2.0F == vp2.floatValue());
        assertTrue(msg1,      -1.0F == vm1.floatValue());
        assertTrue(msg1,      -2.0F == vm2.floatValue());
        assertTrue(msg1,   32766.5F == vh1.floatValue());
        assertTrue(msg1,   32767.5F == vh2.floatValue());
        assertTrue(msg1,  -32768.75F == vh3.floatValue());
        assertTrue(msg1,  -32767.75F == vh4.floatValue());
    }

    @Test
    public final void testSFixed16_16DoubleValue() {
        String msg1 = "SFixed16_16.doubleValue() failed.";
        SFixed16_16 vz0 = new SFixed16_16("", 0x00000000);
        SFixed16_16 vp1 = new SFixed16_16("", 0x00010000);
        SFixed16_16 vp2 = new SFixed16_16("", 0x00020000);
        SFixed16_16 vm1 = new SFixed16_16("", 0xffff0000);
        SFixed16_16 vm2 = new SFixed16_16("", 0xfffe0000);
        SFixed16_16 vh1 = new SFixed16_16("", 0x7ffe8000);
        SFixed16_16 vh2 = new SFixed16_16("", 0x7fff8000);
        SFixed16_16 vh3 = new SFixed16_16("", 0x8000c000);
        SFixed16_16 vh4 = new SFixed16_16("", 0x8001c000);

        assertTrue(msg1,       0.0D == vz0.doubleValue());
        assertTrue(msg1,       1.0D == vp1.doubleValue());
        assertTrue(msg1,       2.0D == vp2.doubleValue());
        assertTrue(msg1,      -1.0D == vm1.doubleValue());
        assertTrue(msg1,      -2.0D == vm2.doubleValue());
        assertTrue(msg1,   32766.5D == vh1.doubleValue());
        assertTrue(msg1,   32767.5D == vh2.doubleValue());
        assertTrue(msg1,  -32768.75D == vh3.doubleValue());
        assertTrue(msg1,  -32767.75D == vh4.doubleValue());
    }

    @Test
    public final void testSFixed16_16ToString() {
        String msg1 = "SFixed16_16.toString() failed.";
        SFixed16_16 vz0 = new SFixed16_16("", 0x00000000);
        SFixed16_16 vp1 = new SFixed16_16("", 0x00010000);
        SFixed16_16 vp2 = new SFixed16_16("", 0x00020000);
        SFixed16_16 vm1 = new SFixed16_16("", 0xffff0000);
        SFixed16_16 vm2 = new SFixed16_16("", 0xfffe0000);
        SFixed16_16 vh1 = new SFixed16_16("", 0x7ffe8000);
        SFixed16_16 vh2 = new SFixed16_16("", 0x7fff8000);
        SFixed16_16 vh3 = new SFixed16_16("", 0x8000c000);
        SFixed16_16 vh4 = new SFixed16_16("", 0x8001c000);

        assertEquals(msg1,       "0.0", vz0.toString());
        assertEquals(msg1,       "1.0", vp1.toString());
        assertEquals(msg1,       "2.0", vp2.toString());
        assertEquals(msg1,      "-1.0", vm1.toString());
        assertEquals(msg1,      "-2.0", vm2.toString());
        assertEquals(msg1,   "32766.5", vh1.toString());
        assertEquals(msg1,   "32767.5", vh2.toString());
        assertEquals(msg1,  "-32768.75", vh3.toString());
        assertEquals(msg1,  "-32767.75", vh4.toString());
    }

    @Test
    public final void testSFixed8_8ShortLongInt() {
        String msg1 = "SFixed8_8(short, long, int) failed.";
        String msg2 = "SFixed8_8(short, long, int) illegal arguments check failed.";
        LargeBitList la = new MemoryBitList(64);
        SFixed8_8 va = new SFixed8_8("", la, 2, 3);

        assertEquals(msg1, 0, va.getRaw());
        assertEquals(msg1, 2, va.getRange().getStart());
        assertEquals(msg1, 3, va.length());
    }

    @Test
    public final void testSFixed8_8SFixed8_8() {
        String msg1 = "SFixed8_8(SFixed8_8) failed.";
        String msg2 = "SFixed8_8.clone() failed.";
        LargeBitList la = new MemoryBitList(64);
        SFixed8_8 va = new SFixed8_8("", la, 2, 3);
        SFixed8_8 vb = new SFixed8_8(va);

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
            SFixed8_8 vc = (SFixed8_8)va.clone();

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
    public final void testSFixed8_8ByteValue() {
        String msg1 = "SFixed8_8.byteValue() failed.";
        SFixed8_8 vz0 = new SFixed8_8("", (short)0x0000);
        SFixed8_8 vp1 = new SFixed8_8("", (short)0x0100);
        SFixed8_8 vp2 = new SFixed8_8("", (short)0x0200);
        SFixed8_8 vm1 = new SFixed8_8("", (short)0xff00);
        SFixed8_8 vm2 = new SFixed8_8("", (short)0xfe00);
        SFixed8_8 vh1 = new SFixed8_8("", (short)0x7e80);
        SFixed8_8 vh2 = new SFixed8_8("", (short)0x7f80);
        SFixed8_8 vh3 = new SFixed8_8("", (short)0x80c0);
        SFixed8_8 vh4 = new SFixed8_8("", (short)0x81c0);

        assertEquals(msg1, (byte)0x00, vz0.byteValue());
        assertEquals(msg1, (byte)0x01, vp1.byteValue());
        assertEquals(msg1, (byte)0x02, vp2.byteValue());
        assertEquals(msg1, (byte)0xff, vm1.byteValue());
        assertEquals(msg1, (byte)0xfe, vm2.byteValue());
        assertEquals(msg1, (byte)0x7e, vh1.byteValue());
        assertEquals(msg1, (byte)0x7f, vh2.byteValue());
        assertEquals(msg1, (byte)0x80, vh3.byteValue());
        assertEquals(msg1, (byte)0x81, vh4.byteValue());
    }

    @Test
    public final void testSFixed8_8ShortValue() {
        String msg1 = "SFixed8_8.shortValue() failed.";
        SFixed8_8 vz0 = new SFixed8_8("", (short)0x0000);
        SFixed8_8 vp1 = new SFixed8_8("", (short)0x0100);
        SFixed8_8 vp2 = new SFixed8_8("", (short)0x0200);
        SFixed8_8 vm1 = new SFixed8_8("", (short)0xff00);
        SFixed8_8 vm2 = new SFixed8_8("", (short)0xfe00);
        SFixed8_8 vh1 = new SFixed8_8("", (short)0x7e80);
        SFixed8_8 vh2 = new SFixed8_8("", (short)0x7f80);
        SFixed8_8 vh3 = new SFixed8_8("", (short)0x80c0);
        SFixed8_8 vh4 = new SFixed8_8("", (short)0x81c0);

        assertEquals(msg1, (short)0x0000, vz0.shortValue());
        assertEquals(msg1, (short)0x0001, vp1.shortValue());
        assertEquals(msg1, (short)0x0002, vp2.shortValue());
        assertEquals(msg1, (short)0xffff, vm1.shortValue());
        assertEquals(msg1, (short)0xfffe, vm2.shortValue());
        assertEquals(msg1, (short)0x007e, vh1.shortValue());
        assertEquals(msg1, (short)0x007f, vh2.shortValue());
        assertEquals(msg1, (short)0xff80, vh3.shortValue());
        assertEquals(msg1, (short)0xff81, vh4.shortValue());
    }

    @Test
    public final void testSFixed8_8IntValue() {
        String msg1 = "SFixed8_8.intValue() failed.";
        SFixed8_8 vz0 = new SFixed8_8("", (short)0x0000);
        SFixed8_8 vp1 = new SFixed8_8("", (short)0x0100);
        SFixed8_8 vp2 = new SFixed8_8("", (short)0x0200);
        SFixed8_8 vm1 = new SFixed8_8("", (short)0xff00);
        SFixed8_8 vm2 = new SFixed8_8("", (short)0xfe00);
        SFixed8_8 vh1 = new SFixed8_8("", (short)0x7e80);
        SFixed8_8 vh2 = new SFixed8_8("", (short)0x7f80);
        SFixed8_8 vh3 = new SFixed8_8("", (short)0x80c0);
        SFixed8_8 vh4 = new SFixed8_8("", (short)0x81c0);

        assertEquals(msg1, 0x00000000, vz0.intValue());
        assertEquals(msg1, 0x00000001, vp1.intValue());
        assertEquals(msg1, 0x00000002, vp2.intValue());
        assertEquals(msg1, 0xffffffff, vm1.intValue());
        assertEquals(msg1, 0xfffffffe, vm2.intValue());
        assertEquals(msg1, 0x0000007e, vh1.intValue());
        assertEquals(msg1, 0x0000007f, vh2.intValue());
        assertEquals(msg1, 0xffffff80, vh3.intValue());
        assertEquals(msg1, 0xffffff81, vh4.intValue());
    }

    @Test
    public final void testSFixed8_8LongValue() {
        String msg1 = "SFixed8_8.longValue() failed.";
        SFixed8_8 vz0 = new SFixed8_8("", (short)0x0000);
        SFixed8_8 vp1 = new SFixed8_8("", (short)0x0100);
        SFixed8_8 vp2 = new SFixed8_8("", (short)0x0200);
        SFixed8_8 vm1 = new SFixed8_8("", (short)0xff00);
        SFixed8_8 vm2 = new SFixed8_8("", (short)0xfe00);
        SFixed8_8 vh1 = new SFixed8_8("", (short)0x7e80);
        SFixed8_8 vh2 = new SFixed8_8("", (short)0x7f80);
        SFixed8_8 vh3 = new SFixed8_8("", (short)0x80c0);
        SFixed8_8 vh4 = new SFixed8_8("", (short)0x81c0);

        assertEquals(msg1, 0x0000000000000000L, vz0.longValue());
        assertEquals(msg1, 0x0000000000000001L, vp1.longValue());
        assertEquals(msg1, 0x0000000000000002L, vp2.longValue());
        assertEquals(msg1, 0xffffffffffffffffL, vm1.longValue());
        assertEquals(msg1, 0xfffffffffffffffeL, vm2.longValue());
        assertEquals(msg1, 0x000000000000007eL, vh1.longValue());
        assertEquals(msg1, 0x000000000000007fL, vh2.longValue());
        assertEquals(msg1, 0xffffffffffffff80L, vh3.longValue());
        assertEquals(msg1, 0xffffffffffffff81L, vh4.longValue());
    }

    @Test
    public final void testSFixed8_8FloatValue() {
        String msg1 = "SFixed8_8.floatValue() failed.";
        SFixed8_8 vz0 = new SFixed8_8("", (short)0x0000);
        SFixed8_8 vp1 = new SFixed8_8("", (short)0x0100);
        SFixed8_8 vp2 = new SFixed8_8("", (short)0x0200);
        SFixed8_8 vm1 = new SFixed8_8("", (short)0xff00);
        SFixed8_8 vm2 = new SFixed8_8("", (short)0xfe00);
        SFixed8_8 vh1 = new SFixed8_8("", (short)0x7e80);
        SFixed8_8 vh2 = new SFixed8_8("", (short)0x7f80);
        SFixed8_8 vh3 = new SFixed8_8("", (short)0x80c0);
        SFixed8_8 vh4 = new SFixed8_8("", (short)0x81c0);

        assertTrue(msg1,       0.0F == vz0.floatValue());
        assertTrue(msg1,       1.0F == vp1.floatValue());
        assertTrue(msg1,       2.0F == vp2.floatValue());
        assertTrue(msg1,      -1.0F == vm1.floatValue());
        assertTrue(msg1,      -2.0F == vm2.floatValue());
        assertTrue(msg1,     126.5F == vh1.floatValue());
        assertTrue(msg1,     127.5F == vh2.floatValue());
        assertTrue(msg1,    -128.75F == vh3.floatValue());
        assertTrue(msg1,    -127.75F == vh4.floatValue());
    }

    @Test
    public final void testSFixed8_8DoubleValue() {
        String msg1 = "SFixed8_8.doubleValue() failed.";
        SFixed8_8 vz0 = new SFixed8_8("", (short)0x0000);
        SFixed8_8 vp1 = new SFixed8_8("", (short)0x0100);
        SFixed8_8 vp2 = new SFixed8_8("", (short)0x0200);
        SFixed8_8 vm1 = new SFixed8_8("", (short)0xff00);
        SFixed8_8 vm2 = new SFixed8_8("", (short)0xfe00);
        SFixed8_8 vh1 = new SFixed8_8("", (short)0x7e80);
        SFixed8_8 vh2 = new SFixed8_8("", (short)0x7f80);
        SFixed8_8 vh3 = new SFixed8_8("", (short)0x80c0);
        SFixed8_8 vh4 = new SFixed8_8("", (short)0x81c0);

        assertTrue(msg1,       0.0D == vz0.doubleValue());
        assertTrue(msg1,       1.0D == vp1.doubleValue());
        assertTrue(msg1,       2.0D == vp2.doubleValue());
        assertTrue(msg1,      -1.0D == vm1.doubleValue());
        assertTrue(msg1,      -2.0D == vm2.doubleValue());
        assertTrue(msg1,     126.5D == vh1.doubleValue());
        assertTrue(msg1,     127.5D == vh2.doubleValue());
        assertTrue(msg1,    -128.75D == vh3.doubleValue());
        assertTrue(msg1,    -127.75D == vh4.doubleValue());
    }

    @Test
    public final void testSFixed8_8ToString() {
        String msg1 = "SFixed8_8.toString() failed.";
        SFixed8_8 vz0 = new SFixed8_8("", (short)0x0000);
        SFixed8_8 vp1 = new SFixed8_8("", (short)0x0100);
        SFixed8_8 vp2 = new SFixed8_8("", (short)0x0200);
        SFixed8_8 vm1 = new SFixed8_8("", (short)0xff00);
        SFixed8_8 vm2 = new SFixed8_8("", (short)0xfe00);
        SFixed8_8 vh1 = new SFixed8_8("", (short)0x7e80);
        SFixed8_8 vh2 = new SFixed8_8("", (short)0x7f80);
        SFixed8_8 vh3 = new SFixed8_8("", (short)0x80c0);
        SFixed8_8 vh4 = new SFixed8_8("", (short)0x81c0);

        assertEquals(msg1,       "0.0", vz0.toString());
        assertEquals(msg1,       "1.0", vp1.toString());
        assertEquals(msg1,       "2.0", vp2.toString());
        assertEquals(msg1,      "-1.0", vm1.toString());
        assertEquals(msg1,      "-2.0", vm2.toString());
        assertEquals(msg1,     "126.5", vh1.toString());
        assertEquals(msg1,     "127.5", vh2.toString());
        assertEquals(msg1,    "-128.75", vh3.toString());
        assertEquals(msg1,    "-127.75", vh4.toString());
    }
}
