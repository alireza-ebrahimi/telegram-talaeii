package org.telegram.messenger;

import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_forwardMessages;
import org.telegram.tgnet.TLRPC$TL_updateMessageID;
import org.telegram.tgnet.TLRPC$TL_updateNewChannelMessage;
import org.telegram.tgnet.TLRPC$TL_updateNewMessage;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.ui.Components.AlertsCreator;

class SendMessagesHelper$6 implements RequestDelegate {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ boolean val$isMegagroupFinal;
    final /* synthetic */ HashMap val$messagesByRandomIdsFinal;
    final /* synthetic */ ArrayList val$newMsgArr;
    final /* synthetic */ ArrayList val$newMsgObjArr;
    final /* synthetic */ long val$peer;
    final /* synthetic */ TLRPC$TL_messages_forwardMessages val$req;
    final /* synthetic */ boolean val$toMyself;
    final /* synthetic */ TLRPC$Peer val$to_id;

    SendMessagesHelper$6(SendMessagesHelper this$0, long j, boolean z, boolean z2, HashMap hashMap, ArrayList arrayList, ArrayList arrayList2, TLRPC$Peer tLRPC$Peer, TLRPC$TL_messages_forwardMessages tLRPC$TL_messages_forwardMessages) {
        this.this$0 = this$0;
        this.val$peer = j;
        this.val$isMegagroupFinal = z;
        this.val$toMyself = z2;
        this.val$messagesByRandomIdsFinal = hashMap;
        this.val$newMsgObjArr = arrayList;
        this.val$newMsgArr = arrayList2;
        this.val$to_id = tLRPC$Peer;
        this.val$req = tLRPC$TL_messages_forwardMessages;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        int a;
        final TLRPC$Message newMsgObj;
        if (error == null) {
            TLRPC$Update update;
            HashMap<Integer, Long> newMessagesByIds = new HashMap();
            TLRPC$Updates updates = (TLRPC$Updates) response;
            a = 0;
            while (a < updates.updates.size()) {
                update = (TLRPC$Update) updates.updates.get(a);
                if (update instanceof TLRPC$TL_updateMessageID) {
                    TLRPC$TL_updateMessageID updateMessageID = (TLRPC$TL_updateMessageID) update;
                    newMessagesByIds.put(Integer.valueOf(updateMessageID.id), Long.valueOf(updateMessageID.random_id));
                    updates.updates.remove(a);
                    a--;
                }
                a++;
            }
            Integer value = (Integer) MessagesController.getInstance().dialogs_read_outbox_max.get(Long.valueOf(this.val$peer));
            if (value == null) {
                value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(true, this.val$peer));
                MessagesController.getInstance().dialogs_read_outbox_max.put(Long.valueOf(this.val$peer), value);
            }
            int sentCount = 0;
            a = 0;
            while (a < updates.updates.size()) {
                update = (TLRPC$Update) updates.updates.get(a);
                if ((update instanceof TLRPC$TL_updateNewMessage) || (update instanceof TLRPC$TL_updateNewChannelMessage)) {
                    TLRPC$Message message;
                    updates.updates.remove(a);
                    a--;
                    if (update instanceof TLRPC$TL_updateNewMessage) {
                        message = ((TLRPC$TL_updateNewMessage) update).message;
                        MessagesController.getInstance().processNewDifferenceParams(-1, update.pts, -1, update.pts_count);
                    } else {
                        message = ((TLRPC$TL_updateNewChannelMessage) update).message;
                        MessagesController.getInstance().processNewChannelDifferenceParams(update.pts, update.pts_count, message.to_id.channel_id);
                        if (this.val$isMegagroupFinal) {
                            message.flags |= Integer.MIN_VALUE;
                        }
                    }
                    message.unread = value.intValue() < message.id;
                    if (this.val$toMyself) {
                        message.out = true;
                        message.unread = false;
                        message.media_unread = false;
                    }
                    Long random_id = (Long) newMessagesByIds.get(Integer.valueOf(message.id));
                    if (random_id != null) {
                        newMsgObj = (TLRPC$Message) this.val$messagesByRandomIdsFinal.get(random_id);
                        if (newMsgObj != null) {
                            int index = this.val$newMsgObjArr.indexOf(newMsgObj);
                            if (index != -1) {
                                MessageObject msgObj = (MessageObject) this.val$newMsgArr.get(index);
                                this.val$newMsgObjArr.remove(index);
                                this.val$newMsgArr.remove(index);
                                final int oldId = newMsgObj.id;
                                final ArrayList<TLRPC$Message> sentMessages = new ArrayList();
                                sentMessages.add(message);
                                newMsgObj.id = message.id;
                                sentCount++;
                                SendMessagesHelper.access$1200(this.this$0, msgObj, message, null, true);
                                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                    /* renamed from: org.telegram.messenger.SendMessagesHelper$6$1$1 */
                                    class C16541 implements Runnable {
                                        C16541() {
                                        }

                                        public void run() {
                                            newMsgObj.send_state = 0;
                                            SearchQuery.increasePeerRaiting(SendMessagesHelper$6.this.val$peer);
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, new Object[]{Integer.valueOf(oldId), Integer.valueOf(message.id), message, Long.valueOf(SendMessagesHelper$6.this.val$peer)});
                                            SendMessagesHelper$6.this.this$0.processSentMessage(oldId);
                                            SendMessagesHelper$6.this.this$0.removeFromSendingMessages(oldId);
                                        }
                                    }

                                    public void run() {
                                        MessagesStorage.getInstance().updateMessageStateAndId(newMsgObj.random_id, Integer.valueOf(oldId), newMsgObj.id, 0, false, SendMessagesHelper$6.this.val$to_id.channel_id);
                                        MessagesStorage.getInstance().putMessages(sentMessages, true, false, false, 0);
                                        AndroidUtilities.runOnUIThread(new C16541());
                                    }
                                });
                            }
                        }
                    }
                }
                a++;
            }
            if (!updates.updates.isEmpty()) {
                MessagesController.getInstance().processUpdates(updates, false);
            }
            StatsController.getInstance().incrementSentItemsCount(ConnectionsManager.getCurrentNetworkType(), 1, sentCount);
        } else {
            final TLRPC$TL_error tLRPC$TL_error = error;
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    AlertsCreator.processError(tLRPC$TL_error, null, SendMessagesHelper$6.this.val$req, new Object[0]);
                }
            });
        }
        for (a = 0; a < this.val$newMsgObjArr.size(); a++) {
            newMsgObj = (TLRPC$Message) this.val$newMsgObjArr.get(a);
            MessagesStorage.getInstance().markMessageAsSendError(newMsgObj);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    newMsgObj.send_state = 2;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, new Object[]{Integer.valueOf(newMsgObj.id)});
                    SendMessagesHelper$6.this.this$0.processSentMessage(newMsgObj.id);
                    SendMessagesHelper$6.this.this$0.removeFromSendingMessages(newMsgObj.id);
                }
            });
        }
    }
}
