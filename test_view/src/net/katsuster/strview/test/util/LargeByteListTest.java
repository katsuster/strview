package net.katsuster.strview.test.util;

import static org.junit.Assert.*;

import java.util.*;

import net.katsuster.strview.util.*;

public class LargeByteListTest {
    public static void testGetLongInner(LargeList<Byte> a,
                                              long offset, byte[] correct) {
        byte buf;
        int i;

        for (i = 0; i < correct.length; i++) {
            buf = (byte)a.get(i + offset);

            assertEquals("get(long) is failure at " + (i + offset) + ".",
                    correct[i], buf);
        }
    }

    public static void testGetLongByteArrayIntIntInner(LargeByteList a,
                                                             long offset, byte[] correct, int unit) {
        byte[] buf = new byte[unit];
        int nread;
        int i, j;

        for (i = 0; i < correct.length - unit; i++) {
            nread = a.get(i, buf, 0, buf.length);

            for (j = 0; j < nread; j++) {
                assertEquals("get(long, array, int, int) is failure at "
                                + (i + j) + " in "
                                + "[" + (i + offset) + ", "
                                + (i + offset + nread - 1) + "].",
                        correct[i + j], buf[j]);
            }
        }
    }

    public static void benchIO(LargeByteList target,
                                     String name, int unit, int iteration) {
        Random ra = new Random();
        byte[] buf_set = new byte[unit];
        byte[] buf_get = new byte[unit];
        long start, elapse_set, elapse_get;
        int i;

        elapse_set = 0;
        elapse_get = 0;
        for (i = 0; i < iteration; i++) {
            //init
            for (int j = 0; j < buf_set.length; j++) {
                buf_set[j] = (byte)ra.nextInt(255);
            }

            //set
            start = System.nanoTime();
            for (int j = 0; j < target.length(); j += buf_set.length) {
                target.set(j, buf_set, 0, buf_set.length);
            }
            elapse_set += (System.nanoTime() - start);

            //get
            start = System.nanoTime();
            for (int j = 0; j < target.length(); j += buf_get.length) {
                target.get(j, buf_get, 0, buf_get.length);
            }
            elapse_get += (System.nanoTime() - start);

            //verify
            for (int j = 0; j < buf_get.length; j++) {
                if (buf_get[j] != buf_set[j]) {
                    assertEquals("failed to verify at " + j,
                            buf_set[j], buf_get[j]);
                }
            }
        }

        LargeByteListTest.printBenchResult(name + "(rd)",
                iteration * target.length(), elapse_get);
        LargeByteListTest.printBenchResult(name + "(wr)",
                iteration * target.length(), elapse_set);
    }

    public static void printBenchResult(String msg, long size, long elapse) {
        System.out.printf("%-24s: %5.3f[MB/s] (%8d[KB], %4d[ms])\n",
                msg,
                (double)size / elapse * 1000000000 / 1024 / 1024,
                size,
                (elapse / 1000000));
    }
}
