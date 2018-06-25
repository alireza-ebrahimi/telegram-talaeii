package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.C0341l.C0339a;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;

final class BackStackState implements Parcelable {
    public static final Creator<BackStackState> CREATOR = new C02231();
    /* renamed from: a */
    final int[] f718a;
    /* renamed from: b */
    final int f719b;
    /* renamed from: c */
    final int f720c;
    /* renamed from: d */
    final String f721d;
    /* renamed from: e */
    final int f722e;
    /* renamed from: f */
    final int f723f;
    /* renamed from: g */
    final CharSequence f724g;
    /* renamed from: h */
    final int f725h;
    /* renamed from: i */
    final CharSequence f726i;
    /* renamed from: j */
    final ArrayList<String> f727j;
    /* renamed from: k */
    final ArrayList<String> f728k;
    /* renamed from: l */
    final boolean f729l;

    /* renamed from: android.support.v4.app.BackStackState$1 */
    static class C02231 implements Creator<BackStackState> {
        C02231() {
        }

        /* renamed from: a */
        public BackStackState m1034a(Parcel parcel) {
            return new BackStackState(parcel);
        }

        /* renamed from: a */
        public BackStackState[] m1035a(int i) {
            return new BackStackState[i];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1034a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1035a(i);
        }
    }

    public BackStackState(Parcel parcel) {
        this.f718a = parcel.createIntArray();
        this.f719b = parcel.readInt();
        this.f720c = parcel.readInt();
        this.f721d = parcel.readString();
        this.f722e = parcel.readInt();
        this.f723f = parcel.readInt();
        this.f724g = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.f725h = parcel.readInt();
        this.f726i = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.f727j = parcel.createStringArrayList();
        this.f728k = parcel.createStringArrayList();
        this.f729l = parcel.readInt() != 0;
    }

    public BackStackState(C0341l c0341l) {
        int size = c0341l.f1039c.size();
        this.f718a = new int[(size * 6)];
        if (c0341l.f1046j) {
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                C0339a c0339a = (C0339a) c0341l.f1039c.get(i2);
                int i3 = i + 1;
                this.f718a[i] = c0339a.f1031a;
                int i4 = i3 + 1;
                this.f718a[i3] = c0339a.f1032b != null ? c0339a.f1032b.mIndex : -1;
                int i5 = i4 + 1;
                this.f718a[i4] = c0339a.f1033c;
                i3 = i5 + 1;
                this.f718a[i5] = c0339a.f1034d;
                i5 = i3 + 1;
                this.f718a[i3] = c0339a.f1035e;
                i = i5 + 1;
                this.f718a[i5] = c0339a.f1036f;
            }
            this.f719b = c0341l.f1044h;
            this.f720c = c0341l.f1045i;
            this.f721d = c0341l.f1048l;
            this.f722e = c0341l.f1050n;
            this.f723f = c0341l.f1051o;
            this.f724g = c0341l.f1052p;
            this.f725h = c0341l.f1053q;
            this.f726i = c0341l.f1054r;
            this.f727j = c0341l.f1055s;
            this.f728k = c0341l.f1056t;
            this.f729l = c0341l.f1057u;
            return;
        }
        throw new IllegalStateException("Not on back stack");
    }

    /* renamed from: a */
    public C0341l m1036a(C0366y c0366y) {
        int i = 0;
        C0341l c0341l = new C0341l(c0366y);
        int i2 = 0;
        while (i < this.f718a.length) {
            C0339a c0339a = new C0339a();
            int i3 = i + 1;
            c0339a.f1031a = this.f718a[i];
            if (C0366y.f1110a) {
                Log.v("FragmentManager", "Instantiate " + c0341l + " op #" + i2 + " base fragment #" + this.f718a[i3]);
            }
            int i4 = i3 + 1;
            i = this.f718a[i3];
            if (i >= 0) {
                c0339a.f1032b = (Fragment) c0366y.f1119e.get(i);
            } else {
                c0339a.f1032b = null;
            }
            i3 = i4 + 1;
            c0339a.f1033c = this.f718a[i4];
            i4 = i3 + 1;
            c0339a.f1034d = this.f718a[i3];
            i3 = i4 + 1;
            c0339a.f1035e = this.f718a[i4];
            i4 = i3 + 1;
            c0339a.f1036f = this.f718a[i3];
            c0341l.f1040d = c0339a.f1033c;
            c0341l.f1041e = c0339a.f1034d;
            c0341l.f1042f = c0339a.f1035e;
            c0341l.f1043g = c0339a.f1036f;
            c0341l.m1469a(c0339a);
            i2++;
            i = i4;
        }
        c0341l.f1044h = this.f719b;
        c0341l.f1045i = this.f720c;
        c0341l.f1048l = this.f721d;
        c0341l.f1050n = this.f722e;
        c0341l.f1046j = true;
        c0341l.f1051o = this.f723f;
        c0341l.f1052p = this.f724g;
        c0341l.f1053q = this.f725h;
        c0341l.f1054r = this.f726i;
        c0341l.f1055s = this.f727j;
        c0341l.f1056t = this.f728k;
        c0341l.f1057u = this.f729l;
        c0341l.m1467a(1);
        return c0341l;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 0;
        parcel.writeIntArray(this.f718a);
        parcel.writeInt(this.f719b);
        parcel.writeInt(this.f720c);
        parcel.writeString(this.f721d);
        parcel.writeInt(this.f722e);
        parcel.writeInt(this.f723f);
        TextUtils.writeToParcel(this.f724g, parcel, 0);
        parcel.writeInt(this.f725h);
        TextUtils.writeToParcel(this.f726i, parcel, 0);
        parcel.writeStringList(this.f727j);
        parcel.writeStringList(this.f728k);
        if (this.f729l) {
            i2 = 1;
        }
        parcel.writeInt(i2);
    }
}
