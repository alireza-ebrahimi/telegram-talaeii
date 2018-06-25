package com.onesignal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Process;
import android.support.annotation.NonNull;
import android.util.Log;

class AndroidSupportV4Compat {

    static class ActivityCompat {
        ActivityCompat() {
        }

        static void requestPermissions(@NonNull Activity activity, @NonNull String[] permissions, int requestCode) {
            ActivityCompatApi23.requestPermissions(activity, permissions, requestCode);
        }
    }

    @TargetApi(23)
    static class ActivityCompatApi23 {
        ActivityCompatApi23() {
        }

        static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
            if (activity instanceof RequestPermissionsRequestCodeValidator) {
                ((RequestPermissionsRequestCodeValidator) activity).validateRequestPermissionsRequestCode(requestCode);
            }
            activity.requestPermissions(permissions, requestCode);
        }
    }

    static class ContextCompat {
        ContextCompat() {
        }

        static int checkSelfPermission(@NonNull Context context, @NonNull String permission) {
            try {
                return context.checkPermission(permission, Process.myPid(), Process.myUid());
            } catch (Throwable th) {
                Log.e("OneSignal", "checkSelfPermission failed, returning PERMISSION_DENIED");
                return -1;
            }
        }

        static int getColor(Context context, int id) {
            if (VERSION.SDK_INT > 22) {
                return context.getColor(id);
            }
            return context.getResources().getColor(id);
        }
    }

    interface RequestPermissionsRequestCodeValidator {
        void validateRequestPermissionsRequestCode(int i);
    }

    AndroidSupportV4Compat() {
    }
}
