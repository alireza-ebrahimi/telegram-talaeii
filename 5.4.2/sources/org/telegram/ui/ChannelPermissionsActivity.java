package org.telegram.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.TL_channelAdminRights;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.RadioButtonCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell2;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class ChannelPermissionsActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int done_button = 1;
    private int addUsersRow;
    private TL_channelAdminRights adminRights = new TL_channelAdminRights();
    private int changeInfoRow;
    private int chatId;
    private int embedLinksRow;
    private int forwardRow;
    private int forwardShadowRow;
    private HeaderCell headerCell2;
    private boolean historyHidden;
    private ChatFull info;
    private LinearLayout linearLayout;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private int permissionsHeaderRow;
    private RadioButtonCell radioButtonCell3;
    private RadioButtonCell radioButtonCell4;
    private int rightsShadowRow;
    private int rowCount = 0;
    private int sendMediaRow;
    private int sendStickersRow;

    /* renamed from: org.telegram.ui.ChannelPermissionsActivity$1 */
    class C41801 extends ActionBarMenuOnItemClick {
        C41801() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ChannelPermissionsActivity.this.finishFragment();
            } else if (i == 1) {
                if (!(ChannelPermissionsActivity.this.headerCell2 == null || ChannelPermissionsActivity.this.headerCell2.getVisibility() != 0 || ChannelPermissionsActivity.this.info == null || ChannelPermissionsActivity.this.info.hidden_prehistory == ChannelPermissionsActivity.this.historyHidden)) {
                    ChannelPermissionsActivity.this.info.hidden_prehistory = ChannelPermissionsActivity.this.historyHidden;
                    MessagesController.getInstance().toogleChannelInvitesHistory(ChannelPermissionsActivity.this.chatId, ChannelPermissionsActivity.this.historyHidden);
                }
                ChannelPermissionsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelPermissionsActivity$3 */
    class C41823 implements OnItemClickListener {
        C41823() {
        }

        public void onItemClick(View view, int i) {
            boolean z = true;
            if (view instanceof TextCheckCell2) {
                TextCheckCell2 textCheckCell2 = (TextCheckCell2) view;
                if (textCheckCell2.isEnabled()) {
                    textCheckCell2.setChecked(!textCheckCell2.isChecked());
                    TL_channelAdminRights access$500;
                    if (i == ChannelPermissionsActivity.this.changeInfoRow) {
                        access$500 = ChannelPermissionsActivity.this.adminRights;
                        if (ChannelPermissionsActivity.this.adminRights.change_info) {
                            z = false;
                        }
                        access$500.change_info = z;
                    } else if (i == ChannelPermissionsActivity.this.addUsersRow) {
                        access$500 = ChannelPermissionsActivity.this.adminRights;
                        if (ChannelPermissionsActivity.this.adminRights.invite_users) {
                            z = false;
                        }
                        access$500.invite_users = z;
                    } else if (i == ChannelPermissionsActivity.this.sendMediaRow) {
                        access$500 = ChannelPermissionsActivity.this.adminRights;
                        if (ChannelPermissionsActivity.this.adminRights.ban_users) {
                            z = false;
                        }
                        access$500.ban_users = z;
                    } else if (i == ChannelPermissionsActivity.this.sendStickersRow) {
                        access$500 = ChannelPermissionsActivity.this.adminRights;
                        if (ChannelPermissionsActivity.this.adminRights.add_admins) {
                            z = false;
                        }
                        access$500.add_admins = z;
                    } else if (i == ChannelPermissionsActivity.this.embedLinksRow) {
                        access$500 = ChannelPermissionsActivity.this.adminRights;
                        if (ChannelPermissionsActivity.this.adminRights.pin_messages) {
                            z = false;
                        }
                        access$500.pin_messages = z;
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelPermissionsActivity$4 */
    class C41834 implements OnClickListener {
        C41834() {
        }

        public void onClick(View view) {
            ChannelPermissionsActivity.this.radioButtonCell3.setChecked(true, true);
            ChannelPermissionsActivity.this.radioButtonCell4.setChecked(false, true);
            ChannelPermissionsActivity.this.historyHidden = false;
        }
    }

    /* renamed from: org.telegram.ui.ChannelPermissionsActivity$5 */
    class C41845 implements OnClickListener {
        C41845() {
        }

        public void onClick(View view) {
            ChannelPermissionsActivity.this.radioButtonCell3.setChecked(false, true);
            ChannelPermissionsActivity.this.radioButtonCell4.setChecked(true, true);
            ChannelPermissionsActivity.this.historyHidden = true;
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return ChannelPermissionsActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == ChannelPermissionsActivity.this.rightsShadowRow || i == ChannelPermissionsActivity.this.forwardShadowRow) ? 2 : (i == ChannelPermissionsActivity.this.changeInfoRow || i == ChannelPermissionsActivity.this.addUsersRow || i == ChannelPermissionsActivity.this.sendMediaRow || i == ChannelPermissionsActivity.this.sendStickersRow || i == ChannelPermissionsActivity.this.embedLinksRow) ? 1 : i == ChannelPermissionsActivity.this.forwardRow ? 3 : i == ChannelPermissionsActivity.this.permissionsHeaderRow ? 0 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 1;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            int i2 = R.drawable.greydivider_bottom;
            switch (viewHolder.getItemViewType()) {
                case 2:
                    ShadowSectionCell shadowSectionCell = (ShadowSectionCell) viewHolder.itemView;
                    if (i == ChannelPermissionsActivity.this.rightsShadowRow) {
                        Context context = this.mContext;
                        if (ChannelPermissionsActivity.this.forwardShadowRow != -1) {
                            i2 = R.drawable.greydivider;
                        }
                        shadowSectionCell.setBackgroundDrawable(Theme.getThemedDrawable(context, i2, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                    shadowSectionCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View headerCell;
            switch (i) {
                case 0:
                    headerCell = new HeaderCell(this.mContext);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    headerCell = new TextCheckCell2(this.mContext);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    headerCell = new ShadowSectionCell(this.mContext);
                    break;
                default:
                    headerCell = ChannelPermissionsActivity.this.linearLayout;
                    break;
            }
            return new Holder(headerCell);
        }
    }

    public ChannelPermissionsActivity(int i) {
        this.chatId = i;
        int i2 = this.rowCount;
        this.rowCount = i2 + 1;
        this.permissionsHeaderRow = i2;
        i2 = this.rowCount;
        this.rowCount = i2 + 1;
        this.sendMediaRow = i2;
        i2 = this.rowCount;
        this.rowCount = i2 + 1;
        this.sendStickersRow = i2;
        i2 = this.rowCount;
        this.rowCount = i2 + 1;
        this.embedLinksRow = i2;
        i2 = this.rowCount;
        this.rowCount = i2 + 1;
        this.addUsersRow = i2;
        i2 = this.rowCount;
        this.rowCount = i2 + 1;
        this.changeInfoRow = i2;
        i2 = this.rowCount;
        this.rowCount = i2 + 1;
        this.rightsShadowRow = i2;
        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
        if (chat == null || !TextUtils.isEmpty(chat.username)) {
            this.forwardRow = -1;
            this.forwardShadowRow = -1;
            return;
        }
        i2 = this.rowCount;
        this.rowCount = i2 + 1;
        this.forwardRow = i2;
        i2 = this.rowCount;
        this.rowCount = i2 + 1;
        this.forwardShadowRow = i2;
    }

    public View createView(Context context) {
        boolean z = true;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C41801());
        this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.listView = new RecyclerListView(context);
        LayoutManager c41812 = new LinearLayoutManager(context, 1, false) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setLayoutManager(c41812);
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listViewAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C41823());
        this.linearLayout = new LinearLayout(context);
        this.linearLayout.setOrientation(1);
        this.linearLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.linearLayout.setLayoutParams(new LayoutParams(-1, -2));
        this.headerCell2 = new HeaderCell(context);
        this.headerCell2.setText(LocaleController.getString("ChatHistory", R.string.ChatHistory));
        this.headerCell2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.linearLayout.addView(this.headerCell2);
        this.radioButtonCell3 = new RadioButtonCell(context);
        this.radioButtonCell3.setBackgroundDrawable(Theme.getSelectorDrawable(false));
        RadioButtonCell radioButtonCell = this.radioButtonCell3;
        String string = LocaleController.getString("ChatHistoryVisible", R.string.ChatHistoryVisible);
        String string2 = LocaleController.getString("ChatHistoryVisibleInfo", R.string.ChatHistoryVisibleInfo);
        if (this.historyHidden) {
            z = false;
        }
        radioButtonCell.setTextAndValue(string, string2, z);
        this.linearLayout.addView(this.radioButtonCell3, LayoutHelper.createLinear(-1, -2));
        this.radioButtonCell3.setOnClickListener(new C41834());
        this.radioButtonCell4 = new RadioButtonCell(context);
        this.radioButtonCell4.setBackgroundDrawable(Theme.getSelectorDrawable(false));
        this.radioButtonCell4.setTextAndValue(LocaleController.getString("ChatHistoryHidden", R.string.ChatHistoryHidden), LocaleController.getString("ChatHistoryHiddenInfo", R.string.ChatHistoryHiddenInfo), this.historyHidden);
        this.linearLayout.addView(this.radioButtonCell4, LayoutHelper.createLinear(-1, -2));
        this.radioButtonCell4.setOnClickListener(new C41845());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            if (chatFull.id == this.chatId) {
                if (this.info == null) {
                    this.historyHidden = chatFull.hidden_prehistory;
                    if (this.radioButtonCell3 != null) {
                        this.radioButtonCell3.setChecked(!this.historyHidden, false);
                        this.radioButtonCell4.setChecked(this.historyHidden, false);
                    }
                }
                this.info = chatFull;
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[28];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextCheckCell2.class, HeaderCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r9[9] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[10] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r9[11] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        r9[12] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        r9[13] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        r9[14] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        r9[15] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[16] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        r9[17] = new ThemeDescription(this.linearLayout, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r9[18] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[19] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r9[20] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r9[21] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[22] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r9[23] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[24] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r9[25] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r9[26] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[27] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        return r9;
    }

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        return super.onFragmentCreate();
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
        if (this.info == null && chatFull != null) {
            this.historyHidden = chatFull.hidden_prehistory;
        }
        this.info = chatFull;
    }
}
