package net.katsuster.strview.test.util;

import static org.junit.Assert.*;

import java.util.*;

import net.katsuster.strview.util.bit.*;

public class LargeBitListTest {
    public static final void testGetLongInner(LargeBitList a,
                                              long offset, boolean[] correct) {
        boolean buf;
        int i;

        for (i = 0; i < correct.length; i++) {
            buf = a.get(i + offset);

            assertEquals("get(long) is failure at " + (i + offset) + ".",
                    correct[i], buf);
        }
    }

    public static final void testGetLongBooleanArrayIntIntInner(LargeBitList a,
                                                                long offset, boolean[] correct, int unit) {
        boolean[] buf = new boolean[unit];
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

    public static final void benchPackedIO(LargeBitList target,
                                           String name, int unit, int iteration) {
        Random ra = new Random();
        boolean[] buf_set = new boolean[unit];
        boolean[] buf_get = new boolean[unit];
        long lset = 0, lget = 0;
        long start, elapse_set, elapse_get;
        int i;

        elapse_set = 0;
        elapse_get = 0;
        for (i = 0; i < iteration; i++) {
            //init
            for (int j = 0; j < buf_set.length; j++) {
                buf_set[j] = (ra.nextInt(255) % 2 == 0);
            }
            lset = AbstractLargeBitList.packBitsLong(buf_set);

            //set
            start = System.nanoTime();
            for (int j = 0; j < target.length(); j += buf_set.length) {
                target.setPackedLong(j, buf_set.length, lset);
            }
            elapse_set += (System.nanoTime() - start);

            //get
            start = System.nanoTime();
            for (int j = 0; j < target.length(); j += buf_get.length) {
                lget = target.getPackedLong(j, buf_get.length);
            }
            elapse_get += (System.nanoTime() - start);

            //verify
            assertEquals("failed to verify",
                    lset, lget);
        }

        LargeBitListTest.printBenchResult(name + "(rd)",
                iteration * target.length(), elapse_get);
        LargeBitListTest.printBenchResult(name + "(wr)",
                iteration * target.length(), elapse_set);
    }

    public static final void benchIO(LargeBitList target,
                                     String name, int unit, int iteration) {
        Random ra = new Random();
        boolean[] buf_set = new boolean[unit];
        boolean[] buf_get = new boolean[unit];
        long start, elapse_set, elapse_get;
        int i;

        elapse_set = 0;
        elapse_get = 0;
        for (i = 0; i < iteration; i++) {
            //init
            for (int j = 0; j < buf_set.length; j++) {
                buf_set[j] = (ra.nextInt(255) % 2 == 0);
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

        LargeBitListTest.printBenchResult(name + "(rd)",
                iteration * target.length(), elapse_get);
        LargeBitListTest.printBenchResult(name + "(wr)",
                iteration * target.length(), elapse_set);
    }

    public static final void printBenchResult(String msg, long size, long elapse) {
        System.out.printf("%-24s: %5.3f[Mbit/s] (%8d[Kbit], %4d[ms])\n",
                msg,
                (double)size / elapse * 1000000000 / 1024 / 1024,
                size,
                (elapse / 1000000));
    }
}
