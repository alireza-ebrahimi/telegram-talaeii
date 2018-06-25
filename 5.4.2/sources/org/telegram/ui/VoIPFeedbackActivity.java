package org.telegram.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import org.telegram.ui.Components.voip.VoIPHelper;

public class VoIPFeedbackActivity extends Activity {

    /* renamed from: org.telegram.ui.VoIPFeedbackActivity$1 */
    class C52881 implements Runnable {
        C52881() {
        }

        public void run() {
            VoIPFeedbackActivity.this.finish();
        }
    }

    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    protected void onCreate(Bundle bundle) {
        getWindow().addFlags(524288);
        super.onCreate(bundle);
        overridePendingTransition(0, 0);
        setContentView(new View(this));
        VoIPHelper.showRateAlert(this, new C52881(), getIntent().getLongExtra("call_id", 0), getIntent().getLongExtra("call_access_hash", 0));
    }
}
