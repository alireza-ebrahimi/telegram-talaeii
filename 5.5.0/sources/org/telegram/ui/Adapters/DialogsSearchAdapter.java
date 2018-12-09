package org.telegram.ui.Adapters;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import org.ir.talaeii.R;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPeerEmpty;
import org.telegram.tgnet.TLRPC$TL_messages_searchGlobal;
import org.telegram.tgnet.TLRPC$TL_topPeer;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.SearchAdapterHelper.HashtagObject;
import org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate;
import org.telegram.ui.Cells.DialogCell;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.HashtagSearchCell;
import org.telegram.ui.Cells.HintDialogCell;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Cells.ProfileSearchCell;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import utils.p178a.C3791b;

public class DialogsSearchAdapter extends SelectionAdapter {
    private DialogsSearchAdapterDelegate delegate;
    private int dialogsType;
    private RecyclerListView innerListView;
    private String lastMessagesSearchString;
    private int lastReqId;
    private int lastSearchId = 0;
    private String lastSearchText;
    private Context mContext;
    private boolean messagesSearchEndReached;
    private int needMessagesSearch;
    private ArrayList<RecentSearchObject> recentSearchObjects = new ArrayList();
    private HashMap<Long, RecentSearchObject> recentSearchObjectsById = new HashMap();
    private int reqId = 0;
    private SearchAdapterHelper searchAdapterHelper = new SearchAdapterHelper();
    private ArrayList<TLObject> searchResult = new ArrayList();
    private ArrayList<String> searchResultHashtags = new ArrayList();
    private ArrayList<MessageObject> searchResultMessages = new ArrayList();
    private ArrayList<CharSequence> searchResultNames = new ArrayList();
    private Timer searchTimer;
    private int selfUserId;

    /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$1 */
    class C38521 implements SearchAdapterHelperDelegate {
        C38521() {
        }

        public void onDataSetChanged() {
            DialogsSearchAdapter.this.notifyDataSetChanged();
        }

        public void onSetHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
            for (int i = 0; i < arrayList.size(); i++) {
                DialogsSearchAdapter.this.searchResultHashtags.add(((HashtagObject) arrayList.get(i)).hashtag);
            }
            if (DialogsSearchAdapter.this.delegate != null) {
                DialogsSearchAdapter.this.delegate.searchStateChanged(false);
            }
            DialogsSearchAdapter.this.notifyDataSetChanged();
        }
    }

    /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$3 */
    class C38573 implements Runnable {

        /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$3$1 */
        class C38551 implements Comparator<RecentSearchObject> {
            C38551() {
            }

            public int compare(RecentSearchObject recentSearchObject, RecentSearchObject recentSearchObject2) {
                return recentSearchObject.date < recentSearchObject2.date ? 1 : recentSearchObject.date > recentSearchObject2.date ? -1 : 0;
            }
        }

        C38573() {
        }

        public void run() {
            int i = 0;
            try {
                RecentSearchObject recentSearchObject;
                ArrayList arrayList;
                int i2;
                SQLiteCursor b = MessagesStorage.getInstance().getDatabase().b("SELECT did, date FROM search_recent WHERE 1", new Object[0]);
                Iterable arrayList2 = new ArrayList();
                Iterable arrayList3 = new ArrayList();
                Iterable arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                final Object arrayList6 = new ArrayList();
                final HashMap hashMap = new HashMap();
                while (b.a()) {
                    long d = b.d(0);
                    int i3 = (int) d;
                    int i4 = (int) (d >> 32);
                    if (i3 == 0) {
                        if (DialogsSearchAdapter.this.dialogsType == 0 && !arrayList4.contains(Integer.valueOf(i4))) {
                            arrayList4.add(Integer.valueOf(i4));
                            i3 = 1;
                        }
                        i3 = 0;
                    } else if (i4 == 1) {
                        if (DialogsSearchAdapter.this.dialogsType == 0 && !arrayList3.contains(Integer.valueOf(i3))) {
                            arrayList3.add(Integer.valueOf(i3));
                            i3 = 1;
                        }
                        i3 = 0;
                    } else if (i3 > 0) {
                        if (!(DialogsSearchAdapter.this.dialogsType == 2 || arrayList2.contains(Integer.valueOf(i3)))) {
                            arrayList2.add(Integer.valueOf(i3));
                            i3 = 1;
                        }
                        i3 = 0;
                    } else {
                        if (!arrayList3.contains(Integer.valueOf(-i3))) {
                            arrayList3.add(Integer.valueOf(-i3));
                            i3 = 1;
                        }
                        i3 = 0;
                    }
                    if (i3 != 0) {
                        recentSearchObject = new RecentSearchObject();
                        recentSearchObject.did = d;
                        recentSearchObject.date = b.b(1);
                        arrayList6.add(recentSearchObject);
                        hashMap.put(Long.valueOf(recentSearchObject.did), recentSearchObject);
                    }
                }
                b.b();
                ArrayList arrayList7 = new ArrayList();
                if (!arrayList4.isEmpty()) {
                    arrayList = new ArrayList();
                    MessagesStorage.getInstance().getEncryptedChatsInternal(TextUtils.join(",", arrayList4), arrayList, arrayList2);
                    for (i2 = 0; i2 < arrayList.size(); i2++) {
                        ((RecentSearchObject) hashMap.get(Long.valueOf(((long) ((EncryptedChat) arrayList.get(i2)).id) << 32))).object = (TLObject) arrayList.get(i2);
                    }
                }
                if (!arrayList3.isEmpty()) {
                    arrayList = new ArrayList();
                    MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", arrayList3), arrayList);
                    for (i2 = 0; i2 < arrayList.size(); i2++) {
                        Chat chat = (Chat) arrayList.get(i2);
                        long makeBroadcastId = chat.id > 0 ? (long) (-chat.id) : AndroidUtilities.makeBroadcastId(chat.id);
                        if (chat.migrated_to != null) {
                            recentSearchObject = (RecentSearchObject) hashMap.remove(Long.valueOf(makeBroadcastId));
                            if (recentSearchObject != null) {
                                arrayList6.remove(recentSearchObject);
                            }
                        } else {
                            ((RecentSearchObject) hashMap.get(Long.valueOf(makeBroadcastId))).object = chat;
                        }
                    }
                }
                if (!arrayList2.isEmpty()) {
                    MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", arrayList2), arrayList7);
                    while (i < arrayList7.size()) {
                        User user = (User) arrayList7.get(i);
                        RecentSearchObject recentSearchObject2 = (RecentSearchObject) hashMap.get(Long.valueOf((long) user.id));
                        if (recentSearchObject2 != null) {
                            recentSearchObject2.object = user;
                        }
                        i++;
                    }
                }
                Collections.sort(arrayList6, new C38551());
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        DialogsSearchAdapter.this.setRecentSearch(arrayList6, hashMap);
                    }
                });
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$5 */
    class C38595 implements Runnable {
        C38595() {
        }

        public void run() {
            try {
                MessagesStorage.getInstance().getDatabase().a("DELETE FROM search_recent WHERE 1").c().e();
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    private class CategoryAdapterRecycler extends SelectionAdapter {
        private CategoryAdapterRecycler() {
        }

        public int getItemCount() {
            return SearchQuery.hints.size();
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Chat chat;
            User user = null;
            HintDialogCell hintDialogCell = (HintDialogCell) viewHolder.itemView;
            boolean x = C3791b.x(DialogsSearchAdapter.this.mContext);
            List arrayList = x ? new ArrayList() : C3791b.b(DialogsSearchAdapter.this.mContext);
            hintDialogCell.setLayoutParams(new LayoutParams(AndroidUtilities.dp(80.0f), AndroidUtilities.dp(100.0f)));
            TLRPC$TL_topPeer tLRPC$TL_topPeer = (TLRPC$TL_topPeer) SearchQuery.hints.get(i);
            TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
            int i2 = 0;
            if (tLRPC$TL_topPeer.peer.user_id != 0) {
                i2 = tLRPC$TL_topPeer.peer.user_id;
                chat = null;
                user = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_topPeer.peer.user_id));
            } else if (tLRPC$TL_topPeer.peer.channel_id != 0) {
                i2 = -tLRPC$TL_topPeer.peer.channel_id;
                chat = MessagesController.getInstance().getChat(Integer.valueOf(tLRPC$TL_topPeer.peer.channel_id));
            } else if (tLRPC$TL_topPeer.peer.chat_id != 0) {
                i2 = -tLRPC$TL_topPeer.peer.chat_id;
                chat = MessagesController.getInstance().getChat(Integer.valueOf(tLRPC$TL_topPeer.peer.chat_id));
            } else {
                chat = null;
            }
            if (!x && (arrayList.contains(Long.valueOf((long) i2)) || arrayList.contains(Long.valueOf(-((long) i2))))) {
                hintDialogCell.setLayoutParams(new LayoutParams(AndroidUtilities.dp(BitmapDescriptorFactory.HUE_RED), AndroidUtilities.dp(100.0f)));
            }
            hintDialogCell.setTag(Integer.valueOf(i2));
            CharSequence charSequence = TtmlNode.ANONYMOUS_REGION_ID;
            if (user != null) {
                charSequence = ContactsController.formatName(user.first_name, user.last_name);
            } else if (chat != null) {
                charSequence = chat.title;
            }
            hintDialogCell.setDialog(i2, true, charSequence);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View hintDialogCell = new HintDialogCell(DialogsSearchAdapter.this.mContext);
            hintDialogCell.setLayoutParams(new LayoutParams(AndroidUtilities.dp(80.0f), AndroidUtilities.dp(100.0f)));
            return new Holder(hintDialogCell);
        }

        public void setIndex(int i) {
            notifyDataSetChanged();
        }
    }

    private class DialogSearchResult {
        public int date;
        public CharSequence name;
        public TLObject object;

        private DialogSearchResult() {
        }
    }

    public interface DialogsSearchAdapterDelegate {
        void didPressedOnSubDialog(long j);

        void needRemoveHint(int i);

        void searchStateChanged(boolean z);
    }

    protected static class RecentSearchObject {
        int date;
        long did;
        TLObject object;

        protected RecentSearchObject() {
        }
    }

    public DialogsSearchAdapter(Context context, int i, int i2) {
        this.searchAdapterHelper.setDelegate(new C38521());
        this.mContext = context;
        this.needMessagesSearch = i;
        this.dialogsType = i2;
        this.selfUserId = UserConfig.getClientUserId();
        loadRecentSearch();
        SearchQuery.loadHints(true);
    }

    private void searchDialogsInternal(final String str, final int i) {
        if (this.needMessagesSearch != 2) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$6$1 */
                class C38601 implements Comparator<DialogSearchResult> {
                    C38601() {
                    }

                    public int compare(DialogSearchResult dialogSearchResult, DialogSearchResult dialogSearchResult2) {
                        return dialogSearchResult.date < dialogSearchResult2.date ? 1 : dialogSearchResult.date > dialogSearchResult2.date ? -1 : 0;
                    }
                }

                public void run() {
                    try {
                        boolean x = C3791b.x(DialogsSearchAdapter.this.mContext);
                        List arrayList = x ? new ArrayList() : C3791b.b(DialogsSearchAdapter.this.mContext);
                        CharSequence toLowerCase = LocaleController.getString("SavedMessages", R.string.SavedMessages).toLowerCase();
                        String toLowerCase2 = str.trim().toLowerCase();
                        if (toLowerCase2.length() == 0) {
                            DialogsSearchAdapter.this.lastSearchId = -1;
                            DialogsSearchAdapter.this.updateSearchResults(new ArrayList(), new ArrayList(), new ArrayList(), DialogsSearchAdapter.this.lastSearchId);
                            return;
                        }
                        int i;
                        TLObject currentUser;
                        int i2;
                        int i3;
                        int i4;
                        AbstractSerializedData g;
                        DialogSearchResult dialogSearchResult;
                        String e;
                        String str;
                        SQLiteCursor b;
                        String e2;
                        AbstractSerializedData g2;
                        String translitString = LocaleController.getInstance().getTranslitString(toLowerCase2);
                        String str2 = (toLowerCase2.equals(translitString) || translitString.length() == 0) ? null : translitString;
                        String[] strArr = new String[((str2 != null ? 1 : 0) + 1)];
                        strArr[0] = toLowerCase2;
                        if (str2 != null) {
                            strArr[1] = str2;
                        }
                        Iterable arrayList2 = new ArrayList();
                        Iterable arrayList3 = new ArrayList();
                        Iterable arrayList4 = new ArrayList();
                        ArrayList arrayList5 = new ArrayList();
                        int i5 = 0;
                        HashMap hashMap = new HashMap();
                        SQLiteCursor b2 = MessagesStorage.getInstance().getDatabase().b("SELECT did, date FROM dialogs ORDER BY date DESC LIMIT 600", new Object[0]);
                        while (b2.a()) {
                            long d = b2.d(0);
                            if ((x || !(arrayList.contains(Long.valueOf(d)) || arrayList.contains(Long.valueOf(-d)))) && !C3791b.f(ApplicationLoader.applicationContext, d)) {
                                DialogSearchResult dialogSearchResult2 = new DialogSearchResult();
                                dialogSearchResult2.date = b2.b(1);
                                hashMap.put(Long.valueOf(d), dialogSearchResult2);
                                i = (int) d;
                                int i6 = (int) (d >> 32);
                                if (i != 0) {
                                    if (i6 == 1) {
                                        if (DialogsSearchAdapter.this.dialogsType == 0 && !arrayList3.contains(Integer.valueOf(i))) {
                                            arrayList3.add(Integer.valueOf(i));
                                        }
                                    } else if (i > 0) {
                                        if (!(DialogsSearchAdapter.this.dialogsType == 2 || arrayList2.contains(Integer.valueOf(i)))) {
                                            arrayList2.add(Integer.valueOf(i));
                                        }
                                    } else if (!arrayList3.contains(Integer.valueOf(-i))) {
                                        arrayList3.add(Integer.valueOf(-i));
                                    }
                                } else if (DialogsSearchAdapter.this.dialogsType == 0 && !arrayList4.contains(Integer.valueOf(i6))) {
                                    arrayList4.add(Integer.valueOf(i6));
                                }
                            }
                        }
                        b2.b();
                        if (toLowerCase.startsWith(toLowerCase2)) {
                            currentUser = UserConfig.getCurrentUser();
                            DialogSearchResult dialogSearchResult3 = new DialogSearchResult();
                            dialogSearchResult3.date = Integer.MAX_VALUE;
                            dialogSearchResult3.name = toLowerCase;
                            dialogSearchResult3.object = currentUser;
                            hashMap.put(Long.valueOf((long) currentUser.id), dialogSearchResult3);
                            i5 = 1;
                        }
                        if (!arrayList2.isEmpty()) {
                            SQLiteCursor b3 = MessagesStorage.getInstance().getDatabase().b(String.format(Locale.US, "SELECT data, status, name FROM users WHERE uid IN(%s)", new Object[]{TextUtils.join(",", arrayList2)}), new Object[0]);
                            i2 = i5;
                            while (b3.a()) {
                                String e3 = b3.e(2);
                                translitString = LocaleController.getInstance().getTranslitString(e3);
                                String str3 = e3.equals(translitString) ? null : translitString;
                                i5 = e3.lastIndexOf(";;;");
                                toLowerCase2 = i5 != -1 ? e3.substring(i5 + 3) : null;
                                int length = strArr.length;
                                i3 = 0;
                                i4 = 0;
                                while (i3 < length) {
                                    TLObject TLdeserialize;
                                    String str4 = strArr[i3];
                                    if (!e3.startsWith(str4)) {
                                        if (!e3.contains(" " + str4) && (str3 == null || !(str3.startsWith(str4) || str3.contains(" " + str4)))) {
                                            i5 = (toLowerCase2 == null || !toLowerCase2.startsWith(str4)) ? i4 : 2;
                                            if (i5 != 0) {
                                                g = b3.g(0);
                                                if (g != null) {
                                                    break;
                                                }
                                                TLdeserialize = User.TLdeserialize(g, g.readInt32(false), false);
                                                g.reuse();
                                                if (x || !arrayList.contains(Integer.valueOf(TLdeserialize.id))) {
                                                    dialogSearchResult = (DialogSearchResult) hashMap.get(Long.valueOf((long) TLdeserialize.id));
                                                    if (TLdeserialize.status != null) {
                                                        TLdeserialize.status.expires = b3.b(1);
                                                    }
                                                    if (i5 != 1) {
                                                        dialogSearchResult.name = AndroidUtilities.generateSearchName(TLdeserialize.first_name, TLdeserialize.last_name, str4);
                                                    } else {
                                                        dialogSearchResult.name = AndroidUtilities.generateSearchName("@" + TLdeserialize.username, null, "@" + str4);
                                                    }
                                                    dialogSearchResult.object = TLdeserialize;
                                                    i4 = i2 + 1;
                                                    i2 = i4;
                                                }
                                            }
                                            i3++;
                                            i4 = i5;
                                        }
                                    }
                                    i5 = 1;
                                    if (i5 != 0) {
                                        g = b3.g(0);
                                        if (g != null) {
                                            break;
                                        }
                                        TLdeserialize = User.TLdeserialize(g, g.readInt32(false), false);
                                        g.reuse();
                                        if (x) {
                                        }
                                        dialogSearchResult = (DialogSearchResult) hashMap.get(Long.valueOf((long) TLdeserialize.id));
                                        if (TLdeserialize.status != null) {
                                            TLdeserialize.status.expires = b3.b(1);
                                        }
                                        if (i5 != 1) {
                                            dialogSearchResult.name = AndroidUtilities.generateSearchName("@" + TLdeserialize.username, null, "@" + str4);
                                        } else {
                                            dialogSearchResult.name = AndroidUtilities.generateSearchName(TLdeserialize.first_name, TLdeserialize.last_name, str4);
                                        }
                                        dialogSearchResult.object = TLdeserialize;
                                        i4 = i2 + 1;
                                        i2 = i4;
                                    }
                                    i3++;
                                    i4 = i5;
                                }
                                i4 = i2;
                                i2 = i4;
                            }
                            b3.b();
                            i5 = i2;
                        }
                        if (!arrayList3.isEmpty()) {
                            SQLiteCursor b4 = MessagesStorage.getInstance().getDatabase().b(String.format(Locale.US, "SELECT data, name FROM chats WHERE uid IN(%s)", new Object[]{TextUtils.join(",", arrayList3)}), new Object[0]);
                            i2 = i5;
                            while (b4.a()) {
                                e = b4.e(1);
                                translitString = LocaleController.getInstance().getTranslitString(e);
                                str = e.equals(translitString) ? null : translitString;
                                i = strArr.length;
                                i4 = 0;
                                while (i4 < i) {
                                    String str5 = strArr[i4];
                                    if (e.startsWith(str5) || e.contains(" " + str5) || (str != null && (str.startsWith(str5) || str.contains(" " + str5)))) {
                                        g = b4.g(0);
                                        if (g != null) {
                                            TLObject TLdeserialize2 = Chat.TLdeserialize(g, g.readInt32(false), false);
                                            g.reuse();
                                            if (TLdeserialize2 == null || TLdeserialize2.deactivated || (ChatObject.isChannel(TLdeserialize2) && ChatObject.isNotInChat(TLdeserialize2))) {
                                                i4 = i2;
                                            } else {
                                                dialogSearchResult = (DialogSearchResult) hashMap.get(Long.valueOf(TLdeserialize2.id > 0 ? (long) (-TLdeserialize2.id) : AndroidUtilities.makeBroadcastId(TLdeserialize2.id)));
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName(TLdeserialize2.title, null, str5);
                                                dialogSearchResult.object = TLdeserialize2;
                                                i4 = i2 + 1;
                                            }
                                            i2 = i4;
                                        }
                                    } else {
                                        i4++;
                                    }
                                }
                            }
                            b4.b();
                            i5 = i2;
                        }
                        if (!arrayList4.isEmpty()) {
                            b = MessagesStorage.getInstance().getDatabase().b(String.format(Locale.US, "SELECT q.data, u.name, q.user, q.g, q.authkey, q.ttl, u.data, u.status, q.layer, q.seq_in, q.seq_out, q.use_count, q.exchange_id, q.key_date, q.fprint, q.fauthkey, q.khash, q.in_seq_no, q.admin_id, q.mtproto_seq FROM enc_chats as q INNER JOIN users as u ON q.user = u.uid WHERE q.uid IN(%s)", new Object[]{TextUtils.join(",", arrayList4)}), new Object[0]);
                            while (b.a()) {
                                e2 = b.e(1);
                                translitString = LocaleController.getInstance().getTranslitString(e2);
                                toLowerCase2 = e2.equals(translitString) ? null : translitString;
                                translitString = null;
                                i2 = e2.lastIndexOf(";;;");
                                if (i2 != -1) {
                                    translitString = e2.substring(i2 + 2);
                                }
                                i3 = 0;
                                i2 = 0;
                                while (i3 < strArr.length) {
                                    String str6 = strArr[i3];
                                    i = (e2.startsWith(str6) || e2.contains(" " + str6) || (toLowerCase2 != null && (toLowerCase2.startsWith(str6) || toLowerCase2.contains(" " + str6)))) ? 1 : (translitString == null || !translitString.startsWith(str6)) ? i2 : 2;
                                    if (i != 0) {
                                        TLObject tLObject;
                                        User user;
                                        AbstractSerializedData g3 = b.g(0);
                                        if (g3 != null) {
                                            currentUser = EncryptedChat.TLdeserialize(g3, g3.readInt32(false), false);
                                            g3.reuse();
                                            tLObject = currentUser;
                                        } else {
                                            tLObject = null;
                                        }
                                        g2 = b.g(6);
                                        if (g2 != null) {
                                            User TLdeserialize3 = User.TLdeserialize(g2, g2.readInt32(false), false);
                                            g2.reuse();
                                            user = TLdeserialize3;
                                        } else {
                                            user = null;
                                        }
                                        if (!(tLObject == null || user == null)) {
                                            dialogSearchResult = (DialogSearchResult) hashMap.get(Long.valueOf(((long) tLObject.id) << 32));
                                            tLObject.user_id = b.b(2);
                                            tLObject.a_or_b = b.f(3);
                                            tLObject.auth_key = b.f(4);
                                            tLObject.ttl = b.b(5);
                                            tLObject.layer = b.b(8);
                                            tLObject.seq_in = b.b(9);
                                            tLObject.seq_out = b.b(10);
                                            i2 = b.b(11);
                                            tLObject.key_use_count_in = (short) (i2 >> 16);
                                            tLObject.key_use_count_out = (short) i2;
                                            tLObject.exchange_id = b.d(12);
                                            tLObject.key_create_date = b.b(13);
                                            tLObject.future_key_fingerprint = b.d(14);
                                            tLObject.future_auth_key = b.f(15);
                                            tLObject.key_hash = b.f(16);
                                            tLObject.in_seq_no = b.b(17);
                                            i2 = b.b(18);
                                            if (i2 != 0) {
                                                tLObject.admin_id = i2;
                                            }
                                            tLObject.mtproto_seq = b.b(19);
                                            if (user.status != null) {
                                                user.status.expires = b.b(7);
                                            }
                                            if (i == 1) {
                                                dialogSearchResult.name = new SpannableStringBuilder(ContactsController.formatName(user.first_name, user.last_name));
                                                ((SpannableStringBuilder) dialogSearchResult.name).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_chats_secretName)), 0, dialogSearchResult.name.length(), 33);
                                            } else {
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName("@" + user.username, null, "@" + str6);
                                            }
                                            dialogSearchResult.object = tLObject;
                                            arrayList5.add(user);
                                            i4 = i5 + 1;
                                            i5 = i4;
                                        }
                                        i4 = i5;
                                        i5 = i4;
                                    } else {
                                        i3++;
                                        i2 = i;
                                    }
                                }
                                i4 = i5;
                                i5 = i4;
                            }
                            b.b();
                        }
                        ArrayList arrayList6 = new ArrayList(i5);
                        for (DialogSearchResult dialogSearchResult4 : hashMap.values()) {
                            if (!(dialogSearchResult4.object == null || dialogSearchResult4.name == null)) {
                                arrayList6.add(dialogSearchResult4);
                            }
                        }
                        Collections.sort(arrayList6, new C38601());
                        ArrayList arrayList7 = new ArrayList();
                        ArrayList arrayList8 = new ArrayList();
                        for (i2 = 0; i2 < arrayList6.size(); i2++) {
                            dialogSearchResult4 = (DialogSearchResult) arrayList6.get(i2);
                            arrayList7.add(dialogSearchResult4.object);
                            arrayList8.add(dialogSearchResult4.name);
                        }
                        if (DialogsSearchAdapter.this.dialogsType != 2) {
                            b = MessagesStorage.getInstance().getDatabase().b("SELECT u.data, u.status, u.name, u.uid FROM users as u INNER JOIN contacts as c ON u.uid = c.uid", new Object[0]);
                            while (b.a()) {
                                if (!hashMap.containsKey(Long.valueOf((long) b.b(3)))) {
                                    e2 = b.e(2);
                                    translitString = LocaleController.getInstance().getTranslitString(e2);
                                    e = e2.equals(translitString) ? null : translitString;
                                    i2 = e2.lastIndexOf(";;;");
                                    str = i2 != -1 ? e2.substring(i2 + 3) : null;
                                    int length2 = strArr.length;
                                    Object obj = null;
                                    i2 = 0;
                                    while (i2 < length2) {
                                        String str7 = strArr[i2];
                                        if (e2.startsWith(str7) || e2.contains(" " + str7) || (e != null && (e.startsWith(str7) || e.contains(" " + str7)))) {
                                            obj = 1;
                                        } else if (str != null && str.startsWith(str7)) {
                                            obj = 2;
                                        }
                                        if (i4 != null) {
                                            g2 = b.g(0);
                                            if (g2 != null) {
                                                User TLdeserialize4 = User.TLdeserialize(g2, g2.readInt32(false), false);
                                                g2.reuse();
                                                if (TLdeserialize4.status != null) {
                                                    TLdeserialize4.status.expires = b.b(1);
                                                }
                                                if (i4 == 1) {
                                                    arrayList8.add(AndroidUtilities.generateSearchName(TLdeserialize4.first_name, TLdeserialize4.last_name, str7));
                                                } else {
                                                    arrayList8.add(AndroidUtilities.generateSearchName("@" + TLdeserialize4.username, null, "@" + str7));
                                                }
                                                arrayList7.add(TLdeserialize4);
                                            }
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                            }
                            b.b();
                        }
                        DialogsSearchAdapter.this.updateSearchResults(arrayList7, arrayList8, arrayList5, i);
                    } catch (Throwable e4) {
                        FileLog.e(e4);
                    }
                }
            });
        }
    }

    private void searchMessagesInternal(String str) {
        if (this.needMessagesSearch == 0) {
            return;
        }
        if ((this.lastMessagesSearchString != null && this.lastMessagesSearchString.length() != 0) || (str != null && str.length() != 0)) {
            if (this.reqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.reqId, true);
                this.reqId = 0;
            }
            if (str == null || str.length() == 0) {
                this.searchResultMessages.clear();
                this.lastReqId = 0;
                this.lastMessagesSearchString = null;
                notifyDataSetChanged();
                if (this.delegate != null) {
                    this.delegate.searchStateChanged(false);
                    return;
                }
                return;
            }
            int i;
            final TLObject tLRPC$TL_messages_searchGlobal = new TLRPC$TL_messages_searchGlobal();
            tLRPC$TL_messages_searchGlobal.limit = 20;
            tLRPC$TL_messages_searchGlobal.f10168q = str;
            if (this.lastMessagesSearchString == null || !str.equals(this.lastMessagesSearchString) || this.searchResultMessages.isEmpty()) {
                tLRPC$TL_messages_searchGlobal.offset_date = 0;
                tLRPC$TL_messages_searchGlobal.offset_id = 0;
                tLRPC$TL_messages_searchGlobal.offset_peer = new TLRPC$TL_inputPeerEmpty();
            } else {
                MessageObject messageObject = (MessageObject) this.searchResultMessages.get(this.searchResultMessages.size() - 1);
                tLRPC$TL_messages_searchGlobal.offset_id = messageObject.getId();
                tLRPC$TL_messages_searchGlobal.offset_date = messageObject.messageOwner.date;
                i = messageObject.messageOwner.to_id.channel_id != 0 ? -messageObject.messageOwner.to_id.channel_id : messageObject.messageOwner.to_id.chat_id != 0 ? -messageObject.messageOwner.to_id.chat_id : messageObject.messageOwner.to_id.user_id;
                tLRPC$TL_messages_searchGlobal.offset_peer = MessagesController.getInputPeer(i);
            }
            this.lastMessagesSearchString = str;
            i = this.lastReqId + 1;
            this.lastReqId = i;
            if (this.delegate != null) {
                this.delegate.searchStateChanged(true);
            }
            this.reqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_searchGlobal, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            boolean z = true;
                            if (i == DialogsSearchAdapter.this.lastReqId && tLRPC$TL_error == null) {
                                TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
                                MessagesStorage.getInstance().putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
                                MessagesController.getInstance().putUsers(tLRPC$messages_Messages.users, false);
                                MessagesController.getInstance().putChats(tLRPC$messages_Messages.chats, false);
                                if (tLRPC$TL_messages_searchGlobal.offset_id == 0) {
                                    DialogsSearchAdapter.this.searchResultMessages.clear();
                                }
                                for (int i = 0; i < tLRPC$messages_Messages.messages.size(); i++) {
                                    Message message = (Message) tLRPC$messages_Messages.messages.get(i);
                                    DialogsSearchAdapter.this.searchResultMessages.add(new MessageObject(message, null, false));
                                    long dialogId = MessageObject.getDialogId(message);
                                    ConcurrentHashMap concurrentHashMap = message.out ? MessagesController.getInstance().dialogs_read_outbox_max : MessagesController.getInstance().dialogs_read_inbox_max;
                                    Integer num = (Integer) concurrentHashMap.get(Long.valueOf(dialogId));
                                    if (num == null) {
                                        num = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, dialogId));
                                        concurrentHashMap.put(Long.valueOf(dialogId), num);
                                    }
                                    message.unread = num.intValue() < message.id;
                                }
                                DialogsSearchAdapter dialogsSearchAdapter = DialogsSearchAdapter.this;
                                if (tLRPC$messages_Messages.messages.size() == 20) {
                                    z = false;
                                }
                                dialogsSearchAdapter.messagesSearchEndReached = z;
                                DialogsSearchAdapter.this.notifyDataSetChanged();
                            }
                            if (DialogsSearchAdapter.this.delegate != null) {
                                DialogsSearchAdapter.this.delegate.searchStateChanged(false);
                            }
                            DialogsSearchAdapter.this.reqId = 0;
                        }
                    });
                }
            }, 2);
        }
    }

    private void setRecentSearch(ArrayList<RecentSearchObject> arrayList, HashMap<Long, RecentSearchObject> hashMap) {
        this.recentSearchObjects = arrayList;
        this.recentSearchObjectsById = hashMap;
        boolean x = C3791b.x(this.mContext);
        List arrayList2 = x ? new ArrayList() : C3791b.b(this.mContext);
        for (int i = 0; i < this.recentSearchObjects.size(); i++) {
            RecentSearchObject recentSearchObject = (RecentSearchObject) this.recentSearchObjects.get(i);
            if (recentSearchObject.object instanceof User) {
                if (x || !arrayList2.contains(Long.valueOf((long) ((User) recentSearchObject.object).id))) {
                    MessagesController.getInstance().putUser((User) recentSearchObject.object, true);
                }
            } else if (recentSearchObject.object instanceof Chat) {
                if (x || !arrayList2.contains(Long.valueOf((long) ((Chat) recentSearchObject.object).id))) {
                    MessagesController.getInstance().putChat((Chat) recentSearchObject.object, true);
                }
            } else if ((recentSearchObject.object instanceof EncryptedChat) && (x || !arrayList2.contains(Long.valueOf((long) ((EncryptedChat) recentSearchObject.object).id)))) {
                MessagesController.getInstance().putEncryptedChat((EncryptedChat) recentSearchObject.object, true);
            }
        }
        notifyDataSetChanged();
    }

    private void updateSearchResults(ArrayList<TLObject> arrayList, ArrayList<CharSequence> arrayList2, ArrayList<User> arrayList3, int i) {
        final int i2 = i;
        final ArrayList<TLObject> arrayList4 = arrayList;
        final ArrayList<User> arrayList5 = arrayList3;
        final ArrayList<CharSequence> arrayList6 = arrayList2;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (i2 == DialogsSearchAdapter.this.lastSearchId) {
                    for (int i = 0; i < arrayList4.size(); i++) {
                        TLObject tLObject = (TLObject) arrayList4.get(i);
                        if (tLObject instanceof User) {
                            User user = (User) tLObject;
                            if (!C3791b.f(ApplicationLoader.applicationContext, (long) user.id)) {
                                MessagesController.getInstance().putUser(user, true);
                            }
                        } else if (tLObject instanceof Chat) {
                            MessagesController.getInstance().putChat((Chat) tLObject, true);
                        } else if (tLObject instanceof EncryptedChat) {
                            MessagesController.getInstance().putEncryptedChat((EncryptedChat) tLObject, true);
                        }
                    }
                    MessagesController.getInstance().putUsers(arrayList5, true);
                    DialogsSearchAdapter.this.searchResult = arrayList4;
                    DialogsSearchAdapter.this.searchResultNames = arrayList6;
                    DialogsSearchAdapter.this.searchAdapterHelper.mergeResults(DialogsSearchAdapter.this.searchResult);
                    DialogsSearchAdapter.this.notifyDataSetChanged();
                }
            }
        });
    }

    public void addHashtagsFromMessage(CharSequence charSequence) {
        this.searchAdapterHelper.addHashtagsFromMessage(charSequence);
    }

    public void clearRecentHashtags() {
        this.searchAdapterHelper.clearRecentHashtags();
        this.searchResultHashtags.clear();
        notifyDataSetChanged();
    }

    public void clearRecentSearch() {
        this.recentSearchObjectsById = new HashMap();
        this.recentSearchObjects = new ArrayList();
        notifyDataSetChanged();
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C38595());
    }

    public RecyclerListView getInnerListView() {
        return this.innerListView;
    }

    public Object getItem(int i) {
        int i2 = 0;
        if (isRecentSearchDisplayed()) {
            if (!SearchQuery.hints.isEmpty()) {
                i2 = 2;
            }
            if (i <= i2 || (i - 1) - i2 >= this.recentSearchObjects.size()) {
                return null;
            }
            TLObject tLObject = ((RecentSearchObject) this.recentSearchObjects.get((i - 1) - i2)).object;
            if (tLObject instanceof User) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(((User) tLObject).id));
                return user != null ? user : tLObject;
            } else if (!(tLObject instanceof Chat)) {
                return tLObject;
            } else {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(((Chat) tLObject).id));
                return chat != null ? chat : tLObject;
            }
        } else if (!this.searchResultHashtags.isEmpty()) {
            return i > 0 ? this.searchResultHashtags.get(i - 1) : null;
        } else {
            ArrayList globalSearch = this.searchAdapterHelper.getGlobalSearch();
            int size = this.searchResult.size();
            int size2 = globalSearch.isEmpty() ? 0 : globalSearch.size() + 1;
            if (!this.searchResultMessages.isEmpty()) {
                i2 = this.searchResultMessages.size() + 1;
            }
            return (i < 0 || i >= size) ? (i <= size || i >= size2 + size) ? (i <= size2 + size || i >= i2 + (size2 + size)) ? null : this.searchResultMessages.get(((i - size) - size2) - 1) : globalSearch.get((i - size) - 1) : this.searchResult.get(i);
        }
    }

    public int getItemCount() {
        int i = 0;
        int size;
        if (isRecentSearchDisplayed()) {
            size = !this.recentSearchObjects.isEmpty() ? this.recentSearchObjects.size() + 1 : 0;
            if (!SearchQuery.hints.isEmpty()) {
                i = 2;
            }
            return size + i;
        } else if (!this.searchResultHashtags.isEmpty()) {
            return this.searchResultHashtags.size() + 1;
        } else {
            size = this.searchResult.size();
            int size2 = this.searchAdapterHelper.getGlobalSearch().size();
            int size3 = this.searchResultMessages.size();
            if (size2 != 0) {
                size += size2 + 1;
            }
            if (size3 == 0) {
                return size;
            }
            size2 = size3 + 1;
            if (!this.messagesSearchEndReached) {
                i = 1;
            }
            return size + (i + size2);
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        int i2 = 2;
        if (isRecentSearchDisplayed()) {
            if (SearchQuery.hints.isEmpty()) {
                i2 = 0;
            }
            return i <= i2 ? (i == i2 || i % 2 == 0) ? 1 : 5 : 0;
        } else if (!this.searchResultHashtags.isEmpty()) {
            return i != 0 ? 4 : 1;
        } else {
            ArrayList globalSearch = this.searchAdapterHelper.getGlobalSearch();
            int size = this.searchResult.size();
            int size2 = globalSearch.isEmpty() ? 0 : globalSearch.size() + 1;
            int size3 = this.searchResultMessages.isEmpty() ? 0 : this.searchResultMessages.size() + 1;
            return ((i < 0 || i >= size) && (i <= size || i >= size2 + size)) ? (i <= size2 + size || i >= (size2 + size) + size3) ? (size3 == 0 || i != (size2 + size) + size3) ? 1 : 3 : 2 : 0;
        }
    }

    public String getLastSearchString() {
        return this.lastMessagesSearchString;
    }

    public boolean hasRecentRearch() {
        return (this.recentSearchObjects.isEmpty() && SearchQuery.hints.isEmpty()) ? false : true;
    }

    public boolean isEnabled(ViewHolder viewHolder) {
        int itemViewType = viewHolder.getItemViewType();
        return (itemViewType == 1 || itemViewType == 3) ? false : true;
    }

    public boolean isGlobalSearch(int i) {
        return i > this.searchResult.size() && i <= this.searchAdapterHelper.getGlobalSearch().size() + this.searchResult.size();
    }

    public boolean isMessagesSearchEndReached() {
        return this.messagesSearchEndReached;
    }

    public boolean isRecentSearchDisplayed() {
        return this.needMessagesSearch != 2 && ((this.lastSearchText == null || this.lastSearchText.length() == 0) && !(this.recentSearchObjects.isEmpty() && SearchQuery.hints.isEmpty()));
    }

    public void loadMoreSearchMessages() {
        searchMessagesInternal(this.lastMessagesSearchString);
    }

    public void loadRecentSearch() {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C38573());
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        CharSequence charSequence;
        switch (viewHolder.getItemViewType()) {
            case 0:
                TLObject tLObject;
                TLObject tLObject2;
                EncryptedChat encryptedChat;
                boolean z;
                CharSequence charSequence2;
                CharSequence charSequence3;
                ProfileSearchCell profileSearchCell = (ProfileSearchCell) viewHolder.itemView;
                Object item = getItem(i);
                TLObject tLObject3;
                if (item instanceof User) {
                    tLObject3 = (User) item;
                    tLObject = null;
                    tLObject2 = tLObject3;
                    charSequence = tLObject3.username;
                    encryptedChat = null;
                } else if (item instanceof Chat) {
                    TLObject chat = MessagesController.getInstance().getChat(Integer.valueOf(((Chat) item).id));
                    tLObject3 = chat == null ? (Chat) item : chat;
                    tLObject = tLObject3;
                    tLObject2 = null;
                    item = tLObject3.username;
                    encryptedChat = null;
                } else if (item instanceof EncryptedChat) {
                    EncryptedChat encryptedChat2 = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(((EncryptedChat) item).id));
                    tLObject = null;
                    tLObject2 = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat2.user_id));
                    encryptedChat = encryptedChat2;
                    charSequence = null;
                } else {
                    charSequence = null;
                    tLObject = null;
                    tLObject2 = null;
                    encryptedChat = null;
                }
                if (isRecentSearchDisplayed()) {
                    z = true;
                    profileSearchCell.useSeparator = i != getItemCount() + -1;
                    charSequence2 = null;
                    charSequence3 = null;
                } else {
                    ArrayList globalSearch = this.searchAdapterHelper.getGlobalSearch();
                    int size = this.searchResult.size();
                    boolean z2 = (i == getItemCount() + -1 || i == size - 1 || i == ((globalSearch.isEmpty() ? 0 : globalSearch.size() + 1) + size) - 1) ? false : true;
                    profileSearchCell.useSeparator = z2;
                    if (i < this.searchResult.size()) {
                        charSequence = (CharSequence) this.searchResultNames.get(i);
                        if (charSequence == null || tLObject2 == null || tLObject2.username == null || tLObject2.username.length() <= 0 || !charSequence.toString().startsWith("@" + tLObject2.username)) {
                            z = false;
                            charSequence2 = charSequence;
                            charSequence3 = null;
                        } else {
                            charSequence2 = null;
                            z = false;
                            charSequence3 = charSequence;
                        }
                    } else if (i <= this.searchResult.size() || charSequence == null) {
                        z = false;
                        charSequence2 = null;
                        charSequence3 = null;
                    } else {
                        String lastFoundUsername = this.searchAdapterHelper.getLastFoundUsername();
                        if (lastFoundUsername.startsWith("@")) {
                            lastFoundUsername = lastFoundUsername.substring(1);
                        }
                        try {
                            charSequence3 = new SpannableStringBuilder();
                            charSequence3.append("@");
                            charSequence3.append(charSequence);
                            if (charSequence.startsWith(lastFoundUsername)) {
                                charSequence3.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), 0, Math.min(charSequence3.length(), lastFoundUsername.length() + 1), 33);
                            }
                            z = false;
                            charSequence2 = null;
                        } catch (Throwable e) {
                            FileLog.e(e);
                            z = false;
                            charSequence2 = null;
                            charSequence3 = charSequence;
                        }
                    }
                }
                boolean z3 = false;
                if (tLObject2 != null && tLObject2.id == this.selfUserId) {
                    charSequence2 = LocaleController.getString("SavedMessages", R.string.SavedMessages);
                    charSequence3 = null;
                    z3 = true;
                }
                if (!(tLObject == null || tLObject.participants_count == 0)) {
                    CharSequence formatPluralString;
                    if (!ChatObject.isChannel(tLObject) || tLObject.megagroup) {
                        Object formatPluralString2 = LocaleController.formatPluralString("Members", tLObject.participants_count);
                    } else {
                        formatPluralString = LocaleController.formatPluralString("Subscribers", tLObject.participants_count);
                    }
                    if (charSequence3 instanceof SpannableStringBuilder) {
                        ((SpannableStringBuilder) charSequence3).append(", ").append(formatPluralString);
                    } else {
                        charSequence3 = !TextUtils.isEmpty(charSequence3) ? TextUtils.concat(new CharSequence[]{charSequence3, ", ", formatPluralString}) : formatPluralString;
                    }
                }
                profileSearchCell.setData(tLObject2 != null ? tLObject2 : tLObject, encryptedChat, charSequence2, charSequence3, z, z3);
                return;
            case 1:
                GraySectionCell graySectionCell = (GraySectionCell) viewHolder.itemView;
                if (isRecentSearchDisplayed()) {
                    if (i < (!SearchQuery.hints.isEmpty() ? 2 : 0)) {
                        graySectionCell.setText(LocaleController.getString("ChatHints", R.string.ChatHints).toUpperCase());
                        return;
                    } else {
                        graySectionCell.setText(LocaleController.getString("Recent", R.string.Recent).toUpperCase());
                        return;
                    }
                } else if (!this.searchResultHashtags.isEmpty()) {
                    graySectionCell.setText(LocaleController.getString("Hashtags", R.string.Hashtags).toUpperCase());
                    return;
                } else if (this.searchAdapterHelper.getGlobalSearch().isEmpty() || i != this.searchResult.size()) {
                    graySectionCell.setText(LocaleController.getString("SearchMessages", R.string.SearchMessages));
                    return;
                } else {
                    graySectionCell.setText(LocaleController.getString("GlobalSearch", R.string.GlobalSearch));
                    return;
                }
            case 2:
                DialogCell dialogCell = (DialogCell) viewHolder.itemView;
                dialogCell.useSeparator = i != getItemCount() + -1;
                MessageObject messageObject = (MessageObject) getItem(i);
                dialogCell.setDialog(messageObject.getDialogId(), messageObject, messageObject.messageOwner.date);
                return;
            case 4:
                HashtagSearchCell hashtagSearchCell = (HashtagSearchCell) viewHolder.itemView;
                hashtagSearchCell.setText((CharSequence) this.searchResultHashtags.get(i - 1));
                hashtagSearchCell.setNeedDivider(i != this.searchResultHashtags.size());
                return;
            case 5:
                ((CategoryAdapterRecycler) ((RecyclerListView) viewHolder.itemView).getAdapter()).setIndex(i / 2);
                return;
            default:
                return;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View profileSearchCell;
        switch (i) {
            case 0:
                profileSearchCell = new ProfileSearchCell(this.mContext);
                break;
            case 1:
                profileSearchCell = new GraySectionCell(this.mContext);
                break;
            case 2:
                profileSearchCell = new DialogCell(this.mContext, false);
                break;
            case 3:
                profileSearchCell = new LoadingCell(this.mContext);
                break;
            case 4:
                profileSearchCell = new HashtagSearchCell(this.mContext);
                break;
            case 5:
                profileSearchCell = new RecyclerListView(this.mContext) {
                    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                        if (!(getParent() == null || getParent().getParent() == null)) {
                            getParent().getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        return super.onInterceptTouchEvent(motionEvent);
                    }
                };
                profileSearchCell.setTag(Integer.valueOf(9));
                profileSearchCell.setItemAnimator(null);
                profileSearchCell.setLayoutAnimation(null);
                LayoutManager anonymousClass10 = new LinearLayoutManager(this.mContext) {
                    public boolean supportsPredictiveItemAnimations() {
                        return false;
                    }
                };
                anonymousClass10.setOrientation(0);
                profileSearchCell.setLayoutManager(anonymousClass10);
                profileSearchCell.setAdapter(new CategoryAdapterRecycler());
                profileSearchCell.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(View view, int i) {
                        if (DialogsSearchAdapter.this.delegate != null) {
                            DialogsSearchAdapter.this.delegate.didPressedOnSubDialog((long) ((Integer) view.getTag()).intValue());
                        }
                    }
                });
                profileSearchCell.setOnItemLongClickListener(new OnItemLongClickListener() {
                    public boolean onItemClick(View view, int i) {
                        if (DialogsSearchAdapter.this.delegate != null) {
                            DialogsSearchAdapter.this.delegate.needRemoveHint(((Integer) view.getTag()).intValue());
                        }
                        return true;
                    }
                });
                this.innerListView = profileSearchCell;
                break;
            default:
                profileSearchCell = null;
                break;
        }
        if (i == 5) {
            profileSearchCell.setLayoutParams(new LayoutParams(-1, AndroidUtilities.dp(100.0f)));
        } else {
            profileSearchCell.setLayoutParams(new LayoutParams(-1, -2));
        }
        return new Holder(profileSearchCell);
    }

    public void putRecentSearch(final long j, TLObject tLObject) {
        RecentSearchObject recentSearchObject = (RecentSearchObject) this.recentSearchObjectsById.get(Long.valueOf(j));
        if (recentSearchObject == null) {
            recentSearchObject = new RecentSearchObject();
            this.recentSearchObjectsById.put(Long.valueOf(j), recentSearchObject);
        } else {
            this.recentSearchObjects.remove(recentSearchObject);
        }
        this.recentSearchObjects.add(0, recentSearchObject);
        recentSearchObject.did = j;
        recentSearchObject.object = tLObject;
        recentSearchObject.date = (int) (System.currentTimeMillis() / 1000);
        notifyDataSetChanged();
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().a("REPLACE INTO search_recent VALUES(?, ?)");
                    a.d();
                    a.a(1, j);
                    a.a(2, (int) (System.currentTimeMillis() / 1000));
                    a.b();
                    a.e();
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void searchDialogs(final String str) {
        if (str == null || this.lastSearchText == null || !str.equals(this.lastSearchText)) {
            this.lastSearchText = str;
            try {
                if (this.searchTimer != null) {
                    this.searchTimer.cancel();
                    this.searchTimer = null;
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (str == null || str.length() == 0) {
                this.searchAdapterHelper.unloadRecentHashtags();
                this.searchResult.clear();
                this.searchResultNames.clear();
                this.searchResultHashtags.clear();
                this.searchAdapterHelper.mergeResults(null);
                if (this.needMessagesSearch != 2) {
                    this.searchAdapterHelper.queryServerSearch(null, true, true, true, true, 0, false);
                }
                searchMessagesInternal(null);
                notifyDataSetChanged();
                return;
            }
            if (this.needMessagesSearch != 2 && str.startsWith("#") && str.length() == 1) {
                this.messagesSearchEndReached = true;
                if (this.searchAdapterHelper.loadRecentHashtags()) {
                    this.searchResultMessages.clear();
                    this.searchResultHashtags.clear();
                    ArrayList hashtags = this.searchAdapterHelper.getHashtags();
                    for (int i = 0; i < hashtags.size(); i++) {
                        this.searchResultHashtags.add(((HashtagObject) hashtags.get(i)).hashtag);
                    }
                    if (this.delegate != null) {
                        this.delegate.searchStateChanged(false);
                    }
                } else if (this.delegate != null) {
                    this.delegate.searchStateChanged(true);
                }
                notifyDataSetChanged();
            } else {
                this.searchResultHashtags.clear();
                notifyDataSetChanged();
            }
            final int i2 = this.lastSearchId + 1;
            this.lastSearchId = i2;
            this.searchTimer = new Timer();
            this.searchTimer.schedule(new TimerTask() {

                /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$8$1 */
                class C38631 implements Runnable {
                    C38631() {
                    }

                    public void run() {
                        if (DialogsSearchAdapter.this.needMessagesSearch != 2) {
                            DialogsSearchAdapter.this.searchAdapterHelper.queryServerSearch(str, true, true, true, true, 0, false);
                        }
                        DialogsSearchAdapter.this.searchMessagesInternal(str);
                    }
                }

                public void run() {
                    try {
                        cancel();
                        DialogsSearchAdapter.this.searchTimer.cancel();
                        DialogsSearchAdapter.this.searchTimer = null;
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    DialogsSearchAdapter.this.searchDialogsInternal(str, i2);
                    AndroidUtilities.runOnUIThread(new C38631());
                }
            }, 200, 300);
        }
    }

    public void setDelegate(DialogsSearchAdapterDelegate dialogsSearchAdapterDelegate) {
        this.delegate = dialogsSearchAdapterDelegate;
    }
}
