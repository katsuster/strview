package net.katsuster.strview.test.media;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.katsuster.strview.media.*;

public class BitBufferTest {
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
    public final void testAllocate() {
        String msg2 = "allocate() illegal arguments check failed.";
        //with capacity
        BitBuffer.allocate(0);
        BitBuffer.allocate(1);
        BitBuffer.allocate(32768);

        try {
            //illegal capacity
            BitBuffer.allocate(-1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public final void testWrap() {
        String msg1 = "wrap() failed.";
        String msg2 = "wrap() illegal arguments check failed.";
        byte[] buf = new byte[10];

        //without offset, length
        assertNotNull(msg1, BitBuffer.wrap(buf));

        //without offset, with length
        assertNotNull(msg1, BitBuffer.wrap(buf, 0));
        assertNotNull(msg1, BitBuffer.wrap(buf, 1));
        assertNotNull(msg1, BitBuffer.wrap(buf, (buf.length << 3) - 1));
        assertNotNull(msg1, BitBuffer.wrap(buf, (buf.length << 3)));

        try {
            //illegal length
            BitBuffer.wrap(buf, (buf.length << 3) + 1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
        try {
            BitBuffer.wrap(buf, -1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }

    @Test
    public final void testCapacity() {
        String msg1 = "capacity() failed.";
        BitBuffer b0 = BitBuffer.allocate(0);
        BitBuffer b1 = BitBuffer.allocate(1);
        BitBuffer b10 = BitBuffer.allocate(10);

        assertEquals(msg1,  0, b0.capacity());
        assertEquals(msg1,  1, b1.capacity());
        assertEquals(msg1, 10, b10.capacity());
    }

    @Test
    public final void testPosition() {
        String msg1 = "position() failed.";
        BitBuffer b0 = BitBuffer.allocate(0);

        assertEquals(msg1, 0, b0.position());
    }

    @Test
    public final void testPositionLong() {
        String msg1 = "position(long) failed.";
        String msg2 = "position(long) illegal arguments check failed.";
        BitBuffer b0 = BitBuffer.allocate(0);
        BitBuffer b1 = BitBuffer.allocate(1);
        BitBuffer b10 = BitBuffer.allocate(10);

        assertSame(msg1, b0, b0.position(0));
        assertEquals(msg1,  0, b0.position());

        assertSame(msg1, b1, b1.position(1));
        assertEquals(msg1,  1, b1.position());

        assertSame(msg1, b10, b10.position(10));
        assertEquals(msg1, 10, b10.position());

        try {
            b0.position(-1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            b0.position(1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
        try {
            b1.position(-1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            b1.position(2);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
        try {
            b10.position(-1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            b10.position(11);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testLimit() {
        String msg1 = "limit() failed.";
        byte[] ba = {
                (byte)0xb4, //0b10110100
                (byte)0x6d, //0b01101101
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        assertEquals(msg1, 16, b1.limit());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testLimitLong() {
        String msg1 = "limit(long) failed.";
        String msg2 = "limit(long) illegal arguments check failed.";
        byte[] ba = {
                (byte)0xb4, //0b10110100
                (byte)0x6d, //0b01101101
        };
        BitBuffer b1 = BitBuffer.wrap(ba);
        BitBuffer b3 = BitBuffer.wrap(ba);

        //without offset
        b1.position(b1.limit());
        assertSame(msg1, b1, b1.limit(10));
        assertEquals(msg1, 10, b1.limit());
        assertEquals(msg1, 10, b1.position());

        //illegal arguments
        try {
            b3.limit(-1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }
        try {
            b3.limit(b3.limit() + 1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testClear() {
        String msg1 = "clear() failed.";
        byte[] ba = {
                (byte)0xb4, //0b10110100
                (byte)0x6d, //0b01101101
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        b1.limit(10);
        b1.position(5);
        assertSame(msg1, b1, b1.clear());
        assertEquals(msg1, 16, b1.limit());
        assertEquals(msg1, 0, b1.position());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testFlip() {
        String msg1 = "flip() failed.";
        byte[] ba = {
                (byte)0xb4, //0b10110100
                (byte)0x6d, //0b01101101
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        b1.limit(10);
        b1.position(5);
        assertSame(msg1, b1, b1.flip());
        assertEquals(msg1, 5, b1.limit());
        assertEquals(msg1, 0, b1.position());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testRewind() {
        String msg1 = "rewind() failed.";
        byte[] ba = {
                (byte)0xb4, //0b10110100
                (byte)0x6d, //0b01101101
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        b1.position(5);
        assertSame(msg1, b1, b1.rewind());
        assertEquals(msg1, 0, b1.position());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testRemaining() {
        String msg1 = "remaining() failed.";
        byte[] ba = {
                (byte)0xb4, //0b10110100
                (byte)0x6d, //0b01101101
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        assertEquals(msg1, 16, b1.remaining());
        b1.limit(15);
        b1.position(5);
        assertEquals(msg1, 10, b1.remaining());
        b1.limit(15);
        b1.position(14);
        assertEquals(msg1, 1, b1.remaining());
        b1.limit(15);
        b1.position(15);
        assertEquals(msg1, 0, b1.remaining());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testHasRemaining() {
        String msg1 = "hasRemaining() failed.";
        byte[] ba = {
                (byte)0xb4, //0b10110100
                (byte)0x6d, //0b01101101
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        assertEquals(msg1, true, b1.hasRemaining());
        b1.limit(15);
        b1.position(5);
        assertEquals(msg1, true, b1.hasRemaining());
        b1.limit(15);
        b1.position(14);
        assertEquals(msg1, true, b1.hasRemaining());
        b1.limit(15);
        b1.position(15);
        assertEquals(msg1, false, b1.hasRemaining());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testIsReadOnly() {
        String msg1 = "isReadOnly() failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        assertEquals(msg1, false, b1.isReadOnly());
    }

    @Test
    public final void testHasArray() {
        String msg1 = "hasArray() failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        assertEquals(msg1, true, b1.hasArray());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testArray() {
        String msg1 = "array() failed.";
        //String msg2 = "array() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        assertNotNull(msg1, b1.array());

        //with offset
        //do nothing

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testIsAlignedByte() {
        String msg1 = "isAlignedByte() failed.";
        //String msg2 = "isAlignedByte() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        b1.position(0);
        assertEquals(msg1, true, b1.isAlignedByte());
        b1.position(1);
        assertEquals(msg1, false, b1.isAlignedByte());
        b1.position(2);
        assertEquals(msg1, false, b1.isAlignedByte());
        b1.position(7);
        assertEquals(msg1, false, b1.isAlignedByte());
        b1.position(8);
        assertEquals(msg1, true, b1.isAlignedByte());
        b1.position(9);
        assertEquals(msg1, false, b1.isAlignedByte());
        b1.position(13);
        assertEquals(msg1, false, b1.isAlignedByte());
        b1.position(16);
        assertEquals(msg1, true, b1.isAlignedByte());
        b1.position(17);
        assertEquals(msg1, false, b1.isAlignedByte());
        b1.position(24);
        assertEquals(msg1, true, b1.isAlignedByte());
        b1.position(32);
        assertEquals(msg1, true, b1.isAlignedByte());
        b1.position(33);
        assertEquals(msg1, false, b1.isAlignedByte());
        b1.position(63);
        assertEquals(msg1, false, b1.isAlignedByte());
        b1.position(64);
        assertEquals(msg1, true, b1.isAlignedByte());
        b1.position(80);
        assertEquals(msg1, true, b1.isAlignedByte());
        b1.position(96);
        assertEquals(msg1, true, b1.isAlignedByte());
        b1.position(128);
        assertEquals(msg1, true, b1.isAlignedByte());
        b1.position(192);
        assertEquals(msg1, true, b1.isAlignedByte());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testAlignByte() {
        String msg1 = "alignByte() failed.";
        String msg2 = "alignByte() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
        };
        BitBuffer b1 = BitBuffer.wrap(ba);
        BitBuffer b2 = BitBuffer.wrap(ba, 195);

        //without offset
        b1.position(0);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 0, b1.position());
        b1.position(1);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 8, b1.position());
        b1.position(2);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 8, b1.position());
        b1.position(7);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 8, b1.position());
        b1.position(8);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 8, b1.position());
        b1.position(9);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 16, b1.position());
        b1.position(13);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 16, b1.position());
        b1.position(16);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 16, b1.position());
        b1.position(17);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 24, b1.position());
        b1.position(24);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 24, b1.position());
        b1.position(32);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 32, b1.position());
        b1.position(33);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 40, b1.position());
        b1.position(63);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 64, b1.position());
        b1.position(64);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 64, b1.position());
        b1.position(80);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 80, b1.position());
        b1.position(96);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 96, b1.position());
        b1.position(128);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 128, b1.position());
        b1.position(192);
        assertSame(msg1, b1, b1.alignByte());
        assertEquals(msg1, 192, b1.position());

        //illegal arguments
        try {
            b2.position(193);
            assertSame(msg1, b2, b2.alignByte());
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testIsAlignedShort() {
        String msg1 = "isAlignedShort() failed.";
        //String msg2 = "isAlignedShort() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        b1.position(0);
        assertEquals(msg1, true, b1.isAlignedShort());
        b1.position(1);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(2);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(7);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(8);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(9);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(13);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(16);
        assertEquals(msg1, true, b1.isAlignedShort());
        b1.position(17);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(24);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(32);
        assertEquals(msg1, true, b1.isAlignedShort());
        b1.position(33);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(63);
        assertEquals(msg1, false, b1.isAlignedShort());
        b1.position(64);
        assertEquals(msg1, true, b1.isAlignedShort());
        b1.position(80);
        assertEquals(msg1, true, b1.isAlignedShort());
        b1.position(96);
        assertEquals(msg1, true, b1.isAlignedShort());
        b1.position(128);
        assertEquals(msg1, true, b1.isAlignedShort());
        b1.position(192);
        assertEquals(msg1, true, b1.isAlignedShort());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testAlignShort() {
        String msg1 = "alignShort() failed.";
        String msg2 = "alignShort() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
        };
        BitBuffer b1 = BitBuffer.wrap(ba);
        BitBuffer b2 = BitBuffer.wrap(ba, 205);

        //without offset
        b1.position(0);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 0, b1.position());
        b1.position(1);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 16, b1.position());
        b1.position(2);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 16, b1.position());
        b1.position(7);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 16, b1.position());
        b1.position(8);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 16, b1.position());
        b1.position(9);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 16, b1.position());
        b1.position(13);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 16, b1.position());
        b1.position(16);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 16, b1.position());
        b1.position(17);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 32, b1.position());
        b1.position(24);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 32, b1.position());
        b1.position(32);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 32, b1.position());
        b1.position(33);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 48, b1.position());
        b1.position(63);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 64, b1.position());
        b1.position(64);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 64, b1.position());
        b1.position(80);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 80, b1.position());
        b1.position(96);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 96, b1.position());
        b1.position(128);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 128, b1.position());
        b1.position(192);
        assertSame(msg1, b1, b1.alignShort());
        assertEquals(msg1, 192, b1.position());

        //illegal arguments
        try {
            b2.position(193);
            assertSame(msg1, b2, b2.alignShort());
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testIsAlignedInt() {
        String msg1 = "isAlignedInt() failed.";
        //String msg2 = "isAlignedInt() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        b1.position(0);
        assertEquals(msg1, true, b1.isAlignedInt());
        b1.position(1);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(2);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(7);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(8);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(9);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(13);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(16);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(17);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(24);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(32);
        assertEquals(msg1, true, b1.isAlignedInt());
        b1.position(33);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(63);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(64);
        assertEquals(msg1, true, b1.isAlignedInt());
        b1.position(80);
        assertEquals(msg1, false, b1.isAlignedInt());
        b1.position(96);
        assertEquals(msg1, true, b1.isAlignedInt());
        b1.position(128);
        assertEquals(msg1, true, b1.isAlignedInt());
        b1.position(192);
        assertEquals(msg1, true, b1.isAlignedInt());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testAlignInt() {
        String msg1 = "alignInt() failed.";
        String msg2 = "alignInt() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
        };
        BitBuffer b1 = BitBuffer.wrap(ba);
        BitBuffer b2 = BitBuffer.wrap(ba, 210);

        //without offset
        b1.position(0);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 0, b1.position());
        b1.position(1);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(2);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(7);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(8);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(9);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(13);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(16);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(17);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(24);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(32);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 32, b1.position());
        b1.position(33);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 64, b1.position());
        b1.position(63);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 64, b1.position());
        b1.position(64);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 64, b1.position());
        b1.position(80);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 96, b1.position());
        b1.position(96);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 96, b1.position());
        b1.position(128);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 128, b1.position());
        b1.position(192);
        assertSame(msg1, b1, b1.alignInt());
        assertEquals(msg1, 192, b1.position());

        //illegal arguments
        try {
            b2.position(193);
            assertSame(msg1, b2, b2.alignInt());
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testIsAlignedLong() {
        String msg1 = "isAlignedLong() failed.";
        //String msg2 = "isAlignedLong() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        //without offset
        b1.position(0);
        assertEquals(msg1, true, b1.isAlignedLong());
        b1.position(1);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(2);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(7);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(8);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(9);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(13);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(16);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(17);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(24);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(32);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(33);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(63);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(64);
        assertEquals(msg1, true, b1.isAlignedLong());
        b1.position(80);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(96);
        assertEquals(msg1, false, b1.isAlignedLong());
        b1.position(128);
        assertEquals(msg1, true, b1.isAlignedLong());
        b1.position(192);
        assertEquals(msg1, true, b1.isAlignedLong());

        //illegal arguments
        //do nothing
    }

    @Test
    public final void testAlignLong() {
        String msg1 = "alignLong() failed.";
        String msg2 = "alignLong() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
                (byte)0x53, (byte)0x53, (byte)0x53, (byte)0x53,
                (byte)0xac, (byte)0xac, (byte)0xac, (byte)0xac,
        };
        BitBuffer b1 = BitBuffer.wrap(ba);
        BitBuffer b2 = BitBuffer.wrap(ba, 230);

        //without offset
        b1.position(0);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 0, b1.position());
        b1.position(1);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(2);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(7);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(8);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(9);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(13);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(16);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(17);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(24);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(32);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(33);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(63);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(64);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 64, b1.position());
        b1.position(80);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 128, b1.position());
        b1.position(96);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 128, b1.position());
        b1.position(128);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 128, b1.position());
        b1.position(192);
        assertSame(msg1, b1, b1.alignLong());
        assertEquals(msg1, 192, b1.position());

        //illegal arguments
        try {
            b2.position(193);
            assertSame(msg1, b2, b2.alignLong());
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testIsDirect() {
        String msg1 = "isDirect() failed.";
        //String msg2 = "isDirect() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
        };
        BitBuffer b1 = BitBuffer.wrap(ba);

        assertEquals(msg1, false, b1.isDirect());
    }

    @Test
    public final void testGet() {
        String msg1 = "get() failed.";
        String msg2 = "get() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
        };
        BitBuffer b1 = BitBuffer.wrap(ba);
        BitBuffer b3 = BitBuffer.wrap(ba);
        int i;

        //without offset
        assertEquals(msg1, 0, b1.get());
        assertEquals(msg1, 1, b1.get());
        assertEquals(msg1, 0, b1.get());
        assertEquals(msg1, 1, b1.get());
        assertEquals(msg1, 0, b1.get());
        assertEquals(msg1, 0, b1.get());
        assertEquals(msg1, 1, b1.get());
        assertEquals(msg1, 1, b1.get());

        assertEquals(msg1, 1, b1.get());
        assertEquals(msg1, 0, b1.get());
        assertEquals(msg1, 1, b1.get());
        assertEquals(msg1, 0, b1.get());
        assertEquals(msg1, 1, b1.get());
        assertEquals(msg1, 1, b1.get());
        assertEquals(msg1, 0, b1.get());
        assertEquals(msg1, 0, b1.get());

        //illegal arguments
        for (i = 0; i < b3.limit(); i++) {
            b3.get();
        }
        try {
            b3.get();
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testGetInt() {
        String msg1 = "get(int) failed.";
        String msg2 = "get(int) illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
        };
        BitBuffer b1 = BitBuffer.wrap(ba);
        BitBuffer b3 = BitBuffer.wrap(ba);

        //without offset
        assertEquals(msg1, 1, b1.get(8));
        assertEquals(msg1, 0, b1.get(9));
        assertEquals(msg1, 1, b1.get(10));
        assertEquals(msg1, 0, b1.get(11));
        assertEquals(msg1, 1, b1.get(12));
        assertEquals(msg1, 1, b1.get(13));
        assertEquals(msg1, 0, b1.get(14));
        assertEquals(msg1, 0, b1.get(15));

        assertEquals(msg1, 0, b1.get(0));
        assertEquals(msg1, 1, b1.get(1));
        assertEquals(msg1, 0, b1.get(2));
        assertEquals(msg1, 1, b1.get(3));
        assertEquals(msg1, 0, b1.get(4));
        assertEquals(msg1, 0, b1.get(5));
        assertEquals(msg1, 1, b1.get(6));
        assertEquals(msg1, 1, b1.get(7));

        assertEquals(msg1, 0, b1.position());

        //illegal arguments
        try {
            b3.get(-1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
        try {
            b3.get(b3.limit());
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
        try {
            b3.get(b3.limit() + 1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testGetByteArrayIntIntWithoutOffset() {
        String msg1 = "get(byte[], int, int) failed.";
        String msg2 = "get(byte[], int, int) illegal arguments check failed.";
        byte[] buf = {
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
        };
        byte[] ba = {
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
        };
        byte[] bb = {
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00,
        };
        byte[] bc = {
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xa0, //0b10100000
        };
        byte[] bd = {
                0x00, 0x00, 0x00,
        };
        byte[] be = {
                (byte)0x1d, //0b00011101
                (byte)0x2c, //0b00101100
                (byte)0x3b, //0b00111011
        };
        byte[] bf = {
                0x00, 0x00, 0x00,
        };
        byte[] bg = {
                (byte)0x1d, //0b00011101
                (byte)0x2c, //0b00101100
                (byte)0x3b, //0b00111011
                (byte)0x40, //0b01000000
        };
        byte[] bh = {
                0x00, 0x00, 0x00, 0x00,
        };
        byte[] bi = {
                (byte)0xc0, //0b11000000
        };
        byte[] bj = {
                0x00,
        };
        byte[] b_ill = {
                0x00,
        };
        BitBuffer b1 = BitBuffer.wrap(buf);
        BitBuffer b3 = BitBuffer.wrap(buf);
        int i;

        //without offset
        //1st, mid(arraycopy)
        assertSame(msg1, b1, b1.get(bb, 0, 56));
        for (i = 0; i < ba.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    ba[i], bb[i]);
        }
        assertEquals(msg1, 56, b1.position());

        //1st, mid, last(arraycopy)
        b1.position(8);
        assertSame(msg1, b1, b1.get(bd, 0, 19));
        for (i = 0; i < bc.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bc[i], bd[i]);
        }
        assertEquals(msg1, 27, b1.position());

        //1st, mid(for-loop)
        b1.position(4);
        assertSame(msg1, b1, b1.get(bf, 0, 24));
        for (i = 0; i < be.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    be[i], bf[i]);
        }
        assertEquals(msg1, 28, b1.position());

        //1st, mid, last(for-loop)
        b1.position(4);
        assertSame(msg1, b1, b1.get(bh, 0, 28));
        for (i = 0; i < bg.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bg[i], bh[i]);
        }
        assertEquals(msg1, 32, b1.position());

        //last only
        b1.position(16);
        assertSame(msg1, b1, b1.get(bj, 0, 4));
        for (i = 0; i < bi.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bi[i], bj[i]);
        }
        assertEquals(msg1, 20, b1.position());

        //illegal arguments
        try {
            b3.get(b_ill, 9, 0);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            b3.get(b_ill, -1, 0);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b3.get(b_ill, 0, -1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b3.get(b_ill, 4, 5);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            b3.position(b3.limit());
            b3.get(b_ill, 0, 8);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testGetByteArrayIntIntWithOffset() {
        String msg1 = "get(byte[], int, int) failed.";
        //String msg2 = "get(byte[], int, int) illegal arguments check failed.";
        byte[] buf = {
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
        };
        byte[] ba = {
                (byte)0x00,
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
        };
        byte[] bb = {
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
        };
        byte[] bc = {
                (byte)0x00,
                (byte)0x00,
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xa0, //0b10100000
        };
        byte[] bd = {
                0x00, 0x00, 0x00, 0x00,
                0x00,
        };
        byte[] be = {
                (byte)0x01, //0b00000001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
        };
        byte[] bf = {
                0x00, 0x00, 0x00,
        };
        byte[] bg = {
                (byte)0x00,
                (byte)0x01, //0b00000001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb0, //0b1011
        };
        byte[] bh = {
                0x00, 0x00, 0x00, 0x00,
                0x00,
        };
        byte[] bi = {
                (byte)0x00,
                (byte)0x0c, //0b1100
        };
        byte[] bj = {
                0x00, 0x00,
        };
        BitBuffer b1 = BitBuffer.wrap(buf);
        int i;

        //without offset
        //1st, mid(arraycopy)
        assertSame(msg1, b1, b1.get(bb, 8, 56));
        for (i = 0; i < ba.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    ba[i], bb[i]);
        }
        assertEquals(msg1, 56, b1.position());

        //1st, mid, last(arraycopy)
        b1.position(8);
        assertSame(msg1, b1, b1.get(bd, 16, 19));
        for (i = 0; i < bc.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bc[i], bd[i]);
        }
        assertEquals(msg1, 27, b1.position());

        //1st, mid(for-loop)
        b1.position(4);
        assertSame(msg1, b1, b1.get(bf, 4, 20));
        for (i = 0; i < be.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    be[i], bf[i]);
        }
        assertEquals(msg1, 24, b1.position());

        //1st, mid, last(for-loop)
        b1.position(4);
        assertSame(msg1, b1, b1.get(bh, 12, 24));
        for (i = 0; i < bg.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bg[i], bh[i]);
        }
        assertEquals(msg1, 28, b1.position());

        //last only
        b1.position(16);
        assertSame(msg1, b1, b1.get(bj, 12, 4));
        for (i = 0; i < bi.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bi[i], bj[i]);
        }
        assertEquals(msg1, 20, b1.position());
    }

    @Test
    public final void testGet32() {
        String msg1 = "get32() failed.";
        String msg2 = "get32() illegal arguments check failed.";
        byte[] ba = {
                (byte)0xf0, //0b11110000
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x9e, //0b10011110
                (byte)0x87, //0b10000111
                (byte)0x87, //0b10000111
                (byte)0x96, //0b10010110
                (byte)0xa5, //0b10100101
        };
        BitBuffer b1 = BitBuffer.wrap(ba);
        BitBuffer b3 = BitBuffer.wrap(ba);

        //without offset
        assertEquals(msg1, 0x00, b1.get32( 0)); //0b
        assertEquals(msg1, 0x01, b1.get32( 1)); //0b1
        assertEquals(msg1, 0x07, b1.get32( 3)); //0b111
        assertEquals(msg1, 0x0e, b1.get32( 8)); //0b00001110
        assertEquals(msg1, 0x01, b1.get32( 4)); //0b0001
        //0b 110 1001 0110 0001 1101 1010 0101 0010
        assertEquals(msg1, 0x6961da52, b1.get32(31));
        //0b1100 1111 0100 0011 1100 0011 1100 1011
        assertEquals(msg1, 0xcf43c3cb, b1.get32(32));

        //illegal arguments
        try {
            b3.get32(-1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b3.get32(33);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b3.position(b3.limit());
            b3.get32(1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testGet64() {
        String msg1 = "get64() failed.";
        String msg2 = "get64() illegal arguments check failed.";
        byte[] ba = {
                (byte)0xf0, //0b11110000
                (byte)0xe1, //0b11100001

                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
                (byte)0x78, //0b01111000
                (byte)0x69, //0b01101001

                (byte)0xda, //0b11011010
                (byte)0x4b, //0b01001011
                (byte)0x3c, //0b00111100
                (byte)0x2d, //0b00101101
                (byte)0x1e, //0b00011110
                (byte)0x0f, //0b00001111
                (byte)0xf0, //0b11110000
                (byte)0xe7, //0b11100111
        };
        BitBuffer b1 = BitBuffer.wrap(ba);
        BitBuffer b3 = BitBuffer.wrap(ba);

        //without offset
        assertEquals(msg1, 0x00, b1.get64( 0)); //0b
        assertEquals(msg1, 0x01, b1.get64( 1)); //0b1
        assertEquals(msg1, 0x07, b1.get64( 3)); //0b111
        assertEquals(msg1, 0x0e, b1.get64( 8)); //0b00001110
        assertEquals(msg1, 0x01, b1.get64( 4)); //0b0001
        //0b 110 1001 0110 0001 1101 1010 0101 0010
        //  1100 1011 0100 0011 1011 1100 0011 0100
        assertEquals(msg1, 0x6961da52cb43bc34L, b1.get64(63));
        //0b1110 1101 0010 0101 1001 1110 0001 0110
        //  1000 1111 0000 0111 1111 1000 0111 0011
        assertEquals(msg1, 0xed259e168f07f873L, b1.get64(64));

        //illegal arguments
        try {
            b3.get64(-1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b3.get64(65);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b3.position(b3.limit());
            b3.get64(1);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testPutInt() {
        String msg1 = "put(int) failed.";
        String msg2 = "put(int) illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
                (byte)0xac, //0b10101100
        };
        byte[] bb = {
                (byte)0x00, //0b00000000
                (byte)0x00, //0b00000000
                (byte)0xff, //0b11111111
        };
        BitBuffer b1 = BitBuffer.wrap(bb);
        BitBuffer b3 = BitBuffer.wrap(ba);
        int i;

        //without offset
        //write on 0
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(1));

        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(0));

        //write on 1
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(1));
        assertSame(msg1, b1, b1.put(0));
        assertSame(msg1, b1, b1.put(0));

        for (i = 0; i < ba.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    ba[i], bb[i]);
        }

        //illegal arguments
        for (i = 0; i < b3.limit(); i++) {
            b3.put(0);
        }
        try {
            b3.put(0);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testPutIntInt() {
        String msg1 = "put(int, int) failed.";
        String msg2 = "put(int, int) illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
        };
        byte[] bb = {
                (byte)0x00, //0b00000000
                (byte)0x00, //0b00000000
        };
        BitBuffer b1 = BitBuffer.wrap(bb);
        BitBuffer b3 = BitBuffer.wrap(ba);
        int i;

        //without offset
        assertSame(msg1, b1, b1.put(8, 1));
        assertSame(msg1, b1, b1.put(9, 0));
        assertSame(msg1, b1, b1.put(10, 1));
        assertSame(msg1, b1, b1.put(11, 0));
        assertSame(msg1, b1, b1.put(12, 1));
        assertSame(msg1, b1, b1.put(13, 1));
        assertSame(msg1, b1, b1.put(14, 0));
        assertSame(msg1, b1, b1.put(15, 0));

        assertSame(msg1, b1, b1.put(0, 0));
        assertSame(msg1, b1, b1.put(1, 1));
        assertSame(msg1, b1, b1.put(2, 0));
        assertSame(msg1, b1, b1.put(3, 1));
        assertSame(msg1, b1, b1.put(4, 0));
        assertSame(msg1, b1, b1.put(5, 0));
        assertSame(msg1, b1, b1.put(6, 1));
        assertSame(msg1, b1, b1.put(7, 1));

        assertEquals(msg1, 0, b1.position());

        for (i = 0; i < ba.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    ba[i], bb[i]);
        }

        //illegal arguments
        try {
            b3.put(-1, 0);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            b3.put(b3.limit(), 0);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            b3.put(b3.limit() + 1, 0);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testPutByteArrayIntIntWithoutOffset() {
        String msg1 = "put(byte[], int, int) failed.";
        String msg2 = "put(byte[], int, int) illegal arguments check failed.";
        byte[] buf = {
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00,
        };
        byte[] ba = {
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
        };
        byte[] bb = {
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
        };
        byte[] bc = {
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xbf, //0b10111111
        };
        byte[] bd = {
                (byte)0x00,
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xa0, //0b101
        };
        byte[] be = {
                (byte)0x1d, //0b00011101
                (byte)0x2c, //0b00101100
                (byte)0x3b, //0b00111011
        };
        byte[] bf = {
                (byte)0x01, //0b    0001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb0, //0b1011
        };
        byte[] bg = {
                (byte)0x1d, //0b00011101
                (byte)0x2c, //0b00101100
                (byte)0x3b, //0b00111011
                (byte)0x49, //0b01001001
        };
        byte[] bh = {
                (byte)0x01, //0b    0001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
        };
        byte[] bi = {
                (byte)0xd0, //0b11010000
        };
        byte[] bj = {
                (byte)0x00,
                (byte)0x00,
                (byte)0xd0, //0b1101
        };
        byte[] b_ill = {
                0x00,
        };
        BitBuffer b1 = BitBuffer.wrap(buf);
        BitBuffer b3 = BitBuffer.wrap(buf);
        int i;

        //without offset
        //1st, mid(arraycopy)
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        assertSame(msg1, b1, b1.put(ba, 0, 56));
        for (i = 0; i < bb.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bb[i], buf[i]);
        }
        assertEquals(msg1, 56, b1.position());

        //1st, mid, last(arraycopy)
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        b1.position(8);
        assertSame(msg1, b1, b1.put(bc, 0, 19));
        for (i = 0; i < bd.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bd[i], buf[i]);
        }
        assertEquals(msg1, 27, b1.position());

        //1st, mid(for-loop)
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        b1.position(4);
        assertSame(msg1, b1, b1.put(be, 0, 24));
        for (i = 0; i < bf.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bf[i], buf[i]);
        }
        assertEquals(msg1, 28, b1.position());

        //1st, mid, last(for-loop)
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        b1.position(4);
        assertSame(msg1, b1, b1.put(bg, 0, 28));
        for (i = 0; i < bh.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bh[i], buf[i]);
        }
        assertEquals(msg1, 32, b1.position());

        //last only
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        b1.position(16);
        assertSame(msg1, b1, b1.put(bi, 0, 4));
        for (i = 0; i < bj.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bj[i], buf[i]);
        }
        assertEquals(msg1, 20, b1.position());

        //illegal arguments
        try {
            b3.put(b_ill, -1, 0);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b3.put(b_ill, 0, -1);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b3.put(b_ill, 4, 5);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }

        try {
            b3.position(b3.limit());
            b3.put(b_ill, 0, 8);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testPutByteArrayIntIntWithOffset() {
        String msg1 = "put(byte[], int, int) failed.";
        //String msg2 = "put(byte[], int, int) illegal arguments check failed.";
        byte[] buf = {
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
        };
        byte[] ba = {
                (byte)0xff,
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
        };
        byte[] bb = {
                (byte)0xe1, //0b11100001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb4, //0b10110100
                (byte)0xa5, //0b10100101
                (byte)0x96, //0b10010110
                (byte)0x87, //0b10000111
        };
        byte[] bc = {
                (byte)0xff,
                (byte)0xff,
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xa0, //0b10100000
        };
        byte[] bd = {
                (byte)0x00,
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xa0, //0b101
        };
        byte[] be = {
                (byte)0x9b, //0b10011011
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
        };
        byte[] bf = {
                (byte)0x0b, //0b    1011
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
        };
        byte[] bg = {
                (byte)0xff,
                (byte)0x09, //0b00001001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb0, //0b1011
        };
        byte[] bh = {
                (byte)0x09, //0b    1001
                (byte)0xd2, //0b11010010
                (byte)0xc3, //0b11000011
                (byte)0xb0, //0b1011
        };
        byte[] bi = {
                (byte)0xff,
                (byte)0x0d, //0b00001101
        };
        byte[] bj = {
                (byte)0x00,
                (byte)0x00,
                (byte)0xd0, //0b1101
        };
        BitBuffer b1 = BitBuffer.wrap(buf);
        int i;

        //without offset
        //1st, mid(arraycopy)
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        assertSame(msg1, b1, b1.put(ba, 8, 56));
        for (i = 0; i < bb.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bb[i], buf[i]);
        }
        assertEquals(msg1, 56, b1.position());

        //1st, mid, last(arraycopy)
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        b1.position(8);
        assertSame(msg1, b1, b1.put(bc, 16, 19));
        for (i = 0; i < bd.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bd[i], buf[i]);
        }
        assertEquals(msg1, 27, b1.position());

        //1st, mid(for-loop)
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        b1.position(4);
        assertSame(msg1, b1, b1.put(be, 4, 20));
        for (i = 0; i < bf.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bf[i], buf[i]);
        }
        assertEquals(msg1, 24, b1.position());

        //1st, mid, last(for-loop)
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        b1.position(4);
        assertSame(msg1, b1, b1.put(bg, 12, 24));
        for (i = 0; i < bh.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bh[i], buf[i]);
        }
        assertEquals(msg1, 28, b1.position());

        //last only
        for (i = 0; i < buf.length; i++) {
            buf[i] = 0x00;
        }
        b1.position(16);
        assertSame(msg1, b1, b1.put(bi, 12, 4));
        for (i = 0; i < bj.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bj[i], buf[i]);
        }
        assertEquals(msg1, 20, b1.position());
    }

    @Test
    public final void testPut32() {
        String msg1 = "put32() failed.";
        String msg2 = "put32() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100

                (byte)0xa0, //0b10100000
                (byte)0x12, //0b00010010
                (byte)0x34, //0b00110100
                (byte)0x57, //0b01010111

                (byte)0xa0, //0b10100000
                (byte)0x12, //0b00010010
                (byte)0x34, //0b00110100
                (byte)0x56, //0b01010110
        };
        byte[] bb = {
                (byte)0x00, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
        };
        byte[] be = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
                (byte)0x4f, //0b0100
        };
        byte[] bf = {
                (byte)0xff, (byte)0xff, (byte)0xff,
        };
        byte[] bi = {
                (byte)0x00, //0b
                (byte)0x03, //0b    0011
                (byte)0x4f, //0b0100
                (byte)0xff, //0b
        };
        byte[] bj = {
                (byte)0x00, (byte)0x00, (byte)0xff, (byte)0xff,
        };
        BitBuffer b1 = BitBuffer.wrap(bb);
        BitBuffer b3 = BitBuffer.wrap(bf);
        BitBuffer b5 = BitBuffer.wrap(bj);
        BitBuffer b6 = BitBuffer.wrap(bj);
        int i;

        //without offset
        assertSame(msg1, b1, b1.put32( 0, 0xff)); //0b
        assertSame(msg1, b1, b1.put32( 1, 0x00)); //0b0
        assertSame(msg1, b1, b1.put32( 3, 0x05)); //0b101
        assertSame(msg1, b1, b1.put32( 8, 0x3a)); //0b00111010
        assertSame(msg1, b1, b1.put32( 4, 0x0c)); //0b1100
        //0b 101 0000 0000 1001 0001 1010 0010 1011
        assertSame(msg1, b1, b1.put32(31, 0x50091a2b));
        //0b1101 0000 0000 1001 0001 1010 0010 1011
        assertSame(msg1, b1, b1.put32(32, 0xd0091a2b));

        for (i = 0; i < ba.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    ba[i], bb[i]);
        }

        //without offset, write on 1
        assertSame(msg1, b3, b3.put32( 0, 0xff)); //0b
        assertSame(msg1, b3, b3.put32( 1, 0x00)); //0b0
        assertSame(msg1, b3, b3.put32( 3, 0x05)); //0b101
        assertSame(msg1, b3, b3.put32(16, 0x3ac4)); //0b00111010 11000101

        for (i = 0; i < be.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    be[i], bf[i]);
        }

        //with offset, write on 1
        b5.position(12);
        assertSame(msg1, b5, b5.put32( 8, 0x34)); //0b101

        for (i = 0; i < bi.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    bi[i], bj[i]);
        }

        //illegal arguments
        try {
            b6.put32(-1, 0);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b6.put32(33, 0);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b6.position(b6.limit());
            b6.put32(1, 0);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }

    @Test
    public final void testPut64() {
        String msg1 = "put64() failed.";
        String msg2 = "put64() illegal arguments check failed.";
        byte[] ba = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100

                (byte)0xf0, //0b11110000
                (byte)0xd2, //0b11010010
                (byte)0xb4, //0b10110100
                (byte)0x96, //0b10010110
                (byte)0x78, //0b01111000
                (byte)0x5a, //0b01011010
                (byte)0x3c, //0b00111100
                (byte)0x1f, //0b00011111

                (byte)0xf0, //0b11110000
                (byte)0xd2, //0b11010010
                (byte)0xb4, //0b10110100
                (byte)0x96, //0b10010110
                (byte)0x78, //0b01111000
                (byte)0x5a, //0b01011010
                (byte)0x3c, //0b00111100
                (byte)0x1e, //0b00011110
        };
        byte[] bb = {
                (byte)0x00, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
        };
        byte[] be = {
                (byte)0x53, //0b01010011
                (byte)0xac, //0b10101100
                (byte)0x4f, //0b0100
        };
        byte[] bf = {
                (byte)0xff, (byte)0xff, (byte)0xff,
        };
        byte[] bj = {
                (byte)0x00, (byte)0x00, (byte)0xff, (byte)0xff,
        };
        BitBuffer b1 = BitBuffer.wrap(bb);
        BitBuffer b3 = BitBuffer.wrap(bf);
        BitBuffer b6 = BitBuffer.wrap(bj);
        int i;

        //without offset
        assertSame(msg1, b1, b1.put64( 0, 0xff)); //0b
        assertSame(msg1, b1, b1.put64( 1, 0x00)); //0b0
        assertSame(msg1, b1, b1.put64( 3, 0x05)); //0b101
        assertSame(msg1, b1, b1.put64( 8, 0x3a)); //0b00111010
        assertSame(msg1, b1, b1.put64( 4, 0x0c)); //0b1100
        //0b  111 1000 0110 1001 0101 1010 0100 1011
        //   0011 1100 0010 1101 0001 1110 0000 1111
        assertSame(msg1, b1, b1.put64(63, 0x78695a4b3c2d1e0fL));
        //0b 1111 1000 0110 1001 0101 1010 0100 1011
        //   0011 1100 0010 1101 0001 1110 0000 1111
        assertSame(msg1, b1, b1.put64(64, 0xf8695a4b3c2d1e0fL));

        for (i = 0; i < ba.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    ba[i], bb[i]);
        }

        //without offset, write on 1
        assertSame(msg1, b3, b3.put64( 0, 0xff)); //0b
        assertSame(msg1, b3, b3.put64( 1, 0x00)); //0b0
        assertSame(msg1, b3, b3.put64( 3, 0x05)); //0b101
        assertSame(msg1, b3, b3.put64(16, 0x3ac4)); //0b00111010 11000101

        for (i = 0; i < be.length; i++) {
            assertEquals(msg1 + " at i=" + i + ".",
                    be[i], bf[i]);
        }

        //illegal arguments
        try {
            b6.put64(-1, 0);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b6.put64(65, 0);
            fail(msg2);
        } catch (IllegalArgumentException ex) {
            //OK
        }

        try {
            b6.position(b6.limit());
            b6.put64(1, 0);
            fail(msg2);
        } catch (IndexOutOfBoundsException ex) {
            //OK
        }
    }
}
