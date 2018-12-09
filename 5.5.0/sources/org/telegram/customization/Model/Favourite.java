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

        public PairDialogMessage(long j, long j2) {
            setDialogId(j);
            setMessageId(j2);
        }

        public boolean equals(Object obj) {
            return (obj instanceof PairDialogMessage) && ((PairDialogMessage) obj).getDialogId() == getDialogId() && ((PairDialogMessage) obj).getMessageId() == getMessageId();
        }

        public long getDialogId() {
            return this.dialogId;
        }

        public long getMessageId() {
            return this.messageId;
        }

        public int hashCode() {
            return (TtmlNode.ANONYMOUS_REGION_ID + this.dialogId + "," + this.messageId).hashCode();
        }

        public void setDialogId(long j) {
            this.dialogId = j;
        }

        public void setMessageId(long j) {
            this.messageId = j;
        }
    }

    public Favourite(long j) {
        this.chat_id = j;
    }

    public Favourite(long j, long j2) {
        this.chat_id = j;
        this.msg_id = j2;
    }

    public Favourite(long j, long j2, long j3) {
        this.id = j;
        this.chat_id = j2;
        this.msg_id = j3;
    }

    public Favourite(long j, long j2, long j3, long j4) {
        this.id = j;
        this.chat_id = j2;
        this.msg_id = j3;
        this.cloud_id = j4;
    }

    public static void addFavourite(Long l) {
        ApplicationLoader.databaseHandler.m12219a(new Favourite(l.longValue()));
        purgeFavouritesCache();
    }

    public static void addFavouriteMessage(final Long l, final long j, MessageObject messageObject) {
        if (messageObject == null) {
            ApplicationLoader.databaseHandler.m12221b(new Favourite(l.longValue(), j));
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(messageObject);
        NotificationCenter.getInstance().addObserver(new NotificationCenterDelegate() {
            boolean isOK = false;

            public void didReceivedNotification(int i, Object... objArr) {
                if (i == NotificationCenter.messageReceivedByServer) {
                    try {
                        TLRPC$TL_message tLRPC$TL_message = (TLRPC$TL_message) objArr[2];
                        if (!this.isOK) {
                            this.isOK = true;
                            ApplicationLoader.databaseHandler.m12221b(new Favourite(0, l.longValue(), j, (long) tLRPC$TL_message.id));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, NotificationCenter.messageReceivedByServer);
        SendMessagesHelper.getInstance().sendMessage(arrayList, (long) UserConfig.getClientUserId());
    }

    public static void canCacheFavoriteFiles(boolean z) {
        canCache = z;
    }

    public static void clearCache() {
        favoriteCache.clear();
        canCacheFavoriteFiles(false);
    }

    public static void deleteFavourite(Long l) {
        ApplicationLoader.databaseHandler.m12214a(l);
        purgeFavouritesCache();
    }

    public static void deleteFavouriteMessage(Long l, Long l2) {
        Favourite a = ApplicationLoader.databaseHandler.m12212a(l, l2.longValue());
        if (a == null || a.cloud_id > 0) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf((int) a.cloud_id));
            MessagesController.getInstance().deleteMessages(arrayList, null, null, 0, true);
            return;
        }
        ApplicationLoader.databaseHandler.m12215a(l, l2);
    }

    public static void deleteFavouritesInternal(ArrayList<Integer> arrayList) {
        ApplicationLoader.databaseHandler.m12216a((ArrayList) arrayList);
    }

    public static ArrayList<Favourite> getFavorites() {
        ArrayList<Favourite> a;
        Throwable e;
        ArrayList<Favourite> arrayList = new ArrayList();
        try {
            a = ApplicationLoader.databaseHandler.m12209a();
            try {
                Collections.reverse(a);
            } catch (Exception e2) {
                e = e2;
                FileLog.m13727e("tmessages", e);
                return a;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            a = arrayList;
            e = th;
            FileLog.m13727e("tmessages", e);
            return a;
        }
        return a;
    }

    public static ArrayList<Long> getFavouriteIds() {
        if (favouritesIds == null) {
            List<Favourite> c = ApplicationLoader.databaseHandler.m12223c();
            favouritesIds = new ArrayList();
            for (Favourite chatID : c) {
                favouritesIds.add(Long.valueOf(chatID.getChatID()));
            }
        }
        return favouritesIds;
    }

    public static boolean hasFavoriteMessage() {
        try {
            return ApplicationLoader.databaseHandler.m12222b();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isFavourite(Long l) {
        try {
            return ApplicationLoader.databaseHandler.m12211a(l.longValue()) != null;
        } catch (Throwable e) {
            FileLog.m13727e("tmessages", e);
            return false;
        }
    }

    public static boolean isFavouriteMessage(Long l, long j) {
        return false;
    }

    public static boolean isFavouriteMessageCache(MessageObject messageObject) {
        if (!canCache) {
            return false;
        }
        PairDialogMessage pairDialogMessage = new PairDialogMessage(messageObject.messageOwner.dialog_id, (long) messageObject.messageOwner.id);
        if (favoriteCache.containsKey(pairDialogMessage)) {
            return ((Boolean) favoriteCache.get(pairDialogMessage)).booleanValue();
        }
        boolean isFavouriteMessage = isFavouriteMessage(Long.valueOf(pairDialogMessage.getDialogId()), pairDialogMessage.getMessageId());
        favoriteCache.put(pairDialogMessage, Boolean.valueOf(isFavouriteMessage));
        return isFavouriteMessage;
    }

    public static void purgeFavouritesCache() {
        favouritesIds = null;
    }

    public long getChatID() {
        return this.chat_id;
    }

    public long getCloudId() {
        return this.cloud_id;
    }

    public long getID() {
        return this.id;
    }

    public long getMsg_id() {
        return this.msg_id;
    }

    public void setChatID(long j) {
        this.chat_id = j;
    }

    public void setCloudId(long j) {
        this.cloud_id = j;
    }

    public void setID(long j) {
        this.id = j;
    }

    public void setMsg_id(long j) {
        this.msg_id = j;
    }
}
