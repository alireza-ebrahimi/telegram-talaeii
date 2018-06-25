package org.telegram.messenger.voip;

import android.media.AudioTrack;
import android.util.Log;
import java.nio.ByteBuffer;
import org.telegram.messenger.FileLog;

public class AudioTrackJNI {
    private AudioTrack audioTrack;
    private byte[] buffer = new byte[1920];
    private int bufferSize;
    private long nativeInst;
    private boolean needResampling;
    private boolean running;
    private Thread thread;

    /* renamed from: org.telegram.messenger.voip.AudioTrackJNI$1 */
    class C19021 implements Runnable {
        C19021() {
        }

        public void run() {
            try {
                ByteBuffer tmp48;
                ByteBuffer tmp44;
                AudioTrackJNI.this.audioTrack.play();
                if (AudioTrackJNI.this.needResampling) {
                    tmp48 = ByteBuffer.allocateDirect(1920);
                } else {
                    tmp48 = null;
                }
                if (AudioTrackJNI.this.needResampling) {
                    tmp44 = ByteBuffer.allocateDirect(1764);
                } else {
                    tmp44 = null;
                }
                while (AudioTrackJNI.this.running) {
                    try {
                        if (AudioTrackJNI.this.needResampling) {
                            AudioTrackJNI.this.nativeCallback(AudioTrackJNI.this.buffer);
                            tmp48.rewind();
                            tmp48.put(AudioTrackJNI.this.buffer);
                            Resampler.convert48to44(tmp48, tmp44);
                            tmp44.rewind();
                            tmp44.get(AudioTrackJNI.this.buffer, 0, 1764);
                            AudioTrackJNI.this.audioTrack.write(AudioTrackJNI.this.buffer, 0, 1764);
                        } else {
                            AudioTrackJNI.this.nativeCallback(AudioTrackJNI.this.buffer);
                            AudioTrackJNI.this.audioTrack.write(AudioTrackJNI.this.buffer, 0, 1920);
                        }
                        if (!AudioTrackJNI.this.running) {
                            AudioTrackJNI.this.audioTrack.stop();
                            break;
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
                Log.i("tg-voip", "audiotrack thread exits");
            } catch (Exception x) {
                FileLog.e("error starting AudioTrack", x);
            }
        }
    }

    private native void nativeCallback(byte[] bArr);

    public AudioTrackJNI(long nativeInst) {
        this.nativeInst = nativeInst;
    }

    private int getBufferSize(int min, int sampleRate) {
        return Math.max(AudioTrack.getMinBufferSize(sampleRate, 4, 2), min);
    }

    public void init(int sampleRate, int bitsPerSample, int channels, int bufferSize) {
        if (this.audioTrack != null) {
            throw new IllegalStateException("already inited");
        }
        int size = getBufferSize(bufferSize, 48000);
        this.bufferSize = bufferSize;
        this.audioTrack = new AudioTrack(0, 48000, channels == 1 ? 4 : 12, 2, size, 1);
        if (this.audioTrack.getState() != 1) {
            int i;
            try {
                this.audioTrack.release();
            } catch (Throwable th) {
            }
            size = getBufferSize(bufferSize * 6, 44100);
            FileLog.d("buffer size: " + size);
            if (channels == 1) {
                i = 4;
            } else {
                i = 12;
            }
            this.audioTrack = new AudioTrack(0, 44100, i, 2, size, 1);
            this.needResampling = true;
        }
    }

    public void stop() {
        if (this.audioTrack != null) {
            this.audioTrack.stop();
        }
    }

    public void release() {
        this.running = false;
        if (this.thread != null) {
            try {
                this.thread.join();
            } catch (InterruptedException e) {
                FileLog.e(e);
            }
            this.thread = null;
        }
        if (this.audioTrack != null) {
            this.audioTrack.release();
            this.audioTrack = null;
        }
    }

    public void start() {
        if (this.thread == null) {
            startThread();
        } else {
            this.audioTrack.play();
        }
    }

    private void startThread() {
        if (this.thread != null) {
            throw new IllegalStateException("thread already started");
        }
        this.running = true;
        this.thread = new Thread(new C19021());
        this.thread.start();
    }
}
