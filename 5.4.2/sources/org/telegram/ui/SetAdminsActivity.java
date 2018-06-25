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
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.TL_chatParticipant;
import org.telegram.tgnet.TLRPC.TL_chatParticipantAdmin;
import org.telegram.tgnet.TLRPC.TL_chatParticipantCreator;
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
    private Chat chat;
    private int chat_id;
    private EmptyTextProgressView emptyView;
    private ChatFull info;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private ArrayList<ChatParticipant> participants = new ArrayList();
    private int rowCount;
    private SearchAdapter searchAdapter;
    private ActionBarMenuItem searchItem;
    private boolean searchWas;
    private boolean searching;
    private int usersEndRow;
    private int usersStartRow;

    /* renamed from: org.telegram.ui.SetAdminsActivity$1 */
    class C51781 extends ActionBarMenuOnItemClick {
        C51781() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                SetAdminsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.SetAdminsActivity$2 */
    class C51792 extends ActionBarMenuItemSearchListener {
        C51792() {
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

        public void onSearchExpand() {
            SetAdminsActivity.this.searching = true;
            SetAdminsActivity.this.listView.setEmptyView(SetAdminsActivity.this.emptyView);
        }

        public void onTextChanged(EditText editText) {
            String obj = editText.getText().toString();
            if (obj.length() != 0) {
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
                SetAdminsActivity.this.searchAdapter.search(obj);
            }
        }
    }

    /* renamed from: org.telegram.ui.SetAdminsActivity$3 */
    class C51803 implements OnItemClickListener {
        C51803() {
        }

        public void onItemClick(View view, int i) {
            boolean z = true;
            if (SetAdminsActivity.this.listView.getAdapter() == SetAdminsActivity.this.searchAdapter || (i >= SetAdminsActivity.this.usersStartRow && i < SetAdminsActivity.this.usersEndRow)) {
                ChatParticipant item;
                int i2;
                UserCell userCell = (UserCell) view;
                SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
                if (SetAdminsActivity.this.listView.getAdapter() == SetAdminsActivity.this.searchAdapter) {
                    item = SetAdminsActivity.this.searchAdapter.getItem(i);
                    i2 = 0;
                    while (i2 < SetAdminsActivity.this.participants.size()) {
                        if (((ChatParticipant) SetAdminsActivity.this.participants.get(i2)).user_id == item.user_id) {
                            break;
                        }
                        i2++;
                    }
                    i2 = -1;
                } else {
                    i2 = i - SetAdminsActivity.this.usersStartRow;
                    item = (ChatParticipant) SetAdminsActivity.this.participants.get(i2);
                }
                if (i2 != -1 && !(item instanceof TL_chatParticipantCreator)) {
                    ChatParticipant tL_chatParticipantAdmin;
                    if (item instanceof TL_chatParticipant) {
                        tL_chatParticipantAdmin = new TL_chatParticipantAdmin();
                        tL_chatParticipantAdmin.user_id = item.user_id;
                        tL_chatParticipantAdmin.date = item.date;
                        tL_chatParticipantAdmin.inviter_id = item.inviter_id;
                    } else {
                        tL_chatParticipantAdmin = new TL_chatParticipant();
                        tL_chatParticipantAdmin.user_id = item.user_id;
                        tL_chatParticipantAdmin.date = item.date;
                        tL_chatParticipantAdmin.inviter_id = item.inviter_id;
                    }
                    SetAdminsActivity.this.participants.set(i2, tL_chatParticipantAdmin);
                    i2 = SetAdminsActivity.this.info.participants.participants.indexOf(item);
                    if (i2 != -1) {
                        SetAdminsActivity.this.info.participants.participants.set(i2, tL_chatParticipantAdmin);
                    }
                    if (SetAdminsActivity.this.listView.getAdapter() == SetAdminsActivity.this.searchAdapter) {
                        SetAdminsActivity.this.searchAdapter.searchResult.set(i, tL_chatParticipantAdmin);
                    }
                    boolean z2 = ((tL_chatParticipantAdmin instanceof TL_chatParticipant) && (SetAdminsActivity.this.chat == null || SetAdminsActivity.this.chat.admins_enabled)) ? false : true;
                    userCell.setChecked(z2, true);
                    if (SetAdminsActivity.this.chat != null && SetAdminsActivity.this.chat.admins_enabled) {
                        MessagesController instance = MessagesController.getInstance();
                        int access$1000 = SetAdminsActivity.this.chat_id;
                        int i3 = tL_chatParticipantAdmin.user_id;
                        if (tL_chatParticipantAdmin instanceof TL_chatParticipant) {
                            z = false;
                        }
                        instance.toggleUserAdmin(access$1000, i3, z);
                    }
                }
            } else if (i == SetAdminsActivity.this.allAdminsRow) {
                SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
                if (SetAdminsActivity.this.chat != null) {
                    SetAdminsActivity.this.chat.admins_enabled = !SetAdminsActivity.this.chat.admins_enabled;
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
    class C51814 implements Comparator<ChatParticipant> {
        C51814() {
        }

        public int compare(ChatParticipant chatParticipant, ChatParticipant chatParticipant2) {
            int access$1500 = SetAdminsActivity.this.getChatAdminParticipantType(chatParticipant);
            int access$15002 = SetAdminsActivity.this.getChatAdminParticipantType(chatParticipant2);
            if (access$1500 > access$15002) {
                return 1;
            }
            if (access$1500 < access$15002) {
                return -1;
            }
            if (access$1500 == access$15002) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(chatParticipant2.user_id));
                User user2 = MessagesController.getInstance().getUser(Integer.valueOf(chatParticipant.user_id));
                access$15002 = (user == null || user.status == null) ? 0 : user.status.expires;
                access$1500 = (user2 == null || user2.status == null) ? 0 : user2.status.expires;
                if (access$15002 > 0 && access$1500 > 0) {
                    return access$15002 <= access$1500 ? access$15002 < access$1500 ? -1 : 0 : 1;
                } else {
                    if (access$15002 < 0 && access$1500 < 0) {
                        return access$15002 <= access$1500 ? access$15002 < access$1500 ? -1 : 0 : 1;
                    } else {
                        if ((access$15002 < 0 && access$1500 > 0) || (access$15002 == 0 && access$1500 != 0)) {
                            return -1;
                        }
                        if (access$1500 < 0 && access$15002 > 0) {
                            return 1;
                        }
                        if (access$1500 == 0 && access$15002 != 0) {
                            return 1;
                        }
                    }
                }
            }
            return 0;
        }
    }

    /* renamed from: org.telegram.ui.SetAdminsActivity$5 */
    class C51825 implements ThemeDescriptionDelegate {
        C51825() {
        }

        public void didSetColor(int i) {
            int childCount = SetAdminsActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = SetAdminsActivity.this.listView.getChildAt(i2);
                if (childAt instanceof UserCell) {
                    ((UserCell) childAt).update(0);
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return SetAdminsActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return i == SetAdminsActivity.this.allAdminsRow ? 0 : (i == SetAdminsActivity.this.allAdminsInfoRow || i == SetAdminsActivity.this.usersEndRow) ? 1 : 2;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == SetAdminsActivity.this.allAdminsRow ? true : adapterPosition >= SetAdminsActivity.this.usersStartRow && adapterPosition < SetAdminsActivity.this.usersEndRow && !(((ChatParticipant) SetAdminsActivity.this.participants.get(adapterPosition - SetAdminsActivity.this.usersStartRow)) instanceof TL_chatParticipantCreator);
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    TextCheckCell textCheckCell = (TextCheckCell) viewHolder.itemView;
                    SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
                    String string = LocaleController.getString("SetAdminsAll", R.string.SetAdminsAll);
                    boolean z2 = (SetAdminsActivity.this.chat == null || SetAdminsActivity.this.chat.admins_enabled) ? false : true;
                    textCheckCell.setTextAndCheck(string, z2, false);
                    return;
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == SetAdminsActivity.this.allAdminsInfoRow) {
                        if (SetAdminsActivity.this.chat.admins_enabled) {
                            textInfoPrivacyCell.setText(LocaleController.getString("SetAdminsNotAllInfo", R.string.SetAdminsNotAllInfo));
                        } else {
                            textInfoPrivacyCell.setText(LocaleController.getString("SetAdminsAllInfo", R.string.SetAdminsAllInfo));
                        }
                        if (SetAdminsActivity.this.usersStartRow != -1) {
                            textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                            return;
                        } else {
                            textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                            return;
                        }
                    } else if (i == SetAdminsActivity.this.usersEndRow) {
                        textInfoPrivacyCell.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    UserCell userCell = (UserCell) viewHolder.itemView;
                    ChatParticipant chatParticipant = (ChatParticipant) SetAdminsActivity.this.participants.get(i - SetAdminsActivity.this.usersStartRow);
                    userCell.setData(MessagesController.getInstance().getUser(Integer.valueOf(chatParticipant.user_id)), null, null, 0);
                    SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
                    boolean z3 = ((chatParticipant instanceof TL_chatParticipant) && (SetAdminsActivity.this.chat == null || SetAdminsActivity.this.chat.admins_enabled)) ? false : true;
                    userCell.setChecked(z3, false);
                    if (SetAdminsActivity.this.chat == null || !SetAdminsActivity.this.chat.admins_enabled || chatParticipant.user_id == UserConfig.getClientUserId()) {
                        z = true;
                    }
                    userCell.setCheckDisabled(z);
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View textCheckCell;
            switch (i) {
                case 0:
                    textCheckCell = new TextCheckCell(this.mContext);
                    textCheckCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    textCheckCell = new TextInfoPrivacyCell(this.mContext);
                    break;
                default:
                    textCheckCell = new UserCell(this.mContext, 1, 2, false);
                    textCheckCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new Holder(textCheckCell);
        }
    }

    public class SearchAdapter extends SelectionAdapter {
        private Context mContext;
        private ArrayList<ChatParticipant> searchResult = new ArrayList();
        private ArrayList<CharSequence> searchResultNames = new ArrayList();
        private Timer searchTimer;

        public SearchAdapter(Context context) {
            this.mContext = context;
        }

        private void processSearch(final String str) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    final ArrayList arrayList = new ArrayList();
                    arrayList.addAll(SetAdminsActivity.this.participants);
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
                                ChatParticipant chatParticipant = (ChatParticipant) arrayList.get(i);
                                User user = MessagesController.getInstance().getUser(Integer.valueOf(chatParticipant.user_id));
                                if (user.id != UserConfig.getClientUserId()) {
                                    String toLowerCase2 = ContactsController.formatName(user.first_name, user.last_name).toLowerCase();
                                    toLowerCase = LocaleController.getInstance().getTranslitString(toLowerCase2);
                                    if (toLowerCase2.equals(toLowerCase)) {
                                        toLowerCase = null;
                                    }
                                    int length = strArr.length;
                                    Object obj = null;
                                    int i2 = 0;
                                    while (i2 < length) {
                                        String str2 = strArr[i2];
                                        if (toLowerCase2.startsWith(str2) || toLowerCase2.contains(" " + str2) || (r2 != null && (r2.startsWith(str2) || r2.contains(" " + str2)))) {
                                            obj = 1;
                                        } else if (user.username != null && user.username.startsWith(str2)) {
                                            obj = 2;
                                        }
                                        if (r3 != null) {
                                            if (r3 == 1) {
                                                arrayList2.add(AndroidUtilities.generateSearchName(user.first_name, user.last_name, str2));
                                            } else {
                                                arrayList2.add(AndroidUtilities.generateSearchName("@" + user.username, null, "@" + str2));
                                            }
                                            arrayList.add(chatParticipant);
                                        } else {
                                            i2++;
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

        private void updateSearchResults(final ArrayList<ChatParticipant> arrayList, final ArrayList<CharSequence> arrayList2) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    SearchAdapter.this.searchResult = arrayList;
                    SearchAdapter.this.searchResultNames = arrayList2;
                    SearchAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public ChatParticipant getItem(int i) {
            return (ChatParticipant) this.searchResult.get(i);
        }

        public int getItemCount() {
            return this.searchResult.size();
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            CharSequence charSequence;
            CharSequence charSequence2 = null;
            boolean z = false;
            ChatParticipant item = getItem(i);
            TLObject user = MessagesController.getInstance().getUser(Integer.valueOf(item.user_id));
            String str = user.username;
            if (i < this.searchResult.size()) {
                CharSequence charSequence3 = (CharSequence) this.searchResultNames.get(i);
                if (charSequence3 == null || str == null || str.length() <= 0 || !charSequence3.toString().startsWith("@" + str)) {
                    charSequence = null;
                    charSequence2 = charSequence3;
                } else {
                    charSequence = charSequence3;
                }
            } else {
                charSequence = null;
            }
            UserCell userCell = (UserCell) viewHolder.itemView;
            userCell.setData(user, charSequence2, charSequence, 0);
            SetAdminsActivity.this.chat = MessagesController.getInstance().getChat(Integer.valueOf(SetAdminsActivity.this.chat_id));
            boolean z2 = ((item instanceof TL_chatParticipant) && (SetAdminsActivity.this.chat == null || SetAdminsActivity.this.chat.admins_enabled)) ? false : true;
            userCell.setChecked(z2, false);
            if (SetAdminsActivity.this.chat == null || !SetAdminsActivity.this.chat.admins_enabled || item.user_id == UserConfig.getClientUserId()) {
                z = true;
            }
            userCell.setCheckDisabled(z);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(new UserCell(this.mContext, 1, 2, false));
        }

        public void search(final String str) {
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
    }

    public SetAdminsActivity(Bundle bundle) {
        super(bundle);
        this.chat_id = bundle.getInt("chat_id");
    }

    private int getChatAdminParticipantType(ChatParticipant chatParticipant) {
        return chatParticipant instanceof TL_chatParticipantCreator ? 0 : chatParticipant instanceof TL_chatParticipantAdmin ? 1 : 2;
    }

    private void updateChatParticipants() {
        if (this.info != null && this.participants.size() != this.info.participants.participants.size()) {
            this.participants.clear();
            this.participants.addAll(this.info.participants.participants);
            try {
                Collections.sort(this.participants, new C51814());
            } catch (Throwable e) {
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

    public View createView(Context context) {
        this.searching = false;
        this.searchWas = false;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("SetAdminsTitle", R.string.SetAdminsTitle));
        this.actionBar.setActionBarMenuOnItemClick(new C51781());
        this.searchItem = this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C51792());
        this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.listAdapter = new ListAdapter(context);
        this.searchAdapter = new SearchAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C51803());
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.setVisibility(8);
        this.emptyView.setShowAtCenter(true);
        this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.emptyView.showTextView();
        updateRowsIds();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            if (chatFull.id == this.chat_id) {
                this.info = chatFull;
                updateChatParticipants();
                updateRowsIds();
            }
        } else if (i == NotificationCenter.updateInterfaces) {
            int intValue = ((Integer) objArr[0]).intValue();
            if (((intValue & 2) != 0 || (intValue & 1) != 0 || (intValue & 4) != 0) && this.listView != null) {
                int childCount = this.listView.getChildCount();
                while (i2 < childCount) {
                    View childAt = this.listView.getChildAt(i2);
                    if (childAt instanceof UserCell) {
                        ((UserCell) childAt).update(intValue);
                    }
                    i2++;
                }
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        C51825 c51825 = new C51825();
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
        themeDescriptionArr[24] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, (ThemeDescriptionDelegate) c51825, Theme.key_windowBackgroundWhiteGrayText);
        themeDescriptionArr[25] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, null, null, (ThemeDescriptionDelegate) c51825, Theme.key_windowBackgroundWhiteBlueText);
        themeDescriptionArr[26] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[27] = new ThemeDescription(null, 0, null, null, null, c51825, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[28] = new ThemeDescription(null, 0, null, null, null, c51825, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[29] = new ThemeDescription(null, 0, null, null, null, c51825, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[30] = new ThemeDescription(null, 0, null, null, null, c51825, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[31] = new ThemeDescription(null, 0, null, null, null, c51825, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[32] = new ThemeDescription(null, 0, null, null, null, c51825, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[33] = new ThemeDescription(null, 0, null, null, null, c51825, Theme.key_avatar_backgroundPink);
        return themeDescriptionArr;
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

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void setChatInfo(ChatFull chatFull) {
        this.info = chatFull;
        updateChatParticipants();
    }
}
