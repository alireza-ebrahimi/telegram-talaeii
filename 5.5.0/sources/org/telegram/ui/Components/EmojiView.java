package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0188f;
import android.support.v4.view.aa;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.EmojiData;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.GridLayoutManager;
import org.telegram.messenger.support.widget.GridLayoutManager.SpanSizeLookup;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.StickerSet;
import org.telegram.tgnet.TLRPC.StickerSetCovered;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ContextLinkCell;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.FeaturedStickerSetInfoCell;
import org.telegram.ui.Cells.StickerEmojiCell;
import org.telegram.ui.Cells.StickerSetGroupInfoCell;
import org.telegram.ui.Cells.StickerSetNameCell;
import org.telegram.ui.Components.PagerSlidingTabStrip.IconTabProvider;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate;
import org.telegram.ui.StickerPreviewViewer;
import org.telegram.ui.StickerPreviewViewer.StickerPreviewViewerDelegate;

public class EmojiView extends FrameLayout implements NotificationCenterDelegate {
    private static final OnScrollChangedListener NOP = new C44102();
    private static final Field superListenerField;
    private ArrayList<EmojiGridAdapter> adapters = new ArrayList();
    private ImageView backspaceButton;
    private boolean backspaceOnce;
    private boolean backspacePressed;
    private int currentBackgroundType = -1;
    private int currentChatId;
    private int currentPage;
    private Paint dotPaint;
    private DragListener dragListener;
    private ArrayList<GridView> emojiGrids = new ArrayList();
    private int emojiSize;
    private LinearLayout emojiTab;
    private int favTabBum = -2;
    private ArrayList<Document> favouriteStickers = new ArrayList();
    private int featuredStickersHash;
    private ExtendedGridLayoutManager flowLayoutManager;
    private int gifTabNum = -2;
    private GifsAdapter gifsAdapter;
    private RecyclerListView gifsGridView;
    private int groupStickerPackNum;
    private int groupStickerPackPosition;
    private TLRPC$TL_messages_stickerSet groupStickerSet;
    private boolean groupStickersHidden;
    private Drawable[] icons;
    private ChatFull info;
    private HashMap<Long, StickerSetCovered> installingStickerSets = new HashMap();
    private boolean isLayout;
    private int lastNotifyWidth;
    private Listener listener;
    private int[] location = new int[2];
    private TextView mediaBanTooltip;
    private int minusDy;
    private int oldWidth;
    private Object outlineProvider;
    private ViewPager pager;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private EmojiColorPickerView pickerView;
    private EmojiPopupWindow pickerViewPopup;
    private int popupHeight;
    private int popupWidth;
    private ArrayList<Document> recentGifs = new ArrayList();
    private ArrayList<Document> recentStickers = new ArrayList();
    private int recentTabBum = -2;
    private HashMap<Long, StickerSetCovered> removingStickerSets = new HashMap();
    private boolean showGifs;
    private StickerPreviewViewerDelegate stickerPreviewViewerDelegate = new C44071();
    private ArrayList<TLRPC$TL_messages_stickerSet> stickerSets = new ArrayList();
    private TextView stickersEmptyView;
    private StickersGridAdapter stickersGridAdapter;
    private RecyclerListView stickersGridView;
    private GridLayoutManager stickersLayoutManager;
    private OnItemClickListener stickersOnItemClickListener;
    private ScrollSlidingTabStrip stickersTab;
    private int stickersTabOffset;
    private FrameLayout stickersWrap;
    private boolean switchToGifTab;
    private TrendingGridAdapter trendingGridAdapter;
    private RecyclerListView trendingGridView;
    private GridLayoutManager trendingLayoutManager;
    private boolean trendingLoaded;
    private int trendingTabNum = -2;
    private ArrayList<View> views = new ArrayList();

    public interface Listener {
        boolean onBackspace();

        void onClearEmojiRecent();

        void onEmojiSelected(String str);

        void onGifSelected(Document document);

        void onGifTab(boolean z);

        void onShowStickerSet(StickerSet stickerSet, InputStickerSet inputStickerSet);

        void onStickerSelected(Document document);

        void onStickerSetAdd(StickerSetCovered stickerSetCovered);

        void onStickerSetRemove(StickerSetCovered stickerSetCovered);

        void onStickersGroupClick(int i);

        void onStickersSettingsClick();

        void onStickersTab(boolean z);
    }

    public interface DragListener {
        void onDrag(int i);

        void onDragCancel();

        void onDragEnd(float f);

        void onDragStart();
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$1 */
    class C44071 implements StickerPreviewViewerDelegate {
        C44071() {
        }

        public void openSet(InputStickerSet inputStickerSet) {
            if (inputStickerSet != null) {
                EmojiView.this.listener.onShowStickerSet(null, inputStickerSet);
            }
        }

        public void sentSticker(Document document) {
            EmojiView.this.listener.onStickerSelected(document);
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$2 */
    static class C44102 implements OnScrollChangedListener {
        C44102() {
        }

        public void onScrollChanged() {
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$3 */
    class C44113 extends ViewOutlineProvider {
        C44113() {
        }

        @TargetApi(21)
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(view.getPaddingLeft(), view.getPaddingTop(), view.getMeasuredWidth() - view.getPaddingRight(), view.getMeasuredHeight() - view.getPaddingBottom(), (float) AndroidUtilities.dp(6.0f));
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$5 */
    class C44135 extends SpanSizeLookup {
        C44135() {
        }

        public int getSpanSize(int i) {
            return (i == EmojiView.this.stickersGridAdapter.totalItems || !(EmojiView.this.stickersGridAdapter.cache.get(Integer.valueOf(i)) == null || (EmojiView.this.stickersGridAdapter.cache.get(Integer.valueOf(i)) instanceof Document))) ? EmojiView.this.stickersGridAdapter.stickersPerRow : 1;
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$6 */
    class C44146 implements OnTouchListener {
        C44146() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return StickerPreviewViewer.getInstance().onTouch(motionEvent, EmojiView.this.stickersGridView, EmojiView.this.getMeasuredHeight(), EmojiView.this.stickersOnItemClickListener, EmojiView.this.stickerPreviewViewerDelegate);
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$7 */
    class C44157 implements OnItemClickListener {
        C44157() {
        }

        public void onItemClick(View view, int i) {
            if (view instanceof StickerEmojiCell) {
                StickerPreviewViewer.getInstance().reset();
                StickerEmojiCell stickerEmojiCell = (StickerEmojiCell) view;
                if (!stickerEmojiCell.isDisabled()) {
                    stickerEmojiCell.disable();
                    EmojiView.this.listener.onStickerSelected(stickerEmojiCell.getSticker());
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$9 */
    class C44179 extends SpanSizeLookup {
        C44179() {
        }

        public int getSpanSize(int i) {
            return ((EmojiView.this.trendingGridAdapter.cache.get(Integer.valueOf(i)) instanceof Integer) || i == EmojiView.this.trendingGridAdapter.totalItems) ? EmojiView.this.trendingGridAdapter.stickersPerRow : 1;
        }
    }

    private class EmojiColorPickerView extends View {
        private Drawable arrowDrawable = getResources().getDrawable(R.drawable.stickers_back_arrow);
        private int arrowX;
        private Drawable backgroundDrawable = getResources().getDrawable(R.drawable.stickers_back_all);
        private String currentEmoji;
        private RectF rect = new RectF();
        private Paint rectPaint = new Paint(1);
        private int selection;

        public EmojiColorPickerView(Context context) {
            super(context);
        }

        public String getEmoji() {
            return this.currentEmoji;
        }

        public int getSelection() {
            return this.selection;
        }

        protected void onDraw(Canvas canvas) {
            float f = 55.5f;
            this.backgroundDrawable.setBounds(0, 0, getMeasuredWidth(), AndroidUtilities.dp(AndroidUtilities.isTablet() ? 60.0f : 52.0f));
            this.backgroundDrawable.draw(canvas);
            Drawable drawable = this.arrowDrawable;
            int dp = this.arrowX - AndroidUtilities.dp(9.0f);
            int dp2 = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 55.5f : 47.5f);
            int dp3 = this.arrowX + AndroidUtilities.dp(9.0f);
            if (!AndroidUtilities.isTablet()) {
                f = 47.5f;
            }
            drawable.setBounds(dp, dp2, dp3, AndroidUtilities.dp(f + 8.0f));
            this.arrowDrawable.draw(canvas);
            if (this.currentEmoji != null) {
                for (int i = 0; i < 6; i++) {
                    String str;
                    int access$900 = (EmojiView.this.emojiSize * i) + AndroidUtilities.dp((float) ((i * 4) + 5));
                    int dp4 = AndroidUtilities.dp(9.0f);
                    if (this.selection == i) {
                        this.rect.set((float) access$900, (float) (dp4 - ((int) AndroidUtilities.dpf2(3.5f))), (float) (EmojiView.this.emojiSize + access$900), (float) ((EmojiView.this.emojiSize + dp4) + AndroidUtilities.dp(3.0f)));
                        canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(4.0f), this.rectPaint);
                    }
                    String str2 = this.currentEmoji;
                    if (i != 0) {
                        switch (i) {
                            case 1:
                                str = "🏻";
                                break;
                            case 2:
                                str = "🏼";
                                break;
                            case 3:
                                str = "🏽";
                                break;
                            case 4:
                                str = "🏾";
                                break;
                            case 5:
                                str = "🏿";
                                break;
                            default:
                                str = TtmlNode.ANONYMOUS_REGION_ID;
                                break;
                        }
                        str = EmojiView.addColorToCode(str2, str);
                    } else {
                        str = str2;
                    }
                    Drawable emojiBigDrawable = Emoji.getEmojiBigDrawable(str);
                    if (emojiBigDrawable != null) {
                        emojiBigDrawable.setBounds(access$900, dp4, EmojiView.this.emojiSize + access$900, EmojiView.this.emojiSize + dp4);
                        emojiBigDrawable.draw(canvas);
                    }
                }
            }
        }

        public void setEmoji(String str, int i) {
            this.currentEmoji = str;
            this.arrowX = i;
            this.rectPaint.setColor(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR);
            invalidate();
        }

        public void setSelection(int i) {
            if (this.selection != i) {
                this.selection = i;
                invalidate();
            }
        }
    }

    private class EmojiGridAdapter extends BaseAdapter {
        private int emojiPage;

        public EmojiGridAdapter(int i) {
            this.emojiPage = i;
        }

        public int getCount() {
            return this.emojiPage == -1 ? Emoji.recentEmoji.size() : EmojiData.dataColored[this.emojiPage].length;
        }

        public Object getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            String str;
            Object obj;
            view = (ImageViewEmoji) view;
            if (view == null) {
                view = new ImageViewEmoji(EmojiView.this.getContext());
            }
            if (this.emojiPage == -1) {
                str = (String) Emoji.recentEmoji.get(i);
                obj = str;
            } else {
                String str2 = EmojiData.dataColored[this.emojiPage][i];
                str = (String) Emoji.emojiColor.get(str2);
                str = str != null ? EmojiView.addColorToCode(str2, str) : str2;
            }
            view.setImageDrawable(Emoji.getEmojiBigDrawable(str));
            view.setTag(obj);
            return view;
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (dataSetObserver != null) {
                super.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    private class EmojiPagesAdapter extends aa implements IconTabProvider {
        private EmojiPagesAdapter() {
        }

        public boolean canScrollToTab(int i) {
            if (i != 6 || EmojiView.this.currentChatId == 0) {
                return true;
            }
            EmojiView.this.showStickerBanHint();
            return false;
        }

        public void customOnDraw(Canvas canvas, int i) {
            if (i == 6 && !StickersQuery.getUnreadStickerSets().isEmpty() && EmojiView.this.dotPaint != null) {
                canvas.drawCircle((float) ((canvas.getWidth() / 2) + AndroidUtilities.dp(9.0f)), (float) ((canvas.getHeight() / 2) - AndroidUtilities.dp(8.0f)), (float) AndroidUtilities.dp(5.0f), EmojiView.this.dotPaint);
            }
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView(i == 6 ? EmojiView.this.stickersWrap : (View) EmojiView.this.views.get(i));
        }

        public int getCount() {
            return EmojiView.this.views.size();
        }

        public Drawable getPageIconDrawable(int i) {
            return EmojiView.this.icons[i];
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View access$7000 = i == 6 ? EmojiView.this.stickersWrap : (View) EmojiView.this.views.get(i);
            viewGroup.addView(access$7000);
            return access$7000;
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (dataSetObserver != null) {
                super.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    private class EmojiPopupWindow extends PopupWindow {
        private OnScrollChangedListener mSuperScrollListener;
        private ViewTreeObserver mViewTreeObserver;

        public EmojiPopupWindow() {
            init();
        }

        public EmojiPopupWindow(int i, int i2) {
            super(i, i2);
            init();
        }

        public EmojiPopupWindow(Context context) {
            super(context);
            init();
        }

        public EmojiPopupWindow(View view) {
            super(view);
            init();
        }

        public EmojiPopupWindow(View view, int i, int i2) {
            super(view, i, i2);
            init();
        }

        public EmojiPopupWindow(View view, int i, int i2, boolean z) {
            super(view, i, i2, z);
            init();
        }

        private void init() {
            if (EmojiView.superListenerField != null) {
                try {
                    this.mSuperScrollListener = (OnScrollChangedListener) EmojiView.superListenerField.get(this);
                    EmojiView.superListenerField.set(this, EmojiView.NOP);
                } catch (Exception e) {
                    this.mSuperScrollListener = null;
                }
            }
        }

        private void registerListener(View view) {
            if (this.mSuperScrollListener != null) {
                ViewTreeObserver viewTreeObserver = view.getWindowToken() != null ? view.getViewTreeObserver() : null;
                if (viewTreeObserver != this.mViewTreeObserver) {
                    if (this.mViewTreeObserver != null && this.mViewTreeObserver.isAlive()) {
                        this.mViewTreeObserver.removeOnScrollChangedListener(this.mSuperScrollListener);
                    }
                    this.mViewTreeObserver = viewTreeObserver;
                    if (viewTreeObserver != null) {
                        viewTreeObserver.addOnScrollChangedListener(this.mSuperScrollListener);
                    }
                }
            }
        }

        private void unregisterListener() {
            if (this.mSuperScrollListener != null && this.mViewTreeObserver != null) {
                if (this.mViewTreeObserver.isAlive()) {
                    this.mViewTreeObserver.removeOnScrollChangedListener(this.mSuperScrollListener);
                }
                this.mViewTreeObserver = null;
            }
        }

        public void dismiss() {
            setFocusable(false);
            try {
                super.dismiss();
            } catch (Exception e) {
            }
            unregisterListener();
        }

        public void showAsDropDown(View view, int i, int i2) {
            try {
                super.showAsDropDown(view, i, i2);
                registerListener(view);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }

        public void showAtLocation(View view, int i, int i2, int i3) {
            super.showAtLocation(view, i, i2, i3);
            unregisterListener();
        }

        public void update(View view, int i, int i2) {
            super.update(view, i, i2);
            registerListener(view);
        }

        public void update(View view, int i, int i2, int i3, int i4) {
            super.update(view, i, i2, i3, i4);
            registerListener(view);
        }
    }

    private class GifsAdapter extends SelectionAdapter {
        private Context mContext;

        public GifsAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return EmojiView.this.recentGifs.size();
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Document document = (Document) EmojiView.this.recentGifs.get(i);
            if (document != null) {
                ((ContextLinkCell) viewHolder.itemView).setGif(document, false);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(new ContextLinkCell(this.mContext));
        }
    }

    private class ImageViewEmoji extends ImageView {
        private float lastX;
        private float lastY;
        private boolean touched;
        private float touchedX;
        private float touchedY;

        public ImageViewEmoji(Context context) {
            super(context);
            setOnClickListener(new OnClickListener(EmojiView.this) {
                public void onClick(View view) {
                    ImageViewEmoji.this.sendEmoji(null);
                }
            });
            setOnLongClickListener(new OnLongClickListener(EmojiView.this) {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public boolean onLongClick(android.view.View r10) {
                    /*
                    r9 = this;
                    r6 = 3;
                    r5 = 2;
                    r8 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
                    r2 = 1;
                    r3 = 0;
                    r0 = r10.getTag();
                    r0 = (java.lang.String) r0;
                    r1 = org.telegram.messenger.EmojiData.emojiColoredMap;
                    r1 = r1.containsKey(r0);
                    if (r1 == 0) goto L_0x01e3;
                L_0x0014:
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1.touched = r2;
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r4 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r4 = r4.lastX;
                    r1.touchedX = r4;
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r4 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r4 = r4.lastY;
                    r1.touchedY = r4;
                    r1 = org.telegram.messenger.Emoji.emojiColor;
                    r1 = r1.get(r0);
                    r1 = (java.lang.String) r1;
                    if (r1 == 0) goto L_0x018c;
                L_0x0039:
                    r4 = -1;
                    r7 = r1.hashCode();
                    switch(r7) {
                        case 1773375: goto L_0x010d;
                        case 1773376: goto L_0x0119;
                        case 1773377: goto L_0x0125;
                        case 1773378: goto L_0x0131;
                        case 1773379: goto L_0x013d;
                        default: goto L_0x0041;
                    };
                L_0x0041:
                    r1 = r4;
                L_0x0042:
                    switch(r1) {
                        case 0: goto L_0x0149;
                        case 1: goto L_0x0156;
                        case 2: goto L_0x0163;
                        case 3: goto L_0x0170;
                        case 4: goto L_0x017e;
                        default: goto L_0x0045;
                    };
                L_0x0045:
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1 = org.telegram.ui.Components.EmojiView.this;
                    r1 = r1.location;
                    r10.getLocationOnScreen(r1);
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1 = org.telegram.ui.Components.EmojiView.this;
                    r1 = r1.emojiSize;
                    r4 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r4 = org.telegram.ui.Components.EmojiView.this;
                    r4 = r4.pickerView;
                    r4 = r4.getSelection();
                    r4 = r4 * r1;
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1 = org.telegram.ui.Components.EmojiView.this;
                    r1 = r1.pickerView;
                    r1 = r1.getSelection();
                    r5 = r1 * 4;
                    r1 = org.telegram.messenger.AndroidUtilities.isTablet();
                    if (r1 == 0) goto L_0x0199;
                L_0x0079:
                    r1 = 5;
                L_0x007a:
                    r1 = r5 - r1;
                    r1 = (float) r1;
                    r1 = org.telegram.messenger.AndroidUtilities.dp(r1);
                    r1 = r1 + r4;
                    r4 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r4 = org.telegram.ui.Components.EmojiView.this;
                    r4 = r4.location;
                    r4 = r4[r3];
                    r4 = r4 - r1;
                    r5 = org.telegram.messenger.AndroidUtilities.dp(r8);
                    if (r4 >= r5) goto L_0x019c;
                L_0x0093:
                    r4 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r4 = org.telegram.ui.Components.EmojiView.this;
                    r4 = r4.location;
                    r4 = r4[r3];
                    r4 = r4 - r1;
                    r5 = org.telegram.messenger.AndroidUtilities.dp(r8);
                    r4 = r4 - r5;
                    r1 = r1 + r4;
                L_0x00a4:
                    r4 = -r1;
                    r1 = r10.getTop();
                    if (r1 >= 0) goto L_0x01dc;
                L_0x00ab:
                    r1 = r10.getTop();
                L_0x00af:
                    r3 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r3 = org.telegram.ui.Components.EmojiView.this;
                    r5 = r3.pickerView;
                    r3 = org.telegram.messenger.AndroidUtilities.isTablet();
                    if (r3 == 0) goto L_0x01df;
                L_0x00bd:
                    r3 = 1106247680; // 0x41f00000 float:30.0 double:5.465589745E-315;
                L_0x00bf:
                    r3 = org.telegram.messenger.AndroidUtilities.dp(r3);
                    r3 = r3 - r4;
                    r6 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
                    r6 = org.telegram.messenger.AndroidUtilities.dpf2(r6);
                    r6 = (int) r6;
                    r3 = r3 + r6;
                    r5.setEmoji(r0, r3);
                    r0 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r0 = org.telegram.ui.Components.EmojiView.this;
                    r0 = r0.pickerViewPopup;
                    r0.setFocusable(r2);
                    r0 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r0 = org.telegram.ui.Components.EmojiView.this;
                    r0 = r0.pickerViewPopup;
                    r3 = r10.getMeasuredHeight();
                    r3 = -r3;
                    r5 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r5 = org.telegram.ui.Components.EmojiView.this;
                    r5 = r5.popupHeight;
                    r3 = r3 - r5;
                    r5 = r10.getMeasuredHeight();
                    r6 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r6 = org.telegram.ui.Components.EmojiView.this;
                    r6 = r6.emojiSize;
                    r5 = r5 - r6;
                    r5 = r5 / 2;
                    r3 = r3 + r5;
                    r1 = r3 - r1;
                    r0.showAsDropDown(r10, r4, r1);
                    r0 = r10.getParent();
                    r0.requestDisallowInterceptTouchEvent(r2);
                L_0x010c:
                    return r2;
                L_0x010d:
                    r7 = "🏻";
                    r1 = r1.equals(r7);
                    if (r1 == 0) goto L_0x0041;
                L_0x0116:
                    r1 = r3;
                    goto L_0x0042;
                L_0x0119:
                    r7 = "🏼";
                    r1 = r1.equals(r7);
                    if (r1 == 0) goto L_0x0041;
                L_0x0122:
                    r1 = r2;
                    goto L_0x0042;
                L_0x0125:
                    r7 = "🏽";
                    r1 = r1.equals(r7);
                    if (r1 == 0) goto L_0x0041;
                L_0x012e:
                    r1 = r5;
                    goto L_0x0042;
                L_0x0131:
                    r7 = "🏾";
                    r1 = r1.equals(r7);
                    if (r1 == 0) goto L_0x0041;
                L_0x013a:
                    r1 = r6;
                    goto L_0x0042;
                L_0x013d:
                    r7 = "🏿";
                    r1 = r1.equals(r7);
                    if (r1 == 0) goto L_0x0041;
                L_0x0146:
                    r1 = 4;
                    goto L_0x0042;
                L_0x0149:
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1 = org.telegram.ui.Components.EmojiView.this;
                    r1 = r1.pickerView;
                    r1.setSelection(r2);
                    goto L_0x0045;
                L_0x0156:
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1 = org.telegram.ui.Components.EmojiView.this;
                    r1 = r1.pickerView;
                    r1.setSelection(r5);
                    goto L_0x0045;
                L_0x0163:
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1 = org.telegram.ui.Components.EmojiView.this;
                    r1 = r1.pickerView;
                    r1.setSelection(r6);
                    goto L_0x0045;
                L_0x0170:
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1 = org.telegram.ui.Components.EmojiView.this;
                    r1 = r1.pickerView;
                    r4 = 4;
                    r1.setSelection(r4);
                    goto L_0x0045;
                L_0x017e:
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1 = org.telegram.ui.Components.EmojiView.this;
                    r1 = r1.pickerView;
                    r4 = 5;
                    r1.setSelection(r4);
                    goto L_0x0045;
                L_0x018c:
                    r1 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r1 = org.telegram.ui.Components.EmojiView.this;
                    r1 = r1.pickerView;
                    r1.setSelection(r3);
                    goto L_0x0045;
                L_0x0199:
                    r1 = r2;
                    goto L_0x007a;
                L_0x019c:
                    r4 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r4 = org.telegram.ui.Components.EmojiView.this;
                    r4 = r4.location;
                    r4 = r4[r3];
                    r4 = r4 - r1;
                    r5 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r5 = org.telegram.ui.Components.EmojiView.this;
                    r5 = r5.popupWidth;
                    r4 = r4 + r5;
                    r5 = org.telegram.messenger.AndroidUtilities.displaySize;
                    r5 = r5.x;
                    r6 = org.telegram.messenger.AndroidUtilities.dp(r8);
                    r5 = r5 - r6;
                    if (r4 <= r5) goto L_0x00a4;
                L_0x01bb:
                    r4 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r4 = org.telegram.ui.Components.EmojiView.this;
                    r4 = r4.location;
                    r4 = r4[r3];
                    r4 = r4 - r1;
                    r5 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r5 = org.telegram.ui.Components.EmojiView.this;
                    r5 = r5.popupWidth;
                    r4 = r4 + r5;
                    r5 = org.telegram.messenger.AndroidUtilities.displaySize;
                    r5 = r5.x;
                    r6 = org.telegram.messenger.AndroidUtilities.dp(r8);
                    r5 = r5 - r6;
                    r4 = r4 - r5;
                    r1 = r1 + r4;
                    goto L_0x00a4;
                L_0x01dc:
                    r1 = r3;
                    goto L_0x00af;
                L_0x01df:
                    r3 = 1102053376; // 0x41b00000 float:22.0 double:5.44486713E-315;
                    goto L_0x00bf;
                L_0x01e3:
                    r0 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r0 = org.telegram.ui.Components.EmojiView.this;
                    r0 = r0.pager;
                    r0 = r0.getCurrentItem();
                    if (r0 != 0) goto L_0x01fc;
                L_0x01f1:
                    r0 = org.telegram.ui.Components.EmojiView.ImageViewEmoji.this;
                    r0 = org.telegram.ui.Components.EmojiView.this;
                    r0 = r0.listener;
                    r0.onClearEmojiRecent();
                L_0x01fc:
                    r2 = r3;
                    goto L_0x010c;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.ImageViewEmoji.2.onLongClick(android.view.View):boolean");
                }
            });
            setBackgroundDrawable(Theme.getSelectorDrawable(false));
            setScaleType(ScaleType.CENTER);
        }

        private void sendEmoji(String str) {
            String str2 = str != null ? str : (String) getTag();
            new SpannableStringBuilder().append(str2);
            if (str == null) {
                if (EmojiView.this.pager.getCurrentItem() != 0) {
                    String str3 = (String) Emoji.emojiColor.get(str2);
                    if (str3 != null) {
                        str2 = EmojiView.addColorToCode(str2, str3);
                    }
                }
                EmojiView.this.addEmojiToRecent(str2);
                if (EmojiView.this.listener != null) {
                    EmojiView.this.listener.onEmojiSelected(Emoji.fixEmoji(str2));
                }
            } else if (EmojiView.this.listener != null) {
                EmojiView.this.listener.onEmojiSelected(Emoji.fixEmoji(str));
            }
        }

        public void onMeasure(int i, int i2) {
            setMeasuredDimension(MeasureSpec.getSize(i), MeasureSpec.getSize(i));
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            int i = 5;
            boolean z = true;
            if (this.touched) {
                if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    if (EmojiView.this.pickerViewPopup != null && EmojiView.this.pickerViewPopup.isShowing()) {
                        String str;
                        EmojiView.this.pickerViewPopup.dismiss();
                        switch (EmojiView.this.pickerView.getSelection()) {
                            case 1:
                                str = "🏻";
                                break;
                            case 2:
                                str = "🏼";
                                break;
                            case 3:
                                str = "🏽";
                                break;
                            case 4:
                                str = "🏾";
                                break;
                            case 5:
                                str = "🏿";
                                break;
                            default:
                                str = null;
                                break;
                        }
                        String str2 = (String) getTag();
                        if (EmojiView.this.pager.getCurrentItem() != 0) {
                            if (str != null) {
                                Emoji.emojiColor.put(str2, str);
                                str2 = EmojiView.addColorToCode(str2, str);
                            } else {
                                Emoji.emojiColor.remove(str2);
                            }
                            setImageDrawable(Emoji.getEmojiBigDrawable(str2));
                            sendEmoji(null);
                            Emoji.saveEmojiColors();
                        } else if (str != null) {
                            sendEmoji(EmojiView.addColorToCode(str2, str));
                        } else {
                            sendEmoji(str2);
                        }
                    }
                    this.touched = false;
                    this.touchedX = -10000.0f;
                    this.touchedY = -10000.0f;
                } else if (motionEvent.getAction() == 2) {
                    if (this.touchedX == -10000.0f) {
                        z = false;
                    } else if (Math.abs(this.touchedX - motionEvent.getX()) > AndroidUtilities.getPixelsInCM(0.2f, true) || Math.abs(this.touchedY - motionEvent.getY()) > AndroidUtilities.getPixelsInCM(0.2f, false)) {
                        this.touchedX = -10000.0f;
                        this.touchedY = -10000.0f;
                        z = false;
                    }
                    if (!z) {
                        getLocationOnScreen(EmojiView.this.location);
                        float x = ((float) EmojiView.this.location[0]) + motionEvent.getX();
                        EmojiView.this.pickerView.getLocationOnScreen(EmojiView.this.location);
                        int dp = (int) ((x - ((float) (EmojiView.this.location[0] + AndroidUtilities.dp(3.0f)))) / ((float) (EmojiView.this.emojiSize + AndroidUtilities.dp(4.0f))));
                        if (dp < 0) {
                            i = 0;
                        } else if (dp <= 5) {
                            i = dp;
                        }
                        EmojiView.this.pickerView.setSelection(i);
                    }
                }
            }
            this.lastX = motionEvent.getX();
            this.lastY = motionEvent.getY();
            return super.onTouchEvent(motionEvent);
        }
    }

    private class StickersGridAdapter extends SelectionAdapter {
        private HashMap<Integer, Object> cache = new HashMap();
        private Context context;
        private HashMap<Object, Integer> packStartPosition = new HashMap();
        private HashMap<Integer, Integer> positionToRow = new HashMap();
        private HashMap<Integer, Object> rowStartPack = new HashMap();
        private int stickersPerRow;
        private int totalItems;

        /* renamed from: org.telegram.ui.Components.EmojiView$StickersGridAdapter$2 */
        class C44212 implements OnClickListener {
            C44212() {
            }

            public void onClick(View view) {
                if (EmojiView.this.groupStickerSet == null) {
                    EmojiView.this.getContext().getSharedPreferences("emoji", 0).edit().putLong("group_hide_stickers_" + EmojiView.this.info.id, EmojiView.this.info.stickerset != null ? EmojiView.this.info.stickerset.id : 0).commit();
                    EmojiView.this.updateStickerTabs();
                    if (EmojiView.this.stickersGridAdapter != null) {
                        EmojiView.this.stickersGridAdapter.notifyDataSetChanged();
                    }
                } else if (EmojiView.this.listener != null) {
                    EmojiView.this.listener.onStickersGroupClick(EmojiView.this.info.id);
                }
            }
        }

        /* renamed from: org.telegram.ui.Components.EmojiView$StickersGridAdapter$3 */
        class C44223 implements OnClickListener {
            C44223() {
            }

            public void onClick(View view) {
                if (EmojiView.this.listener != null) {
                    EmojiView.this.listener.onStickersGroupClick(EmojiView.this.info.id);
                }
            }
        }

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
            Object obj = this.cache.get(Integer.valueOf(i));
            return obj != null ? obj instanceof Document ? 0 : obj instanceof String ? 3 : 2 : 1;
        }

        public int getPositionForPack(Object obj) {
            Integer num = (Integer) this.packStartPosition.get(obj);
            return num == null ? -1 : num.intValue();
        }

        public int getTabForPosition(int i) {
            if (this.stickersPerRow == 0) {
                int measuredWidth = EmojiView.this.getMeasuredWidth();
                if (measuredWidth == 0) {
                    measuredWidth = AndroidUtilities.displaySize.x;
                }
                this.stickersPerRow = measuredWidth / AndroidUtilities.dp(72.0f);
            }
            Integer num = (Integer) this.positionToRow.get(Integer.valueOf(i));
            if (num == null) {
                return (EmojiView.this.stickerSets.size() - 1) + EmojiView.this.stickersTabOffset;
            }
            Object obj = this.rowStartPack.get(num);
            if (obj instanceof String) {
                return "recent".equals(obj) ? EmojiView.this.recentTabBum : EmojiView.this.favTabBum;
            } else {
                return EmojiView.this.stickerSets.indexOf((TLRPC$TL_messages_stickerSet) obj) + EmojiView.this.stickersTabOffset;
            }
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void notifyDataSetChanged() {
            int measuredWidth = EmojiView.this.getMeasuredWidth();
            if (measuredWidth == 0) {
                measuredWidth = AndroidUtilities.displaySize.x;
            }
            this.stickersPerRow = measuredWidth / AndroidUtilities.dp(72.0f);
            EmojiView.this.stickersLayoutManager.setSpanCount(this.stickersPerRow);
            this.rowStartPack.clear();
            this.packStartPosition.clear();
            this.positionToRow.clear();
            this.cache.clear();
            this.totalItems = 0;
            ArrayList access$4900 = EmojiView.this.stickerSets;
            int i = -2;
            int i2 = 0;
            while (i < access$4900.size()) {
                ArrayList access$6600;
                Object obj = null;
                if (i == -2) {
                    access$6600 = EmojiView.this.favouriteStickers;
                    this.packStartPosition.put("fav", Integer.valueOf(this.totalItems));
                } else if (i == -1) {
                    access$6600 = EmojiView.this.recentStickers;
                    this.packStartPosition.put("recent", Integer.valueOf(this.totalItems));
                } else {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) access$4900.get(i);
                    access$6600 = tLRPC$TL_messages_stickerSet.documents;
                    this.packStartPosition.put(tLRPC$TL_messages_stickerSet, Integer.valueOf(this.totalItems));
                }
                if (i == EmojiView.this.groupStickerPackNum) {
                    EmojiView.this.groupStickerPackPosition = this.totalItems;
                    if (access$6600.isEmpty()) {
                        this.rowStartPack.put(Integer.valueOf(i2), obj);
                        int i3 = i2 + 1;
                        this.positionToRow.put(Integer.valueOf(this.totalItems), Integer.valueOf(i2));
                        this.rowStartPack.put(Integer.valueOf(i3), obj);
                        i2 = i3 + 1;
                        this.positionToRow.put(Integer.valueOf(this.totalItems + 1), Integer.valueOf(i3));
                        HashMap hashMap = this.cache;
                        int i4 = this.totalItems;
                        this.totalItems = i4 + 1;
                        hashMap.put(Integer.valueOf(i4), obj);
                        HashMap hashMap2 = this.cache;
                        int i5 = this.totalItems;
                        this.totalItems = i5 + 1;
                        hashMap2.put(Integer.valueOf(i5), "group");
                        i++;
                    }
                }
                if (!access$6600.isEmpty()) {
                    i3 = (int) Math.ceil((double) (((float) access$6600.size()) / ((float) this.stickersPerRow)));
                    if (obj != null) {
                        this.cache.put(Integer.valueOf(this.totalItems), obj);
                    } else {
                        this.cache.put(Integer.valueOf(this.totalItems), access$6600);
                    }
                    this.positionToRow.put(Integer.valueOf(this.totalItems), Integer.valueOf(i2));
                    for (i4 = 0; i4 < access$6600.size(); i4++) {
                        this.cache.put(Integer.valueOf((i4 + 1) + this.totalItems), access$6600.get(i4));
                        this.positionToRow.put(Integer.valueOf((i4 + 1) + this.totalItems), Integer.valueOf((i2 + 1) + (i4 / this.stickersPerRow)));
                    }
                    for (i4 = 0; i4 < i3 + 1; i4++) {
                        if (obj != null) {
                            this.rowStartPack.put(Integer.valueOf(i2 + i4), obj);
                        } else {
                            this.rowStartPack.put(Integer.valueOf(i2 + i4), i == -1 ? "recent" : "fav");
                        }
                    }
                    this.totalItems += (this.stickersPerRow * i3) + 1;
                    i2 += i3 + 1;
                }
                i++;
            }
            super.notifyDataSetChanged();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Chat chat = null;
            boolean z = true;
            int height;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    Document document = (Document) this.cache.get(Integer.valueOf(i));
                    StickerEmojiCell stickerEmojiCell = (StickerEmojiCell) viewHolder.itemView;
                    stickerEmojiCell.setSticker(document, false);
                    boolean z2 = EmojiView.this.recentStickers.contains(document) || EmojiView.this.favouriteStickers.contains(document);
                    stickerEmojiCell.setRecent(z2);
                    return;
                case 1:
                    EmptyCell emptyCell = (EmptyCell) viewHolder.itemView;
                    if (i == this.totalItems) {
                        Integer num = (Integer) this.positionToRow.get(Integer.valueOf(i - 1));
                        if (num == null) {
                            emptyCell.setHeight(1);
                            return;
                        }
                        ArrayList arrayList;
                        Object obj = this.rowStartPack.get(num);
                        if (obj instanceof TLRPC$TL_messages_stickerSet) {
                            arrayList = ((TLRPC$TL_messages_stickerSet) obj).documents;
                        } else if (obj instanceof String) {
                            arrayList = "recent".equals(obj) ? EmojiView.this.recentStickers : EmojiView.this.favouriteStickers;
                        }
                        if (arrayList == null) {
                            emptyCell.setHeight(1);
                            return;
                        } else if (arrayList.isEmpty()) {
                            emptyCell.setHeight(AndroidUtilities.dp(8.0f));
                            return;
                        } else {
                            int i2;
                            height = EmojiView.this.pager.getHeight() - (((int) Math.ceil((double) (((float) arrayList.size()) / ((float) this.stickersPerRow)))) * AndroidUtilities.dp(82.0f));
                            if (height > 0) {
                                i2 = height;
                            }
                            emptyCell.setHeight(i2);
                            return;
                        }
                    }
                    emptyCell.setHeight(AndroidUtilities.dp(82.0f));
                    return;
                case 2:
                    StickerSetNameCell stickerSetNameCell = (StickerSetNameCell) viewHolder.itemView;
                    if (i == EmojiView.this.groupStickerPackPosition) {
                        height = (EmojiView.this.groupStickersHidden && EmojiView.this.groupStickerSet == null) ? 0 : EmojiView.this.groupStickerSet != null ? R.drawable.stickersclose : R.drawable.stickerset_close;
                        if (EmojiView.this.info != null) {
                            chat = MessagesController.getInstance().getChat(Integer.valueOf(EmojiView.this.info.id));
                        }
                        String str = "CurrentGroupStickers";
                        Object[] objArr = new Object[1];
                        objArr[0] = chat != null ? chat.title : "Group Stickers";
                        stickerSetNameCell.setText(LocaleController.formatString(str, R.string.CurrentGroupStickers, objArr), height);
                        return;
                    }
                    ArrayList arrayList2 = this.cache.get(Integer.valueOf(i));
                    if (arrayList2 instanceof TLRPC$TL_messages_stickerSet) {
                        TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) arrayList2;
                        if (tLRPC$TL_messages_stickerSet.set != null) {
                            stickerSetNameCell.setText(tLRPC$TL_messages_stickerSet.set.title, 0);
                            return;
                        }
                        return;
                    } else if (arrayList2 == EmojiView.this.recentStickers) {
                        stickerSetNameCell.setText(LocaleController.getString("RecentStickers", R.string.RecentStickers), 0);
                        return;
                    } else if (arrayList2 == EmojiView.this.favouriteStickers) {
                        stickerSetNameCell.setText(LocaleController.getString("FavoriteStickers", R.string.FavoriteStickers), 0);
                        return;
                    } else {
                        return;
                    }
                case 3:
                    StickerSetGroupInfoCell stickerSetGroupInfoCell = (StickerSetGroupInfoCell) viewHolder.itemView;
                    if (i != this.totalItems - 1) {
                        z = false;
                    }
                    stickerSetGroupInfoCell.setIsLast(z);
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
                case 2:
                    view = new StickerSetNameCell(this.context);
                    ((StickerSetNameCell) view).setOnIconClickListener(new C44212());
                    break;
                case 3:
                    view = new StickerSetGroupInfoCell(this.context);
                    ((StickerSetGroupInfoCell) view).setAddOnClickListener(new C44223());
                    view.setLayoutParams(new LayoutParams(-1, -2));
                    break;
            }
            return new Holder(view);
        }
    }

    private class TrendingGridAdapter extends SelectionAdapter {
        private HashMap<Integer, Object> cache = new HashMap();
        private Context context;
        private HashMap<Integer, StickerSetCovered> positionsToSets = new HashMap();
        private ArrayList<StickerSetCovered> sets = new ArrayList();
        private int stickersPerRow;
        private int totalItems;

        /* renamed from: org.telegram.ui.Components.EmojiView$TrendingGridAdapter$2 */
        class C44242 implements OnClickListener {
            C44242() {
            }

            public void onClick(View view) {
                FeaturedStickerSetInfoCell featuredStickerSetInfoCell = (FeaturedStickerSetInfoCell) view.getParent();
                StickerSetCovered stickerSet = featuredStickerSetInfoCell.getStickerSet();
                if (!EmojiView.this.installingStickerSets.containsKey(Long.valueOf(stickerSet.set.id)) && !EmojiView.this.removingStickerSets.containsKey(Long.valueOf(stickerSet.set.id))) {
                    if (featuredStickerSetInfoCell.isInstalled()) {
                        EmojiView.this.removingStickerSets.put(Long.valueOf(stickerSet.set.id), stickerSet);
                        EmojiView.this.listener.onStickerSetRemove(featuredStickerSetInfoCell.getStickerSet());
                    } else {
                        EmojiView.this.installingStickerSets.put(Long.valueOf(stickerSet.set.id), stickerSet);
                        EmojiView.this.listener.onStickerSetAdd(featuredStickerSetInfoCell.getStickerSet());
                    }
                    featuredStickerSetInfoCell.setDrawProgress(true);
                }
            }
        }

        public TrendingGridAdapter(Context context) {
            this.context = context;
        }

        public Object getItem(int i) {
            return this.cache.get(Integer.valueOf(i));
        }

        public int getItemCount() {
            return this.totalItems;
        }

        public int getItemViewType(int i) {
            Object obj = this.cache.get(Integer.valueOf(i));
            return obj != null ? obj instanceof Document ? 0 : 2 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void notifyDataSetChanged() {
            int measuredWidth = EmojiView.this.getMeasuredWidth();
            if (measuredWidth == 0) {
                if (AndroidUtilities.isTablet()) {
                    int i = AndroidUtilities.displaySize.x;
                    measuredWidth = (i * 35) / 100;
                    if (measuredWidth < AndroidUtilities.dp(320.0f)) {
                        measuredWidth = AndroidUtilities.dp(320.0f);
                    }
                    measuredWidth = i - measuredWidth;
                } else {
                    measuredWidth = AndroidUtilities.displaySize.x;
                }
                if (measuredWidth == 0) {
                    measuredWidth = 1080;
                }
            }
            this.stickersPerRow = measuredWidth / AndroidUtilities.dp(72.0f);
            EmojiView.this.trendingLayoutManager.setSpanCount(Math.max(1, this.stickersPerRow));
            if (!EmojiView.this.trendingLoaded) {
                this.cache.clear();
                this.positionsToSets.clear();
                this.sets.clear();
                this.totalItems = 0;
                ArrayList featuredStickerSets = StickersQuery.getFeaturedStickerSets();
                int i2 = 0;
                for (i = 0; i < featuredStickerSets.size(); i++) {
                    StickerSetCovered stickerSetCovered = (StickerSetCovered) featuredStickerSets.get(i);
                    if (!(StickersQuery.isStickerPackInstalled(stickerSetCovered.set.id) || (stickerSetCovered.covers.isEmpty() && stickerSetCovered.cover == null))) {
                        int ceil;
                        this.sets.add(stickerSetCovered);
                        this.positionsToSets.put(Integer.valueOf(this.totalItems), stickerSetCovered);
                        HashMap hashMap = this.cache;
                        int i3 = this.totalItems;
                        this.totalItems = i3 + 1;
                        Integer valueOf = Integer.valueOf(i3);
                        i3 = i2 + 1;
                        hashMap.put(valueOf, Integer.valueOf(i2));
                        i2 = this.totalItems / this.stickersPerRow;
                        if (stickerSetCovered.covers.isEmpty()) {
                            this.cache.put(Integer.valueOf(this.totalItems), stickerSetCovered.cover);
                            i2 = 1;
                        } else {
                            ceil = (int) Math.ceil((double) (((float) stickerSetCovered.covers.size()) / ((float) this.stickersPerRow)));
                            for (i2 = 0; i2 < stickerSetCovered.covers.size(); i2++) {
                                this.cache.put(Integer.valueOf(this.totalItems + i2), stickerSetCovered.covers.get(i2));
                            }
                            i2 = ceil;
                        }
                        for (ceil = 0; ceil < this.stickersPerRow * i2; ceil++) {
                            this.positionsToSets.put(Integer.valueOf(this.totalItems + ceil), stickerSetCovered);
                        }
                        this.totalItems += i2 * this.stickersPerRow;
                        i2 = i3;
                    }
                }
                if (this.totalItems != 0) {
                    EmojiView.this.trendingLoaded = true;
                    EmojiView.this.featuredStickersHash = StickersQuery.getFeaturesStickersHashWithoutUnread();
                }
                super.notifyDataSetChanged();
            }
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ((StickerEmojiCell) viewHolder.itemView).setSticker((Document) this.cache.get(Integer.valueOf(i)), false);
                    return;
                case 1:
                    ((EmptyCell) viewHolder.itemView).setHeight(AndroidUtilities.dp(82.0f));
                    return;
                case 2:
                    boolean z2;
                    ArrayList unreadStickerSets = StickersQuery.getUnreadStickerSets();
                    StickerSetCovered stickerSetCovered = (StickerSetCovered) this.sets.get(((Integer) this.cache.get(Integer.valueOf(i))).intValue());
                    boolean z3 = unreadStickerSets != null && unreadStickerSets.contains(Long.valueOf(stickerSetCovered.set.id));
                    FeaturedStickerSetInfoCell featuredStickerSetInfoCell = (FeaturedStickerSetInfoCell) viewHolder.itemView;
                    featuredStickerSetInfoCell.setStickerSet(stickerSetCovered, z3);
                    if (z3) {
                        StickersQuery.markFaturedStickersByIdAsRead(stickerSetCovered.set.id);
                    }
                    boolean containsKey = EmojiView.this.installingStickerSets.containsKey(Long.valueOf(stickerSetCovered.set.id));
                    z3 = EmojiView.this.removingStickerSets.containsKey(Long.valueOf(stickerSetCovered.set.id));
                    if (containsKey || z3) {
                        if (containsKey && featuredStickerSetInfoCell.isInstalled()) {
                            EmojiView.this.installingStickerSets.remove(Long.valueOf(stickerSetCovered.set.id));
                            z2 = z3;
                            z3 = false;
                            z = true;
                            featuredStickerSetInfoCell.setDrawProgress(z);
                            return;
                        } else if (z3 && !featuredStickerSetInfoCell.isInstalled()) {
                            EmojiView.this.removingStickerSets.remove(Long.valueOf(stickerSetCovered.set.id));
                            z2 = false;
                            z3 = containsKey;
                            if (z3 || r0) {
                                z = true;
                            }
                            featuredStickerSetInfoCell.setDrawProgress(z);
                            return;
                        }
                    }
                    z2 = z3;
                    z3 = containsKey;
                    z = true;
                    featuredStickerSetInfoCell.setDrawProgress(z);
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
                case 2:
                    view = new FeaturedStickerSetInfoCell(this.context, 17);
                    ((FeaturedStickerSetInfoCell) view).setAddOnClickListener(new C44242());
                    break;
            }
            return new Holder(view);
        }
    }

    static {
        Field field = null;
        try {
            field = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
        }
        superListenerField = field;
    }

    public EmojiView(boolean z, boolean z2, Context context, ChatFull chatFull) {
        View gridView;
        super(context);
        Theme.setDrawableColorByKey(context.getResources().getDrawable(R.drawable.ic_smiles2_stickers), Theme.key_chat_emojiPanelIcon);
        this.icons = new Drawable[]{Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_recent, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_smile, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_nature, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_food, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_car, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_objects, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected)), r0};
        this.showGifs = z2;
        this.info = chatFull;
        this.dotPaint = new Paint(1);
        this.dotPaint.setColor(Theme.getColor(Theme.key_chat_emojiPanelNewTrending));
        if (VERSION.SDK_INT >= 21) {
            this.outlineProvider = new C44113();
        }
        for (int i = 0; i < EmojiData.dataColored.length + 1; i++) {
            gridView = new GridView(context);
            if (AndroidUtilities.isTablet()) {
                gridView.setColumnWidth(AndroidUtilities.dp(60.0f));
            } else {
                gridView.setColumnWidth(AndroidUtilities.dp(45.0f));
            }
            gridView.setNumColumns(-1);
            ListAdapter emojiGridAdapter = new EmojiGridAdapter(i - 1);
            gridView.setAdapter(emojiGridAdapter);
            this.adapters.add(emojiGridAdapter);
            this.emojiGrids.add(gridView);
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.addView(gridView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.views.add(frameLayout);
        }
        if (z) {
            this.stickersWrap = new FrameLayout(context);
            StickersQuery.checkStickers(0);
            StickersQuery.checkFeaturedStickers();
            this.stickersGridView = new RecyclerListView(context) {
                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    return super.onInterceptTouchEvent(motionEvent) || StickerPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, EmojiView.this.stickersGridView, EmojiView.this.getMeasuredHeight(), EmojiView.this.stickerPreviewViewerDelegate);
                }

                public void setVisibility(int i) {
                    if ((EmojiView.this.gifsGridView == null || EmojiView.this.gifsGridView.getVisibility() != 0) && (EmojiView.this.trendingGridView == null || EmojiView.this.trendingGridView.getVisibility() != 0)) {
                        super.setVisibility(i);
                    } else {
                        super.setVisibility(8);
                    }
                }
            };
            RecyclerListView recyclerListView = this.stickersGridView;
            LayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
            this.stickersLayoutManager = gridLayoutManager;
            recyclerListView.setLayoutManager(gridLayoutManager);
            this.stickersLayoutManager.setSpanSizeLookup(new C44135());
            this.stickersGridView.setPadding(0, AndroidUtilities.dp(52.0f), 0, 0);
            this.stickersGridView.setClipToPadding(false);
            this.views.add(this.stickersWrap);
            recyclerListView = this.stickersGridView;
            Adapter stickersGridAdapter = new StickersGridAdapter(context);
            this.stickersGridAdapter = stickersGridAdapter;
            recyclerListView.setAdapter(stickersGridAdapter);
            this.stickersGridView.setOnTouchListener(new C44146());
            this.stickersOnItemClickListener = new C44157();
            this.stickersGridView.setOnItemClickListener(this.stickersOnItemClickListener);
            this.stickersGridView.setGlowColor(Theme.getColor(Theme.key_chat_emojiPanelBackground));
            this.stickersWrap.addView(this.stickersGridView);
            this.trendingGridView = new RecyclerListView(context);
            this.trendingGridView.setItemAnimator(null);
            this.trendingGridView.setLayoutAnimation(null);
            recyclerListView = this.trendingGridView;
            gridLayoutManager = new GridLayoutManager(context, 5) {
                public boolean supportsPredictiveItemAnimations() {
                    return false;
                }
            };
            this.trendingLayoutManager = gridLayoutManager;
            recyclerListView.setLayoutManager(gridLayoutManager);
            this.trendingLayoutManager.setSpanSizeLookup(new C44179());
            this.trendingGridView.setOnScrollListener(new OnScrollListener() {
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    EmojiView.this.checkStickersTabY(recyclerView, i2);
                }
            });
            this.trendingGridView.setClipToPadding(false);
            this.trendingGridView.setPadding(0, AndroidUtilities.dp(48.0f), 0, 0);
            recyclerListView = this.trendingGridView;
            stickersGridAdapter = new TrendingGridAdapter(context);
            this.trendingGridAdapter = stickersGridAdapter;
            recyclerListView.setAdapter(stickersGridAdapter);
            this.trendingGridView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(View view, int i) {
                    StickerSetCovered stickerSetCovered = (StickerSetCovered) EmojiView.this.trendingGridAdapter.positionsToSets.get(Integer.valueOf(i));
                    if (stickerSetCovered != null) {
                        EmojiView.this.listener.onShowStickerSet(stickerSetCovered.set, null);
                    }
                }
            });
            this.trendingGridAdapter.notifyDataSetChanged();
            this.trendingGridView.setGlowColor(Theme.getColor(Theme.key_chat_emojiPanelBackground));
            this.trendingGridView.setVisibility(8);
            this.stickersWrap.addView(this.trendingGridView);
            if (z2) {
                this.gifsGridView = new RecyclerListView(context);
                this.gifsGridView.setClipToPadding(false);
                this.gifsGridView.setPadding(0, AndroidUtilities.dp(48.0f), 0, 0);
                recyclerListView = this.gifsGridView;
                gridLayoutManager = new ExtendedGridLayoutManager(context, 100) {
                    private Size size = new Size();

                    protected Size getSizeForItem(int i) {
                        float f = 100.0f;
                        Document document = (Document) EmojiView.this.recentGifs.get(i);
                        Size size = this.size;
                        float f2 = (document.thumb == null || document.thumb.f10147w == 0) ? 100.0f : (float) document.thumb.f10147w;
                        size.width = f2;
                        Size size2 = this.size;
                        if (!(document.thumb == null || document.thumb.f10146h == 0)) {
                            f = (float) document.thumb.f10146h;
                        }
                        size2.height = f;
                        for (int i2 = 0; i2 < document.attributes.size(); i2++) {
                            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i2);
                            if ((documentAttribute instanceof TLRPC$TL_documentAttributeImageSize) || (documentAttribute instanceof TLRPC$TL_documentAttributeVideo)) {
                                this.size.width = (float) documentAttribute.f10140w;
                                this.size.height = (float) documentAttribute.f10139h;
                                break;
                            }
                        }
                        return this.size;
                    }
                };
                this.flowLayoutManager = gridLayoutManager;
                recyclerListView.setLayoutManager(gridLayoutManager);
                this.flowLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
                    public int getSpanSize(int i) {
                        return EmojiView.this.flowLayoutManager.getSpanSizeForItem(i);
                    }
                });
                this.gifsGridView.addItemDecoration(new ItemDecoration() {
                    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
                        int i = 0;
                        rect.left = 0;
                        rect.top = 0;
                        rect.bottom = 0;
                        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                        if (!EmojiView.this.flowLayoutManager.isFirstRow(childAdapterPosition)) {
                            rect.top = AndroidUtilities.dp(2.0f);
                        }
                        if (!EmojiView.this.flowLayoutManager.isLastInRow(childAdapterPosition)) {
                            i = AndroidUtilities.dp(2.0f);
                        }
                        rect.right = i;
                    }
                });
                this.gifsGridView.setOverScrollMode(2);
                recyclerListView = this.gifsGridView;
                stickersGridAdapter = new GifsAdapter(context);
                this.gifsAdapter = stickersGridAdapter;
                recyclerListView.setAdapter(stickersGridAdapter);
                this.gifsGridView.setOnScrollListener(new OnScrollListener() {
                    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                        EmojiView.this.checkStickersTabY(recyclerView, i2);
                    }
                });
                this.gifsGridView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(View view, int i) {
                        if (i >= 0 && i < EmojiView.this.recentGifs.size() && EmojiView.this.listener != null) {
                            EmojiView.this.listener.onGifSelected((Document) EmojiView.this.recentGifs.get(i));
                        }
                    }
                });
                this.gifsGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
                    public boolean onItemClick(View view, int i) {
                        if (i < 0 || i >= EmojiView.this.recentGifs.size()) {
                            return false;
                        }
                        final Document document = (Document) EmojiView.this.recentGifs.get(i);
                        Builder builder = new Builder(view.getContext());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("DeleteGif", R.string.DeleteGif));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK).toUpperCase(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StickersQuery.removeRecentGif(document);
                                EmojiView.this.recentGifs = StickersQuery.getRecentGifs();
                                if (EmojiView.this.gifsAdapter != null) {
                                    EmojiView.this.gifsAdapter.notifyDataSetChanged();
                                }
                                if (EmojiView.this.recentGifs.isEmpty()) {
                                    EmojiView.this.updateStickerTabs();
                                    if (EmojiView.this.stickersGridAdapter != null) {
                                        EmojiView.this.stickersGridAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        builder.show().setCanceledOnTouchOutside(true);
                        return true;
                    }
                });
                this.gifsGridView.setVisibility(8);
                this.stickersWrap.addView(this.gifsGridView);
            }
            this.stickersEmptyView = new TextView(context);
            this.stickersEmptyView.setText(LocaleController.getString("NoStickers", R.string.NoStickers));
            this.stickersEmptyView.setTextSize(1, 18.0f);
            this.stickersEmptyView.setTextColor(Theme.getColor(Theme.key_chat_emojiPanelEmptyText));
            this.stickersWrap.addView(this.stickersEmptyView, LayoutHelper.createFrame(-2, -2.0f, 17, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.stickersGridView.setEmptyView(this.stickersEmptyView);
            this.stickersTab = new ScrollSlidingTabStrip(context) {
                float downX;
                float downY;
                boolean draggingHorizontally;
                boolean draggingVertically;
                boolean first = true;
                float lastTranslateX;
                float lastX;
                boolean startedScroll;
                final int touchslop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                VelocityTracker vTracker;

                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    if (motionEvent.getAction() == 0) {
                        this.draggingHorizontally = false;
                        this.draggingVertically = false;
                        this.downX = motionEvent.getRawX();
                        this.downY = motionEvent.getRawY();
                    } else if (!(this.draggingVertically || this.draggingHorizontally || EmojiView.this.dragListener == null || Math.abs(motionEvent.getRawY() - this.downY) < ((float) this.touchslop))) {
                        this.draggingVertically = true;
                        this.downY = motionEvent.getRawY();
                        EmojiView.this.dragListener.onDragStart();
                        if (!this.startedScroll) {
                            return true;
                        }
                        EmojiView.this.pager.endFakeDrag();
                        this.startedScroll = false;
                        return true;
                    }
                    return super.onInterceptTouchEvent(motionEvent);
                }

                public boolean onTouchEvent(MotionEvent motionEvent) {
                    boolean z = false;
                    if (this.first) {
                        this.first = false;
                        this.lastX = motionEvent.getX();
                    }
                    if (motionEvent.getAction() == 0) {
                        this.draggingHorizontally = false;
                        this.draggingVertically = false;
                        this.downX = motionEvent.getRawX();
                        this.downY = motionEvent.getRawY();
                    } else if (!(this.draggingVertically || this.draggingHorizontally || EmojiView.this.dragListener == null)) {
                        if (Math.abs(motionEvent.getRawX() - this.downX) >= ((float) this.touchslop)) {
                            this.draggingHorizontally = true;
                        } else if (Math.abs(motionEvent.getRawY() - this.downY) >= ((float) this.touchslop)) {
                            this.draggingVertically = true;
                            this.downY = motionEvent.getRawY();
                            EmojiView.this.dragListener.onDragStart();
                            if (this.startedScroll) {
                                EmojiView.this.pager.endFakeDrag();
                                this.startedScroll = false;
                            }
                        }
                    }
                    float yVelocity;
                    if (this.draggingVertically) {
                        if (this.vTracker == null) {
                            this.vTracker = VelocityTracker.obtain();
                        }
                        this.vTracker.addMovement(motionEvent);
                        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                            this.vTracker.computeCurrentVelocity(1000);
                            yVelocity = this.vTracker.getYVelocity();
                            this.vTracker.recycle();
                            this.vTracker = null;
                            if (motionEvent.getAction() == 1) {
                                EmojiView.this.dragListener.onDragEnd(yVelocity);
                            } else {
                                EmojiView.this.dragListener.onDragCancel();
                            }
                            this.first = true;
                            this.draggingHorizontally = false;
                            this.draggingVertically = false;
                            return true;
                        }
                        EmojiView.this.dragListener.onDrag(Math.round(motionEvent.getRawY() - this.downY));
                        return true;
                    }
                    yVelocity = EmojiView.this.stickersTab.getTranslationX();
                    if (EmojiView.this.stickersTab.getScrollX() == 0 && yVelocity == BitmapDescriptorFactory.HUE_RED) {
                        if (this.startedScroll || this.lastX - motionEvent.getX() >= BitmapDescriptorFactory.HUE_RED) {
                            if (this.startedScroll && this.lastX - motionEvent.getX() > BitmapDescriptorFactory.HUE_RED && EmojiView.this.pager.isFakeDragging()) {
                                EmojiView.this.pager.endFakeDrag();
                                this.startedScroll = false;
                            }
                        } else if (EmojiView.this.pager.beginFakeDrag()) {
                            this.startedScroll = true;
                            this.lastTranslateX = EmojiView.this.stickersTab.getTranslationX();
                        }
                    }
                    if (this.startedScroll) {
                        try {
                            EmojiView.this.pager.fakeDragBy((float) ((int) (((motionEvent.getX() - this.lastX) + yVelocity) - this.lastTranslateX)));
                            this.lastTranslateX = yVelocity;
                        } catch (Throwable e) {
                            try {
                                EmojiView.this.pager.endFakeDrag();
                            } catch (Exception e2) {
                            }
                            this.startedScroll = false;
                            FileLog.e(e);
                        }
                    }
                    this.lastX = motionEvent.getX();
                    if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                        this.first = true;
                        this.draggingHorizontally = false;
                        this.draggingVertically = false;
                        if (this.startedScroll) {
                            EmojiView.this.pager.endFakeDrag();
                            this.startedScroll = false;
                        }
                    }
                    if (this.startedScroll || super.onTouchEvent(motionEvent)) {
                        z = true;
                    }
                    return z;
                }
            };
            this.stickersTab.setUnderlineHeight(AndroidUtilities.dp(1.0f));
            this.stickersTab.setIndicatorColor(Theme.getColor(Theme.key_chat_emojiPanelStickerPackSelector));
            this.stickersTab.setUnderlineColor(Theme.getColor(Theme.key_chat_emojiPanelStickerPackSelector));
            this.stickersTab.setBackgroundColor(Theme.getColor(Theme.key_chat_emojiPanelBackground));
            this.stickersTab.setVisibility(4);
            addView(this.stickersTab, LayoutHelper.createFrame(-1, 48, 51));
            this.stickersTab.setTranslationX((float) AndroidUtilities.displaySize.x);
            updateStickerTabs();
            this.stickersTab.setDelegate(new ScrollSlidingTabStripDelegate() {
                public void onPageSelected(int i) {
                    int i2 = 8;
                    if (EmojiView.this.gifsGridView != null) {
                        if (i == EmojiView.this.gifTabNum + 1) {
                            if (EmojiView.this.gifsGridView.getVisibility() != 0) {
                                EmojiView.this.listener.onGifTab(true);
                                EmojiView.this.showGifTab();
                            }
                        } else if (i == EmojiView.this.trendingTabNum + 1) {
                            if (EmojiView.this.trendingGridView.getVisibility() != 0) {
                                EmojiView.this.showTrendingTab();
                            }
                        } else if (EmojiView.this.gifsGridView.getVisibility() == 0) {
                            EmojiView.this.listener.onGifTab(false);
                            EmojiView.this.gifsGridView.setVisibility(8);
                            EmojiView.this.stickersGridView.setVisibility(0);
                            EmojiView.this.stickersGridView.getVisibility();
                            r2 = EmojiView.this.stickersEmptyView;
                            if (EmojiView.this.stickersGridAdapter.getItemCount() == 0) {
                                i2 = 0;
                            }
                            r2.setVisibility(i2);
                            EmojiView.this.checkScroll();
                            EmojiView.this.saveNewPage();
                        } else if (EmojiView.this.trendingGridView.getVisibility() == 0) {
                            EmojiView.this.trendingGridView.setVisibility(8);
                            EmojiView.this.stickersGridView.setVisibility(0);
                            r2 = EmojiView.this.stickersEmptyView;
                            if (EmojiView.this.stickersGridAdapter.getItemCount() == 0) {
                                i2 = 0;
                            }
                            r2.setVisibility(i2);
                            EmojiView.this.saveNewPage();
                        }
                    }
                    if (i == 0) {
                        EmojiView.this.pager.setCurrentItem(0);
                    } else if (i != EmojiView.this.gifTabNum + 1 && i != EmojiView.this.trendingTabNum + 1) {
                        if (i == EmojiView.this.recentTabBum + 1) {
                            EmojiView.this.stickersLayoutManager.scrollToPositionWithOffset(EmojiView.this.stickersGridAdapter.getPositionForPack("recent"), 0);
                            EmojiView.this.checkStickersTabY(null, 0);
                            EmojiView.this.stickersTab.onPageScrolled(EmojiView.this.recentTabBum + 1, (EmojiView.this.recentTabBum > 0 ? EmojiView.this.recentTabBum : EmojiView.this.stickersTabOffset) + 1);
                        } else if (i == EmojiView.this.favTabBum + 1) {
                            EmojiView.this.stickersLayoutManager.scrollToPositionWithOffset(EmojiView.this.stickersGridAdapter.getPositionForPack("fav"), 0);
                            EmojiView.this.checkStickersTabY(null, 0);
                            EmojiView.this.stickersTab.onPageScrolled(EmojiView.this.favTabBum + 1, (EmojiView.this.favTabBum > 0 ? EmojiView.this.favTabBum : EmojiView.this.stickersTabOffset) + 1);
                        } else {
                            i2 = (i - 1) - EmojiView.this.stickersTabOffset;
                            if (i2 < EmojiView.this.stickerSets.size()) {
                                if (i2 >= EmojiView.this.stickerSets.size()) {
                                    i2 = EmojiView.this.stickerSets.size() - 1;
                                }
                                EmojiView.this.stickersLayoutManager.scrollToPositionWithOffset(EmojiView.this.stickersGridAdapter.getPositionForPack(EmojiView.this.stickerSets.get(i2)), 0);
                                EmojiView.this.checkStickersTabY(null, 0);
                                EmojiView.this.checkScroll();
                            } else if (EmojiView.this.listener != null) {
                                EmojiView.this.listener.onStickersSettingsClick();
                            }
                        }
                    }
                }
            });
            this.stickersGridView.setOnScrollListener(new OnScrollListener() {
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    EmojiView.this.checkScroll();
                    EmojiView.this.checkStickersTabY(recyclerView, i2);
                }
            });
        }
        this.pager = new ViewPager(context) {
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.onInterceptTouchEvent(motionEvent);
            }
        };
        this.pager.setAdapter(new EmojiPagesAdapter());
        this.emojiTab = new LinearLayout(context) {
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.onInterceptTouchEvent(motionEvent);
            }
        };
        this.emojiTab.setOrientation(0);
        addView(this.emojiTab, LayoutHelper.createFrame(-1, 48.0f));
        this.pagerSlidingTabStrip = new PagerSlidingTabStrip(context);
        this.pagerSlidingTabStrip.setViewPager(this.pager);
        this.pagerSlidingTabStrip.setShouldExpand(true);
        this.pagerSlidingTabStrip.setIndicatorHeight(AndroidUtilities.dp(2.0f));
        this.pagerSlidingTabStrip.setUnderlineHeight(AndroidUtilities.dp(1.0f));
        this.pagerSlidingTabStrip.setIndicatorColor(Theme.getColor(Theme.key_chat_emojiPanelIconSelector));
        this.pagerSlidingTabStrip.setUnderlineColor(Theme.getColor(Theme.key_chat_emojiPanelShadowLine));
        this.emojiTab.addView(this.pagerSlidingTabStrip, LayoutHelper.createLinear(0, 48, 1.0f));
        this.pagerSlidingTabStrip.setOnPageChangeListener(new C0188f() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
                EmojiView.this.onPageScrolled(i, (EmojiView.this.getMeasuredWidth() - EmojiView.this.getPaddingLeft()) - EmojiView.this.getPaddingRight(), i2);
            }

            public void onPageSelected(int i) {
                EmojiView.this.saveNewPage();
            }
        });
        View frameLayout2 = new FrameLayout(context);
        this.emojiTab.addView(frameLayout2, LayoutHelper.createLinear(52, 48));
        this.backspaceButton = new ImageView(context) {
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    EmojiView.this.backspacePressed = true;
                    EmojiView.this.backspaceOnce = false;
                    EmojiView.this.postBackspaceRunnable(350);
                } else if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                    EmojiView.this.backspacePressed = false;
                    if (!(EmojiView.this.backspaceOnce || EmojiView.this.listener == null || !EmojiView.this.listener.onBackspace())) {
                        EmojiView.this.backspaceButton.performHapticFeedback(3);
                    }
                }
                super.onTouchEvent(motionEvent);
                return true;
            }
        };
        this.backspaceButton.setImageResource(R.drawable.ic_smiles_backspace);
        this.backspaceButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_emojiPanelBackspace), Mode.MULTIPLY));
        this.backspaceButton.setScaleType(ScaleType.CENTER);
        frameLayout2.addView(this.backspaceButton, LayoutHelper.createFrame(52, 48.0f));
        View view = new View(context);
        view.setBackgroundColor(Theme.getColor(Theme.key_chat_emojiPanelShadowLine));
        frameLayout2.addView(view, LayoutHelper.createFrame(52, 1, 83));
        gridView = new TextView(context);
        gridView.setText(LocaleController.getString("NoRecent", R.string.NoRecent));
        gridView.setTextSize(1, 18.0f);
        gridView.setTextColor(Theme.getColor(Theme.key_chat_emojiPanelEmptyText));
        gridView.setGravity(17);
        gridView.setClickable(false);
        gridView.setFocusable(false);
        ((FrameLayout) this.views.get(0)).addView(gridView, LayoutHelper.createFrame(-2, -2.0f, 17, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        ((GridView) this.emojiGrids.get(0)).setEmptyView(gridView);
        addView(this.pager, 0, LayoutHelper.createFrame(-1, -1, 51));
        this.mediaBanTooltip = new CorrectlyMeasuringTextView(context);
        this.mediaBanTooltip.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
        this.mediaBanTooltip.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
        this.mediaBanTooltip.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
        this.mediaBanTooltip.setGravity(16);
        this.mediaBanTooltip.setTextSize(1, 14.0f);
        this.mediaBanTooltip.setVisibility(4);
        addView(this.mediaBanTooltip, LayoutHelper.createFrame(-2, -2.0f, 53, 30.0f, 53.0f, 5.0f, BitmapDescriptorFactory.HUE_RED));
        this.emojiSize = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 40.0f : 32.0f);
        this.pickerView = new EmojiColorPickerView(context);
        View view2 = this.pickerView;
        int dp = AndroidUtilities.dp((float) ((((AndroidUtilities.isTablet() ? 40 : 32) * 6) + 10) + 20));
        this.popupWidth = dp;
        int dp2 = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 64.0f : 56.0f);
        this.popupHeight = dp2;
        this.pickerViewPopup = new EmojiPopupWindow(view2, dp, dp2);
        this.pickerViewPopup.setOutsideTouchable(true);
        this.pickerViewPopup.setClippingEnabled(true);
        this.pickerViewPopup.setInputMethodMode(2);
        this.pickerViewPopup.setSoftInputMode(0);
        this.pickerViewPopup.getContentView().setFocusableInTouchMode(true);
        this.pickerViewPopup.getContentView().setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 82 || keyEvent.getRepeatCount() != 0 || keyEvent.getAction() != 1 || EmojiView.this.pickerViewPopup == null || !EmojiView.this.pickerViewPopup.isShowing()) {
                    return false;
                }
                EmojiView.this.pickerViewPopup.dismiss();
                return true;
            }
        });
        this.currentPage = getContext().getSharedPreferences("emoji", 0).getInt("selected_page", 0);
        Emoji.loadRecentEmoji();
        ((EmojiGridAdapter) this.adapters.get(0)).notifyDataSetChanged();
    }

    private static String addColorToCode(String str, String str2) {
        String str3 = null;
        int length = str.length();
        if (length > 2 && str.charAt(str.length() - 2) == '‍') {
            str3 = str.substring(str.length() - 2);
            str = str.substring(0, str.length() - 2);
        } else if (length > 3 && str.charAt(str.length() - 3) == '‍') {
            str3 = str.substring(str.length() - 3);
            str = str.substring(0, str.length() - 3);
        }
        String str4 = str + str2;
        return str3 != null ? str4 + str3 : str4;
    }

    private void checkDocuments(boolean z) {
        if (z) {
            int size = this.recentGifs.size();
            this.recentGifs = StickersQuery.getRecentGifs();
            if (this.gifsAdapter != null) {
                this.gifsAdapter.notifyDataSetChanged();
            }
            if (size != this.recentGifs.size()) {
                updateStickerTabs();
            }
            if (this.stickersGridAdapter != null) {
                this.stickersGridAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        int size2 = this.recentStickers.size();
        int size3 = this.favouriteStickers.size();
        this.recentStickers = StickersQuery.getRecentStickers(0);
        this.favouriteStickers = StickersQuery.getRecentStickers(2);
        for (int i = 0; i < this.favouriteStickers.size(); i++) {
            Document document = (Document) this.favouriteStickers.get(i);
            for (int i2 = 0; i2 < this.recentStickers.size(); i2++) {
                Document document2 = (Document) this.recentStickers.get(i2);
                if (document2.dc_id == document.dc_id && document2.id == document.id) {
                    this.recentStickers.remove(i2);
                    break;
                }
            }
        }
        if (!(size2 == this.recentStickers.size() && size3 == this.favouriteStickers.size())) {
            updateStickerTabs();
        }
        if (this.stickersGridAdapter != null) {
            this.stickersGridAdapter.notifyDataSetChanged();
        }
        checkPanels();
    }

    private void checkPanels() {
        int i = 8;
        if (this.stickersTab != null) {
            if (this.trendingTabNum == -2 && this.trendingGridView != null && this.trendingGridView.getVisibility() == 0) {
                this.gifsGridView.setVisibility(8);
                this.trendingGridView.setVisibility(8);
                this.stickersGridView.setVisibility(0);
                this.stickersEmptyView.setVisibility(this.stickersGridAdapter.getItemCount() != 0 ? 8 : 0);
            }
            if (this.gifTabNum == -2 && this.gifsGridView != null && this.gifsGridView.getVisibility() == 0) {
                this.listener.onGifTab(false);
                this.gifsGridView.setVisibility(8);
                this.trendingGridView.setVisibility(8);
                this.stickersGridView.setVisibility(0);
                TextView textView = this.stickersEmptyView;
                if (this.stickersGridAdapter.getItemCount() == 0) {
                    i = 0;
                }
                textView.setVisibility(i);
            } else if (this.gifTabNum == -2) {
            } else {
                if (this.gifsGridView != null && this.gifsGridView.getVisibility() == 0) {
                    this.stickersTab.onPageScrolled(this.gifTabNum + 1, (this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset) + 1);
                } else if (this.trendingGridView == null || this.trendingGridView.getVisibility() != 0) {
                    i = this.stickersLayoutManager.findFirstVisibleItemPosition();
                    if (i != -1) {
                        int i2 = this.favTabBum > 0 ? this.favTabBum : this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset;
                        this.stickersTab.onPageScrolled(this.stickersGridAdapter.getTabForPosition(i) + 1, i2 + 1);
                    }
                } else {
                    this.stickersTab.onPageScrolled(this.trendingTabNum + 1, (this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset) + 1);
                }
            }
        }
    }

    private void checkScroll() {
        int findFirstVisibleItemPosition = this.stickersLayoutManager.findFirstVisibleItemPosition();
        if (findFirstVisibleItemPosition != -1 && this.stickersGridView != null) {
            int i = this.favTabBum > 0 ? this.favTabBum : this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset;
            if (this.stickersGridView.getVisibility() != 0) {
                if (!(this.gifsGridView == null || this.gifsGridView.getVisibility() == 0)) {
                    this.gifsGridView.setVisibility(0);
                }
                if (this.stickersEmptyView != null && this.stickersEmptyView.getVisibility() == 0) {
                    this.stickersEmptyView.setVisibility(8);
                }
                this.stickersTab.onPageScrolled(this.gifTabNum + 1, i + 1);
                return;
            }
            this.stickersTab.onPageScrolled(this.stickersGridAdapter.getTabForPosition(findFirstVisibleItemPosition) + 1, i + 1);
        }
    }

    private void checkStickersTabY(View view, int i) {
        if (view == null) {
            ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
            this.minusDy = 0;
            scrollSlidingTabStrip.setTranslationY((float) null);
        } else if (view.getVisibility() == 0) {
            this.minusDy -= i;
            if (this.minusDy > 0) {
                this.minusDy = 0;
            } else if (this.minusDy < (-AndroidUtilities.dp(288.0f))) {
                this.minusDy = -AndroidUtilities.dp(288.0f);
            }
            this.stickersTab.setTranslationY((float) Math.max(-AndroidUtilities.dp(47.0f), this.minusDy));
        }
    }

    private void onPageScrolled(int i, int i2, int i3) {
        boolean z = true;
        int i4 = 0;
        if (this.stickersTab != null) {
            int i5;
            if (i2 == 0) {
                i2 = AndroidUtilities.displaySize.x;
            }
            int i6;
            if (i == 5) {
                i6 = -i3;
                if (this.listener != null) {
                    Listener listener = this.listener;
                    if (i3 == 0) {
                        z = false;
                    }
                    listener.onStickersTab(z);
                    i5 = i6;
                }
                i5 = i6;
            } else if (i == 6) {
                i6 = -i2;
                if (this.listener != null) {
                    this.listener.onStickersTab(true);
                    i5 = i6;
                }
                i5 = i6;
            } else {
                if (this.listener != null) {
                    this.listener.onStickersTab(false);
                }
                i5 = 0;
            }
            if (this.emojiTab.getTranslationX() != ((float) i5)) {
                this.emojiTab.setTranslationX((float) i5);
                this.stickersTab.setTranslationX((float) (i2 + i5));
                ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
                if (i5 >= 0) {
                    i4 = 4;
                }
                scrollSlidingTabStrip.setVisibility(i4);
            }
        }
    }

    private void postBackspaceRunnable(final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (EmojiView.this.backspacePressed) {
                    if (EmojiView.this.listener != null && EmojiView.this.listener.onBackspace()) {
                        EmojiView.this.backspaceButton.performHapticFeedback(3);
                    }
                    EmojiView.this.backspaceOnce = true;
                    EmojiView.this.postBackspaceRunnable(Math.max(50, i - 100));
                }
            }
        }, (long) i);
    }

    private void reloadStickersAdapter() {
        if (this.stickersGridAdapter != null) {
            this.stickersGridAdapter.notifyDataSetChanged();
        }
        if (this.trendingGridAdapter != null) {
            this.trendingGridAdapter.notifyDataSetChanged();
        }
        if (StickerPreviewViewer.getInstance().isVisible()) {
            StickerPreviewViewer.getInstance().close();
        }
        StickerPreviewViewer.getInstance().reset();
    }

    private void saveNewPage() {
        int i = this.pager.getCurrentItem() == 6 ? (this.gifsGridView == null || this.gifsGridView.getVisibility() != 0) ? 1 : 2 : 0;
        if (this.currentPage != i) {
            this.currentPage = i;
            getContext().getSharedPreferences("emoji", 0).edit().putInt("selected_page", i).commit();
        }
    }

    private void showGifTab() {
        this.gifsGridView.setVisibility(0);
        this.stickersGridView.setVisibility(8);
        this.stickersEmptyView.setVisibility(8);
        this.trendingGridView.setVisibility(8);
        this.stickersTab.onPageScrolled(this.gifTabNum + 1, (this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset) + 1);
        saveNewPage();
    }

    private void showTrendingTab() {
        this.trendingGridView.setVisibility(0);
        this.stickersGridView.setVisibility(8);
        this.stickersEmptyView.setVisibility(8);
        this.gifsGridView.setVisibility(8);
        this.stickersTab.onPageScrolled(this.trendingTabNum + 1, (this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset) + 1);
        saveNewPage();
    }

    private void updateStickerTabs() {
        boolean z = true;
        if (this.stickersTab != null) {
            int i;
            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet;
            this.recentTabBum = -2;
            this.favTabBum = -2;
            this.gifTabNum = -2;
            this.trendingTabNum = -2;
            this.stickersTabOffset = 0;
            int currentPosition = this.stickersTab.getCurrentPosition();
            this.stickersTab.removeTabs();
            Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_smiles2_smile);
            Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
            this.stickersTab.addIconTab(drawable);
            if (this.showGifs && !this.recentGifs.isEmpty()) {
                drawable = getContext().getResources().getDrawable(R.drawable.ic_smiles_gif);
                Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
                this.stickersTab.addIconTab(drawable);
                this.gifTabNum = this.stickersTabOffset;
                this.stickersTabOffset++;
            }
            ArrayList unreadStickerSets = StickersQuery.getUnreadStickerSets();
            if (!(this.trendingGridAdapter == null || this.trendingGridAdapter.getItemCount() == 0 || unreadStickerSets.isEmpty())) {
                drawable = getContext().getResources().getDrawable(R.drawable.ic_smiles_trend);
                Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
                TextView addIconTabWithCounter = this.stickersTab.addIconTabWithCounter(drawable);
                this.trendingTabNum = this.stickersTabOffset;
                this.stickersTabOffset++;
                addIconTabWithCounter.setText(String.format("%d", new Object[]{Integer.valueOf(unreadStickerSets.size())}));
            }
            if (!this.favouriteStickers.isEmpty()) {
                this.favTabBum = this.stickersTabOffset;
                this.stickersTabOffset++;
                drawable = getContext().getResources().getDrawable(R.drawable.staredstickerstab);
                Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
                this.stickersTab.addIconTab(drawable);
            }
            if (!this.recentStickers.isEmpty()) {
                this.recentTabBum = this.stickersTabOffset;
                this.stickersTabOffset++;
                drawable = getContext().getResources().getDrawable(R.drawable.ic_smiles2_recent);
                Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
                this.stickersTab.addIconTab(drawable);
            }
            this.stickerSets.clear();
            this.groupStickerSet = null;
            this.groupStickerPackPosition = -1;
            this.groupStickerPackNum = -10;
            ArrayList stickerSets = StickersQuery.getStickerSets(0);
            for (i = 0; i < stickerSets.size(); i++) {
                tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) stickerSets.get(i);
                if (!(tLRPC$TL_messages_stickerSet.set.archived || tLRPC$TL_messages_stickerSet.documents == null || tLRPC$TL_messages_stickerSet.documents.isEmpty())) {
                    this.stickerSets.add(tLRPC$TL_messages_stickerSet);
                }
            }
            if (this.info != null) {
                long j = getContext().getSharedPreferences("emoji", 0).getLong("group_hide_stickers_" + this.info.id, -1);
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.info.id));
                if (chat == null || this.info.stickerset == null || !ChatObject.hasAdminRights(chat)) {
                    this.groupStickersHidden = j != -1;
                } else if (this.info.stickerset != null) {
                    if (j != this.info.stickerset.id) {
                        z = false;
                    }
                    this.groupStickersHidden = z;
                }
                if (this.info.stickerset != null) {
                    TLRPC$TL_messages_stickerSet groupStickerSetById = StickersQuery.getGroupStickerSetById(this.info.stickerset);
                    if (!(groupStickerSetById == null || groupStickerSetById.documents == null || groupStickerSetById.documents.isEmpty() || groupStickerSetById.set == null)) {
                        tLRPC$TL_messages_stickerSet = new TLRPC$TL_messages_stickerSet();
                        tLRPC$TL_messages_stickerSet.documents = groupStickerSetById.documents;
                        tLRPC$TL_messages_stickerSet.packs = groupStickerSetById.packs;
                        tLRPC$TL_messages_stickerSet.set = groupStickerSetById.set;
                        if (this.groupStickersHidden) {
                            this.groupStickerPackNum = this.stickerSets.size();
                            this.stickerSets.add(tLRPC$TL_messages_stickerSet);
                        } else {
                            this.groupStickerPackNum = 0;
                            this.stickerSets.add(0, tLRPC$TL_messages_stickerSet);
                        }
                        if (!this.info.can_set_stickers) {
                            tLRPC$TL_messages_stickerSet = null;
                        }
                        this.groupStickerSet = tLRPC$TL_messages_stickerSet;
                    }
                } else if (this.info.can_set_stickers) {
                    tLRPC$TL_messages_stickerSet = new TLRPC$TL_messages_stickerSet();
                    if (this.groupStickersHidden) {
                        this.groupStickerPackNum = this.stickerSets.size();
                        this.stickerSets.add(tLRPC$TL_messages_stickerSet);
                    } else {
                        this.groupStickerPackNum = 0;
                        this.stickerSets.add(0, tLRPC$TL_messages_stickerSet);
                    }
                }
            }
            i = 0;
            while (i < this.stickerSets.size()) {
                if (i == this.groupStickerPackNum) {
                    Chat chat2 = MessagesController.getInstance().getChat(Integer.valueOf(this.info.id));
                    if (chat2 == null) {
                        this.stickerSets.remove(0);
                        i--;
                    } else {
                        this.stickersTab.addStickerTab(chat2);
                    }
                } else {
                    this.stickersTab.addStickerTab((Document) ((TLRPC$TL_messages_stickerSet) this.stickerSets.get(i)).documents.get(0));
                }
                i++;
            }
            if (!(this.trendingGridAdapter == null || this.trendingGridAdapter.getItemCount() == 0 || !unreadStickerSets.isEmpty())) {
                drawable = getContext().getResources().getDrawable(R.drawable.ic_smiles_trend);
                Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
                this.trendingTabNum = this.stickersTabOffset + this.stickerSets.size();
                this.stickersTab.addIconTab(drawable);
            }
            drawable = getContext().getResources().getDrawable(R.drawable.ic_smiles_settings);
            Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
            this.stickersTab.addIconTab(drawable);
            this.stickersTab.updateTabStyles();
            if (currentPosition != 0) {
                this.stickersTab.onPageScrolled(currentPosition, currentPosition);
            }
            if (this.switchToGifTab && this.gifTabNum >= 0 && this.gifsGridView.getVisibility() != 0) {
                showGifTab();
                this.switchToGifTab = false;
            }
            checkPanels();
        }
    }

    private void updateVisibleTrendingSets() {
        if (this.trendingGridAdapter != null && this.trendingGridAdapter != null) {
            try {
                int childCount = this.trendingGridView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = this.trendingGridView.getChildAt(i);
                    if ((childAt instanceof FeaturedStickerSetInfoCell) && ((Holder) this.trendingGridView.getChildViewHolder(childAt)) != null) {
                        FeaturedStickerSetInfoCell featuredStickerSetInfoCell = (FeaturedStickerSetInfoCell) childAt;
                        ArrayList unreadStickerSets = StickersQuery.getUnreadStickerSets();
                        StickerSetCovered stickerSet = featuredStickerSetInfoCell.getStickerSet();
                        boolean z = unreadStickerSets != null && unreadStickerSets.contains(Long.valueOf(stickerSet.set.id));
                        featuredStickerSetInfoCell.setStickerSet(stickerSet, z);
                        if (z) {
                            StickersQuery.markFaturedStickersByIdAsRead(stickerSet.set.id);
                        }
                        boolean containsKey = this.installingStickerSets.containsKey(Long.valueOf(stickerSet.set.id));
                        z = this.removingStickerSets.containsKey(Long.valueOf(stickerSet.set.id));
                        if (containsKey || z) {
                            if (containsKey && featuredStickerSetInfoCell.isInstalled()) {
                                this.installingStickerSets.remove(Long.valueOf(stickerSet.set.id));
                                containsKey = false;
                            } else if (z) {
                                if (!featuredStickerSetInfoCell.isInstalled()) {
                                    this.removingStickerSets.remove(Long.valueOf(stickerSet.set.id));
                                    z = false;
                                }
                            }
                        }
                        z = containsKey || z;
                        featuredStickerSetInfoCell.setDrawProgress(z);
                    }
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    public void addEmojiToRecent(String str) {
        Emoji.addRecentEmoji(str);
        if (!(getVisibility() == 0 && this.pager.getCurrentItem() == 0)) {
            Emoji.sortEmoji();
        }
        Emoji.saveRecentEmoji();
        ((EmojiGridAdapter) this.adapters.get(0)).notifyDataSetChanged();
    }

    public void addRecentGif(Document document) {
        if (document != null) {
            boolean isEmpty = this.recentGifs.isEmpty();
            this.recentGifs = StickersQuery.getRecentGifs();
            if (this.gifsAdapter != null) {
                this.gifsAdapter.notifyDataSetChanged();
            }
            if (isEmpty) {
                updateStickerTabs();
            }
        }
    }

    public void addRecentSticker(Document document) {
        if (document != null) {
            StickersQuery.addRecentSticker(0, document, (int) (System.currentTimeMillis() / 1000), false);
            boolean isEmpty = this.recentStickers.isEmpty();
            this.recentStickers = StickersQuery.getRecentStickers(0);
            if (this.stickersGridAdapter != null) {
                this.stickersGridAdapter.notifyDataSetChanged();
            }
            if (isEmpty) {
                updateStickerTabs();
            }
        }
    }

    public boolean areThereAnyStickers() {
        return this.stickersGridAdapter != null && this.stickersGridAdapter.getItemCount() > 0;
    }

    public void clearRecentEmoji() {
        Emoji.clearRecentEmoji();
        ((EmojiGridAdapter) this.adapters.get(0)).notifyDataSetChanged();
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        if (i == NotificationCenter.stickersDidLoaded) {
            if (((Integer) objArr[0]).intValue() == 0) {
                if (this.trendingGridAdapter != null) {
                    if (this.trendingLoaded) {
                        updateVisibleTrendingSets();
                    } else {
                        this.trendingGridAdapter.notifyDataSetChanged();
                    }
                }
                updateStickerTabs();
                reloadStickersAdapter();
                checkPanels();
            }
        } else if (i == NotificationCenter.recentDocumentsDidLoaded) {
            boolean booleanValue = ((Boolean) objArr[0]).booleanValue();
            i2 = ((Integer) objArr[1]).intValue();
            if (booleanValue || i2 == 0 || i2 == 2) {
                checkDocuments(booleanValue);
            }
        } else if (i == NotificationCenter.featuredStickersDidLoaded) {
            if (this.trendingGridAdapter != null) {
                if (this.featuredStickersHash != StickersQuery.getFeaturesStickersHashWithoutUnread()) {
                    this.trendingLoaded = false;
                }
                if (this.trendingLoaded) {
                    updateVisibleTrendingSets();
                } else {
                    this.trendingGridAdapter.notifyDataSetChanged();
                }
            }
            if (this.pagerSlidingTabStrip != null) {
                int childCount = this.pagerSlidingTabStrip.getChildCount();
                while (i2 < childCount) {
                    this.pagerSlidingTabStrip.getChildAt(i2).invalidate();
                    i2++;
                }
            }
            updateStickerTabs();
        } else if (i == NotificationCenter.groupStickersDidLoaded && this.info != null && this.info.stickerset != null && this.info.stickerset.id == ((Long) objArr[0]).longValue()) {
            updateStickerTabs();
        }
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void invalidateViews() {
        for (int i = 0; i < this.emojiGrids.size(); i++) {
            ((GridView) this.emojiGrids.get(i)).invalidateViews();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.stickersGridAdapter != null) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.recentImagesDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.featuredStickersDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.groupStickersDidLoaded);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    EmojiView.this.updateStickerTabs();
                    EmojiView.this.reloadStickersAdapter();
                }
            });
        }
    }

    public void onDestroy() {
        if (this.stickersGridAdapter != null) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stickersDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.recentDocumentsDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.featuredStickersDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.groupStickersDidLoaded);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.pickerViewPopup != null && this.pickerViewPopup.isShowing()) {
            this.pickerViewPopup.dismiss();
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.lastNotifyWidth != i3 - i) {
            this.lastNotifyWidth = i3 - i;
            reloadStickersAdapter();
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    public void onMeasure(int i, int i2) {
        ViewGroup.LayoutParams layoutParams = null;
        this.isLayout = true;
        if (AndroidUtilities.isInMultiwindow) {
            if (this.currentBackgroundType != 1) {
                if (VERSION.SDK_INT >= 21) {
                    setOutlineProvider((ViewOutlineProvider) this.outlineProvider);
                    setClipToOutline(true);
                    setElevation((float) AndroidUtilities.dp(2.0f));
                }
                setBackgroundResource(R.drawable.smiles_popup);
                getBackground().setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_emojiPanelBackground), Mode.MULTIPLY));
                this.emojiTab.setBackgroundDrawable(null);
                this.currentBackgroundType = 1;
            }
        } else if (this.currentBackgroundType != 0) {
            if (VERSION.SDK_INT >= 21) {
                setOutlineProvider(null);
                setClipToOutline(false);
                setElevation(BitmapDescriptorFactory.HUE_RED);
            }
            setBackgroundColor(Theme.getColor(Theme.key_chat_emojiPanelBackground));
            this.emojiTab.setBackgroundColor(Theme.getColor(Theme.key_chat_emojiPanelBackground));
            this.currentBackgroundType = 0;
        }
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.emojiTab.getLayoutParams();
        layoutParams2.width = MeasureSpec.getSize(i);
        if (this.stickersTab != null) {
            layoutParams = (FrameLayout.LayoutParams) this.stickersTab.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = layoutParams2.width;
            }
        }
        if (layoutParams2.width != this.oldWidth) {
            if (!(this.stickersTab == null || layoutParams == null)) {
                onPageScrolled(this.pager.getCurrentItem(), (layoutParams2.width - getPaddingLeft()) - getPaddingRight(), 0);
                this.stickersTab.setLayoutParams(layoutParams);
            }
            this.emojiTab.setLayoutParams(layoutParams2);
            this.oldWidth = layoutParams2.width;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(layoutParams2.width, 1073741824), MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i2), 1073741824));
        this.isLayout = false;
    }

    public void onOpen(boolean z) {
        boolean z2 = true;
        if (this.stickersTab != null) {
            if (!(this.currentPage == 0 || this.currentChatId == 0)) {
                this.currentPage = 0;
            }
            if (this.currentPage == 0 || z) {
                if (this.pager.getCurrentItem() == 6) {
                    ViewPager viewPager = this.pager;
                    if (z) {
                        z2 = false;
                    }
                    viewPager.setCurrentItem(0, z2);
                }
            } else if (this.currentPage == 1) {
                if (this.pager.getCurrentItem() != 6) {
                    this.pager.setCurrentItem(6);
                }
                if (this.stickersTab.getCurrentPosition() != this.gifTabNum + 1) {
                    return;
                }
                if (this.recentTabBum >= 0) {
                    this.stickersTab.selectTab(this.recentTabBum + 1);
                } else if (this.favTabBum >= 0) {
                    this.stickersTab.selectTab(this.favTabBum + 1);
                } else if (this.gifTabNum >= 0) {
                    this.stickersTab.selectTab(this.gifTabNum + 2);
                } else {
                    this.stickersTab.selectTab(1);
                }
            } else if (this.currentPage == 2) {
                if (this.pager.getCurrentItem() != 6) {
                    this.pager.setCurrentItem(6);
                }
                if (this.stickersTab.getCurrentPosition() == this.gifTabNum + 1) {
                    return;
                }
                if (this.gifTabNum < 0 || this.recentGifs.isEmpty()) {
                    this.switchToGifTab = true;
                } else {
                    this.stickersTab.selectTab(this.gifTabNum + 1);
                }
            }
        }
    }

    public void requestLayout() {
        if (!this.isLayout) {
            super.requestLayout();
        }
    }

    public void setChatInfo(ChatFull chatFull) {
        this.info = chatFull;
        updateStickerTabs();
    }

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setStickersBanned(boolean z, int i) {
        if (z) {
            this.currentChatId = i;
        } else {
            this.currentChatId = 0;
        }
        View tab = this.pagerSlidingTabStrip.getTab(6);
        if (tab != null) {
            tab.setAlpha(this.currentChatId != 0 ? 0.5f : 1.0f);
            if (this.currentChatId != 0 && this.pager.getCurrentItem() == 6) {
                this.pager.setCurrentItem(0);
            }
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i != 8) {
            Emoji.sortEmoji();
            ((EmojiGridAdapter) this.adapters.get(0)).notifyDataSetChanged();
            if (this.stickersGridAdapter != null) {
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.recentDocumentsDidLoaded);
                updateStickerTabs();
                reloadStickersAdapter();
                if (!(this.gifsGridView == null || this.gifsGridView.getVisibility() != 0 || this.listener == null)) {
                    Listener listener = this.listener;
                    boolean z = this.pager != null && this.pager.getCurrentItem() >= 6;
                    listener.onGifTab(z);
                }
            }
            if (this.trendingGridAdapter != null) {
                this.trendingLoaded = false;
                this.trendingGridAdapter.notifyDataSetChanged();
            }
            checkDocuments(true);
            checkDocuments(false);
            StickersQuery.loadRecents(0, true, true, false);
            StickersQuery.loadRecents(0, false, true, false);
            StickersQuery.loadRecents(2, false, true, false);
        }
    }

    public void showStickerBanHint() {
        if (this.mediaBanTooltip.getVisibility() != 0) {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.currentChatId));
            if (chat != null && chat.banned_rights != null) {
                if (AndroidUtilities.isBannedForever(chat.banned_rights.until_date)) {
                    this.mediaBanTooltip.setText(LocaleController.getString("AttachStickersRestrictedForever", R.string.AttachStickersRestrictedForever));
                } else {
                    this.mediaBanTooltip.setText(LocaleController.formatString("AttachStickersRestricted", R.string.AttachStickersRestricted, new Object[]{LocaleController.formatDateForBan((long) chat.banned_rights.until_date)}));
                }
                this.mediaBanTooltip.setVisibility(0);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.mediaBanTooltip, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                animatorSet.addListener(new AnimatorListenerAdapter() {

                    /* renamed from: org.telegram.ui.Components.EmojiView$28$1 */
                    class C44091 implements Runnable {

                        /* renamed from: org.telegram.ui.Components.EmojiView$28$1$1 */
                        class C44081 extends AnimatorListenerAdapter {
                            C44081() {
                            }

                            public void onAnimationEnd(Animator animator) {
                                if (EmojiView.this.mediaBanTooltip != null) {
                                    EmojiView.this.mediaBanTooltip.setVisibility(4);
                                }
                            }
                        }

                        C44091() {
                        }

                        public void run() {
                            if (EmojiView.this.mediaBanTooltip != null) {
                                AnimatorSet animatorSet = new AnimatorSet();
                                Animator[] animatorArr = new Animator[1];
                                animatorArr[0] = ObjectAnimator.ofFloat(EmojiView.this.mediaBanTooltip, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                animatorSet.playTogether(animatorArr);
                                animatorSet.addListener(new C44081());
                                animatorSet.setDuration(300);
                                animatorSet.start();
                            }
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        AndroidUtilities.runOnUIThread(new C44091(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                    }
                });
                animatorSet.setDuration(300);
                animatorSet.start();
            }
        }
    }

    public void switchToGifRecent() {
        if (this.gifTabNum < 0 || this.recentGifs.isEmpty()) {
            this.switchToGifTab = true;
        } else {
            this.stickersTab.selectTab(this.gifTabNum + 1);
        }
        this.pager.setCurrentItem(6);
    }
}
