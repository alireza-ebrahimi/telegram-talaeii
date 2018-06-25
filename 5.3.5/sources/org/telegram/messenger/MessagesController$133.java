package org.telegram.messenger;

import android.os.Bundle;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;

class MessagesController$133 implements RequestDelegate {
    final /* synthetic */ Bundle val$bundle;
    final /* synthetic */ BaseFragment val$fragment;
    final /* synthetic */ AlertDialog val$progressDialog;

    MessagesController$133(AlertDialog alertDialog, BaseFragment baseFragment, Bundle bundle) {
        this.val$progressDialog = alertDialog;
        this.val$fragment = baseFragment;
        this.val$bundle = bundle;
    }

    public void run(final TLObject response, TLRPC$TL_error error) {
        if (response != null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    try {
                        MessagesController$133.this.val$progressDialog.dismiss();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    TLRPC$messages_Messages res = response;
                    MessagesController.getInstance().putUsers(res.users, false);
                    MessagesController.getInstance().putChats(res.chats, false);
                    MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, true);
                    MessagesController$133.this.val$fragment.presentFragment(new ChatActivity(MessagesController$133.this.val$bundle), true);
                }
            });
        }
    }
}
