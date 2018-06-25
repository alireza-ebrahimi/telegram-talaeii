package org.telegram.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputUserEmpty;
import org.telegram.tgnet.TLRPC$TL_messages_getCommonChats;
import org.telegram.tgnet.TLRPC$messages_Chats;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Cells.ProfileSearchCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class CommonGroupsActivity extends BaseFragment {
    private ArrayList<Chat> chats = new ArrayList();
    private EmptyTextProgressView emptyView;
    private boolean endReached;
    private boolean firstLoaded;
    private LinearLayoutManager layoutManager;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private boolean loading;
    private int userId;

    /* renamed from: org.telegram.ui.CommonGroupsActivity$1 */
    class C42981 extends ActionBarMenuOnItemClick {
        C42981() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                CommonGroupsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.CommonGroupsActivity$2 */
    class C42992 implements OnItemClickListener {
        C42992() {
        }

        public void onItemClick(View view, int i) {
            if (i >= 0 && i < CommonGroupsActivity.this.chats.size()) {
                Chat chat = (Chat) CommonGroupsActivity.this.chats.get(i);
                Bundle bundle = new Bundle();
                bundle.putInt("chat_id", chat.id);
                if (MessagesController.checkCanOpenChat(bundle, CommonGroupsActivity.this)) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                    CommonGroupsActivity.this.presentFragment(new ChatActivity(bundle), true);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.CommonGroupsActivity$3 */
    class C43003 extends OnScrollListener {
        C43003() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            int findFirstVisibleItemPosition = CommonGroupsActivity.this.layoutManager.findFirstVisibleItemPosition();
            int abs = findFirstVisibleItemPosition == -1 ? 0 : Math.abs(CommonGroupsActivity.this.layoutManager.findLastVisibleItemPosition() - findFirstVisibleItemPosition) + 1;
            if (abs > 0) {
                int itemCount = CommonGroupsActivity.this.listViewAdapter.getItemCount();
                if (!CommonGroupsActivity.this.endReached && !CommonGroupsActivity.this.loading && !CommonGroupsActivity.this.chats.isEmpty() && abs + findFirstVisibleItemPosition >= itemCount - 5) {
                    CommonGroupsActivity.this.getChats(((Chat) CommonGroupsActivity.this.chats.get(CommonGroupsActivity.this.chats.size() - 1)).id, 100);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.CommonGroupsActivity$5 */
    class C43035 implements ThemeDescriptionDelegate {
        C43035() {
        }

        public void didSetColor(int i) {
            int childCount = CommonGroupsActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = CommonGroupsActivity.this.listView.getChildAt(i2);
                if (childAt instanceof ProfileSearchCell) {
                    ((ProfileSearchCell) childAt).update(0);
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
            int size = CommonGroupsActivity.this.chats.size();
            if (CommonGroupsActivity.this.chats.isEmpty()) {
                return size;
            }
            size++;
            return !CommonGroupsActivity.this.endReached ? size + 1 : size;
        }

        public int getItemViewType(int i) {
            return i < CommonGroupsActivity.this.chats.size() ? 0 : (CommonGroupsActivity.this.endReached || i != CommonGroupsActivity.this.chats.size()) ? 2 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getAdapterPosition() != CommonGroupsActivity.this.chats.size();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            if (viewHolder.getItemViewType() == 0) {
                ProfileSearchCell profileSearchCell = (ProfileSearchCell) viewHolder.itemView;
                profileSearchCell.setData((Chat) CommonGroupsActivity.this.chats.get(i), null, null, null, false, false);
                if (!(i == CommonGroupsActivity.this.chats.size() - 1 && CommonGroupsActivity.this.endReached)) {
                    z = true;
                }
                profileSearchCell.useSeparator = z;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View profileSearchCell;
            switch (i) {
                case 0:
                    profileSearchCell = new ProfileSearchCell(this.mContext);
                    profileSearchCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    profileSearchCell = new LoadingCell(this.mContext);
                    profileSearchCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    profileSearchCell = new TextInfoPrivacyCell(this.mContext);
                    profileSearchCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
            }
            return new Holder(profileSearchCell);
        }
    }

    public CommonGroupsActivity(int i) {
        this.userId = i;
    }

    private void getChats(int i, final int i2) {
        if (!this.loading) {
            this.loading = true;
            if (!(this.emptyView == null || this.firstLoaded)) {
                this.emptyView.showProgress();
            }
            if (this.listViewAdapter != null) {
                this.listViewAdapter.notifyDataSetChanged();
            }
            TLObject tLRPC$TL_messages_getCommonChats = new TLRPC$TL_messages_getCommonChats();
            tLRPC$TL_messages_getCommonChats.user_id = MessagesController.getInputUser(this.userId);
            if (!(tLRPC$TL_messages_getCommonChats.user_id instanceof TLRPC$TL_inputUserEmpty)) {
                tLRPC$TL_messages_getCommonChats.limit = i2;
                tLRPC$TL_messages_getCommonChats.max_id = i;
                ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getCommonChats, new RequestDelegate() {
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                if (tLRPC$TL_error == null) {
                                    TLRPC$messages_Chats tLRPC$messages_Chats = (TLRPC$messages_Chats) tLObject;
                                    MessagesController.getInstance().putChats(tLRPC$messages_Chats.chats, false);
                                    CommonGroupsActivity commonGroupsActivity = CommonGroupsActivity.this;
                                    boolean z = tLRPC$messages_Chats.chats.isEmpty() || tLRPC$messages_Chats.chats.size() != i2;
                                    commonGroupsActivity.endReached = z;
                                    CommonGroupsActivity.this.chats.addAll(tLRPC$messages_Chats.chats);
                                } else {
                                    CommonGroupsActivity.this.endReached = true;
                                }
                                CommonGroupsActivity.this.loading = false;
                                CommonGroupsActivity.this.firstLoaded = true;
                                if (CommonGroupsActivity.this.emptyView != null) {
                                    CommonGroupsActivity.this.emptyView.showTextView();
                                }
                                if (CommonGroupsActivity.this.listViewAdapter != null) {
                                    CommonGroupsActivity.this.listViewAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }), this.classGuid);
            }
        }
    }

    public View createView(Context context) {
        int i = 1;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("GroupsInCommonTitle", R.string.GroupsInCommonTitle));
        this.actionBar.setActionBarMenuOnItemClick(new C42981());
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.setText(LocaleController.getString("NoGroupsInCommon", R.string.NoGroupsInCommon));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.emptyView);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.layoutManager = linearLayoutManager;
        recyclerListView.setLayoutManager(linearLayoutManager);
        recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listViewAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        recyclerListView = this.listView;
        if (!LocaleController.isRTL) {
            i = 2;
        }
        recyclerListView.setVerticalScrollbarPosition(i);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C42992());
        this.listView.setOnScrollListener(new C43003());
        if (this.loading) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        C43035 c43035 = new C43035();
        r10 = new ThemeDescription[23];
        r10[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{LoadingCell.class, ProfileSearchCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r10[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r10[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r10[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r10[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r10[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r10[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r10[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r10[9] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        r10[10] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        r10[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[12] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r10[13] = new ThemeDescription(this.listView, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        r10[14] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_namePaint, null, null, Theme.key_chats_name);
        r10[15] = new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[16] = new ThemeDescription(null, 0, null, null, null, c43035, Theme.key_avatar_backgroundRed);
        r10[17] = new ThemeDescription(null, 0, null, null, null, c43035, Theme.key_avatar_backgroundOrange);
        r10[18] = new ThemeDescription(null, 0, null, null, null, c43035, Theme.key_avatar_backgroundViolet);
        r10[19] = new ThemeDescription(null, 0, null, null, null, c43035, Theme.key_avatar_backgroundGreen);
        r10[20] = new ThemeDescription(null, 0, null, null, null, c43035, Theme.key_avatar_backgroundCyan);
        r10[21] = new ThemeDescription(null, 0, null, null, null, c43035, Theme.key_avatar_backgroundBlue);
        r10[22] = new ThemeDescription(null, 0, null, null, null, c43035, Theme.key_avatar_backgroundPink);
        return r10;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        getChats(0, 50);
        return true;
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }
}
