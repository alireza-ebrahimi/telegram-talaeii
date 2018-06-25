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
    class C37031 implements Runnable {
        C37031() {
        }

        public void run() {
            ByteBuffer byteBuffer = null;
            try {
                AudioTrackJNI.this.audioTrack.play();
                ByteBuffer allocateDirect = AudioTrackJNI.this.needResampling ? ByteBuffer.allocateDirect(1920) : null;
                if (AudioTrackJNI.this.needResampling) {
                    byteBuffer = ByteBuffer.allocateDirect(1764);
                }
                while (AudioTrackJNI.this.running) {
                    try {
                        if (AudioTrackJNI.this.needResampling) {
                            AudioTrackJNI.this.nativeCallback(AudioTrackJNI.this.buffer);
                            allocateDirect.rewind();
                            allocateDirect.put(AudioTrackJNI.this.buffer);
                            Resampler.convert48to44(allocateDirect, byteBuffer);
                            byteBuffer.rewind();
                            byteBuffer.get(AudioTrackJNI.this.buffer, 0, 1764);
                            AudioTrackJNI.this.audioTrack.write(AudioTrackJNI.this.buffer, 0, 1764);
                        } else {
                            AudioTrackJNI.this.nativeCallback(AudioTrackJNI.this.buffer);
                            AudioTrackJNI.this.audioTrack.write(AudioTrackJNI.this.buffer, 0, 1920);
                        }
                        if (!AudioTrackJNI.this.running) {
                            AudioTrackJNI.this.audioTrack.stop();
                            break;
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
                Log.i("tg-voip", "audiotrack thread exits");
            } catch (Throwable e2) {
                FileLog.m13727e("error starting AudioTrack", e2);
            }
        }
    }

    public AudioTrackJNI(long j) {
        this.nativeInst = j;
    }

    private int getBufferSize(int i, int i2) {
        return Math.max(AudioTrack.getMinBufferSize(i2, 4, 2), i);
    }

    private native void nativeCallback(byte[] bArr);

    private void startThread() {
        if (this.thread != null) {
            throw new IllegalStateException("thread already started");
        }
        this.running = true;
        this.thread = new Thread(new C37031());
        this.thread.start();
    }

    public void init(int i, int i2, int i3, int i4) {
        if (this.audioTrack != null) {
            throw new IllegalStateException("already inited");
        }
        int bufferSize = getBufferSize(i4, 48000);
        this.bufferSize = i4;
        this.audioTrack = new AudioTrack(0, 48000, i3 == 1 ? 4 : 12, 2, bufferSize, 1);
        if (this.audioTrack.getState() != 1) {
            try {
                this.audioTrack.release();
            } catch (Throwable th) {
            }
            bufferSize = getBufferSize(i4 * 6, 44100);
            FileLog.m13725d("buffer size: " + bufferSize);
            this.audioTrack = new AudioTrack(0, 44100, i3 == 1 ? 4 : 12, 2, bufferSize, 1);
            this.needResampling = true;
        }
    }

    public void release() {
        this.running = false;
        if (this.thread != null) {
            try {
                this.thread.join();
            } catch (Throwable e) {
                FileLog.m13728e(e);
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

    public void stop() {
        if (this.audioTrack != null) {
            this.audioTrack.stop();
        }
    }
}
