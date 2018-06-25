package org.telegram.messenger;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_channelParticipantSelf;
import org.telegram.tgnet.TLRPC$TL_channels_channelParticipant;
import org.telegram.tgnet.TLRPC$TL_channels_getParticipant;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputUserSelf;
import org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_peerChannel;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$118 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$chat_id;

    MessagesController$118(MessagesController this$0, int i) {
        this.this$0 = this$0;
        this.val$chat_id = i;
    }

    public void run() {
        final TLRPC$Chat chat = this.this$0.getChat(Integer.valueOf(this.val$chat_id));
        if (chat != null && ChatObject.isChannel(this.val$chat_id) && !chat.creator) {
            TLRPC$TL_channels_getParticipant req = new TLRPC$TL_channels_getParticipant();
            req.channel = MessagesController.getInputChannel(this.val$chat_id);
            req.user_id = new TLRPC$TL_inputUserSelf();
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(TLObject response, TLRPC$TL_error error) {
                    final TLRPC$TL_channels_channelParticipant res = (TLRPC$TL_channels_channelParticipant) response;
                    if (res != null && (res.participant instanceof TLRPC$TL_channelParticipantSelf) && res.participant.inviter_id != UserConfig.getClientUserId()) {
                        if (!chat.megagroup || !MessagesStorage.getInstance().isMigratedChat(chat.id)) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    MessagesController$118.this.this$0.putUsers(res.users, false);
                                }
                            });
                            MessagesStorage.getInstance().putUsersAndChats(res.users, null, true, true);
                            TLRPC$TL_messageService message = new TLRPC$TL_messageService();
                            message.media_unread = true;
                            message.unread = true;
                            message.flags = 256;
                            message.post = true;
                            if (chat.megagroup) {
                                message.flags |= Integer.MIN_VALUE;
                            }
                            int newMessageId = UserConfig.getNewMessageId();
                            message.id = newMessageId;
                            message.local_id = newMessageId;
                            message.date = res.participant.date;
                            message.action = new TLRPC$TL_messageActionChatAddUser();
                            message.from_id = res.participant.inviter_id;
                            message.action.users.add(Integer.valueOf(UserConfig.getClientUserId()));
                            message.to_id = new TLRPC$TL_peerChannel();
                            message.to_id.channel_id = MessagesController$118.this.val$chat_id;
                            message.dialog_id = (long) (-MessagesController$118.this.val$chat_id);
                            UserConfig.saveConfig(false);
                            final ArrayList<MessageObject> pushMessages = new ArrayList();
                            ArrayList messagesArr = new ArrayList();
                            ConcurrentHashMap<Integer, User> usersDict = new ConcurrentHashMap();
                            for (int a = 0; a < res.users.size(); a++) {
                                User user = (User) res.users.get(a);
                                usersDict.put(Integer.valueOf(user.id), user);
                            }
                            messagesArr.add(message);
                            pushMessages.add(new MessageObject(message, usersDict, true));
                            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                /* renamed from: org.telegram.messenger.MessagesController$118$1$2$1 */
                                class C14571 implements Runnable {
                                    C14571() {
                                    }

                                    public void run() {
                                        NotificationsController.getInstance().processNewMessages(pushMessages, true);
                                    }
                                }

                                public void run() {
                                    AndroidUtilities.runOnUIThread(new C14571());
                                }
                            });
                            MessagesStorage.getInstance().putMessages(messagesArr, true, true, false, 0);
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    MessagesController$118.this.this$0.updateInterfaceWithMessages((long) (-MessagesController$118.this.val$chat_id), pushMessages);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                }
                            });
                        }
                    }
                }
            });
        }
    }
}
