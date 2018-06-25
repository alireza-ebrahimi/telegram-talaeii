package org.telegram.customization.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessageObject;

public class DownloadManager {
    static boolean canCache = false;
    static Map<PairDialogMessage, Boolean> favoriteCache = new HashMap();
    long chat_id;
    long id;
    private long msg_id;

    static class PairDialogMessage {
        private long dialogId;
        private long messageId;

        public PairDialogMessage(long dialogId, long messageId) {
            setDialogId(dialogId);
            setMessageId(messageId);
        }

        public long getDialogId() {
            return this.dialogId;
        }

        public void setDialogId(long dialogId) {
            this.dialogId = dialogId;
        }

        public long getMessageId() {
            return this.messageId;
        }

        public void setMessageId(long messageId) {
            this.messageId = messageId;
        }

        public boolean equals(Object o) {
            return (o instanceof PairDialogMessage) && ((PairDialogMessage) o).getDialogId() == getDialogId() && ((PairDialogMessage) o).getMessageId() == getMessageId();
        }

        public int hashCode() {
            return ("" + this.dialogId + "," + this.messageId).hashCode();
        }
    }

    public DownloadManager(long chat_id) {
        this.chat_id = chat_id;
    }

    public DownloadManager(long chat_id, long msgId) {
        this.chat_id = chat_id;
        this.msg_id = msgId;
    }

    public DownloadManager(long id, long chat_id, long msgId) {
        this.id = id;
        this.chat_id = chat_id;
        this.msg_id = msgId;
    }

    public long getChatID() {
        return this.chat_id;
    }

    public long getID() {
        return this.id;
    }

    public void setChatID(long chat_id) {
        this.chat_id = chat_id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public long getMsg_id() {
        return this.msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
    }

    public static void addMessage(Long dialogId, long msgId) {
        ApplicationLoader.databaseHandler.addMessageToDownloadQueue(new DownloadManager(dialogId.longValue(), msgId));
    }

    public static void deleteMessage(Long dialogId, Long msgId) {
        ApplicationLoader.databaseHandler.deleteMessageFromDownloadQueue(dialogId.longValue(), msgId.longValue());
    }

    public static boolean isInQueue(Long dialogId, long msgId) {
        try {
            if (ApplicationLoader.databaseHandler.getMessageFromDownloadQueue(dialogId.longValue(), msgId) == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            FileLog.e("tmessages", e);
            return false;
        }
    }

    public static ArrayList<DownloadManager> getMessagesFromDownloadQueue() {
        ArrayList<DownloadManager> result = new ArrayList();
        try {
            result = ApplicationLoader.databaseHandler.getMessagesFromDownloadQueue();
            Collections.reverse(result);
            return result;
        } catch (Exception e) {
            FileLog.e("tmessages", e);
            return result;
        }
    }

    public static boolean isDownloadQueueEmpty() {
        try {
            return ApplicationLoader.databaseHandler.isDownloadQueueEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFavouriteMessageCache(MessageObject messageObject) {
        if (!canCache) {
            return false;
        }
        PairDialogMessage tmp = new PairDialogMessage(messageObject.messageOwner.dialog_id, (long) messageObject.messageOwner.id);
        if (favoriteCache.containsKey(tmp)) {
            return ((Boolean) favoriteCache.get(tmp)).booleanValue();
        }
        boolean isFav = isInQueue(Long.valueOf(tmp.getDialogId()), tmp.getMessageId());
        favoriteCache.put(tmp, Boolean.valueOf(isFav));
        return isFav;
    }

    public static void clearCache() {
        favoriteCache.clear();
        canCacheFavoriteFiles(false);
    }

    public static void canCacheFavoriteFiles(boolean value) {
        canCache = value;
    }
}
