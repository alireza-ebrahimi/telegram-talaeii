package com.persianswitch.sdk.base;

import android.content.Context;
import android.support.v7.app.C0768c;
import android.view.ContextThemeWrapper;
import com.persianswitch.sdk.base.manager.LanguageManager;

public abstract class BaseActivity extends C0768c {
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        LanguageManager.m10671a(context, (ContextThemeWrapper) this);
    }
}
