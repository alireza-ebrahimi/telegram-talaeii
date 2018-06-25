package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.StatsController;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class DataUsageActivity extends BaseFragment {
    private int audiosBytesReceivedRow;
    private int audiosBytesSentRow;
    private int audiosReceivedRow;
    private int audiosSection2Row;
    private int audiosSectionRow;
    private int audiosSentRow;
    private int callsBytesReceivedRow;
    private int callsBytesSentRow;
    private int callsReceivedRow;
    private int callsSection2Row;
    private int callsSectionRow;
    private int callsSentRow;
    private int callsTotalTimeRow;
    private int currentType;
    private int filesBytesReceivedRow;
    private int filesBytesSentRow;
    private int filesReceivedRow;
    private int filesSection2Row;
    private int filesSectionRow;
    private int filesSentRow;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int messagesBytesReceivedRow;
    private int messagesBytesSentRow;
    private int messagesReceivedRow = -1;
    private int messagesSection2Row;
    private int messagesSectionRow;
    private int messagesSentRow = -1;
    private int photosBytesReceivedRow;
    private int photosBytesSentRow;
    private int photosReceivedRow;
    private int photosSection2Row;
    private int photosSectionRow;
    private int photosSentRow;
    private int resetRow;
    private int resetSection2Row;
    private int rowCount;
    private int totalBytesReceivedRow;
    private int totalBytesSentRow;
    private int totalSection2Row;
    private int totalSectionRow;
    private int videosBytesReceivedRow;
    private int videosBytesSentRow;
    private int videosReceivedRow;
    private int videosSection2Row;
    private int videosSectionRow;
    private int videosSentRow;

    /* renamed from: org.telegram.ui.DataUsageActivity$1 */
    class C47041 extends ActionBarMenuOnItemClick {
        C47041() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                DataUsageActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.DataUsageActivity$2 */
    class C47062 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.DataUsageActivity$2$1 */
        class C47051 implements OnClickListener {
            C47051() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                StatsController.getInstance().resetStats(DataUsageActivity.this.currentType);
                DataUsageActivity.this.listAdapter.notifyDataSetChanged();
            }
        }

        C47062() {
        }

        public void onItemClick(View view, int i) {
            if (DataUsageActivity.this.getParentActivity() != null && i == DataUsageActivity.this.resetRow) {
                Builder builder = new Builder(DataUsageActivity.this.getParentActivity());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setMessage(LocaleController.getString("ResetStatisticsAlert", R.string.ResetStatisticsAlert));
                builder.setPositiveButton(LocaleController.getString("Reset", R.string.Reset), new C47051());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                DataUsageActivity.this.showDialog(builder.create());
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return DataUsageActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return i == DataUsageActivity.this.resetSection2Row ? 3 : (i == DataUsageActivity.this.resetSection2Row || i == DataUsageActivity.this.callsSection2Row || i == DataUsageActivity.this.filesSection2Row || i == DataUsageActivity.this.audiosSection2Row || i == DataUsageActivity.this.videosSection2Row || i == DataUsageActivity.this.photosSection2Row || i == DataUsageActivity.this.messagesSection2Row || i == DataUsageActivity.this.totalSection2Row) ? 0 : (i == DataUsageActivity.this.totalSectionRow || i == DataUsageActivity.this.callsSectionRow || i == DataUsageActivity.this.filesSectionRow || i == DataUsageActivity.this.audiosSectionRow || i == DataUsageActivity.this.videosSectionRow || i == DataUsageActivity.this.photosSectionRow || i == DataUsageActivity.this.messagesSectionRow) ? 2 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getAdapterPosition() == DataUsageActivity.this.resetRow;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    if (i == DataUsageActivity.this.resetSection2Row) {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                case 1:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    if (i == DataUsageActivity.this.resetRow) {
                        textSettingsCell.setTag(Theme.key_windowBackgroundWhiteRedText2);
                        textSettingsCell.setText(LocaleController.getString("ResetStatistics", R.string.ResetStatistics), false);
                        textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText2));
                        return;
                    }
                    textSettingsCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                    textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    int i2 = (i == DataUsageActivity.this.callsSentRow || i == DataUsageActivity.this.callsReceivedRow || i == DataUsageActivity.this.callsBytesSentRow || i == DataUsageActivity.this.callsBytesReceivedRow) ? 0 : (i == DataUsageActivity.this.messagesSentRow || i == DataUsageActivity.this.messagesReceivedRow || i == DataUsageActivity.this.messagesBytesSentRow || i == DataUsageActivity.this.messagesBytesReceivedRow) ? 1 : (i == DataUsageActivity.this.photosSentRow || i == DataUsageActivity.this.photosReceivedRow || i == DataUsageActivity.this.photosBytesSentRow || i == DataUsageActivity.this.photosBytesReceivedRow) ? 4 : (i == DataUsageActivity.this.audiosSentRow || i == DataUsageActivity.this.audiosReceivedRow || i == DataUsageActivity.this.audiosBytesSentRow || i == DataUsageActivity.this.audiosBytesReceivedRow) ? 3 : (i == DataUsageActivity.this.videosSentRow || i == DataUsageActivity.this.videosReceivedRow || i == DataUsageActivity.this.videosBytesSentRow || i == DataUsageActivity.this.videosBytesReceivedRow) ? 2 : (i == DataUsageActivity.this.filesSentRow || i == DataUsageActivity.this.filesReceivedRow || i == DataUsageActivity.this.filesBytesSentRow || i == DataUsageActivity.this.filesBytesReceivedRow) ? 5 : 6;
                    if (i == DataUsageActivity.this.callsSentRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("OutgoingCalls", R.string.OutgoingCalls), String.format("%d", new Object[]{Integer.valueOf(StatsController.getInstance().getSentItemsCount(DataUsageActivity.this.currentType, i2))}), true);
                        return;
                    } else if (i == DataUsageActivity.this.callsReceivedRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("IncomingCalls", R.string.IncomingCalls), String.format("%d", new Object[]{Integer.valueOf(StatsController.getInstance().getRecivedItemsCount(DataUsageActivity.this.currentType, i2))}), true);
                        return;
                    } else if (i == DataUsageActivity.this.callsTotalTimeRow) {
                        i2 = StatsController.getInstance().getCallsTotalTime(DataUsageActivity.this.currentType);
                        int i3 = i2 / 3600;
                        i2 -= i3 * 3600;
                        i2 -= (i2 / 60) * 60;
                        textSettingsCell.setTextAndValue(LocaleController.getString("CallsTotalTime", R.string.CallsTotalTime), i3 != 0 ? String.format("%d:%02d:%02d", new Object[]{Integer.valueOf(i3), Integer.valueOf(i2 / 60), Integer.valueOf(i2)}) : String.format("%d:%02d", new Object[]{Integer.valueOf(i2 / 60), Integer.valueOf(i2)}), false);
                        return;
                    } else if (i == DataUsageActivity.this.messagesSentRow || i == DataUsageActivity.this.photosSentRow || i == DataUsageActivity.this.videosSentRow || i == DataUsageActivity.this.audiosSentRow || i == DataUsageActivity.this.filesSentRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("CountSent", R.string.CountSent), String.format("%d", new Object[]{Integer.valueOf(StatsController.getInstance().getSentItemsCount(DataUsageActivity.this.currentType, i2))}), true);
                        return;
                    } else if (i == DataUsageActivity.this.messagesReceivedRow || i == DataUsageActivity.this.photosReceivedRow || i == DataUsageActivity.this.videosReceivedRow || i == DataUsageActivity.this.audiosReceivedRow || i == DataUsageActivity.this.filesReceivedRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("CountReceived", R.string.CountReceived), String.format("%d", new Object[]{Integer.valueOf(StatsController.getInstance().getRecivedItemsCount(DataUsageActivity.this.currentType, i2))}), true);
                        return;
                    } else if (i == DataUsageActivity.this.messagesBytesSentRow || i == DataUsageActivity.this.photosBytesSentRow || i == DataUsageActivity.this.videosBytesSentRow || i == DataUsageActivity.this.audiosBytesSentRow || i == DataUsageActivity.this.filesBytesSentRow || i == DataUsageActivity.this.callsBytesSentRow || i == DataUsageActivity.this.totalBytesSentRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("BytesSent", R.string.BytesSent), AndroidUtilities.formatFileSize(StatsController.getInstance().getSentBytesCount(DataUsageActivity.this.currentType, i2)), true);
                        return;
                    } else if (i == DataUsageActivity.this.messagesBytesReceivedRow || i == DataUsageActivity.this.photosBytesReceivedRow || i == DataUsageActivity.this.videosBytesReceivedRow || i == DataUsageActivity.this.audiosBytesReceivedRow || i == DataUsageActivity.this.filesBytesReceivedRow || i == DataUsageActivity.this.callsBytesReceivedRow || i == DataUsageActivity.this.totalBytesReceivedRow) {
                        String string = LocaleController.getString("BytesReceived", R.string.BytesReceived);
                        String formatFileSize = AndroidUtilities.formatFileSize(StatsController.getInstance().getReceivedBytesCount(DataUsageActivity.this.currentType, i2));
                        if (i == DataUsageActivity.this.totalBytesReceivedRow) {
                            z = false;
                        }
                        textSettingsCell.setTextAndValue(string, formatFileSize, z);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (i == DataUsageActivity.this.totalSectionRow) {
                        headerCell.setText(LocaleController.getString("TotalDataUsage", R.string.TotalDataUsage));
                        return;
                    } else if (i == DataUsageActivity.this.callsSectionRow) {
                        headerCell.setText(LocaleController.getString("CallsDataUsage", R.string.CallsDataUsage));
                        return;
                    } else if (i == DataUsageActivity.this.filesSectionRow) {
                        headerCell.setText(LocaleController.getString("FilesDataUsage", R.string.FilesDataUsage));
                        return;
                    } else if (i == DataUsageActivity.this.audiosSectionRow) {
                        headerCell.setText(LocaleController.getString("LocalAudioCache", R.string.LocalAudioCache));
                        return;
                    } else if (i == DataUsageActivity.this.videosSectionRow) {
                        headerCell.setText(LocaleController.getString("LocalVideoCache", R.string.LocalVideoCache));
                        return;
                    } else if (i == DataUsageActivity.this.photosSectionRow) {
                        headerCell.setText(LocaleController.getString("LocalPhotoCache", R.string.LocalPhotoCache));
                        return;
                    } else if (i == DataUsageActivity.this.messagesSectionRow) {
                        headerCell.setText(LocaleController.getString("MessagesDataUsage", R.string.MessagesDataUsage));
                        return;
                    } else {
                        return;
                    }
                case 3:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    textInfoPrivacyCell.setText(LocaleController.formatString("NetworkUsageSince", R.string.NetworkUsageSince, new Object[]{LocaleController.getInstance().formatterStats.format(StatsController.getInstance().getResetStatsDate(DataUsageActivity.this.currentType))}));
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                    view = new ShadowSectionCell(this.mContext);
                    break;
                case 1:
                    view = new TextSettingsCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    view = new HeaderCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    view = new TextInfoPrivacyCell(this.mContext);
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }
    }

    public DataUsageActivity(int i) {
        this.currentType = i;
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        if (this.currentType == 0) {
            this.actionBar.setTitle(LocaleController.getString("MobileUsage", R.string.MobileUsage));
        } else if (this.currentType == 1) {
            this.actionBar.setTitle(LocaleController.getString("WiFiUsage", R.string.WiFiUsage));
        } else if (this.currentType == 2) {
            this.actionBar.setTitle(LocaleController.getString("RoamingUsage", R.string.RoamingUsage));
        }
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setActionBarMenuOnItemClick(new C47041());
        Log.d("LEE", "Jsssson:" + StatsController.getInstance().getNetworkUsageStatistics());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C47062());
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[16];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class, HeaderCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r9[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[10] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        r9[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[12] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r9[13] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[14] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r9[15] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText2);
        return r9;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.photosSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.photosSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.photosReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.photosBytesSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.photosBytesReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.photosSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.videosSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.videosSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.videosReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.videosBytesSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.videosBytesReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.videosSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.audiosSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.audiosSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.audiosReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.audiosBytesSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.audiosBytesReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.audiosSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.filesSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.filesSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.filesReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.filesBytesSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.filesBytesReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.filesSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsBytesSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsBytesReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsTotalTimeRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagesSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagesBytesSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagesBytesReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagesSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.totalSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.totalBytesSentRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.totalBytesReceivedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.totalSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.resetRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.resetSection2Row = i;
        return true;
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
