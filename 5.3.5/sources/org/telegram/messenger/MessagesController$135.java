package org.telegram.messenger;

import android.widget.Toast;
import org.ir.talaeii.R;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_contacts_resolvedPeer;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;

class MessagesController$135 implements RequestDelegate {
    final /* synthetic */ BaseFragment val$fragment;
    final /* synthetic */ AlertDialog[] val$progressDialog;
    final /* synthetic */ int val$type;

    MessagesController$135(AlertDialog[] alertDialogArr, BaseFragment baseFragment, int i) {
        this.val$progressDialog = alertDialogArr;
        this.val$fragment = baseFragment;
        this.val$type = i;
    }

    public void run(final TLObject response, final TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                try {
                    MessagesController$135.this.val$progressDialog[0].dismiss();
                } catch (Exception e) {
                }
                MessagesController$135.this.val$progressDialog[0] = null;
                MessagesController$135.this.val$fragment.setVisibleDialog(null);
                if (error == null) {
                    TLRPC$TL_contacts_resolvedPeer res = response;
                    MessagesController.getInstance().putUsers(res.users, false);
                    MessagesController.getInstance().putChats(res.chats, false);
                    MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, false, true);
                    if (!res.chats.isEmpty()) {
                        MessagesController.openChatOrProfileWith(null, (TLRPC$Chat) res.chats.get(0), MessagesController$135.this.val$fragment, 1, false);
                    } else if (!res.users.isEmpty()) {
                        MessagesController.openChatOrProfileWith((User) res.users.get(0), null, MessagesController$135.this.val$fragment, MessagesController$135.this.val$type, false);
                    }
                } else if (MessagesController$135.this.val$fragment != null && MessagesController$135.this.val$fragment.getParentActivity() != null) {
                    try {
                        Toast.makeText(MessagesController$135.this.val$fragment.getParentActivity(), LocaleController.getString("NoUsernameFound", R.string.NoUsernameFound), 0).show();
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                }
            }
        });
    }
}
