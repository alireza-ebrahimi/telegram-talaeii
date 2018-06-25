package org.telegram.messenger;

import android.net.Uri;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.widget.Toast;
import java.util.ArrayList;
import org.ir.talaeii.R;

class SendMessagesHelper$18 implements Runnable {
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ InputContentInfoCompat val$inputContent;
    final /* synthetic */ String val$mime;
    final /* synthetic */ ArrayList val$originalPaths;
    final /* synthetic */ ArrayList val$paths;
    final /* synthetic */ MessageObject val$reply_to_msg;
    final /* synthetic */ ArrayList val$uris;

    /* renamed from: org.telegram.messenger.SendMessagesHelper$18$1 */
    class C16411 implements Runnable {
        C16411() {
        }

        public void run() {
            try {
                Toast.makeText(ApplicationLoader.applicationContext, LocaleController.getString("UnsupportedAttachment", R.string.UnsupportedAttachment), 0).show();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    SendMessagesHelper$18(ArrayList arrayList, ArrayList arrayList2, String str, long j, MessageObject messageObject, ArrayList arrayList3, InputContentInfoCompat inputContentInfoCompat) {
        this.val$paths = arrayList;
        this.val$originalPaths = arrayList2;
        this.val$mime = str;
        this.val$dialog_id = j;
        this.val$reply_to_msg = messageObject;
        this.val$uris = arrayList3;
        this.val$inputContent = inputContentInfoCompat;
    }

    public void run() {
        int a;
        boolean error = false;
        if (this.val$paths != null) {
            for (a = 0; a < this.val$paths.size(); a++) {
                if (!SendMessagesHelper.access$1500((String) this.val$paths.get(a), (String) this.val$originalPaths.get(a), null, this.val$mime, this.val$dialog_id, this.val$reply_to_msg, null)) {
                    error = true;
                }
            }
        }
        if (this.val$uris != null) {
            for (a = 0; a < this.val$uris.size(); a++) {
                if (!SendMessagesHelper.access$1500(null, null, (Uri) this.val$uris.get(a), this.val$mime, this.val$dialog_id, this.val$reply_to_msg, null)) {
                    error = true;
                }
            }
        }
        if (this.val$inputContent != null) {
            this.val$inputContent.releasePermission();
        }
        if (error) {
            AndroidUtilities.runOnUIThread(new C16411());
        }
    }
}
