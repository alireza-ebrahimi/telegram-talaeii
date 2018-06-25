package org.telegram.messenger.exoplayer2.source;

public final class CompositeSequenceableLoader implements SequenceableLoader {
    private final SequenceableLoader[] loaders;

    public CompositeSequenceableLoader(SequenceableLoader[] sequenceableLoaderArr) {
        this.loaders = sequenceableLoaderArr;
    }

    public final boolean continueLoading(long j) {
        boolean z = false;
        int i;
        do {
            long nextLoadPositionUs = getNextLoadPositionUs();
            if (nextLoadPositionUs == Long.MIN_VALUE) {
                break;
            }
            i = 0;
            for (SequenceableLoader sequenceableLoader : this.loaders) {
                if (sequenceableLoader.getNextLoadPositionUs() == nextLoadPositionUs) {
                    i |= sequenceableLoader.continueLoading(j);
                }
            }
            z |= i;
        } while (i != 0);
        return z;
    }

    public final long getBufferedPositionUs() {
        long j = Long.MAX_VALUE;
        for (SequenceableLoader bufferedPositionUs : this.loaders) {
            long bufferedPositionUs2 = bufferedPositionUs.getBufferedPositionUs();
            if (bufferedPositionUs2 != Long.MIN_VALUE) {
                j = Math.min(j, bufferedPositionUs2);
            }
        }
        return j == Long.MAX_VALUE ? Long.MIN_VALUE : j;
    }

    public final long getNextLoadPositionUs() {
        long j = Long.MAX_VALUE;
        for (SequenceableLoader nextLoadPositionUs : this.loaders) {
            long nextLoadPositionUs2 = nextLoadPositionUs.getNextLoadPositionUs();
            if (nextLoadPositionUs2 != Long.MIN_VALUE) {
                j = Math.min(j, nextLoadPositionUs2);
            }
        }
        return j == Long.MAX_VALUE ? Long.MIN_VALUE : j;
    }
}
