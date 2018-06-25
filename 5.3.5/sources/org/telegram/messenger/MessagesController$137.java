package org.telegram.messenger;

import android.util.Log;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contacts_resolvedPeer;
import org.telegram.tgnet.TLRPC$TL_error;

class MessagesController$137 implements RequestDelegate {
    final /* synthetic */ IResponseReceiver val$responseReceiver;

    MessagesController$137(IResponseReceiver iResponseReceiver) {
        this.val$responseReceiver = iResponseReceiver;
    }

    public void run(final TLObject response, final TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (error == null) {
                    TLRPC$TL_contacts_resolvedPeer res = response;
                    MessagesController.getInstance().putUsers(res.users, false);
                    MessagesController.getInstance().putChats(res.chats, false);
                    Log.d("LEE", "username:" + res.chats.size());
                    MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, false, true);
                    if (MessagesController$137.this.val$responseReceiver != null) {
                        MessagesController$137.this.val$responseReceiver.onResult(res.chats, 0);
                    }
                }
            }
        });
    }
}
