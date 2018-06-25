package org.telegram.customization.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.ir.talaeii.R;
import org.telegram.messenger.ApplicationLoader;

public class ShortcutActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        setTheme(R.style.Theme.TMessages);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        intent.setClassName(ApplicationLoader.applicationContext.getPackageName(), "org.telegram.ui.LaunchActivity");
        startActivity(intent);
        finish();
    }
}
