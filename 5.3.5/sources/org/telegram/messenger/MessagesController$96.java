package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$InputUser;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputUserSelf;
import org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
import org.telegram.tgnet.TLRPC$TL_updateNewChannelMessage;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.AlertsCreator;

class MessagesController$96 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$chat_id;
    final /* synthetic */ BaseFragment val$fragment;
    final /* synthetic */ TLRPC$InputUser val$inputUser;
    final /* synthetic */ boolean val$isChannel;
    final /* synthetic */ boolean val$isMegagroup;
    final /* synthetic */ TLObject val$request;

    /* renamed from: org.telegram.messenger.MessagesController$96$1 */
    class C15201 implements Runnable {
        C15201() {
        }

        public void run() {
            MessagesController.access$5900(MessagesController$96.this.this$0).remove(Integer.valueOf(MessagesController$96.this.val$chat_id));
        }
    }

    /* renamed from: org.telegram.messenger.MessagesController$96$3 */
    class C15223 implements Runnable {
        C15223() {
        }

        public void run() {
            MessagesController$96.this.this$0.loadFullChat(MessagesController$96.this.val$chat_id, 0, true);
        }
    }

    MessagesController$96(MessagesController this$0, boolean z, TLRPC$InputUser tLRPC$InputUser, int i, BaseFragment baseFragment, TLObject tLObject, boolean z2) {
        this.this$0 = this$0;
        this.val$isChannel = z;
        this.val$inputUser = tLRPC$InputUser;
        this.val$chat_id = i;
        this.val$fragment = baseFragment;
        this.val$request = tLObject;
        this.val$isMegagroup = z2;
    }

    public void run(TLObject response, final TLRPC$TL_error error) {
        if (this.val$isChannel && (this.val$inputUser instanceof TLRPC$TL_inputUserSelf)) {
            AndroidUtilities.runOnUIThread(new C15201());
        }
        if (error != null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    boolean z = true;
                    TLRPC$TL_error tLRPC$TL_error = error;
                    BaseFragment baseFragment = MessagesController$96.this.val$fragment;
                    TLObject tLObject = MessagesController$96.this.val$request;
                    Object[] objArr = new Object[1];
                    if (!MessagesController$96.this.val$isChannel || MessagesController$96.this.val$isMegagroup) {
                        z = false;
                    }
                    objArr[0] = Boolean.valueOf(z);
                    AlertsCreator.processError(tLRPC$TL_error, baseFragment, tLObject, objArr);
                }
            });
            return;
        }
        boolean hasJoinMessage = false;
        TLRPC$Updates updates = (TLRPC$Updates) response;
        for (int a = 0; a < updates.updates.size(); a++) {
            TLRPC$Update update = (TLRPC$Update) updates.updates.get(a);
            if ((update instanceof TLRPC$TL_updateNewChannelMessage) && (((TLRPC$TL_updateNewChannelMessage) update).message.action instanceof TLRPC$TL_messageActionChatAddUser)) {
                hasJoinMessage = true;
                break;
            }
        }
        this.this$0.processUpdates(updates, false);
        if (this.val$isChannel) {
            if (!hasJoinMessage && (this.val$inputUser instanceof TLRPC$TL_inputUserSelf)) {
                this.this$0.generateJoinMessage(this.val$chat_id, true);
            }
            AndroidUtilities.runOnUIThread(new C15223(), 1000);
        }
        if (this.val$isChannel && (this.val$inputUser instanceof TLRPC$TL_inputUserSelf)) {
            MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), null, true, this.val$chat_id);
        }
    }
}
