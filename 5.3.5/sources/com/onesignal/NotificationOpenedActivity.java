package com.onesignal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class NotificationOpenedActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationOpenedProcessor.processFromContext(this, getIntent());
        finish();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        NotificationOpenedProcessor.processFromContext(this, getIntent());
        finish();
    }
}
