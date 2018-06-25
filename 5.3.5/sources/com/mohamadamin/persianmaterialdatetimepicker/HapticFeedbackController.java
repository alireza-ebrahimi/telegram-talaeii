package com.mohamadamin.persianmaterialdatetimepicker;

import android.content.Context;
import android.database.ContentObserver;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings.System;

public class HapticFeedbackController {
    private static final int VIBRATE_DELAY_MS = 125;
    private static final int VIBRATE_LENGTH_MS = 5;
    private final ContentObserver mContentObserver = new ContentObserver(null) {
        public void onChange(boolean selfChange) {
            HapticFeedbackController.this.mIsGloballyEnabled = HapticFeedbackController.checkGlobalSetting(HapticFeedbackController.this.mContext);
        }
    };
    private final Context mContext;
    private boolean mIsGloballyEnabled;
    private long mLastVibrate;
    private Vibrator mVibrator;

    private static boolean checkGlobalSetting(Context context) {
        return System.getInt(context.getContentResolver(), "haptic_feedback_enabled", 0) == 1;
    }

    public HapticFeedbackController(Context context) {
        this.mContext = context;
    }

    public void start() {
        this.mVibrator = (Vibrator) this.mContext.getSystemService("vibrator");
        this.mIsGloballyEnabled = checkGlobalSetting(this.mContext);
        this.mContext.getContentResolver().registerContentObserver(System.getUriFor("haptic_feedback_enabled"), false, this.mContentObserver);
    }

    public void stop() {
        this.mVibrator = null;
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    public void tryVibrate() {
        if (this.mVibrator != null && this.mIsGloballyEnabled) {
            long now = SystemClock.uptimeMillis();
            if (now - this.mLastVibrate >= 125) {
                this.mVibrator.vibrate(5);
                this.mLastVibrate = now;
            }
        }
    }
}
