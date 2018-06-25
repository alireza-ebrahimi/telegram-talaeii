package org.telegram.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.ui.Components.ShareAlert;

public class ShareActivity extends Activity {
    private Dialog visibleDialog;

    /* renamed from: org.telegram.ui.ShareActivity$1 */
    class C52091 implements OnDismissListener {
        C52091() {
        }

        public void onDismiss(DialogInterface dialogInterface) {
            if (!ShareActivity.this.isFinishing()) {
                ShareActivity.this.finish();
            }
            ShareActivity.this.visibleDialog = null;
        }
    }

    protected void onCreate(Bundle bundle) {
        ApplicationLoader.postInitApplication();
        AndroidUtilities.checkDisplaySize(this, getResources().getConfiguration());
        requestWindowFeature(1);
        setTheme(R.style.Theme.TMessages.Transparent);
        super.onCreate(bundle);
        setContentView(new View(this), new LayoutParams(-1, -1));
        Intent intent = getIntent();
        if (intent == null || !"android.intent.action.VIEW".equals(intent.getAction()) || intent.getData() == null) {
            finish();
            return;
        }
        Uri data = intent.getData();
        String scheme = data.getScheme();
        String uri = data.toString();
        String queryParameter = data.getQueryParameter("hash");
        if ("tgb".equals(scheme) && uri.toLowerCase().startsWith("tgb://share_game_score") && !TextUtils.isEmpty(queryParameter)) {
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("botshare", 0);
            Object string = sharedPreferences.getString(queryParameter + "_m", null);
            if (TextUtils.isEmpty(string)) {
                finish();
                return;
            }
            AbstractSerializedData serializedData = new SerializedData(Utilities.hexToBytes(string));
            Message TLdeserialize = Message.TLdeserialize(serializedData, serializedData.readInt32(false), false);
            if (TLdeserialize == null) {
                finish();
                return;
            }
            String string2 = sharedPreferences.getString(queryParameter + "_link", null);
            MessageObject messageObject = new MessageObject(TLdeserialize, null, false);
            messageObject.messageOwner.with_my_score = true;
            try {
                this.visibleDialog = ShareAlert.createShareAlert(this, messageObject, null, false, string2, false);
                this.visibleDialog.setCanceledOnTouchOutside(true);
                this.visibleDialog.setOnDismissListener(new C52091());
                this.visibleDialog.show();
                return;
            } catch (Throwable e) {
                FileLog.e(e);
                finish();
                return;
            }
        }
        finish();
    }

    public void onPause() {
        super.onPause();
        try {
            if (this.visibleDialog != null && this.visibleDialog.isShowing()) {
                this.visibleDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }
}
