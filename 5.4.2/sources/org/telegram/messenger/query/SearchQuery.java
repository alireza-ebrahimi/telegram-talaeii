package org.telegram.messenger.query;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Icon;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.OpenChatReceiver;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contacts_getTopPeers;
import org.telegram.tgnet.TLRPC$TL_contacts_resetTopPeerRating;
import org.telegram.tgnet.TLRPC$TL_contacts_topPeers;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_peerChat;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_topPeer;
import org.telegram.tgnet.TLRPC$TL_topPeerCategoryBotsInline;
import org.telegram.tgnet.TLRPC$TL_topPeerCategoryCorrespondents;
import org.telegram.tgnet.TLRPC$TL_topPeerCategoryPeers;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.LaunchActivity;

public class SearchQuery {
    private static RectF bitmapRect;
    public static ArrayList<TLRPC$TL_topPeer> hints = new ArrayList();
    public static ArrayList<TLRPC$TL_topPeer> inlineBots = new ArrayList();
    private static boolean loaded;
    private static boolean loading;
    private static Paint roundPaint;

    /* renamed from: org.telegram.messenger.query.SearchQuery$2 */
    static class C35862 implements Runnable {
        C35862() {
        }

        public void run() {
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            final ArrayList arrayList3 = new ArrayList();
            final ArrayList arrayList4 = new ArrayList();
            int clientUserId = UserConfig.getClientUserId();
            try {
                Iterable arrayList5 = new ArrayList();
                Iterable arrayList6 = new ArrayList();
                SQLiteCursor b = MessagesStorage.getInstance().getDatabase().m12165b("SELECT did, type, rating FROM chat_hints WHERE 1 ORDER BY rating DESC", new Object[0]);
                while (b.m12152a()) {
                    int b2 = b.m12154b(0);
                    if (b2 != clientUserId) {
                        int b3 = b.m12154b(1);
                        TLRPC$TL_topPeer tLRPC$TL_topPeer = new TLRPC$TL_topPeer();
                        tLRPC$TL_topPeer.rating = b.m12156c(2);
                        if (b2 > 0) {
                            tLRPC$TL_topPeer.peer = new TLRPC$TL_peerUser();
                            tLRPC$TL_topPeer.peer.user_id = b2;
                            arrayList5.add(Integer.valueOf(b2));
                        } else {
                            tLRPC$TL_topPeer.peer = new TLRPC$TL_peerChat();
                            tLRPC$TL_topPeer.peer.chat_id = -b2;
                            arrayList6.add(Integer.valueOf(-b2));
                        }
                        if (b3 == 0) {
                            arrayList.add(tLRPC$TL_topPeer);
                        } else if (b3 == 1) {
                            arrayList2.add(tLRPC$TL_topPeer);
                        }
                    }
                }
                b.m12155b();
                if (!arrayList5.isEmpty()) {
                    MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", arrayList5), arrayList3);
                }
                if (!arrayList6.isEmpty()) {
                    MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", arrayList6), arrayList4);
                }
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        MessagesController.getInstance().putUsers(arrayList3, true);
                        MessagesController.getInstance().putChats(arrayList4, true);
                        SearchQuery.loading = false;
                        SearchQuery.loaded = true;
                        SearchQuery.hints = arrayList;
                        SearchQuery.inlineBots = arrayList2;
                        SearchQuery.buildShortcuts();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadHints, new Object[0]);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInlineHints, new Object[0]);
                        if (Math.abs(UserConfig.lastHintsSyncTime - ((int) (System.currentTimeMillis() / 1000))) >= 86400) {
                            SearchQuery.loadHints(false);
                        }
                    }
                });
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    /* renamed from: org.telegram.messenger.query.SearchQuery$3 */
    static class C35903 implements RequestDelegate {
        C35903() {
        }

        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject instanceof TLRPC$TL_contacts_topPeers) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        final TLRPC$TL_contacts_topPeers tLRPC$TL_contacts_topPeers = (TLRPC$TL_contacts_topPeers) tLObject;
                        MessagesController.getInstance().putUsers(tLRPC$TL_contacts_topPeers.users, false);
                        MessagesController.getInstance().putChats(tLRPC$TL_contacts_topPeers.chats, false);
                        for (int i = 0; i < tLRPC$TL_contacts_topPeers.categories.size(); i++) {
                            TLRPC$TL_topPeerCategoryPeers tLRPC$TL_topPeerCategoryPeers = (TLRPC$TL_topPeerCategoryPeers) tLRPC$TL_contacts_topPeers.categories.get(i);
                            if (tLRPC$TL_topPeerCategoryPeers.category instanceof TLRPC$TL_topPeerCategoryBotsInline) {
                                SearchQuery.inlineBots = tLRPC$TL_topPeerCategoryPeers.peers;
                                UserConfig.botRatingLoadTime = (int) (System.currentTimeMillis() / 1000);
                            } else {
                                SearchQuery.hints = tLRPC$TL_topPeerCategoryPeers.peers;
                                int clientUserId = UserConfig.getClientUserId();
                                for (int i2 = 0; i2 < SearchQuery.hints.size(); i2++) {
                                    if (((TLRPC$TL_topPeer) SearchQuery.hints.get(i2)).peer.user_id == clientUserId) {
                                        SearchQuery.hints.remove(i2);
                                        break;
                                    }
                                }
                                UserConfig.ratingLoadTime = (int) (System.currentTimeMillis() / 1000);
                            }
                        }
                        UserConfig.saveConfig(false);
                        SearchQuery.buildShortcuts();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadHints, new Object[0]);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInlineHints, new Object[0]);
                        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                            /* renamed from: org.telegram.messenger.query.SearchQuery$3$1$1$1 */
                            class C35871 implements Runnable {
                                C35871() {
                                }

                                public void run() {
                                    UserConfig.lastHintsSyncTime = (int) (System.currentTimeMillis() / 1000);
                                    UserConfig.saveConfig(false);
                                }
                            }

                            public void run() {
                                try {
                                    MessagesStorage.getInstance().getDatabase().m12164a("DELETE FROM chat_hints WHERE 1").m12179c().m12181e();
                                    MessagesStorage.getInstance().getDatabase().m12168d();
                                    MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_topPeers.users, tLRPC$TL_contacts_topPeers.chats, false, false);
                                    SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO chat_hints VALUES(?, ?, ?, ?)");
                                    for (int i = 0; i < tLRPC$TL_contacts_topPeers.categories.size(); i++) {
                                        TLRPC$TL_topPeerCategoryPeers tLRPC$TL_topPeerCategoryPeers = (TLRPC$TL_topPeerCategoryPeers) tLRPC$TL_contacts_topPeers.categories.get(i);
                                        int i2 = tLRPC$TL_topPeerCategoryPeers.category instanceof TLRPC$TL_topPeerCategoryBotsInline ? 1 : 0;
                                        for (int i3 = 0; i3 < tLRPC$TL_topPeerCategoryPeers.peers.size(); i3++) {
                                            TLRPC$TL_topPeer tLRPC$TL_topPeer = (TLRPC$TL_topPeer) tLRPC$TL_topPeerCategoryPeers.peers.get(i3);
                                            int i4 = tLRPC$TL_topPeer.peer instanceof TLRPC$TL_peerUser ? tLRPC$TL_topPeer.peer.user_id : tLRPC$TL_topPeer.peer instanceof TLRPC$TL_peerChat ? -tLRPC$TL_topPeer.peer.chat_id : -tLRPC$TL_topPeer.peer.channel_id;
                                            a.m12180d();
                                            a.m12174a(1, i4);
                                            a.m12174a(2, i2);
                                            a.m12173a(3, tLRPC$TL_topPeer.rating);
                                            a.m12174a(4, 0);
                                            a.m12178b();
                                        }
                                    }
                                    a.m12181e();
                                    MessagesStorage.getInstance().getDatabase().m12169e();
                                    AndroidUtilities.runOnUIThread(new C35871());
                                } catch (Throwable e) {
                                    FileLog.m13728e(e);
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    /* renamed from: org.telegram.messenger.query.SearchQuery$4 */
    static class C35914 implements Comparator<TLRPC$TL_topPeer> {
        C35914() {
        }

        public int compare(TLRPC$TL_topPeer tLRPC$TL_topPeer, TLRPC$TL_topPeer tLRPC$TL_topPeer2) {
            return tLRPC$TL_topPeer.rating > tLRPC$TL_topPeer2.rating ? -1 : tLRPC$TL_topPeer.rating < tLRPC$TL_topPeer2.rating ? 1 : 0;
        }
    }

    /* renamed from: org.telegram.messenger.query.SearchQuery$5 */
    static class C35925 implements RequestDelegate {
        C35925() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }
    }

    /* renamed from: org.telegram.messenger.query.SearchQuery$6 */
    static class C35936 implements RequestDelegate {
        C35936() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }
    }

    public static void buildShortcuts() {
        if (VERSION.SDK_INT >= 25) {
            final ArrayList arrayList = new ArrayList();
            for (int i = 0; i < hints.size(); i++) {
                arrayList.add(hints.get(i));
                if (arrayList.size() == 3) {
                    break;
                }
            }
            Utilities.globalQueue.postRunnable(new Runnable() {
                @SuppressLint({"NewApi"})
                public void run() {
                    try {
                        TLRPC$TL_topPeer tLRPC$TL_topPeer;
                        int i;
                        ShortcutManager shortcutManager = (ShortcutManager) ApplicationLoader.applicationContext.getSystemService(ShortcutManager.class);
                        List dynamicShortcuts = shortcutManager.getDynamicShortcuts();
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        List arrayList3 = new ArrayList();
                        if (!(dynamicShortcuts == null || dynamicShortcuts.isEmpty())) {
                            arrayList2.add("compose");
                            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                long j;
                                tLRPC$TL_topPeer = (TLRPC$TL_topPeer) arrayList.get(i2);
                                if (tLRPC$TL_topPeer.peer.user_id != 0) {
                                    j = (long) tLRPC$TL_topPeer.peer.user_id;
                                } else {
                                    j = (long) (-tLRPC$TL_topPeer.peer.chat_id);
                                    if (j == 0) {
                                        j = (long) (-tLRPC$TL_topPeer.peer.channel_id);
                                    }
                                }
                                arrayList2.add("did" + j);
                            }
                            for (i = 0; i < dynamicShortcuts.size(); i++) {
                                String id = ((ShortcutInfo) dynamicShortcuts.get(i)).getId();
                                if (!arrayList2.remove(id)) {
                                    arrayList3.add(id);
                                }
                                arrayList.add(id);
                            }
                            if (arrayList2.isEmpty() && arrayList3.isEmpty()) {
                                return;
                            }
                        }
                        Intent intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                        intent.setAction("new_dialog");
                        List arrayList4 = new ArrayList();
                        arrayList4.add(new Builder(ApplicationLoader.applicationContext, "compose").setShortLabel(LocaleController.getString("NewConversationShortcut", R.string.NewConversationShortcut)).setLongLabel(LocaleController.getString("NewConversationShortcut", R.string.NewConversationShortcut)).setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.shortcut_compose)).setIntent(intent).build());
                        if (arrayList.contains("compose")) {
                            shortcutManager.updateShortcuts(arrayList4);
                        } else {
                            shortcutManager.addDynamicShortcuts(arrayList4);
                        }
                        arrayList4.clear();
                        if (!arrayList3.isEmpty()) {
                            shortcutManager.removeDynamicShortcuts(arrayList3);
                        }
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            User user;
                            long j2;
                            Chat chat;
                            Intent intent2 = new Intent(ApplicationLoader.applicationContext, OpenChatReceiver.class);
                            tLRPC$TL_topPeer = (TLRPC$TL_topPeer) arrayList.get(i3);
                            if (tLRPC$TL_topPeer.peer.user_id != 0) {
                                intent2.putExtra("userId", tLRPC$TL_topPeer.peer.user_id);
                                user = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_topPeer.peer.user_id));
                                j2 = (long) tLRPC$TL_topPeer.peer.user_id;
                                chat = null;
                            } else {
                                i = tLRPC$TL_topPeer.peer.chat_id;
                                if (i == 0) {
                                    i = tLRPC$TL_topPeer.peer.channel_id;
                                }
                                Chat chat2 = MessagesController.getInstance().getChat(Integer.valueOf(i));
                                intent2.putExtra("chatId", i);
                                user = null;
                                j2 = (long) (-i);
                                chat = chat2;
                            }
                            if (user != null || chat != null) {
                                CharSequence formatName;
                                TLObject tLObject = null;
                                if (user != null) {
                                    formatName = ContactsController.formatName(user.first_name, user.last_name);
                                    if (user.photo != null) {
                                        tLObject = user.photo.photo_small;
                                    }
                                } else {
                                    formatName = chat.title;
                                    if (chat.photo != null) {
                                        tLObject = chat.photo.photo_small;
                                    }
                                }
                                intent2.setAction("com.tmessages.openchat" + j2);
                                intent2.addFlags(ConnectionsManager.FileTypeFile);
                                Bitmap bitmap = null;
                                if (tLObject != null) {
                                    Bitmap createBitmap;
                                    bitmap = BitmapFactory.decodeFile(FileLoader.getPathToAttach(tLObject, true).toString());
                                    if (bitmap != null) {
                                        int dp = AndroidUtilities.dp(48.0f);
                                        createBitmap = Bitmap.createBitmap(dp, dp, Config.ARGB_8888);
                                        createBitmap.eraseColor(0);
                                        Canvas canvas = new Canvas(createBitmap);
                                        Shader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                                        if (SearchQuery.roundPaint == null) {
                                            SearchQuery.roundPaint = new Paint(1);
                                            SearchQuery.bitmapRect = new RectF();
                                        }
                                        float width = ((float) dp) / ((float) bitmap.getWidth());
                                        canvas.scale(width, width);
                                        SearchQuery.roundPaint.setShader(bitmapShader);
                                        SearchQuery.bitmapRect.set((float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(46.0f), (float) AndroidUtilities.dp(46.0f));
                                        canvas.drawRoundRect(SearchQuery.bitmapRect, (float) bitmap.getWidth(), (float) bitmap.getHeight(), SearchQuery.roundPaint);
                                        try {
                                            canvas.setBitmap(null);
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        createBitmap = bitmap;
                                    }
                                    bitmap = createBitmap;
                                }
                                String str = "did" + j2;
                                CharSequence charSequence = TextUtils.isEmpty(formatName) ? " " : formatName;
                                Builder intent3 = new Builder(ApplicationLoader.applicationContext, str).setShortLabel(charSequence).setLongLabel(charSequence).setIntent(intent2);
                                if (bitmap != null) {
                                    intent3.setIcon(Icon.createWithBitmap(bitmap));
                                } else {
                                    intent3.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.shortcut_user));
                                }
                                arrayList4.add(intent3.build());
                                if (arrayList.contains(str)) {
                                    shortcutManager.updateShortcuts(arrayList4);
                                } else {
                                    shortcutManager.addDynamicShortcuts(arrayList4);
                                }
                                arrayList4.clear();
                            }
                        }
                    } catch (Throwable th) {
                    }
                }
            });
        }
    }

    public static void cleanup() {
        loading = false;
        loaded = false;
        hints.clear();
        inlineBots.clear();
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadHints, new Object[0]);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInlineHints, new Object[0]);
    }

    private static void deletePeer(final int i, final int i2) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.getInstance().getDatabase().m12164a(String.format(Locale.US, "DELETE FROM chat_hints WHERE did = %d AND type = %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)})).m12179c().m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public static void increaseInlineRaiting(int i) {
        TLRPC$TL_topPeer tLRPC$TL_topPeer;
        int max = UserConfig.botRatingLoadTime != 0 ? Math.max(1, ((int) (System.currentTimeMillis() / 1000)) - UserConfig.botRatingLoadTime) : 60;
        for (int i2 = 0; i2 < inlineBots.size(); i2++) {
            tLRPC$TL_topPeer = (TLRPC$TL_topPeer) inlineBots.get(i2);
            if (tLRPC$TL_topPeer.peer.user_id == i) {
                break;
            }
        }
        tLRPC$TL_topPeer = null;
        if (tLRPC$TL_topPeer == null) {
            tLRPC$TL_topPeer = new TLRPC$TL_topPeer();
            tLRPC$TL_topPeer.peer = new TLRPC$TL_peerUser();
            tLRPC$TL_topPeer.peer.user_id = i;
            inlineBots.add(tLRPC$TL_topPeer);
        }
        tLRPC$TL_topPeer.rating += Math.exp((double) (max / MessagesController.getInstance().ratingDecay));
        Collections.sort(inlineBots, new C35914());
        if (inlineBots.size() > 20) {
            inlineBots.remove(inlineBots.size() - 1);
        }
        savePeer(i, 1, tLRPC$TL_topPeer.rating);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInlineHints, new Object[0]);
    }

    public static void increasePeerRaiting(final long j) {
        final int i = (int) j;
        if (i > 0) {
            User user = i > 0 ? MessagesController.getInstance().getUser(Integer.valueOf(i)) : null;
            if (user != null && !user.bot) {
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        int i = 0;
                        double d = 0.0d;
                        try {
                            int b;
                            SQLiteCursor b2 = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT MAX(mid), MAX(date) FROM messages WHERE uid = %d AND out = 1", new Object[]{Long.valueOf(j)}), new Object[0]);
                            if (b2.m12152a()) {
                                i = b2.m12154b(0);
                                b = b2.m12154b(1);
                            } else {
                                b = 0;
                            }
                            b2.m12155b();
                            if (i > 0 && UserConfig.ratingLoadTime != 0) {
                                d = (double) (b - UserConfig.ratingLoadTime);
                            }
                        } catch (Throwable e) {
                            FileLog.m13728e(e);
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.messenger.query.SearchQuery$7$1$1 */
                            class C35941 implements Comparator<TLRPC$TL_topPeer> {
                                C35941() {
                                }

                                public int compare(TLRPC$TL_topPeer tLRPC$TL_topPeer, TLRPC$TL_topPeer tLRPC$TL_topPeer2) {
                                    return tLRPC$TL_topPeer.rating > tLRPC$TL_topPeer2.rating ? -1 : tLRPC$TL_topPeer.rating < tLRPC$TL_topPeer2.rating ? 1 : 0;
                                }
                            }

                            public void run() {
                                TLRPC$TL_topPeer tLRPC$TL_topPeer;
                                for (int i = 0; i < SearchQuery.hints.size(); i++) {
                                    tLRPC$TL_topPeer = (TLRPC$TL_topPeer) SearchQuery.hints.get(i);
                                    if ((i < 0 && (tLRPC$TL_topPeer.peer.chat_id == (-i) || tLRPC$TL_topPeer.peer.channel_id == (-i))) || (i > 0 && tLRPC$TL_topPeer.peer.user_id == i)) {
                                        break;
                                    }
                                }
                                tLRPC$TL_topPeer = null;
                                if (tLRPC$TL_topPeer == null) {
                                    tLRPC$TL_topPeer = new TLRPC$TL_topPeer();
                                    if (i > 0) {
                                        tLRPC$TL_topPeer.peer = new TLRPC$TL_peerUser();
                                        tLRPC$TL_topPeer.peer.user_id = i;
                                    } else {
                                        tLRPC$TL_topPeer.peer = new TLRPC$TL_peerChat();
                                        tLRPC$TL_topPeer.peer.chat_id = -i;
                                    }
                                    SearchQuery.hints.add(tLRPC$TL_topPeer);
                                }
                                tLRPC$TL_topPeer.rating += Math.exp(d / ((double) MessagesController.getInstance().ratingDecay));
                                Collections.sort(SearchQuery.hints, new C35941());
                                SearchQuery.savePeer((int) j, 0, tLRPC$TL_topPeer.rating);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadHints, new Object[0]);
                            }
                        });
                    }
                });
            }
        }
    }

    public static void loadHints(boolean z) {
        if (!loading) {
            if (!z) {
                loading = true;
                TLObject tLRPC$TL_contacts_getTopPeers = new TLRPC$TL_contacts_getTopPeers();
                tLRPC$TL_contacts_getTopPeers.hash = 0;
                tLRPC$TL_contacts_getTopPeers.bots_pm = false;
                tLRPC$TL_contacts_getTopPeers.correspondents = true;
                tLRPC$TL_contacts_getTopPeers.groups = false;
                tLRPC$TL_contacts_getTopPeers.channels = false;
                tLRPC$TL_contacts_getTopPeers.bots_inline = true;
                tLRPC$TL_contacts_getTopPeers.offset = 0;
                tLRPC$TL_contacts_getTopPeers.limit = 20;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_getTopPeers, new C35903());
            } else if (!loaded) {
                loading = true;
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new C35862());
                loaded = true;
            }
        }
    }

    public static void removeInline(int i) {
        for (int i2 = 0; i2 < inlineBots.size(); i2++) {
            if (((TLRPC$TL_topPeer) inlineBots.get(i2)).peer.user_id == i) {
                inlineBots.remove(i2);
                TLObject tLRPC$TL_contacts_resetTopPeerRating = new TLRPC$TL_contacts_resetTopPeerRating();
                tLRPC$TL_contacts_resetTopPeerRating.category = new TLRPC$TL_topPeerCategoryBotsInline();
                tLRPC$TL_contacts_resetTopPeerRating.peer = MessagesController.getInputPeer(i);
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_resetTopPeerRating, new C35925());
                deletePeer(i, 1);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInlineHints, new Object[0]);
                return;
            }
        }
    }

    public static void removePeer(int i) {
        for (int i2 = 0; i2 < hints.size(); i2++) {
            if (((TLRPC$TL_topPeer) hints.get(i2)).peer.user_id == i) {
                hints.remove(i2);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadHints, new Object[0]);
                TLObject tLRPC$TL_contacts_resetTopPeerRating = new TLRPC$TL_contacts_resetTopPeerRating();
                tLRPC$TL_contacts_resetTopPeerRating.category = new TLRPC$TL_topPeerCategoryCorrespondents();
                tLRPC$TL_contacts_resetTopPeerRating.peer = MessagesController.getInputPeer(i);
                deletePeer(i, 0);
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_resetTopPeerRating, new C35936());
                return;
            }
        }
    }

    private static void savePeer(final int i, final int i2, final double d) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().m12164a("REPLACE INTO chat_hints VALUES(?, ?, ?, ?)");
                    a.m12180d();
                    a.m12174a(1, i);
                    a.m12174a(2, i2);
                    a.m12173a(3, d);
                    a.m12174a(4, ((int) System.currentTimeMillis()) / 1000);
                    a.m12178b();
                    a.m12181e();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            }
        });
    }
}
