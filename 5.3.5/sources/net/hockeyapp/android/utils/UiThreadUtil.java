package net.hockeyapp.android.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;
import java.lang.ref.WeakReference;

public class UiThreadUtil {

    private static class WbUtilHolder {
        public static final UiThreadUtil INSTANCE = new UiThreadUtil();

        private WbUtilHolder() {
        }
    }

    private UiThreadUtil() {
    }

    public static UiThreadUtil getInstance() {
        return WbUtilHolder.INSTANCE;
    }

    public void dismissLoadingDialogAndDisplayError(WeakReference<Activity> weakActivity, final ProgressDialog progressDialog, final int errorDialogId) {
        if (weakActivity != null) {
            final Activity activity = (Activity) weakActivity.get();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        activity.showDialog(errorDialogId);
                    }
                });
            }
        }
    }

    public void dismissLoading(WeakReference<Activity> weakActivity, final ProgressDialog progressDialog) {
        if (weakActivity != null) {
            Activity activity = (Activity) weakActivity.get();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        }
    }

    public void displayToastMessage(WeakReference<Activity> weakActivity, final String message, final int flags) {
        if (weakActivity != null) {
            final Activity activity = (Activity) weakActivity.get();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity, message, flags).show();
                    }
                });
            }
        }
    }
}
