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
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$TL_channelAdminRights;
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
    private TLRPC$TL_channelAdminRights adminRights = new TLRPC$TL_channelAdminRights();
    private int changeInfoRow;
    private int chatId;
    private int embedLinksRow;
    private int forwardRow;
    private int forwardShadowRow;
    private HeaderCell headerCell2;
    private boolean historyHidden;
    private TLRPC$ChatFull info;
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
    class C23421 extends ActionBarMenuOnItemClick {
        C23421() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                ChannelPermissionsActivity.this.finishFragment();
            } else if (id == 1) {
                if (!(ChannelPermissionsActivity.this.headerCell2 == null || ChannelPermissionsActivity.this.headerCell2.getVisibility() != 0 || ChannelPermissionsActivity.this.info == null || ChannelPermissionsActivity.this.info.hidden_prehistory == ChannelPermissionsActivity.this.historyHidden)) {
                    ChannelPermissionsActivity.this.info.hidden_prehistory = ChannelPermissionsActivity.this.historyHidden;
                    MessagesController.getInstance().toogleChannelInvitesHistory(ChannelPermissionsActivity.this.chatId, ChannelPermissionsActivity.this.historyHidden);
                }
                ChannelPermissionsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelPermissionsActivity$3 */
    class C23443 implements OnItemClickListener {
        C23443() {
        }

        public void onItemClick(View view, int position) {
            boolean z = true;
            if (view instanceof TextCheckCell2) {
                TextCheckCell2 checkCell = (TextCheckCell2) view;
                if (checkCell.isEnabled()) {
                    boolean z2;
                    if (checkCell.isChecked()) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    checkCell.setChecked(z2);
                    TLRPC$TL_channelAdminRights access$500;
                    if (position == ChannelPermissionsActivity.this.changeInfoRow) {
                        access$500 = ChannelPermissionsActivity.this.adminRights;
                        if (ChannelPermissionsActivity.this.adminRights.change_info) {
                            z = false;
                        }
                        access$500.change_info = z;
                    } else if (position == ChannelPermissionsActivity.this.addUsersRow) {
                        access$500 = ChannelPermissionsActivity.this.adminRights;
                        if (ChannelPermissionsActivity.this.adminRights.invite_users) {
                            z = false;
                        }
                        access$500.invite_users = z;
                    } else if (position == ChannelPermissionsActivity.this.sendMediaRow) {
                        access$500 = ChannelPermissionsActivity.this.adminRights;
                        if (ChannelPermissionsActivity.this.adminRights.ban_users) {
                            z = false;
                        }
                        access$500.ban_users = z;
                    } else if (position == ChannelPermissionsActivity.this.sendStickersRow) {
                        access$500 = ChannelPermissionsActivity.this.adminRights;
                        if (ChannelPermissionsActivity.this.adminRights.add_admins) {
                            z = false;
                        }
                        access$500.add_admins = z;
                    } else if (position == ChannelPermissionsActivity.this.embedLinksRow) {
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
    class C23454 implements OnClickListener {
        C23454() {
        }

        public void onClick(View v) {
            ChannelPermissionsActivity.this.radioButtonCell3.setChecked(true, true);
            ChannelPermissionsActivity.this.radioButtonCell4.setChecked(false, true);
            ChannelPermissionsActivity.this.historyHidden = false;
        }
    }

    /* renamed from: org.telegram.ui.ChannelPermissionsActivity$5 */
    class C23465 implements OnClickListener {
        C23465() {
        }

        public void onClick(View v) {
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

        public boolean isEnabled(ViewHolder holder) {
            if (holder.getItemViewType() == 1) {
                return true;
            }
            return false;
        }

        public int getItemCount() {
            return ChannelPermissionsActivity.this.rowCount;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new HeaderCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new TextCheckCell2(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    view = new ShadowSectionCell(this.mContext);
                    break;
                default:
                    view = ChannelPermissionsActivity.this.linearLayout;
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            int i = R.drawable.greydivider_bottom;
            switch (holder.getItemViewType()) {
                case 2:
                    ShadowSectionCell shadowCell = holder.itemView;
                    if (position == ChannelPermissionsActivity.this.rightsShadowRow) {
                        Context context = this.mContext;
                        if (ChannelPermissionsActivity.this.forwardShadowRow != -1) {
                            i = R.drawable.greydivider;
                        }
                        shadowCell.setBackgroundDrawable(Theme.getThemedDrawable(context, i, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                    shadowCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    return;
                default:
                    return;
            }
        }

        public int getItemViewType(int position) {
            if (position == ChannelPermissionsActivity.this.rightsShadowRow || position == ChannelPermissionsActivity.this.forwardShadowRow) {
                return 2;
            }
            if (position == ChannelPermissionsActivity.this.changeInfoRow || position == ChannelPermissionsActivity.this.addUsersRow || position == ChannelPermissionsActivity.this.sendMediaRow || position == ChannelPermissionsActivity.this.sendStickersRow || position == ChannelPermissionsActivity.this.embedLinksRow) {
                return 1;
            }
            if (position == ChannelPermissionsActivity.this.forwardRow) {
                return 3;
            }
            return position == ChannelPermissionsActivity.this.permissionsHeaderRow ? 0 : 0;
        }
    }

    public ChannelPermissionsActivity(int channelId) {
        this.chatId = channelId;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.permissionsHeaderRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.sendMediaRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.sendStickersRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.embedLinksRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.addUsersRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.changeInfoRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.rightsShadowRow = i;
        TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
        if (chat == null || !TextUtils.isEmpty(chat.username)) {
            this.forwardRow = -1;
            this.forwardShadowRow = -1;
            return;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.forwardRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.forwardShadowRow = i;
    }

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
    }

    public View createView(Context context) {
        boolean z = true;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C23421());
        this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = this.fragmentView;
        this.listView = new RecyclerListView(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setLayoutManager(linearLayoutManager);
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listViewAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C23443());
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
        this.radioButtonCell3.setOnClickListener(new C23454());
        this.radioButtonCell4 = new RadioButtonCell(context);
        this.radioButtonCell4.setBackgroundDrawable(Theme.getSelectorDrawable(false));
        this.radioButtonCell4.setTextAndValue(LocaleController.getString("ChatHistoryHidden", R.string.ChatHistoryHidden), LocaleController.getString("ChatHistoryHiddenInfo", R.string.ChatHistoryHiddenInfo), this.historyHidden);
        this.linearLayout.addView(this.radioButtonCell4, LayoutHelper.createLinear(-1, -2));
        this.radioButtonCell4.setOnClickListener(new C23465());
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.chatInfoDidLoaded) {
            TLRPC$ChatFull chatFull = args[0];
            if (chatFull.id == this.chatId) {
                if (this.info == null) {
                    this.historyHidden = chatFull.hidden_prehistory;
                    if (this.radioButtonCell3 != null) {
                        boolean z;
                        RadioButtonCell radioButtonCell = this.radioButtonCell3;
                        if (this.historyHidden) {
                            z = false;
                        } else {
                            z = true;
                        }
                        radioButtonCell.setChecked(z, false);
                        this.radioButtonCell4.setChecked(this.historyHidden, false);
                    }
                }
                this.info = chatFull;
            }
        }
    }

    public void setInfo(TLRPC$ChatFull chatFull) {
        if (this.info == null && chatFull != null) {
            this.historyHidden = chatFull.hidden_prehistory;
        }
        this.info = chatFull;
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
}
