package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;

final class FragmentState implements Parcelable {
    public static final Creator<FragmentState> CREATOR = new C02321();
    /* renamed from: a */
    final String f754a;
    /* renamed from: b */
    final int f755b;
    /* renamed from: c */
    final boolean f756c;
    /* renamed from: d */
    final int f757d;
    /* renamed from: e */
    final int f758e;
    /* renamed from: f */
    final String f759f;
    /* renamed from: g */
    final boolean f760g;
    /* renamed from: h */
    final boolean f761h;
    /* renamed from: i */
    final Bundle f762i;
    /* renamed from: j */
    final boolean f763j;
    /* renamed from: k */
    Bundle f764k;
    /* renamed from: l */
    Fragment f765l;

    /* renamed from: android.support.v4.app.FragmentState$1 */
    static class C02321 implements Creator<FragmentState> {
        C02321() {
        }

        /* renamed from: a */
        public FragmentState m1063a(Parcel parcel) {
            return new FragmentState(parcel);
        }

        /* renamed from: a */
        public FragmentState[] m1064a(int i) {
            return new FragmentState[i];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1063a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1064a(i);
        }
    }

    public FragmentState(Parcel parcel) {
        boolean z = true;
        this.f754a = parcel.readString();
        this.f755b = parcel.readInt();
        this.f756c = parcel.readInt() != 0;
        this.f757d = parcel.readInt();
        this.f758e = parcel.readInt();
        this.f759f = parcel.readString();
        this.f760g = parcel.readInt() != 0;
        this.f761h = parcel.readInt() != 0;
        this.f762i = parcel.readBundle();
        if (parcel.readInt() == 0) {
            z = false;
        }
        this.f763j = z;
        this.f764k = parcel.readBundle();
    }

    public FragmentState(Fragment fragment) {
        this.f754a = fragment.getClass().getName();
        this.f755b = fragment.mIndex;
        this.f756c = fragment.mFromLayout;
        this.f757d = fragment.mFragmentId;
        this.f758e = fragment.mContainerId;
        this.f759f = fragment.mTag;
        this.f760g = fragment.mRetainInstance;
        this.f761h = fragment.mDetached;
        this.f762i = fragment.mArguments;
        this.f763j = fragment.mHidden;
    }

    /* renamed from: a */
    public Fragment m1065a(C0350w c0350w, Fragment fragment, C0367z c0367z) {
        if (this.f765l == null) {
            Context i = c0350w.m1510i();
            if (this.f762i != null) {
                this.f762i.setClassLoader(i.getClassLoader());
            }
            this.f765l = Fragment.instantiate(i, this.f754a, this.f762i);
            if (this.f764k != null) {
                this.f764k.setClassLoader(i.getClassLoader());
                this.f765l.mSavedFragmentState = this.f764k;
            }
            this.f765l.setIndex(this.f755b, fragment);
            this.f765l.mFromLayout = this.f756c;
            this.f765l.mRestored = true;
            this.f765l.mFragmentId = this.f757d;
            this.f765l.mContainerId = this.f758e;
            this.f765l.mTag = this.f759f;
            this.f765l.mRetainInstance = this.f760g;
            this.f765l.mDetached = this.f761h;
            this.f765l.mHidden = this.f763j;
            this.f765l.mFragmentManager = c0350w.f1068d;
            if (C0366y.f1110a) {
                Log.v("FragmentManager", "Instantiated fragment " + this.f765l);
            }
        }
        this.f765l.mChildNonConfig = c0367z;
        return this.f765l;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeString(this.f754a);
        parcel.writeInt(this.f755b);
        parcel.writeInt(this.f756c ? 1 : 0);
        parcel.writeInt(this.f757d);
        parcel.writeInt(this.f758e);
        parcel.writeString(this.f759f);
        parcel.writeInt(this.f760g ? 1 : 0);
        parcel.writeInt(this.f761h ? 1 : 0);
        parcel.writeBundle(this.f762i);
        if (!this.f763j) {
            i2 = 0;
        }
        parcel.writeInt(i2);
        parcel.writeBundle(this.f764k);
    }
}
