package org.telegram.messenger;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.view.InputDeviceCompat;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.ir.talaeii.R;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLClassStore;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$DecryptedMessage;
import org.telegram.tgnet.TLRPC$DecryptedMessageAction;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$EncryptedFile;
import org.telegram.tgnet.TLRPC$EncryptedMessage;
import org.telegram.tgnet.TLRPC$InputEncryptedFile;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_decryptedMessage;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionAbortKey;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionAcceptKey;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionCommitKey;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionDeleteMessages;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionFlushHistory;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionNoop;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionNotifyLayer;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionReadMessages;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionRequestKey;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionResend;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionScreenshotMessages;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageLayer;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaAudio;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaContact;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaDocument_layer8;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaExternalDocument;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaGeoPoint;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaVenue;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaVideo;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageService;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_documentEncrypted;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_encryptedChatDiscarded;
import org.telegram.tgnet.TLRPC$TL_encryptedChatRequested;
import org.telegram.tgnet.TLRPC$TL_encryptedChatWaiting;
import org.telegram.tgnet.TLRPC$TL_encryptedFile;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_fileEncryptedLocation;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_geoPoint;
import org.telegram.tgnet.TLRPC$TL_inputEncryptedChat;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
import org.telegram.tgnet.TLRPC$TL_messageMediaContact;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeo;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_message_secret;
import org.telegram.tgnet.TLRPC$TL_messages_acceptEncryption;
import org.telegram.tgnet.TLRPC$TL_messages_dhConfig;
import org.telegram.tgnet.TLRPC$TL_messages_discardEncryption;
import org.telegram.tgnet.TLRPC$TL_messages_getDhConfig;
import org.telegram.tgnet.TLRPC$TL_messages_requestEncryption;
import org.telegram.tgnet.TLRPC$TL_messages_sendEncrypted;
import org.telegram.tgnet.TLRPC$TL_messages_sendEncryptedFile;
import org.telegram.tgnet.TLRPC$TL_messages_sendEncryptedMultiMedia;
import org.telegram.tgnet.TLRPC$TL_messages_sendEncryptedService;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photo;
import org.telegram.tgnet.TLRPC$TL_photoCachedSize;
import org.telegram.tgnet.TLRPC$TL_photoSize;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC$TL_updateEncryption;
import org.telegram.tgnet.TLRPC$TL_webPageUrlPending;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$messages_DhConfig;
import org.telegram.tgnet.TLRPC$messages_SentEncryptedMessage;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;

public class SecretChatHelper {
    public static final int CURRENT_SECRET_CHAT_LAYER = 73;
    private static volatile SecretChatHelper Instance = null;
    private HashMap<Integer, TLRPC$EncryptedChat> acceptingChats = new HashMap();
    public ArrayList<TLRPC$Update> delayedEncryptedChatUpdates = new ArrayList();
    private ArrayList<Long> pendingEncMessagesToDelete = new ArrayList();
    private HashMap<Integer, ArrayList<TL_decryptedMessageHolder>> secretHolesQueue = new HashMap();
    private ArrayList<Integer> sendingNotifyLayer = new ArrayList();
    private boolean startingSecretChat = false;

    /* renamed from: org.telegram.messenger.SecretChatHelper$8 */
    class C16228 implements Comparator<TL_decryptedMessageHolder> {
        C16228() {
        }

        public int compare(TL_decryptedMessageHolder lhs, TL_decryptedMessageHolder rhs) {
            if (lhs.layer.out_seq_no > rhs.layer.out_seq_no) {
                return 1;
            }
            if (lhs.layer.out_seq_no < rhs.layer.out_seq_no) {
                return -1;
            }
            return 0;
        }
    }

    public static class TL_decryptedMessageHolder extends TLObject {
        public static int constructor = 1431655929;
        public int date;
        public int decryptedWithVersion;
        public TLRPC$EncryptedFile file;
        public TLRPC$TL_decryptedMessageLayer layer;
        public boolean new_key_used;

        public void readParams(AbstractSerializedData stream, boolean exception) {
            stream.readInt64(exception);
            this.date = stream.readInt32(exception);
            this.layer = TLRPC$TL_decryptedMessageLayer.TLdeserialize(stream, stream.readInt32(exception), exception);
            if (stream.readBool(exception)) {
                this.file = TLRPC$EncryptedFile.TLdeserialize(stream, stream.readInt32(exception), exception);
            }
            this.new_key_used = stream.readBool(exception);
        }

        public void serializeToStream(AbstractSerializedData stream) {
            stream.writeInt32(constructor);
            stream.writeInt64(0);
            stream.writeInt32(this.date);
            this.layer.serializeToStream(stream);
            stream.writeBool(this.file != null);
            if (this.file != null) {
                this.file.serializeToStream(stream);
            }
            stream.writeBool(this.new_key_used);
        }
    }

    public static SecretChatHelper getInstance() {
        SecretChatHelper localInstance = Instance;
        if (localInstance == null) {
            synchronized (SecretChatHelper.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        SecretChatHelper localInstance2 = new SecretChatHelper();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    public void cleanup() {
        this.sendingNotifyLayer.clear();
        this.acceptingChats.clear();
        this.secretHolesQueue.clear();
        this.delayedEncryptedChatUpdates.clear();
        this.pendingEncMessagesToDelete.clear();
        this.startingSecretChat = false;
    }

    protected void processPendingEncMessages() {
        if (!this.pendingEncMessagesToDelete.isEmpty()) {
            final ArrayList<Long> pendingEncMessagesToDeleteCopy = new ArrayList(this.pendingEncMessagesToDelete);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    for (int a = 0; a < pendingEncMessagesToDeleteCopy.size(); a++) {
                        MessageObject messageObject = (MessageObject) MessagesController.getInstance().dialogMessagesByRandomIds.get(pendingEncMessagesToDeleteCopy.get(a));
                        if (messageObject != null) {
                            messageObject.deleted = true;
                        }
                    }
                }
            });
            MessagesStorage.getInstance().markMessagesAsDeletedByRandoms(new ArrayList(this.pendingEncMessagesToDelete));
            this.pendingEncMessagesToDelete.clear();
        }
    }

    private TLRPC$TL_messageService createServiceSecretMessage(TLRPC$EncryptedChat encryptedChat, TLRPC$DecryptedMessageAction decryptedMessage) {
        TLRPC$TL_messageService newMsg = new TLRPC$TL_messageService();
        newMsg.action = new TLRPC$TL_messageEncryptedAction();
        newMsg.action.encryptedAction = decryptedMessage;
        int newMessageId = UserConfig.getNewMessageId();
        newMsg.id = newMessageId;
        newMsg.local_id = newMessageId;
        newMsg.from_id = UserConfig.getClientUserId();
        newMsg.unread = true;
        newMsg.out = true;
        newMsg.flags = 256;
        newMsg.dialog_id = ((long) encryptedChat.id) << 32;
        newMsg.to_id = new TLRPC$TL_peerUser();
        newMsg.send_state = 1;
        if (encryptedChat.participant_id == UserConfig.getClientUserId()) {
            newMsg.to_id.user_id = encryptedChat.admin_id;
        } else {
            newMsg.to_id.user_id = encryptedChat.participant_id;
        }
        if ((decryptedMessage instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) || (decryptedMessage instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL)) {
            newMsg.date = ConnectionsManager.getInstance().getCurrentTime();
        } else {
            newMsg.date = 0;
        }
        newMsg.random_id = SendMessagesHelper.getInstance().getNextRandomId();
        UserConfig.saveConfig(false);
        ArrayList arr = new ArrayList();
        arr.add(newMsg);
        MessagesStorage.getInstance().putMessages(arr, false, true, true, 0);
        return newMsg;
    }

    public void sendMessagesReadMessage(TLRPC$EncryptedChat encryptedChat, ArrayList<Long> random_ids, TLRPC$Message resendMessage) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionReadMessages();
                reqSend.action.random_ids = random_ids;
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    protected void processUpdateEncryption(TLRPC$TL_updateEncryption update, ConcurrentHashMap<Integer, User> usersDict) {
        final TLRPC$EncryptedChat newChat = update.chat;
        long dialog_id = ((long) newChat.id) << 32;
        TLRPC$EncryptedChat existingChat = MessagesController.getInstance().getEncryptedChatDB(newChat.id, false);
        if ((newChat instanceof TLRPC$TL_encryptedChatRequested) && existingChat == null) {
            int user_id = newChat.participant_id;
            if (user_id == UserConfig.getClientUserId()) {
                user_id = newChat.admin_id;
            }
            User user = MessagesController.getInstance().getUser(Integer.valueOf(user_id));
            if (user == null) {
                user = (User) usersDict.get(Integer.valueOf(user_id));
            }
            newChat.user_id = user_id;
            final TLRPC$TL_dialog dialog = new TLRPC$TL_dialog();
            dialog.id = dialog_id;
            dialog.unread_count = 0;
            dialog.top_message = 0;
            dialog.last_message_date = update.date;
            MessagesController.getInstance().putEncryptedChat(newChat, false);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController.getInstance().dialogs_dict.put(Long.valueOf(dialog.id), dialog);
                    MessagesController.getInstance().dialogs.add(dialog);
                    MessagesController.getInstance().sortDialogs(null);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                }
            });
            MessagesStorage.getInstance().putEncryptedChat(newChat, user, dialog);
            getInstance().acceptSecretChat(newChat);
        } else if (!(newChat instanceof TLRPC$TL_encryptedChat)) {
            final TLRPC$EncryptedChat exist = existingChat;
            if (exist != null) {
                newChat.user_id = exist.user_id;
                newChat.auth_key = exist.auth_key;
                newChat.key_create_date = exist.key_create_date;
                newChat.key_use_count_in = exist.key_use_count_in;
                newChat.key_use_count_out = exist.key_use_count_out;
                newChat.ttl = exist.ttl;
                newChat.seq_in = exist.seq_in;
                newChat.seq_out = exist.seq_out;
                newChat.admin_id = exist.admin_id;
                newChat.mtproto_seq = exist.mtproto_seq;
            }
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (exist != null) {
                        MessagesController.getInstance().putEncryptedChat(newChat, false);
                    }
                    MessagesStorage.getInstance().updateEncryptedChat(newChat);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, new Object[]{newChat});
                }
            });
        } else if (existingChat != null && (existingChat instanceof TLRPC$TL_encryptedChatWaiting) && (existingChat.auth_key == null || existingChat.auth_key.length == 1)) {
            newChat.a_or_b = existingChat.a_or_b;
            newChat.user_id = existingChat.user_id;
            processAcceptedSecretChat(newChat);
        } else if (existingChat == null && this.startingSecretChat) {
            this.delayedEncryptedChatUpdates.add(update);
        }
    }

    public void sendMessagesDeleteMessage(TLRPC$EncryptedChat encryptedChat, ArrayList<Long> random_ids, TLRPC$Message resendMessage) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionDeleteMessages();
                reqSend.action.random_ids = random_ids;
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    public void sendClearHistoryMessage(TLRPC$EncryptedChat encryptedChat, TLRPC$Message resendMessage) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionFlushHistory();
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    public void sendNotifyLayerMessage(TLRPC$EncryptedChat encryptedChat, TLRPC$Message resendMessage) {
        if ((encryptedChat instanceof TLRPC$TL_encryptedChat) && !this.sendingNotifyLayer.contains(Integer.valueOf(encryptedChat.id))) {
            TLRPC$Message message;
            this.sendingNotifyLayer.add(Integer.valueOf(encryptedChat.id));
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionNotifyLayer();
                reqSend.action.layer = 73;
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    public void sendRequestKeyMessage(TLRPC$EncryptedChat encryptedChat, TLRPC$Message resendMessage) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionRequestKey();
                reqSend.action.exchange_id = encryptedChat.exchange_id;
                reqSend.action.g_a = encryptedChat.g_a;
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    public void sendAcceptKeyMessage(TLRPC$EncryptedChat encryptedChat, TLRPC$Message resendMessage) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionAcceptKey();
                reqSend.action.exchange_id = encryptedChat.exchange_id;
                reqSend.action.key_fingerprint = encryptedChat.future_key_fingerprint;
                reqSend.action.g_b = encryptedChat.g_a_or_b;
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    public void sendCommitKeyMessage(TLRPC$EncryptedChat encryptedChat, TLRPC$Message resendMessage) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionCommitKey();
                reqSend.action.exchange_id = encryptedChat.exchange_id;
                reqSend.action.key_fingerprint = encryptedChat.future_key_fingerprint;
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    public void sendAbortKeyMessage(TLRPC$EncryptedChat encryptedChat, TLRPC$Message resendMessage, long excange_id) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionAbortKey();
                reqSend.action.exchange_id = excange_id;
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    public void sendNoopMessage(TLRPC$EncryptedChat encryptedChat, TLRPC$Message resendMessage) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionNoop();
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    public void sendTTLMessage(TLRPC$EncryptedChat encryptedChat, TLRPC$Message resendMessage) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionSetMessageTTL();
                reqSend.action.ttl_seconds = encryptedChat.ttl;
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
                MessageObject newMsgObj = new MessageObject(message, null, false);
                newMsgObj.messageOwner.send_state = 1;
                ArrayList<MessageObject> objArr = new ArrayList();
                objArr.add(newMsgObj);
                MessagesController.getInstance().updateInterfaceWithMessages(message.dialog_id, objArr);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    public void sendScreenshotMessage(TLRPC$EncryptedChat encryptedChat, ArrayList<Long> random_ids, TLRPC$Message resendMessage) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            TLRPC$Message message;
            TLRPC$TL_decryptedMessageService reqSend = new TLRPC$TL_decryptedMessageService();
            if (resendMessage != null) {
                message = resendMessage;
                reqSend.action = message.action.encryptedAction;
            } else {
                reqSend.action = new TLRPC$TL_decryptedMessageActionScreenshotMessages();
                reqSend.action.random_ids = random_ids;
                message = createServiceSecretMessage(encryptedChat, reqSend.action);
                MessageObject newMsgObj = new MessageObject(message, null, false);
                newMsgObj.messageOwner.send_state = 1;
                ArrayList<MessageObject> objArr = new ArrayList();
                objArr.add(newMsgObj);
                MessagesController.getInstance().updateInterfaceWithMessages(message.dialog_id, objArr);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            }
            reqSend.random_id = message.random_id;
            performSendEncryptedRequest(reqSend, message, encryptedChat, null, null, null);
        }
    }

    private void updateMediaPaths(MessageObject newMsgObj, TLRPC$EncryptedFile file, TLRPC$DecryptedMessage decryptedMessage, String originalPath) {
        TLRPC$Message newMsg = newMsgObj.messageOwner;
        if (file == null) {
            return;
        }
        ArrayList arr;
        if ((newMsg.media instanceof TLRPC$TL_messageMediaPhoto) && newMsg.media.photo != null) {
            TLRPC$PhotoSize size = (TLRPC$PhotoSize) newMsg.media.photo.sizes.get(newMsg.media.photo.sizes.size() - 1);
            String fileName = size.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size.location.local_id;
            size.location = new TLRPC$TL_fileEncryptedLocation();
            size.location.key = decryptedMessage.media.key;
            size.location.iv = decryptedMessage.media.iv;
            size.location.dc_id = file.dc_id;
            size.location.volume_id = file.id;
            size.location.secret = file.access_hash;
            size.location.local_id = file.key_fingerprint;
            String fileName2 = size.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size.location.local_id;
            new File(FileLoader.getInstance().getDirectory(4), fileName + ".jpg").renameTo(FileLoader.getPathToAttach(size));
            ImageLoader.getInstance().replaceImageInCache(fileName, fileName2, size.location, true);
            arr = new ArrayList();
            arr.add(newMsg);
            MessagesStorage.getInstance().putMessages(arr, false, true, false, 0);
        } else if ((newMsg.media instanceof TLRPC$TL_messageMediaDocument) && newMsg.media.document != null) {
            TLRPC$Document document = newMsg.media.document;
            newMsg.media.document = new TLRPC$TL_documentEncrypted();
            newMsg.media.document.id = file.id;
            newMsg.media.document.access_hash = file.access_hash;
            newMsg.media.document.date = document.date;
            newMsg.media.document.attributes = document.attributes;
            newMsg.media.document.mime_type = document.mime_type;
            newMsg.media.document.size = file.size;
            newMsg.media.document.key = decryptedMessage.media.key;
            newMsg.media.document.iv = decryptedMessage.media.iv;
            newMsg.media.document.thumb = document.thumb;
            newMsg.media.document.dc_id = file.dc_id;
            newMsg.media.document.caption = document.caption != null ? document.caption : "";
            if (newMsg.attachPath != null && newMsg.attachPath.startsWith(FileLoader.getInstance().getDirectory(4).getAbsolutePath()) && new File(newMsg.attachPath).renameTo(FileLoader.getPathToAttach(newMsg.media.document))) {
                newMsgObj.mediaExists = newMsgObj.attachPathExists;
                newMsgObj.attachPathExists = false;
                newMsg.attachPath = "";
            }
            arr = new ArrayList();
            arr.add(newMsg);
            MessagesStorage.getInstance().putMessages(arr, false, true, false, 0);
        }
    }

    public static boolean isSecretVisibleMessage(TLRPC$Message message) {
        return (message.action instanceof TLRPC$TL_messageEncryptedAction) && ((message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL));
    }

    public static boolean isSecretInvisibleMessage(TLRPC$Message message) {
        return (!(message.action instanceof TLRPC$TL_messageEncryptedAction) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL)) ? false : true;
    }

    protected void performSendEncryptedRequest(TLRPC$TL_messages_sendEncryptedMultiMedia req, SendMessagesHelper$DelayedMessage message) {
        for (int a = 0; a < req.files.size(); a++) {
            performSendEncryptedRequest((TLRPC$DecryptedMessage) req.messages.get(a), (TLRPC$Message) message.messages.get(a), message.encryptedChat, (TLRPC$InputEncryptedFile) req.files.get(a), (String) message.originalPaths.get(a), (MessageObject) message.messageObjects.get(a));
        }
    }

    protected void performSendEncryptedRequest(TLRPC$DecryptedMessage req, TLRPC$Message newMsgObj, TLRPC$EncryptedChat chat, TLRPC$InputEncryptedFile encryptedFile, String originalPath, MessageObject newMsg) {
        if (req != null && chat.auth_key != null && !(chat instanceof TLRPC$TL_encryptedChatRequested) && !(chat instanceof TLRPC$TL_encryptedChatWaiting)) {
            SendMessagesHelper.getInstance().putToSendingMessages(newMsgObj);
            final TLRPC$EncryptedChat tLRPC$EncryptedChat = chat;
            final TLRPC$DecryptedMessage tLRPC$DecryptedMessage = req;
            final TLRPC$Message tLRPC$Message = newMsgObj;
            final TLRPC$InputEncryptedFile tLRPC$InputEncryptedFile = encryptedFile;
            final MessageObject messageObject = newMsg;
            final String str = originalPath;
            Utilities.stageQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.SecretChatHelper$4$1 */
                class C16131 implements RequestDelegate {

                    /* renamed from: org.telegram.messenger.SecretChatHelper$4$1$2 */
                    class C16122 implements Runnable {
                        C16122() {
                        }

                        public void run() {
                            tLRPC$Message.send_state = 2;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, new Object[]{Integer.valueOf(tLRPC$Message.id)});
                            SendMessagesHelper.getInstance().processSentMessage(tLRPC$Message.id);
                            if (MessageObject.isVideoMessage(tLRPC$Message) || MessageObject.isNewGifMessage(tLRPC$Message) || MessageObject.isRoundVideoMessage(tLRPC$Message)) {
                                SendMessagesHelper.getInstance().stopVideoService(tLRPC$Message.attachPath);
                            }
                            SendMessagesHelper.getInstance().removeFromSendingMessages(tLRPC$Message.id);
                        }
                    }

                    C16131() {
                    }

                    public void run(TLObject response, TLRPC$TL_error error) {
                        if (error == null && (tLRPC$DecryptedMessage.action instanceof TLRPC$TL_decryptedMessageActionNotifyLayer)) {
                            TLRPC$EncryptedChat currentChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(tLRPC$EncryptedChat.id));
                            if (currentChat == null) {
                                currentChat = tLRPC$EncryptedChat;
                            }
                            if (currentChat.key_hash == null) {
                                currentChat.key_hash = AndroidUtilities.calcAuthKeyHash(currentChat.auth_key);
                            }
                            if (AndroidUtilities.getPeerLayerVersion(currentChat.layer) >= 46 && currentChat.key_hash.length == 16) {
                                try {
                                    byte[] sha256 = Utilities.computeSHA256(tLRPC$EncryptedChat.auth_key, 0, tLRPC$EncryptedChat.auth_key.length);
                                    byte[] key_hash = new byte[36];
                                    System.arraycopy(tLRPC$EncryptedChat.key_hash, 0, key_hash, 0, 16);
                                    System.arraycopy(sha256, 0, key_hash, 16, 20);
                                    currentChat.key_hash = key_hash;
                                    MessagesStorage.getInstance().updateEncryptedChat(currentChat);
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                            }
                            SecretChatHelper.this.sendingNotifyLayer.remove(Integer.valueOf(currentChat.id));
                            currentChat.layer = AndroidUtilities.setMyLayerVersion(currentChat.layer, 73);
                            MessagesStorage.getInstance().updateEncryptedChatLayer(currentChat);
                        }
                        if (tLRPC$Message == null) {
                            return;
                        }
                        if (error == null) {
                            final String attachPath = tLRPC$Message.attachPath;
                            final TLRPC$messages_SentEncryptedMessage res = (TLRPC$messages_SentEncryptedMessage) response;
                            if (SecretChatHelper.isSecretVisibleMessage(tLRPC$Message)) {
                                tLRPC$Message.date = res.date;
                            }
                            if (messageObject != null && (res.file instanceof TLRPC$TL_encryptedFile)) {
                                SecretChatHelper.this.updateMediaPaths(messageObject, res.file, tLRPC$DecryptedMessage, str);
                            }
                            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                /* renamed from: org.telegram.messenger.SecretChatHelper$4$1$1$1 */
                                class C16101 implements Runnable {
                                    C16101() {
                                    }

                                    public void run() {
                                        tLRPC$Message.send_state = 0;
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, new Object[]{Integer.valueOf(tLRPC$Message.id), Integer.valueOf(tLRPC$Message.id), tLRPC$Message, Long.valueOf(tLRPC$Message.dialog_id)});
                                        SendMessagesHelper.getInstance().processSentMessage(tLRPC$Message.id);
                                        if (MessageObject.isVideoMessage(tLRPC$Message) || MessageObject.isNewGifMessage(tLRPC$Message) || MessageObject.isRoundVideoMessage(tLRPC$Message)) {
                                            SendMessagesHelper.getInstance().stopVideoService(attachPath);
                                        }
                                        SendMessagesHelper.getInstance().removeFromSendingMessages(tLRPC$Message.id);
                                    }
                                }

                                public void run() {
                                    if (SecretChatHelper.isSecretInvisibleMessage(tLRPC$Message)) {
                                        res.date = 0;
                                    }
                                    MessagesStorage.getInstance().updateMessageStateAndId(tLRPC$Message.random_id, Integer.valueOf(tLRPC$Message.id), tLRPC$Message.id, res.date, false, 0);
                                    AndroidUtilities.runOnUIThread(new C16101());
                                }
                            });
                            return;
                        }
                        MessagesStorage.getInstance().markMessageAsSendError(tLRPC$Message);
                        AndroidUtilities.runOnUIThread(new C16122());
                    }
                }

                public void run() {
                    try {
                        TLObject reqToSend;
                        TLObject layer = new TLRPC$TL_decryptedMessageLayer();
                        layer.layer = Math.min(Math.max(46, AndroidUtilities.getMyLayerVersion(tLRPC$EncryptedChat.layer)), Math.max(46, AndroidUtilities.getPeerLayerVersion(tLRPC$EncryptedChat.layer)));
                        layer.message = tLRPC$DecryptedMessage;
                        layer.random_bytes = new byte[15];
                        Utilities.random.nextBytes(layer.random_bytes);
                        TLObject toEncryptObject = layer;
                        int mtprotoVersion = AndroidUtilities.getPeerLayerVersion(tLRPC$EncryptedChat.layer) >= 73 ? 2 : 1;
                        if (tLRPC$EncryptedChat.seq_in == 0 && tLRPC$EncryptedChat.seq_out == 0) {
                            if (tLRPC$EncryptedChat.admin_id == UserConfig.getClientUserId()) {
                                tLRPC$EncryptedChat.seq_out = 1;
                                tLRPC$EncryptedChat.seq_in = -2;
                            } else {
                                tLRPC$EncryptedChat.seq_in = -1;
                            }
                        }
                        if (tLRPC$Message.seq_in == 0 && tLRPC$Message.seq_out == 0) {
                            int i;
                            if (tLRPC$EncryptedChat.seq_in > 0) {
                                i = tLRPC$EncryptedChat.seq_in;
                            } else {
                                i = tLRPC$EncryptedChat.seq_in + 2;
                            }
                            layer.in_seq_no = i;
                            layer.out_seq_no = tLRPC$EncryptedChat.seq_out;
                            TLRPC$EncryptedChat tLRPC$EncryptedChat = tLRPC$EncryptedChat;
                            tLRPC$EncryptedChat.seq_out += 2;
                            if (AndroidUtilities.getPeerLayerVersion(tLRPC$EncryptedChat.layer) >= 20) {
                                if (tLRPC$EncryptedChat.key_create_date == 0) {
                                    tLRPC$EncryptedChat.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                                }
                                tLRPC$EncryptedChat = tLRPC$EncryptedChat;
                                tLRPC$EncryptedChat.key_use_count_out = (short) (tLRPC$EncryptedChat.key_use_count_out + 1);
                                if ((tLRPC$EncryptedChat.key_use_count_out >= (short) 100 || tLRPC$EncryptedChat.key_create_date < ConnectionsManager.getInstance().getCurrentTime() - 604800) && tLRPC$EncryptedChat.exchange_id == 0 && tLRPC$EncryptedChat.future_key_fingerprint == 0) {
                                    SecretChatHelper.this.requestNewSecretChatKey(tLRPC$EncryptedChat);
                                }
                            }
                            MessagesStorage.getInstance().updateEncryptedChatSeq(tLRPC$EncryptedChat, false);
                            if (tLRPC$Message != null) {
                                tLRPC$Message.seq_in = layer.in_seq_no;
                                tLRPC$Message.seq_out = layer.out_seq_no;
                                MessagesStorage.getInstance().setMessageSeq(tLRPC$Message.id, tLRPC$Message.seq_in, tLRPC$Message.seq_out);
                            }
                        } else {
                            layer.in_seq_no = tLRPC$Message.seq_in;
                            layer.out_seq_no = tLRPC$Message.seq_out;
                        }
                        FileLog.e(tLRPC$DecryptedMessage + " send message with in_seq = " + layer.in_seq_no + " out_seq = " + layer.out_seq_no);
                        int len = toEncryptObject.getObjectSize();
                        AbstractSerializedData nativeByteBuffer = new NativeByteBuffer(len + 4);
                        nativeByteBuffer.writeInt32(len);
                        toEncryptObject.serializeToStream(nativeByteBuffer);
                        len = nativeByteBuffer.length();
                        int extraLen = len % 16 != 0 ? 16 - (len % 16) : 0;
                        if (mtprotoVersion == 2) {
                            extraLen += (Utilities.random.nextInt(3) + 2) * 16;
                        }
                        NativeByteBuffer dataForEncryption = new NativeByteBuffer(len + extraLen);
                        nativeByteBuffer.position(0);
                        dataForEncryption.writeBytes((NativeByteBuffer) nativeByteBuffer);
                        if (extraLen != 0) {
                            byte[] b = new byte[extraLen];
                            Utilities.random.nextBytes(b);
                            dataForEncryption.writeBytes(b);
                        }
                        Object messageKey = new byte[16];
                        boolean incoming = mtprotoVersion == 2 && tLRPC$EncryptedChat.admin_id != UserConfig.getClientUserId();
                        if (mtprotoVersion == 2) {
                            System.arraycopy(Utilities.computeSHA256(tLRPC$EncryptedChat.auth_key, (incoming ? 8 : 0) + 88, 32, dataForEncryption.buffer, 0, dataForEncryption.buffer.limit()), 8, messageKey, 0, 16);
                        } else {
                            Object messageKeyFull = Utilities.computeSHA1(nativeByteBuffer.buffer);
                            System.arraycopy(messageKeyFull, messageKeyFull.length - 16, messageKey, 0, 16);
                        }
                        nativeByteBuffer.reuse();
                        MessageKeyData keyData = MessageKeyData.generateMessageKeyData(tLRPC$EncryptedChat.auth_key, messageKey, incoming, mtprotoVersion);
                        Utilities.aesIgeEncryption(dataForEncryption.buffer, keyData.aesKey, keyData.aesIv, true, false, 0, dataForEncryption.limit());
                        NativeByteBuffer data = new NativeByteBuffer((messageKey.length + 8) + dataForEncryption.length());
                        dataForEncryption.position(0);
                        data.writeInt64(tLRPC$EncryptedChat.key_fingerprint);
                        data.writeBytes((byte[]) messageKey);
                        data.writeBytes(dataForEncryption);
                        dataForEncryption.reuse();
                        data.position(0);
                        TLObject req2;
                        if (tLRPC$InputEncryptedFile != null) {
                            req2 = new TLRPC$TL_messages_sendEncryptedFile();
                            req2.data = data;
                            req2.random_id = tLRPC$DecryptedMessage.random_id;
                            req2.peer = new TLRPC$TL_inputEncryptedChat();
                            req2.peer.chat_id = tLRPC$EncryptedChat.id;
                            req2.peer.access_hash = tLRPC$EncryptedChat.access_hash;
                            req2.file = tLRPC$InputEncryptedFile;
                            reqToSend = req2;
                        } else if (tLRPC$DecryptedMessage instanceof TLRPC$TL_decryptedMessageService) {
                            req2 = new TLRPC$TL_messages_sendEncryptedService();
                            req2.data = data;
                            req2.random_id = tLRPC$DecryptedMessage.random_id;
                            req2.peer = new TLRPC$TL_inputEncryptedChat();
                            req2.peer.chat_id = tLRPC$EncryptedChat.id;
                            req2.peer.access_hash = tLRPC$EncryptedChat.access_hash;
                            reqToSend = req2;
                        } else {
                            req2 = new TLRPC$TL_messages_sendEncrypted();
                            req2.data = data;
                            req2.random_id = tLRPC$DecryptedMessage.random_id;
                            req2.peer = new TLRPC$TL_inputEncryptedChat();
                            req2.peer.chat_id = tLRPC$EncryptedChat.id;
                            req2.peer.access_hash = tLRPC$EncryptedChat.access_hash;
                            reqToSend = req2;
                        }
                        ConnectionsManager.getInstance().sendRequest(reqToSend, new C16131(), 64);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    private void applyPeerLayer(final TLRPC$EncryptedChat chat, int newPeerLayer) {
        int currentPeerLayer = AndroidUtilities.getPeerLayerVersion(chat.layer);
        if (newPeerLayer > currentPeerLayer) {
            if (chat.key_hash.length == 16 && currentPeerLayer >= 46) {
                try {
                    byte[] sha256 = Utilities.computeSHA256(chat.auth_key, 0, chat.auth_key.length);
                    byte[] key_hash = new byte[36];
                    System.arraycopy(chat.key_hash, 0, key_hash, 0, 16);
                    System.arraycopy(sha256, 0, key_hash, 16, 20);
                    chat.key_hash = key_hash;
                    MessagesStorage.getInstance().updateEncryptedChat(chat);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
            chat.layer = AndroidUtilities.setPeerLayerVersion(chat.layer, newPeerLayer);
            MessagesStorage.getInstance().updateEncryptedChatLayer(chat);
            if (currentPeerLayer < 73) {
                sendNotifyLayerMessage(chat, null);
            }
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, new Object[]{chat});
                }
            });
        }
    }

    public TLRPC$Message processDecryptedObject(TLRPC$EncryptedChat chat, TLRPC$EncryptedFile file, int date, TLObject object, boolean new_key_used) {
        if (object != null) {
            int from_id = chat.admin_id;
            if (from_id == UserConfig.getClientUserId()) {
                from_id = chat.participant_id;
            }
            if (AndroidUtilities.getPeerLayerVersion(chat.layer) >= 20 && chat.exchange_id == 0 && chat.future_key_fingerprint == 0 && chat.key_use_count_in >= (short) 120) {
                requestNewSecretChatKey(chat);
            }
            if (chat.exchange_id == 0 && chat.future_key_fingerprint != 0 && !new_key_used) {
                chat.future_auth_key = new byte[256];
                chat.future_key_fingerprint = 0;
                MessagesStorage.getInstance().updateEncryptedChat(chat);
            } else if (chat.exchange_id != 0 && new_key_used) {
                chat.key_fingerprint = chat.future_key_fingerprint;
                chat.auth_key = chat.future_auth_key;
                chat.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                chat.future_auth_key = new byte[256];
                chat.future_key_fingerprint = 0;
                chat.key_use_count_in = (short) 0;
                chat.key_use_count_out = (short) 0;
                chat.exchange_id = 0;
                MessagesStorage.getInstance().updateEncryptedChat(chat);
            }
            int newMessageId;
            if (object instanceof TLRPC$TL_decryptedMessage) {
                TLRPC$TL_message newMessage;
                TLRPC$TL_decryptedMessage decryptedMessage = (TLRPC$TL_decryptedMessage) object;
                if (AndroidUtilities.getPeerLayerVersion(chat.layer) >= 17) {
                    newMessage = new TLRPC$TL_message_secret();
                    newMessage.ttl = decryptedMessage.ttl;
                    newMessage.entities = decryptedMessage.entities;
                } else {
                    newMessage = new TLRPC$TL_message();
                    newMessage.ttl = chat.ttl;
                }
                newMessage.message = decryptedMessage.message;
                newMessage.date = date;
                newMessageId = UserConfig.getNewMessageId();
                newMessage.id = newMessageId;
                newMessage.local_id = newMessageId;
                UserConfig.saveConfig(false);
                newMessage.from_id = from_id;
                newMessage.to_id = new TLRPC$TL_peerUser();
                newMessage.random_id = decryptedMessage.random_id;
                newMessage.to_id.user_id = UserConfig.getClientUserId();
                newMessage.unread = true;
                newMessage.flags = 768;
                if (decryptedMessage.via_bot_name != null && decryptedMessage.via_bot_name.length() > 0) {
                    newMessage.via_bot_name = decryptedMessage.via_bot_name;
                    newMessage.flags |= 2048;
                }
                if (decryptedMessage.grouped_id != 0) {
                    newMessage.grouped_id = decryptedMessage.grouped_id;
                    newMessage.flags |= 131072;
                }
                newMessage.dialog_id = ((long) chat.id) << 32;
                if (decryptedMessage.reply_to_random_id != 0) {
                    newMessage.reply_to_random_id = decryptedMessage.reply_to_random_id;
                    newMessage.flags |= 8;
                }
                if (decryptedMessage.media == null || (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaEmpty)) {
                    newMessage.media = new TLRPC$TL_messageMediaEmpty();
                    return newMessage;
                } else if (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaWebPage) {
                    newMessage.media = new TLRPC$TL_messageMediaWebPage();
                    newMessage.media.webpage = new TLRPC$TL_webPageUrlPending();
                    newMessage.media.webpage.url = decryptedMessage.media.url;
                    return newMessage;
                } else if (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaContact) {
                    newMessage.media = new TLRPC$TL_messageMediaContact();
                    newMessage.media.last_name = decryptedMessage.media.last_name;
                    newMessage.media.first_name = decryptedMessage.media.first_name;
                    newMessage.media.phone_number = decryptedMessage.media.phone_number;
                    newMessage.media.user_id = decryptedMessage.media.user_id;
                    return newMessage;
                } else if (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaGeoPoint) {
                    newMessage.media = new TLRPC$TL_messageMediaGeo();
                    newMessage.media.geo = new TLRPC$TL_geoPoint();
                    newMessage.media.geo.lat = decryptedMessage.media.lat;
                    newMessage.media.geo._long = decryptedMessage.media._long;
                    return newMessage;
                } else if (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaPhoto) {
                    if (decryptedMessage.media.key == null || decryptedMessage.media.key.length != 32 || decryptedMessage.media.iv == null || decryptedMessage.media.iv.length != 32) {
                        return null;
                    }
                    newMessage.media = new TLRPC$TL_messageMediaPhoto();
                    r4 = newMessage.media;
                    r4.flags |= 3;
                    newMessage.media.caption = decryptedMessage.media.caption != null ? decryptedMessage.media.caption : "";
                    newMessage.media.photo = new TLRPC$TL_photo();
                    newMessage.media.photo.date = newMessage.date;
                    thumb = ((TLRPC$TL_decryptedMessageMediaPhoto) decryptedMessage.media).thumb;
                    if (thumb != null && thumb.length != 0 && thumb.length <= 6000 && decryptedMessage.media.thumb_w <= 100 && decryptedMessage.media.thumb_h <= 100) {
                        TLRPC$TL_photoCachedSize small = new TLRPC$TL_photoCachedSize();
                        small.w = decryptedMessage.media.thumb_w;
                        small.h = decryptedMessage.media.thumb_h;
                        small.bytes = thumb;
                        small.type = "s";
                        small.location = new TLRPC$TL_fileLocationUnavailable();
                        newMessage.media.photo.sizes.add(small);
                    }
                    if (newMessage.ttl != 0) {
                        newMessage.media.ttl_seconds = newMessage.ttl;
                        newMessage.flags |= 4;
                    }
                    TLRPC$TL_photoSize big = new TLRPC$TL_photoSize();
                    big.w = decryptedMessage.media.f71w;
                    big.h = decryptedMessage.media.f70h;
                    big.type = "x";
                    big.size = file.size;
                    big.location = new TLRPC$TL_fileEncryptedLocation();
                    big.location.key = decryptedMessage.media.key;
                    big.location.iv = decryptedMessage.media.iv;
                    big.location.dc_id = file.dc_id;
                    big.location.volume_id = file.id;
                    big.location.secret = file.access_hash;
                    big.location.local_id = file.key_fingerprint;
                    newMessage.media.photo.sizes.add(big);
                    return newMessage;
                } else if (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaVideo) {
                    if (decryptedMessage.media.key == null || decryptedMessage.media.key.length != 32 || decryptedMessage.media.iv == null || decryptedMessage.media.iv.length != 32) {
                        return null;
                    }
                    newMessage.media = new TLRPC$TL_messageMediaDocument();
                    r4 = newMessage.media;
                    r4.flags |= 3;
                    newMessage.media.document = new TLRPC$TL_documentEncrypted();
                    newMessage.media.document.key = decryptedMessage.media.key;
                    newMessage.media.document.iv = decryptedMessage.media.iv;
                    newMessage.media.document.dc_id = file.dc_id;
                    newMessage.media.caption = decryptedMessage.media.caption != null ? decryptedMessage.media.caption : "";
                    newMessage.media.document.date = date;
                    newMessage.media.document.size = file.size;
                    newMessage.media.document.id = file.id;
                    newMessage.media.document.access_hash = file.access_hash;
                    newMessage.media.document.mime_type = decryptedMessage.media.mime_type;
                    if (newMessage.media.document.mime_type == null) {
                        newMessage.media.document.mime_type = MimeTypes.VIDEO_MP4;
                    }
                    thumb = ((TLRPC$TL_decryptedMessageMediaVideo) decryptedMessage.media).thumb;
                    if (thumb == null || thumb.length == 0 || thumb.length > 6000 || decryptedMessage.media.thumb_w > 100 || decryptedMessage.media.thumb_h > 100) {
                        newMessage.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
                        newMessage.media.document.thumb.type = "s";
                    } else {
                        newMessage.media.document.thumb = new TLRPC$TL_photoCachedSize();
                        newMessage.media.document.thumb.bytes = thumb;
                        newMessage.media.document.thumb.f78w = decryptedMessage.media.thumb_w;
                        newMessage.media.document.thumb.f77h = decryptedMessage.media.thumb_h;
                        newMessage.media.document.thumb.type = "s";
                        newMessage.media.document.thumb.location = new TLRPC$TL_fileLocationUnavailable();
                    }
                    TLRPC$TL_documentAttributeVideo attributeVideo = new TLRPC$TL_documentAttributeVideo();
                    attributeVideo.w = decryptedMessage.media.f71w;
                    attributeVideo.h = decryptedMessage.media.f70h;
                    attributeVideo.duration = decryptedMessage.media.duration;
                    newMessage.media.document.attributes.add(attributeVideo);
                    if (newMessage.ttl == 0) {
                        return newMessage;
                    }
                    newMessage.ttl = Math.max(decryptedMessage.media.duration + 2, newMessage.ttl);
                    newMessage.media.ttl_seconds = newMessage.ttl;
                    newMessage.flags |= 4;
                    return newMessage;
                } else if (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaDocument) {
                    if (decryptedMessage.media.key == null || decryptedMessage.media.key.length != 32 || decryptedMessage.media.iv == null || decryptedMessage.media.iv.length != 32) {
                        return null;
                    }
                    newMessage.media = new TLRPC$TL_messageMediaDocument();
                    r4 = newMessage.media;
                    r4.flags |= 3;
                    newMessage.media.caption = decryptedMessage.media.caption != null ? decryptedMessage.media.caption : "";
                    newMessage.media.document = new TLRPC$TL_documentEncrypted();
                    newMessage.media.document.id = file.id;
                    newMessage.media.document.access_hash = file.access_hash;
                    newMessage.media.document.date = date;
                    if (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaDocument_layer8) {
                        TLRPC$TL_documentAttributeFilename fileName = new TLRPC$TL_documentAttributeFilename();
                        fileName.file_name = decryptedMessage.media.file_name;
                        newMessage.media.document.attributes.add(fileName);
                    } else {
                        newMessage.media.document.attributes = decryptedMessage.media.attributes;
                    }
                    newMessage.media.document.mime_type = decryptedMessage.media.mime_type;
                    TLRPC$Document tLRPC$Document = newMessage.media.document;
                    if (decryptedMessage.media.size != 0) {
                        newMessageId = Math.min(decryptedMessage.media.size, file.size);
                    } else {
                        newMessageId = file.size;
                    }
                    tLRPC$Document.size = newMessageId;
                    newMessage.media.document.key = decryptedMessage.media.key;
                    newMessage.media.document.iv = decryptedMessage.media.iv;
                    if (newMessage.media.document.mime_type == null) {
                        newMessage.media.document.mime_type = "";
                    }
                    thumb = ((TLRPC$TL_decryptedMessageMediaDocument) decryptedMessage.media).thumb;
                    if (thumb == null || thumb.length == 0 || thumb.length > 6000 || decryptedMessage.media.thumb_w > 100 || decryptedMessage.media.thumb_h > 100) {
                        newMessage.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
                        newMessage.media.document.thumb.type = "s";
                    } else {
                        newMessage.media.document.thumb = new TLRPC$TL_photoCachedSize();
                        newMessage.media.document.thumb.bytes = thumb;
                        newMessage.media.document.thumb.f78w = decryptedMessage.media.thumb_w;
                        newMessage.media.document.thumb.f77h = decryptedMessage.media.thumb_h;
                        newMessage.media.document.thumb.type = "s";
                        newMessage.media.document.thumb.location = new TLRPC$TL_fileLocationUnavailable();
                    }
                    newMessage.media.document.dc_id = file.dc_id;
                    if (!MessageObject.isVoiceMessage(newMessage) && !MessageObject.isRoundVideoMessage(newMessage)) {
                        return newMessage;
                    }
                    newMessage.media_unread = true;
                    return newMessage;
                } else if (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaExternalDocument) {
                    newMessage.media = new TLRPC$TL_messageMediaDocument();
                    r4 = newMessage.media;
                    r4.flags |= 3;
                    newMessage.media.caption = "";
                    newMessage.media.document = new TLRPC$TL_document();
                    newMessage.media.document.id = decryptedMessage.media.id;
                    newMessage.media.document.access_hash = decryptedMessage.media.access_hash;
                    newMessage.media.document.date = decryptedMessage.media.date;
                    newMessage.media.document.attributes = decryptedMessage.media.attributes;
                    newMessage.media.document.mime_type = decryptedMessage.media.mime_type;
                    newMessage.media.document.dc_id = decryptedMessage.media.dc_id;
                    newMessage.media.document.size = decryptedMessage.media.size;
                    newMessage.media.document.thumb = ((TLRPC$TL_decryptedMessageMediaExternalDocument) decryptedMessage.media).thumb;
                    if (newMessage.media.document.mime_type != null) {
                        return newMessage;
                    }
                    newMessage.media.document.mime_type = "";
                    return newMessage;
                } else if (decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaAudio) {
                    if (decryptedMessage.media.key == null || decryptedMessage.media.key.length != 32 || decryptedMessage.media.iv == null || decryptedMessage.media.iv.length != 32) {
                        return null;
                    }
                    newMessage.media = new TLRPC$TL_messageMediaDocument();
                    r4 = newMessage.media;
                    r4.flags |= 3;
                    newMessage.media.document = new TLRPC$TL_documentEncrypted();
                    newMessage.media.document.key = decryptedMessage.media.key;
                    newMessage.media.document.iv = decryptedMessage.media.iv;
                    newMessage.media.document.id = file.id;
                    newMessage.media.document.access_hash = file.access_hash;
                    newMessage.media.document.date = date;
                    newMessage.media.document.size = file.size;
                    newMessage.media.document.dc_id = file.dc_id;
                    newMessage.media.document.mime_type = decryptedMessage.media.mime_type;
                    newMessage.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
                    newMessage.media.document.thumb.type = "s";
                    newMessage.media.caption = decryptedMessage.media.caption != null ? decryptedMessage.media.caption : "";
                    if (newMessage.media.document.mime_type == null) {
                        newMessage.media.document.mime_type = "audio/ogg";
                    }
                    TLRPC$TL_documentAttributeAudio attributeAudio = new TLRPC$TL_documentAttributeAudio();
                    attributeAudio.duration = decryptedMessage.media.duration;
                    attributeAudio.voice = true;
                    newMessage.media.document.attributes.add(attributeAudio);
                    if (newMessage.ttl == 0) {
                        return newMessage;
                    }
                    newMessage.ttl = Math.max(decryptedMessage.media.duration + 1, newMessage.ttl);
                    return newMessage;
                } else if (!(decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaVenue)) {
                    return null;
                } else {
                    newMessage.media = new TLRPC$TL_messageMediaVenue();
                    newMessage.media.geo = new TLRPC$TL_geoPoint();
                    newMessage.media.geo.lat = decryptedMessage.media.lat;
                    newMessage.media.geo._long = decryptedMessage.media._long;
                    newMessage.media.title = decryptedMessage.media.title;
                    newMessage.media.address = decryptedMessage.media.address;
                    newMessage.media.provider = decryptedMessage.media.provider;
                    newMessage.media.venue_id = decryptedMessage.media.venue_id;
                    newMessage.media.venue_type = "";
                    return newMessage;
                }
            } else if (object instanceof TLRPC$TL_decryptedMessageService) {
                TLRPC$TL_decryptedMessageService serviceMessage = (TLRPC$TL_decryptedMessageService) object;
                if ((serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL) || (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages)) {
                    TLRPC$Message newMessage2 = new TLRPC$TL_messageService();
                    if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL) {
                        newMessage2.action = new TLRPC$TL_messageEncryptedAction();
                        if (serviceMessage.action.ttl_seconds < 0 || serviceMessage.action.ttl_seconds > 31536000) {
                            serviceMessage.action.ttl_seconds = 31536000;
                        }
                        chat.ttl = serviceMessage.action.ttl_seconds;
                        newMessage2.action.encryptedAction = serviceMessage.action;
                        MessagesStorage.getInstance().updateEncryptedChatTTL(chat);
                    } else if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) {
                        newMessage2.action = new TLRPC$TL_messageEncryptedAction();
                        newMessage2.action.encryptedAction = serviceMessage.action;
                    }
                    newMessageId = UserConfig.getNewMessageId();
                    newMessage2.id = newMessageId;
                    newMessage2.local_id = newMessageId;
                    UserConfig.saveConfig(false);
                    newMessage2.unread = true;
                    newMessage2.flags = 256;
                    newMessage2.date = date;
                    newMessage2.from_id = from_id;
                    newMessage2.to_id = new TLRPC$TL_peerUser();
                    newMessage2.to_id.user_id = UserConfig.getClientUserId();
                    newMessage2.dialog_id = ((long) chat.id) << 32;
                    return newMessage2;
                } else if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionFlushHistory) {
                    final long j = ((long) chat.id) << 32;
                    AndroidUtilities.runOnUIThread(new Runnable() {

                        /* renamed from: org.telegram.messenger.SecretChatHelper$6$1 */
                        class C16171 implements Runnable {

                            /* renamed from: org.telegram.messenger.SecretChatHelper$6$1$1 */
                            class C16161 implements Runnable {
                                C16161() {
                                }

                                public void run() {
                                    NotificationsController.getInstance().processReadMessages(null, j, 0, Integer.MAX_VALUE, false);
                                    HashMap<Long, Integer> dialogsToUpdate = new HashMap();
                                    dialogsToUpdate.put(Long.valueOf(j), Integer.valueOf(0));
                                    NotificationsController.getInstance().processDialogsUpdateRead(dialogsToUpdate);
                                }
                            }

                            C16171() {
                            }

                            public void run() {
                                AndroidUtilities.runOnUIThread(new C16161());
                            }
                        }

                        public void run() {
                            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(j));
                            if (dialog != null) {
                                dialog.unread_count = 0;
                                MessagesController.getInstance().dialogMessage.remove(Long.valueOf(dialog.id));
                            }
                            MessagesStorage.getInstance().getStorageQueue().postRunnable(new C16171());
                            MessagesStorage.getInstance().deleteDialog(j, 1);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.removeAllMessagesFromDialog, new Object[]{Long.valueOf(j), Boolean.valueOf(false)});
                        }
                    });
                    return null;
                } else if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionDeleteMessages) {
                    if (!serviceMessage.action.random_ids.isEmpty()) {
                        this.pendingEncMessagesToDelete.addAll(serviceMessage.action.random_ids);
                    }
                    return null;
                } else if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionReadMessages) {
                    if (!serviceMessage.action.random_ids.isEmpty()) {
                        int time = ConnectionsManager.getInstance().getCurrentTime();
                        MessagesStorage.getInstance().createTaskForSecretChat(chat.id, time, time, 1, serviceMessage.action.random_ids);
                    }
                } else if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionNotifyLayer) {
                    applyPeerLayer(chat, serviceMessage.action.layer);
                } else if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionRequestKey) {
                    if (chat.exchange_id != 0) {
                        if (chat.exchange_id > serviceMessage.action.exchange_id) {
                            FileLog.e("we already have request key with higher exchange_id");
                            return null;
                        }
                        sendAbortKeyMessage(chat, null, chat.exchange_id);
                    }
                    byte[] salt = new byte[256];
                    Utilities.random.nextBytes(salt);
                    r0 = new BigInteger(1, MessagesStorage.secretPBytes);
                    BigInteger g_b = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, salt), r0);
                    r0 = new BigInteger(1, serviceMessage.action.g_a);
                    if (Utilities.isGoodGaAndGb(r0, r0)) {
                        byte[] g_b_bytes = g_b.toByteArray();
                        if (g_b_bytes.length > 256) {
                            correctedAuth = new byte[256];
                            System.arraycopy(g_b_bytes, 1, correctedAuth, 0, 256);
                            g_b_bytes = correctedAuth;
                        }
                        authKey = r0.modPow(new BigInteger(1, salt), r0).toByteArray();
                        if (authKey.length > 256) {
                            correctedAuth = new byte[256];
                            System.arraycopy(authKey, authKey.length + InputDeviceCompat.SOURCE_ANY, correctedAuth, 0, 256);
                            authKey = correctedAuth;
                        } else if (authKey.length < 256) {
                            correctedAuth = new byte[256];
                            System.arraycopy(authKey, 0, correctedAuth, 256 - authKey.length, authKey.length);
                            for (a = 0; a < 256 - authKey.length; a++) {
                                authKey[a] = (byte) 0;
                            }
                            authKey = correctedAuth;
                        }
                        authKeyHash = Utilities.computeSHA1(authKey);
                        authKeyId = new byte[8];
                        System.arraycopy(authKeyHash, authKeyHash.length - 8, authKeyId, 0, 8);
                        chat.exchange_id = serviceMessage.action.exchange_id;
                        chat.future_auth_key = authKey;
                        chat.future_key_fingerprint = Utilities.bytesToLong(authKeyId);
                        chat.g_a_or_b = g_b_bytes;
                        MessagesStorage.getInstance().updateEncryptedChat(chat);
                        sendAcceptKeyMessage(chat, null);
                    } else {
                        sendAbortKeyMessage(chat, null, serviceMessage.action.exchange_id);
                        return null;
                    }
                } else if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionAcceptKey) {
                    if (chat.exchange_id == serviceMessage.action.exchange_id) {
                        r0 = new BigInteger(1, MessagesStorage.secretPBytes);
                        r0 = new BigInteger(1, serviceMessage.action.g_b);
                        if (Utilities.isGoodGaAndGb(r0, r0)) {
                            authKey = r0.modPow(new BigInteger(1, chat.a_or_b), r0).toByteArray();
                            if (authKey.length > 256) {
                                correctedAuth = new byte[256];
                                System.arraycopy(authKey, authKey.length + InputDeviceCompat.SOURCE_ANY, correctedAuth, 0, 256);
                                authKey = correctedAuth;
                            } else if (authKey.length < 256) {
                                correctedAuth = new byte[256];
                                System.arraycopy(authKey, 0, correctedAuth, 256 - authKey.length, authKey.length);
                                for (a = 0; a < 256 - authKey.length; a++) {
                                    authKey[a] = (byte) 0;
                                }
                                authKey = correctedAuth;
                            }
                            authKeyHash = Utilities.computeSHA1(authKey);
                            authKeyId = new byte[8];
                            System.arraycopy(authKeyHash, authKeyHash.length - 8, authKeyId, 0, 8);
                            long fingerprint = Utilities.bytesToLong(authKeyId);
                            if (serviceMessage.action.key_fingerprint == fingerprint) {
                                chat.future_auth_key = authKey;
                                chat.future_key_fingerprint = fingerprint;
                                MessagesStorage.getInstance().updateEncryptedChat(chat);
                                sendCommitKeyMessage(chat, null);
                            } else {
                                chat.future_auth_key = new byte[256];
                                chat.future_key_fingerprint = 0;
                                chat.exchange_id = 0;
                                MessagesStorage.getInstance().updateEncryptedChat(chat);
                                sendAbortKeyMessage(chat, null, serviceMessage.action.exchange_id);
                            }
                        } else {
                            chat.future_auth_key = new byte[256];
                            chat.future_key_fingerprint = 0;
                            chat.exchange_id = 0;
                            MessagesStorage.getInstance().updateEncryptedChat(chat);
                            sendAbortKeyMessage(chat, null, serviceMessage.action.exchange_id);
                            return null;
                        }
                    }
                    chat.future_auth_key = new byte[256];
                    chat.future_key_fingerprint = 0;
                    chat.exchange_id = 0;
                    MessagesStorage.getInstance().updateEncryptedChat(chat);
                    sendAbortKeyMessage(chat, null, serviceMessage.action.exchange_id);
                } else if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionCommitKey) {
                    if (chat.exchange_id == serviceMessage.action.exchange_id && chat.future_key_fingerprint == serviceMessage.action.key_fingerprint) {
                        long old_fingerpring = chat.key_fingerprint;
                        byte[] old_key = chat.auth_key;
                        chat.key_fingerprint = chat.future_key_fingerprint;
                        chat.auth_key = chat.future_auth_key;
                        chat.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                        chat.future_auth_key = old_key;
                        chat.future_key_fingerprint = old_fingerpring;
                        chat.key_use_count_in = (short) 0;
                        chat.key_use_count_out = (short) 0;
                        chat.exchange_id = 0;
                        MessagesStorage.getInstance().updateEncryptedChat(chat);
                        sendNoopMessage(chat, null);
                    } else {
                        chat.future_auth_key = new byte[256];
                        chat.future_key_fingerprint = 0;
                        chat.exchange_id = 0;
                        MessagesStorage.getInstance().updateEncryptedChat(chat);
                        sendAbortKeyMessage(chat, null, serviceMessage.action.exchange_id);
                    }
                } else if (serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionAbortKey) {
                    if (chat.exchange_id == serviceMessage.action.exchange_id) {
                        chat.future_auth_key = new byte[256];
                        chat.future_key_fingerprint = 0;
                        chat.exchange_id = 0;
                        MessagesStorage.getInstance().updateEncryptedChat(chat);
                    }
                } else if (!(serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionNoop)) {
                    if (!(serviceMessage.action instanceof TLRPC$TL_decryptedMessageActionResend)) {
                        return null;
                    }
                    if (serviceMessage.action.end_seq_no < chat.in_seq_no || serviceMessage.action.end_seq_no < serviceMessage.action.start_seq_no) {
                        return null;
                    }
                    if (serviceMessage.action.start_seq_no < chat.in_seq_no) {
                        serviceMessage.action.start_seq_no = chat.in_seq_no;
                    }
                    resendMessages(serviceMessage.action.start_seq_no, serviceMessage.action.end_seq_no, chat);
                }
            } else {
                FileLog.e("unknown message " + object);
            }
        } else {
            FileLog.e("unknown TLObject");
        }
        return null;
    }

    private TLRPC$Message createDeleteMessage(int mid, int seq_out, int seq_in, long random_id, TLRPC$EncryptedChat encryptedChat) {
        TLRPC$TL_messageService newMsg = new TLRPC$TL_messageService();
        newMsg.action = new TLRPC$TL_messageEncryptedAction();
        newMsg.action.encryptedAction = new TLRPC$TL_decryptedMessageActionDeleteMessages();
        newMsg.action.encryptedAction.random_ids.add(Long.valueOf(random_id));
        newMsg.id = mid;
        newMsg.local_id = mid;
        newMsg.from_id = UserConfig.getClientUserId();
        newMsg.unread = true;
        newMsg.out = true;
        newMsg.flags = 256;
        newMsg.dialog_id = ((long) encryptedChat.id) << 32;
        newMsg.to_id = new TLRPC$TL_peerUser();
        newMsg.send_state = 1;
        newMsg.seq_in = seq_in;
        newMsg.seq_out = seq_out;
        if (encryptedChat.participant_id == UserConfig.getClientUserId()) {
            newMsg.to_id.user_id = encryptedChat.admin_id;
        } else {
            newMsg.to_id.user_id = encryptedChat.participant_id;
        }
        newMsg.date = 0;
        newMsg.random_id = random_id;
        return newMsg;
    }

    private void resendMessages(final int startSeq, final int endSeq, final TLRPC$EncryptedChat encryptedChat) {
        if (encryptedChat != null && endSeq - startSeq >= 0) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.SecretChatHelper$7$1 */
                class C16191 implements Comparator<TLRPC$Message> {
                    C16191() {
                    }

                    public int compare(TLRPC$Message lhs, TLRPC$Message rhs) {
                        return AndroidUtilities.compare(lhs.seq_out, rhs.seq_out);
                    }
                }

                public void run() {
                    try {
                        int sSeq = startSeq;
                        if (encryptedChat.admin_id == UserConfig.getClientUserId() && sSeq % 2 == 0) {
                            sSeq++;
                        }
                        SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT uid FROM requested_holes WHERE uid = %d AND ((seq_out_start >= %d AND %d <= seq_out_end) OR (seq_out_start >= %d AND %d <= seq_out_end))", new Object[]{Integer.valueOf(encryptedChat.id), Integer.valueOf(sSeq), Integer.valueOf(sSeq), Integer.valueOf(endSeq), Integer.valueOf(endSeq)}), new Object[0]);
                        boolean exists = cursor.next();
                        cursor.dispose();
                        if (!exists) {
                            long dialog_id = ((long) encryptedChat.id) << 32;
                            HashMap<Integer, TLRPC$Message> messagesToResend = new HashMap();
                            ArrayList<TLRPC$Message> messages = new ArrayList();
                            for (int a = sSeq; a < endSeq; a += 2) {
                                messagesToResend.put(Integer.valueOf(a), null);
                            }
                            cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT m.data, r.random_id, s.seq_in, s.seq_out, m.ttl, s.mid FROM messages_seq as s LEFT JOIN randoms as r ON r.mid = s.mid LEFT JOIN messages as m ON m.mid = s.mid WHERE m.uid = %d AND m.out = 1 AND s.seq_out >= %d AND s.seq_out <= %d ORDER BY seq_out ASC", new Object[]{Long.valueOf(dialog_id), Integer.valueOf(sSeq), Integer.valueOf(endSeq)}), new Object[0]);
                            while (cursor.next()) {
                                TLRPC$Message message;
                                long random_id = cursor.longValue(1);
                                if (random_id == 0) {
                                    random_id = Utilities.random.nextLong();
                                }
                                int seq_in = cursor.intValue(2);
                                int seq_out = cursor.intValue(3);
                                int mid = cursor.intValue(5);
                                AbstractSerializedData data = cursor.byteBufferValue(0);
                                if (data != null) {
                                    message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                    data.reuse();
                                    message.random_id = random_id;
                                    message.dialog_id = dialog_id;
                                    message.seq_in = seq_in;
                                    message.seq_out = seq_out;
                                    message.ttl = cursor.intValue(4);
                                } else {
                                    message = SecretChatHelper.this.createDeleteMessage(mid, seq_out, seq_in, random_id, encryptedChat);
                                }
                                messages.add(message);
                                messagesToResend.remove(Integer.valueOf(seq_out));
                            }
                            cursor.dispose();
                            if (!messagesToResend.isEmpty()) {
                                for (Entry<Integer, TLRPC$Message> entry : messagesToResend.entrySet()) {
                                    ArrayList<TLRPC$Message> arrayList = messages;
                                    arrayList.add(SecretChatHelper.this.createDeleteMessage(UserConfig.getNewMessageId(), ((Integer) entry.getKey()).intValue(), 0, Utilities.random.nextLong(), encryptedChat));
                                }
                                UserConfig.saveConfig(false);
                            }
                            Collections.sort(messages, new C16191());
                            ArrayList<TLRPC$EncryptedChat> encryptedChats = new ArrayList();
                            encryptedChats.add(encryptedChat);
                            final ArrayList<TLRPC$Message> arrayList2 = messages;
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    for (int a = 0; a < arrayList2.size(); a++) {
                                        MessageObject messageObject = new MessageObject((TLRPC$Message) arrayList2.get(a), null, false);
                                        messageObject.resendAsIs = true;
                                        SendMessagesHelper.getInstance().retrySendMessage(messageObject, true);
                                    }
                                }
                            });
                            SendMessagesHelper.getInstance().processUnsentMessages(messages, new ArrayList(), new ArrayList(), encryptedChats);
                            MessagesStorage.getInstance().getDatabase().executeFast(String.format(Locale.US, "REPLACE INTO requested_holes VALUES(%d, %d, %d)", new Object[]{Integer.valueOf(encryptedChat.id), Integer.valueOf(sSeq), Integer.valueOf(endSeq)})).stepThis().dispose();
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    public void checkSecretHoles(TLRPC$EncryptedChat chat, ArrayList<TLRPC$Message> messages) {
        ArrayList<TL_decryptedMessageHolder> holes = (ArrayList) this.secretHolesQueue.get(Integer.valueOf(chat.id));
        if (holes != null) {
            Collections.sort(holes, new C16228());
            boolean update = false;
            int a = 0;
            while (holes.size() > 0) {
                TL_decryptedMessageHolder holder = (TL_decryptedMessageHolder) holes.get(a);
                if (holder.layer.out_seq_no != chat.seq_in && chat.seq_in != holder.layer.out_seq_no - 2) {
                    break;
                }
                applyPeerLayer(chat, holder.layer.layer);
                chat.seq_in = holder.layer.out_seq_no;
                chat.in_seq_no = holder.layer.in_seq_no;
                holes.remove(a);
                a--;
                update = true;
                if (holder.decryptedWithVersion == 2) {
                    chat.mtproto_seq = Math.min(chat.mtproto_seq, chat.seq_in);
                }
                TLRPC$Message message = processDecryptedObject(chat, holder.file, holder.date, holder.layer.message, holder.new_key_used);
                if (message != null) {
                    messages.add(message);
                }
                a++;
            }
            if (holes.isEmpty()) {
                this.secretHolesQueue.remove(Integer.valueOf(chat.id));
            }
            if (update) {
                MessagesStorage.getInstance().updateEncryptedChatSeq(chat, true);
            }
        }
    }

    private boolean decryptWithMtProtoVersion(NativeByteBuffer is, byte[] keyToDecrypt, byte[] messageKey, int version, boolean incoming, boolean encryptOnError) {
        if (version == 1) {
            incoming = false;
        }
        MessageKeyData keyData = MessageKeyData.generateMessageKeyData(keyToDecrypt, messageKey, incoming, version);
        Utilities.aesIgeEncryption(is.buffer, keyData.aesKey, keyData.aesIv, false, false, 24, is.limit() - 24);
        int len = is.readInt32(false);
        if (version == 2) {
            if (!Utilities.arraysEquals(messageKey, 0, Utilities.computeSHA256(keyToDecrypt, (incoming ? 8 : 0) + 88, 32, is.buffer, 24, is.buffer.limit()), 8)) {
                if (encryptOnError) {
                    Utilities.aesIgeEncryption(is.buffer, keyData.aesKey, keyData.aesIv, true, false, 24, is.limit() - 24);
                    is.position(24);
                }
                return false;
            }
        }
        int l = len + 28;
        if (l < is.buffer.limit() - 15 || l > is.buffer.limit()) {
            l = is.buffer.limit();
        }
        byte[] messageKeyFull = Utilities.computeSHA1(is.buffer, 24, l);
        if (!Utilities.arraysEquals(messageKey, 0, messageKeyFull, messageKeyFull.length - 16)) {
            if (encryptOnError) {
                Utilities.aesIgeEncryption(is.buffer, keyData.aesKey, keyData.aesIv, true, false, 24, is.limit() - 24);
                is.position(24);
            }
            return false;
        }
        if (len <= 0 || len > is.limit() - 28) {
            return false;
        }
        int padding = (is.limit() - 28) - len;
        if ((version != 2 || (padding >= 12 && padding <= 1024)) && (version != 1 || padding <= 15)) {
            return true;
        }
        return false;
    }

    protected ArrayList<TLRPC$Message> decryptMessage(TLRPC$EncryptedMessage message) {
        TLRPC$EncryptedChat chat = MessagesController.getInstance().getEncryptedChatDB(message.chat_id, true);
        if (chat == null || (chat instanceof TLRPC$TL_encryptedChatDiscarded)) {
            return null;
        }
        try {
            NativeByteBuffer is = new NativeByteBuffer(message.bytes.length);
            is.writeBytes(message.bytes);
            is.position(0);
            long fingerprint = is.readInt64(false);
            byte[] keyToDecrypt = null;
            boolean new_key_used = false;
            if (chat.key_fingerprint == fingerprint) {
                keyToDecrypt = chat.auth_key;
            } else if (chat.future_key_fingerprint != 0 && chat.future_key_fingerprint == fingerprint) {
                keyToDecrypt = chat.future_auth_key;
                new_key_used = true;
            }
            int mtprotoVersion = AndroidUtilities.getPeerLayerVersion(chat.layer) >= 73 ? 2 : 1;
            int decryptedWithVersion = mtprotoVersion;
            if (keyToDecrypt != null) {
                byte[] messageKey = is.readData(16, false);
                boolean incoming = chat.admin_id == UserConfig.getClientUserId();
                boolean tryAnotherDecrypt = true;
                if (decryptedWithVersion == 2 && chat.mtproto_seq != 0) {
                    tryAnotherDecrypt = false;
                }
                if (!decryptWithMtProtoVersion(is, keyToDecrypt, messageKey, mtprotoVersion, incoming, tryAnotherDecrypt)) {
                    if (mtprotoVersion == 2) {
                        decryptedWithVersion = 1;
                        if (!(tryAnotherDecrypt && decryptWithMtProtoVersion(is, keyToDecrypt, messageKey, 1, incoming, false))) {
                            return null;
                        }
                    }
                    decryptedWithVersion = 2;
                    if (!decryptWithMtProtoVersion(is, keyToDecrypt, messageKey, 2, incoming, tryAnotherDecrypt)) {
                        return null;
                    }
                }
                TLObject object = TLClassStore.Instance().TLdeserialize(is, is.readInt32(false), false);
                is.reuse();
                if (!new_key_used && AndroidUtilities.getPeerLayerVersion(chat.layer) >= 20) {
                    chat.key_use_count_in = (short) (chat.key_use_count_in + 1);
                }
                if (object instanceof TLRPC$TL_decryptedMessageLayer) {
                    TLRPC$TL_decryptedMessageLayer layer = (TLRPC$TL_decryptedMessageLayer) object;
                    if (chat.seq_in == 0 && chat.seq_out == 0) {
                        if (chat.admin_id == UserConfig.getClientUserId()) {
                            chat.seq_out = 1;
                            chat.seq_in = -2;
                        } else {
                            chat.seq_in = -1;
                        }
                    }
                    if (layer.random_bytes.length < 15) {
                        FileLog.e("got random bytes less than needed");
                        return null;
                    }
                    FileLog.e("current chat in_seq = " + chat.seq_in + " out_seq = " + chat.seq_out);
                    FileLog.e("got message with in_seq = " + layer.in_seq_no + " out_seq = " + layer.out_seq_no);
                    if (layer.out_seq_no <= chat.seq_in) {
                        return null;
                    }
                    if (decryptedWithVersion == 1 && chat.mtproto_seq != 0 && layer.out_seq_no >= chat.mtproto_seq) {
                        return null;
                    }
                    if (chat.seq_in != layer.out_seq_no - 2) {
                        FileLog.e("got hole");
                        ArrayList<TL_decryptedMessageHolder> arr = (ArrayList) this.secretHolesQueue.get(Integer.valueOf(chat.id));
                        if (arr == null) {
                            arr = new ArrayList();
                            this.secretHolesQueue.put(Integer.valueOf(chat.id), arr);
                        }
                        if (arr.size() >= 4) {
                            this.secretHolesQueue.remove(Integer.valueOf(chat.id));
                            TLRPC$TL_encryptedChatDiscarded newChat = new TLRPC$TL_encryptedChatDiscarded();
                            newChat.id = chat.id;
                            newChat.user_id = chat.user_id;
                            newChat.auth_key = chat.auth_key;
                            newChat.key_create_date = chat.key_create_date;
                            newChat.key_use_count_in = chat.key_use_count_in;
                            newChat.key_use_count_out = chat.key_use_count_out;
                            newChat.seq_in = chat.seq_in;
                            newChat.seq_out = chat.seq_out;
                            final TLRPC$TL_encryptedChatDiscarded tLRPC$TL_encryptedChatDiscarded = newChat;
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    MessagesController.getInstance().putEncryptedChat(tLRPC$TL_encryptedChatDiscarded, false);
                                    MessagesStorage.getInstance().updateEncryptedChat(tLRPC$TL_encryptedChatDiscarded);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, new Object[]{tLRPC$TL_encryptedChatDiscarded});
                                }
                            });
                            declineSecretChat(chat.id);
                            return null;
                        }
                        TL_decryptedMessageHolder holder = new TL_decryptedMessageHolder();
                        holder.layer = layer;
                        holder.file = message.file;
                        holder.date = message.date;
                        holder.new_key_used = new_key_used;
                        holder.decryptedWithVersion = decryptedWithVersion;
                        arr.add(holder);
                        return null;
                    }
                    if (decryptedWithVersion == 2) {
                        chat.mtproto_seq = Math.min(chat.mtproto_seq, chat.seq_in);
                    }
                    applyPeerLayer(chat, layer.layer);
                    chat.seq_in = layer.out_seq_no;
                    chat.in_seq_no = layer.in_seq_no;
                    MessagesStorage.getInstance().updateEncryptedChatSeq(chat, true);
                    object = layer.message;
                } else if (!((object instanceof TLRPC$TL_decryptedMessageService) && (((TLRPC$TL_decryptedMessageService) object).action instanceof TLRPC$TL_decryptedMessageActionNotifyLayer))) {
                    return null;
                }
                ArrayList<TLRPC$Message> messages = new ArrayList();
                TLRPC$Message decryptedMessage = processDecryptedObject(chat, message.file, message.date, object, new_key_used);
                if (decryptedMessage != null) {
                    messages.add(decryptedMessage);
                }
                checkSecretHoles(chat, messages);
                return messages;
            }
            is.reuse();
            FileLog.e(String.format("fingerprint mismatch %x", new Object[]{Long.valueOf(fingerprint)}));
            return null;
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void requestNewSecretChatKey(TLRPC$EncryptedChat encryptedChat) {
        if (AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 20) {
            byte[] salt = new byte[256];
            Utilities.random.nextBytes(salt);
            byte[] g_a = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, salt), new BigInteger(1, MessagesStorage.secretPBytes)).toByteArray();
            if (g_a.length > 256) {
                byte[] correctedAuth = new byte[256];
                System.arraycopy(g_a, 1, correctedAuth, 0, 256);
                g_a = correctedAuth;
            }
            encryptedChat.exchange_id = SendMessagesHelper.getInstance().getNextRandomId();
            encryptedChat.a_or_b = salt;
            encryptedChat.g_a = g_a;
            MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
            sendRequestKeyMessage(encryptedChat, null);
        }
    }

    public void processAcceptedSecretChat(final TLRPC$EncryptedChat encryptedChat) {
        BigInteger p = new BigInteger(1, MessagesStorage.secretPBytes);
        BigInteger i_authKey = new BigInteger(1, encryptedChat.g_a_or_b);
        if (Utilities.isGoodGaAndGb(i_authKey, p)) {
            byte[] authKey = i_authKey.modPow(new BigInteger(1, encryptedChat.a_or_b), p).toByteArray();
            byte[] correctedAuth;
            if (authKey.length > 256) {
                correctedAuth = new byte[256];
                System.arraycopy(authKey, authKey.length + InputDeviceCompat.SOURCE_ANY, correctedAuth, 0, 256);
                authKey = correctedAuth;
            } else if (authKey.length < 256) {
                correctedAuth = new byte[256];
                System.arraycopy(authKey, 0, correctedAuth, 256 - authKey.length, authKey.length);
                for (int a = 0; a < 256 - authKey.length; a++) {
                    authKey[a] = (byte) 0;
                }
                authKey = correctedAuth;
            }
            byte[] authKeyHash = Utilities.computeSHA1(authKey);
            byte[] authKeyId = new byte[8];
            System.arraycopy(authKeyHash, authKeyHash.length - 8, authKeyId, 0, 8);
            if (encryptedChat.key_fingerprint == Utilities.bytesToLong(authKeyId)) {
                encryptedChat.auth_key = authKey;
                encryptedChat.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                encryptedChat.seq_in = -2;
                encryptedChat.seq_out = 1;
                MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                MessagesController.getInstance().putEncryptedChat(encryptedChat, false);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, new Object[]{encryptedChat});
                        SecretChatHelper.this.sendNotifyLayerMessage(encryptedChat, null);
                    }
                });
                return;
            }
            final TLRPC$TL_encryptedChatDiscarded newChat = new TLRPC$TL_encryptedChatDiscarded();
            newChat.id = encryptedChat.id;
            newChat.user_id = encryptedChat.user_id;
            newChat.auth_key = encryptedChat.auth_key;
            newChat.key_create_date = encryptedChat.key_create_date;
            newChat.key_use_count_in = encryptedChat.key_use_count_in;
            newChat.key_use_count_out = encryptedChat.key_use_count_out;
            newChat.seq_in = encryptedChat.seq_in;
            newChat.seq_out = encryptedChat.seq_out;
            newChat.admin_id = encryptedChat.admin_id;
            newChat.mtproto_seq = encryptedChat.mtproto_seq;
            MessagesStorage.getInstance().updateEncryptedChat(newChat);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController.getInstance().putEncryptedChat(newChat, false);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, new Object[]{newChat});
                }
            });
            declineSecretChat(encryptedChat.id);
            return;
        }
        declineSecretChat(encryptedChat.id);
    }

    public void declineSecretChat(int chat_id) {
        TLRPC$TL_messages_discardEncryption req = new TLRPC$TL_messages_discardEncryption();
        req.chat_id = chat_id;
        ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
            public void run(TLObject response, TLRPC$TL_error error) {
            }
        });
    }

    public void acceptSecretChat(final TLRPC$EncryptedChat encryptedChat) {
        if (this.acceptingChats.get(Integer.valueOf(encryptedChat.id)) == null) {
            this.acceptingChats.put(Integer.valueOf(encryptedChat.id), encryptedChat);
            TLRPC$TL_messages_getDhConfig req = new TLRPC$TL_messages_getDhConfig();
            req.random_length = 256;
            req.version = MessagesStorage.lastSecretVersion;
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {

                /* renamed from: org.telegram.messenger.SecretChatHelper$13$1 */
                class C16001 implements RequestDelegate {
                    C16001() {
                    }

                    public void run(TLObject response, TLRPC$TL_error error) {
                        SecretChatHelper.this.acceptingChats.remove(Integer.valueOf(encryptedChat.id));
                        if (error == null) {
                            final TLRPC$EncryptedChat newChat = (TLRPC$EncryptedChat) response;
                            newChat.auth_key = encryptedChat.auth_key;
                            newChat.user_id = encryptedChat.user_id;
                            newChat.seq_in = encryptedChat.seq_in;
                            newChat.seq_out = encryptedChat.seq_out;
                            newChat.key_create_date = encryptedChat.key_create_date;
                            newChat.key_use_count_in = encryptedChat.key_use_count_in;
                            newChat.key_use_count_out = encryptedChat.key_use_count_out;
                            MessagesStorage.getInstance().updateEncryptedChat(newChat);
                            MessagesController.getInstance().putEncryptedChat(newChat, false);
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, new Object[]{newChat});
                                    SecretChatHelper.this.sendNotifyLayerMessage(newChat, null);
                                }
                            });
                        }
                    }
                }

                public void run(TLObject response, TLRPC$TL_error error) {
                    if (error == null) {
                        int a;
                        TLRPC$messages_DhConfig res = (TLRPC$messages_DhConfig) response;
                        if (response instanceof TLRPC$TL_messages_dhConfig) {
                            if (Utilities.isGoodPrime(res.f86p, res.f85g)) {
                                MessagesStorage.secretPBytes = res.f86p;
                                MessagesStorage.secretG = res.f85g;
                                MessagesStorage.lastSecretVersion = res.version;
                                MessagesStorage.getInstance().saveSecretParams(MessagesStorage.lastSecretVersion, MessagesStorage.secretG, MessagesStorage.secretPBytes);
                            } else {
                                SecretChatHelper.this.acceptingChats.remove(Integer.valueOf(encryptedChat.id));
                                SecretChatHelper.this.declineSecretChat(encryptedChat.id);
                                return;
                            }
                        }
                        byte[] salt = new byte[256];
                        for (a = 0; a < 256; a++) {
                            salt[a] = (byte) (((byte) ((int) (Utilities.random.nextDouble() * 256.0d))) ^ res.random[a]);
                        }
                        encryptedChat.a_or_b = salt;
                        encryptedChat.seq_in = -1;
                        encryptedChat.seq_out = 0;
                        BigInteger p = new BigInteger(1, MessagesStorage.secretPBytes);
                        BigInteger g_b = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, salt), p);
                        BigInteger g_a = new BigInteger(1, encryptedChat.g_a);
                        if (Utilities.isGoodGaAndGb(g_a, p)) {
                            byte[] correctedAuth;
                            byte[] g_b_bytes = g_b.toByteArray();
                            if (g_b_bytes.length > 256) {
                                correctedAuth = new byte[256];
                                System.arraycopy(g_b_bytes, 1, correctedAuth, 0, 256);
                                g_b_bytes = correctedAuth;
                            }
                            byte[] authKey = g_a.modPow(new BigInteger(1, salt), p).toByteArray();
                            if (authKey.length > 256) {
                                correctedAuth = new byte[256];
                                System.arraycopy(authKey, authKey.length + InputDeviceCompat.SOURCE_ANY, correctedAuth, 0, 256);
                                authKey = correctedAuth;
                            } else if (authKey.length < 256) {
                                correctedAuth = new byte[256];
                                System.arraycopy(authKey, 0, correctedAuth, 256 - authKey.length, authKey.length);
                                for (a = 0; a < 256 - authKey.length; a++) {
                                    authKey[a] = (byte) 0;
                                }
                                authKey = correctedAuth;
                            }
                            byte[] authKeyHash = Utilities.computeSHA1(authKey);
                            byte[] authKeyId = new byte[8];
                            System.arraycopy(authKeyHash, authKeyHash.length - 8, authKeyId, 0, 8);
                            encryptedChat.auth_key = authKey;
                            encryptedChat.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                            TLRPC$TL_messages_acceptEncryption req2 = new TLRPC$TL_messages_acceptEncryption();
                            req2.g_b = g_b_bytes;
                            req2.peer = new TLRPC$TL_inputEncryptedChat();
                            req2.peer.chat_id = encryptedChat.id;
                            req2.peer.access_hash = encryptedChat.access_hash;
                            req2.key_fingerprint = Utilities.bytesToLong(authKeyId);
                            ConnectionsManager.getInstance().sendRequest(req2, new C16001());
                            return;
                        }
                        SecretChatHelper.this.acceptingChats.remove(Integer.valueOf(encryptedChat.id));
                        SecretChatHelper.this.declineSecretChat(encryptedChat.id);
                        return;
                    }
                    SecretChatHelper.this.acceptingChats.remove(Integer.valueOf(encryptedChat.id));
                }
            });
        }
    }

    public void startSecretChat(final Context context, final User user) {
        if (user != null && context != null) {
            this.startingSecretChat = true;
            final AlertDialog progressDialog = new AlertDialog(context, 1);
            progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            TLRPC$TL_messages_getDhConfig req = new TLRPC$TL_messages_getDhConfig();
            req.random_length = 256;
            req.version = MessagesStorage.lastSecretVersion;
            final int reqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {

                /* renamed from: org.telegram.messenger.SecretChatHelper$14$1 */
                class C16011 implements Runnable {
                    C16011() {
                    }

                    public void run() {
                        try {
                            if (!((Activity) context).isFinishing()) {
                                progressDialog.dismiss();
                            }
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                }

                /* renamed from: org.telegram.messenger.SecretChatHelper$14$3 */
                class C16063 implements Runnable {
                    C16063() {
                    }

                    public void run() {
                        SecretChatHelper.this.startingSecretChat = false;
                        if (!((Activity) context).isFinishing()) {
                            try {
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                        }
                    }
                }

                public void run(TLObject response, TLRPC$TL_error error) {
                    if (error == null) {
                        TLRPC$messages_DhConfig res = (TLRPC$messages_DhConfig) response;
                        if (response instanceof TLRPC$TL_messages_dhConfig) {
                            if (Utilities.isGoodPrime(res.f86p, res.f85g)) {
                                MessagesStorage.secretPBytes = res.f86p;
                                MessagesStorage.secretG = res.f85g;
                                MessagesStorage.lastSecretVersion = res.version;
                                MessagesStorage.getInstance().saveSecretParams(MessagesStorage.lastSecretVersion, MessagesStorage.secretG, MessagesStorage.secretPBytes);
                            } else {
                                AndroidUtilities.runOnUIThread(new C16011());
                                return;
                            }
                        }
                        final byte[] salt = new byte[256];
                        for (int a = 0; a < 256; a++) {
                            salt[a] = (byte) (((byte) ((int) (Utilities.random.nextDouble() * 256.0d))) ^ res.random[a]);
                        }
                        byte[] g_a = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, salt), new BigInteger(1, MessagesStorage.secretPBytes)).toByteArray();
                        if (g_a.length > 256) {
                            byte[] correctedAuth = new byte[256];
                            System.arraycopy(g_a, 1, correctedAuth, 0, 256);
                            g_a = correctedAuth;
                        }
                        TLRPC$TL_messages_requestEncryption req2 = new TLRPC$TL_messages_requestEncryption();
                        req2.g_a = g_a;
                        req2.user_id = MessagesController.getInputUser(user);
                        req2.random_id = Utilities.random.nextInt();
                        ConnectionsManager.getInstance().sendRequest(req2, new RequestDelegate() {

                            /* renamed from: org.telegram.messenger.SecretChatHelper$14$2$2 */
                            class C16042 implements Runnable {
                                C16042() {
                                }

                                public void run() {
                                    if (!((Activity) context).isFinishing()) {
                                        SecretChatHelper.this.startingSecretChat = false;
                                        try {
                                            progressDialog.dismiss();
                                        } catch (Exception e) {
                                            FileLog.e(e);
                                        }
                                        Builder builder = new Builder(context);
                                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        builder.setMessage(LocaleController.getString("CreateEncryptedChatError", R.string.CreateEncryptedChatError));
                                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                        builder.show().setCanceledOnTouchOutside(true);
                                    }
                                }
                            }

                            public void run(final TLObject response, TLRPC$TL_error error) {
                                if (error == null) {
                                    AndroidUtilities.runOnUIThread(new Runnable() {

                                        /* renamed from: org.telegram.messenger.SecretChatHelper$14$2$1$1 */
                                        class C16021 implements Runnable {
                                            C16021() {
                                            }

                                            public void run() {
                                                if (!SecretChatHelper.this.delayedEncryptedChatUpdates.isEmpty()) {
                                                    MessagesController.getInstance().processUpdateArray(SecretChatHelper.this.delayedEncryptedChatUpdates, null, null, false);
                                                    SecretChatHelper.this.delayedEncryptedChatUpdates.clear();
                                                }
                                            }
                                        }

                                        public void run() {
                                            SecretChatHelper.this.startingSecretChat = false;
                                            if (!((Activity) context).isFinishing()) {
                                                try {
                                                    progressDialog.dismiss();
                                                } catch (Exception e) {
                                                    FileLog.e(e);
                                                }
                                            }
                                            TLRPC$EncryptedChat chat = response;
                                            chat.user_id = chat.participant_id;
                                            chat.seq_in = -2;
                                            chat.seq_out = 1;
                                            chat.a_or_b = salt;
                                            MessagesController.getInstance().putEncryptedChat(chat, false);
                                            TLRPC$TL_dialog dialog = new TLRPC$TL_dialog();
                                            dialog.id = ((long) chat.id) << 32;
                                            dialog.unread_count = 0;
                                            dialog.top_message = 0;
                                            dialog.last_message_date = ConnectionsManager.getInstance().getCurrentTime();
                                            MessagesController.getInstance().dialogs_dict.put(Long.valueOf(dialog.id), dialog);
                                            MessagesController.getInstance().dialogs.add(dialog);
                                            MessagesController.getInstance().sortDialogs(null);
                                            MessagesStorage.getInstance().putEncryptedChat(chat, user, dialog);
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatCreated, new Object[]{chat});
                                            Utilities.stageQueue.postRunnable(new C16021());
                                        }
                                    });
                                    return;
                                }
                                SecretChatHelper.this.delayedEncryptedChatUpdates.clear();
                                AndroidUtilities.runOnUIThread(new C16042());
                            }
                        }, 2);
                        return;
                    }
                    SecretChatHelper.this.delayedEncryptedChatUpdates.clear();
                    AndroidUtilities.runOnUIThread(new C16063());
                }
            }, 2);
            progressDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ConnectionsManager.getInstance().cancelRequest(reqId, true);
                    try {
                        dialog.dismiss();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
            try {
                progressDialog.show();
            } catch (Exception e) {
            }
        }
    }
}
