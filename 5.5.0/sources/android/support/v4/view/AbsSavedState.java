package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.p014d.C0085g;
import android.support.v4.p014d.C0437f;

public abstract class AbsSavedState implements Parcelable {
    public static final Creator<AbsSavedState> CREATOR = C0437f.m1919a(new C04842());
    public static final AbsSavedState EMPTY_STATE = new C04831();
    private final Parcelable mSuperState;

    /* renamed from: android.support.v4.view.AbsSavedState$1 */
    static class C04831 extends AbsSavedState {
        C04831() {
            super();
        }
    }

    /* renamed from: android.support.v4.view.AbsSavedState$2 */
    static class C04842 implements C0085g<AbsSavedState> {
        C04842() {
        }

        /* renamed from: a */
        public AbsSavedState m2051a(Parcel parcel, ClassLoader classLoader) {
            if (parcel.readParcelable(classLoader) == null) {
                return AbsSavedState.EMPTY_STATE;
            }
            throw new IllegalStateException("superState must be null");
        }

        /* renamed from: a */
        public AbsSavedState[] m2052a(int i) {
            return new AbsSavedState[i];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return m2051a(parcel, classLoader);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m2052a(i);
        }
    }

    private AbsSavedState() {
        this.mSuperState = null;
    }

    protected AbsSavedState(Parcel parcel) {
        this(parcel, null);
    }

    protected AbsSavedState(Parcel parcel, ClassLoader classLoader) {
        Parcelable readParcelable = parcel.readParcelable(classLoader);
        if (readParcelable == null) {
            readParcelable = EMPTY_STATE;
        }
        this.mSuperState = readParcelable;
    }

    protected AbsSavedState(Parcelable parcelable) {
        if (parcelable == null) {
            throw new IllegalArgumentException("superState must not be null");
        }
        if (parcelable == EMPTY_STATE) {
            parcelable = null;
        }
        this.mSuperState = parcelable;
    }

    public int describeContents() {
        return 0;
    }

    public final Parcelable getSuperState() {
        return this.mSuperState;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mSuperState, i);
    }
}
