package org.telegram.messenger.exoplayer2.extractor;

public interface SeekMap {

    public static final class Unseekable implements SeekMap {
        private final long durationUs;

        public Unseekable(long j) {
            this.durationUs = j;
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPosition(long j) {
            return 0;
        }

        public boolean isSeekable() {
            return false;
        }
    }

    long getDurationUs();

    long getPosition(long j);

    boolean isSeekable();
}
