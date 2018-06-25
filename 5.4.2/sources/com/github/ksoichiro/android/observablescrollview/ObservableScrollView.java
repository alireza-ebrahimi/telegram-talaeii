package com.github.ksoichiro.android.observablescrollview;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.widget.ScrollView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ObservableScrollView extends ScrollView {
    /* renamed from: a */
    private int f5093a;
    /* renamed from: b */
    private int f5094b;
    /* renamed from: c */
    private C1651a f5095c;
    /* renamed from: d */
    private C1652b f5096d;
    /* renamed from: e */
    private boolean f5097e;
    /* renamed from: f */
    private boolean f5098f;
    /* renamed from: g */
    private boolean f5099g;
    /* renamed from: h */
    private MotionEvent f5100h;
    /* renamed from: i */
    private ViewGroup f5101i;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C16501();
        /* renamed from: a */
        int f5091a;
        /* renamed from: b */
        int f5092b;

        /* renamed from: com.github.ksoichiro.android.observablescrollview.ObservableScrollView$SavedState$1 */
        static class C16501 implements Creator<SavedState> {
            C16501() {
            }

            /* renamed from: a */
            public SavedState m8068a(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] m8069a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m8068a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m8069a(i);
            }
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.f5091a = parcel.readInt();
            this.f5092b = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f5091a);
            parcel.writeInt(this.f5092b);
        }
    }

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ObservableScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public int getCurrentScrollY() {
        return this.f5094b;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.f5095c != null) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                    this.f5098f = true;
                    this.f5097e = true;
                    this.f5095c.mo4196a();
                    break;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        this.f5093a = savedState.f5091a;
        this.f5094b = savedState.f5092b;
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.f5091a = this.f5093a;
        savedState.f5092b = this.f5094b;
        return savedState;
    }

    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.f5095c != null) {
            this.f5094b = i2;
            this.f5095c.mo4197a(i2, this.f5097e, this.f5098f);
            if (this.f5097e) {
                this.f5097e = false;
            }
            if (this.f5093a < i2) {
                this.f5096d = C1652b.UP;
            } else if (i2 < this.f5093a) {
                this.f5096d = C1652b.DOWN;
            }
            this.f5093a = i2;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.f5095c != null) {
            switch (motionEvent.getActionMasked()) {
                case 1:
                case 3:
                    this.f5099g = false;
                    this.f5098f = false;
                    this.f5095c.mo4198a(this.f5096d);
                    break;
                case 2:
                    if (this.f5100h == null) {
                        this.f5100h = motionEvent;
                    }
                    float y = motionEvent.getY() - this.f5100h.getY();
                    this.f5100h = MotionEvent.obtainNoHistory(motionEvent);
                    if (((float) getCurrentScrollY()) - y <= BitmapDescriptorFactory.HUE_RED) {
                        if (this.f5099g) {
                            return false;
                        }
                        final View view = this.f5101i == null ? (ViewGroup) getParent() : this.f5101i;
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
                        this.f5099g = true;
                        obtainNoHistory.setAction(0);
                        post(new Runnable(this) {
                            /* renamed from: c */
                            final /* synthetic */ ObservableScrollView f5090c;

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

    public void setScrollViewCallbacks(C1651a c1651a) {
        this.f5095c = c1651a;
    }

    public void setTouchInterceptionViewGroup(ViewGroup viewGroup) {
        this.f5101i = viewGroup;
    }
}
