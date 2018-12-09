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
import org.telegram.tgnet.TLRPC$TL_contacts_found;
import org.telegram.tgnet.TLRPC$TL_contacts_search;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsBanned;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsKicked;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsSearch;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC.User;

public class SearchAdapterHelper {
    private int channelLastReqId;
    private int channelLastReqId2;
    private int channelReqId = 0;
    private int channelReqId2 = 0;
    private SearchAdapterHelperDelegate delegate;
    private ArrayList<TLObject> globalSearch = new ArrayList();
    private HashMap<Integer, TLObject> globalSearchMap = new HashMap();
    private ArrayList<ChannelParticipant> groupSearch = new ArrayList();
    private ArrayList<ChannelParticipant> groupSearch2 = new ArrayList();
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
    class C38944 implements Runnable {

        /* renamed from: org.telegram.ui.Adapters.SearchAdapterHelper$4$1 */
        class C38921 implements Comparator<HashtagObject> {
            C38921() {
            }

            public int compare(HashtagObject hashtagObject, HashtagObject hashtagObject2) {
                return hashtagObject.date < hashtagObject2.date ? 1 : hashtagObject.date > hashtagObject2.date ? -1 : 0;
            }
        }

        C38944() {
        }

        public void run() {
            try {
                SQLiteCursor b = MessagesStorage.getInstance().getDatabase().b("SELECT id, date FROM hashtag_recent_v2 WHERE 1", new Object[0]);
                final Object arrayList = new ArrayList();
                final HashMap hashMap = new HashMap();
                while (b.a()) {
                    HashtagObject hashtagObject = new HashtagObject();
                    hashtagObject.hashtag = b.e(0);
                    hashtagObject.date = b.b(1);
                    arrayList.add(hashtagObject);
                    hashMap.put(hashtagObject.hashtag, hashtagObject);
                }
                b.b();
                Collections.sort(arrayList, new C38921());
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        SearchAdapterHelper.this.setHashtags(arrayList, hashMap);
                    }
                });
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    /* renamed from: org.telegram.ui.Adapters.SearchAdapterHelper$6 */
    class C38966 implements Runnable {
        C38966() {
        }

        public void run() {
            try {
                MessagesStorage.getInstance().getDatabase().a("DELETE FROM hashtag_recent_v2 WHERE 1").c().e();
            } catch (Throwable e) {
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

    private void putRecentHashtags(final ArrayList<HashtagObject> arrayList) {
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
            public void run() {
                int i = 100;
                try {
                    MessagesStorage.getInstance().getDatabase().d();
                    SQLitePreparedStatement a = MessagesStorage.getInstance().getDatabase().a("REPLACE INTO hashtag_recent_v2 VALUES(?, ?)");
                    int i2 = 0;
                    while (i2 < arrayList.size() && i2 != 100) {
                        HashtagObject hashtagObject = (HashtagObject) arrayList.get(i2);
                        a.d();
                        a.a(1, hashtagObject.hashtag);
                        a.a(2, hashtagObject.date);
                        a.b();
                        i2++;
                    }
                    a.e();
                    MessagesStorage.getInstance().getDatabase().e();
                    if (arrayList.size() >= 100) {
                        MessagesStorage.getInstance().getDatabase().d();
                        while (i < arrayList.size()) {
                            MessagesStorage.getInstance().getDatabase().a("DELETE FROM hashtag_recent_v2 WHERE id = '" + ((HashtagObject) arrayList.get(i)).hashtag + "'").c().e();
                            i++;
                        }
                        MessagesStorage.getInstance().getDatabase().e();
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        });
    }

    public void addHashtagsFromMessage(CharSequence charSequence) {
        if (charSequence != null) {
            Matcher matcher = Pattern.compile("(^|\\s)#[\\w@\\.]+").matcher(charSequence);
            int i = 0;
            while (matcher.find()) {
                i = matcher.start();
                int end = matcher.end();
                if (!(charSequence.charAt(i) == '@' || charSequence.charAt(i) == '#')) {
                    i++;
                }
                String charSequence2 = charSequence.subSequence(i, end).toString();
                if (this.hashtagsByText == null) {
                    this.hashtagsByText = new HashMap();
                    this.hashtags = new ArrayList();
                }
                HashtagObject hashtagObject = (HashtagObject) this.hashtagsByText.get(charSequence2);
                if (hashtagObject == null) {
                    hashtagObject = new HashtagObject();
                    hashtagObject.hashtag = charSequence2;
                    this.hashtagsByText.put(hashtagObject.hashtag, hashtagObject);
                } else {
                    this.hashtags.remove(hashtagObject);
                }
                hashtagObject.date = (int) (System.currentTimeMillis() / 1000);
                this.hashtags.add(0, hashtagObject);
                i = 1;
            }
            if (i != 0) {
                putRecentHashtags(this.hashtags);
            }
        }
    }

    public void clearRecentHashtags() {
        this.hashtags = new ArrayList();
        this.hashtagsByText = new HashMap();
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C38966());
    }

    public ArrayList<TLObject> getGlobalSearch() {
        return this.globalSearch;
    }

    public ArrayList<ChannelParticipant> getGroupSearch() {
        return this.groupSearch;
    }

    public ArrayList<ChannelParticipant> getGroupSearch2() {
        return this.groupSearch2;
    }

    public ArrayList<HashtagObject> getHashtags() {
        return this.hashtags;
    }

    public String getLastFoundChannel() {
        return this.lastFoundChannel;
    }

    public String getLastFoundChannel2() {
        return this.lastFoundChannel2;
    }

    public String getLastFoundUsername() {
        return this.lastFoundUsername;
    }

    public boolean loadRecentHashtags() {
        if (this.hashtagsLoadedFromDb) {
            return true;
        }
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C38944());
        return false;
    }

    public void mergeResults(ArrayList<TLObject> arrayList) {
        this.localSearchResults = arrayList;
        if (!this.globalSearch.isEmpty() && arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                TLObject tLObject = (TLObject) arrayList.get(i);
                if (tLObject instanceof User) {
                    User user = (User) this.globalSearchMap.get(Integer.valueOf(((User) tLObject).id));
                    if (user != null) {
                        this.globalSearch.remove(user);
                        this.globalSearchMap.remove(Integer.valueOf(user.id));
                    }
                } else if (tLObject instanceof Chat) {
                    Chat chat = (Chat) this.globalSearchMap.get(Integer.valueOf(-((Chat) tLObject).id));
                    if (chat != null) {
                        this.globalSearch.remove(chat);
                        this.globalSearchMap.remove(Integer.valueOf(-chat.id));
                    }
                }
            }
        }
    }

    public void queryServerSearch(final String str, boolean z, boolean z2, boolean z3, boolean z4, int i, boolean z5) {
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
        if (str == null) {
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
        if (str.length() <= 0 || i == 0) {
            this.groupSearch.clear();
            this.groupSearch2.clear();
            this.channelLastReqId = 0;
            this.delegate.onDataSetChanged();
        } else {
            TLObject tL_channels_getParticipants = new TL_channels_getParticipants();
            if (z5) {
                tL_channels_getParticipants.filter = new TL_channelParticipantsBanned();
            } else {
                tL_channels_getParticipants.filter = new TL_channelParticipantsSearch();
            }
            tL_channels_getParticipants.filter.f10136q = str;
            tL_channels_getParticipants.limit = 50;
            tL_channels_getParticipants.offset = 0;
            tL_channels_getParticipants.channel = MessagesController.getInputChannel(i);
            final int i2 = this.channelLastReqId + 1;
            this.channelLastReqId = i2;
            this.channelReqId = ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipants, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (i2 == SearchAdapterHelper.this.channelLastReqId && tLRPC$TL_error == null) {
                                TL_channels_channelParticipants tL_channels_channelParticipants = (TL_channels_channelParticipants) tLObject;
                                SearchAdapterHelper.this.lastFoundChannel = str.toLowerCase();
                                MessagesController.getInstance().putUsers(tL_channels_channelParticipants.users, false);
                                SearchAdapterHelper.this.groupSearch = tL_channels_channelParticipants.participants;
                                SearchAdapterHelper.this.delegate.onDataSetChanged();
                            }
                            SearchAdapterHelper.this.channelReqId = 0;
                        }
                    });
                }
            }, 2);
            if (z5) {
                tL_channels_getParticipants = new TL_channels_getParticipants();
                tL_channels_getParticipants.filter = new TL_channelParticipantsKicked();
                tL_channels_getParticipants.filter.f10136q = str;
                tL_channels_getParticipants.limit = 50;
                tL_channels_getParticipants.offset = 0;
                tL_channels_getParticipants.channel = MessagesController.getInputChannel(i);
                i2 = this.channelLastReqId2 + 1;
                this.channelLastReqId2 = i2;
                this.channelReqId2 = ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipants, new RequestDelegate() {
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (i2 == SearchAdapterHelper.this.channelLastReqId2 && tLRPC$TL_error == null) {
                                    TL_channels_channelParticipants tL_channels_channelParticipants = (TL_channels_channelParticipants) tLObject;
                                    SearchAdapterHelper.this.lastFoundChannel2 = str.toLowerCase();
                                    MessagesController.getInstance().putUsers(tL_channels_channelParticipants.users, false);
                                    SearchAdapterHelper.this.groupSearch2 = tL_channels_channelParticipants.participants;
                                    SearchAdapterHelper.this.delegate.onDataSetChanged();
                                }
                                SearchAdapterHelper.this.channelReqId2 = 0;
                            }
                        });
                    }
                }, 2);
            }
        }
        if (!z) {
            return;
        }
        if (str.length() >= 5) {
            TLObject tLRPC$TL_contacts_search = new TLRPC$TL_contacts_search();
            tLRPC$TL_contacts_search.f10159q = str;
            tLRPC$TL_contacts_search.limit = 50;
            final int i3 = this.lastReqId + 1;
            this.lastReqId = i3;
            final boolean z6 = z2;
            final boolean z7 = z3;
            final boolean z8 = z4;
            final String str2 = str;
            this.reqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_contacts_search, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (i3 == SearchAdapterHelper.this.lastReqId && tLRPC$TL_error == null) {
                                int i;
                                TLRPC$TL_contacts_found tLRPC$TL_contacts_found = (TLRPC$TL_contacts_found) tLObject;
                                SearchAdapterHelper.this.globalSearch.clear();
                                SearchAdapterHelper.this.globalSearchMap.clear();
                                MessagesController.getInstance().putChats(tLRPC$TL_contacts_found.chats, false);
                                MessagesController.getInstance().putUsers(tLRPC$TL_contacts_found.users, false);
                                MessagesStorage.getInstance().putUsersAndChats(tLRPC$TL_contacts_found.users, tLRPC$TL_contacts_found.chats, true, true);
                                if (z6) {
                                    for (i = 0; i < tLRPC$TL_contacts_found.chats.size(); i++) {
                                        Chat chat = (Chat) tLRPC$TL_contacts_found.chats.get(i);
                                        SearchAdapterHelper.this.globalSearch.add(chat);
                                        SearchAdapterHelper.this.globalSearchMap.put(Integer.valueOf(-chat.id), chat);
                                    }
                                }
                                for (i = 0; i < tLRPC$TL_contacts_found.users.size(); i++) {
                                    User user = (User) tLRPC$TL_contacts_found.users.get(i);
                                    if ((z7 || !user.bot) && (z8 || !user.self)) {
                                        SearchAdapterHelper.this.globalSearch.add(user);
                                        SearchAdapterHelper.this.globalSearchMap.put(Integer.valueOf(user.id), user);
                                    }
                                }
                                SearchAdapterHelper.this.lastFoundUsername = str2.toLowerCase();
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

    public void setDelegate(SearchAdapterHelperDelegate searchAdapterHelperDelegate) {
        this.delegate = searchAdapterHelperDelegate;
    }

    public void setHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
        this.hashtags = arrayList;
        this.hashtagsByText = hashMap;
        this.hashtagsLoadedFromDb = true;
        this.delegate.onSetHashtags(arrayList, hashMap);
    }

    public void unloadRecentHashtags() {
        this.hashtagsLoadedFromDb = false;
    }
}
