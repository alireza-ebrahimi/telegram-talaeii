package org.telegram.messenger;

import android.text.TextUtils;
import java.util.ArrayList;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messageMediaGame;
import org.telegram.tgnet.TLRPC$TL_messages_sendBroadcast;
import org.telegram.tgnet.TLRPC$TL_updateNewChannelMessage;
import org.telegram.tgnet.TLRPC$TL_updateNewMessage;
import org.telegram.tgnet.TLRPC$TL_updateShortSentMessage;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.ui.Components.AlertsCreator;

class SendMessagesHelper$13 implements RequestDelegate {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ MessageObject val$msgObj;
    final /* synthetic */ TLRPC$Message val$newMsgObj;
    final /* synthetic */ String val$originalPath;
    final /* synthetic */ TLObject val$req;

    SendMessagesHelper$13(SendMessagesHelper this$0, TLRPC$Message tLRPC$Message, TLObject tLObject, MessageObject messageObject, String str) {
        this.this$0 = this$0;
        this.val$newMsgObj = tLRPC$Message;
        this.val$req = tLObject;
        this.val$msgObj = messageObject;
        this.val$originalPath = str;
    }

    public void run(final TLObject response, final TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                boolean isSentError = false;
                if (error == null) {
                    int i;
                    int oldId = SendMessagesHelper$13.this.val$newMsgObj.id;
                    boolean isBroadcast = SendMessagesHelper$13.this.val$req instanceof TLRPC$TL_messages_sendBroadcast;
                    ArrayList<TLRPC$Message> sentMessages = new ArrayList();
                    String attachPath = SendMessagesHelper$13.this.val$newMsgObj.attachPath;
                    TLRPC$Message tLRPC$Message;
                    if (response instanceof TLRPC$TL_updateShortSentMessage) {
                        TLRPC$TL_updateShortSentMessage res = (TLRPC$TL_updateShortSentMessage) response;
                        tLRPC$Message = SendMessagesHelper$13.this.val$newMsgObj;
                        TLRPC$Message tLRPC$Message2 = SendMessagesHelper$13.this.val$newMsgObj;
                        i = res.id;
                        tLRPC$Message2.id = i;
                        tLRPC$Message.local_id = i;
                        SendMessagesHelper$13.this.val$newMsgObj.date = res.date;
                        SendMessagesHelper$13.this.val$newMsgObj.entities = res.entities;
                        SendMessagesHelper$13.this.val$newMsgObj.out = res.out;
                        if (res.media != null) {
                            SendMessagesHelper$13.this.val$newMsgObj.media = res.media;
                            tLRPC$Message = SendMessagesHelper$13.this.val$newMsgObj;
                            tLRPC$Message.flags |= 512;
                        }
                        if ((res.media instanceof TLRPC$TL_messageMediaGame) && !TextUtils.isEmpty(res.message)) {
                            SendMessagesHelper$13.this.val$newMsgObj.message = res.message;
                        }
                        if (!SendMessagesHelper$13.this.val$newMsgObj.entities.isEmpty()) {
                            tLRPC$Message = SendMessagesHelper$13.this.val$newMsgObj;
                            tLRPC$Message.flags |= 128;
                        }
                        final TLRPC$TL_updateShortSentMessage tLRPC$TL_updateShortSentMessage = res;
                        Utilities.stageQueue.postRunnable(new Runnable() {
                            public void run() {
                                MessagesController.getInstance().processNewDifferenceParams(-1, tLRPC$TL_updateShortSentMessage.pts, tLRPC$TL_updateShortSentMessage.date, tLRPC$TL_updateShortSentMessage.pts_count);
                            }
                        });
                        sentMessages.add(SendMessagesHelper$13.this.val$newMsgObj);
                    } else if (response instanceof TLRPC$Updates) {
                        TLRPC$Updates updates = (TLRPC$Updates) response;
                        ArrayList<TLRPC$Update> updatesArr = ((TLRPC$Updates) response).updates;
                        TLRPC$Message message = null;
                        int a = 0;
                        while (a < updatesArr.size()) {
                            TLRPC$Update update = (TLRPC$Update) updatesArr.get(a);
                            if (update instanceof TLRPC$TL_updateNewMessage) {
                                final TLRPC$TL_updateNewMessage newMessage = (TLRPC$TL_updateNewMessage) update;
                                message = newMessage.message;
                                sentMessages.add(message);
                                Utilities.stageQueue.postRunnable(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().processNewDifferenceParams(-1, newMessage.pts, -1, newMessage.pts_count);
                                    }
                                });
                                updatesArr.remove(a);
                                break;
                            } else if (update instanceof TLRPC$TL_updateNewChannelMessage) {
                                final TLRPC$TL_updateNewChannelMessage newMessage2 = (TLRPC$TL_updateNewChannelMessage) update;
                                message = newMessage2.message;
                                sentMessages.add(message);
                                if ((SendMessagesHelper$13.this.val$newMsgObj.flags & Integer.MIN_VALUE) != 0) {
                                    tLRPC$Message = newMessage2.message;
                                    tLRPC$Message.flags |= Integer.MIN_VALUE;
                                }
                                Utilities.stageQueue.postRunnable(new Runnable() {
                                    public void run() {
                                        MessagesController.getInstance().processNewChannelDifferenceParams(newMessage2.pts, newMessage2.pts_count, newMessage2.message.to_id.channel_id);
                                    }
                                });
                                updatesArr.remove(a);
                            } else {
                                a++;
                            }
                        }
                        if (message != null) {
                            Integer value = (Integer) MessagesController.getInstance().dialogs_read_outbox_max.get(Long.valueOf(message.dialog_id));
                            if (value == null) {
                                value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                                MessagesController.getInstance().dialogs_read_outbox_max.put(Long.valueOf(message.dialog_id), value);
                            }
                            message.unread = value.intValue() < message.id;
                            SendMessagesHelper$13.this.val$newMsgObj.id = message.id;
                            SendMessagesHelper.access$1200(SendMessagesHelper$13.this.this$0, SendMessagesHelper$13.this.val$msgObj, message, SendMessagesHelper$13.this.val$originalPath, false);
                        } else {
                            isSentError = true;
                        }
                        final TLRPC$Updates tLRPC$Updates = updates;
                        Utilities.stageQueue.postRunnable(new Runnable() {
                            public void run() {
                                MessagesController.getInstance().processUpdates(tLRPC$Updates, false);
                            }
                        });
                    }
                    if (MessageObject.isLiveLocationMessage(SendMessagesHelper$13.this.val$newMsgObj)) {
                        LocationController.getInstance().addSharingLocation(SendMessagesHelper$13.this.val$newMsgObj.dialog_id, SendMessagesHelper$13.this.val$newMsgObj.id, SendMessagesHelper$13.this.val$newMsgObj.media.period, SendMessagesHelper$13.this.val$newMsgObj);
                    }
                    if (!isSentError) {
                        int i2;
                        StatsController.getInstance().incrementSentItemsCount(ConnectionsManager.getCurrentNetworkType(), 1, 1);
                        SendMessagesHelper$13.this.val$newMsgObj.send_state = 0;
                        NotificationCenter instance = NotificationCenter.getInstance();
                        i = NotificationCenter.messageReceivedByServer;
                        Object[] objArr = new Object[4];
                        objArr[0] = Integer.valueOf(oldId);
                        if (isBroadcast) {
                            i2 = oldId;
                        } else {
                            i2 = SendMessagesHelper$13.this.val$newMsgObj.id;
                        }
                        objArr[1] = Integer.valueOf(i2);
                        objArr[2] = SendMessagesHelper$13.this.val$newMsgObj;
                        objArr[3] = Long.valueOf(SendMessagesHelper$13.this.val$newMsgObj.dialog_id);
                        instance.postNotificationName(i, objArr);
                        i = oldId;
                        final boolean z = isBroadcast;
                        final ArrayList<TLRPC$Message> arrayList = sentMessages;
                        final String str = attachPath;
                        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                            /* renamed from: org.telegram.messenger.SendMessagesHelper$13$1$5$1 */
                            class C16361 implements Runnable {
                                C16361() {
                                }

                                public void run() {
                                    if (z) {
                                        for (int a = 0; a < arrayList.size(); a++) {
                                            TLRPC$Message message = (TLRPC$Message) arrayList.get(a);
                                            ArrayList<MessageObject> arr = new ArrayList();
                                            MessageObject messageObject = new MessageObject(message, null, false);
                                            arr.add(messageObject);
                                            MessagesController.getInstance().updateInterfaceWithMessages(messageObject.getDialogId(), arr, true);
                                        }
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                    }
                                    SearchQuery.increasePeerRaiting(SendMessagesHelper$13.this.val$newMsgObj.dialog_id);
                                    NotificationCenter instance = NotificationCenter.getInstance();
                                    int i = NotificationCenter.messageReceivedByServer;
                                    Object[] objArr = new Object[4];
                                    objArr[0] = Integer.valueOf(i);
                                    objArr[1] = Integer.valueOf(z ? i : SendMessagesHelper$13.this.val$newMsgObj.id);
                                    objArr[2] = SendMessagesHelper$13.this.val$newMsgObj;
                                    objArr[3] = Long.valueOf(SendMessagesHelper$13.this.val$newMsgObj.dialog_id);
                                    instance.postNotificationName(i, objArr);
                                    SendMessagesHelper$13.this.this$0.processSentMessage(i);
                                    SendMessagesHelper$13.this.this$0.removeFromSendingMessages(i);
                                }
                            }

                            public void run() {
                                MessagesStorage.getInstance().updateMessageStateAndId(SendMessagesHelper$13.this.val$newMsgObj.random_id, Integer.valueOf(i), z ? i : SendMessagesHelper$13.this.val$newMsgObj.id, 0, false, SendMessagesHelper$13.this.val$newMsgObj.to_id.channel_id);
                                MessagesStorage.getInstance().putMessages(arrayList, true, false, z, 0);
                                if (z) {
                                    ArrayList currentMessage = new ArrayList();
                                    currentMessage.add(SendMessagesHelper$13.this.val$newMsgObj);
                                    MessagesStorage.getInstance().putMessages(currentMessage, true, false, false, 0);
                                }
                                AndroidUtilities.runOnUIThread(new C16361());
                                if (MessageObject.isVideoMessage(SendMessagesHelper$13.this.val$newMsgObj) || MessageObject.isRoundVideoMessage(SendMessagesHelper$13.this.val$newMsgObj) || MessageObject.isNewGifMessage(SendMessagesHelper$13.this.val$newMsgObj)) {
                                    SendMessagesHelper$13.this.this$0.stopVideoService(str);
                                }
                            }
                        });
                    }
                } else {
                    AlertsCreator.processError(error, null, SendMessagesHelper$13.this.val$req, new Object[0]);
                    isSentError = true;
                }
                if (isSentError) {
                    MessagesStorage.getInstance().markMessageAsSendError(SendMessagesHelper$13.this.val$newMsgObj);
                    SendMessagesHelper$13.this.val$newMsgObj.send_state = 2;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, new Object[]{Integer.valueOf(SendMessagesHelper$13.this.val$newMsgObj.id)});
                    SendMessagesHelper$13.this.this$0.processSentMessage(SendMessagesHelper$13.this.val$newMsgObj.id);
                    if (MessageObject.isVideoMessage(SendMessagesHelper$13.this.val$newMsgObj) || MessageObject.isRoundVideoMessage(SendMessagesHelper$13.this.val$newMsgObj) || MessageObject.isNewGifMessage(SendMessagesHelper$13.this.val$newMsgObj)) {
                        SendMessagesHelper$13.this.this$0.stopVideoService(SendMessagesHelper$13.this.val$newMsgObj.attachPath);
                    }
                    SendMessagesHelper$13.this.this$0.removeFromSendingMessages(SendMessagesHelper$13.this.val$newMsgObj.id);
                }
            }
        });
    }
}
