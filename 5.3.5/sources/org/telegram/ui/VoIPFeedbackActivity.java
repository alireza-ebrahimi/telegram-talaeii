package org.telegram.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import org.telegram.ui.Components.voip.VoIPHelper;

public class VoIPFeedbackActivity extends Activity {

    /* renamed from: org.telegram.ui.VoIPFeedbackActivity$1 */
    class C34491 implements Runnable {
        C34491() {
        }

        public void run() {
            VoIPFeedbackActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(524288);
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(new View(this));
        VoIPHelper.showRateAlert(this, new C34491(), getIntent().getLongExtra("call_id", 0), getIntent().getLongExtra("call_access_hash", 0));
    }

    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
