package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.content.C0235a;
import android.support.v4.p014d.C0085g;
import android.support.v4.p014d.C0437f;
import android.support.v4.view.p023a.C0510a;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.view.p023a.C0556o;
import android.support.v4.widget.C0700i;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.telegram.messenger.MessagesController;

public class ViewPager extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<C0491b> COMPARATOR = new C04851();
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    static final int[] LAYOUT_ATTRS = new int[]{16842931};
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = false;
    private static final Interpolator sInterpolator = new C04862();
    private static final C0496i sPositionComparator = new C0496i();
    private int mActivePointerId = -1;
    aa mAdapter;
    private List<C0180e> mAdapterChangeListeners;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable = new C04873(this);
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout = true;
    private float mFirstOffset = -3.4028235E38f;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private C0188f mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsScrollStarted;
    private boolean mIsUnableToDrag;
    private final ArrayList<C0491b> mItems = new ArrayList();
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset = Float.MAX_VALUE;
    private C0700i mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets = false;
    private C0495h mObserver;
    private int mOffscreenPageLimit = 1;
    private C0188f mOnPageChangeListener;
    private List<C0188f> mOnPageChangeListeners;
    private int mPageMargin;
    private C0494g mPageTransformer;
    private int mPageTransformerLayerType;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private int mRestoredCurItem = -1;
    private C0700i mRightEdge;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private Method mSetChildrenDrawingOrderEnabled;
    private final C0491b mTempItem = new C0491b();
    private final Rect mTempRect = new Rect();
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    /* renamed from: android.support.v4.view.ViewPager$e */
    public interface C0180e {
        /* renamed from: a */
        void mo164a(ViewPager viewPager, aa aaVar, aa aaVar2);
    }

    /* renamed from: android.support.v4.view.ViewPager$f */
    public interface C0188f {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    /* renamed from: android.support.v4.view.ViewPager$1 */
    static class C04851 implements Comparator<C0491b> {
        C04851() {
        }

        /* renamed from: a */
        public int m2053a(C0491b c0491b, C0491b c0491b2) {
            return c0491b.f1281b - c0491b2.f1281b;
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m2053a((C0491b) obj, (C0491b) obj2);
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$2 */
    static class C04862 implements Interpolator {
        C04862() {
        }

        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * (((f2 * f2) * f2) * f2)) + 1.0f;
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$3 */
    class C04873 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ ViewPager f1274a;

        C04873(ViewPager viewPager) {
            this.f1274a = viewPager;
        }

        public void run() {
            this.f1274a.setScrollState(0);
            this.f1274a.populate();
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$4 */
    class C04884 implements C0081z {
        /* renamed from: a */
        final /* synthetic */ ViewPager f1275a;
        /* renamed from: b */
        private final Rect f1276b = new Rect();

        C04884(ViewPager viewPager) {
            this.f1275a = viewPager;
        }

        /* renamed from: a */
        public be mo57a(View view, be beVar) {
            be a = ah.m2774a(view, beVar);
            if (a.m3081e()) {
                return a;
            }
            Rect rect = this.f1276b;
            rect.left = a.m3076a();
            rect.top = a.m3078b();
            rect.right = a.m3079c();
            rect.bottom = a.m3080d();
            int childCount = this.f1275a.getChildCount();
            for (int i = 0; i < childCount; i++) {
                be b = ah.m2794b(this.f1275a.getChildAt(i), a);
                rect.left = Math.min(b.m3076a(), rect.left);
                rect.top = Math.min(b.m3078b(), rect.top);
                rect.right = Math.min(b.m3079c(), rect.right);
                rect.bottom = Math.min(b.m3080d(), rect.bottom);
            }
            return a.m3077a(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = C0437f.m1919a(new C04891());
        /* renamed from: a */
        int f1277a;
        /* renamed from: b */
        Parcelable f1278b;
        /* renamed from: c */
        ClassLoader f1279c;

        /* renamed from: android.support.v4.view.ViewPager$SavedState$1 */
        static class C04891 implements C0085g<SavedState> {
            C04891() {
            }

            /* renamed from: a */
            public SavedState m2055a(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState[] m2056a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return m2055a(parcel, classLoader);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m2056a(i);
            }
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                classLoader = getClass().getClassLoader();
            }
            this.f1277a = parcel.readInt();
            this.f1278b = parcel.readParcelable(classLoader);
            this.f1279c = classLoader;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.f1277a + "}";
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f1277a);
            parcel.writeParcelable(this.f1278b, i);
        }
    }

    @Inherited
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* renamed from: android.support.v4.view.ViewPager$a */
    public @interface C0490a {
    }

    /* renamed from: android.support.v4.view.ViewPager$b */
    static class C0491b {
        /* renamed from: a */
        Object f1280a;
        /* renamed from: b */
        int f1281b;
        /* renamed from: c */
        boolean f1282c;
        /* renamed from: d */
        float f1283d;
        /* renamed from: e */
        float f1284e;

        C0491b() {
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$c */
    public static class C0492c extends LayoutParams {
        /* renamed from: a */
        public boolean f1285a;
        /* renamed from: b */
        public int f1286b;
        /* renamed from: c */
        float f1287c = BitmapDescriptorFactory.HUE_RED;
        /* renamed from: d */
        boolean f1288d;
        /* renamed from: e */
        int f1289e;
        /* renamed from: f */
        int f1290f;

        public C0492c() {
            super(-1, -1);
        }

        public C0492c(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.LAYOUT_ATTRS);
            this.f1286b = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$d */
    class C0493d extends C0074a {
        /* renamed from: a */
        final /* synthetic */ ViewPager f1291a;

        C0493d(ViewPager viewPager) {
            this.f1291a = viewPager;
        }

        /* renamed from: a */
        private boolean m2057a() {
            return this.f1291a.mAdapter != null && this.f1291a.mAdapter.getCount() > 1;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(ViewPager.class.getName());
            C0556o a = C0510a.m2132a(accessibilityEvent);
            a.m2485d(m2057a());
            if (accessibilityEvent.getEventType() == 4096 && this.f1291a.mAdapter != null) {
                a.m2475a(this.f1291a.mAdapter.getCount());
                a.m2479b(this.f1291a.mCurItem);
                a.m2482c(this.f1291a.mCurItem);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
            super.onInitializeAccessibilityNodeInfo(view, c0531e);
            c0531e.m2313b(ViewPager.class.getName());
            c0531e.m2336i(m2057a());
            if (this.f1291a.canScrollHorizontally(1)) {
                c0531e.m2305a(4096);
            }
            if (this.f1291a.canScrollHorizontally(-1)) {
                c0531e.m2305a((int) MessagesController.UPDATE_MASK_CHANNEL);
            }
        }

        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            switch (i) {
                case 4096:
                    if (!this.f1291a.canScrollHorizontally(1)) {
                        return false;
                    }
                    this.f1291a.setCurrentItem(this.f1291a.mCurItem + 1);
                    return true;
                case MessagesController.UPDATE_MASK_CHANNEL /*8192*/:
                    if (!this.f1291a.canScrollHorizontally(-1)) {
                        return false;
                    }
                    this.f1291a.setCurrentItem(this.f1291a.mCurItem - 1);
                    return true;
                default:
                    return false;
            }
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$g */
    public interface C0494g {
        /* renamed from: a */
        void mo3498a(View view, float f);
    }

    /* renamed from: android.support.v4.view.ViewPager$h */
    private class C0495h extends DataSetObserver {
        /* renamed from: a */
        final /* synthetic */ ViewPager f1292a;

        C0495h(ViewPager viewPager) {
            this.f1292a = viewPager;
        }

        public void onChanged() {
            this.f1292a.dataSetChanged();
        }

        public void onInvalidated() {
            this.f1292a.dataSetChanged();
        }
    }

    /* renamed from: android.support.v4.view.ViewPager$i */
    static class C0496i implements Comparator<View> {
        C0496i() {
        }

        /* renamed from: a */
        public int m2059a(View view, View view2) {
            C0492c c0492c = (C0492c) view.getLayoutParams();
            C0492c c0492c2 = (C0492c) view2.getLayoutParams();
            return c0492c.f1285a != c0492c2.f1285a ? c0492c.f1285a ? 1 : -1 : c0492c.f1289e - c0492c2.f1289e;
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m2059a((View) obj, (View) obj2);
        }
    }

    public ViewPager(Context context) {
        super(context);
        initViewPager();
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViewPager();
    }

    private void calculatePageOffsets(C0491b c0491b, int i, C0491b c0491b2) {
        float f;
        int i2;
        C0491b c0491b3;
        int i3;
        int count = this.mAdapter.getCount();
        int clientWidth = getClientWidth();
        float f2 = clientWidth > 0 ? ((float) this.mPageMargin) / ((float) clientWidth) : BitmapDescriptorFactory.HUE_RED;
        if (c0491b2 != null) {
            clientWidth = c0491b2.f1281b;
            int i4;
            if (clientWidth < c0491b.f1281b) {
                f = (c0491b2.f1284e + c0491b2.f1283d) + f2;
                i4 = clientWidth + 1;
                i2 = 0;
                while (i4 <= c0491b.f1281b && i2 < this.mItems.size()) {
                    c0491b3 = (C0491b) this.mItems.get(i2);
                    while (i4 > c0491b3.f1281b && i2 < this.mItems.size() - 1) {
                        i2++;
                        c0491b3 = (C0491b) this.mItems.get(i2);
                    }
                    while (i4 < c0491b3.f1281b) {
                        f += this.mAdapter.getPageWidth(i4) + f2;
                        i4++;
                    }
                    c0491b3.f1284e = f;
                    f += c0491b3.f1283d + f2;
                    i4++;
                }
            } else if (clientWidth > c0491b.f1281b) {
                i2 = this.mItems.size() - 1;
                f = c0491b2.f1284e;
                i4 = clientWidth - 1;
                while (i4 >= c0491b.f1281b && i2 >= 0) {
                    c0491b3 = (C0491b) this.mItems.get(i2);
                    while (i4 < c0491b3.f1281b && i2 > 0) {
                        i2--;
                        c0491b3 = (C0491b) this.mItems.get(i2);
                    }
                    while (i4 > c0491b3.f1281b) {
                        f -= this.mAdapter.getPageWidth(i4) + f2;
                        i4--;
                    }
                    f -= c0491b3.f1283d + f2;
                    c0491b3.f1284e = f;
                    i4--;
                }
            }
        }
        int size = this.mItems.size();
        float f3 = c0491b.f1284e;
        i2 = c0491b.f1281b - 1;
        this.mFirstOffset = c0491b.f1281b == 0 ? c0491b.f1284e : -3.4028235E38f;
        this.mLastOffset = c0491b.f1281b == count + -1 ? (c0491b.f1284e + c0491b.f1283d) - 1.0f : Float.MAX_VALUE;
        for (i3 = i - 1; i3 >= 0; i3--) {
            c0491b3 = (C0491b) this.mItems.get(i3);
            f = f3;
            while (i2 > c0491b3.f1281b) {
                f -= this.mAdapter.getPageWidth(i2) + f2;
                i2--;
            }
            f3 = f - (c0491b3.f1283d + f2);
            c0491b3.f1284e = f3;
            if (c0491b3.f1281b == 0) {
                this.mFirstOffset = f3;
            }
            i2--;
        }
        f3 = (c0491b.f1284e + c0491b.f1283d) + f2;
        i2 = c0491b.f1281b + 1;
        for (i3 = i + 1; i3 < size; i3++) {
            c0491b3 = (C0491b) this.mItems.get(i3);
            f = f3;
            while (i2 < c0491b3.f1281b) {
                f = (this.mAdapter.getPageWidth(i2) + f2) + f;
                i2++;
            }
            if (c0491b3.f1281b == count - 1) {
                this.mLastOffset = (c0491b3.f1283d + f) - 1.0f;
            }
            c0491b3.f1284e = f;
            f3 = f + (c0491b3.f1283d + f2);
            i2++;
        }
        this.mNeedCalculatePageOffsets = false;
    }

    private void completeScroll(boolean z) {
        int scrollX;
        boolean z2 = this.mScrollState == 2;
        if (z2) {
            setScrollingCacheEnabled(false);
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.mScroller.getCurrX();
                int currY = this.mScroller.getCurrY();
                if (!(scrollX == currX && scrollY == currY)) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        pageScrolled(currX);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        boolean z3 = z2;
        for (scrollX = 0; scrollX < this.mItems.size(); scrollX++) {
            C0491b c0491b = (C0491b) this.mItems.get(scrollX);
            if (c0491b.f1282c) {
                c0491b.f1282c = false;
                z3 = true;
            }
        }
        if (!z3) {
            return;
        }
        if (z) {
            ah.m2787a((View) this, this.mEndScrollRunnable);
        } else {
            this.mEndScrollRunnable.run();
        }
    }

    private int determineTargetPage(int i, float f, int i2, int i3) {
        if (Math.abs(i3) <= this.mFlingDistance || Math.abs(i2) <= this.mMinimumVelocity) {
            i += (int) ((i >= this.mCurItem ? 0.4f : 0.6f) + f);
        } else if (i2 <= 0) {
            i++;
        }
        if (this.mItems.size() <= 0) {
            return i;
        }
        return Math.max(((C0491b) this.mItems.get(0)).f1281b, Math.min(i, ((C0491b) this.mItems.get(this.mItems.size() - 1)).f1281b));
    }

    private void dispatchOnPageScrolled(int i, float f, int i2) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(i, f, i2);
        }
        if (this.mOnPageChangeListeners != null) {
            int size = this.mOnPageChangeListeners.size();
            for (int i3 = 0; i3 < size; i3++) {
                C0188f c0188f = (C0188f) this.mOnPageChangeListeners.get(i3);
                if (c0188f != null) {
                    c0188f.onPageScrolled(i, f, i2);
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(i, f, i2);
        }
    }

    private void dispatchOnPageSelected(int i) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(i);
        }
        if (this.mOnPageChangeListeners != null) {
            int size = this.mOnPageChangeListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0188f c0188f = (C0188f) this.mOnPageChangeListeners.get(i2);
                if (c0188f != null) {
                    c0188f.onPageSelected(i);
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(i);
        }
    }

    private void dispatchOnScrollStateChanged(int i) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged(i);
        }
        if (this.mOnPageChangeListeners != null) {
            int size = this.mOnPageChangeListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0188f c0188f = (C0188f) this.mOnPageChangeListeners.get(i2);
                if (c0188f != null) {
                    c0188f.onPageScrollStateChanged(i);
                }
            }
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrollStateChanged(i);
        }
    }

    private void enableLayers(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ah.m2778a(getChildAt(i), z ? this.mPageTransformerLayerType : 0, null);
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        Rect rect2 = rect == null ? new Rect() : rect;
        if (view == null) {
            rect2.set(0, 0, 0, 0);
            return rect2;
        }
        rect2.left = view.getLeft();
        rect2.right = view.getRight();
        rect2.top = view.getTop();
        rect2.bottom = view.getBottom();
        ViewPager parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = parent;
            rect2.left += viewGroup.getLeft();
            rect2.right += viewGroup.getRight();
            rect2.top += viewGroup.getTop();
            rect2.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect2;
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private C0491b infoForCurrentScrollPosition() {
        int clientWidth = getClientWidth();
        float scrollX = clientWidth > 0 ? ((float) getScrollX()) / ((float) clientWidth) : BitmapDescriptorFactory.HUE_RED;
        float f = clientWidth > 0 ? ((float) this.mPageMargin) / ((float) clientWidth) : BitmapDescriptorFactory.HUE_RED;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        float f3 = BitmapDescriptorFactory.HUE_RED;
        int i = -1;
        int i2 = 0;
        Object obj = 1;
        C0491b c0491b = null;
        while (i2 < this.mItems.size()) {
            int i3;
            C0491b c0491b2;
            C0491b c0491b3 = (C0491b) this.mItems.get(i2);
            C0491b c0491b4;
            if (obj != null || c0491b3.f1281b == i + 1) {
                c0491b4 = c0491b3;
                i3 = i2;
                c0491b2 = c0491b4;
            } else {
                c0491b3 = this.mTempItem;
                c0491b3.f1284e = (f2 + f3) + f;
                c0491b3.f1281b = i + 1;
                c0491b3.f1283d = this.mAdapter.getPageWidth(c0491b3.f1281b);
                c0491b4 = c0491b3;
                i3 = i2 - 1;
                c0491b2 = c0491b4;
            }
            f2 = c0491b2.f1284e;
            f3 = (c0491b2.f1283d + f2) + f;
            if (obj == null && scrollX < f2) {
                return c0491b;
            }
            if (scrollX < f3 || i3 == this.mItems.size() - 1) {
                return c0491b2;
            }
            f3 = f2;
            i = c0491b2.f1281b;
            obj = null;
            f2 = c0491b2.f1283d;
            c0491b = c0491b2;
            i2 = i3 + 1;
        }
        return c0491b;
    }

    private static boolean isDecorView(View view) {
        return view.getClass().getAnnotation(C0490a.class) != null;
    }

    private boolean isGutterDrag(float f, float f2) {
        return (f < ((float) this.mGutterSize) && f2 > BitmapDescriptorFactory.HUE_RED) || (f > ((float) (getWidth() - this.mGutterSize)) && f2 < BitmapDescriptorFactory.HUE_RED);
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int b = C0659t.m3206b(motionEvent);
        if (motionEvent.getPointerId(b) == this.mActivePointerId) {
            b = b == 0 ? 1 : 0;
            this.mLastMotionX = motionEvent.getX(b);
            this.mActivePointerId = motionEvent.getPointerId(b);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private boolean pageScrolled(int i) {
        if (this.mItems.size() != 0) {
            C0491b infoForCurrentScrollPosition = infoForCurrentScrollPosition();
            int clientWidth = getClientWidth();
            int i2 = this.mPageMargin + clientWidth;
            float f = ((float) this.mPageMargin) / ((float) clientWidth);
            int i3 = infoForCurrentScrollPosition.f1281b;
            float f2 = ((((float) i) / ((float) clientWidth)) - infoForCurrentScrollPosition.f1284e) / (infoForCurrentScrollPosition.f1283d + f);
            clientWidth = (int) (((float) i2) * f2);
            this.mCalledSuper = false;
            onPageScrolled(i3, f2, clientWidth);
            if (this.mCalledSuper) {
                return true;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        } else if (this.mFirstLayout) {
            return false;
        } else {
            this.mCalledSuper = false;
            onPageScrolled(0, BitmapDescriptorFactory.HUE_RED, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
    }

    private boolean performDrag(float f) {
        boolean z;
        float f2;
        boolean z2 = true;
        boolean z3 = false;
        float f3 = this.mLastMotionX - f;
        this.mLastMotionX = f;
        float scrollX = ((float) getScrollX()) + f3;
        int clientWidth = getClientWidth();
        float f4 = ((float) clientWidth) * this.mFirstOffset;
        float f5 = ((float) clientWidth) * this.mLastOffset;
        C0491b c0491b = (C0491b) this.mItems.get(0);
        C0491b c0491b2 = (C0491b) this.mItems.get(this.mItems.size() - 1);
        if (c0491b.f1281b != 0) {
            f4 = c0491b.f1284e * ((float) clientWidth);
            z = false;
        } else {
            z = true;
        }
        if (c0491b2.f1281b != this.mAdapter.getCount() - 1) {
            f2 = c0491b2.f1284e * ((float) clientWidth);
            z2 = false;
        } else {
            f2 = f5;
        }
        if (scrollX < f4) {
            if (z) {
                z3 = this.mLeftEdge.m3421a(Math.abs(f4 - scrollX) / ((float) clientWidth));
            }
        } else if (scrollX > f2) {
            if (z2) {
                z3 = this.mRightEdge.m3421a(Math.abs(scrollX - f2) / ((float) clientWidth));
            }
            f4 = f2;
        } else {
            f4 = scrollX;
        }
        this.mLastMotionX += f4 - ((float) ((int) f4));
        scrollTo((int) f4, getScrollY());
        pageScrolled((int) f4);
        return z3;
    }

    private void recomputeScrollPosition(int i, int i2, int i3, int i4) {
        if (i2 <= 0 || this.mItems.isEmpty()) {
            C0491b infoForPosition = infoForPosition(this.mCurItem);
            int min = (int) ((infoForPosition != null ? Math.min(infoForPosition.f1284e, this.mLastOffset) : BitmapDescriptorFactory.HUE_RED) * ((float) ((i - getPaddingLeft()) - getPaddingRight())));
            if (min != getScrollX()) {
                completeScroll(false);
                scrollTo(min, getScrollY());
            }
        } else if (this.mScroller.isFinished()) {
            scrollTo((int) (((float) (((i - getPaddingLeft()) - getPaddingRight()) + i3)) * (((float) getScrollX()) / ((float) (((i2 - getPaddingLeft()) - getPaddingRight()) + i4)))), getScrollY());
        } else {
            this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
        }
    }

    private void removeNonDecorViews() {
        int i = 0;
        while (i < getChildCount()) {
            if (!((C0492c) getChildAt(i).getLayoutParams()).f1285a) {
                removeViewAt(i);
                i--;
            }
            i++;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    private boolean resetTouch() {
        this.mActivePointerId = -1;
        endDrag();
        return this.mLeftEdge.m3426c() | this.mRightEdge.m3426c();
    }

    private void scrollToItem(int i, boolean z, int i2, boolean z2) {
        int max;
        C0491b infoForPosition = infoForPosition(i);
        if (infoForPosition != null) {
            max = (int) (Math.max(this.mFirstOffset, Math.min(infoForPosition.f1284e, this.mLastOffset)) * ((float) getClientWidth()));
        } else {
            max = 0;
        }
        if (z) {
            smoothScrollTo(max, 0, i2);
            if (z2) {
                dispatchOnPageSelected(i);
                return;
            }
            return;
        }
        if (z2) {
            dispatchOnPageSelected(i);
        }
        completeScroll(false);
        scrollTo(max, 0);
        pageScrolled(max);
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.mDrawingOrderedChildren.add(getChildAt(i));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0) {
                    C0491b infoForChild = infoForChild(childAt);
                    if (infoForChild != null && infoForChild.f1281b == this.mCurItem) {
                        childAt.addFocusables(arrayList, i, i2);
                    }
                }
            }
        }
        if ((descendantFocusability == 262144 && size != arrayList.size()) || !isFocusable()) {
            return;
        }
        if (((i2 & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && arrayList != null) {
            arrayList.add(this);
        }
    }

    C0491b addNewItem(int i, int i2) {
        C0491b c0491b = new C0491b();
        c0491b.f1281b = i;
        c0491b.f1280a = this.mAdapter.instantiateItem((ViewGroup) this, i);
        c0491b.f1283d = this.mAdapter.getPageWidth(i);
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(c0491b);
        } else {
            this.mItems.add(i2, c0491b);
        }
        return c0491b;
    }

    public void addOnAdapterChangeListener(C0180e c0180e) {
        if (this.mAdapterChangeListeners == null) {
            this.mAdapterChangeListeners = new ArrayList();
        }
        this.mAdapterChangeListeners.add(c0180e);
    }

    public void addOnPageChangeListener(C0188f c0188f) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList();
        }
        this.mOnPageChangeListeners.add(c0188f);
    }

    public void addTouchables(ArrayList<View> arrayList) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                C0491b infoForChild = infoForChild(childAt);
                if (infoForChild != null && infoForChild.f1281b == this.mCurItem) {
                    childAt.addTouchables(arrayList);
                }
            }
        }
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        LayoutParams generateLayoutParams = !checkLayoutParams(layoutParams) ? generateLayoutParams(layoutParams) : layoutParams;
        C0492c c0492c = (C0492c) generateLayoutParams;
        c0492c.f1285a |= isDecorView(view);
        if (!this.mInLayout) {
            super.addView(view, i, generateLayoutParams);
        } else if (c0492c == null || !c0492c.f1285a) {
            c0492c.f1288d = true;
            addViewInLayout(view, i, generateLayoutParams);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    public boolean arrowScroll(int i) {
        View view;
        boolean pageLeft;
        View findFocus = findFocus();
        if (findFocus == this) {
            view = null;
        } else {
            if (findFocus != null) {
                Object obj;
                for (ViewPager parent = findFocus.getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
                    if (parent == this) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(findFocus.getClass().getSimpleName());
                    for (ViewParent parent2 = findFocus.getParent(); parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
                        stringBuilder.append(" => ").append(parent2.getClass().getSimpleName());
                    }
                    Log.e(TAG, "arrowScroll tried to find focus based on non-child current focused view " + stringBuilder.toString());
                    view = null;
                }
            }
            view = findFocus;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, view, i);
        if (findNextFocus == null || findNextFocus == view) {
            if (i == 17 || i == 1) {
                pageLeft = pageLeft();
            } else {
                if (i == 66 || i == 2) {
                    pageLeft = pageRight();
                }
                pageLeft = false;
            }
        } else if (i == 17) {
            pageLeft = (view == null || getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left < getChildRectInPagerCoordinates(this.mTempRect, view).left) ? findNextFocus.requestFocus() : pageLeft();
        } else {
            if (i == 66) {
                pageLeft = (view == null || getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left > getChildRectInPagerCoordinates(this.mTempRect, view).left) ? findNextFocus.requestFocus() : pageRight();
            }
            pageLeft = false;
        }
        if (pageLeft) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
        }
        return pageLeft;
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionX = BitmapDescriptorFactory.HUE_RED;
        this.mInitialMotionX = BitmapDescriptorFactory.HUE_RED;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
        this.mFakeDragBeginTime = uptimeMillis;
        return true;
    }

    protected boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (i2 + scrollX >= childAt.getLeft() && i2 + scrollX < childAt.getRight() && i3 + scrollY >= childAt.getTop() && i3 + scrollY < childAt.getBottom()) {
                    if (canScroll(childAt, true, i, (i2 + scrollX) - childAt.getLeft(), (i3 + scrollY) - childAt.getTop())) {
                        return true;
                    }
                }
            }
        }
        return z && ah.m2792a(view, -i);
    }

    public boolean canScrollHorizontally(int i) {
        boolean z = true;
        if (this.mAdapter == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        if (i < 0) {
            if (scrollX <= ((int) (((float) clientWidth) * this.mFirstOffset))) {
                z = false;
            }
            return z;
        } else if (i <= 0) {
            return false;
        } else {
            if (scrollX >= ((int) (((float) clientWidth) * this.mLastOffset))) {
                z = false;
            }
            return z;
        }
    }

    protected boolean checkLayoutParams(LayoutParams layoutParams) {
        return (layoutParams instanceof C0492c) && super.checkLayoutParams(layoutParams);
    }

    public void clearOnPageChangeListeners() {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.clear();
        }
    }

    public void computeScroll() {
        this.mIsScrollStarted = true;
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (!(scrollX == currX && scrollY == currY)) {
            scrollTo(currX, currY);
            if (!pageScrolled(currX)) {
                this.mScroller.abortAnimation();
                scrollTo(0, currY);
            }
        }
        ah.m2799c(this);
    }

    void dataSetChanged() {
        int i;
        int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        boolean z = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < count;
        boolean z2 = false;
        int i2 = this.mCurItem;
        boolean z3 = z;
        int i3 = 0;
        while (i3 < this.mItems.size()) {
            int i4;
            boolean z4;
            boolean z5;
            C0491b c0491b = (C0491b) this.mItems.get(i3);
            int itemPosition = this.mAdapter.getItemPosition(c0491b.f1280a);
            if (itemPosition == -1) {
                i4 = i3;
                z4 = z2;
                i = i2;
                z5 = z3;
            } else if (itemPosition == -2) {
                this.mItems.remove(i3);
                i3--;
                if (!z2) {
                    this.mAdapter.startUpdate((ViewGroup) this);
                    z2 = true;
                }
                this.mAdapter.destroyItem((ViewGroup) this, c0491b.f1281b, c0491b.f1280a);
                if (this.mCurItem == c0491b.f1281b) {
                    i4 = i3;
                    z4 = z2;
                    i = Math.max(0, Math.min(this.mCurItem, count - 1));
                    z5 = true;
                } else {
                    i4 = i3;
                    z4 = z2;
                    i = i2;
                    z5 = true;
                }
            } else if (c0491b.f1281b != itemPosition) {
                if (c0491b.f1281b == this.mCurItem) {
                    i2 = itemPosition;
                }
                c0491b.f1281b = itemPosition;
                i4 = i3;
                z4 = z2;
                i = i2;
                z5 = true;
            } else {
                i4 = i3;
                z4 = z2;
                i = i2;
                z5 = z3;
            }
            z3 = z5;
            i2 = i;
            z2 = z4;
            i3 = i4 + 1;
        }
        if (z2) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (z3) {
            i = getChildCount();
            for (i3 = 0; i3 < i; i3++) {
                C0492c c0492c = (C0492c) getChildAt(i3).getLayoutParams();
                if (!c0492c.f1285a) {
                    c0492c.f1287c = BitmapDescriptorFactory.HUE_RED;
                }
            }
            setCurrentItemInternal(i2, false, true);
            requestLayout();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0) {
                C0491b infoForChild = infoForChild(childAt);
                if (infoForChild != null && infoForChild.f1281b == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                    return true;
                }
            }
        }
        return false;
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((double) ((float) (((double) (f - 0.5f)) * 0.4712389167638204d)));
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int i = 0;
        int overScrollMode = getOverScrollMode();
        if (overScrollMode == 0 || (overScrollMode == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
            int height;
            int width;
            if (!this.mLeftEdge.m3420a()) {
                overScrollMode = canvas.save();
                height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float) ((-height) + getPaddingTop()), this.mFirstOffset * ((float) width));
                this.mLeftEdge.m3419a(height, width);
                i = 0 | this.mLeftEdge.m3424a(canvas);
                canvas.restoreToCount(overScrollMode);
            }
            if (!this.mRightEdge.m3420a()) {
                overScrollMode = canvas.save();
                height = getWidth();
                width = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float) (-getPaddingTop()), (-(this.mLastOffset + 1.0f)) * ((float) height));
                this.mRightEdge.m3419a(width, height);
                i |= this.mRightEdge.m3424a(canvas);
                canvas.restoreToCount(overScrollMode);
            }
        } else {
            this.mLeftEdge.m3425b();
            this.mRightEdge.m3425b();
        }
        if (i != 0) {
            ah.m2799c(this);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mMarginDrawable;
        if (drawable != null && drawable.isStateful()) {
            drawable.setState(getDrawableState());
        }
    }

    public void endFakeDrag() {
        if (this.mFakeDragging) {
            if (this.mAdapter != null) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                int a = (int) af.m2516a(velocityTracker, this.mActivePointerId);
                this.mPopulatePending = true;
                int clientWidth = getClientWidth();
                int scrollX = getScrollX();
                C0491b infoForCurrentScrollPosition = infoForCurrentScrollPosition();
                setCurrentItemInternal(determineTargetPage(infoForCurrentScrollPosition.f1281b, ((((float) scrollX) / ((float) clientWidth)) - infoForCurrentScrollPosition.f1284e) / infoForCurrentScrollPosition.f1283d, a, (int) (this.mLastMotionX - this.mInitialMotionX)), true, true, a);
            }
            endDrag();
            this.mFakeDragging = false;
            return;
        }
        throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) {
            return false;
        }
        switch (keyEvent.getKeyCode()) {
            case 21:
                return arrowScroll(17);
            case 22:
                return arrowScroll(66);
            case 61:
                return VERSION.SDK_INT >= 11 ? C0630h.m3136a(keyEvent) ? arrowScroll(2) : C0630h.m3137a(keyEvent, 1) ? arrowScroll(1) : false : false;
            default:
                return false;
        }
    }

    public void fakeDragBy(float f) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        } else if (this.mAdapter != null) {
            this.mLastMotionX += f;
            float scrollX = ((float) getScrollX()) - f;
            int clientWidth = getClientWidth();
            float f2 = ((float) clientWidth) * this.mLastOffset;
            C0491b c0491b = (C0491b) this.mItems.get(0);
            C0491b c0491b2 = (C0491b) this.mItems.get(this.mItems.size() - 1);
            float f3 = c0491b.f1281b != 0 ? c0491b.f1284e * ((float) clientWidth) : ((float) clientWidth) * this.mFirstOffset;
            float f4 = c0491b2.f1281b != this.mAdapter.getCount() + -1 ? c0491b2.f1284e * ((float) clientWidth) : f2;
            if (scrollX >= f3) {
                f3 = scrollX > f4 ? f4 : scrollX;
            }
            this.mLastMotionX += f3 - ((float) ((int) f3));
            scrollTo((int) f3, getScrollY());
            pageScrolled((int) f3);
            MotionEvent obtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, BitmapDescriptorFactory.HUE_RED, 0);
            this.mVelocityTracker.addMovement(obtain);
            obtain.recycle();
        }
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new C0492c();
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new C0492c(getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    public aa getAdapter() {
        return this.mAdapter;
    }

    protected int getChildDrawingOrder(int i, int i2) {
        if (this.mDrawingOrder == 2) {
            i2 = (i - 1) - i2;
        }
        return ((C0492c) ((View) this.mDrawingOrderedChildren.get(i2)).getLayoutParams()).f1290f;
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    C0491b infoForAnyChild(View view) {
        while (true) {
            ViewPager parent = view.getParent();
            if (parent == this) {
                return infoForChild(view);
            }
            if (parent != null && (parent instanceof View)) {
                view = parent;
            }
        }
        return null;
    }

    C0491b infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); i++) {
            C0491b c0491b = (C0491b) this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, c0491b.f1280a)) {
                return c0491b;
            }
        }
        return null;
    }

    C0491b infoForPosition(int i) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            C0491b c0491b = (C0491b) this.mItems.get(i2);
            if (c0491b.f1281b == i) {
                return c0491b;
            }
        }
        return null;
    }

    void initViewPager() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int) (400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new C0700i(context);
        this.mRightEdge = new C0700i(context);
        this.mFlingDistance = (int) (25.0f * f);
        this.mCloseEnough = (int) (2.0f * f);
        this.mDefaultGutterSize = (int) (16.0f * f);
        ah.m2783a((View) this, new C0493d(this));
        if (ah.m2803d(this) == 0) {
            ah.m2801c((View) this, 1);
        }
        ah.m2785a((View) this, new C04884(this));
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        if (!(this.mScroller == null || this.mScroller.isFinished())) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int scrollX = getScrollX();
            int width = getWidth();
            float f = ((float) this.mPageMargin) / ((float) width);
            C0491b c0491b = (C0491b) this.mItems.get(0);
            float f2 = c0491b.f1284e;
            int size = this.mItems.size();
            int i = c0491b.f1281b;
            int i2 = ((C0491b) this.mItems.get(size - 1)).f1281b;
            int i3 = 0;
            int i4 = i;
            while (i4 < i2) {
                float f3;
                while (i4 > c0491b.f1281b && i3 < size) {
                    i3++;
                    c0491b = (C0491b) this.mItems.get(i3);
                }
                if (i4 == c0491b.f1281b) {
                    f3 = (c0491b.f1284e + c0491b.f1283d) * ((float) width);
                    f2 = (c0491b.f1284e + c0491b.f1283d) + f;
                } else {
                    float pageWidth = this.mAdapter.getPageWidth(i4);
                    f3 = (f2 + pageWidth) * ((float) width);
                    f2 += pageWidth + f;
                }
                if (((float) this.mPageMargin) + f3 > ((float) scrollX)) {
                    this.mMarginDrawable.setBounds(Math.round(f3), this.mTopPageBounds, Math.round(((float) this.mPageMargin) + f3), this.mBottomPageBounds);
                    this.mMarginDrawable.draw(canvas);
                }
                if (f3 <= ((float) (scrollX + width))) {
                    i4++;
                } else {
                    return;
                }
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            resetTouch();
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (action) {
            case 0:
                float x = motionEvent.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                x = motionEvent.getY();
                this.mInitialMotionY = x;
                this.mLastMotionY = x;
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mIsUnableToDrag = false;
                this.mIsScrollStarted = true;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    populate();
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                    break;
                }
                completeScroll(false);
                this.mIsBeingDragged = false;
                break;
            case 2:
                action = this.mActivePointerId;
                if (action != -1) {
                    action = motionEvent.findPointerIndex(action);
                    float x2 = motionEvent.getX(action);
                    float f = x2 - this.mLastMotionX;
                    float abs = Math.abs(f);
                    float y = motionEvent.getY(action);
                    float abs2 = Math.abs(y - this.mInitialMotionY);
                    if (f == BitmapDescriptorFactory.HUE_RED || isGutterDrag(this.mLastMotionX, f) || !canScroll(this, false, (int) f, (int) x2, (int) y)) {
                        if (abs > ((float) this.mTouchSlop) && 0.5f * abs > abs2) {
                            this.mIsBeingDragged = true;
                            requestParentDisallowInterceptTouchEvent(true);
                            setScrollState(1);
                            this.mLastMotionX = f > BitmapDescriptorFactory.HUE_RED ? this.mInitialMotionX + ((float) this.mTouchSlop) : this.mInitialMotionX - ((float) this.mTouchSlop);
                            this.mLastMotionY = y;
                            setScrollingCacheEnabled(true);
                        } else if (abs2 > ((float) this.mTouchSlop)) {
                            this.mIsUnableToDrag = true;
                        }
                        if (this.mIsBeingDragged && performDrag(x2)) {
                            ah.m2799c(this);
                            break;
                        }
                    }
                    this.mLastMotionX = x2;
                    this.mLastMotionY = y;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                break;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int max;
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i7 = 0;
        int i8 = 0;
        while (i8 < childCount) {
            C0492c c0492c;
            int measuredWidth;
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                c0492c = (C0492c) childAt.getLayoutParams();
                if (c0492c.f1285a) {
                    int i9 = c0492c.f1286b & 112;
                    switch (c0492c.f1286b & 7) {
                        case 1:
                            max = Math.max((i5 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 3:
                            max = paddingLeft;
                            paddingLeft = childAt.getMeasuredWidth() + paddingLeft;
                            break;
                        case 5:
                            measuredWidth = (i5 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                            max = measuredWidth;
                            break;
                        default:
                            max = paddingLeft;
                            break;
                    }
                    int i10;
                    switch (i9) {
                        case 16:
                            measuredWidth = Math.max((i6 - childAt.getMeasuredHeight()) / 2, paddingTop);
                            i10 = paddingBottom;
                            paddingBottom = paddingTop;
                            paddingTop = i10;
                            break;
                        case 48:
                            measuredWidth = childAt.getMeasuredHeight() + paddingTop;
                            i10 = paddingTop;
                            paddingTop = paddingBottom;
                            paddingBottom = measuredWidth;
                            measuredWidth = i10;
                            break;
                        case 80:
                            measuredWidth = (i6 - paddingBottom) - childAt.getMeasuredHeight();
                            i10 = paddingBottom + childAt.getMeasuredHeight();
                            paddingBottom = paddingTop;
                            paddingTop = i10;
                            break;
                        default:
                            measuredWidth = paddingTop;
                            i10 = paddingBottom;
                            paddingBottom = paddingTop;
                            paddingTop = i10;
                            break;
                    }
                    max += scrollX;
                    childAt.layout(max, measuredWidth, childAt.getMeasuredWidth() + max, childAt.getMeasuredHeight() + measuredWidth);
                    measuredWidth = i7 + 1;
                    i7 = paddingBottom;
                    paddingBottom = paddingTop;
                    paddingTop = paddingRight;
                    paddingRight = paddingLeft;
                    i8++;
                    paddingLeft = paddingRight;
                    paddingRight = paddingTop;
                    paddingTop = i7;
                    i7 = measuredWidth;
                }
            }
            measuredWidth = i7;
            i7 = paddingTop;
            paddingTop = paddingRight;
            paddingRight = paddingLeft;
            i8++;
            paddingLeft = paddingRight;
            paddingRight = paddingTop;
            paddingTop = i7;
            i7 = measuredWidth;
        }
        max = (i5 - paddingLeft) - paddingRight;
        for (paddingRight = 0; paddingRight < childCount; paddingRight++) {
            View childAt2 = getChildAt(paddingRight);
            if (childAt2.getVisibility() != 8) {
                c0492c = (C0492c) childAt2.getLayoutParams();
                if (!c0492c.f1285a) {
                    C0491b infoForChild = infoForChild(childAt2);
                    if (infoForChild != null) {
                        i5 = ((int) (infoForChild.f1284e * ((float) max))) + paddingLeft;
                        if (c0492c.f1288d) {
                            c0492c.f1288d = false;
                            childAt2.measure(MeasureSpec.makeMeasureSpec((int) (c0492c.f1287c * ((float) max)), 1073741824), MeasureSpec.makeMeasureSpec((i6 - paddingTop) - paddingBottom, 1073741824));
                        }
                        childAt2.layout(i5, paddingTop, childAt2.getMeasuredWidth() + i5, childAt2.getMeasuredHeight() + paddingTop);
                    }
                }
            }
        }
        this.mTopPageBounds = paddingTop;
        this.mBottomPageBounds = i6 - paddingBottom;
        this.mDecorChildCount = i7;
        if (this.mFirstLayout) {
            scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        setMeasuredDimension(getDefaultSize(0, i), getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        this.mGutterSize = Math.min(measuredWidth / 10, this.mDefaultGutterSize);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            C0492c c0492c;
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                c0492c = (C0492c) childAt.getLayoutParams();
                if (c0492c != null && c0492c.f1285a) {
                    int i6 = c0492c.f1286b & 7;
                    int i7 = c0492c.f1286b & 112;
                    i3 = Integer.MIN_VALUE;
                    i4 = Integer.MIN_VALUE;
                    Object obj = (i7 == 48 || i7 == 80) ? 1 : null;
                    Object obj2 = (i6 == 3 || i6 == 5) ? 1 : null;
                    if (obj != null) {
                        i3 = 1073741824;
                    } else if (obj2 != null) {
                        i4 = 1073741824;
                    }
                    if (c0492c.width != -2) {
                        i7 = 1073741824;
                        i3 = c0492c.width != -1 ? c0492c.width : paddingLeft;
                    } else {
                        i7 = i3;
                        i3 = paddingLeft;
                    }
                    if (c0492c.height != -2) {
                        i4 = 1073741824;
                        if (c0492c.height != -1) {
                            measuredWidth = c0492c.height;
                            childAt.measure(MeasureSpec.makeMeasureSpec(i3, i7), MeasureSpec.makeMeasureSpec(measuredWidth, i4));
                            if (obj != null) {
                                measuredHeight -= childAt.getMeasuredHeight();
                            } else if (obj2 != null) {
                                paddingLeft -= childAt.getMeasuredWidth();
                            }
                        }
                    }
                    measuredWidth = measuredHeight;
                    childAt.measure(MeasureSpec.makeMeasureSpec(i3, i7), MeasureSpec.makeMeasureSpec(measuredWidth, i4));
                    if (obj != null) {
                        measuredHeight -= childAt.getMeasuredHeight();
                    } else if (obj2 != null) {
                        paddingLeft -= childAt.getMeasuredWidth();
                    }
                }
            }
        }
        this.mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        this.mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        i3 = getChildCount();
        for (i4 = 0; i4 < i3; i4++) {
            View childAt2 = getChildAt(i4);
            if (childAt2.getVisibility() != 8) {
                c0492c = (C0492c) childAt2.getLayoutParams();
                if (c0492c == null || !c0492c.f1285a) {
                    childAt2.measure(MeasureSpec.makeMeasureSpec((int) (c0492c.f1287c * ((float) paddingLeft)), 1073741824), this.mChildHeightMeasureSpec);
                }
            }
        }
    }

    protected void onPageScrolled(int i, float f, int i2) {
        int paddingLeft;
        int paddingRight;
        int i3;
        if (this.mDecorChildCount > 0) {
            int scrollX = getScrollX();
            paddingLeft = getPaddingLeft();
            paddingRight = getPaddingRight();
            int width = getWidth();
            int childCount = getChildCount();
            i3 = 0;
            while (i3 < childCount) {
                int i4;
                View childAt = getChildAt(i3);
                C0492c c0492c = (C0492c) childAt.getLayoutParams();
                if (c0492c.f1285a) {
                    int max;
                    switch (c0492c.f1286b & 7) {
                        case 1:
                            max = Math.max((width - childAt.getMeasuredWidth()) / 2, paddingLeft);
                            i4 = paddingRight;
                            paddingRight = paddingLeft;
                            paddingLeft = i4;
                            break;
                        case 3:
                            max = childAt.getWidth() + paddingLeft;
                            i4 = paddingLeft;
                            paddingLeft = paddingRight;
                            paddingRight = max;
                            max = i4;
                            break;
                        case 5:
                            max = (width - paddingRight) - childAt.getMeasuredWidth();
                            i4 = paddingRight + childAt.getMeasuredWidth();
                            paddingRight = paddingLeft;
                            paddingLeft = i4;
                            break;
                        default:
                            max = paddingLeft;
                            i4 = paddingRight;
                            paddingRight = paddingLeft;
                            paddingLeft = i4;
                            break;
                    }
                    max = (max + scrollX) - childAt.getLeft();
                    if (max != 0) {
                        childAt.offsetLeftAndRight(max);
                    }
                } else {
                    i4 = paddingRight;
                    paddingRight = paddingLeft;
                    paddingLeft = i4;
                }
                i3++;
                i4 = paddingLeft;
                paddingLeft = paddingRight;
                paddingRight = i4;
            }
        }
        dispatchOnPageScrolled(i, f, i2);
        if (this.mPageTransformer != null) {
            paddingRight = getScrollX();
            i3 = getChildCount();
            for (paddingLeft = 0; paddingLeft < i3; paddingLeft++) {
                View childAt2 = getChildAt(paddingLeft);
                if (!((C0492c) childAt2.getLayoutParams()).f1285a) {
                    this.mPageTransformer.mo3498a(childAt2, ((float) (childAt2.getLeft() - paddingRight)) / ((float) getClientWidth()));
                }
            }
        }
        this.mCalledSuper = true;
    }

    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3 = -1;
        int childCount = getChildCount();
        if ((i & 2) != 0) {
            i3 = 1;
            i2 = 0;
        } else {
            i2 = childCount - 1;
            childCount = -1;
        }
        while (i2 != childCount) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0) {
                C0491b infoForChild = infoForChild(childAt);
                if (infoForChild != null && infoForChild.f1281b == this.mCurItem && childAt.requestFocus(i, rect)) {
                    return true;
                }
            }
            i2 += i3;
        }
        return false;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            if (this.mAdapter != null) {
                this.mAdapter.restoreState(savedState.f1278b, savedState.f1279c);
                setCurrentItemInternal(savedState.f1277a, false, true);
                return;
            }
            this.mRestoredCurItem = savedState.f1277a;
            this.mRestoredAdapterState = savedState.f1278b;
            this.mRestoredClassLoader = savedState.f1279c;
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.f1277a = this.mCurItem;
        if (this.mAdapter != null) {
            savedState.f1278b = this.mAdapter.saveState();
        }
        return savedState;
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            recomputeScrollPosition(i, i3, this.mPageMargin, this.mPageMargin);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (this.mFakeDragging) {
            return true;
        }
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        float x;
        int a;
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                x = motionEvent.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                x = motionEvent.getY();
                this.mInitialMotionY = x;
                this.mLastMotionY = x;
                this.mActivePointerId = motionEvent.getPointerId(0);
                break;
            case 1:
                if (this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    a = (int) af.m2516a(velocityTracker, this.mActivePointerId);
                    this.mPopulatePending = true;
                    int clientWidth = getClientWidth();
                    int scrollX = getScrollX();
                    C0491b infoForCurrentScrollPosition = infoForCurrentScrollPosition();
                    setCurrentItemInternal(determineTargetPage(infoForCurrentScrollPosition.f1281b, ((((float) scrollX) / ((float) clientWidth)) - infoForCurrentScrollPosition.f1284e) / (infoForCurrentScrollPosition.f1283d + (((float) this.mPageMargin) / ((float) clientWidth))), a, (int) (motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX)), true, true, a);
                    z = resetTouch();
                    break;
                }
                break;
            case 2:
                if (!this.mIsBeingDragged) {
                    a = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (a == -1) {
                        z = resetTouch();
                        break;
                    }
                    float x2 = motionEvent.getX(a);
                    float abs = Math.abs(x2 - this.mLastMotionX);
                    float y = motionEvent.getY(a);
                    x = Math.abs(y - this.mLastMotionY);
                    if (abs > ((float) this.mTouchSlop) && abs > x) {
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        this.mLastMotionX = x2 - this.mInitialMotionX > BitmapDescriptorFactory.HUE_RED ? this.mInitialMotionX + ((float) this.mTouchSlop) : this.mInitialMotionX - ((float) this.mTouchSlop);
                        this.mLastMotionY = y;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    z = false | performDrag(motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)));
                    break;
                }
                break;
            case 3:
                if (this.mIsBeingDragged) {
                    scrollToItem(this.mCurItem, true, 0, false);
                    z = resetTouch();
                    break;
                }
                break;
            case 5:
                a = C0659t.m3206b(motionEvent);
                this.mLastMotionX = motionEvent.getX(a);
                this.mActivePointerId = motionEvent.getPointerId(a);
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                this.mLastMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                break;
        }
        if (z) {
            ah.m2799c(this);
        }
        return true;
    }

    boolean pageLeft() {
        if (this.mCurItem <= 0) {
            return false;
        }
        setCurrentItem(this.mCurItem - 1, true);
        return true;
    }

    boolean pageRight() {
        if (this.mAdapter == null || this.mCurItem >= this.mAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    void populate() {
        populate(this.mCurItem);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void populate(int r18) {
        /*
        r17 = this;
        r2 = 0;
        r0 = r17;
        r3 = r0.mCurItem;
        r0 = r18;
        if (r3 == r0) goto L_0x0328;
    L_0x0009:
        r0 = r17;
        r2 = r0.mCurItem;
        r0 = r17;
        r2 = r0.infoForPosition(r2);
        r0 = r18;
        r1 = r17;
        r1.mCurItem = r0;
        r3 = r2;
    L_0x001a:
        r0 = r17;
        r2 = r0.mAdapter;
        if (r2 != 0) goto L_0x0024;
    L_0x0020:
        r17.sortChildDrawingOrder();
    L_0x0023:
        return;
    L_0x0024:
        r0 = r17;
        r2 = r0.mPopulatePending;
        if (r2 == 0) goto L_0x002e;
    L_0x002a:
        r17.sortChildDrawingOrder();
        goto L_0x0023;
    L_0x002e:
        r2 = r17.getWindowToken();
        if (r2 == 0) goto L_0x0023;
    L_0x0034:
        r0 = r17;
        r2 = r0.mAdapter;
        r0 = r17;
        r2.startUpdate(r0);
        r0 = r17;
        r2 = r0.mOffscreenPageLimit;
        r4 = 0;
        r0 = r17;
        r5 = r0.mCurItem;
        r5 = r5 - r2;
        r10 = java.lang.Math.max(r4, r5);
        r0 = r17;
        r4 = r0.mAdapter;
        r11 = r4.getCount();
        r4 = r11 + -1;
        r0 = r17;
        r5 = r0.mCurItem;
        r2 = r2 + r5;
        r12 = java.lang.Math.min(r4, r2);
        r0 = r17;
        r2 = r0.mExpectedAdapterCount;
        if (r11 == r2) goto L_0x00d0;
    L_0x0064:
        r2 = r17.getResources();	 Catch:{ NotFoundException -> 0x00c6 }
        r3 = r17.getId();	 Catch:{ NotFoundException -> 0x00c6 }
        r2 = r2.getResourceName(r3);	 Catch:{ NotFoundException -> 0x00c6 }
    L_0x0070:
        r3 = new java.lang.IllegalStateException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: ";
        r4 = r4.append(r5);
        r0 = r17;
        r5 = r0.mExpectedAdapterCount;
        r4 = r4.append(r5);
        r5 = ", found: ";
        r4 = r4.append(r5);
        r4 = r4.append(r11);
        r5 = " Pager id: ";
        r4 = r4.append(r5);
        r2 = r4.append(r2);
        r4 = " Pager class: ";
        r2 = r2.append(r4);
        r4 = r17.getClass();
        r2 = r2.append(r4);
        r4 = " Problematic adapter: ";
        r2 = r2.append(r4);
        r0 = r17;
        r4 = r0.mAdapter;
        r4 = r4.getClass();
        r2 = r2.append(r4);
        r2 = r2.toString();
        r3.<init>(r2);
        throw r3;
    L_0x00c6:
        r2 = move-exception;
        r2 = r17.getId();
        r2 = java.lang.Integer.toHexString(r2);
        goto L_0x0070;
    L_0x00d0:
        r5 = 0;
        r2 = 0;
        r4 = r2;
    L_0x00d3:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.size();
        if (r4 >= r2) goto L_0x0325;
    L_0x00dd:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.get(r4);
        r2 = (android.support.v4.view.ViewPager.C0491b) r2;
        r6 = r2.f1281b;
        r0 = r17;
        r7 = r0.mCurItem;
        if (r6 < r7) goto L_0x01c1;
    L_0x00ef:
        r6 = r2.f1281b;
        r0 = r17;
        r7 = r0.mCurItem;
        if (r6 != r7) goto L_0x0325;
    L_0x00f7:
        if (r2 != 0) goto L_0x0322;
    L_0x00f9:
        if (r11 <= 0) goto L_0x0322;
    L_0x00fb:
        r0 = r17;
        r2 = r0.mCurItem;
        r0 = r17;
        r2 = r0.addNewItem(r2, r4);
        r9 = r2;
    L_0x0106:
        if (r9 == 0) goto L_0x0172;
    L_0x0108:
        r8 = 0;
        r7 = r4 + -1;
        if (r7 < 0) goto L_0x01c6;
    L_0x010d:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.get(r7);
        r2 = (android.support.v4.view.ViewPager.C0491b) r2;
    L_0x0117:
        r13 = r17.getClientWidth();
        if (r13 > 0) goto L_0x01c9;
    L_0x011d:
        r5 = 0;
    L_0x011e:
        r0 = r17;
        r6 = r0.mCurItem;
        r6 = r6 + -1;
        r15 = r6;
        r6 = r8;
        r8 = r15;
        r16 = r7;
        r7 = r4;
        r4 = r16;
    L_0x012c:
        if (r8 < 0) goto L_0x0136;
    L_0x012e:
        r14 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1));
        if (r14 < 0) goto L_0x0208;
    L_0x0132:
        if (r8 >= r10) goto L_0x0208;
    L_0x0134:
        if (r2 != 0) goto L_0x01d8;
    L_0x0136:
        r5 = r9.f1283d;
        r8 = r7 + 1;
        r2 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r2 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1));
        if (r2 >= 0) goto L_0x016d;
    L_0x0140:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.size();
        if (r8 >= r2) goto L_0x023e;
    L_0x014a:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.get(r8);
        r2 = (android.support.v4.view.ViewPager.C0491b) r2;
        r6 = r2;
    L_0x0155:
        if (r13 > 0) goto L_0x0241;
    L_0x0157:
        r2 = 0;
        r4 = r2;
    L_0x0159:
        r0 = r17;
        r2 = r0.mCurItem;
        r2 = r2 + 1;
        r15 = r2;
        r2 = r6;
        r6 = r8;
        r8 = r15;
    L_0x0163:
        if (r8 >= r11) goto L_0x016d;
    L_0x0165:
        r10 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1));
        if (r10 < 0) goto L_0x0288;
    L_0x0169:
        if (r8 <= r12) goto L_0x0288;
    L_0x016b:
        if (r2 != 0) goto L_0x024e;
    L_0x016d:
        r0 = r17;
        r0.calculatePageOffsets(r9, r7, r3);
    L_0x0172:
        r0 = r17;
        r3 = r0.mAdapter;
        r0 = r17;
        r4 = r0.mCurItem;
        if (r9 == 0) goto L_0x02d2;
    L_0x017c:
        r2 = r9.f1280a;
    L_0x017e:
        r0 = r17;
        r3.setPrimaryItem(r0, r4, r2);
        r0 = r17;
        r2 = r0.mAdapter;
        r0 = r17;
        r2.finishUpdate(r0);
        r4 = r17.getChildCount();
        r2 = 0;
        r3 = r2;
    L_0x0192:
        if (r3 >= r4) goto L_0x02d5;
    L_0x0194:
        r0 = r17;
        r5 = r0.getChildAt(r3);
        r2 = r5.getLayoutParams();
        r2 = (android.support.v4.view.ViewPager.C0492c) r2;
        r2.f1290f = r3;
        r6 = r2.f1285a;
        if (r6 != 0) goto L_0x01bd;
    L_0x01a6:
        r6 = r2.f1287c;
        r7 = 0;
        r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1));
        if (r6 != 0) goto L_0x01bd;
    L_0x01ad:
        r0 = r17;
        r5 = r0.infoForChild(r5);
        if (r5 == 0) goto L_0x01bd;
    L_0x01b5:
        r6 = r5.f1283d;
        r2.f1287c = r6;
        r5 = r5.f1281b;
        r2.f1289e = r5;
    L_0x01bd:
        r2 = r3 + 1;
        r3 = r2;
        goto L_0x0192;
    L_0x01c1:
        r2 = r4 + 1;
        r4 = r2;
        goto L_0x00d3;
    L_0x01c6:
        r2 = 0;
        goto L_0x0117;
    L_0x01c9:
        r5 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r6 = r9.f1283d;
        r5 = r5 - r6;
        r6 = r17.getPaddingLeft();
        r6 = (float) r6;
        r14 = (float) r13;
        r6 = r6 / r14;
        r5 = r5 + r6;
        goto L_0x011e;
    L_0x01d8:
        r14 = r2.f1281b;
        if (r8 != r14) goto L_0x0202;
    L_0x01dc:
        r14 = r2.f1282c;
        if (r14 != 0) goto L_0x0202;
    L_0x01e0:
        r0 = r17;
        r14 = r0.mItems;
        r14.remove(r4);
        r0 = r17;
        r14 = r0.mAdapter;
        r2 = r2.f1280a;
        r0 = r17;
        r14.destroyItem(r0, r8, r2);
        r4 = r4 + -1;
        r7 = r7 + -1;
        if (r4 < 0) goto L_0x0206;
    L_0x01f8:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.get(r4);
        r2 = (android.support.v4.view.ViewPager.C0491b) r2;
    L_0x0202:
        r8 = r8 + -1;
        goto L_0x012c;
    L_0x0206:
        r2 = 0;
        goto L_0x0202;
    L_0x0208:
        if (r2 == 0) goto L_0x0222;
    L_0x020a:
        r14 = r2.f1281b;
        if (r8 != r14) goto L_0x0222;
    L_0x020e:
        r2 = r2.f1283d;
        r6 = r6 + r2;
        r4 = r4 + -1;
        if (r4 < 0) goto L_0x0220;
    L_0x0215:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.get(r4);
        r2 = (android.support.v4.view.ViewPager.C0491b) r2;
        goto L_0x0202;
    L_0x0220:
        r2 = 0;
        goto L_0x0202;
    L_0x0222:
        r2 = r4 + 1;
        r0 = r17;
        r2 = r0.addNewItem(r8, r2);
        r2 = r2.f1283d;
        r6 = r6 + r2;
        r7 = r7 + 1;
        if (r4 < 0) goto L_0x023c;
    L_0x0231:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.get(r4);
        r2 = (android.support.v4.view.ViewPager.C0491b) r2;
        goto L_0x0202;
    L_0x023c:
        r2 = 0;
        goto L_0x0202;
    L_0x023e:
        r6 = 0;
        goto L_0x0155;
    L_0x0241:
        r2 = r17.getPaddingRight();
        r2 = (float) r2;
        r4 = (float) r13;
        r2 = r2 / r4;
        r4 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r2 = r2 + r4;
        r4 = r2;
        goto L_0x0159;
    L_0x024e:
        r10 = r2.f1281b;
        if (r8 != r10) goto L_0x031d;
    L_0x0252:
        r10 = r2.f1282c;
        if (r10 != 0) goto L_0x031d;
    L_0x0256:
        r0 = r17;
        r10 = r0.mItems;
        r10.remove(r6);
        r0 = r17;
        r10 = r0.mAdapter;
        r2 = r2.f1280a;
        r0 = r17;
        r10.destroyItem(r0, r8, r2);
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.size();
        if (r6 >= r2) goto L_0x0286;
    L_0x0272:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.get(r6);
        r2 = (android.support.v4.view.ViewPager.C0491b) r2;
    L_0x027c:
        r15 = r5;
        r5 = r2;
        r2 = r15;
    L_0x027f:
        r8 = r8 + 1;
        r15 = r2;
        r2 = r5;
        r5 = r15;
        goto L_0x0163;
    L_0x0286:
        r2 = 0;
        goto L_0x027c;
    L_0x0288:
        if (r2 == 0) goto L_0x02ad;
    L_0x028a:
        r10 = r2.f1281b;
        if (r8 != r10) goto L_0x02ad;
    L_0x028e:
        r2 = r2.f1283d;
        r5 = r5 + r2;
        r6 = r6 + 1;
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.size();
        if (r6 >= r2) goto L_0x02ab;
    L_0x029d:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.get(r6);
        r2 = (android.support.v4.view.ViewPager.C0491b) r2;
    L_0x02a7:
        r15 = r5;
        r5 = r2;
        r2 = r15;
        goto L_0x027f;
    L_0x02ab:
        r2 = 0;
        goto L_0x02a7;
    L_0x02ad:
        r0 = r17;
        r2 = r0.addNewItem(r8, r6);
        r6 = r6 + 1;
        r2 = r2.f1283d;
        r5 = r5 + r2;
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.size();
        if (r6 >= r2) goto L_0x02d0;
    L_0x02c2:
        r0 = r17;
        r2 = r0.mItems;
        r2 = r2.get(r6);
        r2 = (android.support.v4.view.ViewPager.C0491b) r2;
    L_0x02cc:
        r15 = r5;
        r5 = r2;
        r2 = r15;
        goto L_0x027f;
    L_0x02d0:
        r2 = 0;
        goto L_0x02cc;
    L_0x02d2:
        r2 = 0;
        goto L_0x017e;
    L_0x02d5:
        r17.sortChildDrawingOrder();
        r2 = r17.hasFocus();
        if (r2 == 0) goto L_0x0023;
    L_0x02de:
        r2 = r17.findFocus();
        if (r2 == 0) goto L_0x031b;
    L_0x02e4:
        r0 = r17;
        r2 = r0.infoForAnyChild(r2);
    L_0x02ea:
        if (r2 == 0) goto L_0x02f4;
    L_0x02ec:
        r2 = r2.f1281b;
        r0 = r17;
        r3 = r0.mCurItem;
        if (r2 == r3) goto L_0x0023;
    L_0x02f4:
        r2 = 0;
    L_0x02f5:
        r3 = r17.getChildCount();
        if (r2 >= r3) goto L_0x0023;
    L_0x02fb:
        r0 = r17;
        r3 = r0.getChildAt(r2);
        r0 = r17;
        r4 = r0.infoForChild(r3);
        if (r4 == 0) goto L_0x0318;
    L_0x0309:
        r4 = r4.f1281b;
        r0 = r17;
        r5 = r0.mCurItem;
        if (r4 != r5) goto L_0x0318;
    L_0x0311:
        r4 = 2;
        r3 = r3.requestFocus(r4);
        if (r3 != 0) goto L_0x0023;
    L_0x0318:
        r2 = r2 + 1;
        goto L_0x02f5;
    L_0x031b:
        r2 = 0;
        goto L_0x02ea;
    L_0x031d:
        r15 = r5;
        r5 = r2;
        r2 = r15;
        goto L_0x027f;
    L_0x0322:
        r9 = r2;
        goto L_0x0106;
    L_0x0325:
        r2 = r5;
        goto L_0x00f7;
    L_0x0328:
        r3 = r2;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.populate(int):void");
    }

    public void removeOnAdapterChangeListener(C0180e c0180e) {
        if (this.mAdapterChangeListeners != null) {
            this.mAdapterChangeListeners.remove(c0180e);
        }
    }

    public void removeOnPageChangeListener(C0188f c0188f) {
        if (this.mOnPageChangeListeners != null) {
            this.mOnPageChangeListeners.remove(c0188f);
        }
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public void setAdapter(aa aaVar) {
        int i = 0;
        if (this.mAdapter != null) {
            this.mAdapter.setViewPagerObserver(null);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int i2 = 0; i2 < this.mItems.size(); i2++) {
                C0491b c0491b = (C0491b) this.mItems.get(i2);
                this.mAdapter.destroyItem((ViewGroup) this, c0491b.f1281b, c0491b.f1280a);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        aa aaVar2 = this.mAdapter;
        this.mAdapter = aaVar;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new C0495h(this);
            }
            this.mAdapter.setViewPagerObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean z = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (z) {
                requestLayout();
            } else {
                populate();
            }
        }
        if (this.mAdapterChangeListeners != null && !this.mAdapterChangeListeners.isEmpty()) {
            int size = this.mAdapterChangeListeners.size();
            while (i < size) {
                ((C0180e) this.mAdapterChangeListeners.get(i)).mo164a(this, aaVar2, aaVar);
                i++;
            }
        }
    }

    void setChildrenDrawingOrderEnabledCompat(boolean z) {
        if (VERSION.SDK_INT >= 7) {
            if (this.mSetChildrenDrawingOrderEnabled == null) {
                try {
                    this.mSetChildrenDrawingOrderEnabled = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
                } catch (Throwable e) {
                    Log.e(TAG, "Can't find setChildrenDrawingOrderEnabled", e);
                }
            }
            try {
                this.mSetChildrenDrawingOrderEnabled.invoke(this, new Object[]{Boolean.valueOf(z)});
            } catch (Throwable e2) {
                Log.e(TAG, "Error changing children drawing order", e2);
            }
        }
    }

    public void setCurrentItem(int i) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, !this.mFirstLayout, false);
    }

    public void setCurrentItem(int i, boolean z) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, z, false);
    }

    void setCurrentItemInternal(int i, boolean z, boolean z2) {
        setCurrentItemInternal(i, z, z2, 0);
    }

    void setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        boolean z3 = false;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (z2 || this.mCurItem != i || this.mItems.size() == 0) {
            if (i < 0) {
                i = 0;
            } else if (i >= this.mAdapter.getCount()) {
                i = this.mAdapter.getCount() - 1;
            }
            int i3 = this.mOffscreenPageLimit;
            if (i > this.mCurItem + i3 || i < this.mCurItem - i3) {
                for (int i4 = 0; i4 < this.mItems.size(); i4++) {
                    ((C0491b) this.mItems.get(i4)).f1282c = true;
                }
            }
            if (this.mCurItem != i) {
                z3 = true;
            }
            if (this.mFirstLayout) {
                this.mCurItem = i;
                if (z3) {
                    dispatchOnPageSelected(i);
                }
                requestLayout();
                return;
            }
            populate(i);
            scrollToItem(i, z, i2, z3);
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    C0188f setInternalPageChangeListener(C0188f c0188f) {
        C0188f c0188f2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = c0188f;
        return c0188f2;
    }

    public void setOffscreenPageLimit(int i) {
        if (i < 1) {
            Log.w(TAG, "Requested offscreen page limit " + i + " too small; defaulting to " + 1);
            i = 1;
        }
        if (i != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = i;
            populate();
        }
    }

    @Deprecated
    public void setOnPageChangeListener(C0188f c0188f) {
        this.mOnPageChangeListener = c0188f;
    }

    public void setPageMargin(int i) {
        int i2 = this.mPageMargin;
        this.mPageMargin = i;
        int width = getWidth();
        recomputeScrollPosition(width, width, i, i2);
        requestLayout();
    }

    public void setPageMarginDrawable(int i) {
        setPageMarginDrawable(C0235a.m1066a(getContext(), i));
    }

    public void setPageMarginDrawable(Drawable drawable) {
        this.mMarginDrawable = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setPageTransformer(boolean z, C0494g c0494g) {
        setPageTransformer(z, c0494g, 2);
    }

    public void setPageTransformer(boolean z, C0494g c0494g, int i) {
        int i2 = 1;
        if (VERSION.SDK_INT >= 11) {
            boolean z2 = c0494g != null;
            int i3 = z2 != (this.mPageTransformer != null) ? 1 : 0;
            this.mPageTransformer = c0494g;
            setChildrenDrawingOrderEnabledCompat(z2);
            if (z2) {
                if (z) {
                    i2 = 2;
                }
                this.mDrawingOrder = i2;
                this.mPageTransformerLayerType = i;
            } else {
                this.mDrawingOrder = 0;
            }
            if (i3 != 0) {
                populate();
            }
        }
    }

    void setScrollState(int i) {
        if (this.mScrollState != i) {
            this.mScrollState = i;
            if (this.mPageTransformer != null) {
                enableLayers(i != 0);
            }
            dispatchOnScrollStateChanged(i);
        }
    }

    void smoothScrollTo(int i, int i2) {
        smoothScrollTo(i, i2, 0);
    }

    void smoothScrollTo(int i, int i2, int i3) {
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        int i4;
        boolean z = (this.mScroller == null || this.mScroller.isFinished()) ? false : true;
        if (z) {
            int currX = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
            this.mScroller.abortAnimation();
            setScrollingCacheEnabled(false);
            i4 = currX;
        } else {
            i4 = getScrollX();
        }
        int scrollY = getScrollY();
        int i5 = i - i4;
        int i6 = i2 - scrollY;
        if (i5 == 0 && i6 == 0) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        currX = getClientWidth();
        int i7 = currX / 2;
        float distanceInfluenceForSnapDuration = (((float) i7) * distanceInfluenceForSnapDuration(Math.min(1.0f, (((float) Math.abs(i5)) * 1.0f) / ((float) currX)))) + ((float) i7);
        int abs = Math.abs(i3);
        i7 = Math.min(abs > 0 ? Math.round(1000.0f * Math.abs(distanceInfluenceForSnapDuration / ((float) abs))) * 4 : (int) (((((float) Math.abs(i5)) / ((((float) currX) * this.mAdapter.getPageWidth(this.mCurItem)) + ((float) this.mPageMargin))) + 1.0f) * 100.0f), MAX_SETTLE_DURATION);
        this.mIsScrollStarted = false;
        this.mScroller.startScroll(i4, scrollY, i5, i6, i7);
        ah.m2799c(this);
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mMarginDrawable;
    }
}
