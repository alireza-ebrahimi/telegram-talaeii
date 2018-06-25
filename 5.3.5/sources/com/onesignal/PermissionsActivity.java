package com.onesignal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class PermissionsActivity extends Activity {
    private static final int REQUEST_LOCATION = 2;
    private static ActivityAvailableListener activityAvailableListener;
    static boolean answered;
    static boolean waiting;

    /* renamed from: com.onesignal.PermissionsActivity$1 */
    static class C06901 implements ActivityAvailableListener {
        C06901() {
        }

        public void available(Activity activity) {
            if (!activity.getClass().equals(PermissionsActivity.class)) {
                Intent intent = new Intent(activity, PermissionsActivity.class);
                intent.setFlags(131072);
                activity.startActivity(intent);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.getBoolean("android:hasCurrentPermissionsRequest", false)) {
            requestPermission();
        } else {
            waiting = true;
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (OneSignal.initDone) {
            requestPermission();
        }
    }

    private void requestPermission() {
        if (VERSION.SDK_INT < 23) {
            finish();
        } else if (!waiting) {
            waiting = true;
            ActivityCompat.requestPermissions(this, new String[]{LocationGMS.requestPermission}, 2);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        answered = true;
        waiting = false;
        if (requestCode == 2) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                LocationGMS.fireFailedComplete();
            } else {
                LocationGMS.startGetLocation();
            }
        }
        ActivityLifecycleHandler.removeActivityAvailableListener(activityAvailableListener);
        finish();
    }

    static void startPrompt() {
        if (!waiting && !answered) {
            activityAvailableListener = new C06901();
            ActivityLifecycleHandler.setActivityAvailableListener(activityAvailableListener);
        }
    }
}
