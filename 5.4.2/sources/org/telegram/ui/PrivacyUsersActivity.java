package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.telegram.ui.GroupCreateActivity.GroupCreateActivityDelegate;

public class PrivacyUsersActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int block_user = 1;
    private PrivacyActivityDelegate delegate;
    private EmptyTextProgressView emptyView;
    private boolean isAlwaysShare;
    private boolean isGroup;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private int selectedUserId;
    private ArrayList<Integer> uidArray;

    public interface PrivacyActivityDelegate {
        void didUpdatedUserList(ArrayList<Integer> arrayList, boolean z);
    }

    /* renamed from: org.telegram.ui.PrivacyUsersActivity$1 */
    class C51071 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.PrivacyUsersActivity$1$1 */
        class C51061 implements GroupCreateActivityDelegate {
            C51061() {
            }

            public void didSelectUsers(ArrayList<Integer> arrayList) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    if (!PrivacyUsersActivity.this.uidArray.contains(num)) {
                        PrivacyUsersActivity.this.uidArray.add(num);
                    }
                }
                PrivacyUsersActivity.this.listViewAdapter.notifyDataSetChanged();
                if (PrivacyUsersActivity.this.delegate != null) {
                    PrivacyUsersActivity.this.delegate.didUpdatedUserList(PrivacyUsersActivity.this.uidArray, true);
                }
            }
        }

        C51071() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                PrivacyUsersActivity.this.finishFragment();
            } else if (i == 1) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(PrivacyUsersActivity.this.isAlwaysShare ? "isAlwaysShare" : "isNeverShare", true);
                bundle.putBoolean("isGroup", PrivacyUsersActivity.this.isGroup);
                BaseFragment groupCreateActivity = new GroupCreateActivity(bundle);
                groupCreateActivity.setDelegate(new C51061());
                PrivacyUsersActivity.this.presentFragment(groupCreateActivity);
            }
        }
    }

    /* renamed from: org.telegram.ui.PrivacyUsersActivity$2 */
    class C51082 implements OnItemClickListener {
        C51082() {
        }

        public void onItemClick(View view, int i) {
            if (i < PrivacyUsersActivity.this.uidArray.size()) {
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", ((Integer) PrivacyUsersActivity.this.uidArray.get(i)).intValue());
                PrivacyUsersActivity.this.presentFragment(new ProfileActivity(bundle));
            }
        }
    }

    /* renamed from: org.telegram.ui.PrivacyUsersActivity$3 */
    class C51103 implements OnItemLongClickListener {

        /* renamed from: org.telegram.ui.PrivacyUsersActivity$3$1 */
        class C51091 implements OnClickListener {
            C51091() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    PrivacyUsersActivity.this.uidArray.remove(Integer.valueOf(PrivacyUsersActivity.this.selectedUserId));
                    PrivacyUsersActivity.this.listViewAdapter.notifyDataSetChanged();
                    if (PrivacyUsersActivity.this.delegate != null) {
                        PrivacyUsersActivity.this.delegate.didUpdatedUserList(PrivacyUsersActivity.this.uidArray, false);
                    }
                }
            }
        }

        C51103() {
        }

        public boolean onItemClick(View view, int i) {
            if (i < 0 || i >= PrivacyUsersActivity.this.uidArray.size() || PrivacyUsersActivity.this.getParentActivity() == null) {
                return false;
            }
            PrivacyUsersActivity.this.selectedUserId = ((Integer) PrivacyUsersActivity.this.uidArray.get(i)).intValue();
            Builder builder = new Builder(PrivacyUsersActivity.this.getParentActivity());
            builder.setItems(new CharSequence[]{LocaleController.getString("Delete", R.string.Delete)}, new C51091());
            PrivacyUsersActivity.this.showDialog(builder.create());
            return true;
        }
    }

    /* renamed from: org.telegram.ui.PrivacyUsersActivity$4 */
    class C51114 implements ThemeDescriptionDelegate {
        C51114() {
        }

        public void didSetColor(int i) {
            int childCount = PrivacyUsersActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = PrivacyUsersActivity.this.listView.getChildAt(i2);
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
            return PrivacyUsersActivity.this.uidArray.isEmpty() ? 0 : PrivacyUsersActivity.this.uidArray.size() + 1;
        }

        public int getItemViewType(int i) {
            return i == PrivacyUsersActivity.this.uidArray.size() ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getAdapterPosition() != PrivacyUsersActivity.this.uidArray.size();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 0) {
                TLObject user = MessagesController.getInstance().getUser((Integer) PrivacyUsersActivity.this.uidArray.get(i));
                UserCell userCell = (UserCell) viewHolder.itemView;
                CharSequence string = (user.phone == null || user.phone.length() == 0) ? LocaleController.getString("NumberUnknown", R.string.NumberUnknown) : C2488b.a().e("+" + user.phone);
                userCell.setData(user, null, string, 0);
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
                    ((TextInfoCell) userCell).setText(LocaleController.getString("RemoveFromListText", R.string.RemoveFromListText));
                    break;
            }
            return new Holder(userCell);
        }
    }

    public PrivacyUsersActivity(ArrayList<Integer> arrayList, boolean z, boolean z2) {
        this.uidArray = arrayList;
        this.isAlwaysShare = z2;
        this.isGroup = z;
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
        if (this.isGroup) {
            if (this.isAlwaysShare) {
                this.actionBar.setTitle(LocaleController.getString("AlwaysAllow", R.string.AlwaysAllow));
            } else {
                this.actionBar.setTitle(LocaleController.getString("NeverAllow", R.string.NeverAllow));
            }
        } else if (this.isAlwaysShare) {
            this.actionBar.setTitle(LocaleController.getString("AlwaysShareWithTitle", R.string.AlwaysShareWithTitle));
        } else {
            this.actionBar.setTitle(LocaleController.getString("NeverShareWithTitle", R.string.NeverShareWithTitle));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C51071());
        this.actionBar.createMenu().addItem(1, (int) R.drawable.plus);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.showTextView();
        this.emptyView.setText(LocaleController.getString("NoContacts", R.string.NoContacts));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.emptyView);
        this.listView.setVerticalScrollBarEnabled(false);
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
        this.listView.setOnItemClickListener(new C51082());
        this.listView.setOnItemLongClickListener(new C51103());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.updateInterfaces) {
            int intValue = ((Integer) objArr[0]).intValue();
            if ((intValue & 2) != 0 || (intValue & 1) != 0) {
                updateVisibleRows(intValue);
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        C51114 c51114 = new C51114();
        r10 = new ThemeDescription[19];
        r10[8] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText5);
        r10[9] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[10] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, (ThemeDescriptionDelegate) c51114, Theme.key_windowBackgroundWhiteGrayText);
        r10[11] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[12] = new ThemeDescription(null, 0, null, null, null, c51114, Theme.key_avatar_backgroundRed);
        r10[13] = new ThemeDescription(null, 0, null, null, null, c51114, Theme.key_avatar_backgroundOrange);
        r10[14] = new ThemeDescription(null, 0, null, null, null, c51114, Theme.key_avatar_backgroundViolet);
        r10[15] = new ThemeDescription(null, 0, null, null, null, c51114, Theme.key_avatar_backgroundGreen);
        r10[16] = new ThemeDescription(null, 0, null, null, null, c51114, Theme.key_avatar_backgroundCyan);
        r10[17] = new ThemeDescription(null, 0, null, null, null, c51114, Theme.key_avatar_backgroundBlue);
        r10[18] = new ThemeDescription(null, 0, null, null, null, c51114, Theme.key_avatar_backgroundPink);
        return r10;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    public void setDelegate(PrivacyActivityDelegate privacyActivityDelegate) {
        this.delegate = privacyActivityDelegate;
    }
}
