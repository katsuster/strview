package net.katsuster.strview.test.io;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import net.katsuster.strview.io.*;

/**
 * @author katsuhiro
 */
public class MemoryByteListTest {
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

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#MemoryByteList()}.
     */
    @Test
    public final void testMemoryByteList() {
        String msg1 = "MemoryByteList() failed.";
        //String msg2 = "MemoryByteList() illegal arguments check failed.";

        MemoryByteList a1 = new MemoryByteList();
        assertNotNull(msg1, a1);
    }

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#clone()}.
     */
    @Test
    public final void testClone() {
        String msg1 = "clone() failed.";
        //String msg2 = "clone() illegal arguments check failed.";

        byte[] a_a = {
                (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03,
        };
        MemoryByteList a1 = new MemoryByteList(a_a);
        assertNotNull(msg1, a1);

        a1.set(0, (byte)30);
        assertEquals(msg1, (byte)30, (byte)a1.get(0L));

        try {
            MemoryByteList a2 = a1.clone();
            assertNotNull(msg1, a2);

            a2.set(0, (byte)40);
            assertEquals(msg1, (byte)30, (byte)a1.get(0L));
            assertEquals(msg1, (byte)40, (byte)a2.get(0L));
        } catch (CloneNotSupportedException ex) {
            fail(msg1);
        }
    }

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#MemoryByteList(long)}.
     */
    @Test
    public final void testMemoryByteListLong() {
        String msg1 = "MemoryByteList(long) failed.";
        String msg2 = "MemoryByteList(long) illegal arguments check failed.";

        MemoryByteList a1 = new MemoryByteList(100);
        assertNotNull(msg1, a1);

        try {
            new MemoryByteList(-1);
            fail(msg2);
        } catch (NegativeArraySizeException ex) {
            //OK
        }
        try {
            new MemoryByteList((long)Integer.MAX_VALUE + 1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#MemoryByteList(byte[])}.
     */
    @Test
    public final void testMemoryByteListByteArray() {
        String msg1 = "MemoryByteList(byte[]) failed.";
        String msg2 = "MemoryByteList(byte[]) illegal arguments check failed.";

        byte[] a_a = new byte[512];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (byte)i;
        }

        MemoryByteList a1 = new MemoryByteList(a_a);
        assertNotNull(msg1, a1);

        LargeByteListTest.testGetLongInner(a1, 0, a_a);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a1, 0, a_a, 6);

        try {
            new MemoryByteList(null);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#length()}.
     */
    @Test
    public final void testLength() {
        String msg1 = "length() failed.";
        //String msg2 = "length() illegal arguments check failed.";

        byte[] a_a = new byte[1];
        byte[] a_b = new byte[512];

        MemoryByteList a1 = new MemoryByteList();
        MemoryByteList a2 = new MemoryByteList(a_a);
        MemoryByteList a3 = new MemoryByteList(a_b);

        assertEquals(msg1, 0, a1.length());
        assertEquals(msg1, 1, a2.length());
        assertEquals(msg1, 512, a3.length());
    }

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#get(long)}.
     */
    @Test
    public final void testGetLong() {
        //String msg1 = "get(long) failed.";
        String msg2 = "get(long) illegal arguments check failed.";

        byte[] a_a = new byte[1024];
        byte[] a_b = new byte[2048];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (byte)i;
        }
        for (int i = 0; i < a_b.length; i++) {
            a_b[i] = (byte)(a_b.length - i);
        }

        MemoryByteList a1 = new MemoryByteList(a_a);
        MemoryByteList a2 = new MemoryByteList(a_b);

        LargeByteListTest.testGetLongInner(a1, 0, a_a);
        LargeByteListTest.testGetLongInner(a2, 0, a_b);

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

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#get(long, byte[], int, int)}.
     */
    @Test
    public final void testGetLongByteArrayIntInt() {
        //String msg1 = "get(long, byte[], int, int) failed.";
        //String msg2 = "get(long, byte[], int, int) illegal arguments check failed.";

        byte[] a_a = new byte[2048];
        byte[] a_b = new byte[4096];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (byte)(a_a.length - i);
        }
        for (int i = 0; i < a_b.length; i++) {
            a_b[i] = (byte)(i);
        }

        MemoryByteList a1 = new MemoryByteList(a_a);
        MemoryByteList a2 = new MemoryByteList(a_b);

        LargeByteListTest.testGetLongByteArrayIntIntInner(a1, 0, a_a, 1);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a1, 0, a_a, 30);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a2, 0, a_b, 4);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a2, 0, a_b, 100);
    }

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#set(long, Byte)}.
     */
    @Test
    public final void testSetLongByte() {
        //String msg1 = "set(long, byte) failed.";
        String msg2 = "set(long, byte) illegal arguments check failed.";

        byte[] a_a = new byte[1024];
        byte[] a_b = new byte[1024];
        byte[] b_a = new byte[4096];
        byte[] b_b = new byte[4096];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (byte)(a_a.length - i);
        }
        for (int i = 0; i < b_a.length; i++) {
            b_a[i] = (byte)(i);
        }

        MemoryByteList a1 = new MemoryByteList(a_b);
        MemoryByteList a2 = new MemoryByteList(b_b);
        for (int i = 0; i < a1.length(); i++) {
            a1.set(i, (byte)(a1.length() - i));
        }
        for (int i = 0; i < a2.length(); i++) {
            a2.set(i, (byte)(i));
        }

        LargeByteListTest.testGetLongInner(a1, 0, a_a);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a1, 0, a_a, 30);
        LargeByteListTest.testGetLongInner(a2, 0, b_a);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a2, 0, b_a, 100);

        try {
            a1.set(a_b.length, (byte)0);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            a1.set(-1, (byte)0);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#set(long, byte[], int, int)}.
     */
    @Test
    public final void testSetLongByteArrayIntInt() {
        //String msg1 = "set(long, byte[], int, int) failed.";
        //String msg2 = "set(long, byte[], int, int) illegal arguments check failed.";

        byte[] a_a = new byte[1024];
        byte[] a_b = new byte[1024];
        byte[] b_a = new byte[1024];
        byte[] b_b = new byte[1024];
        byte[] buf_a = new byte[5];
        byte[] buf_b = new byte[5];
        for (int i = 0; i < a_a.length - buf_a.length; i += buf_a.length) {
            for (int j = i; j < buf_a.length; j++) {
                buf_a[i] = (byte)(a_a.length - j * 3);
                buf_b[i] = (byte)(j * 3);
            }
            System.arraycopy(buf_a, 0, a_a, i, buf_a.length);
            System.arraycopy(buf_b, 0, b_a, i, buf_b.length);
        }

        MemoryByteList a1 = new MemoryByteList(a_b);
        MemoryByteList a2 = new MemoryByteList(b_b);
        for (int i = 0; i < a_a.length - buf_a.length; i += buf_a.length) {
            for (int j = i; j < buf_a.length; j++) {
                buf_a[i] = (byte)(a_a.length - j * 3);
                buf_b[i] = (byte)(j * 3);
            }
            a1.set(i, buf_a, 0, buf_a.length);
            a2.set(i, buf_b, 0, buf_b.length);
        }

        LargeByteListTest.testGetLongInner(a1, 0, a_a);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a1, 0, a_a, 30);
        LargeByteListTest.testGetLongInner(a2, 0, b_a);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a2, 0, b_a, 100);
    }

    /**
     * Test method for {@link net.katsuster.strview.io.MemoryByteList#set(long, byte[], int, int)}.
     */
    @Test
    public final void testBench() {
        Random ra = new Random();
        byte[] dest = new byte[64 * 1024 * 1024];
        byte a_a;

        MemoryByteList a1 = new MemoryByteList(dest);

        long start, elapse;
        int ite = 1, i;

        System.out.println("MemoryByteListTest.testBench()");

        //seq 1B
        elapse = 0;
        for (i = 0; i < ite; i++) {
            a_a = (byte)ra.nextInt(255);

            start = System.nanoTime();
            for (int j = 0; j < a1.length(); j++) {
                a1.set(j, a_a);
            }
            elapse += (System.nanoTime() - start);
        }
        LargeByteListTest.printBenchResult("seq write 1B",
                i * a1.length(), elapse);

        LargeByteListTest.benchIO(a1, "seq access 4B", 4, 2);
        LargeByteListTest.benchIO(a1, "seq access 512B", 512, 8);
        LargeByteListTest.benchIO(a1, "seq access 4KB", 4 * 1024, 8);
        LargeByteListTest.benchIO(a1, "seq access 64KB", 64 * 1024, 8);
    }
}
