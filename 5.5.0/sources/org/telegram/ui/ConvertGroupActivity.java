package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class ConvertGroupActivity extends BaseFragment implements NotificationCenterDelegate {
    private int chat_id;
    private int convertDetailRow;
    private int convertInfoRow;
    private int convertRow;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int rowCount;

    /* renamed from: org.telegram.ui.ConvertGroupActivity$1 */
    class C46841 extends ActionBarMenuOnItemClick {
        C46841() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ConvertGroupActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ConvertGroupActivity$2 */
    class C46862 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.ConvertGroupActivity$2$1 */
        class C46851 implements OnClickListener {
            C46851() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                MessagesController.getInstance().convertToMegaGroup(ConvertGroupActivity.this.getParentActivity(), ConvertGroupActivity.this.chat_id);
            }
        }

        C46862() {
        }

        public void onItemClick(View view, int i) {
            if (i == ConvertGroupActivity.this.convertRow) {
                Builder builder = new Builder(ConvertGroupActivity.this.getParentActivity());
                builder.setMessage(LocaleController.getString("ConvertGroupAlert", R.string.ConvertGroupAlert));
                builder.setTitle(LocaleController.getString("ConvertGroupAlertWarning", R.string.ConvertGroupAlertWarning));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C46851());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                ConvertGroupActivity.this.showDialog(builder.create());
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return ConvertGroupActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return i == ConvertGroupActivity.this.convertRow ? 0 : (i == ConvertGroupActivity.this.convertInfoRow || i == ConvertGroupActivity.this.convertDetailRow) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getAdapterPosition() == ConvertGroupActivity.this.convertRow;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    if (i == ConvertGroupActivity.this.convertRow) {
                        textSettingsCell.setText(LocaleController.getString("ConvertGroup", R.string.ConvertGroup), false);
                        return;
                    }
                    return;
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == ConvertGroupActivity.this.convertInfoRow) {
                        textInfoPrivacyCell.setText(AndroidUtilities.replaceTags(LocaleController.getString("ConvertGroupInfo2", R.string.ConvertGroupInfo2)));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == ConvertGroupActivity.this.convertDetailRow) {
                        textInfoPrivacyCell.setText(AndroidUtilities.replaceTags(LocaleController.getString("ConvertGroupInfo3", R.string.ConvertGroupInfo3)));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View textSettingsCell;
            switch (i) {
                case 0:
                    textSettingsCell = new TextSettingsCell(this.mContext);
                    textSettingsCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    textSettingsCell = new TextInfoPrivacyCell(this.mContext);
                    break;
            }
            return new Holder(textSettingsCell);
        }
    }

    public ConvertGroupActivity(Bundle bundle) {
        super(bundle);
        this.chat_id = bundle.getInt("chat_id");
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("ConvertGroup", R.string.ConvertGroup));
        this.actionBar.setActionBarMenuOnItemClick(new C46841());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C46862());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.closeChats) {
            removeSelfFromStack();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[11];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[10] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r9;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.convertInfoRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.convertRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.convertDetailRow = i;
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
