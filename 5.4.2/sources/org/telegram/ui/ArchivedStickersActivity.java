package org.telegram.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetShortName;
import org.telegram.tgnet.TLRPC$TL_messages_archivedStickers;
import org.telegram.tgnet.TLRPC$TL_messages_getArchivedStickers;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.StickerSetCovered;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.ArchivedStickerSetCell;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.StickersAlert.StickersAlertInstallDelegate;

public class ArchivedStickersActivity extends BaseFragment implements NotificationCenterDelegate {
    private int currentType;
    private EmptyTextProgressView emptyView;
    private boolean endReached;
    private boolean firstLoaded;
    private LinearLayoutManager layoutManager;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private boolean loadingStickers;
    private int rowCount;
    private ArrayList<StickerSetCovered> sets = new ArrayList();
    private int stickersEndRow;
    private int stickersLoadingRow;
    private int stickersShadowRow;
    private int stickersStartRow;

    /* renamed from: org.telegram.ui.ArchivedStickersActivity$1 */
    class C38981 extends ActionBarMenuOnItemClick {
        C38981() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ArchivedStickersActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ArchivedStickersActivity$2 */
    class C39002 implements OnItemClickListener {
        C39002() {
        }

        public void onItemClick(final View view, int i) {
            if (i >= ArchivedStickersActivity.this.stickersStartRow && i < ArchivedStickersActivity.this.stickersEndRow && ArchivedStickersActivity.this.getParentActivity() != null) {
                InputStickerSet tLRPC$TL_inputStickerSetID;
                StickerSetCovered stickerSetCovered = (StickerSetCovered) ArchivedStickersActivity.this.sets.get(i);
                if (stickerSetCovered.set.id != 0) {
                    tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
                    tLRPC$TL_inputStickerSetID.id = stickerSetCovered.set.id;
                } else {
                    tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetShortName();
                    tLRPC$TL_inputStickerSetID.short_name = stickerSetCovered.set.short_name;
                }
                tLRPC$TL_inputStickerSetID.access_hash = stickerSetCovered.set.access_hash;
                Dialog stickersAlert = new StickersAlert(ArchivedStickersActivity.this.getParentActivity(), ArchivedStickersActivity.this, tLRPC$TL_inputStickerSetID, null, null);
                stickersAlert.setInstallDelegate(new StickersAlertInstallDelegate() {
                    public void onStickerSetInstalled() {
                        ((ArchivedStickerSetCell) view).setChecked(true);
                    }

                    public void onStickerSetUninstalled() {
                        ((ArchivedStickerSetCell) view).setChecked(false);
                    }
                });
                ArchivedStickersActivity.this.showDialog(stickersAlert);
            }
        }
    }

    /* renamed from: org.telegram.ui.ArchivedStickersActivity$3 */
    class C39013 extends OnScrollListener {
        C39013() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            if (!ArchivedStickersActivity.this.loadingStickers && !ArchivedStickersActivity.this.endReached && ArchivedStickersActivity.this.layoutManager.findLastVisibleItemPosition() > ArchivedStickersActivity.this.stickersLoadingRow - 2) {
                ArchivedStickersActivity.this.getStickers();
            }
        }
    }

    /* renamed from: org.telegram.ui.ArchivedStickersActivity$4 */
    class C39034 implements RequestDelegate {
        C39034() {
        }

        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (tLRPC$TL_error == null) {
                        TLRPC$TL_messages_archivedStickers tLRPC$TL_messages_archivedStickers = (TLRPC$TL_messages_archivedStickers) tLObject;
                        ArchivedStickersActivity.this.sets.addAll(tLRPC$TL_messages_archivedStickers.sets);
                        ArchivedStickersActivity.this.endReached = tLRPC$TL_messages_archivedStickers.sets.size() != 15;
                        ArchivedStickersActivity.this.loadingStickers = false;
                        ArchivedStickersActivity.this.firstLoaded = true;
                        if (ArchivedStickersActivity.this.emptyView != null) {
                            ArchivedStickersActivity.this.emptyView.showTextView();
                        }
                        ArchivedStickersActivity.this.updateRows();
                    }
                }
            });
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.ArchivedStickersActivity$ListAdapter$1 */
        class C39041 implements OnCheckedChangeListener {
            C39041() {
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                int intValue = ((Integer) ((ArchivedStickerSetCell) compoundButton.getParent()).getTag()).intValue();
                if (intValue < ArchivedStickersActivity.this.sets.size()) {
                    StickersQuery.removeStickersSet(ArchivedStickersActivity.this.getParentActivity(), ((StickerSetCovered) ArchivedStickersActivity.this.sets.get(intValue)).set, !z ? 1 : 2, ArchivedStickersActivity.this, false);
                }
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return ArchivedStickersActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i < ArchivedStickersActivity.this.stickersStartRow || i >= ArchivedStickersActivity.this.stickersEndRow) ? i == ArchivedStickersActivity.this.stickersLoadingRow ? 1 : i == ArchivedStickersActivity.this.stickersShadowRow ? 2 : 0 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (getItemViewType(i) == 0) {
                ArchivedStickerSetCell archivedStickerSetCell = (ArchivedStickerSetCell) viewHolder.itemView;
                archivedStickerSetCell.setTag(Integer.valueOf(i));
                StickerSetCovered stickerSetCovered = (StickerSetCovered) ArchivedStickersActivity.this.sets.get(i);
                archivedStickerSetCell.setStickersSet(stickerSetCovered, i != ArchivedStickersActivity.this.sets.size() + -1);
                archivedStickerSetCell.setChecked(StickersQuery.isStickerPackInstalled(stickerSetCovered.set.id));
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                    view = new ArchivedStickerSetCell(this.mContext, true);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((ArchivedStickerSetCell) view).setOnCheckClick(new C39041());
                    break;
                case 1:
                    view = new LoadingCell(this.mContext);
                    view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
                case 2:
                    view = new TextInfoPrivacyCell(this.mContext);
                    view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }
    }

    public ArchivedStickersActivity(int i) {
        this.currentType = i;
    }

    private void getStickers() {
        if (!this.loadingStickers && !this.endReached) {
            this.loadingStickers = true;
            if (!(this.emptyView == null || this.firstLoaded)) {
                this.emptyView.showProgress();
            }
            if (this.listAdapter != null) {
                this.listAdapter.notifyDataSetChanged();
            }
            TLObject tLRPC$TL_messages_getArchivedStickers = new TLRPC$TL_messages_getArchivedStickers();
            tLRPC$TL_messages_getArchivedStickers.offset_id = this.sets.isEmpty() ? 0 : ((StickerSetCovered) this.sets.get(this.sets.size() - 1)).set.id;
            tLRPC$TL_messages_getArchivedStickers.limit = 15;
            tLRPC$TL_messages_getArchivedStickers.masks = this.currentType == 1;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getArchivedStickers, new C39034()), this.classGuid);
        }
    }

    private void updateRows() {
        this.rowCount = 0;
        if (this.sets.isEmpty()) {
            this.stickersStartRow = -1;
            this.stickersEndRow = -1;
            this.stickersLoadingRow = -1;
            this.stickersShadowRow = -1;
        } else {
            this.stickersStartRow = this.rowCount;
            this.stickersEndRow = this.rowCount + this.sets.size();
            this.rowCount += this.sets.size();
            int i;
            if (this.endReached) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.stickersShadowRow = i;
                this.stickersLoadingRow = -1;
            } else {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.stickersLoadingRow = i;
                this.stickersShadowRow = -1;
            }
        }
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.currentType == 0) {
            this.actionBar.setTitle(LocaleController.getString("ArchivedStickers", R.string.ArchivedStickers));
        } else {
            this.actionBar.setTitle(LocaleController.getString("ArchivedMasks", R.string.ArchivedMasks));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C38981());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.emptyView = new EmptyTextProgressView(context);
        if (this.currentType == 0) {
            this.emptyView.setText(LocaleController.getString("ArchivedStickersEmpty", R.string.ArchivedStickersEmpty));
        } else {
            this.emptyView.setText(LocaleController.getString("ArchivedMasksEmpty", R.string.ArchivedMasksEmpty));
        }
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        if (this.loadingStickers) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        this.listView = new RecyclerListView(context);
        this.listView.setFocusable(true);
        this.listView.setEmptyView(this.emptyView);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.layoutManager = linearLayoutManager;
        recyclerListView.setLayoutManager(linearLayoutManager);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C39002());
        this.listView.setOnScrollListener(new C39013());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.needReloadArchivedStickers) {
            this.firstLoaded = false;
            this.endReached = false;
            this.sets.clear();
            updateRows();
            if (this.emptyView != null) {
                this.emptyView.showProgress();
            }
            getStickers();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[19];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ArchivedStickerSetCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{LoadingCell.class, TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[10] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[11] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{ArchivedStickerSetCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{ArchivedStickerSetCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{ArchivedStickerSetCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{ArchivedStickerSetCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, 0, new Class[]{ArchivedStickerSetCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{ArchivedStickerSetCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        return themeDescriptionArr;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        getStickers();
        updateRows();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.needReloadArchivedStickers);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.needReloadArchivedStickers);
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
