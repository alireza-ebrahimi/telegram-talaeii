package org.telegram.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$TL_chatParticipant;
import org.telegram.tgnet.TLRPC$TL_chatParticipantAdmin;
import org.telegram.tgnet.TLRPC$TL_chatParticipantCreator;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class SetAdminsActivity extends BaseFragment implements NotificationCenterDelegate {
    private int allAdminsInfoRow;
    private int allAdminsRow;
    private TLRPC$Chat chat;
    private int chat_id;
    private EmptyTextProgressView emptyView;
    private TLRPC$ChatFull info;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private ArrayList<TLRPC$ChatParticipant> participants = new ArrayList();
    private int rowCount;
    private SearchAdapter searchAdapter;
    private ActionBarMenuItem searchItem;
    private boolean searchWas;
    private boolean searching;
    private int usersEndRow;
    private int usersStartRow;

    /* renamed from: org.telegram.ui.SetAdminsActivity$1 */
    class C33391 extends ActionBarMenuOnItemClick {
        C33391() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                SetAdminsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.SetAdminsActivity$2 */
    class C33402 extends ActionBarMenuItemSearchListener {
        C33402() {
        }

        public void onSearchExpand() {
            SetAdminsActivity.this.searching = true;
            SetAdminsActivity.this.listView.setEmptyView(SetAdminsActivity.this.emptyView);
        }

        public void onSearchCollapse() {
            SetAdminsActivity.this.searching = false;
            SetAdminsActivity.this.searchWas = false;
            if (SetAdminsActivity.this.listView != null) {
                SetAdminsActivity.this.listView.setEmptyView(null);
                SetAdminsActivity.this.emptyView.setVisibility(8);
                if (SetAdminsActivity.this.listView.getAdapter() != SetAdminsActivity.this.listAdapter) {
                    SetAdminsActivity.this.listView.setAdapter(SetAdminsActivity.this.listAdapter);
                }
            }
            if (SetAdminsActivity.this.searchAdapter != null) {
                SetAdminsActivity.this.searchAdapter.search(null);
            }
        }

        public void onTextChanged(EditText editText) {
            String text = editText.getText().toString();
            if (text.length() != 0) {
                SetAdminsActivity.this.searchWas = true;
                if (!(SetAdminsActivity.this.searchAdapter == null || SetAdminsActivity.this.listView.getAdapter() == SetAdminsActivity.this.searchAdapter)) {
                    SetAdminsActivity.this.listView.setAdapter(SetAdminsActivity.this.searchAdapter);
                    SetAdminsActivity.this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                }
                if (!(SetAdminsActivity.this.emptyView == null || SetAdminsActivity.this.listView.getEmptyView() == SetAdminsActivity.this.emptyView)) {
                    SetAdminsActivity.this.emptyView.showTextView();
                    SetAdminsActivity.this.listView.setEmptyView(SetAdminsActivity.this.emptyView);
                }
            }
            if (SetAdminsActivity.this.searchAdapter != null) {
                SetAdminsActivity.this.searchAdapter.search(text);
            }
        }
    }

    /* renamed from: org.telegram.ui.SetAdminsActivity$3 */
    class C33413 implements OnItemClickListener {
        C33413() {
        }

        public void onItemClick(View view, int position) {
            boolean z = true;
            boolean z2;
            if (SetAdminsActivity.this.listView.getAdapter() == SetAdminsActivity.this.searchAdapter || (position >= SetAdminsActivity.this.usersStartRow && position < SetAdminsActivity.this.usersEndRow)) {
                TLRPC$ChatParticipant participant;
                UserCell userCell = (UserCell) view;
                SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
                int index = -1;
                if (SetAdminsActivity.this.listView.getAdapter() == SetAdminsActivity.this.searchAdapter) {
                    participant = SetAdminsActivity.this.searchAdapter.getItem(position);
                    for (int a = 0; a < SetAdminsActivity.this.participants.size(); a++) {
                        if (((TLRPC$ChatParticipant) SetAdminsActivity.this.participants.get(a)).user_id == participant.user_id) {
                            index = a;
                            break;
                        }
                    }
                } else {
                    index = position - SetAdminsActivity.this.usersStartRow;
                    participant = (TLRPC$ChatParticipant) SetAdminsActivity.this.participants.get(index);
                }
                if (index != -1 && !(participant instanceof TLRPC$TL_chatParticipantCreator)) {
                    TLRPC$ChatParticipant newParticipant;
                    if (participant instanceof TLRPC$TL_chatParticipant) {
                        newParticipant = new TLRPC$TL_chatParticipantAdmin();
                        newParticipant.user_id = participant.user_id;
                        newParticipant.date = participant.date;
                        newParticipant.inviter_id = participant.inviter_id;
                    } else {
                        newParticipant = new TLRPC$TL_chatParticipant();
                        newParticipant.user_id = participant.user_id;
                        newParticipant.date = participant.date;
                        newParticipant.inviter_id = participant.inviter_id;
                    }
                    SetAdminsActivity.this.participants.set(index, newParticipant);
                    index = SetAdminsActivity.this.info.participants.participants.indexOf(participant);
                    if (index != -1) {
                        SetAdminsActivity.this.info.participants.participants.set(index, newParticipant);
                    }
                    if (SetAdminsActivity.this.listView.getAdapter() == SetAdminsActivity.this.searchAdapter) {
                        SetAdminsActivity.this.searchAdapter.searchResult.set(position, newParticipant);
                    }
                    participant = newParticipant;
                    z2 = ((participant instanceof TLRPC$TL_chatParticipant) && (SetAdminsActivity.this.chat == null || SetAdminsActivity.this.chat.admins_enabled)) ? false : true;
                    userCell.setChecked(z2, true);
                    if (SetAdminsActivity.this.chat != null && SetAdminsActivity.this.chat.admins_enabled) {
                        MessagesController instance = MessagesController.getInstance();
                        int access$1000 = SetAdminsActivity.this.chat_id;
                        int i = participant.user_id;
                        if (participant instanceof TLRPC$TL_chatParticipant) {
                            z = false;
                        }
                        instance.toggleUserAdmin(access$1000, i, z);
                    }
                }
            } else if (position == SetAdminsActivity.this.allAdminsRow) {
                SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
                if (SetAdminsActivity.this.chat != null) {
                    TLRPC$Chat access$900 = SetAdminsActivity.this.chat;
                    if (SetAdminsActivity.this.chat.admins_enabled) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    access$900.admins_enabled = z2;
                    TextCheckCell textCheckCell = (TextCheckCell) view;
                    if (SetAdminsActivity.this.chat.admins_enabled) {
                        z = false;
                    }
                    textCheckCell.setChecked(z);
                    MessagesController.getInstance().toggleAdminMode(SetAdminsActivity.this.chat_id, SetAdminsActivity.this.chat.admins_enabled);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.SetAdminsActivity$4 */
    class C33424 implements Comparator<TLRPC$ChatParticipant> {
        C33424() {
        }

        public int compare(TLRPC$ChatParticipant lhs, TLRPC$ChatParticipant rhs) {
            int type1 = SetAdminsActivity.this.getChatAdminParticipantType(lhs);
            int type2 = SetAdminsActivity.this.getChatAdminParticipantType(rhs);
            if (type1 > type2) {
                return 1;
            }
            if (type1 < type2) {
                return -1;
            }
            if (type1 == type2) {
                User user1 = MessagesController.getInstance().getUser(Integer.valueOf(rhs.user_id));
                User user2 = MessagesController.getInstance().getUser(Integer.valueOf(lhs.user_id));
                int status1 = 0;
                int status2 = 0;
                if (!(user1 == null || user1.status == null)) {
                    status1 = user1.status.expires;
                }
                if (!(user2 == null || user2.status == null)) {
                    status2 = user2.status.expires;
                }
                if (status1 <= 0 || status2 <= 0) {
                    if (status1 >= 0 || status2 >= 0) {
                        if ((status1 < 0 && status2 > 0) || (status1 == 0 && status2 != 0)) {
                            return -1;
                        }
                        if (status2 < 0 && status1 > 0) {
                            return 1;
                        }
                        if (status2 == 0 && status1 != 0) {
                            return 1;
                        }
                    } else if (status1 > status2) {
                        return 1;
                    } else {
                        if (status1 < status2) {
                            return -1;
                        }
                        return 0;
                    }
                } else if (status1 > status2) {
                    return 1;
                } else {
                    if (status1 < status2) {
                        return -1;
                    }
                    return 0;
                }
            }
            return 0;
        }
    }

    /* renamed from: org.telegram.ui.SetAdminsActivity$5 */
    class C33435 implements ThemeDescriptionDelegate {
        C33435() {
        }

        public void didSetColor(int color) {
            int count = SetAdminsActivity.this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = SetAdminsActivity.this.listView.getChildAt(a);
                if (child instanceof UserCell) {
                    ((UserCell) child).update(0);
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public boolean isEnabled(ViewHolder holder) {
            int position = holder.getAdapterPosition();
            if (position == SetAdminsActivity.this.allAdminsRow) {
                return true;
            }
            if (position < SetAdminsActivity.this.usersStartRow || position >= SetAdminsActivity.this.usersEndRow || (((TLRPC$ChatParticipant) SetAdminsActivity.this.participants.get(position - SetAdminsActivity.this.usersStartRow)) instanceof TLRPC$TL_chatParticipantCreator)) {
                return false;
            }
            return true;
        }

        public int getItemCount() {
            return SetAdminsActivity.this.rowCount;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new TextCheckCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new TextInfoPrivacyCell(this.mContext);
                    break;
                default:
                    view = new UserCell(this.mContext, 1, 2, false);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            boolean z = true;
            boolean z2 = false;
            switch (holder.getItemViewType()) {
                case 0:
                    TextCheckCell checkCell = holder.itemView;
                    SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
                    String string = LocaleController.getString("SetAdminsAll", R.string.SetAdminsAll);
                    if (SetAdminsActivity.this.chat == null || SetAdminsActivity.this.chat.admins_enabled) {
                        z = false;
                    }
                    checkCell.setTextAndCheck(string, z, false);
                    return;
                case 1:
                    TextInfoPrivacyCell privacyCell = holder.itemView;
                    if (position == SetAdminsActivity.this.allAdminsInfoRow) {
                        if (SetAdminsActivity.this.chat.admins_enabled) {
                            privacyCell.setText(LocaleController.getString("SetAdminsNotAllInfo", R.string.SetAdminsNotAllInfo));
                        } else {
                            privacyCell.setText(LocaleController.getString("SetAdminsAllInfo", R.string.SetAdminsAllInfo));
                        }
                        if (SetAdminsActivity.this.usersStartRow != -1) {
                            privacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                            return;
                        } else {
                            privacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                            return;
                        }
                    } else if (position == SetAdminsActivity.this.usersEndRow) {
                        privacyCell.setText("");
                        privacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    boolean z3;
                    UserCell userCell = holder.itemView;
                    TLRPC$ChatParticipant part = (TLRPC$ChatParticipant) SetAdminsActivity.this.participants.get(position - SetAdminsActivity.this.usersStartRow);
                    userCell.setData(MessagesController.getInstance().getUser(Integer.valueOf(part.user_id)), null, null, 0);
                    SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
                    if ((part instanceof TLRPC$TL_chatParticipant) && (SetAdminsActivity.this.chat == null || SetAdminsActivity.this.chat.admins_enabled)) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    userCell.setChecked(z3, false);
                    if (SetAdminsActivity.this.chat == null || !SetAdminsActivity.this.chat.admins_enabled || part.user_id == UserConfig.getClientUserId()) {
                        z2 = true;
                    }
                    userCell.setCheckDisabled(z2);
                    return;
                default:
                    return;
            }
        }

        public int getItemViewType(int i) {
            if (i == SetAdminsActivity.this.allAdminsRow) {
                return 0;
            }
            if (i == SetAdminsActivity.this.allAdminsInfoRow || i == SetAdminsActivity.this.usersEndRow) {
                return 1;
            }
            return 2;
        }
    }

    public class SearchAdapter extends SelectionAdapter {
        private Context mContext;
        private ArrayList<TLRPC$ChatParticipant> searchResult = new ArrayList();
        private ArrayList<CharSequence> searchResultNames = new ArrayList();
        private Timer searchTimer;

        public SearchAdapter(Context context) {
            this.mContext = context;
        }

        public void search(final String query) {
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
                    final ArrayList<TLRPC$ChatParticipant> contactsCopy = new ArrayList();
                    contactsCopy.addAll(SetAdminsActivity.this.participants);
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
                            ArrayList<TLRPC$ChatParticipant> resultArray = new ArrayList();
                            ArrayList<CharSequence> resultArrayNames = new ArrayList();
                            for (int a = 0; a < contactsCopy.size(); a++) {
                                TLRPC$ChatParticipant participant = (TLRPC$ChatParticipant) contactsCopy.get(a);
                                User user = MessagesController.getInstance().getUser(Integer.valueOf(participant.user_id));
                                if (user.id != UserConfig.getClientUserId()) {
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
                                            resultArray.add(participant);
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

        private void updateSearchResults(final ArrayList<TLRPC$ChatParticipant> users, final ArrayList<CharSequence> names) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    SearchAdapter.this.searchResult = users;
                    SearchAdapter.this.searchResultNames = names;
                    SearchAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public boolean isEnabled(ViewHolder holder) {
            return true;
        }

        public int getItemCount() {
            return this.searchResult.size();
        }

        public TLRPC$ChatParticipant getItem(int i) {
            return (TLRPC$ChatParticipant) this.searchResult.get(i);
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new UserCell(this.mContext, 1, 2, false));
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            boolean z;
            boolean z2 = false;
            TLRPC$ChatParticipant participant = getItem(position);
            User user = MessagesController.getInstance().getUser(Integer.valueOf(participant.user_id));
            String un = user.username;
            CharSequence username = null;
            CharSequence name = null;
            if (position < this.searchResult.size()) {
                name = (CharSequence) this.searchResultNames.get(position);
                if (name != null && un != null && un.length() > 0 && name.toString().startsWith("@" + un)) {
                    username = name;
                    name = null;
                }
            }
            UserCell userCell = holder.itemView;
            userCell.setData(user, name, username, 0);
            SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
            if ((participant instanceof TLRPC$TL_chatParticipant) && (SetAdminsActivity.this.chat == null || SetAdminsActivity.this.chat.admins_enabled)) {
                z = false;
            } else {
                z = true;
            }
            userCell.setChecked(z, false);
            if (SetAdminsActivity.this.chat == null || !SetAdminsActivity.this.chat.admins_enabled || participant.user_id == UserConfig.getClientUserId()) {
                z2 = true;
            }
            userCell.setCheckDisabled(z2);
        }

        public int getItemViewType(int i) {
            return 0;
        }
    }

    public SetAdminsActivity(Bundle args) {
        super(args);
        this.chat_id = args.getInt("chat_id");
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
    }

    public View createView(Context context) {
        this.searching = false;
        this.searchWas = false;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("SetAdminsTitle", R.string.SetAdminsTitle));
        this.actionBar.setActionBarMenuOnItemClick(new C33391());
        this.searchItem = this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C33402());
        this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.listAdapter = new ListAdapter(context);
        this.searchAdapter = new SearchAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C33413());
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.setVisibility(8);
        this.emptyView.setShowAtCenter(true);
        this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.emptyView.showTextView();
        updateRowsIds();
        return this.fragmentView;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.chatInfoDidLoaded) {
            TLRPC$ChatFull chatFull = args[0];
            if (chatFull.id == this.chat_id) {
                this.info = chatFull;
                updateChatParticipants();
                updateRowsIds();
            }
        } else if (id == NotificationCenter.updateInterfaces) {
            int mask = ((Integer) args[0]).intValue();
            if (((mask & 2) != 0 || (mask & 1) != 0 || (mask & 4) != 0) && this.listView != null) {
                int count = this.listView.getChildCount();
                for (int a = 0; a < count; a++) {
                    View child = this.listView.getChildAt(a);
                    if (child instanceof UserCell) {
                        ((UserCell) child).update(mask);
                    }
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void setChatInfo(TLRPC$ChatFull chatParticipants) {
        this.info = chatParticipants;
        updateChatParticipants();
    }

    private int getChatAdminParticipantType(TLRPC$ChatParticipant participant) {
        if (participant instanceof TLRPC$TL_chatParticipantCreator) {
            return 0;
        }
        if (participant instanceof TLRPC$TL_chatParticipantAdmin) {
            return 1;
        }
        return 2;
    }

    private void updateChatParticipants() {
        if (this.info != null && this.participants.size() != this.info.participants.participants.size()) {
            this.participants.clear();
            this.participants.addAll(this.info.participants.participants);
            try {
                Collections.sort(this.participants, new C33424());
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    private void updateRowsIds() {
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.allAdminsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.allAdminsInfoRow = i;
        if (this.info != null) {
            this.usersStartRow = this.rowCount;
            this.rowCount += this.participants.size();
            i = this.rowCount;
            this.rowCount = i + 1;
            this.usersEndRow = i;
            if (!(this.searchItem == null || this.searchWas)) {
                this.searchItem.setVisibility(0);
            }
        } else {
            this.usersStartRow = -1;
            this.usersEndRow = -1;
            if (this.searchItem != null) {
                this.searchItem.setVisibility(8);
            }
        }
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new C33435();
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[34];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextCheckCell.class, UserCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch);
        themeDescriptionArr[8] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[11] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[19] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, null, null, Theme.key_checkboxSquareUnchecked);
        themeDescriptionArr[20] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, null, null, Theme.key_checkboxSquareDisabled);
        themeDescriptionArr[21] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, null, null, Theme.key_checkboxSquareBackground);
        themeDescriptionArr[22] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, null, null, Theme.key_checkboxSquareCheck);
        themeDescriptionArr[23] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[24] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteGrayText);
        themeDescriptionArr[25] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteBlueText);
        themeDescriptionArr[26] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[27] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[28] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[29] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[30] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[31] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[32] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[33] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
        return themeDescriptionArr;
    }
}
