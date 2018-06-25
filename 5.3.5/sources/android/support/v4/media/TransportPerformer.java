package android.support.v4.media;

import android.os.SystemClock;
import android.view.KeyEvent;

@Deprecated
public abstract class TransportPerformer {
    static final int AUDIOFOCUS_GAIN = 1;
    static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
    static final int AUDIOFOCUS_LOSS = -1;
    static final int AUDIOFOCUS_LOSS_TRANSIENT = -2;
    static final int AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;

    @Deprecated
    public abstract long onGetCurrentPosition();

    @Deprecated
    public abstract long onGetDuration();

    @Deprecated
    public abstract boolean onIsPlaying();

    @Deprecated
    public abstract void onPause();

    @Deprecated
    public abstract void onSeekTo(long j);

    @Deprecated
    public abstract void onStart();

    @Deprecated
    public abstract void onStop();

    @Deprecated
    public int onGetBufferPercentage() {
        return 100;
    }

    @Deprecated
    public int onGetTransportControlFlags() {
        return 60;
    }

    @Deprecated
    public boolean onMediaButtonDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 79:
            case 85:
                if (!onIsPlaying()) {
                    onStart();
                    break;
                }
                onPause();
                break;
            case 86:
                onStop();
                break;
            case TransportMediator.KEYCODE_MEDIA_PLAY /*126*/:
                onStart();
                break;
            case 127:
                onPause();
                break;
        }
        return true;
    }

    @Deprecated
    public boolean onMediaButtonUp(int keyCode, KeyEvent event) {
        return true;
    }

    @Deprecated
    public void onAudioFocusChange(int focusChange) {
        int keyCode = 0;
        switch (focusChange) {
            case -1:
                keyCode = 127;
                break;
        }
        if (keyCode != 0) {
            long now = SystemClock.uptimeMillis();
            onMediaButtonDown(keyCode, new KeyEvent(now, now, 0, keyCode, 0));
            onMediaButtonUp(keyCode, new KeyEvent(now, now, 1, keyCode, 0));
        }
    }
}
