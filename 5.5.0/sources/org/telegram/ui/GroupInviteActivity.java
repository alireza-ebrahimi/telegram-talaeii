package org.telegram.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_exportChatInvite;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ExportedChatInvite;
import org.telegram.tgnet.TLRPC.TL_channels_exportInvite;
import org.telegram.tgnet.TLRPC.TL_chatInviteExported;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.TextBlockCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class GroupInviteActivity extends BaseFragment implements NotificationCenterDelegate {
    private int chat_id;
    private int copyLinkRow;
    private EmptyTextProgressView emptyView;
    private ExportedChatInvite invite;
    private int linkInfoRow;
    private int linkRow;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private boolean loading;
    private int revokeLinkRow;
    private int rowCount;
    private int shadowRow;
    private int shareLinkRow;

    /* renamed from: org.telegram.ui.GroupInviteActivity$1 */
    class C47741 extends ActionBarMenuOnItemClick {
        C47741() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                GroupInviteActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.GroupInviteActivity$2 */
    class C47762 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.GroupInviteActivity$2$1 */
        class C47751 implements OnClickListener {
            C47751() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                GroupInviteActivity.this.generateLink(true);
            }
        }

        C47762() {
        }

        public void onItemClick(View view, int i) {
            if (GroupInviteActivity.this.getParentActivity() != null) {
                if (i == GroupInviteActivity.this.copyLinkRow || i == GroupInviteActivity.this.linkRow) {
                    if (GroupInviteActivity.this.invite != null) {
                        try {
                            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", GroupInviteActivity.this.invite.link));
                            Toast.makeText(GroupInviteActivity.this.getParentActivity(), LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                } else if (i == GroupInviteActivity.this.shareLinkRow) {
                    if (GroupInviteActivity.this.invite != null) {
                        try {
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("text/plain");
                            intent.putExtra("android.intent.extra.TEXT", GroupInviteActivity.this.invite.link);
                            GroupInviteActivity.this.getParentActivity().startActivityForResult(Intent.createChooser(intent, LocaleController.getString("InviteToGroupByLink", R.string.InviteToGroupByLink)), ChatActivity.startAllServices);
                        } catch (Throwable e2) {
                            FileLog.e(e2);
                        }
                    }
                } else if (i == GroupInviteActivity.this.revokeLinkRow) {
                    Builder builder = new Builder(GroupInviteActivity.this.getParentActivity());
                    builder.setMessage(LocaleController.getString("RevokeAlert", R.string.RevokeAlert));
                    builder.setTitle(LocaleController.getString("RevokeLink", R.string.RevokeLink));
                    builder.setPositiveButton(LocaleController.getString("RevokeButton", R.string.RevokeButton), new C47751());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    GroupInviteActivity.this.showDialog(builder.create());
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
            return GroupInviteActivity.this.loading ? 0 : GroupInviteActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == GroupInviteActivity.this.copyLinkRow || i == GroupInviteActivity.this.shareLinkRow || i == GroupInviteActivity.this.revokeLinkRow) ? 0 : (i == GroupInviteActivity.this.shadowRow || i == GroupInviteActivity.this.linkInfoRow) ? 1 : i == GroupInviteActivity.this.linkRow ? 2 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == GroupInviteActivity.this.revokeLinkRow || adapterPosition == GroupInviteActivity.this.copyLinkRow || adapterPosition == GroupInviteActivity.this.shareLinkRow || adapterPosition == GroupInviteActivity.this.linkRow;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    if (i == GroupInviteActivity.this.copyLinkRow) {
                        textSettingsCell.setText(LocaleController.getString("CopyLink", R.string.CopyLink), true);
                        return;
                    } else if (i == GroupInviteActivity.this.shareLinkRow) {
                        textSettingsCell.setText(LocaleController.getString("ShareLink", R.string.ShareLink), false);
                        return;
                    } else if (i == GroupInviteActivity.this.revokeLinkRow) {
                        textSettingsCell.setText(LocaleController.getString("RevokeLink", R.string.RevokeLink), true);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == GroupInviteActivity.this.shadowRow) {
                        textInfoPrivacyCell.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == GroupInviteActivity.this.linkInfoRow) {
                        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(GroupInviteActivity.this.chat_id));
                        if (!ChatObject.isChannel(chat) || chat.megagroup) {
                            textInfoPrivacyCell.setText(LocaleController.getString("LinkInfo", R.string.LinkInfo));
                        } else {
                            textInfoPrivacyCell.setText(LocaleController.getString("ChannelLinkInfo", R.string.ChannelLinkInfo));
                        }
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    ((TextBlockCell) viewHolder.itemView).setText(GroupInviteActivity.this.invite != null ? GroupInviteActivity.this.invite.link : "error", false);
                    return;
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
                case 1:
                    textSettingsCell = new TextInfoPrivacyCell(this.mContext);
                    break;
                default:
                    textSettingsCell = new TextBlockCell(this.mContext);
                    textSettingsCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new Holder(textSettingsCell);
        }
    }

    public GroupInviteActivity(int i) {
        this.chat_id = i;
    }

    private void generateLink(final boolean z) {
        TLObject tL_channels_exportInvite;
        this.loading = true;
        if (ChatObject.isChannel(this.chat_id)) {
            tL_channels_exportInvite = new TL_channels_exportInvite();
            tL_channels_exportInvite.channel = MessagesController.getInputChannel(this.chat_id);
        } else {
            tL_channels_exportInvite = new TLRPC$TL_messages_exportChatInvite();
            tL_channels_exportInvite.chat_id = this.chat_id;
        }
        ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tL_channels_exportInvite, new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            GroupInviteActivity.this.invite = (ExportedChatInvite) tLObject;
                            if (z) {
                                if (GroupInviteActivity.this.getParentActivity() != null) {
                                    Builder builder = new Builder(GroupInviteActivity.this.getParentActivity());
                                    builder.setMessage(LocaleController.getString("RevokeAlertNewLink", R.string.RevokeAlertNewLink));
                                    builder.setTitle(LocaleController.getString("RevokeLink", R.string.RevokeLink));
                                    builder.setNegativeButton(LocaleController.getString("OK", R.string.OK), null);
                                    GroupInviteActivity.this.showDialog(builder.create());
                                } else {
                                    return;
                                }
                            }
                        }
                        GroupInviteActivity.this.loading = false;
                        GroupInviteActivity.this.listAdapter.notifyDataSetChanged();
                    }
                });
            }
        }), this.classGuid);
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("InviteLink", R.string.InviteLink));
        this.actionBar.setActionBarMenuOnItemClick(new C47741());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.showProgress();
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView = new RecyclerListView(context);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setEmptyView(this.emptyView);
        this.listView.setVerticalScrollBarEnabled(false);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C47762());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            int intValue = ((Integer) objArr[1]).intValue();
            if (chatFull.id == this.chat_id && intValue == this.classGuid) {
                this.invite = MessagesController.getInstance().getExportedInvite(this.chat_id);
                if (this.invite instanceof TL_chatInviteExported) {
                    this.loading = false;
                    if (this.listAdapter != null) {
                        this.listAdapter.notifyDataSetChanged();
                        return;
                    }
                    return;
                }
                generateLink(false);
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[14];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class, TextBlockCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextBlockCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        return themeDescriptionArr;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        MessagesController.getInstance().loadFullChat(this.chat_id, this.classGuid, true);
        this.loading = true;
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.linkRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.linkInfoRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.copyLinkRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.revokeLinkRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.shareLinkRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.shadowRow = i;
        return true;
    }

    public void onFragmentDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
