package org.telegram.messenger.exoplayer2.audio;

public abstract class AudioDecoderException extends Exception {
    public AudioDecoderException(String str) {
        super(str);
    }

    public AudioDecoderException(String str, Throwable th) {
        super(str, th);
    }
}
