package org.telegram.customization.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    View cancel;
    View close;
    EditText etId;
    View search;

    /* renamed from: org.telegram.customization.Activities.IdFinderActivity$1 */
    class C10251 implements OnEditorActionListener {
        C10251() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 3) {
                return false;
            }
            IdFinderActivity.this.performSearch();
            return true;
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_finder);
        initView();
    }

    private void initView() {
        this.cancel = findViewById(R.id.cancel);
        this.search = findViewById(R.id.download);
        this.close = findViewById(R.id.iv_close);
        this.etId = (EditText) findViewById(R.id.et_id);
        this.cancel.setOnClickListener(this);
        this.search.setOnClickListener(this);
        this.close.setOnClickListener(this);
        this.etId.setOnEditorActionListener(new C10251());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
            case R.id.cancel:
                finish();
                return;
            case R.id.download:
                performSearch();
                return;
            default:
                return;
        }
    }

    private void performSearch() {
        if (TextUtils.isEmpty(this.etId.getText().toString())) {
            Toast.makeText(getApplicationContext(), getString(R.string.err_empty_id), 0).show();
            return;
        }
        finish();
        MessagesController.openByUserName(this.etId.getText().toString(), (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
    }
}
