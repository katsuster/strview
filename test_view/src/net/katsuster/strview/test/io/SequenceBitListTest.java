package net.katsuster.strview.test.io;

import net.katsuster.strview.io.SequenceBitList;
import net.katsuster.strview.util.SimpleRange;
import net.katsuster.strview.io.MemoryBitList;
import net.katsuster.strview.test.util.LargeBitListTest;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author katsuhiro
 */
public class SequenceBitListTest {
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
    public final void testSequenceBitList() {
        String msg1 = "SequenceBitList() failed.";
        //String msg2 = "SequenceBitList() illegal arguments check failed.";

        SequenceBitList a1 = new SequenceBitList();
        assertNotNull(msg1, a1);
    }

    @Test
    public final void testAddRange() {
        String msg1 = "add(Range) failed.";
        String msg2 = "add(Range) illegal arguments check failed.";

        SequenceBitList a1 = new SequenceBitList();
        MemoryBitList m1 = new MemoryBitList(30);
        MemoryBitList m2 = new MemoryBitList(60);
        MemoryBitList m3 = new MemoryBitList(90);

        a1.add(new SimpleRange(m1, 0, 30));
        a1.add(new SimpleRange(m2, 0, 60));
        a1.add(new SimpleRange(m3, 0, 90));
        assertEquals(msg1, 180, a1.length());

        try {
            SequenceBitList a2 = new SequenceBitList();
            a2.add(null);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public final void testGetLong() {
        //String msg1 = "get(long) failed.";
        String msg2 = "get(long) illegal arguments check failed.";

        boolean[] a1_a = new boolean[1023];
        boolean[] a1_1_a = new boolean[320];
        boolean[] a1_2_a = new boolean[621];
        boolean[] a1_3_a = new boolean[82];
        boolean[] a2_a = new boolean[2048];
        boolean[] a2_1_a = new boolean[641];
        boolean[] a2_2_a = new boolean[490];
        boolean[] a2_3_a = new boolean[690];
        boolean[] a2_4_a = new boolean[317];
        for (int i = 0; i < a1_a.length; i++) {
            a1_a[i] = (i % 17 == 0);
        }
        {
            int c = 17, i = 0, s;
            for (s = i; (i - s) < a1_1_a.length; i++) {
                a1_1_a[i - s] = (i % c == 0);
            }
            for (s = i; (i - s) < a1_2_a.length; i++) {
                a1_2_a[i - s] = (i % c == 0);
            }
            for (s = i; (i - s) < a1_3_a.length; i++) {
                a1_3_a[i - s] = (i % c == 0);
            }
        }
        for (int i = 0; i < a2_a.length; i++) {
            a2_a[i] = (i % 19 == 0);
        }
        {
            int c = 19, i = 0, s;
            for (s = i; (i - s) < a2_1_a.length; i++) {
                a2_1_a[i - s] = (i % c == 0);
            }
            for (s = i; (i - s) < a2_2_a.length; i++) {
                a2_2_a[i - s] = (i % c == 0);
            }
            for (s = i; (i - s) < a2_3_a.length; i++) {
                a2_3_a[i - s] = (i % c == 0);
            }
            for (s = i; (i - s) < a2_4_a.length; i++) {
                a2_4_a[i - s] = (i % c == 0);
            }
        }

        MemoryBitList a1_1 = new MemoryBitList(a1_1_a);
        MemoryBitList a1_2 = new MemoryBitList(a1_2_a);
        MemoryBitList a1_3 = new MemoryBitList(a1_3_a);
        SequenceBitList a1 = new SequenceBitList();
        a1.add(new SimpleRange(a1_1, 0, a1_1.length()));
        a1.add(new SimpleRange(a1_2, 0, a1_2.length()));
        a1.add(new SimpleRange(a1_3, 0, a1_3.length()));

        MemoryBitList a2_1 = new MemoryBitList(a2_1_a);
        MemoryBitList a2_2 = new MemoryBitList(a2_2_a);
        MemoryBitList a2_3 = new MemoryBitList(a2_3_a);
        MemoryBitList a2_4 = new MemoryBitList(a2_4_a);
        SequenceBitList a2 = new SequenceBitList();
        a2.add(new SimpleRange(a2_1, 0, a2_1.length()));
        a2.add(new SimpleRange(a2_2, 0, a2_2.length()));
        a2.add(new SimpleRange(a2_3, 0, a2_3.length()));
        a2.add(new SimpleRange(a2_4, 0, a2_4.length()));

        LargeBitListTest.testGetLongInner(a1, 0, a1_a);
        LargeBitListTest.testGetLongInner(a2, 0, a2_a);

        try {
            a1.get(a1_a.length);
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
}
