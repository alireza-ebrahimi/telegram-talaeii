package com.google.firebase.auth.internal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.C0353t;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.internal.firebase_auth.zzbf;

@KeepName
public class FederatedSignInActivity extends C0353t {
    /* renamed from: n */
    private static boolean f5513n = false;
    /* renamed from: o */
    private boolean f5514o = false;

    /* renamed from: a */
    private final void m8604a(int i, Intent intent) {
        f5513n = false;
        setResult(-1, intent);
        finish();
    }

    /* renamed from: c */
    private final void m8605c(int i) {
        f5513n = false;
        this.f5514o = false;
        setResult(0);
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String action = getIntent().getAction();
        if (!"com.google.firebase.auth.internal.SIGN_IN".equals(action) && !"com.google.firebase.auth.internal.GET_CRED".equals(action)) {
            String str = "IdpSignInActivity";
            String str2 = "Unknown action: ";
            action = String.valueOf(action);
            Log.e(str, action.length() != 0 ? str2.concat(action) : new String(str2));
            setResult(0);
            finish();
        } else if (f5513n) {
            setResult(0);
            finish();
        } else {
            f5513n = true;
            if (bundle != null) {
                this.f5514o = bundle.getBoolean("com.google.firebase.auth.internal.KEY_STARTED_SIGN_IN");
            }
        }
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        Intent intent;
        if (this.f5514o) {
            boolean z;
            intent = getIntent();
            Intent intent2;
            if ("com.google.firebase.auth.internal.WEB_SIGN_IN_FAILED".equals(intent.getAction())) {
                Log.e("IdpSignInActivity", "Web sign-in failed, finishing");
                if (C1869i.m8626a(intent)) {
                    Status b = C1869i.m8627b(intent);
                    f5513n = false;
                    intent2 = new Intent();
                    C1869i.m8625a(intent2, b);
                    setResult(-1, intent2);
                    finish();
                    z = true;
                } else {
                    m8605c(0);
                    z = true;
                }
            } else if (intent.hasExtra("com.google.firebase.auth.internal.OPERATION") && intent.hasExtra("com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST")) {
                String stringExtra = intent.getStringExtra("com.google.firebase.auth.internal.OPERATION");
                if ("com.google.firebase.auth.internal.SIGN_IN".equals(stringExtra)) {
                    zzbf zzbf = (zzbf) SafeParcelableSerializer.deserializeFromIntentExtra(intent, "com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST", zzbf.CREATOR);
                    intent2 = new Intent();
                    SafeParcelableSerializer.serializeToIntentExtra(zzbf, intent2, "com.google.firebase.auth.internal.CREDENTIAL_FOR_AUTH_RESULT");
                    m8604a(-1, intent2);
                    z = true;
                } else if ("com.google.firebase.auth.internal.GET_CRED".equals(stringExtra)) {
                    intent2 = new Intent();
                    intent2.putExtra("com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST", intent.getByteArrayExtra("com.google.firebase.auth.internal.VERIFY_ASSERTION_REQUEST"));
                    m8604a(-1, intent2);
                    z = true;
                } else {
                    z = false;
                }
            } else {
                z = false;
            }
            if (!z) {
                m8605c(0);
                return;
            }
            return;
        }
        intent = new Intent("com.google.firebase.auth.api.gms.ui.START_WEB_SIGN_IN");
        intent.setPackage("com.google.android.gms");
        intent.putExtras(getIntent().getExtras());
        intent.putExtra("com.google.firebase.auth.internal.OPERATION", getIntent().getAction());
        try {
            startActivityForResult(intent, 40963);
        } catch (ActivityNotFoundException e) {
            Log.w("IdpSignInActivity", "Could not launch web sign-in Intent. Google Play service is unavailable");
            m8605c(0);
        }
        this.f5514o = true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("com.google.firebase.auth.internal.KEY_STARTED_SIGN_IN", this.f5514o);
    }
}
