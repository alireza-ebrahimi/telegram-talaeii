package com.persianswitch.sdk.payment.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PaymentProfile implements Parcelable {
    public static final Creator<PaymentProfile> CREATOR = new C22931();
    /* renamed from: a */
    private int f7511a;
    /* renamed from: b */
    private TransactionStatus f7512b;
    /* renamed from: c */
    private long f7513c;
    /* renamed from: d */
    private UserCard f7514d;
    /* renamed from: e */
    private String f7515e;
    /* renamed from: f */
    private CVV2Status f7516f;
    /* renamed from: g */
    private String f7517g;
    /* renamed from: h */
    private String f7518h;
    /* renamed from: i */
    private String f7519i;
    /* renamed from: j */
    private String f7520j;
    /* renamed from: k */
    private String f7521k;
    /* renamed from: l */
    private String f7522l;
    /* renamed from: m */
    private String f7523m;
    /* renamed from: n */
    private String f7524n;
    /* renamed from: o */
    private String f7525o;
    /* renamed from: p */
    private String f7526p;
    /* renamed from: q */
    private String f7527q;
    /* renamed from: r */
    private String f7528r;
    /* renamed from: s */
    private int f7529s;
    /* renamed from: t */
    private String f7530t;
    /* renamed from: u */
    private boolean f7531u;

    /* renamed from: com.persianswitch.sdk.payment.model.PaymentProfile$1 */
    static class C22931 implements Creator<PaymentProfile> {
        C22931() {
        }

        /* renamed from: a */
        public PaymentProfile m11161a(Parcel parcel) {
            return new PaymentProfile(parcel);
        }

        /* renamed from: a */
        public PaymentProfile[] m11162a(int i) {
            return new PaymentProfile[i];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m11161a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m11162a(i);
        }
    }

    public PaymentProfile() {
        this.f7511a = 1001;
        this.f7512b = TransactionStatus.UNKNOWN;
        this.f7516f = CVV2Status.CVV2_REQUIRED;
    }

    private PaymentProfile(Parcel parcel) {
        this.f7511a = 1001;
        this.f7512b = TransactionStatus.UNKNOWN;
        this.f7511a = parcel.readInt();
        this.f7512b = TransactionStatus.valueOf(parcel.readString());
        this.f7513c = parcel.readLong();
        this.f7514d = (UserCard) parcel.readParcelable(UserCard.class.getClassLoader());
        this.f7515e = parcel.readString();
        this.f7516f = parcel.readInt() == CVV2Status.CVV2_REQUIRED.m11121a() ? CVV2Status.CVV2_REQUIRED : CVV2Status.CVV2_NOT_REQUIRED_STATUS;
        this.f7517g = parcel.readString();
        this.f7518h = parcel.readString();
        this.f7519i = parcel.readString();
        this.f7520j = parcel.readString();
        this.f7521k = parcel.readString();
        this.f7522l = parcel.readString();
        this.f7523m = parcel.readString();
        this.f7524n = parcel.readString();
        this.f7525o = parcel.readString();
        this.f7526p = parcel.readString();
        this.f7527q = parcel.readString();
        this.f7528r = parcel.readString();
        this.f7529s = parcel.readInt();
        this.f7530t = parcel.readString();
        this.f7531u = parcel.readInt() > 0;
    }

    /* renamed from: a */
    public String m11163a() {
        return this.f7525o;
    }

    /* renamed from: a */
    public void m11164a(int i) {
        this.f7511a = i;
    }

    /* renamed from: a */
    public void m11165a(long j) {
        this.f7513c = j;
    }

    /* renamed from: a */
    public void m11166a(CVV2Status cVV2Status) {
        this.f7516f = cVV2Status;
    }

    /* renamed from: a */
    public void m11167a(TransactionStatus transactionStatus) {
        this.f7512b = transactionStatus;
    }

    /* renamed from: a */
    public void m11168a(UserCard userCard) {
        this.f7514d = userCard;
    }

    /* renamed from: a */
    public void m11169a(String str) {
        this.f7525o = str;
    }

    /* renamed from: a */
    public void m11170a(boolean z) {
        this.f7531u = z;
    }

    /* renamed from: b */
    public String m11171b() {
        return this.f7526p;
    }

    /* renamed from: b */
    public void m11172b(int i) {
        this.f7529s = i;
    }

    /* renamed from: b */
    public void m11173b(String str) {
        this.f7526p = str;
    }

    /* renamed from: c */
    public int m11174c() {
        return this.f7511a;
    }

    /* renamed from: c */
    public void m11175c(String str) {
        this.f7515e = str;
    }

    /* renamed from: d */
    public CVV2Status m11176d() {
        return this.f7516f;
    }

    /* renamed from: d */
    public void m11177d(String str) {
        this.f7522l = str;
    }

    public int describeContents() {
        return 0;
    }

    /* renamed from: e */
    public String m11178e() {
        return this.f7515e;
    }

    /* renamed from: e */
    public void m11179e(String str) {
        this.f7523m = str;
    }

    /* renamed from: f */
    public UserCard m11180f() {
        return this.f7514d;
    }

    /* renamed from: f */
    public void m11181f(String str) {
        this.f7519i = str;
    }

    /* renamed from: g */
    public String m11182g() {
        return this.f7522l;
    }

    /* renamed from: g */
    public void m11183g(String str) {
        this.f7520j = str;
    }

    /* renamed from: h */
    public String m11184h() {
        return this.f7523m;
    }

    /* renamed from: h */
    public void m11185h(String str) {
        this.f7521k = str;
    }

    /* renamed from: i */
    public int m11186i() {
        return this.f7529s;
    }

    /* renamed from: i */
    public void m11187i(String str) {
        this.f7517g = str;
    }

    /* renamed from: j */
    public TransactionStatus m11188j() {
        return this.f7512b;
    }

    /* renamed from: j */
    public void m11189j(String str) {
        this.f7518h = str;
    }

    /* renamed from: k */
    public String m11190k() {
        return this.f7519i;
    }

    /* renamed from: k */
    public void m11191k(String str) {
        this.f7530t = str;
    }

    /* renamed from: l */
    public String m11192l() {
        return this.f7520j;
    }

    /* renamed from: l */
    public void m11193l(String str) {
        this.f7528r = str;
    }

    /* renamed from: m */
    public String m11194m() {
        return this.f7521k;
    }

    /* renamed from: m */
    public void m11195m(String str) {
        this.f7527q = str;
    }

    /* renamed from: n */
    public String m11196n() {
        return this.f7517g;
    }

    /* renamed from: o */
    public String m11197o() {
        return this.f7518h;
    }

    /* renamed from: p */
    public String m11198p() {
        return this.f7530t;
    }

    /* renamed from: q */
    public String m11199q() {
        return this.f7528r;
    }

    /* renamed from: r */
    public String m11200r() {
        return this.f7527q;
    }

    /* renamed from: s */
    public boolean m11201s() {
        return this.f7512b == TransactionStatus.UNKNOWN && this.f7531u;
    }

    /* renamed from: t */
    public Long m11202t() {
        return Long.valueOf(this.f7513c);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f7511a);
        parcel.writeString(this.f7512b.toString());
        parcel.writeLong(this.f7513c);
        parcel.writeParcelable(this.f7514d, i);
        parcel.writeString(this.f7515e);
        parcel.writeInt(this.f7516f.m11121a());
        parcel.writeString(this.f7517g);
        parcel.writeString(this.f7518h);
        parcel.writeString(this.f7519i);
        parcel.writeString(this.f7520j);
        parcel.writeString(this.f7521k);
        parcel.writeString(this.f7522l);
        parcel.writeString(this.f7523m);
        parcel.writeString(this.f7524n);
        parcel.writeString(this.f7525o);
        parcel.writeString(this.f7526p);
        parcel.writeString(this.f7527q);
        parcel.writeString(this.f7528r);
        parcel.writeInt(this.f7529s);
        parcel.writeString(this.f7530t);
        parcel.writeInt(this.f7531u ? 1 : 0);
    }
}
