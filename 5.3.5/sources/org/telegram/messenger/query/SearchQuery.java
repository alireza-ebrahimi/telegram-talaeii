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
import org.telegram.tgnet.TLRPC$Chat;
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
    static class C18082 implements Runnable {
        C18082() {
        }

        public void run() {
            final ArrayList<TLRPC$TL_topPeer> hintsNew = new ArrayList();
            final ArrayList<TLRPC$TL_topPeer> inlineBotsNew = new ArrayList();
            final ArrayList<User> users = new ArrayList();
            final ArrayList<TLRPC$Chat> chats = new ArrayList();
            int selfUserId = UserConfig.getClientUserId();
            try {
                ArrayList<Integer> usersToLoad = new ArrayList();
                ArrayList<Integer> chatsToLoad = new ArrayList();
                SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized("SELECT did, type, rating FROM chat_hints WHERE 1 ORDER BY rating DESC", new Object[0]);
                while (cursor.next()) {
                    int did = cursor.intValue(0);
                    if (did != selfUserId) {
                        int type = cursor.intValue(1);
                        TLRPC$TL_topPeer peer = new TLRPC$TL_topPeer();
                        peer.rating = cursor.doubleValue(2);
                        if (did > 0) {
                            peer.peer = new TLRPC$TL_peerUser();
                            peer.peer.user_id = did;
                            usersToLoad.add(Integer.valueOf(did));
                        } else {
                            peer.peer = new TLRPC$TL_peerChat();
                            peer.peer.chat_id = -did;
                            chatsToLoad.add(Integer.valueOf(-did));
                        }
                        if (type == 0) {
                            hintsNew.add(peer);
                        } else if (type == 1) {
                            inlineBotsNew.add(peer);
                        }
                    }
                }
                cursor.dispose();
                if (!usersToLoad.isEmpty()) {
                    MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", usersToLoad), users);
                }
                if (!chatsToLoad.isEmpty()) {
                    MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", chatsToLoad), chats);
                }
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        MessagesController.getInstance().putUsers(users, true);
                        MessagesController.getInstance().putChats(chats, true);
                        SearchQuery.loading = false;
                        SearchQuery.loaded = true;
                        SearchQuery.hints = hintsNew;
                        SearchQuery.inlineBots = inlineBotsNew;
                        SearchQuery.buildShortcuts();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadHints, new Object[0]);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInlineHints, new Object[0]);
                        if (Math.abs(UserConfig.lastHintsSyncTime - ((int) (System.currentTimeMillis() / 1000))) >= 86400) {
                            SearchQuery.loadHints(false);
                        }
                    }
                });
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    /* renamed from: org.telegram.messenger.query.SearchQuery$3 */
    static class C18123 implements RequestDelegate {
        C18123() {
        }

        public void run(final TLObject response, TLRPC$TL_error error) {
            if (response instanceof TLRPC$TL_contacts_topPeers) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        final TLRPC$TL_contacts_topPeers topPeers = response;
                        MessagesController.getInstance().putUsers(topPeers.users, false);
                        MessagesController.getInstance().putChats(topPeers.chats, false);
                        for (int a = 0; a < topPeers.categories.size(); a++) {
                            TLRPC$TL_topPeerCategoryPeers category = (TLRPC$TL_topPeerCategoryPeers) topPeers.categories.get(a);
                            if (category.category instanceof TLRPC$TL_topPeerCategoryBotsInline) {
                                SearchQuery.inlineBots = category.peers;
                                UserConfig.botRatingLoadTime = (int) (System.currentTimeMillis() / 1000);
                            } else {
                                SearchQuery.hints = category.peers;
                                int selfUserId = UserConfig.getClientUserId();
                                for (int b = 0; b < SearchQuery.hints.size(); b++) {
                                    if (((TLRPC$TL_topPeer) SearchQuery.hints.get(b)).peer.user_id == selfUserId) {
                                        SearchQuery.hints.remove(b);
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
                            class C18091 implements Runnable {
                                C18091() {
                                }

                                public void run() {
                                    UserConfig.lastHintsSyncTime = (int) (System.currentTimeMillis() / 1000);
                                    UserConfig.saveConfig(false);
                                }
                            }

                            public void run() {
                                try {
                                    MessagesStorage.getInstance().getDatabase().executeFast("DELETE FROM chat_hints WHERE 1").stepThis().dispose();
                                    MessagesStorage.getInstance().getDatabase().beginTransaction();
                                    MessagesStorage.getInstance().putUsersAndChats(topPeers.users, topPeers.chats, false, false);
                                    SQLitePreparedStatement state = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO chat_hints VALUES(?, ?, ?, ?)");
                                    for (int a = 0; a < topPeers.categories.size(); a++) {
                                        int type;
                                        TLRPC$TL_topPeerCategoryPeers category = (TLRPC$TL_topPeerCategoryPeers) topPeers.categories.get(a);
                                        if (category.category instanceof TLRPC$TL_topPeerCategoryBotsInline) {
                                            type = 1;
                                        } else {
                                            type = 0;
                                        }
                                        for (int b = 0; b < category.peers.size(); b++) {
                                            int did;
                                            TLRPC$TL_topPeer peer = (TLRPC$TL_topPeer) category.peers.get(b);
                                            if (peer.peer instanceof TLRPC$TL_peerUser) {
                                                did = peer.peer.user_id;
                                            } else if (peer.peer instanceof TLRPC$TL_peerChat) {
                                                did = -peer.peer.chat_id;
                                            } else {
                                                did = -peer.peer.channel_id;
                                            }
                                            state.requery();
                                            state.bindInteger(1, did);
                                            state.bindInteger(2, type);
                                            state.bindDouble(3, peer.rating);
                                            state.bindInteger(4, 0);
                                            state.step();
                                        }
                                    }
                                    state.dispose();
                                    MessagesStorage.getInstance().getDatabase().commitTransaction();
                                    AndroidUtilities.runOnUIThread(new C18091());
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    /* renamed from: org.telegram.messenger.query.SearchQuery$4 */
    static class C18134 implements Comparator<TLRPC$TL_topPeer> {
        C18134() {
        }

        public int compare(TLRPC$TL_topPeer lhs, TLRPC$TL_topPeer rhs) {
            if (lhs.rating > rhs.rating) {
                return -1;
            }
            if (lhs.rating < rhs.rating) {
                return 1;
            }
            return 0;
        }
    }

    /* renamed from: org.telegram.messenger.query.SearchQuery$5 */
    static class C18145 implements RequestDelegate {
        C18145() {
        }

        public void run(TLObject response, TLRPC$TL_error error) {
        }
    }

    /* renamed from: org.telegram.messenger.query.SearchQuery$6 */
    static class C18156 implements RequestDelegate {
        C18156() {
        }

        public void run(TLObject response, TLRPC$TL_error error) {
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

    public static void buildShortcuts() {
        if (VERSION.SDK_INT >= 25) {
            final ArrayList<TLRPC$TL_topPeer> hintsFinal = new ArrayList();
            for (int a = 0; a < hints.size(); a++) {
                hintsFinal.add(hints.get(a));
                if (hintsFinal.size() == 3) {
                    break;
                }
            }
            Utilities.globalQueue.postRunnable(new Runnable() {
                @SuppressLint({"NewApi"})
                public void run() {
                    try {
                        int a;
                        TLRPC$TL_topPeer hint;
                        long did;
                        String id;
                        ShortcutManager shortcutManager = (ShortcutManager) ApplicationLoader.applicationContext.getSystemService(ShortcutManager.class);
                        List<ShortcutInfo> currentShortcuts = shortcutManager.getDynamicShortcuts();
                        ArrayList<String> shortcutsToUpdate = new ArrayList();
                        ArrayList<String> newShortcutsIds = new ArrayList();
                        ArrayList<String> shortcutsToDelete = new ArrayList();
                        if (!(currentShortcuts == null || currentShortcuts.isEmpty())) {
                            newShortcutsIds.add("compose");
                            for (a = 0; a < hintsFinal.size(); a++) {
                                hint = (TLRPC$TL_topPeer) hintsFinal.get(a);
                                if (hint.peer.user_id != 0) {
                                    did = (long) hint.peer.user_id;
                                } else {
                                    did = (long) (-hint.peer.chat_id);
                                    if (did == 0) {
                                        did = (long) (-hint.peer.channel_id);
                                    }
                                }
                                newShortcutsIds.add("did" + did);
                            }
                            for (a = 0; a < currentShortcuts.size(); a++) {
                                id = ((ShortcutInfo) currentShortcuts.get(a)).getId();
                                if (!newShortcutsIds.remove(id)) {
                                    shortcutsToDelete.add(id);
                                }
                                shortcutsToUpdate.add(id);
                            }
                            if (newShortcutsIds.isEmpty() && shortcutsToDelete.isEmpty()) {
                                return;
                            }
                        }
                        Intent intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                        intent.setAction("new_dialog");
                        ArrayList<ShortcutInfo> arrayList = new ArrayList();
                        arrayList.add(new Builder(ApplicationLoader.applicationContext, "compose").setShortLabel(LocaleController.getString("NewConversationShortcut", R.string.NewConversationShortcut)).setLongLabel(LocaleController.getString("NewConversationShortcut", R.string.NewConversationShortcut)).setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.shortcut_compose)).setIntent(intent).build());
                        if (shortcutsToUpdate.contains("compose")) {
                            shortcutManager.updateShortcuts(arrayList);
                        } else {
                            shortcutManager.addDynamicShortcuts(arrayList);
                        }
                        arrayList.clear();
                        if (!shortcutsToDelete.isEmpty()) {
                            shortcutManager.removeDynamicShortcuts(shortcutsToDelete);
                        }
                        for (a = 0; a < hintsFinal.size(); a++) {
                            intent = new Intent(ApplicationLoader.applicationContext, OpenChatReceiver.class);
                            hint = (TLRPC$TL_topPeer) hintsFinal.get(a);
                            User user = null;
                            TLRPC$Chat chat = null;
                            if (hint.peer.user_id != 0) {
                                intent.putExtra("userId", hint.peer.user_id);
                                user = MessagesController.getInstance().getUser(Integer.valueOf(hint.peer.user_id));
                                did = (long) hint.peer.user_id;
                            } else {
                                int chat_id = hint.peer.chat_id;
                                if (chat_id == 0) {
                                    chat_id = hint.peer.channel_id;
                                }
                                chat = MessagesController.getInstance().getChat(Integer.valueOf(chat_id));
                                intent.putExtra("chatId", chat_id);
                                did = (long) (-chat_id);
                            }
                            if (user != null || chat != null) {
                                String name;
                                TLObject photo = null;
                                if (user != null) {
                                    name = ContactsController.formatName(user.first_name, user.last_name);
                                    if (user.photo != null) {
                                        photo = user.photo.photo_small;
                                    }
                                } else {
                                    name = chat.title;
                                    if (chat.photo != null) {
                                        photo = chat.photo.photo_small;
                                    }
                                }
                                intent.setAction("com.tmessages.openchat" + did);
                                intent.addFlags(ConnectionsManager.FileTypeFile);
                                Bitmap bitmap = null;
                                if (photo != null) {
                                    bitmap = BitmapFactory.decodeFile(FileLoader.getPathToAttach(photo, true).toString());
                                    if (bitmap != null) {
                                        int size = AndroidUtilities.dp(48.0f);
                                        Bitmap result = Bitmap.createBitmap(size, size, Config.ARGB_8888);
                                        result.eraseColor(0);
                                        Canvas canvas = new Canvas(result);
                                        Shader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                                        if (SearchQuery.roundPaint == null) {
                                            SearchQuery.roundPaint = new Paint(1);
                                            SearchQuery.bitmapRect = new RectF();
                                        }
                                        float scale = ((float) size) / ((float) bitmap.getWidth());
                                        canvas.scale(scale, scale);
                                        SearchQuery.roundPaint.setShader(bitmapShader);
                                        SearchQuery.bitmapRect.set((float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(46.0f), (float) AndroidUtilities.dp(46.0f));
                                        canvas.drawRoundRect(SearchQuery.bitmapRect, (float) bitmap.getWidth(), (float) bitmap.getHeight(), SearchQuery.roundPaint);
                                        try {
                                            canvas.setBitmap(null);
                                        } catch (Exception e) {
                                        }
                                        bitmap = result;
                                    }
                                }
                                id = "did" + did;
                                if (TextUtils.isEmpty(name)) {
                                    name = " ";
                                }
                                Builder builder = new Builder(ApplicationLoader.applicationContext, id).setShortLabel(name).setLongLabel(name).setIntent(intent);
                                if (bitmap != null) {
                                    builder.setIcon(Icon.createWithBitmap(bitmap));
                                } else {
                                    builder.setIcon(Icon.createWithResource(ApplicationLoader.applicationContext, R.drawable.shortcut_user));
                                }
                                arrayList.add(builder.build());
                                if (shortcutsToUpdate.contains(id)) {
                                    shortcutManager.updateShortcuts(arrayList);
                                } else {
                                    shortcutManager.addDynamicShortcuts(arrayList);
                                }
                                arrayList.clear();
                            }
                        }
                    } catch (Throwable th) {
                    }
                }
            });
        }
    }

    public static void loadHints(boolean cache) {
        if (!loading) {
            if (!cache) {
                loading = true;
                TLRPC$TL_contacts_getTopPeers req = new TLRPC$TL_contacts_getTopPeers();
                req.hash = 0;
                req.bots_pm = false;
                req.correspondents = true;
                req.groups = false;
                req.channels = false;
                req.bots_inline = true;
                req.offset = 0;
                req.limit = 20;
                ConnectionsManager.getInstance().sendRequest(req, new C18123());
            } else if (!loaded) {
                loading = true;
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new C18082());
                loaded = true;
            }
        }
    }

    public static void increaseInlineRaiting(int uid) {
        int dt;
        if (UserConfig.botRatingLoadTime != 0) {
            dt = Math.max(1, ((int) (System.currentTimeMillis() / 1000)) - UserConfig.botRatingLoadTime);
        } else {
            dt = 60;
        }
        TLRPC$TL_topPeer peer = null;
        for (int a = 0; a < inlineBots.size(); a++) {
            TLRPC$TL_topPeer p = (TLRPC$TL_topPeer) inlineBots.get(a);
            if (p.peer.user_id == uid) {
                peer = p;
                break;
            }
        }
        if (peer == null) {
            peer = new TLRPC$TL_topPeer();
            peer.peer = new TLRPC$TL_peerUser();
            peer.peer.user_id = uid;
            inlineBots.add(peer);
        }
        peer.rating += Math.exp((double) (dt / MessagesController.getInstance().ratingDecay));
        Collections.sort(inlineBots, new C18134());
        if (inlineBots.size() > 20) {
            inlineBots.remove(inlineBots.size() - 1);
        }
        savePeer(uid, 1, peer.rating);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInlineHints, new Object[0]);
    }

    public static void removeInline(int uid) {
        for (int a = 0; a < inlineBots.size(); a++) {
            if (((TLRPC$TL_topPeer) inlineBots.get(a)).peer.user_id == uid) {
                inlineBots.remove(a);
                TLRPC$TL_contacts_resetTopPeerRating req = new TLRPC$TL_contacts_resetTopPeerRating();
                req.category = new TLRPC$TL_topPeerCategoryBotsInline();
                req.peer = MessagesController.getInputPeer(uid);
                ConnectionsManager.getInstance().sendRequest(req, new C18145());
                deletePeer(uid, 1);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadInlineHints, new Object[0]);
                return;
            }
        }
    }

    public static void removePeer(int uid) {
        for (int a = 0; a < hints.size(); a++) {
            if (((TLRPC$TL_topPeer) hints.get(a)).peer.user_id == uid) {
                hints.remove(a);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadHints, new Object[0]);
                TLRPC$TL_contacts_resetTopPeerRating req = new TLRPC$TL_contacts_resetTopPeerRating();
                req.category = new TLRPC$TL_topPeerCategoryCorrespondents();
                req.peer = MessagesController.getInputPeer(uid);
                deletePeer(uid, 0);
                ConnectionsManager.getInstance().sendRequest(req, new C18156());
                return;
            }
        }
    }

    public static void increasePeerRaiting(final long did) {
        final int lower_id = (int) did;
        if (lower_id > 0) {
            User user = lower_id > 0 ? MessagesController.getInstance().getUser(Integer.valueOf(lower_id)) : null;
            if (user != null && !user.bot) {
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        double dt = 0.0d;
                        int lastTime = 0;
                        int lastMid = 0;
                        try {
                            SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT MAX(mid), MAX(date) FROM messages WHERE uid = %d AND out = 1", new Object[]{Long.valueOf(did)}), new Object[0]);
                            if (cursor.next()) {
                                lastMid = cursor.intValue(0);
                                lastTime = cursor.intValue(1);
                            }
                            cursor.dispose();
                            if (lastMid > 0 && UserConfig.ratingLoadTime != 0) {
                                dt = (double) (lastTime - UserConfig.ratingLoadTime);
                            }
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                        final double dtFinal = dt;
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.messenger.query.SearchQuery$7$1$1 */
                            class C18161 implements Comparator<TLRPC$TL_topPeer> {
                                C18161() {
                                }

                                public int compare(TLRPC$TL_topPeer lhs, TLRPC$TL_topPeer rhs) {
                                    if (lhs.rating > rhs.rating) {
                                        return -1;
                                    }
                                    if (lhs.rating < rhs.rating) {
                                        return 1;
                                    }
                                    return 0;
                                }
                            }

                            public void run() {
                                TLRPC$TL_topPeer peer = null;
                                for (int a = 0; a < SearchQuery.hints.size(); a++) {
                                    TLRPC$TL_topPeer p = (TLRPC$TL_topPeer) SearchQuery.hints.get(a);
                                    if ((lower_id < 0 && (p.peer.chat_id == (-lower_id) || p.peer.channel_id == (-lower_id))) || (lower_id > 0 && p.peer.user_id == lower_id)) {
                                        peer = p;
                                        break;
                                    }
                                }
                                if (peer == null) {
                                    peer = new TLRPC$TL_topPeer();
                                    if (lower_id > 0) {
                                        peer.peer = new TLRPC$TL_peerUser();
                                        peer.peer.user_id = lower_id;
                                    } else {
                                        peer.peer = new TLRPC$TL_peerChat();
                                        peer.peer.chat_id = -lower_id;
                                    }
                                    SearchQuery.hints.add(peer);
                                }
                                peer.rating += Math.exp(dtFinal / ((double) MessagesController.getInstance().ratingDecay));
                                Collections.sort(SearchQuery.hints, new C18161());
                                SearchQuery.savePeer((int) did, 0, peer.rating);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.reloadHints, new Object[0]);
                            }
                        });
                    }
                });
            }
        }
    }

    private static void savePeer(final int did, final int type, final double rating) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement state = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO chat_hints VALUES(?, ?, ?, ?)");
                    state.requery();
                    state.bindInteger(1, did);
                    state.bindInteger(2, type);
                    state.bindDouble(3, rating);
                    state.bindInteger(4, ((int) System.currentTimeMillis()) / 1000);
                    state.step();
                    state.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    private static void deletePeer(final int did, final int type) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.getInstance().getDatabase().executeFast(String.format(Locale.US, "DELETE FROM chat_hints WHERE did = %d AND type = %d", new Object[]{Integer.valueOf(did), Integer.valueOf(type)})).stepThis().dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }
}
