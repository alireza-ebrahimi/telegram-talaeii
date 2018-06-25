package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.ScheduleDownloadActivity;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$ChannelParticipant;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$TL_channelAdminRights;
import org.telegram.tgnet.TLRPC$TL_channelBannedRights;
import org.telegram.tgnet.TLRPC$TL_channelFull;
import org.telegram.tgnet.TLRPC$TL_channelParticipant;
import org.telegram.tgnet.TLRPC$TL_channelParticipantAdmin;
import org.telegram.tgnet.TLRPC$TL_channelParticipantBanned;
import org.telegram.tgnet.TLRPC$TL_channelParticipantCreator;
import org.telegram.tgnet.TLRPC$TL_channelParticipantsRecent;
import org.telegram.tgnet.TLRPC$TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC$TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC$TL_chatChannelParticipant;
import org.telegram.tgnet.TLRPC$TL_chatParticipantAdmin;
import org.telegram.tgnet.TLRPC$TL_chatParticipants;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Adapters.SearchAdapterHelper;
import org.telegram.ui.Adapters.SearchAdapterHelper.HashtagObject;
import org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Cells.ManageChatTextCell;
import org.telegram.ui.Cells.ManageChatUserCell;
import org.telegram.ui.Cells.ManageChatUserCell.ManageChatUserCellDelegate;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.ChannelRightsEditActivity.ChannelRightsEditActivityDelegate;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class ChannelEditActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int search_button = 1;
    private int blockedUsersRow;
    private int chat_id;
    private TLRPC$Chat currentChat;
    private int eventLogRow;
    private TLRPC$ChatFull info;
    private int infoRow;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private int loadMoreMembersRow;
    private boolean loadingUsers;
    private int managementRow;
    private int membersEndRow;
    private int membersSection2Row;
    private int membersSectionRow;
    private int membersStartRow;
    private HashMap<Integer, TLRPC$ChatParticipant> participantsMap = new HashMap();
    private int permissionsRow;
    private int rowCount = 0;
    private SearchAdapter searchListViewAdapter;
    private boolean searchWas;
    private boolean searching;
    private ArrayList<Integer> sortedUsers;
    private boolean usersEndReached;

    /* renamed from: org.telegram.ui.ChannelEditActivity$2 */
    class C23022 extends ActionBarMenuOnItemClick {
        C23022() {
        }

        public void onItemClick(int id) {
            if (ChannelEditActivity.this.getParentActivity() != null && id == -1) {
                ChannelEditActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditActivity$3 */
    class C23033 extends ActionBarMenuItemSearchListener {
        C23033() {
        }

        public void onSearchExpand() {
            ChannelEditActivity.this.searching = true;
        }

        public void onSearchCollapse() {
            ChannelEditActivity.this.searchListViewAdapter.searchDialogs(null);
            ChannelEditActivity.this.searching = false;
            ChannelEditActivity.this.searchWas = false;
            ChannelEditActivity.this.listView.setAdapter(ChannelEditActivity.this.listViewAdapter);
            ChannelEditActivity.this.listViewAdapter.notifyDataSetChanged();
            ChannelEditActivity.this.listView.setFastScrollVisible(true);
            ChannelEditActivity.this.listView.setVerticalScrollBarEnabled(false);
        }

        public void onTextChanged(EditText editText) {
            if (ChannelEditActivity.this.searchListViewAdapter != null) {
                String text = editText.getText().toString();
                if (text.length() != 0) {
                    ChannelEditActivity.this.searchWas = true;
                    if (ChannelEditActivity.this.listView != null) {
                        ChannelEditActivity.this.listView.setAdapter(ChannelEditActivity.this.searchListViewAdapter);
                        ChannelEditActivity.this.searchListViewAdapter.notifyDataSetChanged();
                        ChannelEditActivity.this.listView.setFastScrollVisible(false);
                        ChannelEditActivity.this.listView.setVerticalScrollBarEnabled(true);
                    }
                }
                ChannelEditActivity.this.searchListViewAdapter.searchDialogs(text);
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditActivity$5 */
    class C23055 implements OnItemClickListener {
        C23055() {
        }

        public void onItemClick(View view, int position) {
            if (ChannelEditActivity.this.getParentActivity() != null) {
                Bundle args;
                if (ChannelEditActivity.this.listView.getAdapter() == ChannelEditActivity.this.searchListViewAdapter) {
                    args = new Bundle();
                    args.putInt("user_id", ChannelEditActivity.this.searchListViewAdapter.getItem(position).user_id);
                    ChannelEditActivity.this.presentFragment(new ProfileActivity(args));
                } else if (position >= ChannelEditActivity.this.membersStartRow && position < ChannelEditActivity.this.membersEndRow) {
                    int user_id;
                    if (ChannelEditActivity.this.sortedUsers.isEmpty()) {
                        user_id = ((TLRPC$ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(position - ChannelEditActivity.this.membersStartRow)).user_id;
                    } else {
                        user_id = ((TLRPC$ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(((Integer) ChannelEditActivity.this.sortedUsers.get(position - ChannelEditActivity.this.membersStartRow)).intValue())).user_id;
                    }
                    args = new Bundle();
                    args.putInt("user_id", user_id);
                    ChannelEditActivity.this.presentFragment(new ProfileActivity(args));
                } else if (position == ChannelEditActivity.this.blockedUsersRow || position == ChannelEditActivity.this.managementRow) {
                    args = new Bundle();
                    args.putInt("chat_id", ChannelEditActivity.this.chat_id);
                    if (position == ChannelEditActivity.this.blockedUsersRow) {
                        args.putInt(Param.TYPE, 0);
                    } else if (position == ChannelEditActivity.this.managementRow) {
                        args.putInt(Param.TYPE, 1);
                    }
                    ChannelEditActivity.this.presentFragment(new ChannelUsersActivity(args));
                } else if (position == ChannelEditActivity.this.permissionsRow) {
                    ChannelPermissionsActivity permissions = new ChannelPermissionsActivity(ChannelEditActivity.this.chat_id);
                    permissions.setInfo(ChannelEditActivity.this.info);
                    ChannelEditActivity.this.presentFragment(permissions);
                } else if (position == ChannelEditActivity.this.eventLogRow) {
                    ChannelEditActivity.this.presentFragment(new ChannelAdminLogActivity(ChannelEditActivity.this.currentChat));
                } else if (position == ChannelEditActivity.this.infoRow) {
                    args = new Bundle();
                    args.putInt("chat_id", ChannelEditActivity.this.chat_id);
                    ChannelEditInfoActivity fragment = new ChannelEditInfoActivity(args);
                    fragment.setInfo(ChannelEditActivity.this.info);
                    ChannelEditActivity.this.presentFragment(fragment);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditActivity$6 */
    class C23066 implements OnItemLongClickListener {
        C23066() {
        }

        public boolean onItemClick(View view, int position) {
            if (position < ChannelEditActivity.this.membersStartRow || position >= ChannelEditActivity.this.membersEndRow) {
                return false;
            }
            if (ChannelEditActivity.this.getParentActivity() == null) {
                return false;
            }
            TLRPC$TL_chatChannelParticipant user;
            if (ChannelEditActivity.this.sortedUsers.isEmpty()) {
                user = (TLRPC$TL_chatChannelParticipant) ChannelEditActivity.this.info.participants.participants.get(position - ChannelEditActivity.this.membersStartRow);
            } else {
                user = (TLRPC$TL_chatChannelParticipant) ChannelEditActivity.this.info.participants.participants.get(((Integer) ChannelEditActivity.this.sortedUsers.get(position - ChannelEditActivity.this.membersStartRow)).intValue());
            }
            return ChannelEditActivity.this.createMenuForParticipant(user, null, false);
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditActivity$9 */
    class C23119 implements ThemeDescriptionDelegate {
        C23119() {
        }

        public void didSetColor(int color) {
            int count = ChannelEditActivity.this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = ChannelEditActivity.this.listView.getChildAt(a);
                if (child instanceof ManageChatUserCell) {
                    ((ManageChatUserCell) child).update(0);
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.ChannelEditActivity$ListAdapter$1 */
        class C23121 implements ManageChatUserCellDelegate {
            C23121() {
            }

            public boolean onOptionsButtonCheck(ManageChatUserCell cell, boolean click) {
                TLRPC$ChatParticipant part;
                int i = ((Integer) cell.getTag()).intValue();
                if (ChannelEditActivity.this.sortedUsers.isEmpty()) {
                    part = (TLRPC$ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(i - ChannelEditActivity.this.membersStartRow);
                } else {
                    part = (TLRPC$ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(((Integer) ChannelEditActivity.this.sortedUsers.get(i - ChannelEditActivity.this.membersStartRow)).intValue());
                }
                return ChannelEditActivity.this.createMenuForParticipant((TLRPC$TL_chatChannelParticipant) part, null, !click);
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 0:
                    view = new ManageChatTextCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new ManageChatUserCell(this.mContext, 8, true);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((ManageChatUserCell) view).setDelegate(new C23121());
                    break;
                case 2:
                    view = new ShadowSectionCell(this.mContext);
                    break;
                case 3:
                    view = new LoadingCell(this.mContext);
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public void onViewRecycled(ViewHolder holder) {
            if (holder.itemView instanceof ManageChatUserCell) {
                ((ManageChatUserCell) holder.itemView).recycle();
            }
        }

        public void onBindViewHolder(ViewHolder holder, int i) {
            boolean z = false;
            String str = null;
            switch (holder.getItemViewType()) {
                case 0:
                    ManageChatTextCell textCell = holder.itemView;
                    textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    textCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                    if (i == ChannelEditActivity.this.managementRow) {
                        textCell.setText(LocaleController.getString("ChannelAdministrators", R.string.ChannelAdministrators), ChannelEditActivity.this.info != null ? String.format("%d", new Object[]{Integer.valueOf(ChannelEditActivity.this.info.admins_count)}) : null, R.drawable.group_admin, ChannelEditActivity.this.blockedUsersRow != -1);
                        return;
                    } else if (i == ChannelEditActivity.this.blockedUsersRow) {
                        String string = LocaleController.getString("ChannelBlacklist", R.string.ChannelBlacklist);
                        if (ChannelEditActivity.this.info != null) {
                            str = String.format("%d", new Object[]{Integer.valueOf(ChannelEditActivity.this.info.kicked_count + ChannelEditActivity.this.info.banned_count)});
                        }
                        textCell.setText(string, str, R.drawable.group_banned, false);
                        return;
                    } else if (i == ChannelEditActivity.this.eventLogRow) {
                        textCell.setText(LocaleController.getString("EventLog", R.string.EventLog), null, R.drawable.group_log, true);
                        return;
                    } else if (i == ChannelEditActivity.this.infoRow) {
                        textCell.setText(ChannelEditActivity.this.currentChat.megagroup ? LocaleController.getString("EventLogFilterGroupInfo", R.string.EventLogFilterGroupInfo) : LocaleController.getString("EventLogFilterChannelInfo", R.string.EventLogFilterChannelInfo), null, R.drawable.group_edit, true);
                        return;
                    } else if (i != ChannelEditActivity.this.permissionsRow) {
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TLRPC$ChatParticipant part;
                    ManageChatUserCell userCell = holder.itemView;
                    userCell.setTag(Integer.valueOf(i));
                    if (ChannelEditActivity.this.sortedUsers.isEmpty()) {
                        part = (TLRPC$ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(i - ChannelEditActivity.this.membersStartRow);
                    } else {
                        part = (TLRPC$ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(((Integer) ChannelEditActivity.this.sortedUsers.get(i - ChannelEditActivity.this.membersStartRow)).intValue());
                    }
                    if (part != null) {
                        if (part instanceof TLRPC$TL_chatChannelParticipant) {
                            TLRPC$ChannelParticipant channelParticipant = ((TLRPC$TL_chatChannelParticipant) part).channelParticipant;
                            if ((channelParticipant instanceof TLRPC$TL_channelParticipantCreator) || (channelParticipant instanceof TLRPC$TL_channelParticipantAdmin)) {
                                z = true;
                            }
                            userCell.setIsAdmin(z);
                        } else {
                            userCell.setIsAdmin(part instanceof TLRPC$TL_chatParticipantAdmin);
                        }
                        userCell.setData(MessagesController.getInstance().getUser(Integer.valueOf(part.user_id)), null, null);
                        return;
                    }
                    return;
                case 2:
                    if (i != ChannelEditActivity.this.membersSectionRow || ChannelEditActivity.this.membersStartRow == -1) {
                        holder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        holder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                default:
                    return;
            }
        }

        public boolean isEnabled(ViewHolder holder) {
            int type = holder.getItemViewType();
            if (type == 0 || type == 1) {
                return true;
            }
            return false;
        }

        public int getItemCount() {
            return ChannelEditActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            if (i == ChannelEditActivity.this.managementRow || i == ChannelEditActivity.this.blockedUsersRow || i == ChannelEditActivity.this.infoRow || i == ChannelEditActivity.this.eventLogRow || i == ChannelEditActivity.this.permissionsRow) {
                return 0;
            }
            if (i >= ChannelEditActivity.this.membersStartRow && i < ChannelEditActivity.this.membersEndRow) {
                return 1;
            }
            if (i == ChannelEditActivity.this.membersSectionRow || i == ChannelEditActivity.this.membersSection2Row) {
                return 2;
            }
            if (i == ChannelEditActivity.this.loadMoreMembersRow) {
                return 3;
            }
            return 0;
        }
    }

    private class SearchAdapter extends SelectionAdapter {
        private Context mContext;
        private SearchAdapterHelper searchAdapterHelper = new SearchAdapterHelper();
        private Timer searchTimer;

        /* renamed from: org.telegram.ui.ChannelEditActivity$SearchAdapter$4 */
        class C23164 implements ManageChatUserCellDelegate {
            C23164() {
            }

            public boolean onOptionsButtonCheck(ManageChatUserCell cell, boolean click) {
                return ChannelEditActivity.this.createMenuForParticipant(null, SearchAdapter.this.getItem(((Integer) cell.getTag()).intValue()), !click);
            }
        }

        public SearchAdapter(Context context) {
            this.mContext = context;
            this.searchAdapterHelper.setDelegate(new SearchAdapterHelperDelegate(ChannelEditActivity.this) {
                public void onDataSetChanged() {
                    SearchAdapter.this.notifyDataSetChanged();
                }

                public void onSetHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
                }
            });
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
                this.searchAdapterHelper.queryServerSearch(null, false, false, true, true, ChannelEditActivity.this.chat_id, false);
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
                    SearchAdapter.this.searchAdapterHelper.queryServerSearch(query, false, false, true, true, ChannelEditActivity.this.chat_id, false);
                }
            });
        }

        public boolean isEnabled(ViewHolder holder) {
            return holder.getItemViewType() != 1;
        }

        public int getItemCount() {
            return this.searchAdapterHelper.getGroupSearch().size();
        }

        public TLRPC$ChannelParticipant getItem(int i) {
            return (TLRPC$ChannelParticipant) this.searchAdapterHelper.getGroupSearch().get(i);
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = new ManageChatUserCell(this.mContext, 8, true);
            view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            ((ManageChatUserCell) view).setDelegate(new C23164());
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    User user;
                    boolean isAdmin;
                    TLObject object = getItem(position);
                    if (object instanceof User) {
                        user = (User) object;
                        TLRPC$ChatParticipant part = (TLRPC$ChatParticipant) ChannelEditActivity.this.participantsMap.get(Integer.valueOf(user.id));
                        if (part instanceof TLRPC$TL_chatChannelParticipant) {
                            TLRPC$ChannelParticipant channelParticipant = ((TLRPC$TL_chatChannelParticipant) part).channelParticipant;
                            isAdmin = (channelParticipant instanceof TLRPC$TL_channelParticipantCreator) || (channelParticipant instanceof TLRPC$TL_channelParticipantAdmin);
                        } else {
                            isAdmin = part instanceof TLRPC$TL_chatParticipantAdmin;
                        }
                    } else {
                        isAdmin = (object instanceof TLRPC$TL_channelParticipantAdmin) || (object instanceof TLRPC$TL_channelParticipantCreator);
                        user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$ChannelParticipant) object).user_id));
                    }
                    CharSequence name = null;
                    String nameSearch = this.searchAdapterHelper.getLastFoundChannel();
                    if (nameSearch != null) {
                        String u = UserObject.getUserName(user);
                        name = new SpannableStringBuilder(u);
                        int idx = u.toLowerCase().indexOf(nameSearch);
                        if (idx != -1) {
                            ((SpannableStringBuilder) name).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), idx, nameSearch.length() + idx, 33);
                        }
                    }
                    ManageChatUserCell userCell = holder.itemView;
                    userCell.setTag(Integer.valueOf(position));
                    userCell.setIsAdmin(isAdmin);
                    userCell.setData(user, name, null);
                    return;
                default:
                    return;
            }
        }

        public int getItemViewType(int i) {
            return 0;
        }
    }

    public ChannelEditActivity(Bundle args) {
        super(args);
    }

    public boolean onFragmentCreate() {
        this.chat_id = getArguments().getInt("chat_id", 0);
        this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
        if (this.currentChat == null) {
            final Semaphore semaphore = new Semaphore(0);
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    ChannelEditActivity.this.currentChat = MessagesStorage.getInstance().getChat(ChannelEditActivity.this.chat_id);
                    semaphore.release();
                }
            });
            try {
                semaphore.acquire();
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (this.currentChat == null) {
                return false;
            }
            MessagesController.getInstance().putChat(this.currentChat, true);
        }
        getChannelParticipants(true);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        this.sortedUsers = new ArrayList();
        updateRowsIds();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
    }

    public View createView(Context context) {
        Theme.createProfileResources(context);
        this.searching = false;
        this.searchWas = false;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.currentChat.megagroup) {
            this.actionBar.setTitle(LocaleController.getString("ManageGroup", R.string.ManageGroup));
        } else {
            this.actionBar.setTitle(LocaleController.getString("ManageChannel", R.string.ManageChannel));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C23022());
        this.searchListViewAdapter = new SearchAdapter(context);
        this.actionBar.createMenu().addItem(1, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C23033()).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.listViewAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = this.fragmentView;
        EmptyTextProgressView emptyView = new EmptyTextProgressView(context);
        emptyView.setShowAtCenter(true);
        emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        emptyView.showTextView();
        frameLayout.addView(emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context) {
            public boolean hasOverlappingRendering() {
                return false;
            }
        };
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setEmptyView(emptyView);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listViewAdapter);
        this.listView.setOnItemClickListener(new C23055());
        this.listView.setOnItemLongClickListener(new C23066());
        return this.fragmentView;
    }

    public void didReceivedNotification(int id, Object... args) {
        boolean loadChannelParticipants = false;
        if (id == NotificationCenter.chatInfoDidLoaded) {
            TLRPC$ChatFull chatFull = args[0];
            if (chatFull.id == this.chat_id) {
                boolean byChannelUsers = ((Boolean) args[2]).booleanValue();
                if ((this.info instanceof TLRPC$TL_channelFull) && chatFull.participants == null && this.info != null) {
                    chatFull.participants = this.info.participants;
                }
                if (this.info == null && (chatFull instanceof TLRPC$TL_channelFull)) {
                    loadChannelParticipants = true;
                }
                this.info = chatFull;
                fetchUsersFromChannelInfo();
                updateRowsIds();
                if (this.listViewAdapter != null) {
                    this.listViewAdapter.notifyDataSetChanged();
                }
                TLRPC$Chat newChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                if (newChat != null) {
                    this.currentChat = newChat;
                }
                if (loadChannelParticipants || !byChannelUsers) {
                    getChannelParticipants(true);
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    private void getChannelParticipants(boolean reload) {
        int i = 0;
        if (!this.loadingUsers && this.participantsMap != null && this.info != null) {
            int delay;
            this.loadingUsers = true;
            if (this.participantsMap.isEmpty() || !reload) {
                delay = 0;
            } else {
                delay = ScheduleDownloadActivity.CHECK_CELL2;
            }
            final TLRPC$TL_channels_getParticipants req = new TLRPC$TL_channels_getParticipants();
            req.channel = MessagesController.getInputChannel(this.chat_id);
            req.filter = new TLRPC$TL_channelParticipantsRecent();
            if (!reload) {
                i = this.participantsMap.size();
            }
            req.offset = i;
            req.limit = 200;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (error == null) {
                                TLRPC$TL_channels_channelParticipants res = response;
                                MessagesController.getInstance().putUsers(res.users, false);
                                if (res.users.size() != 200) {
                                    ChannelEditActivity.this.usersEndReached = true;
                                }
                                if (req.offset == 0) {
                                    ChannelEditActivity.this.participantsMap.clear();
                                    ChannelEditActivity.this.info.participants = new TLRPC$TL_chatParticipants();
                                    MessagesStorage.getInstance().putUsersAndChats(res.users, null, true, true);
                                    MessagesStorage.getInstance().updateChannelUsers(ChannelEditActivity.this.chat_id, res.participants);
                                }
                                for (int a = 0; a < res.participants.size(); a++) {
                                    TLRPC$TL_chatChannelParticipant participant = new TLRPC$TL_chatChannelParticipant();
                                    participant.channelParticipant = (TLRPC$ChannelParticipant) res.participants.get(a);
                                    participant.inviter_id = participant.channelParticipant.inviter_id;
                                    participant.user_id = participant.channelParticipant.user_id;
                                    participant.date = participant.channelParticipant.date;
                                    if (!ChannelEditActivity.this.participantsMap.containsKey(Integer.valueOf(participant.user_id))) {
                                        ChannelEditActivity.this.info.participants.participants.add(participant);
                                        ChannelEditActivity.this.participantsMap.put(Integer.valueOf(participant.user_id), participant);
                                    }
                                }
                            }
                            ChannelEditActivity.this.loadingUsers = false;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{ChannelEditActivity.this.info, Integer.valueOf(0), Boolean.valueOf(true), null});
                        }
                    }, (long) delay);
                }
            }), this.classGuid);
        }
    }

    public void setInfo(TLRPC$ChatFull chatInfo) {
        this.info = chatInfo;
        fetchUsersFromChannelInfo();
    }

    private void fetchUsersFromChannelInfo() {
        if ((this.info instanceof TLRPC$TL_channelFull) && this.info.participants != null) {
            for (int a = 0; a < this.info.participants.participants.size(); a++) {
                TLRPC$ChatParticipant chatParticipant = (TLRPC$ChatParticipant) this.info.participants.participants.get(a);
                this.participantsMap.put(Integer.valueOf(chatParticipant.user_id), chatParticipant);
            }
        }
    }

    private void updateRowsIds() {
        int i;
        this.rowCount = 0;
        if (ChatObject.canEditInfo(this.currentChat)) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.infoRow = i;
        } else {
            this.infoRow = -1;
        }
        this.permissionsRow = -1;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.eventLogRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.managementRow = i;
        if (this.currentChat.megagroup || !(this.info == null || (this.info.banned_count == 0 && this.info.kicked_count == 0))) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.blockedUsersRow = i;
        } else {
            this.blockedUsersRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.membersSectionRow = i;
        if (this.info == null || this.info.participants == null || this.info.participants.participants.isEmpty()) {
            this.membersStartRow = -1;
            this.membersEndRow = -1;
            this.loadMoreMembersRow = -1;
            this.membersSection2Row = -1;
            return;
        }
        this.membersStartRow = this.rowCount;
        this.rowCount += this.info.participants.participants.size();
        this.membersEndRow = this.rowCount;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.membersSection2Row = i;
        if (this.usersEndReached) {
            this.loadMoreMembersRow = -1;
            return;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.loadMoreMembersRow = i;
    }

    private boolean createMenuForParticipant(TLRPC$TL_chatChannelParticipant user, TLRPC$ChannelParticipant channelParticipant, boolean resultOnly) {
        if (user == null && channelParticipant == null) {
            return false;
        }
        int uid;
        ArrayList<String> items;
        ArrayList<Integer> actions;
        int currentUserId = UserConfig.getClientUserId();
        if (channelParticipant != null) {
            if (currentUserId == channelParticipant.user_id) {
                return false;
            }
            uid = channelParticipant.user_id;
            user = (TLRPC$TL_chatChannelParticipant) this.participantsMap.get(Integer.valueOf(channelParticipant.user_id));
            if (user != null) {
                channelParticipant = user.channelParticipant;
            }
        } else if (user.user_id == UserConfig.getClientUserId()) {
            return false;
        } else {
            uid = user.user_id;
            channelParticipant = user.channelParticipant;
        }
        User u = MessagesController.getInstance().getUser(Integer.valueOf(uid));
        boolean allowSetAdmin = (channelParticipant instanceof TLRPC$TL_channelParticipant) || (channelParticipant instanceof TLRPC$TL_channelParticipantBanned);
        boolean canEditAdmin = !((channelParticipant instanceof TLRPC$TL_channelParticipantAdmin) || (channelParticipant instanceof TLRPC$TL_channelParticipantCreator)) || channelParticipant.can_edit;
        if (resultOnly) {
            items = null;
            actions = null;
        } else {
            items = new ArrayList();
            actions = new ArrayList();
        }
        if (allowSetAdmin && ChatObject.canAddAdmins(this.currentChat)) {
            if (resultOnly) {
                return true;
            }
            items.add(LocaleController.getString("SetAsAdmin", R.string.SetAsAdmin));
            actions.add(Integer.valueOf(0));
        }
        if (ChatObject.canBlockUsers(this.currentChat) && canEditAdmin) {
            if (resultOnly) {
                return true;
            }
            if (this.currentChat.megagroup) {
                items.add(LocaleController.getString("KickFromSupergroup", R.string.KickFromSupergroup));
                actions.add(Integer.valueOf(1));
                items.add(LocaleController.getString("KickFromGroup", R.string.KickFromGroup));
                actions.add(Integer.valueOf(2));
            } else {
                items.add(LocaleController.getString("ChannelRemoveUser", R.string.ChannelRemoveUser));
                actions.add(Integer.valueOf(2));
            }
        }
        if (items == null || items.isEmpty()) {
            return false;
        }
        final TLRPC$ChannelParticipant channelParticipantFinal = channelParticipant;
        final TLRPC$TL_chatChannelParticipant userFinal = user;
        Builder builder = new Builder(getParentActivity());
        builder.setItems((CharSequence[]) items.toArray(new CharSequence[items.size()]), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, final int i) {
                if (((Integer) actions.get(i)).intValue() == 2) {
                    MessagesController.getInstance().deleteUserFromChat(ChannelEditActivity.this.chat_id, MessagesController.getInstance().getUser(Integer.valueOf(uid)), ChannelEditActivity.this.info);
                    return;
                }
                ChannelRightsEditActivity fragment = new ChannelRightsEditActivity(channelParticipantFinal.user_id, ChannelEditActivity.this.chat_id, channelParticipantFinal.admin_rights, channelParticipantFinal.banned_rights, ((Integer) actions.get(i)).intValue(), true);
                fragment.setDelegate(new ChannelRightsEditActivityDelegate() {
                    public void didSetRights(int rights, TLRPC$TL_channelAdminRights rightsAdmin, TLRPC$TL_channelBannedRights rightsBanned) {
                        channelParticipantFinal.admin_rights = rightsAdmin;
                        channelParticipantFinal.banned_rights = rightsBanned;
                        if (((Integer) actions.get(i)).intValue() == 0) {
                            if (userFinal != null) {
                                if (rights == 1) {
                                    userFinal.channelParticipant = new TLRPC$TL_channelParticipantAdmin();
                                } else {
                                    userFinal.channelParticipant = new TLRPC$TL_channelParticipant();
                                }
                                userFinal.channelParticipant.inviter_id = UserConfig.getClientUserId();
                                userFinal.channelParticipant.user_id = userFinal.user_id;
                                userFinal.channelParticipant.date = userFinal.date;
                            }
                        } else if (((Integer) actions.get(i)).intValue() == 1 && rights == 0 && ChannelEditActivity.this.currentChat.megagroup && ChannelEditActivity.this.info != null && ChannelEditActivity.this.info.participants != null) {
                            int a;
                            boolean changed = false;
                            for (a = 0; a < ChannelEditActivity.this.info.participants.participants.size(); a++) {
                                if (((TLRPC$TL_chatChannelParticipant) ChannelEditActivity.this.info.participants.participants.get(a)).channelParticipant.user_id == uid) {
                                    if (ChannelEditActivity.this.info != null) {
                                        TLRPC$ChatFull access$1000 = ChannelEditActivity.this.info;
                                        access$1000.participants_count--;
                                    }
                                    ChannelEditActivity.this.info.participants.participants.remove(a);
                                    changed = true;
                                    if (ChannelEditActivity.this.info != null && ChannelEditActivity.this.info.participants != null) {
                                        for (a = 0; a < ChannelEditActivity.this.info.participants.participants.size(); a++) {
                                            if (((TLRPC$ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(a)).user_id == uid) {
                                                ChannelEditActivity.this.info.participants.participants.remove(a);
                                                changed = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (changed) {
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{ChannelEditActivity.this.info, Integer.valueOf(0), Boolean.valueOf(true), null});
                                    }
                                }
                            }
                            for (a = 0; a < ChannelEditActivity.this.info.participants.participants.size(); a++) {
                                if (((TLRPC$ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(a)).user_id == uid) {
                                    ChannelEditActivity.this.info.participants.participants.remove(a);
                                    changed = true;
                                    break;
                                }
                            }
                            if (changed) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{ChannelEditActivity.this.info, Integer.valueOf(0), Boolean.valueOf(true), null});
                            }
                        }
                    }
                });
                ChannelEditActivity.this.presentFragment(fragment);
            }
        });
        showDialog(builder.create());
        return true;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new C23119();
        r10 = new ThemeDescription[30];
        r10[7] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r10[8] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{ManageChatTextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{ManageChatTextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGreenText2);
        r10[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{ManageChatTextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText5);
        r10[12] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatTextCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        r10[13] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[14] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"statusColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteGrayText);
        r10[15] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"statusOnlineColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteBlueText);
        r10[16] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[17] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        r10[18] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        r10[19] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        r10[20] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        r10[21] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        r10[22] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        r10[23] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
        r10[24] = new ThemeDescription(this.listView, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        r10[25] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[26] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGray);
        r10[27] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[28] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGray);
        r10[29] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r10;
    }
}
