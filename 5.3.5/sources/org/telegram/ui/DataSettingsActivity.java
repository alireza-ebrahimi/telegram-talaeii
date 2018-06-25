package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
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
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class DataSettingsActivity extends BaseFragment {
    private AnimatorSet animatorSet;
    private int autoDownloadMediaRow;
    private int callsSection2Row;
    private int callsSectionRow;
    private int filesRow;
    private int gifsRow;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int mediaDownloadSection2Row;
    private int mediaDownloadSectionRow;
    private int mobileUsageRow;
    private int musicRow;
    private int photosRow;
    private int proxyRow;
    private int proxySection2Row;
    private int proxySectionRow;
    private int resetDownloadRow;
    private int roamingUsageRow;
    private int rowCount;
    private int storageUsageRow;
    private int usageSection2Row;
    private int usageSectionRow;
    private int useLessDataForCallsRow;
    private int videoMessagesRow;
    private int videosRow;
    private int voiceMessagesRow;
    private int wifiUsageRow;

    /* renamed from: org.telegram.ui.DataSettingsActivity$1 */
    class C28611 extends ActionBarMenuOnItemClick {
        C28611() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                DataSettingsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.DataSettingsActivity$2 */
    class C28642 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.DataSettingsActivity$2$1 */
        class C28621 implements OnClickListener {
            C28621() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                MediaController mediaController = MediaController.getInstance();
                int a = 0;
                while (a < 4) {
                    Object valueOf;
                    mediaController.mobileDataDownloadMask[a] = 115;
                    mediaController.wifiDownloadMask[a] = 115;
                    mediaController.roamingDownloadMask[a] = 0;
                    editor.putInt("mobileDataDownloadMask" + (a != 0 ? Integer.valueOf(a) : ""), mediaController.mobileDataDownloadMask[a]);
                    editor.putInt("wifiDownloadMask" + (a != 0 ? Integer.valueOf(a) : ""), mediaController.wifiDownloadMask[a]);
                    StringBuilder append = new StringBuilder().append("roamingDownloadMask");
                    if (a != 0) {
                        valueOf = Integer.valueOf(a);
                    } else {
                        valueOf = "";
                    }
                    editor.putInt(append.append(valueOf).toString(), mediaController.roamingDownloadMask[a]);
                    a++;
                }
                for (a = 0; a < 7; a++) {
                    int sdefault;
                    if (a == 1) {
                        sdefault = 2097152;
                    } else if (a == 6) {
                        sdefault = 5242880;
                    } else {
                        sdefault = 10485760;
                    }
                    mediaController.mobileMaxFileSize[a] = sdefault;
                    mediaController.wifiMaxFileSize[a] = sdefault;
                    mediaController.roamingMaxFileSize[a] = sdefault;
                    editor.putInt("mobileMaxDownloadSize" + a, sdefault);
                    editor.putInt("wifiMaxDownloadSize" + a, sdefault);
                    editor.putInt("roamingMaxDownloadSize" + a, sdefault);
                }
                if (!MediaController.getInstance().globalAutodownloadEnabled) {
                    MediaController.getInstance().globalAutodownloadEnabled = true;
                    editor.putBoolean("globalAutodownloadEnabled", MediaController.getInstance().globalAutodownloadEnabled);
                    DataSettingsActivity.this.updateAutodownloadRows(true);
                }
                editor.commit();
                MediaController.getInstance().checkAutodownloadSettings();
            }
        }

        C28642() {
        }

        public void onItemClick(View view, final int position) {
            if (position == DataSettingsActivity.this.photosRow || position == DataSettingsActivity.this.voiceMessagesRow || position == DataSettingsActivity.this.videoMessagesRow || position == DataSettingsActivity.this.videosRow || position == DataSettingsActivity.this.filesRow || position == DataSettingsActivity.this.musicRow || position == DataSettingsActivity.this.gifsRow) {
                if (!MediaController.getInstance().globalAutodownloadEnabled) {
                    return;
                }
                if (position == DataSettingsActivity.this.photosRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(1));
                } else if (position == DataSettingsActivity.this.voiceMessagesRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(2));
                } else if (position == DataSettingsActivity.this.videoMessagesRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(64));
                } else if (position == DataSettingsActivity.this.videosRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(4));
                } else if (position == DataSettingsActivity.this.filesRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(8));
                } else if (position == DataSettingsActivity.this.musicRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(16));
                } else if (position == DataSettingsActivity.this.gifsRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(32));
                }
            } else if (position == DataSettingsActivity.this.resetDownloadRow) {
                if (DataSettingsActivity.this.getParentActivity() != null) {
                    Builder builder = new Builder(DataSettingsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setMessage(LocaleController.getString("ResetAutomaticMediaDownloadAlert", R.string.ResetAutomaticMediaDownloadAlert));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C28621());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    builder.show();
                }
            } else if (position == DataSettingsActivity.this.autoDownloadMediaRow) {
                boolean z;
                MediaController instance = MediaController.getInstance();
                if (MediaController.getInstance().globalAutodownloadEnabled) {
                    z = false;
                } else {
                    z = true;
                }
                instance.globalAutodownloadEnabled = z;
                ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putBoolean("globalAutodownloadEnabled", MediaController.getInstance().globalAutodownloadEnabled).commit();
                ((TextCheckCell) view).setChecked(MediaController.getInstance().globalAutodownloadEnabled);
                DataSettingsActivity.this.updateAutodownloadRows(false);
            } else if (position == DataSettingsActivity.this.storageUsageRow) {
                DataSettingsActivity.this.presentFragment(new CacheControlActivity());
            } else if (position == DataSettingsActivity.this.useLessDataForCallsRow) {
                final SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                Dialog dlg = AlertsCreator.createSingleChoiceDialog(DataSettingsActivity.this.getParentActivity(), DataSettingsActivity.this, new String[]{LocaleController.getString("UseLessDataNever", R.string.UseLessDataNever), LocaleController.getString("UseLessDataOnMobile", R.string.UseLessDataOnMobile), LocaleController.getString("UseLessDataAlways", R.string.UseLessDataAlways)}, LocaleController.getString("VoipUseLessData", R.string.VoipUseLessData), preferences.getInt("VoipDataSaving", 0), new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int val = -1;
                        switch (which) {
                            case 0:
                                val = 0;
                                break;
                            case 1:
                                val = 1;
                                break;
                            case 2:
                                val = 2;
                                break;
                        }
                        if (val != -1) {
                            preferences.edit().putInt("VoipDataSaving", val).commit();
                        }
                        if (DataSettingsActivity.this.listAdapter != null) {
                            DataSettingsActivity.this.listAdapter.notifyItemChanged(position);
                        }
                    }
                });
                DataSettingsActivity.this.setVisibleDialog(dlg);
                dlg.show();
            } else if (position == DataSettingsActivity.this.mobileUsageRow) {
                DataSettingsActivity.this.presentFragment(new DataUsageActivity(0));
            } else if (position == DataSettingsActivity.this.roamingUsageRow) {
                DataSettingsActivity.this.presentFragment(new DataUsageActivity(2));
            } else if (position == DataSettingsActivity.this.wifiUsageRow) {
                DataSettingsActivity.this.presentFragment(new DataUsageActivity(1));
            } else if (position == DataSettingsActivity.this.proxyRow) {
                DataSettingsActivity.this.presentFragment(new ProxySettingsActivity());
            }
        }
    }

    /* renamed from: org.telegram.ui.DataSettingsActivity$3 */
    class C28653 extends AnimatorListenerAdapter {
        C28653() {
        }

        public void onAnimationEnd(Animator animator) {
            if (animator.equals(DataSettingsActivity.this.animatorSet)) {
                DataSettingsActivity.this.animatorSet = null;
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return DataSettingsActivity.this.rowCount;
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    if (position == DataSettingsActivity.this.proxySection2Row) {
                        holder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        holder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                case 1:
                    TextSettingsCell textCell = holder.itemView;
                    textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    if (position == DataSettingsActivity.this.storageUsageRow) {
                        textCell.setText(LocaleController.getString("StorageUsage", R.string.StorageUsage), true);
                        return;
                    } else if (position == DataSettingsActivity.this.useLessDataForCallsRow) {
                        String value = null;
                        switch (ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("VoipDataSaving", 0)) {
                            case 0:
                                value = LocaleController.getString("UseLessDataNever", R.string.UseLessDataNever);
                                break;
                            case 1:
                                value = LocaleController.getString("UseLessDataOnMobile", R.string.UseLessDataOnMobile);
                                break;
                            case 2:
                                value = LocaleController.getString("UseLessDataAlways", R.string.UseLessDataAlways);
                                break;
                        }
                        textCell.setTextAndValue(LocaleController.getString("VoipUseLessData", R.string.VoipUseLessData), value, false);
                        return;
                    } else if (position == DataSettingsActivity.this.mobileUsageRow) {
                        textCell.setText(LocaleController.getString("MobileUsage", R.string.MobileUsage), true);
                        return;
                    } else if (position == DataSettingsActivity.this.roamingUsageRow) {
                        textCell.setText(LocaleController.getString("RoamingUsage", R.string.RoamingUsage), false);
                        return;
                    } else if (position == DataSettingsActivity.this.wifiUsageRow) {
                        textCell.setText(LocaleController.getString("WiFiUsage", R.string.WiFiUsage), true);
                        return;
                    } else if (position == DataSettingsActivity.this.proxyRow) {
                        textCell.setText(LocaleController.getString("ProxySettings", R.string.ProxySettings), true);
                        return;
                    } else if (position == DataSettingsActivity.this.resetDownloadRow) {
                        textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText));
                        textCell.setText(LocaleController.getString("ResetAutomaticMediaDownload", R.string.ResetAutomaticMediaDownload), false);
                        return;
                    } else if (position == DataSettingsActivity.this.photosRow) {
                        textCell.setText(LocaleController.getString("LocalPhotoCache", R.string.LocalPhotoCache), true);
                        return;
                    } else if (position == DataSettingsActivity.this.voiceMessagesRow) {
                        textCell.setText(LocaleController.getString("AudioAutodownload", R.string.AudioAutodownload), true);
                        return;
                    } else if (position == DataSettingsActivity.this.videoMessagesRow) {
                        textCell.setText(LocaleController.getString("VideoMessagesAutodownload", R.string.VideoMessagesAutodownload), true);
                        return;
                    } else if (position == DataSettingsActivity.this.videosRow) {
                        textCell.setText(LocaleController.getString("LocalVideoCache", R.string.LocalVideoCache), true);
                        return;
                    } else if (position == DataSettingsActivity.this.filesRow) {
                        textCell.setText(LocaleController.getString("FilesDataUsage", R.string.FilesDataUsage), true);
                        return;
                    } else if (position == DataSettingsActivity.this.musicRow) {
                        textCell.setText(LocaleController.getString("AttachMusic", R.string.AttachMusic), true);
                        return;
                    } else if (position == DataSettingsActivity.this.gifsRow) {
                        textCell.setText(LocaleController.getString("LocalGifCache", R.string.LocalGifCache), true);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    HeaderCell headerCell = holder.itemView;
                    if (position == DataSettingsActivity.this.mediaDownloadSectionRow) {
                        headerCell.setText(LocaleController.getString("AutomaticMediaDownload", R.string.AutomaticMediaDownload));
                        return;
                    } else if (position == DataSettingsActivity.this.usageSectionRow) {
                        headerCell.setText(LocaleController.getString("DataUsage", R.string.DataUsage));
                        return;
                    } else if (position == DataSettingsActivity.this.callsSectionRow) {
                        headerCell.setText(LocaleController.getString("Calls", R.string.Calls));
                        return;
                    } else if (position == DataSettingsActivity.this.proxySectionRow) {
                        headerCell.setText(LocaleController.getString("Proxy", R.string.Proxy));
                        return;
                    } else {
                        return;
                    }
                case 3:
                    TextCheckCell checkCell = holder.itemView;
                    if (position == DataSettingsActivity.this.autoDownloadMediaRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("AutoDownloadMedia", R.string.AutoDownloadMedia), MediaController.getInstance().globalAutodownloadEnabled, true);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public void onViewAttachedToWindow(ViewHolder holder) {
            int viewType = holder.getItemViewType();
            if (viewType == 1) {
                int position = holder.getAdapterPosition();
                TextSettingsCell textCell = holder.itemView;
                if (position < DataSettingsActivity.this.photosRow || position > DataSettingsActivity.this.gifsRow) {
                    textCell.setEnabled(true, null);
                } else {
                    textCell.setEnabled(MediaController.getInstance().globalAutodownloadEnabled, null);
                }
            } else if (viewType == 3) {
                holder.itemView.setChecked(MediaController.getInstance().globalAutodownloadEnabled);
            }
        }

        public boolean isEnabled(ViewHolder holder) {
            int position = holder.getAdapterPosition();
            if (position == DataSettingsActivity.this.photosRow || position == DataSettingsActivity.this.voiceMessagesRow || position == DataSettingsActivity.this.videoMessagesRow || position == DataSettingsActivity.this.videosRow || position == DataSettingsActivity.this.filesRow || position == DataSettingsActivity.this.musicRow || position == DataSettingsActivity.this.gifsRow) {
                return MediaController.getInstance().globalAutodownloadEnabled;
            }
            return position == DataSettingsActivity.this.storageUsageRow || position == DataSettingsActivity.this.useLessDataForCallsRow || position == DataSettingsActivity.this.mobileUsageRow || position == DataSettingsActivity.this.roamingUsageRow || position == DataSettingsActivity.this.wifiUsageRow || position == DataSettingsActivity.this.proxyRow || position == DataSettingsActivity.this.resetDownloadRow || position == DataSettingsActivity.this.autoDownloadMediaRow;
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
                    view = new TextCheckCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public int getItemViewType(int position) {
            if (position == DataSettingsActivity.this.mediaDownloadSection2Row || position == DataSettingsActivity.this.usageSection2Row || position == DataSettingsActivity.this.callsSection2Row || position == DataSettingsActivity.this.proxySection2Row) {
                return 0;
            }
            if (position == DataSettingsActivity.this.mediaDownloadSectionRow || position == DataSettingsActivity.this.callsSectionRow || position == DataSettingsActivity.this.usageSectionRow || position == DataSettingsActivity.this.proxySectionRow) {
                return 2;
            }
            if (position == DataSettingsActivity.this.autoDownloadMediaRow) {
                return 3;
            }
            return 1;
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.usageSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.storageUsageRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mobileUsageRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.wifiUsageRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.roamingUsageRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.usageSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mediaDownloadSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.autoDownloadMediaRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.photosRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.voiceMessagesRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.videoMessagesRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.videosRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.filesRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.musicRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.gifsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.resetDownloadRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mediaDownloadSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.useLessDataForCallsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.proxySectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.proxyRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.proxySection2Row = i;
        return true;
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setTitle(LocaleController.getString("DataSettings", R.string.DataSettings));
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C28611());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = this.fragmentView;
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C28642());
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    protected void onDialogDismiss(Dialog dialog) {
        MediaController.getInstance().checkAutodownloadSettings();
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    private void updateAutodownloadRows(boolean check) {
        int count = this.listView.getChildCount();
        ArrayList<Animator> animators = new ArrayList();
        for (int a = 0; a < count; a++) {
            Holder holder = (Holder) this.listView.getChildViewHolder(this.listView.getChildAt(a));
            int type = holder.getItemViewType();
            int p = holder.getAdapterPosition();
            if (p >= this.photosRow && p <= this.gifsRow) {
                holder.itemView.setEnabled(MediaController.getInstance().globalAutodownloadEnabled, animators);
            } else if (check && p == this.autoDownloadMediaRow) {
                holder.itemView.setChecked(true);
            }
        }
        if (!animators.isEmpty()) {
            if (this.animatorSet != null) {
                this.animatorSet.cancel();
            }
            this.animatorSet = new AnimatorSet();
            this.animatorSet.playTogether(animators);
            this.animatorSet.addListener(new C28653());
            this.animatorSet.setDuration(150);
            this.animatorSet.start();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[19];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class, TextCheckCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        return themeDescriptionArr;
    }
}
