package org.telegram.messenger;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ActionBar.BaseFragment;

class MessagesController$134 implements OnClickListener {
    final /* synthetic */ BaseFragment val$fragment;
    final /* synthetic */ int val$reqId;

    MessagesController$134(int i, BaseFragment baseFragment) {
        this.val$reqId = i;
        this.val$fragment = baseFragment;
    }

    public void onClick(DialogInterface dialog, int which) {
        ConnectionsManager.getInstance().cancelRequest(this.val$reqId, true);
        try {
            dialog.dismiss();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (this.val$fragment != null) {
            this.val$fragment.setVisibleDialog(null);
        }
    }
}
