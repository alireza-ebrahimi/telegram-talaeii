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
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.SearchAdapterHelper.HashtagObject;
import org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.ProfileSearchCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import utils.p178a.C3791b;

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
    class C38811 implements SearchAdapterHelperDelegate {
        C38811() {
        }

        public void onDataSetChanged() {
            SearchAdapter.this.notifyDataSetChanged();
        }

        public void onSetHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
        }
    }

    public SearchAdapter(Context context, HashMap<Integer, User> hashMap, boolean z, boolean z2, boolean z3, boolean z4, int i) {
        this.mContext = context;
        this.ignoreUsers = hashMap;
        this.onlyMutual = z2;
        this.allowUsernameSearch = z;
        this.allowChats = z3;
        this.allowBots = z4;
        this.channelId = i;
        this.searchAdapterHelper = new SearchAdapterHelper();
        this.searchAdapterHelper.setDelegate(new C38811());
    }

    private void processSearch(final String str) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (SearchAdapter.this.allowUsernameSearch) {
                    SearchAdapter.this.searchAdapterHelper.queryServerSearch(str, true, SearchAdapter.this.allowChats, SearchAdapter.this.allowBots, true, SearchAdapter.this.channelId, false);
                }
                final ArrayList arrayList = new ArrayList();
                arrayList.addAll(ContactsController.getInstance().contacts);
                Utilities.searchQueue.postRunnable(new Runnable() {
                    public void run() {
                        String toLowerCase = str.trim().toLowerCase();
                        if (toLowerCase.length() == 0) {
                            SearchAdapter.this.updateSearchResults(new ArrayList(), new ArrayList());
                            return;
                        }
                        String translitString = LocaleController.getInstance().getTranslitString(toLowerCase);
                        String str = (toLowerCase.equals(translitString) || translitString.length() == 0) ? null : translitString;
                        String[] strArr = new String[((str != null ? 1 : 0) + 1)];
                        strArr[0] = toLowerCase;
                        if (str != null) {
                            strArr[1] = str;
                        }
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        for (int i = 0; i < arrayList.size(); i++) {
                            TLRPC$TL_contact tLRPC$TL_contact = (TLRPC$TL_contact) arrayList.get(i);
                            if (!C3791b.f(ApplicationLoader.applicationContext, (long) tLRPC$TL_contact.user_id)) {
                                User user = MessagesController.getInstance().getUser(Integer.valueOf(tLRPC$TL_contact.user_id));
                                if (user.id != UserConfig.getClientUserId() && (!SearchAdapter.this.onlyMutual || user.mutual_contact)) {
                                    String toLowerCase2 = ContactsController.formatName(user.first_name, user.last_name).toLowerCase();
                                    translitString = LocaleController.getInstance().getTranslitString(toLowerCase2);
                                    if (toLowerCase2.equals(translitString)) {
                                        translitString = null;
                                    }
                                    int length = strArr.length;
                                    Object obj = null;
                                    int i2 = 0;
                                    while (i2 < length) {
                                        String str2 = strArr[i2];
                                        if (toLowerCase2.startsWith(str2) || toLowerCase2.contains(" " + str2) || (r0 != null && (r0.startsWith(str2) || r0.contains(" " + str2)))) {
                                            obj = 1;
                                        } else if (user.username != null && user.username.startsWith(str2)) {
                                            obj = 2;
                                        }
                                        if (r2 != null) {
                                            if (r2 == 1) {
                                                arrayList2.add(AndroidUtilities.generateSearchName(user.first_name, user.last_name, str2));
                                            } else {
                                                arrayList2.add(AndroidUtilities.generateSearchName("@" + user.username, null, "@" + str2));
                                            }
                                            arrayList.add(user);
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                            }
                        }
                        SearchAdapter.this.updateSearchResults(arrayList, arrayList2);
                    }
                });
            }
        });
    }

    private void updateSearchResults(final ArrayList<User> arrayList, final ArrayList<CharSequence> arrayList2) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                SearchAdapter.this.searchResult = arrayList;
                SearchAdapter.this.searchResultNames = arrayList2;
                SearchAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public TLObject getItem(int i) {
        int size = this.searchResult.size();
        return (i < 0 || i >= size) ? (i <= size || i > this.searchAdapterHelper.getGlobalSearch().size() + size) ? null : (TLObject) this.searchAdapterHelper.getGlobalSearch().get((i - size) - 1) : (TLObject) this.searchResult.get(i);
    }

    public int getItemCount() {
        int size = this.searchResult.size();
        int size2 = this.searchAdapterHelper.getGlobalSearch().size();
        return size2 != 0 ? size + (size2 + 1) : size;
    }

    public int getItemViewType(int i) {
        return i == this.searchResult.size() ? 1 : 0;
    }

    public boolean isEnabled(ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() != this.searchResult.size();
    }

    public boolean isGlobalSearch(int i) {
        int size = this.searchResult.size();
        return (i < 0 || i >= size) && i > size && i <= size + this.searchAdapterHelper.getGlobalSearch().size();
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        boolean z = false;
        if (viewHolder.getItemViewType() == 0) {
            TLObject item = getItem(i);
            if (item != null) {
                CharSequence charSequence;
                int i2;
                Object obj;
                CharSequence charSequence2;
                if (item instanceof User) {
                    charSequence = ((User) item).username;
                    i2 = ((User) item).id;
                } else if (item instanceof Chat) {
                    obj = ((Chat) item).username;
                    i2 = ((Chat) item).id;
                } else {
                    charSequence = null;
                    i2 = 0;
                }
                if (i < this.searchResult.size()) {
                    CharSequence charSequence3 = (CharSequence) this.searchResultNames.get(i);
                    if (charSequence3 == null || charSequence == null || charSequence.length() <= 0 || !charSequence3.toString().startsWith("@" + charSequence)) {
                        charSequence2 = charSequence3;
                        charSequence = null;
                    } else {
                        charSequence2 = null;
                        charSequence = charSequence3;
                    }
                } else if (i <= this.searchResult.size() || charSequence == null) {
                    charSequence2 = null;
                    charSequence = null;
                } else {
                    String lastFoundUsername = this.searchAdapterHelper.getLastFoundUsername();
                    String substring = lastFoundUsername.startsWith("@") ? lastFoundUsername.substring(1) : lastFoundUsername;
                    try {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), 0, substring.length(), 33);
                        obj = spannableStringBuilder;
                        charSequence2 = null;
                    } catch (Throwable e) {
                        FileLog.e(e);
                        charSequence2 = null;
                    }
                }
                if (this.useUserCell) {
                    UserCell userCell = (UserCell) viewHolder.itemView;
                    userCell.setData(item, charSequence2, charSequence, 0);
                    if (this.checkedMap != null) {
                        userCell.setChecked(this.checkedMap.containsKey(Integer.valueOf(i2)), false);
                        return;
                    }
                    return;
                }
                ProfileSearchCell profileSearchCell = (ProfileSearchCell) viewHolder.itemView;
                profileSearchCell.setData(item, null, charSequence2, charSequence, false, false);
                if (!(i == getItemCount() - 1 || i == this.searchResult.size() - 1)) {
                    z = true;
                }
                profileSearchCell.useSeparator = z;
            }
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View profileSearchCell;
        switch (i) {
            case 0:
                if (!this.useUserCell) {
                    profileSearchCell = new ProfileSearchCell(this.mContext);
                    break;
                }
                profileSearchCell = new UserCell(this.mContext, 1, 1, false);
                if (this.checkedMap != null) {
                    ((UserCell) profileSearchCell).setChecked(false, false);
                    break;
                }
                break;
            default:
                profileSearchCell = new GraySectionCell(this.mContext);
                ((GraySectionCell) profileSearchCell).setText(LocaleController.getString("GlobalSearch", R.string.GlobalSearch));
                break;
        }
        return new Holder(profileSearchCell);
    }

    public void searchDialogs(final String str) {
        try {
            if (this.searchTimer != null) {
                this.searchTimer.cancel();
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        if (str == null) {
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
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                SearchAdapter.this.processSearch(str);
            }
        }, 200, 300);
    }

    public void setCheckedMap(HashMap<Integer, ?> hashMap) {
        this.checkedMap = hashMap;
    }

    public void setUseUserCell(boolean z) {
        this.useUserCell = z;
    }
}
