package org.telegram.messenger.query;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputDocument;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_messages_allStickers;
import org.telegram.tgnet.TLRPC$TL_messages_archivedStickers;
import org.telegram.tgnet.TLRPC$TL_messages_faveSticker;
import org.telegram.tgnet.TLRPC$TL_messages_favedStickers;
import org.telegram.tgnet.TLRPC$TL_messages_featuredStickers;
import org.telegram.tgnet.TLRPC$TL_messages_getAllStickers;
import org.telegram.tgnet.TLRPC$TL_messages_getArchivedStickers;
import org.telegram.tgnet.TLRPC$TL_messages_getFavedStickers;
import org.telegram.tgnet.TLRPC$TL_messages_getFeaturedStickers;
import org.telegram.tgnet.TLRPC$TL_messages_getMaskStickers;
import org.telegram.tgnet.TLRPC$TL_messages_getRecentStickers;
import org.telegram.tgnet.TLRPC$TL_messages_getSavedGifs;
import org.telegram.tgnet.TLRPC$TL_messages_getStickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_installStickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_readFeaturedStickers;
import org.telegram.tgnet.TLRPC$TL_messages_recentStickers;
import org.telegram.tgnet.TLRPC$TL_messages_saveGif;
import org.telegram.tgnet.TLRPC$TL_messages_savedGifs;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSetInstallResultArchive;
import org.telegram.tgnet.TLRPC$TL_messages_uninstallStickerSet;
import org.telegram.tgnet.TLRPC$TL_stickerPack;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.StickerSet;
import org.telegram.tgnet.TLRPC.StickerSetCovered;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.StickersArchiveAlert;

public class StickersQuery {
    public static final int TYPE_FAVE = 2;
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_MASK = 1;
    private static HashMap<String, ArrayList<Document>> allStickers = new HashMap();
    private static int[] archivedStickersCount = new int[2];
    private static ArrayList<StickerSetCovered> featuredStickerSets = new ArrayList();
    private static HashMap<Long, StickerSetCovered> featuredStickerSetsById = new HashMap();
    private static boolean featuredStickersLoaded;
    private static HashMap<Long, TLRPC$TL_messages_stickerSet> groupStickerSets = new HashMap();
    private static int[] loadDate = new int[2];
    private static int loadFeaturedDate;
    private static int loadFeaturedHash;
    private static int[] loadHash = new int[2];
    private static boolean loadingFeaturedStickers;
    private static boolean loadingRecentGifs;
    private static boolean[] loadingRecentStickers = new boolean[3];
    private static boolean[] loadingStickers = new boolean[2];
    private static ArrayList<Long> readingStickerSets = new ArrayList();
    private static ArrayList<Document> recentGifs = new ArrayList();
    private static boolean recentGifsLoaded;
    private static ArrayList<Document>[] recentStickers = new ArrayList[]{new ArrayList(), new ArrayList(), new ArrayList()};
    private static boolean[] recentStickersLoaded = new boolean[3];
    private static ArrayList<TLRPC$TL_messages_stickerSet>[] stickerSets = new ArrayList[]{new ArrayList(), new ArrayList()};
    private static HashMap<Long, TLRPC$TL_messages_stickerSet> stickerSetsById = new HashMap();
    private static HashMap<String, TLRPC$TL_messages_stickerSet> stickerSetsByName = new HashMap();
    private static HashMap<Long, String> stickersByEmoji = new HashMap();
    private static boolean[] stickersLoaded = new boolean[2];
    private static ArrayList<Long> unreadStickerSets = new ArrayList();

    /* renamed from: org.telegram.messenger.query.StickersQuery$1 */
    static class C36141 implements RequestDelegate {
        C36141() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }
    }

    /* renamed from: org.telegram.messenger.query.StickersQuery$3 */
    static class C36263 implements RequestDelegate {
        C36263() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }
    }

    /* renamed from: org.telegram.messenger.query.StickersQuery$7 */
    static class C36337 implements RequestDelegate {
        C36337() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject != null) {
                final TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) tLObject;
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        try {
                            SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO web_recent_v3 VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            a.m12180d();
                            a.m12176a(1, "s_" + tLRPC$TL_messages_stickerSet.set.id);
                            a.m12174a(2, 6);
                            a.m12176a(3, TtmlNode.ANONYMOUS_REGION_ID);
                            a.m12176a(4, TtmlNode.ANONYMOUS_REGION_ID);
                            a.m12176a(5, TtmlNode.ANONYMOUS_REGION_ID);
                            a.m12174a(6, 0);
                            a.m12174a(7, 0);
                            a.m12174a(8, 0);
                            a.m12174a(9, 0);
                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$TL_messages_stickerSet.getObjectSize());
                            tLRPC$TL_messages_stickerSet.serializeToStream(nativeByteBuffer);
                            a.m12177a(10, nativeByteBuffer);
                            a.m12178b();
                            nativeByteBuffer.reuse();
                            a.m12181e();
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                });
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        StickersQuery.groupStickerSets.put(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id), tLRPC$TL_messages_stickerSet);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.groupStickersDidLoaded, Long.valueOf(tLRPC$TL_messages_stickerSet.set.id));
                    }
                });
            }
        }
    }

    public static void addNewStickerSet(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
        if (!stickerSetsById.containsKey(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id)) && !stickerSetsByName.containsKey(tLRPC$TL_messages_stickerSet.set.short_name)) {
            int i = tLRPC$TL_messages_stickerSet.set.masks ? 1 : 0;
            stickerSets[i].add(0, tLRPC$TL_messages_stickerSet);
            stickerSetsById.put(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id), tLRPC$TL_messages_stickerSet);
            stickerSetsByName.put(tLRPC$TL_messages_stickerSet.set.short_name, tLRPC$TL_messages_stickerSet);
            HashMap hashMap = new HashMap();
            for (int i2 = 0; i2 < tLRPC$TL_messages_stickerSet.documents.size(); i2++) {
                Document document = (Document) tLRPC$TL_messages_stickerSet.documents.get(i2);
                hashMap.put(Long.valueOf(document.id), document);
            }
            for (int i3 = 0; i3 < tLRPC$TL_messages_stickerSet.packs.size(); i3++) {
                ArrayList arrayList;
                TLRPC$TL_stickerPack tLRPC$TL_stickerPack = (TLRPC$TL_stickerPack) tLRPC$TL_messages_stickerSet.packs.get(i3);
                tLRPC$TL_stickerPack.emoticon = tLRPC$TL_stickerPack.emoticon.replace("️", TtmlNode.ANONYMOUS_REGION_ID);
                ArrayList arrayList2 = (ArrayList) allStickers.get(tLRPC$TL_stickerPack.emoticon);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    allStickers.put(tLRPC$TL_stickerPack.emoticon, arrayList2);
                    arrayList = arrayList2;
                } else {
                    arrayList = arrayList2;
                }
                for (int i4 = 0; i4 < tLRPC$TL_stickerPack.documents.size(); i4++) {
                    Long l = (Long) tLRPC$TL_stickerPack.documents.get(i4);
                    if (!stickersByEmoji.containsKey(l)) {
                        stickersByEmoji.put(l, tLRPC$TL_stickerPack.emoticon);
                    }
                    Document document2 = (Document) hashMap.get(l);
                    if (document2 != null) {
                        arrayList.add(document2);
                    }
                }
            }
            loadHash[i] = calcStickersHash(stickerSets[i]);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.stickersDidLoaded, Integer.valueOf(i));
            loadStickers(i, false, true);
        }
    }

    public static void addRecentGif(Document document, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < recentGifs.size(); i3++) {
            Document document2 = (Document) recentGifs.get(i3);
            if (document2.id == document.id) {
                recentGifs.remove(i3);
                recentGifs.add(0, document2);
                i2 = true;
            }
        }
        if (i2 == 0) {
            recentGifs.add(0, document);
        }
        if (recentGifs.size() > MessagesController.getInstance().maxRecentGifsCount) {
            document2 = (Document) recentGifs.remove(recentGifs.size() - 1);
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    try {
                        MessagesStorage.getInstance().getDatabase().m12164a("DELETE FROM web_recent_v3 WHERE id = '" + document2.id + "' AND type = 2").m12179c().m12181e();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(document);
        processLoadedRecentDocuments(0, arrayList, true, i);
    }

    public static void addRecentSticker(final int i, Document document, int i2, boolean z) {
        int i3;
        boolean z2 = false;
        for (int i4 = 0; i4 < recentStickers[i].size(); i4++) {
            Document document2 = (Document) recentStickers[i].get(i4);
            if (document2.id == document.id) {
                recentStickers[i].remove(i4);
                if (!z) {
                    recentStickers[i].add(0, document2);
                }
                z2 = true;
            }
        }
        if (!(z2 || z)) {
            recentStickers[i].add(0, document);
        }
        if (i == 2) {
            if (z) {
                Toast.makeText(ApplicationLoader.applicationContext, LocaleController.getString("RemovedFromFavorites", R.string.RemovedFromFavorites), 0).show();
            } else {
                Toast.makeText(ApplicationLoader.applicationContext, LocaleController.getString("AddedToFavorites", R.string.AddedToFavorites), 0).show();
            }
            TLObject tLRPC$TL_messages_faveSticker = new TLRPC$TL_messages_faveSticker();
            tLRPC$TL_messages_faveSticker.id = new TLRPC$TL_inputDocument();
            tLRPC$TL_messages_faveSticker.id.id = document.id;
            tLRPC$TL_messages_faveSticker.id.access_hash = document.access_hash;
            tLRPC$TL_messages_faveSticker.unfave = z;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_faveSticker, new C36141());
            i3 = MessagesController.getInstance().maxFaveStickersCount;
        } else {
            i3 = MessagesController.getInstance().maxRecentStickersCount;
        }
        if (recentStickers[i].size() > i3 || z) {
            document2 = z ? document : (Document) recentStickers[i].remove(recentStickers[i].size() - 1);
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    int i = i == 0 ? 3 : i == 1 ? 4 : 5;
                    try {
                        MessagesStorage.getInstance().getDatabase().m12164a("DELETE FROM web_recent_v3 WHERE id = '" + document2.id + "' AND type = " + i).m12179c().m12181e();
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
        if (!z) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(document);
            processLoadedRecentDocuments(i, arrayList, false, i2);
        }
        if (i == 2) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.recentDocumentsDidLoaded, Boolean.valueOf(false), Integer.valueOf(i));
        }
    }

    private static int calcDocumentsHash(ArrayList<Document> arrayList) {
        if (arrayList == null) {
            return 0;
        }
        long j = 0;
        for (int i = 0; i < Math.min(Callback.DEFAULT_DRAG_ANIMATION_DURATION, arrayList.size()); i++) {
            Document document = (Document) arrayList.get(i);
            if (document != null) {
                j = (((((((j * 20261) + 2147483648L) + ((long) ((int) (document.id >> 32)))) % 2147483648L) * 20261) + 2147483648L) + ((long) ((int) document.id))) % 2147483648L;
            }
        }
        return (int) j;
    }

    private static int calcFeaturedStickersHash(ArrayList<StickerSetCovered> arrayList) {
        long j = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            StickerSet stickerSet = ((StickerSetCovered) arrayList.get(i)).set;
            if (!stickerSet.archived) {
                j = (((((((j * 20261) + 2147483648L) + ((long) ((int) (stickerSet.id >> 32)))) % 2147483648L) * 20261) + 2147483648L) + ((long) ((int) stickerSet.id))) % 2147483648L;
                if (unreadStickerSets.contains(Long.valueOf(stickerSet.id))) {
                    j = (((j * 20261) + 2147483648L) + 1) % 2147483648L;
                }
            }
        }
        return (int) j;
    }

    public static void calcNewHash(int i) {
        loadHash[i] = calcStickersHash(stickerSets[i]);
    }

    private static int calcStickersHash(ArrayList<TLRPC$TL_messages_stickerSet> arrayList) {
        long j = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            StickerSet stickerSet = ((TLRPC$TL_messages_stickerSet) arrayList.get(i)).set;
            if (!stickerSet.archived) {
                j = (((j * 20261) + 2147483648L) + ((long) stickerSet.hash)) % 2147483648L;
            }
        }
        return (int) j;
    }

    public static boolean canAddStickerToFavorites() {
        return (stickersLoaded[0] && stickerSets[0].size() < 5 && recentStickers[2].isEmpty()) ? false : true;
    }

    public static void checkFeaturedStickers() {
        if (!loadingFeaturedStickers) {
            if (!featuredStickersLoaded || Math.abs((System.currentTimeMillis() / 1000) - ((long) loadFeaturedDate)) >= 3600) {
                loadFeaturesStickers(true, false);
            }
        }
    }

    public static void checkStickers(int i) {
        if (!loadingStickers[i]) {
            if (!stickersLoaded[i] || Math.abs((System.currentTimeMillis() / 1000) - ((long) loadDate[i])) >= 3600) {
                loadStickers(i, true, false);
            }
        }
    }

    public static void cleanup() {
        int i;
        for (i = 0; i < 3; i++) {
            recentStickers[i].clear();
            loadingRecentStickers[i] = false;
            recentStickersLoaded[i] = false;
        }
        for (i = 0; i < 2; i++) {
            loadHash[i] = 0;
            loadDate[i] = 0;
            stickerSets[i].clear();
            loadingStickers[i] = false;
            stickersLoaded[i] = false;
        }
        loadFeaturedDate = 0;
        loadFeaturedHash = 0;
        allStickers.clear();
        stickersByEmoji.clear();
        featuredStickerSetsById.clear();
        featuredStickerSets.clear();
        unreadStickerSets.clear();
        recentGifs.clear();
        stickerSetsById.clear();
        stickerSetsByName.clear();
        loadingFeaturedStickers = false;
        featuredStickersLoaded = false;
        loadingRecentGifs = false;
        recentGifsLoaded = false;
    }

    public static HashMap<String, ArrayList<Document>> getAllStickers() {
        return allStickers;
    }

    public static int getArchivedStickersCount(int i) {
        return archivedStickersCount[i];
    }

    public static String getEmojiForSticker(long j) {
        String str = (String) stickersByEmoji.get(Long.valueOf(j));
        return str != null ? str : TtmlNode.ANONYMOUS_REGION_ID;
    }

    public static ArrayList<StickerSetCovered> getFeaturedStickerSets() {
        return featuredStickerSets;
    }

    public static int getFeaturesStickersHashWithoutUnread() {
        long j = 0;
        for (int i = 0; i < featuredStickerSets.size(); i++) {
            StickerSet stickerSet = ((StickerSetCovered) featuredStickerSets.get(i)).set;
            if (!stickerSet.archived) {
                j = (((((((j * 20261) + 2147483648L) + ((long) ((int) (stickerSet.id >> 32)))) % 2147483648L) * 20261) + 2147483648L) + ((long) ((int) stickerSet.id))) % 2147483648L;
            }
        }
        return (int) j;
    }

    public static TLRPC$TL_messages_stickerSet getGroupStickerSetById(StickerSet stickerSet) {
        TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) stickerSetsById.get(Long.valueOf(stickerSet.id));
        if (tLRPC$TL_messages_stickerSet == null) {
            tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) groupStickerSets.get(Long.valueOf(stickerSet.id));
            if (tLRPC$TL_messages_stickerSet == null || tLRPC$TL_messages_stickerSet.set == null) {
                loadGroupStickerSet(stickerSet, true);
            } else if (tLRPC$TL_messages_stickerSet.set.hash != stickerSet.hash) {
                loadGroupStickerSet(stickerSet, false);
            }
        }
        return tLRPC$TL_messages_stickerSet;
    }

    public static ArrayList<Document> getRecentGifs() {
        return new ArrayList(recentGifs);
    }

    public static ArrayList<Document> getRecentStickers(int i) {
        return new ArrayList(recentStickers[i]);
    }

    public static ArrayList<Document> getRecentStickersNoCopy(int i) {
        return recentStickers[i];
    }

    public static TLRPC$TL_messages_stickerSet getStickerSetById(Long l) {
        return (TLRPC$TL_messages_stickerSet) stickerSetsById.get(l);
    }

    public static TLRPC$TL_messages_stickerSet getStickerSetByName(String str) {
        return (TLRPC$TL_messages_stickerSet) stickerSetsByName.get(str);
    }

    public static long getStickerSetId(Document document) {
        for (int i = 0; i < document.attributes.size(); i++) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                if (documentAttribute.stickerset instanceof TLRPC$TL_inputStickerSetID) {
                    return documentAttribute.stickerset.id;
                }
                return -1;
            }
        }
        return -1;
    }

    public static String getStickerSetName(long j) {
        TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) stickerSetsById.get(Long.valueOf(j));
        if (tLRPC$TL_messages_stickerSet != null) {
            return tLRPC$TL_messages_stickerSet.set.short_name;
        }
        StickerSetCovered stickerSetCovered = (StickerSetCovered) featuredStickerSetsById.get(Long.valueOf(j));
        return stickerSetCovered != null ? stickerSetCovered.set.short_name : null;
    }

    public static ArrayList<TLRPC$TL_messages_stickerSet> getStickerSets(int i) {
        return stickerSets[i];
    }

    public static ArrayList<Long> getUnreadStickerSets() {
        return unreadStickerSets;
    }

    public static boolean isLoadingStickers(int i) {
        return loadingStickers[i];
    }

    public static boolean isStickerInFavorites(Document document) {
        for (int i = 0; i < recentStickers[2].size(); i++) {
            Document document2 = (Document) recentStickers[2].get(i);
            if (document2.id == document.id && document2.dc_id == document.dc_id) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStickerPackInstalled(long j) {
        return stickerSetsById.containsKey(Long.valueOf(j));
    }

    public static boolean isStickerPackInstalled(String str) {
        return stickerSetsByName.containsKey(str);
    }

    public static boolean isStickerPackUnread(long j) {
        return unreadStickerSets.contains(Long.valueOf(j));
    }

    public static void loadArchivedStickersCount(final int i, boolean z) {
        boolean z2 = true;
        if (z) {
            int i2 = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getInt("archivedStickersCount" + i, -1);
            if (i2 == -1) {
                loadArchivedStickersCount(i, false);
                return;
            }
            archivedStickersCount[i] = i2;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.archivedStickersCountDidLoaded, Integer.valueOf(i));
            return;
        }
        TLObject tLRPC$TL_messages_getArchivedStickers = new TLRPC$TL_messages_getArchivedStickers();
        tLRPC$TL_messages_getArchivedStickers.limit = 0;
        if (i != 1) {
            z2 = false;
        }
        tLRPC$TL_messages_getArchivedStickers.masks = z2;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getArchivedStickers, new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            TLRPC$TL_messages_archivedStickers tLRPC$TL_messages_archivedStickers = (TLRPC$TL_messages_archivedStickers) tLObject;
                            StickersQuery.archivedStickersCount[i] = tLRPC$TL_messages_archivedStickers.count;
                            ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("archivedStickersCount" + i, tLRPC$TL_messages_archivedStickers.count).commit();
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.archivedStickersCountDidLoaded, Integer.valueOf(i));
                        }
                    }
                });
            }
        });
    }

    public static void loadFeaturesStickers(boolean z, boolean z2) {
        if (!loadingFeaturedStickers) {
            loadingFeaturedStickers = true;
            if (z) {
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    /* JADX WARNING: inconsistent code. */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void run() {
                        /*
                        r12 = this;
                        r2 = 0;
                        r10 = 1;
                        r0 = 0;
                        r5 = new java.util.ArrayList;
                        r5.<init>();
                        r1 = org.telegram.messenger.MessagesStorage.getInstance();	 Catch:{ Throwable -> 0x007c, all -> 0x0089 }
                        r1 = r1.getDatabase();	 Catch:{ Throwable -> 0x007c, all -> 0x0089 }
                        r3 = "SELECT data, unread, date, hash FROM stickers_featured WHERE 1";
                        r4 = 0;
                        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x007c, all -> 0x0089 }
                        r3 = r1.m12165b(r3, r4);	 Catch:{ Throwable -> 0x007c, all -> 0x0089 }
                        r1 = r3.m12152a();	 Catch:{ Throwable -> 0x0096, all -> 0x0091 }
                        if (r1 == 0) goto L_0x00aa;
                    L_0x0020:
                        r1 = 0;
                        r6 = r3.m12161g(r1);	 Catch:{ Throwable -> 0x0096, all -> 0x0091 }
                        if (r6 == 0) goto L_0x00a8;
                    L_0x0027:
                        r4 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x0096, all -> 0x0091 }
                        r4.<init>();	 Catch:{ Throwable -> 0x0096, all -> 0x0091 }
                        r1 = 0;
                        r2 = r6.readInt32(r1);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        r1 = r0;
                    L_0x0032:
                        if (r1 >= r2) goto L_0x0044;
                    L_0x0034:
                        r7 = 0;
                        r7 = r6.readInt32(r7);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        r8 = 0;
                        r7 = org.telegram.tgnet.TLRPC.StickerSetCovered.TLdeserialize(r6, r7, r8);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        r4.add(r7);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        r1 = r1 + 1;
                        goto L_0x0032;
                    L_0x0044:
                        r6.reuse();	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                    L_0x0047:
                        r1 = 1;
                        r2 = r3.m12161g(r1);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        if (r2 == 0) goto L_0x0068;
                    L_0x004e:
                        r1 = 0;
                        r6 = r2.readInt32(r1);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        r1 = r0;
                    L_0x0054:
                        if (r1 >= r6) goto L_0x0065;
                    L_0x0056:
                        r7 = 0;
                        r8 = r2.readInt64(r7);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        r7 = java.lang.Long.valueOf(r8);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        r5.add(r7);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        r1 = r1 + 1;
                        goto L_0x0054;
                    L_0x0065:
                        r2.reuse();	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                    L_0x0068:
                        r1 = 2;
                        r2 = r3.m12154b(r1);	 Catch:{ Throwable -> 0x009b, all -> 0x0091 }
                        r0 = org.telegram.messenger.query.StickersQuery.calcFeaturedStickersHash(r4);	 Catch:{ Throwable -> 0x00a1, all -> 0x0091 }
                        r1 = r2;
                        r2 = r4;
                    L_0x0073:
                        if (r3 == 0) goto L_0x0078;
                    L_0x0075:
                        r3.m12155b();
                    L_0x0078:
                        org.telegram.messenger.query.StickersQuery.processLoadedFeaturedStickers(r2, r5, r10, r1, r0);
                        return;
                    L_0x007c:
                        r1 = move-exception;
                        r3 = r1;
                        r4 = r2;
                        r1 = r0;
                    L_0x0080:
                        org.telegram.messenger.FileLog.m13728e(r3);	 Catch:{ all -> 0x0093 }
                        if (r4 == 0) goto L_0x0078;
                    L_0x0085:
                        r4.m12155b();
                        goto L_0x0078;
                    L_0x0089:
                        r0 = move-exception;
                        r3 = r2;
                    L_0x008b:
                        if (r3 == 0) goto L_0x0090;
                    L_0x008d:
                        r3.m12155b();
                    L_0x0090:
                        throw r0;
                    L_0x0091:
                        r0 = move-exception;
                        goto L_0x008b;
                    L_0x0093:
                        r0 = move-exception;
                        r3 = r4;
                        goto L_0x008b;
                    L_0x0096:
                        r1 = move-exception;
                        r4 = r3;
                        r3 = r1;
                        r1 = r0;
                        goto L_0x0080;
                    L_0x009b:
                        r1 = move-exception;
                        r2 = r4;
                        r4 = r3;
                        r3 = r1;
                        r1 = r0;
                        goto L_0x0080;
                    L_0x00a1:
                        r1 = move-exception;
                        r11 = r1;
                        r1 = r2;
                        r2 = r4;
                        r4 = r3;
                        r3 = r11;
                        goto L_0x0080;
                    L_0x00a8:
                        r4 = r2;
                        goto L_0x0047;
                    L_0x00aa:
                        r1 = r0;
                        goto L_0x0073;
                        */
                        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.query.StickersQuery.14.run():void");
                    }
                });
                return;
            }
            final TLObject tLRPC$TL_messages_getFeaturedStickers = new TLRPC$TL_messages_getFeaturedStickers();
            tLRPC$TL_messages_getFeaturedStickers.hash = z2 ? 0 : loadFeaturedHash;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getFeaturedStickers, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLObject instanceof TLRPC$TL_messages_featuredStickers) {
                                TLRPC$TL_messages_featuredStickers tLRPC$TL_messages_featuredStickers = (TLRPC$TL_messages_featuredStickers) tLObject;
                                StickersQuery.processLoadedFeaturedStickers(tLRPC$TL_messages_featuredStickers.sets, tLRPC$TL_messages_featuredStickers.unread, false, (int) (System.currentTimeMillis() / 1000), tLRPC$TL_messages_featuredStickers.hash);
                                return;
                            }
                            StickersQuery.processLoadedFeaturedStickers(null, null, false, (int) (System.currentTimeMillis() / 1000), tLRPC$TL_messages_getFeaturedStickers.hash);
                        }
                    });
                }
            });
        }
    }

    private static void loadGroupStickerSet(final StickerSet stickerSet, boolean z) {
        if (z) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = null;
                    try {
                        SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b("SELECT document FROM web_recent_v3 WHERE id = 's_" + stickerSet.id + "'", new Object[0]);
                        if (b.m12152a() && !b.m12153a(0)) {
                            AbstractSerializedData g = b.m12161g(0);
                            if (g != null) {
                                tLRPC$TL_messages_stickerSet = TLRPC$TL_messages_stickerSet.TLdeserialize(g, g.readInt32(false), false);
                                g.reuse();
                            }
                        }
                        b.m12155b();
                        if (tLRPC$TL_messages_stickerSet == null || tLRPC$TL_messages_stickerSet.set == null || tLRPC$TL_messages_stickerSet.set.hash != stickerSet.hash) {
                            StickersQuery.loadGroupStickerSet(stickerSet, false);
                        }
                        if (tLRPC$TL_messages_stickerSet != null && tLRPC$TL_messages_stickerSet.set != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    StickersQuery.groupStickerSets.put(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id), tLRPC$TL_messages_stickerSet);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.groupStickersDidLoaded, Long.valueOf(tLRPC$TL_messages_stickerSet.set.id));
                                }
                            });
                        }
                    } catch (Throwable th) {
                        FileLog.m13728e(th);
                    }
                }
            });
            return;
        }
        TLObject tLRPC$TL_messages_getStickerSet = new TLRPC$TL_messages_getStickerSet();
        tLRPC$TL_messages_getStickerSet.stickerset = new TLRPC$TL_inputStickerSetID();
        tLRPC$TL_messages_getStickerSet.stickerset.id = stickerSet.id;
        tLRPC$TL_messages_getStickerSet.stickerset.access_hash = stickerSet.access_hash;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getStickerSet, new C36337());
    }

    public static void loadRecents(final int i, final boolean z, boolean z2, boolean z3) {
        if (z) {
            if (!loadingRecentGifs) {
                loadingRecentGifs = true;
                if (recentGifsLoaded) {
                    z2 = false;
                }
            } else {
                return;
            }
        } else if (!loadingRecentStickers[i]) {
            loadingRecentStickers[i] = true;
            if (recentStickersLoaded[i]) {
                z2 = false;
            }
        } else {
            return;
        }
        if (z2) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    try {
                        int i = z ? 2 : i == 0 ? 3 : i == 1 ? 4 : 5;
                        SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b("SELECT document FROM web_recent_v3 WHERE type = " + i + " ORDER BY date DESC", new Object[0]);
                        final ArrayList arrayList = new ArrayList();
                        while (b.m12152a()) {
                            if (!b.m12153a(0)) {
                                AbstractSerializedData g = b.m12161g(0);
                                if (g != null) {
                                    Document TLdeserialize = Document.TLdeserialize(g, g.readInt32(false), false);
                                    if (TLdeserialize != null) {
                                        arrayList.add(TLdeserialize);
                                    }
                                    g.reuse();
                                }
                            }
                        }
                        b.m12155b();
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (z) {
                                    StickersQuery.recentGifs = arrayList;
                                    StickersQuery.loadingRecentGifs = false;
                                    StickersQuery.recentGifsLoaded = true;
                                } else {
                                    StickersQuery.recentStickers[i] = arrayList;
                                    StickersQuery.loadingRecentStickers[i] = false;
                                    StickersQuery.recentStickersLoaded[i] = true;
                                }
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.recentDocumentsDidLoaded, Boolean.valueOf(z), Integer.valueOf(i));
                                StickersQuery.loadRecents(i, z, false, false);
                            }
                        });
                    } catch (Throwable th) {
                        FileLog.m13728e(th);
                    }
                }
            });
            return;
        }
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0);
        if (!z3) {
            long j = z ? sharedPreferences.getLong("lastGifLoadTime", 0) : i == 0 ? sharedPreferences.getLong("lastStickersLoadTime", 0) : i == 1 ? sharedPreferences.getLong("lastStickersLoadTimeMask", 0) : sharedPreferences.getLong("lastStickersLoadTimeFavs", 0);
            if (Math.abs(System.currentTimeMillis() - j) < 3600000) {
                if (z) {
                    loadingRecentGifs = false;
                    return;
                } else {
                    loadingRecentStickers[i] = false;
                    return;
                }
            }
        }
        if (z) {
            TLObject tLRPC$TL_messages_getSavedGifs = new TLRPC$TL_messages_getSavedGifs();
            tLRPC$TL_messages_getSavedGifs.hash = calcDocumentsHash(recentGifs);
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getSavedGifs, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    ArrayList arrayList = null;
                    if (tLObject instanceof TLRPC$TL_messages_savedGifs) {
                        arrayList = ((TLRPC$TL_messages_savedGifs) tLObject).gifs;
                    }
                    StickersQuery.processLoadedRecentDocuments(i, arrayList, z, 0);
                }
            });
            return;
        }
        if (i == 2) {
            tLRPC$TL_messages_getSavedGifs = new TLRPC$TL_messages_getFavedStickers();
            tLRPC$TL_messages_getSavedGifs.hash = calcDocumentsHash(recentStickers[i]);
        } else {
            TLObject tLRPC$TL_messages_getRecentStickers = new TLRPC$TL_messages_getRecentStickers();
            tLRPC$TL_messages_getRecentStickers.hash = calcDocumentsHash(recentStickers[i]);
            tLRPC$TL_messages_getRecentStickers.attached = i == 1;
            tLRPC$TL_messages_getSavedGifs = tLRPC$TL_messages_getRecentStickers;
        }
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getSavedGifs, new RequestDelegate() {
            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                ArrayList arrayList = null;
                if (i == 2) {
                    if (tLObject instanceof TLRPC$TL_messages_favedStickers) {
                        arrayList = ((TLRPC$TL_messages_favedStickers) tLObject).stickers;
                    }
                } else if (tLObject instanceof TLRPC$TL_messages_recentStickers) {
                    arrayList = ((TLRPC$TL_messages_recentStickers) tLObject).stickers;
                }
                StickersQuery.processLoadedRecentDocuments(i, arrayList, z, 0);
            }
        });
    }

    public static void loadStickers(final int i, boolean z, boolean z2) {
        int i2 = 0;
        if (!loadingStickers[i]) {
            loadArchivedStickersCount(i, z);
            loadingStickers[i] = true;
            if (z) {
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    /* JADX WARNING: inconsistent code. */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void run() {
                        /*
                        r10 = this;
                        r2 = 0;
                        r8 = 1;
                        r0 = 0;
                        r1 = org.telegram.messenger.MessagesStorage.getInstance();	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r1 = r1.getDatabase();	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r3.<init>();	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r4 = "SELECT data, date, hash FROM stickers_v2 WHERE id = ";
                        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r4 = r4;	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r4 = r4 + 1;
                        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r3 = r3.toString();	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r4 = 0;
                        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r3 = r1.m12165b(r3, r4);	 Catch:{ Throwable -> 0x006d, all -> 0x007a }
                        r1 = r3.m12152a();	 Catch:{ Throwable -> 0x0087, all -> 0x0082 }
                        if (r1 == 0) goto L_0x009b;
                    L_0x0030:
                        r1 = 0;
                        r5 = r3.m12161g(r1);	 Catch:{ Throwable -> 0x0087, all -> 0x0082 }
                        if (r5 == 0) goto L_0x0099;
                    L_0x0037:
                        r4 = new java.util.ArrayList;	 Catch:{ Throwable -> 0x0087, all -> 0x0082 }
                        r4.<init>();	 Catch:{ Throwable -> 0x0087, all -> 0x0082 }
                        r1 = 0;
                        r2 = r5.readInt32(r1);	 Catch:{ Throwable -> 0x008c, all -> 0x0082 }
                        r1 = r0;
                    L_0x0042:
                        if (r1 >= r2) goto L_0x0054;
                    L_0x0044:
                        r6 = 0;
                        r6 = r5.readInt32(r6);	 Catch:{ Throwable -> 0x008c, all -> 0x0082 }
                        r7 = 0;
                        r6 = org.telegram.tgnet.TLRPC$TL_messages_stickerSet.TLdeserialize(r5, r6, r7);	 Catch:{ Throwable -> 0x008c, all -> 0x0082 }
                        r4.add(r6);	 Catch:{ Throwable -> 0x008c, all -> 0x0082 }
                        r1 = r1 + 1;
                        goto L_0x0042;
                    L_0x0054:
                        r5.reuse();	 Catch:{ Throwable -> 0x008c, all -> 0x0082 }
                    L_0x0057:
                        r1 = 1;
                        r2 = r3.m12154b(r1);	 Catch:{ Throwable -> 0x008c, all -> 0x0082 }
                        r0 = org.telegram.messenger.query.StickersQuery.calcStickersHash(r4);	 Catch:{ Throwable -> 0x0092, all -> 0x0082 }
                        r1 = r2;
                        r2 = r4;
                    L_0x0062:
                        if (r3 == 0) goto L_0x0067;
                    L_0x0064:
                        r3.m12155b();
                    L_0x0067:
                        r3 = r4;
                        org.telegram.messenger.query.StickersQuery.processLoadedStickers(r3, r2, r8, r1, r0);
                        return;
                    L_0x006d:
                        r1 = move-exception;
                        r3 = r1;
                        r4 = r2;
                        r1 = r0;
                    L_0x0071:
                        org.telegram.messenger.FileLog.m13728e(r3);	 Catch:{ all -> 0x0084 }
                        if (r4 == 0) goto L_0x0067;
                    L_0x0076:
                        r4.m12155b();
                        goto L_0x0067;
                    L_0x007a:
                        r0 = move-exception;
                        r3 = r2;
                    L_0x007c:
                        if (r3 == 0) goto L_0x0081;
                    L_0x007e:
                        r3.m12155b();
                    L_0x0081:
                        throw r0;
                    L_0x0082:
                        r0 = move-exception;
                        goto L_0x007c;
                    L_0x0084:
                        r0 = move-exception;
                        r3 = r4;
                        goto L_0x007c;
                    L_0x0087:
                        r1 = move-exception;
                        r4 = r3;
                        r3 = r1;
                        r1 = r0;
                        goto L_0x0071;
                    L_0x008c:
                        r1 = move-exception;
                        r2 = r4;
                        r4 = r3;
                        r3 = r1;
                        r1 = r0;
                        goto L_0x0071;
                    L_0x0092:
                        r1 = move-exception;
                        r9 = r1;
                        r1 = r2;
                        r2 = r4;
                        r4 = r3;
                        r3 = r9;
                        goto L_0x0071;
                    L_0x0099:
                        r4 = r2;
                        goto L_0x0057;
                    L_0x009b:
                        r1 = r0;
                        goto L_0x0062;
                        */
                        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.query.StickersQuery.23.run():void");
                    }
                });
                return;
            }
            TLObject tLRPC$TL_messages_getAllStickers;
            if (i == 0) {
                tLRPC$TL_messages_getAllStickers = new TLRPC$TL_messages_getAllStickers();
                TLRPC$TL_messages_getAllStickers tLRPC$TL_messages_getAllStickers2 = (TLRPC$TL_messages_getAllStickers) tLRPC$TL_messages_getAllStickers;
                if (!z2) {
                    i2 = loadHash[i];
                }
                tLRPC$TL_messages_getAllStickers2.hash = i2;
            } else {
                tLRPC$TL_messages_getAllStickers = new TLRPC$TL_messages_getMaskStickers();
                TLRPC$TL_messages_getMaskStickers tLRPC$TL_messages_getMaskStickers = (TLRPC$TL_messages_getMaskStickers) tLRPC$TL_messages_getAllStickers;
                if (!z2) {
                    i2 = loadHash[i];
                }
                tLRPC$TL_messages_getMaskStickers.hash = i2;
            }
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getAllStickers, new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLObject instanceof TLRPC$TL_messages_allStickers) {
                                final TLRPC$TL_messages_allStickers tLRPC$TL_messages_allStickers = (TLRPC$TL_messages_allStickers) tLObject;
                                final ArrayList arrayList = new ArrayList();
                                if (tLRPC$TL_messages_allStickers.sets.isEmpty()) {
                                    StickersQuery.processLoadedStickers(i, arrayList, false, (int) (System.currentTimeMillis() / 1000), tLRPC$TL_messages_allStickers.hash);
                                    return;
                                }
                                final HashMap hashMap = new HashMap();
                                for (int i = 0; i < tLRPC$TL_messages_allStickers.sets.size(); i++) {
                                    final StickerSet stickerSet = (StickerSet) tLRPC$TL_messages_allStickers.sets.get(i);
                                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) StickersQuery.stickerSetsById.get(Long.valueOf(stickerSet.id));
                                    if (tLRPC$TL_messages_stickerSet == null || tLRPC$TL_messages_stickerSet.set.hash != stickerSet.hash) {
                                        arrayList.add(null);
                                        TLObject tLRPC$TL_messages_getStickerSet = new TLRPC$TL_messages_getStickerSet();
                                        tLRPC$TL_messages_getStickerSet.stickerset = new TLRPC$TL_inputStickerSetID();
                                        tLRPC$TL_messages_getStickerSet.stickerset.id = stickerSet.id;
                                        tLRPC$TL_messages_getStickerSet.stickerset.access_hash = stickerSet.access_hash;
                                        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getStickerSet, new RequestDelegate() {
                                            public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                                AndroidUtilities.runOnUIThread(new Runnable() {
                                                    public void run() {
                                                        TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) tLObject;
                                                        arrayList.set(i, tLRPC$TL_messages_stickerSet);
                                                        hashMap.put(Long.valueOf(stickerSet.id), tLRPC$TL_messages_stickerSet);
                                                        if (hashMap.size() == tLRPC$TL_messages_allStickers.sets.size()) {
                                                            for (int i = 0; i < arrayList.size(); i++) {
                                                                if (arrayList.get(i) == null) {
                                                                    arrayList.remove(i);
                                                                }
                                                            }
                                                            StickersQuery.processLoadedStickers(i, arrayList, false, (int) (System.currentTimeMillis() / 1000), tLRPC$TL_messages_allStickers.hash);
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    } else {
                                        tLRPC$TL_messages_stickerSet.set.archived = stickerSet.archived;
                                        tLRPC$TL_messages_stickerSet.set.installed = stickerSet.installed;
                                        tLRPC$TL_messages_stickerSet.set.official = stickerSet.official;
                                        hashMap.put(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id), tLRPC$TL_messages_stickerSet);
                                        arrayList.add(tLRPC$TL_messages_stickerSet);
                                        if (hashMap.size() == tLRPC$TL_messages_allStickers.sets.size()) {
                                            StickersQuery.processLoadedStickers(i, arrayList, false, (int) (System.currentTimeMillis() / 1000), tLRPC$TL_messages_allStickers.hash);
                                        }
                                    }
                                }
                                return;
                            }
                            StickersQuery.processLoadedStickers(i, null, false, (int) (System.currentTimeMillis() / 1000), i2);
                        }
                    });
                }
            });
        }
    }

    public static void markFaturedStickersAsRead(boolean z) {
        if (!unreadStickerSets.isEmpty()) {
            unreadStickerSets.clear();
            loadFeaturedHash = calcFeaturedStickersHash(featuredStickerSets);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.featuredStickersDidLoaded, new Object[0]);
            putFeaturedStickersToCache(featuredStickerSets, unreadStickerSets, loadFeaturedDate, loadFeaturedHash);
            if (z) {
                ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_messages_readFeaturedStickers(), new RequestDelegate() {
                    public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    }
                });
            }
        }
    }

    public static void markFaturedStickersByIdAsRead(final long j) {
        if (unreadStickerSets.contains(Long.valueOf(j)) && !readingStickerSets.contains(Long.valueOf(j))) {
            readingStickerSets.add(Long.valueOf(j));
            TLObject tLRPC$TL_messages_readFeaturedStickers = new TLRPC$TL_messages_readFeaturedStickers();
            tLRPC$TL_messages_readFeaturedStickers.id.add(Long.valueOf(j));
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_readFeaturedStickers, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                }
            });
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    StickersQuery.unreadStickerSets.remove(Long.valueOf(j));
                    StickersQuery.readingStickerSets.remove(Long.valueOf(j));
                    StickersQuery.loadFeaturedHash = StickersQuery.calcFeaturedStickersHash(StickersQuery.featuredStickerSets);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.featuredStickersDidLoaded, new Object[0]);
                    StickersQuery.putFeaturedStickersToCache(StickersQuery.featuredStickerSets, StickersQuery.unreadStickerSets, StickersQuery.loadFeaturedDate, StickersQuery.loadFeaturedHash);
                }
            }, 1000);
        }
    }

    private static void processLoadedFeaturedStickers(ArrayList<StickerSetCovered> arrayList, ArrayList<Long> arrayList2, boolean z, int i, int i2) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                StickersQuery.loadingFeaturedStickers = false;
                StickersQuery.featuredStickersLoaded = true;
            }
        });
        final boolean z2 = z;
        final ArrayList<StickerSetCovered> arrayList3 = arrayList;
        final int i3 = i;
        final int i4 = i2;
        final ArrayList<Long> arrayList4 = arrayList2;
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.query.StickersQuery$17$1 */
            class C36111 implements Runnable {
                C36111() {
                }

                public void run() {
                    if (!(arrayList3 == null || i4 == 0)) {
                        StickersQuery.loadFeaturedHash = i4;
                    }
                    StickersQuery.loadFeaturesStickers(false, false);
                }
            }

            /* renamed from: org.telegram.messenger.query.StickersQuery$17$3 */
            class C36133 implements Runnable {
                C36133() {
                }

                public void run() {
                    StickersQuery.loadFeaturedDate = i3;
                }
            }

            public void run() {
                long j = 1000;
                if ((z2 && (arrayList3 == null || Math.abs((System.currentTimeMillis() / 1000) - ((long) i3)) >= 3600)) || (!z2 && arrayList3 == null && i4 == 0)) {
                    Runnable c36111 = new C36111();
                    if (arrayList3 != null || z2) {
                        j = 0;
                    }
                    AndroidUtilities.runOnUIThread(c36111, j);
                    if (arrayList3 == null) {
                        return;
                    }
                }
                if (arrayList3 != null) {
                    try {
                        final ArrayList arrayList = new ArrayList();
                        final HashMap hashMap = new HashMap();
                        for (int i = 0; i < arrayList3.size(); i++) {
                            StickerSetCovered stickerSetCovered = (StickerSetCovered) arrayList3.get(i);
                            arrayList.add(stickerSetCovered);
                            hashMap.put(Long.valueOf(stickerSetCovered.set.id), stickerSetCovered);
                        }
                        if (!z2) {
                            StickersQuery.putFeaturedStickersToCache(arrayList, arrayList4, i3, i4);
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                StickersQuery.unreadStickerSets = arrayList4;
                                StickersQuery.featuredStickerSetsById = hashMap;
                                StickersQuery.featuredStickerSets = arrayList;
                                StickersQuery.loadFeaturedHash = i4;
                                StickersQuery.loadFeaturedDate = i3;
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.featuredStickersDidLoaded, new Object[0]);
                            }
                        });
                    } catch (Throwable th) {
                        FileLog.m13728e(th);
                    }
                } else if (!z2) {
                    AndroidUtilities.runOnUIThread(new C36133());
                    StickersQuery.putFeaturedStickersToCache(null, null, i3, 0);
                }
            }
        });
    }

    private static void processLoadedRecentDocuments(final int i, final ArrayList<Document> arrayList, final boolean z, final int i2) {
        if (arrayList != null) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    int i = 3;
                    int i2 = 0;
                    try {
                        SQLiteDatabase database = MessagesStorage.getInstance().getDatabase();
                        int i3 = z ? MessagesController.getInstance().maxRecentGifsCount : i == 2 ? MessagesController.getInstance().maxFaveStickersCount : MessagesController.getInstance().maxRecentStickersCount;
                        database.m12168d();
                        SQLitePreparedStatement a = database.m12164a("REPLACE INTO web_recent_v3 VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        int size = arrayList.size();
                        if (z) {
                            i = 2;
                        } else if (i != 0) {
                            i = i == 1 ? 4 : 5;
                        }
                        while (i2 < size && i2 != i3) {
                            Document document = (Document) arrayList.get(i2);
                            a.m12180d();
                            a.m12176a(1, TtmlNode.ANONYMOUS_REGION_ID + document.id);
                            a.m12174a(2, i);
                            a.m12176a(3, TtmlNode.ANONYMOUS_REGION_ID);
                            a.m12176a(4, TtmlNode.ANONYMOUS_REGION_ID);
                            a.m12176a(5, TtmlNode.ANONYMOUS_REGION_ID);
                            a.m12174a(6, 0);
                            a.m12174a(7, 0);
                            a.m12174a(8, 0);
                            a.m12174a(9, i2 != 0 ? i2 : size - i2);
                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(document.getObjectSize());
                            document.serializeToStream(nativeByteBuffer);
                            a.m12177a(10, nativeByteBuffer);
                            a.m12178b();
                            if (nativeByteBuffer != null) {
                                nativeByteBuffer.reuse();
                            }
                            i2++;
                        }
                        a.m12181e();
                        database.m12169e();
                        if (arrayList.size() >= i3) {
                            database.m12168d();
                            for (int i4 = i3; i4 < arrayList.size(); i4++) {
                                database.m12164a("DELETE FROM web_recent_v3 WHERE id = '" + ((Document) arrayList.get(i4)).id + "' AND type = " + i).m12179c().m12181e();
                            }
                            database.m12169e();
                        }
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
            });
        }
        if (i2 == 0) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit();
                    if (z) {
                        StickersQuery.loadingRecentGifs = false;
                        StickersQuery.recentGifsLoaded = true;
                        edit.putLong("lastGifLoadTime", System.currentTimeMillis()).commit();
                    } else {
                        StickersQuery.loadingRecentStickers[i] = false;
                        StickersQuery.recentStickersLoaded[i] = true;
                        if (i == 0) {
                            edit.putLong("lastStickersLoadTime", System.currentTimeMillis()).commit();
                        } else if (i == 1) {
                            edit.putLong("lastStickersLoadTimeMask", System.currentTimeMillis()).commit();
                        } else {
                            edit.putLong("lastStickersLoadTimeFavs", System.currentTimeMillis()).commit();
                        }
                    }
                    if (arrayList != null) {
                        if (z) {
                            StickersQuery.recentGifs = arrayList;
                        } else {
                            StickersQuery.recentStickers[i] = arrayList;
                        }
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.recentDocumentsDidLoaded, Boolean.valueOf(z), Integer.valueOf(i));
                    }
                }
            });
        }
    }

    private static void processLoadedStickers(final int i, ArrayList<TLRPC$TL_messages_stickerSet> arrayList, boolean z, int i2, int i3) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                StickersQuery.loadingStickers[i] = false;
                StickersQuery.stickersLoaded[i] = true;
            }
        });
        final boolean z2 = z;
        final ArrayList<TLRPC$TL_messages_stickerSet> arrayList2 = arrayList;
        final int i4 = i2;
        final int i5 = i3;
        final int i6 = i;
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.query.StickersQuery$27$1 */
            class C36191 implements Runnable {
                C36191() {
                }

                public void run() {
                    if (!(arrayList2 == null || i5 == 0)) {
                        StickersQuery.loadHash[i6] = i5;
                    }
                    StickersQuery.loadStickers(i6, false, false);
                }
            }

            /* renamed from: org.telegram.messenger.query.StickersQuery$27$3 */
            class C36213 implements Runnable {
                C36213() {
                }

                public void run() {
                    StickersQuery.loadDate[i6] = i4;
                }
            }

            public void run() {
                long j = 1000;
                if ((z2 && (arrayList2 == null || Math.abs((System.currentTimeMillis() / 1000) - ((long) i4)) >= 3600)) || (!z2 && arrayList2 == null && i5 == 0)) {
                    Runnable c36191 = new C36191();
                    if (arrayList2 != null || z2) {
                        j = 0;
                    }
                    AndroidUtilities.runOnUIThread(c36191, j);
                    if (arrayList2 == null) {
                        return;
                    }
                }
                if (arrayList2 != null) {
                    try {
                        final ArrayList arrayList = new ArrayList();
                        final HashMap hashMap = new HashMap();
                        final HashMap hashMap2 = new HashMap();
                        final HashMap hashMap3 = new HashMap();
                        HashMap hashMap4 = new HashMap();
                        final HashMap hashMap5 = new HashMap();
                        for (int i = 0; i < arrayList2.size(); i++) {
                            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) arrayList2.get(i);
                            if (tLRPC$TL_messages_stickerSet != null) {
                                arrayList.add(tLRPC$TL_messages_stickerSet);
                                hashMap.put(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id), tLRPC$TL_messages_stickerSet);
                                hashMap2.put(tLRPC$TL_messages_stickerSet.set.short_name, tLRPC$TL_messages_stickerSet);
                                for (int i2 = 0; i2 < tLRPC$TL_messages_stickerSet.documents.size(); i2++) {
                                    Document document = (Document) tLRPC$TL_messages_stickerSet.documents.get(i2);
                                    if (!(document == null || (document instanceof TLRPC$TL_documentEmpty))) {
                                        hashMap4.put(Long.valueOf(document.id), document);
                                    }
                                }
                                if (!tLRPC$TL_messages_stickerSet.set.archived) {
                                    for (int i3 = 0; i3 < tLRPC$TL_messages_stickerSet.packs.size(); i3++) {
                                        TLRPC$TL_stickerPack tLRPC$TL_stickerPack = (TLRPC$TL_stickerPack) tLRPC$TL_messages_stickerSet.packs.get(i3);
                                        if (!(tLRPC$TL_stickerPack == null || tLRPC$TL_stickerPack.emoticon == null)) {
                                            ArrayList arrayList2;
                                            tLRPC$TL_stickerPack.emoticon = tLRPC$TL_stickerPack.emoticon.replace("️", TtmlNode.ANONYMOUS_REGION_ID);
                                            ArrayList arrayList3 = (ArrayList) hashMap5.get(tLRPC$TL_stickerPack.emoticon);
                                            if (arrayList3 == null) {
                                                arrayList3 = new ArrayList();
                                                hashMap5.put(tLRPC$TL_stickerPack.emoticon, arrayList3);
                                                arrayList2 = arrayList3;
                                            } else {
                                                arrayList2 = arrayList3;
                                            }
                                            for (int i4 = 0; i4 < tLRPC$TL_stickerPack.documents.size(); i4++) {
                                                Long l = (Long) tLRPC$TL_stickerPack.documents.get(i4);
                                                if (!hashMap3.containsKey(l)) {
                                                    hashMap3.put(l, tLRPC$TL_stickerPack.emoticon);
                                                }
                                                Document document2 = (Document) hashMap4.get(l);
                                                if (document2 != null) {
                                                    arrayList2.add(document2);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (!z2) {
                            StickersQuery.putStickersToCache(i6, arrayList, i4, i5);
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                for (int i = 0; i < StickersQuery.stickerSets[i6].size(); i++) {
                                    StickerSet stickerSet = ((TLRPC$TL_messages_stickerSet) StickersQuery.stickerSets[i6].get(i)).set;
                                    StickersQuery.stickerSetsById.remove(Long.valueOf(stickerSet.id));
                                    StickersQuery.stickerSetsByName.remove(stickerSet.short_name);
                                }
                                StickersQuery.stickerSetsById.putAll(hashMap);
                                StickersQuery.stickerSetsByName.putAll(hashMap2);
                                StickersQuery.stickerSets[i6] = arrayList;
                                StickersQuery.loadHash[i6] = i5;
                                StickersQuery.loadDate[i6] = i4;
                                if (i6 == 0) {
                                    StickersQuery.allStickers = hashMap5;
                                    StickersQuery.stickersByEmoji = hashMap3;
                                }
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.stickersDidLoaded, Integer.valueOf(i6));
                            }
                        });
                    } catch (Throwable th) {
                        FileLog.m13728e(th);
                    }
                } else if (!z2) {
                    AndroidUtilities.runOnUIThread(new C36213());
                    StickersQuery.putStickersToCache(i6, null, i4, 0);
                }
            }
        });
    }

    private static void putFeaturedStickersToCache(ArrayList<StickerSetCovered> arrayList, final ArrayList<Long> arrayList2, final int i, final int i2) {
        final ArrayList arrayList3 = arrayList != null ? new ArrayList(arrayList) : null;
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    if (arrayList3 != null) {
                        int i2;
                        SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO stickers_featured VALUES(?, ?, ?, ?, ?)");
                        a.m12180d();
                        int i3 = 4;
                        for (i2 = 0; i2 < arrayList3.size(); i2++) {
                            i3 += ((StickerSetCovered) arrayList3.get(i2)).getObjectSize();
                        }
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(i3);
                        NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer((arrayList2.size() * 8) + 4);
                        nativeByteBuffer.writeInt32(arrayList3.size());
                        for (i2 = 0; i2 < arrayList3.size(); i2++) {
                            ((StickerSetCovered) arrayList3.get(i2)).serializeToStream(nativeByteBuffer);
                        }
                        nativeByteBuffer2.writeInt32(arrayList2.size());
                        while (i < arrayList2.size()) {
                            nativeByteBuffer2.writeInt64(((Long) arrayList2.get(i)).longValue());
                            i++;
                        }
                        a.m12174a(1, 1);
                        a.m12177a(2, nativeByteBuffer);
                        a.m12177a(3, nativeByteBuffer2);
                        a.m12174a(4, i);
                        a.m12174a(5, i2);
                        a.m12178b();
                        nativeByteBuffer.reuse();
                        nativeByteBuffer2.reuse();
                        a.m12181e();
                        return;
                    }
                    SQLitePreparedStatement a2 = MessagesStorage.getInstance().getDatabase().m12164a("UPDATE stickers_featured SET date = ?");
                    a2.m12180d();
                    a2.m12174a(1, i);
                    a2.m12178b();
                    a2.m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public static void putGroupStickerSet(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
        groupStickerSets.put(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id), tLRPC$TL_messages_stickerSet);
    }

    private static void putStickersToCache(final int i, ArrayList<TLRPC$TL_messages_stickerSet> arrayList, final int i2, final int i3) {
        final ArrayList arrayList2 = arrayList != null ? new ArrayList(arrayList) : null;
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    if (arrayList2 != null) {
                        SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO stickers_v2 VALUES(?, ?, ?, ?)");
                        a.m12180d();
                        int i2 = 4;
                        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                            i2 += ((TLRPC$TL_messages_stickerSet) arrayList2.get(i3)).getObjectSize();
                        }
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(i2);
                        nativeByteBuffer.writeInt32(arrayList2.size());
                        while (i < arrayList2.size()) {
                            ((TLRPC$TL_messages_stickerSet) arrayList2.get(i)).serializeToStream(nativeByteBuffer);
                            i++;
                        }
                        a.m12174a(1, i == 0 ? 1 : 2);
                        a.m12177a(2, nativeByteBuffer);
                        a.m12174a(3, i2);
                        a.m12174a(4, i3);
                        a.m12178b();
                        nativeByteBuffer.reuse();
                        a.m12181e();
                        return;
                    }
                    SQLitePreparedStatement a2 = MessagesStorage.getInstance().getDatabase().m12164a("UPDATE stickers_v2 SET date = ?");
                    a2.m12180d();
                    a2.m12174a(1, i2);
                    a2.m12178b();
                    a2.m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public static void removeRecentGif(final Document document) {
        recentGifs.remove(document);
        TLObject tLRPC$TL_messages_saveGif = new TLRPC$TL_messages_saveGif();
        tLRPC$TL_messages_saveGif.id = new TLRPC$TL_inputDocument();
        tLRPC$TL_messages_saveGif.id.id = document.id;
        tLRPC$TL_messages_saveGif.id.access_hash = document.access_hash;
        tLRPC$TL_messages_saveGif.unsave = true;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_saveGif, new C36263());
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.getInstance().getDatabase().m12164a("DELETE FROM web_recent_v3 WHERE id = '" + document.id + "' AND type = 2").m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public static void removeStickersSet(final Context context, final StickerSet stickerSet, final int i, final BaseFragment baseFragment, final boolean z) {
        boolean z2 = true;
        final int i2 = stickerSet.masks ? 1 : 0;
        InputStickerSet tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
        tLRPC$TL_inputStickerSetID.access_hash = stickerSet.access_hash;
        tLRPC$TL_inputStickerSetID.id = stickerSet.id;
        TLObject tLRPC$TL_messages_installStickerSet;
        if (i != 0) {
            stickerSet.archived = i == 1;
            for (int i3 = 0; i3 < stickerSets[i2].size(); i3++) {
                TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) stickerSets[i2].get(i3);
                if (tLRPC$TL_messages_stickerSet.set.id == stickerSet.id) {
                    stickerSets[i2].remove(i3);
                    if (i == 2) {
                        stickerSets[i2].add(0, tLRPC$TL_messages_stickerSet);
                    } else {
                        stickerSetsById.remove(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id));
                        stickerSetsByName.remove(tLRPC$TL_messages_stickerSet.set.short_name);
                    }
                    loadHash[i2] = calcStickersHash(stickerSets[i2]);
                    putStickersToCache(i2, stickerSets[i2], loadDate[i2], loadHash[i2]);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.stickersDidLoaded, Integer.valueOf(i2));
                    tLRPC$TL_messages_installStickerSet = new TLRPC$TL_messages_installStickerSet();
                    tLRPC$TL_messages_installStickerSet.stickerset = tLRPC$TL_inputStickerSetID;
                    if (i != 1) {
                        z2 = false;
                    }
                    tLRPC$TL_messages_installStickerSet.archived = z2;
                    ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_installStickerSet, new RequestDelegate() {

                        /* renamed from: org.telegram.messenger.query.StickersQuery$28$2 */
                        class C36232 implements Runnable {
                            C36232() {
                            }

                            public void run() {
                                StickersQuery.loadStickers(i2, false, false);
                            }
                        }

                        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    if (tLObject instanceof TLRPC$TL_messages_stickerSetInstallResultArchive) {
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.needReloadArchivedStickers, Integer.valueOf(i2));
                                        if (i != 1 && baseFragment != null && baseFragment.getParentActivity() != null) {
                                            baseFragment.showDialog(new StickersArchiveAlert(baseFragment.getParentActivity(), z ? baseFragment : null, ((TLRPC$TL_messages_stickerSetInstallResultArchive) tLObject).sets).create());
                                        }
                                    }
                                }
                            });
                            AndroidUtilities.runOnUIThread(new C36232(), 1000);
                        }
                    });
                    return;
                }
            }
            loadHash[i2] = calcStickersHash(stickerSets[i2]);
            putStickersToCache(i2, stickerSets[i2], loadDate[i2], loadHash[i2]);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.stickersDidLoaded, Integer.valueOf(i2));
            tLRPC$TL_messages_installStickerSet = new TLRPC$TL_messages_installStickerSet();
            tLRPC$TL_messages_installStickerSet.stickerset = tLRPC$TL_inputStickerSetID;
            if (i != 1) {
                z2 = false;
            }
            tLRPC$TL_messages_installStickerSet.archived = z2;
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_installStickerSet, /* anonymous class already generated */);
            return;
        }
        tLRPC$TL_messages_installStickerSet = new TLRPC$TL_messages_uninstallStickerSet();
        tLRPC$TL_messages_installStickerSet.stickerset = tLRPC$TL_inputStickerSetID;
        ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_installStickerSet, new RequestDelegate() {
            public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        try {
                            if (tLRPC$TL_error == null) {
                                if (stickerSet.masks) {
                                    Toast.makeText(context, LocaleController.getString("MasksRemoved", R.string.MasksRemoved), 0).show();
                                } else {
                                    Toast.makeText(context, LocaleController.getString("StickersRemoved", R.string.StickersRemoved), 0).show();
                                }
                                StickersQuery.loadStickers(i2, false, true);
                            }
                            Toast.makeText(context, LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred), 0).show();
                            StickersQuery.loadStickers(i2, false, true);
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                    }
                });
            }
        });
    }

    public static void reorderStickers(int i, final ArrayList<Long> arrayList) {
        Collections.sort(stickerSets[i], new Comparator<TLRPC$TL_messages_stickerSet>() {
            public int compare(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet, TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet2) {
                int indexOf = arrayList.indexOf(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id));
                int indexOf2 = arrayList.indexOf(Long.valueOf(tLRPC$TL_messages_stickerSet2.set.id));
                return indexOf > indexOf2 ? 1 : indexOf < indexOf2 ? -1 : 0;
            }
        });
        loadHash[i] = calcStickersHash(stickerSets[i]);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.stickersDidLoaded, Integer.valueOf(i));
        loadStickers(i, false, true);
    }
}
