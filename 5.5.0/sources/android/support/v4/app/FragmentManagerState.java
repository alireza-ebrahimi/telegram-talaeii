package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

final class FragmentManagerState implements Parcelable {
    public static final Creator<FragmentManagerState> CREATOR = new C02311();
    /* renamed from: a */
    FragmentState[] f751a;
    /* renamed from: b */
    int[] f752b;
    /* renamed from: c */
    BackStackState[] f753c;

    /* renamed from: android.support.v4.app.FragmentManagerState$1 */
    static class C02311 implements Creator<FragmentManagerState> {
        C02311() {
        }

        /* renamed from: a */
        public FragmentManagerState m1061a(Parcel parcel) {
            return new FragmentManagerState(parcel);
        }

        /* renamed from: a */
        public FragmentManagerState[] m1062a(int i) {
            return new FragmentManagerState[i];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1061a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1062a(i);
        }
    }

    public FragmentManagerState(Parcel parcel) {
        this.f751a = (FragmentState[]) parcel.createTypedArray(FragmentState.CREATOR);
        this.f752b = parcel.createIntArray();
        this.f753c = (BackStackState[]) parcel.createTypedArray(BackStackState.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.f751a, i);
        parcel.writeIntArray(this.f752b);
        parcel.writeTypedArray(this.f753c, i);
    }
}
