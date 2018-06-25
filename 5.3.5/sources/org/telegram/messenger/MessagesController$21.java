package org.telegram.messenger;

import android.content.SharedPreferences.Editor;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_peerSettings;

class MessagesController$21 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$dialogId;

    MessagesController$21(MessagesController this$0, long j) {
        this.this$0 = this$0;
        this.val$dialogId = j;
    }

    public void run(final TLObject response, TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessagesController.access$3000(MessagesController$21.this.this$0).remove(Long.valueOf(MessagesController$21.this.val$dialogId));
                if (response != null) {
                    TLRPC$TL_peerSettings res = response;
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    if (res.report_spam) {
                        FileLog.d("show spam button for " + MessagesController$21.this.val$dialogId);
                        editor.putInt("spam3_" + MessagesController$21.this.val$dialogId, 2);
                        editor.commit();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.peerSettingsDidLoaded, new Object[]{Long.valueOf(MessagesController$21.this.val$dialogId)});
                        return;
                    }
                    FileLog.d("don't show spam button for " + MessagesController$21.this.val$dialogId);
                    editor.putInt("spam3_" + MessagesController$21.this.val$dialogId, 1);
                    editor.commit();
                }
            }
        });
    }
}
