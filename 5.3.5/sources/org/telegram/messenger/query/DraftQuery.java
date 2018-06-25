package org.telegram.messenger.query;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$DraftMessage;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$TL_channels_getMessages;
import org.telegram.tgnet.TLRPC$TL_draftMessage;
import org.telegram.tgnet.TLRPC$TL_draftMessageEmpty;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_getAllDrafts;
import org.telegram.tgnet.TLRPC$TL_messages_getMessages;
import org.telegram.tgnet.TLRPC$TL_messages_saveDraft;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.User;

public class DraftQuery {
    private static HashMap<Long, TLRPC$Message> draftMessages = new HashMap();
    private static HashMap<Long, TLRPC$DraftMessage> drafts = new HashMap();
    private static boolean inTransaction;
    private static boolean loadingDrafts;
    private static SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("drafts", 0);

    /* renamed from: org.telegram.messenger.query.DraftQuery$1 */
    static class C17841 implements RequestDelegate {

        /* renamed from: org.telegram.messenger.query.DraftQuery$1$1 */
        class C17831 implements Runnable {
            C17831() {
            }

            public void run() {
                UserConfig.draftsLoaded = true;
                DraftQuery.loadingDrafts = false;
                UserConfig.saveConfig(false);
            }
        }

        C17841() {
        }

        public void run(TLObject response, TLRPC$TL_error error) {
            if (error == null) {
                MessagesController.getInstance().processUpdates((TLRPC$Updates) response, false);
                AndroidUtilities.runOnUIThread(new C17831());
            }
        }
    }

    /* renamed from: org.telegram.messenger.query.DraftQuery$2 */
    static class C17852 implements RequestDelegate {
        C17852() {
        }

        public void run(TLObject response, TLRPC$TL_error error) {
        }
    }

    static {
        for (Entry<String, ?> entry : preferences.getAll().entrySet()) {
            try {
                String key = (String) entry.getKey();
                long did = Utilities.parseLong(key).longValue();
                SerializedData serializedData = new SerializedData(Utilities.hexToBytes((String) entry.getValue()));
                if (key.startsWith("r_")) {
                    TLRPC$Message message = TLRPC$Message.TLdeserialize(serializedData, serializedData.readInt32(true), true);
                    if (message != null) {
                        draftMessages.put(Long.valueOf(did), message);
                    }
                } else {
                    TLRPC$DraftMessage draftMessage = TLRPC$DraftMessage.TLdeserialize(serializedData, serializedData.readInt32(true), true);
                    if (draftMessage != null) {
                        drafts.put(Long.valueOf(did), draftMessage);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static void loadDrafts() {
        if (!UserConfig.draftsLoaded && !loadingDrafts) {
            loadingDrafts = true;
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_messages_getAllDrafts(), new C17841());
        }
    }

    public static void cleanup() {
        drafts.clear();
        draftMessages.clear();
        preferences.edit().clear().commit();
    }

    public static TLRPC$DraftMessage getDraft(long did) {
        return (TLRPC$DraftMessage) drafts.get(Long.valueOf(did));
    }

    public static TLRPC$Message getDraftMessage(long did) {
        return (TLRPC$Message) draftMessages.get(Long.valueOf(did));
    }

    public static void saveDraft(long did, CharSequence message, ArrayList<TLRPC$MessageEntity> entities, TLRPC$Message replyToMessage, boolean noWebpage) {
        saveDraft(did, message, entities, replyToMessage, noWebpage, false);
    }

    public static void saveDraft(long did, CharSequence message, ArrayList<TLRPC$MessageEntity> entities, TLRPC$Message replyToMessage, boolean noWebpage, boolean clean) {
        TLRPC$DraftMessage draftMessage;
        if (TextUtils.isEmpty(message) && replyToMessage == null) {
            draftMessage = new TLRPC$TL_draftMessageEmpty();
        } else {
            draftMessage = new TLRPC$TL_draftMessage();
        }
        draftMessage.date = (int) (System.currentTimeMillis() / 1000);
        draftMessage.message = message == null ? "" : message.toString();
        draftMessage.no_webpage = noWebpage;
        if (replyToMessage != null) {
            draftMessage.reply_to_msg_id = replyToMessage.id;
            draftMessage.flags |= 1;
        }
        if (!(entities == null || entities.isEmpty())) {
            draftMessage.entities = entities;
            draftMessage.flags |= 8;
        }
        TLRPC$DraftMessage currentDraft = (TLRPC$DraftMessage) drafts.get(Long.valueOf(did));
        if (!clean) {
            if (currentDraft == null || !currentDraft.message.equals(draftMessage.message) || currentDraft.reply_to_msg_id != draftMessage.reply_to_msg_id || currentDraft.no_webpage != draftMessage.no_webpage) {
                if (currentDraft == null && TextUtils.isEmpty(draftMessage.message) && draftMessage.reply_to_msg_id == 0) {
                    return;
                }
            }
            return;
        }
        saveDraft(did, draftMessage, replyToMessage, false);
        int lower_id = (int) did;
        if (lower_id != 0) {
            TLRPC$TL_messages_saveDraft req = new TLRPC$TL_messages_saveDraft();
            req.peer = MessagesController.getInputPeer(lower_id);
            if (req.peer != null) {
                req.message = draftMessage.message;
                req.no_webpage = draftMessage.no_webpage;
                req.reply_to_msg_id = draftMessage.reply_to_msg_id;
                req.entities = draftMessage.entities;
                req.flags = draftMessage.flags;
                ConnectionsManager.getInstance().sendRequest(req, new C17852());
            } else {
                return;
            }
        }
        MessagesController.getInstance().sortDialogs(null);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
    }

    public static void saveDraft(long did, TLRPC$DraftMessage draft, TLRPC$Message replyToMessage, boolean fromServer) {
        Editor editor = preferences.edit();
        if (draft == null || (draft instanceof TLRPC$TL_draftMessageEmpty)) {
            drafts.remove(Long.valueOf(did));
            draftMessages.remove(Long.valueOf(did));
            preferences.edit().remove("" + did).remove("r_" + did).commit();
        } else {
            drafts.put(Long.valueOf(did), draft);
            try {
                SerializedData serializedData = new SerializedData(draft.getObjectSize());
                draft.serializeToStream(serializedData);
                editor.putString("" + did, Utilities.bytesToHex(serializedData.toByteArray()));
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        if (replyToMessage == null) {
            draftMessages.remove(Long.valueOf(did));
            editor.remove("r_" + did);
        } else {
            draftMessages.put(Long.valueOf(did), replyToMessage);
            serializedData = new SerializedData(replyToMessage.getObjectSize());
            replyToMessage.serializeToStream(serializedData);
            editor.putString("r_" + did, Utilities.bytesToHex(serializedData.toByteArray()));
        }
        editor.commit();
        if (fromServer) {
            if (draft.reply_to_msg_id != 0 && replyToMessage == null) {
                int lower_id = (int) did;
                User user = null;
                TLRPC$Chat chat = null;
                if (lower_id > 0) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(lower_id));
                } else {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
                }
                if (!(user == null && chat == null)) {
                    int channelIdFinal;
                    long messageId = (long) draft.reply_to_msg_id;
                    if (ChatObject.isChannel(chat)) {
                        messageId |= ((long) chat.id) << 32;
                        channelIdFinal = chat.id;
                    } else {
                        channelIdFinal = 0;
                    }
                    final long messageIdFinal = messageId;
                    final long j = did;
                    MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                        /* renamed from: org.telegram.messenger.query.DraftQuery$3$1 */
                        class C17861 implements RequestDelegate {
                            C17861() {
                            }

                            public void run(TLObject response, TLRPC$TL_error error) {
                                if (error == null) {
                                    TLRPC$messages_Messages messagesRes = (TLRPC$messages_Messages) response;
                                    if (!messagesRes.messages.isEmpty()) {
                                        DraftQuery.saveDraftReplyMessage(j, (TLRPC$Message) messagesRes.messages.get(0));
                                    }
                                }
                            }
                        }

                        /* renamed from: org.telegram.messenger.query.DraftQuery$3$2 */
                        class C17872 implements RequestDelegate {
                            C17872() {
                            }

                            public void run(TLObject response, TLRPC$TL_error error) {
                                if (error == null) {
                                    TLRPC$messages_Messages messagesRes = (TLRPC$messages_Messages) response;
                                    if (!messagesRes.messages.isEmpty()) {
                                        DraftQuery.saveDraftReplyMessage(j, (TLRPC$Message) messagesRes.messages.get(0));
                                    }
                                }
                            }
                        }

                        public void run() {
                            TLRPC$Message message = null;
                            try {
                                SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data FROM messages WHERE mid = %d", new Object[]{Long.valueOf(messageIdFinal)}), new Object[0]);
                                if (cursor.next()) {
                                    NativeByteBuffer data = cursor.byteBufferValue(0);
                                    if (data != null) {
                                        message = TLRPC$Message.TLdeserialize(data, data.readInt32(false), false);
                                        data.reuse();
                                    }
                                }
                                cursor.dispose();
                                if (message != null) {
                                    DraftQuery.saveDraftReplyMessage(j, message);
                                } else if (channelIdFinal != 0) {
                                    TLRPC$TL_channels_getMessages req = new TLRPC$TL_channels_getMessages();
                                    req.channel = MessagesController.getInputChannel(channelIdFinal);
                                    req.id.add(Integer.valueOf((int) messageIdFinal));
                                    ConnectionsManager.getInstance().sendRequest(req, new C17861());
                                } else {
                                    TLRPC$TL_messages_getMessages req2 = new TLRPC$TL_messages_getMessages();
                                    req2.id.add(Integer.valueOf((int) messageIdFinal));
                                    ConnectionsManager.getInstance().sendRequest(req2, new C17872());
                                }
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                        }
                    });
                }
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.newDraftReceived, new Object[]{Long.valueOf(did)});
        }
    }

    private static void saveDraftReplyMessage(final long did, final TLRPC$Message message) {
        if (message != null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    TLRPC$DraftMessage draftMessage = (TLRPC$DraftMessage) DraftQuery.drafts.get(Long.valueOf(did));
                    if (draftMessage != null && draftMessage.reply_to_msg_id == message.id) {
                        DraftQuery.draftMessages.put(Long.valueOf(did), message);
                        SerializedData serializedData = new SerializedData(message.getObjectSize());
                        message.serializeToStream(serializedData);
                        DraftQuery.preferences.edit().putString("r_" + did, Utilities.bytesToHex(serializedData.toByteArray())).commit();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.newDraftReceived, new Object[]{Long.valueOf(did)});
                    }
                }
            });
        }
    }

    public static void cleanDraft(long did, boolean replyOnly) {
        TLRPC$DraftMessage draftMessage = (TLRPC$DraftMessage) drafts.get(Long.valueOf(did));
        if (draftMessage != null) {
            if (!replyOnly) {
                drafts.remove(Long.valueOf(did));
                draftMessages.remove(Long.valueOf(did));
                preferences.edit().remove("" + did).remove("r_" + did).commit();
                MessagesController.getInstance().sortDialogs(null);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            } else if (draftMessage.reply_to_msg_id != 0) {
                draftMessage.reply_to_msg_id = 0;
                draftMessage.flags &= -2;
                saveDraft(did, draftMessage.message, draftMessage.entities, null, draftMessage.no_webpage, true);
            }
        }
    }

    public static void beginTransaction() {
        inTransaction = true;
    }

    public static void endTransaction() {
        inTransaction = false;
    }
}
