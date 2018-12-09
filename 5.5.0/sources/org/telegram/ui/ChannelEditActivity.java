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
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.TL_channelAdminRights;
import org.telegram.tgnet.TLRPC.TL_channelBannedRights;
import org.telegram.tgnet.TLRPC.TL_channelFull;
import org.telegram.tgnet.TLRPC.TL_channelParticipant;
import org.telegram.tgnet.TLRPC.TL_channelParticipantAdmin;
import org.telegram.tgnet.TLRPC.TL_channelParticipantBanned;
import org.telegram.tgnet.TLRPC.TL_channelParticipantCreator;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsRecent;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC.TL_chatChannelParticipant;
import org.telegram.tgnet.TLRPC.TL_chatParticipantAdmin;
import org.telegram.tgnet.TLRPC.TL_chatParticipants;
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
    private Chat currentChat;
    private int eventLogRow;
    private ChatFull info;
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
    private HashMap<Integer, ChatParticipant> participantsMap = new HashMap();
    private int permissionsRow;
    private int rowCount = 0;
    private SearchAdapter searchListViewAdapter;
    private boolean searchWas;
    private boolean searching;
    private ArrayList<Integer> sortedUsers;
    private boolean usersEndReached;

    /* renamed from: org.telegram.ui.ChannelEditActivity$2 */
    class C41402 extends ActionBarMenuOnItemClick {
        C41402() {
        }

        public void onItemClick(int i) {
            if (ChannelEditActivity.this.getParentActivity() != null && i == -1) {
                ChannelEditActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditActivity$3 */
    class C41413 extends ActionBarMenuItemSearchListener {
        C41413() {
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

        public void onSearchExpand() {
            ChannelEditActivity.this.searching = true;
        }

        public void onTextChanged(EditText editText) {
            if (ChannelEditActivity.this.searchListViewAdapter != null) {
                String obj = editText.getText().toString();
                if (obj.length() != 0) {
                    ChannelEditActivity.this.searchWas = true;
                    if (ChannelEditActivity.this.listView != null) {
                        ChannelEditActivity.this.listView.setAdapter(ChannelEditActivity.this.searchListViewAdapter);
                        ChannelEditActivity.this.searchListViewAdapter.notifyDataSetChanged();
                        ChannelEditActivity.this.listView.setFastScrollVisible(false);
                        ChannelEditActivity.this.listView.setVerticalScrollBarEnabled(true);
                    }
                }
                ChannelEditActivity.this.searchListViewAdapter.searchDialogs(obj);
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditActivity$5 */
    class C41435 implements OnItemClickListener {
        C41435() {
        }

        public void onItemClick(View view, int i) {
            if (ChannelEditActivity.this.getParentActivity() != null) {
                Bundle bundle;
                if (ChannelEditActivity.this.listView.getAdapter() == ChannelEditActivity.this.searchListViewAdapter) {
                    bundle = new Bundle();
                    bundle.putInt("user_id", ChannelEditActivity.this.searchListViewAdapter.getItem(i).user_id);
                    ChannelEditActivity.this.presentFragment(new ProfileActivity(bundle));
                } else if (i >= ChannelEditActivity.this.membersStartRow && i < ChannelEditActivity.this.membersEndRow) {
                    int i2 = !ChannelEditActivity.this.sortedUsers.isEmpty() ? ((ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(((Integer) ChannelEditActivity.this.sortedUsers.get(i - ChannelEditActivity.this.membersStartRow)).intValue())).user_id : ((ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(i - ChannelEditActivity.this.membersStartRow)).user_id;
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("user_id", i2);
                    ChannelEditActivity.this.presentFragment(new ProfileActivity(bundle2));
                } else if (i == ChannelEditActivity.this.blockedUsersRow || i == ChannelEditActivity.this.managementRow) {
                    bundle = new Bundle();
                    bundle.putInt("chat_id", ChannelEditActivity.this.chat_id);
                    if (i == ChannelEditActivity.this.blockedUsersRow) {
                        bundle.putInt(Param.TYPE, 0);
                    } else if (i == ChannelEditActivity.this.managementRow) {
                        bundle.putInt(Param.TYPE, 1);
                    }
                    ChannelEditActivity.this.presentFragment(new ChannelUsersActivity(bundle));
                } else if (i == ChannelEditActivity.this.permissionsRow) {
                    BaseFragment channelPermissionsActivity = new ChannelPermissionsActivity(ChannelEditActivity.this.chat_id);
                    channelPermissionsActivity.setInfo(ChannelEditActivity.this.info);
                    ChannelEditActivity.this.presentFragment(channelPermissionsActivity);
                } else if (i == ChannelEditActivity.this.eventLogRow) {
                    ChannelEditActivity.this.presentFragment(new ChannelAdminLogActivity(ChannelEditActivity.this.currentChat));
                } else if (i == ChannelEditActivity.this.infoRow) {
                    bundle = new Bundle();
                    bundle.putInt("chat_id", ChannelEditActivity.this.chat_id);
                    BaseFragment channelEditInfoActivity = new ChannelEditInfoActivity(bundle);
                    channelEditInfoActivity.setInfo(ChannelEditActivity.this.info);
                    ChannelEditActivity.this.presentFragment(channelEditInfoActivity);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditActivity$6 */
    class C41446 implements OnItemLongClickListener {
        C41446() {
        }

        public boolean onItemClick(View view, int i) {
            if (i < ChannelEditActivity.this.membersStartRow || i >= ChannelEditActivity.this.membersEndRow) {
                return false;
            }
            if (ChannelEditActivity.this.getParentActivity() == null) {
                return false;
            }
            return ChannelEditActivity.this.createMenuForParticipant(!ChannelEditActivity.this.sortedUsers.isEmpty() ? (TL_chatChannelParticipant) ChannelEditActivity.this.info.participants.participants.get(((Integer) ChannelEditActivity.this.sortedUsers.get(i - ChannelEditActivity.this.membersStartRow)).intValue()) : (TL_chatChannelParticipant) ChannelEditActivity.this.info.participants.participants.get(i - ChannelEditActivity.this.membersStartRow), null, false);
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditActivity$9 */
    class C41499 implements ThemeDescriptionDelegate {
        C41499() {
        }

        public void didSetColor(int i) {
            int childCount = ChannelEditActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = ChannelEditActivity.this.listView.getChildAt(i2);
                if (childAt instanceof ManageChatUserCell) {
                    ((ManageChatUserCell) childAt).update(0);
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.ChannelEditActivity$ListAdapter$1 */
        class C41501 implements ManageChatUserCellDelegate {
            C41501() {
            }

            public boolean onOptionsButtonCheck(ManageChatUserCell manageChatUserCell, boolean z) {
                int intValue = ((Integer) manageChatUserCell.getTag()).intValue();
                return ChannelEditActivity.this.createMenuForParticipant((TL_chatChannelParticipant) (!ChannelEditActivity.this.sortedUsers.isEmpty() ? (ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(((Integer) ChannelEditActivity.this.sortedUsers.get(intValue - ChannelEditActivity.this.membersStartRow)).intValue()) : (ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(intValue - ChannelEditActivity.this.membersStartRow)), null, !z);
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return ChannelEditActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == ChannelEditActivity.this.managementRow || i == ChannelEditActivity.this.blockedUsersRow || i == ChannelEditActivity.this.infoRow || i == ChannelEditActivity.this.eventLogRow || i == ChannelEditActivity.this.permissionsRow) ? 0 : (i < ChannelEditActivity.this.membersStartRow || i >= ChannelEditActivity.this.membersEndRow) ? (i == ChannelEditActivity.this.membersSectionRow || i == ChannelEditActivity.this.membersSection2Row) ? 2 : i == ChannelEditActivity.this.loadMoreMembersRow ? 3 : 0 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 0 || itemViewType == 1;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            String str = null;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ManageChatTextCell manageChatTextCell = (ManageChatTextCell) viewHolder.itemView;
                    manageChatTextCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    manageChatTextCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                    if (i == ChannelEditActivity.this.managementRow) {
                        manageChatTextCell.setText(LocaleController.getString("ChannelAdministrators", R.string.ChannelAdministrators), ChannelEditActivity.this.info != null ? String.format("%d", new Object[]{Integer.valueOf(ChannelEditActivity.this.info.admins_count)}) : null, R.drawable.group_admin, ChannelEditActivity.this.blockedUsersRow != -1);
                        return;
                    } else if (i == ChannelEditActivity.this.blockedUsersRow) {
                        String string = LocaleController.getString("ChannelBlacklist", R.string.ChannelBlacklist);
                        if (ChannelEditActivity.this.info != null) {
                            str = String.format("%d", new Object[]{Integer.valueOf(ChannelEditActivity.this.info.kicked_count + ChannelEditActivity.this.info.banned_count)});
                        }
                        manageChatTextCell.setText(string, str, R.drawable.group_banned, false);
                        return;
                    } else if (i == ChannelEditActivity.this.eventLogRow) {
                        manageChatTextCell.setText(LocaleController.getString("EventLog", R.string.EventLog), null, R.drawable.group_log, true);
                        return;
                    } else if (i == ChannelEditActivity.this.infoRow) {
                        manageChatTextCell.setText(ChannelEditActivity.this.currentChat.megagroup ? LocaleController.getString("EventLogFilterGroupInfo", R.string.EventLogFilterGroupInfo) : LocaleController.getString("EventLogFilterChannelInfo", R.string.EventLogFilterChannelInfo), null, R.drawable.group_edit, true);
                        return;
                    } else if (i != ChannelEditActivity.this.permissionsRow) {
                        return;
                    } else {
                        return;
                    }
                case 1:
                    ManageChatUserCell manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                    manageChatUserCell.setTag(Integer.valueOf(i));
                    ChatParticipant chatParticipant = !ChannelEditActivity.this.sortedUsers.isEmpty() ? (ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(((Integer) ChannelEditActivity.this.sortedUsers.get(i - ChannelEditActivity.this.membersStartRow)).intValue()) : (ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(i - ChannelEditActivity.this.membersStartRow);
                    if (chatParticipant != null) {
                        if (chatParticipant instanceof TL_chatChannelParticipant) {
                            ChannelParticipant channelParticipant = ((TL_chatChannelParticipant) chatParticipant).channelParticipant;
                            if ((channelParticipant instanceof TL_channelParticipantCreator) || (channelParticipant instanceof TL_channelParticipantAdmin)) {
                                z = true;
                            }
                            manageChatUserCell.setIsAdmin(z);
                        } else {
                            manageChatUserCell.setIsAdmin(chatParticipant instanceof TL_chatParticipantAdmin);
                        }
                        manageChatUserCell.setData(MessagesController.getInstance().getUser(Integer.valueOf(chatParticipant.user_id)), null, null);
                        return;
                    }
                    return;
                case 2:
                    if (i != ChannelEditActivity.this.membersSectionRow || ChannelEditActivity.this.membersStartRow == -1) {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                    view = new ManageChatTextCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new ManageChatUserCell(this.mContext, 8, true);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((ManageChatUserCell) view).setDelegate(new C41501());
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

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ManageChatUserCell) {
                ((ManageChatUserCell) viewHolder.itemView).recycle();
            }
        }
    }

    private class SearchAdapter extends SelectionAdapter {
        private Context mContext;
        private SearchAdapterHelper searchAdapterHelper = new SearchAdapterHelper();
        private Timer searchTimer;

        /* renamed from: org.telegram.ui.ChannelEditActivity$SearchAdapter$4 */
        class C41544 implements ManageChatUserCellDelegate {
            C41544() {
            }

            public boolean onOptionsButtonCheck(ManageChatUserCell manageChatUserCell, boolean z) {
                return ChannelEditActivity.this.createMenuForParticipant(null, SearchAdapter.this.getItem(((Integer) manageChatUserCell.getTag()).intValue()), !z);
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

        private void processSearch(final String str) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    SearchAdapter.this.searchAdapterHelper.queryServerSearch(str, false, false, true, true, ChannelEditActivity.this.chat_id, false);
                }
            });
        }

        public ChannelParticipant getItem(int i) {
            return (ChannelParticipant) this.searchAdapterHelper.getGroupSearch().get(i);
        }

        public int getItemCount() {
            return this.searchAdapterHelper.getGroupSearch().size();
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() != 1;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    User user;
                    CharSequence spannableStringBuilder;
                    ChannelParticipant item = getItem(i);
                    if (item instanceof User) {
                        boolean z2;
                        User user2 = (User) item;
                        ChatParticipant chatParticipant = (ChatParticipant) ChannelEditActivity.this.participantsMap.get(Integer.valueOf(user2.id));
                        if (chatParticipant instanceof TL_chatChannelParticipant) {
                            ChannelParticipant channelParticipant = ((TL_chatChannelParticipant) chatParticipant).channelParticipant;
                            z2 = (channelParticipant instanceof TL_channelParticipantCreator) || (channelParticipant instanceof TL_channelParticipantAdmin);
                        } else {
                            z2 = chatParticipant instanceof TL_chatParticipantAdmin;
                        }
                        z = z2;
                        user = user2;
                    } else {
                        if ((item instanceof TL_channelParticipantAdmin) || (item instanceof TL_channelParticipantCreator)) {
                            z = true;
                        }
                        user = MessagesController.getInstance().getUser(Integer.valueOf(item.user_id));
                    }
                    String lastFoundChannel = this.searchAdapterHelper.getLastFoundChannel();
                    if (lastFoundChannel != null) {
                        Object userName = UserObject.getUserName(user);
                        spannableStringBuilder = new SpannableStringBuilder(userName);
                        int indexOf = userName.toLowerCase().indexOf(lastFoundChannel);
                        if (indexOf != -1) {
                            ((SpannableStringBuilder) spannableStringBuilder).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastFoundChannel.length() + indexOf, 33);
                        }
                    } else {
                        spannableStringBuilder = null;
                    }
                    ManageChatUserCell manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                    manageChatUserCell.setTag(Integer.valueOf(i));
                    manageChatUserCell.setIsAdmin(z);
                    manageChatUserCell.setData(user, spannableStringBuilder, null);
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View manageChatUserCell = new ManageChatUserCell(this.mContext, 8, true);
            manageChatUserCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            ((ManageChatUserCell) manageChatUserCell).setDelegate(new C41544());
            return new Holder(manageChatUserCell);
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
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    SearchAdapter.this.processSearch(str);
                }
            }, 200, 300);
        }
    }

    public ChannelEditActivity(Bundle bundle) {
        super(bundle);
    }

    private boolean createMenuForParticipant(TL_chatChannelParticipant tL_chatChannelParticipant, ChannelParticipant channelParticipant, boolean z) {
        ArrayList arrayList = null;
        if (tL_chatChannelParticipant == null && channelParticipant == null) {
            return false;
        }
        int i;
        ChannelParticipant channelParticipant2;
        TL_chatChannelParticipant tL_chatChannelParticipant2;
        ArrayList arrayList2;
        int clientUserId = UserConfig.getClientUserId();
        if (channelParticipant != null) {
            if (clientUserId == channelParticipant.user_id) {
                return false;
            }
            i = channelParticipant.user_id;
            TL_chatChannelParticipant tL_chatChannelParticipant3 = (TL_chatChannelParticipant) this.participantsMap.get(Integer.valueOf(channelParticipant.user_id));
            if (tL_chatChannelParticipant3 != null) {
                channelParticipant2 = tL_chatChannelParticipant3.channelParticipant;
                tL_chatChannelParticipant2 = tL_chatChannelParticipant3;
            } else {
                channelParticipant2 = channelParticipant;
                tL_chatChannelParticipant2 = tL_chatChannelParticipant3;
            }
        } else if (tL_chatChannelParticipant.user_id == UserConfig.getClientUserId()) {
            return false;
        } else {
            i = tL_chatChannelParticipant.user_id;
            channelParticipant2 = tL_chatChannelParticipant.channelParticipant;
            tL_chatChannelParticipant2 = tL_chatChannelParticipant;
        }
        MessagesController.getInstance().getUser(Integer.valueOf(i));
        boolean z2 = (channelParticipant2 instanceof TL_channelParticipant) || (channelParticipant2 instanceof TL_channelParticipantBanned);
        boolean z3 = !((channelParticipant2 instanceof TL_channelParticipantAdmin) || (channelParticipant2 instanceof TL_channelParticipantCreator)) || channelParticipant2.can_edit;
        if (z) {
            arrayList2 = null;
        } else {
            arrayList2 = new ArrayList();
            arrayList = new ArrayList();
        }
        if (z2 && ChatObject.canAddAdmins(this.currentChat)) {
            if (z) {
                return true;
            }
            arrayList2.add(LocaleController.getString("SetAsAdmin", R.string.SetAsAdmin));
            arrayList.add(Integer.valueOf(0));
        }
        if (ChatObject.canBlockUsers(this.currentChat) && z3) {
            if (z) {
                return true;
            }
            if (this.currentChat.megagroup) {
                arrayList2.add(LocaleController.getString("KickFromSupergroup", R.string.KickFromSupergroup));
                arrayList.add(Integer.valueOf(1));
                arrayList2.add(LocaleController.getString("KickFromGroup", R.string.KickFromGroup));
                arrayList.add(Integer.valueOf(2));
            } else {
                arrayList2.add(LocaleController.getString("ChannelRemoveUser", R.string.ChannelRemoveUser));
                arrayList.add(Integer.valueOf(2));
            }
        }
        if (arrayList2 == null || arrayList2.isEmpty()) {
            return false;
        }
        Builder builder = new Builder(getParentActivity());
        builder.setItems((CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, final int i) {
                if (((Integer) arrayList.get(i)).intValue() == 2) {
                    MessagesController.getInstance().deleteUserFromChat(ChannelEditActivity.this.chat_id, MessagesController.getInstance().getUser(Integer.valueOf(i)), ChannelEditActivity.this.info);
                    return;
                }
                BaseFragment channelRightsEditActivity = new ChannelRightsEditActivity(channelParticipant2.user_id, ChannelEditActivity.this.chat_id, channelParticipant2.admin_rights, channelParticipant2.banned_rights, ((Integer) arrayList.get(i)).intValue(), true);
                channelRightsEditActivity.setDelegate(new ChannelRightsEditActivityDelegate() {
                    public void didSetRights(int i, TL_channelAdminRights tL_channelAdminRights, TL_channelBannedRights tL_channelBannedRights) {
                        channelParticipant2.admin_rights = tL_channelAdminRights;
                        channelParticipant2.banned_rights = tL_channelBannedRights;
                        if (((Integer) arrayList.get(i)).intValue() == 0) {
                            if (tL_chatChannelParticipant2 != null) {
                                if (i == 1) {
                                    tL_chatChannelParticipant2.channelParticipant = new TL_channelParticipantAdmin();
                                } else {
                                    tL_chatChannelParticipant2.channelParticipant = new TL_channelParticipant();
                                }
                                tL_chatChannelParticipant2.channelParticipant.inviter_id = UserConfig.getClientUserId();
                                tL_chatChannelParticipant2.channelParticipant.user_id = tL_chatChannelParticipant2.user_id;
                                tL_chatChannelParticipant2.channelParticipant.date = tL_chatChannelParticipant2.date;
                            }
                        } else if (((Integer) arrayList.get(i)).intValue() == 1 && i == 0 && ChannelEditActivity.this.currentChat.megagroup && ChannelEditActivity.this.info != null && ChannelEditActivity.this.info.participants != null) {
                            boolean z;
                            int i2;
                            for (int i3 = 0; i3 < ChannelEditActivity.this.info.participants.participants.size(); i3++) {
                                if (((TL_chatChannelParticipant) ChannelEditActivity.this.info.participants.participants.get(i3)).channelParticipant.user_id == i) {
                                    if (ChannelEditActivity.this.info != null) {
                                        ChatFull access$1000 = ChannelEditActivity.this.info;
                                        access$1000.participants_count--;
                                    }
                                    ChannelEditActivity.this.info.participants.participants.remove(i3);
                                    z = true;
                                    if (ChannelEditActivity.this.info != null && ChannelEditActivity.this.info.participants != null) {
                                        for (i2 = 0; i2 < ChannelEditActivity.this.info.participants.participants.size(); i2++) {
                                            if (((ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(i2)).user_id == i) {
                                                ChannelEditActivity.this.info.participants.participants.remove(i2);
                                                z = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (z) {
                                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{ChannelEditActivity.this.info, Integer.valueOf(0), Boolean.valueOf(true), null});
                                    }
                                }
                            }
                            z = false;
                            for (i2 = 0; i2 < ChannelEditActivity.this.info.participants.participants.size(); i2++) {
                                if (((ChatParticipant) ChannelEditActivity.this.info.participants.participants.get(i2)).user_id == i) {
                                    ChannelEditActivity.this.info.participants.participants.remove(i2);
                                    z = true;
                                    break;
                                }
                            }
                            if (z) {
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{ChannelEditActivity.this.info, Integer.valueOf(0), Boolean.valueOf(true), null});
                            }
                        }
                    }
                });
                ChannelEditActivity.this.presentFragment(channelRightsEditActivity);
            }
        });
        showDialog(builder.create());
        return true;
    }

    private void fetchUsersFromChannelInfo() {
        if ((this.info instanceof TL_channelFull) && this.info.participants != null) {
            for (int i = 0; i < this.info.participants.participants.size(); i++) {
                ChatParticipant chatParticipant = (ChatParticipant) this.info.participants.participants.get(i);
                this.participantsMap.put(Integer.valueOf(chatParticipant.user_id), chatParticipant);
            }
        }
    }

    private void getChannelParticipants(boolean z) {
        int i = 0;
        if (!this.loadingUsers && this.participantsMap != null && this.info != null) {
            this.loadingUsers = true;
            final int i2 = (this.participantsMap.isEmpty() || !z) ? 0 : 300;
            final TLObject tL_channels_getParticipants = new TL_channels_getParticipants();
            tL_channels_getParticipants.channel = MessagesController.getInputChannel(this.chat_id);
            tL_channels_getParticipants.filter = new TL_channelParticipantsRecent();
            if (!z) {
                i = this.participantsMap.size();
            }
            tL_channels_getParticipants.offset = i;
            tL_channels_getParticipants.limit = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipants, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                TL_channels_channelParticipants tL_channels_channelParticipants = (TL_channels_channelParticipants) tLObject;
                                MessagesController.getInstance().putUsers(tL_channels_channelParticipants.users, false);
                                if (tL_channels_channelParticipants.users.size() != Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                                    ChannelEditActivity.this.usersEndReached = true;
                                }
                                if (tL_channels_getParticipants.offset == 0) {
                                    ChannelEditActivity.this.participantsMap.clear();
                                    ChannelEditActivity.this.info.participants = new TL_chatParticipants();
                                    MessagesStorage.getInstance().putUsersAndChats(tL_channels_channelParticipants.users, null, true, true);
                                    MessagesStorage.getInstance().updateChannelUsers(ChannelEditActivity.this.chat_id, tL_channels_channelParticipants.participants);
                                }
                                for (int i = 0; i < tL_channels_channelParticipants.participants.size(); i++) {
                                    TL_chatChannelParticipant tL_chatChannelParticipant = new TL_chatChannelParticipant();
                                    tL_chatChannelParticipant.channelParticipant = (ChannelParticipant) tL_channels_channelParticipants.participants.get(i);
                                    tL_chatChannelParticipant.inviter_id = tL_chatChannelParticipant.channelParticipant.inviter_id;
                                    tL_chatChannelParticipant.user_id = tL_chatChannelParticipant.channelParticipant.user_id;
                                    tL_chatChannelParticipant.date = tL_chatChannelParticipant.channelParticipant.date;
                                    if (!ChannelEditActivity.this.participantsMap.containsKey(Integer.valueOf(tL_chatChannelParticipant.user_id))) {
                                        ChannelEditActivity.this.info.participants.participants.add(tL_chatChannelParticipant);
                                        ChannelEditActivity.this.participantsMap.put(Integer.valueOf(tL_chatChannelParticipant.user_id), tL_chatChannelParticipant);
                                    }
                                }
                            }
                            ChannelEditActivity.this.loadingUsers = false;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{ChannelEditActivity.this.info, Integer.valueOf(0), Boolean.valueOf(true), null});
                        }
                    }, (long) i2);
                }
            }), this.classGuid);
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
        this.actionBar.setActionBarMenuOnItemClick(new C41402());
        this.searchListViewAdapter = new SearchAdapter(context);
        this.actionBar.createMenu().addItem(1, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C41413()).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.listViewAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        View emptyTextProgressView = new EmptyTextProgressView(context);
        emptyTextProgressView.setShowAtCenter(true);
        emptyTextProgressView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        emptyTextProgressView.showTextView();
        frameLayout.addView(emptyTextProgressView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context) {
            public boolean hasOverlappingRendering() {
                return false;
            }
        };
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setEmptyView(emptyTextProgressView);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listViewAdapter);
        this.listView.setOnItemClickListener(new C41435());
        this.listView.setOnItemLongClickListener(new C41446());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            if (chatFull.id == this.chat_id) {
                boolean booleanValue = ((Boolean) objArr[2]).booleanValue();
                if ((this.info instanceof TL_channelFull) && chatFull.participants == null && this.info != null) {
                    chatFull.participants = this.info.participants;
                }
                boolean z = this.info == null && (chatFull instanceof TL_channelFull);
                this.info = chatFull;
                fetchUsersFromChannelInfo();
                updateRowsIds();
                if (this.listViewAdapter != null) {
                    this.listViewAdapter.notifyDataSetChanged();
                }
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                if (chat != null) {
                    this.currentChat = chat;
                }
                if (z || !booleanValue) {
                    getChannelParticipants(true);
                }
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        C41499 c41499 = new C41499();
        r10 = new ThemeDescription[30];
        r10[7] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r10[8] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{ManageChatTextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{ManageChatTextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGreenText2);
        r10[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{ManageChatTextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText5);
        r10[12] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatTextCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        r10[13] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[14] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"statusColor"}, null, null, (ThemeDescriptionDelegate) c41499, Theme.key_windowBackgroundWhiteGrayText);
        r10[15] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"statusOnlineColor"}, null, null, (ThemeDescriptionDelegate) c41499, Theme.key_windowBackgroundWhiteBlueText);
        r10[16] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[17] = new ThemeDescription(null, 0, null, null, null, c41499, Theme.key_avatar_backgroundRed);
        r10[18] = new ThemeDescription(null, 0, null, null, null, c41499, Theme.key_avatar_backgroundOrange);
        r10[19] = new ThemeDescription(null, 0, null, null, null, c41499, Theme.key_avatar_backgroundViolet);
        r10[20] = new ThemeDescription(null, 0, null, null, null, c41499, Theme.key_avatar_backgroundGreen);
        r10[21] = new ThemeDescription(null, 0, null, null, null, c41499, Theme.key_avatar_backgroundCyan);
        r10[22] = new ThemeDescription(null, 0, null, null, null, c41499, Theme.key_avatar_backgroundBlue);
        r10[23] = new ThemeDescription(null, 0, null, null, null, c41499, Theme.key_avatar_backgroundPink);
        r10[24] = new ThemeDescription(this.listView, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        r10[25] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[26] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGray);
        r10[27] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[28] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGray);
        r10[29] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r10;
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
            } catch (Throwable e) {
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

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    public void setInfo(ChatFull chatFull) {
        this.info = chatFull;
        fetchUsersFromChannelInfo();
    }
}
