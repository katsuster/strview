package net.katsuster.strview.test.io;

        import static org.junit.Assert.*;

        import org.junit.After;
        import org.junit.AfterClass;
        import org.junit.Before;
        import org.junit.BeforeClass;
        import org.junit.Test;

        import java.io.*;
        import java.util.*;

        import net.katsuster.strview.io.*;

/**
 * @author katsuhiro
 */
public class FileByteListTest {
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
     * Test method for {@link net.katsuster.strview.io.FileByteList#FileByteList()}.
     */
    @Test
    public final void testFileByteList() {
        String msg1 = "FileByteList() failed.";
        //String msg2 = "FileByteList() illegal arguments check failed.";

        FileByteList a1 = new FileByteList();
        assertNotNull(msg1, a1);
    }

    /**
     * Test method for {@link net.katsuster.strview.io.FileByteList#FileByteList(long)}.
     */
    @Test
    public final void testFileByteListLong() {
        String msg1 = "FileByteList(long) failed.";
        String msg2 = "FileByteList(long) illegal arguments check failed.";

        FileByteList a1 = new FileByteList(100);
        assertNotNull(msg1, a1);

        FileByteList a2 = new FileByteList((long)Integer.MAX_VALUE + 1);
        assertNotNull(msg1, a2);

        try {
            new FileByteList(-1);
            fail(msg2);
        } catch (NegativeArraySizeException ex) {
            //OK
        }
    }

    /**
     * Test method for {@link net.katsuster.strview.io.FileByteList#FileByteList(String)}.
     */
    @Test
    public final void testFileByteListString() {
        String msg1 = "FileByteList(String) failed.";
        String msg2 = "FileByteList(String) illegal arguments check failed.";

        String s_a = "";
        try {
            File f_a = File.createTempFile("test", ".tmp");
            f_a.deleteOnExit();
            s_a = f_a.getAbsolutePath();
        } catch (IOException ex) {
            fail("setup failed.");
        }

        FileByteList a1 = new FileByteList(s_a);
        assertNotNull(msg1, a1);

        try {
            String s_b = null;
            new FileByteList(s_b);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    /**
     * Test method for {@link net.katsuster.strview.io.FileByteList#FileByteList(RandomAccessFile)}.
     */
    @Test
    public final void testFileByteListRandomAccessFile() {
        String msg1 = "FileByteList(RandomAccessFile) failed.";
        String msg2 = "FileByteList(RandomAccessFile) illegal arguments check failed.";

        RandomAccessFile r_a = null;
        try {
            File f_a = File.createTempFile("test", ".tmp");
            f_a.deleteOnExit();
            r_a = new RandomAccessFile(f_a, "rw");
        } catch (IOException ex) {
            fail("setup failed.");
        }

        FileByteList a1 = new FileByteList(r_a);
        assertNotNull(msg1, a1);

        try {
            RandomAccessFile tmp = null;
            new FileByteList(tmp);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    /**
     * Test method for {@link net.katsuster.strview.io.FileByteList#length()}.
     */
    @Test
    public final void testLength() {
        String msg1 = "length() failed.";
        //String msg2 = "length() illegal arguments check failed.";

        byte[] a_a = new byte[1];
        byte[] a_b = new byte[512];

        FileByteList a1 = new FileByteList();
        FileByteList a2 = new FileByteList(TempFile.initTempFile(a_a));
        FileByteList a3 = new FileByteList(TempFile.initTempFile(a_b));

        assertEquals(msg1, 0, a1.length());
        assertEquals(msg1, 1, a2.length());
        assertEquals(msg1, 512, a3.length());
    }

    /**
     * Test method for {@link net.katsuster.strview.io.FileByteList#get(long)}.
     */
    @Test
    public final void testGetLong() {
        //String msg1 = "get(long) failed.";
        //String msg2 = "get(long) illegal arguments check failed.";

        byte[] a_a = new byte[1024];
        byte[] a_b = new byte[2048];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (byte)i;
        }
        for (int i = 0; i < a_b.length; i++) {
            a_b[i] = (byte)(a_b.length - i);
        }

        FileByteList a1 = new FileByteList(TempFile.initTempFile(a_a));
        FileByteList a2 = new FileByteList(TempFile.initTempFile(a_b));

        LargeByteListTest.testGetLongInner(a1, 0, a_a);
        LargeByteListTest.testGetLongInner(a2, 0, a_b);
    }

    /**
     * Test method for {@link net.katsuster.strview.io.FileByteList#get(long, byte[], int, int)}.
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

        FileByteList a1 = new FileByteList(TempFile.initTempFile(a_a));
        FileByteList a2 = new FileByteList(TempFile.initTempFile(a_b));

        LargeByteListTest.testGetLongByteArrayIntIntInner(a1, 0, a_a, 1);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a1, 0, a_a, 30);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a2, 0, a_b, 4);
        LargeByteListTest.testGetLongByteArrayIntIntInner(a2, 0, a_b, 100);
    }

    /**
     *
     */
    @Test
    public final void testSetLongByte() {
        //String msg1 = "set(long, byte) failed.";
        //String msg2 = "set(long, byte) illegal arguments check failed.";

        byte[] a_a = new byte[512];
        byte[] a_b = new byte[512];
        byte[] b_a = new byte[8192];
        byte[] b_b = new byte[8192];
        for (int i = 0; i < a_a.length; i++) {
            a_a[i] = (byte)(a_a.length - i);
        }
        for (int i = 0; i < b_a.length; i++) {
            b_a[i] = (byte)(i);
        }

        FileByteList a1 = new FileByteList(TempFile.initTempFile(a_b));
        FileByteList a2 = new FileByteList(TempFile.initTempFile(b_b));
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
    }

    /**
     * Test method for {@link net.katsuster.strview.io.FileByteList#set(long, byte[], int, int)}.
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

        FileByteList a1 = new FileByteList(TempFile.initTempFile(a_b));
        FileByteList a2 = new FileByteList(TempFile.initTempFile(b_b));
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
     * Test method for {@link net.katsuster.strview.io.FileByteList#set(long, byte[], int, int)}.
     */
    @Test
    public final void testBench() {
        Random ra = new Random();
        byte a_a;

        FileByteList a1 = new FileByteList(64 * 1024 * 1024);

        long start, elapse;
        int ite = 1, i;

        System.out.println("FileByteListTest.testBench()");

        //seq 1B
        elapse = 0;
        for (i = 0; i < ite / 128; i++) {
            a_a = (byte)ra.nextInt(255);

            start = System.nanoTime();
            for (int j = 0; j < a1.length(); j++) {
                a1.set(j, a_a);
            }
            elapse += (System.nanoTime() - start);
        }
        LargeByteListTest.printBenchResult("seq write 1B",
                i * a1.length(), elapse);

        //seq multi-bytes
        LargeByteListTest.benchIO(a1, "seq access 4B", 4, 0);
        LargeByteListTest.benchIO(a1, "seq access 512B", 512, 1);
        LargeByteListTest.benchIO(a1, "seq access 4KB", 4 * 1024, 1);
        LargeByteListTest.benchIO(a1, "seq access 64KB", 64 * 1024, 1);
    }
}
