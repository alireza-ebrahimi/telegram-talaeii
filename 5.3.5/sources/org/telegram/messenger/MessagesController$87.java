package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_channels_inviteToChannel;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.AlertsCreator;

class MessagesController$87 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ BaseFragment val$fragment;
    final /* synthetic */ TLRPC$TL_channels_inviteToChannel val$req;

    MessagesController$87(MessagesController this$0, BaseFragment baseFragment, TLRPC$TL_channels_inviteToChannel tLRPC$TL_channels_inviteToChannel) {
        this.this$0 = this$0;
        this.val$fragment = baseFragment;
        this.val$req = tLRPC$TL_channels_inviteToChannel;
    }

    public void run(TLObject response, final TLRPC$TL_error error) {
        if (error != null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    AlertsCreator.processError(error, MessagesController$87.this.val$fragment, MessagesController$87.this.val$req, Boolean.valueOf(true));
                }
            });
        } else {
            this.this$0.processUpdates((TLRPC$Updates) response, false);
        }
    }
}
