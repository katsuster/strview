package net.katsuster.strview.test.media.mkv;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import net.katsuster.strview.io.*;
import net.katsuster.strview.media.mkv.*;
import net.katsuster.strview.test.util.*;

/**
 * @author katsuhiro
 */
public class EBMLvintTest {
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
    public void getValue() throws Exception {

    }

    @Test
    public void setValue() throws Exception {

    }

    @Test
    public void getSizeAll() throws Exception {

    }

    @Test
    public void setSizeAll() throws Exception {

    }

    @Test
    public void getSizeContent() throws Exception {

    }

    @Test
    public void setSizeContent() throws Exception {

    }

    @Test
    public void testGetVintTypeInt() throws Exception {
        String msg1 = "getVintType(int) failed.";
        String msg2 = "getVintType(int) illegal arguments check failed.";

        assertEquals(msg1, 0, EBMLvint.getVintType(0x80));
        assertEquals(msg1, 1, EBMLvint.getVintType(0x40));
        assertEquals(msg1, 2, EBMLvint.getVintType(0x20));
        assertEquals(msg1, 3, EBMLvint.getVintType(0x10));
        assertEquals(msg1, 4, EBMLvint.getVintType(0x08));
        assertEquals(msg1, 5, EBMLvint.getVintType(0x04));
        assertEquals(msg1, 6, EBMLvint.getVintType(0x02));
        assertEquals(msg1, 7, EBMLvint.getVintType(0x01));

        try {
            EBMLvint.getVintType(0x00);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void getVintSizeInt() throws Exception {
        String msg1 = "getVintSize(int) failed.";
        String msg2 = "getVintSize(int) illegal arguments check failed.";

        assertEquals(msg1, 8, EBMLvint.getVintSize(0x80));
        assertEquals(msg1, 16, EBMLvint.getVintSize(0x40));
        assertEquals(msg1, 24, EBMLvint.getVintSize(0x20));
        assertEquals(msg1, 32, EBMLvint.getVintSize(0x10));
        assertEquals(msg1, 40, EBMLvint.getVintSize(0x08));
        assertEquals(msg1, 48, EBMLvint.getVintSize(0x04));
        assertEquals(msg1, 56, EBMLvint.getVintSize(0x02));
        assertEquals(msg1, 64, EBMLvint.getVintSize(0x01));

        try {
            EBMLvint.getVintSize(0x00);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void getVintContentSizeInt() throws Exception {
        String msg1 = "getVintContentSize(int) failed.";
        String msg2 = "getVintContentSize(int) illegal arguments check failed.";

        assertEquals(msg1, 7, EBMLvint.getVintContentSize(0x80));
        assertEquals(msg1, 14, EBMLvint.getVintContentSize(0x40));
        assertEquals(msg1, 21, EBMLvint.getVintContentSize(0x20));
        assertEquals(msg1, 28, EBMLvint.getVintContentSize(0x10));
        assertEquals(msg1, 35, EBMLvint.getVintContentSize(0x08));
        assertEquals(msg1, 42, EBMLvint.getVintContentSize(0x04));
        assertEquals(msg1, 49, EBMLvint.getVintContentSize(0x02));
        assertEquals(msg1, 56, EBMLvint.getVintContentSize(0x01));

        try {
            EBMLvint.getVintContentSize(0x00);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testParseVintTypeLong() throws Exception {
        String msg1 = "parseVintType(long) failed.";
        String msg2 = "parseVintType(long) illegal arguments check failed.";

        assertEquals(msg1, 0, EBMLvint.parseVintType(0x0L));
        assertEquals(msg1, 0, EBMLvint.parseVintType(0x7eL));
        assertEquals(msg1, 1, EBMLvint.parseVintType(0x7fL));
        assertEquals(msg1, 1, EBMLvint.parseVintType(0x80L));
        assertEquals(msg1, 1, EBMLvint.parseVintType(0x3ffeL));
        assertEquals(msg1, 2, EBMLvint.parseVintType(0x3fffL));
        assertEquals(msg1, 2, EBMLvint.parseVintType(0x4000L));
        assertEquals(msg1, 2, EBMLvint.parseVintType(0x1ffffeL));
        assertEquals(msg1, 3, EBMLvint.parseVintType(0x1fffffL));
        assertEquals(msg1, 3, EBMLvint.parseVintType(0x200000L));
        assertEquals(msg1, 3, EBMLvint.parseVintType(0xffffffeL));

        assertEquals(msg1, 4, EBMLvint.parseVintType(0xfffffffL));
        assertEquals(msg1, 4, EBMLvint.parseVintType(0x10000000L));
        assertEquals(msg1, 4, EBMLvint.parseVintType(0x7fffffffeL));
        assertEquals(msg1, 5, EBMLvint.parseVintType(0x7ffffffffL));
        assertEquals(msg1, 5, EBMLvint.parseVintType(0x800000000L));
        assertEquals(msg1, 5, EBMLvint.parseVintType(0x3fffffffffeL));
        assertEquals(msg1, 6, EBMLvint.parseVintType(0x3ffffffffffL));
        assertEquals(msg1, 6, EBMLvint.parseVintType(0x40000000000L));
        assertEquals(msg1, 6, EBMLvint.parseVintType(0x1fffffffffffeL));
        assertEquals(msg1, 7, EBMLvint.parseVintType(0x1ffffffffffffL));
        assertEquals(msg1, 7, EBMLvint.parseVintType(0x2000000000000L));
        assertEquals(msg1, 7, EBMLvint.parseVintType(0xfffffffffffffeL));

        try {
            EBMLvint.parseVintType(0xffffffffffffffL);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            EBMLvint.parseVintType(0x100000000000000L);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            EBMLvint.parseVintType(0x8000000000000000L);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testParseVintSizeLong() throws Exception {
        String msg1 = "parseVintSize(long) failed.";
        String msg2 = "parseVintSize(long) illegal arguments check failed.";

        assertEquals(msg1, 8, EBMLvint.parseVintSize(0x0L));
        assertEquals(msg1, 8, EBMLvint.parseVintSize(0x7eL));
        assertEquals(msg1, 16, EBMLvint.parseVintSize(0x7fL));
        assertEquals(msg1, 16, EBMLvint.parseVintSize(0x80L));
        assertEquals(msg1, 16, EBMLvint.parseVintSize(0x3ffeL));
        assertEquals(msg1, 24, EBMLvint.parseVintSize(0x3fffL));
        assertEquals(msg1, 24, EBMLvint.parseVintSize(0x4000L));
        assertEquals(msg1, 24, EBMLvint.parseVintSize(0x1ffffeL));
        assertEquals(msg1, 32, EBMLvint.parseVintSize(0x1fffffL));
        assertEquals(msg1, 32, EBMLvint.parseVintSize(0x200000L));
        assertEquals(msg1, 32, EBMLvint.parseVintSize(0xffffffeL));

        assertEquals(msg1, 40, EBMLvint.parseVintSize(0xfffffffL));
        assertEquals(msg1, 40, EBMLvint.parseVintSize(0x10000000L));
        assertEquals(msg1, 40, EBMLvint.parseVintSize(0x7fffffffeL));
        assertEquals(msg1, 48, EBMLvint.parseVintSize(0x7ffffffffL));
        assertEquals(msg1, 48, EBMLvint.parseVintSize(0x800000000L));
        assertEquals(msg1, 48, EBMLvint.parseVintSize(0x3fffffffffeL));
        assertEquals(msg1, 56, EBMLvint.parseVintSize(0x3ffffffffffL));
        assertEquals(msg1, 56, EBMLvint.parseVintSize(0x40000000000L));
        assertEquals(msg1, 56, EBMLvint.parseVintSize(0x1fffffffffffeL));
        assertEquals(msg1, 64, EBMLvint.parseVintSize(0x1ffffffffffffL));
        assertEquals(msg1, 64, EBMLvint.parseVintSize(0x2000000000000L));
        assertEquals(msg1, 64, EBMLvint.parseVintSize(0xfffffffffffffeL));

        try {
            EBMLvint.parseVintSize(0xffffffffffffffL);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            EBMLvint.parseVintSize(0x100000000000000L);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            EBMLvint.parseVintSize(0x8000000000000000L);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public void testParseVintContentSizeLong() throws Exception {
        String msg1 = "parseVintContentSize(long) failed.";
        String msg2 = "parseVintContentSize(long) illegal arguments check failed.";

        assertEquals(msg1, 7, EBMLvint.parseVintContentSize(0x0L));
        assertEquals(msg1, 7, EBMLvint.parseVintContentSize(0x7eL));
        assertEquals(msg1, 14, EBMLvint.parseVintContentSize(0x7fL));
        assertEquals(msg1, 14, EBMLvint.parseVintContentSize(0x80L));
        assertEquals(msg1, 14, EBMLvint.parseVintContentSize(0x3ffeL));
        assertEquals(msg1, 21, EBMLvint.parseVintContentSize(0x3fffL));
        assertEquals(msg1, 21, EBMLvint.parseVintContentSize(0x4000L));
        assertEquals(msg1, 21, EBMLvint.parseVintContentSize(0x1ffffeL));
        assertEquals(msg1, 28, EBMLvint.parseVintContentSize(0x1fffffL));
        assertEquals(msg1, 28, EBMLvint.parseVintContentSize(0x200000L));
        assertEquals(msg1, 28, EBMLvint.parseVintContentSize(0xffffffeL));

        assertEquals(msg1, 35, EBMLvint.parseVintContentSize(0xfffffffL));
        assertEquals(msg1, 35, EBMLvint.parseVintContentSize(0x10000000L));
        assertEquals(msg1, 35, EBMLvint.parseVintContentSize(0x7fffffffeL));
        assertEquals(msg1, 42, EBMLvint.parseVintContentSize(0x7ffffffffL));
        assertEquals(msg1, 42, EBMLvint.parseVintContentSize(0x800000000L));
        assertEquals(msg1, 42, EBMLvint.parseVintContentSize(0x3fffffffffeL));
        assertEquals(msg1, 49, EBMLvint.parseVintContentSize(0x3ffffffffffL));
        assertEquals(msg1, 49, EBMLvint.parseVintContentSize(0x40000000000L));
        assertEquals(msg1, 49, EBMLvint.parseVintContentSize(0x1fffffffffffeL));
        assertEquals(msg1, 56, EBMLvint.parseVintContentSize(0x1ffffffffffffL));
        assertEquals(msg1, 56, EBMLvint.parseVintContentSize(0x2000000000000L));
        assertEquals(msg1, 56, EBMLvint.parseVintContentSize(0xfffffffffffffeL));

        try {
            EBMLvint.parseVintContentSize(0xffffffffffffffL);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            EBMLvint.parseVintContentSize(0x100000000000000L);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            EBMLvint.parseVintContentSize(0x8000000000000000L);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
}