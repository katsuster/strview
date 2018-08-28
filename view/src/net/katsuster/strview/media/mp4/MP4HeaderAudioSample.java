package net.katsuster.strview.media.mp4;

import net.katsuster.strview.media.bit.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * MPEG4 media file format の AudioSampleEntry ヘッダ。
 * </p>
 *
 * <p>
 * 参考規格
 * </p>
 * <ul>
 * <li>ISO/IEC 14496-12: ISO base media file format</li>
 * </ul>
 *
 * @author katsuhiro
 */
public class MP4HeaderAudioSample extends MP4HeaderSample
        implements Cloneable {
    public UInt[] reserved2;
    public UInt channelcount;
    public UInt samplesize;
    public UInt pre_defined;
    public UInt reserved3;
    public SFixed16_16 samplerate;

    public MP4HeaderAudioSample() {
        reserved2 = new UInt[2];
        channelcount = new UInt("channelcount");
        samplesize = new UInt("samplesize");
        pre_defined = new UInt("pre_defined");
        reserved3 = new UInt("reserved3");
        samplerate = new SFixed16_16("samplerate", 0);
    }

    @Override
    public MP4HeaderAudioSample clone()
            throws CloneNotSupportedException {
        MP4HeaderAudioSample obj = (MP4HeaderAudioSample)super.clone();
        int i;

        obj.reserved2 = new UInt[2];
        for (i = 0; i < obj.reserved2.length; i++) {
            obj.reserved2[i] = (UInt)reserved2[i].clone();
        }

        obj.channelcount = (UInt)channelcount.clone();
        obj.samplesize = (UInt)samplesize.clone();
        obj.pre_defined = (UInt)pre_defined.clone();
        obj.reserved3 = (UInt)reserved3.clone();
        obj.samplerate = (SFixed16_16)samplerate.clone();

        return obj;
    }

    @Override
    public boolean isRecursive() {
        return true;
    }

    @Override
    public void readBits(BitStreamReader c) {
        readBits(c, this);
    }

    public static void readBits(BitStreamReader c,
                                MP4HeaderAudioSample d) {
        c.enterBlock(d);

        MP4HeaderSample.readBits(c, d);

        d.reserved2 = new UInt[2];
        for (int i = 0; i < d.reserved2.length; i++) {
            d.reserved2[i] = new UInt("reserved2[" + i + "]");
            d.reserved2[i] = c.readUInt(32, d.reserved2[i]);
        }

        d.channelcount = c.readUInt(16, d.channelcount );
        d.samplesize   = c.readUInt(16, d.samplesize   );
        d.pre_defined  = c.readUInt(16, d.pre_defined  );
        d.reserved3    = c.readUInt(16, d.reserved3    );
        d.samplerate   = c.readSF16_16(32, d.samplerate);

        //FIXME: 暫定対応、本来は QuickTime 対応版を作るべき
        //間違って QuickTime Sound Sample Description(Version 1) を
        //読んだときの対策
        if (d.reserved2[0].intValue() == 0x10000) {
            c.position(c.position() + 128);
        }

        c.leaveBlock();
    }

    @Override
    public void writeBits(BitStreamWriter c) {
        writeBits(c, this);
    }

    public static void writeBits(BitStreamWriter c,
                                 MP4HeaderAudioSample d) {
        c.enterBlock(d);

        MP4HeaderSample.writeBits(c, d);

        for (int i = 0; i < d.reserved2.length; i++) {
            c.writeUInt(32, d.reserved2[i]);
        }

        c.writeUInt(16, d.channelcount );
        c.writeUInt(16, d.samplesize   );
        c.writeUInt(16, d.pre_defined  );
        c.writeUInt(16, d.reserved3    );
        c.writeSF16_16(32, d.samplerate);

        c.leaveBlock();
    }
}
