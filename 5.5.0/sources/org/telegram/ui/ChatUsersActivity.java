package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import java.util.ArrayList;
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
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.TL_chatParticipant;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.ManageChatUserCell;
import org.telegram.ui.Cells.ManageChatUserCell.ManageChatUserCellDelegate;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class ChatUsersActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int search_button = 0;
    private int chatId = this.arguments.getInt("chat_id");
    private Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
    private EmptyTextProgressView emptyView;
    private boolean firstLoaded;
    private ChatFull info;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private boolean loadingUsers;
    private ArrayList<ChatParticipant> participants = new ArrayList();
    private int participantsEndRow;
    private int participantsInfoRow;
    private int participantsStartRow;
    private int rowCount;
    private ActionBarMenuItem searchItem;
    private SearchAdapter searchListViewAdapter;
    private boolean searchWas;
    private boolean searching;

    /* renamed from: org.telegram.ui.ChatUsersActivity$1 */
    class C42851 extends ActionBarMenuOnItemClick {
        C42851() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ChatUsersActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChatUsersActivity$2 */
    class C42862 extends ActionBarMenuItemSearchListener {
        C42862() {
        }

        public void onSearchCollapse() {
            ChatUsersActivity.this.searchListViewAdapter.searchDialogs(null);
            ChatUsersActivity.this.searching = false;
            ChatUsersActivity.this.searchWas = false;
            ChatUsersActivity.this.listView.setAdapter(ChatUsersActivity.this.listViewAdapter);
            ChatUsersActivity.this.listViewAdapter.notifyDataSetChanged();
            ChatUsersActivity.this.listView.setFastScrollVisible(true);
            ChatUsersActivity.this.listView.setVerticalScrollBarEnabled(false);
            ChatUsersActivity.this.emptyView.setShowAtCenter(false);
        }

        public void onSearchExpand() {
            ChatUsersActivity.this.searching = true;
            ChatUsersActivity.this.emptyView.setShowAtCenter(true);
        }

        public void onTextChanged(EditText editText) {
            if (ChatUsersActivity.this.searchListViewAdapter != null) {
                String obj = editText.getText().toString();
                if (obj.length() != 0) {
                    ChatUsersActivity.this.searchWas = true;
                    if (ChatUsersActivity.this.listView != null) {
                        ChatUsersActivity.this.listView.setAdapter(ChatUsersActivity.this.searchListViewAdapter);
                        ChatUsersActivity.this.searchListViewAdapter.notifyDataSetChanged();
                        ChatUsersActivity.this.listView.setFastScrollVisible(false);
                        ChatUsersActivity.this.listView.setVerticalScrollBarEnabled(true);
                    }
                }
                ChatUsersActivity.this.searchListViewAdapter.searchDialogs(obj);
            }
        }
    }

    /* renamed from: org.telegram.ui.ChatUsersActivity$3 */
    class C42873 implements OnItemClickListener {
        C42873() {
        }

        public void onItemClick(View view, int i) {
            int i2;
            ChatParticipant item;
            if (ChatUsersActivity.this.listView.getAdapter() == ChatUsersActivity.this.listViewAdapter) {
                item = ChatUsersActivity.this.listViewAdapter.getItem(i);
                if (item != null) {
                    i2 = item.user_id;
                }
                i2 = 0;
            } else {
                TLObject item2 = ChatUsersActivity.this.searchListViewAdapter.getItem(i);
                item = item2 instanceof ChatParticipant ? (ChatParticipant) item2 : null;
                if (item != null) {
                    i2 = item.user_id;
                }
                i2 = 0;
            }
            if (i2 != 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", i2);
                ChatUsersActivity.this.presentFragment(new ProfileActivity(bundle));
            }
        }
    }

    /* renamed from: org.telegram.ui.ChatUsersActivity$4 */
    class C42884 implements OnItemLongClickListener {
        C42884() {
        }

        public boolean onItemClick(View view, int i) {
            return ChatUsersActivity.this.getParentActivity() != null && ChatUsersActivity.this.listView.getAdapter() == ChatUsersActivity.this.listViewAdapter && ChatUsersActivity.this.createMenuForParticipant(ChatUsersActivity.this.listViewAdapter.getItem(i), false);
        }
    }

    /* renamed from: org.telegram.ui.ChatUsersActivity$5 */
    class C42895 extends OnScrollListener {
        C42895() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1 && ChatUsersActivity.this.searching && ChatUsersActivity.this.searchWas) {
                AndroidUtilities.hideKeyboard(ChatUsersActivity.this.getParentActivity().getCurrentFocus());
            }
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
        }
    }

    /* renamed from: org.telegram.ui.ChatUsersActivity$7 */
    class C42917 implements ThemeDescriptionDelegate {
        C42917() {
        }

        public void didSetColor(int i) {
            int childCount = ChatUsersActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = ChatUsersActivity.this.listView.getChildAt(i2);
                if (childAt instanceof ManageChatUserCell) {
                    ((ManageChatUserCell) childAt).update(0);
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.ChatUsersActivity$ListAdapter$1 */
        class C42921 implements ManageChatUserCellDelegate {
            C42921() {
            }

            public boolean onOptionsButtonCheck(ManageChatUserCell manageChatUserCell, boolean z) {
                return ChatUsersActivity.this.createMenuForParticipant(ChatUsersActivity.this.listViewAdapter.getItem(((Integer) manageChatUserCell.getTag()).intValue()), !z);
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public ChatParticipant getItem(int i) {
            return (ChatUsersActivity.this.participantsStartRow == -1 || i < ChatUsersActivity.this.participantsStartRow || i >= ChatUsersActivity.this.participantsEndRow) ? null : (ChatParticipant) ChatUsersActivity.this.participants.get(i - ChatUsersActivity.this.participantsStartRow);
        }

        public int getItemCount() {
            return ChatUsersActivity.this.loadingUsers ? 0 : ChatUsersActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return ((i < ChatUsersActivity.this.participantsStartRow || i >= ChatUsersActivity.this.participantsEndRow) && i == ChatUsersActivity.this.participantsInfoRow) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 0 || itemViewType == 2 || itemViewType == 6;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ManageChatUserCell manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                    manageChatUserCell.setTag(Integer.valueOf(i));
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(getItem(i).user_id));
                    if (user != null) {
                        manageChatUserCell.setData(user, null, null);
                        return;
                    }
                    return;
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == ChatUsersActivity.this.participantsInfoRow) {
                        textInfoPrivacyCell.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View manageChatUserCell;
            switch (i) {
                case 0:
                    manageChatUserCell = new ManageChatUserCell(this.mContext, 1, true);
                    manageChatUserCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((ManageChatUserCell) manageChatUserCell).setDelegate(new C42921());
                    break;
                default:
                    manageChatUserCell = new TextInfoPrivacyCell(this.mContext);
                    manageChatUserCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
            }
            return new Holder(manageChatUserCell);
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ManageChatUserCell) {
                ((ManageChatUserCell) viewHolder.itemView).recycle();
            }
        }
    }

    private class SearchAdapter extends SelectionAdapter {
        private Context mContext;
        private ArrayList<ChatParticipant> searchResult = new ArrayList();
        private ArrayList<CharSequence> searchResultNames = new ArrayList();
        private Timer searchTimer;

        /* renamed from: org.telegram.ui.ChatUsersActivity$SearchAdapter$4 */
        class C42974 implements ManageChatUserCellDelegate {
            C42974() {
            }

            public boolean onOptionsButtonCheck(ManageChatUserCell manageChatUserCell, boolean z) {
                boolean z2 = false;
                if (!(SearchAdapter.this.getItem(((Integer) manageChatUserCell.getTag()).intValue()) instanceof ChatParticipant)) {
                    return false;
                }
                ChatParticipant chatParticipant = (ChatParticipant) SearchAdapter.this.getItem(((Integer) manageChatUserCell.getTag()).intValue());
                ChatUsersActivity chatUsersActivity = ChatUsersActivity.this;
                if (!z) {
                    z2 = true;
                }
                return chatUsersActivity.createMenuForParticipant(chatParticipant, z2);
            }
        }

        public SearchAdapter(Context context) {
            this.mContext = context;
        }

        private void processSearch(final String str) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    final ArrayList arrayList = new ArrayList();
                    arrayList.addAll(ChatUsersActivity.this.participants);
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

        public TLObject getItem(int i) {
            return (TLObject) this.searchResult.get(i);
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
            TLObject item = getItem(i);
            User user = item instanceof User ? (User) item : MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) item).user_id));
            String str = user.username;
            CharSequence charSequence3 = (CharSequence) this.searchResultNames.get(i);
            if (charSequence3 == null || str == null || str.length() <= 0 || !charSequence3.toString().startsWith("@" + str)) {
                charSequence = charSequence3;
            } else {
                charSequence = null;
                charSequence2 = charSequence3;
            }
            ManageChatUserCell manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
            manageChatUserCell.setTag(Integer.valueOf(i));
            manageChatUserCell.setData(user, charSequence, charSequence2);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View manageChatUserCell = new ManageChatUserCell(this.mContext, 2, true);
            manageChatUserCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            ((ManageChatUserCell) manageChatUserCell).setDelegate(new C42974());
            return new Holder(manageChatUserCell);
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ManageChatUserCell) {
                ((ManageChatUserCell) viewHolder.itemView).recycle();
            }
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

    public ChatUsersActivity(Bundle bundle) {
        super(bundle);
    }

    private boolean createMenuForParticipant(final ChatParticipant chatParticipant, boolean z) {
        if (chatParticipant == null) {
            return false;
        }
        int clientUserId = UserConfig.getClientUserId();
        if (chatParticipant.user_id == clientUserId) {
            return false;
        }
        boolean z2 = this.currentChat.creator ? true : (chatParticipant instanceof TL_chatParticipant) && ((this.currentChat.admin && this.currentChat.admins_enabled) || chatParticipant.inviter_id == clientUserId);
        if (!z2) {
            return false;
        }
        if (z) {
            return true;
        }
        ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        arrayList.add(LocaleController.getString("KickFromGroup", R.string.KickFromGroup));
        arrayList2.add(Integer.valueOf(0));
        Builder builder = new Builder(getParentActivity());
        builder.setItems((CharSequence[]) arrayList.toArray(new CharSequence[arrayList2.size()]), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (((Integer) arrayList2.get(i)).intValue() == 0) {
                    MessagesController.getInstance().deleteUserFromChat(ChatUsersActivity.this.chatId, MessagesController.getInstance().getUser(Integer.valueOf(chatParticipant.user_id)), ChatUsersActivity.this.info);
                }
            }
        });
        showDialog(builder.create());
        return true;
    }

    private void fetchUsers() {
        if (this.info == null) {
            this.loadingUsers = true;
            return;
        }
        this.loadingUsers = false;
        this.participants = new ArrayList(this.info.participants.participants);
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    private void updateRows() {
        this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
        if (this.currentChat != null) {
            this.participantsStartRow = -1;
            this.participantsEndRow = -1;
            this.participantsInfoRow = -1;
            this.rowCount = 0;
            if (this.participants.isEmpty()) {
                this.participantsStartRow = -1;
                this.participantsEndRow = -1;
            } else {
                this.participantsStartRow = this.rowCount;
                this.rowCount += this.participants.size();
                this.participantsEndRow = this.rowCount;
            }
            if (this.rowCount != 0) {
                int i = this.rowCount;
                this.rowCount = i + 1;
                this.participantsInfoRow = i;
            }
        }
    }

    public View createView(Context context) {
        int i = 1;
        this.searching = false;
        this.searchWas = false;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("GroupMembers", R.string.GroupMembers));
        this.actionBar.setActionBarMenuOnItemClick(new C42851());
        this.searchListViewAdapter = new SearchAdapter(context);
        this.searchItem = this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C42862());
        this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.emptyView);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listViewAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        recyclerListView = this.listView;
        if (!LocaleController.isRTL) {
            i = 2;
        }
        recyclerListView.setVerticalScrollbarPosition(i);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C42873());
        this.listView.setOnItemLongClickListener(new C42884());
        this.listView.setOnScrollListener(new C42895());
        if (this.loadingUsers) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        updateRows();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            boolean booleanValue = ((Boolean) objArr[2]).booleanValue();
            if (chatFull.id == this.chatId && !booleanValue) {
                this.info = chatFull;
                fetchUsers();
                updateRows();
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        C42917 c42917 = new C42917();
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[22];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ManageChatUserCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"statusColor"}, null, null, (ThemeDescriptionDelegate) c42917, Theme.key_windowBackgroundWhiteGrayText);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"statusOnlineColor"}, null, null, (ThemeDescriptionDelegate) c42917, Theme.key_windowBackgroundWhiteBlueText);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[15] = new ThemeDescription(null, 0, null, null, null, c42917, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[16] = new ThemeDescription(null, 0, null, null, null, c42917, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[17] = new ThemeDescription(null, 0, null, null, null, c42917, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[18] = new ThemeDescription(null, 0, null, null, null, c42917, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[19] = new ThemeDescription(null, 0, null, null, null, c42917, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[20] = new ThemeDescription(null, 0, null, null, null, c42917, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[21] = new ThemeDescription(null, 0, null, null, null, c42917, Theme.key_avatar_backgroundPink);
        return themeDescriptionArr;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        fetchUsers();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    protected void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && !z2) {
            this.searchItem.openSearch(true);
        }
    }

    public void setInfo(ChatFull chatFull) {
        this.info = chatFull;
    }
}
