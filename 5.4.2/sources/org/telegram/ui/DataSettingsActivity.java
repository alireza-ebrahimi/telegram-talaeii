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
import java.util.Collection;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
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
    class C46991 extends ActionBarMenuOnItemClick {
        C46991() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                DataSettingsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.DataSettingsActivity$2 */
    class C47022 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.DataSettingsActivity$2$1 */
        class C47001 implements OnClickListener {
            C47001() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                int i2 = 0;
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                MediaController instance = MediaController.getInstance();
                int i3 = 0;
                while (i3 < 4) {
                    instance.mobileDataDownloadMask[i3] = 115;
                    instance.wifiDownloadMask[i3] = 115;
                    instance.roamingDownloadMask[i3] = 0;
                    edit.putInt("mobileDataDownloadMask" + (i3 != 0 ? Integer.valueOf(i3) : TtmlNode.ANONYMOUS_REGION_ID), instance.mobileDataDownloadMask[i3]);
                    edit.putInt("wifiDownloadMask" + (i3 != 0 ? Integer.valueOf(i3) : TtmlNode.ANONYMOUS_REGION_ID), instance.wifiDownloadMask[i3]);
                    edit.putInt("roamingDownloadMask" + (i3 != 0 ? Integer.valueOf(i3) : TtmlNode.ANONYMOUS_REGION_ID), instance.roamingDownloadMask[i3]);
                    i3++;
                }
                while (i2 < 7) {
                    int i4 = i2 == 1 ? 2097152 : i2 == 6 ? 5242880 : 10485760;
                    instance.mobileMaxFileSize[i2] = i4;
                    instance.wifiMaxFileSize[i2] = i4;
                    instance.roamingMaxFileSize[i2] = i4;
                    edit.putInt("mobileMaxDownloadSize" + i2, i4);
                    edit.putInt("wifiMaxDownloadSize" + i2, i4);
                    edit.putInt("roamingMaxDownloadSize" + i2, i4);
                    i2++;
                }
                if (!MediaController.getInstance().globalAutodownloadEnabled) {
                    MediaController.getInstance().globalAutodownloadEnabled = true;
                    edit.putBoolean("globalAutodownloadEnabled", MediaController.getInstance().globalAutodownloadEnabled);
                    DataSettingsActivity.this.updateAutodownloadRows(true);
                }
                edit.commit();
                MediaController.getInstance().checkAutodownloadSettings();
            }
        }

        C47022() {
        }

        public void onItemClick(View view, final int i) {
            if (i == DataSettingsActivity.this.photosRow || i == DataSettingsActivity.this.voiceMessagesRow || i == DataSettingsActivity.this.videoMessagesRow || i == DataSettingsActivity.this.videosRow || i == DataSettingsActivity.this.filesRow || i == DataSettingsActivity.this.musicRow || i == DataSettingsActivity.this.gifsRow) {
                if (!MediaController.getInstance().globalAutodownloadEnabled) {
                    return;
                }
                if (i == DataSettingsActivity.this.photosRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(1));
                } else if (i == DataSettingsActivity.this.voiceMessagesRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(2));
                } else if (i == DataSettingsActivity.this.videoMessagesRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(64));
                } else if (i == DataSettingsActivity.this.videosRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(4));
                } else if (i == DataSettingsActivity.this.filesRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(8));
                } else if (i == DataSettingsActivity.this.musicRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(16));
                } else if (i == DataSettingsActivity.this.gifsRow) {
                    DataSettingsActivity.this.presentFragment(new DataAutoDownloadActivity(32));
                }
            } else if (i == DataSettingsActivity.this.resetDownloadRow) {
                if (DataSettingsActivity.this.getParentActivity() != null) {
                    Builder builder = new Builder(DataSettingsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setMessage(LocaleController.getString("ResetAutomaticMediaDownloadAlert", R.string.ResetAutomaticMediaDownloadAlert));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C47001());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    builder.show();
                }
            } else if (i == DataSettingsActivity.this.autoDownloadMediaRow) {
                MediaController.getInstance().globalAutodownloadEnabled = !MediaController.getInstance().globalAutodownloadEnabled;
                ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putBoolean("globalAutodownloadEnabled", MediaController.getInstance().globalAutodownloadEnabled).commit();
                ((TextCheckCell) view).setChecked(MediaController.getInstance().globalAutodownloadEnabled);
                DataSettingsActivity.this.updateAutodownloadRows(false);
            } else if (i == DataSettingsActivity.this.storageUsageRow) {
                DataSettingsActivity.this.presentFragment(new CacheControlActivity());
            } else if (i == DataSettingsActivity.this.useLessDataForCallsRow) {
                final SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                Dialog createSingleChoiceDialog = AlertsCreator.createSingleChoiceDialog(DataSettingsActivity.this.getParentActivity(), DataSettingsActivity.this, new String[]{LocaleController.getString("UseLessDataNever", R.string.UseLessDataNever), LocaleController.getString("UseLessDataOnMobile", R.string.UseLessDataOnMobile), LocaleController.getString("UseLessDataAlways", R.string.UseLessDataAlways)}, LocaleController.getString("VoipUseLessData", R.string.VoipUseLessData), sharedPreferences.getInt("VoipDataSaving", 0), new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int i2;
                        switch (i) {
                            case 0:
                                i2 = 0;
                                break;
                            case 1:
                                i2 = 1;
                                break;
                            case 2:
                                i2 = 2;
                                break;
                            default:
                                i2 = -1;
                                break;
                        }
                        if (i2 != -1) {
                            sharedPreferences.edit().putInt("VoipDataSaving", i2).commit();
                        }
                        if (DataSettingsActivity.this.listAdapter != null) {
                            DataSettingsActivity.this.listAdapter.notifyItemChanged(i);
                        }
                    }
                });
                DataSettingsActivity.this.setVisibleDialog(createSingleChoiceDialog);
                createSingleChoiceDialog.show();
            } else if (i == DataSettingsActivity.this.mobileUsageRow) {
                DataSettingsActivity.this.presentFragment(new DataUsageActivity(0));
            } else if (i == DataSettingsActivity.this.roamingUsageRow) {
                DataSettingsActivity.this.presentFragment(new DataUsageActivity(2));
            } else if (i == DataSettingsActivity.this.wifiUsageRow) {
                DataSettingsActivity.this.presentFragment(new DataUsageActivity(1));
            } else if (i == DataSettingsActivity.this.proxyRow) {
                DataSettingsActivity.this.presentFragment(new ProxySettingsActivity());
            }
        }
    }

    /* renamed from: org.telegram.ui.DataSettingsActivity$3 */
    class C47033 extends AnimatorListenerAdapter {
        C47033() {
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

        public int getItemViewType(int i) {
            return (i == DataSettingsActivity.this.mediaDownloadSection2Row || i == DataSettingsActivity.this.usageSection2Row || i == DataSettingsActivity.this.callsSection2Row || i == DataSettingsActivity.this.proxySection2Row) ? 0 : (i == DataSettingsActivity.this.mediaDownloadSectionRow || i == DataSettingsActivity.this.callsSectionRow || i == DataSettingsActivity.this.usageSectionRow || i == DataSettingsActivity.this.proxySectionRow) ? 2 : i == DataSettingsActivity.this.autoDownloadMediaRow ? 3 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return (adapterPosition == DataSettingsActivity.this.photosRow || adapterPosition == DataSettingsActivity.this.voiceMessagesRow || adapterPosition == DataSettingsActivity.this.videoMessagesRow || adapterPosition == DataSettingsActivity.this.videosRow || adapterPosition == DataSettingsActivity.this.filesRow || adapterPosition == DataSettingsActivity.this.musicRow || adapterPosition == DataSettingsActivity.this.gifsRow) ? MediaController.getInstance().globalAutodownloadEnabled : adapterPosition == DataSettingsActivity.this.storageUsageRow || adapterPosition == DataSettingsActivity.this.useLessDataForCallsRow || adapterPosition == DataSettingsActivity.this.mobileUsageRow || adapterPosition == DataSettingsActivity.this.roamingUsageRow || adapterPosition == DataSettingsActivity.this.wifiUsageRow || adapterPosition == DataSettingsActivity.this.proxyRow || adapterPosition == DataSettingsActivity.this.resetDownloadRow || adapterPosition == DataSettingsActivity.this.autoDownloadMediaRow;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    if (i == DataSettingsActivity.this.proxySection2Row) {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                case 1:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    if (i == DataSettingsActivity.this.storageUsageRow) {
                        textSettingsCell.setText(LocaleController.getString("StorageUsage", R.string.StorageUsage), true);
                        return;
                    } else if (i == DataSettingsActivity.this.useLessDataForCallsRow) {
                        String str = null;
                        switch (ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("VoipDataSaving", 0)) {
                            case 0:
                                str = LocaleController.getString("UseLessDataNever", R.string.UseLessDataNever);
                                break;
                            case 1:
                                str = LocaleController.getString("UseLessDataOnMobile", R.string.UseLessDataOnMobile);
                                break;
                            case 2:
                                str = LocaleController.getString("UseLessDataAlways", R.string.UseLessDataAlways);
                                break;
                        }
                        textSettingsCell.setTextAndValue(LocaleController.getString("VoipUseLessData", R.string.VoipUseLessData), str, false);
                        return;
                    } else if (i == DataSettingsActivity.this.mobileUsageRow) {
                        textSettingsCell.setText(LocaleController.getString("MobileUsage", R.string.MobileUsage), true);
                        return;
                    } else if (i == DataSettingsActivity.this.roamingUsageRow) {
                        textSettingsCell.setText(LocaleController.getString("RoamingUsage", R.string.RoamingUsage), false);
                        return;
                    } else if (i == DataSettingsActivity.this.wifiUsageRow) {
                        textSettingsCell.setText(LocaleController.getString("WiFiUsage", R.string.WiFiUsage), true);
                        return;
                    } else if (i == DataSettingsActivity.this.proxyRow) {
                        textSettingsCell.setText(LocaleController.getString("ProxySettings", R.string.ProxySettings), true);
                        return;
                    } else if (i == DataSettingsActivity.this.resetDownloadRow) {
                        textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText));
                        textSettingsCell.setText(LocaleController.getString("ResetAutomaticMediaDownload", R.string.ResetAutomaticMediaDownload), false);
                        return;
                    } else if (i == DataSettingsActivity.this.photosRow) {
                        textSettingsCell.setText(LocaleController.getString("LocalPhotoCache", R.string.LocalPhotoCache), true);
                        return;
                    } else if (i == DataSettingsActivity.this.voiceMessagesRow) {
                        textSettingsCell.setText(LocaleController.getString("AudioAutodownload", R.string.AudioAutodownload), true);
                        return;
                    } else if (i == DataSettingsActivity.this.videoMessagesRow) {
                        textSettingsCell.setText(LocaleController.getString("VideoMessagesAutodownload", R.string.VideoMessagesAutodownload), true);
                        return;
                    } else if (i == DataSettingsActivity.this.videosRow) {
                        textSettingsCell.setText(LocaleController.getString("LocalVideoCache", R.string.LocalVideoCache), true);
                        return;
                    } else if (i == DataSettingsActivity.this.filesRow) {
                        textSettingsCell.setText(LocaleController.getString("FilesDataUsage", R.string.FilesDataUsage), true);
                        return;
                    } else if (i == DataSettingsActivity.this.musicRow) {
                        textSettingsCell.setText(LocaleController.getString("AttachMusic", R.string.AttachMusic), true);
                        return;
                    } else if (i == DataSettingsActivity.this.gifsRow) {
                        textSettingsCell.setText(LocaleController.getString("LocalGifCache", R.string.LocalGifCache), true);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (i == DataSettingsActivity.this.mediaDownloadSectionRow) {
                        headerCell.setText(LocaleController.getString("AutomaticMediaDownload", R.string.AutomaticMediaDownload));
                        return;
                    } else if (i == DataSettingsActivity.this.usageSectionRow) {
                        headerCell.setText(LocaleController.getString("DataUsage", R.string.DataUsage));
                        return;
                    } else if (i == DataSettingsActivity.this.callsSectionRow) {
                        headerCell.setText(LocaleController.getString("Calls", R.string.Calls));
                        return;
                    } else if (i == DataSettingsActivity.this.proxySectionRow) {
                        headerCell.setText(LocaleController.getString("Proxy", R.string.Proxy));
                        return;
                    } else {
                        return;
                    }
                case 3:
                    TextCheckCell textCheckCell = (TextCheckCell) viewHolder.itemView;
                    if (i == DataSettingsActivity.this.autoDownloadMediaRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("AutoDownloadMedia", R.string.AutoDownloadMedia), MediaController.getInstance().globalAutodownloadEnabled, true);
                        return;
                    }
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
                    view = new TextCheckCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public void onViewAttachedToWindow(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 1) {
                int adapterPosition = viewHolder.getAdapterPosition();
                TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                if (adapterPosition < DataSettingsActivity.this.photosRow || adapterPosition > DataSettingsActivity.this.gifsRow) {
                    textSettingsCell.setEnabled(true, null);
                } else {
                    textSettingsCell.setEnabled(MediaController.getInstance().globalAutodownloadEnabled, null);
                }
            } else if (itemViewType == 3) {
                ((TextCheckCell) viewHolder.itemView).setChecked(MediaController.getInstance().globalAutodownloadEnabled);
            }
        }
    }

    private void updateAutodownloadRows(boolean z) {
        int childCount = this.listView.getChildCount();
        Collection arrayList = new ArrayList();
        for (int i = 0; i < childCount; i++) {
            Holder holder = (Holder) this.listView.getChildViewHolder(this.listView.getChildAt(i));
            holder.getItemViewType();
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition >= this.photosRow && adapterPosition <= this.gifsRow) {
                ((TextSettingsCell) holder.itemView).setEnabled(MediaController.getInstance().globalAutodownloadEnabled, arrayList);
            } else if (z && adapterPosition == this.autoDownloadMediaRow) {
                ((TextCheckCell) holder.itemView).setChecked(true);
            }
        }
        if (!arrayList.isEmpty()) {
            if (this.animatorSet != null) {
                this.animatorSet.cancel();
            }
            this.animatorSet = new AnimatorSet();
            this.animatorSet.playTogether(arrayList);
            this.animatorSet.addListener(new C47033());
            this.animatorSet.setDuration(150);
            this.animatorSet.start();
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setTitle(LocaleController.getString("DataSettings", R.string.DataSettings));
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C46991());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C47022());
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
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

    protected void onDialogDismiss(Dialog dialog) {
        MediaController.getInstance().checkAutodownloadSettings();
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

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
