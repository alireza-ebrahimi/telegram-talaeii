package org.telegram.ui.Adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$ChannelParticipant;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_channelParticipantsBanned;
import org.telegram.tgnet.TLRPC$TL_channelParticipantsKicked;
import org.telegram.tgnet.TLRPC$TL_channelParticipantsSearch;
import org.telegram.tgnet.TLRPC$TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC$TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC$TL_contacts_found;
import org.telegram.tgnet.TLRPC$TL_contacts_search;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.User;

public class SearchAdapterHelper {
    private int channelLastReqId;
    private int channelLastReqId2;
    private int channelReqId = 0;
    private int channelReqId2 = 0;
    private SearchAdapterHelperDelegate delegate;
    private ArrayList<TLObject> globalSearch = new ArrayList();
    private HashMap<Integer, TLObject> globalSearchMap = new HashMap();
    private ArrayList<TLRPC$ChannelParticipant> groupSearch = new ArrayList();
    private ArrayList<TLRPC$ChannelParticipant> groupSearch2 = new ArrayList();
    private ArrayList<HashtagObject> hashtags;
    private HashMap<String, HashtagObject> hashtagsByText;
    private boolean hashtagsLoadedFromDb = false;
    private String lastFoundChannel;
    private String lastFoundChannel2;
    private String lastFoundUsername = null;
    private int lastReqId;
    private ArrayList<TLObject> localSearchResults;
    private int reqId = 0;

    public interface SearchAdapterHelperDelegate {
        void onDataSetChanged();

        void onSetHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap);
    }

    /* renamed from: org.telegram.ui.Adapters.SearchAdapterHelper$4 */
    class C20564 implements Runnable {

        /* renamed from: org.telegram.ui.Adapters.SearchAdapterHelper$4$1 */
        class C20541 implements Comparator<HashtagObject> {
            C20541() {
            }

            public int compare(HashtagObject lhs, HashtagObject rhs) {
                if (lhs.date < rhs.date) {
                    return 1;
                }
                if (lhs.date > rhs.date) {
                    return -1;
                }
                return 0;
            }
        }

        C20564() {
        }

        public void run() {
            try {
                SQLiteCursor cursor = MessagesStorage.getInstance().getDatabase().queryFinalized("SELECT id, date FROM hashtag_recent_v2 WHERE 1", new Object[0]);
                final ArrayList<HashtagObject> arrayList = new ArrayList();
                final HashMap<String, HashtagObject> hashMap = new HashMap();
                while (cursor.next()) {
                    HashtagObject hashtagObject = new HashtagObject();
                    hashtagObject.hashtag = cursor.stringValue(0);
                    hashtagObject.date = cursor.intValue(1);
                    arrayList.add(hashtagObject);
                    hashMap.put(hashtagObject.hashtag, hashtagObject);
                }
                cursor.dispose();
                Collections.sort(arrayList, new C20541());
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        SearchAdapterHelper.this.setHashtags(arrayList, hashMap);
                    }
                });
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    /* renamed from: org.telegram.ui.Adapters.SearchAdapterHelper$6 */
    class C20586 implements Runnable {
        C20586() {
        }

        public void run() {
            try {
                MessagesStorage.getInstance().getDatabase().executeFast("DELETE FROM hashtag_recent_v2 WHERE 1").stepThis().dispose();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    protected static final class DialogSearchResult {
        public int date;
        public CharSequence name;
        public TLObject object;

        protected DialogSearchResult() {
        }
    }

    public static class HashtagObject {
        int date;
        String hashtag;
    }

    public void queryServerSearch(final String query, boolean allowUsername, boolean allowChats, boolean allowBots, boolean allowSelf, int channelId, boolean kicked) {
        if (this.reqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.reqId, true);
            this.reqId = 0;
        }
        if (this.channelReqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.channelReqId, true);
            this.channelReqId = 0;
        }
        if (this.channelReqId2 != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.channelReqId2, true);
            this.channelReqId2 = 0;
        }
        if (query == null) {
            this.groupSearch.clear();
            this.groupSearch2.clear();
            this.globalSearch.clear();
            this.globalSearchMap.clear();
            this.lastReqId = 0;
            this.channelLastReqId = 0;
            this.channelLastReqId2 = 0;
            this.delegate.onDataSetChanged();
            return;
        }
        TLRPC$TL_channels_getParticipants req;
        if (query.length() <= 0 || channelId == 0) {
            this.groupSearch.clear();
            this.groupSearch2.clear();
            this.channelLastReqId = 0;
            this.delegate.onDataSetChanged();
        } else {
            req = new TLRPC$TL_channels_getParticipants();
            if (kicked) {
                req.filter = new TLRPC$TL_channelParticipantsBanned();
            } else {
                req.filter = new TLRPC$TL_channelParticipantsSearch();
            }
            req.filter.f69q = query;
            req.limit = 50;
            req.offset = 0;
            req.channel = MessagesController.getInputChannel(channelId);
            final int currentReqId = this.channelLastReqId + 1;
            this.channelLastReqId = currentReqId;
            this.channelReqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (currentReqId == SearchAdapterHelper.this.channelLastReqId && error == null) {
                                TLRPC$TL_channels_channelParticipants res = response;
                                SearchAdapterHelper.this.lastFoundChannel = query.toLowerCase();
                                MessagesController.getInstance().putUsers(res.users, false);
                                SearchAdapterHelper.this.groupSearch = res.participants;
                                SearchAdapterHelper.this.delegate.onDataSetChanged();
                            }
                            SearchAdapterHelper.this.channelReqId = 0;
                        }
                    });
                }
            }, 2);
            if (kicked) {
                req = new TLRPC$TL_channels_getParticipants();
                req.filter = new TLRPC$TL_channelParticipantsKicked();
                req.filter.f69q = query;
                req.limit = 50;
                req.offset = 0;
                req.channel = MessagesController.getInputChannel(channelId);
                final int currentReqId2 = this.channelLastReqId2 + 1;
                this.channelLastReqId2 = currentReqId2;
                this.channelReqId2 = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                    public void run(final TLObject response, final TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (currentReqId2 == SearchAdapterHelper.this.channelLastReqId2 && error == null) {
                                    TLRPC$TL_channels_channelParticipants res = response;
                                    SearchAdapterHelper.this.lastFoundChannel2 = query.toLowerCase();
                                    MessagesController.getInstance().putUsers(res.users, false);
                                    SearchAdapterHelper.this.groupSearch2 = res.participants;
                                    SearchAdapterHelper.this.delegate.onDataSetChanged();
                                }
                                SearchAdapterHelper.this.channelReqId2 = 0;
                            }
                        });
                    }
                }, 2);
            }
        }
        if (!allowUsername) {
            return;
        }
        if (query.length() >= 5) {
            req = new TLRPC$TL_contacts_search();
            req.f80q = query;
            req.limit = 50;
            currentReqId = this.lastReqId + 1;
            this.lastReqId = currentReqId;
            final boolean z = allowChats;
            final boolean z2 = allowBots;
            final boolean z3 = allowSelf;
            final String str = query;
            this.reqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (currentReqId == SearchAdapterHelper.this.lastReqId && error == null) {
                                int a;
                                TLRPC$TL_contacts_found res = response;
                                SearchAdapterHelper.this.globalSearch.clear();
                                SearchAdapterHelper.this.globalSearchMap.clear();
                                MessagesController.getInstance().putChats(res.chats, false);
                                MessagesController.getInstance().putUsers(res.users, false);
                                MessagesStorage.getInstance().putUsersAndChats(res.users, res.chats, true, true);
                                if (z) {
                                    for (a = 0; a < res.chats.size(); a++) {
                                        TLRPC$Chat chat = (TLRPC$Chat) res.chats.get(a);
                                        SearchAdapterHelper.this.globalSearch.add(chat);
                                        SearchAdapterHelper.this.globalSearchMap.put(Integer.valueOf(-chat.id), chat);
                                    }
                                }
                                for (a = 0; a < res.users.size(); a++) {
                                    User user = (User) res.users.get(a);
                                    if ((z2 || !user.bot) && (z3 || !user.self)) {
                                        SearchAdapterHelper.this.globalSearch.add(user);
                                        SearchAdapterHelper.this.globalSearchMap.put(Integer.valueOf(user.id), user);
                                    }
                                }
                                SearchAdapterHelper.this.lastFoundUsername = str.toLowerCase();
                                if (SearchAdapterHelper.this.localSearchResults != null) {
                                    SearchAdapterHelper.this.mergeResults(SearchAdapterHelper.this.localSearchResults);
                                }
                                SearchAdapterHelper.this.delegate.onDataSetChanged();
                            }
                            SearchAdapterHelper.this.reqId = 0;
                        }
                    });
                }
            }, 2);
            return;
        }
        this.globalSearch.clear();
        this.globalSearchMap.clear();
        this.lastReqId = 0;
        this.delegate.onDataSetChanged();
    }

    public void unloadRecentHashtags() {
        this.hashtagsLoadedFromDb = false;
    }

    public boolean loadRecentHashtags() {
        if (this.hashtagsLoadedFromDb) {
            return true;
        }
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C20564());
        return false;
    }

    public void mergeResults(ArrayList<TLObject> localResults) {
        this.localSearchResults = localResults;
        if (!this.globalSearch.isEmpty() && localResults != null) {
            int count = localResults.size();
            for (int a = 0; a < count; a++) {
                TLObject obj = (TLObject) localResults.get(a);
                if (obj instanceof User) {
                    User u = (User) this.globalSearchMap.get(Integer.valueOf(((User) obj).id));
                    if (u != null) {
                        this.globalSearch.remove(u);
                        this.globalSearchMap.remove(Integer.valueOf(u.id));
                    }
                } else if (obj instanceof TLRPC$Chat) {
                    TLRPC$Chat c = (TLRPC$Chat) this.globalSearchMap.get(Integer.valueOf(-((TLRPC$Chat) obj).id));
                    if (c != null) {
                        this.globalSearch.remove(c);
                        this.globalSearchMap.remove(Integer.valueOf(-c.id));
                    }
                }
            }
        }
    }

    public void setDelegate(SearchAdapterHelperDelegate searchAdapterHelperDelegate) {
        this.delegate = searchAdapterHelperDelegate;
    }

    public void addHashtagsFromMessage(CharSequence message) {
        if (message != null) {
            boolean changed = false;
            Matcher matcher = Pattern.compile("(^|\\s)#[\\w@\\.]+").matcher(message);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                if (!(message.charAt(start) == '@' || message.charAt(start) == '#')) {
                    start++;
                }
                String hashtag = message.subSequence(start, end).toString();
                if (this.hashtagsByText == null) {
                    this.hashtagsByText = new HashMap();
                    this.hashtags = new ArrayList();
                }
                HashtagObject hashtagObject = (HashtagObject) this.hashtagsByText.get(hashtag);
                if (hashtagObject == null) {
                    hashtagObject = new HashtagObject();
                    hashtagObject.hashtag = hashtag;
                    this.hashtagsByText.put(hashtagObject.hashtag, hashtagObject);
                } else {
                    this.hashtags.remove(hashtagObject);
                }
                hashtagObject.date = (int) (System.currentTimeMillis() / 1000);
                this.hashtags.add(0, hashtagObject);
                changed = true;
            }
            if (changed) {
                putRecentHashtags(this.hashtags);
            }
        }
    }

    private void putRecentHashtags(final ArrayList<HashtagObject> arrayList) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                try {
                    MessagesStorage.getInstance().getDatabase().beginTransaction();
                    SQLitePreparedStatement state = MessagesStorage.getInstance().getDatabase().executeFast("REPLACE INTO hashtag_recent_v2 VALUES(?, ?)");
                    int a = 0;
                    while (a < arrayList.size() && a != 100) {
                        HashtagObject hashtagObject = (HashtagObject) arrayList.get(a);
                        state.requery();
                        state.bindString(1, hashtagObject.hashtag);
                        state.bindInteger(2, hashtagObject.date);
                        state.step();
                        a++;
                    }
                    state.dispose();
                    MessagesStorage.getInstance().getDatabase().commitTransaction();
                    if (arrayList.size() >= 100) {
                        MessagesStorage.getInstance().getDatabase().beginTransaction();
                        for (a = 100; a < arrayList.size(); a++) {
                            MessagesStorage.getInstance().getDatabase().executeFast("DELETE FROM hashtag_recent_v2 WHERE id = '" + ((HashtagObject) arrayList.get(a)).hashtag + "'").stepThis().dispose();
                        }
                        MessagesStorage.getInstance().getDatabase().commitTransaction();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public ArrayList<TLObject> getGlobalSearch() {
        return this.globalSearch;
    }

    public ArrayList<TLRPC$ChannelParticipant> getGroupSearch() {
        return this.groupSearch;
    }

    public ArrayList<TLRPC$ChannelParticipant> getGroupSearch2() {
        return this.groupSearch2;
    }

    public ArrayList<HashtagObject> getHashtags() {
        return this.hashtags;
    }

    public String getLastFoundUsername() {
        return this.lastFoundUsername;
    }

    public String getLastFoundChannel() {
        return this.lastFoundChannel;
    }

    public String getLastFoundChannel2() {
        return this.lastFoundChannel2;
    }

    public void clearRecentHashtags() {
        this.hashtags = new ArrayList();
        this.hashtagsByText = new HashMap();
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C20586());
    }

    public void setHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
        this.hashtags = arrayList;
        this.hashtagsByText = hashMap;
        this.hashtagsLoadedFromDb = true;
        this.delegate.onSetHashtags(arrayList, hashMap);
    }
}
