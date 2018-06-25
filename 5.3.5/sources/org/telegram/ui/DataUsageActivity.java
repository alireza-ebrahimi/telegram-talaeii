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
    class C28661 extends ActionBarMenuOnItemClick {
        C28661() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                DataUsageActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.DataUsageActivity$2 */
    class C28682 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.DataUsageActivity$2$1 */
        class C28671 implements OnClickListener {
            C28671() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                StatsController.getInstance().resetStats(DataUsageActivity.this.currentType);
                DataUsageActivity.this.listAdapter.notifyDataSetChanged();
            }
        }

        C28682() {
        }

        public void onItemClick(View view, int position) {
            if (DataUsageActivity.this.getParentActivity() != null && position == DataUsageActivity.this.resetRow) {
                Builder builder = new Builder(DataUsageActivity.this.getParentActivity());
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setMessage(LocaleController.getString("ResetStatisticsAlert", R.string.ResetStatisticsAlert));
                builder.setPositiveButton(LocaleController.getString("Reset", R.string.Reset), new C28671());
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

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    if (position == DataUsageActivity.this.resetSection2Row) {
                        holder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        holder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                case 1:
                    TextSettingsCell textCell = holder.itemView;
                    if (position == DataUsageActivity.this.resetRow) {
                        textCell.setTag(Theme.key_windowBackgroundWhiteRedText2);
                        textCell.setText(LocaleController.getString("ResetStatistics", R.string.ResetStatistics), false);
                        textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText2));
                        return;
                    }
                    int type;
                    textCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                    textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    if (position == DataUsageActivity.this.callsSentRow || position == DataUsageActivity.this.callsReceivedRow || position == DataUsageActivity.this.callsBytesSentRow || position == DataUsageActivity.this.callsBytesReceivedRow) {
                        type = 0;
                    } else if (position == DataUsageActivity.this.messagesSentRow || position == DataUsageActivity.this.messagesReceivedRow || position == DataUsageActivity.this.messagesBytesSentRow || position == DataUsageActivity.this.messagesBytesReceivedRow) {
                        type = 1;
                    } else if (position == DataUsageActivity.this.photosSentRow || position == DataUsageActivity.this.photosReceivedRow || position == DataUsageActivity.this.photosBytesSentRow || position == DataUsageActivity.this.photosBytesReceivedRow) {
                        type = 4;
                    } else if (position == DataUsageActivity.this.audiosSentRow || position == DataUsageActivity.this.audiosReceivedRow || position == DataUsageActivity.this.audiosBytesSentRow || position == DataUsageActivity.this.audiosBytesReceivedRow) {
                        type = 3;
                    } else if (position == DataUsageActivity.this.videosSentRow || position == DataUsageActivity.this.videosReceivedRow || position == DataUsageActivity.this.videosBytesSentRow || position == DataUsageActivity.this.videosBytesReceivedRow) {
                        type = 2;
                    } else if (position == DataUsageActivity.this.filesSentRow || position == DataUsageActivity.this.filesReceivedRow || position == DataUsageActivity.this.filesBytesSentRow || position == DataUsageActivity.this.filesBytesReceivedRow) {
                        type = 5;
                    } else {
                        type = 6;
                    }
                    if (position == DataUsageActivity.this.callsSentRow) {
                        textCell.setTextAndValue(LocaleController.getString("OutgoingCalls", R.string.OutgoingCalls), String.format("%d", new Object[]{Integer.valueOf(StatsController.getInstance().getSentItemsCount(DataUsageActivity.this.currentType, type))}), true);
                        return;
                    } else if (position == DataUsageActivity.this.callsReceivedRow) {
                        textCell.setTextAndValue(LocaleController.getString("IncomingCalls", R.string.IncomingCalls), String.format("%d", new Object[]{Integer.valueOf(StatsController.getInstance().getRecivedItemsCount(DataUsageActivity.this.currentType, type))}), true);
                        return;
                    } else if (position == DataUsageActivity.this.callsTotalTimeRow) {
                        String time;
                        int total = StatsController.getInstance().getCallsTotalTime(DataUsageActivity.this.currentType);
                        int hours = total / 3600;
                        total -= hours * 3600;
                        total -= (total / 60) * 60;
                        if (hours != 0) {
                            time = String.format("%d:%02d:%02d", new Object[]{Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(total)});
                        } else {
                            time = String.format("%d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(total)});
                        }
                        textCell.setTextAndValue(LocaleController.getString("CallsTotalTime", R.string.CallsTotalTime), time, false);
                        return;
                    } else if (position == DataUsageActivity.this.messagesSentRow || position == DataUsageActivity.this.photosSentRow || position == DataUsageActivity.this.videosSentRow || position == DataUsageActivity.this.audiosSentRow || position == DataUsageActivity.this.filesSentRow) {
                        textCell.setTextAndValue(LocaleController.getString("CountSent", R.string.CountSent), String.format("%d", new Object[]{Integer.valueOf(StatsController.getInstance().getSentItemsCount(DataUsageActivity.this.currentType, type))}), true);
                        return;
                    } else if (position == DataUsageActivity.this.messagesReceivedRow || position == DataUsageActivity.this.photosReceivedRow || position == DataUsageActivity.this.videosReceivedRow || position == DataUsageActivity.this.audiosReceivedRow || position == DataUsageActivity.this.filesReceivedRow) {
                        textCell.setTextAndValue(LocaleController.getString("CountReceived", R.string.CountReceived), String.format("%d", new Object[]{Integer.valueOf(StatsController.getInstance().getRecivedItemsCount(DataUsageActivity.this.currentType, type))}), true);
                        return;
                    } else if (position == DataUsageActivity.this.messagesBytesSentRow || position == DataUsageActivity.this.photosBytesSentRow || position == DataUsageActivity.this.videosBytesSentRow || position == DataUsageActivity.this.audiosBytesSentRow || position == DataUsageActivity.this.filesBytesSentRow || position == DataUsageActivity.this.callsBytesSentRow || position == DataUsageActivity.this.totalBytesSentRow) {
                        textCell.setTextAndValue(LocaleController.getString("BytesSent", R.string.BytesSent), AndroidUtilities.formatFileSize(StatsController.getInstance().getSentBytesCount(DataUsageActivity.this.currentType, type)), true);
                        return;
                    } else if (position == DataUsageActivity.this.messagesBytesReceivedRow || position == DataUsageActivity.this.photosBytesReceivedRow || position == DataUsageActivity.this.videosBytesReceivedRow || position == DataUsageActivity.this.audiosBytesReceivedRow || position == DataUsageActivity.this.filesBytesReceivedRow || position == DataUsageActivity.this.callsBytesReceivedRow || position == DataUsageActivity.this.totalBytesReceivedRow) {
                        textCell.setTextAndValue(LocaleController.getString("BytesReceived", R.string.BytesReceived), AndroidUtilities.formatFileSize(StatsController.getInstance().getReceivedBytesCount(DataUsageActivity.this.currentType, type)), position != DataUsageActivity.this.totalBytesReceivedRow);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    HeaderCell headerCell = holder.itemView;
                    if (position == DataUsageActivity.this.totalSectionRow) {
                        headerCell.setText(LocaleController.getString("TotalDataUsage", R.string.TotalDataUsage));
                        return;
                    } else if (position == DataUsageActivity.this.callsSectionRow) {
                        headerCell.setText(LocaleController.getString("CallsDataUsage", R.string.CallsDataUsage));
                        return;
                    } else if (position == DataUsageActivity.this.filesSectionRow) {
                        headerCell.setText(LocaleController.getString("FilesDataUsage", R.string.FilesDataUsage));
                        return;
                    } else if (position == DataUsageActivity.this.audiosSectionRow) {
                        headerCell.setText(LocaleController.getString("LocalAudioCache", R.string.LocalAudioCache));
                        return;
                    } else if (position == DataUsageActivity.this.videosSectionRow) {
                        headerCell.setText(LocaleController.getString("LocalVideoCache", R.string.LocalVideoCache));
                        return;
                    } else if (position == DataUsageActivity.this.photosSectionRow) {
                        headerCell.setText(LocaleController.getString("LocalPhotoCache", R.string.LocalPhotoCache));
                        return;
                    } else if (position == DataUsageActivity.this.messagesSectionRow) {
                        headerCell.setText(LocaleController.getString("MessagesDataUsage", R.string.MessagesDataUsage));
                        return;
                    } else {
                        return;
                    }
                case 3:
                    TextInfoPrivacyCell cell = holder.itemView;
                    cell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    cell.setText(LocaleController.formatString("NetworkUsageSince", R.string.NetworkUsageSince, new Object[]{LocaleController.getInstance().formatterStats.format(StatsController.getInstance().getResetStatsDate(DataUsageActivity.this.currentType))}));
                    return;
                default:
                    return;
            }
        }

        public boolean isEnabled(ViewHolder holder) {
            return holder.getAdapterPosition() == DataUsageActivity.this.resetRow;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
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

        public int getItemViewType(int position) {
            if (position == DataUsageActivity.this.resetSection2Row) {
                return 3;
            }
            if (position == DataUsageActivity.this.resetSection2Row || position == DataUsageActivity.this.callsSection2Row || position == DataUsageActivity.this.filesSection2Row || position == DataUsageActivity.this.audiosSection2Row || position == DataUsageActivity.this.videosSection2Row || position == DataUsageActivity.this.photosSection2Row || position == DataUsageActivity.this.messagesSection2Row || position == DataUsageActivity.this.totalSection2Row) {
                return 0;
            }
            if (position == DataUsageActivity.this.totalSectionRow || position == DataUsageActivity.this.callsSectionRow || position == DataUsageActivity.this.filesSectionRow || position == DataUsageActivity.this.audiosSectionRow || position == DataUsageActivity.this.videosSectionRow || position == DataUsageActivity.this.photosSectionRow || position == DataUsageActivity.this.messagesSectionRow) {
                return 2;
            }
            return 1;
        }
    }

    public DataUsageActivity(int type) {
        this.currentType = type;
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
        this.actionBar.setActionBarMenuOnItemClick(new C28661());
        Log.d("LEE", "Jsssson:" + StatsController.getInstance().getNetworkUsageStatistics());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = this.fragmentView;
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C28682());
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
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
}
