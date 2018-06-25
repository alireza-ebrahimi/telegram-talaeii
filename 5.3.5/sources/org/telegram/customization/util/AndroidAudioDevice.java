package org.telegram.customization.util;

import android.media.AudioTrack;

public class AndroidAudioDevice {
    short[] buffer = new short[1024];
    AudioTrack track = new AudioTrack(3, 44100, 2, 2, AudioTrack.getMinBufferSize(44100, 2, 2), 1);

    public AndroidAudioDevice() {
        this.track.play();
    }

    public void writeSamples(float[] samples) {
        fillBuffer(samples);
        this.track.write(this.buffer, 0, samples.length);
    }

    private void fillBuffer(float[] samples) {
        if (this.buffer.length < samples.length) {
            this.buffer = new short[samples.length];
        }
        for (int i = 0; i < samples.length; i++) {
            this.buffer[i] = (short) ((int) (samples[i] * 32767.0f));
        }
    }
}
