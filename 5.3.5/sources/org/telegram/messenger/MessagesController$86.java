package org.telegram.messenger;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import org.telegram.tgnet.ConnectionsManager;

class MessagesController$86 implements OnClickListener {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$reqId;

    MessagesController$86(MessagesController this$0, int i) {
        this.this$0 = this$0;
        this.val$reqId = i;
    }

    public void onClick(DialogInterface dialog, int which) {
        ConnectionsManager.getInstance().cancelRequest(this.val$reqId, true);
        try {
            dialog.dismiss();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }
}
