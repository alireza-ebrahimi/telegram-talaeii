package org.telegram.messenger;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class SendMessagesHelper$4 implements OnClickListener {
    final /* synthetic */ SendMessagesHelper this$0;

    SendMessagesHelper$4(SendMessagesHelper this$0) {
        this.this$0 = this$0;
    }

    public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
    }
}
