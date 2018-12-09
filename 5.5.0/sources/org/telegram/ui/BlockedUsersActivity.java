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
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.TLObject;
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
    class C39491 extends ActionBarMenuOnItemClick {
        C39491() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                BlockedUsersActivity.this.finishFragment();
            } else if (i == 1) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("onlyUsers", true);
                bundle.putBoolean("destroyAfterSelect", true);
                bundle.putBoolean("returnAsResult", true);
                BaseFragment contactsActivity = new ContactsActivity(bundle);
                contactsActivity.setDelegate(BlockedUsersActivity.this);
                BlockedUsersActivity.this.presentFragment(contactsActivity);
            }
        }
    }

    /* renamed from: org.telegram.ui.BlockedUsersActivity$2 */
    class C39502 implements OnItemClickListener {
        C39502() {
        }

        public void onItemClick(View view, int i) {
            if (i < MessagesController.getInstance().blockedUsers.size()) {
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", ((Integer) MessagesController.getInstance().blockedUsers.get(i)).intValue());
                BlockedUsersActivity.this.presentFragment(new ProfileActivity(bundle));
            }
        }
    }

    /* renamed from: org.telegram.ui.BlockedUsersActivity$3 */
    class C39523 implements OnItemLongClickListener {

        /* renamed from: org.telegram.ui.BlockedUsersActivity$3$1 */
        class C39511 implements OnClickListener {
            C39511() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    MessagesController.getInstance().unblockUser(BlockedUsersActivity.this.selectedUserId);
                }
            }
        }

        C39523() {
        }

        public boolean onItemClick(View view, int i) {
            if (i < MessagesController.getInstance().blockedUsers.size() && BlockedUsersActivity.this.getParentActivity() != null) {
                BlockedUsersActivity.this.selectedUserId = ((Integer) MessagesController.getInstance().blockedUsers.get(i)).intValue();
                Builder builder = new Builder(BlockedUsersActivity.this.getParentActivity());
                builder.setItems(new CharSequence[]{LocaleController.getString("Unblock", R.string.Unblock)}, new C39511());
                BlockedUsersActivity.this.showDialog(builder.create());
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.BlockedUsersActivity$4 */
    class C39534 implements ThemeDescriptionDelegate {
        C39534() {
        }

        public void didSetColor(int i) {
            int childCount = BlockedUsersActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = BlockedUsersActivity.this.listView.getChildAt(i2);
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
            return MessagesController.getInstance().blockedUsers.isEmpty() ? 0 : MessagesController.getInstance().blockedUsers.size() + 1;
        }

        public int getItemViewType(int i) {
            return i == MessagesController.getInstance().blockedUsers.size() ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 0) {
                TLObject user = MessagesController.getInstance().getUser((Integer) MessagesController.getInstance().blockedUsers.get(i));
                if (user != null) {
                    CharSequence charSequence;
                    if (user.bot) {
                        charSequence = LocaleController.getString("Bot", R.string.Bot).substring(0, 1).toUpperCase() + LocaleController.getString("Bot", R.string.Bot).substring(1);
                    } else {
                        Object string = (user.phone == null || user.phone.length() == 0) ? LocaleController.getString("NumberUnknown", R.string.NumberUnknown) : C2488b.a().e("+" + user.phone);
                    }
                    ((UserCell) viewHolder.itemView).setData(user, null, charSequence, 0);
                }
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View userCell;
            switch (i) {
                case 0:
                    userCell = new UserCell(this.mContext, 1, 0, false);
                    break;
                default:
                    userCell = new TextInfoCell(this.mContext);
                    ((TextInfoCell) userCell).setText(LocaleController.getString("UnblockText", R.string.UnblockText));
                    break;
            }
            return new Holder(userCell);
        }
    }

    private void updateVisibleRows(int i) {
        if (this.listView != null) {
            int childCount = this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.listView.getChildAt(i2);
                if (childAt instanceof UserCell) {
                    ((UserCell) childAt).update(i);
                }
            }
        }
    }

    public View createView(Context context) {
        int i = 1;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("BlockedUsers", R.string.BlockedUsers));
        this.actionBar.setActionBarMenuOnItemClick(new C39491());
        this.actionBar.createMenu().addItem(1, (int) R.drawable.plus);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
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
        this.listView.setOnItemClickListener(new C39502());
        this.listView.setOnItemLongClickListener(new C39523());
        if (MessagesController.getInstance().loadingBlockedUsers) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.updateInterfaces) {
            int intValue = ((Integer) objArr[0]).intValue();
            if ((intValue & 2) != 0 || (intValue & 1) != 0) {
                updateVisibleRows(intValue);
            }
        } else if (i == NotificationCenter.blockedUsersDidLoaded) {
            this.emptyView.showTextView();
            if (this.listViewAdapter != null) {
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void didSelectContact(User user, String str, ContactsActivity contactsActivity) {
        if (user != null) {
            MessagesController.getInstance().blockUser(user.id);
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        C39534 c39534 = new C39534();
        r10 = new ThemeDescription[20];
        r10[9] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText5);
        r10[10] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[11] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, (ThemeDescriptionDelegate) c39534, Theme.key_windowBackgroundWhiteGrayText);
        r10[12] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[13] = new ThemeDescription(null, 0, null, null, null, c39534, Theme.key_avatar_backgroundRed);
        r10[14] = new ThemeDescription(null, 0, null, null, null, c39534, Theme.key_avatar_backgroundOrange);
        r10[15] = new ThemeDescription(null, 0, null, null, null, c39534, Theme.key_avatar_backgroundViolet);
        r10[16] = new ThemeDescription(null, 0, null, null, null, c39534, Theme.key_avatar_backgroundGreen);
        r10[17] = new ThemeDescription(null, 0, null, null, null, c39534, Theme.key_avatar_backgroundCyan);
        r10[18] = new ThemeDescription(null, 0, null, null, null, c39534, Theme.key_avatar_backgroundBlue);
        r10[19] = new ThemeDescription(null, 0, null, null, null, c39534, Theme.key_avatar_backgroundPink);
        return r10;
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

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }
}
