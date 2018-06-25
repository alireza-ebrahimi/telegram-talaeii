package org.telegram.messenger.exoplayer2.extractor;

public interface SeekMap {

    public static final class Unseekable implements SeekMap {
        private final long durationUs;

        public Unseekable(long durationUs) {
            this.durationUs = durationUs;
        }

        public boolean isSeekable() {
            return false;
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPosition(long timeUs) {
            return 0;
        }
    }

    long getDurationUs();

    long getPosition(long j);

    boolean isSeekable();
}
