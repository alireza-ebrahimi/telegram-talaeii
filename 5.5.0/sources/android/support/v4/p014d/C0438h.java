package android.support.v4.p014d;

import android.annotation.TargetApi;
import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;

@TargetApi(13)
/* renamed from: android.support.v4.d.h */
class C0438h<T> implements ClassLoaderCreator<T> {
    /* renamed from: a */
    private final C0085g<T> f1196a;

    public C0438h(C0085g<T> c0085g) {
        this.f1196a = c0085g;
    }

    public T createFromParcel(Parcel parcel) {
        return this.f1196a.createFromParcel(parcel, null);
    }

    public T createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return this.f1196a.createFromParcel(parcel, classLoader);
    }

    public T[] newArray(int i) {
        return this.f1196a.newArray(i);
    }
}
