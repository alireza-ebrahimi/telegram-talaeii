package utils.view.VideoController;

import android.media.MediaPlayer.OnCompletionListener;
import android.telephony.PhoneStateListener;
import android.widget.VideoView;
import org.telegram.messenger.FileLog;

public class FullScreenVideoView extends VideoView {
    /* renamed from: a */
    private int f10314a;
    /* renamed from: b */
    private int f10315b;

    /* renamed from: utils.view.VideoController.FullScreenVideoView$1 */
    class C53291 extends PhoneStateListener {
        /* renamed from: a */
        final /* synthetic */ FullScreenVideoView f10313a;

        public void onCallStateChanged(int i, String str) {
            if (i == 1) {
                FileLog.d("telegram call is ringing...");
                if (this.f10313a.isPlaying()) {
                    this.f10313a.pause();
                }
            } else if (i == 0) {
                FileLog.d("telegram call is idle...");
                if (!this.f10313a.isPlaying()) {
                    this.f10313a.start();
                }
            } else if (i == 2) {
                FileLog.d("telegram call is offhook...");
                if (this.f10313a.isPlaying()) {
                    this.f10313a.pause();
                }
            }
            super.onCallStateChanged(i, str);
        }
    }

    public int getVideoHeight() {
        return this.f10315b;
    }

    public int getVideoWidth() {
        return this.f10314a;
    }

    protected void onMeasure(int i, int i2) {
        int defaultSize = getDefaultSize(this.f10314a, i);
        int defaultSize2 = getDefaultSize(this.f10315b, i2);
        if (this.f10314a > 0 && this.f10315b > 0) {
            if (this.f10314a * defaultSize2 > this.f10315b * defaultSize) {
                defaultSize2 = (int) Math.ceil((double) ((((float) defaultSize2) * ((float) defaultSize)) / ((float) defaultSize)));
            } else if (this.f10314a * defaultSize2 < this.f10315b * defaultSize) {
                defaultSize2 = (int) Math.ceil((double) ((((float) defaultSize2) * ((float) defaultSize)) / ((float) defaultSize)));
            }
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    public void seekTo(int i) {
        FileLog.d("telegram canSeekBackward: " + canSeekBackward() + " canSeekForward: " + canSeekForward());
        try {
            super.seekTo(i);
        } catch (Exception e) {
            FileLog.d("telegram" + e.getMessage());
        }
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        super.setOnCompletionListener(onCompletionListener);
        seekTo(0);
    }

    public void setVideoHeight(int i) {
        this.f10315b = i;
    }

    public void setVideoWidth(int i) {
        this.f10314a = i;
    }
}
