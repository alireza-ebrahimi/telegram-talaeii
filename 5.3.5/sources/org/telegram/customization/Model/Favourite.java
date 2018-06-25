package org.telegram.customization.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC$TL_message;

public class Favourite {
    static boolean canCache = false;
    static Map<PairDialogMessage, Boolean> favoriteCache = new HashMap();
    static ArrayList<Long> favouritesIds = null;
    long chat_id;
    long cloud_id = 0;
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

    public Favourite(long chat_id) {
        this.chat_id = chat_id;
    }

    public Favourite(long chat_id, long msgId) {
        this.chat_id = chat_id;
        this.msg_id = msgId;
    }

    public Favourite(long id, long chat_id, long msgId) {
        this.id = id;
        this.chat_id = chat_id;
        this.msg_id = msgId;
    }

    public Favourite(long id, long chat_id, long msgId, long cloud_id) {
        this.id = id;
        this.chat_id = chat_id;
        this.msg_id = msgId;
        this.cloud_id = cloud_id;
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

    public long getCloudId() {
        return this.cloud_id;
    }

    public void setCloudId(long cloud_id) {
        this.cloud_id = cloud_id;
    }

    public static void addFavourite(Long id) {
        ApplicationLoader.databaseHandler.addFavourite(new Favourite(id.longValue()));
        purgeFavouritesCache();
    }

    public static void deleteFavourite(Long id) {
        ApplicationLoader.databaseHandler.deleteFavourite(id);
        purgeFavouritesCache();
    }

    public static boolean isFavourite(Long id) {
        try {
            if (ApplicationLoader.databaseHandler.getFavouriteByChatId(id.longValue()) == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            FileLog.e("tmessages", e);
            return false;
        }
    }

    public static void purgeFavouritesCache() {
        favouritesIds = null;
    }

    public static ArrayList<Long> getFavouriteIds() {
        if (favouritesIds == null) {
            List<Favourite> favourites = ApplicationLoader.databaseHandler.getAllFavourites();
            favouritesIds = new ArrayList();
            for (Favourite fav : favourites) {
                favouritesIds.add(Long.valueOf(fav.getChatID()));
            }
        }
        return favouritesIds;
    }

    public long getMsg_id() {
        return this.msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
    }

    public static void addFavouriteMessage(final Long dialogId, final long msgId, MessageObject msgObj) {
        if (msgObj == null) {
            ApplicationLoader.databaseHandler.addFavouriteMessage(new Favourite(dialogId.longValue(), msgId));
            return;
        }
        ArrayList<MessageObject> msgArr = new ArrayList();
        msgArr.add(msgObj);
        NotificationCenter.getInstance().addObserver(new NotificationCenterDelegate() {
            boolean isOK = false;

            public void didReceivedNotification(int id, Object... args) {
                if (id == NotificationCenter.messageReceivedByServer) {
                    try {
                        TLRPC$TL_message msg = args[2];
                        if (!this.isOK) {
                            this.isOK = true;
                            ApplicationLoader.databaseHandler.addFavouriteMessage(new Favourite(0, dialogId.longValue(), msgId, (long) msg.id));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, NotificationCenter.messageReceivedByServer);
        SendMessagesHelper.getInstance().sendMessage(msgArr, (long) UserConfig.getClientUserId());
    }

    public static void deleteFavouriteMessage(Long dialogId, Long msgId) {
        Favourite fav = ApplicationLoader.databaseHandler.getFavouriteMessageByMessageId(dialogId, msgId.longValue());
        if (fav == null || fav.cloud_id > 0) {
            ArrayList<Integer> cloudIds = new ArrayList();
            cloudIds.add(Integer.valueOf((int) fav.cloud_id));
            MessagesController.getInstance().deleteMessages(cloudIds, null, null, 0, true);
            return;
        }
        ApplicationLoader.databaseHandler.deleteFavouriteMessage(dialogId, msgId);
    }

    public static void deleteFavouritesInternal(ArrayList<Integer> messages) {
        ApplicationLoader.databaseHandler.deleteFavouriteMessagesByIds(messages);
    }

    public static boolean isFavouriteMessage(Long dialogId, long msgId) {
        return false;
    }

    public static ArrayList<Favourite> getFavorites() {
        ArrayList<Favourite> result = new ArrayList();
        try {
            result = ApplicationLoader.databaseHandler.getFavouriteMessages();
            Collections.reverse(result);
            return result;
        } catch (Exception e) {
            FileLog.e("tmessages", e);
            return result;
        }
    }

    public static boolean hasFavoriteMessage() {
        try {
            return ApplicationLoader.databaseHandler.hasFavouriteMessages();
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
        boolean isFav = isFavouriteMessage(Long.valueOf(tmp.getDialogId()), tmp.getMessageId());
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
