package com.persianswitch.sdk.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import com.persianswitch.sdk.base.manager.LanguageManager;

public abstract class BaseActivity extends AppCompatActivity {
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        LanguageManager.applyCurrentLocale(newBase, this);
    }
}
