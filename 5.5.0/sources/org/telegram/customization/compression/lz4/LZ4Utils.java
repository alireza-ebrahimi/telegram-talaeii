package org.telegram.customization.compression.lz4;

public enum LZ4Utils {
    ;
    
    private static final int MAX_INPUT_SIZE = 2113929216;

    public static class Match {
        int len;
        int ref;
        int start;

        int end() {
            return this.start + this.len;
        }

        void fix(int i) {
            this.start += i;
            this.ref += i;
            this.len -= i;
        }
    }

    public static void copyTo(Match match, Match match2) {
        match2.len = match.len;
        match2.start = match.start;
        match2.ref = match.ref;
    }

    public static int hash(int i) {
        return (-1640531535 * i) >>> 20;
    }

    public static int hash64k(int i) {
        return (-1640531535 * i) >>> 19;
    }

    public static int hashHC(int i) {
        return (-1640531535 * i) >>> 17;
    }

    public static int maxCompressedLength(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("length must be >= 0, got " + i);
        } else if (i < MAX_INPUT_SIZE) {
            return ((i / 255) + i) + 16;
        } else {
            throw new IllegalArgumentException("length must be < 2113929216");
        }
    }
}
