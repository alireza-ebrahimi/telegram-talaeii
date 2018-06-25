package utils.view.VideoController;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer.OnCompletionListener;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.widget.VideoView;
import org.telegram.messenger.FileLog;

public class FullScreenVideoView extends VideoView {
    Activity activity;
    private int mForceHeight;
    private int mForceWidth;
    PhoneStateListener phoneStateListener = new C34811();
    private int videoHeight;
    private int videoWidth;

    /* renamed from: utils.view.VideoController.FullScreenVideoView$1 */
    class C34811 extends PhoneStateListener {
        C34811() {
        }

        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == 1) {
                FileLog.d("telegram call is ringing...");
                if (FullScreenVideoView.this.isPlaying()) {
                    FullScreenVideoView.this.pause();
                }
            } else if (state == 0) {
                FileLog.d("telegram call is idle...");
                if (!FullScreenVideoView.this.isPlaying()) {
                    FullScreenVideoView.this.start();
                }
            } else if (state == 2) {
                FileLog.d("telegram call is offhook...");
                if (FullScreenVideoView.this.isPlaying()) {
                    FullScreenVideoView.this.pause();
                }
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void seekTo(int msec) {
        FileLog.d("telegram canSeekBackward: " + canSeekBackward() + " canSeekForward: " + canSeekForward());
        try {
            super.seekTo(msec);
        } catch (Exception e) {
            FileLog.d("telegram" + e.getMessage());
        }
    }

    public void registerPhoneListner(Activity activity) {
        this.activity = activity;
        TelephonyManager mgr = (TelephonyManager) activity.getSystemService("phone");
        if (mgr != null) {
            mgr.listen(this.phoneStateListener, 32);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(this.videoWidth, widthMeasureSpec);
        int height = getDefaultSize(this.videoHeight, heightMeasureSpec);
        if (this.videoWidth > 0 && this.videoHeight > 0) {
            if (this.videoWidth * height > this.videoHeight * width) {
                height = (int) Math.ceil((double) ((((float) width) * ((float) height)) / ((float) width)));
            } else if (this.videoWidth * height < this.videoHeight * width) {
                height = (int) Math.ceil((double) ((((float) width) * ((float) height)) / ((float) width)));
            }
        }
        setMeasuredDimension(width, height);
    }

    public void setOnCompletionListener(OnCompletionListener l) {
        super.setOnCompletionListener(l);
        seekTo(0);
    }

    public int getVideoWidth() {
        return this.videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return this.videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }
}
