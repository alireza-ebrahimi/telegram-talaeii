package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.ir.talaeii.R;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.TextInfoCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.ContactsActivity.ContactsActivityDelegate;

public class BlockedUsersActivity extends BaseFragment implements NotificationCenterDelegate, ContactsActivityDelegate {
    private static final int block_user = 1;
    private EmptyTextProgressView emptyView;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private int selectedUserId;

    /* renamed from: org.telegram.ui.BlockedUsersActivity$1 */
    class C21111 extends ActionBarMenuOnItemClick {
        C21111() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                BlockedUsersActivity.this.finishFragment();
            } else if (id == 1) {
                Bundle args = new Bundle();
                args.putBoolean("onlyUsers", true);
                args.putBoolean("destroyAfterSelect", true);
                args.putBoolean("returnAsResult", true);
                ContactsActivity fragment = new ContactsActivity(args);
                fragment.setDelegate(BlockedUsersActivity.this);
                BlockedUsersActivity.this.presentFragment(fragment);
            }
        }
    }

    /* renamed from: org.telegram.ui.BlockedUsersActivity$2 */
    class C21122 implements OnItemClickListener {
        C21122() {
        }

        public void onItemClick(View view, int position) {
            if (position < MessagesController.getInstance().blockedUsers.size()) {
                Bundle args = new Bundle();
                args.putInt("user_id", ((Integer) MessagesController.getInstance().blockedUsers.get(position)).intValue());
                BlockedUsersActivity.this.presentFragment(new ProfileActivity(args));
            }
        }
    }

    /* renamed from: org.telegram.ui.BlockedUsersActivity$3 */
    class C21143 implements OnItemLongClickListener {

        /* renamed from: org.telegram.ui.BlockedUsersActivity$3$1 */
        class C21131 implements OnClickListener {
            C21131() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    MessagesController.getInstance().unblockUser(BlockedUsersActivity.this.selectedUserId);
                }
            }
        }

        C21143() {
        }

        public boolean onItemClick(View view, int position) {
            if (position < MessagesController.getInstance().blockedUsers.size() && BlockedUsersActivity.this.getParentActivity() != null) {
                BlockedUsersActivity.this.selectedUserId = ((Integer) MessagesController.getInstance().blockedUsers.get(position)).intValue();
                Builder builder = new Builder(BlockedUsersActivity.this.getParentActivity());
                builder.setItems(new CharSequence[]{LocaleController.getString("Unblock", R.string.Unblock)}, new C21131());
                BlockedUsersActivity.this.showDialog(builder.create());
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.BlockedUsersActivity$4 */
    class C21154 implements ThemeDescriptionDelegate {
        C21154() {
        }

        public void didSetColor(int color) {
            int count = BlockedUsersActivity.this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = BlockedUsersActivity.this.listView.getChildAt(a);
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

        public int getItemCount() {
            if (MessagesController.getInstance().blockedUsers.isEmpty()) {
                return 0;
            }
            return MessagesController.getInstance().blockedUsers.size() + 1;
        }

        public boolean isEnabled(ViewHolder holder) {
            return holder.getItemViewType() == 0;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new UserCell(this.mContext, 1, 0, false);
                    break;
                default:
                    view = new TextInfoCell(this.mContext);
                    ((TextInfoCell) view).setText(LocaleController.getString("UnblockText", R.string.UnblockText));
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder.getItemViewType() == 0) {
                User user = MessagesController.getInstance().getUser((Integer) MessagesController.getInstance().blockedUsers.get(position));
                if (user != null) {
                    String number;
                    if (user.bot) {
                        number = LocaleController.getString("Bot", R.string.Bot).substring(0, 1).toUpperCase() + LocaleController.getString("Bot", R.string.Bot).substring(1);
                    } else if (user.phone == null || user.phone.length() == 0) {
                        number = LocaleController.getString("NumberUnknown", R.string.NumberUnknown);
                    } else {
                        number = PhoneFormat.getInstance().format("+" + user.phone);
                    }
                    ((UserCell) holder.itemView).setData(user, null, number, 0);
                }
            }
        }

        public int getItemViewType(int i) {
            if (i == MessagesController.getInstance().blockedUsers.size()) {
                return 1;
            }
            return 0;
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.blockedUsersDidLoaded);
        MessagesController.getInstance().getBlockedUsers(false);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.blockedUsersDidLoaded);
    }

    public View createView(Context context) {
        int i = 1;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("BlockedUsers", R.string.BlockedUsers));
        this.actionBar.setActionBarMenuOnItemClick(new C21111());
        this.actionBar.createMenu().addItem(1, (int) R.drawable.plus);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.setText(LocaleController.getString("NoBlocked", R.string.NoBlocked));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.emptyView);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
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
        this.listView.setOnItemClickListener(new C21122());
        this.listView.setOnItemLongClickListener(new C21143());
        if (MessagesController.getInstance().loadingBlockedUsers) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        return this.fragmentView;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.updateInterfaces) {
            int mask = ((Integer) args[0]).intValue();
            if ((mask & 2) != 0 || (mask & 1) != 0) {
                updateVisibleRows(mask);
            }
        } else if (id == NotificationCenter.blockedUsersDidLoaded) {
            this.emptyView.showTextView();
            if (this.listViewAdapter != null) {
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateVisibleRows(int mask) {
        if (this.listView != null) {
            int count = this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = this.listView.getChildAt(a);
                if (child instanceof UserCell) {
                    ((UserCell) child).update(mask);
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

    public void didSelectContact(User user, String param, ContactsActivity activity) {
        if (user != null) {
            MessagesController.getInstance().blockUser(user.id);
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new C21154();
        r10 = new ThemeDescription[20];
        r10[9] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText5);
        r10[10] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[11] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteGrayText);
        r10[12] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[13] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        r10[14] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        r10[15] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        r10[16] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        r10[17] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        r10[18] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        r10[19] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
        return r10;
    }
}
