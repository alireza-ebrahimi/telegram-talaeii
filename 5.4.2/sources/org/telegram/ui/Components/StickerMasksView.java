package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.GridLayoutManager;
import org.telegram.messenger.support.widget.GridLayoutManager.SpanSizeLookup;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.StickerEmojiCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate;
import org.telegram.ui.StickerPreviewViewer;

public class StickerMasksView extends FrameLayout implements NotificationCenterDelegate {
    private int currentType = 1;
    private int lastNotifyWidth;
    private Listener listener;
    private ArrayList<Document>[] recentStickers = new ArrayList[]{new ArrayList(), new ArrayList()};
    private int recentTabBum = -2;
    private ScrollSlidingTabStrip scrollSlidingTabStrip;
    private ArrayList<TLRPC$TL_messages_stickerSet>[] stickerSets = new ArrayList[]{new ArrayList(), new ArrayList()};
    private TextView stickersEmptyView;
    private StickersGridAdapter stickersGridAdapter;
    private RecyclerListView stickersGridView;
    private GridLayoutManager stickersLayoutManager;
    private OnItemClickListener stickersOnItemClickListener;
    private int stickersTabOffset;

    public interface Listener {
        void onStickerSelected(Document document);

        void onTypeChanged();
    }

    /* renamed from: org.telegram.ui.Components.StickerMasksView$2 */
    class C45982 extends SpanSizeLookup {
        C45982() {
        }

        public int getSpanSize(int i) {
            return i == StickerMasksView.this.stickersGridAdapter.totalItems ? StickerMasksView.this.stickersGridAdapter.stickersPerRow : 1;
        }
    }

    /* renamed from: org.telegram.ui.Components.StickerMasksView$3 */
    class C45993 implements OnTouchListener {
        C45993() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return StickerPreviewViewer.getInstance().onTouch(motionEvent, StickerMasksView.this.stickersGridView, StickerMasksView.this.getMeasuredHeight(), StickerMasksView.this.stickersOnItemClickListener, null);
        }
    }

    /* renamed from: org.telegram.ui.Components.StickerMasksView$4 */
    class C46004 implements OnItemClickListener {
        C46004() {
        }

        public void onItemClick(View view, int i) {
            if (view instanceof StickerEmojiCell) {
                StickerPreviewViewer.getInstance().reset();
                StickerEmojiCell stickerEmojiCell = (StickerEmojiCell) view;
                if (!stickerEmojiCell.isDisabled()) {
                    Document sticker = stickerEmojiCell.getSticker();
                    StickerMasksView.this.listener.onStickerSelected(sticker);
                    StickersQuery.addRecentSticker(1, sticker, (int) (System.currentTimeMillis() / 1000), false);
                    MessagesController.getInstance().saveRecentSticker(sticker, true);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.StickerMasksView$5 */
    class C46015 implements ScrollSlidingTabStripDelegate {
        C46015() {
        }

        public void onPageSelected(int i) {
            if (i == 0) {
                if (StickerMasksView.this.currentType == 0) {
                    StickerMasksView.this.currentType = 1;
                } else {
                    StickerMasksView.this.currentType = 0;
                }
                if (StickerMasksView.this.listener != null) {
                    StickerMasksView.this.listener.onTypeChanged();
                }
                StickerMasksView.this.recentStickers[StickerMasksView.this.currentType] = StickersQuery.getRecentStickers(StickerMasksView.this.currentType);
                StickerMasksView.this.stickersLayoutManager.scrollToPositionWithOffset(0, 0);
                StickerMasksView.this.updateStickerTabs();
                StickerMasksView.this.reloadStickersAdapter();
                StickerMasksView.this.checkDocuments();
                StickerMasksView.this.checkPanels();
            } else if (i == StickerMasksView.this.recentTabBum + 1) {
                StickerMasksView.this.stickersLayoutManager.scrollToPositionWithOffset(0, 0);
            } else {
                int access$1400 = (i - 1) - StickerMasksView.this.stickersTabOffset;
                if (access$1400 >= StickerMasksView.this.stickerSets[StickerMasksView.this.currentType].size()) {
                    access$1400 = StickerMasksView.this.stickerSets[StickerMasksView.this.currentType].size() - 1;
                }
                StickerMasksView.this.stickersLayoutManager.scrollToPositionWithOffset(StickerMasksView.this.stickersGridAdapter.getPositionForPack((TLRPC$TL_messages_stickerSet) StickerMasksView.this.stickerSets[StickerMasksView.this.currentType].get(access$1400)), 0);
                StickerMasksView.this.checkScroll();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.StickerMasksView$6 */
    class C46026 extends OnScrollListener {
        C46026() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            StickerMasksView.this.checkScroll();
        }
    }

    /* renamed from: org.telegram.ui.Components.StickerMasksView$7 */
    class C46037 implements Runnable {
        C46037() {
        }

        public void run() {
            StickerMasksView.this.updateStickerTabs();
            StickerMasksView.this.reloadStickersAdapter();
        }
    }

    private class StickersGridAdapter extends SelectionAdapter {
        private HashMap<Integer, Document> cache = new HashMap();
        private Context context;
        private HashMap<TLRPC$TL_messages_stickerSet, Integer> packStartRow = new HashMap();
        private HashMap<Integer, TLRPC$TL_messages_stickerSet> rowStartPack = new HashMap();
        private int stickersPerRow;
        private int totalItems;

        public StickersGridAdapter(Context context) {
            this.context = context;
        }

        public Object getItem(int i) {
            return this.cache.get(Integer.valueOf(i));
        }

        public int getItemCount() {
            return this.totalItems != 0 ? this.totalItems + 1 : 0;
        }

        public int getItemViewType(int i) {
            return this.cache.get(Integer.valueOf(i)) != null ? 0 : 1;
        }

        public int getPositionForPack(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            return ((Integer) this.packStartRow.get(tLRPC$TL_messages_stickerSet)).intValue() * this.stickersPerRow;
        }

        public int getTabForPosition(int i) {
            if (this.stickersPerRow == 0) {
                int measuredWidth = StickerMasksView.this.getMeasuredWidth();
                if (measuredWidth == 0) {
                    measuredWidth = AndroidUtilities.displaySize.x;
                }
                this.stickersPerRow = measuredWidth / AndroidUtilities.dp(72.0f);
            }
            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) this.rowStartPack.get(Integer.valueOf(i / this.stickersPerRow));
            return tLRPC$TL_messages_stickerSet == null ? StickerMasksView.this.recentTabBum : StickerMasksView.this.stickerSets[StickerMasksView.this.currentType].indexOf(tLRPC$TL_messages_stickerSet) + StickerMasksView.this.stickersTabOffset;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void notifyDataSetChanged() {
            int measuredWidth = StickerMasksView.this.getMeasuredWidth();
            if (measuredWidth == 0) {
                measuredWidth = AndroidUtilities.displaySize.x;
            }
            this.stickersPerRow = measuredWidth / AndroidUtilities.dp(72.0f);
            StickerMasksView.this.stickersLayoutManager.setSpanCount(this.stickersPerRow);
            this.rowStartPack.clear();
            this.packStartRow.clear();
            this.cache.clear();
            this.totalItems = 0;
            ArrayList arrayList = StickerMasksView.this.stickerSets[StickerMasksView.this.currentType];
            for (int i = -1; i < arrayList.size(); i++) {
                ArrayList arrayList2;
                Object obj = null;
                int i2 = this.totalItems / this.stickersPerRow;
                if (i == -1) {
                    arrayList2 = StickerMasksView.this.recentStickers[StickerMasksView.this.currentType];
                } else {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) arrayList.get(i);
                    arrayList2 = tLRPC$TL_messages_stickerSet.documents;
                    this.packStartRow.put(tLRPC$TL_messages_stickerSet, Integer.valueOf(i2));
                }
                if (!arrayList2.isEmpty()) {
                    int ceil = (int) Math.ceil((double) (((float) arrayList2.size()) / ((float) this.stickersPerRow)));
                    for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                        this.cache.put(Integer.valueOf(this.totalItems + i3), arrayList2.get(i3));
                    }
                    this.totalItems += this.stickersPerRow * ceil;
                    for (int i4 = 0; i4 < ceil; i4++) {
                        this.rowStartPack.put(Integer.valueOf(i2 + i4), obj);
                    }
                }
            }
            super.notifyDataSetChanged();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ((StickerEmojiCell) viewHolder.itemView).setSticker((Document) this.cache.get(Integer.valueOf(i)), false);
                    return;
                case 1:
                    if (i == this.totalItems) {
                        TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) this.rowStartPack.get(Integer.valueOf((i - 1) / this.stickersPerRow));
                        if (tLRPC$TL_messages_stickerSet == null) {
                            ((EmptyCell) viewHolder.itemView).setHeight(1);
                            return;
                        }
                        int measuredHeight = StickerMasksView.this.stickersGridView.getMeasuredHeight() - (((int) Math.ceil((double) (((float) tLRPC$TL_messages_stickerSet.documents.size()) / ((float) this.stickersPerRow)))) * AndroidUtilities.dp(82.0f));
                        EmptyCell emptyCell = (EmptyCell) viewHolder.itemView;
                        if (measuredHeight <= 0) {
                            measuredHeight = 1;
                        }
                        emptyCell.setHeight(measuredHeight);
                        return;
                    }
                    ((EmptyCell) viewHolder.itemView).setHeight(AndroidUtilities.dp(82.0f));
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                    view = new StickerEmojiCell(this.context) {
                        public void onMeasure(int i, int i2) {
                            super.onMeasure(i, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(82.0f), 1073741824));
                        }
                    };
                    break;
                case 1:
                    view = new EmptyCell(this.context);
                    break;
            }
            return new Holder(view);
        }
    }

    public StickerMasksView(Context context) {
        super(context);
        setBackgroundColor(-14540254);
        setClickable(true);
        StickersQuery.checkStickers(0);
        StickersQuery.checkStickers(1);
        this.stickersGridView = new RecyclerListView(context) {
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return super.onInterceptTouchEvent(motionEvent) || StickerPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, StickerMasksView.this.stickersGridView, StickerMasksView.this.getMeasuredHeight(), null);
            }
        };
        RecyclerListView recyclerListView = this.stickersGridView;
        LayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        this.stickersLayoutManager = gridLayoutManager;
        recyclerListView.setLayoutManager(gridLayoutManager);
        this.stickersLayoutManager.setSpanSizeLookup(new C45982());
        this.stickersGridView.setPadding(0, AndroidUtilities.dp(4.0f), 0, 0);
        this.stickersGridView.setClipToPadding(false);
        recyclerListView = this.stickersGridView;
        Adapter stickersGridAdapter = new StickersGridAdapter(context);
        this.stickersGridAdapter = stickersGridAdapter;
        recyclerListView.setAdapter(stickersGridAdapter);
        this.stickersGridView.setOnTouchListener(new C45993());
        this.stickersOnItemClickListener = new C46004();
        this.stickersGridView.setOnItemClickListener(this.stickersOnItemClickListener);
        this.stickersGridView.setGlowColor(-657673);
        addView(this.stickersGridView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.stickersEmptyView = new TextView(context);
        this.stickersEmptyView.setTextSize(1, 18.0f);
        this.stickersEmptyView.setTextColor(-7829368);
        addView(this.stickersEmptyView, LayoutHelper.createFrame(-2, -2.0f, 17, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.stickersGridView.setEmptyView(this.stickersEmptyView);
        this.scrollSlidingTabStrip = new ScrollSlidingTabStrip(context);
        this.scrollSlidingTabStrip.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.scrollSlidingTabStrip.setUnderlineHeight(AndroidUtilities.dp(1.0f));
        this.scrollSlidingTabStrip.setIndicatorColor(-10305560);
        this.scrollSlidingTabStrip.setUnderlineColor(-15066598);
        this.scrollSlidingTabStrip.setIndicatorHeight(AndroidUtilities.dp(1.0f) + 1);
        addView(this.scrollSlidingTabStrip, LayoutHelper.createFrame(-1, 48, 51));
        updateStickerTabs();
        this.scrollSlidingTabStrip.setDelegate(new C46015());
        this.stickersGridView.setOnScrollListener(new C46026());
    }

    private void checkDocuments() {
        int size = this.recentStickers[this.currentType].size();
        this.recentStickers[this.currentType] = StickersQuery.getRecentStickers(this.currentType);
        if (this.stickersGridAdapter != null) {
            this.stickersGridAdapter.notifyDataSetChanged();
        }
        if (size != this.recentStickers[this.currentType].size()) {
            updateStickerTabs();
        }
    }

    private void checkPanels() {
        if (this.scrollSlidingTabStrip != null) {
            int findFirstVisibleItemPosition = this.stickersLayoutManager.findFirstVisibleItemPosition();
            if (findFirstVisibleItemPosition != -1) {
                this.scrollSlidingTabStrip.onPageScrolled(this.stickersGridAdapter.getTabForPosition(findFirstVisibleItemPosition) + 1, (this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset) + 1);
            }
        }
    }

    private void checkScroll() {
        int findFirstVisibleItemPosition = this.stickersLayoutManager.findFirstVisibleItemPosition();
        if (findFirstVisibleItemPosition != -1) {
            checkStickersScroll(findFirstVisibleItemPosition);
        }
    }

    private void checkStickersScroll(int i) {
        if (this.stickersGridView != null) {
            this.scrollSlidingTabStrip.onPageScrolled(this.stickersGridAdapter.getTabForPosition(i) + 1, (this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset) + 1);
        }
    }

    private void reloadStickersAdapter() {
        if (this.stickersGridAdapter != null) {
            this.stickersGridAdapter.notifyDataSetChanged();
        }
        if (StickerPreviewViewer.getInstance().isVisible()) {
            StickerPreviewViewer.getInstance().close();
        }
        StickerPreviewViewer.getInstance().reset();
    }

    private void updateStickerTabs() {
        if (this.scrollSlidingTabStrip != null) {
            int i;
            this.recentTabBum = -2;
            this.stickersTabOffset = 0;
            int currentPosition = this.scrollSlidingTabStrip.getCurrentPosition();
            this.scrollSlidingTabStrip.removeTabs();
            Drawable drawable;
            if (this.currentType == 0) {
                drawable = getContext().getResources().getDrawable(R.drawable.ic_masks_msk1);
                Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
                this.scrollSlidingTabStrip.addIconTab(drawable);
                this.stickersEmptyView.setText(LocaleController.getString("NoStickers", R.string.NoStickers));
            } else {
                drawable = getContext().getResources().getDrawable(R.drawable.ic_masks_sticker1);
                Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
                this.scrollSlidingTabStrip.addIconTab(drawable);
                this.stickersEmptyView.setText(LocaleController.getString("NoMasks", R.string.NoMasks));
            }
            if (!this.recentStickers[this.currentType].isEmpty()) {
                this.recentTabBum = this.stickersTabOffset;
                this.stickersTabOffset++;
                this.scrollSlidingTabStrip.addIconTab(Theme.createEmojiIconSelectorDrawable(getContext(), R.drawable.ic_masks_recent1, Theme.getColor(Theme.key_chat_emojiPanelMasksIcon), Theme.getColor(Theme.key_chat_emojiPanelMasksIconSelected)));
            }
            this.stickerSets[this.currentType].clear();
            ArrayList stickerSets = StickersQuery.getStickerSets(this.currentType);
            for (i = 0; i < stickerSets.size(); i++) {
                TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) stickerSets.get(i);
                if (!(tLRPC$TL_messages_stickerSet.set.archived || tLRPC$TL_messages_stickerSet.documents == null || tLRPC$TL_messages_stickerSet.documents.isEmpty())) {
                    this.stickerSets[this.currentType].add(tLRPC$TL_messages_stickerSet);
                }
            }
            for (i = 0; i < this.stickerSets[this.currentType].size(); i++) {
                this.scrollSlidingTabStrip.addStickerTab((Document) ((TLRPC$TL_messages_stickerSet) this.stickerSets[this.currentType].get(i)).documents.get(0));
            }
            this.scrollSlidingTabStrip.updateTabStyles();
            if (currentPosition != 0) {
                this.scrollSlidingTabStrip.onPageScrolled(currentPosition, currentPosition);
            }
            checkPanels();
        }
    }

    public void addRecentSticker(Document document) {
        if (document != null) {
            StickersQuery.addRecentSticker(this.currentType, document, (int) (System.currentTimeMillis() / 1000), false);
            boolean isEmpty = this.recentStickers[this.currentType].isEmpty();
            this.recentStickers[this.currentType] = StickersQuery.getRecentStickers(this.currentType);
            if (this.stickersGridAdapter != null) {
                this.stickersGridAdapter.notifyDataSetChanged();
            }
            if (isEmpty) {
                updateStickerTabs();
            }
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.stickersDidLoaded) {
            if (((Integer) objArr[0]).intValue() == this.currentType) {
                updateStickerTabs();
                reloadStickersAdapter();
                checkPanels();
            }
        } else if (i == NotificationCenter.recentDocumentsDidLoaded && !((Boolean) objArr[0]).booleanValue() && ((Integer) objArr[1]).intValue() == this.currentType) {
            checkDocuments();
        }
    }

    public int getCurrentType() {
        return this.currentType;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.recentImagesDidLoaded);
        AndroidUtilities.runOnUIThread(new C46037());
    }

    public void onDestroy() {
        if (this.stickersGridAdapter != null) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stickersDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.recentDocumentsDidLoaded);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.lastNotifyWidth != i3 - i) {
            this.lastNotifyWidth = i3 - i;
            reloadStickersAdapter();
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i != 8) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.recentDocumentsDidLoaded);
            updateStickerTabs();
            reloadStickersAdapter();
            checkDocuments();
            StickersQuery.loadRecents(0, false, true, false);
            StickersQuery.loadRecents(1, false, true, false);
            StickersQuery.loadRecents(2, false, true, false);
        }
    }
}
