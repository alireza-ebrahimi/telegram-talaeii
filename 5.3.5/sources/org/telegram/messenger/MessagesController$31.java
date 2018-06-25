package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_channels_editAdmin;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.AlertsCreator;

class MessagesController$31 implements RequestDelegate {
    final /* synthetic */ int val$chatId;
    final /* synthetic */ boolean val$isMegagroup;
    final /* synthetic */ BaseFragment val$parentFragment;
    final /* synthetic */ TLRPC$TL_channels_editAdmin val$req;

    /* renamed from: org.telegram.messenger.MessagesController$31$1 */
    class C14831 implements Runnable {
        C14831() {
        }

        public void run() {
            MessagesController.getInstance().loadFullChat(MessagesController$31.this.val$chatId, 0, true);
        }
    }

    MessagesController$31(int i, BaseFragment baseFragment, TLRPC$TL_channels_editAdmin tLRPC$TL_channels_editAdmin, boolean z) {
        this.val$chatId = i;
        this.val$parentFragment = baseFragment;
        this.val$req = tLRPC$TL_channels_editAdmin;
        this.val$isMegagroup = z;
    }

    public void run(TLObject response, final TLRPC$TL_error error) {
        if (error == null) {
            MessagesController.getInstance().processUpdates((TLRPC$Updates) response, false);
            AndroidUtilities.runOnUIThread(new C14831(), 1000);
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                boolean z = true;
                TLRPC$TL_error tLRPC$TL_error = error;
                BaseFragment baseFragment = MessagesController$31.this.val$parentFragment;
                TLObject tLObject = MessagesController$31.this.val$req;
                Object[] objArr = new Object[1];
                if (MessagesController$31.this.val$isMegagroup) {
                    z = false;
                }
                objArr[0] = Boolean.valueOf(z);
                AlertsCreator.processError(tLRPC$TL_error, baseFragment, tLObject, objArr);
            }
        });
    }
}
