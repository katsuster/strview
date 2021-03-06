package net.katsuster.strview.test.io;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import net.katsuster.strview.io.*;
import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;
import net.katsuster.strview.test.util.*;

public class MemoryBitListTest {
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
    public final void testMemoryBitList() {
        String msg1 = "MemoryBitList() failed.";
        //String msg2 = "MemoryBitList() illegal arguments check failed.";

        MemoryBitList a1 = new MemoryBitList();
        assertNotNull(msg1, a1);
    }

    @Test
    public final void testMemoryBitListLong() {
        String msg1 = "MemoryBitList(long) failed.";
        String msg2 = "MemoryBitList(long) illegal arguments check failed.";

        MemoryBitList a1 = new MemoryBitList(100);
        assertNotNull(msg1, a1);

        MemoryBitList a2 = new MemoryBitList(LargeList.LENGTH_UNKNOWN);
        assertNotNull(msg1, a2);

        try {
            new MemoryBitList(-2);
            fail(msg2);
        } catch (NegativeArraySizeException ex) {
            //OK
        }

        try {
            new MemoryBitList((long)Integer.MAX_VALUE + 1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public final void testMemoryBitListBooleanArray() {
        String msg1 = "MemoryBitList(boolean[]) failed.";
        String msg2 = "MemoryBitList(boolean[]) illegal arguments check failed.";

        boolean[] a_a = new boolean[512];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (i % 5 == 0);
        }

        MemoryBitList a1 = new MemoryBitList(a_a);
        assertNotNull(msg1, a1);

        LargeBitListTest.testGetLongInner(a1, 0, a_a);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a1, 0, a_a, 6);

        try {
            new MemoryBitList(null);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public final void testClone() {
        String msg1 = "clone() failed.";
        //String msg2 = "clone() illegal arguments check failed.";

        boolean[] a_a = {
                false, false, true, true,
        };
        MemoryBitList a1 = new MemoryBitList(a_a);
        assertNotNull(msg1, a1);

        a1.set(0, true);
        assertEquals(msg1, true, a1.get(0));

        try {
            MemoryBitList a2 = a1.clone();
            assertNotNull(msg1, a2);

            a2.set(0, false);
            assertEquals(msg1, true, a1.get(0));
            assertEquals(msg1, false, a2.get(0));
        } catch (CloneNotSupportedException ex) {
            fail(msg1);
        }
    }

    @Test
    public final void testLength() {
        String msg1 = "length() failed.";
        //String msg2 = "length() illegal arguments check failed.";

        boolean[] a_a = new boolean[1];
        boolean[] a_b = new boolean[512];

        MemoryBitList a1 = new MemoryBitList();
        MemoryBitList a2 = new MemoryBitList(a_a);
        MemoryBitList a3 = new MemoryBitList(a_b);

        assertEquals(msg1, 0, a1.length());
        assertEquals(msg1, 1, a2.length());
        assertEquals(msg1, 512, a3.length());
    }

    @Test
    public final void testGetLong() {
        //String msg1 = "get(long) failed.";
        String msg2 = "get(long) illegal arguments check failed.";

        boolean[] a_a = new boolean[1023];
        boolean[] a_b = new boolean[2048];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (i % 6 == 0);
        }
        for (int i = 0; i < a_b.length; i++) {
            a_b[i] = (i % 7 == 0);
        }

        MemoryBitList a1 = new MemoryBitList(a_a);
        MemoryBitList a2 = new MemoryBitList(a_b);

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
    public final void testGetLongBoolArrayIntInt() {
        //String msg1 = "get(long, boolean[], int, int) failed.";
        //String msg2 = "get(long, boolean[], int, int) illegal arguments check failed.";

        boolean[] a_a = new boolean[2048];
        boolean[] a_b = new boolean[4096];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (i % 8 == 0);
        }
        for (int i = 0; i < a_b.length; i++) {
            a_b[i] = (i % 9 == 0);
        }

        MemoryBitList a1 = new MemoryBitList(a_a);
        MemoryBitList a2 = new MemoryBitList(a_b);

        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a1, 0, a_a, 1);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a1, 0, a_a, 30);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a2, 0, a_b, 4);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a2, 0, a_b, 100);
    }

    @Test
    public final void testSetLongByte() {
        //String msg1 = "set(long, byte) failed.";
        String msg2 = "set(long, byte) illegal arguments check failed.";

        boolean[] a_a = new boolean[1024];
        boolean[] a_b = new boolean[1024];
        boolean[] b_a = new boolean[4096];
        boolean[] b_b = new boolean[4096];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (i % 10 == 0);
        }
        for (int i = 0; i < b_a.length; i++) {
            b_a[i] = (i % 11 == 0);
        }

        MemoryBitList a1 = new MemoryBitList(a_b);
        MemoryBitList a2 = new MemoryBitList(b_b);
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
    public final void testSetLongBoolArrayIntInt() {
        //String msg1 = "set(long, boolean[], int, int) failed.";
        //String msg2 = "set(long, boolean[], int, int) illegal arguments check failed.";

        boolean[] a_a = new boolean[1024];
        boolean[] a_b = new boolean[1024];
        boolean[] b_a = new boolean[1024];
        boolean[] b_b = new boolean[1024];
        boolean[] buf_a = new boolean[5];
        boolean[] buf_b = new boolean[5];
        for (int i = 0; i < a_a.length - buf_a.length; i += buf_a.length) {
            for (int j = 0; j < buf_a.length; j++) {
                buf_a[j] = ((i + j) % 3 == 0);
                buf_b[j] = ((i + j) % 4 == 0);
            }
            System.arraycopy(buf_a, 0, a_a, i, buf_a.length);
            System.arraycopy(buf_b, 0, b_a, i, buf_b.length);
        }

        MemoryBitList a1 = new MemoryBitList(a_b);
        MemoryBitList a2 = new MemoryBitList(b_b);
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

        boolean[] buf = new boolean[500];
        MemoryBitList a;
        int size, diff, start, off, i;
        long result, act;

        for (i = 0; i < buf.length; i++) {
            buf[i] = (i % 17 == 0) || (i % 3 == 0);
        }
        a = new MemoryBitList(buf);

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
            a.getPackedLong(buf.length - 1, 2);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testBench() {
        Random ra = new Random();
        boolean[] dest = new boolean[32 * 1024 * 1024];
        boolean a_a;

        MemoryBitList a1 = new MemoryBitList(dest);

        long start, elapse;
        int ite = 1, i;

        System.out.println("MemoryBitListTest.testBench()");

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
