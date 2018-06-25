package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.File;
import org.ir.talaeii.R;
import org.telegram.customization.Model.SettingAndUpdate;
import org.telegram.customization.util.DownloadProgressView;
import org.telegram.customization.util.DownloadProgressView.DownloadStatusListener;
import utils.view.Constants;
import utils.view.ToastUtil;

public class SelfUpdateActivity extends Activity implements OnClickListener {
    private static String[] PERMISSIONS_STORAGE = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private long downloadID;
    private DownloadManager downloadManager;
    DownloadProgressView downloadProgressView;
    LinearLayout llConntainer;
    private Request request;
    SettingAndUpdate updateModel;

    /* renamed from: org.telegram.customization.Activities.SelfUpdateActivity$1 */
    class C10931 implements DownloadStatusListener {
        C10931() {
        }

        public void downloadFailed(int reason) {
            if (SelfUpdateActivity.this.isRequired()) {
                SelfUpdateActivity.this.setResult(0);
                SelfUpdateActivity.this.downloadManager.remove(new long[]{SelfUpdateActivity.this.downloadID});
                SelfUpdateActivity.this.finish();
            }
        }

        public void downloadSuccessful(String filePath) {
            SelfUpdateActivity.this.installAPK(SelfUpdateActivity.this.getApplicationContext(), filePath);
        }

        public void downloadCancelled() {
            if (SelfUpdateActivity.this.isRequired()) {
                SelfUpdateActivity.this.setResult(0);
                SelfUpdateActivity.this.finish();
                SelfUpdateActivity.this.downloadManager.remove(new long[]{SelfUpdateActivity.this.downloadID});
                SelfUpdateActivity.this.findViewById(R.id.download).setVisibility(0);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfupdate);
        String json = getIntent().getStringExtra(Constants.EXTRA_UPDATE_MODEL);
        if (!TextUtils.isEmpty(json)) {
            this.updateModel = (SettingAndUpdate) new Gson().fromJson(json, SettingAndUpdate.class);
        }
        this.downloadProgressView = (DownloadProgressView) findViewById(R.id.downloadProgressView);
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        TextView tvDownload = (TextView) findViewById(R.id.download);
        tvDownload.setOnClickListener(this);
        this.llConntainer = (LinearLayout) findViewById(R.id.ll_change_container);
        if (this.updateModel.getUpdate().isFromMarket()) {
            tvDownload.setText(getString(R.string.download_from_bazaar));
        }
        for (int i = 0; i < this.updateModel.getUpdate().getChangeList().size(); i++) {
            TextView child = (TextView) getLayoutInflater().inflate(R.layout.textview, null);
            child.setText((CharSequence) this.updateModel.getUpdate().getChangeList().get(i));
            child.setTextColor(Color.parseColor("#000000"));
            this.llConntainer.addView(child);
        }
        try {
            if (this.updateModel.getUpdate().getDownloadLink() == null || TextUtils.isEmpty(this.updateModel.getUpdate().getDownloadLink())) {
                setResult(-1);
                finish();
                return;
            }
            this.downloadManager = (DownloadManager) getSystemService("download");
            this.request = new Request(Uri.parse(this.updateModel.getUpdate().getDownloadLink()));
        } catch (Exception e) {
            setResult(0);
            finish();
        }
    }

    public void onBackPressed() {
        if (this.updateModel == null || !this.updateModel.getUpdate().isForceUpdate()) {
            setResult(-1);
        } else {
            setResult(0);
        }
        super.onBackPressed();
    }

    private void showError() {
        ToastUtil.AppToast(this, "این نسخه از برنامه منقضی شده است. لطفا نسخه جدید را دانلود نمایید").show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
            case R.id.cancel:
                try {
                    if (this.downloadManager != null) {
                        this.downloadManager.remove(new long[]{this.downloadID});
                    }
                    if (this.updateModel == null || !this.updateModel.getUpdate().isForceUpdate()) {
                        setResult(-1);
                        finish();
                        return;
                    }
                    setResult(0);
                    finish();
                    return;
                } catch (Exception e) {
                }
                break;
            case R.id.download:
                if (this.updateModel.getUpdate().isFromMarket()) {
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse("bazaar://details?id=org.ir.talaeii"));
                        intent.setPackage("com.farsitel.bazaar");
                        startActivity(intent);
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                } else if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    return;
                } else {
                    findViewById(R.id.download).setVisibility(8);
                    try {
                        startDownlaod();
                        return;
                    } catch (Exception e22) {
                        e22.printStackTrace();
                        return;
                    }
                }
            default:
                return;
        }
    }

    private void startDownlaod() {
        this.request.setDestinationInExternalFilesDir(getApplicationContext(), null, System.currentTimeMillis() + ".apk");
        this.request.allowScanningByMediaScanner();
        this.downloadID = this.downloadManager.enqueue(this.request);
        this.downloadProgressView.show(this.downloadID, new C10931());
    }

    private boolean isRequired() {
        if (this.updateModel == null || !this.updateModel.getUpdate().isForceUpdate()) {
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    setResult(0);
                    finish();
                    return;
                }
                try {
                    startDownlaod();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }

    public void installAPK(Context ctx, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Intent installIntent;
            boolean couldInstallApk;
            int activityResult = 0;
            try {
                installIntent = new Intent("android.intent.action.VIEW");
                installIntent.setFlags(268435456);
                installIntent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
                startActivity(installIntent);
                couldInstallApk = true;
                activityResult = -1;
            } catch (Exception e) {
                e.printStackTrace();
                couldInstallApk = false;
            }
            if (!couldInstallApk) {
                try {
                    installIntent = new Intent("android.intent.action.INSTALL_PACKAGE");
                    installIntent.setFlags(268435456);
                    installIntent.setFlags(1);
                    installIntent.setData(FileProvider.getUriForFile(ctx, "org.ir.talaeii.provider", file));
                    startActivity(installIntent);
                    activityResult = -1;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (!isRequired() || activityResult == -1) {
                setResult(-1);
            } else {
                setResult(0);
            }
        } else if (isRequired()) {
            setResult(0);
        } else {
            setResult(-1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_UPDATE /*8888*/:
                if (resultCode != -1) {
                    finish();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
