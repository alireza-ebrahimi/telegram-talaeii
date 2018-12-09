package org.telegram.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetShortName;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.StickerSetCovered;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.FeaturedStickerSetCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.StickersAlert.StickersAlertInstallDelegate;

public class FeaturedStickersActivity extends BaseFragment implements NotificationCenterDelegate {
    private HashMap<Long, StickerSetCovered> installingStickerSets = new HashMap();
    private LinearLayoutManager layoutManager;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int rowCount;
    private int stickersEndRow;
    private int stickersShadowRow;
    private int stickersStartRow;
    private ArrayList<Long> unreadStickers = null;

    /* renamed from: org.telegram.ui.FeaturedStickersActivity$1 */
    class C47431 extends ActionBarMenuOnItemClick {
        C47431() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                FeaturedStickersActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.FeaturedStickersActivity$3 */
    class C47463 implements OnItemClickListener {
        C47463() {
        }

        public void onItemClick(final View view, int i) {
            if (i >= FeaturedStickersActivity.this.stickersStartRow && i < FeaturedStickersActivity.this.stickersEndRow && FeaturedStickersActivity.this.getParentActivity() != null) {
                InputStickerSet tLRPC$TL_inputStickerSetID;
                final StickerSetCovered stickerSetCovered = (StickerSetCovered) StickersQuery.getFeaturedStickerSets().get(i);
                if (stickerSetCovered.set.id != 0) {
                    tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
                    tLRPC$TL_inputStickerSetID.id = stickerSetCovered.set.id;
                } else {
                    tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetShortName();
                    tLRPC$TL_inputStickerSetID.short_name = stickerSetCovered.set.short_name;
                }
                tLRPC$TL_inputStickerSetID.access_hash = stickerSetCovered.set.access_hash;
                Dialog stickersAlert = new StickersAlert(FeaturedStickersActivity.this.getParentActivity(), FeaturedStickersActivity.this, tLRPC$TL_inputStickerSetID, null, null);
                stickersAlert.setInstallDelegate(new StickersAlertInstallDelegate() {
                    public void onStickerSetInstalled() {
                        ((FeaturedStickerSetCell) view).setDrawProgress(true);
                        FeaturedStickersActivity.this.installingStickerSets.put(Long.valueOf(stickerSetCovered.set.id), stickerSetCovered);
                    }

                    public void onStickerSetUninstalled() {
                    }
                });
                FeaturedStickersActivity.this.showDialog(stickersAlert);
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.FeaturedStickersActivity$ListAdapter$1 */
        class C47471 implements OnClickListener {
            C47471() {
            }

            public void onClick(View view) {
                FeaturedStickerSetCell featuredStickerSetCell = (FeaturedStickerSetCell) view.getParent();
                StickerSetCovered stickerSet = featuredStickerSetCell.getStickerSet();
                if (!FeaturedStickersActivity.this.installingStickerSets.containsKey(Long.valueOf(stickerSet.set.id))) {
                    FeaturedStickersActivity.this.installingStickerSets.put(Long.valueOf(stickerSet.set.id), stickerSet);
                    StickersQuery.removeStickersSet(FeaturedStickersActivity.this.getParentActivity(), stickerSet.set, 2, FeaturedStickersActivity.this, false);
                    featuredStickerSetCell.setDrawProgress(true);
                }
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return FeaturedStickersActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return ((i < FeaturedStickersActivity.this.stickersStartRow || i >= FeaturedStickersActivity.this.stickersEndRow) && i == FeaturedStickersActivity.this.stickersShadowRow) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            boolean z2 = false;
            if (getItemViewType(i) == 0) {
                ArrayList featuredStickerSets = StickersQuery.getFeaturedStickerSets();
                FeaturedStickerSetCell featuredStickerSetCell = (FeaturedStickerSetCell) viewHolder.itemView;
                featuredStickerSetCell.setTag(Integer.valueOf(i));
                StickerSetCovered stickerSetCovered = (StickerSetCovered) featuredStickerSets.get(i);
                boolean z3 = i != featuredStickerSets.size() + -1;
                if (FeaturedStickersActivity.this.unreadStickers == null || !FeaturedStickersActivity.this.unreadStickers.contains(Long.valueOf(stickerSetCovered.set.id))) {
                    z = false;
                }
                featuredStickerSetCell.setStickersSet(stickerSetCovered, z3, z);
                z3 = FeaturedStickersActivity.this.installingStickerSets.containsKey(Long.valueOf(stickerSetCovered.set.id));
                if (z3 && featuredStickerSetCell.isInstalled()) {
                    FeaturedStickersActivity.this.installingStickerSets.remove(Long.valueOf(stickerSetCovered.set.id));
                    featuredStickerSetCell.setDrawProgress(false);
                } else {
                    z2 = z3;
                }
                featuredStickerSetCell.setDrawProgress(z2);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                    view = new FeaturedStickerSetCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((FeaturedStickerSetCell) view).setAddOnClickListener(new C47471());
                    break;
                case 1:
                    view = new TextInfoPrivacyCell(this.mContext);
                    view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }
    }

    private void updateRows() {
        this.rowCount = 0;
        ArrayList featuredStickerSets = StickersQuery.getFeaturedStickerSets();
        if (featuredStickerSets.isEmpty()) {
            this.stickersStartRow = -1;
            this.stickersEndRow = -1;
            this.stickersShadowRow = -1;
        } else {
            this.stickersStartRow = this.rowCount;
            this.stickersEndRow = this.rowCount + featuredStickerSets.size();
            this.rowCount = featuredStickerSets.size() + this.rowCount;
            int i = this.rowCount;
            this.rowCount = i + 1;
            this.stickersShadowRow = i;
        }
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        StickersQuery.markFaturedStickersAsRead(true);
    }

    private void updateVisibleTrendingSets() {
        if (this.layoutManager != null) {
            int findFirstVisibleItemPosition = this.layoutManager.findFirstVisibleItemPosition();
            if (findFirstVisibleItemPosition != -1) {
                int findLastVisibleItemPosition = this.layoutManager.findLastVisibleItemPosition();
                if (findLastVisibleItemPosition != -1) {
                    this.listAdapter.notifyItemRangeChanged(findFirstVisibleItemPosition, (findLastVisibleItemPosition - findFirstVisibleItemPosition) + 1);
                }
            }
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("FeaturedStickers", R.string.FeaturedStickers));
        this.actionBar.setActionBarMenuOnItemClick(new C47431());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setFocusable(true);
        this.listView.setTag(Integer.valueOf(14));
        this.layoutManager = new LinearLayoutManager(context) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.layoutManager.setOrientation(1);
        this.listView.setLayoutManager(this.layoutManager);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C47463());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.featuredStickersDidLoaded) {
            if (this.unreadStickers == null) {
                this.unreadStickers = StickersQuery.getUnreadStickerSets();
            }
            updateRows();
        } else if (i == NotificationCenter.stickersDidLoaded) {
            updateVisibleTrendingSets();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[17];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{FeaturedStickerSetCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{FeaturedStickerSetCell.class}, new String[]{"progressPaint"}, null, null, null, Theme.key_featuredStickers_buttonProgress);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, 0, new Class[]{FeaturedStickerSetCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{FeaturedStickerSetCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{FeaturedStickerSetCell.class}, new String[]{"addButton"}, null, null, null, Theme.key_featuredStickers_buttonText);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{FeaturedStickerSetCell.class}, new String[]{"checkImage"}, null, null, null, Theme.key_featuredStickers_addedIcon);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{FeaturedStickerSetCell.class}, new String[]{"addButton"}, null, null, null, Theme.key_featuredStickers_addButton);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, new Class[]{FeaturedStickerSetCell.class}, new String[]{"addButton"}, null, null, null, Theme.key_featuredStickers_addButtonPressed);
        return themeDescriptionArr;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        StickersQuery.checkFeaturedStickers();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.featuredStickersDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
        Collection unreadStickerSets = StickersQuery.getUnreadStickerSets();
        if (unreadStickerSets != null) {
            this.unreadStickers = new ArrayList(unreadStickerSets);
        }
        updateRows();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.featuredStickersDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stickersDidLoaded);
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
