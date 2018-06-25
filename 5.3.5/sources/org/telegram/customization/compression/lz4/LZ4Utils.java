package org.telegram.customization.compression.lz4;

public enum LZ4Utils {
    ;
    
    private static final int MAX_INPUT_SIZE = 2113929216;

    public static class Match {
        int len;
        int ref;
        int start;

        void fix(int correction) {
            this.start += correction;
            this.ref += correction;
            this.len -= correction;
        }

        int end() {
            return this.start + this.len;
        }
    }

    public static int maxCompressedLength(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must be >= 0, got " + length);
        } else if (length < MAX_INPUT_SIZE) {
            return ((length / 255) + length) + 16;
        } else {
            throw new IllegalArgumentException("length must be < 2113929216");
        }
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

    public static void copyTo(Match m1, Match m2) {
        m2.len = m1.len;
        m2.start = m1.start;
        m2.ref = m1.ref;
    }
}
