package net.katsuster.strview.test.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;

public class UFixedTest {
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
    public final void testUFixed16_16IntLongInt() {
        String msg1 = "UFixed16_16(int, long, int) failed.";
        String msg2 = "UFixed16_16(int, long, int) illegal arguments check failed.";
        LargeBitList la = new MemoryBitList(64);
        UFixed16_16 va = new UFixed16_16(la, 2, 3);

        assertEquals(msg1, 0, va.getRaw());
        assertEquals(msg1, 2, va.getSourceStart());
        assertEquals(msg1, 3, va.length());
    }

    @Test
    public final void testUFixed16_16UFixed16_16() {
        String msg1 = "UFixed16_16(UFixed16_16) failed.";
        String msg2 = "UFixed16_16.clone() failed.";
        LargeBitList la = new MemoryBitList(64);
        UFixed16_16 va = new UFixed16_16(la, 2, 3);
        UFixed16_16 vb = new UFixed16_16(va);

        assertEquals(msg1, va.getRaw(), vb.getRaw());
        assertEquals(msg1, va.getSourceStart(), vb.getSourceStart());
        assertEquals(msg1, va.getSourceEnd(), vb.getSourceEnd());

        vb.setRaw(10);
        vb.setSourceStart(20);
        vb.setSourceEnd(30);
        assertNotEquals(msg1, va.getRaw(), vb.getRaw());
        assertNotEquals(msg1, va.getSourceStart(), vb.getSourceStart());
        assertNotEquals(msg1, va.getSourceEnd(), vb.getSourceEnd());

        try {
            UFixed16_16 vc = (UFixed16_16)va.clone();

            assertEquals(msg2, va.getRaw(), vc.getRaw());
            assertEquals(msg2, va.getSourceStart(), vc.getSourceStart());
            assertEquals(msg2, va.getSourceEnd(), vc.getSourceEnd());

            vc.setRaw(100);
            vc.setSourceStart(4);
            vc.setSourceEnd(10);
            assertNotEquals(msg2, va.getRaw(), vc.getRaw());
            assertNotEquals(msg2, va.getSourceStart(), vc.getSourceStart());
            assertNotEquals(msg2, va.getSourceEnd(), vc.getSourceEnd());
        } catch (Exception ex) {
            fail(msg2);
        }
    }

    @Test
    public final void testUFixed16_16ByteValue() {
        String msg1 = "UFixed16_16.byteValue() failed.";
        UFixed16_16 vz0 = new UFixed16_16(0x00000000);
        UFixed16_16 vp1 = new UFixed16_16(0x00010000);
        UFixed16_16 vp2 = new UFixed16_16(0x00020000);
        UFixed16_16 vm1 = new UFixed16_16(0xffff0000);
        UFixed16_16 vm2 = new UFixed16_16(0xfffe0000);
        UFixed16_16 vh1 = new UFixed16_16(0x7ffe8000);
        UFixed16_16 vh2 = new UFixed16_16(0x7fff8000);
        UFixed16_16 vh3 = new UFixed16_16(0x8000c000);
        UFixed16_16 vh4 = new UFixed16_16(0x8001c000);

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
    public final void testUFixed16_16ShortValue() {
        String msg1 = "UFixed16_16.shortValue() failed.";
        UFixed16_16 vz0 = new UFixed16_16(0x00000000);
        UFixed16_16 vp1 = new UFixed16_16(0x00010000);
        UFixed16_16 vp2 = new UFixed16_16(0x00020000);
        UFixed16_16 vm1 = new UFixed16_16(0xffff0000);
        UFixed16_16 vm2 = new UFixed16_16(0xfffe0000);
        UFixed16_16 vh1 = new UFixed16_16(0x7ffe8000);
        UFixed16_16 vh2 = new UFixed16_16(0x7fff8000);
        UFixed16_16 vh3 = new UFixed16_16(0x8000c000);
        UFixed16_16 vh4 = new UFixed16_16(0x8001c000);

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
    public final void testUFixed16_16IntValue() {
        String msg1 = "UFixed16_16.intValue() failed.";
        UFixed16_16 vz0 = new UFixed16_16(0x00000000);
        UFixed16_16 vp1 = new UFixed16_16(0x00010000);
        UFixed16_16 vp2 = new UFixed16_16(0x00020000);
        UFixed16_16 vm1 = new UFixed16_16(0xffff0000);
        UFixed16_16 vm2 = new UFixed16_16(0xfffe0000);
        UFixed16_16 vh1 = new UFixed16_16(0x7ffe8000);
        UFixed16_16 vh2 = new UFixed16_16(0x7fff8000);
        UFixed16_16 vh3 = new UFixed16_16(0x8000c000);
        UFixed16_16 vh4 = new UFixed16_16(0x8001c000);

        assertEquals(msg1, 0x00000000, vz0.intValue());
        assertEquals(msg1, 0x00000001, vp1.intValue());
        assertEquals(msg1, 0x00000002, vp2.intValue());
        assertEquals(msg1, 0x0000ffff, vm1.intValue());
        assertEquals(msg1, 0x0000fffe, vm2.intValue());
        assertEquals(msg1, 0x00007ffe, vh1.intValue());
        assertEquals(msg1, 0x00007fff, vh2.intValue());
        assertEquals(msg1, 0x00008000, vh3.intValue());
        assertEquals(msg1, 0x00008001, vh4.intValue());
    }

    @Test
    public final void testUFixed16_16LongValue() {
        String msg1 = "UFixed16_16.longValue() failed.";
        UFixed16_16 vz0 = new UFixed16_16(0x00000000);
        UFixed16_16 vp1 = new UFixed16_16(0x00010000);
        UFixed16_16 vp2 = new UFixed16_16(0x00020000);
        UFixed16_16 vm1 = new UFixed16_16(0xffff0000);
        UFixed16_16 vm2 = new UFixed16_16(0xfffe0000);
        UFixed16_16 vh1 = new UFixed16_16(0x7ffe8000);
        UFixed16_16 vh2 = new UFixed16_16(0x7fff8000);
        UFixed16_16 vh3 = new UFixed16_16(0x8000c000);
        UFixed16_16 vh4 = new UFixed16_16(0x8001c000);

        assertEquals(msg1, 0x0000000000000000L, vz0.longValue());
        assertEquals(msg1, 0x0000000000000001L, vp1.longValue());
        assertEquals(msg1, 0x0000000000000002L, vp2.longValue());
        assertEquals(msg1, 0x000000000000ffffL, vm1.longValue());
        assertEquals(msg1, 0x000000000000fffeL, vm2.longValue());
        assertEquals(msg1, 0x0000000000007ffeL, vh1.longValue());
        assertEquals(msg1, 0x0000000000007fffL, vh2.longValue());
        assertEquals(msg1, 0x0000000000008000L, vh3.longValue());
        assertEquals(msg1, 0x0000000000008001L, vh4.longValue());
    }

    @Test
    public final void testUFixed16_16FloatValue() {
        String msg1 = "UFixed16_16.floatValue() failed.";
        UFixed16_16 vz0 = new UFixed16_16(0x00000000);
        UFixed16_16 vp1 = new UFixed16_16(0x00010000);
        UFixed16_16 vp2 = new UFixed16_16(0x00020000);
        UFixed16_16 vm1 = new UFixed16_16(0xffff0000);
        UFixed16_16 vm2 = new UFixed16_16(0xfffe0000);
        UFixed16_16 vh1 = new UFixed16_16(0x7ffe8000);
        UFixed16_16 vh2 = new UFixed16_16(0x7fff8000);
        UFixed16_16 vh3 = new UFixed16_16(0x8000c000);
        UFixed16_16 vh4 = new UFixed16_16(0x8001c000);

        assertTrue(msg1,       0.0F == vz0.floatValue());
        assertTrue(msg1,       1.0F == vp1.floatValue());
        assertTrue(msg1,       2.0F == vp2.floatValue());
        assertTrue(msg1,   65535.0F == vm1.floatValue());
        assertTrue(msg1,   65534.0F == vm2.floatValue());
        assertTrue(msg1,   32766.5F == vh1.floatValue());
        assertTrue(msg1,   32767.5F == vh2.floatValue());
        assertTrue(msg1,   32768.75F == vh3.floatValue());
        assertTrue(msg1,   32769.75F == vh4.floatValue());
    }

    @Test
    public final void testUFixed16_16DoubleValue() {
        String msg1 = "UFixed16_16.doubleValue() failed.";
        UFixed16_16 vz0 = new UFixed16_16(0x00000000);
        UFixed16_16 vp1 = new UFixed16_16(0x00010000);
        UFixed16_16 vp2 = new UFixed16_16(0x00020000);
        UFixed16_16 vm1 = new UFixed16_16(0xffff0000);
        UFixed16_16 vm2 = new UFixed16_16(0xfffe0000);
        UFixed16_16 vh1 = new UFixed16_16(0x7ffe8000);
        UFixed16_16 vh2 = new UFixed16_16(0x7fff8000);
        UFixed16_16 vh3 = new UFixed16_16(0x8000c000);
        UFixed16_16 vh4 = new UFixed16_16(0x8001c000);

        assertTrue(msg1,       0.0D == vz0.doubleValue());
        assertTrue(msg1,       1.0D == vp1.doubleValue());
        assertTrue(msg1,       2.0D == vp2.doubleValue());
        assertTrue(msg1,   65535.0D == vm1.doubleValue());
        assertTrue(msg1,   65534.0D == vm2.doubleValue());
        assertTrue(msg1,   32766.5D == vh1.doubleValue());
        assertTrue(msg1,   32767.5D == vh2.doubleValue());
        assertTrue(msg1,   32768.75D == vh3.doubleValue());
        assertTrue(msg1,   32769.75D == vh4.doubleValue());
    }

    @Test
    public final void testUFixed16_16ToString() {
        String msg1 = "UFixed16_16.toString() failed.";
        UFixed16_16 vz0 = new UFixed16_16(0x00000000);
        UFixed16_16 vp1 = new UFixed16_16(0x00010000);
        UFixed16_16 vp2 = new UFixed16_16(0x00020000);
        UFixed16_16 vm1 = new UFixed16_16(0xffff0000);
        UFixed16_16 vm2 = new UFixed16_16(0xfffe0000);
        UFixed16_16 vh1 = new UFixed16_16(0x7ffe8000);
        UFixed16_16 vh2 = new UFixed16_16(0x7fff8000);
        UFixed16_16 vh3 = new UFixed16_16(0x8000c000);
        UFixed16_16 vh4 = new UFixed16_16(0x8001c000);

        assertEquals(msg1,       "0.0", vz0.toString());
        assertEquals(msg1,       "1.0", vp1.toString());
        assertEquals(msg1,       "2.0", vp2.toString());
        assertEquals(msg1,   "65535.0", vm1.toString());
        assertEquals(msg1,   "65534.0", vm2.toString());
        assertEquals(msg1,   "32766.5", vh1.toString());
        assertEquals(msg1,   "32767.5", vh2.toString());
        assertEquals(msg1,   "32768.75", vh3.toString());
        assertEquals(msg1,   "32769.75", vh4.toString());
    }

    @Test
    public final void testUFixed8_8ShortLongInt() {
        String msg1 = "UFixed8_8(short, long, int) failed.";
        String msg2 = "UFixed8_8(short, long, int) illegal arguments check failed.";
        LargeBitList la = new MemoryBitList(64);
        UFixed8_8 va = new UFixed8_8(la, 2, 3);

        assertEquals(msg1, 0, va.getRaw());
        assertEquals(msg1, 2, va.getSourceStart());
        assertEquals(msg1, 3, va.length());
    }

    @Test
    public final void testUFixed8_8UFixed8_8() {
        String msg1 = "UFixed8_8(UFixed8_8) failed.";
        String msg2 = "UFixed8_8.clone() failed.";
        LargeBitList la = new MemoryBitList(64);
        UFixed8_8 va = new UFixed8_8(la, 2, 3);
        UFixed8_8 vb = new UFixed8_8(va);

        assertEquals(msg1, va.getRaw(), vb.getRaw());
        assertEquals(msg1, va.getSourceStart(), vb.getSourceStart());
        assertEquals(msg1, va.getSourceEnd(), vb.getSourceEnd());

        vb.setRaw((short)10);
        vb.setSourceStart(20);
        vb.setSourceEnd(30);
        assertNotEquals(msg1, va.getRaw(), vb.getRaw());
        assertNotEquals(msg1, va.getSourceStart(), vb.getSourceStart());
        assertNotEquals(msg1, va.getSourceEnd(), vb.getSourceEnd());

        try {
            UFixed8_8 vc = (UFixed8_8)va.clone();

            assertEquals(msg2, va.getRaw(), vc.getRaw());
            assertEquals(msg2, va.getSourceStart(), vc.getSourceStart());
            assertEquals(msg2, va.getSourceEnd(), vc.getSourceEnd());

            vc.setRaw((short)100);
            vc.setSourceStart(4);
            vc.setSourceEnd(10);
            assertNotEquals(msg2, va.getRaw(), vc.getRaw());
            assertNotEquals(msg2, va.getSourceStart(), vc.getSourceStart());
            assertNotEquals(msg2, va.getSourceEnd(), vc.getSourceEnd());
        } catch (Exception ex) {
            fail(msg2);
        }
    }

    @Test
    public final void testUFixed8_8ByteValue() {
        String msg1 = "UFixed8_8.byteValue() failed.";
        UFixed8_8 vz0 = new UFixed8_8((short)0x0000);
        UFixed8_8 vp1 = new UFixed8_8((short)0x0100);
        UFixed8_8 vp2 = new UFixed8_8((short)0x0200);
        UFixed8_8 vm1 = new UFixed8_8((short)0xff00);
        UFixed8_8 vm2 = new UFixed8_8((short)0xfe00);
        UFixed8_8 vh1 = new UFixed8_8((short)0x7e80);
        UFixed8_8 vh2 = new UFixed8_8((short)0x7f80);
        UFixed8_8 vh3 = new UFixed8_8((short)0x80c0);
        UFixed8_8 vh4 = new UFixed8_8((short)0x81c0);

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
    public final void testUFixed8_8ShortValue() {
        String msg1 = "UFixed8_8.shortValue() failed.";
        UFixed8_8 vz0 = new UFixed8_8((short)0x0000);
        UFixed8_8 vp1 = new UFixed8_8((short)0x0100);
        UFixed8_8 vp2 = new UFixed8_8((short)0x0200);
        UFixed8_8 vm1 = new UFixed8_8((short)0xff00);
        UFixed8_8 vm2 = new UFixed8_8((short)0xfe00);
        UFixed8_8 vh1 = new UFixed8_8((short)0x7e80);
        UFixed8_8 vh2 = new UFixed8_8((short)0x7f80);
        UFixed8_8 vh3 = new UFixed8_8((short)0x80c0);
        UFixed8_8 vh4 = new UFixed8_8((short)0x81c0);

        assertEquals(msg1, (short)0x0000, vz0.shortValue());
        assertEquals(msg1, (short)0x0001, vp1.shortValue());
        assertEquals(msg1, (short)0x0002, vp2.shortValue());
        assertEquals(msg1, (short)0x00ff, vm1.shortValue());
        assertEquals(msg1, (short)0x00fe, vm2.shortValue());
        assertEquals(msg1, (short)0x007e, vh1.shortValue());
        assertEquals(msg1, (short)0x007f, vh2.shortValue());
        assertEquals(msg1, (short)0x0080, vh3.shortValue());
        assertEquals(msg1, (short)0x0081, vh4.shortValue());
    }

    @Test
    public final void testUFixed8_8IntValue() {
        String msg1 = "UFixed8_8.intValue() failed.";
        UFixed8_8 vz0 = new UFixed8_8((short)0x0000);
        UFixed8_8 vp1 = new UFixed8_8((short)0x0100);
        UFixed8_8 vp2 = new UFixed8_8((short)0x0200);
        UFixed8_8 vm1 = new UFixed8_8((short)0xff00);
        UFixed8_8 vm2 = new UFixed8_8((short)0xfe00);
        UFixed8_8 vh1 = new UFixed8_8((short)0x7e80);
        UFixed8_8 vh2 = new UFixed8_8((short)0x7f80);
        UFixed8_8 vh3 = new UFixed8_8((short)0x80c0);
        UFixed8_8 vh4 = new UFixed8_8((short)0x81c0);

        assertEquals(msg1, 0x00000000, vz0.intValue());
        assertEquals(msg1, 0x00000001, vp1.intValue());
        assertEquals(msg1, 0x00000002, vp2.intValue());
        assertEquals(msg1, 0x000000ff, vm1.intValue());
        assertEquals(msg1, 0x000000fe, vm2.intValue());
        assertEquals(msg1, 0x0000007e, vh1.intValue());
        assertEquals(msg1, 0x0000007f, vh2.intValue());
        assertEquals(msg1, 0x00000080, vh3.intValue());
        assertEquals(msg1, 0x00000081, vh4.intValue());
    }

    @Test
    public final void testUFixed8_8LongValue() {
        String msg1 = "UFixed8_8.longValue() failed.";
        UFixed8_8 vz0 = new UFixed8_8((short)0x0000);
        UFixed8_8 vp1 = new UFixed8_8((short)0x0100);
        UFixed8_8 vp2 = new UFixed8_8((short)0x0200);
        UFixed8_8 vm1 = new UFixed8_8((short)0xff00);
        UFixed8_8 vm2 = new UFixed8_8((short)0xfe00);
        UFixed8_8 vh1 = new UFixed8_8((short)0x7e80);
        UFixed8_8 vh2 = new UFixed8_8((short)0x7f80);
        UFixed8_8 vh3 = new UFixed8_8((short)0x80c0);
        UFixed8_8 vh4 = new UFixed8_8((short)0x81c0);

        assertEquals(msg1, 0x0000000000000000L, vz0.longValue());
        assertEquals(msg1, 0x0000000000000001L, vp1.longValue());
        assertEquals(msg1, 0x0000000000000002L, vp2.longValue());
        assertEquals(msg1, 0x00000000000000ffL, vm1.longValue());
        assertEquals(msg1, 0x00000000000000feL, vm2.longValue());
        assertEquals(msg1, 0x000000000000007eL, vh1.longValue());
        assertEquals(msg1, 0x000000000000007fL, vh2.longValue());
        assertEquals(msg1, 0x0000000000000080L, vh3.longValue());
        assertEquals(msg1, 0x0000000000000081L, vh4.longValue());
    }

    @Test
    public final void testUFixed8_8FloatValue() {
        String msg1 = "UFixed8_8.floatValue() failed.";
        UFixed8_8 vz0 = new UFixed8_8((short)0x0000);
        UFixed8_8 vp1 = new UFixed8_8((short)0x0100);
        UFixed8_8 vp2 = new UFixed8_8((short)0x0200);
        UFixed8_8 vm1 = new UFixed8_8((short)0xff00);
        UFixed8_8 vm2 = new UFixed8_8((short)0xfe00);
        UFixed8_8 vh1 = new UFixed8_8((short)0x7e80);
        UFixed8_8 vh2 = new UFixed8_8((short)0x7f80);
        UFixed8_8 vh3 = new UFixed8_8((short)0x80c0);
        UFixed8_8 vh4 = new UFixed8_8((short)0x81c0);

        assertTrue(msg1,       0.0F == vz0.floatValue());
        assertTrue(msg1,       1.0F == vp1.floatValue());
        assertTrue(msg1,       2.0F == vp2.floatValue());
        assertTrue(msg1,     255.0F == vm1.floatValue());
        assertTrue(msg1,     254.0F == vm2.floatValue());
        assertTrue(msg1,     126.5F == vh1.floatValue());
        assertTrue(msg1,     127.5F == vh2.floatValue());
        assertTrue(msg1,     128.75F == vh3.floatValue());
        assertTrue(msg1,     129.75F == vh4.floatValue());
    }

    @Test
    public final void testUFixed8_8DoubleValue() {
        String msg1 = "UFixed8_8.doubleValue() failed.";
        UFixed8_8 vz0 = new UFixed8_8((short)0x0000);
        UFixed8_8 vp1 = new UFixed8_8((short)0x0100);
        UFixed8_8 vp2 = new UFixed8_8((short)0x0200);
        UFixed8_8 vm1 = new UFixed8_8((short)0xff00);
        UFixed8_8 vm2 = new UFixed8_8((short)0xfe00);
        UFixed8_8 vh1 = new UFixed8_8((short)0x7e80);
        UFixed8_8 vh2 = new UFixed8_8((short)0x7f80);
        UFixed8_8 vh3 = new UFixed8_8((short)0x80c0);
        UFixed8_8 vh4 = new UFixed8_8((short)0x81c0);

        assertTrue(msg1,       0.0D == vz0.doubleValue());
        assertTrue(msg1,       1.0D == vp1.doubleValue());
        assertTrue(msg1,       2.0D == vp2.doubleValue());
        assertTrue(msg1,     255.0D == vm1.doubleValue());
        assertTrue(msg1,     254.0D == vm2.doubleValue());
        assertTrue(msg1,     126.5D == vh1.doubleValue());
        assertTrue(msg1,     127.5D == vh2.doubleValue());
        assertTrue(msg1,     128.75D == vh3.doubleValue());
        assertTrue(msg1,     129.75D == vh4.doubleValue());
    }

    @Test
    public final void testUFixed8_8ToString() {
        String msg1 = "UFixed8_8.toString() failed.";
        UFixed8_8 vz0 = new UFixed8_8((short)0x0000);
        UFixed8_8 vp1 = new UFixed8_8((short)0x0100);
        UFixed8_8 vp2 = new UFixed8_8((short)0x0200);
        UFixed8_8 vm1 = new UFixed8_8((short)0xff00);
        UFixed8_8 vm2 = new UFixed8_8((short)0xfe00);
        UFixed8_8 vh1 = new UFixed8_8((short)0x7e80);
        UFixed8_8 vh2 = new UFixed8_8((short)0x7f80);
        UFixed8_8 vh3 = new UFixed8_8((short)0x80c0);
        UFixed8_8 vh4 = new UFixed8_8((short)0x81c0);

        assertEquals(msg1,       "0.0", vz0.toString());
        assertEquals(msg1,       "1.0", vp1.toString());
        assertEquals(msg1,       "2.0", vp2.toString());
        assertEquals(msg1,     "255.0", vm1.toString());
        assertEquals(msg1,     "254.0", vm2.toString());
        assertEquals(msg1,     "126.5", vh1.toString());
        assertEquals(msg1,     "127.5", vh2.toString());
        assertEquals(msg1,     "128.75", vh3.toString());
        assertEquals(msg1,     "129.75", vh4.toString());
    }
}
