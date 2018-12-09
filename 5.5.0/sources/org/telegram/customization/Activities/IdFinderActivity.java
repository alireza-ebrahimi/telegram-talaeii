package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import org.ir.talaeii.R;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.LaunchActivity;

public class IdFinderActivity extends Activity implements OnClickListener {
    /* renamed from: a */
    View f8354a;
    /* renamed from: b */
    View f8355b;
    /* renamed from: c */
    View f8356c;
    /* renamed from: d */
    EditText f8357d;

    /* renamed from: org.telegram.customization.Activities.IdFinderActivity$1 */
    class C24981 implements OnEditorActionListener {
        /* renamed from: a */
        final /* synthetic */ IdFinderActivity f8353a;

        C24981(IdFinderActivity idFinderActivity) {
            this.f8353a = idFinderActivity;
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 3) {
                return false;
            }
            this.f8353a.m12229b();
            return true;
        }
    }

    /* renamed from: a */
    private void m12227a() {
        this.f8354a = findViewById(R.id.cancel);
        this.f8355b = findViewById(R.id.download);
        this.f8356c = findViewById(R.id.iv_close);
        this.f8357d = (EditText) findViewById(R.id.et_id);
        this.f8354a.setOnClickListener(this);
        this.f8355b.setOnClickListener(this);
        this.f8356c.setOnClickListener(this);
        this.f8357d.setOnEditorActionListener(new C24981(this));
    }

    /* renamed from: b */
    private void m12229b() {
        if (TextUtils.isEmpty(this.f8357d.getText().toString())) {
            Toast.makeText(getApplicationContext(), getString(R.string.err_empty_id), 0).show();
            return;
        }
        finish();
        MessagesController.openByUserName(this.f8357d.getText().toString(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
            case R.id.cancel:
                finish();
                return;
            case R.id.download:
                m12229b();
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_id_finder);
        m12227a();
    }
}
