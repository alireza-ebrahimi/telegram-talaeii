package org.telegram.customization.util.view.observerRecyclerView;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0910h;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ObservableRecyclerView extends RecyclerView {
    /* renamed from: H */
    int f9756H;
    /* renamed from: I */
    int f9757I;
    /* renamed from: J */
    int f9758J;
    /* renamed from: K */
    boolean f9759K = false;
    /* renamed from: L */
    boolean f9760L = false;
    /* renamed from: M */
    public int f9761M;
    /* renamed from: N */
    private int f9762N;
    /* renamed from: O */
    private int f9763O = -1;
    /* renamed from: P */
    private int f9764P;
    /* renamed from: Q */
    private int f9765Q;
    /* renamed from: R */
    private SparseIntArray f9766R;
    /* renamed from: S */
    private C2951a f9767S;
    /* renamed from: T */
    private C2952c f9768T;
    /* renamed from: U */
    private boolean f9769U;
    /* renamed from: V */
    private boolean f9770V;
    /* renamed from: W */
    private boolean f9771W;
    private MotionEvent aa;
    private ViewGroup ab;
    private C2621b ac = null;
    private boolean ad = true;

    static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C29502();
        /* renamed from: a */
        public static final SavedState f9748a = new C29491();
        /* renamed from: b */
        int f9749b;
        /* renamed from: c */
        int f9750c;
        /* renamed from: d */
        int f9751d;
        /* renamed from: e */
        int f9752e;
        /* renamed from: f */
        int f9753f;
        /* renamed from: g */
        SparseIntArray f9754g;
        /* renamed from: h */
        Parcelable f9755h;

        /* renamed from: org.telegram.customization.util.view.observerRecyclerView.ObservableRecyclerView$SavedState$1 */
        static class C29491 extends SavedState {
            C29491() {
                super();
            }
        }

        /* renamed from: org.telegram.customization.util.view.observerRecyclerView.ObservableRecyclerView$SavedState$2 */
        static class C29502 implements Creator<SavedState> {
            C29502() {
            }

            /* renamed from: a */
            public SavedState m13615a(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] m13616a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m13615a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m13616a(i);
            }
        }

        private SavedState() {
            this.f9750c = -1;
            this.f9755h = null;
        }

        private SavedState(Parcel parcel) {
            this.f9750c = -1;
            Parcelable readParcelable = parcel.readParcelable(RecyclerView.class.getClassLoader());
            if (readParcelable == null) {
                readParcelable = f9748a;
            }
            this.f9755h = readParcelable;
            this.f9749b = parcel.readInt();
            this.f9750c = parcel.readInt();
            this.f9751d = parcel.readInt();
            this.f9752e = parcel.readInt();
            this.f9753f = parcel.readInt();
            this.f9754g = new SparseIntArray();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                for (int i = 0; i < readInt; i++) {
                    this.f9754g.put(parcel.readInt(), parcel.readInt());
                }
            }
        }

        SavedState(Parcelable parcelable) {
            this.f9750c = -1;
            if (parcelable == f9748a) {
                parcelable = null;
            }
            this.f9755h = parcelable;
        }

        /* renamed from: a */
        public Parcelable m13614a() {
            return this.f9755h;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            int i2 = 0;
            parcel.writeParcelable(this.f9755h, i);
            parcel.writeInt(this.f9749b);
            parcel.writeInt(this.f9750c);
            parcel.writeInt(this.f9751d);
            parcel.writeInt(this.f9752e);
            parcel.writeInt(this.f9753f);
            int size = this.f9754g == null ? 0 : this.f9754g.size();
            parcel.writeInt(size);
            if (size > 0) {
                while (i2 < size) {
                    parcel.writeInt(this.f9754g.keyAt(i2));
                    parcel.writeInt(this.f9754g.valueAt(i2));
                    i2++;
                }
            }
        }
    }

    public ObservableRecyclerView(Context context) {
        super(context);
        m13617A();
    }

    public ObservableRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m13617A();
    }

    public ObservableRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m13617A();
    }

    /* renamed from: A */
    private void m13617A() {
        this.f9766R = new SparseIntArray();
    }

    public boolean canScrollHorizontally(int i) {
        return super.canScrollHorizontally(i);
    }

    public boolean canScrollVertically(int i) {
        return mo3515z() && super.canScrollVertically(i);
    }

    public int getCurrentScrollY() {
        return this.f9761M;
    }

    /* renamed from: h */
    public void m13618h(int i) {
        C0910h layoutManager = getLayoutManager();
        if (layoutManager == null || !(layoutManager instanceof LinearLayoutManager)) {
            m234a(i);
        } else {
            ((LinearLayoutManager) layoutManager).m4672b(i, 0);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.f9767S != null) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                    this.f9770V = true;
                    this.f9769U = true;
                    this.f9767S.m13620a();
                    break;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        this.f9762N = savedState.f9749b;
        this.f9763O = savedState.f9750c;
        this.f9764P = savedState.f9751d;
        this.f9765Q = savedState.f9752e;
        this.f9761M = savedState.f9753f;
        this.f9766R = savedState.f9754g;
        super.onRestoreInstanceState(savedState.m13614a());
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.f9749b = this.f9762N;
        savedState.f9750c = this.f9763O;
        savedState.f9751d = this.f9764P;
        savedState.f9752e = this.f9765Q;
        savedState.f9753f = this.f9761M;
        savedState.f9754g = this.f9766R;
        return savedState;
    }

    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        try {
            if (!(getLayoutManager() == null || (this.f9759K && this.f9760L))) {
                this.f9757I = getLayoutManager().m4615w();
                this.f9758J = getLayoutManager().m4496G();
                if (getLayoutManager() instanceof LinearLayoutManager) {
                    boolean z;
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
                    this.f9756H = linearLayoutManager.m4696l();
                    if (this.f9760L || linearLayoutManager.m4697m() != 0) {
                        z = true;
                    } else {
                        this.f9760L = true;
                        this.ac.mo3455b(this);
                        z = false;
                    }
                    if (!this.f9759K && linearLayoutManager.m4699o() == this.f9758J - 1) {
                        this.f9759K = true;
                        this.ac.mo3451a((View) this);
                        z = false;
                    }
                    if (!(!z || linearLayoutManager.m4697m() == 0 || linearLayoutManager.m4699o() == this.f9758J - 1)) {
                        this.ac.mo3452a(this, i2);
                    }
                } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                }
            }
        } catch (Exception e) {
        }
        if (this.f9767S != null && getChildCount() > 0) {
            int f = m273f(getChildAt(0));
            int f2 = m273f(getChildAt(getChildCount() - 1));
            int i5 = 0;
            int i6 = f;
            while (i6 <= f2) {
                if (getChildAt(i5) != null && (this.f9766R.indexOfKey(i6) < 0 || getChildAt(i5).getHeight() != this.f9766R.get(i6))) {
                    this.f9766R.put(i6, getChildAt(i5).getHeight());
                }
                i6++;
                i5++;
            }
            View childAt = getChildAt(0);
            if (childAt != null) {
                if (this.f9762N < f) {
                    if (f - this.f9762N != 1) {
                        i6 = 0;
                        for (i5 = f - 1; i5 > this.f9762N; i5--) {
                            i6 = this.f9766R.indexOfKey(i5) > 0 ? i6 + this.f9766R.get(i5) : i6 + childAt.getHeight();
                        }
                    } else {
                        i6 = 0;
                    }
                    this.f9764P += i6 + this.f9763O;
                    this.f9763O = childAt.getHeight();
                } else if (f < this.f9762N) {
                    if (this.f9762N - f != 1) {
                        i5 = 0;
                        for (i6 = this.f9762N - 1; i6 > f; i6--) {
                            i5 = this.f9766R.indexOfKey(i6) > 0 ? i5 + this.f9766R.get(i6) : i5 + childAt.getHeight();
                        }
                    } else {
                        i5 = 0;
                    }
                    this.f9764P -= i5 + childAt.getHeight();
                    this.f9763O = childAt.getHeight();
                } else if (f == 0) {
                    this.f9763O = childAt.getHeight();
                }
                if (this.f9763O < 0) {
                    this.f9763O = 0;
                }
                this.f9761M = this.f9764P - childAt.getTop();
                this.f9762N = f;
                this.f9767S.m13621a(this.f9761M, this.f9769U, this.f9770V);
                if (this.f9769U) {
                    this.f9769U = false;
                }
                if (this.f9765Q < this.f9761M) {
                    this.f9768T = C2952c.UP;
                } else if (this.f9761M < this.f9765Q) {
                    this.f9768T = C2952c.DOWN;
                } else {
                    this.f9768T = C2952c.STOP;
                }
                if (this.ac != null) {
                    this.ac.mo3454a(this.f9768T);
                }
                this.f9765Q = this.f9761M;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.f9767S != null) {
            switch (motionEvent.getActionMasked()) {
                case 1:
                case 3:
                    this.f9771W = false;
                    this.f9770V = false;
                    this.f9767S.m13622a(this.f9768T);
                    break;
                case 2:
                    if (this.aa == null) {
                        this.aa = motionEvent;
                    }
                    float y = motionEvent.getY() - this.aa.getY();
                    this.aa = MotionEvent.obtainNoHistory(motionEvent);
                    if (((float) getCurrentScrollY()) - y <= BitmapDescriptorFactory.HUE_RED) {
                        if (this.f9771W) {
                            return false;
                        }
                        final View view = this.ab == null ? (ViewGroup) getParent() : this.ab;
                        View view2 = this;
                        float f2 = BitmapDescriptorFactory.HUE_RED;
                        while (view2 != null && view2 != view) {
                            f2 += (float) (view2.getLeft() - view2.getScrollX());
                            f += (float) (view2.getTop() - view2.getScrollY());
                            view2 = (View) view2.getParent();
                        }
                        final MotionEvent obtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
                        obtainNoHistory.offsetLocation(f2, f);
                        if (!view.onInterceptTouchEvent(obtainNoHistory)) {
                            return super.onTouchEvent(motionEvent);
                        }
                        this.f9771W = true;
                        obtainNoHistory.setAction(0);
                        post(new Runnable(this) {
                            /* renamed from: c */
                            final /* synthetic */ ObservableRecyclerView f9747c;

                            public void run() {
                                view.dispatchTouchEvent(obtainNoHistory);
                            }
                        });
                        return false;
                    }
                    break;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setCanScrollVertically(boolean z) {
        this.ad = z;
    }

    public void setLoadingEnd(boolean z) {
        this.f9759K = z;
    }

    public void setLoadingStart(boolean z) {
        this.f9760L = z;
    }

    public void setScrollPositionChangesListener(C2621b c2621b) {
        this.ac = c2621b;
    }

    public void setScrollViewCallbacks(C2951a c2951a) {
        this.f9767S = c2951a;
    }

    public void setTouchInterceptionViewGroup(ViewGroup viewGroup) {
        this.ab = viewGroup;
    }

    /* renamed from: z */
    public boolean mo3515z() {
        return this.ad;
    }
}
