package net.katsuster.strview.test.io;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import net.katsuster.strview.io.*;
import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.test.util.*;

/**
 * @author katsuhiro
 */
public class ByteToBitListTest {
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

    private boolean[] byteArrayToBooleanArray(byte[] a) {
        boolean[] b = new boolean[a.length * 8];
        int i;
        byte t;
        int s;

        for (i = 0; i < b.length; i++) {
            t = a[i/8];
            s = 7 - (i % 8);
            b[i] = (((t >>> s) & 0x01) == 1);
        }

        return b;
    }

    @Test
    public final void testNewWrappedArray() {
        String msg1 = "ByteToBitList() failed.";
        //String msg2 = "ByteToBitList() illegal arguments check failed.";

        ByteToBitList a1 = new ByteToBitList();
        assertNotNull(msg1, a1);
    }

    @Test
    public final void testClone() {
        String msg1 = "clone() failed.";
        //String msg2 = "clone() illegal arguments check failed.";

        byte[] a_a = {
                0x30,
        };
        ByteToBitList a1 = new ByteToBitList(new MemoryByteList(a_a));
        assertNotNull(msg1, a1);

        a1.set(0, true);
        assertEquals(msg1, true, a1.get(0));

        try {
            ByteToBitList a2 = (ByteToBitList)a1.clone();
            assertNotNull(msg1, a2);
            assertEquals(msg1, a1.length(), a2.length());

            a2.set(0, false);
            assertEquals(msg1, true, a1.get(0));
            assertEquals(msg1, false, a2.get(0));

            a2.length(a2.length() - 1);
            assertNotEquals(msg1, a1.length(), a2.length());
        } catch (CloneNotSupportedException ex) {
            fail(msg1);
        }
    }

    @Test
    public final void testNewWrappedArrayLargeArray() {
        String msg1 = "ByteToBitList(LargeArray) failed.";
        String msg2 = "ByteToBitList(LargeArray) illegal arguments check failed.";

        byte[] ba_a = new byte[64];
        boolean[] a_a;
        for (int i = 0; i < ba_a.length; i++) {
            ba_a[i] = (byte)(i % 5);
        }
        a_a = byteArrayToBooleanArray(ba_a);

        ByteToBitList a1 = new ByteToBitList(new MemoryByteList(ba_a));
        assertNotNull(msg1, a1);

        LargeBitListTest.testGetLongInner(a1, 0, a_a);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a1, 0, a_a, 6);

        try {
            new ByteToBitList(null);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public final void testLength() {
        String msg1 = "length() failed.";
        //String msg2 = "length() illegal arguments check failed.";

        byte[] a_a = new byte[1];
        byte[] a_b = new byte[64];

        ByteToBitList a1 = new ByteToBitList();
        ByteToBitList a2 = new ByteToBitList(new MemoryByteList(a_a));
        ByteToBitList a3 = new ByteToBitList(new MemoryByteList(a_b));

        assertEquals(msg1, 0, a1.length());
        assertEquals(msg1, 8, a2.length());
        assertEquals(msg1, 512, a3.length());
    }

    @Test
    public final void testGetLong() {
        //String msg1 = "get(long) failed.";
        String msg2 = "get(long) illegal arguments check failed.";

        byte[] ba_a = new byte[254];
        byte[] ba_b = new byte[512];
        boolean[] a_a;
        boolean[] a_b;
        for (int i = 0; i < ba_a.length; i++) {
            ba_a[i] = (byte)(i % 6);
        }
        for (int i = 0; i < ba_b.length; i++) {
            ba_b[i] = (byte)(i % 7);
        }
        a_a = byteArrayToBooleanArray(ba_a);
        a_b = byteArrayToBooleanArray(ba_b);

        ByteToBitList a1 = new ByteToBitList(new MemoryByteList(ba_a));
        ByteToBitList a2 = new ByteToBitList(new MemoryByteList(ba_b));

        LargeBitListTest.testGetLongInner(a1, 0, a_a);
        LargeBitListTest.testGetLongInner(a2, 0, a_b);

        try {
            a1.get(a_a.length);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            a1.get(-1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testGetLongByteArrayIntInt() {
        //String msg1 = "get(long, boolean[], int, int) failed.";
        //String msg2 = "get(long, boolean[], int, int) illegal arguments check failed.";

        byte[] ba_a = new byte[256];
        byte[] ba_b = new byte[512];
        boolean[] a_a;
        boolean[] a_b;
        for (int i = 0; i < ba_a.length; i++) {
            ba_a[i] = (byte)(i % 8);
        }
        for (int i = 0; i < ba_b.length; i++) {
            ba_b[i] = (byte)(i % 9);
        }
        a_a = byteArrayToBooleanArray(ba_a);
        a_b = byteArrayToBooleanArray(ba_b);

        ByteToBitList a1 = new ByteToBitList(new MemoryByteList(ba_a));
        ByteToBitList a2 = new ByteToBitList(new MemoryByteList(ba_b));

        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a1, 0, a_a, 1);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a1, 0, a_a, 30);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a2, 0, a_b, 4);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a2, 0, a_b, 100);
    }

    @Test
    public final void testSetLongByte() {
        //String msg1 = "set(long, byte) failed.";
        String msg2 = "set(long, byte) illegal arguments check failed.";
        boolean b;
        int s;

        byte[] ba_a = new byte[254];
        byte[] ba_b = new byte[254];
        byte[] bb_a = new byte[512];
        byte[] bb_b = new byte[512];
        boolean[] a_a;
        boolean[] a_b;
        boolean[] b_a;
        //boolean[] b_b;
        for (int i = 0; i < ba_a.length * 8; i++) {
            b = (i % 10 == 0);
            if (b) {
                s = 7 - (i % 8);
                ba_a[i / 8] |= (byte)(1 << s);
            }
        }
        for (int i = 0; i < bb_a.length * 8; i++) {
            b = (i % 11 == 0);
            if (b) {
                s = 7 - (i % 8);
                bb_a[i / 8] |= (byte)(1 << s);
            }
        }
        a_a = byteArrayToBooleanArray(ba_a);
        a_b = byteArrayToBooleanArray(ba_b);
        b_a = byteArrayToBooleanArray(bb_a);
        //b_b = byteArrayToBooleanArray(bb_b);

        ByteToBitList a1 = new ByteToBitList(new MemoryByteList(ba_b));
        ByteToBitList a2 = new ByteToBitList(new MemoryByteList(bb_b));
        for (int i = 0; i < a1.length(); i++) {
            a1.set(i, (i % 10 == 0));
        }
        for (int i = 0; i < a2.length(); i++) {
            a2.set(i, (i % 11 == 0));
        }

        LargeBitListTest.testGetLongInner(a1, 0, a_a);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a1, 0, a_a, 30);
        LargeBitListTest.testGetLongInner(a2, 0, b_a);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a2, 0, b_a, 100);

        try {
            a1.set(a_b.length, false);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            a1.set(-1, false);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testSetLongByteArrayIntInt() {
        //String msg1 = "set(long, boolean[], int, int) failed.";
        //String msg2 = "set(long, boolean[], int, int) illegal arguments check failed.";
        int s;

        byte[] ba_a = new byte[1024];
        byte[] ba_b = new byte[1024];
        byte[] bb_a = new byte[1024];
        byte[] bb_b = new byte[1024];
        boolean[] buf_a = new boolean[5];
        boolean[] buf_b = new boolean[5];
        boolean[] a_a;
        //boolean[] a_b;
        boolean[] b_a;
        //boolean[] b_b;
        for (int i = 0; i < ba_a.length * 8 - buf_a.length; i += buf_a.length) {
            for (int j = 0; j < buf_a.length; j++) {
                s = 7 - ((i + j) % 8);

                buf_a[j] = ((i + j) % 3 == 0);
                if (buf_a[j]) {
                    ba_a[(i + j) / 8] |= (byte)(1 << s);
                }
                buf_b[j] = ((i + j) % 4 == 0);
                if (buf_b[j]) {
                    bb_a[(i + j) / 8] |= (byte)(1 << s);
                }
            }
        }
        a_a = byteArrayToBooleanArray(ba_a);
        //a_b = byteArrayToBooleanArray(ba_b);
        b_a = byteArrayToBooleanArray(bb_a);
        //b_b = byteArrayToBooleanArray(bb_b);

        ByteToBitList a1 = new ByteToBitList(new MemoryByteList(ba_b));
        ByteToBitList a2 = new ByteToBitList(new MemoryByteList(bb_b));
        for (int i = 0; i < a_a.length - buf_a.length; i += buf_a.length) {
            for (int j = 0; j < buf_a.length; j++) {
                buf_a[j] = ((i + j) % 3 == 0);
                buf_b[j] = ((i + j) % 4 == 0);
            }
            a1.set(i, buf_a, 0, buf_a.length);
            a2.set(i, buf_b, 0, buf_b.length);
        }

        LargeBitListTest.testGetLongInner(a1, 0, a_a);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a1, 0, a_a, 30);
        LargeBitListTest.testGetLongInner(a2, 0, b_a);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a2, 0, b_a, 100);
    }

    @Test
    public final void testGetPackedLong() {
        String msg1 = "getPackedLong(long, int) failed.";
        String msg2 = "getPackedLong(long, int) illegal arguments check failed.";

        byte[] buf = new byte[61];
        ByteToBitList a;
        int size, diff, start, off, i;
        long result, act;

        for (i = 0; i < buf.length; i++) {
            buf[i] = (byte)((i % 17) | (i % 3));
        }
        a = new ByteToBitList(new MemoryByteList(buf));

        for (size = 0; size <= 64; size++) {
            for (diff = 1; diff <= 64; diff++) {
                for (start = 0; start <= 64; start++) {
                    for (off = start; off < buf.length - size; off += diff) {
                        boolean[] actbuf = new boolean[size];

                        result = a.getPackedLong(off, size);
                        a.get(off, actbuf, 0, size);
                        act = AbstractLargeBitList.packBitsLong(actbuf);

                        assertEquals(msg1
                                        + "size:" + size
                                        + "diff:" + diff
                                        + "start:" + start
                                        + "off:" + off,
                                act, result);
                    }
                }
            }
        }

        try {
            a.getPackedLong(0, -1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            a.getPackedLong(0, 65);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            a.getPackedLong(-1, 1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            a.getPackedLong(buf.length * 8 - 1, 2);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testBench() {
        Random ra = new Random();
        byte[] dest = new byte[4 * 1024 * 1024];
        boolean a_a;

        ByteToBitList a1 = new ByteToBitList(new MemoryByteList(dest));

        long start, elapse;
        int ite = 1, i;

        System.out.println("ByteToBitListTest.testBench()");

        //seq 1B
        elapse = 0;
        for (i = 0; i < ite; i++) {
            a_a = (ra.nextInt(255) % 2 == 0);

            start = System.nanoTime();
            for (int j = 0; j < a1.length(); j++) {
                a1.set(j, a_a);
            }
            elapse += (System.nanoTime() - start);
        }
        LargeBitListTest.printBenchResult("seq write 1bit",
                i * a1.length(), elapse);

        LargeBitListTest.benchPackedIO(a1, "seq packed 4bit", 4, 1);
        LargeBitListTest.benchPackedIO(a1, "seq packed 8bit", 8, 1);
        LargeBitListTest.benchPackedIO(a1, "seq packed 16bit", 16, 1);
        LargeBitListTest.benchPackedIO(a1, "seq packed 32bit", 32, 1);
        LargeBitListTest.benchPackedIO(a1, "seq packed 64bit", 64, 1);

        LargeBitListTest.benchIO(a1, "seq access 4bit", 4, 1);
        LargeBitListTest.benchIO(a1, "seq access 16bit", 16, 1);
        LargeBitListTest.benchIO(a1, "seq access 4Kbit", 4 * 1024, 1);
        LargeBitListTest.benchIO(a1, "seq access 64Kbit", 64 * 1024, 1);
    }
}
