package org.telegram.ui.Components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.SparseArray;
import android.util.StateSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.AdapterDataObserver;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnItemTouchListener;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.Theme;

public class RecyclerListView extends RecyclerView {
    private static int[] attributes;
    private static boolean gotAttributes;
    private Runnable clickRunnable;
    private int currentChildPosition;
    private View currentChildView;
    private int currentFirst = -1;
    private int currentVisible = -1;
    private boolean disallowInterceptTouchEvents;
    private View emptyView;
    private FastScroll fastScroll;
    private GestureDetector gestureDetector;
    private ArrayList<View> headers;
    private ArrayList<View> headersCache;
    private boolean hiddenByEmptyView;
    private boolean ignoreOnScroll;
    private boolean instantClick;
    private boolean interceptedByChild;
    private boolean isChildViewEnabled;
    private AdapterDataObserver observer = new C27241();
    private OnInterceptTouchListener onInterceptTouchListener;
    public OnItemClickListener onItemClickListener;
    private OnItemClickListenerExtended onItemClickListenerExtended;
    private OnItemLongClickListener onItemLongClickListener;
    private OnScrollListener onScrollListener;
    private View pinnedHeader;
    private boolean scrollEnabled = true;
    private SectionsAdapter sectionsAdapter;
    private int sectionsCount;
    private int sectionsType;
    private Runnable selectChildRunnable;
    private Drawable selectorDrawable;
    private int selectorPosition;
    private Rect selectorRect = new Rect();
    private boolean selfOnLayout;
    private int startSection;
    private boolean wasPressed;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public static abstract class SelectionAdapter extends Adapter {
        public abstract boolean isEnabled(ViewHolder viewHolder);
    }

    public static abstract class FastScrollAdapter extends SelectionAdapter {
        public abstract String getLetter(int i);

        public abstract int getPositionForScrollProgress(float f);
    }

    public static abstract class SectionsAdapter extends FastScrollAdapter {
        private int count;
        private SparseArray<Integer> sectionCache;
        private int sectionCount;
        private SparseArray<Integer> sectionCountCache;
        private SparseArray<Integer> sectionPositionCache;

        public abstract int getCountForSection(int i);

        public abstract Object getItem(int i, int i2);

        public abstract int getItemViewType(int i, int i2);

        public abstract int getSectionCount();

        public abstract View getSectionHeaderView(int i, View view);

        public abstract boolean isEnabled(int i, int i2);

        public abstract void onBindViewHolder(int i, int i2, ViewHolder viewHolder);

        private void cleanupCache() {
            this.sectionCache = new SparseArray();
            this.sectionPositionCache = new SparseArray();
            this.sectionCountCache = new SparseArray();
            this.count = -1;
            this.sectionCount = -1;
        }

        public SectionsAdapter() {
            cleanupCache();
        }

        public void notifyDataSetChanged() {
            cleanupCache();
            super.notifyDataSetChanged();
        }

        public boolean isEnabled(ViewHolder holder) {
            int position = holder.getAdapterPosition();
            return isEnabled(getSectionForPosition(position), getPositionInSectionForPosition(position));
        }

        public final int getItemCount() {
            if (this.count >= 0) {
                return this.count;
            }
            this.count = 0;
            for (int i = 0; i < internalGetSectionCount(); i++) {
                this.count += internalGetCountForSection(i);
            }
            return this.count;
        }

        public final Object getItem(int position) {
            return getItem(getSectionForPosition(position), getPositionInSectionForPosition(position));
        }

        public final int getItemViewType(int position) {
            return getItemViewType(getSectionForPosition(position), getPositionInSectionForPosition(position));
        }

        public final void onBindViewHolder(ViewHolder holder, int position) {
            onBindViewHolder(getSectionForPosition(position), getPositionInSectionForPosition(position), holder);
        }

        private int internalGetCountForSection(int section) {
            Integer cachedSectionCount = (Integer) this.sectionCountCache.get(section);
            if (cachedSectionCount != null) {
                return cachedSectionCount.intValue();
            }
            int sectionCount = getCountForSection(section);
            this.sectionCountCache.put(section, Integer.valueOf(sectionCount));
            return sectionCount;
        }

        private int internalGetSectionCount() {
            if (this.sectionCount >= 0) {
                return this.sectionCount;
            }
            this.sectionCount = getSectionCount();
            return this.sectionCount;
        }

        public final int getSectionForPosition(int position) {
            Integer cachedSection = (Integer) this.sectionCache.get(position);
            if (cachedSection != null) {
                return cachedSection.intValue();
            }
            int sectionStart = 0;
            int i = 0;
            while (i < internalGetSectionCount()) {
                int sectionEnd = sectionStart + internalGetCountForSection(i);
                if (position < sectionStart || position >= sectionEnd) {
                    sectionStart = sectionEnd;
                    i++;
                } else {
                    this.sectionCache.put(position, Integer.valueOf(i));
                    return i;
                }
            }
            return -1;
        }

        public int getPositionInSectionForPosition(int position) {
            Integer cachedPosition = (Integer) this.sectionPositionCache.get(position);
            if (cachedPosition != null) {
                return cachedPosition.intValue();
            }
            int sectionStart = 0;
            int i = 0;
            while (i < internalGetSectionCount()) {
                int sectionEnd = sectionStart + internalGetCountForSection(i);
                if (position < sectionStart || position >= sectionEnd) {
                    sectionStart = sectionEnd;
                    i++;
                } else {
                    int positionInSection = position - sectionStart;
                    this.sectionPositionCache.put(position, Integer.valueOf(positionInSection));
                    return positionInSection;
                }
            }
            return -1;
        }
    }

    public interface OnItemLongClickListener {
        boolean onItemClick(View view, int i);
    }

    public interface OnItemClickListenerExtended {
        void onItemClick(View view, int i, float f, float f2);
    }

    /* renamed from: org.telegram.ui.Components.RecyclerListView$1 */
    class C27241 extends AdapterDataObserver {
        C27241() {
        }

        public void onChanged() {
            RecyclerListView.this.checkIfEmpty();
            RecyclerListView.this.selectorRect.setEmpty();
            RecyclerListView.this.invalidate();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            RecyclerListView.this.checkIfEmpty();
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            RecyclerListView.this.checkIfEmpty();
        }
    }

    /* renamed from: org.telegram.ui.Components.RecyclerListView$2 */
    class C27252 extends OnScrollListener {
        boolean scrollingByUser;

        C27252() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            boolean z = false;
            if (!(newState == 0 || RecyclerListView.this.currentChildView == null)) {
                if (RecyclerListView.this.selectChildRunnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(RecyclerListView.this.selectChildRunnable);
                    RecyclerListView.this.selectChildRunnable = null;
                }
                MotionEvent event = MotionEvent.obtain(0, 0, 3, 0.0f, 0.0f, 0);
                try {
                    RecyclerListView.this.gestureDetector.onTouchEvent(event);
                } catch (Exception e) {
                    FileLog.e(e);
                }
                RecyclerListView.this.currentChildView.onTouchEvent(event);
                event.recycle();
                View child = RecyclerListView.this.currentChildView;
                RecyclerListView.this.onChildPressed(RecyclerListView.this.currentChildView, false);
                RecyclerListView.this.currentChildView = null;
                RecyclerListView.this.removeSelection(child, null);
                RecyclerListView.this.interceptedByChild = false;
            }
            if (RecyclerListView.this.onScrollListener != null) {
                RecyclerListView.this.onScrollListener.onScrollStateChanged(recyclerView, newState);
            }
            if (newState == 1 || newState == 2) {
                z = true;
            }
            this.scrollingByUser = z;
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (RecyclerListView.this.onScrollListener != null) {
                RecyclerListView.this.onScrollListener.onScrolled(recyclerView, dx, dy);
            }
            if (RecyclerListView.this.selectorPosition != -1) {
                RecyclerListView.this.selectorRect.offset(-dx, -dy);
                RecyclerListView.this.selectorDrawable.setBounds(RecyclerListView.this.selectorRect);
                RecyclerListView.this.invalidate();
            } else {
                RecyclerListView.this.selectorRect.setEmpty();
            }
            if ((this.scrollingByUser && RecyclerListView.this.fastScroll != null) || (RecyclerListView.this.sectionsType != 0 && RecyclerListView.this.sectionsAdapter != null)) {
                LayoutManager layoutManager = RecyclerListView.this.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    if (linearLayoutManager.getOrientation() == 1) {
                        int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                        if (firstVisibleItem != -1) {
                            if (this.scrollingByUser && RecyclerListView.this.fastScroll != null) {
                                Adapter adapter = RecyclerListView.this.getAdapter();
                                if (adapter instanceof FastScrollAdapter) {
                                    RecyclerListView.this.fastScroll.setProgress(((float) firstVisibleItem) / ((float) adapter.getItemCount()));
                                }
                            }
                            if (RecyclerListView.this.sectionsAdapter == null) {
                                return;
                            }
                            View child;
                            int headerTop;
                            if (RecyclerListView.this.sectionsType == 1) {
                                int visibleItemCount = Math.abs(linearLayoutManager.findLastVisibleItemPosition() - firstVisibleItem) + 1;
                                RecyclerListView.this.headersCache.addAll(RecyclerListView.this.headers);
                                RecyclerListView.this.headers.clear();
                                if (RecyclerListView.this.sectionsAdapter.getItemCount() != 0) {
                                    int itemNum;
                                    if (!(RecyclerListView.this.currentFirst == firstVisibleItem && RecyclerListView.this.currentVisible == visibleItemCount)) {
                                        RecyclerListView.this.currentFirst = firstVisibleItem;
                                        RecyclerListView.this.currentVisible = visibleItemCount;
                                        RecyclerListView.this.sectionsCount = 1;
                                        RecyclerListView.this.startSection = RecyclerListView.this.sectionsAdapter.getSectionForPosition(firstVisibleItem);
                                        itemNum = (RecyclerListView.this.sectionsAdapter.getCountForSection(RecyclerListView.this.startSection) + firstVisibleItem) - RecyclerListView.this.sectionsAdapter.getPositionInSectionForPosition(firstVisibleItem);
                                        while (itemNum < firstVisibleItem + visibleItemCount) {
                                            itemNum += RecyclerListView.this.sectionsAdapter.getCountForSection(RecyclerListView.this.startSection + RecyclerListView.this.sectionsCount);
                                            RecyclerListView.this.sectionsCount = RecyclerListView.this.sectionsCount + 1;
                                        }
                                    }
                                    itemNum = firstVisibleItem;
                                    for (int a = RecyclerListView.this.startSection; a < RecyclerListView.this.startSection + RecyclerListView.this.sectionsCount; a++) {
                                        View header = null;
                                        if (!RecyclerListView.this.headersCache.isEmpty()) {
                                            header = (View) RecyclerListView.this.headersCache.get(0);
                                            RecyclerListView.this.headersCache.remove(0);
                                        }
                                        header = RecyclerListView.this.getSectionHeaderView(a, header);
                                        RecyclerListView.this.headers.add(header);
                                        int count = RecyclerListView.this.sectionsAdapter.getCountForSection(a);
                                        if (a == RecyclerListView.this.startSection) {
                                            int pos = RecyclerListView.this.sectionsAdapter.getPositionInSectionForPosition(itemNum);
                                            if (pos == count - 1) {
                                                header.setTag(Integer.valueOf(-header.getHeight()));
                                            } else if (pos == count - 2) {
                                                child = RecyclerListView.this.getChildAt(itemNum - firstVisibleItem);
                                                if (child != null) {
                                                    headerTop = child.getTop();
                                                } else {
                                                    headerTop = -AndroidUtilities.dp(100.0f);
                                                }
                                                if (headerTop < 0) {
                                                    header.setTag(Integer.valueOf(headerTop));
                                                } else {
                                                    header.setTag(Integer.valueOf(0));
                                                }
                                            } else {
                                                header.setTag(Integer.valueOf(0));
                                            }
                                            itemNum += count - RecyclerListView.this.sectionsAdapter.getPositionInSectionForPosition(firstVisibleItem);
                                        } else {
                                            child = RecyclerListView.this.getChildAt(itemNum - firstVisibleItem);
                                            if (child != null) {
                                                header.setTag(Integer.valueOf(child.getTop()));
                                            } else {
                                                header.setTag(Integer.valueOf(-AndroidUtilities.dp(100.0f)));
                                            }
                                            itemNum += count;
                                        }
                                    }
                                }
                            } else if (RecyclerListView.this.sectionsType == 2 && RecyclerListView.this.sectionsAdapter.getItemCount() != 0) {
                                int startSection = RecyclerListView.this.sectionsAdapter.getSectionForPosition(firstVisibleItem);
                                if (RecyclerListView.this.currentFirst != startSection || RecyclerListView.this.pinnedHeader == null) {
                                    RecyclerListView.this.pinnedHeader = RecyclerListView.this.getSectionHeaderView(startSection, RecyclerListView.this.pinnedHeader);
                                    RecyclerListView.this.currentFirst = startSection;
                                }
                                if (RecyclerListView.this.sectionsAdapter.getPositionInSectionForPosition(firstVisibleItem) == RecyclerListView.this.sectionsAdapter.getCountForSection(startSection) - 1) {
                                    child = RecyclerListView.this.getChildAt(0);
                                    int headerHeight = RecyclerListView.this.pinnedHeader.getHeight();
                                    headerTop = 0;
                                    if (child != null) {
                                        int available = child.getTop() + child.getHeight();
                                        if (available < headerHeight) {
                                            headerTop = available - headerHeight;
                                        }
                                    } else {
                                        headerTop = -AndroidUtilities.dp(100.0f);
                                    }
                                    if (headerTop < 0) {
                                        RecyclerListView.this.pinnedHeader.setTag(Integer.valueOf(headerTop));
                                    } else {
                                        RecyclerListView.this.pinnedHeader.setTag(Integer.valueOf(0));
                                    }
                                } else {
                                    RecyclerListView.this.pinnedHeader.setTag(Integer.valueOf(0));
                                }
                                RecyclerListView.this.invalidate();
                            }
                        }
                    }
                }
            }
        }
    }

    private class FastScroll extends View {
        private float bubbleProgress;
        private int[] colors = new int[6];
        private String currentLetter;
        private long lastUpdateTime;
        private float lastY;
        private StaticLayout letterLayout;
        private TextPaint letterPaint = new TextPaint(1);
        private StaticLayout oldLetterLayout;
        private Paint paint = new Paint(1);
        private Path path = new Path();
        private boolean pressed;
        private float progress;
        private float[] radii = new float[8];
        private RectF rect = new RectF();
        private int scrollX;
        private float startDy;
        private float textX;
        private float textY;

        public FastScroll(Context context) {
            super(context);
            this.letterPaint.setTextSize((float) AndroidUtilities.dp(45.0f));
            for (int a = 0; a < 8; a++) {
                this.radii[a] = (float) AndroidUtilities.dp(44.0f);
            }
            this.scrollX = LocaleController.isRTL ? AndroidUtilities.dp(10.0f) : AndroidUtilities.dp(117.0f);
            updateColors();
        }

        private void updateColors() {
            int inactive = Theme.getColor(Theme.key_fastScrollInactive);
            int active = Theme.getColor(Theme.key_fastScrollActive);
            this.paint.setColor(inactive);
            this.letterPaint.setColor(Theme.getColor(Theme.key_fastScrollText));
            this.colors[0] = Color.red(inactive);
            this.colors[1] = Color.red(active);
            this.colors[2] = Color.green(inactive);
            this.colors[3] = Color.green(active);
            this.colors[4] = Color.blue(inactive);
            this.colors[5] = Color.blue(active);
            invalidate();
        }

        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    float x = event.getX();
                    this.lastY = event.getY();
                    float currectY = ((float) Math.ceil((double) (((float) (getMeasuredHeight() - AndroidUtilities.dp(54.0f))) * this.progress))) + ((float) AndroidUtilities.dp(12.0f));
                    if ((LocaleController.isRTL && x > ((float) AndroidUtilities.dp(25.0f))) || ((!LocaleController.isRTL && x < ((float) AndroidUtilities.dp(107.0f))) || this.lastY < currectY || this.lastY > ((float) AndroidUtilities.dp(30.0f)) + currectY)) {
                        return false;
                    }
                    this.startDy = this.lastY - currectY;
                    this.pressed = true;
                    this.lastUpdateTime = System.currentTimeMillis();
                    getCurrentLetter();
                    invalidate();
                    return true;
                case 1:
                case 3:
                    this.pressed = false;
                    this.lastUpdateTime = System.currentTimeMillis();
                    invalidate();
                    return true;
                case 2:
                    if (!this.pressed) {
                        return true;
                    }
                    float newY = event.getY();
                    float minY = ((float) AndroidUtilities.dp(12.0f)) + this.startDy;
                    float maxY = ((float) (getMeasuredHeight() - AndroidUtilities.dp(42.0f))) + this.startDy;
                    if (newY < minY) {
                        newY = minY;
                    } else if (newY > maxY) {
                        newY = maxY;
                    }
                    float dy = newY - this.lastY;
                    this.lastY = newY;
                    this.progress += dy / ((float) (getMeasuredHeight() - AndroidUtilities.dp(54.0f)));
                    if (this.progress < 0.0f) {
                        this.progress = 0.0f;
                    } else if (this.progress > 1.0f) {
                        this.progress = 1.0f;
                    }
                    getCurrentLetter();
                    invalidate();
                    return true;
                default:
                    return super.onTouchEvent(event);
            }
        }

        private void getCurrentLetter() {
            LayoutManager layoutManager = RecyclerListView.this.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == 1) {
                    Adapter adapter = RecyclerListView.this.getAdapter();
                    if (adapter instanceof FastScrollAdapter) {
                        FastScrollAdapter fastScrollAdapter = (FastScrollAdapter) adapter;
                        int position = fastScrollAdapter.getPositionForScrollProgress(this.progress);
                        linearLayoutManager.scrollToPositionWithOffset(position, 0);
                        String newLetter = fastScrollAdapter.getLetter(position);
                        if (newLetter == null) {
                            if (this.letterLayout != null) {
                                this.oldLetterLayout = this.letterLayout;
                            }
                            this.letterLayout = null;
                        } else if (!newLetter.equals(this.currentLetter)) {
                            this.letterLayout = new StaticLayout(newLetter, this.letterPaint, 1000, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                            this.oldLetterLayout = null;
                            if (this.letterLayout.getLineCount() > 0) {
                                float lWidth = this.letterLayout.getLineWidth(0);
                                float lleft = this.letterLayout.getLineLeft(0);
                                if (LocaleController.isRTL) {
                                    this.textX = (((float) AndroidUtilities.dp(10.0f)) + ((((float) AndroidUtilities.dp(88.0f)) - this.letterLayout.getLineWidth(0)) / 2.0f)) - this.letterLayout.getLineLeft(0);
                                } else {
                                    this.textX = ((((float) AndroidUtilities.dp(88.0f)) - this.letterLayout.getLineWidth(0)) / 2.0f) - this.letterLayout.getLineLeft(0);
                                }
                                this.textY = (float) ((AndroidUtilities.dp(88.0f) - this.letterLayout.getHeight()) / 2);
                            }
                        }
                    }
                }
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(AndroidUtilities.dp(132.0f), MeasureSpec.getSize(heightMeasureSpec));
        }

        protected void onDraw(Canvas canvas) {
            this.paint.setColor(Color.argb(255, this.colors[0] + ((int) (((float) (this.colors[1] - this.colors[0])) * this.bubbleProgress)), this.colors[2] + ((int) (((float) (this.colors[3] - this.colors[2])) * this.bubbleProgress)), this.colors[4] + ((int) (((float) (this.colors[5] - this.colors[4])) * this.bubbleProgress))));
            int y = (int) Math.ceil((double) (((float) (getMeasuredHeight() - AndroidUtilities.dp(54.0f))) * this.progress));
            this.rect.set((float) this.scrollX, (float) (AndroidUtilities.dp(12.0f) + y), (float) (this.scrollX + AndroidUtilities.dp(5.0f)), (float) (AndroidUtilities.dp(42.0f) + y));
            canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), this.paint);
            if (this.pressed || this.bubbleProgress != 0.0f) {
                float raduisTop;
                float raduisBottom;
                StaticLayout layoutToDraw;
                this.paint.setAlpha((int) (255.0f * this.bubbleProgress));
                int progressY = y + AndroidUtilities.dp(30.0f);
                y -= AndroidUtilities.dp(46.0f);
                float diff = 0.0f;
                if (y <= AndroidUtilities.dp(12.0f)) {
                    diff = (float) (AndroidUtilities.dp(12.0f) - y);
                    y = AndroidUtilities.dp(12.0f);
                }
                canvas.translate((float) AndroidUtilities.dp(10.0f), (float) y);
                if (diff <= ((float) AndroidUtilities.dp(29.0f))) {
                    raduisTop = (float) AndroidUtilities.dp(44.0f);
                    raduisBottom = ((float) AndroidUtilities.dp(4.0f)) + ((diff / ((float) AndroidUtilities.dp(29.0f))) * ((float) AndroidUtilities.dp(40.0f)));
                } else {
                    raduisBottom = (float) AndroidUtilities.dp(44.0f);
                    raduisTop = ((float) AndroidUtilities.dp(4.0f)) + ((1.0f - ((diff - ((float) AndroidUtilities.dp(29.0f))) / ((float) AndroidUtilities.dp(29.0f)))) * ((float) AndroidUtilities.dp(40.0f)));
                }
                if ((LocaleController.isRTL && !(this.radii[0] == raduisTop && this.radii[6] == raduisBottom)) || !(LocaleController.isRTL || (this.radii[2] == raduisTop && this.radii[4] == raduisBottom))) {
                    float[] fArr;
                    if (LocaleController.isRTL) {
                        fArr = this.radii;
                        this.radii[1] = raduisTop;
                        fArr[0] = raduisTop;
                        fArr = this.radii;
                        this.radii[7] = raduisBottom;
                        fArr[6] = raduisBottom;
                    } else {
                        fArr = this.radii;
                        this.radii[3] = raduisTop;
                        fArr[2] = raduisTop;
                        fArr = this.radii;
                        this.radii[5] = raduisBottom;
                        fArr[4] = raduisBottom;
                    }
                    this.path.reset();
                    this.rect.set(LocaleController.isRTL ? (float) AndroidUtilities.dp(10.0f) : 0.0f, 0.0f, (float) AndroidUtilities.dp(LocaleController.isRTL ? 98.0f : 88.0f), (float) AndroidUtilities.dp(88.0f));
                    this.path.addRoundRect(this.rect, this.radii, Direction.CW);
                    this.path.close();
                }
                if (this.letterLayout != null) {
                    layoutToDraw = this.letterLayout;
                } else {
                    layoutToDraw = this.oldLetterLayout;
                }
                if (layoutToDraw != null) {
                    canvas.save();
                    canvas.scale(this.bubbleProgress, this.bubbleProgress, (float) this.scrollX, (float) (progressY - y));
                    canvas.drawPath(this.path, this.paint);
                    canvas.translate(this.textX, this.textY);
                    layoutToDraw.draw(canvas);
                    canvas.restore();
                }
            }
            if ((this.pressed && this.letterLayout != null && this.bubbleProgress < 1.0f) || ((!this.pressed || this.letterLayout == null) && this.bubbleProgress > 0.0f)) {
                long newTime = System.currentTimeMillis();
                long dt = newTime - this.lastUpdateTime;
                if (dt < 0 || dt > 17) {
                    dt = 17;
                }
                this.lastUpdateTime = newTime;
                invalidate();
                if (!this.pressed || this.letterLayout == null) {
                    this.bubbleProgress -= ((float) dt) / 120.0f;
                    if (this.bubbleProgress < 0.0f) {
                        this.bubbleProgress = 0.0f;
                        return;
                    }
                    return;
                }
                this.bubbleProgress += ((float) dt) / 120.0f;
                if (this.bubbleProgress > 1.0f) {
                    this.bubbleProgress = 1.0f;
                }
            }
        }

        public void layout(int l, int t, int r, int b) {
            if (RecyclerListView.this.selfOnLayout) {
                super.layout(l, t, r, b);
            }
        }

        private void setProgress(float value) {
            this.progress = value;
            invalidate();
        }
    }

    public static class Holder extends ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    public interface OnInterceptTouchListener {
        boolean onInterceptTouchEvent(MotionEvent motionEvent);
    }

    private class RecyclerListViewItemClickListener implements OnItemTouchListener {

        /* renamed from: org.telegram.ui.Components.RecyclerListView$RecyclerListViewItemClickListener$2 */
        class C27282 implements Runnable {
            C27282() {
            }

            public void run() {
                if (RecyclerListView.this.selectChildRunnable != null && RecyclerListView.this.currentChildView != null) {
                    RecyclerListView.this.onChildPressed(RecyclerListView.this.currentChildView, true);
                    RecyclerListView.this.selectChildRunnable = null;
                }
            }
        }

        public RecyclerListViewItemClickListener(Context context) {
            RecyclerListView.this.gestureDetector = new GestureDetector(context, new SimpleOnGestureListener(RecyclerListView.this) {
                public boolean onSingleTapUp(MotionEvent e) {
                    if (!(RecyclerListView.this.currentChildView == null || (RecyclerListView.this.onItemClickListener == null && RecyclerListView.this.onItemClickListenerExtended == null))) {
                        RecyclerListView.this.onChildPressed(RecyclerListView.this.currentChildView, true);
                        final View view = RecyclerListView.this.currentChildView;
                        final int position = RecyclerListView.this.currentChildPosition;
                        final float x = e.getX();
                        final float y = e.getY();
                        if (RecyclerListView.this.instantClick && position != -1) {
                            view.playSoundEffect(0);
                            if (RecyclerListView.this.onItemClickListener != null) {
                                RecyclerListView.this.onItemClickListener.onItemClick(view, position);
                            } else if (RecyclerListView.this.onItemClickListenerExtended != null) {
                                RecyclerListView.this.onItemClickListenerExtended.onItemClick(view, position, x, y);
                            }
                        }
                        AndroidUtilities.runOnUIThread(RecyclerListView.this.clickRunnable = new Runnable() {
                            public void run() {
                                if (this == RecyclerListView.this.clickRunnable) {
                                    RecyclerListView.this.clickRunnable = null;
                                }
                                if (view != null) {
                                    RecyclerListView.this.onChildPressed(view, false);
                                    if (!RecyclerListView.this.instantClick) {
                                        view.playSoundEffect(0);
                                        if (position == -1) {
                                            return;
                                        }
                                        if (RecyclerListView.this.onItemClickListener != null) {
                                            RecyclerListView.this.onItemClickListener.onItemClick(view, position);
                                        } else if (RecyclerListView.this.onItemClickListenerExtended != null) {
                                            RecyclerListView.this.onItemClickListenerExtended.onItemClick(view, position, x, y);
                                        }
                                    }
                                }
                            }
                        }, (long) ViewConfiguration.getPressedStateDuration());
                        if (RecyclerListView.this.selectChildRunnable != null) {
                            View pressedChild = RecyclerListView.this.currentChildView;
                            AndroidUtilities.cancelRunOnUIThread(RecyclerListView.this.selectChildRunnable);
                            RecyclerListView.this.selectChildRunnable = null;
                            RecyclerListView.this.currentChildView = null;
                            RecyclerListView.this.interceptedByChild = false;
                            RecyclerListView.this.removeSelection(pressedChild, e);
                        }
                    }
                    return true;
                }

                public void onLongPress(MotionEvent event) {
                    if (RecyclerListView.this.currentChildView != null) {
                        View child = RecyclerListView.this.currentChildView;
                        if (RecyclerListView.this.onItemLongClickListener != null && RecyclerListView.this.currentChildPosition != -1 && RecyclerListView.this.onItemLongClickListener.onItemClick(RecyclerListView.this.currentChildView, RecyclerListView.this.currentChildPosition)) {
                            child.performHapticFeedback(0);
                        }
                    }
                }
            });
        }

        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent event) {
            int action = event.getActionMasked();
            boolean isScrollIdle = RecyclerListView.this.getScrollState() == 0;
            if ((action == 0 || action == 5) && RecyclerListView.this.currentChildView == null && isScrollIdle) {
                float ex = event.getX();
                float ey = event.getY();
                if (RecyclerListView.this.allowSelectChildAtPosition(ex, ey)) {
                    RecyclerListView.this.currentChildView = view.findChildViewUnder(ex, ey);
                }
                if (RecyclerListView.this.currentChildView instanceof ViewGroup) {
                    float x = event.getX() - ((float) RecyclerListView.this.currentChildView.getLeft());
                    float y = event.getY() - ((float) RecyclerListView.this.currentChildView.getTop());
                    ViewGroup viewGroup = (ViewGroup) RecyclerListView.this.currentChildView;
                    for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
                        View child = viewGroup.getChildAt(i);
                        if (x >= ((float) child.getLeft()) && x <= ((float) child.getRight()) && y >= ((float) child.getTop()) && y <= ((float) child.getBottom()) && child.isClickable()) {
                            RecyclerListView.this.currentChildView = null;
                            break;
                        }
                    }
                }
                RecyclerListView.this.currentChildPosition = -1;
                if (RecyclerListView.this.currentChildView != null) {
                    RecyclerListView.this.currentChildPosition = view.getChildPosition(RecyclerListView.this.currentChildView);
                    MotionEvent childEvent = MotionEvent.obtain(0, 0, event.getActionMasked(), event.getX() - ((float) RecyclerListView.this.currentChildView.getLeft()), event.getY() - ((float) RecyclerListView.this.currentChildView.getTop()), 0);
                    if (RecyclerListView.this.currentChildView.onTouchEvent(childEvent)) {
                        RecyclerListView.this.interceptedByChild = true;
                    }
                    childEvent.recycle();
                }
            }
            if (!(RecyclerListView.this.currentChildView == null || RecyclerListView.this.interceptedByChild || event == null)) {
                try {
                    RecyclerListView.this.gestureDetector.onTouchEvent(event);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            if (action == 0 || action == 5) {
                if (!(RecyclerListView.this.interceptedByChild || RecyclerListView.this.currentChildView == null)) {
                    RecyclerListView.this.selectChildRunnable = new C27282();
                    AndroidUtilities.runOnUIThread(RecyclerListView.this.selectChildRunnable, (long) ViewConfiguration.getTapTimeout());
                    if (RecyclerListView.this.currentChildView.isEnabled()) {
                        RecyclerListView.this.positionSelector(RecyclerListView.this.currentChildPosition, RecyclerListView.this.currentChildView);
                        if (RecyclerListView.this.selectorDrawable != null) {
                            Drawable d = RecyclerListView.this.selectorDrawable.getCurrent();
                            if (d != null && (d instanceof TransitionDrawable)) {
                                if (RecyclerListView.this.onItemLongClickListener != null) {
                                    ((TransitionDrawable) d).startTransition(ViewConfiguration.getLongPressTimeout());
                                } else {
                                    ((TransitionDrawable) d).resetTransition();
                                }
                            }
                            if (VERSION.SDK_INT >= 21) {
                                RecyclerListView.this.selectorDrawable.setHotspot(event.getX(), event.getY());
                            }
                        }
                        RecyclerListView.this.updateSelectorState();
                    } else {
                        RecyclerListView.this.selectorRect.setEmpty();
                    }
                }
            } else if ((action == 1 || action == 6 || action == 3 || !isScrollIdle) && RecyclerListView.this.currentChildView != null) {
                if (RecyclerListView.this.selectChildRunnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(RecyclerListView.this.selectChildRunnable);
                    RecyclerListView.this.selectChildRunnable = null;
                }
                View pressedChild = RecyclerListView.this.currentChildView;
                RecyclerListView.this.onChildPressed(RecyclerListView.this.currentChildView, false);
                RecyclerListView.this.currentChildView = null;
                RecyclerListView.this.interceptedByChild = false;
                RecyclerListView.this.removeSelection(pressedChild, event);
            }
            return false;
        }

        public void onTouchEvent(RecyclerView view, MotionEvent event) {
        }

        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            RecyclerListView.this.cancelClickRunnables(true);
        }
    }

    protected void onChildPressed(View child, boolean pressed) {
        child.setPressed(pressed);
    }

    protected boolean allowSelectChildAtPosition(float x, float y) {
        return true;
    }

    private void removeSelection(View pressedChild, MotionEvent event) {
        if (pressedChild != null) {
            if (pressedChild == null || !pressedChild.isEnabled()) {
                this.selectorRect.setEmpty();
            } else {
                positionSelector(this.currentChildPosition, pressedChild);
                if (this.selectorDrawable != null) {
                    Drawable d = this.selectorDrawable.getCurrent();
                    if (d != null && (d instanceof TransitionDrawable)) {
                        ((TransitionDrawable) d).resetTransition();
                    }
                    if (event != null && VERSION.SDK_INT >= 21) {
                        this.selectorDrawable.setHotspot(event.getX(), event.getY());
                    }
                }
            }
            updateSelectorState();
        }
    }

    public void cancelClickRunnables(boolean uncheck) {
        if (this.selectChildRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.selectChildRunnable);
            this.selectChildRunnable = null;
        }
        if (this.currentChildView != null) {
            View child = this.currentChildView;
            if (uncheck) {
                onChildPressed(this.currentChildView, false);
            }
            this.currentChildView = null;
            removeSelection(child, null);
        }
        if (this.clickRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.clickRunnable);
            this.clickRunnable = null;
        }
        this.interceptedByChild = false;
    }

    public int[] getResourceDeclareStyleableIntArray(String packageName, String name) {
        try {
            Field f = Class.forName(packageName + ".R$styleable").getField(name);
            if (f != null) {
                return (int[]) f.get(null);
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public RecyclerListView(Context context) {
        super(context);
        setGlowColor(Theme.getColor(Theme.key_actionBarDefault));
        this.selectorDrawable = Theme.getSelectorDrawable(false);
        this.selectorDrawable.setCallback(this);
        try {
            if (!gotAttributes) {
                attributes = getResourceDeclareStyleableIntArray("com.android.internal", "View");
                gotAttributes = true;
            }
            TypedArray a = context.getTheme().obtainStyledAttributes(attributes);
            View.class.getDeclaredMethod("initializeScrollbars", new Class[]{TypedArray.class}).invoke(this, new Object[]{a});
            a.recycle();
        } catch (Throwable e) {
            FileLog.e(e);
        }
        super.setOnScrollListener(new C27252());
        addOnItemTouchListener(new RecyclerListViewItemClickListener(context));
    }

    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        if (attributes != null) {
            super.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
        }
    }

    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (this.fastScroll != null) {
            this.fastScroll.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(132.0f), 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.fastScroll != null) {
            this.selfOnLayout = true;
            if (LocaleController.isRTL) {
                this.fastScroll.layout(0, t, this.fastScroll.getMeasuredWidth(), this.fastScroll.getMeasuredHeight() + t);
            } else {
                int x = getMeasuredWidth() - this.fastScroll.getMeasuredWidth();
                this.fastScroll.layout(x, t, this.fastScroll.getMeasuredWidth() + x, this.fastScroll.getMeasuredHeight() + t);
            }
            this.selfOnLayout = false;
        }
    }

    public void setListSelectorColor(int color) {
        Theme.setSelectorDrawableColor(this.selectorDrawable, color, true);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListenerExtended listener) {
        this.onItemClickListenerExtended = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public void setEmptyView(View view) {
        if (this.emptyView != view) {
            this.emptyView = view;
            checkIfEmpty();
        }
    }

    public View getEmptyView() {
        return this.emptyView;
    }

    public void invalidateViews() {
        int count = getChildCount();
        for (int a = 0; a < count; a++) {
            getChildAt(a).invalidate();
        }
    }

    public void updateFastScrollColors() {
        if (this.fastScroll != null) {
            this.fastScroll.updateColors();
        }
    }

    public boolean canScrollVertically(int direction) {
        return this.scrollEnabled && super.canScrollVertically(direction);
    }

    public void setScrollEnabled(boolean value) {
        this.scrollEnabled = value;
    }

    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (!isEnabled()) {
            return false;
        }
        if (this.disallowInterceptTouchEvents) {
            requestDisallowInterceptTouchEvent(true);
        }
        if ((this.onInterceptTouchListener == null || !this.onInterceptTouchListener.onInterceptTouchEvent(e)) && !super.onInterceptTouchEvent(e)) {
            return false;
        }
        return true;
    }

    private void checkIfEmpty() {
        int i = 0;
        if (getAdapter() != null && this.emptyView != null) {
            boolean emptyViewVisible;
            if (getAdapter().getItemCount() == 0) {
                emptyViewVisible = true;
            } else {
                emptyViewVisible = false;
            }
            this.emptyView.setVisibility(emptyViewVisible ? 0 : 8);
            if (emptyViewVisible) {
                i = 4;
            }
            setVisibility(i);
            this.hiddenByEmptyView = true;
        } else if (this.hiddenByEmptyView && getVisibility() != 0) {
            setVisibility(0);
            this.hiddenByEmptyView = false;
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != 0) {
            this.hiddenByEmptyView = false;
        }
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.onScrollListener = listener;
    }

    public void setOnInterceptTouchListener(OnInterceptTouchListener listener) {
        this.onInterceptTouchListener = listener;
    }

    public void setInstantClick(boolean value) {
        this.instantClick = value;
    }

    public void setDisallowInterceptTouchEvents(boolean value) {
        this.disallowInterceptTouchEvents = value;
    }

    public void setFastScrollEnabled() {
        this.fastScroll = new FastScroll(getContext());
        if (getParent() != null) {
            ((ViewGroup) getParent()).addView(this.fastScroll);
        }
    }

    public void setFastScrollVisible(boolean value) {
        if (this.fastScroll != null) {
            this.fastScroll.setVisibility(value ? 0 : 8);
        }
    }

    public void setSectionsType(int type) {
        this.sectionsType = type;
        if (this.sectionsType == 1) {
            this.headers = new ArrayList();
            this.headersCache = new ArrayList();
        }
    }

    private void positionSelector(int position, View sel) {
        positionSelector(position, sel, false, -1.0f, -1.0f);
    }

    private void positionSelector(int position, View sel, boolean manageHotspot, float x, float y) {
        if (this.selectorDrawable != null) {
            boolean positionChanged;
            if (position != this.selectorPosition) {
                positionChanged = true;
            } else {
                positionChanged = false;
            }
            if (position != -1) {
                this.selectorPosition = position;
            }
            this.selectorRect.set(sel.getLeft(), sel.getTop(), sel.getRight(), sel.getBottom());
            boolean enabled = sel.isEnabled();
            if (this.isChildViewEnabled != enabled) {
                this.isChildViewEnabled = enabled;
            }
            if (positionChanged) {
                this.selectorDrawable.setVisible(false, false);
                this.selectorDrawable.setState(StateSet.NOTHING);
            }
            this.selectorDrawable.setBounds(this.selectorRect);
            if (positionChanged && getVisibility() == 0) {
                this.selectorDrawable.setVisible(true, false);
            }
            if (VERSION.SDK_INT >= 21 && manageHotspot) {
                this.selectorDrawable.setHotspot(x, y);
            }
        }
    }

    private void updateSelectorState() {
        if (this.selectorDrawable != null && this.selectorDrawable.isStateful()) {
            if (this.currentChildView == null) {
                this.selectorDrawable.setState(StateSet.NOTHING);
            } else if (this.selectorDrawable.setState(getDrawableStateForSelector())) {
                invalidateDrawable(this.selectorDrawable);
            }
        }
    }

    private int[] getDrawableStateForSelector() {
        int[] state = onCreateDrawableState(1);
        state[state.length - 1] = 16842919;
        return state;
    }

    public void onChildAttachedToWindow(View child) {
        if (getAdapter() instanceof SelectionAdapter) {
            ViewHolder holder = findContainingViewHolder(child);
            if (holder != null) {
                child.setEnabled(((SelectionAdapter) getAdapter()).isEnabled(holder));
            }
        } else {
            child.setEnabled(false);
        }
        super.onChildAttachedToWindow(child);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateSelectorState();
    }

    public boolean verifyDrawable(Drawable drawable) {
        return this.selectorDrawable == drawable || super.verifyDrawable(drawable);
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.selectorDrawable != null) {
            this.selectorDrawable.jumpToCurrentState();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.fastScroll != null && this.fastScroll.getParent() != getParent()) {
            ViewGroup parent = (ViewGroup) this.fastScroll.getParent();
            if (parent != null) {
                parent.removeView(this.fastScroll);
            }
            ((ViewGroup) getParent()).addView(this.fastScroll);
        }
    }

    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(this.observer);
        }
        if (this.headers != null) {
            this.headers.clear();
            this.headersCache.clear();
        }
        this.selectorPosition = -1;
        this.selectorRect.setEmpty();
        this.pinnedHeader = null;
        if (adapter instanceof SectionsAdapter) {
            this.sectionsAdapter = (SectionsAdapter) adapter;
        } else {
            this.sectionsAdapter = null;
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.observer);
        }
        checkIfEmpty();
    }

    public void stopScroll() {
        try {
            super.stopScroll();
        } catch (NullPointerException e) {
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    private View getSectionHeaderView(int section, View oldView) {
        boolean shouldLayout;
        if (oldView == null) {
            shouldLayout = true;
        } else {
            shouldLayout = false;
        }
        View view = this.sectionsAdapter.getSectionHeaderView(section, oldView);
        if (shouldLayout) {
            ensurePinnedHeaderLayout(view, false);
        }
        return view;
    }

    private void ensurePinnedHeaderLayout(View header, boolean forceLayout) {
        if (header.isLayoutRequested() || forceLayout) {
            if (this.sectionsType == 1) {
                LayoutParams layoutParams = header.getLayoutParams();
                try {
                    header.measure(MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824), MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824));
                } catch (Exception e) {
                    FileLog.e(e);
                }
            } else if (this.sectionsType == 2) {
                try {
                    header.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.sectionsType == 1) {
            if (this.sectionsAdapter != null && !this.headers.isEmpty()) {
                for (int a = 0; a < this.headers.size(); a++) {
                    ensurePinnedHeaderLayout((View) this.headers.get(a), true);
                }
            }
        } else if (this.sectionsType == 2 && this.sectionsAdapter != null && this.pinnedHeader != null) {
            ensurePinnedHeaderLayout(this.pinnedHeader, true);
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        float f = 0.0f;
        super.dispatchDraw(canvas);
        int saveCount;
        int top;
        if (this.sectionsType == 1) {
            if (this.sectionsAdapter != null && !this.headers.isEmpty()) {
                for (int a = 0; a < this.headers.size(); a++) {
                    float width;
                    View header = (View) this.headers.get(a);
                    saveCount = canvas.save();
                    top = ((Integer) header.getTag()).intValue();
                    if (LocaleController.isRTL) {
                        width = (float) (getWidth() - header.getWidth());
                    } else {
                        width = 0.0f;
                    }
                    canvas.translate(width, (float) top);
                    canvas.clipRect(0, 0, getWidth(), header.getMeasuredHeight());
                    header.draw(canvas);
                    canvas.restoreToCount(saveCount);
                }
            } else {
                return;
            }
        } else if (this.sectionsType == 2) {
            if (this.sectionsAdapter != null && this.pinnedHeader != null) {
                saveCount = canvas.save();
                top = ((Integer) this.pinnedHeader.getTag()).intValue();
                if (LocaleController.isRTL) {
                    f = (float) (getWidth() - this.pinnedHeader.getWidth());
                }
                canvas.translate(f, (float) top);
                canvas.clipRect(0, 0, getWidth(), this.pinnedHeader.getMeasuredHeight());
                this.pinnedHeader.draw(canvas);
                canvas.restoreToCount(saveCount);
            } else {
                return;
            }
        }
        if (!this.selectorRect.isEmpty()) {
            this.selectorDrawable.setBounds(this.selectorRect);
            this.selectorDrawable.draw(canvas);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.selectorPosition = -1;
        this.selectorRect.setEmpty();
    }

    public ArrayList<View> getHeaders() {
        return this.headers;
    }

    public ArrayList<View> getHeadersCache() {
        return this.headersCache;
    }

    public View getPinnedHeader() {
        return this.pinnedHeader;
    }
}
