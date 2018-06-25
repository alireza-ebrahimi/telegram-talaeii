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
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_draftMessage;
import org.telegram.tgnet.TLRPC$TL_draftMessageEmpty;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_getAllDrafts;
import org.telegram.tgnet.TLRPC$TL_messages_getMessages;
import org.telegram.tgnet.TLRPC$TL_messages_saveDraft;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.DraftMessage;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.TL_channels_getMessages;
import org.telegram.tgnet.TLRPC.User;

public class DraftQuery {
    private static HashMap<Long, Message> draftMessages = new HashMap();
    private static HashMap<Long, DraftMessage> drafts = new HashMap();
    private static boolean inTransaction;
    private static boolean loadingDrafts;
    private static SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("drafts", 0);

    /* renamed from: org.telegram.messenger.query.DraftQuery$1 */
    static class C35621 implements RequestDelegate {

        /* renamed from: org.telegram.messenger.query.DraftQuery$1$1 */
        class C35611 implements Runnable {
            C35611() {
            }

            public void run() {
                UserConfig.draftsLoaded = true;
                DraftQuery.loadingDrafts = false;
                UserConfig.saveConfig(false);
            }
        }

        C35621() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLRPC$TL_error == null) {
                MessagesController.getInstance().processUpdates((TLRPC$Updates) tLObject, false);
                AndroidUtilities.runOnUIThread(new C35611());
            }
        }
    }

    /* renamed from: org.telegram.messenger.query.DraftQuery$2 */
    static class C35632 implements RequestDelegate {
        C35632() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }
    }

    static {
        for (Entry entry : preferences.getAll().entrySet()) {
            try {
                String str = (String) entry.getKey();
                long longValue = Utilities.parseLong(str).longValue();
                AbstractSerializedData serializedData = new SerializedData(Utilities.hexToBytes((String) entry.getValue()));
                if (str.startsWith("r_")) {
                    Message TLdeserialize = Message.TLdeserialize(serializedData, serializedData.readInt32(true), true);
                    if (TLdeserialize != null) {
                        draftMessages.put(Long.valueOf(longValue), TLdeserialize);
                    }
                } else {
                    DraftMessage TLdeserialize2 = DraftMessage.TLdeserialize(serializedData, serializedData.readInt32(true), true);
                    if (TLdeserialize2 != null) {
                        drafts.put(Long.valueOf(longValue), TLdeserialize2);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static void beginTransaction() {
        inTransaction = true;
    }

    public static void cleanDraft(long j, boolean z) {
        DraftMessage draftMessage = (DraftMessage) drafts.get(Long.valueOf(j));
        if (draftMessage != null) {
            if (!z) {
                drafts.remove(Long.valueOf(j));
                draftMessages.remove(Long.valueOf(j));
                preferences.edit().remove(TtmlNode.ANONYMOUS_REGION_ID + j).remove("r_" + j).commit();
                MessagesController.getInstance().sortDialogs(null);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            } else if (draftMessage.reply_to_msg_id != 0) {
                draftMessage.reply_to_msg_id = 0;
                draftMessage.flags &= -2;
                saveDraft(j, draftMessage.message, draftMessage.entities, null, draftMessage.no_webpage, true);
            }
        }
    }

    public static void cleanup() {
        drafts.clear();
        draftMessages.clear();
        preferences.edit().clear().commit();
    }

    public static void endTransaction() {
        inTransaction = false;
    }

    public static DraftMessage getDraft(long j) {
        return (DraftMessage) drafts.get(Long.valueOf(j));
    }

    public static Message getDraftMessage(long j) {
        return (Message) draftMessages.get(Long.valueOf(j));
    }

    public static void loadDrafts() {
        if (!UserConfig.draftsLoaded && !loadingDrafts) {
            loadingDrafts = true;
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_messages_getAllDrafts(), new C35621());
        }
    }

    public static void saveDraft(long j, CharSequence charSequence, ArrayList<MessageEntity> arrayList, Message message, boolean z) {
        saveDraft(j, charSequence, arrayList, message, z, false);
    }

    public static void saveDraft(long j, CharSequence charSequence, ArrayList<MessageEntity> arrayList, Message message, boolean z, boolean z2) {
        DraftMessage tLRPC$TL_draftMessageEmpty = (TextUtils.isEmpty(charSequence) && message == null) ? new TLRPC$TL_draftMessageEmpty() : new TLRPC$TL_draftMessage();
        tLRPC$TL_draftMessageEmpty.date = (int) (System.currentTimeMillis() / 1000);
        tLRPC$TL_draftMessageEmpty.message = charSequence == null ? TtmlNode.ANONYMOUS_REGION_ID : charSequence.toString();
        tLRPC$TL_draftMessageEmpty.no_webpage = z;
        if (message != null) {
            tLRPC$TL_draftMessageEmpty.reply_to_msg_id = message.id;
            tLRPC$TL_draftMessageEmpty.flags |= 1;
        }
        if (!(arrayList == null || arrayList.isEmpty())) {
            tLRPC$TL_draftMessageEmpty.entities = arrayList;
            tLRPC$TL_draftMessageEmpty.flags |= 8;
        }
        DraftMessage draftMessage = (DraftMessage) drafts.get(Long.valueOf(j));
        if (!z2) {
            if (draftMessage == null || !draftMessage.message.equals(tLRPC$TL_draftMessageEmpty.message) || draftMessage.reply_to_msg_id != tLRPC$TL_draftMessageEmpty.reply_to_msg_id || draftMessage.no_webpage != tLRPC$TL_draftMessageEmpty.no_webpage) {
                if (draftMessage == null && TextUtils.isEmpty(tLRPC$TL_draftMessageEmpty.message) && tLRPC$TL_draftMessageEmpty.reply_to_msg_id == 0) {
                    return;
                }
            }
            return;
        }
        saveDraft(j, tLRPC$TL_draftMessageEmpty, message, false);
        int i = (int) j;
        if (i != 0) {
            TLObject tLRPC$TL_messages_saveDraft = new TLRPC$TL_messages_saveDraft();
            tLRPC$TL_messages_saveDraft.peer = MessagesController.getInputPeer(i);
            if (tLRPC$TL_messages_saveDraft.peer != null) {
                tLRPC$TL_messages_saveDraft.message = tLRPC$TL_draftMessageEmpty.message;
                tLRPC$TL_messages_saveDraft.no_webpage = tLRPC$TL_draftMessageEmpty.no_webpage;
                tLRPC$TL_messages_saveDraft.reply_to_msg_id = tLRPC$TL_draftMessageEmpty.reply_to_msg_id;
                tLRPC$TL_messages_saveDraft.entities = tLRPC$TL_draftMessageEmpty.entities;
                tLRPC$TL_messages_saveDraft.flags = tLRPC$TL_draftMessageEmpty.flags;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_saveDraft, new C35632());
            } else {
                return;
            }
        }
        MessagesController.getInstance().sortDialogs(null);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
    }

    public static void saveDraft(long j, DraftMessage draftMessage, Message message, boolean z) {
        User user = null;
        Editor edit = preferences.edit();
        if (draftMessage == null || (draftMessage instanceof TLRPC$TL_draftMessageEmpty)) {
            drafts.remove(Long.valueOf(j));
            draftMessages.remove(Long.valueOf(j));
            preferences.edit().remove(TtmlNode.ANONYMOUS_REGION_ID + j).remove("r_" + j).commit();
        } else {
            drafts.put(Long.valueOf(j), draftMessage);
            try {
                AbstractSerializedData serializedData = new SerializedData(draftMessage.getObjectSize());
                draftMessage.serializeToStream(serializedData);
                edit.putString(TtmlNode.ANONYMOUS_REGION_ID + j, Utilities.bytesToHex(serializedData.toByteArray()));
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
        if (message == null) {
            draftMessages.remove(Long.valueOf(j));
            edit.remove("r_" + j);
        } else {
            draftMessages.put(Long.valueOf(j), message);
            serializedData = new SerializedData(message.getObjectSize());
            message.serializeToStream(serializedData);
            edit.putString("r_" + j, Utilities.bytesToHex(serializedData.toByteArray()));
        }
        edit.commit();
        if (z) {
            if (draftMessage.reply_to_msg_id != 0 && message == null) {
                Chat chat;
                int i = (int) j;
                if (i > 0) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(i));
                    chat = null;
                } else {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(-i));
                }
                if (!(user == null && chat == null)) {
                    int i2;
                    long j2 = (long) draftMessage.reply_to_msg_id;
                    if (ChatObject.isChannel(chat)) {
                        j2 |= ((long) chat.id) << 32;
                        i2 = chat.id;
                    } else {
                        i2 = 0;
                    }
                    final long j3 = j;
                    MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                        /* renamed from: org.telegram.messenger.query.DraftQuery$3$1 */
                        class C35641 implements RequestDelegate {
                            C35641() {
                            }

                            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                if (tLRPC$TL_error == null) {
                                    TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                    if (!tLRPC$messages_Messages.messages.isEmpty()) {
                                        DraftQuery.saveDraftReplyMessage(j3, (Message) tLRPC$messages_Messages.messages.get(0));
                                    }
                                }
                            }
                        }

                        /* renamed from: org.telegram.messenger.query.DraftQuery$3$2 */
                        class C35652 implements RequestDelegate {
                            C35652() {
                            }

                            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                if (tLRPC$TL_error == null) {
                                    TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                    if (!tLRPC$messages_Messages.messages.isEmpty()) {
                                        DraftQuery.saveDraftReplyMessage(j3, (Message) tLRPC$messages_Messages.messages.get(0));
                                    }
                                }
                            }
                        }

                        public void run() {
                            Message message = null;
                            try {
                                SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT data FROM messages WHERE mid = %d", new Object[]{Long.valueOf(j2)}), new Object[0]);
                                if (b.m12152a()) {
                                    AbstractSerializedData g = b.m12161g(0);
                                    if (g != null) {
                                        message = Message.TLdeserialize(g, g.readInt32(false), false);
                                        g.reuse();
                                    }
                                }
                                b.m12155b();
                                if (message != null) {
                                    DraftQuery.saveDraftReplyMessage(j3, message);
                                } else if (i2 != 0) {
                                    r0 = new TL_channels_getMessages();
                                    r0.channel = MessagesController.getInputChannel(i2);
                                    r0.id.add(Integer.valueOf((int) j2));
                                    ConnectionsManager.getInstance().sendRequest(r0, new C35641());
                                } else {
                                    r0 = new TLRPC$TL_messages_getMessages();
                                    r0.id.add(Integer.valueOf((int) j2));
                                    ConnectionsManager.getInstance().sendRequest(r0, new C35652());
                                }
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    });
                }
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.newDraftReceived, Long.valueOf(j));
        }
    }

    private static void saveDraftReplyMessage(final long j, final Message message) {
        if (message != null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    DraftMessage draftMessage = (DraftMessage) DraftQuery.drafts.get(Long.valueOf(j));
                    if (draftMessage != null && draftMessage.reply_to_msg_id == message.id) {
                        DraftQuery.draftMessages.put(Long.valueOf(j), message);
                        AbstractSerializedData serializedData = new SerializedData(message.getObjectSize());
                        message.serializeToStream(serializedData);
                        DraftQuery.preferences.edit().putString("r_" + j, Utilities.bytesToHex(serializedData.toByteArray())).commit();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.newDraftReceived, Long.valueOf(j));
                    }
                }
            });
        }
    }
}
