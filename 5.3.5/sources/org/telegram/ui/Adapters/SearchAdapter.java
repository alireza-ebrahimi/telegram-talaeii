package org.telegram.ui.Adapters;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.SearchAdapterHelper.HashtagObject;
import org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.ProfileSearchCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class SearchAdapter extends SelectionAdapter {
    private boolean allowBots;
    private boolean allowChats;
    private boolean allowUsernameSearch;
    private int channelId;
    private HashMap<Integer, ?> checkedMap;
    private HashMap<Integer, User> ignoreUsers;
    private Context mContext;
    private boolean onlyMutual;
    private SearchAdapterHelper searchAdapterHelper;
    private ArrayList<User> searchResult = new ArrayList();
    private ArrayList<CharSequence> searchResultNames = new ArrayList();
    private Timer searchTimer;
    private boolean useUserCell;

    /* renamed from: org.telegram.ui.Adapters.SearchAdapter$1 */
    class C20431 implements SearchAdapterHelperDelegate {
        C20431() {
        }

        public void onDataSetChanged() {
            SearchAdapter.this.notifyDataSetChanged();
        }

        public void onSetHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
        }
    }

    public SearchAdapter(Context context, HashMap<Integer, User> arg1, boolean usernameSearch, boolean mutual, boolean chats, boolean bots, int searchChannelId) {
        this.mContext = context;
        this.ignoreUsers = arg1;
        this.onlyMutual = mutual;
        this.allowUsernameSearch = usernameSearch;
        this.allowChats = chats;
        this.allowBots = bots;
        this.channelId = searchChannelId;
        this.searchAdapterHelper = new SearchAdapterHelper();
        this.searchAdapterHelper.setDelegate(new C20431());
    }

    public void setCheckedMap(HashMap<Integer, ?> map) {
        this.checkedMap = map;
    }

    public void setUseUserCell(boolean value) {
        this.useUserCell = value;
    }

    public void searchDialogs(final String query) {
        try {
            if (this.searchTimer != null) {
                this.searchTimer.cancel();
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (query == null) {
            this.searchResult.clear();
            this.searchResultNames.clear();
            if (this.allowUsernameSearch) {
                this.searchAdapterHelper.queryServerSearch(null, true, this.allowChats, this.allowBots, true, this.channelId, false);
            }
            notifyDataSetChanged();
            return;
        }
        this.searchTimer = new Timer();
        this.searchTimer.schedule(new TimerTask() {
            public void run() {
                try {
                    SearchAdapter.this.searchTimer.cancel();
                    SearchAdapter.this.searchTimer = null;
                } catch (Exception e) {
                    FileLog.e(e);
                }
                SearchAdapter.this.processSearch(query);
            }
        }, 200, 300);
    }

    private void processSearch(final String query) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (SearchAdapter.this.allowUsernameSearch) {
                    SearchAdapter.this.searchAdapterHelper.queryServerSearch(query, true, SearchAdapter.this.allowChats, SearchAdapter.this.allowBots, true, SearchAdapter.this.channelId, false);
                }
                final ArrayList<TLRPC$TL_contact> contactsCopy = new ArrayList();
                contactsCopy.addAll(ContactsController.getInstance().contacts);
                Utilities.searchQueue.postRunnable(new Runnable() {
                    public void run() {
                        String search1 = query.trim().toLowerCase();
                        if (search1.length() == 0) {
                            SearchAdapter.this.updateSearchResults(new ArrayList(), new ArrayList());
                            return;
                        }
                        String search2 = LocaleController.getInstance().getTranslitString(search1);
                        if (search1.equals(search2) || search2.length() == 0) {
                            search2 = null;
                        }
                        String[] search = new String[((search2 != null ? 1 : 0) + 1)];
                        search[0] = search1;
                        if (search2 != null) {
                            search[1] = search2;
                        }
                        ArrayList<User> resultArray = new ArrayList();
                        ArrayList<CharSequence> resultArrayNames = new ArrayList();
                        for (int a = 0; a < contactsCopy.size(); a++) {
                            User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) contactsCopy.get(a)).user_id));
                            if (user.id != UserConfig.getClientUserId() && (!SearchAdapter.this.onlyMutual || user.mutual_contact)) {
                                String name = ContactsController.formatName(user.first_name, user.last_name).toLowerCase();
                                String tName = LocaleController.getInstance().getTranslitString(name);
                                if (name.equals(tName)) {
                                    tName = null;
                                }
                                int found = 0;
                                int length = search.length;
                                int i = 0;
                                while (i < length) {
                                    String q = search[i];
                                    if (name.startsWith(q) || name.contains(" " + q) || (tName != null && (tName.startsWith(q) || tName.contains(" " + q)))) {
                                        found = 1;
                                    } else if (user.username != null && user.username.startsWith(q)) {
                                        found = 2;
                                    }
                                    if (found != 0) {
                                        if (found == 1) {
                                            resultArrayNames.add(AndroidUtilities.generateSearchName(user.first_name, user.last_name, q));
                                        } else {
                                            resultArrayNames.add(AndroidUtilities.generateSearchName("@" + user.username, null, "@" + q));
                                        }
                                        resultArray.add(user);
                                    } else {
                                        i++;
                                    }
                                }
                            }
                        }
                        SearchAdapter.this.updateSearchResults(resultArray, resultArrayNames);
                    }
                });
            }
        });
    }

    private void updateSearchResults(final ArrayList<User> users, final ArrayList<CharSequence> names) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                SearchAdapter.this.searchResult = users;
                SearchAdapter.this.searchResultNames = names;
                SearchAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public boolean isEnabled(ViewHolder holder) {
        return holder.getAdapterPosition() != this.searchResult.size();
    }

    public int getItemCount() {
        int count = this.searchResult.size();
        int globalCount = this.searchAdapterHelper.getGlobalSearch().size();
        if (globalCount != 0) {
            return count + (globalCount + 1);
        }
        return count;
    }

    public boolean isGlobalSearch(int i) {
        int localCount = this.searchResult.size();
        int globalCount = this.searchAdapterHelper.getGlobalSearch().size();
        if ((i < 0 || i >= localCount) && i > localCount && i <= globalCount + localCount) {
            return true;
        }
        return false;
    }

    public TLObject getItem(int i) {
        int localCount = this.searchResult.size();
        int globalCount = this.searchAdapterHelper.getGlobalSearch().size();
        if (i >= 0 && i < localCount) {
            return (TLObject) this.searchResult.get(i);
        }
        if (i <= localCount || i > globalCount + localCount) {
            return null;
        }
        return (TLObject) this.searchAdapterHelper.getGlobalSearch().get((i - localCount) - 1);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                if (!this.useUserCell) {
                    view = new ProfileSearchCell(this.mContext);
                    break;
                }
                view = new UserCell(this.mContext, 1, 1, false);
                if (this.checkedMap != null) {
                    ((UserCell) view).setChecked(false, false);
                    break;
                }
                break;
            default:
                view = new GraySectionCell(this.mContext);
                ((GraySectionCell) view).setText(LocaleController.getString("GlobalSearch", R.string.GlobalSearch));
                break;
        }
        return new Holder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Exception e;
        Object username;
        ProfileSearchCell profileSearchCell;
        boolean z;
        UserCell userCell;
        if (holder.getItemViewType() == 0) {
            TLObject object = getItem(position);
            if (object != null) {
                int id = 0;
                String un = null;
                if (object instanceof User) {
                    un = ((User) object).username;
                    id = ((User) object).id;
                } else if (object instanceof TLRPC$Chat) {
                    un = ((TLRPC$Chat) object).username;
                    id = ((TLRPC$Chat) object).id;
                }
                CharSequence username2 = null;
                CharSequence name = null;
                if (position < this.searchResult.size()) {
                    name = (CharSequence) this.searchResultNames.get(position);
                    if (name != null && un != null && un.length() > 0 && name.toString().startsWith("@" + un)) {
                        username2 = name;
                        name = null;
                    }
                } else if (position > this.searchResult.size() && un != null) {
                    String foundUserName = this.searchAdapterHelper.getLastFoundUsername();
                    if (foundUserName.startsWith("@")) {
                        foundUserName = foundUserName.substring(1);
                    }
                    try {
                        CharSequence username3 = new SpannableStringBuilder(un);
                        try {
                            ((SpannableStringBuilder) username3).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), 0, foundUserName.length(), 33);
                            username2 = username3;
                        } catch (Exception e2) {
                            e = e2;
                            username2 = username3;
                            username = un;
                            FileLog.e(e);
                            if (this.useUserCell) {
                                profileSearchCell = holder.itemView;
                                profileSearchCell.setData(object, null, name, username2, false, false);
                                if (position == getItemCount() + -1) {
                                }
                                profileSearchCell.useSeparator = z;
                            }
                            userCell = holder.itemView;
                            userCell.setData(object, name, username2, 0);
                            if (this.checkedMap == null) {
                                userCell.setChecked(this.checkedMap.containsKey(Integer.valueOf(id)), false);
                                return;
                            }
                            return;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        username = un;
                        FileLog.e(e);
                        if (this.useUserCell) {
                            userCell = holder.itemView;
                            userCell.setData(object, name, username2, 0);
                            if (this.checkedMap == null) {
                                userCell.setChecked(this.checkedMap.containsKey(Integer.valueOf(id)), false);
                                return;
                            }
                            return;
                        }
                        profileSearchCell = holder.itemView;
                        profileSearchCell.setData(object, null, name, username2, false, false);
                        if (position == getItemCount() + -1) {
                        }
                        profileSearchCell.useSeparator = z;
                    }
                }
                if (this.useUserCell) {
                    userCell = holder.itemView;
                    userCell.setData(object, name, username2, 0);
                    if (this.checkedMap == null) {
                        userCell.setChecked(this.checkedMap.containsKey(Integer.valueOf(id)), false);
                        return;
                    }
                    return;
                }
                profileSearchCell = holder.itemView;
                profileSearchCell.setData(object, null, name, username2, false, false);
                z = position == getItemCount() + -1 && position != this.searchResult.size() - 1;
                profileSearchCell.useSeparator = z;
            }
        }
    }

    public int getItemViewType(int i) {
        if (i == this.searchResult.size()) {
            return 1;
        }
        return 0;
    }
}
