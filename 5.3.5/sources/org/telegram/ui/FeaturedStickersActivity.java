package org.telegram.ui;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$InputStickerSet;
import org.telegram.tgnet.TLRPC$StickerSetCovered;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetShortName;
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
    private HashMap<Long, TLRPC$StickerSetCovered> installingStickerSets = new HashMap();
    private LinearLayoutManager layoutManager;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int rowCount;
    private int stickersEndRow;
    private int stickersShadowRow;
    private int stickersStartRow;
    private ArrayList<Long> unreadStickers = null;

    /* renamed from: org.telegram.ui.FeaturedStickersActivity$1 */
    class C29051 extends ActionBarMenuOnItemClick {
        C29051() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                FeaturedStickersActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.FeaturedStickersActivity$3 */
    class C29083 implements OnItemClickListener {
        C29083() {
        }

        public void onItemClick(final View view, int position) {
            if (position >= FeaturedStickersActivity.this.stickersStartRow && position < FeaturedStickersActivity.this.stickersEndRow && FeaturedStickersActivity.this.getParentActivity() != null) {
                TLRPC$InputStickerSet inputStickerSet;
                final TLRPC$StickerSetCovered stickerSet = (TLRPC$StickerSetCovered) StickersQuery.getFeaturedStickerSets().get(position);
                if (stickerSet.set.id != 0) {
                    inputStickerSet = new TLRPC$TL_inputStickerSetID();
                    inputStickerSet.id = stickerSet.set.id;
                } else {
                    inputStickerSet = new TLRPC$TL_inputStickerSetShortName();
                    inputStickerSet.short_name = stickerSet.set.short_name;
                }
                inputStickerSet.access_hash = stickerSet.set.access_hash;
                StickersAlert stickersAlert = new StickersAlert(FeaturedStickersActivity.this.getParentActivity(), FeaturedStickersActivity.this, inputStickerSet, null, null);
                stickersAlert.setInstallDelegate(new StickersAlertInstallDelegate() {
                    public void onStickerSetInstalled() {
                        view.setDrawProgress(true);
                        FeaturedStickersActivity.this.installingStickerSets.put(Long.valueOf(stickerSet.set.id), stickerSet);
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
        class C29091 implements OnClickListener {
            C29091() {
            }

            public void onClick(View v) {
                FeaturedStickerSetCell parent = (FeaturedStickerSetCell) v.getParent();
                TLRPC$StickerSetCovered pack = parent.getStickerSet();
                if (!FeaturedStickersActivity.this.installingStickerSets.containsKey(Long.valueOf(pack.set.id))) {
                    FeaturedStickersActivity.this.installingStickerSets.put(Long.valueOf(pack.set.id), pack);
                    StickersQuery.removeStickersSet(FeaturedStickersActivity.this.getParentActivity(), pack.set, 2, FeaturedStickersActivity.this, false);
                    parent.setDrawProgress(true);
                }
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return FeaturedStickersActivity.this.rowCount;
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            boolean z = true;
            if (getItemViewType(position) == 0) {
                boolean z2;
                ArrayList<TLRPC$StickerSetCovered> arrayList = StickersQuery.getFeaturedStickerSets();
                FeaturedStickerSetCell cell = holder.itemView;
                cell.setTag(Integer.valueOf(position));
                TLRPC$StickerSetCovered stickerSet = (TLRPC$StickerSetCovered) arrayList.get(position);
                if (position != arrayList.size() - 1) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (FeaturedStickersActivity.this.unreadStickers == null || !FeaturedStickersActivity.this.unreadStickers.contains(Long.valueOf(stickerSet.set.id))) {
                    z = false;
                }
                cell.setStickersSet(stickerSet, z2, z);
                boolean installing = FeaturedStickersActivity.this.installingStickerSets.containsKey(Long.valueOf(stickerSet.set.id));
                if (installing && cell.isInstalled()) {
                    FeaturedStickersActivity.this.installingStickerSets.remove(Long.valueOf(stickerSet.set.id));
                    installing = false;
                    cell.setDrawProgress(false);
                }
                cell.setDrawProgress(installing);
            }
        }

        public boolean isEnabled(ViewHolder holder) {
            return holder.getItemViewType() == 0;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 0:
                    view = new FeaturedStickerSetCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((FeaturedStickerSetCell) view).setAddOnClickListener(new C29091());
                    break;
                case 1:
                    view = new TextInfoPrivacyCell(this.mContext);
                    view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public int getItemViewType(int i) {
            if ((i < FeaturedStickersActivity.this.stickersStartRow || i >= FeaturedStickersActivity.this.stickersEndRow) && i == FeaturedStickersActivity.this.stickersShadowRow) {
                return 1;
            }
            return 0;
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        StickersQuery.checkFeaturedStickers();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.featuredStickersDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
        ArrayList<Long> arrayList = StickersQuery.getUnreadStickerSets();
        if (arrayList != null) {
            this.unreadStickers = new ArrayList(arrayList);
        }
        updateRows();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.featuredStickersDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stickersDidLoaded);
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("FeaturedStickers", R.string.FeaturedStickers));
        this.actionBar.setActionBarMenuOnItemClick(new C29051());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
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
        this.listView.setOnItemClickListener(new C29083());
        return this.fragmentView;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.featuredStickersDidLoaded) {
            if (this.unreadStickers == null) {
                this.unreadStickers = StickersQuery.getUnreadStickerSets();
            }
            updateRows();
        } else if (id == NotificationCenter.stickersDidLoaded) {
            updateVisibleTrendingSets();
        }
    }

    private void updateVisibleTrendingSets() {
        if (this.layoutManager != null) {
            int first = this.layoutManager.findFirstVisibleItemPosition();
            if (first != -1) {
                int last = this.layoutManager.findLastVisibleItemPosition();
                if (last != -1) {
                    this.listAdapter.notifyItemRangeChanged(first, (last - first) + 1);
                }
            }
        }
    }

    private void updateRows() {
        this.rowCount = 0;
        ArrayList<TLRPC$StickerSetCovered> stickerSets = StickersQuery.getFeaturedStickerSets();
        if (stickerSets.isEmpty()) {
            this.stickersStartRow = -1;
            this.stickersEndRow = -1;
            this.stickersShadowRow = -1;
        } else {
            this.stickersStartRow = this.rowCount;
            this.stickersEndRow = this.rowCount + stickerSets.size();
            this.rowCount += stickerSets.size();
            int i = this.rowCount;
            this.rowCount = i + 1;
            this.stickersShadowRow = i;
        }
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        StickersQuery.markFaturedStickersAsRead(true);
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
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
}
