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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import android.widget.PopupWindow;
import android.widget.TextView;
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
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$InputStickerSet;
import org.telegram.tgnet.TLRPC$StickerSet;
import org.telegram.tgnet.TLRPC$StickerSetCovered;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
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
    private static final OnScrollChangedListener NOP = new C25722();
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
    private ArrayList<TLRPC$Document> favouriteStickers = new ArrayList();
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
    private TLRPC$ChatFull info;
    private HashMap<Long, TLRPC$StickerSetCovered> installingStickerSets = new HashMap();
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
    private ArrayList<TLRPC$Document> recentGifs = new ArrayList();
    private ArrayList<TLRPC$Document> recentStickers = new ArrayList();
    private int recentTabBum = -2;
    private HashMap<Long, TLRPC$StickerSetCovered> removingStickerSets = new HashMap();
    private boolean showGifs;
    private StickerPreviewViewerDelegate stickerPreviewViewerDelegate = new C25691();
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

        void onGifSelected(TLRPC$Document tLRPC$Document);

        void onGifTab(boolean z);

        void onShowStickerSet(TLRPC$StickerSet tLRPC$StickerSet, TLRPC$InputStickerSet tLRPC$InputStickerSet);

        void onStickerSelected(TLRPC$Document tLRPC$Document);

        void onStickerSetAdd(TLRPC$StickerSetCovered tLRPC$StickerSetCovered);

        void onStickerSetRemove(TLRPC$StickerSetCovered tLRPC$StickerSetCovered);

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
    class C25691 implements StickerPreviewViewerDelegate {
        C25691() {
        }

        public void sentSticker(TLRPC$Document sticker) {
            EmojiView.this.listener.onStickerSelected(sticker);
        }

        public void openSet(TLRPC$InputStickerSet set) {
            if (set != null) {
                EmojiView.this.listener.onShowStickerSet(null, set);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$2 */
    static class C25722 implements OnScrollChangedListener {
        C25722() {
        }

        public void onScrollChanged() {
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$3 */
    class C25733 extends ViewOutlineProvider {
        C25733() {
        }

        @TargetApi(21)
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(view.getPaddingLeft(), view.getPaddingTop(), view.getMeasuredWidth() - view.getPaddingRight(), view.getMeasuredHeight() - view.getPaddingBottom(), (float) AndroidUtilities.dp(6.0f));
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$5 */
    class C25755 extends SpanSizeLookup {
        C25755() {
        }

        public int getSpanSize(int position) {
            if (position == EmojiView.this.stickersGridAdapter.totalItems || (EmojiView.this.stickersGridAdapter.cache.get(Integer.valueOf(position)) != null && !(EmojiView.this.stickersGridAdapter.cache.get(Integer.valueOf(position)) instanceof TLRPC$Document))) {
                return EmojiView.this.stickersGridAdapter.stickersPerRow;
            }
            return 1;
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$6 */
    class C25766 implements OnTouchListener {
        C25766() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return StickerPreviewViewer.getInstance().onTouch(event, EmojiView.this.stickersGridView, EmojiView.this.getMeasuredHeight(), EmojiView.this.stickersOnItemClickListener, EmojiView.this.stickerPreviewViewerDelegate);
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$7 */
    class C25777 implements OnItemClickListener {
        C25777() {
        }

        public void onItemClick(View view, int position) {
            if (view instanceof StickerEmojiCell) {
                StickerPreviewViewer.getInstance().reset();
                StickerEmojiCell cell = (StickerEmojiCell) view;
                if (!cell.isDisabled()) {
                    cell.disable();
                    EmojiView.this.listener.onStickerSelected(cell.getSticker());
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$9 */
    class C25799 extends SpanSizeLookup {
        C25799() {
        }

        public int getSpanSize(int position) {
            if ((EmojiView.this.trendingGridAdapter.cache.get(Integer.valueOf(position)) instanceof Integer) || position == EmojiView.this.trendingGridAdapter.totalItems) {
                return EmojiView.this.trendingGridAdapter.stickersPerRow;
            }
            return 1;
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

        public void setEmoji(String emoji, int arrowPosition) {
            this.currentEmoji = emoji;
            this.arrowX = arrowPosition;
            this.rectPaint.setColor(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR);
            invalidate();
        }

        public String getEmoji() {
            return this.currentEmoji;
        }

        public void setSelection(int position) {
            if (this.selection != position) {
                this.selection = position;
                invalidate();
            }
        }

        public int getSelection() {
            return this.selection;
        }

        public EmojiColorPickerView(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            float f;
            this.backgroundDrawable.setBounds(0, 0, getMeasuredWidth(), AndroidUtilities.dp(AndroidUtilities.isTablet() ? 60.0f : 52.0f));
            this.backgroundDrawable.draw(canvas);
            Drawable drawable = this.arrowDrawable;
            int dp = this.arrowX - AndroidUtilities.dp(9.0f);
            int dp2 = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 55.5f : 47.5f);
            int dp3 = AndroidUtilities.dp(9.0f) + this.arrowX;
            if (AndroidUtilities.isTablet()) {
                f = 55.5f;
            } else {
                f = 47.5f;
            }
            drawable.setBounds(dp, dp2, dp3, AndroidUtilities.dp(f + 8.0f));
            this.arrowDrawable.draw(canvas);
            if (this.currentEmoji != null) {
                for (int a = 0; a < 6; a++) {
                    int x = (EmojiView.this.emojiSize * a) + AndroidUtilities.dp((float) ((a * 4) + 5));
                    int y = AndroidUtilities.dp(9.0f);
                    if (this.selection == a) {
                        this.rect.set((float) x, (float) (y - ((int) AndroidUtilities.dpf2(3.5f))), (float) (EmojiView.this.emojiSize + x), (float) ((EmojiView.this.emojiSize + y) + AndroidUtilities.dp(3.0f)));
                        canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(4.0f), this.rectPaint);
                    }
                    String code = this.currentEmoji;
                    if (a != 0) {
                        String color;
                        switch (a) {
                            case 1:
                                color = "🏻";
                                break;
                            case 2:
                                color = "🏼";
                                break;
                            case 3:
                                color = "🏽";
                                break;
                            case 4:
                                color = "🏾";
                                break;
                            case 5:
                                color = "🏿";
                                break;
                            default:
                                color = "";
                                break;
                        }
                        code = EmojiView.addColorToCode(code, color);
                    }
                    Drawable drawable2 = Emoji.getEmojiBigDrawable(code);
                    if (drawable2 != null) {
                        drawable2.setBounds(x, y, EmojiView.this.emojiSize + x, EmojiView.this.emojiSize + y);
                        drawable2.draw(canvas);
                    }
                }
            }
        }
    }

    private class EmojiGridAdapter extends BaseAdapter {
        private int emojiPage;

        public EmojiGridAdapter(int page) {
            this.emojiPage = page;
        }

        public Object getItem(int position) {
            return null;
        }

        public int getCount() {
            if (this.emojiPage == -1) {
                return Emoji.recentEmoji.size();
            }
            return EmojiData.dataColored[this.emojiPage].length;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View view, ViewGroup paramViewGroup) {
            String code;
            String coloredCode;
            ImageViewEmoji imageView = (ImageViewEmoji) view;
            if (imageView == null) {
                imageView = new ImageViewEmoji(EmojiView.this.getContext());
            }
            if (this.emojiPage == -1) {
                code = (String) Emoji.recentEmoji.get(position);
                coloredCode = code;
            } else {
                code = EmojiData.dataColored[this.emojiPage][position];
                coloredCode = code;
                String color = (String) Emoji.emojiColor.get(code);
                if (color != null) {
                    coloredCode = EmojiView.addColorToCode(coloredCode, color);
                }
            }
            imageView.setImageDrawable(Emoji.getEmojiBigDrawable(coloredCode));
            imageView.setTag(code);
            return imageView;
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
            }
        }
    }

    private class EmojiPagesAdapter extends PagerAdapter implements IconTabProvider {
        private EmojiPagesAdapter() {
        }

        public void destroyItem(ViewGroup viewGroup, int position, Object object) {
            View view;
            if (position == 6) {
                view = EmojiView.this.stickersWrap;
            } else {
                view = (View) EmojiView.this.views.get(position);
            }
            viewGroup.removeView(view);
        }

        public boolean canScrollToTab(int position) {
            if (position != 6 || EmojiView.this.currentChatId == 0) {
                return true;
            }
            EmojiView.this.showStickerBanHint();
            return false;
        }

        public int getCount() {
            return EmojiView.this.views.size();
        }

        public Drawable getPageIconDrawable(int position) {
            return EmojiView.this.icons[position];
        }

        public void customOnDraw(Canvas canvas, int position) {
            if (position == 6 && !StickersQuery.getUnreadStickerSets().isEmpty() && EmojiView.this.dotPaint != null) {
                canvas.drawCircle((float) ((canvas.getWidth() / 2) + AndroidUtilities.dp(9.0f)), (float) ((canvas.getHeight() / 2) - AndroidUtilities.dp(8.0f)), (float) AndroidUtilities.dp(5.0f), EmojiView.this.dotPaint);
            }
        }

        public Object instantiateItem(ViewGroup viewGroup, int position) {
            View view;
            if (position == 6) {
                view = EmojiView.this.stickersWrap;
            } else {
                view = (View) EmojiView.this.views.get(position);
            }
            viewGroup.addView(view);
            return view;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
            }
        }
    }

    private class EmojiPopupWindow extends PopupWindow {
        private OnScrollChangedListener mSuperScrollListener;
        private ViewTreeObserver mViewTreeObserver;

        public EmojiPopupWindow() {
            init();
        }

        public EmojiPopupWindow(Context context) {
            super(context);
            init();
        }

        public EmojiPopupWindow(int width, int height) {
            super(width, height);
            init();
        }

        public EmojiPopupWindow(View contentView) {
            super(contentView);
            init();
        }

        public EmojiPopupWindow(View contentView, int width, int height, boolean focusable) {
            super(contentView, width, height, focusable);
            init();
        }

        public EmojiPopupWindow(View contentView, int width, int height) {
            super(contentView, width, height);
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

        private void unregisterListener() {
            if (this.mSuperScrollListener != null && this.mViewTreeObserver != null) {
                if (this.mViewTreeObserver.isAlive()) {
                    this.mViewTreeObserver.removeOnScrollChangedListener(this.mSuperScrollListener);
                }
                this.mViewTreeObserver = null;
            }
        }

        private void registerListener(View anchor) {
            if (this.mSuperScrollListener != null) {
                ViewTreeObserver vto = anchor.getWindowToken() != null ? anchor.getViewTreeObserver() : null;
                if (vto != this.mViewTreeObserver) {
                    if (this.mViewTreeObserver != null && this.mViewTreeObserver.isAlive()) {
                        this.mViewTreeObserver.removeOnScrollChangedListener(this.mSuperScrollListener);
                    }
                    this.mViewTreeObserver = vto;
                    if (vto != null) {
                        vto.addOnScrollChangedListener(this.mSuperScrollListener);
                    }
                }
            }
        }

        public void showAsDropDown(View anchor, int xoff, int yoff) {
            try {
                super.showAsDropDown(anchor, xoff, yoff);
                registerListener(anchor);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void update(View anchor, int xoff, int yoff, int width, int height) {
            super.update(anchor, xoff, yoff, width, height);
            registerListener(anchor);
        }

        public void update(View anchor, int width, int height) {
            super.update(anchor, width, height);
            registerListener(anchor);
        }

        public void showAtLocation(View parent, int gravity, int x, int y) {
            super.showAtLocation(parent, gravity, x, y);
            unregisterListener();
        }

        public void dismiss() {
            setFocusable(false);
            try {
                super.dismiss();
            } catch (Exception e) {
            }
            unregisterListener();
        }
    }

    private class GifsAdapter extends SelectionAdapter {
        private Context mContext;

        public GifsAdapter(Context context) {
            this.mContext = context;
        }

        public boolean isEnabled(ViewHolder holder) {
            return false;
        }

        public int getItemCount() {
            return EmojiView.this.recentGifs.size();
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(new ContextLinkCell(this.mContext));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            TLRPC$Document document = (TLRPC$Document) EmojiView.this.recentGifs.get(i);
            if (document != null) {
                ((ContextLinkCell) viewHolder.itemView).setGif(document, false);
            }
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
                public void onClick(View v) {
                    ImageViewEmoji.this.sendEmoji(null);
                }
            });
            setOnLongClickListener(new OnLongClickListener(EmojiView.this) {
                public boolean onLongClick(View view) {
                    int yOffset = 0;
                    String code = (String) view.getTag();
                    if (EmojiData.emojiColoredMap.containsKey(code)) {
                        int i;
                        ImageViewEmoji.this.touched = true;
                        ImageViewEmoji.this.touchedX = ImageViewEmoji.this.lastX;
                        ImageViewEmoji.this.touchedY = ImageViewEmoji.this.lastY;
                        String color = (String) Emoji.emojiColor.get(code);
                        if (color != null) {
                            i = -1;
                            switch (color.hashCode()) {
                                case 1773375:
                                    if (color.equals("🏻")) {
                                        i = 0;
                                        break;
                                    }
                                    break;
                                case 1773376:
                                    if (color.equals("🏼")) {
                                        boolean z = true;
                                        break;
                                    }
                                    break;
                                case 1773377:
                                    if (color.equals("🏽")) {
                                        i = 2;
                                        break;
                                    }
                                    break;
                                case 1773378:
                                    if (color.equals("🏾")) {
                                        i = 3;
                                        break;
                                    }
                                    break;
                                case 1773379:
                                    if (color.equals("🏿")) {
                                        i = 4;
                                        break;
                                    }
                                    break;
                            }
                            switch (i) {
                                case 0:
                                    EmojiView.this.pickerView.setSelection(1);
                                    break;
                                case 1:
                                    EmojiView.this.pickerView.setSelection(2);
                                    break;
                                case 2:
                                    EmojiView.this.pickerView.setSelection(3);
                                    break;
                                case 3:
                                    EmojiView.this.pickerView.setSelection(4);
                                    break;
                                case 4:
                                    EmojiView.this.pickerView.setSelection(5);
                                    break;
                            }
                        }
                        EmojiView.this.pickerView.setSelection(0);
                        view.getLocationOnScreen(EmojiView.this.location);
                        int selection = EmojiView.this.pickerView.getSelection() * EmojiView.this.emojiSize;
                        int selection2 = EmojiView.this.pickerView.getSelection() * 4;
                        if (AndroidUtilities.isTablet()) {
                            i = 5;
                        } else {
                            i = 1;
                        }
                        int x = selection + AndroidUtilities.dp((float) (selection2 - i));
                        if (EmojiView.this.location[0] - x < AndroidUtilities.dp(5.0f)) {
                            x += (EmojiView.this.location[0] - x) - AndroidUtilities.dp(5.0f);
                        } else if ((EmojiView.this.location[0] - x) + EmojiView.this.popupWidth > AndroidUtilities.displaySize.x - AndroidUtilities.dp(5.0f)) {
                            x += ((EmojiView.this.location[0] - x) + EmojiView.this.popupWidth) - (AndroidUtilities.displaySize.x - AndroidUtilities.dp(5.0f));
                        }
                        int xOffset = -x;
                        if (view.getTop() < 0) {
                            yOffset = view.getTop();
                        }
                        EmojiView.this.pickerView.setEmoji(code, (AndroidUtilities.dp(AndroidUtilities.isTablet() ? 30.0f : 22.0f) - xOffset) + ((int) AndroidUtilities.dpf2(0.5f)));
                        EmojiView.this.pickerViewPopup.setFocusable(true);
                        EmojiView.this.pickerViewPopup.showAsDropDown(view, xOffset, (((-view.getMeasuredHeight()) - EmojiView.this.popupHeight) + ((view.getMeasuredHeight() - EmojiView.this.emojiSize) / 2)) - yOffset);
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                    if (EmojiView.this.pager.getCurrentItem() == 0) {
                        EmojiView.this.listener.onClearEmojiRecent();
                    }
                    return false;
                }
            });
            setBackgroundDrawable(Theme.getSelectorDrawable(false));
            setScaleType(ScaleType.CENTER);
        }

        private void sendEmoji(String override) {
            String code;
            if (override != null) {
                code = override;
            } else {
                code = (String) getTag();
            }
            new SpannableStringBuilder().append(code);
            if (override == null) {
                if (EmojiView.this.pager.getCurrentItem() != 0) {
                    String color = (String) Emoji.emojiColor.get(code);
                    if (color != null) {
                        code = EmojiView.addColorToCode(code, color);
                    }
                }
                EmojiView.this.addEmojiToRecent(code);
                if (EmojiView.this.listener != null) {
                    EmojiView.this.listener.onEmojiSelected(Emoji.fixEmoji(code));
                }
            } else if (EmojiView.this.listener != null) {
                EmojiView.this.listener.onEmojiSelected(Emoji.fixEmoji(override));
            }
        }

        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(widthMeasureSpec));
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (this.touched) {
                if (event.getAction() == 1 || event.getAction() == 3) {
                    if (EmojiView.this.pickerViewPopup != null && EmojiView.this.pickerViewPopup.isShowing()) {
                        EmojiView.this.pickerViewPopup.dismiss();
                        String color = null;
                        switch (EmojiView.this.pickerView.getSelection()) {
                            case 1:
                                color = "🏻";
                                break;
                            case 2:
                                color = "🏼";
                                break;
                            case 3:
                                color = "🏽";
                                break;
                            case 4:
                                color = "🏾";
                                break;
                            case 5:
                                color = "🏿";
                                break;
                        }
                        String code = (String) getTag();
                        if (EmojiView.this.pager.getCurrentItem() != 0) {
                            if (color != null) {
                                Emoji.emojiColor.put(code, color);
                                code = EmojiView.addColorToCode(code, color);
                            } else {
                                Emoji.emojiColor.remove(code);
                            }
                            setImageDrawable(Emoji.getEmojiBigDrawable(code));
                            sendEmoji(null);
                            Emoji.saveEmojiColors();
                        } else if (color != null) {
                            sendEmoji(EmojiView.addColorToCode(code, color));
                        } else {
                            sendEmoji(code);
                        }
                    }
                    this.touched = false;
                    this.touchedX = -10000.0f;
                    this.touchedY = -10000.0f;
                } else if (event.getAction() == 2) {
                    boolean ignore = false;
                    if (this.touchedX != -10000.0f) {
                        if (Math.abs(this.touchedX - event.getX()) > AndroidUtilities.getPixelsInCM(0.2f, true) || Math.abs(this.touchedY - event.getY()) > AndroidUtilities.getPixelsInCM(0.2f, false)) {
                            this.touchedX = -10000.0f;
                            this.touchedY = -10000.0f;
                        } else {
                            ignore = true;
                        }
                    }
                    if (!ignore) {
                        getLocationOnScreen(EmojiView.this.location);
                        float x = ((float) EmojiView.this.location[0]) + event.getX();
                        EmojiView.this.pickerView.getLocationOnScreen(EmojiView.this.location);
                        int position = (int) ((x - ((float) (EmojiView.this.location[0] + AndroidUtilities.dp(3.0f)))) / ((float) (EmojiView.this.emojiSize + AndroidUtilities.dp(4.0f))));
                        if (position < 0) {
                            position = 0;
                        } else if (position > 5) {
                            position = 5;
                        }
                        EmojiView.this.pickerView.setSelection(position);
                    }
                }
            }
            this.lastX = event.getX();
            this.lastY = event.getY();
            return super.onTouchEvent(event);
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
        class C25832 implements OnClickListener {
            C25832() {
            }

            public void onClick(View v) {
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
        class C25843 implements OnClickListener {
            C25843() {
            }

            public void onClick(View v) {
                if (EmojiView.this.listener != null) {
                    EmojiView.this.listener.onStickersGroupClick(EmojiView.this.info.id);
                }
            }
        }

        public StickersGridAdapter(Context context) {
            this.context = context;
        }

        public boolean isEnabled(ViewHolder holder) {
            return false;
        }

        public int getItemCount() {
            return this.totalItems != 0 ? this.totalItems + 1 : 0;
        }

        public Object getItem(int i) {
            return this.cache.get(Integer.valueOf(i));
        }

        public int getPositionForPack(Object pack) {
            Integer pos = (Integer) this.packStartPosition.get(pack);
            if (pos == null) {
                return -1;
            }
            return pos.intValue();
        }

        public int getItemViewType(int position) {
            Object object = this.cache.get(Integer.valueOf(position));
            if (object == null) {
                return 1;
            }
            if (object instanceof TLRPC$Document) {
                return 0;
            }
            if (object instanceof String) {
                return 3;
            }
            return 2;
        }

        public int getTabForPosition(int position) {
            if (this.stickersPerRow == 0) {
                int width = EmojiView.this.getMeasuredWidth();
                if (width == 0) {
                    width = AndroidUtilities.displaySize.x;
                }
                this.stickersPerRow = width / AndroidUtilities.dp(72.0f);
            }
            Integer row = (Integer) this.positionToRow.get(Integer.valueOf(position));
            if (row == null) {
                return (EmojiView.this.stickerSets.size() - 1) + EmojiView.this.stickersTabOffset;
            }
            TLRPC$TL_messages_stickerSet pack = this.rowStartPack.get(row);
            if (!(pack instanceof String)) {
                return EmojiView.this.stickersTabOffset + EmojiView.this.stickerSets.indexOf(pack);
            } else if ("recent".equals(pack)) {
                return EmojiView.this.recentTabBum;
            } else {
                return EmojiView.this.favTabBum;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 0:
                    view = new StickerEmojiCell(this.context) {
                        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(82.0f), 1073741824));
                        }
                    };
                    break;
                case 1:
                    view = new EmptyCell(this.context);
                    break;
                case 2:
                    view = new StickerSetNameCell(this.context);
                    ((StickerSetNameCell) view).setOnIconClickListener(new C25832());
                    break;
                case 3:
                    view = new StickerSetGroupInfoCell(this.context);
                    ((StickerSetGroupInfoCell) view).setAddOnClickListener(new C25843());
                    view.setLayoutParams(new LayoutParams(-1, -2));
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    TLRPC$Document sticker = (TLRPC$Document) this.cache.get(Integer.valueOf(position));
                    StickerEmojiCell cell = holder.itemView;
                    cell.setSticker(sticker, false);
                    boolean z = EmojiView.this.recentStickers.contains(sticker) || EmojiView.this.favouriteStickers.contains(sticker);
                    cell.setRecent(z);
                    return;
                case 1:
                    EmptyCell cell2 = holder.itemView;
                    if (position == this.totalItems) {
                        Integer row = (Integer) this.positionToRow.get(Integer.valueOf(position - 1));
                        if (row == null) {
                            cell2.setHeight(1);
                            return;
                        }
                        ArrayList<TLRPC$Document> documents;
                        Object pack = this.rowStartPack.get(row);
                        if (pack instanceof TLRPC$TL_messages_stickerSet) {
                            documents = ((TLRPC$TL_messages_stickerSet) pack).documents;
                        } else if (!(pack instanceof String)) {
                            documents = null;
                        } else if ("recent".equals(pack)) {
                            documents = EmojiView.this.recentStickers;
                        } else {
                            documents = EmojiView.this.favouriteStickers;
                        }
                        if (documents == null) {
                            cell2.setHeight(1);
                            return;
                        } else if (documents.isEmpty()) {
                            cell2.setHeight(AndroidUtilities.dp(8.0f));
                            return;
                        } else {
                            int height = EmojiView.this.pager.getHeight() - (((int) Math.ceil((double) (((float) documents.size()) / ((float) this.stickersPerRow)))) * AndroidUtilities.dp(82.0f));
                            if (height <= 0) {
                                height = 1;
                            }
                            cell2.setHeight(height);
                            return;
                        }
                    }
                    cell2.setHeight(AndroidUtilities.dp(82.0f));
                    return;
                case 2:
                    StickerSetNameCell cell3 = holder.itemView;
                    if (position == EmojiView.this.groupStickerPackPosition) {
                        int icon;
                        if (EmojiView.this.groupStickersHidden && EmojiView.this.groupStickerSet == null) {
                            icon = 0;
                        } else {
                            icon = EmojiView.this.groupStickerSet != null ? R.drawable.stickersclose : R.drawable.stickerset_close;
                        }
                        TLRPC$Chat chat = EmojiView.this.info != null ? MessagesController.getInstance().getChat(Integer.valueOf(EmojiView.this.info.id)) : null;
                        String str = "CurrentGroupStickers";
                        Object[] objArr = new Object[1];
                        objArr[0] = chat != null ? chat.title : "Group Stickers";
                        cell3.setText(LocaleController.formatString(str, R.string.CurrentGroupStickers, objArr), icon);
                        return;
                    }
                    TLRPC$TL_messages_stickerSet object = this.cache.get(Integer.valueOf(position));
                    if (object instanceof TLRPC$TL_messages_stickerSet) {
                        TLRPC$TL_messages_stickerSet set = object;
                        if (set.set != null) {
                            cell3.setText(set.set.title, 0);
                            return;
                        }
                        return;
                    } else if (object == EmojiView.this.recentStickers) {
                        cell3.setText(LocaleController.getString("RecentStickers", R.string.RecentStickers), 0);
                        return;
                    } else if (object == EmojiView.this.favouriteStickers) {
                        cell3.setText(LocaleController.getString("FavoriteStickers", R.string.FavoriteStickers), 0);
                        return;
                    } else {
                        return;
                    }
                case 3:
                    holder.itemView.setIsLast(position == this.totalItems + -1);
                    return;
                default:
                    return;
            }
        }

        public void notifyDataSetChanged() {
            int width = EmojiView.this.getMeasuredWidth();
            if (width == 0) {
                width = AndroidUtilities.displaySize.x;
            }
            this.stickersPerRow = width / AndroidUtilities.dp(72.0f);
            EmojiView.this.stickersLayoutManager.setSpanCount(this.stickersPerRow);
            this.rowStartPack.clear();
            this.packStartPosition.clear();
            this.positionToRow.clear();
            this.cache.clear();
            this.totalItems = 0;
            ArrayList<TLRPC$TL_messages_stickerSet> packs = EmojiView.this.stickerSets;
            int startRow = 0;
            int a = -2;
            while (a < packs.size()) {
                ArrayList<TLRPC$Document> documents;
                TLRPC$TL_messages_stickerSet pack = null;
                if (a == -2) {
                    documents = EmojiView.this.favouriteStickers;
                    this.packStartPosition.put("fav", Integer.valueOf(this.totalItems));
                } else if (a == -1) {
                    documents = EmojiView.this.recentStickers;
                    this.packStartPosition.put("recent", Integer.valueOf(this.totalItems));
                } else {
                    pack = (TLRPC$TL_messages_stickerSet) packs.get(a);
                    documents = pack.documents;
                    this.packStartPosition.put(pack, Integer.valueOf(this.totalItems));
                }
                if (a == EmojiView.this.groupStickerPackNum) {
                    EmojiView.this.groupStickerPackPosition = this.totalItems;
                    if (documents.isEmpty()) {
                        this.rowStartPack.put(Integer.valueOf(startRow), pack);
                        int startRow2 = startRow + 1;
                        this.positionToRow.put(Integer.valueOf(this.totalItems), Integer.valueOf(startRow));
                        this.rowStartPack.put(Integer.valueOf(startRow2), pack);
                        startRow = startRow2 + 1;
                        this.positionToRow.put(Integer.valueOf(this.totalItems + 1), Integer.valueOf(startRow2));
                        HashMap hashMap = this.cache;
                        int i = this.totalItems;
                        this.totalItems = i + 1;
                        hashMap.put(Integer.valueOf(i), pack);
                        hashMap = this.cache;
                        i = this.totalItems;
                        this.totalItems = i + 1;
                        hashMap.put(Integer.valueOf(i), "group");
                        a++;
                    }
                }
                if (!documents.isEmpty()) {
                    int b;
                    int count = (int) Math.ceil((double) (((float) documents.size()) / ((float) this.stickersPerRow)));
                    if (pack != null) {
                        this.cache.put(Integer.valueOf(this.totalItems), pack);
                    } else {
                        this.cache.put(Integer.valueOf(this.totalItems), documents);
                    }
                    this.positionToRow.put(Integer.valueOf(this.totalItems), Integer.valueOf(startRow));
                    for (b = 0; b < documents.size(); b++) {
                        this.cache.put(Integer.valueOf((b + 1) + this.totalItems), documents.get(b));
                        this.positionToRow.put(Integer.valueOf((b + 1) + this.totalItems), Integer.valueOf((startRow + 1) + (b / this.stickersPerRow)));
                    }
                    for (b = 0; b < count + 1; b++) {
                        if (pack != null) {
                            this.rowStartPack.put(Integer.valueOf(startRow + b), pack);
                        } else {
                            this.rowStartPack.put(Integer.valueOf(startRow + b), a == -1 ? "recent" : "fav");
                        }
                    }
                    this.totalItems += (this.stickersPerRow * count) + 1;
                    startRow += count + 1;
                }
                a++;
            }
            super.notifyDataSetChanged();
        }
    }

    private class TrendingGridAdapter extends SelectionAdapter {
        private HashMap<Integer, Object> cache = new HashMap();
        private Context context;
        private HashMap<Integer, TLRPC$StickerSetCovered> positionsToSets = new HashMap();
        private ArrayList<TLRPC$StickerSetCovered> sets = new ArrayList();
        private int stickersPerRow;
        private int totalItems;

        /* renamed from: org.telegram.ui.Components.EmojiView$TrendingGridAdapter$2 */
        class C25862 implements OnClickListener {
            C25862() {
            }

            public void onClick(View v) {
                FeaturedStickerSetInfoCell parent = (FeaturedStickerSetInfoCell) v.getParent();
                TLRPC$StickerSetCovered pack = parent.getStickerSet();
                if (!EmojiView.this.installingStickerSets.containsKey(Long.valueOf(pack.set.id)) && !EmojiView.this.removingStickerSets.containsKey(Long.valueOf(pack.set.id))) {
                    if (parent.isInstalled()) {
                        EmojiView.this.removingStickerSets.put(Long.valueOf(pack.set.id), pack);
                        EmojiView.this.listener.onStickerSetRemove(parent.getStickerSet());
                    } else {
                        EmojiView.this.installingStickerSets.put(Long.valueOf(pack.set.id), pack);
                        EmojiView.this.listener.onStickerSetAdd(parent.getStickerSet());
                    }
                    parent.setDrawProgress(true);
                }
            }
        }

        public TrendingGridAdapter(Context context) {
            this.context = context;
        }

        public int getItemCount() {
            return this.totalItems;
        }

        public Object getItem(int i) {
            return this.cache.get(Integer.valueOf(i));
        }

        public boolean isEnabled(ViewHolder holder) {
            return false;
        }

        public int getItemViewType(int position) {
            Object object = this.cache.get(Integer.valueOf(position));
            if (object == null) {
                return 1;
            }
            if (object instanceof TLRPC$Document) {
                return 0;
            }
            return 2;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 0:
                    view = new StickerEmojiCell(this.context) {
                        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(82.0f), 1073741824));
                        }
                    };
                    break;
                case 1:
                    view = new EmptyCell(this.context);
                    break;
                case 2:
                    view = new FeaturedStickerSetInfoCell(this.context, 17);
                    ((FeaturedStickerSetInfoCell) view).setAddOnClickListener(new C25862());
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            boolean z = false;
            switch (holder.getItemViewType()) {
                case 0:
                    ((StickerEmojiCell) holder.itemView).setSticker((TLRPC$Document) this.cache.get(Integer.valueOf(position)), false);
                    return;
                case 1:
                    ((EmptyCell) holder.itemView).setHeight(AndroidUtilities.dp(82.0f));
                    return;
                case 2:
                    boolean unread;
                    ArrayList<Long> unreadStickers = StickersQuery.getUnreadStickerSets();
                    TLRPC$StickerSetCovered stickerSetCovered = (TLRPC$StickerSetCovered) this.sets.get(((Integer) this.cache.get(Integer.valueOf(position))).intValue());
                    if (unreadStickers == null || !unreadStickers.contains(Long.valueOf(stickerSetCovered.set.id))) {
                        unread = false;
                    } else {
                        unread = true;
                    }
                    FeaturedStickerSetInfoCell cell = holder.itemView;
                    cell.setStickerSet(stickerSetCovered, unread);
                    if (unread) {
                        StickersQuery.markFaturedStickersByIdAsRead(stickerSetCovered.set.id);
                    }
                    boolean installing = EmojiView.this.installingStickerSets.containsKey(Long.valueOf(stickerSetCovered.set.id));
                    boolean removing = EmojiView.this.removingStickerSets.containsKey(Long.valueOf(stickerSetCovered.set.id));
                    if (installing || removing) {
                        if (installing && cell.isInstalled()) {
                            EmojiView.this.installingStickerSets.remove(Long.valueOf(stickerSetCovered.set.id));
                            installing = false;
                        } else if (removing && !cell.isInstalled()) {
                            EmojiView.this.removingStickerSets.remove(Long.valueOf(stickerSetCovered.set.id));
                            removing = false;
                        }
                    }
                    if (installing || removing) {
                        z = true;
                    }
                    cell.setDrawProgress(z);
                    return;
                default:
                    return;
            }
        }

        public void notifyDataSetChanged() {
            int width = EmojiView.this.getMeasuredWidth();
            if (width == 0) {
                if (AndroidUtilities.isTablet()) {
                    int smallSide = AndroidUtilities.displaySize.x;
                    int leftSide = (smallSide * 35) / 100;
                    if (leftSide < AndroidUtilities.dp(320.0f)) {
                        leftSide = AndroidUtilities.dp(320.0f);
                    }
                    width = smallSide - leftSide;
                } else {
                    width = AndroidUtilities.displaySize.x;
                }
                if (width == 0) {
                    width = 1080;
                }
            }
            this.stickersPerRow = width / AndroidUtilities.dp(72.0f);
            EmojiView.this.trendingLayoutManager.setSpanCount(Math.max(1, this.stickersPerRow));
            if (!EmojiView.this.trendingLoaded) {
                this.cache.clear();
                this.positionsToSets.clear();
                this.sets.clear();
                this.totalItems = 0;
                int num = 0;
                ArrayList<TLRPC$StickerSetCovered> packs = StickersQuery.getFeaturedStickerSets();
                for (int a = 0; a < packs.size(); a++) {
                    TLRPC$StickerSetCovered pack = (TLRPC$StickerSetCovered) packs.get(a);
                    if (!(StickersQuery.isStickerPackInstalled(pack.set.id) || (pack.covers.isEmpty() && pack.cover == null))) {
                        int count;
                        int b;
                        this.sets.add(pack);
                        this.positionsToSets.put(Integer.valueOf(this.totalItems), pack);
                        HashMap hashMap = this.cache;
                        int i = this.totalItems;
                        this.totalItems = i + 1;
                        int num2 = num + 1;
                        hashMap.put(Integer.valueOf(i), Integer.valueOf(num));
                        int startRow = this.totalItems / this.stickersPerRow;
                        if (pack.covers.isEmpty()) {
                            count = 1;
                            this.cache.put(Integer.valueOf(this.totalItems), pack.cover);
                        } else {
                            count = (int) Math.ceil((double) (((float) pack.covers.size()) / ((float) this.stickersPerRow)));
                            for (b = 0; b < pack.covers.size(); b++) {
                                this.cache.put(Integer.valueOf(this.totalItems + b), pack.covers.get(b));
                            }
                        }
                        for (b = 0; b < this.stickersPerRow * count; b++) {
                            this.positionsToSets.put(Integer.valueOf(this.totalItems + b), pack);
                        }
                        this.totalItems += this.stickersPerRow * count;
                        num = num2;
                    }
                }
                if (this.totalItems != 0) {
                    EmojiView.this.trendingLoaded = true;
                    EmojiView.this.featuredStickersHash = StickersQuery.getFeaturesStickersHashWithoutUnread();
                }
                super.notifyDataSetChanged();
            }
        }
    }

    static {
        Field f = null;
        try {
            f = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
        }
        superListenerField = f;
    }

    private static String addColorToCode(String code, String color) {
        String end = null;
        int lenght = code.length();
        if (lenght > 2 && code.charAt(code.length() - 2) == '‍') {
            end = code.substring(code.length() - 2);
            code = code.substring(0, code.length() - 2);
        } else if (lenght > 3 && code.charAt(code.length() - 3) == '‍') {
            end = code.substring(code.length() - 3);
            code = code.substring(0, code.length() - 3);
        }
        code = code + color;
        if (end != null) {
            return code + end;
        }
        return code;
    }

    public void addEmojiToRecent(String code) {
        Emoji.addRecentEmoji(code);
        if (!(getVisibility() == 0 && this.pager.getCurrentItem() == 0)) {
            Emoji.sortEmoji();
        }
        Emoji.saveRecentEmoji();
        ((EmojiGridAdapter) this.adapters.get(0)).notifyDataSetChanged();
    }

    public EmojiView(boolean needStickers, boolean needGif, Context context, TLRPC$ChatFull chatFull) {
        FrameLayout frameLayout;
        super(context);
        Drawable stickersDrawable = context.getResources().getDrawable(R.drawable.ic_smiles2_stickers);
        Theme.setDrawableColorByKey(stickersDrawable, Theme.key_chat_emojiPanelIcon);
        Drawable[] drawableArr = new Drawable[7];
        drawableArr[0] = Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_recent, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected));
        drawableArr[1] = Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_smile, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected));
        drawableArr[2] = Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_nature, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected));
        drawableArr[3] = Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_food, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected));
        drawableArr[4] = Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_car, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected));
        drawableArr[5] = Theme.createEmojiIconSelectorDrawable(context, R.drawable.ic_smiles2_objects, Theme.getColor(Theme.key_chat_emojiPanelIcon), Theme.getColor(Theme.key_chat_emojiPanelIconSelected));
        drawableArr[6] = stickersDrawable;
        this.icons = drawableArr;
        this.showGifs = needGif;
        this.info = chatFull;
        this.dotPaint = new Paint(1);
        this.dotPaint.setColor(Theme.getColor(Theme.key_chat_emojiPanelNewTrending));
        if (VERSION.SDK_INT >= 21) {
            this.outlineProvider = new C25733();
        }
        for (int i = 0; i < EmojiData.dataColored.length + 1; i++) {
            GridView gridView = new GridView(context);
            if (AndroidUtilities.isTablet()) {
                gridView.setColumnWidth(AndroidUtilities.dp(60.0f));
            } else {
                gridView.setColumnWidth(AndroidUtilities.dp(45.0f));
            }
            gridView.setNumColumns(-1);
            EmojiGridAdapter emojiGridAdapter = new EmojiGridAdapter(i - 1);
            gridView.setAdapter(emojiGridAdapter);
            this.adapters.add(emojiGridAdapter);
            this.emojiGrids.add(gridView);
            frameLayout = new FrameLayout(context);
            frameLayout.addView(gridView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 48.0f, 0.0f, 0.0f));
            this.views.add(frameLayout);
        }
        if (needStickers) {
            this.stickersWrap = new FrameLayout(context);
            StickersQuery.checkStickers(0);
            StickersQuery.checkFeaturedStickers();
            this.stickersGridView = new RecyclerListView(context) {
                public boolean onInterceptTouchEvent(MotionEvent event) {
                    return super.onInterceptTouchEvent(event) || StickerPreviewViewer.getInstance().onInterceptTouchEvent(event, EmojiView.this.stickersGridView, EmojiView.this.getMeasuredHeight(), EmojiView.this.stickerPreviewViewerDelegate);
                }

                public void setVisibility(int visibility) {
                    if ((EmojiView.this.gifsGridView == null || EmojiView.this.gifsGridView.getVisibility() != 0) && (EmojiView.this.trendingGridView == null || EmojiView.this.trendingGridView.getVisibility() != 0)) {
                        super.setVisibility(visibility);
                    } else {
                        super.setVisibility(8);
                    }
                }
            };
            RecyclerListView recyclerListView = this.stickersGridView;
            LayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
            this.stickersLayoutManager = gridLayoutManager;
            recyclerListView.setLayoutManager(gridLayoutManager);
            this.stickersLayoutManager.setSpanSizeLookup(new C25755());
            this.stickersGridView.setPadding(0, AndroidUtilities.dp(52.0f), 0, 0);
            this.stickersGridView.setClipToPadding(false);
            this.views.add(this.stickersWrap);
            recyclerListView = this.stickersGridView;
            Adapter stickersGridAdapter = new StickersGridAdapter(context);
            this.stickersGridAdapter = stickersGridAdapter;
            recyclerListView.setAdapter(stickersGridAdapter);
            this.stickersGridView.setOnTouchListener(new C25766());
            this.stickersOnItemClickListener = new C25777();
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
            this.trendingLayoutManager.setSpanSizeLookup(new C25799());
            this.trendingGridView.setOnScrollListener(new OnScrollListener() {
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    EmojiView.this.checkStickersTabY(recyclerView, dy);
                }
            });
            this.trendingGridView.setClipToPadding(false);
            this.trendingGridView.setPadding(0, AndroidUtilities.dp(48.0f), 0, 0);
            recyclerListView = this.trendingGridView;
            stickersGridAdapter = new TrendingGridAdapter(context);
            this.trendingGridAdapter = stickersGridAdapter;
            recyclerListView.setAdapter(stickersGridAdapter);
            this.trendingGridView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    TLRPC$StickerSetCovered pack = (TLRPC$StickerSetCovered) EmojiView.this.trendingGridAdapter.positionsToSets.get(Integer.valueOf(position));
                    if (pack != null) {
                        EmojiView.this.listener.onShowStickerSet(pack.set, null);
                    }
                }
            });
            this.trendingGridAdapter.notifyDataSetChanged();
            this.trendingGridView.setGlowColor(Theme.getColor(Theme.key_chat_emojiPanelBackground));
            this.trendingGridView.setVisibility(8);
            this.stickersWrap.addView(this.trendingGridView);
            if (needGif) {
                this.gifsGridView = new RecyclerListView(context);
                this.gifsGridView.setClipToPadding(false);
                this.gifsGridView.setPadding(0, AndroidUtilities.dp(48.0f), 0, 0);
                recyclerListView = this.gifsGridView;
                gridLayoutManager = new ExtendedGridLayoutManager(context, 100) {
                    private Size size = new Size();

                    protected Size getSizeForItem(int i) {
                        float f;
                        float f2 = 100.0f;
                        TLRPC$Document document = (TLRPC$Document) EmojiView.this.recentGifs.get(i);
                        Size size = this.size;
                        if (document.thumb == null || document.thumb.f78w == 0) {
                            f = 100.0f;
                        } else {
                            f = (float) document.thumb.f78w;
                        }
                        size.width = f;
                        Size size2 = this.size;
                        if (!(document.thumb == null || document.thumb.f77h == 0)) {
                            f2 = (float) document.thumb.f77h;
                        }
                        size2.height = f2;
                        for (int b = 0; b < document.attributes.size(); b++) {
                            DocumentAttribute attribute = (DocumentAttribute) document.attributes.get(b);
                            if ((attribute instanceof TLRPC$TL_documentAttributeImageSize) || (attribute instanceof TLRPC$TL_documentAttributeVideo)) {
                                this.size.width = (float) attribute.f44w;
                                this.size.height = (float) attribute.f43h;
                                break;
                            }
                        }
                        return this.size;
                    }
                };
                this.flowLayoutManager = gridLayoutManager;
                recyclerListView.setLayoutManager(gridLayoutManager);
                this.flowLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
                    public int getSpanSize(int position) {
                        return EmojiView.this.flowLayoutManager.getSpanSizeForItem(position);
                    }
                });
                this.gifsGridView.addItemDecoration(new ItemDecoration() {
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                        int i = 0;
                        outRect.left = 0;
                        outRect.top = 0;
                        outRect.bottom = 0;
                        int position = parent.getChildAdapterPosition(view);
                        if (!EmojiView.this.flowLayoutManager.isFirstRow(position)) {
                            outRect.top = AndroidUtilities.dp(2.0f);
                        }
                        if (!EmojiView.this.flowLayoutManager.isLastInRow(position)) {
                            i = AndroidUtilities.dp(2.0f);
                        }
                        outRect.right = i;
                    }
                });
                this.gifsGridView.setOverScrollMode(2);
                recyclerListView = this.gifsGridView;
                stickersGridAdapter = new GifsAdapter(context);
                this.gifsAdapter = stickersGridAdapter;
                recyclerListView.setAdapter(stickersGridAdapter);
                this.gifsGridView.setOnScrollListener(new OnScrollListener() {
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        EmojiView.this.checkStickersTabY(recyclerView, dy);
                    }
                });
                this.gifsGridView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(View view, int position) {
                        if (position >= 0 && position < EmojiView.this.recentGifs.size() && EmojiView.this.listener != null) {
                            EmojiView.this.listener.onGifSelected((TLRPC$Document) EmojiView.this.recentGifs.get(position));
                        }
                    }
                });
                this.gifsGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
                    public boolean onItemClick(View view, int position) {
                        if (position < 0 || position >= EmojiView.this.recentGifs.size()) {
                            return false;
                        }
                        final TLRPC$Document searchImage = (TLRPC$Document) EmojiView.this.recentGifs.get(position);
                        Builder builder = new Builder(view.getContext());
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("DeleteGif", R.string.DeleteGif));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK).toUpperCase(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StickersQuery.removeRecentGif(searchImage);
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
            this.stickersWrap.addView(this.stickersEmptyView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 48.0f, 0.0f, 0.0f));
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

                public boolean onInterceptTouchEvent(MotionEvent ev) {
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    if (ev.getAction() == 0) {
                        this.draggingHorizontally = false;
                        this.draggingVertically = false;
                        this.downX = ev.getRawX();
                        this.downY = ev.getRawY();
                    } else if (!(this.draggingVertically || this.draggingHorizontally || EmojiView.this.dragListener == null || Math.abs(ev.getRawY() - this.downY) < ((float) this.touchslop))) {
                        this.draggingVertically = true;
                        this.downY = ev.getRawY();
                        EmojiView.this.dragListener.onDragStart();
                        if (!this.startedScroll) {
                            return true;
                        }
                        EmojiView.this.pager.endFakeDrag();
                        this.startedScroll = false;
                        return true;
                    }
                    return super.onInterceptTouchEvent(ev);
                }

                public boolean onTouchEvent(MotionEvent ev) {
                    boolean z = false;
                    if (this.first) {
                        this.first = false;
                        this.lastX = ev.getX();
                    }
                    if (ev.getAction() == 0) {
                        this.draggingHorizontally = false;
                        this.draggingVertically = false;
                        this.downX = ev.getRawX();
                        this.downY = ev.getRawY();
                    } else if (!(this.draggingVertically || this.draggingHorizontally || EmojiView.this.dragListener == null)) {
                        if (Math.abs(ev.getRawX() - this.downX) >= ((float) this.touchslop)) {
                            this.draggingHorizontally = true;
                        } else if (Math.abs(ev.getRawY() - this.downY) >= ((float) this.touchslop)) {
                            this.draggingVertically = true;
                            this.downY = ev.getRawY();
                            EmojiView.this.dragListener.onDragStart();
                            if (this.startedScroll) {
                                EmojiView.this.pager.endFakeDrag();
                                this.startedScroll = false;
                            }
                        }
                    }
                    if (this.draggingVertically) {
                        if (this.vTracker == null) {
                            this.vTracker = VelocityTracker.obtain();
                        }
                        this.vTracker.addMovement(ev);
                        if (ev.getAction() == 1 || ev.getAction() == 3) {
                            this.vTracker.computeCurrentVelocity(1000);
                            float velocity = this.vTracker.getYVelocity();
                            this.vTracker.recycle();
                            this.vTracker = null;
                            if (ev.getAction() == 1) {
                                EmojiView.this.dragListener.onDragEnd(velocity);
                            } else {
                                EmojiView.this.dragListener.onDragCancel();
                            }
                            this.first = true;
                            this.draggingHorizontally = false;
                            this.draggingVertically = false;
                            return true;
                        }
                        EmojiView.this.dragListener.onDrag(Math.round(ev.getRawY() - this.downY));
                        return true;
                    }
                    float newTranslationX = EmojiView.this.stickersTab.getTranslationX();
                    if (EmojiView.this.stickersTab.getScrollX() == 0 && newTranslationX == 0.0f) {
                        if (this.startedScroll || this.lastX - ev.getX() >= 0.0f) {
                            if (this.startedScroll && this.lastX - ev.getX() > 0.0f && EmojiView.this.pager.isFakeDragging()) {
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
                            EmojiView.this.pager.fakeDragBy((float) ((int) (((ev.getX() - this.lastX) + newTranslationX) - this.lastTranslateX)));
                            this.lastTranslateX = newTranslationX;
                        } catch (Exception e) {
                            try {
                                EmojiView.this.pager.endFakeDrag();
                            } catch (Exception e2) {
                            }
                            this.startedScroll = false;
                            FileLog.e(e);
                        }
                    }
                    this.lastX = ev.getX();
                    if (ev.getAction() == 3 || ev.getAction() == 1) {
                        this.first = true;
                        this.draggingHorizontally = false;
                        this.draggingVertically = false;
                        if (this.startedScroll) {
                            EmojiView.this.pager.endFakeDrag();
                            this.startedScroll = false;
                        }
                    }
                    if (this.startedScroll || super.onTouchEvent(ev)) {
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
                public void onPageSelected(int page) {
                    int i = 8;
                    if (EmojiView.this.gifsGridView != null) {
                        if (page == EmojiView.this.gifTabNum + 1) {
                            if (EmojiView.this.gifsGridView.getVisibility() != 0) {
                                EmojiView.this.listener.onGifTab(true);
                                EmojiView.this.showGifTab();
                            }
                        } else if (page == EmojiView.this.trendingTabNum + 1) {
                            if (EmojiView.this.trendingGridView.getVisibility() != 0) {
                                EmojiView.this.showTrendingTab();
                            }
                        } else if (EmojiView.this.gifsGridView.getVisibility() == 0) {
                            EmojiView.this.listener.onGifTab(false);
                            EmojiView.this.gifsGridView.setVisibility(8);
                            EmojiView.this.stickersGridView.setVisibility(0);
                            int vis = EmojiView.this.stickersGridView.getVisibility();
                            r4 = EmojiView.this.stickersEmptyView;
                            if (EmojiView.this.stickersGridAdapter.getItemCount() == 0) {
                                i = 0;
                            }
                            r4.setVisibility(i);
                            EmojiView.this.checkScroll();
                            EmojiView.this.saveNewPage();
                        } else if (EmojiView.this.trendingGridView.getVisibility() == 0) {
                            EmojiView.this.trendingGridView.setVisibility(8);
                            EmojiView.this.stickersGridView.setVisibility(0);
                            r4 = EmojiView.this.stickersEmptyView;
                            if (EmojiView.this.stickersGridAdapter.getItemCount() == 0) {
                                i = 0;
                            }
                            r4.setVisibility(i);
                            EmojiView.this.saveNewPage();
                        }
                    }
                    if (page == 0) {
                        EmojiView.this.pager.setCurrentItem(0);
                    } else if (page != EmojiView.this.gifTabNum + 1 && page != EmojiView.this.trendingTabNum + 1) {
                        if (page == EmojiView.this.recentTabBum + 1) {
                            EmojiView.this.stickersLayoutManager.scrollToPositionWithOffset(EmojiView.this.stickersGridAdapter.getPositionForPack("recent"), 0);
                            EmojiView.this.checkStickersTabY(null, 0);
                            EmojiView.this.stickersTab.onPageScrolled(EmojiView.this.recentTabBum + 1, (EmojiView.this.recentTabBum > 0 ? EmojiView.this.recentTabBum : EmojiView.this.stickersTabOffset) + 1);
                        } else if (page == EmojiView.this.favTabBum + 1) {
                            EmojiView.this.stickersLayoutManager.scrollToPositionWithOffset(EmojiView.this.stickersGridAdapter.getPositionForPack("fav"), 0);
                            EmojiView.this.checkStickersTabY(null, 0);
                            EmojiView.this.stickersTab.onPageScrolled(EmojiView.this.favTabBum + 1, (EmojiView.this.favTabBum > 0 ? EmojiView.this.favTabBum : EmojiView.this.stickersTabOffset) + 1);
                        } else {
                            int index = (page - 1) - EmojiView.this.stickersTabOffset;
                            if (index < EmojiView.this.stickerSets.size()) {
                                if (index >= EmojiView.this.stickerSets.size()) {
                                    index = EmojiView.this.stickerSets.size() - 1;
                                }
                                EmojiView.this.stickersLayoutManager.scrollToPositionWithOffset(EmojiView.this.stickersGridAdapter.getPositionForPack(EmojiView.this.stickerSets.get(index)), 0);
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
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    EmojiView.this.checkScroll();
                    EmojiView.this.checkStickersTabY(recyclerView, dy);
                }
            });
        }
        this.pager = new ViewPager(context) {
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.onInterceptTouchEvent(ev);
            }
        };
        EmojiView emojiView = this;
        this.pager.setAdapter(new EmojiPagesAdapter());
        this.emojiTab = new LinearLayout(context) {
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.onInterceptTouchEvent(ev);
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
        this.pagerSlidingTabStrip.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                EmojiView.this.onPageScrolled(position, (EmojiView.this.getMeasuredWidth() - EmojiView.this.getPaddingLeft()) - EmojiView.this.getPaddingRight(), positionOffsetPixels);
            }

            public void onPageSelected(int position) {
                EmojiView.this.saveNewPage();
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        frameLayout = new FrameLayout(context);
        this.emojiTab.addView(frameLayout, LayoutHelper.createLinear(52, 48));
        this.backspaceButton = new ImageView(context) {
            public boolean onTouchEvent(MotionEvent event) {
                if (event.getAction() == 0) {
                    EmojiView.this.backspacePressed = true;
                    EmojiView.this.backspaceOnce = false;
                    EmojiView.this.postBackspaceRunnable(350);
                } else if (event.getAction() == 3 || event.getAction() == 1) {
                    EmojiView.this.backspacePressed = false;
                    if (!(EmojiView.this.backspaceOnce || EmojiView.this.listener == null || !EmojiView.this.listener.onBackspace())) {
                        EmojiView.this.backspaceButton.performHapticFeedback(3);
                    }
                }
                super.onTouchEvent(event);
                return true;
            }
        };
        this.backspaceButton.setImageResource(R.drawable.ic_smiles_backspace);
        this.backspaceButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_emojiPanelBackspace), Mode.MULTIPLY));
        this.backspaceButton.setScaleType(ScaleType.CENTER);
        frameLayout.addView(this.backspaceButton, LayoutHelper.createFrame(52, 48.0f));
        View view = new View(context);
        view.setBackgroundColor(Theme.getColor(Theme.key_chat_emojiPanelShadowLine));
        frameLayout.addView(view, LayoutHelper.createFrame(52, 1, 83));
        TextView textView = new TextView(context);
        textView.setText(LocaleController.getString("NoRecent", R.string.NoRecent));
        textView.setTextSize(1, 18.0f);
        textView.setTextColor(Theme.getColor(Theme.key_chat_emojiPanelEmptyText));
        textView.setGravity(17);
        textView.setClickable(false);
        textView.setFocusable(false);
        ((FrameLayout) this.views.get(0)).addView(textView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 48.0f, 0.0f, 0.0f));
        ((GridView) this.emojiGrids.get(0)).setEmptyView(textView);
        addView(this.pager, 0, LayoutHelper.createFrame(-1, -1, 51));
        this.mediaBanTooltip = new CorrectlyMeasuringTextView(context);
        this.mediaBanTooltip.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(3.0f), Theme.getColor(Theme.key_chat_gifSaveHintBackground)));
        this.mediaBanTooltip.setTextColor(Theme.getColor(Theme.key_chat_gifSaveHintText));
        this.mediaBanTooltip.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(8.0f), AndroidUtilities.dp(7.0f));
        this.mediaBanTooltip.setGravity(16);
        this.mediaBanTooltip.setTextSize(1, 14.0f);
        this.mediaBanTooltip.setVisibility(4);
        addView(this.mediaBanTooltip, LayoutHelper.createFrame(-2, -2.0f, 53, 30.0f, 53.0f, 5.0f, 0.0f));
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
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 82 || event.getRepeatCount() != 0 || event.getAction() != 1 || EmojiView.this.pickerViewPopup == null || !EmojiView.this.pickerViewPopup.isShowing()) {
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

    private void checkStickersTabY(View list, int dy) {
        if (list == null) {
            ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
            this.minusDy = 0;
            scrollSlidingTabStrip.setTranslationY((float) null);
        } else if (list.getVisibility() == 0) {
            this.minusDy -= dy;
            if (this.minusDy > 0) {
                this.minusDy = 0;
            } else if (this.minusDy < (-AndroidUtilities.dp(288.0f))) {
                this.minusDy = -AndroidUtilities.dp(288.0f);
            }
            this.stickersTab.setTranslationY((float) Math.max(-AndroidUtilities.dp(47.0f), this.minusDy));
        }
    }

    private void checkScroll() {
        int firstVisibleItem = this.stickersLayoutManager.findFirstVisibleItemPosition();
        if (firstVisibleItem != -1 && this.stickersGridView != null) {
            int firstTab;
            if (this.favTabBum > 0) {
                firstTab = this.favTabBum;
            } else if (this.recentTabBum > 0) {
                firstTab = this.recentTabBum;
            } else {
                firstTab = this.stickersTabOffset;
            }
            if (this.stickersGridView.getVisibility() != 0) {
                if (!(this.gifsGridView == null || this.gifsGridView.getVisibility() == 0)) {
                    this.gifsGridView.setVisibility(0);
                }
                if (this.stickersEmptyView != null && this.stickersEmptyView.getVisibility() == 0) {
                    this.stickersEmptyView.setVisibility(8);
                }
                this.stickersTab.onPageScrolled(this.gifTabNum + 1, firstTab + 1);
                return;
            }
            this.stickersTab.onPageScrolled(this.stickersGridAdapter.getTabForPosition(firstVisibleItem) + 1, firstTab + 1);
        }
    }

    private void saveNewPage() {
        int newPage;
        if (this.pager.getCurrentItem() != 6) {
            newPage = 0;
        } else if (this.gifsGridView == null || this.gifsGridView.getVisibility() != 0) {
            newPage = 1;
        } else {
            newPage = 2;
        }
        if (this.currentPage != newPage) {
            this.currentPage = newPage;
            getContext().getSharedPreferences("emoji", 0).edit().putInt("selected_page", newPage).commit();
        }
    }

    public void clearRecentEmoji() {
        Emoji.clearRecentEmoji();
        ((EmojiGridAdapter) this.adapters.get(0)).notifyDataSetChanged();
    }

    private void showTrendingTab() {
        this.trendingGridView.setVisibility(0);
        this.stickersGridView.setVisibility(8);
        this.stickersEmptyView.setVisibility(8);
        this.gifsGridView.setVisibility(8);
        this.stickersTab.onPageScrolled(this.trendingTabNum + 1, (this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset) + 1);
        saveNewPage();
    }

    private void showGifTab() {
        this.gifsGridView.setVisibility(0);
        this.stickersGridView.setVisibility(8);
        this.stickersEmptyView.setVisibility(8);
        this.trendingGridView.setVisibility(8);
        this.stickersTab.onPageScrolled(this.gifTabNum + 1, (this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset) + 1);
        saveNewPage();
    }

    private void onPageScrolled(int position, int width, int positionOffsetPixels) {
        boolean z = true;
        int i = 0;
        if (this.stickersTab != null) {
            if (width == 0) {
                width = AndroidUtilities.displaySize.x;
            }
            int margin = 0;
            if (position == 5) {
                margin = -positionOffsetPixels;
                if (this.listener != null) {
                    Listener listener = this.listener;
                    if (positionOffsetPixels == 0) {
                        z = false;
                    }
                    listener.onStickersTab(z);
                }
            } else if (position == 6) {
                margin = -width;
                if (this.listener != null) {
                    this.listener.onStickersTab(true);
                }
            } else if (this.listener != null) {
                this.listener.onStickersTab(false);
            }
            if (this.emojiTab.getTranslationX() != ((float) margin)) {
                this.emojiTab.setTranslationX((float) margin);
                this.stickersTab.setTranslationX((float) (width + margin));
                ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
                if (margin >= 0) {
                    i = 4;
                }
                scrollSlidingTabStrip.setVisibility(i);
            }
        }
    }

    private void postBackspaceRunnable(final int time) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (EmojiView.this.backspacePressed) {
                    if (EmojiView.this.listener != null && EmojiView.this.listener.onBackspace()) {
                        EmojiView.this.backspaceButton.performHapticFeedback(3);
                    }
                    EmojiView.this.backspaceOnce = true;
                    EmojiView.this.postBackspaceRunnable(Math.max(50, time - 100));
                }
            }
        }, (long) time);
    }

    public void switchToGifRecent() {
        if (this.gifTabNum < 0 || this.recentGifs.isEmpty()) {
            this.switchToGifTab = true;
        } else {
            this.stickersTab.selectTab(this.gifTabNum + 1);
        }
        this.pager.setCurrentItem(6);
    }

    private void updateStickerTabs() {
        if (this.stickersTab != null) {
            int a;
            TLRPC$TL_messages_stickerSet pack;
            TLRPC$Chat chat;
            this.recentTabBum = -2;
            this.favTabBum = -2;
            this.gifTabNum = -2;
            this.trendingTabNum = -2;
            this.stickersTabOffset = 0;
            int lastPosition = this.stickersTab.getCurrentPosition();
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
            ArrayList<Long> unread = StickersQuery.getUnreadStickerSets();
            if (!(this.trendingGridAdapter == null || this.trendingGridAdapter.getItemCount() == 0 || unread.isEmpty())) {
                drawable = getContext().getResources().getDrawable(R.drawable.ic_smiles_trend);
                Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
                TextView stickersCounter = this.stickersTab.addIconTabWithCounter(drawable);
                this.trendingTabNum = this.stickersTabOffset;
                this.stickersTabOffset++;
                stickersCounter.setText(String.format("%d", new Object[]{Integer.valueOf(unread.size())}));
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
            ArrayList<TLRPC$TL_messages_stickerSet> packs = StickersQuery.getStickerSets(0);
            for (a = 0; a < packs.size(); a++) {
                pack = (TLRPC$TL_messages_stickerSet) packs.get(a);
                if (!(pack.set.archived || pack.documents == null || pack.documents.isEmpty())) {
                    this.stickerSets.add(pack);
                }
            }
            if (this.info != null) {
                long hiddenStickerSetId = getContext().getSharedPreferences("emoji", 0).getLong("group_hide_stickers_" + this.info.id, -1);
                chat = MessagesController.getInstance().getChat(Integer.valueOf(this.info.id));
                if (chat == null || this.info.stickerset == null || !ChatObject.hasAdminRights(chat)) {
                    this.groupStickersHidden = hiddenStickerSetId != -1;
                } else if (this.info.stickerset != null) {
                    this.groupStickersHidden = hiddenStickerSetId == this.info.stickerset.id;
                }
                if (this.info.stickerset != null) {
                    pack = StickersQuery.getGroupStickerSetById(this.info.stickerset);
                    if (!(pack == null || pack.documents == null || pack.documents.isEmpty() || pack.set == null)) {
                        TLRPC$TL_messages_stickerSet set = new TLRPC$TL_messages_stickerSet();
                        set.documents = pack.documents;
                        set.packs = pack.packs;
                        set.set = pack.set;
                        if (this.groupStickersHidden) {
                            this.groupStickerPackNum = this.stickerSets.size();
                            this.stickerSets.add(set);
                        } else {
                            this.groupStickerPackNum = 0;
                            this.stickerSets.add(0, set);
                        }
                        if (!this.info.can_set_stickers) {
                            set = null;
                        }
                        this.groupStickerSet = set;
                    }
                } else if (this.info.can_set_stickers) {
                    pack = new TLRPC$TL_messages_stickerSet();
                    if (this.groupStickersHidden) {
                        this.groupStickerPackNum = this.stickerSets.size();
                        this.stickerSets.add(pack);
                    } else {
                        this.groupStickerPackNum = 0;
                        this.stickerSets.add(0, pack);
                    }
                }
            }
            a = 0;
            while (a < this.stickerSets.size()) {
                if (a == this.groupStickerPackNum) {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(this.info.id));
                    if (chat == null) {
                        this.stickerSets.remove(0);
                        a--;
                    } else {
                        this.stickersTab.addStickerTab(chat);
                    }
                } else {
                    this.stickersTab.addStickerTab((TLRPC$Document) ((TLRPC$TL_messages_stickerSet) this.stickerSets.get(a)).documents.get(0));
                }
                a++;
            }
            if (!(this.trendingGridAdapter == null || this.trendingGridAdapter.getItemCount() == 0 || !unread.isEmpty())) {
                drawable = getContext().getResources().getDrawable(R.drawable.ic_smiles_trend);
                Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
                this.trendingTabNum = this.stickersTabOffset + this.stickerSets.size();
                this.stickersTab.addIconTab(drawable);
            }
            drawable = getContext().getResources().getDrawable(R.drawable.ic_smiles_settings);
            Theme.setDrawableColorByKey(drawable, Theme.key_chat_emojiPanelIcon);
            this.stickersTab.addIconTab(drawable);
            this.stickersTab.updateTabStyles();
            if (lastPosition != 0) {
                this.stickersTab.onPageScrolled(lastPosition, lastPosition);
            }
            if (this.switchToGifTab && this.gifTabNum >= 0 && this.gifsGridView.getVisibility() != 0) {
                showGifTab();
                this.switchToGifTab = false;
            }
            checkPanels();
        }
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
                    int position = this.stickersLayoutManager.findFirstVisibleItemPosition();
                    if (position != -1) {
                        int firstTab;
                        if (this.favTabBum > 0) {
                            firstTab = this.favTabBum;
                        } else if (this.recentTabBum > 0) {
                            firstTab = this.recentTabBum;
                        } else {
                            firstTab = this.stickersTabOffset;
                        }
                        this.stickersTab.onPageScrolled(this.stickersGridAdapter.getTabForPosition(position) + 1, firstTab + 1);
                    }
                } else {
                    this.stickersTab.onPageScrolled(this.trendingTabNum + 1, (this.recentTabBum > 0 ? this.recentTabBum : this.stickersTabOffset) + 1);
                }
            }
        }
    }

    public void addRecentSticker(TLRPC$Document document) {
        if (document != null) {
            StickersQuery.addRecentSticker(0, document, (int) (System.currentTimeMillis() / 1000), false);
            boolean wasEmpty = this.recentStickers.isEmpty();
            this.recentStickers = StickersQuery.getRecentStickers(0);
            if (this.stickersGridAdapter != null) {
                this.stickersGridAdapter.notifyDataSetChanged();
            }
            if (wasEmpty) {
                updateStickerTabs();
            }
        }
    }

    public void addRecentGif(TLRPC$Document document) {
        if (document != null) {
            boolean wasEmpty = this.recentGifs.isEmpty();
            this.recentGifs = StickersQuery.getRecentGifs();
            if (this.gifsAdapter != null) {
                this.gifsAdapter.notifyDataSetChanged();
            }
            if (wasEmpty) {
                updateStickerTabs();
            }
        }
    }

    public void requestLayout() {
        if (!this.isLayout) {
            super.requestLayout();
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
                setElevation(0.0f);
            }
            setBackgroundColor(Theme.getColor(Theme.key_chat_emojiPanelBackground));
            this.emojiTab.setBackgroundColor(Theme.getColor(Theme.key_chat_emojiPanelBackground));
            this.currentBackgroundType = 0;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.emojiTab.getLayoutParams();
        FrameLayout.LayoutParams layoutParams1 = null;
        layoutParams.width = MeasureSpec.getSize(widthMeasureSpec);
        if (this.stickersTab != null) {
            layoutParams1 = (FrameLayout.LayoutParams) this.stickersTab.getLayoutParams();
            if (layoutParams1 != null) {
                layoutParams1.width = layoutParams.width;
            }
        }
        if (layoutParams.width != this.oldWidth) {
            if (!(this.stickersTab == null || layoutParams1 == null)) {
                onPageScrolled(this.pager.getCurrentItem(), (layoutParams.width - getPaddingLeft()) - getPaddingRight(), 0);
                this.stickersTab.setLayoutParams(layoutParams1);
            }
            this.emojiTab.setLayoutParams(layoutParams);
            this.oldWidth = layoutParams.width;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824), MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), 1073741824));
        this.isLayout = false;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (this.lastNotifyWidth != right - left) {
            this.lastNotifyWidth = right - left;
            reloadStickersAdapter();
        }
        super.onLayout(changed, left, top, right, bottom);
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

    public void setListener(Listener value) {
        this.listener = value;
    }

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    public void setChatInfo(TLRPC$ChatFull chatInfo) {
        this.info = chatInfo;
        updateStickerTabs();
    }

    public void invalidateViews() {
        for (int a = 0; a < this.emojiGrids.size(); a++) {
            ((GridView) this.emojiGrids.get(a)).invalidateViews();
        }
    }

    public void onOpen(boolean forceEmoji) {
        boolean z = true;
        if (this.stickersTab != null) {
            if (!(this.currentPage == 0 || this.currentChatId == 0)) {
                this.currentPage = 0;
            }
            if (this.currentPage == 0 || forceEmoji) {
                if (this.pager.getCurrentItem() == 6) {
                    ViewPager viewPager = this.pager;
                    if (forceEmoji) {
                        z = false;
                    }
                    viewPager.setCurrentItem(0, z);
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

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != 8) {
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

    public int getCurrentPage() {
        return this.currentPage;
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

    private void checkDocuments(boolean isGif) {
        int previousCount;
        if (isGif) {
            previousCount = this.recentGifs.size();
            this.recentGifs = StickersQuery.getRecentGifs();
            if (this.gifsAdapter != null) {
                this.gifsAdapter.notifyDataSetChanged();
            }
            if (previousCount != this.recentGifs.size()) {
                updateStickerTabs();
            }
            if (this.stickersGridAdapter != null) {
                this.stickersGridAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        previousCount = this.recentStickers.size();
        int previousCount2 = this.favouriteStickers.size();
        this.recentStickers = StickersQuery.getRecentStickers(0);
        this.favouriteStickers = StickersQuery.getRecentStickers(2);
        for (int a = 0; a < this.favouriteStickers.size(); a++) {
            TLRPC$Document favSticker = (TLRPC$Document) this.favouriteStickers.get(a);
            for (int b = 0; b < this.recentStickers.size(); b++) {
                TLRPC$Document recSticker = (TLRPC$Document) this.recentStickers.get(b);
                if (recSticker.dc_id == favSticker.dc_id && recSticker.id == favSticker.id) {
                    this.recentStickers.remove(b);
                    break;
                }
            }
        }
        if (!(previousCount == this.recentStickers.size() && previousCount2 == this.favouriteStickers.size())) {
            updateStickerTabs();
        }
        if (this.stickersGridAdapter != null) {
            this.stickersGridAdapter.notifyDataSetChanged();
        }
        checkPanels();
    }

    public void setStickersBanned(boolean value, int chatId) {
        if (value) {
            this.currentChatId = chatId;
        } else {
            this.currentChatId = 0;
        }
        View view = this.pagerSlidingTabStrip.getTab(6);
        if (view != null) {
            view.setAlpha(this.currentChatId != 0 ? 0.5f : 1.0f);
            if (this.currentChatId != 0 && this.pager.getCurrentItem() == 6) {
                this.pager.setCurrentItem(0);
            }
        }
    }

    public void showStickerBanHint() {
        if (this.mediaBanTooltip.getVisibility() != 0) {
            TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.currentChatId));
            if (chat != null && chat.banned_rights != null) {
                if (AndroidUtilities.isBannedForever(chat.banned_rights.until_date)) {
                    this.mediaBanTooltip.setText(LocaleController.getString("AttachStickersRestrictedForever", R.string.AttachStickersRestrictedForever));
                } else {
                    this.mediaBanTooltip.setText(LocaleController.formatString("AttachStickersRestricted", R.string.AttachStickersRestricted, new Object[]{LocaleController.formatDateForBan((long) chat.banned_rights.until_date)}));
                }
                this.mediaBanTooltip.setVisibility(0);
                AnimatorSet AnimatorSet = new AnimatorSet();
                AnimatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.mediaBanTooltip, "alpha", new float[]{0.0f, 1.0f})});
                AnimatorSet.addListener(new AnimatorListenerAdapter() {

                    /* renamed from: org.telegram.ui.Components.EmojiView$28$1 */
                    class C25711 implements Runnable {

                        /* renamed from: org.telegram.ui.Components.EmojiView$28$1$1 */
                        class C25701 extends AnimatorListenerAdapter {
                            C25701() {
                            }

                            public void onAnimationEnd(Animator animation) {
                                if (EmojiView.this.mediaBanTooltip != null) {
                                    EmojiView.this.mediaBanTooltip.setVisibility(4);
                                }
                            }
                        }

                        C25711() {
                        }

                        public void run() {
                            if (EmojiView.this.mediaBanTooltip != null) {
                                AnimatorSet AnimatorSet = new AnimatorSet();
                                Animator[] animatorArr = new Animator[1];
                                animatorArr[0] = ObjectAnimator.ofFloat(EmojiView.this.mediaBanTooltip, "alpha", new float[]{0.0f});
                                AnimatorSet.playTogether(animatorArr);
                                AnimatorSet.addListener(new C25701());
                                AnimatorSet.setDuration(300);
                                AnimatorSet.start();
                            }
                        }
                    }

                    public void onAnimationEnd(Animator animation) {
                        AndroidUtilities.runOnUIThread(new C25711(), DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                    }
                });
                AnimatorSet.setDuration(300);
                AnimatorSet.start();
            }
        }
    }

    private void updateVisibleTrendingSets() {
        if (this.trendingGridAdapter != null && this.trendingGridAdapter != null) {
            try {
                int count = this.trendingGridView.getChildCount();
                for (int a = 0; a < count; a++) {
                    View child = this.trendingGridView.getChildAt(a);
                    if ((child instanceof FeaturedStickerSetInfoCell) && ((Holder) this.trendingGridView.getChildViewHolder(child)) != null) {
                        FeaturedStickerSetInfoCell cell = (FeaturedStickerSetInfoCell) child;
                        ArrayList<Long> unreadStickers = StickersQuery.getUnreadStickerSets();
                        TLRPC$StickerSetCovered stickerSetCovered = cell.getStickerSet();
                        boolean unread = unreadStickers != null && unreadStickers.contains(Long.valueOf(stickerSetCovered.set.id));
                        cell.setStickerSet(stickerSetCovered, unread);
                        if (unread) {
                            StickersQuery.markFaturedStickersByIdAsRead(stickerSetCovered.set.id);
                        }
                        boolean installing = this.installingStickerSets.containsKey(Long.valueOf(stickerSetCovered.set.id));
                        boolean removing = this.removingStickerSets.containsKey(Long.valueOf(stickerSetCovered.set.id));
                        if (installing || removing) {
                            if (installing && cell.isInstalled()) {
                                this.installingStickerSets.remove(Long.valueOf(stickerSetCovered.set.id));
                                installing = false;
                            } else if (removing) {
                                if (!cell.isInstalled()) {
                                    this.removingStickerSets.remove(Long.valueOf(stickerSetCovered.set.id));
                                    removing = false;
                                }
                            }
                        }
                        boolean z = installing || removing;
                        cell.setDrawProgress(z);
                    }
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public boolean areThereAnyStickers() {
        return this.stickersGridAdapter != null && this.stickersGridAdapter.getItemCount() > 0;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.stickersDidLoaded) {
            if (((Integer) args[0]).intValue() == 0) {
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
        } else if (id == NotificationCenter.recentDocumentsDidLoaded) {
            boolean isGif = ((Boolean) args[0]).booleanValue();
            int type = ((Integer) args[1]).intValue();
            if (isGif || type == 0 || type == 2) {
                checkDocuments(isGif);
            }
        } else if (id == NotificationCenter.featuredStickersDidLoaded) {
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
                int count = this.pagerSlidingTabStrip.getChildCount();
                for (int a = 0; a < count; a++) {
                    this.pagerSlidingTabStrip.getChildAt(a).invalidate();
                }
            }
            updateStickerTabs();
        } else if (id == NotificationCenter.groupStickersDidLoaded && this.info != null && this.info.stickerset != null && this.info.stickerset.id == ((Long) args[0]).longValue()) {
            updateStickerTabs();
        }
    }
}
