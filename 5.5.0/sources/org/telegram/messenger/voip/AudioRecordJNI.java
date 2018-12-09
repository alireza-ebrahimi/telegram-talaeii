package org.telegram.messenger.voip;

import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.os.Build.VERSION;
import android.util.Log;
import java.nio.ByteBuffer;
import org.telegram.messenger.FileLog;

public class AudioRecordJNI {
    private AcousticEchoCanceler aec;
    private AutomaticGainControl agc;
    private AudioRecord audioRecord;
    private ByteBuffer buffer;
    private int bufferSize;
    private long nativeInst;
    private boolean needResampling = false;
    private NoiseSuppressor ns;
    private boolean running;
    private Thread thread;

    public AudioRecordJNI(long j) {
        this.nativeInst = j;
    }

    private int getBufferSize(int i, int i2) {
        return Math.max(AudioRecord.getMinBufferSize(i2, 16, 2), i);
    }

    private native void nativeCallback(ByteBuffer byteBuffer);

    private void startThread() {
        if (this.thread != null) {
            throw new IllegalStateException("thread already started");
        }
        this.running = true;
        final ByteBuffer allocateDirect = this.needResampling ? ByteBuffer.allocateDirect(1764) : null;
        this.thread = new Thread(new Runnable() {
            public void run() {
                while (AudioRecordJNI.this.running) {
                    try {
                        if (AudioRecordJNI.this.needResampling) {
                            AudioRecordJNI.this.audioRecord.read(allocateDirect, 1764);
                            Resampler.convert44to48(allocateDirect, AudioRecordJNI.this.buffer);
                        } else {
                            AudioRecordJNI.this.audioRecord.read(AudioRecordJNI.this.buffer, 1920);
                        }
                        if (!AudioRecordJNI.this.running) {
                            AudioRecordJNI.this.audioRecord.stop();
                            break;
                        }
                        AudioRecordJNI.this.nativeCallback(AudioRecordJNI.this.buffer);
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
                Log.i("tg-voip", "audiotrack thread exits");
            }
        });
        this.thread.start();
    }

    private boolean tryInit(int i, int i2) {
        if (this.audioRecord != null) {
            try {
                this.audioRecord.release();
            } catch (Exception e) {
            }
        }
        FileLog.m13725d("Trying to initialize AudioRecord with source=" + i + " and sample rate=" + i2);
        try {
            this.audioRecord = new AudioRecord(i, i2, 16, 2, getBufferSize(this.bufferSize, 48000));
        } catch (Throwable e2) {
            FileLog.m13727e("AudioRecord init failed!", e2);
        }
        this.needResampling = i2 != 48000;
        return this.audioRecord != null && this.audioRecord.getState() == 1;
    }

    public void init(int i, int i2, int i3, int i4) {
        if (this.audioRecord != null) {
            throw new IllegalStateException("already inited");
        }
        this.bufferSize = i4;
        boolean tryInit = tryInit(7, 48000);
        if (!tryInit) {
            tryInit(1, 48000);
        }
        if (!tryInit) {
            tryInit(7, 44100);
        }
        if (!tryInit) {
            tryInit(1, 44100);
        }
        this.buffer = ByteBuffer.allocateDirect(i4);
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
        if (this.audioRecord != null) {
            this.audioRecord.release();
            this.audioRecord = null;
        }
        if (this.agc != null) {
            this.agc.release();
            this.agc = null;
        }
        if (this.ns != null) {
            this.ns.release();
            this.ns = null;
        }
        if (this.aec != null) {
            this.aec.release();
            this.aec = null;
        }
    }

    public boolean start() {
        try {
            if (this.thread != null) {
                this.audioRecord.startRecording();
            } else if (this.audioRecord == null) {
                return false;
            } else {
                this.audioRecord.startRecording();
                if (VERSION.SDK_INT >= 16) {
                    try {
                        if (AutomaticGainControl.isAvailable()) {
                            this.agc = AutomaticGainControl.create(this.audioRecord.getAudioSessionId());
                            if (this.agc != null) {
                                this.agc.setEnabled(false);
                            }
                        } else {
                            FileLog.m13729w("AutomaticGainControl is not available on this device :(");
                        }
                    } catch (Throwable th) {
                        FileLog.m13727e("error creating AutomaticGainControl", th);
                    }
                    try {
                        if (NoiseSuppressor.isAvailable()) {
                            this.ns = NoiseSuppressor.create(this.audioRecord.getAudioSessionId());
                            if (this.ns != null) {
                                this.ns.setEnabled(VoIPServerConfig.getBoolean("user_system_ns", true));
                            }
                        } else {
                            FileLog.m13729w("NoiseSuppressor is not available on this device :(");
                        }
                    } catch (Throwable th2) {
                        FileLog.m13727e("error creating NoiseSuppressor", th2);
                    }
                    try {
                        if (AcousticEchoCanceler.isAvailable()) {
                            this.aec = AcousticEchoCanceler.create(this.audioRecord.getAudioSessionId());
                            if (this.aec != null) {
                                this.aec.setEnabled(VoIPServerConfig.getBoolean("use_system_aec", true));
                            }
                        } else {
                            FileLog.m13729w("AcousticEchoCanceler is not available on this device");
                        }
                    } catch (Throwable th22) {
                        FileLog.m13727e("error creating AcousticEchoCanceler", th22);
                    }
                }
                startThread();
            }
            return true;
        } catch (Throwable e) {
            FileLog.m13727e("Error initializing AudioRecord", e);
            return false;
        }
    }

    public void stop() {
        if (this.audioRecord != null) {
            this.audioRecord.stop();
        }
    }
}
