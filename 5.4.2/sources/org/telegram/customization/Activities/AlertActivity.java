package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import org.ir.talaeii.R;

public class AlertActivity extends Activity {
    /* renamed from: a */
    TextView f8341a;
    /* renamed from: b */
    TextView f8342b;

    /* renamed from: org.telegram.customization.Activities.AlertActivity$1 */
    class C24921 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ AlertActivity f8340a;

        C24921(AlertActivity alertActivity) {
            this.f8340a = alertActivity;
        }

        public void onClick(View view) {
            System.exit(0);
        }
    }

    public void onBackPressed() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_alert);
        this.f8341a = (TextView) findViewById(R.id.btn_done);
        this.f8342b = (TextView) findViewById(R.id.ftv_msg);
        CharSequence stringExtra = getIntent().getStringExtra("EXTRA_UPDATE_MODEL");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.f8342b.setText(stringExtra);
        }
        this.f8341a.setOnClickListener(new C24921(this));
    }
}
