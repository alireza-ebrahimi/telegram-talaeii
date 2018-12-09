package android.support.v4.p014d;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;

/* renamed from: android.support.v4.d.f */
public final class C0437f {

    /* renamed from: android.support.v4.d.f$a */
    static class C0436a<T> implements Creator<T> {
        /* renamed from: a */
        final C0085g<T> f1195a;

        public C0436a(C0085g<T> c0085g) {
            this.f1195a = c0085g;
        }

        public T createFromParcel(Parcel parcel) {
            return this.f1195a.createFromParcel(parcel, null);
        }

        public T[] newArray(int i) {
            return this.f1195a.newArray(i);
        }
    }

    /* renamed from: a */
    public static <T> Creator<T> m1919a(C0085g<T> c0085g) {
        return VERSION.SDK_INT >= 13 ? C0439i.m1920a(c0085g) : new C0436a(c0085g);
    }
}
