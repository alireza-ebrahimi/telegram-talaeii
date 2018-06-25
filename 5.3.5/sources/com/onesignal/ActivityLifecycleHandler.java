package com.onesignal;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.onesignal.OneSignal.LOG_LEVEL;

class ActivityLifecycleHandler {
    static Activity curActivity;
    static FocusHandlerThread focusHandlerThread = new FocusHandlerThread();
    private static ActivityAvailableListener mActivityAvailableListener;
    static boolean nextResumeIsFirstActivity;

    interface ActivityAvailableListener {
        void available(Activity activity);
    }

    private static class AppFocusRunnable implements Runnable {
        private boolean backgrounded;
        private boolean completed;

        private AppFocusRunnable() {
        }

        public void run() {
            if (ActivityLifecycleHandler.curActivity == null) {
                this.backgrounded = true;
                OneSignal.onAppLostFocus();
                this.completed = true;
            }
        }
    }

    static class FocusHandlerThread extends HandlerThread {
        private AppFocusRunnable appFocusRunnable;
        Handler mHandler = null;

        FocusHandlerThread() {
            super("FocusHandlerThread");
            start();
            this.mHandler = new Handler(getLooper());
        }

        Looper getHandlerLooper() {
            return this.mHandler.getLooper();
        }

        void resetBackgroundState() {
            if (this.appFocusRunnable != null) {
                this.appFocusRunnable.backgrounded = false;
            }
        }

        void stopScheduledRunnable() {
            this.mHandler.removeCallbacksAndMessages(null);
        }

        void runRunnable(AppFocusRunnable runnable) {
            if (this.appFocusRunnable == null || !this.appFocusRunnable.backgrounded || this.appFocusRunnable.completed) {
                this.appFocusRunnable = runnable;
                this.mHandler.removeCallbacksAndMessages(null);
                this.mHandler.postDelayed(runnable, FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
            }
        }

        boolean hasBackgrounded() {
            return this.appFocusRunnable != null && this.appFocusRunnable.backgrounded;
        }
    }

    ActivityLifecycleHandler() {
    }

    static void setActivityAvailableListener(ActivityAvailableListener activityAvailableListener) {
        if (curActivity != null) {
            activityAvailableListener.available(curActivity);
            mActivityAvailableListener = activityAvailableListener;
            return;
        }
        mActivityAvailableListener = activityAvailableListener;
    }

    public static void removeActivityAvailableListener(ActivityAvailableListener activityAvailableListener) {
        mActivityAvailableListener = null;
    }

    private static void setCurActivity(Activity activity) {
        curActivity = activity;
        if (mActivityAvailableListener != null) {
            mActivityAvailableListener.available(curActivity);
        }
    }

    static void onActivityCreated(Activity activity) {
    }

    static void onActivityStarted(Activity activity) {
    }

    static void onActivityResumed(Activity activity) {
        setCurActivity(activity);
        logCurActivity();
        handleFocus();
    }

    static void onActivityPaused(Activity activity) {
        if (activity == curActivity) {
            curActivity = null;
            handleLostFocus();
        }
        logCurActivity();
    }

    static void onActivityStopped(Activity activity) {
        OneSignal.Log(LOG_LEVEL.DEBUG, "onActivityStopped: " + activity.getClass().getName());
        if (activity == curActivity) {
            curActivity = null;
            handleLostFocus();
        }
        logCurActivity();
    }

    static void onActivityDestroyed(Activity activity) {
        OneSignal.Log(LOG_LEVEL.DEBUG, "onActivityDestroyed: " + activity.getClass().getName());
        if (activity == curActivity) {
            curActivity = null;
            handleLostFocus();
        }
        logCurActivity();
    }

    private static void logCurActivity() {
        OneSignal.Log(LOG_LEVEL.DEBUG, "curActivity is NOW: " + (curActivity != null ? "" + curActivity.getClass().getName() + ":" + curActivity : "null"));
    }

    private static void handleLostFocus() {
        focusHandlerThread.runRunnable(new AppFocusRunnable());
    }

    private static void handleFocus() {
        if (focusHandlerThread.hasBackgrounded() || nextResumeIsFirstActivity) {
            nextResumeIsFirstActivity = false;
            focusHandlerThread.resetBackgroundState();
            OneSignal.onAppFocus();
            return;
        }
        focusHandlerThread.stopScheduledRunnable();
    }
}
