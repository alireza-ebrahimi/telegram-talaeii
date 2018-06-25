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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    private AdapterDataObserver observer = new C45621();
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

        public SectionsAdapter() {
            cleanupCache();
        }

        private void cleanupCache() {
            this.sectionCache = new SparseArray();
            this.sectionPositionCache = new SparseArray();
            this.sectionCountCache = new SparseArray();
            this.count = -1;
            this.sectionCount = -1;
        }

        private int internalGetCountForSection(int i) {
            Integer num = (Integer) this.sectionCountCache.get(i);
            if (num != null) {
                return num.intValue();
            }
            int countForSection = getCountForSection(i);
            this.sectionCountCache.put(i, Integer.valueOf(countForSection));
            return countForSection;
        }

        private int internalGetSectionCount() {
            if (this.sectionCount >= 0) {
                return this.sectionCount;
            }
            this.sectionCount = getSectionCount();
            return this.sectionCount;
        }

        public abstract int getCountForSection(int i);

        public final Object getItem(int i) {
            return getItem(getSectionForPosition(i), getPositionInSectionForPosition(i));
        }

        public abstract Object getItem(int i, int i2);

        public final int getItemCount() {
            int i = 0;
            if (this.count >= 0) {
                return this.count;
            }
            this.count = 0;
            while (i < internalGetSectionCount()) {
                this.count += internalGetCountForSection(i);
                i++;
            }
            return this.count;
        }

        public final int getItemViewType(int i) {
            return getItemViewType(getSectionForPosition(i), getPositionInSectionForPosition(i));
        }

        public abstract int getItemViewType(int i, int i2);

        public int getPositionInSectionForPosition(int i) {
            int i2 = 0;
            Integer num = (Integer) this.sectionPositionCache.get(i);
            if (num != null) {
                return num.intValue();
            }
            int i3 = 0;
            while (i3 < internalGetSectionCount()) {
                int internalGetCountForSection = internalGetCountForSection(i3) + i2;
                if (i < i2 || i >= internalGetCountForSection) {
                    i3++;
                    i2 = internalGetCountForSection;
                } else {
                    i3 = i - i2;
                    this.sectionPositionCache.put(i, Integer.valueOf(i3));
                    return i3;
                }
            }
            return -1;
        }

        public abstract int getSectionCount();

        public final int getSectionForPosition(int i) {
            int i2 = 0;
            Integer num = (Integer) this.sectionCache.get(i);
            if (num != null) {
                return num.intValue();
            }
            int i3 = 0;
            while (i3 < internalGetSectionCount()) {
                int internalGetCountForSection = internalGetCountForSection(i3) + i2;
                if (i < i2 || i >= internalGetCountForSection) {
                    i3++;
                    i2 = internalGetCountForSection;
                } else {
                    this.sectionCache.put(i, Integer.valueOf(i3));
                    return i3;
                }
            }
            return -1;
        }

        public abstract View getSectionHeaderView(int i, View view);

        public abstract boolean isEnabled(int i, int i2);

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return isEnabled(getSectionForPosition(adapterPosition), getPositionInSectionForPosition(adapterPosition));
        }

        public void notifyDataSetChanged() {
            cleanupCache();
            super.notifyDataSetChanged();
        }

        public abstract void onBindViewHolder(int i, int i2, ViewHolder viewHolder);

        public final void onBindViewHolder(ViewHolder viewHolder, int i) {
            onBindViewHolder(getSectionForPosition(i), getPositionInSectionForPosition(i), viewHolder);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public interface OnItemLongClickListener {
        boolean onItemClick(View view, int i);
    }

    public interface OnItemClickListenerExtended {
        void onItemClick(View view, int i, float f, float f2);
    }

    /* renamed from: org.telegram.ui.Components.RecyclerListView$1 */
    class C45621 extends AdapterDataObserver {
        C45621() {
        }

        public void onChanged() {
            RecyclerListView.this.checkIfEmpty();
            RecyclerListView.this.selectorRect.setEmpty();
            RecyclerListView.this.invalidate();
        }

        public void onItemRangeInserted(int i, int i2) {
            RecyclerListView.this.checkIfEmpty();
        }

        public void onItemRangeRemoved(int i, int i2) {
            RecyclerListView.this.checkIfEmpty();
        }
    }

    /* renamed from: org.telegram.ui.Components.RecyclerListView$2 */
    class C45632 extends OnScrollListener {
        boolean scrollingByUser;

        C45632() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            boolean z = false;
            if (!(i == 0 || RecyclerListView.this.currentChildView == null)) {
                if (RecyclerListView.this.selectChildRunnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(RecyclerListView.this.selectChildRunnable);
                    RecyclerListView.this.selectChildRunnable = null;
                }
                MotionEvent obtain = MotionEvent.obtain(0, 0, 3, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
                try {
                    RecyclerListView.this.gestureDetector.onTouchEvent(obtain);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                RecyclerListView.this.currentChildView.onTouchEvent(obtain);
                obtain.recycle();
                View access$200 = RecyclerListView.this.currentChildView;
                RecyclerListView.this.onChildPressed(RecyclerListView.this.currentChildView, false);
                RecyclerListView.this.currentChildView = null;
                RecyclerListView.this.removeSelection(access$200, null);
                RecyclerListView.this.interceptedByChild = false;
            }
            if (RecyclerListView.this.onScrollListener != null) {
                RecyclerListView.this.onScrollListener.onScrollStateChanged(recyclerView, i);
            }
            if (i == 1 || i == 2) {
                z = true;
            }
            this.scrollingByUser = z;
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            if (RecyclerListView.this.onScrollListener != null) {
                RecyclerListView.this.onScrollListener.onScrolled(recyclerView, i, i2);
            }
            if (RecyclerListView.this.selectorPosition != -1) {
                RecyclerListView.this.selectorRect.offset(-i, -i2);
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
                        int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                        if (findFirstVisibleItemPosition != -1) {
                            if (this.scrollingByUser && RecyclerListView.this.fastScroll != null) {
                                Adapter adapter = RecyclerListView.this.getAdapter();
                                if (adapter instanceof FastScrollAdapter) {
                                    RecyclerListView.this.fastScroll.setProgress(((float) findFirstVisibleItemPosition) / ((float) adapter.getItemCount()));
                                }
                            }
                            if (RecyclerListView.this.sectionsAdapter == null) {
                                return;
                            }
                            int abs;
                            int countForSection;
                            View view;
                            if (RecyclerListView.this.sectionsType == 1) {
                                abs = Math.abs(linearLayoutManager.findLastVisibleItemPosition() - findFirstVisibleItemPosition) + 1;
                                RecyclerListView.this.headersCache.addAll(RecyclerListView.this.headers);
                                RecyclerListView.this.headers.clear();
                                if (RecyclerListView.this.sectionsAdapter.getItemCount() != 0) {
                                    if (!(RecyclerListView.this.currentFirst == findFirstVisibleItemPosition && RecyclerListView.this.currentVisible == abs)) {
                                        RecyclerListView.this.currentFirst = findFirstVisibleItemPosition;
                                        RecyclerListView.this.currentVisible = abs;
                                        RecyclerListView.this.sectionsCount = 1;
                                        RecyclerListView.this.startSection = RecyclerListView.this.sectionsAdapter.getSectionForPosition(findFirstVisibleItemPosition);
                                        countForSection = (RecyclerListView.this.sectionsAdapter.getCountForSection(RecyclerListView.this.startSection) + findFirstVisibleItemPosition) - RecyclerListView.this.sectionsAdapter.getPositionInSectionForPosition(findFirstVisibleItemPosition);
                                        while (countForSection < findFirstVisibleItemPosition + abs) {
                                            countForSection += RecyclerListView.this.sectionsAdapter.getCountForSection(RecyclerListView.this.startSection + RecyclerListView.this.sectionsCount);
                                            RecyclerListView.this.sectionsCount = RecyclerListView.this.sectionsCount + 1;
                                        }
                                    }
                                    int i3 = findFirstVisibleItemPosition;
                                    for (abs = RecyclerListView.this.startSection; abs < RecyclerListView.this.startSection + RecyclerListView.this.sectionsCount; abs++) {
                                        view = null;
                                        if (!RecyclerListView.this.headersCache.isEmpty()) {
                                            view = (View) RecyclerListView.this.headersCache.get(0);
                                            RecyclerListView.this.headersCache.remove(0);
                                        }
                                        View access$2800 = RecyclerListView.this.getSectionHeaderView(abs, view);
                                        RecyclerListView.this.headers.add(access$2800);
                                        int countForSection2 = RecyclerListView.this.sectionsAdapter.getCountForSection(abs);
                                        if (abs == RecyclerListView.this.startSection) {
                                            countForSection = RecyclerListView.this.sectionsAdapter.getPositionInSectionForPosition(i3);
                                            if (countForSection == countForSection2 - 1) {
                                                access$2800.setTag(Integer.valueOf(-access$2800.getHeight()));
                                            } else if (countForSection == countForSection2 - 2) {
                                                view = RecyclerListView.this.getChildAt(i3 - findFirstVisibleItemPosition);
                                                countForSection = view != null ? view.getTop() : -AndroidUtilities.dp(100.0f);
                                                if (countForSection < 0) {
                                                    access$2800.setTag(Integer.valueOf(countForSection));
                                                } else {
                                                    access$2800.setTag(Integer.valueOf(0));
                                                }
                                            } else {
                                                access$2800.setTag(Integer.valueOf(0));
                                            }
                                            i3 += countForSection2 - RecyclerListView.this.sectionsAdapter.getPositionInSectionForPosition(findFirstVisibleItemPosition);
                                        } else {
                                            view = RecyclerListView.this.getChildAt(i3 - findFirstVisibleItemPosition);
                                            if (view != null) {
                                                access$2800.setTag(Integer.valueOf(view.getTop()));
                                            } else {
                                                access$2800.setTag(Integer.valueOf(-AndroidUtilities.dp(100.0f)));
                                            }
                                            i3 += countForSection2;
                                        }
                                    }
                                }
                            } else if (RecyclerListView.this.sectionsType == 2 && RecyclerListView.this.sectionsAdapter.getItemCount() != 0) {
                                countForSection = RecyclerListView.this.sectionsAdapter.getSectionForPosition(findFirstVisibleItemPosition);
                                if (RecyclerListView.this.currentFirst != countForSection || RecyclerListView.this.pinnedHeader == null) {
                                    RecyclerListView.this.pinnedHeader = RecyclerListView.this.getSectionHeaderView(countForSection, RecyclerListView.this.pinnedHeader);
                                    RecyclerListView.this.currentFirst = countForSection;
                                }
                                if (RecyclerListView.this.sectionsAdapter.getPositionInSectionForPosition(findFirstVisibleItemPosition) == RecyclerListView.this.sectionsAdapter.getCountForSection(countForSection) - 1) {
                                    view = RecyclerListView.this.getChildAt(0);
                                    abs = RecyclerListView.this.pinnedHeader.getHeight();
                                    if (view != null) {
                                        countForSection = view.getHeight() + view.getTop();
                                        countForSection = countForSection < abs ? countForSection - abs : 0;
                                    } else {
                                        countForSection = -AndroidUtilities.dp(100.0f);
                                    }
                                    if (countForSection < 0) {
                                        RecyclerListView.this.pinnedHeader.setTag(Integer.valueOf(countForSection));
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
            for (int i = 0; i < 8; i++) {
                this.radii[i] = (float) AndroidUtilities.dp(44.0f);
            }
            this.scrollX = LocaleController.isRTL ? AndroidUtilities.dp(10.0f) : AndroidUtilities.dp(117.0f);
            updateColors();
        }

        private void getCurrentLetter() {
            LayoutManager layoutManager = RecyclerListView.this.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == 1) {
                    Adapter adapter = RecyclerListView.this.getAdapter();
                    if (adapter instanceof FastScrollAdapter) {
                        FastScrollAdapter fastScrollAdapter = (FastScrollAdapter) adapter;
                        int positionForScrollProgress = fastScrollAdapter.getPositionForScrollProgress(this.progress);
                        linearLayoutManager.scrollToPositionWithOffset(positionForScrollProgress, 0);
                        CharSequence letter = fastScrollAdapter.getLetter(positionForScrollProgress);
                        if (letter == null) {
                            if (this.letterLayout != null) {
                                this.oldLetterLayout = this.letterLayout;
                            }
                            this.letterLayout = null;
                        } else if (!letter.equals(this.currentLetter)) {
                            this.letterLayout = new StaticLayout(letter, this.letterPaint, 1000, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                            this.oldLetterLayout = null;
                            if (this.letterLayout.getLineCount() > 0) {
                                this.letterLayout.getLineWidth(0);
                                this.letterLayout.getLineLeft(0);
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

        private void setProgress(float f) {
            this.progress = f;
            invalidate();
        }

        private void updateColors() {
            int color = Theme.getColor(Theme.key_fastScrollInactive);
            int color2 = Theme.getColor(Theme.key_fastScrollActive);
            this.paint.setColor(color);
            this.letterPaint.setColor(Theme.getColor(Theme.key_fastScrollText));
            this.colors[0] = Color.red(color);
            this.colors[1] = Color.red(color2);
            this.colors[2] = Color.green(color);
            this.colors[3] = Color.green(color2);
            this.colors[4] = Color.blue(color);
            this.colors[5] = Color.blue(color2);
            invalidate();
        }

        public void layout(int i, int i2, int i3, int i4) {
            if (RecyclerListView.this.selfOnLayout) {
                super.layout(i, i2, i3, i4);
            }
        }

        protected void onDraw(Canvas canvas) {
            this.paint.setColor(Color.argb(255, this.colors[0] + ((int) (((float) (this.colors[1] - this.colors[0])) * this.bubbleProgress)), this.colors[2] + ((int) (((float) (this.colors[3] - this.colors[2])) * this.bubbleProgress)), this.colors[4] + ((int) (((float) (this.colors[5] - this.colors[4])) * this.bubbleProgress))));
            int ceil = (int) Math.ceil((double) (((float) (getMeasuredHeight() - AndroidUtilities.dp(54.0f))) * this.progress));
            this.rect.set((float) this.scrollX, (float) (AndroidUtilities.dp(12.0f) + ceil), (float) (this.scrollX + AndroidUtilities.dp(5.0f)), (float) (AndroidUtilities.dp(42.0f) + ceil));
            canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), this.paint);
            if (this.pressed || this.bubbleProgress != BitmapDescriptorFactory.HUE_RED) {
                float dp;
                int dp2;
                float dp3;
                this.paint.setAlpha((int) (255.0f * this.bubbleProgress));
                int dp4 = ceil + AndroidUtilities.dp(30.0f);
                ceil -= AndroidUtilities.dp(46.0f);
                if (ceil <= AndroidUtilities.dp(12.0f)) {
                    dp = (float) (AndroidUtilities.dp(12.0f) - ceil);
                    dp2 = AndroidUtilities.dp(12.0f);
                } else {
                    dp2 = ceil;
                    dp = BitmapDescriptorFactory.HUE_RED;
                }
                canvas.translate((float) AndroidUtilities.dp(10.0f), (float) dp2);
                if (dp <= ((float) AndroidUtilities.dp(29.0f))) {
                    dp3 = (float) AndroidUtilities.dp(44.0f);
                    dp = ((dp / ((float) AndroidUtilities.dp(29.0f))) * ((float) AndroidUtilities.dp(40.0f))) + ((float) AndroidUtilities.dp(4.0f));
                } else {
                    dp3 = dp - ((float) AndroidUtilities.dp(29.0f));
                    dp = (float) AndroidUtilities.dp(44.0f);
                    dp3 = ((1.0f - (dp3 / ((float) AndroidUtilities.dp(29.0f)))) * ((float) AndroidUtilities.dp(40.0f))) + ((float) AndroidUtilities.dp(4.0f));
                }
                if ((LocaleController.isRTL && !(this.radii[0] == dp3 && this.radii[6] == dp)) || !(LocaleController.isRTL || (this.radii[2] == dp3 && this.radii[4] == dp))) {
                    float[] fArr;
                    float[] fArr2;
                    if (LocaleController.isRTL) {
                        fArr = this.radii;
                        this.radii[1] = dp3;
                        fArr[0] = dp3;
                        fArr2 = this.radii;
                        this.radii[7] = dp;
                        fArr2[6] = dp;
                    } else {
                        fArr = this.radii;
                        this.radii[3] = dp3;
                        fArr[2] = dp3;
                        fArr2 = this.radii;
                        this.radii[5] = dp;
                        fArr2[4] = dp;
                    }
                    this.path.reset();
                    this.rect.set(LocaleController.isRTL ? (float) AndroidUtilities.dp(10.0f) : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(LocaleController.isRTL ? 98.0f : 88.0f), (float) AndroidUtilities.dp(88.0f));
                    this.path.addRoundRect(this.rect, this.radii, Direction.CW);
                    this.path.close();
                }
                StaticLayout staticLayout = this.letterLayout != null ? this.letterLayout : this.oldLetterLayout;
                if (staticLayout != null) {
                    canvas.save();
                    canvas.scale(this.bubbleProgress, this.bubbleProgress, (float) this.scrollX, (float) (dp4 - dp2));
                    canvas.drawPath(this.path, this.paint);
                    canvas.translate(this.textX, this.textY);
                    staticLayout.draw(canvas);
                    canvas.restore();
                }
            }
            if ((this.pressed && this.letterLayout != null && this.bubbleProgress < 1.0f) || ((!this.pressed || this.letterLayout == null) && this.bubbleProgress > BitmapDescriptorFactory.HUE_RED)) {
                long currentTimeMillis = System.currentTimeMillis();
                long j = currentTimeMillis - this.lastUpdateTime;
                if (j < 0 || j > 17) {
                    j = 17;
                }
                this.lastUpdateTime = currentTimeMillis;
                invalidate();
                if (!this.pressed || this.letterLayout == null) {
                    this.bubbleProgress -= ((float) j) / 120.0f;
                    if (this.bubbleProgress < BitmapDescriptorFactory.HUE_RED) {
                        this.bubbleProgress = BitmapDescriptorFactory.HUE_RED;
                        return;
                    }
                    return;
                }
                this.bubbleProgress += ((float) j) / 120.0f;
                if (this.bubbleProgress > 1.0f) {
                    this.bubbleProgress = 1.0f;
                }
            }
        }

        protected void onMeasure(int i, int i2) {
            setMeasuredDimension(AndroidUtilities.dp(132.0f), MeasureSpec.getSize(i2));
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            float x;
            float ceil;
            switch (motionEvent.getAction()) {
                case 0:
                    x = motionEvent.getX();
                    this.lastY = motionEvent.getY();
                    ceil = ((float) Math.ceil((double) (((float) (getMeasuredHeight() - AndroidUtilities.dp(54.0f))) * this.progress))) + ((float) AndroidUtilities.dp(12.0f));
                    if ((LocaleController.isRTL && x > ((float) AndroidUtilities.dp(25.0f))) || ((!LocaleController.isRTL && x < ((float) AndroidUtilities.dp(107.0f))) || this.lastY < ceil || this.lastY > ((float) AndroidUtilities.dp(30.0f)) + ceil)) {
                        return false;
                    }
                    this.startDy = this.lastY - ceil;
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
                    float y = motionEvent.getY();
                    x = ((float) AndroidUtilities.dp(12.0f)) + this.startDy;
                    ceil = ((float) (getMeasuredHeight() - AndroidUtilities.dp(42.0f))) + this.startDy;
                    if (y >= x) {
                        x = y > ceil ? ceil : y;
                    }
                    ceil = x - this.lastY;
                    this.lastY = x;
                    this.progress += ceil / ((float) (getMeasuredHeight() - AndroidUtilities.dp(54.0f)));
                    if (this.progress < BitmapDescriptorFactory.HUE_RED) {
                        this.progress = BitmapDescriptorFactory.HUE_RED;
                    } else if (this.progress > 1.0f) {
                        this.progress = 1.0f;
                    }
                    getCurrentLetter();
                    invalidate();
                    return true;
                default:
                    return super.onTouchEvent(motionEvent);
            }
        }
    }

    public static class Holder extends ViewHolder {
        public Holder(View view) {
            super(view);
        }
    }

    public interface OnInterceptTouchListener {
        boolean onInterceptTouchEvent(MotionEvent motionEvent);
    }

    private class RecyclerListViewItemClickListener implements OnItemTouchListener {

        /* renamed from: org.telegram.ui.Components.RecyclerListView$RecyclerListViewItemClickListener$2 */
        class C45662 implements Runnable {
            C45662() {
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
                public void onLongPress(MotionEvent motionEvent) {
                    if (RecyclerListView.this.currentChildView != null) {
                        View access$200 = RecyclerListView.this.currentChildView;
                        if (RecyclerListView.this.onItemLongClickListener != null && RecyclerListView.this.currentChildPosition != -1 && RecyclerListView.this.onItemLongClickListener.onItemClick(RecyclerListView.this.currentChildView, RecyclerListView.this.currentChildPosition)) {
                            access$200.performHapticFeedback(0);
                        }
                    }
                }

                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    if (!(RecyclerListView.this.currentChildView == null || (RecyclerListView.this.onItemClickListener == null && RecyclerListView.this.onItemClickListenerExtended == null))) {
                        RecyclerListView.this.onChildPressed(RecyclerListView.this.currentChildView, true);
                        final View access$200 = RecyclerListView.this.currentChildView;
                        final int access$400 = RecyclerListView.this.currentChildPosition;
                        final float x = motionEvent.getX();
                        final float y = motionEvent.getY();
                        if (RecyclerListView.this.instantClick && access$400 != -1) {
                            access$200.playSoundEffect(0);
                            if (RecyclerListView.this.onItemClickListener != null) {
                                RecyclerListView.this.onItemClickListener.onItemClick(access$200, access$400);
                            } else if (RecyclerListView.this.onItemClickListenerExtended != null) {
                                RecyclerListView.this.onItemClickListenerExtended.onItemClick(access$200, access$400, x, y);
                            }
                        }
                        AndroidUtilities.runOnUIThread(RecyclerListView.this.clickRunnable = new Runnable() {
                            public void run() {
                                if (this == RecyclerListView.this.clickRunnable) {
                                    RecyclerListView.this.clickRunnable = null;
                                }
                                if (access$200 != null) {
                                    RecyclerListView.this.onChildPressed(access$200, false);
                                    if (!RecyclerListView.this.instantClick) {
                                        access$200.playSoundEffect(0);
                                        if (access$400 == -1) {
                                            return;
                                        }
                                        if (RecyclerListView.this.onItemClickListener != null) {
                                            RecyclerListView.this.onItemClickListener.onItemClick(access$200, access$400);
                                        } else if (RecyclerListView.this.onItemClickListenerExtended != null) {
                                            RecyclerListView.this.onItemClickListenerExtended.onItemClick(access$200, access$400, x, y);
                                        }
                                    }
                                }
                            }
                        }, (long) ViewConfiguration.getPressedStateDuration());
                        if (RecyclerListView.this.selectChildRunnable != null) {
                            View access$2002 = RecyclerListView.this.currentChildView;
                            AndroidUtilities.cancelRunOnUIThread(RecyclerListView.this.selectChildRunnable);
                            RecyclerListView.this.selectChildRunnable = null;
                            RecyclerListView.this.currentChildView = null;
                            RecyclerListView.this.interceptedByChild = false;
                            RecyclerListView.this.removeSelection(access$2002, motionEvent);
                        }
                    }
                    return true;
                }
            });
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            Object obj = RecyclerListView.this.getScrollState() == 0 ? 1 : null;
            if ((actionMasked == 0 || actionMasked == 5) && RecyclerListView.this.currentChildView == null && obj != null) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if (RecyclerListView.this.allowSelectChildAtPosition(x, y)) {
                    RecyclerListView.this.currentChildView = recyclerView.findChildViewUnder(x, y);
                }
                if (RecyclerListView.this.currentChildView instanceof ViewGroup) {
                    float x2 = motionEvent.getX() - ((float) RecyclerListView.this.currentChildView.getLeft());
                    float y2 = motionEvent.getY() - ((float) RecyclerListView.this.currentChildView.getTop());
                    ViewGroup viewGroup = (ViewGroup) RecyclerListView.this.currentChildView;
                    for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                        View childAt = viewGroup.getChildAt(childCount);
                        if (x2 >= ((float) childAt.getLeft()) && x2 <= ((float) childAt.getRight()) && y2 >= ((float) childAt.getTop()) && y2 <= ((float) childAt.getBottom()) && childAt.isClickable()) {
                            RecyclerListView.this.currentChildView = null;
                            break;
                        }
                    }
                }
                RecyclerListView.this.currentChildPosition = -1;
                if (RecyclerListView.this.currentChildView != null) {
                    RecyclerListView.this.currentChildPosition = recyclerView.getChildPosition(RecyclerListView.this.currentChildView);
                    MotionEvent obtain = MotionEvent.obtain(0, 0, motionEvent.getActionMasked(), motionEvent.getX() - ((float) RecyclerListView.this.currentChildView.getLeft()), motionEvent.getY() - ((float) RecyclerListView.this.currentChildView.getTop()), 0);
                    if (RecyclerListView.this.currentChildView.onTouchEvent(obtain)) {
                        RecyclerListView.this.interceptedByChild = true;
                    }
                    obtain.recycle();
                }
            }
            if (!(RecyclerListView.this.currentChildView == null || RecyclerListView.this.interceptedByChild || motionEvent == null)) {
                try {
                    RecyclerListView.this.gestureDetector.onTouchEvent(motionEvent);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
            if (actionMasked == 0 || actionMasked == 5) {
                if (!(RecyclerListView.this.interceptedByChild || RecyclerListView.this.currentChildView == null)) {
                    RecyclerListView.this.selectChildRunnable = new C45662();
                    AndroidUtilities.runOnUIThread(RecyclerListView.this.selectChildRunnable, (long) ViewConfiguration.getTapTimeout());
                    if (RecyclerListView.this.currentChildView.isEnabled()) {
                        RecyclerListView.this.positionSelector(RecyclerListView.this.currentChildPosition, RecyclerListView.this.currentChildView);
                        if (RecyclerListView.this.selectorDrawable != null) {
                            Drawable current = RecyclerListView.this.selectorDrawable.getCurrent();
                            if (current != null && (current instanceof TransitionDrawable)) {
                                if (RecyclerListView.this.onItemLongClickListener != null) {
                                    ((TransitionDrawable) current).startTransition(ViewConfiguration.getLongPressTimeout());
                                } else {
                                    ((TransitionDrawable) current).resetTransition();
                                }
                            }
                            if (VERSION.SDK_INT >= 21) {
                                RecyclerListView.this.selectorDrawable.setHotspot(motionEvent.getX(), motionEvent.getY());
                            }
                        }
                        RecyclerListView.this.updateSelectorState();
                    } else {
                        RecyclerListView.this.selectorRect.setEmpty();
                    }
                }
            } else if ((actionMasked == 1 || actionMasked == 6 || actionMasked == 3 || obj == null) && RecyclerListView.this.currentChildView != null) {
                if (RecyclerListView.this.selectChildRunnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(RecyclerListView.this.selectChildRunnable);
                    RecyclerListView.this.selectChildRunnable = null;
                }
                View access$200 = RecyclerListView.this.currentChildView;
                RecyclerListView.this.onChildPressed(RecyclerListView.this.currentChildView, false);
                RecyclerListView.this.currentChildView = null;
                RecyclerListView.this.interceptedByChild = false;
                RecyclerListView.this.removeSelection(access$200, motionEvent);
            }
            return false;
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            RecyclerListView.this.cancelClickRunnables(true);
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }
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
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributes);
            View.class.getDeclaredMethod("initializeScrollbars", new Class[]{TypedArray.class}).invoke(this, new Object[]{obtainStyledAttributes});
            obtainStyledAttributes.recycle();
        } catch (Throwable th) {
            FileLog.e(th);
        }
        super.setOnScrollListener(new C45632());
        addOnItemTouchListener(new RecyclerListViewItemClickListener(context));
    }

    private void checkIfEmpty() {
        int i = 0;
        if (getAdapter() != null && this.emptyView != null) {
            boolean z = getAdapter().getItemCount() == 0;
            this.emptyView.setVisibility(z ? 0 : 8);
            if (z) {
                i = 4;
            }
            setVisibility(i);
            this.hiddenByEmptyView = true;
        } else if (this.hiddenByEmptyView && getVisibility() != 0) {
            setVisibility(0);
            this.hiddenByEmptyView = false;
        }
    }

    private void ensurePinnedHeaderLayout(View view, boolean z) {
        if (view.isLayoutRequested() || z) {
            if (this.sectionsType == 1) {
                LayoutParams layoutParams = view.getLayoutParams();
                try {
                    view.measure(MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824), MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824));
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            } else if (this.sectionsType == 2) {
                try {
                    view.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
    }

    private int[] getDrawableStateForSelector() {
        int[] onCreateDrawableState = onCreateDrawableState(1);
        onCreateDrawableState[onCreateDrawableState.length - 1] = 16842919;
        return onCreateDrawableState;
    }

    private View getSectionHeaderView(int i, View view) {
        boolean z = view == null;
        View sectionHeaderView = this.sectionsAdapter.getSectionHeaderView(i, view);
        if (z) {
            ensurePinnedHeaderLayout(sectionHeaderView, false);
        }
        return sectionHeaderView;
    }

    private void positionSelector(int i, View view) {
        positionSelector(i, view, false, -1.0f, -1.0f);
    }

    private void positionSelector(int i, View view, boolean z, float f, float f2) {
        if (this.selectorDrawable != null) {
            boolean z2 = i != this.selectorPosition;
            if (i != -1) {
                this.selectorPosition = i;
            }
            this.selectorRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            boolean isEnabled = view.isEnabled();
            if (this.isChildViewEnabled != isEnabled) {
                this.isChildViewEnabled = isEnabled;
            }
            if (z2) {
                this.selectorDrawable.setVisible(false, false);
                this.selectorDrawable.setState(StateSet.NOTHING);
            }
            this.selectorDrawable.setBounds(this.selectorRect);
            if (z2 && getVisibility() == 0) {
                this.selectorDrawable.setVisible(true, false);
            }
            if (VERSION.SDK_INT >= 21 && z) {
                this.selectorDrawable.setHotspot(f, f2);
            }
        }
    }

    private void removeSelection(View view, MotionEvent motionEvent) {
        if (view != null) {
            if (view == null || !view.isEnabled()) {
                this.selectorRect.setEmpty();
            } else {
                positionSelector(this.currentChildPosition, view);
                if (this.selectorDrawable != null) {
                    Drawable current = this.selectorDrawable.getCurrent();
                    if (current != null && (current instanceof TransitionDrawable)) {
                        ((TransitionDrawable) current).resetTransition();
                    }
                    if (motionEvent != null && VERSION.SDK_INT >= 21) {
                        this.selectorDrawable.setHotspot(motionEvent.getX(), motionEvent.getY());
                    }
                }
            }
            updateSelectorState();
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

    protected boolean allowSelectChildAtPosition(float f, float f2) {
        return true;
    }

    public boolean canScrollVertically(int i) {
        return this.scrollEnabled && super.canScrollVertically(i);
    }

    public void cancelClickRunnables(boolean z) {
        if (this.selectChildRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.selectChildRunnable);
            this.selectChildRunnable = null;
        }
        if (this.currentChildView != null) {
            View view = this.currentChildView;
            if (z) {
                onChildPressed(this.currentChildView, false);
            }
            this.currentChildView = null;
            removeSelection(view, null);
        }
        if (this.clickRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.clickRunnable);
            this.clickRunnable = null;
        }
        this.interceptedByChild = false;
    }

    protected void dispatchDraw(Canvas canvas) {
        float f = BitmapDescriptorFactory.HUE_RED;
        super.dispatchDraw(canvas);
        if (this.sectionsType == 1) {
            if (this.sectionsAdapter != null && !this.headers.isEmpty()) {
                for (int i = 0; i < this.headers.size(); i++) {
                    View view = (View) this.headers.get(i);
                    int save = canvas.save();
                    canvas.translate(LocaleController.isRTL ? (float) (getWidth() - view.getWidth()) : BitmapDescriptorFactory.HUE_RED, (float) ((Integer) view.getTag()).intValue());
                    canvas.clipRect(0, 0, getWidth(), view.getMeasuredHeight());
                    view.draw(canvas);
                    canvas.restoreToCount(save);
                }
            } else {
                return;
            }
        } else if (this.sectionsType == 2) {
            if (this.sectionsAdapter != null && this.pinnedHeader != null) {
                int save2 = canvas.save();
                int intValue = ((Integer) this.pinnedHeader.getTag()).intValue();
                if (LocaleController.isRTL) {
                    f = (float) (getWidth() - this.pinnedHeader.getWidth());
                }
                canvas.translate(f, (float) intValue);
                canvas.clipRect(0, 0, getWidth(), this.pinnedHeader.getMeasuredHeight());
                this.pinnedHeader.draw(canvas);
                canvas.restoreToCount(save2);
            } else {
                return;
            }
        }
        if (!this.selectorRect.isEmpty()) {
            this.selectorDrawable.setBounds(this.selectorRect);
            this.selectorDrawable.draw(canvas);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateSelectorState();
    }

    public View getEmptyView() {
        return this.emptyView;
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

    public int[] getResourceDeclareStyleableIntArray(String str, String str2) {
        try {
            Field field = Class.forName(str + ".R$styleable").getField(str2);
            if (field != null) {
                return (int[]) field.get(null);
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public void invalidateViews() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).invalidate();
        }
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
            ViewGroup viewGroup = (ViewGroup) this.fastScroll.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.fastScroll);
            }
            ((ViewGroup) getParent()).addView(this.fastScroll);
        }
    }

    public void onChildAttachedToWindow(View view) {
        if (getAdapter() instanceof SelectionAdapter) {
            ViewHolder findContainingViewHolder = findContainingViewHolder(view);
            if (findContainingViewHolder != null) {
                view.setEnabled(((SelectionAdapter) getAdapter()).isEnabled(findContainingViewHolder));
            }
        } else {
            view.setEnabled(false);
        }
        super.onChildAttachedToWindow(view);
    }

    protected void onChildPressed(View view, boolean z) {
        view.setPressed(z);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.selectorPosition = -1;
        this.selectorRect.setEmpty();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        if (this.disallowInterceptTouchEvents) {
            requestDisallowInterceptTouchEvent(true);
        }
        return (this.onInterceptTouchListener != null && this.onInterceptTouchListener.onInterceptTouchEvent(motionEvent)) || super.onInterceptTouchEvent(motionEvent);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.fastScroll != null) {
            this.selfOnLayout = true;
            if (LocaleController.isRTL) {
                this.fastScroll.layout(0, i2, this.fastScroll.getMeasuredWidth(), this.fastScroll.getMeasuredHeight() + i2);
            } else {
                int measuredWidth = getMeasuredWidth() - this.fastScroll.getMeasuredWidth();
                this.fastScroll.layout(measuredWidth, i2, this.fastScroll.getMeasuredWidth() + measuredWidth, this.fastScroll.getMeasuredHeight() + i2);
            }
            this.selfOnLayout = false;
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.fastScroll != null) {
            this.fastScroll.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(132.0f), 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.sectionsType == 1) {
            if (this.sectionsAdapter != null && !this.headers.isEmpty()) {
                for (int i5 = 0; i5 < this.headers.size(); i5++) {
                    ensurePinnedHeaderLayout((View) this.headers.get(i5), true);
                }
            }
        } else if (this.sectionsType == 2 && this.sectionsAdapter != null && this.pinnedHeader != null) {
            ensurePinnedHeaderLayout(this.pinnedHeader, true);
        }
    }

    public void setAdapter(Adapter adapter) {
        Adapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.unregisterAdapterDataObserver(this.observer);
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

    public void setDisallowInterceptTouchEvents(boolean z) {
        this.disallowInterceptTouchEvents = z;
    }

    public void setEmptyView(View view) {
        if (this.emptyView != view) {
            this.emptyView = view;
            checkIfEmpty();
        }
    }

    public void setFastScrollEnabled() {
        this.fastScroll = new FastScroll(getContext());
        if (getParent() != null) {
            ((ViewGroup) getParent()).addView(this.fastScroll);
        }
    }

    public void setFastScrollVisible(boolean z) {
        if (this.fastScroll != null) {
            this.fastScroll.setVisibility(z ? 0 : 8);
        }
    }

    public void setInstantClick(boolean z) {
        this.instantClick = z;
    }

    public void setListSelectorColor(int i) {
        Theme.setSelectorDrawableColor(this.selectorDrawable, i, true);
    }

    public void setOnInterceptTouchListener(OnInterceptTouchListener onInterceptTouchListener) {
        this.onInterceptTouchListener = onInterceptTouchListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListenerExtended onItemClickListenerExtended) {
        this.onItemClickListenerExtended = onItemClickListenerExtended;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setScrollEnabled(boolean z) {
        this.scrollEnabled = z;
    }

    public void setSectionsType(int i) {
        this.sectionsType = i;
        if (this.sectionsType == 1) {
            this.headers = new ArrayList();
            this.headersCache = new ArrayList();
        }
    }

    public void setVerticalScrollBarEnabled(boolean z) {
        if (attributes != null) {
            super.setVerticalScrollBarEnabled(z);
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i != 0) {
            this.hiddenByEmptyView = false;
        }
    }

    public void stopScroll() {
        try {
            super.stopScroll();
        } catch (NullPointerException e) {
        }
    }

    public void updateFastScrollColors() {
        if (this.fastScroll != null) {
            this.fastScroll.updateColors();
        }
    }

    public boolean verifyDrawable(Drawable drawable) {
        return this.selectorDrawable == drawable || super.verifyDrawable(drawable);
    }
}
