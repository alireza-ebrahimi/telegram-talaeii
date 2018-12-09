package net.hockeyapp.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import net.hockeyapp.android.C2417f.C2414b;
import net.hockeyapp.android.C2417f.C2415c;
import net.hockeyapp.android.C2417f.C2416d;
import net.hockeyapp.android.p137e.C2408i;

public class ExpiryInfoActivity extends Activity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle(getString(C2416d.hockeyapp_expiry_info_title));
        setContentView(C2415c.hockeyapp_activity_expiry_info);
        String b = C2408i.m11884b((Context) this);
        ((TextView) findViewById(C2414b.label_message)).setText(String.format(getString(C2416d.hockeyapp_expiry_info_text), new Object[]{b}));
    }
}
