package org.telegram.messenger.exoplayer2.audio;

public final class AudioProcessor$UnhandledFormatException extends Exception {
    public AudioProcessor$UnhandledFormatException(int sampleRateHz, int channelCount, int encoding) {
        super("Unhandled format: " + sampleRateHz + " Hz, " + channelCount + " channels in encoding " + encoding);
    }
}
