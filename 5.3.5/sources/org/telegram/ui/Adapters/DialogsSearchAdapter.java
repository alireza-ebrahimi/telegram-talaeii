package org.telegram.ui.Adapters;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPeerEmpty;
import org.telegram.tgnet.TLRPC$TL_messages_searchGlobal;
import org.telegram.tgnet.TLRPC$TL_topPeer;
import org.telegram.tgnet.TLRPC$messages_Messages;
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
import utils.app.AppPreferences;

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
    class C20141 implements SearchAdapterHelperDelegate {
        C20141() {
        }

        public void onDataSetChanged() {
            DialogsSearchAdapter.this.notifyDataSetChanged();
        }

        public void onSetHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
            for (int a = 0; a < arrayList.size(); a++) {
                DialogsSearchAdapter.this.searchResultHashtags.add(((HashtagObject) arrayList.get(a)).hashtag);
            }
            if (DialogsSearchAdapter.this.delegate != null) {
                DialogsSearchAdapter.this.delegate.searchStateChanged(false);
            }
            DialogsSearchAdapter.this.notifyDataSetChanged();
        }
    }

    /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$3 */
    class C20193 implements Runnable {

        /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$3$1 */
        class C20171 implements Comparator<RecentSearchObject> {
            C20171() {
            }

            public int compare(RecentSearchObject lhs, RecentSearchObject rhs) {
                if (lhs.date < rhs.date) {
                    return 1;
                }
                if (lhs.date > rhs.date) {
                    return -1;
                }
                return 0;
            }
        }

        C20193() {
        }

        public void run() {
            try {
                long did;
                RecentSearchObject recentSearchObject;
                int a;
                SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized("SELECT did, date FROM search_recent WHERE 1", new Object[0]);
                ArrayList<Integer> usersToLoad = new ArrayList();
                ArrayList<Integer> chatsToLoad = new ArrayList();
                ArrayList<Integer> encryptedToLoad = new ArrayList();
                ArrayList<User> encUsers = new ArrayList();
                final ArrayList<RecentSearchObject> arrayList = new ArrayList();
                HashMap<Long, RecentSearchObject> hashMap = new HashMap();
                while (cursor.next()) {
                    did = cursor.longValue(0);
                    boolean add = false;
                    int lower_id = (int) did;
                    int high_id = (int) (did >> 32);
                    if (lower_id != 0) {
                        if (high_id == 1) {
                            if (DialogsSearchAdapter.this.dialogsType == 0 && !chatsToLoad.contains(Integer.valueOf(lower_id))) {
                                chatsToLoad.add(Integer.valueOf(lower_id));
                                add = true;
                            }
                        } else if (lower_id > 0) {
                            if (!(DialogsSearchAdapter.this.dialogsType == 2 || usersToLoad.contains(Integer.valueOf(lower_id)))) {
                                usersToLoad.add(Integer.valueOf(lower_id));
                                add = true;
                            }
                        } else if (!chatsToLoad.contains(Integer.valueOf(-lower_id))) {
                            chatsToLoad.add(Integer.valueOf(-lower_id));
                            add = true;
                        }
                    } else if (DialogsSearchAdapter.this.dialogsType == 0 && !encryptedToLoad.contains(Integer.valueOf(high_id))) {
                        encryptedToLoad.add(Integer.valueOf(high_id));
                        add = true;
                    }
                    if (add) {
                        recentSearchObject = new RecentSearchObject();
                        recentSearchObject.did = did;
                        recentSearchObject.date = cursor.intValue(1);
                        arrayList.add(recentSearchObject);
                        hashMap.put(Long.valueOf(recentSearchObject.did), recentSearchObject);
                    }
                }
                cursor.dispose();
                ArrayList<User> users = new ArrayList();
                if (!encryptedToLoad.isEmpty()) {
                    ArrayList<TLRPC$EncryptedChat> encryptedChats = new ArrayList();
                    MessagesStorage.getInstance().getEncryptedChatsInternal(TextUtils.join(",", encryptedToLoad), encryptedChats, usersToLoad);
                    for (a = 0; a < encryptedChats.size(); a++) {
                        ((RecentSearchObject) hashMap.get(Long.valueOf(((long) ((TLRPC$EncryptedChat) encryptedChats.get(a)).id) << 32))).object = (TLObject) encryptedChats.get(a);
                    }
                }
                if (!chatsToLoad.isEmpty()) {
                    ArrayList<TLRPC$Chat> chats = new ArrayList();
                    MessagesStorage.getInstance().getChatsInternal(TextUtils.join(",", chatsToLoad), chats);
                    for (a = 0; a < chats.size(); a++) {
                        TLRPC$Chat chat = (TLRPC$Chat) chats.get(a);
                        if (chat.id > 0) {
                            did = (long) (-chat.id);
                        } else {
                            did = AndroidUtilities.makeBroadcastId(chat.id);
                        }
                        if (chat.migrated_to != null) {
                            recentSearchObject = (RecentSearchObject) hashMap.remove(Long.valueOf(did));
                            if (recentSearchObject != null) {
                                arrayList.remove(recentSearchObject);
                            }
                        } else {
                            ((RecentSearchObject) hashMap.get(Long.valueOf(did))).object = chat;
                        }
                    }
                }
                if (!usersToLoad.isEmpty()) {
                    MessagesStorage.getInstance().getUsersInternal(TextUtils.join(",", usersToLoad), users);
                    for (a = 0; a < users.size(); a++) {
                        TLObject user = (User) users.get(a);
                        recentSearchObject = (RecentSearchObject) hashMap.get(Long.valueOf((long) user.id));
                        if (recentSearchObject != null) {
                            recentSearchObject.object = user;
                        }
                    }
                }
                Collections.sort(arrayList, new C20171());
                final HashMap<Long, RecentSearchObject> hashMap2 = hashMap;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        DialogsSearchAdapter.this.setRecentSearch(arrayList, hashMap2);
                    }
                });
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$5 */
    class C20215 implements Runnable {
        C20215() {
        }

        public void run() {
            try {
                MessagesStorage.getInstance().getDatabase().executeFast("DELETE FROM search_recent WHERE 1").stepThis().dispose();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    private class CategoryAdapterRecycler extends SelectionAdapter {
        private CategoryAdapterRecycler() {
        }

        public void setIndex(int value) {
            notifyDataSetChanged();
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new HintDialogCell(DialogsSearchAdapter.this.mContext);
            view.setLayoutParams(new LayoutParams(AndroidUtilities.dp(80.0f), AndroidUtilities.dp(100.0f)));
            return new Holder(view);
        }

        public boolean isEnabled(ViewHolder holder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            HintDialogCell cell = holder.itemView;
            boolean showHidden = AppPreferences.isShowHiddenDialogs(DialogsSearchAdapter.this.mContext);
            List<Long> hiddenList;
            if (showHidden) {
                hiddenList = new ArrayList();
            } else {
                hiddenList = AppPreferences.getHiddenList(DialogsSearchAdapter.this.mContext);
            }
            cell.setLayoutParams(new LayoutParams(AndroidUtilities.dp(80.0f), AndroidUtilities.dp(100.0f)));
            TLRPC$TL_topPeer peer = (TLRPC$TL_topPeer) SearchQuery.hints.get(position);
            TLRPC$TL_dialog dialog = new TLRPC$TL_dialog();
            TLRPC$Chat chat = null;
            User user = null;
            int did = 0;
            if (peer.peer.user_id != 0) {
                did = peer.peer.user_id;
                user = MessagesController.getInstance().getUser(Integer.valueOf(peer.peer.user_id));
            } else if (peer.peer.channel_id != 0) {
                did = -peer.peer.channel_id;
                chat = MessagesController.getInstance().getChat(Integer.valueOf(peer.peer.channel_id));
            } else if (peer.peer.chat_id != 0) {
                did = -peer.peer.chat_id;
                chat = MessagesController.getInstance().getChat(Integer.valueOf(peer.peer.chat_id));
            }
            if (!showHidden && (hiddenList.contains(Long.valueOf((long) did)) || hiddenList.contains(Long.valueOf(-((long) did))))) {
                cell.setLayoutParams(new LayoutParams(AndroidUtilities.dp(0.0f), AndroidUtilities.dp(100.0f)));
            }
            cell.setTag(Integer.valueOf(did));
            String name = "";
            if (user != null) {
                name = ContactsController.formatName(user.first_name, user.last_name);
            } else if (chat != null) {
                name = chat.title;
            }
            cell.setDialog(did, true, name);
        }

        public int getItemCount() {
            return SearchQuery.hints.size();
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

    public DialogsSearchAdapter(Context context, int messagesSearch, int type) {
        this.searchAdapterHelper.setDelegate(new C20141());
        this.mContext = context;
        this.needMessagesSearch = messagesSearch;
        this.dialogsType = type;
        this.selfUserId = UserConfig.getClientUserId();
        loadRecentSearch();
        SearchQuery.loadHints(true);
    }

    public RecyclerListView getInnerListView() {
        return this.innerListView;
    }

    public void setDelegate(DialogsSearchAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    public boolean isMessagesSearchEndReached() {
        return this.messagesSearchEndReached;
    }

    public void loadMoreSearchMessages() {
        searchMessagesInternal(this.lastMessagesSearchString);
    }

    public String getLastSearchString() {
        return this.lastMessagesSearchString;
    }

    private void searchMessagesInternal(String query) {
        if (this.needMessagesSearch == 0) {
            return;
        }
        if ((this.lastMessagesSearchString != null && this.lastMessagesSearchString.length() != 0) || (query != null && query.length() != 0)) {
            if (this.reqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.reqId, true);
                this.reqId = 0;
            }
            if (query == null || query.length() == 0) {
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
            final TLRPC$TL_messages_searchGlobal req = new TLRPC$TL_messages_searchGlobal();
            req.limit = 20;
            req.f89q = query;
            if (this.lastMessagesSearchString == null || !query.equals(this.lastMessagesSearchString) || this.searchResultMessages.isEmpty()) {
                req.offset_date = 0;
                req.offset_id = 0;
                req.offset_peer = new TLRPC$TL_inputPeerEmpty();
            } else {
                int id;
                MessageObject lastMessage = (MessageObject) this.searchResultMessages.get(this.searchResultMessages.size() - 1);
                req.offset_id = lastMessage.getId();
                req.offset_date = lastMessage.messageOwner.date;
                if (lastMessage.messageOwner.to_id.channel_id != 0) {
                    id = -lastMessage.messageOwner.to_id.channel_id;
                } else if (lastMessage.messageOwner.to_id.chat_id != 0) {
                    id = -lastMessage.messageOwner.to_id.chat_id;
                } else {
                    id = lastMessage.messageOwner.to_id.user_id;
                }
                req.offset_peer = MessagesController.getInputPeer(id);
            }
            this.lastMessagesSearchString = query;
            final int currentReqId = this.lastReqId + 1;
            this.lastReqId = currentReqId;
            if (this.delegate != null) {
                this.delegate.searchStateChanged(true);
            }
            this.reqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            boolean z = true;
                            if (currentReqId == DialogsSearchAdapter.this.lastReqId && error == null) {
                                TLRPC$messages_Messages res = response;
                                MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, true);
                                MessagesController.getInstance().putUsers(res.users, false);
                                MessagesController.getInstance().putChats(res.chats, false);
                                if (req.offset_id == 0) {
                                    DialogsSearchAdapter.this.searchResultMessages.clear();
                                }
                                for (int a = 0; a < res.messages.size(); a++) {
                                    boolean z2;
                                    TLRPC$Message message = (TLRPC$Message) res.messages.get(a);
                                    DialogsSearchAdapter.this.searchResultMessages.add(new MessageObject(message, null, false));
                                    long dialog_id = MessageObject.getDialogId(message);
                                    ConcurrentHashMap<Long, Integer> read_max = message.out ? MessagesController.getInstance().dialogs_read_outbox_max : MessagesController.getInstance().dialogs_read_inbox_max;
                                    Integer value = (Integer) read_max.get(Long.valueOf(dialog_id));
                                    if (value == null) {
                                        value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, dialog_id));
                                        read_max.put(Long.valueOf(dialog_id), value);
                                    }
                                    if (value.intValue() < message.id) {
                                        z2 = true;
                                    } else {
                                        z2 = false;
                                    }
                                    message.unread = z2;
                                }
                                DialogsSearchAdapter dialogsSearchAdapter = DialogsSearchAdapter.this;
                                if (res.messages.size() == 20) {
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

    public boolean hasRecentRearch() {
        return (this.recentSearchObjects.isEmpty() && SearchQuery.hints.isEmpty()) ? false : true;
    }

    public boolean isRecentSearchDisplayed() {
        return this.needMessagesSearch != 2 && ((this.lastSearchText == null || this.lastSearchText.length() == 0) && !(this.recentSearchObjects.isEmpty() && SearchQuery.hints.isEmpty()));
    }

    public void loadRecentSearch() {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C20193());
    }

    public void putRecentSearch(final long did, TLObject object) {
        RecentSearchObject recentSearchObject = (RecentSearchObject) this.recentSearchObjectsById.get(Long.valueOf(did));
        if (recentSearchObject == null) {
            recentSearchObject = new RecentSearchObject();
            this.recentSearchObjectsById.put(Long.valueOf(did), recentSearchObject);
        } else {
            this.recentSearchObjects.remove(recentSearchObject);
        }
        this.recentSearchObjects.add(0, recentSearchObject);
        recentSearchObject.did = did;
        recentSearchObject.object = object;
        recentSearchObject.date = (int) (System.currentTimeMillis() / 1000);
        notifyDataSetChanged();
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    SQLitePreparedStatement state = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO search_recent VALUES(?, ?)");
                    state.requery();
                    state.bindLong(1, did);
                    state.bindInteger(2, (int) (System.currentTimeMillis() / 1000));
                    state.step();
                    state.dispose();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void clearRecentSearch() {
        this.recentSearchObjectsById = new HashMap();
        this.recentSearchObjects = new ArrayList();
        notifyDataSetChanged();
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C20215());
    }

    public void addHashtagsFromMessage(CharSequence message) {
        this.searchAdapterHelper.addHashtagsFromMessage(message);
    }

    private void setRecentSearch(ArrayList<RecentSearchObject> arrayList, HashMap<Long, RecentSearchObject> hashMap) {
        this.recentSearchObjects = arrayList;
        this.recentSearchObjectsById = hashMap;
        boolean showHidden = AppPreferences.isShowHiddenDialogs(this.mContext);
        List<Long> hiddenList;
        if (showHidden) {
            hiddenList = new ArrayList();
        } else {
            hiddenList = AppPreferences.getHiddenList(this.mContext);
        }
        for (int a = 0; a < this.recentSearchObjects.size(); a++) {
            RecentSearchObject recentSearchObject = (RecentSearchObject) this.recentSearchObjects.get(a);
            if (recentSearchObject.object instanceof User) {
                if (showHidden || !hiddenList.contains(Long.valueOf((long) ((User) recentSearchObject.object).id))) {
                    MessagesController.getInstance().putUser((User) recentSearchObject.object, true);
                }
            } else if (recentSearchObject.object instanceof TLRPC$Chat) {
                if (showHidden || !hiddenList.contains(Long.valueOf((long) ((TLRPC$Chat) recentSearchObject.object).id))) {
                    MessagesController.getInstance().putChat((TLRPC$Chat) recentSearchObject.object, true);
                }
            } else if ((recentSearchObject.object instanceof TLRPC$EncryptedChat) && (showHidden || !hiddenList.contains(Long.valueOf((long) ((TLRPC$EncryptedChat) recentSearchObject.object).id)))) {
                MessagesController.getInstance().putEncryptedChat((TLRPC$EncryptedChat) recentSearchObject.object, true);
            }
        }
        notifyDataSetChanged();
    }

    private void searchDialogsInternal(final String query, final int searchId) {
        if (this.needMessagesSearch != 2) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$6$1 */
                class C20221 implements Comparator<DialogSearchResult> {
                    C20221() {
                    }

                    public int compare(DialogSearchResult lhs, DialogSearchResult rhs) {
                        if (lhs.date < rhs.date) {
                            return 1;
                        }
                        if (lhs.date > rhs.date) {
                            return -1;
                        }
                        return 0;
                    }
                }

                public void run() {
                    try {
                        boolean showHidden = AppPreferences.isShowHiddenDialogs(DialogsSearchAdapter.this.mContext);
                        List<Long> hiddenList = showHidden ? new ArrayList() : AppPreferences.getHiddenList(DialogsSearchAdapter.this.mContext);
                        String savedMessages = LocaleController.getString("SavedMessages", R.string.SavedMessages).toLowerCase();
                        String search1 = query.trim().toLowerCase();
                        if (search1.length() == 0) {
                            DialogsSearchAdapter.this.lastSearchId = -1;
                            DialogsSearchAdapter.this.updateSearchResults(new ArrayList(), new ArrayList(), new ArrayList(), DialogsSearchAdapter.this.lastSearchId);
                            return;
                        }
                        DialogSearchResult dialogSearchResult;
                        TLObject user;
                        String name;
                        String tName;
                        String username;
                        int usernamePos;
                        int found;
                        int i;
                        int length;
                        String q;
                        NativeByteBuffer data;
                        int a;
                        User user2;
                        String search2 = LocaleController.getInstance().getTranslitString(search1);
                        if (search1.equals(search2) || search2.length() == 0) {
                            search2 = null;
                        }
                        String[] search = new String[((search2 != null ? 1 : 0) + 1)];
                        search[0] = search1;
                        if (search2 != null) {
                            search[1] = search2;
                        }
                        ArrayList<Integer> usersToLoad = new ArrayList();
                        ArrayList<Integer> chatsToLoad = new ArrayList();
                        ArrayList<Integer> encryptedToLoad = new ArrayList();
                        ArrayList<User> encUsers = new ArrayList();
                        int resultCount = 0;
                        HashMap<Long, DialogSearchResult> dialogsResult = new HashMap();
                        SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized("SELECT did, date FROM dialogs ORDER BY date DESC LIMIT 600", new Object[0]);
                        while (cursor.next()) {
                            long id = cursor.longValue(0);
                            if (showHidden || !(hiddenList.contains(Long.valueOf(id)) || hiddenList.contains(Long.valueOf(-id)))) {
                                dialogSearchResult = new DialogSearchResult();
                                dialogSearchResult.date = cursor.intValue(1);
                                dialogsResult.put(Long.valueOf(id), dialogSearchResult);
                                int lower_id = (int) id;
                                int high_id = (int) (id >> 32);
                                if (lower_id != 0) {
                                    if (high_id == 1) {
                                        if (DialogsSearchAdapter.this.dialogsType == 0 && !chatsToLoad.contains(Integer.valueOf(lower_id))) {
                                            chatsToLoad.add(Integer.valueOf(lower_id));
                                        }
                                    } else if (lower_id > 0) {
                                        if (!(DialogsSearchAdapter.this.dialogsType == 2 || usersToLoad.contains(Integer.valueOf(lower_id)))) {
                                            usersToLoad.add(Integer.valueOf(lower_id));
                                        }
                                    } else if (!chatsToLoad.contains(Integer.valueOf(-lower_id))) {
                                        chatsToLoad.add(Integer.valueOf(-lower_id));
                                    }
                                } else if (DialogsSearchAdapter.this.dialogsType == 0 && !encryptedToLoad.contains(Integer.valueOf(high_id))) {
                                    encryptedToLoad.add(Integer.valueOf(high_id));
                                }
                            }
                        }
                        cursor.dispose();
                        if (savedMessages.startsWith(search1)) {
                            user = UserConfig.getCurrentUser();
                            dialogSearchResult = new DialogSearchResult();
                            dialogSearchResult.date = Integer.MAX_VALUE;
                            dialogSearchResult.name = savedMessages;
                            dialogSearchResult.object = user;
                            dialogsResult.put(Long.valueOf((long) user.id), dialogSearchResult);
                            resultCount = 0 + 1;
                        }
                        if (!usersToLoad.isEmpty()) {
                            cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data, status, name FROM users WHERE uid IN(%s)", new Object[]{TextUtils.join(",", usersToLoad)}), new Object[0]);
                            while (cursor.next()) {
                                name = cursor.stringValue(2);
                                tName = LocaleController.getInstance().getTranslitString(name);
                                if (name.equals(tName)) {
                                    tName = null;
                                }
                                username = null;
                                usernamePos = name.lastIndexOf(";;;");
                                if (usernamePos != -1) {
                                    username = name.substring(usernamePos + 3);
                                }
                                found = 0;
                                for (String q2 : search) {
                                    if (name.startsWith(q2) || name.contains(" " + q2) || (tName != null && (tName.startsWith(q2) || tName.contains(" " + q2)))) {
                                        found = 1;
                                    } else if (username != null && username.startsWith(q2)) {
                                        found = 2;
                                    }
                                    if (found != 0) {
                                        data = cursor.byteBufferValue(0);
                                        if (data == null) {
                                            break;
                                        }
                                        user = User.TLdeserialize(data, data.readInt32(false), false);
                                        data.reuse();
                                        if (showHidden || !hiddenList.contains(Integer.valueOf(user.id))) {
                                            dialogSearchResult = (DialogSearchResult) dialogsResult.get(Long.valueOf((long) user.id));
                                            if (user.status != null) {
                                                user.status.expires = cursor.intValue(1);
                                            }
                                            if (found == 1) {
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName(user.first_name, user.last_name, q2);
                                            } else {
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName("@" + user.username, null, "@" + q2);
                                            }
                                            dialogSearchResult.object = user;
                                            resultCount++;
                                        }
                                    }
                                }
                            }
                            cursor.dispose();
                        }
                        if (!chatsToLoad.isEmpty()) {
                            cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT data, name FROM chats WHERE uid IN(%s)", new Object[]{TextUtils.join(",", chatsToLoad)}), new Object[0]);
                            while (cursor.next()) {
                                name = cursor.stringValue(1);
                                tName = LocaleController.getInstance().getTranslitString(name);
                                if (name.equals(tName)) {
                                    tName = null;
                                }
                                length = search.length;
                                i = 0;
                                while (i < length) {
                                    q2 = search[i];
                                    if (name.startsWith(q2) || name.contains(" " + q2) || (tName != null && (tName.startsWith(q2) || tName.contains(" " + q2)))) {
                                        data = cursor.byteBufferValue(0);
                                        if (data != null) {
                                            TLRPC$Chat chat = TLRPC$Chat.TLdeserialize(data, data.readInt32(false), false);
                                            data.reuse();
                                            if (!(chat == null || chat.deactivated)) {
                                                if (!ChatObject.isChannel(chat) || !ChatObject.isNotInChat(chat)) {
                                                    long dialog_id;
                                                    if (chat.id > 0) {
                                                        dialog_id = (long) (-chat.id);
                                                    } else {
                                                        dialog_id = AndroidUtilities.makeBroadcastId(chat.id);
                                                    }
                                                    dialogSearchResult = (DialogSearchResult) dialogsResult.get(Long.valueOf(dialog_id));
                                                    dialogSearchResult.name = AndroidUtilities.generateSearchName(chat.title, null, q2);
                                                    dialogSearchResult.object = chat;
                                                    resultCount++;
                                                }
                                            }
                                        }
                                    } else {
                                        i++;
                                    }
                                }
                            }
                            cursor.dispose();
                        }
                        if (!encryptedToLoad.isEmpty()) {
                            cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT q.data, u.name, q.user, q.g, q.authkey, q.ttl, u.data, u.status, q.layer, q.seq_in, q.seq_out, q.use_count, q.exchange_id, q.key_date, q.fprint, q.fauthkey, q.khash, q.in_seq_no, q.admin_id, q.mtproto_seq FROM enc_chats as q INNER JOIN users as u ON q.user = u.uid WHERE q.uid IN(%s)", new Object[]{TextUtils.join(",", encryptedToLoad)}), new Object[0]);
                            while (cursor.next()) {
                                name = cursor.stringValue(1);
                                tName = LocaleController.getInstance().getTranslitString(name);
                                if (name.equals(tName)) {
                                    tName = null;
                                }
                                username = null;
                                usernamePos = name.lastIndexOf(";;;");
                                if (usernamePos != -1) {
                                    username = name.substring(usernamePos + 2);
                                }
                                found = 0;
                                a = 0;
                                while (a < search.length) {
                                    q2 = search[a];
                                    if (name.startsWith(q2) || name.contains(" " + q2) || (tName != null && (tName.startsWith(q2) || tName.contains(" " + q2)))) {
                                        found = 1;
                                    } else if (username != null && username.startsWith(q2)) {
                                        found = 2;
                                    }
                                    if (found != 0) {
                                        TLRPC$EncryptedChat chat2 = null;
                                        user2 = null;
                                        data = cursor.byteBufferValue(0);
                                        if (data != null) {
                                            chat2 = TLRPC$EncryptedChat.TLdeserialize(data, data.readInt32(false), false);
                                            data.reuse();
                                        }
                                        data = cursor.byteBufferValue(6);
                                        if (data != null) {
                                            user2 = User.TLdeserialize(data, data.readInt32(false), false);
                                            data.reuse();
                                        }
                                        if (!(chat2 == null || user2 == null)) {
                                            dialogSearchResult = (DialogSearchResult) dialogsResult.get(Long.valueOf(((long) chat2.id) << 32));
                                            chat2.user_id = cursor.intValue(2);
                                            chat2.a_or_b = cursor.byteArrayValue(3);
                                            chat2.auth_key = cursor.byteArrayValue(4);
                                            chat2.ttl = cursor.intValue(5);
                                            chat2.layer = cursor.intValue(8);
                                            chat2.seq_in = cursor.intValue(9);
                                            chat2.seq_out = cursor.intValue(10);
                                            int use_count = cursor.intValue(11);
                                            chat2.key_use_count_in = (short) (use_count >> 16);
                                            chat2.key_use_count_out = (short) use_count;
                                            chat2.exchange_id = cursor.longValue(12);
                                            chat2.key_create_date = cursor.intValue(13);
                                            chat2.future_key_fingerprint = cursor.longValue(14);
                                            chat2.future_auth_key = cursor.byteArrayValue(15);
                                            chat2.key_hash = cursor.byteArrayValue(16);
                                            chat2.in_seq_no = cursor.intValue(17);
                                            int admin_id = cursor.intValue(18);
                                            if (admin_id != 0) {
                                                chat2.admin_id = admin_id;
                                            }
                                            chat2.mtproto_seq = cursor.intValue(19);
                                            if (user2.status != null) {
                                                user2.status.expires = cursor.intValue(7);
                                            }
                                            if (found == 1) {
                                                dialogSearchResult.name = new SpannableStringBuilder(ContactsController.formatName(user2.first_name, user2.last_name));
                                                ((SpannableStringBuilder) dialogSearchResult.name).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_chats_secretName)), 0, dialogSearchResult.name.length(), 33);
                                            } else {
                                                dialogSearchResult.name = AndroidUtilities.generateSearchName("@" + user2.username, null, "@" + q2);
                                            }
                                            dialogSearchResult.object = chat2;
                                            encUsers.add(user2);
                                            resultCount++;
                                        }
                                    } else {
                                        a++;
                                    }
                                }
                            }
                            cursor.dispose();
                        }
                        ArrayList<DialogSearchResult> arrayList = new ArrayList(resultCount);
                        for (DialogSearchResult dialogSearchResult2 : dialogsResult.values()) {
                            if (!(dialogSearchResult2.object == null || dialogSearchResult2.name == null)) {
                                arrayList.add(dialogSearchResult2);
                            }
                        }
                        Collections.sort(arrayList, new C20221());
                        ArrayList<TLObject> resultArray = new ArrayList();
                        ArrayList<CharSequence> resultArrayNames = new ArrayList();
                        for (a = 0; a < arrayList.size(); a++) {
                            dialogSearchResult2 = (DialogSearchResult) arrayList.get(a);
                            resultArray.add(dialogSearchResult2.object);
                            resultArrayNames.add(dialogSearchResult2.name);
                        }
                        if (DialogsSearchAdapter.this.dialogsType != 2) {
                            cursor = MessagesStorage.getInstance().getDatabase().queryFinalized("SELECT u.data, u.status, u.name, u.uid FROM users as u INNER JOIN contacts as c ON u.uid = c.uid", new Object[0]);
                            while (cursor.next()) {
                                if (!dialogsResult.containsKey(Long.valueOf((long) cursor.intValue(3)))) {
                                    name = cursor.stringValue(2);
                                    tName = LocaleController.getInstance().getTranslitString(name);
                                    if (name.equals(tName)) {
                                        tName = null;
                                    }
                                    username = null;
                                    usernamePos = name.lastIndexOf(";;;");
                                    if (usernamePos != -1) {
                                        username = name.substring(usernamePos + 3);
                                    }
                                    found = 0;
                                    length = search.length;
                                    i = 0;
                                    while (i < length) {
                                        q2 = search[i];
                                        if (name.startsWith(q2) || name.contains(" " + q2) || (tName != null && (tName.startsWith(q2) || tName.contains(" " + q2)))) {
                                            found = 1;
                                        } else if (username != null && username.startsWith(q2)) {
                                            found = 2;
                                        }
                                        if (found != 0) {
                                            data = cursor.byteBufferValue(0);
                                            if (data != null) {
                                                user2 = User.TLdeserialize(data, data.readInt32(false), false);
                                                data.reuse();
                                                if (user2.status != null) {
                                                    user2.status.expires = cursor.intValue(1);
                                                }
                                                if (found == 1) {
                                                    resultArrayNames.add(AndroidUtilities.generateSearchName(user2.first_name, user2.last_name, q2));
                                                } else {
                                                    resultArrayNames.add(AndroidUtilities.generateSearchName("@" + user2.username, null, "@" + q2));
                                                }
                                                resultArray.add(user2);
                                            }
                                        } else {
                                            i++;
                                        }
                                    }
                                }
                            }
                            cursor.dispose();
                        }
                        DialogsSearchAdapter.this.updateSearchResults(resultArray, resultArrayNames, encUsers, searchId);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
        }
    }

    private void updateSearchResults(ArrayList<TLObject> result, ArrayList<CharSequence> names, ArrayList<User> encUsers, int searchId) {
        final int i = searchId;
        final ArrayList<TLObject> arrayList = result;
        final ArrayList<User> arrayList2 = encUsers;
        final ArrayList<CharSequence> arrayList3 = names;
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (i == DialogsSearchAdapter.this.lastSearchId) {
                    for (int a = 0; a < arrayList.size(); a++) {
                        TLObject obj = (TLObject) arrayList.get(a);
                        if (obj instanceof User) {
                            MessagesController.getInstance().putUser((User) obj, true);
                        } else if (obj instanceof TLRPC$Chat) {
                            MessagesController.getInstance().putChat((TLRPC$Chat) obj, true);
                        } else if (obj instanceof TLRPC$EncryptedChat) {
                            MessagesController.getInstance().putEncryptedChat((TLRPC$EncryptedChat) obj, true);
                        }
                    }
                    MessagesController.getInstance().putUsers(arrayList2, true);
                    DialogsSearchAdapter.this.searchResult = arrayList;
                    DialogsSearchAdapter.this.searchResultNames = arrayList3;
                    DialogsSearchAdapter.this.searchAdapterHelper.mergeResults(DialogsSearchAdapter.this.searchResult);
                    DialogsSearchAdapter.this.notifyDataSetChanged();
                }
            }
        });
    }

    public boolean isGlobalSearch(int i) {
        return i > this.searchResult.size() && i <= this.searchAdapterHelper.getGlobalSearch().size() + this.searchResult.size();
    }

    public void clearRecentHashtags() {
        this.searchAdapterHelper.clearRecentHashtags();
        this.searchResultHashtags.clear();
        notifyDataSetChanged();
    }

    public void searchDialogs(final String query) {
        if (query == null || this.lastSearchText == null || !query.equals(this.lastSearchText)) {
            this.lastSearchText = query;
            try {
                if (this.searchTimer != null) {
                    this.searchTimer.cancel();
                    this.searchTimer = null;
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (query == null || query.length() == 0) {
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
            if (this.needMessagesSearch != 2 && query.startsWith("#") && query.length() == 1) {
                this.messagesSearchEndReached = true;
                if (this.searchAdapterHelper.loadRecentHashtags()) {
                    this.searchResultMessages.clear();
                    this.searchResultHashtags.clear();
                    ArrayList<HashtagObject> hashtags = this.searchAdapterHelper.getHashtags();
                    for (int a = 0; a < hashtags.size(); a++) {
                        this.searchResultHashtags.add(((HashtagObject) hashtags.get(a)).hashtag);
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
            final int searchId = this.lastSearchId + 1;
            this.lastSearchId = searchId;
            this.searchTimer = new Timer();
            this.searchTimer.schedule(new TimerTask() {

                /* renamed from: org.telegram.ui.Adapters.DialogsSearchAdapter$8$1 */
                class C20251 implements Runnable {
                    C20251() {
                    }

                    public void run() {
                        if (DialogsSearchAdapter.this.needMessagesSearch != 2) {
                            DialogsSearchAdapter.this.searchAdapterHelper.queryServerSearch(query, true, true, true, true, 0, false);
                        }
                        DialogsSearchAdapter.this.searchMessagesInternal(query);
                    }
                }

                public void run() {
                    try {
                        cancel();
                        DialogsSearchAdapter.this.searchTimer.cancel();
                        DialogsSearchAdapter.this.searchTimer = null;
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    DialogsSearchAdapter.this.searchDialogsInternal(query, searchId);
                    AndroidUtilities.runOnUIThread(new C20251());
                }
            }, 200, 300);
        }
    }

    public int getItemCount() {
        int i = 0;
        int i2;
        if (isRecentSearchDisplayed()) {
            if (this.recentSearchObjects.isEmpty()) {
                i2 = 0;
            } else {
                i2 = this.recentSearchObjects.size() + 1;
            }
            if (!SearchQuery.hints.isEmpty()) {
                i = 2;
            }
            return i2 + i;
        } else if (!this.searchResultHashtags.isEmpty()) {
            return this.searchResultHashtags.size() + 1;
        } else {
            int count = this.searchResult.size();
            int globalCount = this.searchAdapterHelper.getGlobalSearch().size();
            int messagesCount = this.searchResultMessages.size();
            if (globalCount != 0) {
                count += globalCount + 1;
            }
            if (messagesCount == 0) {
                return count;
            }
            i2 = messagesCount + 1;
            if (!this.messagesSearchEndReached) {
                i = 1;
            }
            return count + (i2 + i);
        }
    }

    public Object getItem(int i) {
        if (isRecentSearchDisplayed()) {
            int offset = !SearchQuery.hints.isEmpty() ? 2 : 0;
            if (i <= offset || (i - 1) - offset >= this.recentSearchObjects.size()) {
                return null;
            }
            TLObject object = ((RecentSearchObject) this.recentSearchObjects.get((i - 1) - offset)).object;
            if (object instanceof User) {
                TLObject user = MessagesController.getInstance().getUser(Integer.valueOf(((User) object).id));
                if (user != null) {
                    return user;
                }
                return object;
            } else if (!(object instanceof TLRPC$Chat)) {
                return object;
            } else {
                TLObject chat = MessagesController.getInstance().getChat(Integer.valueOf(((TLRPC$Chat) object).id));
                if (chat != null) {
                    return chat;
                }
                return object;
            }
        } else if (this.searchResultHashtags.isEmpty()) {
            ArrayList<TLObject> globalSearch = this.searchAdapterHelper.getGlobalSearch();
            int localCount = this.searchResult.size();
            int globalCount = globalSearch.isEmpty() ? 0 : globalSearch.size() + 1;
            int messagesCount = this.searchResultMessages.isEmpty() ? 0 : this.searchResultMessages.size() + 1;
            if (i >= 0 && i < localCount) {
                return this.searchResult.get(i);
            }
            if (i > localCount && i < globalCount + localCount) {
                return globalSearch.get((i - localCount) - 1);
            }
            if (i <= globalCount + localCount || i >= (globalCount + localCount) + messagesCount) {
                return null;
            }
            return this.searchResultMessages.get(((i - localCount) - globalCount) - 1);
        } else if (i > 0) {
            return this.searchResultHashtags.get(i - 1);
        } else {
            return null;
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public boolean isEnabled(ViewHolder holder) {
        int type = holder.getItemViewType();
        if (type == 1 || type == 3) {
            return false;
        }
        return true;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = new ProfileSearchCell(this.mContext);
                break;
            case 1:
                view = new GraySectionCell(this.mContext);
                break;
            case 2:
                view = new DialogCell(this.mContext, false);
                break;
            case 3:
                view = new LoadingCell(this.mContext);
                break;
            case 4:
                view = new HashtagSearchCell(this.mContext);
                break;
            case 5:
                View horizontalListView = new RecyclerListView(this.mContext) {
                    public boolean onInterceptTouchEvent(MotionEvent e) {
                        if (!(getParent() == null || getParent().getParent() == null)) {
                            getParent().getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        return super.onInterceptTouchEvent(e);
                    }
                };
                horizontalListView.setTag(Integer.valueOf(9));
                horizontalListView.setItemAnimator(null);
                horizontalListView.setLayoutAnimation(null);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this.mContext) {
                    public boolean supportsPredictiveItemAnimations() {
                        return false;
                    }
                };
                layoutManager.setOrientation(0);
                horizontalListView.setLayoutManager(layoutManager);
                horizontalListView.setAdapter(new CategoryAdapterRecycler());
                horizontalListView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(View view, int position) {
                        if (DialogsSearchAdapter.this.delegate != null) {
                            DialogsSearchAdapter.this.delegate.didPressedOnSubDialog((long) ((Integer) view.getTag()).intValue());
                        }
                    }
                });
                horizontalListView.setOnItemLongClickListener(new OnItemLongClickListener() {
                    public boolean onItemClick(View view, int position) {
                        if (DialogsSearchAdapter.this.delegate != null) {
                            DialogsSearchAdapter.this.delegate.needRemoveHint(((Integer) view.getTag()).intValue());
                        }
                        return true;
                    }
                });
                view = horizontalListView;
                this.innerListView = horizontalListView;
                break;
        }
        if (viewType == 5) {
            view.setLayoutParams(new LayoutParams(-1, AndroidUtilities.dp(100.0f)));
        } else {
            view.setLayoutParams(new LayoutParams(-1, -2));
        }
        return new Holder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                Object username;
                TLObject tLObject;
                ProfileSearchCell cell = holder.itemView;
                TLObject user = null;
                TLObject chat = null;
                TLRPC$EncryptedChat encryptedChat = null;
                CharSequence username2 = null;
                CharSequence name = null;
                boolean isRecent = false;
                String un = null;
                TLObject obj = getItem(position);
                if (obj instanceof User) {
                    user = (User) obj;
                    un = user.username;
                } else if (obj instanceof TLRPC$Chat) {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(((TLRPC$Chat) obj).id));
                    if (chat == null) {
                        chat = (TLRPC$Chat) obj;
                    }
                    un = chat.username;
                } else if (obj instanceof TLRPC$EncryptedChat) {
                    encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(((TLRPC$EncryptedChat) obj).id));
                    user = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id));
                }
                boolean z;
                if (isRecentSearchDisplayed()) {
                    isRecent = true;
                    if (position != getItemCount() - 1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    cell.useSeparator = z;
                } else {
                    ArrayList<TLObject> globalSearch = this.searchAdapterHelper.getGlobalSearch();
                    int localCount = this.searchResult.size();
                    z = (position == getItemCount() + -1 || position == localCount - 1 || position == (localCount + (globalSearch.isEmpty() ? 0 : globalSearch.size() + 1)) - 1) ? false : true;
                    cell.useSeparator = z;
                    if (position < this.searchResult.size()) {
                        name = (CharSequence) this.searchResultNames.get(position);
                        if (!(name == null || user == null || user.username == null || user.username.length() <= 0 || !name.toString().startsWith("@" + user.username))) {
                            username2 = name;
                            name = null;
                        }
                    } else if (position > this.searchResult.size() && un != null) {
                        String foundUserName = this.searchAdapterHelper.getLastFoundUsername();
                        if (foundUserName.startsWith("@")) {
                            foundUserName = foundUserName.substring(1);
                        }
                        try {
                            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                            spannableStringBuilder.append("@");
                            spannableStringBuilder.append(un);
                            if (un.startsWith(foundUserName)) {
                                spannableStringBuilder.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), 0, Math.min(spannableStringBuilder.length(), foundUserName.length() + 1), 33);
                            }
                            username = spannableStringBuilder;
                        } catch (Exception e) {
                            username = un;
                            FileLog.e(e);
                        }
                    }
                }
                boolean savedMessages = false;
                if (user != null && user.id == this.selfUserId) {
                    name = LocaleController.getString("SavedMessages", R.string.SavedMessages);
                    username2 = null;
                    savedMessages = true;
                }
                if (!(chat == null || chat.participants_count == 0)) {
                    String membersString;
                    if (!ChatObject.isChannel((TLRPC$Chat) chat) || chat.megagroup) {
                        membersString = LocaleController.formatPluralString("Members", chat.participants_count);
                    } else {
                        membersString = LocaleController.formatPluralString("Subscribers", chat.participants_count);
                    }
                    if (username2 instanceof SpannableStringBuilder) {
                        ((SpannableStringBuilder) username2).append(", ").append(membersString);
                    } else if (TextUtils.isEmpty(username2)) {
                        username = membersString;
                    } else {
                        username2 = TextUtils.concat(new CharSequence[]{username2, ", ", membersString});
                    }
                }
                if (user != null) {
                    tLObject = user;
                } else {
                    tLObject = chat;
                }
                cell.setData(tLObject, encryptedChat, name, username2, isRecent, savedMessages);
                return;
            case 1:
                GraySectionCell cell2 = holder.itemView;
                if (isRecentSearchDisplayed()) {
                    if (position < (!SearchQuery.hints.isEmpty() ? 2 : 0)) {
                        cell2.setText(LocaleController.getString("ChatHints", R.string.ChatHints).toUpperCase());
                        return;
                    } else {
                        cell2.setText(LocaleController.getString("Recent", R.string.Recent).toUpperCase());
                        return;
                    }
                } else if (!this.searchResultHashtags.isEmpty()) {
                    cell2.setText(LocaleController.getString("Hashtags", R.string.Hashtags).toUpperCase());
                    return;
                } else if (this.searchAdapterHelper.getGlobalSearch().isEmpty() || position != this.searchResult.size()) {
                    cell2.setText(LocaleController.getString("SearchMessages", R.string.SearchMessages));
                    return;
                } else {
                    cell2.setText(LocaleController.getString("GlobalSearch", R.string.GlobalSearch));
                    return;
                }
            case 2:
                DialogCell cell3 = holder.itemView;
                cell3.useSeparator = position != getItemCount() + -1;
                MessageObject messageObject = (MessageObject) getItem(position);
                cell3.setDialog(messageObject.getDialogId(), messageObject, messageObject.messageOwner.date);
                return;
            case 4:
                HashtagSearchCell cell4 = holder.itemView;
                cell4.setText((CharSequence) this.searchResultHashtags.get(position - 1));
                cell4.setNeedDivider(position != this.searchResultHashtags.size());
                return;
            case 5:
                ((CategoryAdapterRecycler) ((RecyclerListView) holder.itemView).getAdapter()).setIndex(position / 2);
                return;
            default:
                return;
        }
    }

    public int getItemViewType(int i) {
        if (isRecentSearchDisplayed()) {
            int offset = !SearchQuery.hints.isEmpty() ? 2 : 0;
            if (i > offset) {
                return 0;
            }
            if (i == offset || i % 2 == 0) {
                return 1;
            }
            return 5;
        } else if (this.searchResultHashtags.isEmpty()) {
            ArrayList<TLObject> globalSearch = this.searchAdapterHelper.getGlobalSearch();
            int localCount = this.searchResult.size();
            int globalCount = globalSearch.isEmpty() ? 0 : globalSearch.size() + 1;
            int messagesCount = this.searchResultMessages.isEmpty() ? 0 : this.searchResultMessages.size() + 1;
            if ((i >= 0 && i < localCount) || (i > localCount && i < globalCount + localCount)) {
                return 0;
            }
            if (i > globalCount + localCount && i < (globalCount + localCount) + messagesCount) {
                return 2;
            }
            if (messagesCount == 0 || i != (globalCount + localCount) + messagesCount) {
                return 1;
            }
            return 3;
        } else if (i != 0) {
            return 4;
        } else {
            return 1;
        }
    }
}
