package org.telegram.messenger;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import org.telegram.tgnet.TLRPC.DecryptedMessage;
import org.telegram.tgnet.TLRPC.DecryptedMessageAction;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.EncryptedFile;
import org.telegram.tgnet.TLRPC.EncryptedMessage;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;

public class SecretChatHelper {
    public static final int CURRENT_SECRET_CHAT_LAYER = 73;
    private static volatile SecretChatHelper Instance = null;
    private HashMap<Integer, EncryptedChat> acceptingChats = new HashMap();
    public ArrayList<TLRPC$Update> delayedEncryptedChatUpdates = new ArrayList();
    private ArrayList<Long> pendingEncMessagesToDelete = new ArrayList();
    private HashMap<Integer, ArrayList<TL_decryptedMessageHolder>> secretHolesQueue = new HashMap();
    private ArrayList<Integer> sendingNotifyLayer = new ArrayList();
    private boolean startingSecretChat = false;

    /* renamed from: org.telegram.messenger.SecretChatHelper$8 */
    class C33608 implements Comparator<TL_decryptedMessageHolder> {
        C33608() {
        }

        public int compare(TL_decryptedMessageHolder tL_decryptedMessageHolder, TL_decryptedMessageHolder tL_decryptedMessageHolder2) {
            return tL_decryptedMessageHolder.layer.out_seq_no > tL_decryptedMessageHolder2.layer.out_seq_no ? 1 : tL_decryptedMessageHolder.layer.out_seq_no < tL_decryptedMessageHolder2.layer.out_seq_no ? -1 : 0;
        }
    }

    public static class TL_decryptedMessageHolder extends TLObject {
        public static int constructor = 1431655929;
        public int date;
        public int decryptedWithVersion;
        public EncryptedFile file;
        public TLRPC$TL_decryptedMessageLayer layer;
        public boolean new_key_used;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            abstractSerializedData.readInt64(z);
            this.date = abstractSerializedData.readInt32(z);
            this.layer = TLRPC$TL_decryptedMessageLayer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (abstractSerializedData.readBool(z)) {
                this.file = EncryptedFile.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
            this.new_key_used = abstractSerializedData.readBool(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt64(0);
            abstractSerializedData.writeInt32(this.date);
            this.layer.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeBool(this.file != null);
            if (this.file != null) {
                this.file.serializeToStream(abstractSerializedData);
            }
            abstractSerializedData.writeBool(this.new_key_used);
        }
    }

    private void applyPeerLayer(final EncryptedChat encryptedChat, int i) {
        int peerLayerVersion = AndroidUtilities.getPeerLayerVersion(encryptedChat.layer);
        if (i > peerLayerVersion) {
            if (encryptedChat.key_hash.length == 16 && peerLayerVersion >= 46) {
                try {
                    Object computeSHA256 = Utilities.computeSHA256(encryptedChat.auth_key, 0, encryptedChat.auth_key.length);
                    Object obj = new byte[36];
                    System.arraycopy(encryptedChat.key_hash, 0, obj, 0, 16);
                    System.arraycopy(computeSHA256, 0, obj, 16, 20);
                    encryptedChat.key_hash = obj;
                    MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                } catch (Throwable th) {
                    FileLog.m13728e(th);
                }
            }
            encryptedChat.layer = AndroidUtilities.setPeerLayerVersion(encryptedChat.layer, i);
            MessagesStorage.getInstance().updateEncryptedChatLayer(encryptedChat);
            if (peerLayerVersion < 73) {
                sendNotifyLayerMessage(encryptedChat, null);
            }
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, encryptedChat);
                }
            });
        }
    }

    private Message createDeleteMessage(int i, int i2, int i3, long j, EncryptedChat encryptedChat) {
        Message tLRPC$TL_messageService = new TLRPC$TL_messageService();
        tLRPC$TL_messageService.action = new TLRPC$TL_messageEncryptedAction();
        tLRPC$TL_messageService.action.encryptedAction = new TLRPC$TL_decryptedMessageActionDeleteMessages();
        tLRPC$TL_messageService.action.encryptedAction.random_ids.add(Long.valueOf(j));
        tLRPC$TL_messageService.id = i;
        tLRPC$TL_messageService.local_id = i;
        tLRPC$TL_messageService.from_id = UserConfig.getClientUserId();
        tLRPC$TL_messageService.unread = true;
        tLRPC$TL_messageService.out = true;
        tLRPC$TL_messageService.flags = 256;
        tLRPC$TL_messageService.dialog_id = ((long) encryptedChat.id) << 32;
        tLRPC$TL_messageService.to_id = new TLRPC$TL_peerUser();
        tLRPC$TL_messageService.send_state = 1;
        tLRPC$TL_messageService.seq_in = i3;
        tLRPC$TL_messageService.seq_out = i2;
        if (encryptedChat.participant_id == UserConfig.getClientUserId()) {
            tLRPC$TL_messageService.to_id.user_id = encryptedChat.admin_id;
        } else {
            tLRPC$TL_messageService.to_id.user_id = encryptedChat.participant_id;
        }
        tLRPC$TL_messageService.date = 0;
        tLRPC$TL_messageService.random_id = j;
        return tLRPC$TL_messageService;
    }

    private TLRPC$TL_messageService createServiceSecretMessage(EncryptedChat encryptedChat, DecryptedMessageAction decryptedMessageAction) {
        TLRPC$TL_messageService tLRPC$TL_messageService = new TLRPC$TL_messageService();
        tLRPC$TL_messageService.action = new TLRPC$TL_messageEncryptedAction();
        tLRPC$TL_messageService.action.encryptedAction = decryptedMessageAction;
        int newMessageId = UserConfig.getNewMessageId();
        tLRPC$TL_messageService.id = newMessageId;
        tLRPC$TL_messageService.local_id = newMessageId;
        tLRPC$TL_messageService.from_id = UserConfig.getClientUserId();
        tLRPC$TL_messageService.unread = true;
        tLRPC$TL_messageService.out = true;
        tLRPC$TL_messageService.flags = 256;
        tLRPC$TL_messageService.dialog_id = ((long) encryptedChat.id) << 32;
        tLRPC$TL_messageService.to_id = new TLRPC$TL_peerUser();
        tLRPC$TL_messageService.send_state = 1;
        if (encryptedChat.participant_id == UserConfig.getClientUserId()) {
            tLRPC$TL_messageService.to_id.user_id = encryptedChat.admin_id;
        } else {
            tLRPC$TL_messageService.to_id.user_id = encryptedChat.participant_id;
        }
        if ((decryptedMessageAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) || (decryptedMessageAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL)) {
            tLRPC$TL_messageService.date = ConnectionsManager.getInstance().getCurrentTime();
        } else {
            tLRPC$TL_messageService.date = 0;
        }
        tLRPC$TL_messageService.random_id = SendMessagesHelper.getInstance().getNextRandomId();
        UserConfig.saveConfig(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(tLRPC$TL_messageService);
        MessagesStorage.getInstance().putMessages(arrayList, false, true, true, 0);
        return tLRPC$TL_messageService;
    }

    private boolean decryptWithMtProtoVersion(NativeByteBuffer nativeByteBuffer, byte[] bArr, byte[] bArr2, int i, boolean z, boolean z2) {
        if (i == 1) {
            z = false;
        }
        MessageKeyData generateMessageKeyData = MessageKeyData.generateMessageKeyData(bArr, bArr2, z, i);
        Utilities.aesIgeEncryption(nativeByteBuffer.buffer, generateMessageKeyData.aesKey, generateMessageKeyData.aesIv, false, false, 24, nativeByteBuffer.limit() - 24);
        int readInt32 = nativeByteBuffer.readInt32(false);
        if (i == 2) {
            if (!Utilities.arraysEquals(bArr2, 0, Utilities.computeSHA256(bArr, (z ? 8 : 0) + 88, 32, nativeByteBuffer.buffer, 24, nativeByteBuffer.buffer.limit()), 8)) {
                if (z2) {
                    Utilities.aesIgeEncryption(nativeByteBuffer.buffer, generateMessageKeyData.aesKey, generateMessageKeyData.aesIv, true, false, 24, nativeByteBuffer.limit() - 24);
                    nativeByteBuffer.position(24);
                }
                return false;
            }
        }
        int i2 = readInt32 + 28;
        if (i2 < nativeByteBuffer.buffer.limit() - 15 || i2 > nativeByteBuffer.buffer.limit()) {
            i2 = nativeByteBuffer.buffer.limit();
        }
        byte[] computeSHA1 = Utilities.computeSHA1(nativeByteBuffer.buffer, 24, i2);
        if (!Utilities.arraysEquals(bArr2, 0, computeSHA1, computeSHA1.length - 16)) {
            if (z2) {
                Utilities.aesIgeEncryption(nativeByteBuffer.buffer, generateMessageKeyData.aesKey, generateMessageKeyData.aesIv, true, false, 24, nativeByteBuffer.limit() - 24);
                nativeByteBuffer.position(24);
            }
            return false;
        }
        if (readInt32 <= 0 || readInt32 > nativeByteBuffer.limit() - 28) {
            return false;
        }
        i2 = (nativeByteBuffer.limit() - 28) - readInt32;
        return (i != 2 || (i2 >= 12 && i2 <= 1024)) && (i != 1 || i2 <= 15);
    }

    public static SecretChatHelper getInstance() {
        SecretChatHelper secretChatHelper = Instance;
        if (secretChatHelper == null) {
            synchronized (SecretChatHelper.class) {
                secretChatHelper = Instance;
                if (secretChatHelper == null) {
                    secretChatHelper = new SecretChatHelper();
                    Instance = secretChatHelper;
                }
            }
        }
        return secretChatHelper;
    }

    public static boolean isSecretInvisibleMessage(Message message) {
        return (!(message.action instanceof TLRPC$TL_messageEncryptedAction) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL)) ? false : true;
    }

    public static boolean isSecretVisibleMessage(Message message) {
        return (message.action instanceof TLRPC$TL_messageEncryptedAction) && ((message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) || (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL));
    }

    private void resendMessages(final int i, final int i2, final EncryptedChat encryptedChat) {
        if (encryptedChat != null && i2 - i >= 0) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.SecretChatHelper$7$1 */
                class C33571 implements Comparator<Message> {
                    C33571() {
                    }

                    public int compare(Message message, Message message2) {
                        return AndroidUtilities.compare(message.seq_out, message2.seq_out);
                    }
                }

                public void run() {
                    try {
                        int i = i;
                        if (encryptedChat.admin_id == UserConfig.getClientUserId() && i % 2 == 0) {
                            i++;
                        }
                        SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT uid FROM requested_holes WHERE uid = %d AND ((seq_out_start >= %d AND %d <= seq_out_end) OR (seq_out_start >= %d AND %d <= seq_out_end))", new Object[]{Integer.valueOf(encryptedChat.id), Integer.valueOf(i), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i2)}), new Object[0]);
                        boolean a = b.m12152a();
                        b.m12155b();
                        if (!a) {
                            long j = ((long) encryptedChat.id) << 32;
                            HashMap hashMap = new HashMap();
                            final ArrayList arrayList = new ArrayList();
                            for (int i2 = i; i2 < i2; i2 += 2) {
                                hashMap.put(Integer.valueOf(i2), null);
                            }
                            SQLiteCursor b2 = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT m.data, r.random_id, s.seq_in, s.seq_out, m.ttl, s.mid FROM messages_seq as s LEFT JOIN randoms as r ON r.mid = s.mid LEFT JOIN messages as m ON m.mid = s.mid WHERE m.uid = %d AND m.out = 1 AND s.seq_out >= %d AND s.seq_out <= %d ORDER BY seq_out ASC", new Object[]{Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2)}), new Object[0]);
                            while (b2.m12152a()) {
                                Object TLdeserialize;
                                long d = b2.m12158d(1);
                                if (d == 0) {
                                    d = Utilities.random.nextLong();
                                }
                                int b3 = b2.m12154b(2);
                                int b4 = b2.m12154b(3);
                                int b5 = b2.m12154b(5);
                                AbstractSerializedData g = b2.m12161g(0);
                                if (g != null) {
                                    TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                    g.reuse();
                                    TLdeserialize.random_id = d;
                                    TLdeserialize.dialog_id = j;
                                    TLdeserialize.seq_in = b3;
                                    TLdeserialize.seq_out = b4;
                                    TLdeserialize.ttl = b2.m12154b(4);
                                } else {
                                    TLdeserialize = SecretChatHelper.this.createDeleteMessage(b5, b4, b3, d, encryptedChat);
                                }
                                arrayList.add(TLdeserialize);
                                hashMap.remove(Integer.valueOf(b4));
                            }
                            b2.m12155b();
                            if (!hashMap.isEmpty()) {
                                for (Entry key : hashMap.entrySet()) {
                                    arrayList.add(SecretChatHelper.this.createDeleteMessage(UserConfig.getNewMessageId(), ((Integer) key.getKey()).intValue(), 0, Utilities.random.nextLong(), encryptedChat));
                                }
                                UserConfig.saveConfig(false);
                            }
                            Collections.sort(arrayList, new C33571());
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(encryptedChat);
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    for (int i = 0; i < arrayList.size(); i++) {
                                        MessageObject messageObject = new MessageObject((Message) arrayList.get(i), null, false);
                                        messageObject.resendAsIs = true;
                                        SendMessagesHelper.getInstance().retrySendMessage(messageObject, true);
                                    }
                                }
                            });
                            SendMessagesHelper.getInstance().processUnsentMessages(arrayList, new ArrayList(), new ArrayList(), arrayList2);
                            MessagesStorage.getInstance().getDatabase().m12164a(String.format(Locale.US, "REPLACE INTO requested_holes VALUES(%d, %d, %d)", new Object[]{Integer.valueOf(encryptedChat.id), Integer.valueOf(i), Integer.valueOf(i2)})).m12179c().m12181e();
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    private void updateMediaPaths(MessageObject messageObject, EncryptedFile encryptedFile, DecryptedMessage decryptedMessage, String str) {
        Message message = messageObject.messageOwner;
        if (encryptedFile == null) {
            return;
        }
        ArrayList arrayList;
        if ((message.media instanceof TLRPC$TL_messageMediaPhoto) && message.media.photo != null) {
            PhotoSize photoSize = (PhotoSize) message.media.photo.sizes.get(message.media.photo.sizes.size() - 1);
            String str2 = photoSize.location.volume_id + "_" + photoSize.location.local_id;
            photoSize.location = new TLRPC$TL_fileEncryptedLocation();
            photoSize.location.key = decryptedMessage.media.key;
            photoSize.location.iv = decryptedMessage.media.iv;
            photoSize.location.dc_id = encryptedFile.dc_id;
            photoSize.location.volume_id = encryptedFile.id;
            photoSize.location.secret = encryptedFile.access_hash;
            photoSize.location.local_id = encryptedFile.key_fingerprint;
            String str3 = photoSize.location.volume_id + "_" + photoSize.location.local_id;
            new File(FileLoader.getInstance().getDirectory(4), str2 + ".jpg").renameTo(FileLoader.getPathToAttach(photoSize));
            ImageLoader.getInstance().replaceImageInCache(str2, str3, photoSize.location, true);
            arrayList = new ArrayList();
            arrayList.add(message);
            MessagesStorage.getInstance().putMessages(arrayList, false, true, false, 0);
        } else if ((message.media instanceof TLRPC$TL_messageMediaDocument) && message.media.document != null) {
            Document document = message.media.document;
            message.media.document = new TLRPC$TL_documentEncrypted();
            message.media.document.id = encryptedFile.id;
            message.media.document.access_hash = encryptedFile.access_hash;
            message.media.document.date = document.date;
            message.media.document.attributes = document.attributes;
            message.media.document.mime_type = document.mime_type;
            message.media.document.size = encryptedFile.size;
            message.media.document.key = decryptedMessage.media.key;
            message.media.document.iv = decryptedMessage.media.iv;
            message.media.document.thumb = document.thumb;
            message.media.document.dc_id = encryptedFile.dc_id;
            message.media.document.caption = document.caption != null ? document.caption : TtmlNode.ANONYMOUS_REGION_ID;
            if (message.attachPath != null && message.attachPath.startsWith(FileLoader.getInstance().getDirectory(4).getAbsolutePath()) && new File(message.attachPath).renameTo(FileLoader.getPathToAttach(message.media.document))) {
                messageObject.mediaExists = messageObject.attachPathExists;
                messageObject.attachPathExists = false;
                message.attachPath = TtmlNode.ANONYMOUS_REGION_ID;
            }
            arrayList = new ArrayList();
            arrayList.add(message);
            MessagesStorage.getInstance().putMessages(arrayList, false, true, false, 0);
        }
    }

    public void acceptSecretChat(final EncryptedChat encryptedChat) {
        if (this.acceptingChats.get(Integer.valueOf(encryptedChat.id)) == null) {
            this.acceptingChats.put(Integer.valueOf(encryptedChat.id), encryptedChat);
            TLObject tLRPC$TL_messages_getDhConfig = new TLRPC$TL_messages_getDhConfig();
            tLRPC$TL_messages_getDhConfig.random_length = 256;
            tLRPC$TL_messages_getDhConfig.version = MessagesStorage.lastSecretVersion;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getDhConfig, new RequestDelegate() {

                /* renamed from: org.telegram.messenger.SecretChatHelper$13$1 */
                class C33381 implements RequestDelegate {
                    C33381() {
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        SecretChatHelper.this.acceptingChats.remove(Integer.valueOf(encryptedChat.id));
                        if (tLRPC$TL_error == null) {
                            final EncryptedChat encryptedChat = (EncryptedChat) tLObject;
                            encryptedChat.auth_key = encryptedChat.auth_key;
                            encryptedChat.user_id = encryptedChat.user_id;
                            encryptedChat.seq_in = encryptedChat.seq_in;
                            encryptedChat.seq_out = encryptedChat.seq_out;
                            encryptedChat.key_create_date = encryptedChat.key_create_date;
                            encryptedChat.key_use_count_in = encryptedChat.key_use_count_in;
                            encryptedChat.key_use_count_out = encryptedChat.key_use_count_out;
                            MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                            MessagesController.getInstance().putEncryptedChat(encryptedChat, false);
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, encryptedChat);
                                    SecretChatHelper.this.sendNotifyLayerMessage(encryptedChat, null);
                                }
                            });
                        }
                    }
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        int i;
                        TLRPC$messages_DhConfig tLRPC$messages_DhConfig = (TLRPC$messages_DhConfig) tLObject;
                        if (tLObject instanceof TLRPC$TL_messages_dhConfig) {
                            if (Utilities.isGoodPrime(tLRPC$messages_DhConfig.f10165p, tLRPC$messages_DhConfig.f10164g)) {
                                MessagesStorage.secretPBytes = tLRPC$messages_DhConfig.f10165p;
                                MessagesStorage.secretG = tLRPC$messages_DhConfig.f10164g;
                                MessagesStorage.lastSecretVersion = tLRPC$messages_DhConfig.version;
                                MessagesStorage.getInstance().saveSecretParams(MessagesStorage.lastSecretVersion, MessagesStorage.secretG, MessagesStorage.secretPBytes);
                            } else {
                                SecretChatHelper.this.acceptingChats.remove(Integer.valueOf(encryptedChat.id));
                                SecretChatHelper.this.declineSecretChat(encryptedChat.id);
                                return;
                            }
                        }
                        byte[] bArr = new byte[256];
                        for (i = 0; i < 256; i++) {
                            bArr[i] = (byte) (((byte) ((int) (Utilities.random.nextDouble() * 256.0d))) ^ tLRPC$messages_DhConfig.random[i]);
                        }
                        encryptedChat.a_or_b = bArr;
                        encryptedChat.seq_in = -1;
                        encryptedChat.seq_out = 0;
                        BigInteger bigInteger = new BigInteger(1, MessagesStorage.secretPBytes);
                        BigInteger modPow = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, bArr), bigInteger);
                        BigInteger bigInteger2 = new BigInteger(1, encryptedChat.g_a);
                        if (Utilities.isGoodGaAndGb(bigInteger2, bigInteger)) {
                            byte[] bArr2;
                            byte[] bArr3;
                            Object obj;
                            Object toByteArray = modPow.toByteArray();
                            if (toByteArray.length > 256) {
                                bArr2 = new byte[256];
                                System.arraycopy(toByteArray, 1, bArr2, 0, 256);
                            } else {
                                Object obj2 = toByteArray;
                            }
                            Object toByteArray2 = bigInteger2.modPow(new BigInteger(1, bArr), bigInteger).toByteArray();
                            if (toByteArray2.length > 256) {
                                bArr3 = new byte[256];
                                System.arraycopy(toByteArray2, toByteArray2.length - 256, bArr3, 0, 256);
                            } else if (toByteArray2.length < 256) {
                                obj = new byte[256];
                                System.arraycopy(toByteArray2, 0, obj, 256 - toByteArray2.length, toByteArray2.length);
                                for (i = 0; i < 256 - toByteArray2.length; i++) {
                                    toByteArray2[i] = null;
                                }
                                toByteArray = obj;
                            } else {
                                toByteArray = toByteArray2;
                            }
                            obj = Utilities.computeSHA1(bArr3);
                            toByteArray2 = new byte[8];
                            System.arraycopy(obj, obj.length - 8, toByteArray2, 0, 8);
                            encryptedChat.auth_key = bArr3;
                            encryptedChat.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                            TLObject tLRPC$TL_messages_acceptEncryption = new TLRPC$TL_messages_acceptEncryption();
                            tLRPC$TL_messages_acceptEncryption.g_b = bArr2;
                            tLRPC$TL_messages_acceptEncryption.peer = new TLRPC$TL_inputEncryptedChat();
                            tLRPC$TL_messages_acceptEncryption.peer.chat_id = encryptedChat.id;
                            tLRPC$TL_messages_acceptEncryption.peer.access_hash = encryptedChat.access_hash;
                            tLRPC$TL_messages_acceptEncryption.key_fingerprint = Utilities.bytesToLong(toByteArray2);
                            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_acceptEncryption, new C33381());
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

    public void checkSecretHoles(EncryptedChat encryptedChat, ArrayList<Message> arrayList) {
        ArrayList arrayList2 = (ArrayList) this.secretHolesQueue.get(Integer.valueOf(encryptedChat.id));
        if (arrayList2 != null) {
            Collections.sort(arrayList2, new C33608());
            int i = 0;
            while (arrayList2.size() > 0) {
                TL_decryptedMessageHolder tL_decryptedMessageHolder = (TL_decryptedMessageHolder) arrayList2.get(0);
                if (tL_decryptedMessageHolder.layer.out_seq_no != encryptedChat.seq_in && encryptedChat.seq_in != tL_decryptedMessageHolder.layer.out_seq_no - 2) {
                    break;
                }
                applyPeerLayer(encryptedChat, tL_decryptedMessageHolder.layer.layer);
                encryptedChat.seq_in = tL_decryptedMessageHolder.layer.out_seq_no;
                encryptedChat.in_seq_no = tL_decryptedMessageHolder.layer.in_seq_no;
                arrayList2.remove(0);
                if (tL_decryptedMessageHolder.decryptedWithVersion == 2) {
                    encryptedChat.mtproto_seq = Math.min(encryptedChat.mtproto_seq, encryptedChat.seq_in);
                }
                Message processDecryptedObject = processDecryptedObject(encryptedChat, tL_decryptedMessageHolder.file, tL_decryptedMessageHolder.date, tL_decryptedMessageHolder.layer.message, tL_decryptedMessageHolder.new_key_used);
                if (processDecryptedObject != null) {
                    arrayList.add(processDecryptedObject);
                }
                boolean z = true;
            }
            if (arrayList2.isEmpty()) {
                this.secretHolesQueue.remove(Integer.valueOf(encryptedChat.id));
            }
            if (i != 0) {
                MessagesStorage.getInstance().updateEncryptedChatSeq(encryptedChat, true);
            }
        }
    }

    public void cleanup() {
        this.sendingNotifyLayer.clear();
        this.acceptingChats.clear();
        this.secretHolesQueue.clear();
        this.delayedEncryptedChatUpdates.clear();
        this.pendingEncMessagesToDelete.clear();
        this.startingSecretChat = false;
    }

    public void declineSecretChat(int i) {
        TLObject tLRPC$TL_messages_discardEncryption = new TLRPC$TL_messages_discardEncryption();
        tLRPC$TL_messages_discardEncryption.chat_id = i;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_discardEncryption, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            }
        });
    }

    protected ArrayList<Message> decryptMessage(EncryptedMessage encryptedMessage) {
        boolean z = false;
        EncryptedChat encryptedChatDB = MessagesController.getInstance().getEncryptedChatDB(encryptedMessage.chat_id, true);
        if (encryptedChatDB == null || (encryptedChatDB instanceof TLRPC$TL_encryptedChatDiscarded)) {
            return null;
        }
        try {
            byte[] bArr;
            boolean z2;
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(encryptedMessage.bytes.length);
            nativeByteBuffer.writeBytes(encryptedMessage.bytes);
            nativeByteBuffer.position(0);
            long readInt64 = nativeByteBuffer.readInt64(false);
            if (encryptedChatDB.key_fingerprint == readInt64) {
                bArr = encryptedChatDB.auth_key;
                z2 = false;
            } else if (encryptedChatDB.future_key_fingerprint == 0 || encryptedChatDB.future_key_fingerprint != readInt64) {
                z2 = false;
                bArr = null;
            } else {
                bArr = encryptedChatDB.future_auth_key;
                z2 = true;
            }
            int i = AndroidUtilities.getPeerLayerVersion(encryptedChatDB.layer) >= 73 ? 2 : 1;
            if (bArr != null) {
                TLObject tLObject;
                byte[] readData = nativeByteBuffer.readData(16, false);
                boolean z3 = encryptedChatDB.admin_id == UserConfig.getClientUserId();
                if (i != 2 || encryptedChatDB.mtproto_seq == 0) {
                    z = true;
                }
                if (!decryptWithMtProtoVersion(nativeByteBuffer, bArr, readData, i, z3, z)) {
                    if (i == 2) {
                        if (!z || !decryptWithMtProtoVersion(nativeByteBuffer, bArr, readData, 1, z3, false)) {
                            return null;
                        }
                        i = 1;
                    } else if (!decryptWithMtProtoVersion(nativeByteBuffer, bArr, readData, 2, z3, z)) {
                        return null;
                    } else {
                        i = 2;
                    }
                }
                TLObject TLdeserialize = TLClassStore.Instance().TLdeserialize(nativeByteBuffer, nativeByteBuffer.readInt32(false), false);
                nativeByteBuffer.reuse();
                if (!z2 && AndroidUtilities.getPeerLayerVersion(encryptedChatDB.layer) >= 20) {
                    encryptedChatDB.key_use_count_in = (short) (encryptedChatDB.key_use_count_in + 1);
                }
                if (TLdeserialize instanceof TLRPC$TL_decryptedMessageLayer) {
                    TLRPC$TL_decryptedMessageLayer tLRPC$TL_decryptedMessageLayer = (TLRPC$TL_decryptedMessageLayer) TLdeserialize;
                    if (encryptedChatDB.seq_in == 0 && encryptedChatDB.seq_out == 0) {
                        if (encryptedChatDB.admin_id == UserConfig.getClientUserId()) {
                            encryptedChatDB.seq_out = 1;
                            encryptedChatDB.seq_in = -2;
                        } else {
                            encryptedChatDB.seq_in = -1;
                        }
                    }
                    if (tLRPC$TL_decryptedMessageLayer.random_bytes.length < 15) {
                        FileLog.m13726e("got random bytes less than needed");
                        return null;
                    }
                    FileLog.m13726e("current chat in_seq = " + encryptedChatDB.seq_in + " out_seq = " + encryptedChatDB.seq_out);
                    FileLog.m13726e("got message with in_seq = " + tLRPC$TL_decryptedMessageLayer.in_seq_no + " out_seq = " + tLRPC$TL_decryptedMessageLayer.out_seq_no);
                    if (tLRPC$TL_decryptedMessageLayer.out_seq_no <= encryptedChatDB.seq_in) {
                        return null;
                    }
                    if (i == 1 && encryptedChatDB.mtproto_seq != 0 && tLRPC$TL_decryptedMessageLayer.out_seq_no >= encryptedChatDB.mtproto_seq) {
                        return null;
                    }
                    if (encryptedChatDB.seq_in != tLRPC$TL_decryptedMessageLayer.out_seq_no - 2) {
                        FileLog.m13726e("got hole");
                        ArrayList arrayList = (ArrayList) this.secretHolesQueue.get(Integer.valueOf(encryptedChatDB.id));
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            this.secretHolesQueue.put(Integer.valueOf(encryptedChatDB.id), arrayList);
                        }
                        if (arrayList.size() >= 4) {
                            this.secretHolesQueue.remove(Integer.valueOf(encryptedChatDB.id));
                            final TLRPC$TL_encryptedChatDiscarded tLRPC$TL_encryptedChatDiscarded = new TLRPC$TL_encryptedChatDiscarded();
                            tLRPC$TL_encryptedChatDiscarded.id = encryptedChatDB.id;
                            tLRPC$TL_encryptedChatDiscarded.user_id = encryptedChatDB.user_id;
                            tLRPC$TL_encryptedChatDiscarded.auth_key = encryptedChatDB.auth_key;
                            tLRPC$TL_encryptedChatDiscarded.key_create_date = encryptedChatDB.key_create_date;
                            tLRPC$TL_encryptedChatDiscarded.key_use_count_in = encryptedChatDB.key_use_count_in;
                            tLRPC$TL_encryptedChatDiscarded.key_use_count_out = encryptedChatDB.key_use_count_out;
                            tLRPC$TL_encryptedChatDiscarded.seq_in = encryptedChatDB.seq_in;
                            tLRPC$TL_encryptedChatDiscarded.seq_out = encryptedChatDB.seq_out;
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    MessagesController.getInstance().putEncryptedChat(tLRPC$TL_encryptedChatDiscarded, false);
                                    MessagesStorage.getInstance().updateEncryptedChat(tLRPC$TL_encryptedChatDiscarded);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, tLRPC$TL_encryptedChatDiscarded);
                                }
                            });
                            declineSecretChat(encryptedChatDB.id);
                            return null;
                        }
                        TL_decryptedMessageHolder tL_decryptedMessageHolder = new TL_decryptedMessageHolder();
                        tL_decryptedMessageHolder.layer = tLRPC$TL_decryptedMessageLayer;
                        tL_decryptedMessageHolder.file = encryptedMessage.file;
                        tL_decryptedMessageHolder.date = encryptedMessage.date;
                        tL_decryptedMessageHolder.new_key_used = z2;
                        tL_decryptedMessageHolder.decryptedWithVersion = i;
                        arrayList.add(tL_decryptedMessageHolder);
                        return null;
                    }
                    if (i == 2) {
                        encryptedChatDB.mtproto_seq = Math.min(encryptedChatDB.mtproto_seq, encryptedChatDB.seq_in);
                    }
                    applyPeerLayer(encryptedChatDB, tLRPC$TL_decryptedMessageLayer.layer);
                    encryptedChatDB.seq_in = tLRPC$TL_decryptedMessageLayer.out_seq_no;
                    encryptedChatDB.in_seq_no = tLRPC$TL_decryptedMessageLayer.in_seq_no;
                    MessagesStorage.getInstance().updateEncryptedChatSeq(encryptedChatDB, true);
                    tLObject = tLRPC$TL_decryptedMessageLayer.message;
                } else if (!(TLdeserialize instanceof TLRPC$TL_decryptedMessageService) || !(((TLRPC$TL_decryptedMessageService) TLdeserialize).action instanceof TLRPC$TL_decryptedMessageActionNotifyLayer)) {
                    return null;
                } else {
                    tLObject = TLdeserialize;
                }
                ArrayList<Message> arrayList2 = new ArrayList();
                Message processDecryptedObject = processDecryptedObject(encryptedChatDB, encryptedMessage.file, encryptedMessage.date, tLObject, z2);
                if (processDecryptedObject != null) {
                    arrayList2.add(processDecryptedObject);
                }
                checkSecretHoles(encryptedChatDB, arrayList2);
                return arrayList2;
            }
            nativeByteBuffer.reuse();
            FileLog.m13726e(String.format("fingerprint mismatch %x", new Object[]{Long.valueOf(readInt64)}));
            return null;
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    protected void performSendEncryptedRequest(DecryptedMessage decryptedMessage, Message message, EncryptedChat encryptedChat, InputEncryptedFile inputEncryptedFile, String str, MessageObject messageObject) {
        if (decryptedMessage != null && encryptedChat.auth_key != null && !(encryptedChat instanceof TLRPC$TL_encryptedChatRequested) && !(encryptedChat instanceof TLRPC$TL_encryptedChatWaiting)) {
            SendMessagesHelper.getInstance().putToSendingMessages(message);
            final EncryptedChat encryptedChat2 = encryptedChat;
            final DecryptedMessage decryptedMessage2 = decryptedMessage;
            final Message message2 = message;
            final InputEncryptedFile inputEncryptedFile2 = inputEncryptedFile;
            final MessageObject messageObject2 = messageObject;
            final String str2 = str;
            Utilities.stageQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.SecretChatHelper$4$1 */
                class C33511 implements RequestDelegate {

                    /* renamed from: org.telegram.messenger.SecretChatHelper$4$1$2 */
                    class C33502 implements Runnable {
                        C33502() {
                        }

                        public void run() {
                            message2.send_state = 2;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageSendError, Integer.valueOf(message2.id));
                            SendMessagesHelper.getInstance().processSentMessage(message2.id);
                            if (MessageObject.isVideoMessage(message2) || MessageObject.isNewGifMessage(message2) || MessageObject.isRoundVideoMessage(message2)) {
                                SendMessagesHelper.getInstance().stopVideoService(message2.attachPath);
                            }
                            SendMessagesHelper.getInstance().removeFromSendingMessages(message2.id);
                        }
                    }

                    C33511() {
                    }

                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLRPC$TL_error == null && (decryptedMessage2.action instanceof TLRPC$TL_decryptedMessageActionNotifyLayer)) {
                            EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(encryptedChat2.id));
                            if (encryptedChat == null) {
                                encryptedChat = encryptedChat2;
                            }
                            if (encryptedChat.key_hash == null) {
                                encryptedChat.key_hash = AndroidUtilities.calcAuthKeyHash(encryptedChat.auth_key);
                            }
                            if (AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 46 && encryptedChat.key_hash.length == 16) {
                                try {
                                    Object computeSHA256 = Utilities.computeSHA256(encryptedChat2.auth_key, 0, encryptedChat2.auth_key.length);
                                    Object obj = new byte[36];
                                    System.arraycopy(encryptedChat2.key_hash, 0, obj, 0, 16);
                                    System.arraycopy(computeSHA256, 0, obj, 16, 20);
                                    encryptedChat.key_hash = obj;
                                    MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                                } catch (Throwable th) {
                                    FileLog.m13728e(th);
                                }
                            }
                            SecretChatHelper.this.sendingNotifyLayer.remove(Integer.valueOf(encryptedChat.id));
                            encryptedChat.layer = AndroidUtilities.setMyLayerVersion(encryptedChat.layer, 73);
                            MessagesStorage.getInstance().updateEncryptedChatLayer(encryptedChat);
                        }
                        if (message2 == null) {
                            return;
                        }
                        if (tLRPC$TL_error == null) {
                            final String str = message2.attachPath;
                            final TLRPC$messages_SentEncryptedMessage tLRPC$messages_SentEncryptedMessage = (TLRPC$messages_SentEncryptedMessage) tLObject;
                            if (SecretChatHelper.isSecretVisibleMessage(message2)) {
                                message2.date = tLRPC$messages_SentEncryptedMessage.date;
                            }
                            if (messageObject2 != null && (tLRPC$messages_SentEncryptedMessage.file instanceof TLRPC$TL_encryptedFile)) {
                                SecretChatHelper.this.updateMediaPaths(messageObject2, tLRPC$messages_SentEncryptedMessage.file, decryptedMessage2, str2);
                            }
                            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                                /* renamed from: org.telegram.messenger.SecretChatHelper$4$1$1$1 */
                                class C33481 implements Runnable {
                                    C33481() {
                                    }

                                    public void run() {
                                        message2.send_state = 0;
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageReceivedByServer, Integer.valueOf(message2.id), Integer.valueOf(message2.id), message2, Long.valueOf(message2.dialog_id));
                                        SendMessagesHelper.getInstance().processSentMessage(message2.id);
                                        if (MessageObject.isVideoMessage(message2) || MessageObject.isNewGifMessage(message2) || MessageObject.isRoundVideoMessage(message2)) {
                                            SendMessagesHelper.getInstance().stopVideoService(str);
                                        }
                                        SendMessagesHelper.getInstance().removeFromSendingMessages(message2.id);
                                    }
                                }

                                public void run() {
                                    if (SecretChatHelper.isSecretInvisibleMessage(message2)) {
                                        tLRPC$messages_SentEncryptedMessage.date = 0;
                                    }
                                    MessagesStorage.getInstance().updateMessageStateAndId(message2.random_id, Integer.valueOf(message2.id), message2.id, tLRPC$messages_SentEncryptedMessage.date, false, 0);
                                    AndroidUtilities.runOnUIThread(new C33481());
                                }
                            });
                            return;
                        }
                        MessagesStorage.getInstance().markMessageAsSendError(message2);
                        AndroidUtilities.runOnUIThread(new C33502());
                    }
                }

                public void run() {
                    int i = 8;
                    try {
                        TLObject tLRPC$TL_messages_sendEncryptedFile;
                        TLObject tLRPC$TL_decryptedMessageLayer = new TLRPC$TL_decryptedMessageLayer();
                        tLRPC$TL_decryptedMessageLayer.layer = Math.min(Math.max(46, AndroidUtilities.getMyLayerVersion(encryptedChat2.layer)), Math.max(46, AndroidUtilities.getPeerLayerVersion(encryptedChat2.layer)));
                        tLRPC$TL_decryptedMessageLayer.message = decryptedMessage2;
                        tLRPC$TL_decryptedMessageLayer.random_bytes = new byte[15];
                        Utilities.random.nextBytes(tLRPC$TL_decryptedMessageLayer.random_bytes);
                        int i2 = AndroidUtilities.getPeerLayerVersion(encryptedChat2.layer) >= 73 ? 2 : 1;
                        if (encryptedChat2.seq_in == 0 && encryptedChat2.seq_out == 0) {
                            if (encryptedChat2.admin_id == UserConfig.getClientUserId()) {
                                encryptedChat2.seq_out = 1;
                                encryptedChat2.seq_in = -2;
                            } else {
                                encryptedChat2.seq_in = -1;
                            }
                        }
                        if (message2.seq_in == 0 && message2.seq_out == 0) {
                            tLRPC$TL_decryptedMessageLayer.in_seq_no = encryptedChat2.seq_in > 0 ? encryptedChat2.seq_in : encryptedChat2.seq_in + 2;
                            tLRPC$TL_decryptedMessageLayer.out_seq_no = encryptedChat2.seq_out;
                            EncryptedChat encryptedChat = encryptedChat2;
                            encryptedChat.seq_out += 2;
                            if (AndroidUtilities.getPeerLayerVersion(encryptedChat2.layer) >= 20) {
                                if (encryptedChat2.key_create_date == 0) {
                                    encryptedChat2.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                                }
                                encryptedChat = encryptedChat2;
                                encryptedChat.key_use_count_out = (short) (encryptedChat.key_use_count_out + 1);
                                if ((encryptedChat2.key_use_count_out >= (short) 100 || encryptedChat2.key_create_date < ConnectionsManager.getInstance().getCurrentTime() - 604800) && encryptedChat2.exchange_id == 0 && encryptedChat2.future_key_fingerprint == 0) {
                                    SecretChatHelper.this.requestNewSecretChatKey(encryptedChat2);
                                }
                            }
                            MessagesStorage.getInstance().updateEncryptedChatSeq(encryptedChat2, false);
                            if (message2 != null) {
                                message2.seq_in = tLRPC$TL_decryptedMessageLayer.in_seq_no;
                                message2.seq_out = tLRPC$TL_decryptedMessageLayer.out_seq_no;
                                MessagesStorage.getInstance().setMessageSeq(message2.id, message2.seq_in, message2.seq_out);
                            }
                        } else {
                            tLRPC$TL_decryptedMessageLayer.in_seq_no = message2.seq_in;
                            tLRPC$TL_decryptedMessageLayer.out_seq_no = message2.seq_out;
                        }
                        FileLog.m13726e(decryptedMessage2 + " send message with in_seq = " + tLRPC$TL_decryptedMessageLayer.in_seq_no + " out_seq = " + tLRPC$TL_decryptedMessageLayer.out_seq_no);
                        int objectSize = tLRPC$TL_decryptedMessageLayer.getObjectSize();
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(objectSize + 4);
                        nativeByteBuffer.writeInt32(objectSize);
                        tLRPC$TL_decryptedMessageLayer.serializeToStream(nativeByteBuffer);
                        int length = nativeByteBuffer.length();
                        objectSize = length % 16 != 0 ? 16 - (length % 16) : 0;
                        if (i2 == 2) {
                            objectSize += (Utilities.random.nextInt(3) + 2) * 16;
                        }
                        NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(length + objectSize);
                        nativeByteBuffer.position(0);
                        nativeByteBuffer2.writeBytes(nativeByteBuffer);
                        if (objectSize != 0) {
                            byte[] bArr = new byte[objectSize];
                            Utilities.random.nextBytes(bArr);
                            nativeByteBuffer2.writeBytes(bArr);
                        }
                        byte[] bArr2 = new byte[16];
                        boolean z = i2 == 2 && encryptedChat2.admin_id != UserConfig.getClientUserId();
                        if (i2 == 2) {
                            byte[] bArr3 = encryptedChat2.auth_key;
                            if (!z) {
                                i = 0;
                            }
                            System.arraycopy(Utilities.computeSHA256(bArr3, i + 88, 32, nativeByteBuffer2.buffer, 0, nativeByteBuffer2.buffer.limit()), 8, bArr2, 0, 16);
                        } else {
                            Object computeSHA1 = Utilities.computeSHA1(nativeByteBuffer.buffer);
                            System.arraycopy(computeSHA1, computeSHA1.length - 16, bArr2, 0, 16);
                        }
                        nativeByteBuffer.reuse();
                        MessageKeyData generateMessageKeyData = MessageKeyData.generateMessageKeyData(encryptedChat2.auth_key, bArr2, z, i2);
                        Utilities.aesIgeEncryption(nativeByteBuffer2.buffer, generateMessageKeyData.aesKey, generateMessageKeyData.aesIv, true, false, 0, nativeByteBuffer2.limit());
                        NativeByteBuffer nativeByteBuffer3 = new NativeByteBuffer((bArr2.length + 8) + nativeByteBuffer2.length());
                        nativeByteBuffer2.position(0);
                        nativeByteBuffer3.writeInt64(encryptedChat2.key_fingerprint);
                        nativeByteBuffer3.writeBytes(bArr2);
                        nativeByteBuffer3.writeBytes(nativeByteBuffer2);
                        nativeByteBuffer2.reuse();
                        nativeByteBuffer3.position(0);
                        if (inputEncryptedFile2 != null) {
                            tLRPC$TL_messages_sendEncryptedFile = new TLRPC$TL_messages_sendEncryptedFile();
                            tLRPC$TL_messages_sendEncryptedFile.data = nativeByteBuffer3;
                            tLRPC$TL_messages_sendEncryptedFile.random_id = decryptedMessage2.random_id;
                            tLRPC$TL_messages_sendEncryptedFile.peer = new TLRPC$TL_inputEncryptedChat();
                            tLRPC$TL_messages_sendEncryptedFile.peer.chat_id = encryptedChat2.id;
                            tLRPC$TL_messages_sendEncryptedFile.peer.access_hash = encryptedChat2.access_hash;
                            tLRPC$TL_messages_sendEncryptedFile.file = inputEncryptedFile2;
                        } else if (decryptedMessage2 instanceof TLRPC$TL_decryptedMessageService) {
                            tLRPC$TL_messages_sendEncryptedFile = new TLRPC$TL_messages_sendEncryptedService();
                            tLRPC$TL_messages_sendEncryptedFile.data = nativeByteBuffer3;
                            tLRPC$TL_messages_sendEncryptedFile.random_id = decryptedMessage2.random_id;
                            tLRPC$TL_messages_sendEncryptedFile.peer = new TLRPC$TL_inputEncryptedChat();
                            tLRPC$TL_messages_sendEncryptedFile.peer.chat_id = encryptedChat2.id;
                            tLRPC$TL_messages_sendEncryptedFile.peer.access_hash = encryptedChat2.access_hash;
                        } else {
                            tLRPC$TL_messages_sendEncryptedFile = new TLRPC$TL_messages_sendEncrypted();
                            tLRPC$TL_messages_sendEncryptedFile.data = nativeByteBuffer3;
                            tLRPC$TL_messages_sendEncryptedFile.random_id = decryptedMessage2.random_id;
                            tLRPC$TL_messages_sendEncryptedFile.peer = new TLRPC$TL_inputEncryptedChat();
                            tLRPC$TL_messages_sendEncryptedFile.peer.chat_id = encryptedChat2.id;
                            tLRPC$TL_messages_sendEncryptedFile.peer.access_hash = encryptedChat2.access_hash;
                        }
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_sendEncryptedFile, new C33511(), 64);
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
    }

    protected void performSendEncryptedRequest(TLRPC$TL_messages_sendEncryptedMultiMedia tLRPC$TL_messages_sendEncryptedMultiMedia, DelayedMessage delayedMessage) {
        for (int i = 0; i < tLRPC$TL_messages_sendEncryptedMultiMedia.files.size(); i++) {
            performSendEncryptedRequest((DecryptedMessage) tLRPC$TL_messages_sendEncryptedMultiMedia.messages.get(i), (Message) delayedMessage.messages.get(i), delayedMessage.encryptedChat, (InputEncryptedFile) tLRPC$TL_messages_sendEncryptedMultiMedia.files.get(i), (String) delayedMessage.originalPaths.get(i), (MessageObject) delayedMessage.messageObjects.get(i));
        }
    }

    public void processAcceptedSecretChat(final EncryptedChat encryptedChat) {
        BigInteger bigInteger = new BigInteger(1, MessagesStorage.secretPBytes);
        BigInteger bigInteger2 = new BigInteger(1, encryptedChat.g_a_or_b);
        if (Utilities.isGoodGaAndGb(bigInteger2, bigInteger)) {
            byte[] bArr;
            Object obj;
            Object toByteArray = bigInteger2.modPow(new BigInteger(1, encryptedChat.a_or_b), bigInteger).toByteArray();
            if (toByteArray.length > 256) {
                bArr = new byte[256];
                System.arraycopy(toByteArray, toByteArray.length - 256, bArr, 0, 256);
            } else if (toByteArray.length < 256) {
                obj = new byte[256];
                System.arraycopy(toByteArray, 0, obj, 256 - toByteArray.length, toByteArray.length);
                for (int i = 0; i < 256 - toByteArray.length; i++) {
                    toByteArray[i] = null;
                }
                r0 = obj;
            } else {
                r0 = toByteArray;
            }
            obj = Utilities.computeSHA1(bArr);
            toByteArray = new byte[8];
            System.arraycopy(obj, obj.length - 8, toByteArray, 0, 8);
            if (encryptedChat.key_fingerprint == Utilities.bytesToLong(toByteArray)) {
                encryptedChat.auth_key = bArr;
                encryptedChat.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                encryptedChat.seq_in = -2;
                encryptedChat.seq_out = 1;
                MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                MessagesController.getInstance().putEncryptedChat(encryptedChat, false);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, encryptedChat);
                        SecretChatHelper.this.sendNotifyLayerMessage(encryptedChat, null);
                    }
                });
                return;
            }
            final EncryptedChat tLRPC$TL_encryptedChatDiscarded = new TLRPC$TL_encryptedChatDiscarded();
            tLRPC$TL_encryptedChatDiscarded.id = encryptedChat.id;
            tLRPC$TL_encryptedChatDiscarded.user_id = encryptedChat.user_id;
            tLRPC$TL_encryptedChatDiscarded.auth_key = encryptedChat.auth_key;
            tLRPC$TL_encryptedChatDiscarded.key_create_date = encryptedChat.key_create_date;
            tLRPC$TL_encryptedChatDiscarded.key_use_count_in = encryptedChat.key_use_count_in;
            tLRPC$TL_encryptedChatDiscarded.key_use_count_out = encryptedChat.key_use_count_out;
            tLRPC$TL_encryptedChatDiscarded.seq_in = encryptedChat.seq_in;
            tLRPC$TL_encryptedChatDiscarded.seq_out = encryptedChat.seq_out;
            tLRPC$TL_encryptedChatDiscarded.admin_id = encryptedChat.admin_id;
            tLRPC$TL_encryptedChatDiscarded.mtproto_seq = encryptedChat.mtproto_seq;
            MessagesStorage.getInstance().updateEncryptedChat(tLRPC$TL_encryptedChatDiscarded);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController.getInstance().putEncryptedChat(tLRPC$TL_encryptedChatDiscarded, false);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, tLRPC$TL_encryptedChatDiscarded);
                }
            });
            declineSecretChat(encryptedChat.id);
            return;
        }
        declineSecretChat(encryptedChat.id);
    }

    public Message processDecryptedObject(EncryptedChat encryptedChat, EncryptedFile encryptedFile, int i, TLObject tLObject, boolean z) {
        if (tLObject != null) {
            int i2 = encryptedChat.admin_id;
            if (i2 == UserConfig.getClientUserId()) {
                i2 = encryptedChat.participant_id;
            }
            if (AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 20 && encryptedChat.exchange_id == 0 && encryptedChat.future_key_fingerprint == 0 && encryptedChat.key_use_count_in >= (short) 120) {
                requestNewSecretChatKey(encryptedChat);
            }
            if (encryptedChat.exchange_id == 0 && encryptedChat.future_key_fingerprint != 0 && !z) {
                encryptedChat.future_auth_key = new byte[256];
                encryptedChat.future_key_fingerprint = 0;
                MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
            } else if (encryptedChat.exchange_id != 0 && z) {
                encryptedChat.key_fingerprint = encryptedChat.future_key_fingerprint;
                encryptedChat.auth_key = encryptedChat.future_auth_key;
                encryptedChat.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                encryptedChat.future_auth_key = new byte[256];
                encryptedChat.future_key_fingerprint = 0;
                encryptedChat.key_use_count_in = (short) 0;
                encryptedChat.key_use_count_out = (short) 0;
                encryptedChat.exchange_id = 0;
                MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
            }
            Message tLRPC$TL_message_secret;
            int newMessageId;
            byte[] bArr;
            if (tLObject instanceof TLRPC$TL_decryptedMessage) {
                TLRPC$TL_decryptedMessage tLRPC$TL_decryptedMessage = (TLRPC$TL_decryptedMessage) tLObject;
                if (AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 17) {
                    tLRPC$TL_message_secret = new TLRPC$TL_message_secret();
                    tLRPC$TL_message_secret.ttl = tLRPC$TL_decryptedMessage.ttl;
                    tLRPC$TL_message_secret.entities = tLRPC$TL_decryptedMessage.entities;
                } else {
                    tLRPC$TL_message_secret = new TLRPC$TL_message();
                    tLRPC$TL_message_secret.ttl = encryptedChat.ttl;
                }
                tLRPC$TL_message_secret.message = tLRPC$TL_decryptedMessage.message;
                tLRPC$TL_message_secret.date = i;
                newMessageId = UserConfig.getNewMessageId();
                tLRPC$TL_message_secret.id = newMessageId;
                tLRPC$TL_message_secret.local_id = newMessageId;
                UserConfig.saveConfig(false);
                tLRPC$TL_message_secret.from_id = i2;
                tLRPC$TL_message_secret.to_id = new TLRPC$TL_peerUser();
                tLRPC$TL_message_secret.random_id = tLRPC$TL_decryptedMessage.random_id;
                tLRPC$TL_message_secret.to_id.user_id = UserConfig.getClientUserId();
                tLRPC$TL_message_secret.unread = true;
                tLRPC$TL_message_secret.flags = 768;
                if (tLRPC$TL_decryptedMessage.via_bot_name != null && tLRPC$TL_decryptedMessage.via_bot_name.length() > 0) {
                    tLRPC$TL_message_secret.via_bot_name = tLRPC$TL_decryptedMessage.via_bot_name;
                    tLRPC$TL_message_secret.flags |= 2048;
                }
                if (tLRPC$TL_decryptedMessage.grouped_id != 0) {
                    tLRPC$TL_message_secret.grouped_id = tLRPC$TL_decryptedMessage.grouped_id;
                    tLRPC$TL_message_secret.flags |= 131072;
                }
                tLRPC$TL_message_secret.dialog_id = ((long) encryptedChat.id) << 32;
                if (tLRPC$TL_decryptedMessage.reply_to_random_id != 0) {
                    tLRPC$TL_message_secret.reply_to_random_id = tLRPC$TL_decryptedMessage.reply_to_random_id;
                    tLRPC$TL_message_secret.flags |= 8;
                }
                if (tLRPC$TL_decryptedMessage.media == null || (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaEmpty)) {
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaEmpty();
                    return tLRPC$TL_message_secret;
                } else if (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaWebPage) {
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaWebPage();
                    tLRPC$TL_message_secret.media.webpage = new TLRPC$TL_webPageUrlPending();
                    tLRPC$TL_message_secret.media.webpage.url = tLRPC$TL_decryptedMessage.media.url;
                    return tLRPC$TL_message_secret;
                } else if (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaContact) {
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaContact();
                    tLRPC$TL_message_secret.media.last_name = tLRPC$TL_decryptedMessage.media.last_name;
                    tLRPC$TL_message_secret.media.first_name = tLRPC$TL_decryptedMessage.media.first_name;
                    tLRPC$TL_message_secret.media.phone_number = tLRPC$TL_decryptedMessage.media.phone_number;
                    tLRPC$TL_message_secret.media.user_id = tLRPC$TL_decryptedMessage.media.user_id;
                    return tLRPC$TL_message_secret;
                } else if (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaGeoPoint) {
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaGeo();
                    tLRPC$TL_message_secret.media.geo = new TLRPC$TL_geoPoint();
                    tLRPC$TL_message_secret.media.geo.lat = tLRPC$TL_decryptedMessage.media.lat;
                    tLRPC$TL_message_secret.media.geo._long = tLRPC$TL_decryptedMessage.media._long;
                    return tLRPC$TL_message_secret;
                } else if (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaPhoto) {
                    if (tLRPC$TL_decryptedMessage.media.key == null || tLRPC$TL_decryptedMessage.media.key.length != 32 || tLRPC$TL_decryptedMessage.media.iv == null || tLRPC$TL_decryptedMessage.media.iv.length != 32) {
                        return null;
                    }
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaPhoto();
                    r0 = tLRPC$TL_message_secret.media;
                    r0.flags |= 3;
                    tLRPC$TL_message_secret.media.caption = tLRPC$TL_decryptedMessage.media.caption != null ? tLRPC$TL_decryptedMessage.media.caption : TtmlNode.ANONYMOUS_REGION_ID;
                    tLRPC$TL_message_secret.media.photo = new TLRPC$TL_photo();
                    tLRPC$TL_message_secret.media.photo.date = tLRPC$TL_message_secret.date;
                    bArr = ((TLRPC$TL_decryptedMessageMediaPhoto) tLRPC$TL_decryptedMessage.media).thumb;
                    if (bArr != null && bArr.length != 0 && bArr.length <= 6000 && tLRPC$TL_decryptedMessage.media.thumb_w <= 100 && tLRPC$TL_decryptedMessage.media.thumb_h <= 100) {
                        TLRPC$TL_photoCachedSize tLRPC$TL_photoCachedSize = new TLRPC$TL_photoCachedSize();
                        tLRPC$TL_photoCachedSize.w = tLRPC$TL_decryptedMessage.media.thumb_w;
                        tLRPC$TL_photoCachedSize.h = tLRPC$TL_decryptedMessage.media.thumb_h;
                        tLRPC$TL_photoCachedSize.bytes = bArr;
                        tLRPC$TL_photoCachedSize.type = "s";
                        tLRPC$TL_photoCachedSize.location = new TLRPC$TL_fileLocationUnavailable();
                        tLRPC$TL_message_secret.media.photo.sizes.add(tLRPC$TL_photoCachedSize);
                    }
                    if (tLRPC$TL_message_secret.ttl != 0) {
                        tLRPC$TL_message_secret.media.ttl_seconds = tLRPC$TL_message_secret.ttl;
                        tLRPC$TL_message_secret.flags |= 4;
                    }
                    TLRPC$TL_photoSize tLRPC$TL_photoSize = new TLRPC$TL_photoSize();
                    tLRPC$TL_photoSize.w = tLRPC$TL_decryptedMessage.media.f10138w;
                    tLRPC$TL_photoSize.h = tLRPC$TL_decryptedMessage.media.f10137h;
                    tLRPC$TL_photoSize.type = "x";
                    tLRPC$TL_photoSize.size = encryptedFile.size;
                    tLRPC$TL_photoSize.location = new TLRPC$TL_fileEncryptedLocation();
                    tLRPC$TL_photoSize.location.key = tLRPC$TL_decryptedMessage.media.key;
                    tLRPC$TL_photoSize.location.iv = tLRPC$TL_decryptedMessage.media.iv;
                    tLRPC$TL_photoSize.location.dc_id = encryptedFile.dc_id;
                    tLRPC$TL_photoSize.location.volume_id = encryptedFile.id;
                    tLRPC$TL_photoSize.location.secret = encryptedFile.access_hash;
                    tLRPC$TL_photoSize.location.local_id = encryptedFile.key_fingerprint;
                    tLRPC$TL_message_secret.media.photo.sizes.add(tLRPC$TL_photoSize);
                    return tLRPC$TL_message_secret;
                } else if (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaVideo) {
                    if (tLRPC$TL_decryptedMessage.media.key == null || tLRPC$TL_decryptedMessage.media.key.length != 32 || tLRPC$TL_decryptedMessage.media.iv == null || tLRPC$TL_decryptedMessage.media.iv.length != 32) {
                        return null;
                    }
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaDocument();
                    r0 = tLRPC$TL_message_secret.media;
                    r0.flags |= 3;
                    tLRPC$TL_message_secret.media.document = new TLRPC$TL_documentEncrypted();
                    tLRPC$TL_message_secret.media.document.key = tLRPC$TL_decryptedMessage.media.key;
                    tLRPC$TL_message_secret.media.document.iv = tLRPC$TL_decryptedMessage.media.iv;
                    tLRPC$TL_message_secret.media.document.dc_id = encryptedFile.dc_id;
                    tLRPC$TL_message_secret.media.caption = tLRPC$TL_decryptedMessage.media.caption != null ? tLRPC$TL_decryptedMessage.media.caption : TtmlNode.ANONYMOUS_REGION_ID;
                    tLRPC$TL_message_secret.media.document.date = i;
                    tLRPC$TL_message_secret.media.document.size = encryptedFile.size;
                    tLRPC$TL_message_secret.media.document.id = encryptedFile.id;
                    tLRPC$TL_message_secret.media.document.access_hash = encryptedFile.access_hash;
                    tLRPC$TL_message_secret.media.document.mime_type = tLRPC$TL_decryptedMessage.media.mime_type;
                    if (tLRPC$TL_message_secret.media.document.mime_type == null) {
                        tLRPC$TL_message_secret.media.document.mime_type = MimeTypes.VIDEO_MP4;
                    }
                    bArr = ((TLRPC$TL_decryptedMessageMediaVideo) tLRPC$TL_decryptedMessage.media).thumb;
                    if (bArr == null || bArr.length == 0 || bArr.length > 6000 || tLRPC$TL_decryptedMessage.media.thumb_w > 100 || tLRPC$TL_decryptedMessage.media.thumb_h > 100) {
                        tLRPC$TL_message_secret.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
                        tLRPC$TL_message_secret.media.document.thumb.type = "s";
                    } else {
                        tLRPC$TL_message_secret.media.document.thumb = new TLRPC$TL_photoCachedSize();
                        tLRPC$TL_message_secret.media.document.thumb.bytes = bArr;
                        tLRPC$TL_message_secret.media.document.thumb.f10147w = tLRPC$TL_decryptedMessage.media.thumb_w;
                        tLRPC$TL_message_secret.media.document.thumb.f10146h = tLRPC$TL_decryptedMessage.media.thumb_h;
                        tLRPC$TL_message_secret.media.document.thumb.type = "s";
                        tLRPC$TL_message_secret.media.document.thumb.location = new TLRPC$TL_fileLocationUnavailable();
                    }
                    TLRPC$TL_documentAttributeVideo tLRPC$TL_documentAttributeVideo = new TLRPC$TL_documentAttributeVideo();
                    tLRPC$TL_documentAttributeVideo.w = tLRPC$TL_decryptedMessage.media.f10138w;
                    tLRPC$TL_documentAttributeVideo.h = tLRPC$TL_decryptedMessage.media.f10137h;
                    tLRPC$TL_documentAttributeVideo.duration = tLRPC$TL_decryptedMessage.media.duration;
                    tLRPC$TL_message_secret.media.document.attributes.add(tLRPC$TL_documentAttributeVideo);
                    if (tLRPC$TL_message_secret.ttl == 0) {
                        return tLRPC$TL_message_secret;
                    }
                    tLRPC$TL_message_secret.ttl = Math.max(tLRPC$TL_decryptedMessage.media.duration + 2, tLRPC$TL_message_secret.ttl);
                    tLRPC$TL_message_secret.media.ttl_seconds = tLRPC$TL_message_secret.ttl;
                    tLRPC$TL_message_secret.flags |= 4;
                    return tLRPC$TL_message_secret;
                } else if (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaDocument) {
                    if (tLRPC$TL_decryptedMessage.media.key == null || tLRPC$TL_decryptedMessage.media.key.length != 32 || tLRPC$TL_decryptedMessage.media.iv == null || tLRPC$TL_decryptedMessage.media.iv.length != 32) {
                        return null;
                    }
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaDocument();
                    r0 = tLRPC$TL_message_secret.media;
                    r0.flags |= 3;
                    tLRPC$TL_message_secret.media.caption = tLRPC$TL_decryptedMessage.media.caption != null ? tLRPC$TL_decryptedMessage.media.caption : TtmlNode.ANONYMOUS_REGION_ID;
                    tLRPC$TL_message_secret.media.document = new TLRPC$TL_documentEncrypted();
                    tLRPC$TL_message_secret.media.document.id = encryptedFile.id;
                    tLRPC$TL_message_secret.media.document.access_hash = encryptedFile.access_hash;
                    tLRPC$TL_message_secret.media.document.date = i;
                    if (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaDocument_layer8) {
                        TLRPC$TL_documentAttributeFilename tLRPC$TL_documentAttributeFilename = new TLRPC$TL_documentAttributeFilename();
                        tLRPC$TL_documentAttributeFilename.file_name = tLRPC$TL_decryptedMessage.media.file_name;
                        tLRPC$TL_message_secret.media.document.attributes.add(tLRPC$TL_documentAttributeFilename);
                    } else {
                        tLRPC$TL_message_secret.media.document.attributes = tLRPC$TL_decryptedMessage.media.attributes;
                    }
                    tLRPC$TL_message_secret.media.document.mime_type = tLRPC$TL_decryptedMessage.media.mime_type;
                    tLRPC$TL_message_secret.media.document.size = tLRPC$TL_decryptedMessage.media.size != 0 ? Math.min(tLRPC$TL_decryptedMessage.media.size, encryptedFile.size) : encryptedFile.size;
                    tLRPC$TL_message_secret.media.document.key = tLRPC$TL_decryptedMessage.media.key;
                    tLRPC$TL_message_secret.media.document.iv = tLRPC$TL_decryptedMessage.media.iv;
                    if (tLRPC$TL_message_secret.media.document.mime_type == null) {
                        tLRPC$TL_message_secret.media.document.mime_type = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                    bArr = ((TLRPC$TL_decryptedMessageMediaDocument) tLRPC$TL_decryptedMessage.media).thumb;
                    if (bArr == null || bArr.length == 0 || bArr.length > 6000 || tLRPC$TL_decryptedMessage.media.thumb_w > 100 || tLRPC$TL_decryptedMessage.media.thumb_h > 100) {
                        tLRPC$TL_message_secret.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
                        tLRPC$TL_message_secret.media.document.thumb.type = "s";
                    } else {
                        tLRPC$TL_message_secret.media.document.thumb = new TLRPC$TL_photoCachedSize();
                        tLRPC$TL_message_secret.media.document.thumb.bytes = bArr;
                        tLRPC$TL_message_secret.media.document.thumb.f10147w = tLRPC$TL_decryptedMessage.media.thumb_w;
                        tLRPC$TL_message_secret.media.document.thumb.f10146h = tLRPC$TL_decryptedMessage.media.thumb_h;
                        tLRPC$TL_message_secret.media.document.thumb.type = "s";
                        tLRPC$TL_message_secret.media.document.thumb.location = new TLRPC$TL_fileLocationUnavailable();
                    }
                    tLRPC$TL_message_secret.media.document.dc_id = encryptedFile.dc_id;
                    if (!MessageObject.isVoiceMessage(tLRPC$TL_message_secret) && !MessageObject.isRoundVideoMessage(tLRPC$TL_message_secret)) {
                        return tLRPC$TL_message_secret;
                    }
                    tLRPC$TL_message_secret.media_unread = true;
                    return tLRPC$TL_message_secret;
                } else if (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaExternalDocument) {
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaDocument();
                    r0 = tLRPC$TL_message_secret.media;
                    r0.flags |= 3;
                    tLRPC$TL_message_secret.media.caption = TtmlNode.ANONYMOUS_REGION_ID;
                    tLRPC$TL_message_secret.media.document = new TLRPC$TL_document();
                    tLRPC$TL_message_secret.media.document.id = tLRPC$TL_decryptedMessage.media.id;
                    tLRPC$TL_message_secret.media.document.access_hash = tLRPC$TL_decryptedMessage.media.access_hash;
                    tLRPC$TL_message_secret.media.document.date = tLRPC$TL_decryptedMessage.media.date;
                    tLRPC$TL_message_secret.media.document.attributes = tLRPC$TL_decryptedMessage.media.attributes;
                    tLRPC$TL_message_secret.media.document.mime_type = tLRPC$TL_decryptedMessage.media.mime_type;
                    tLRPC$TL_message_secret.media.document.dc_id = tLRPC$TL_decryptedMessage.media.dc_id;
                    tLRPC$TL_message_secret.media.document.size = tLRPC$TL_decryptedMessage.media.size;
                    tLRPC$TL_message_secret.media.document.thumb = ((TLRPC$TL_decryptedMessageMediaExternalDocument) tLRPC$TL_decryptedMessage.media).thumb;
                    if (tLRPC$TL_message_secret.media.document.mime_type != null) {
                        return tLRPC$TL_message_secret;
                    }
                    tLRPC$TL_message_secret.media.document.mime_type = TtmlNode.ANONYMOUS_REGION_ID;
                    return tLRPC$TL_message_secret;
                } else if (tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaAudio) {
                    if (tLRPC$TL_decryptedMessage.media.key == null || tLRPC$TL_decryptedMessage.media.key.length != 32 || tLRPC$TL_decryptedMessage.media.iv == null || tLRPC$TL_decryptedMessage.media.iv.length != 32) {
                        return null;
                    }
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaDocument();
                    r0 = tLRPC$TL_message_secret.media;
                    r0.flags |= 3;
                    tLRPC$TL_message_secret.media.document = new TLRPC$TL_documentEncrypted();
                    tLRPC$TL_message_secret.media.document.key = tLRPC$TL_decryptedMessage.media.key;
                    tLRPC$TL_message_secret.media.document.iv = tLRPC$TL_decryptedMessage.media.iv;
                    tLRPC$TL_message_secret.media.document.id = encryptedFile.id;
                    tLRPC$TL_message_secret.media.document.access_hash = encryptedFile.access_hash;
                    tLRPC$TL_message_secret.media.document.date = i;
                    tLRPC$TL_message_secret.media.document.size = encryptedFile.size;
                    tLRPC$TL_message_secret.media.document.dc_id = encryptedFile.dc_id;
                    tLRPC$TL_message_secret.media.document.mime_type = tLRPC$TL_decryptedMessage.media.mime_type;
                    tLRPC$TL_message_secret.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
                    tLRPC$TL_message_secret.media.document.thumb.type = "s";
                    tLRPC$TL_message_secret.media.caption = tLRPC$TL_decryptedMessage.media.caption != null ? tLRPC$TL_decryptedMessage.media.caption : TtmlNode.ANONYMOUS_REGION_ID;
                    if (tLRPC$TL_message_secret.media.document.mime_type == null) {
                        tLRPC$TL_message_secret.media.document.mime_type = "audio/ogg";
                    }
                    TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
                    tLRPC$TL_documentAttributeAudio.duration = tLRPC$TL_decryptedMessage.media.duration;
                    tLRPC$TL_documentAttributeAudio.voice = true;
                    tLRPC$TL_message_secret.media.document.attributes.add(tLRPC$TL_documentAttributeAudio);
                    if (tLRPC$TL_message_secret.ttl == 0) {
                        return tLRPC$TL_message_secret;
                    }
                    tLRPC$TL_message_secret.ttl = Math.max(tLRPC$TL_decryptedMessage.media.duration + 1, tLRPC$TL_message_secret.ttl);
                    return tLRPC$TL_message_secret;
                } else if (!(tLRPC$TL_decryptedMessage.media instanceof TLRPC$TL_decryptedMessageMediaVenue)) {
                    return null;
                } else {
                    tLRPC$TL_message_secret.media = new TLRPC$TL_messageMediaVenue();
                    tLRPC$TL_message_secret.media.geo = new TLRPC$TL_geoPoint();
                    tLRPC$TL_message_secret.media.geo.lat = tLRPC$TL_decryptedMessage.media.lat;
                    tLRPC$TL_message_secret.media.geo._long = tLRPC$TL_decryptedMessage.media._long;
                    tLRPC$TL_message_secret.media.title = tLRPC$TL_decryptedMessage.media.title;
                    tLRPC$TL_message_secret.media.address = tLRPC$TL_decryptedMessage.media.address;
                    tLRPC$TL_message_secret.media.provider = tLRPC$TL_decryptedMessage.media.provider;
                    tLRPC$TL_message_secret.media.venue_id = tLRPC$TL_decryptedMessage.media.venue_id;
                    tLRPC$TL_message_secret.media.venue_type = TtmlNode.ANONYMOUS_REGION_ID;
                    return tLRPC$TL_message_secret;
                }
            } else if (tLObject instanceof TLRPC$TL_decryptedMessageService) {
                TLRPC$TL_decryptedMessageService tLRPC$TL_decryptedMessageService = (TLRPC$TL_decryptedMessageService) tLObject;
                if ((tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL) || (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages)) {
                    tLRPC$TL_message_secret = new TLRPC$TL_messageService();
                    if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL) {
                        tLRPC$TL_message_secret.action = new TLRPC$TL_messageEncryptedAction();
                        if (tLRPC$TL_decryptedMessageService.action.ttl_seconds < 0 || tLRPC$TL_decryptedMessageService.action.ttl_seconds > 31536000) {
                            tLRPC$TL_decryptedMessageService.action.ttl_seconds = 31536000;
                        }
                        encryptedChat.ttl = tLRPC$TL_decryptedMessageService.action.ttl_seconds;
                        tLRPC$TL_message_secret.action.encryptedAction = tLRPC$TL_decryptedMessageService.action;
                        MessagesStorage.getInstance().updateEncryptedChatTTL(encryptedChat);
                    } else if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) {
                        tLRPC$TL_message_secret.action = new TLRPC$TL_messageEncryptedAction();
                        tLRPC$TL_message_secret.action.encryptedAction = tLRPC$TL_decryptedMessageService.action;
                    }
                    newMessageId = UserConfig.getNewMessageId();
                    tLRPC$TL_message_secret.id = newMessageId;
                    tLRPC$TL_message_secret.local_id = newMessageId;
                    UserConfig.saveConfig(false);
                    tLRPC$TL_message_secret.unread = true;
                    tLRPC$TL_message_secret.flags = 256;
                    tLRPC$TL_message_secret.date = i;
                    tLRPC$TL_message_secret.from_id = i2;
                    tLRPC$TL_message_secret.to_id = new TLRPC$TL_peerUser();
                    tLRPC$TL_message_secret.to_id.user_id = UserConfig.getClientUserId();
                    tLRPC$TL_message_secret.dialog_id = ((long) encryptedChat.id) << 32;
                    return tLRPC$TL_message_secret;
                } else if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionFlushHistory) {
                    r0 = ((long) encryptedChat.id) << 32;
                    AndroidUtilities.runOnUIThread(new Runnable() {

                        /* renamed from: org.telegram.messenger.SecretChatHelper$6$1 */
                        class C33551 implements Runnable {

                            /* renamed from: org.telegram.messenger.SecretChatHelper$6$1$1 */
                            class C33541 implements Runnable {
                                C33541() {
                                }

                                public void run() {
                                    NotificationsController.getInstance().processReadMessages(null, r0, 0, Integer.MAX_VALUE, false);
                                    HashMap hashMap = new HashMap();
                                    hashMap.put(Long.valueOf(r0), Integer.valueOf(0));
                                    NotificationsController.getInstance().processDialogsUpdateRead(hashMap);
                                }
                            }

                            C33551() {
                            }

                            public void run() {
                                AndroidUtilities.runOnUIThread(new C33541());
                            }
                        }

                        public void run() {
                            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(r0));
                            if (tLRPC$TL_dialog != null) {
                                tLRPC$TL_dialog.unread_count = 0;
                                MessagesController.getInstance().dialogMessage.remove(Long.valueOf(tLRPC$TL_dialog.id));
                            }
                            MessagesStorage.getInstance().getStorageQueue().postRunnable(new C33551());
                            MessagesStorage.getInstance().deleteDialog(r0, 1);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.removeAllMessagesFromDialog, Long.valueOf(r0), Boolean.valueOf(false));
                        }
                    });
                    return null;
                } else if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionDeleteMessages) {
                    if (!tLRPC$TL_decryptedMessageService.action.random_ids.isEmpty()) {
                        this.pendingEncMessagesToDelete.addAll(tLRPC$TL_decryptedMessageService.action.random_ids);
                    }
                    return null;
                } else if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionReadMessages) {
                    if (!tLRPC$TL_decryptedMessageService.action.random_ids.isEmpty()) {
                        newMessageId = ConnectionsManager.getInstance().getCurrentTime();
                        MessagesStorage.getInstance().createTaskForSecretChat(encryptedChat.id, newMessageId, newMessageId, 1, tLRPC$TL_decryptedMessageService.action.random_ids);
                    }
                } else if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionNotifyLayer) {
                    applyPeerLayer(encryptedChat, tLRPC$TL_decryptedMessageService.action.layer);
                } else if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionRequestKey) {
                    if (encryptedChat.exchange_id != 0) {
                        if (encryptedChat.exchange_id > tLRPC$TL_decryptedMessageService.action.exchange_id) {
                            FileLog.m13726e("we already have request key with higher exchange_id");
                            return null;
                        }
                        sendAbortKeyMessage(encryptedChat, null, encryptedChat.exchange_id);
                    }
                    r2 = new byte[256];
                    Utilities.random.nextBytes(r2);
                    BigInteger bigInteger = new BigInteger(1, MessagesStorage.secretPBytes);
                    r0 = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, r2), bigInteger);
                    BigInteger bigInteger2 = new BigInteger(1, tLRPC$TL_decryptedMessageService.action.g_a);
                    if (Utilities.isGoodGaAndGb(bigInteger2, bigInteger)) {
                        byte[] bArr2;
                        r1 = r0.toByteArray();
                        if (r1.length > 256) {
                            bArr = new byte[256];
                            System.arraycopy(r1, 1, bArr, 0, 256);
                        } else {
                            r0 = r1;
                        }
                        Object toByteArray = bigInteger2.modPow(new BigInteger(1, r2), bigInteger).toByteArray();
                        if (toByteArray.length > 256) {
                            bArr2 = new byte[256];
                            System.arraycopy(toByteArray, toByteArray.length - 256, bArr2, 0, 256);
                        } else if (toByteArray.length < 256) {
                            r2 = new byte[256];
                            System.arraycopy(toByteArray, 0, r2, 256 - toByteArray.length, toByteArray.length);
                            for (int i3 = 0; i3 < 256 - toByteArray.length; i3++) {
                                toByteArray[i3] = (byte) 0;
                            }
                            r1 = r2;
                        } else {
                            r1 = toByteArray;
                        }
                        r2 = Utilities.computeSHA1(bArr2);
                        toByteArray = new byte[8];
                        System.arraycopy(r2, r2.length - 8, toByteArray, 0, 8);
                        encryptedChat.exchange_id = tLRPC$TL_decryptedMessageService.action.exchange_id;
                        encryptedChat.future_auth_key = bArr2;
                        encryptedChat.future_key_fingerprint = Utilities.bytesToLong(toByteArray);
                        encryptedChat.g_a_or_b = bArr;
                        MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                        sendAcceptKeyMessage(encryptedChat, null);
                    } else {
                        sendAbortKeyMessage(encryptedChat, null, tLRPC$TL_decryptedMessageService.action.exchange_id);
                        return null;
                    }
                } else if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionAcceptKey) {
                    if (encryptedChat.exchange_id == tLRPC$TL_decryptedMessageService.action.exchange_id) {
                        r0 = new BigInteger(1, MessagesStorage.secretPBytes);
                        BigInteger bigInteger3 = new BigInteger(1, tLRPC$TL_decryptedMessageService.action.g_b);
                        if (Utilities.isGoodGaAndGb(bigInteger3, r0)) {
                            r2 = bigInteger3.modPow(new BigInteger(1, encryptedChat.a_or_b), r0).toByteArray();
                            if (r2.length > 256) {
                                bArr = new byte[256];
                                System.arraycopy(r2, r2.length - 256, bArr, 0, 256);
                            } else if (r2.length < 256) {
                                r1 = new byte[256];
                                System.arraycopy(r2, 0, r1, 256 - r2.length, r2.length);
                                for (i2 = 0; i2 < 256 - r2.length; i2++) {
                                    r2[i2] = (byte) 0;
                                }
                                r0 = r1;
                            } else {
                                r0 = r2;
                            }
                            r1 = Utilities.computeSHA1(bArr);
                            r2 = new byte[8];
                            System.arraycopy(r1, r1.length - 8, r2, 0, 8);
                            long bytesToLong = Utilities.bytesToLong(r2);
                            if (tLRPC$TL_decryptedMessageService.action.key_fingerprint == bytesToLong) {
                                encryptedChat.future_auth_key = bArr;
                                encryptedChat.future_key_fingerprint = bytesToLong;
                                MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                                sendCommitKeyMessage(encryptedChat, null);
                            } else {
                                encryptedChat.future_auth_key = new byte[256];
                                encryptedChat.future_key_fingerprint = 0;
                                encryptedChat.exchange_id = 0;
                                MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                                sendAbortKeyMessage(encryptedChat, null, tLRPC$TL_decryptedMessageService.action.exchange_id);
                            }
                        } else {
                            encryptedChat.future_auth_key = new byte[256];
                            encryptedChat.future_key_fingerprint = 0;
                            encryptedChat.exchange_id = 0;
                            MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                            sendAbortKeyMessage(encryptedChat, null, tLRPC$TL_decryptedMessageService.action.exchange_id);
                            return null;
                        }
                    }
                    encryptedChat.future_auth_key = new byte[256];
                    encryptedChat.future_key_fingerprint = 0;
                    encryptedChat.exchange_id = 0;
                    MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                    sendAbortKeyMessage(encryptedChat, null, tLRPC$TL_decryptedMessageService.action.exchange_id);
                } else if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionCommitKey) {
                    if (encryptedChat.exchange_id == tLRPC$TL_decryptedMessageService.action.exchange_id && encryptedChat.future_key_fingerprint == tLRPC$TL_decryptedMessageService.action.key_fingerprint) {
                        r0 = encryptedChat.key_fingerprint;
                        r2 = encryptedChat.auth_key;
                        encryptedChat.key_fingerprint = encryptedChat.future_key_fingerprint;
                        encryptedChat.auth_key = encryptedChat.future_auth_key;
                        encryptedChat.key_create_date = ConnectionsManager.getInstance().getCurrentTime();
                        encryptedChat.future_auth_key = r2;
                        encryptedChat.future_key_fingerprint = r0;
                        encryptedChat.key_use_count_in = (short) 0;
                        encryptedChat.key_use_count_out = (short) 0;
                        encryptedChat.exchange_id = 0;
                        MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                        sendNoopMessage(encryptedChat, null);
                    } else {
                        encryptedChat.future_auth_key = new byte[256];
                        encryptedChat.future_key_fingerprint = 0;
                        encryptedChat.exchange_id = 0;
                        MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                        sendAbortKeyMessage(encryptedChat, null, tLRPC$TL_decryptedMessageService.action.exchange_id);
                    }
                } else if (tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionAbortKey) {
                    if (encryptedChat.exchange_id == tLRPC$TL_decryptedMessageService.action.exchange_id) {
                        encryptedChat.future_auth_key = new byte[256];
                        encryptedChat.future_key_fingerprint = 0;
                        encryptedChat.exchange_id = 0;
                        MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                    }
                } else if (!(tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionNoop)) {
                    if (!(tLRPC$TL_decryptedMessageService.action instanceof TLRPC$TL_decryptedMessageActionResend)) {
                        return null;
                    }
                    if (tLRPC$TL_decryptedMessageService.action.end_seq_no < encryptedChat.in_seq_no || tLRPC$TL_decryptedMessageService.action.end_seq_no < tLRPC$TL_decryptedMessageService.action.start_seq_no) {
                        return null;
                    }
                    if (tLRPC$TL_decryptedMessageService.action.start_seq_no < encryptedChat.in_seq_no) {
                        tLRPC$TL_decryptedMessageService.action.start_seq_no = encryptedChat.in_seq_no;
                    }
                    resendMessages(tLRPC$TL_decryptedMessageService.action.start_seq_no, tLRPC$TL_decryptedMessageService.action.end_seq_no, encryptedChat);
                }
            } else {
                FileLog.m13726e("unknown message " + tLObject);
            }
        } else {
            FileLog.m13726e("unknown TLObject");
        }
        return null;
    }

    protected void processPendingEncMessages() {
        if (!this.pendingEncMessagesToDelete.isEmpty()) {
            final ArrayList arrayList = new ArrayList(this.pendingEncMessagesToDelete);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    for (int i = 0; i < arrayList.size(); i++) {
                        MessageObject messageObject = (MessageObject) MessagesController.getInstance().dialogMessagesByRandomIds.get(arrayList.get(i));
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

    protected void processUpdateEncryption(TLRPC$TL_updateEncryption tLRPC$TL_updateEncryption, ConcurrentHashMap<Integer, User> concurrentHashMap) {
        final EncryptedChat encryptedChat = tLRPC$TL_updateEncryption.chat;
        long j = ((long) encryptedChat.id) << 32;
        final EncryptedChat encryptedChatDB = MessagesController.getInstance().getEncryptedChatDB(encryptedChat.id, false);
        if ((encryptedChat instanceof TLRPC$TL_encryptedChatRequested) && encryptedChatDB == null) {
            int i = encryptedChat.participant_id;
            int i2 = i == UserConfig.getClientUserId() ? encryptedChat.admin_id : i;
            User user = MessagesController.getInstance().getUser(Integer.valueOf(i2));
            if (user == null) {
                user = (User) concurrentHashMap.get(Integer.valueOf(i2));
            }
            encryptedChat.user_id = i2;
            final TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
            tLRPC$TL_dialog.id = j;
            tLRPC$TL_dialog.unread_count = 0;
            tLRPC$TL_dialog.top_message = 0;
            tLRPC$TL_dialog.last_message_date = tLRPC$TL_updateEncryption.date;
            MessagesController.getInstance().putEncryptedChat(encryptedChat, false);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    MessagesController.getInstance().dialogs_dict.put(Long.valueOf(tLRPC$TL_dialog.id), tLRPC$TL_dialog);
                    MessagesController.getInstance().dialogs.add(tLRPC$TL_dialog);
                    MessagesController.getInstance().sortDialogs(null);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                }
            });
            MessagesStorage.getInstance().putEncryptedChat(encryptedChat, user, tLRPC$TL_dialog);
            getInstance().acceptSecretChat(encryptedChat);
        } else if (!(encryptedChat instanceof TLRPC$TL_encryptedChat)) {
            if (encryptedChatDB != null) {
                encryptedChat.user_id = encryptedChatDB.user_id;
                encryptedChat.auth_key = encryptedChatDB.auth_key;
                encryptedChat.key_create_date = encryptedChatDB.key_create_date;
                encryptedChat.key_use_count_in = encryptedChatDB.key_use_count_in;
                encryptedChat.key_use_count_out = encryptedChatDB.key_use_count_out;
                encryptedChat.ttl = encryptedChatDB.ttl;
                encryptedChat.seq_in = encryptedChatDB.seq_in;
                encryptedChat.seq_out = encryptedChatDB.seq_out;
                encryptedChat.admin_id = encryptedChatDB.admin_id;
                encryptedChat.mtproto_seq = encryptedChatDB.mtproto_seq;
            }
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (encryptedChatDB != null) {
                        MessagesController.getInstance().putEncryptedChat(encryptedChat, false);
                    }
                    MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatUpdated, encryptedChat);
                }
            });
        } else if (encryptedChatDB != null && (encryptedChatDB instanceof TLRPC$TL_encryptedChatWaiting) && (encryptedChatDB.auth_key == null || encryptedChatDB.auth_key.length == 1)) {
            encryptedChat.a_or_b = encryptedChatDB.a_or_b;
            encryptedChat.user_id = encryptedChatDB.user_id;
            processAcceptedSecretChat(encryptedChat);
        } else if (encryptedChatDB == null && this.startingSecretChat) {
            this.delayedEncryptedChatUpdates.add(tLRPC$TL_updateEncryption);
        }
    }

    public void requestNewSecretChatKey(EncryptedChat encryptedChat) {
        if (AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 20) {
            byte[] bArr;
            byte[] bArr2 = new byte[256];
            Utilities.random.nextBytes(bArr2);
            Object toByteArray = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, bArr2), new BigInteger(1, MessagesStorage.secretPBytes)).toByteArray();
            if (toByteArray.length > 256) {
                bArr = new byte[256];
                System.arraycopy(toByteArray, 1, bArr, 0, 256);
            } else {
                Object obj = toByteArray;
            }
            encryptedChat.exchange_id = SendMessagesHelper.getInstance().getNextRandomId();
            encryptedChat.a_or_b = bArr2;
            encryptedChat.g_a = bArr;
            MessagesStorage.getInstance().updateEncryptedChat(encryptedChat);
            sendRequestKeyMessage(encryptedChat, null);
        }
    }

    public void sendAbortKeyMessage(EncryptedChat encryptedChat, Message message, long j) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionAbortKey();
                tLRPC$TL_decryptedMessageService.action.exchange_id = j;
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendAcceptKeyMessage(EncryptedChat encryptedChat, Message message) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionAcceptKey();
                tLRPC$TL_decryptedMessageService.action.exchange_id = encryptedChat.exchange_id;
                tLRPC$TL_decryptedMessageService.action.key_fingerprint = encryptedChat.future_key_fingerprint;
                tLRPC$TL_decryptedMessageService.action.g_b = encryptedChat.g_a_or_b;
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendClearHistoryMessage(EncryptedChat encryptedChat, Message message) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionFlushHistory();
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendCommitKeyMessage(EncryptedChat encryptedChat, Message message) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionCommitKey();
                tLRPC$TL_decryptedMessageService.action.exchange_id = encryptedChat.exchange_id;
                tLRPC$TL_decryptedMessageService.action.key_fingerprint = encryptedChat.future_key_fingerprint;
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendMessagesDeleteMessage(EncryptedChat encryptedChat, ArrayList<Long> arrayList, Message message) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionDeleteMessages();
                tLRPC$TL_decryptedMessageService.action.random_ids = arrayList;
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendMessagesReadMessage(EncryptedChat encryptedChat, ArrayList<Long> arrayList, Message message) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionReadMessages();
                tLRPC$TL_decryptedMessageService.action.random_ids = arrayList;
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendNoopMessage(EncryptedChat encryptedChat, Message message) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionNoop();
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendNotifyLayerMessage(EncryptedChat encryptedChat, Message message) {
        if ((encryptedChat instanceof TLRPC$TL_encryptedChat) && !this.sendingNotifyLayer.contains(Integer.valueOf(encryptedChat.id))) {
            Message message2;
            this.sendingNotifyLayer.add(Integer.valueOf(encryptedChat.id));
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionNotifyLayer();
                tLRPC$TL_decryptedMessageService.action.layer = 73;
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendRequestKeyMessage(EncryptedChat encryptedChat, Message message) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionRequestKey();
                tLRPC$TL_decryptedMessageService.action.exchange_id = encryptedChat.exchange_id;
                tLRPC$TL_decryptedMessageService.action.g_a = encryptedChat.g_a;
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendScreenshotMessage(EncryptedChat encryptedChat, ArrayList<Long> arrayList, Message message) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionScreenshotMessages();
                tLRPC$TL_decryptedMessageService.action.random_ids = arrayList;
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
                MessageObject messageObject = new MessageObject(message2, null, false);
                messageObject.messageOwner.send_state = 1;
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(messageObject);
                MessagesController.getInstance().updateInterfaceWithMessages(message2.dialog_id, arrayList2);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void sendTTLMessage(EncryptedChat encryptedChat, Message message) {
        if (encryptedChat instanceof TLRPC$TL_encryptedChat) {
            Message message2;
            DecryptedMessage tLRPC$TL_decryptedMessageService = new TLRPC$TL_decryptedMessageService();
            if (message != null) {
                tLRPC$TL_decryptedMessageService.action = message.action.encryptedAction;
                message2 = message;
            } else {
                tLRPC$TL_decryptedMessageService.action = new TLRPC$TL_decryptedMessageActionSetMessageTTL();
                tLRPC$TL_decryptedMessageService.action.ttl_seconds = encryptedChat.ttl;
                message2 = createServiceSecretMessage(encryptedChat, tLRPC$TL_decryptedMessageService.action);
                MessageObject messageObject = new MessageObject(message2, null, false);
                messageObject.messageOwner.send_state = 1;
                ArrayList arrayList = new ArrayList();
                arrayList.add(messageObject);
                MessagesController.getInstance().updateInterfaceWithMessages(message2.dialog_id, arrayList);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            }
            tLRPC$TL_decryptedMessageService.random_id = message2.random_id;
            performSendEncryptedRequest(tLRPC$TL_decryptedMessageService, message2, encryptedChat, null, null, null);
        }
    }

    public void startSecretChat(final Context context, final User user) {
        if (user != null && context != null) {
            this.startingSecretChat = true;
            final AlertDialog alertDialog = new AlertDialog(context, 1);
            alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            TLObject tLRPC$TL_messages_getDhConfig = new TLRPC$TL_messages_getDhConfig();
            tLRPC$TL_messages_getDhConfig.random_length = 256;
            tLRPC$TL_messages_getDhConfig.version = MessagesStorage.lastSecretVersion;
            final int sendRequest = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getDhConfig, new RequestDelegate() {

                /* renamed from: org.telegram.messenger.SecretChatHelper$14$1 */
                class C33391 implements Runnable {
                    C33391() {
                    }

                    public void run() {
                        try {
                            if (!((Activity) context).isFinishing()) {
                                alertDialog.dismiss();
                            }
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                }

                /* renamed from: org.telegram.messenger.SecretChatHelper$14$3 */
                class C33443 implements Runnable {
                    C33443() {
                    }

                    public void run() {
                        SecretChatHelper.this.startingSecretChat = false;
                        if (!((Activity) context).isFinishing()) {
                            try {
                                alertDialog.dismiss();
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    }
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLRPC$TL_error == null) {
                        byte[] bArr;
                        TLRPC$messages_DhConfig tLRPC$messages_DhConfig = (TLRPC$messages_DhConfig) tLObject;
                        if (tLObject instanceof TLRPC$TL_messages_dhConfig) {
                            if (Utilities.isGoodPrime(tLRPC$messages_DhConfig.f10165p, tLRPC$messages_DhConfig.f10164g)) {
                                MessagesStorage.secretPBytes = tLRPC$messages_DhConfig.f10165p;
                                MessagesStorage.secretG = tLRPC$messages_DhConfig.f10164g;
                                MessagesStorage.lastSecretVersion = tLRPC$messages_DhConfig.version;
                                MessagesStorage.getInstance().saveSecretParams(MessagesStorage.lastSecretVersion, MessagesStorage.secretG, MessagesStorage.secretPBytes);
                            } else {
                                AndroidUtilities.runOnUIThread(new C33391());
                                return;
                            }
                        }
                        final byte[] bArr2 = new byte[256];
                        for (int i = 0; i < 256; i++) {
                            bArr2[i] = (byte) (((byte) ((int) (Utilities.random.nextDouble() * 256.0d))) ^ tLRPC$messages_DhConfig.random[i]);
                        }
                        Object toByteArray = BigInteger.valueOf((long) MessagesStorage.secretG).modPow(new BigInteger(1, bArr2), new BigInteger(1, MessagesStorage.secretPBytes)).toByteArray();
                        if (toByteArray.length > 256) {
                            bArr = new byte[256];
                            System.arraycopy(toByteArray, 1, bArr, 0, 256);
                        } else {
                            Object obj = toByteArray;
                        }
                        TLObject tLRPC$TL_messages_requestEncryption = new TLRPC$TL_messages_requestEncryption();
                        tLRPC$TL_messages_requestEncryption.g_a = bArr;
                        tLRPC$TL_messages_requestEncryption.user_id = MessagesController.getInputUser(user);
                        tLRPC$TL_messages_requestEncryption.random_id = Utilities.random.nextInt();
                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_requestEncryption, new RequestDelegate() {

                            /* renamed from: org.telegram.messenger.SecretChatHelper$14$2$2 */
                            class C33422 implements Runnable {
                                C33422() {
                                }

                                public void run() {
                                    if (!((Activity) context).isFinishing()) {
                                        SecretChatHelper.this.startingSecretChat = false;
                                        try {
                                            alertDialog.dismiss();
                                        } catch (Throwable e) {
                                            FileLog.m13728e(e);
                                        }
                                        Builder builder = new Builder(context);
                                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                        builder.setMessage(LocaleController.getString("CreateEncryptedChatError", R.string.CreateEncryptedChatError));
                                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                                        builder.show().setCanceledOnTouchOutside(true);
                                    }
                                }
                            }

                            public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                if (tLRPC$TL_error == null) {
                                    AndroidUtilities.runOnUIThread(new Runnable() {

                                        /* renamed from: org.telegram.messenger.SecretChatHelper$14$2$1$1 */
                                        class C33401 implements Runnable {
                                            C33401() {
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
                                                    alertDialog.dismiss();
                                                } catch (Throwable e) {
                                                    FileLog.m13728e(e);
                                                }
                                            }
                                            EncryptedChat encryptedChat = (EncryptedChat) tLObject;
                                            encryptedChat.user_id = encryptedChat.participant_id;
                                            encryptedChat.seq_in = -2;
                                            encryptedChat.seq_out = 1;
                                            encryptedChat.a_or_b = bArr2;
                                            MessagesController.getInstance().putEncryptedChat(encryptedChat, false);
                                            TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                                            tLRPC$TL_dialog.id = ((long) encryptedChat.id) << 32;
                                            tLRPC$TL_dialog.unread_count = 0;
                                            tLRPC$TL_dialog.top_message = 0;
                                            tLRPC$TL_dialog.last_message_date = ConnectionsManager.getInstance().getCurrentTime();
                                            MessagesController.getInstance().dialogs_dict.put(Long.valueOf(tLRPC$TL_dialog.id), tLRPC$TL_dialog);
                                            MessagesController.getInstance().dialogs.add(tLRPC$TL_dialog);
                                            MessagesController.getInstance().sortDialogs(null);
                                            MessagesStorage.getInstance().putEncryptedChat(encryptedChat, user, tLRPC$TL_dialog);
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.encryptedChatCreated, encryptedChat);
                                            Utilities.stageQueue.postRunnable(new C33401());
                                        }
                                    });
                                    return;
                                }
                                SecretChatHelper.this.delayedEncryptedChatUpdates.clear();
                                AndroidUtilities.runOnUIThread(new C33422());
                            }
                        }, 2);
                        return;
                    }
                    SecretChatHelper.this.delayedEncryptedChatUpdates.clear();
                    AndroidUtilities.runOnUIThread(new C33443());
                }
            }, 2);
            alertDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ConnectionsManager.getInstance().cancelRequest(sendRequest, true);
                    try {
                        dialogInterface.dismiss();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
            try {
                alertDialog.show();
            } catch (Exception e) {
            }
        }
    }
}
