package net.katsuster.strview.test.io;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.io.*;
import net.katsuster.strview.test.util.*;

/**
 * @author katsuhiro
 */
public class SlowMemoryBitListTest {
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
    public final void testNewMemoryArray() {
        String msg1 = "SlowMemoryBitList() failed.";
        //String msg2 = "SlowMemoryBitList() illegal arguments check failed.";

        SlowMemoryBitList a1 = new SlowMemoryBitList();
        assertNotNull(msg1, a1);
    }

    @Test
    public final void testClone() {
        String msg1 = "clone() failed.";
        //String msg2 = "clone() illegal arguments check failed.";

        boolean[] a_a = {
                false, false, true, true,
        };
        SlowMemoryBitList a1 = new SlowMemoryBitList(a_a);
        assertNotNull(msg1, a1);

        a1.set(0, true);
        assertEquals(msg1, true, a1.get(0));

        try {
            SlowMemoryBitList a2 = a1.clone();
            assertNotNull(msg1, a2);

            a2.set(0, false);
            assertEquals(msg1, true, a1.get(0));
            assertEquals(msg1, false, a2.get(0));
        } catch (CloneNotSupportedException ex) {
            fail(msg1);
        }
    }

    @Test
    public final void testNewMemoryArrayLong() {
        String msg1 = "SlowMemoryBitList(long) failed.";
        String msg2 = "SlowMemoryBitList(long) illegal arguments check failed.";

        SlowMemoryBitList a1 = new SlowMemoryBitList(100);
        assertNotNull(msg1, a1);

        SlowMemoryBitList a2 = new SlowMemoryBitList(LargeList.LENGTH_UNKNOWN);
        assertNotNull(msg1, a2);

        try {
            new SlowMemoryBitList(-2);
            fail(msg2);
        } catch (NegativeArraySizeException ex) {
            //OK
        }

        try {
            new SlowMemoryBitList((long)Integer.MAX_VALUE * SlowMemoryBitList.ELEM_BITS + 1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public final void testNewMemoryArrayBooleanArray() {
        String msg1 = "SlowMemoryBitList(boolean[]) failed.";
        String msg2 = "SlowMemoryBitList(boolean[]) illegal arguments check failed.";

        boolean[] a_a = new boolean[512];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (i % 5 == 0);
        }

        SlowMemoryBitList a1 = new SlowMemoryBitList(a_a);
        assertNotNull(msg1, a1);

        LargeBitListTest.testGetLongInner(a1, 0, a_a);
        LargeBitListTest.testGetLongBooleanArrayIntIntInner(a1, 0, a_a, 6);

        try {
            new SlowMemoryBitList(null);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public final void testLength() {
        String msg1 = "length() failed.";
        //String msg2 = "length() illegal arguments check failed.";

        boolean[] a_a = new boolean[1];
        boolean[] a_b = new boolean[512];

        SlowMemoryBitList a1 = new SlowMemoryBitList();
        SlowMemoryBitList a2 = new SlowMemoryBitList(a_a);
        SlowMemoryBitList a3 = new SlowMemoryBitList(a_b);

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

        SlowMemoryBitList a1 = new SlowMemoryBitList(a_a);
        SlowMemoryBitList a2 = new SlowMemoryBitList(a_b);

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

        SlowMemoryBitList a1 = new SlowMemoryBitList(a_a);
        SlowMemoryBitList a2 = new SlowMemoryBitList(a_b);

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

        SlowMemoryBitList a1 = new SlowMemoryBitList(a_b);
        SlowMemoryBitList a2 = new SlowMemoryBitList(b_b);
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

        SlowMemoryBitList a1 = new SlowMemoryBitList(a_b);
        SlowMemoryBitList a2 = new SlowMemoryBitList(b_b);
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
    public final void testSubLargeListLongLong() {
        String msg1 = "subLargeList(long, long) failed.";
        String msg2 = "subLargeList(long, long) illegal arguments check failed.";
        int off;

        boolean[] a_a = new boolean[1024];
        boolean[] a_b = new boolean[1024];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (i % 7 == 0);
            a_b[i] = (i % 7 == 0);
        }

        SlowMemoryBitList a1 = new SlowMemoryBitList(a_a);

        off = 0;
        LargeBitList s1 = a1.subLargeList(off, 5);
        assertNotNull(msg1, s1);
        assertEquals(msg1, 5, s1.length());
        for (int i = 0; i < s1.length(); i++) {
            assertEquals(msg1, a_b[i + off], s1.get(i));
        }

        off = 100;
        LargeBitList s2 = a1.subLargeList(off, 755);
        assertNotNull(msg1, s2);
        assertEquals(msg1, 655, s2.length());
        for (int i = 0; i < s2.length(); i++) {
            assertEquals(msg1, a_b[i + off], s2.get(i));
        }

        //illegal access
        try {
            s1.get(-1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            s1.get(5);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        //illegal arguments
        try {
            a1.subLargeList(-1, 4);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            a1.subLargeList(4, -1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            a1.subLargeList(5, 4);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public final void testBench() {
        Random ra = new Random();
        boolean[] dest = new boolean[32 * 1024 * 1024];
        boolean a_a;

        SlowMemoryBitList a1 = new SlowMemoryBitList(dest);

        long start, elapse;
        int ite = 1, i;

        System.out.println("SlowMemoryBitListTest.testBench()");

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
