package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.p023a.C0510a;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.view.p023a.C0531e.C0530m;
import android.support.v4.view.p023a.C0556o;
import android.support.v7.widget.RecyclerView.C0908i;
import android.support.v7.widget.RecyclerView.C0910h;
import android.support.v7.widget.RecyclerView.C0910h.C0939a;
import android.support.v7.widget.RecyclerView.C0910h.C0940b;
import android.support.v7.widget.RecyclerView.C0947o;
import android.support.v7.widget.RecyclerView.C0952s;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

public class StaggeredGridLayoutManager extends C0910h {
    /* renamed from: A */
    private SavedState f2640A;
    /* renamed from: B */
    private int f2641B;
    /* renamed from: C */
    private final Rect f2642C = new Rect();
    /* renamed from: D */
    private final C0966a f2643D = new C0966a(this);
    /* renamed from: E */
    private boolean f2644E = false;
    /* renamed from: F */
    private boolean f2645F = true;
    /* renamed from: G */
    private int[] f2646G;
    /* renamed from: H */
    private final Runnable f2647H = new C09631(this);
    /* renamed from: a */
    C0968c[] f2648a;
    /* renamed from: b */
    au f2649b;
    /* renamed from: c */
    au f2650c;
    /* renamed from: d */
    boolean f2651d = false;
    /* renamed from: e */
    boolean f2652e = false;
    /* renamed from: f */
    int f2653f = -1;
    /* renamed from: g */
    int f2654g = Integer.MIN_VALUE;
    /* renamed from: h */
    LazySpanLookup f2655h = new LazySpanLookup();
    /* renamed from: i */
    private int f2656i = -1;
    /* renamed from: j */
    private int f2657j;
    /* renamed from: k */
    private int f2658k;
    /* renamed from: l */
    private final an f2659l;
    /* renamed from: m */
    private BitSet f2660m;
    /* renamed from: n */
    private int f2661n = 2;
    /* renamed from: o */
    private boolean f2662o;
    /* renamed from: z */
    private boolean f2663z;

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$1 */
    class C09631 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ StaggeredGridLayoutManager f2608a;

        C09631(StaggeredGridLayoutManager staggeredGridLayoutManager) {
            this.f2608a = staggeredGridLayoutManager;
        }

        public void run() {
            this.f2608a.m5138f();
        }
    }

    static class LazySpanLookup {
        /* renamed from: a */
        int[] f2613a;
        /* renamed from: b */
        List<FullSpanItem> f2614b;

        static class FullSpanItem implements Parcelable {
            public static final Creator<FullSpanItem> CREATOR = new C09641();
            /* renamed from: a */
            int f2609a;
            /* renamed from: b */
            int f2610b;
            /* renamed from: c */
            int[] f2611c;
            /* renamed from: d */
            boolean f2612d;

            /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem$1 */
            static class C09641 implements Creator<FullSpanItem> {
                C09641() {
                }

                /* renamed from: a */
                public FullSpanItem m5007a(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                /* renamed from: a */
                public FullSpanItem[] m5008a(int i) {
                    return new FullSpanItem[i];
                }

                public /* synthetic */ Object createFromParcel(Parcel parcel) {
                    return m5007a(parcel);
                }

                public /* synthetic */ Object[] newArray(int i) {
                    return m5008a(i);
                }
            }

            public FullSpanItem(Parcel parcel) {
                boolean z = true;
                this.f2609a = parcel.readInt();
                this.f2610b = parcel.readInt();
                if (parcel.readInt() != 1) {
                    z = false;
                }
                this.f2612d = z;
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    this.f2611c = new int[readInt];
                    parcel.readIntArray(this.f2611c);
                }
            }

            /* renamed from: a */
            int m5009a(int i) {
                return this.f2611c == null ? 0 : this.f2611c[i];
            }

            public int describeContents() {
                return 0;
            }

            public String toString() {
                return "FullSpanItem{mPosition=" + this.f2609a + ", mGapDir=" + this.f2610b + ", mHasUnwantedGapAfter=" + this.f2612d + ", mGapPerSpan=" + Arrays.toString(this.f2611c) + '}';
            }

            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.f2609a);
                parcel.writeInt(this.f2610b);
                parcel.writeInt(this.f2612d ? 1 : 0);
                if (this.f2611c == null || this.f2611c.length <= 0) {
                    parcel.writeInt(0);
                    return;
                }
                parcel.writeInt(this.f2611c.length);
                parcel.writeIntArray(this.f2611c);
            }
        }

        LazySpanLookup() {
        }

        /* renamed from: c */
        private void m5010c(int i, int i2) {
            if (this.f2614b != null) {
                int i3 = i + i2;
                for (int size = this.f2614b.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = (FullSpanItem) this.f2614b.get(size);
                    if (fullSpanItem.f2609a >= i) {
                        if (fullSpanItem.f2609a < i3) {
                            this.f2614b.remove(size);
                        } else {
                            fullSpanItem.f2609a -= i2;
                        }
                    }
                }
            }
        }

        /* renamed from: d */
        private void m5011d(int i, int i2) {
            if (this.f2614b != null) {
                for (int size = this.f2614b.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = (FullSpanItem) this.f2614b.get(size);
                    if (fullSpanItem.f2609a >= i) {
                        fullSpanItem.f2609a += i2;
                    }
                }
            }
        }

        /* renamed from: g */
        private int m5012g(int i) {
            if (this.f2614b == null) {
                return -1;
            }
            FullSpanItem f = m5024f(i);
            if (f != null) {
                this.f2614b.remove(f);
            }
            int size = this.f2614b.size();
            int i2 = 0;
            while (i2 < size) {
                if (((FullSpanItem) this.f2614b.get(i2)).f2609a >= i) {
                    break;
                }
                i2++;
            }
            i2 = -1;
            if (i2 == -1) {
                return -1;
            }
            f = (FullSpanItem) this.f2614b.get(i2);
            this.f2614b.remove(i2);
            return f.f2609a;
        }

        /* renamed from: a */
        int m5013a(int i) {
            if (this.f2614b != null) {
                for (int size = this.f2614b.size() - 1; size >= 0; size--) {
                    if (((FullSpanItem) this.f2614b.get(size)).f2609a >= i) {
                        this.f2614b.remove(size);
                    }
                }
            }
            return m5019b(i);
        }

        /* renamed from: a */
        public FullSpanItem m5014a(int i, int i2, int i3, boolean z) {
            if (this.f2614b == null) {
                return null;
            }
            int size = this.f2614b.size();
            for (int i4 = 0; i4 < size; i4++) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.f2614b.get(i4);
                if (fullSpanItem.f2609a >= i2) {
                    return null;
                }
                if (fullSpanItem.f2609a >= i) {
                    if (i3 == 0 || fullSpanItem.f2610b == i3) {
                        return fullSpanItem;
                    }
                    if (z && fullSpanItem.f2612d) {
                        return fullSpanItem;
                    }
                }
            }
            return null;
        }

        /* renamed from: a */
        void m5015a() {
            if (this.f2613a != null) {
                Arrays.fill(this.f2613a, -1);
            }
            this.f2614b = null;
        }

        /* renamed from: a */
        void m5016a(int i, int i2) {
            if (this.f2613a != null && i < this.f2613a.length) {
                m5023e(i + i2);
                System.arraycopy(this.f2613a, i + i2, this.f2613a, i, (this.f2613a.length - i) - i2);
                Arrays.fill(this.f2613a, this.f2613a.length - i2, this.f2613a.length, -1);
                m5010c(i, i2);
            }
        }

        /* renamed from: a */
        void m5017a(int i, C0968c c0968c) {
            m5023e(i);
            this.f2613a[i] = c0968c.f2638e;
        }

        /* renamed from: a */
        public void m5018a(FullSpanItem fullSpanItem) {
            if (this.f2614b == null) {
                this.f2614b = new ArrayList();
            }
            int size = this.f2614b.size();
            for (int i = 0; i < size; i++) {
                FullSpanItem fullSpanItem2 = (FullSpanItem) this.f2614b.get(i);
                if (fullSpanItem2.f2609a == fullSpanItem.f2609a) {
                    this.f2614b.remove(i);
                }
                if (fullSpanItem2.f2609a >= fullSpanItem.f2609a) {
                    this.f2614b.add(i, fullSpanItem);
                    return;
                }
            }
            this.f2614b.add(fullSpanItem);
        }

        /* renamed from: b */
        int m5019b(int i) {
            if (this.f2613a == null || i >= this.f2613a.length) {
                return -1;
            }
            int g = m5012g(i);
            if (g == -1) {
                Arrays.fill(this.f2613a, i, this.f2613a.length, -1);
                return this.f2613a.length;
            }
            Arrays.fill(this.f2613a, i, g + 1, -1);
            return g + 1;
        }

        /* renamed from: b */
        void m5020b(int i, int i2) {
            if (this.f2613a != null && i < this.f2613a.length) {
                m5023e(i + i2);
                System.arraycopy(this.f2613a, i, this.f2613a, i + i2, (this.f2613a.length - i) - i2);
                Arrays.fill(this.f2613a, i, i + i2, -1);
                m5011d(i, i2);
            }
        }

        /* renamed from: c */
        int m5021c(int i) {
            return (this.f2613a == null || i >= this.f2613a.length) ? -1 : this.f2613a[i];
        }

        /* renamed from: d */
        int m5022d(int i) {
            int length = this.f2613a.length;
            while (length <= i) {
                length *= 2;
            }
            return length;
        }

        /* renamed from: e */
        void m5023e(int i) {
            if (this.f2613a == null) {
                this.f2613a = new int[(Math.max(i, 10) + 1)];
                Arrays.fill(this.f2613a, -1);
            } else if (i >= this.f2613a.length) {
                Object obj = this.f2613a;
                this.f2613a = new int[m5022d(i)];
                System.arraycopy(obj, 0, this.f2613a, 0, obj.length);
                Arrays.fill(this.f2613a, obj.length, this.f2613a.length, -1);
            }
        }

        /* renamed from: f */
        public FullSpanItem m5024f(int i) {
            if (this.f2614b == null) {
                return null;
            }
            for (int size = this.f2614b.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = (FullSpanItem) this.f2614b.get(size);
                if (fullSpanItem.f2609a == i) {
                    return fullSpanItem;
                }
            }
            return null;
        }
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C09651();
        /* renamed from: a */
        int f2615a;
        /* renamed from: b */
        int f2616b;
        /* renamed from: c */
        int f2617c;
        /* renamed from: d */
        int[] f2618d;
        /* renamed from: e */
        int f2619e;
        /* renamed from: f */
        int[] f2620f;
        /* renamed from: g */
        List<FullSpanItem> f2621g;
        /* renamed from: h */
        boolean f2622h;
        /* renamed from: i */
        boolean f2623i;
        /* renamed from: j */
        boolean f2624j;

        /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$SavedState$1 */
        static class C09651 implements Creator<SavedState> {
            C09651() {
            }

            /* renamed from: a */
            public SavedState m5025a(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] m5026a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m5025a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m5026a(i);
            }
        }

        SavedState(Parcel parcel) {
            boolean z = true;
            this.f2615a = parcel.readInt();
            this.f2616b = parcel.readInt();
            this.f2617c = parcel.readInt();
            if (this.f2617c > 0) {
                this.f2618d = new int[this.f2617c];
                parcel.readIntArray(this.f2618d);
            }
            this.f2619e = parcel.readInt();
            if (this.f2619e > 0) {
                this.f2620f = new int[this.f2619e];
                parcel.readIntArray(this.f2620f);
            }
            this.f2622h = parcel.readInt() == 1;
            this.f2623i = parcel.readInt() == 1;
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.f2624j = z;
            this.f2621g = parcel.readArrayList(FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.f2617c = savedState.f2617c;
            this.f2615a = savedState.f2615a;
            this.f2616b = savedState.f2616b;
            this.f2618d = savedState.f2618d;
            this.f2619e = savedState.f2619e;
            this.f2620f = savedState.f2620f;
            this.f2622h = savedState.f2622h;
            this.f2623i = savedState.f2623i;
            this.f2624j = savedState.f2624j;
            this.f2621g = savedState.f2621g;
        }

        /* renamed from: a */
        void m5027a() {
            this.f2618d = null;
            this.f2617c = 0;
            this.f2619e = 0;
            this.f2620f = null;
            this.f2621g = null;
        }

        /* renamed from: b */
        void m5028b() {
            this.f2618d = null;
            this.f2617c = 0;
            this.f2615a = -1;
            this.f2616b = -1;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            int i2 = 1;
            parcel.writeInt(this.f2615a);
            parcel.writeInt(this.f2616b);
            parcel.writeInt(this.f2617c);
            if (this.f2617c > 0) {
                parcel.writeIntArray(this.f2618d);
            }
            parcel.writeInt(this.f2619e);
            if (this.f2619e > 0) {
                parcel.writeIntArray(this.f2620f);
            }
            parcel.writeInt(this.f2622h ? 1 : 0);
            parcel.writeInt(this.f2623i ? 1 : 0);
            if (!this.f2624j) {
                i2 = 0;
            }
            parcel.writeInt(i2);
            parcel.writeList(this.f2621g);
        }
    }

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$a */
    class C0966a {
        /* renamed from: a */
        int f2625a;
        /* renamed from: b */
        int f2626b;
        /* renamed from: c */
        boolean f2627c;
        /* renamed from: d */
        boolean f2628d;
        /* renamed from: e */
        boolean f2629e;
        /* renamed from: f */
        int[] f2630f;
        /* renamed from: g */
        final /* synthetic */ StaggeredGridLayoutManager f2631g;

        public C0966a(StaggeredGridLayoutManager staggeredGridLayoutManager) {
            this.f2631g = staggeredGridLayoutManager;
            m5029a();
        }

        /* renamed from: a */
        void m5029a() {
            this.f2625a = -1;
            this.f2626b = Integer.MIN_VALUE;
            this.f2627c = false;
            this.f2628d = false;
            this.f2629e = false;
            if (this.f2630f != null) {
                Arrays.fill(this.f2630f, -1);
            }
        }

        /* renamed from: a */
        void m5030a(int i) {
            if (this.f2627c) {
                this.f2626b = this.f2631g.f2649b.mo949d() - i;
            } else {
                this.f2626b = this.f2631g.f2649b.mo947c() + i;
            }
        }

        /* renamed from: a */
        void m5031a(C0968c[] c0968cArr) {
            int length = c0968cArr.length;
            if (this.f2630f == null || this.f2630f.length < length) {
                this.f2630f = new int[this.f2631g.f2648a.length];
            }
            for (int i = 0; i < length; i++) {
                this.f2630f[i] = c0968cArr[i].m5035a(Integer.MIN_VALUE);
            }
        }

        /* renamed from: b */
        void m5032b() {
            this.f2626b = this.f2627c ? this.f2631g.f2649b.mo949d() : this.f2631g.f2649b.mo947c();
        }
    }

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$b */
    public static class C0967b extends C0908i {
        /* renamed from: a */
        C0968c f2632a;
        /* renamed from: b */
        boolean f2633b;

        public C0967b(int i, int i2) {
            super(i, i2);
        }

        public C0967b(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public C0967b(LayoutParams layoutParams) {
            super(layoutParams);
        }

        public C0967b(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        /* renamed from: a */
        public boolean m5033a() {
            return this.f2633b;
        }

        /* renamed from: b */
        public final int m5034b() {
            return this.f2632a == null ? -1 : this.f2632a.f2638e;
        }
    }

    /* renamed from: android.support.v7.widget.StaggeredGridLayoutManager$c */
    class C0968c {
        /* renamed from: a */
        ArrayList<View> f2634a = new ArrayList();
        /* renamed from: b */
        int f2635b = Integer.MIN_VALUE;
        /* renamed from: c */
        int f2636c = Integer.MIN_VALUE;
        /* renamed from: d */
        int f2637d = 0;
        /* renamed from: e */
        final int f2638e;
        /* renamed from: f */
        final /* synthetic */ StaggeredGridLayoutManager f2639f;

        C0968c(StaggeredGridLayoutManager staggeredGridLayoutManager, int i) {
            this.f2639f = staggeredGridLayoutManager;
            this.f2638e = i;
        }

        /* renamed from: a */
        int m5035a(int i) {
            if (this.f2635b != Integer.MIN_VALUE) {
                return this.f2635b;
            }
            if (this.f2634a.size() == 0) {
                return i;
            }
            m5039a();
            return this.f2635b;
        }

        /* renamed from: a */
        int m5036a(int i, int i2, boolean z) {
            return m5037a(i, i2, false, false, z);
        }

        /* renamed from: a */
        int m5037a(int i, int i2, boolean z, boolean z2, boolean z3) {
            int c = this.f2639f.f2649b.mo947c();
            int d = this.f2639f.f2649b.mo949d();
            int i3 = i2 > i ? 1 : -1;
            while (i != i2) {
                Object obj;
                View view = (View) this.f2634a.get(i);
                int a = this.f2639f.f2649b.mo944a(view);
                int b = this.f2639f.f2649b.mo946b(view);
                if (z3) {
                    obj = a <= d ? 1 : null;
                } else if (a < d) {
                    int i4 = 1;
                } else {
                    obj = null;
                }
                Object obj2 = z3 ? b >= c ? 1 : null : b > c ? 1 : null;
                if (!(obj == null || r2 == null)) {
                    if (z && z2) {
                        if (a >= c && b <= d) {
                            return this.f2639f.m4573d(view);
                        }
                    } else if (z2) {
                        return this.f2639f.m4573d(view);
                    } else {
                        if (a < c || b > d) {
                            return this.f2639f.m4573d(view);
                        }
                    }
                }
                i += i3;
            }
            return -1;
        }

        /* renamed from: a */
        public View m5038a(int i, int i2) {
            View view = null;
            int i3;
            View view2;
            if (i2 == -1) {
                int size = this.f2634a.size();
                i3 = 0;
                while (i3 < size) {
                    view2 = (View) this.f2634a.get(i3);
                    if ((this.f2639f.f2651d && this.f2639f.m4573d(view2) <= i) || ((!this.f2639f.f2651d && this.f2639f.m4573d(view2) >= i) || !view2.hasFocusable())) {
                        break;
                    }
                    i3++;
                    view = view2;
                }
                return view;
            }
            i3 = this.f2634a.size() - 1;
            while (i3 >= 0) {
                view2 = (View) this.f2634a.get(i3);
                if (this.f2639f.f2651d && this.f2639f.m4573d(view2) >= i) {
                    break;
                } else if (this.f2639f.f2651d || this.f2639f.m4573d(view2) > i) {
                    if (!view2.hasFocusable()) {
                        break;
                    }
                    i3--;
                    view = view2;
                } else {
                    return view;
                }
            }
            return view;
        }

        /* renamed from: a */
        void m5039a() {
            View view = (View) this.f2634a.get(0);
            C0967b c = m5045c(view);
            this.f2635b = this.f2639f.f2649b.mo944a(view);
            if (c.f2633b) {
                FullSpanItem f = this.f2639f.f2655h.m5024f(c.m4476f());
                if (f != null && f.f2610b == -1) {
                    this.f2635b -= f.m5009a(this.f2638e);
                }
            }
        }

        /* renamed from: a */
        void m5040a(View view) {
            C0967b c = m5045c(view);
            c.f2632a = this;
            this.f2634a.add(0, view);
            this.f2635b = Integer.MIN_VALUE;
            if (this.f2634a.size() == 1) {
                this.f2636c = Integer.MIN_VALUE;
            }
            if (c.m4474d() || c.m4475e()) {
                this.f2637d += this.f2639f.f2649b.mo952e(view);
            }
        }

        /* renamed from: a */
        void m5041a(boolean z, int i) {
            int b = z ? m5043b(Integer.MIN_VALUE) : m5035a(Integer.MIN_VALUE);
            m5050e();
            if (b != Integer.MIN_VALUE) {
                if (z && b < this.f2639f.f2649b.mo949d()) {
                    return;
                }
                if (z || b <= this.f2639f.f2649b.mo947c()) {
                    if (i != Integer.MIN_VALUE) {
                        b += i;
                    }
                    this.f2636c = b;
                    this.f2635b = b;
                }
            }
        }

        /* renamed from: b */
        int m5042b() {
            if (this.f2635b != Integer.MIN_VALUE) {
                return this.f2635b;
            }
            m5039a();
            return this.f2635b;
        }

        /* renamed from: b */
        int m5043b(int i) {
            if (this.f2636c != Integer.MIN_VALUE) {
                return this.f2636c;
            }
            if (this.f2634a.size() == 0) {
                return i;
            }
            m5046c();
            return this.f2636c;
        }

        /* renamed from: b */
        void m5044b(View view) {
            C0967b c = m5045c(view);
            c.f2632a = this;
            this.f2634a.add(view);
            this.f2636c = Integer.MIN_VALUE;
            if (this.f2634a.size() == 1) {
                this.f2635b = Integer.MIN_VALUE;
            }
            if (c.m4474d() || c.m4475e()) {
                this.f2637d += this.f2639f.f2649b.mo952e(view);
            }
        }

        /* renamed from: c */
        C0967b m5045c(View view) {
            return (C0967b) view.getLayoutParams();
        }

        /* renamed from: c */
        void m5046c() {
            View view = (View) this.f2634a.get(this.f2634a.size() - 1);
            C0967b c = m5045c(view);
            this.f2636c = this.f2639f.f2649b.mo946b(view);
            if (c.f2633b) {
                FullSpanItem f = this.f2639f.f2655h.m5024f(c.m4476f());
                if (f != null && f.f2610b == 1) {
                    this.f2636c = f.m5009a(this.f2638e) + this.f2636c;
                }
            }
        }

        /* renamed from: c */
        void m5047c(int i) {
            this.f2635b = i;
            this.f2636c = i;
        }

        /* renamed from: d */
        int m5048d() {
            if (this.f2636c != Integer.MIN_VALUE) {
                return this.f2636c;
            }
            m5046c();
            return this.f2636c;
        }

        /* renamed from: d */
        void m5049d(int i) {
            if (this.f2635b != Integer.MIN_VALUE) {
                this.f2635b += i;
            }
            if (this.f2636c != Integer.MIN_VALUE) {
                this.f2636c += i;
            }
        }

        /* renamed from: e */
        void m5050e() {
            this.f2634a.clear();
            m5051f();
            this.f2637d = 0;
        }

        /* renamed from: f */
        void m5051f() {
            this.f2635b = Integer.MIN_VALUE;
            this.f2636c = Integer.MIN_VALUE;
        }

        /* renamed from: g */
        void m5052g() {
            int size = this.f2634a.size();
            View view = (View) this.f2634a.remove(size - 1);
            C0967b c = m5045c(view);
            c.f2632a = null;
            if (c.m4474d() || c.m4475e()) {
                this.f2637d -= this.f2639f.f2649b.mo952e(view);
            }
            if (size == 1) {
                this.f2635b = Integer.MIN_VALUE;
            }
            this.f2636c = Integer.MIN_VALUE;
        }

        /* renamed from: h */
        void m5053h() {
            View view = (View) this.f2634a.remove(0);
            C0967b c = m5045c(view);
            c.f2632a = null;
            if (this.f2634a.size() == 0) {
                this.f2636c = Integer.MIN_VALUE;
            }
            if (c.m4474d() || c.m4475e()) {
                this.f2637d -= this.f2639f.f2649b.mo952e(view);
            }
            this.f2635b = Integer.MIN_VALUE;
        }

        /* renamed from: i */
        public int m5054i() {
            return this.f2637d;
        }

        /* renamed from: j */
        public int m5055j() {
            return this.f2639f.f2651d ? m5036a(this.f2634a.size() - 1, -1, true) : m5036a(0, this.f2634a.size(), true);
        }

        /* renamed from: k */
        public int m5056k() {
            return this.f2639f.f2651d ? m5036a(0, this.f2634a.size(), true) : m5036a(this.f2634a.size() - 1, -1, true);
        }
    }

    public StaggeredGridLayoutManager(int i, int i2) {
        boolean z = true;
        this.f2657j = i2;
        m5102a(i);
        if (this.f2661n == 0) {
            z = false;
        }
        m4570c(z);
        this.f2659l = new an();
        m5057M();
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        boolean z = true;
        C0940b a = C0910h.m4481a(context, attributeSet, i, i2);
        m5122b(a.f2493a);
        m5102a(a.f2494b);
        m5117a(a.f2495c);
        if (this.f2661n == 0) {
            z = false;
        }
        m4570c(z);
        this.f2659l = new an();
        m5057M();
    }

    /* renamed from: M */
    private void m5057M() {
        this.f2649b = au.m5517a(this, this.f2657j);
        this.f2650c = au.m5517a(this, 1 - this.f2657j);
    }

    /* renamed from: N */
    private void m5058N() {
        boolean z = true;
        if (this.f2657j == 1 || !m5144i()) {
            this.f2652e = this.f2651d;
            return;
        }
        if (this.f2651d) {
            z = false;
        }
        this.f2652e = z;
    }

    /* renamed from: O */
    private void m5059O() {
        if (this.f2650c.mo956h() != 1073741824) {
            float f = BitmapDescriptorFactory.HUE_RED;
            int w = m4615w();
            int i = 0;
            while (i < w) {
                float f2;
                View h = m4596h(i);
                float e = (float) this.f2650c.mo952e(h);
                if (e < f) {
                    f2 = f;
                } else {
                    f2 = Math.max(f, ((C0967b) h.getLayoutParams()).m5033a() ? (1.0f * e) / ((float) this.f2656i) : e);
                }
                i++;
                f = f2;
            }
            i = this.f2658k;
            int round = Math.round(((float) this.f2656i) * f);
            if (this.f2650c.mo956h() == Integer.MIN_VALUE) {
                round = Math.min(round, this.f2650c.mo953f());
            }
            m5135e(round);
            if (this.f2658k != i) {
                for (int i2 = 0; i2 < w; i2++) {
                    View h2 = m4596h(i2);
                    C0967b c0967b = (C0967b) h2.getLayoutParams();
                    if (!c0967b.f2633b) {
                        if (m5144i() && this.f2657j == 1) {
                            h2.offsetLeftAndRight(((-((this.f2656i - 1) - c0967b.f2632a.f2638e)) * this.f2658k) - ((-((this.f2656i - 1) - c0967b.f2632a.f2638e)) * i));
                        } else {
                            int i3 = c0967b.f2632a.f2638e * this.f2658k;
                            round = c0967b.f2632a.f2638e * i;
                            if (this.f2657j == 1) {
                                h2.offsetLeftAndRight(i3 - round);
                            } else {
                                h2.offsetTopAndBottom(i3 - round);
                            }
                        }
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private int m5060a(C0947o c0947o, an anVar, C0952s c0952s) {
        int q;
        this.f2660m.set(0, this.f2656i, true);
        int i = this.f2659l.f2881i ? anVar.f2877e == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE : anVar.f2877e == 1 ? anVar.f2879g + anVar.f2874b : anVar.f2878f - anVar.f2874b;
        m5062a(anVar.f2877e, i);
        int d = this.f2652e ? this.f2649b.mo949d() : this.f2649b.mo947c();
        Object obj = null;
        while (anVar.m5465a(c0952s) && (this.f2659l.f2881i || !this.f2660m.isEmpty())) {
            C0968c c0968c;
            int e;
            int e2;
            View a = anVar.m5464a(c0947o);
            C0967b c0967b = (C0967b) a.getLayoutParams();
            int f = c0967b.m4476f();
            int c = this.f2655h.m5021c(f);
            Object obj2 = c == -1 ? 1 : null;
            if (obj2 != null) {
                C0968c a2 = c0967b.f2633b ? this.f2648a[0] : m5061a(anVar);
                this.f2655h.m5017a(f, a2);
                c0968c = a2;
            } else {
                c0968c = this.f2648a[c];
            }
            c0967b.f2632a = c0968c;
            if (anVar.f2877e == 1) {
                m4556b(a);
            } else {
                m4557b(a, 0);
            }
            m5070a(a, c0967b, false);
            if (anVar.f2877e == 1) {
                q = c0967b.f2633b ? m5088q(d) : c0968c.m5043b(d);
                e = q + this.f2649b.mo952e(a);
                if (obj2 == null || !c0967b.f2633b) {
                    c = q;
                } else {
                    FullSpanItem m = m5083m(q);
                    m.f2610b = -1;
                    m.f2609a = f;
                    this.f2655h.m5018a(m);
                    c = q;
                }
            } else {
                q = c0967b.f2633b ? m5086p(d) : c0968c.m5035a(d);
                c = q - this.f2649b.mo952e(a);
                if (obj2 != null && c0967b.f2633b) {
                    FullSpanItem n = m5084n(q);
                    n.f2610b = 1;
                    n.f2609a = f;
                    this.f2655h.m5018a(n);
                }
                e = q;
            }
            if (c0967b.f2633b && anVar.f2876d == -1) {
                if (obj2 != null) {
                    this.f2644E = true;
                } else {
                    obj = anVar.f2877e == 1 ? !m5148l() ? 1 : null : !m5149m() ? 1 : null;
                    if (obj != null) {
                        FullSpanItem f2 = this.f2655h.m5024f(f);
                        if (f2 != null) {
                            f2.f2612d = true;
                        }
                        this.f2644E = true;
                    }
                }
            }
            m5069a(a, c0967b, anVar);
            if (m5144i() && this.f2657j == 1) {
                q = c0967b.f2633b ? this.f2650c.mo949d() : this.f2650c.mo949d() - (((this.f2656i - 1) - c0968c.f2638e) * this.f2658k);
                e2 = q - this.f2650c.mo952e(a);
                f = q;
            } else {
                q = c0967b.f2633b ? this.f2650c.mo947c() : (c0968c.f2638e * this.f2658k) + this.f2650c.mo947c();
                f = q + this.f2650c.mo952e(a);
                e2 = q;
            }
            if (this.f2657j == 1) {
                m4529a(a, e2, c, f, e);
            } else {
                m4529a(a, c, e2, e, f);
            }
            if (c0967b.f2633b) {
                m5062a(this.f2659l.f2877e, i);
            } else {
                m5067a(c0968c, this.f2659l.f2877e, i);
            }
            m5065a(c0947o, this.f2659l);
            if (this.f2659l.f2880h && a.hasFocusable()) {
                if (c0967b.f2633b) {
                    this.f2660m.clear();
                } else {
                    this.f2660m.set(c0968c.f2638e, false);
                }
            }
            obj = 1;
        }
        if (obj == null) {
            m5065a(c0947o, this.f2659l);
        }
        q = this.f2659l.f2877e == -1 ? this.f2649b.mo947c() - m5086p(this.f2649b.mo947c()) : m5088q(this.f2649b.mo949d()) - this.f2649b.mo949d();
        return q > 0 ? Math.min(anVar.f2874b, q) : 0;
    }

    /* renamed from: a */
    private C0968c m5061a(an anVar) {
        int i;
        int i2;
        C0968c c0968c = null;
        int i3 = -1;
        if (m5091s(anVar.f2877e)) {
            i = this.f2656i - 1;
            i2 = -1;
        } else {
            i = 0;
            i2 = this.f2656i;
            i3 = 1;
        }
        int c;
        int i4;
        C0968c c0968c2;
        int b;
        C0968c c0968c3;
        if (anVar.f2877e == 1) {
            c = this.f2649b.mo947c();
            i4 = i;
            i = Integer.MAX_VALUE;
            while (i4 != i2) {
                c0968c2 = this.f2648a[i4];
                b = c0968c2.m5043b(c);
                if (b < i) {
                    c0968c3 = c0968c2;
                } else {
                    b = i;
                    c0968c3 = c0968c;
                }
                i4 += i3;
                c0968c = c0968c3;
                i = b;
            }
        } else {
            c = this.f2649b.mo949d();
            i4 = i;
            i = Integer.MIN_VALUE;
            while (i4 != i2) {
                c0968c2 = this.f2648a[i4];
                b = c0968c2.m5035a(c);
                if (b > i) {
                    c0968c3 = c0968c2;
                } else {
                    b = i;
                    c0968c3 = c0968c;
                }
                i4 += i3;
                c0968c = c0968c3;
                i = b;
            }
        }
        return c0968c;
    }

    /* renamed from: a */
    private void m5062a(int i, int i2) {
        for (int i3 = 0; i3 < this.f2656i; i3++) {
            if (!this.f2648a[i3].f2634a.isEmpty()) {
                m5067a(this.f2648a[i3], i, i2);
            }
        }
    }

    /* renamed from: a */
    private void m5063a(C0947o c0947o, int i) {
        while (m4615w() > 0) {
            View h = m4596h(0);
            if (this.f2649b.mo946b(h) <= i && this.f2649b.mo948c(h) <= i) {
                C0967b c0967b = (C0967b) h.getLayoutParams();
                if (c0967b.f2633b) {
                    int i2 = 0;
                    while (i2 < this.f2656i) {
                        if (this.f2648a[i2].f2634a.size() != 1) {
                            i2++;
                        } else {
                            return;
                        }
                    }
                    for (i2 = 0; i2 < this.f2656i; i2++) {
                        this.f2648a[i2].m5053h();
                    }
                } else if (c0967b.f2632a.f2634a.size() != 1) {
                    c0967b.f2632a.m5053h();
                } else {
                    return;
                }
                m4533a(h, c0947o);
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    private void m5064a(C0947o c0947o, C0952s c0952s, boolean z) {
        C0966a c0966a = this.f2643D;
        if (!(this.f2640A == null && this.f2653f == -1) && c0952s.m4959e() == 0) {
            m4564c(c0947o);
            c0966a.m5029a();
            return;
        }
        boolean z2 = (c0966a.f2629e && this.f2653f == -1 && this.f2640A == null) ? false : true;
        if (z2) {
            c0966a.m5029a();
            if (this.f2640A != null) {
                m5066a(c0966a);
            } else {
                m5058N();
                c0966a.f2627c = this.f2652e;
            }
            m5109a(c0952s, c0966a);
            c0966a.f2629e = true;
        }
        if (this.f2640A == null && this.f2653f == -1 && !(c0966a.f2627c == this.f2662o && m5144i() == this.f2663z)) {
            this.f2655h.m5015a();
            c0966a.f2628d = true;
        }
        if (m4615w() > 0 && (this.f2640A == null || this.f2640A.f2617c < 1)) {
            int i;
            if (c0966a.f2628d) {
                for (i = 0; i < this.f2656i; i++) {
                    this.f2648a[i].m5050e();
                    if (c0966a.f2626b != Integer.MIN_VALUE) {
                        this.f2648a[i].m5047c(c0966a.f2626b);
                    }
                }
            } else if (z2 || this.f2643D.f2630f == null) {
                for (i = 0; i < this.f2656i; i++) {
                    this.f2648a[i].m5041a(this.f2652e, c0966a.f2626b);
                }
                this.f2643D.m5031a(this.f2648a);
            } else {
                for (i = 0; i < this.f2656i; i++) {
                    C0968c c0968c = this.f2648a[i];
                    c0968c.m5050e();
                    c0968c.m5047c(this.f2643D.f2630f[i]);
                }
            }
        }
        m4515a(c0947o);
        this.f2659l.f2873a = false;
        this.f2644E = false;
        m5135e(this.f2650c.mo953f());
        m5074b(c0966a.f2625a, c0952s);
        if (c0966a.f2627c) {
            m5082l(-1);
            m5060a(c0947o, this.f2659l, c0952s);
            m5082l(1);
            this.f2659l.f2875c = c0966a.f2625a + this.f2659l.f2876d;
            m5060a(c0947o, this.f2659l, c0952s);
        } else {
            m5082l(1);
            m5060a(c0947o, this.f2659l, c0952s);
            m5082l(-1);
            this.f2659l.f2875c = c0966a.f2625a + this.f2659l.f2876d;
            m5060a(c0947o, this.f2659l, c0952s);
        }
        m5059O();
        if (m4615w() > 0) {
            if (this.f2652e) {
                m5076b(c0947o, c0952s, true);
                m5078c(c0947o, c0952s, false);
            } else {
                m5078c(c0947o, c0952s, true);
                m5076b(c0947o, c0952s, false);
            }
        }
        if (z && !c0952s.m4955a()) {
            z2 = this.f2661n != 0 && m4615w() > 0 && (this.f2644E || m5140g() != null);
            if (z2) {
                m4549a(this.f2647H);
                if (m5138f()) {
                    z2 = true;
                    if (c0952s.m4955a()) {
                        this.f2643D.m5029a();
                    }
                    this.f2662o = c0966a.f2627c;
                    this.f2663z = m5144i();
                    if (z2) {
                        this.f2643D.m5029a();
                        m5064a(c0947o, c0952s, false);
                    }
                }
            }
        }
        z2 = false;
        if (c0952s.m4955a()) {
            this.f2643D.m5029a();
        }
        this.f2662o = c0966a.f2627c;
        this.f2663z = m5144i();
        if (z2) {
            this.f2643D.m5029a();
            m5064a(c0947o, c0952s, false);
        }
    }

    /* renamed from: a */
    private void m5065a(C0947o c0947o, an anVar) {
        if (anVar.f2873a && !anVar.f2881i) {
            if (anVar.f2874b == 0) {
                if (anVar.f2877e == -1) {
                    m5075b(c0947o, anVar.f2879g);
                } else {
                    m5063a(c0947o, anVar.f2878f);
                }
            } else if (anVar.f2877e == -1) {
                r0 = anVar.f2878f - m5085o(anVar.f2878f);
                m5075b(c0947o, r0 < 0 ? anVar.f2879g : anVar.f2879g - Math.min(r0, anVar.f2874b));
            } else {
                r0 = m5090r(anVar.f2879g) - anVar.f2879g;
                m5063a(c0947o, r0 < 0 ? anVar.f2878f : Math.min(r0, anVar.f2874b) + anVar.f2878f);
            }
        }
    }

    /* renamed from: a */
    private void m5066a(C0966a c0966a) {
        if (this.f2640A.f2617c > 0) {
            if (this.f2640A.f2617c == this.f2656i) {
                for (int i = 0; i < this.f2656i; i++) {
                    this.f2648a[i].m5050e();
                    int i2 = this.f2640A.f2618d[i];
                    if (i2 != Integer.MIN_VALUE) {
                        i2 = this.f2640A.f2623i ? i2 + this.f2649b.mo949d() : i2 + this.f2649b.mo947c();
                    }
                    this.f2648a[i].m5047c(i2);
                }
            } else {
                this.f2640A.m5027a();
                this.f2640A.f2615a = this.f2640A.f2616b;
            }
        }
        this.f2663z = this.f2640A.f2624j;
        m5117a(this.f2640A.f2622h);
        m5058N();
        if (this.f2640A.f2615a != -1) {
            this.f2653f = this.f2640A.f2615a;
            c0966a.f2627c = this.f2640A.f2623i;
        } else {
            c0966a.f2627c = this.f2652e;
        }
        if (this.f2640A.f2619e > 1) {
            this.f2655h.f2613a = this.f2640A.f2620f;
            this.f2655h.f2614b = this.f2640A.f2621g;
        }
    }

    /* renamed from: a */
    private void m5067a(C0968c c0968c, int i, int i2) {
        int i3 = c0968c.m5054i();
        if (i == -1) {
            if (i3 + c0968c.m5042b() <= i2) {
                this.f2660m.set(c0968c.f2638e, false);
            }
        } else if (c0968c.m5048d() - i3 >= i2) {
            this.f2660m.set(c0968c.f2638e, false);
        }
    }

    /* renamed from: a */
    private void m5068a(View view, int i, int i2, boolean z) {
        m4558b(view, this.f2642C);
        C0967b c0967b = (C0967b) view.getLayoutParams();
        int b = m5072b(i, c0967b.leftMargin + this.f2642C.left, c0967b.rightMargin + this.f2642C.right);
        int b2 = m5072b(i2, c0967b.topMargin + this.f2642C.top, c0967b.bottomMargin + this.f2642C.bottom);
        if (z ? m4546a(view, b, b2, (C0908i) c0967b) : m4560b(view, b, b2, (C0908i) c0967b)) {
            view.measure(b, b2);
        }
    }

    /* renamed from: a */
    private void m5069a(View view, C0967b c0967b, an anVar) {
        if (anVar.f2877e == 1) {
            if (c0967b.f2633b) {
                m5087p(view);
            } else {
                c0967b.f2632a.m5044b(view);
            }
        } else if (c0967b.f2633b) {
            m5089q(view);
        } else {
            c0967b.f2632a.m5040a(view);
        }
    }

    /* renamed from: a */
    private void m5070a(View view, C0967b c0967b, boolean z) {
        if (c0967b.f2633b) {
            if (this.f2657j == 1) {
                m5068a(view, this.f2641B, C0910h.m4480a(m4490A(), m4617y(), 0, c0967b.height, true), z);
            } else {
                m5068a(view, C0910h.m4480a(m4618z(), m4616x(), 0, c0967b.width, true), this.f2641B, z);
            }
        } else if (this.f2657j == 1) {
            m5068a(view, C0910h.m4480a(this.f2658k, m4616x(), 0, c0967b.width, false), C0910h.m4480a(m4490A(), m4617y(), 0, c0967b.height, true), z);
        } else {
            m5068a(view, C0910h.m4480a(m4618z(), m4616x(), 0, c0967b.width, true), C0910h.m4480a(this.f2658k, m4617y(), 0, c0967b.height, false), z);
        }
    }

    /* renamed from: a */
    private boolean m5071a(C0968c c0968c) {
        boolean z = true;
        if (this.f2652e) {
            if (c0968c.m5048d() < this.f2649b.mo949d()) {
                return !c0968c.m5045c((View) c0968c.f2634a.get(c0968c.f2634a.size() + -1)).f2633b;
            }
        } else if (c0968c.m5042b() > this.f2649b.mo947c()) {
            if (c0968c.m5045c((View) c0968c.f2634a.get(0)).f2633b) {
                z = false;
            }
            return z;
        }
        return false;
    }

    /* renamed from: b */
    private int m5072b(int i, int i2, int i3) {
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        int mode = MeasureSpec.getMode(i);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? MeasureSpec.makeMeasureSpec(Math.max(0, (MeasureSpec.getSize(i) - i2) - i3), mode) : i;
    }

    /* renamed from: b */
    private int m5073b(C0952s c0952s) {
        boolean z = true;
        if (m4615w() == 0) {
            return 0;
        }
        au auVar = this.f2649b;
        View b = m5121b(!this.f2645F);
        if (this.f2645F) {
            z = false;
        }
        return bb.m5608a(c0952s, auVar, b, m5131d(z), this, this.f2645F, this.f2652e);
    }

    /* renamed from: b */
    private void m5074b(int i, C0952s c0952s) {
        int c;
        int i2;
        an anVar;
        boolean z = false;
        this.f2659l.f2874b = 0;
        this.f2659l.f2875c = i;
        if (m4612t()) {
            c = c0952s.m4957c();
            if (c != -1) {
                if (this.f2652e == (c < i)) {
                    c = this.f2649b.mo953f();
                    i2 = 0;
                } else {
                    i2 = this.f2649b.mo953f();
                    c = 0;
                }
                if (m4611s()) {
                    this.f2659l.f2879g = c + this.f2649b.mo951e();
                    this.f2659l.f2878f = -i2;
                } else {
                    this.f2659l.f2878f = this.f2649b.mo947c() - i2;
                    this.f2659l.f2879g = c + this.f2649b.mo949d();
                }
                this.f2659l.f2880h = false;
                this.f2659l.f2873a = true;
                anVar = this.f2659l;
                if (this.f2649b.mo956h() == 0 && this.f2649b.mo951e() == 0) {
                    z = true;
                }
                anVar.f2881i = z;
            }
        }
        c = 0;
        i2 = 0;
        if (m4611s()) {
            this.f2659l.f2879g = c + this.f2649b.mo951e();
            this.f2659l.f2878f = -i2;
        } else {
            this.f2659l.f2878f = this.f2649b.mo947c() - i2;
            this.f2659l.f2879g = c + this.f2649b.mo949d();
        }
        this.f2659l.f2880h = false;
        this.f2659l.f2873a = true;
        anVar = this.f2659l;
        z = true;
        anVar.f2881i = z;
    }

    /* renamed from: b */
    private void m5075b(C0947o c0947o, int i) {
        int w = m4615w() - 1;
        while (w >= 0) {
            View h = m4596h(w);
            if (this.f2649b.mo944a(h) >= i && this.f2649b.mo950d(h) >= i) {
                C0967b c0967b = (C0967b) h.getLayoutParams();
                if (c0967b.f2633b) {
                    int i2 = 0;
                    while (i2 < this.f2656i) {
                        if (this.f2648a[i2].f2634a.size() != 1) {
                            i2++;
                        } else {
                            return;
                        }
                    }
                    for (i2 = 0; i2 < this.f2656i; i2++) {
                        this.f2648a[i2].m5052g();
                    }
                } else if (c0967b.f2632a.f2634a.size() != 1) {
                    c0967b.f2632a.m5052g();
                } else {
                    return;
                }
                m4533a(h, c0947o);
                w--;
            } else {
                return;
            }
        }
    }

    /* renamed from: b */
    private void m5076b(C0947o c0947o, C0952s c0952s, boolean z) {
        int q = m5088q(Integer.MIN_VALUE);
        if (q != Integer.MIN_VALUE) {
            q = this.f2649b.mo949d() - q;
            if (q > 0) {
                q -= -m5126c(-q, c0947o, c0952s);
                if (z && q > 0) {
                    this.f2649b.mo945a(q);
                }
            }
        }
    }

    /* renamed from: c */
    private void m5077c(int i, int i2, int i3) {
        int i4;
        int i5;
        int n = this.f2652e ? m5150n() : m5151o();
        if (i3 != 8) {
            i4 = i + i2;
            i5 = i;
        } else if (i < i2) {
            i4 = i2 + 1;
            i5 = i;
        } else {
            i4 = i + 1;
            i5 = i2;
        }
        this.f2655h.m5019b(i5);
        switch (i3) {
            case 1:
                this.f2655h.m5020b(i, i2);
                break;
            case 2:
                this.f2655h.m5016a(i, i2);
                break;
            case 8:
                this.f2655h.m5016a(i, 1);
                this.f2655h.m5020b(i2, 1);
                break;
        }
        if (i4 > n) {
            if (i5 <= (this.f2652e ? m5151o() : m5150n())) {
                m4608p();
            }
        }
    }

    /* renamed from: c */
    private void m5078c(C0947o c0947o, C0952s c0952s, boolean z) {
        int p = m5086p(Integer.MAX_VALUE);
        if (p != Integer.MAX_VALUE) {
            p -= this.f2649b.mo947c();
            if (p > 0) {
                p -= m5126c(p, c0947o, c0952s);
                if (z && p > 0) {
                    this.f2649b.mo945a(-p);
                }
            }
        }
    }

    /* renamed from: c */
    private boolean m5079c(C0952s c0952s, C0966a c0966a) {
        c0966a.f2625a = this.f2662o ? m5094v(c0952s.m4959e()) : m5093u(c0952s.m4959e());
        c0966a.f2626b = Integer.MIN_VALUE;
        return true;
    }

    /* renamed from: i */
    private int m5080i(C0952s c0952s) {
        boolean z = true;
        if (m4615w() == 0) {
            return 0;
        }
        au auVar = this.f2649b;
        View b = m5121b(!this.f2645F);
        if (this.f2645F) {
            z = false;
        }
        return bb.m5607a(c0952s, auVar, b, m5131d(z), this, this.f2645F);
    }

    /* renamed from: j */
    private int m5081j(C0952s c0952s) {
        boolean z = true;
        if (m4615w() == 0) {
            return 0;
        }
        au auVar = this.f2649b;
        View b = m5121b(!this.f2645F);
        if (this.f2645F) {
            z = false;
        }
        return bb.m5609b(c0952s, auVar, b, m5131d(z), this, this.f2645F);
    }

    /* renamed from: l */
    private void m5082l(int i) {
        int i2 = 1;
        this.f2659l.f2877e = i;
        an anVar = this.f2659l;
        if (this.f2652e != (i == -1)) {
            i2 = -1;
        }
        anVar.f2876d = i2;
    }

    /* renamed from: m */
    private FullSpanItem m5083m(int i) {
        FullSpanItem fullSpanItem = new FullSpanItem();
        fullSpanItem.f2611c = new int[this.f2656i];
        for (int i2 = 0; i2 < this.f2656i; i2++) {
            fullSpanItem.f2611c[i2] = i - this.f2648a[i2].m5043b(i);
        }
        return fullSpanItem;
    }

    /* renamed from: n */
    private FullSpanItem m5084n(int i) {
        FullSpanItem fullSpanItem = new FullSpanItem();
        fullSpanItem.f2611c = new int[this.f2656i];
        for (int i2 = 0; i2 < this.f2656i; i2++) {
            fullSpanItem.f2611c[i2] = this.f2648a[i2].m5035a(i) - i;
        }
        return fullSpanItem;
    }

    /* renamed from: o */
    private int m5085o(int i) {
        int a = this.f2648a[0].m5035a(i);
        for (int i2 = 1; i2 < this.f2656i; i2++) {
            int a2 = this.f2648a[i2].m5035a(i);
            if (a2 > a) {
                a = a2;
            }
        }
        return a;
    }

    /* renamed from: p */
    private int m5086p(int i) {
        int a = this.f2648a[0].m5035a(i);
        for (int i2 = 1; i2 < this.f2656i; i2++) {
            int a2 = this.f2648a[i2].m5035a(i);
            if (a2 < a) {
                a = a2;
            }
        }
        return a;
    }

    /* renamed from: p */
    private void m5087p(View view) {
        for (int i = this.f2656i - 1; i >= 0; i--) {
            this.f2648a[i].m5044b(view);
        }
    }

    /* renamed from: q */
    private int m5088q(int i) {
        int b = this.f2648a[0].m5043b(i);
        for (int i2 = 1; i2 < this.f2656i; i2++) {
            int b2 = this.f2648a[i2].m5043b(i);
            if (b2 > b) {
                b = b2;
            }
        }
        return b;
    }

    /* renamed from: q */
    private void m5089q(View view) {
        for (int i = this.f2656i - 1; i >= 0; i--) {
            this.f2648a[i].m5040a(view);
        }
    }

    /* renamed from: r */
    private int m5090r(int i) {
        int b = this.f2648a[0].m5043b(i);
        for (int i2 = 1; i2 < this.f2656i; i2++) {
            int b2 = this.f2648a[i2].m5043b(i);
            if (b2 < b) {
                b = b2;
            }
        }
        return b;
    }

    /* renamed from: s */
    private boolean m5091s(int i) {
        if (this.f2657j == 0) {
            return (i == -1) != this.f2652e;
        } else {
            return ((i == -1) == this.f2652e) == m5144i();
        }
    }

    /* renamed from: t */
    private int m5092t(int i) {
        int i2 = -1;
        if (m4615w() == 0) {
            return this.f2652e ? 1 : -1;
        } else {
            if ((i < m5151o()) == this.f2652e) {
                i2 = 1;
            }
            return i2;
        }
    }

    /* renamed from: u */
    private int m5093u(int i) {
        int w = m4615w();
        for (int i2 = 0; i2 < w; i2++) {
            int d = m4573d(m4596h(i2));
            if (d >= 0 && d < i) {
                return d;
            }
        }
        return 0;
    }

    /* renamed from: v */
    private int m5094v(int i) {
        for (int w = m4615w() - 1; w >= 0; w--) {
            int d = m4573d(m4596h(w));
            if (d >= 0 && d < i) {
                return d;
            }
        }
        return 0;
    }

    /* renamed from: w */
    private int m5095w(int i) {
        int i2 = Integer.MIN_VALUE;
        int i3 = 1;
        switch (i) {
            case 1:
                return (this.f2657j == 1 || !m5144i()) ? -1 : 1;
            case 2:
                return this.f2657j == 1 ? 1 : !m5144i() ? 1 : -1;
            case 17:
                return this.f2657j != 0 ? Integer.MIN_VALUE : -1;
            case 33:
                return this.f2657j != 1 ? Integer.MIN_VALUE : -1;
            case 66:
                if (this.f2657j != 0) {
                    i3 = Integer.MIN_VALUE;
                }
                return i3;
            case TsExtractor.TS_STREAM_TYPE_HDMV_DTS /*130*/:
                if (this.f2657j == 1) {
                    i2 = 1;
                }
                return i2;
            default:
                return Integer.MIN_VALUE;
        }
    }

    /* renamed from: a */
    public int mo802a(int i, C0947o c0947o, C0952s c0952s) {
        return m5126c(i, c0947o, c0952s);
    }

    /* renamed from: a */
    public int mo829a(C0947o c0947o, C0952s c0952s) {
        return this.f2657j == 0 ? this.f2656i : super.mo829a(c0947o, c0952s);
    }

    /* renamed from: a */
    public C0908i mo803a() {
        return this.f2657j == 0 ? new C0967b(-2, -1) : new C0967b(-1, -2);
    }

    /* renamed from: a */
    public C0908i mo830a(Context context, AttributeSet attributeSet) {
        return new C0967b(context, attributeSet);
    }

    /* renamed from: a */
    public C0908i mo831a(LayoutParams layoutParams) {
        return layoutParams instanceof MarginLayoutParams ? new C0967b((MarginLayoutParams) layoutParams) : new C0967b(layoutParams);
    }

    /* renamed from: a */
    public View mo804a(View view, int i, C0947o c0947o, C0952s c0952s) {
        int i2 = 0;
        if (m4615w() == 0) {
            return null;
        }
        View e = m4580e(view);
        if (e == null) {
            return null;
        }
        m5058N();
        int w = m5095w(i);
        if (w == Integer.MIN_VALUE) {
            return null;
        }
        View a;
        int i3;
        View c;
        C0967b c0967b = (C0967b) e.getLayoutParams();
        boolean z = c0967b.f2633b;
        C0968c c0968c = c0967b.f2632a;
        int n = w == 1 ? m5150n() : m5151o();
        m5074b(n, c0952s);
        m5082l(w);
        this.f2659l.f2875c = this.f2659l.f2876d + n;
        this.f2659l.f2874b = (int) (0.33333334f * ((float) this.f2649b.mo953f()));
        this.f2659l.f2880h = true;
        this.f2659l.f2873a = false;
        m5060a(c0947o, this.f2659l, c0952s);
        this.f2662o = this.f2652e;
        if (!z) {
            a = c0968c.m5038a(n, w);
            if (!(a == null || a == e)) {
                return a;
            }
        }
        if (m5091s(w)) {
            for (int i4 = this.f2656i - 1; i4 >= 0; i4--) {
                a = this.f2648a[i4].m5038a(n, w);
                if (a != null && a != e) {
                    return a;
                }
            }
        } else {
            for (i3 = 0; i3 < this.f2656i; i3++) {
                View a2 = this.f2648a[i3].m5038a(n, w);
                if (a2 != null && a2 != e) {
                    return a2;
                }
            }
        }
        boolean z2 = (!this.f2651d) == (w == -1);
        if (!z) {
            c = mo817c(z2 ? c0968c.m5055j() : c0968c.m5056k());
            if (!(c == null || c == e)) {
                return c;
            }
        }
        if (m5091s(w)) {
            for (i3 = this.f2656i - 1; i3 >= 0; i3--) {
                if (i3 != c0968c.f2638e) {
                    c = mo817c(z2 ? this.f2648a[i3].m5055j() : this.f2648a[i3].m5056k());
                    if (!(c == null || c == e)) {
                        return c;
                    }
                }
            }
        } else {
            while (i2 < this.f2656i) {
                c = mo817c(z2 ? this.f2648a[i2].m5055j() : this.f2648a[i2].m5056k());
                if (c != null && c != e) {
                    return c;
                }
                i2++;
            }
        }
        return null;
    }

    /* renamed from: a */
    public void m5102a(int i) {
        mo812a(null);
        if (i != this.f2656i) {
            m5142h();
            this.f2656i = i;
            this.f2660m = new BitSet(this.f2656i);
            this.f2648a = new C0968c[this.f2656i];
            for (int i2 = 0; i2 < this.f2656i; i2++) {
                this.f2648a[i2] = new C0968c(this, i2);
            }
            m4608p();
        }
    }

    /* renamed from: a */
    public void mo805a(int i, int i2, C0952s c0952s, C0939a c0939a) {
        int i3 = 0;
        if (this.f2657j != 0) {
            i = i2;
        }
        if (m4615w() != 0 && i != 0) {
            m5104a(i, c0952s);
            if (this.f2646G == null || this.f2646G.length < this.f2656i) {
                this.f2646G = new int[this.f2656i];
            }
            int i4 = 0;
            for (int i5 = 0; i5 < this.f2656i; i5++) {
                int a = this.f2659l.f2876d == -1 ? this.f2659l.f2878f - this.f2648a[i5].m5035a(this.f2659l.f2878f) : this.f2648a[i5].m5043b(this.f2659l.f2879g) - this.f2659l.f2879g;
                if (a >= 0) {
                    this.f2646G[i4] = a;
                    i4++;
                }
            }
            Arrays.sort(this.f2646G, 0, i4);
            while (i3 < i4 && this.f2659l.m5465a(c0952s)) {
                c0939a.mo932b(this.f2659l.f2875c, this.f2646G[i3]);
                an anVar = this.f2659l;
                anVar.f2875c += this.f2659l.f2876d;
                i3++;
            }
        }
    }

    /* renamed from: a */
    void m5104a(int i, C0952s c0952s) {
        int n;
        int i2;
        if (i > 0) {
            n = m5150n();
            i2 = 1;
        } else {
            i2 = -1;
            n = m5151o();
        }
        this.f2659l.f2873a = true;
        m5074b(n, c0952s);
        m5082l(i2);
        this.f2659l.f2875c = this.f2659l.f2876d + n;
        this.f2659l.f2874b = Math.abs(i);
    }

    /* renamed from: a */
    public void mo833a(Rect rect, int i, int i2) {
        int D = m4493D() + m4491B();
        int C = m4492C() + m4494E();
        if (this.f2657j == 1) {
            C = C0910h.m4479a(i2, C + rect.height(), m4498I());
            D = C0910h.m4479a(i, D + (this.f2658k * this.f2656i), m4497H());
        } else {
            D = C0910h.m4479a(i, D + rect.width(), m4497H());
            C = C0910h.m4479a(i2, C + (this.f2658k * this.f2656i), m4498I());
        }
        m4593g(D, C);
    }

    /* renamed from: a */
    public void mo807a(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.f2640A = (SavedState) parcelable;
            m4608p();
        }
    }

    /* renamed from: a */
    public void mo836a(C0947o c0947o, C0952s c0952s, View view, C0531e c0531e) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof C0967b) {
            C0967b c0967b = (C0967b) layoutParams;
            if (this.f2657j == 0) {
                c0531e.m2321c(C0530m.m2298a(c0967b.m5034b(), c0967b.f2633b ? this.f2656i : 1, -1, -1, c0967b.f2633b, false));
                return;
            } else {
                c0531e.m2321c(C0530m.m2298a(-1, -1, c0967b.m5034b(), c0967b.f2633b ? this.f2656i : 1, c0967b.f2633b, false));
                return;
            }
        }
        super.m4532a(view, c0531e);
    }

    /* renamed from: a */
    public void mo808a(C0952s c0952s) {
        super.mo808a(c0952s);
        this.f2653f = -1;
        this.f2654g = Integer.MIN_VALUE;
        this.f2640A = null;
        this.f2643D.m5029a();
    }

    /* renamed from: a */
    void m5109a(C0952s c0952s, C0966a c0966a) {
        if (!m5125b(c0952s, c0966a) && !m5079c(c0952s, c0966a)) {
            c0966a.m5032b();
            c0966a.f2625a = 0;
        }
    }

    /* renamed from: a */
    public void mo838a(RecyclerView recyclerView) {
        this.f2655h.m5015a();
        m4608p();
    }

    /* renamed from: a */
    public void mo839a(RecyclerView recyclerView, int i, int i2) {
        m5077c(i, i2, 1);
    }

    /* renamed from: a */
    public void mo840a(RecyclerView recyclerView, int i, int i2, int i3) {
        m5077c(i, i2, 8);
    }

    /* renamed from: a */
    public void mo841a(RecyclerView recyclerView, int i, int i2, Object obj) {
        m5077c(i, i2, 4);
    }

    /* renamed from: a */
    public void mo809a(RecyclerView recyclerView, C0947o c0947o) {
        m4549a(this.f2647H);
        for (int i = 0; i < this.f2656i; i++) {
            this.f2648a[i].m5050e();
        }
        recyclerView.requestLayout();
    }

    /* renamed from: a */
    public void mo811a(AccessibilityEvent accessibilityEvent) {
        super.mo811a(accessibilityEvent);
        if (m4615w() > 0) {
            C0556o a = C0510a.m2132a(accessibilityEvent);
            View b = m5121b(false);
            View d = m5131d(false);
            if (b != null && d != null) {
                int d2 = m4573d(b);
                int d3 = m4573d(d);
                if (d2 < d3) {
                    a.m2479b(d2);
                    a.m2482c(d3);
                    return;
                }
                a.m2479b(d3);
                a.m2482c(d2);
            }
        }
    }

    /* renamed from: a */
    public void mo812a(String str) {
        if (this.f2640A == null) {
            super.mo812a(str);
        }
    }

    /* renamed from: a */
    public void m5117a(boolean z) {
        mo812a(null);
        if (!(this.f2640A == null || this.f2640A.f2622h == z)) {
            this.f2640A.f2622h = z;
        }
        this.f2651d = z;
        m4608p();
    }

    /* renamed from: a */
    public boolean mo843a(C0908i c0908i) {
        return c0908i instanceof C0967b;
    }

    /* renamed from: b */
    public int mo813b(int i, C0947o c0947o, C0952s c0952s) {
        return m5126c(i, c0947o, c0952s);
    }

    /* renamed from: b */
    public int mo844b(C0947o c0947o, C0952s c0952s) {
        return this.f2657j == 1 ? this.f2656i : super.mo844b(c0947o, c0952s);
    }

    /* renamed from: b */
    View m5121b(boolean z) {
        int c = this.f2649b.mo947c();
        int d = this.f2649b.mo949d();
        int w = m4615w();
        View view = null;
        for (int i = 0; i < w; i++) {
            View h = m4596h(i);
            int a = this.f2649b.mo944a(h);
            if (this.f2649b.mo946b(h) > c && a < d) {
                if (a >= c || !z) {
                    return h;
                }
                if (view == null) {
                    view = h;
                }
            }
        }
        return view;
    }

    /* renamed from: b */
    public void m5122b(int i) {
        if (i == 0 || i == 1) {
            mo812a(null);
            if (i != this.f2657j) {
                this.f2657j = i;
                au auVar = this.f2649b;
                this.f2649b = this.f2650c;
                this.f2650c = auVar;
                m4608p();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    /* renamed from: b */
    public void mo845b(RecyclerView recyclerView, int i, int i2) {
        m5077c(i, i2, 2);
    }

    /* renamed from: b */
    public boolean mo814b() {
        return this.f2640A == null;
    }

    /* renamed from: b */
    boolean m5125b(C0952s c0952s, C0966a c0966a) {
        boolean z = false;
        if (c0952s.m4955a() || this.f2653f == -1) {
            return false;
        }
        if (this.f2653f < 0 || this.f2653f >= c0952s.m4959e()) {
            this.f2653f = -1;
            this.f2654g = Integer.MIN_VALUE;
            return false;
        } else if (this.f2640A == null || this.f2640A.f2615a == -1 || this.f2640A.f2617c < 1) {
            View c = mo817c(this.f2653f);
            if (c != null) {
                c0966a.f2625a = this.f2652e ? m5150n() : m5151o();
                if (this.f2654g != Integer.MIN_VALUE) {
                    if (c0966a.f2627c) {
                        c0966a.f2626b = (this.f2649b.mo949d() - this.f2654g) - this.f2649b.mo946b(c);
                        return true;
                    }
                    c0966a.f2626b = (this.f2649b.mo947c() + this.f2654g) - this.f2649b.mo944a(c);
                    return true;
                } else if (this.f2649b.mo952e(c) > this.f2649b.mo953f()) {
                    c0966a.f2626b = c0966a.f2627c ? this.f2649b.mo949d() : this.f2649b.mo947c();
                    return true;
                } else {
                    int a = this.f2649b.mo944a(c) - this.f2649b.mo947c();
                    if (a < 0) {
                        c0966a.f2626b = -a;
                        return true;
                    }
                    a = this.f2649b.mo949d() - this.f2649b.mo946b(c);
                    if (a < 0) {
                        c0966a.f2626b = a;
                        return true;
                    }
                    c0966a.f2626b = Integer.MIN_VALUE;
                    return true;
                }
            }
            c0966a.f2625a = this.f2653f;
            if (this.f2654g == Integer.MIN_VALUE) {
                if (m5092t(c0966a.f2625a) == 1) {
                    z = true;
                }
                c0966a.f2627c = z;
                c0966a.m5032b();
            } else {
                c0966a.m5030a(this.f2654g);
            }
            c0966a.f2628d = true;
            return true;
        } else {
            c0966a.f2626b = Integer.MIN_VALUE;
            c0966a.f2625a = this.f2653f;
            return true;
        }
    }

    /* renamed from: c */
    int m5126c(int i, C0947o c0947o, C0952s c0952s) {
        if (m4615w() == 0 || i == 0) {
            return 0;
        }
        m5104a(i, c0952s);
        int a = m5060a(c0947o, this.f2659l, c0952s);
        if (this.f2659l.f2874b >= a) {
            i = i < 0 ? -a : a;
        }
        this.f2649b.mo945a(-i);
        this.f2662o = this.f2652e;
        this.f2659l.f2874b = 0;
        m5065a(c0947o, this.f2659l);
        return i;
    }

    /* renamed from: c */
    public int mo815c(C0952s c0952s) {
        return m5073b(c0952s);
    }

    /* renamed from: c */
    public Parcelable mo816c() {
        if (this.f2640A != null) {
            return new SavedState(this.f2640A);
        }
        SavedState savedState = new SavedState();
        savedState.f2622h = this.f2651d;
        savedState.f2623i = this.f2662o;
        savedState.f2624j = this.f2663z;
        if (this.f2655h == null || this.f2655h.f2613a == null) {
            savedState.f2619e = 0;
        } else {
            savedState.f2620f = this.f2655h.f2613a;
            savedState.f2619e = savedState.f2620f.length;
            savedState.f2621g = this.f2655h.f2614b;
        }
        if (m4615w() > 0) {
            savedState.f2615a = this.f2662o ? m5150n() : m5151o();
            savedState.f2616b = m5145j();
            savedState.f2617c = this.f2656i;
            savedState.f2618d = new int[this.f2656i];
            for (int i = 0; i < this.f2656i; i++) {
                int b;
                if (this.f2662o) {
                    b = this.f2648a[i].m5043b(Integer.MIN_VALUE);
                    if (b != Integer.MIN_VALUE) {
                        b -= this.f2649b.mo949d();
                    }
                } else {
                    b = this.f2648a[i].m5035a(Integer.MIN_VALUE);
                    if (b != Integer.MIN_VALUE) {
                        b -= this.f2649b.mo947c();
                    }
                }
                savedState.f2618d[i] = b;
            }
        } else {
            savedState.f2615a = -1;
            savedState.f2616b = -1;
            savedState.f2617c = 0;
        }
        return savedState;
    }

    /* renamed from: c */
    public void mo818c(C0947o c0947o, C0952s c0952s) {
        m5064a(c0947o, c0952s, true);
    }

    /* renamed from: d */
    public int mo819d(C0952s c0952s) {
        return m5073b(c0952s);
    }

    /* renamed from: d */
    View m5131d(boolean z) {
        int c = this.f2649b.mo947c();
        int d = this.f2649b.mo949d();
        View view = null;
        for (int w = m4615w() - 1; w >= 0; w--) {
            View h = m4596h(w);
            int a = this.f2649b.mo944a(h);
            int b = this.f2649b.mo946b(h);
            if (b > c && a < d) {
                if (b <= d || !z) {
                    return h;
                }
                if (view == null) {
                    view = h;
                }
            }
        }
        return view;
    }

    /* renamed from: d */
    public void mo820d(int i) {
        if (!(this.f2640A == null || this.f2640A.f2615a == i)) {
            this.f2640A.m5028b();
        }
        this.f2653f = i;
        this.f2654g = Integer.MIN_VALUE;
        m4608p();
    }

    /* renamed from: d */
    public boolean mo821d() {
        return this.f2657j == 0;
    }

    /* renamed from: e */
    public int mo822e(C0952s c0952s) {
        return m5080i(c0952s);
    }

    /* renamed from: e */
    void m5135e(int i) {
        this.f2658k = i / this.f2656i;
        this.f2641B = MeasureSpec.makeMeasureSpec(i, this.f2650c.mo956h());
    }

    /* renamed from: e */
    public boolean mo823e() {
        return this.f2657j == 1;
    }

    /* renamed from: f */
    public int mo824f(C0952s c0952s) {
        return m5080i(c0952s);
    }

    /* renamed from: f */
    boolean m5138f() {
        if (m4615w() == 0 || this.f2661n == 0 || !m4610r()) {
            return false;
        }
        int n;
        int o;
        if (this.f2652e) {
            n = m5150n();
            o = m5151o();
        } else {
            n = m5151o();
            o = m5150n();
        }
        if (n == 0 && m5140g() != null) {
            this.f2655h.m5015a();
            m4500K();
            m4608p();
            return true;
        } else if (!this.f2644E) {
            return false;
        } else {
            int i = this.f2652e ? -1 : 1;
            FullSpanItem a = this.f2655h.m5014a(n, o + 1, i, true);
            if (a == null) {
                this.f2644E = false;
                this.f2655h.m5013a(o + 1);
                return false;
            }
            FullSpanItem a2 = this.f2655h.m5014a(n, a.f2609a, i * -1, true);
            if (a2 == null) {
                this.f2655h.m5013a(a.f2609a);
            } else {
                this.f2655h.m5013a(a2.f2609a + 1);
            }
            m4500K();
            m4608p();
            return true;
        }
    }

    /* renamed from: g */
    public int mo825g(C0952s c0952s) {
        return m5081j(c0952s);
    }

    /* renamed from: g */
    View m5140g() {
        int i;
        int w = m4615w() - 1;
        BitSet bitSet = new BitSet(this.f2656i);
        bitSet.set(0, this.f2656i, true);
        boolean z = (this.f2657j == 1 && m5144i()) ? true : true;
        if (this.f2652e) {
            i = -1;
        } else {
            i = w + 1;
            w = 0;
        }
        int i2 = w < i ? 1 : -1;
        int i3 = w;
        while (i3 != i) {
            View h = m4596h(i3);
            C0967b c0967b = (C0967b) h.getLayoutParams();
            if (bitSet.get(c0967b.f2632a.f2638e)) {
                if (m5071a(c0967b.f2632a)) {
                    return h;
                }
                bitSet.clear(c0967b.f2632a.f2638e);
            }
            if (!(c0967b.f2633b || i3 + i2 == i)) {
                boolean z2;
                View h2 = m4596h(i3 + i2);
                int b;
                if (this.f2652e) {
                    w = this.f2649b.mo946b(h);
                    b = this.f2649b.mo946b(h2);
                    if (w < b) {
                        return h;
                    }
                    if (w == b) {
                        z2 = true;
                    }
                    z2 = false;
                } else {
                    w = this.f2649b.mo944a(h);
                    b = this.f2649b.mo944a(h2);
                    if (w > b) {
                        return h;
                    }
                    if (w == b) {
                        z2 = true;
                    }
                    z2 = false;
                }
                if (z2) {
                    if ((c0967b.f2632a.f2638e - ((C0967b) h2.getLayoutParams()).f2632a.f2638e < 0) != (z >= false)) {
                        return h;
                    }
                } else {
                    continue;
                }
            }
            i3 += i2;
        }
        return null;
    }

    /* renamed from: h */
    public int mo826h(C0952s c0952s) {
        return m5081j(c0952s);
    }

    /* renamed from: h */
    public void m5142h() {
        this.f2655h.m5015a();
        m4608p();
    }

    /* renamed from: i */
    public void mo880i(int i) {
        super.mo880i(i);
        for (int i2 = 0; i2 < this.f2656i; i2++) {
            this.f2648a[i2].m5049d(i);
        }
    }

    /* renamed from: i */
    boolean m5144i() {
        return m4613u() == 1;
    }

    /* renamed from: j */
    int m5145j() {
        View d = this.f2652e ? m5131d(true) : m5121b(true);
        return d == null ? -1 : m4573d(d);
    }

    /* renamed from: j */
    public void mo881j(int i) {
        super.mo881j(i);
        for (int i2 = 0; i2 < this.f2656i; i2++) {
            this.f2648a[i2].m5049d(i);
        }
    }

    /* renamed from: k */
    public void mo882k(int i) {
        if (i == 0) {
            m5138f();
        }
    }

    /* renamed from: l */
    boolean m5148l() {
        int b = this.f2648a[0].m5043b(Integer.MIN_VALUE);
        for (int i = 1; i < this.f2656i; i++) {
            if (this.f2648a[i].m5043b(Integer.MIN_VALUE) != b) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: m */
    boolean m5149m() {
        int a = this.f2648a[0].m5035a(Integer.MIN_VALUE);
        for (int i = 1; i < this.f2656i; i++) {
            if (this.f2648a[i].m5035a(Integer.MIN_VALUE) != a) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: n */
    int m5150n() {
        int w = m4615w();
        return w == 0 ? 0 : m4573d(m4596h(w - 1));
    }

    /* renamed from: o */
    int m5151o() {
        return m4615w() == 0 ? 0 : m4573d(m4596h(0));
    }
}
