package org.telegram.ui;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.MaxFileSizeCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckBoxCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class DataAutoDownloadActivity extends BaseFragment {
    private static final int done_button = 1;
    private int currentType;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int mChannelsRow;
    private int mContactsRow;
    private int mGroupRow;
    private int mPrivateRow;
    private int mSizeRow;
    private long maxSize;
    private int mobileDataChannelDownloadMask;
    private int mobileDataDownloadMask;
    private int mobileDataGroupDownloadMask;
    private int mobileDataPrivateDownloadMask;
    private int mobileMaxSize;
    private int mobileSection2Row;
    private int mobileSectionRow;
    private int rChannelsRow;
    private int rContactsRow;
    private int rGroupRow;
    private int rPrivateRow;
    private int rSizeRow;
    private int roamingChannelDownloadMask;
    private int roamingDownloadMask;
    private int roamingGroupDownloadMask;
    private int roamingMaxSize;
    private int roamingPrivateDownloadMask;
    private int roamingSection2Row;
    private int roamingSectionRow;
    private int rowCount;
    private int wChannelsRow;
    private int wContactsRow;
    private int wGroupRow;
    private int wPrivateRow;
    private int wSizeRow;
    private int wifiChannelDownloadMask;
    private int wifiDownloadMask;
    private int wifiGroupDownloadMask;
    private int wifiMaxSize;
    private int wifiPrivateDownloadMask;
    private int wifiSection2Row;
    private int wifiSectionRow;

    /* renamed from: org.telegram.ui.DataAutoDownloadActivity$1 */
    class C46961 extends ActionBarMenuOnItemClick {
        C46961() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                DataAutoDownloadActivity.this.finishFragment();
            } else if (i == 1) {
                MediaController.getInstance().mobileDataDownloadMask[0] = DataAutoDownloadActivity.this.mobileDataDownloadMask;
                MediaController.getInstance().mobileDataDownloadMask[1] = DataAutoDownloadActivity.this.mobileDataPrivateDownloadMask;
                MediaController.getInstance().mobileDataDownloadMask[2] = DataAutoDownloadActivity.this.mobileDataGroupDownloadMask;
                MediaController.getInstance().mobileDataDownloadMask[3] = DataAutoDownloadActivity.this.mobileDataChannelDownloadMask;
                MediaController.getInstance().wifiDownloadMask[0] = DataAutoDownloadActivity.this.wifiDownloadMask;
                MediaController.getInstance().wifiDownloadMask[1] = DataAutoDownloadActivity.this.wifiPrivateDownloadMask;
                MediaController.getInstance().wifiDownloadMask[2] = DataAutoDownloadActivity.this.wifiGroupDownloadMask;
                MediaController.getInstance().wifiDownloadMask[3] = DataAutoDownloadActivity.this.wifiChannelDownloadMask;
                MediaController.getInstance().roamingDownloadMask[0] = DataAutoDownloadActivity.this.roamingDownloadMask;
                MediaController.getInstance().roamingDownloadMask[1] = DataAutoDownloadActivity.this.roamingPrivateDownloadMask;
                MediaController.getInstance().roamingDownloadMask[2] = DataAutoDownloadActivity.this.roamingGroupDownloadMask;
                MediaController.getInstance().roamingDownloadMask[3] = DataAutoDownloadActivity.this.roamingChannelDownloadMask;
                MediaController.getInstance().mobileMaxFileSize[MediaController.maskToIndex(DataAutoDownloadActivity.this.currentType)] = DataAutoDownloadActivity.this.mobileMaxSize;
                MediaController.getInstance().wifiMaxFileSize[MediaController.maskToIndex(DataAutoDownloadActivity.this.currentType)] = DataAutoDownloadActivity.this.wifiMaxSize;
                MediaController.getInstance().roamingMaxFileSize[MediaController.maskToIndex(DataAutoDownloadActivity.this.currentType)] = DataAutoDownloadActivity.this.roamingMaxSize;
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                int i2 = 0;
                while (i2 < 4) {
                    edit.putInt("mobileDataDownloadMask" + (i2 != 0 ? Integer.valueOf(i2) : TtmlNode.ANONYMOUS_REGION_ID), MediaController.getInstance().mobileDataDownloadMask[i2]);
                    edit.putInt("wifiDownloadMask" + (i2 != 0 ? Integer.valueOf(i2) : TtmlNode.ANONYMOUS_REGION_ID), MediaController.getInstance().wifiDownloadMask[i2]);
                    edit.putInt("roamingDownloadMask" + (i2 != 0 ? Integer.valueOf(i2) : TtmlNode.ANONYMOUS_REGION_ID), MediaController.getInstance().roamingDownloadMask[i2]);
                    i2++;
                }
                edit.putInt("mobileMaxDownloadSize" + MediaController.maskToIndex(DataAutoDownloadActivity.this.currentType), DataAutoDownloadActivity.this.mobileMaxSize);
                edit.putInt("wifiMaxDownloadSize" + MediaController.maskToIndex(DataAutoDownloadActivity.this.currentType), DataAutoDownloadActivity.this.wifiMaxSize);
                edit.putInt("roamingMaxDownloadSize" + MediaController.maskToIndex(DataAutoDownloadActivity.this.currentType), DataAutoDownloadActivity.this.roamingMaxSize);
                edit.commit();
                MediaController.getInstance().checkAutodownloadSettings();
                DataAutoDownloadActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.DataAutoDownloadActivity$2 */
    class C46972 implements OnItemClickListener {
        C46972() {
        }

        public void onItemClick(View view, int i) {
            if (view instanceof TextCheckBoxCell) {
                int access$1600 = DataAutoDownloadActivity.this.getMaskForRow(i);
                TextCheckBoxCell textCheckBoxCell = (TextCheckBoxCell) view;
                boolean z = !textCheckBoxCell.isChecked();
                DataAutoDownloadActivity.this.setMaskForRow(i, z ? DataAutoDownloadActivity.this.currentType | access$1600 : (DataAutoDownloadActivity.this.currentType ^ -1) & access$1600);
                textCheckBoxCell.setChecked(z);
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return DataAutoDownloadActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == DataAutoDownloadActivity.this.mobileSection2Row || i == DataAutoDownloadActivity.this.wifiSection2Row || i == DataAutoDownloadActivity.this.roamingSection2Row) ? 0 : (i == DataAutoDownloadActivity.this.mobileSectionRow || i == DataAutoDownloadActivity.this.wifiSectionRow || i == DataAutoDownloadActivity.this.roamingSectionRow) ? 2 : (i == DataAutoDownloadActivity.this.wSizeRow || i == DataAutoDownloadActivity.this.mSizeRow || i == DataAutoDownloadActivity.this.rSizeRow) ? 3 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return (adapterPosition == DataAutoDownloadActivity.this.mSizeRow || adapterPosition == DataAutoDownloadActivity.this.rSizeRow || adapterPosition == DataAutoDownloadActivity.this.wSizeRow || adapterPosition == DataAutoDownloadActivity.this.mobileSectionRow || adapterPosition == DataAutoDownloadActivity.this.wifiSectionRow || adapterPosition == DataAutoDownloadActivity.this.roamingSectionRow || adapterPosition == DataAutoDownloadActivity.this.mobileSection2Row || adapterPosition == DataAutoDownloadActivity.this.wifiSection2Row || adapterPosition == DataAutoDownloadActivity.this.roamingSection2Row) ? false : true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            boolean z2 = true;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    if (i == DataAutoDownloadActivity.this.mobileSection2Row || i == DataAutoDownloadActivity.this.wifiSection2Row) {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                case 1:
                    TextCheckBoxCell textCheckBoxCell = (TextCheckBoxCell) viewHolder.itemView;
                    String string;
                    if (i == DataAutoDownloadActivity.this.mContactsRow || i == DataAutoDownloadActivity.this.wContactsRow || i == DataAutoDownloadActivity.this.rContactsRow) {
                        string = LocaleController.getString("AutodownloadContacts", R.string.AutodownloadContacts);
                        if ((DataAutoDownloadActivity.this.getMaskForRow(i) & DataAutoDownloadActivity.this.currentType) != 0) {
                            z = true;
                        }
                        textCheckBoxCell.setTextAndCheck(string, z, true);
                        return;
                    } else if (i == DataAutoDownloadActivity.this.mPrivateRow || i == DataAutoDownloadActivity.this.wPrivateRow || i == DataAutoDownloadActivity.this.rPrivateRow) {
                        string = LocaleController.getString("AutodownloadPrivateChats", R.string.AutodownloadPrivateChats);
                        if ((DataAutoDownloadActivity.this.getMaskForRow(i) & DataAutoDownloadActivity.this.currentType) != 0) {
                            z = true;
                        }
                        textCheckBoxCell.setTextAndCheck(string, z, true);
                        return;
                    } else if (i == DataAutoDownloadActivity.this.mChannelsRow || i == DataAutoDownloadActivity.this.wChannelsRow || i == DataAutoDownloadActivity.this.rChannelsRow) {
                        String string2 = LocaleController.getString("AutodownloadChannels", R.string.AutodownloadChannels);
                        boolean z3 = (DataAutoDownloadActivity.this.getMaskForRow(i) & DataAutoDownloadActivity.this.currentType) != 0;
                        if (DataAutoDownloadActivity.this.mSizeRow == -1) {
                            z2 = false;
                        }
                        textCheckBoxCell.setTextAndCheck(string2, z3, z2);
                        return;
                    } else if (i == DataAutoDownloadActivity.this.mGroupRow || i == DataAutoDownloadActivity.this.wGroupRow || i == DataAutoDownloadActivity.this.rGroupRow) {
                        string = LocaleController.getString("AutodownloadGroupChats", R.string.AutodownloadGroupChats);
                        if ((DataAutoDownloadActivity.this.getMaskForRow(i) & DataAutoDownloadActivity.this.currentType) != 0) {
                            z = true;
                        }
                        textCheckBoxCell.setTextAndCheck(string, z, true);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (i == DataAutoDownloadActivity.this.mobileSectionRow) {
                        headerCell.setText(LocaleController.getString("WhenUsingMobileData", R.string.WhenUsingMobileData));
                        return;
                    } else if (i == DataAutoDownloadActivity.this.wifiSectionRow) {
                        headerCell.setText(LocaleController.getString("WhenConnectedOnWiFi", R.string.WhenConnectedOnWiFi));
                        return;
                    } else if (i == DataAutoDownloadActivity.this.roamingSectionRow) {
                        headerCell.setText(LocaleController.getString("WhenRoaming", R.string.WhenRoaming));
                        return;
                    } else {
                        return;
                    }
                case 3:
                    MaxFileSizeCell maxFileSizeCell = (MaxFileSizeCell) viewHolder.itemView;
                    if (i == DataAutoDownloadActivity.this.mSizeRow) {
                        maxFileSizeCell.setSize((long) DataAutoDownloadActivity.this.mobileMaxSize, DataAutoDownloadActivity.this.maxSize);
                        maxFileSizeCell.setTag(Integer.valueOf(0));
                        return;
                    } else if (i == DataAutoDownloadActivity.this.wSizeRow) {
                        maxFileSizeCell.setSize((long) DataAutoDownloadActivity.this.wifiMaxSize, DataAutoDownloadActivity.this.maxSize);
                        maxFileSizeCell.setTag(Integer.valueOf(1));
                        return;
                    } else if (i == DataAutoDownloadActivity.this.rSizeRow) {
                        maxFileSizeCell.setSize((long) DataAutoDownloadActivity.this.roamingMaxSize, DataAutoDownloadActivity.this.maxSize);
                        maxFileSizeCell.setTag(Integer.valueOf(2));
                        return;
                    } else {
                        return;
                    }
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
                    view = new TextCheckBoxCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    view = new HeaderCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    view = new MaxFileSizeCell(this.mContext) {
                        protected void didChangedSizeValue(int i) {
                            Integer num = (Integer) getTag();
                            if (num.intValue() == 0) {
                                DataAutoDownloadActivity.this.mobileMaxSize = i;
                            } else if (num.intValue() == 1) {
                                DataAutoDownloadActivity.this.wifiMaxSize = i;
                            } else if (num.intValue() == 2) {
                                DataAutoDownloadActivity.this.roamingMaxSize = i;
                            }
                        }
                    };
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }
    }

    public DataAutoDownloadActivity(int i) {
        this.currentType = i;
        if (this.currentType == 64) {
            this.maxSize = 8388608;
        } else if (this.currentType == 32) {
            this.maxSize = 10485760;
        } else {
            this.maxSize = 1610612736;
        }
        this.mobileDataDownloadMask = MediaController.getInstance().mobileDataDownloadMask[0];
        this.mobileDataPrivateDownloadMask = MediaController.getInstance().mobileDataDownloadMask[1];
        this.mobileDataGroupDownloadMask = MediaController.getInstance().mobileDataDownloadMask[2];
        this.mobileDataChannelDownloadMask = MediaController.getInstance().mobileDataDownloadMask[3];
        this.wifiDownloadMask = MediaController.getInstance().wifiDownloadMask[0];
        this.wifiPrivateDownloadMask = MediaController.getInstance().wifiDownloadMask[1];
        this.wifiGroupDownloadMask = MediaController.getInstance().wifiDownloadMask[2];
        this.wifiChannelDownloadMask = MediaController.getInstance().wifiDownloadMask[3];
        this.roamingDownloadMask = MediaController.getInstance().roamingDownloadMask[0];
        this.roamingPrivateDownloadMask = MediaController.getInstance().roamingDownloadMask[1];
        this.roamingGroupDownloadMask = MediaController.getInstance().roamingDownloadMask[2];
        this.roamingChannelDownloadMask = MediaController.getInstance().roamingDownloadMask[2];
        this.mobileMaxSize = MediaController.getInstance().mobileMaxFileSize[MediaController.maskToIndex(this.currentType)];
        this.wifiMaxSize = MediaController.getInstance().wifiMaxFileSize[MediaController.maskToIndex(this.currentType)];
        this.roamingMaxSize = MediaController.getInstance().roamingMaxFileSize[MediaController.maskToIndex(this.currentType)];
    }

    private int getMaskForRow(int i) {
        return i == this.mContactsRow ? this.mobileDataDownloadMask : i == this.mPrivateRow ? this.mobileDataPrivateDownloadMask : i == this.mGroupRow ? this.mobileDataGroupDownloadMask : i == this.mChannelsRow ? this.mobileDataChannelDownloadMask : i == this.wContactsRow ? this.wifiDownloadMask : i == this.wPrivateRow ? this.wifiPrivateDownloadMask : i == this.wGroupRow ? this.wifiGroupDownloadMask : i == this.wChannelsRow ? this.wifiChannelDownloadMask : i == this.rContactsRow ? this.roamingDownloadMask : i == this.rPrivateRow ? this.roamingPrivateDownloadMask : i == this.rGroupRow ? this.roamingGroupDownloadMask : i == this.rChannelsRow ? this.roamingChannelDownloadMask : 0;
    }

    private void setMaskForRow(int i, int i2) {
        if (i == this.mContactsRow) {
            this.mobileDataDownloadMask = i2;
        } else if (i == this.mPrivateRow) {
            this.mobileDataPrivateDownloadMask = i2;
        } else if (i == this.mGroupRow) {
            this.mobileDataGroupDownloadMask = i2;
        } else if (i == this.mChannelsRow) {
            this.mobileDataChannelDownloadMask = i2;
        } else if (i == this.wContactsRow) {
            this.wifiDownloadMask = i2;
        } else if (i == this.wPrivateRow) {
            this.wifiPrivateDownloadMask = i2;
        } else if (i == this.wGroupRow) {
            this.wifiGroupDownloadMask = i2;
        } else if (i == this.wChannelsRow) {
            this.wifiChannelDownloadMask = i2;
        } else if (i == this.rContactsRow) {
            this.roamingDownloadMask = i2;
        } else if (i == this.rPrivateRow) {
            this.roamingPrivateDownloadMask = i2;
        } else if (i == this.rGroupRow) {
            this.roamingGroupDownloadMask = i2;
        } else if (i == this.rChannelsRow) {
            this.roamingChannelDownloadMask = i2;
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        if (this.currentType == 1) {
            this.actionBar.setTitle(LocaleController.getString("LocalPhotoCache", R.string.LocalPhotoCache));
        } else if (this.currentType == 2) {
            this.actionBar.setTitle(LocaleController.getString("AudioAutodownload", R.string.AudioAutodownload));
        } else if (this.currentType == 64) {
            this.actionBar.setTitle(LocaleController.getString("VideoMessagesAutodownload", R.string.VideoMessagesAutodownload));
        } else if (this.currentType == 4) {
            this.actionBar.setTitle(LocaleController.getString("LocalVideoCache", R.string.LocalVideoCache));
        } else if (this.currentType == 8) {
            this.actionBar.setTitle(LocaleController.getString("FilesDataUsage", R.string.FilesDataUsage));
        } else if (this.currentType == 16) {
            this.actionBar.setTitle(LocaleController.getString("AttachMusic", R.string.AttachMusic));
        } else if (this.currentType == 32) {
            this.actionBar.setTitle(LocaleController.getString("LocalGifCache", R.string.LocalGifCache));
        }
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C46961());
        this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C46972());
        frameLayout.addView(this.actionBar);
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[18];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextCheckBoxCell.class, MaxFileSizeCell.class, HeaderCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r9[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[10] = new ThemeDescription(this.listView, 0, new Class[]{MaxFileSizeCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[11] = new ThemeDescription(this.listView, 0, new Class[]{MaxFileSizeCell.class}, new String[]{"sizeTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[12] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[13] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, null, null, null, Theme.key_checkboxSquareUnchecked);
        r9[14] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, null, null, null, Theme.key_checkboxSquareDisabled);
        r9[15] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, null, null, null, Theme.key_checkboxSquareBackground);
        r9[16] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, null, null, null, Theme.key_checkboxSquareCheck);
        r9[17] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        return r9;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.mobileSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mContactsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mPrivateRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mGroupRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mChannelsRow = i;
        if (this.currentType != 1) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.mSizeRow = i;
        } else {
            this.mSizeRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.mobileSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.wifiSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.wContactsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.wPrivateRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.wGroupRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.wChannelsRow = i;
        if (this.currentType != 1) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.wSizeRow = i;
        } else {
            this.wSizeRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.wifiSection2Row = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.roamingSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.rContactsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.rPrivateRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.rGroupRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.rChannelsRow = i;
        if (this.currentType != 1) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.rSizeRow = i;
        } else {
            this.rSizeRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.roamingSection2Row = i;
        return true;
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
