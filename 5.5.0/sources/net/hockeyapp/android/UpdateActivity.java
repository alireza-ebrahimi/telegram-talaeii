package net.hockeyapp.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.Locale;
import net.hockeyapp.android.C2417f.C2414b;
import net.hockeyapp.android.C2417f.C2415c;
import net.hockeyapp.android.C2417f.C2416d;
import net.hockeyapp.android.p133b.C2359a;
import net.hockeyapp.android.p135c.C2369a;
import net.hockeyapp.android.p136d.C2387d;
import net.hockeyapp.android.p136d.C2388e;
import net.hockeyapp.android.p137e.C2393a;
import net.hockeyapp.android.p137e.C2400d;
import net.hockeyapp.android.p137e.C2408i;
import net.hockeyapp.android.p137e.C2411k;

public class UpdateActivity extends Activity implements OnClickListener, C2365g {
    /* renamed from: a */
    protected C2387d f7949a;
    /* renamed from: b */
    protected C2411k f7950b;
    /* renamed from: c */
    private C2369a f7951c;
    /* renamed from: d */
    private Context f7952d;

    /* renamed from: net.hockeyapp.android.UpdateActivity$1 */
    class C23571 implements DialogInterface.OnClickListener {
        /* renamed from: a */
        final /* synthetic */ UpdateActivity f7938a;

        C23571(UpdateActivity updateActivity) {
            this.f7938a = updateActivity;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f7938a.f7951c = null;
            dialogInterface.cancel();
        }
    }

    /* renamed from: net.hockeyapp.android.UpdateActivity$4 */
    class C23614 extends C2359a {
        /* renamed from: a */
        final /* synthetic */ UpdateActivity f7945a;

        C23614(UpdateActivity updateActivity) {
            this.f7945a = updateActivity;
        }

        /* renamed from: a */
        public void mo3381a(C2387d c2387d) {
            this.f7945a.m11711f();
        }

        /* renamed from: a */
        public void mo3382a(C2387d c2387d, Boolean bool) {
            if (bool.booleanValue()) {
                this.f7945a.m11710e();
            } else {
                this.f7945a.m11711f();
            }
        }
    }

    /* renamed from: net.hockeyapp.android.UpdateActivity$5 */
    class C23625 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ UpdateActivity f7946a;

        C23625(UpdateActivity updateActivity) {
            this.f7946a = updateActivity;
        }

        public void run() {
            this.f7946a.showDialog(0);
        }
    }

    /* renamed from: net.hockeyapp.android.UpdateActivity$6 */
    class C23636 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ UpdateActivity f7947a;

        C23636(UpdateActivity updateActivity) {
            this.f7947a = updateActivity;
        }

        public void run() {
            this.f7947a.showDialog(0);
        }
    }

    /* renamed from: net.hockeyapp.android.UpdateActivity$7 */
    class C23647 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ UpdateActivity f7948a;

        C23647(UpdateActivity updateActivity) {
            this.f7948a = updateActivity;
        }

        public void run() {
            this.f7948a.showDialog(0);
        }
    }

    /* renamed from: a */
    private boolean m11702a(Context context) {
        return context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }

    @SuppressLint({"InlinedApi"})
    /* renamed from: i */
    private boolean m11703i() {
        try {
            return (VERSION.SDK_INT < 17 || VERSION.SDK_INT >= 21) ? Secure.getInt(getContentResolver(), "install_non_market_apps") == 1 : Global.getInt(getContentResolver(), "install_non_market_apps") == 1;
        } catch (SettingNotFoundException e) {
            return true;
        }
    }

    /* renamed from: a */
    public int mo3383a() {
        int i = -1;
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 128).versionCode;
        } catch (NameNotFoundException e) {
            return i;
        }
    }

    /* renamed from: a */
    protected void m11705a(String str) {
        m11706a(str, new C23614(this));
        C2393a.m11833a(this.f7949a);
    }

    /* renamed from: a */
    protected void m11706a(String str, C2359a c2359a) {
        this.f7949a = new C2387d(this, str, c2359a);
    }

    @SuppressLint({"InflateParams"})
    /* renamed from: b */
    public View m11707b() {
        return getLayoutInflater().inflate(C2415c.hockeyapp_activity_update, null);
    }

    /* renamed from: c */
    protected void m11708c() {
        ((TextView) findViewById(C2414b.label_title)).setText(m11712g());
        final TextView textView = (TextView) findViewById(C2414b.label_version);
        final String str = "Version " + this.f7950b.m11904a();
        final String b = this.f7950b.m11906b();
        String str2 = "Unknown size";
        if (this.f7950b.m11907c() >= 0) {
            str2 = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(((float) r4) / 1048576.0f)}) + " MB";
        } else {
            C2393a.m11833a(new C2388e(this, getIntent().getStringExtra(ImagesContract.URL), new C2359a(this) {
                /* renamed from: d */
                final /* synthetic */ UpdateActivity f7944d;

                /* renamed from: a */
                public void mo3381a(C2387d c2387d) {
                    if (c2387d instanceof C2388e) {
                        long c = ((C2388e) c2387d).m11815c();
                        String str = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(((float) c) / 1048576.0f)}) + " MB";
                        textView.setText(this.f7944d.getString(C2416d.hockeyapp_update_version_details_label, new Object[]{str, b, str}));
                    }
                }
            }));
        }
        textView.setText(getString(C2416d.hockeyapp_update_version_details_label, new Object[]{str, b, str2}));
        ((Button) findViewById(C2414b.button_update)).setOnClickListener(this);
        WebView webView = (WebView) findViewById(C2414b.web_update_details);
        webView.clearCache(true);
        webView.destroyDrawingCache();
        webView.loadDataWithBaseURL("https://sdk.hockeyapp.net/", m11709d(), "text/html", "utf-8", null);
    }

    /* renamed from: d */
    protected String m11709d() {
        return this.f7950b.m11905a(false);
    }

    /* renamed from: e */
    protected void m11710e() {
        m11705a(getIntent().getStringExtra(ImagesContract.URL));
    }

    /* renamed from: f */
    public void m11711f() {
        findViewById(C2414b.button_update).setEnabled(true);
    }

    /* renamed from: g */
    public String m11712g() {
        try {
            PackageManager packageManager = getPackageManager();
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(getPackageName(), 0)).toString();
        } catch (NameNotFoundException e) {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    /* renamed from: h */
    protected void m11713h() {
        if (!C2408i.m11882a(this.f7952d)) {
            this.f7951c = new C2369a();
            this.f7951c.m11730a(getString(C2416d.hockeyapp_error_no_network_message));
            runOnUiThread(new C23625(this));
        } else if (m11702a(this.f7952d)) {
            if (m11703i()) {
                m11710e();
                return;
            }
            this.f7951c = new C2369a();
            this.f7951c.m11730a("The installation from unknown sources is not enabled. Please check the device settings.");
            runOnUiThread(new C23647(this));
        } else if (VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        } else {
            this.f7951c = new C2369a();
            this.f7951c.m11730a("The permission to access the external storage permission is not set. Please contact the developer.");
            runOnUiThread(new C23636(this));
        }
    }

    public void onClick(View view) {
        m11713h();
        view.setEnabled(false);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle("App Update");
        setContentView(m11707b());
        this.f7952d = this;
        this.f7950b = new C2411k(this, getIntent().getStringExtra("json"), this);
        m11708c();
        this.f7949a = (C2387d) getLastNonConfigurationInstance();
        if (this.f7949a != null) {
            this.f7949a.m11807a((Context) this);
        }
    }

    protected Dialog onCreateDialog(int i) {
        return onCreateDialog(i, null);
    }

    protected Dialog onCreateDialog(int i, Bundle bundle) {
        switch (i) {
            case 0:
                return new Builder(this).setMessage("An error has occured").setCancelable(false).setTitle("Error").setIcon(17301543).setPositiveButton("OK", new C23571(this)).create();
            default:
                return null;
        }
    }

    protected void onPrepareDialog(int i, Dialog dialog) {
        switch (i) {
            case 0:
                AlertDialog alertDialog = (AlertDialog) dialog;
                if (this.f7951c != null) {
                    alertDialog.setMessage(this.f7951c.m11729a());
                    return;
                } else {
                    alertDialog.setMessage("An unknown error has occured.");
                    return;
                }
            default:
                return;
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        m11711f();
        if (strArr.length != 0 && iArr.length != 0 && i == 1) {
            if (iArr[0] == 0) {
                m11713h();
                return;
            }
            C2400d.m11844b("User denied write permission, can't continue with updater task.");
            C2419i a = C2418h.m11910a();
            if (a != null) {
                a.m11914c();
            } else {
                new Builder(this.f7952d).setTitle(getString(C2416d.hockeyapp_permission_update_title)).setMessage(getString(C2416d.hockeyapp_permission_update_message)).setNegativeButton(getString(C2416d.hockeyapp_permission_dialog_negative_button), null).setPositiveButton(getString(C2416d.hockeyapp_permission_dialog_positive_button), new DialogInterface.OnClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ UpdateActivity f7940b;

                    public void onClick(DialogInterface dialogInterface, int i) {
                        this.m11713h();
                    }
                }).create().show();
            }
        }
    }

    public Object onRetainNonConfigurationInstance() {
        if (this.f7949a != null) {
            this.f7949a.m11806a();
        }
        return this.f7949a;
    }
}
