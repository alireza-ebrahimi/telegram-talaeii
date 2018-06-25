package org.telegram.messenger;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.messenger.query.MessagesQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatParticipants;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$PhoneCall;
import org.telegram.tgnet.TLRPC$TL_channel;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPhoneCall;
import org.telegram.tgnet.TLRPC$TL_notifyPeer;
import org.telegram.tgnet.TLRPC$TL_peerChat;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
import org.telegram.tgnet.TLRPC$TL_phoneCallRequested;
import org.telegram.tgnet.TLRPC$TL_phone_discardCall;
import org.telegram.tgnet.TLRPC$TL_privacyKeyChatInvite;
import org.telegram.tgnet.TLRPC$TL_privacyKeyPhoneCall;
import org.telegram.tgnet.TLRPC$TL_privacyKeyStatusTimestamp;
import org.telegram.tgnet.TLRPC$TL_updateChannel;
import org.telegram.tgnet.TLRPC$TL_updateChatAdmins;
import org.telegram.tgnet.TLRPC$TL_updateContactsReset;
import org.telegram.tgnet.TLRPC$TL_updateDialogPinned;
import org.telegram.tgnet.TLRPC$TL_updateDraftMessage;
import org.telegram.tgnet.TLRPC$TL_updateFavedStickers;
import org.telegram.tgnet.TLRPC$TL_updateGroupCall;
import org.telegram.tgnet.TLRPC$TL_updateGroupCallParticipant;
import org.telegram.tgnet.TLRPC$TL_updateNewStickerSet;
import org.telegram.tgnet.TLRPC$TL_updateNotifySettings;
import org.telegram.tgnet.TLRPC$TL_updatePhoneCall;
import org.telegram.tgnet.TLRPC$TL_updatePinnedDialogs;
import org.telegram.tgnet.TLRPC$TL_updatePrivacy;
import org.telegram.tgnet.TLRPC$TL_updateReadFeaturedStickers;
import org.telegram.tgnet.TLRPC$TL_updateRecentStickers;
import org.telegram.tgnet.TLRPC$TL_updateSavedGifs;
import org.telegram.tgnet.TLRPC$TL_updateStickerSets;
import org.telegram.tgnet.TLRPC$TL_updateStickerSetsOrder;
import org.telegram.tgnet.TLRPC$TL_updateUserName;
import org.telegram.tgnet.TLRPC$TL_updateUserPhone;
import org.telegram.tgnet.TLRPC$TL_updateUserPhoto;
import org.telegram.tgnet.TLRPC$TL_updateUserStatus;
import org.telegram.tgnet.TLRPC$TL_user;
import org.telegram.tgnet.TLRPC$TL_userStatusLastMonth;
import org.telegram.tgnet.TLRPC$TL_userStatusLastWeek;
import org.telegram.tgnet.TLRPC$TL_userStatusRecently;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$TL_webPageEmpty;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$129 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ SparseArray val$channelViews;
    final /* synthetic */ ArrayList val$chatInfoToUpdate;
    final /* synthetic */ ArrayList val$contactsIds;
    final /* synthetic */ HashMap val$editingMessages;
    final /* synthetic */ int val$interfaceUpdateMaskFinal;
    final /* synthetic */ HashMap val$messages;
    final /* synthetic */ boolean val$printChangedArg;
    final /* synthetic */ ArrayList val$updatesOnMainThread;
    final /* synthetic */ HashMap val$webPages;

    /* renamed from: org.telegram.messenger.MessagesController$129$3 */
    class C14663 implements RequestDelegate {
        C14663() {
        }

        public void run(TLObject response, TLRPC$TL_error error) {
            if (response != null) {
                MessagesController$129.this.this$0.processUpdates((TLRPC$Updates) response, false);
            }
        }
    }

    MessagesController$129(MessagesController this$0, int i, ArrayList arrayList, HashMap hashMap, HashMap hashMap2, HashMap hashMap3, boolean z, ArrayList arrayList2, ArrayList arrayList3, SparseArray sparseArray) {
        this.this$0 = this$0;
        this.val$interfaceUpdateMaskFinal = i;
        this.val$updatesOnMainThread = arrayList;
        this.val$webPages = hashMap;
        this.val$messages = hashMap2;
        this.val$editingMessages = hashMap3;
        this.val$printChangedArg = z;
        this.val$contactsIds = arrayList2;
        this.val$chatInfoToUpdate = arrayList3;
        this.val$channelViews = sparseArray;
    }

    public void run() {
        int a;
        long dialog_id;
        ArrayList<MessageObject> arrayList;
        int updateMask = this.val$interfaceUpdateMaskFinal;
        boolean hasDraftUpdates = false;
        if (!this.val$updatesOnMainThread.isEmpty()) {
            ArrayList<User> dbUsers = new ArrayList();
            ArrayList<User> dbUsersStatus = new ArrayList();
            Editor editor = null;
            for (a = 0; a < this.val$updatesOnMainThread.size(); a++) {
                TLRPC$Update update = (TLRPC$Update) this.val$updatesOnMainThread.get(a);
                User toDbUser = new TLRPC$TL_user();
                toDbUser.id = update.user_id;
                User currentUser = this.this$0.getUser(Integer.valueOf(update.user_id));
                if (update instanceof TLRPC$TL_updatePrivacy) {
                    if (update.key instanceof TLRPC$TL_privacyKeyStatusTimestamp) {
                        ContactsController.getInstance().setPrivacyRules(update.rules, 0);
                    } else if (update.key instanceof TLRPC$TL_privacyKeyChatInvite) {
                        ContactsController.getInstance().setPrivacyRules(update.rules, 1);
                    } else if (update.key instanceof TLRPC$TL_privacyKeyPhoneCall) {
                        ContactsController.getInstance().setPrivacyRules(update.rules, 2);
                    }
                } else if (update instanceof TLRPC$TL_updateUserStatus) {
                    if (update.status instanceof TLRPC$TL_userStatusRecently) {
                        update.status.expires = -100;
                    } else if (update.status instanceof TLRPC$TL_userStatusLastWeek) {
                        update.status.expires = FetchConst.ERROR_UNKNOWN;
                    } else if (update.status instanceof TLRPC$TL_userStatusLastMonth) {
                        update.status.expires = FetchConst.ERROR_FILE_NOT_CREATED;
                    }
                    if (currentUser != null) {
                        currentUser.id = update.user_id;
                        currentUser.status = update.status;
                    }
                    toDbUser.status = update.status;
                    dbUsersStatus.add(toDbUser);
                    if (update.user_id == UserConfig.getClientUserId()) {
                        NotificationsController.getInstance().setLastOnlineFromOtherDevice(update.status.expires);
                    }
                } else if (update instanceof TLRPC$TL_updateUserName) {
                    if (currentUser != null) {
                        if (!UserObject.isContact(currentUser)) {
                            currentUser.first_name = update.first_name;
                            currentUser.last_name = update.last_name;
                        }
                        if (currentUser.username != null && currentUser.username.length() > 0) {
                            MessagesController.access$6300(this.this$0).remove(currentUser.username);
                        }
                        if (update.username != null && update.username.length() > 0) {
                            MessagesController.access$6300(this.this$0).put(update.username, currentUser);
                        }
                        currentUser.username = update.username;
                    }
                    toDbUser.first_name = update.first_name;
                    toDbUser.last_name = update.last_name;
                    toDbUser.username = update.username;
                    dbUsers.add(toDbUser);
                } else if (update instanceof TLRPC$TL_updateDialogPinned) {
                    TLRPC$TL_updateDialogPinned updateDialogPinned = (TLRPC$TL_updateDialogPinned) update;
                    if (updateDialogPinned.peer instanceof TLRPC$TL_peerUser) {
                        did = (long) updateDialogPinned.peer.user_id;
                    } else if (updateDialogPinned.peer instanceof TLRPC$TL_peerChat) {
                        did = (long) (-updateDialogPinned.peer.chat_id);
                    } else {
                        did = (long) (-updateDialogPinned.peer.channel_id);
                    }
                    if (!this.this$0.pinDialog(did, updateDialogPinned.pinned, null, -1)) {
                        UserConfig.pinnedDialogsLoaded = false;
                        UserConfig.saveConfig(false);
                        this.this$0.loadPinnedDialogs(did, null);
                    }
                } else if (update instanceof TLRPC$TL_updatePinnedDialogs) {
                    ArrayList<Long> order;
                    UserConfig.pinnedDialogsLoaded = false;
                    UserConfig.saveConfig(false);
                    if ((update.flags & 1) != 0) {
                        order = new ArrayList();
                        ArrayList<TLRPC$Peer> peers = ((TLRPC$TL_updatePinnedDialogs) update).order;
                        for (int b = 0; b < peers.size(); b++) {
                            peer = (TLRPC$Peer) peers.get(b);
                            if (peer.user_id != 0) {
                                did = (long) peer.user_id;
                            } else if (peer.chat_id != 0) {
                                did = (long) (-peer.chat_id);
                            } else {
                                did = (long) (-peer.channel_id);
                            }
                            order.add(Long.valueOf(did));
                        }
                    } else {
                        order = null;
                    }
                    this.this$0.loadPinnedDialogs(0, order);
                } else if (update instanceof TLRPC$TL_updateUserPhoto) {
                    if (currentUser != null) {
                        currentUser.photo = update.photo;
                    }
                    toDbUser.photo = update.photo;
                    dbUsers.add(toDbUser);
                } else if (update instanceof TLRPC$TL_updateUserPhone) {
                    if (currentUser != null) {
                        currentUser.phone = update.phone;
                        final User user = currentUser;
                        Utilities.phoneBookQueue.postRunnable(new Runnable() {
                            public void run() {
                                ContactsController.getInstance().addContactToPhoneBook(user, true);
                            }
                        });
                    }
                    toDbUser.phone = update.phone;
                    dbUsers.add(toDbUser);
                } else if (update instanceof TLRPC$TL_updateNotifySettings) {
                    TLRPC$TL_updateNotifySettings updateNotifySettings = (TLRPC$TL_updateNotifySettings) update;
                    if ((update.notify_settings instanceof TLRPC$TL_peerNotifySettings) && (updateNotifySettings.peer instanceof TLRPC$TL_notifyPeer)) {
                        if (editor == null) {
                            editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                        }
                        if (updateNotifySettings.peer.peer.user_id != 0) {
                            dialog_id = (long) updateNotifySettings.peer.peer.user_id;
                        } else if (updateNotifySettings.peer.peer.chat_id != 0) {
                            dialog_id = (long) (-updateNotifySettings.peer.peer.chat_id);
                        } else {
                            dialog_id = (long) (-updateNotifySettings.peer.peer.channel_id);
                        }
                        dialog = (TLRPC$TL_dialog) this.this$0.dialogs_dict.get(Long.valueOf(dialog_id));
                        if (dialog != null) {
                            dialog.notify_settings = update.notify_settings;
                        }
                        editor.putBoolean("silent_" + dialog_id, update.notify_settings.silent);
                        int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                        if (update.notify_settings.mute_until > currentTime) {
                            int until = 0;
                            if (update.notify_settings.mute_until > 31536000 + currentTime) {
                                editor.putInt("notify2_" + dialog_id, 2);
                                if (dialog != null) {
                                    dialog.notify_settings.mute_until = Integer.MAX_VALUE;
                                }
                            } else {
                                until = update.notify_settings.mute_until;
                                editor.putInt("notify2_" + dialog_id, 3);
                                editor.putInt("notifyuntil_" + dialog_id, update.notify_settings.mute_until);
                                if (dialog != null) {
                                    dialog.notify_settings.mute_until = until;
                                }
                            }
                            MessagesStorage.getInstance().setDialogFlags(dialog_id, (((long) until) << 32) | 1);
                            NotificationsController.getInstance().removeNotificationsForDialog(dialog_id);
                        } else {
                            if (dialog != null) {
                                dialog.notify_settings.mute_until = 0;
                            }
                            editor.remove("notify2_" + dialog_id);
                            MessagesStorage.getInstance().setDialogFlags(dialog_id, 0);
                        }
                    }
                } else if (update instanceof TLRPC$TL_updateChannel) {
                    dialog = (TLRPC$TL_dialog) this.this$0.dialogs_dict.get(Long.valueOf(-((long) update.channel_id)));
                    TLRPC$Chat chat = this.this$0.getChat(Integer.valueOf(update.channel_id));
                    if (chat != null) {
                        if (dialog == null && (chat instanceof TLRPC$TL_channel) && !chat.left) {
                            final TLRPC$Update tLRPC$Update = update;
                            Utilities.stageQueue.postRunnable(new Runnable() {
                                public void run() {
                                    MessagesController$129.this.this$0.getChannelDifference(tLRPC$Update.channel_id, 1, 0, null);
                                }
                            });
                        } else if (chat.left && dialog != null) {
                            this.this$0.deleteDialog(dialog.id, 0);
                        }
                    }
                    updateMask |= 8192;
                    this.this$0.loadFullChat(update.channel_id, 0, true);
                } else if (update instanceof TLRPC$TL_updateChatAdmins) {
                    updateMask |= 16384;
                } else if (update instanceof TLRPC$TL_updateStickerSets) {
                    StickersQuery.loadStickers(update.masks ? 1 : 0, false, true);
                } else if (update instanceof TLRPC$TL_updateStickerSetsOrder) {
                    StickersQuery.reorderStickers(update.masks ? 1 : 0, ((TLRPC$TL_updateStickerSetsOrder) update).order);
                } else if (update instanceof TLRPC$TL_updateFavedStickers) {
                    StickersQuery.loadRecents(2, false, false, true);
                } else if (update instanceof TLRPC$TL_updateContactsReset) {
                    ContactsController.getInstance().forceImportContacts();
                } else if (update instanceof TLRPC$TL_updateNewStickerSet) {
                    StickersQuery.addNewStickerSet(update.stickerset);
                } else if (update instanceof TLRPC$TL_updateSavedGifs) {
                    ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putLong("lastGifLoadTime", 0).commit();
                } else if (update instanceof TLRPC$TL_updateRecentStickers) {
                    ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putLong("lastStickersLoadTime", 0).commit();
                } else if (update instanceof TLRPC$TL_updateDraftMessage) {
                    hasDraftUpdates = true;
                    peer = ((TLRPC$TL_updateDraftMessage) update).peer;
                    if (peer.user_id != 0) {
                        did = (long) peer.user_id;
                    } else if (peer.channel_id != 0) {
                        did = (long) (-peer.channel_id);
                    } else {
                        did = (long) (-peer.chat_id);
                    }
                    DraftQuery.saveDraft(did, update.draft, null, true);
                } else if (update instanceof TLRPC$TL_updateReadFeaturedStickers) {
                    StickersQuery.markFaturedStickersAsRead(false);
                } else if (update instanceof TLRPC$TL_updatePhoneCall) {
                    TLRPC$PhoneCall call = ((TLRPC$TL_updatePhoneCall) update).phone_call;
                    VoIPService svc = VoIPService.getSharedInstance();
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.d("Received call in update: " + call);
                        FileLog.d("call id " + call.id);
                    }
                    if (call instanceof TLRPC$TL_phoneCallRequested) {
                        if (call.date + (this.this$0.callRingTimeout / 1000) >= ConnectionsManager.getInstance().getCurrentTime()) {
                            TelephonyManager tm = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
                            if (svc == null && VoIPService.callIShouldHavePutIntoIntent == null && tm.getCallState() == 0) {
                                if (BuildVars.DEBUG_VERSION) {
                                    FileLog.d("Starting service for call " + call.id);
                                }
                                VoIPService.callIShouldHavePutIntoIntent = call;
                                Intent intent = new Intent(ApplicationLoader.applicationContext, VoIPService.class);
                                intent.putExtra("is_outgoing", false);
                                intent.putExtra("user_id", call.participant_id == UserConfig.getClientUserId() ? call.admin_id : call.participant_id);
                                ApplicationLoader.applicationContext.startService(intent);
                            } else {
                                if (BuildVars.DEBUG_VERSION) {
                                    FileLog.d("Auto-declining call " + call.id + " because there's already active one");
                                }
                                TLObject req = new TLRPC$TL_phone_discardCall();
                                req.peer = new TLRPC$TL_inputPhoneCall();
                                req.peer.access_hash = call.access_hash;
                                req.peer.id = call.id;
                                req.reason = new TLRPC$TL_phoneCallDiscardReasonBusy();
                                ConnectionsManager.getInstance().sendRequest(req, new C14663());
                            }
                        } else if (BuildVars.DEBUG_VERSION) {
                            FileLog.d("ignoring too old call");
                        }
                    } else if (svc != null && call != null) {
                        svc.onCallUpdated(call);
                    } else if (VoIPService.callIShouldHavePutIntoIntent != null) {
                        FileLog.d("Updated the call while the service is starting");
                        if (call.id == VoIPService.callIShouldHavePutIntoIntent.id) {
                            VoIPService.callIShouldHavePutIntoIntent = call;
                        }
                    }
                } else if (!(update instanceof TLRPC$TL_updateGroupCall) && (update instanceof TLRPC$TL_updateGroupCallParticipant)) {
                }
            }
            if (editor != null) {
                editor.commit();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
            }
            MessagesStorage.getInstance().updateUsers(dbUsersStatus, true, true, true);
            MessagesStorage.getInstance().updateUsers(dbUsers, false, true, true);
        }
        if (!this.val$webPages.isEmpty()) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didReceivedWebpagesInUpdates, new Object[]{this.val$webPages});
            for (Entry<Long, TLRPC$WebPage> entry : this.val$webPages.entrySet()) {
                arrayList = (ArrayList) MessagesController.access$4700(this.this$0).remove(entry.getKey());
                if (arrayList != null) {
                    TLRPC$WebPage webpage = (TLRPC$WebPage) entry.getValue();
                    ArrayList messagesArr = new ArrayList();
                    dialog_id = 0;
                    if ((webpage instanceof TLRPC$TL_webPage) || (webpage instanceof TLRPC$TL_webPageEmpty)) {
                        for (a = 0; a < arrayList.size(); a++) {
                            ((MessageObject) arrayList.get(a)).messageOwner.media.webpage = webpage;
                            if (a == 0) {
                                dialog_id = ((MessageObject) arrayList.get(a)).getDialogId();
                                ImageLoader.saveMessageThumbs(((MessageObject) arrayList.get(a)).messageOwner);
                            }
                            messagesArr.add(((MessageObject) arrayList.get(a)).messageOwner);
                        }
                    } else {
                        MessagesController.access$4700(this.this$0).put(Long.valueOf(webpage.id), arrayList);
                    }
                    if (!messagesArr.isEmpty()) {
                        MessagesStorage.getInstance().putMessages(messagesArr, true, true, false, MediaController.getInstance().getAutodownloadMask());
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.replaceMessagesObjects, new Object[]{Long.valueOf(dialog_id), arrayList});
                    }
                }
            }
        }
        boolean updateDialogs = false;
        if (!this.val$messages.isEmpty()) {
            for (Entry<Long, ArrayList<MessageObject>> entry2 : this.val$messages.entrySet()) {
                this.this$0.updateInterfaceWithMessages(((Long) entry2.getKey()).longValue(), (ArrayList) entry2.getValue());
            }
            updateDialogs = true;
        } else if (hasDraftUpdates) {
            this.this$0.sortDialogs(null);
            updateDialogs = true;
        }
        if (!this.val$editingMessages.isEmpty()) {
            for (Entry<Long, ArrayList<MessageObject>> pair : this.val$editingMessages.entrySet()) {
                Long dialog_id2 = (Long) pair.getKey();
                arrayList = (ArrayList) pair.getValue();
                MessageObject oldObject = (MessageObject) this.this$0.dialogMessage.get(dialog_id2);
                if (oldObject != null) {
                    a = 0;
                    while (a < arrayList.size()) {
                        MessageObject newMessage = (MessageObject) arrayList.get(a);
                        if (oldObject.getId() == newMessage.getId()) {
                            this.this$0.dialogMessage.put(dialog_id2, newMessage);
                            if (newMessage.messageOwner.to_id != null && newMessage.messageOwner.to_id.channel_id == 0) {
                                this.this$0.dialogMessagesByIds.put(Integer.valueOf(newMessage.getId()), newMessage);
                            }
                            updateDialogs = true;
                        } else {
                            a++;
                        }
                    }
                }
                MessagesQuery.loadReplyMessagesForMessages(arrayList, dialog_id2.longValue());
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.replaceMessagesObjects, new Object[]{dialog_id2, arrayList});
            }
        }
        if (updateDialogs) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
        }
        if (this.val$printChangedArg) {
            updateMask |= 64;
        }
        if (!this.val$contactsIds.isEmpty()) {
            updateMask = (updateMask | 1) | 128;
        }
        if (!this.val$chatInfoToUpdate.isEmpty()) {
            for (a = 0; a < this.val$chatInfoToUpdate.size(); a++) {
                MessagesStorage.getInstance().updateChatParticipants((TLRPC$ChatParticipants) this.val$chatInfoToUpdate.get(a));
            }
        }
        if (this.val$channelViews.size() != 0) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedMessagesViews, new Object[]{this.val$channelViews});
        }
        if (updateMask != 0) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(updateMask)});
        }
    }
}
