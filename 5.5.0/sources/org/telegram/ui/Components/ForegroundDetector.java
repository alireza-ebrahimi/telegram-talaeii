package org.telegram.ui.Components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import org.telegram.messenger.FileLog;

@SuppressLint({"NewApi"})
public class ForegroundDetector implements ActivityLifecycleCallbacks {
    private static ForegroundDetector Instance = null;
    private long enterBackgroundTime = 0;
    private CopyOnWriteArrayList<ForegroundDetector$Listener> listeners = new CopyOnWriteArrayList();
    private int refs;
    private boolean wasInBackground = true;

    public ForegroundDetector(Application application) {
        Instance = this;
        application.registerActivityLifecycleCallbacks(this);
    }

    public static ForegroundDetector getInstance() {
        return Instance;
    }

    public void addListener(ForegroundDetector$Listener foregroundDetector$Listener) {
        this.listeners.add(foregroundDetector$Listener);
    }

    public boolean isBackground() {
        return this.refs == 0;
    }

    public boolean isForeground() {
        return this.refs > 0;
    }

    public boolean isWasInBackground(boolean z) {
        if (z && VERSION.SDK_INT >= 21 && System.currentTimeMillis() - this.enterBackgroundTime < 200) {
            this.wasInBackground = false;
        }
        return this.wasInBackground;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
        int i = this.refs + 1;
        this.refs = i;
        if (i == 1) {
            if (System.currentTimeMillis() - this.enterBackgroundTime < 200) {
                this.wasInBackground = false;
            }
            FileLog.m13726e("switch to foreground");
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                try {
                    ((ForegroundDetector$Listener) it.next()).onBecameForeground();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        }
    }

    public void onActivityStopped(Activity activity) {
        int i = this.refs - 1;
        this.refs = i;
        if (i == 0) {
            this.enterBackgroundTime = System.currentTimeMillis();
            this.wasInBackground = true;
            FileLog.m13726e("switch to background");
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                try {
                    ((ForegroundDetector$Listener) it.next()).onBecameBackground();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        }
    }

    public void removeListener(ForegroundDetector$Listener foregroundDetector$Listener) {
        this.listeners.remove(foregroundDetector$Listener);
    }

    public void resetBackgroundVar() {
        this.wasInBackground = false;
    }
}
