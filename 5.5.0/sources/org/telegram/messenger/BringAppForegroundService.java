package org.telegram.messenger;

import android.app.IntentService;
import android.content.Intent;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import org.telegram.ui.LaunchActivity;

public class BringAppForegroundService extends IntentService {
    public BringAppForegroundService() {
        super("BringAppForegroundService");
    }

    protected void onHandleIntent(Intent intent) {
        Intent intent2 = new Intent(this, LaunchActivity.class);
        intent2.setFlags(ErrorDialogData.BINDER_CRASH);
        startActivity(intent2);
    }
}
