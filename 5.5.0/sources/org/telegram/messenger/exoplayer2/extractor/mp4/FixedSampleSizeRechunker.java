package org.telegram.messenger.exoplayer2.extractor.mp4;

import org.telegram.messenger.exoplayer2.util.Util;

final class FixedSampleSizeRechunker {
    private static final int MAX_SAMPLE_SIZE = 8192;

    public static final class Results {
        public final int[] flags;
        public final int maximumSize;
        public final long[] offsets;
        public final int[] sizes;
        public final long[] timestamps;

        private Results(long[] jArr, int[] iArr, int i, long[] jArr2, int[] iArr2) {
            this.offsets = jArr;
            this.sizes = iArr;
            this.maximumSize = i;
            this.timestamps = jArr2;
            this.flags = iArr2;
        }
    }

    FixedSampleSizeRechunker() {
    }

    public static Results rechunk(int i, long[] jArr, int[] iArr, long j) {
        int i2 = 8192 / i;
        int i3 = 0;
        int i4 = 0;
        while (i3 < iArr.length) {
            i3++;
            i4 = Util.ceilDivide(iArr[i3], i2) + i4;
        }
        long[] jArr2 = new long[i4];
        int[] iArr2 = new int[i4];
        long[] jArr3 = new long[i4];
        int[] iArr3 = new int[i4];
        i3 = 0;
        i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i3 < iArr.length) {
            int i7 = iArr[i3];
            long j2 = jArr[i3];
            int i8 = i6;
            i6 = i5;
            i5 = i4;
            i4 = i8;
            while (i7 > 0) {
                int min = Math.min(i2, i7);
                jArr2[i4] = j2;
                iArr2[i4] = i * min;
                int max = Math.max(i5, iArr2[i4]);
                jArr3[i4] = ((long) i6) * j;
                iArr3[i4] = 1;
                j2 += (long) iArr2[i4];
                i4++;
                i7 -= min;
                i6 += min;
                i5 = max;
            }
            i3++;
            i8 = i4;
            i4 = i5;
            i5 = i6;
            i6 = i8;
        }
        return new Results(jArr2, iArr2, i4, jArr3, iArr3);
    }
}
