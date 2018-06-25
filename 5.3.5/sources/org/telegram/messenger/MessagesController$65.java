package org.telegram.messenger;

import java.util.HashMap;
import java.util.Map.Entry;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_draftMessage;
import org.telegram.tgnet.TLRPC$messages_Dialogs;
import org.telegram.ui.ChatActivity;

class MessagesController$65 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$date;
    final /* synthetic */ TLRPC$messages_Dialogs val$dialogsRes;
    final /* synthetic */ int val$newPts;
    final /* synthetic */ HashMap val$new_dialogMessage;
    final /* synthetic */ HashMap val$new_dialogs_dict;
    final /* synthetic */ int val$qts;

    /* renamed from: org.telegram.messenger.MessagesController$65$1 */
    class C14961 implements Runnable {
        C14961() {
        }

        public void run() {
            MessagesController.access$5102(MessagesController$65.this.this$0, false);
            MessagesController.access$5200(MessagesController$65.this.this$0, MessagesController$65.this.val$dialogsRes.dialogs);
            if (!UserConfig.draftsLoaded) {
                DraftQuery.loadDrafts();
            }
            MessagesController$65.this.this$0.putUsers(MessagesController$65.this.val$dialogsRes.users, false);
            MessagesController$65.this.this$0.putChats(MessagesController$65.this.val$dialogsRes.chats, false);
            for (int a = 0; a < MessagesController$65.this.this$0.dialogs.size(); a++) {
                MessageObject messageObject;
                TLRPC$TL_dialog oldDialog = (TLRPC$TL_dialog) MessagesController$65.this.this$0.dialogs.get(a);
                if (((int) oldDialog.id) != 0) {
                    MessagesController$65.this.this$0.dialogs_dict.remove(Long.valueOf(oldDialog.id));
                    messageObject = (MessageObject) MessagesController$65.this.this$0.dialogMessage.remove(Long.valueOf(oldDialog.id));
                    if (messageObject != null) {
                        MessagesController$65.this.this$0.dialogMessagesByIds.remove(Integer.valueOf(messageObject.getId()));
                        if (messageObject.messageOwner.random_id != 0) {
                            MessagesController$65.this.this$0.dialogMessagesByRandomIds.remove(Long.valueOf(messageObject.messageOwner.random_id));
                        }
                    }
                }
            }
            for (Entry<Long, TLRPC$TL_dialog> pair : MessagesController$65.this.val$new_dialogs_dict.entrySet()) {
                Long key = (Long) pair.getKey();
                TLRPC$TL_dialog value = (TLRPC$TL_dialog) pair.getValue();
                if (value.draft instanceof TLRPC$TL_draftMessage) {
                    DraftQuery.saveDraft(value.id, value.draft, null, false);
                }
                MessagesController$65.this.this$0.dialogs_dict.put(key, value);
                messageObject = (MessageObject) MessagesController$65.this.val$new_dialogMessage.get(Long.valueOf(value.id));
                MessagesController$65.this.this$0.dialogMessage.put(key, messageObject);
                if (messageObject != null && messageObject.messageOwner.to_id.channel_id == 0) {
                    MessagesController$65.this.this$0.dialogMessagesByIds.put(Integer.valueOf(messageObject.getId()), messageObject);
                    if (messageObject.messageOwner.random_id != 0) {
                        MessagesController$65.this.this$0.dialogMessagesByRandomIds.put(Long.valueOf(messageObject.messageOwner.random_id), messageObject);
                    }
                }
            }
            MessagesController$65.this.this$0.dialogs.clear();
            MessagesController$65.this.this$0.dialogs.addAll(MessagesController$65.this.this$0.dialogs_dict.values());
            MessagesController$65.this.this$0.sortDialogs(null);
            MessagesController$65.this.this$0.dialogsEndReached = true;
            MessagesController$65.this.this$0.serverDialogsEndReached = false;
            if (!(UserConfig.totalDialogsLoadCount >= ChatActivity.scheduleDownloads || UserConfig.dialogsLoadOffsetId == -1 || UserConfig.dialogsLoadOffsetId == Integer.MAX_VALUE)) {
                MessagesController$65.this.this$0.loadDialogs(0, 100, false);
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
        }
    }

    MessagesController$65(MessagesController this$0, int i, int i2, int i3, TLRPC$messages_Dialogs tLRPC$messages_Dialogs, HashMap hashMap, HashMap hashMap2) {
        this.this$0 = this$0;
        this.val$newPts = i;
        this.val$date = i2;
        this.val$qts = i3;
        this.val$dialogsRes = tLRPC$messages_Dialogs;
        this.val$new_dialogs_dict = hashMap;
        this.val$new_dialogMessage = hashMap2;
    }

    public void run() {
        this.this$0.gettingDifference = false;
        MessagesStorage.lastPtsValue = this.val$newPts;
        MessagesStorage.lastDateValue = this.val$date;
        MessagesStorage.lastQtsValue = this.val$qts;
        this.this$0.getDifference();
        AndroidUtilities.runOnUIThread(new C14961());
    }
}
