package net.katsuster.strview.io;

import java.util.*;

import net.katsuster.strview.util.*;
import net.katsuster.strview.util.bit.*;

/**
 * <p>
 * 1つ以上のビット列を連結して作ったビット列です。
 * </p>
 */
public class SequenceBitList extends AbstractLargeBitList {
    private List<Range<LargeBitList>> l;

    public SequenceBitList() {
        super(0);

        l = new ArrayList<>();
    }

    @Override
    public long length() {
        long sum = 0;

        for (Range<LargeBitList> r : l) {
            sum += r.getLength();
        }

        return sum;
    }

    @Override
    protected Boolean getInner(long index) {
        long st = 0;

        for (Range<LargeBitList> r : l) {
            if (st <= index && index < st + r.getLength()) {
                return r.getBuffer().get(index - st);
            }

            st += r.getLength();
        }

        throw new IndexOutOfBoundsException("index(" + index + ").");
    }

    @Override
    protected void setInner(long index, Boolean data) {
        long st = 0;

        for (Range<LargeBitList> r : l) {
            if (st <= index && index < st + r.getLength()) {
                r.getBuffer().set(index - st, data);
                return;
            }

            st += r.getLength();
        }

        throw new IndexOutOfBoundsException("index(" + index + ").");
    }

    public boolean add(Range<LargeBitList> r) {
        if (r == null) {
            throw new IllegalArgumentException("range is null.");
        }

        return l.add(r);
    }
}
