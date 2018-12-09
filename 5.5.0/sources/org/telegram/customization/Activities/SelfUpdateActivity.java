package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.C0236a;
import android.support.v4.content.C0235a;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crashlytics.android.p064a.C1333b;
import com.crashlytics.android.p064a.C1351m;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.p098a.C1768f;
import java.io.File;
import org.ir.talaeii.R;
import org.telegram.customization.Model.SettingAndUpdate;
import org.telegram.customization.util.DownloadProgressView;
import org.telegram.customization.util.DownloadProgressView.C2513a;
import org.telegram.messenger.BuildConfig;

public class SelfUpdateActivity extends Activity implements OnClickListener {
    /* renamed from: g */
    private static String[] f8429g = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    /* renamed from: a */
    SettingAndUpdate f8430a;
    /* renamed from: b */
    LinearLayout f8431b;
    /* renamed from: c */
    DownloadProgressView f8432c;
    /* renamed from: d */
    private long f8433d;
    /* renamed from: e */
    private DownloadManager f8434e;
    /* renamed from: f */
    private Request f8435f;

    /* renamed from: org.telegram.customization.Activities.SelfUpdateActivity$1 */
    class C25141 implements C2513a {
        /* renamed from: a */
        final /* synthetic */ SelfUpdateActivity f8428a;

        C25141(SelfUpdateActivity selfUpdateActivity) {
            this.f8428a = selfUpdateActivity;
        }

        /* renamed from: a */
        public void mo3420a() {
            if (this.f8428a.m12266b()) {
                this.f8428a.setResult(0);
                this.f8428a.finish();
                this.f8428a.f8434e.remove(new long[]{this.f8428a.f8433d});
                this.f8428a.findViewById(R.id.download).setVisibility(0);
            }
        }

        /* renamed from: a */
        public void mo3421a(int i) {
            if (this.f8428a.m12266b()) {
                this.f8428a.setResult(0);
                this.f8428a.f8434e.remove(new long[]{this.f8428a.f8433d});
                this.f8428a.finish();
            }
        }

        /* renamed from: a */
        public void mo3422a(String str) {
            this.f8428a.m12268a(this.f8428a.getApplicationContext(), str);
        }
    }

    /* renamed from: a */
    private void m12263a() {
        this.f8435f.setDestinationInExternalFilesDir(getApplicationContext(), null, System.currentTimeMillis() + ".apk");
        this.f8435f.allowScanningByMediaScanner();
        this.f8433d = this.f8434e.enqueue(this.f8435f);
        this.f8432c.m13272a(this.f8433d, new C25141(this));
    }

    /* renamed from: b */
    private boolean m12266b() {
        return this.f8430a != null && this.f8430a.getUpdate().isForceUpdate();
    }

    /* renamed from: a */
    public void m12268a(Context context, String str) {
        int i;
        File file = new File(str);
        if (file.exists()) {
            Intent intent;
            int i2;
            try {
                intent = new Intent("android.intent.action.VIEW");
                intent.setFlags(ErrorDialogData.BINDER_CRASH);
                intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
                startActivity(intent);
                i2 = 1;
                i = -1;
            } catch (Exception e) {
                e.printStackTrace();
                i = 0;
                i2 = 0;
            }
            if (i2 == 0) {
                try {
                    intent = new Intent("android.intent.action.INSTALL_PACKAGE");
                    intent.setFlags(ErrorDialogData.BINDER_CRASH);
                    intent.setFlags(1);
                    intent.setData(FileProvider.m1845a(context, "org.ir.talaeii.provider", file));
                    startActivity(intent);
                    i = -1;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (!m12266b() || r0 == -1) {
                setResult(-1);
            } else {
                setResult(0);
            }
        } else if (m12266b()) {
            setResult(0);
        } else {
            setResult(-1);
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case 8888:
                if (i2 != -1) {
                    finish();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        if (this.f8430a == null || !this.f8430a.getUpdate().isForceUpdate()) {
            setResult(-1);
            super.onBackPressed();
            return;
        }
        setResult(0);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
            case R.id.cancel:
                try {
                    if (this.f8434e != null) {
                        this.f8434e.remove(new long[]{this.f8433d});
                    }
                    if (this.f8430a == null || !this.f8430a.getUpdate().isForceUpdate()) {
                        setResult(-1);
                        finish();
                        return;
                    }
                    setResult(0);
                    return;
                } catch (Exception e) {
                    return;
                }
            case R.id.download:
                try {
                    C1333b.m6818c().m6821a((C1351m) new C1351m("TRACKER_VIEW_ACTION").m6783a("Download Clicked", Integer.valueOf(BuildConfig.VERSION_CODE)));
                } catch (Exception e2) {
                }
                if (this.f8430a.getUpdate().isFromMarket()) {
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse("bazaar://details?id=org.ir.talaeii"));
                        intent.setPackage("com.farsitel.bazaar");
                        startActivity(intent);
                        return;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        return;
                    }
                } else if (C0235a.m1072b((Context) this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    C0236a.m1080a(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    return;
                } else {
                    findViewById(R.id.download).setVisibility(8);
                    try {
                        m12263a();
                        return;
                    } catch (Exception e32) {
                        e32.printStackTrace();
                        return;
                    }
                }
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_selfupdate);
        try {
            C1333b.m6818c().m6821a((C1351m) new C1351m("TRACKER_VIEW_ACTION").m6783a("UPDATE ACTIVITY SHOWN", Integer.valueOf(BuildConfig.VERSION_CODE)));
        } catch (Exception e) {
        }
        String stringExtra = getIntent().getStringExtra("EXTRA_UPDATE_MODEL");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.f8430a = (SettingAndUpdate) new C1768f().m8392a(stringExtra, SettingAndUpdate.class);
        }
        this.f8432c = (DownloadProgressView) findViewById(R.id.downloadProgressView);
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.download);
        textView.setOnClickListener(this);
        this.f8431b = (LinearLayout) findViewById(R.id.ll_change_container);
        if (this.f8430a.getUpdate().isFromMarket()) {
            textView.setText(getString(R.string.download_from_bazaar));
        }
        int i = 0;
        while (i < this.f8430a.getUpdate().getChangeList().size()) {
            try {
                textView = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
                textView.setText((CharSequence) this.f8430a.getUpdate().getChangeList().get(i));
                textView.setTextColor(Color.parseColor("#000000"));
                this.f8431b.addView(textView);
                i++;
            } catch (Exception e2) {
            }
        }
        try {
            if (this.f8430a.getUpdate().getDownloadLink() == null || TextUtils.isEmpty(this.f8430a.getUpdate().getDownloadLink())) {
                setResult(-1);
                finish();
                return;
            }
            this.f8434e = (DownloadManager) getSystemService("download");
            this.f8435f = new Request(Uri.parse(this.f8430a.getUpdate().getDownloadLink()));
        } catch (Exception e3) {
            setResult(0);
            finish();
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        switch (i) {
            case 1:
                if (iArr.length <= 0 || iArr[0] != 0) {
                    setResult(0);
                    finish();
                    return;
                }
                try {
                    m12263a();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }
}
