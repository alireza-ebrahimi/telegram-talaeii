package org.telegram.customization.Activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.ir.talaeii.R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.CustomCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import utils.app.AppPreferences;

public class SelectCategoryActivity extends BaseFragment {
    private volatile boolean canceled = false;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    int rowAll;
    private int rowCount;
    int rowNotifOff;
    int rowNotifOn;
    int rowUnread;

    /* renamed from: org.telegram.customization.Activities.SelectCategoryActivity$1 */
    class C10911 extends ActionBarMenuOnItemClick {
        C10911() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                SelectCategoryActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.SelectCategoryActivity$2 */
    class C10922 implements OnItemClickListener {
        C10922() {
        }

        public void onItemClick(View view, int position) {
            if (SelectCategoryActivity.this.getParentActivity() != null) {
                if (position == SelectCategoryActivity.this.rowAll) {
                    AppPreferences.setSubTitle(SelectCategoryActivity.this.getParentActivity(), "");
                    AppPreferences.setSubTitleType(SelectCategoryActivity.this.getParentActivity(), 1);
                } else if (position == SelectCategoryActivity.this.rowNotifOn) {
                    AppPreferences.setSubTitle(SelectCategoryActivity.this.getParentActivity(), LocaleController.getString("un_silent_chats", R.string.un_silent_chats));
                    AppPreferences.setSubTitleType(SelectCategoryActivity.this.getParentActivity(), 4);
                } else if (position == SelectCategoryActivity.this.rowNotifOff) {
                    AppPreferences.setSubTitle(SelectCategoryActivity.this.getParentActivity(), LocaleController.getString("silent_chats", R.string.silent_chats));
                    AppPreferences.setSubTitleType(SelectCategoryActivity.this.getParentActivity(), 3);
                } else if (position == SelectCategoryActivity.this.rowUnread) {
                    AppPreferences.setSubTitle(SelectCategoryActivity.this.getParentActivity(), LocaleController.getString("unreadChats", R.string.unreadChats));
                    AppPreferences.setSubTitleType(SelectCategoryActivity.this.getParentActivity(), 2);
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(-1)});
                SelectCategoryActivity.this.finishFragment(true);
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
            return true;
        }

        public int getItemCount() {
            return SelectCategoryActivity.this.rowCount;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new CustomCell(this.mContext, 0, 0, false);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    view = new CustomCell(this.mContext, 0, 0, false);
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    CustomCell textCell = holder.itemView;
                    User user;
                    if (position == SelectCategoryActivity.this.rowAll) {
                        user = new User();
                        user.first_name = LocaleController.getString("All", R.string.All);
                        textCell.setData(user, LocaleController.getString("All", R.string.All), LocaleController.getString("AllDialogs", R.string.AllDialogs), R.drawable.approval);
                        return;
                    } else if (position == SelectCategoryActivity.this.rowUnread) {
                        user = new User();
                        user.first_name = LocaleController.getString("unreadChats", R.string.unreadChats);
                        textCell.setData(user, LocaleController.getString("unreadChats", R.string.unreadChats), LocaleController.getString("unreadChats1", R.string.unreadChats1), R.drawable.approval);
                        return;
                    } else if (position == SelectCategoryActivity.this.rowNotifOn) {
                        user = new User();
                        user.first_name = LocaleController.getString("notifOn", R.string.notifOn);
                        textCell.setData(user, LocaleController.getString("notifOn", R.string.notifOn), LocaleController.getString("notifOnChats", R.string.notifOnChats), R.drawable.approval);
                        return;
                    } else if (position == SelectCategoryActivity.this.rowNotifOff) {
                        user = new User();
                        user.first_name = LocaleController.getString("mutedChats", R.string.mutedChats);
                        textCell.setData(user, LocaleController.getString("mutedChats", R.string.mutedChats), LocaleController.getString("mutedChats", R.string.mutedChats1), R.drawable.approval);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public int getItemViewType(int i) {
            return 0;
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.rowAll = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.rowUnread = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.rowNotifOff = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.rowNotifOn = i;
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("CategoryDialogs", R.string.CategoryDialogs));
        this.actionBar.setActionBarMenuOnItemClick(new C10911());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C10922());
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[12];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[9] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r9[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[11] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r9;
    }
}
