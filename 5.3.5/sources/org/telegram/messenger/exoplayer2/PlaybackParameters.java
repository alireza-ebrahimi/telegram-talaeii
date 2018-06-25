package org.telegram.messenger.exoplayer2;

public final class PlaybackParameters {
    public static final PlaybackParameters DEFAULT = new PlaybackParameters(1.0f, 1.0f);
    public final float pitch;
    private final int scaledUsPerMs;
    public final float speed;

    public PlaybackParameters(float speed, float pitch) {
        this.speed = speed;
        this.pitch = pitch;
        this.scaledUsPerMs = Math.round(1000.0f * speed);
    }

    public long getSpeedAdjustedDurationUs(long timeMs) {
        return ((long) this.scaledUsPerMs) * timeMs;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PlaybackParameters other = (PlaybackParameters) obj;
        if (this.speed == other.speed && this.pitch == other.pitch) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((Float.floatToRawIntBits(this.speed) + 527) * 31) + Float.floatToRawIntBits(this.pitch);
    }
}
