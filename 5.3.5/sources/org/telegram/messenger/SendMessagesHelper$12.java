package org.telegram.messenger;

import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_sendMultiMedia;
import org.telegram.tgnet.TLRPC$TL_updateMessageID;
import org.telegram.tgnet.TLRPC$TL_updateNewChannelMessage;
import org.telegram.tgnet.TLRPC$TL_updateNewMessage;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.ui.Components.AlertsCreator;

class SendMessagesHelper$12 implements RequestDelegate {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ ArrayList val$msgObjs;
    final /* synthetic */ ArrayList val$originalPaths;
    final /* synthetic */ TLRPC$TL_messages_sendMultiMedia val$req;

    SendMessagesHelper$12(SendMessagesHelper this$0, ArrayList arrayList, ArrayList arrayList2, TLRPC$TL_messages_sendMultiMedia tLRPC$TL_messages_sendMultiMedia) {
        this.this$0 = this$0;
        this.val$msgObjs = arrayList;
        this.val$originalPaths = arrayList2;
        this.val$req = tLRPC$TL_messages_sendMultiMedia;
    }

    public void run(final TLObject response, final TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                int i;
                final TLRPC$Message newMsgObj;
                boolean isSentError = false;
                if (error == null) {
                    HashMap<Integer, TLRPC$Message> newMessages = new HashMap();
                    HashMap<Long, Integer> newIds = new HashMap();
                    TLRPC$Updates updates = (TLRPC$Updates) response;
                    ArrayList<TLRPC$Update> updatesArr = ((TLRPC$Updates) response).updates;
                    int a = 0;
                    while (a < updatesArr.size()) {
                        TLRPC$Update update = (TLRPC$Update) updatesArr.get(a);
                        if (update instanceof TLRPC$TL_updateMessageID) {
                            newIds.put(Long.valueOf(update.random_id), Integer.valueOf(((TLRPC$TL_updateMessageID) update).id));
                            updatesArr.remove(a);
                            a--;
                        } else if (update instanceof TLRPC$TL_updateNewMessage) {
                            final TLRPC$TL_updateNewMessage newMessage = (TLRPC$TL_updateNewMessage) update;
                            newMessages.put(Integer.valueOf(newMessage.message.id), newMessage.message);
                            Utilities.stageQueue.postRunnable(new Runnable() {
                                public void run() {
                                    MessagesController.getInstance().processNewDifferenceParams(-1, newMessage.pts, -1, newMessage.pts_count);
                                }
                            });
                            updatesArr.remove(a);
                            a--;
                        } else if (update instanceof TLRPC$TL_updateNewChannelMessage) {
                            final TLRPC$TL_updateNewChannelMessage newMessage2 = (TLRPC$TL_updateNewChannelMessage) update;
                            newMessages.put(Integer.valueOf(newMessage2.message.id), newMessage2.message);
                            Utilities.stageQueue.postRunnable(new Runnable() {
                                public void run() {
                                    MessagesController.getInstance().processNewChannelDifferenceParams(newMessage2.pts, newMessage2.pts_count, newMessage2.message.to_id.channel_id);
                                }
                            });
                            updatesArr.remove(a);
                            a--;
                        }
                        a++;
                    }
                    for (i = 0; i < SendMessagesHelper$12.this.val$msgObjs.size(); i++) {
                        MessageObject msgObj = (MessageObject) SendMessagesHelper$12.this.val$msgObjs.get(i);
                        String originalPath = (String) SendMessagesHelper$12.this.val$originalPaths.get(i);
                        newMsgObj = msgObj.messageOwner;
                        final int oldId = newMsgObj.id;
                        ArrayList<TLRPC$Message> sentMessages = new ArrayList();
                        String attachPath = newMsgObj.attachPath;
                        Integer id = (Integer) newIds.get(Long.valueOf(newMsgObj.random_id));
                        if (id == null) {
                            isSentError = true;
                            break;
                        }
                        TLRPC$Message message = (TLRPC$Message) newMessages.get(id);
                        if (message == null) {
                            isSentError = true;
                            break;
                        }
                        sentMessages.add(message);
                        newMsgObj.id = message.id;
                        if ((newMsgObj.flags & Integer.MIN_VALUE) != 0) {
                            message.flags |= Integer.MIN_VALUE;
                        }
                        Integer value = (Integer) MessagesController.getInstance().dialogs_read_outbox_max.get(Long.valueOf(message.dialog_id));
                        if (value == null) {
                            value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                            MessagesController.getInstance().dialogs_read_outbox_max.put(Long.valueOf(message.dialog_id), value);
                        }
                        message.unread = value.intValue() < message.id;
                        SendMessagesHelper.access$1200(SendMessagesHelper$12.this.this$0, msgObj, message, originalPath, false);
                        if (null == null) {
                            StatsController.getInstance().incrementSentItemsCount(ConnectionsManager.getCurrentNetworkType(), 1, 1);
                            newMsgObj.send_state = 0;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, new Object[]{Integer.valueOf(oldId), Integer.valueOf(newMsgObj.id), newMsgObj, Long.valueOf(newMsgObj.dialog_id)});
                            final ArrayList<TLRPC$Message> arrayList = sentMessages;
                            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                /* renamed from: org.telegram.messenger.SendMessagesHelper$12$1$3$1 */
                                class C16281 implements Runnable {
                                    C16281() {
                                    }

                                    public void run() {
                                        SearchQuery.increasePeerRaiting(newMsgObj.dialog_id);
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, new Object[]{Integer.valueOf(oldId), Integer.valueOf(newMsgObj.id), newMsgObj, Long.valueOf(newMsgObj.dialog_id)});
                                        SendMessagesHelper$12.this.this$0.processSentMessage(oldId);
                                        SendMessagesHelper$12.this.this$0.removeFromSendingMessages(oldId);
                                    }
                                }

                                public void run() {
                                    MessagesStorage.getInstance().updateMessageStateAndId(newMsgObj.random_id, Integer.valueOf(oldId), newMsgObj.id, 0, false, newMsgObj.to_id.channel_id);
                                    MessagesStorage.getInstance().putMessages(arrayList, true, false, false, 0);
                                    AndroidUtilities.runOnUIThread(new C16281());
                                }
                            });
                        }
                    }
                    final TLRPC$Updates tLRPC$Updates = updates;
                    Utilities.stageQueue.postRunnable(new Runnable() {
                        public void run() {
                            MessagesController.getInstance().processUpdates(tLRPC$Updates, false);
                        }
                    });
                } else {
                    AlertsCreator.processError(error, null, SendMessagesHelper$12.this.val$req, new Object[0]);
                    isSentError = true;
                }
                if (isSentError) {
                    for (i = 0; i < SendMessagesHelper$12.this.val$msgObjs.size(); i++) {
                        newMsgObj = ((MessageObject) SendMessagesHelper$12.this.val$msgObjs.get(i)).messageOwner;
                        MessagesStorage.getInstance().markMessageAsSendError(newMsgObj);
                        newMsgObj.send_state = 2;
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, new Object[]{Integer.valueOf(newMsgObj.id)});
                        SendMessagesHelper$12.this.this$0.processSentMessage(newMsgObj.id);
                        SendMessagesHelper$12.this.this$0.removeFromSendingMessages(newMsgObj.id);
                    }
                }
            }
        });
    }
}
