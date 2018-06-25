package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.firebase.C1897b;
import com.google.firebase.auth.C1824k;
import com.google.firebase.auth.C1881j;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.zzd;
import java.util.ArrayList;
import java.util.List;

@Class(creator = "DefaultFirebaseUserCreator")
public class zzl extends FirebaseUser {
    public static final Creator<zzl> CREATOR = new C1873m();
    @Field(getter = "getCachedTokenState", id = 1)
    /* renamed from: a */
    private zzao f5551a;
    @Field(getter = "getDefaultAuthUserInfo", id = 2)
    /* renamed from: b */
    private zzh f5552b;
    @Field(getter = "getFirebaseAppName", id = 3)
    /* renamed from: c */
    private String f5553c;
    @Field(getter = "getUserType", id = 4)
    /* renamed from: d */
    private String f5554d;
    @Field(getter = "getUserInfos", id = 5)
    /* renamed from: e */
    private List<zzh> f5555e;
    @Field(getter = "getProviders", id = 6)
    /* renamed from: f */
    private List<String> f5556f;
    @Field(getter = "getCurrentVersion", id = 7)
    /* renamed from: g */
    private String f5557g;
    @Field(getter = "isAnonymous", id = 8)
    /* renamed from: h */
    private Boolean f5558h;
    @Field(getter = "getMetadata", id = 9)
    /* renamed from: i */
    private zzn f5559i;
    @Field(getter = "isNewUser", id = 10)
    /* renamed from: j */
    private boolean f5560j;
    @Field(getter = "getDefaultOAuthCredential", id = 11)
    /* renamed from: k */
    private zzd f5561k;

    @Constructor
    zzl(@Param(id = 1) zzao zzao, @Param(id = 2) zzh zzh, @Param(id = 3) String str, @Param(id = 4) String str2, @Param(id = 5) List<zzh> list, @Param(id = 6) List<String> list2, @Param(id = 7) String str3, @Param(id = 8) Boolean bool, @Param(id = 9) zzn zzn, @Param(id = 10) boolean z, @Param(id = 11) zzd zzd) {
        this.f5551a = zzao;
        this.f5552b = zzh;
        this.f5553c = str;
        this.f5554d = str2;
        this.f5555e = list;
        this.f5556f = list2;
        this.f5557g = str3;
        this.f5558h = bool;
        this.f5559i = zzn;
        this.f5560j = z;
        this.f5561k = zzd;
    }

    public zzl(C1897b c1897b, List<? extends C1824k> list) {
        Preconditions.checkNotNull(c1897b);
        this.f5553c = c1897b.m8695b();
        this.f5554d = "com.google.firebase.auth.internal.DefaultFirebaseUser";
        this.f5557g = "2";
        mo3027a((List) list);
    }

    /* renamed from: a */
    public final FirebaseUser mo3027a(List<? extends C1824k> list) {
        Preconditions.checkNotNull(list);
        this.f5555e = new ArrayList(list.size());
        this.f5556f = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            C1824k c1824k = (C1824k) list.get(i);
            if (c1824k.mo3026k().equals("firebase")) {
                this.f5552b = (zzh) c1824k;
            } else {
                this.f5556f.add(c1824k.mo3026k());
            }
            this.f5555e.add((zzh) c1824k);
        }
        if (this.f5552b == null) {
            this.f5552b = (zzh) this.f5555e.get(0);
        }
        return this;
    }

    /* renamed from: a */
    public final zzl m8647a(String str) {
        this.f5557g = str;
        return this;
    }

    /* renamed from: a */
    public String mo3028a() {
        return this.f5552b.m8639a();
    }

    /* renamed from: a */
    public final void mo3029a(zzao zzao) {
        this.f5551a = (zzao) Preconditions.checkNotNull(zzao);
    }

    /* renamed from: a */
    public final void m8650a(zzn zzn) {
        this.f5559i = zzn;
    }

    /* renamed from: b */
    public boolean mo3030b() {
        if (this.f5558h == null || this.f5558h.booleanValue()) {
            String str = TtmlNode.ANONYMOUS_REGION_ID;
            if (this.f5551a != null) {
                C1881j a = C1879t.m8633a(this.f5551a.zzaw());
                str = a != null ? a.m8666a() : TtmlNode.ANONYMOUS_REGION_ID;
            }
            boolean z = mo3032d().size() <= 1 && (str == null || !str.equals("custom"));
            this.f5558h = Boolean.valueOf(z);
        }
        return this.f5558h.booleanValue();
    }

    /* renamed from: c */
    public final List<String> mo3031c() {
        return this.f5556f;
    }

    /* renamed from: d */
    public List<? extends C1824k> mo3032d() {
        return this.f5555e;
    }

    /* renamed from: e */
    public final /* synthetic */ FirebaseUser mo3033e() {
        this.f5558h = Boolean.valueOf(false);
        return this;
    }

    /* renamed from: f */
    public final C1897b mo3034f() {
        return C1897b.m8679a(this.f5553c);
    }

    /* renamed from: g */
    public final zzao mo3035g() {
        return this.f5551a;
    }

    /* renamed from: h */
    public final String mo3036h() {
        return this.f5551a.toJson();
    }

    /* renamed from: i */
    public final String mo3037i() {
        return mo3035g().zzaw();
    }

    /* renamed from: j */
    public FirebaseUserMetadata mo3038j() {
        return this.f5559i;
    }

    /* renamed from: k */
    public String mo3026k() {
        return this.f5552b.mo3026k();
    }

    /* renamed from: l */
    public final List<zzh> m8661l() {
        return this.f5555e;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, mo3035g(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.f5552b, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.f5553c, false);
        SafeParcelWriter.writeString(parcel, 4, this.f5554d, false);
        SafeParcelWriter.writeTypedList(parcel, 5, this.f5555e, false);
        SafeParcelWriter.writeStringList(parcel, 6, mo3031c(), false);
        SafeParcelWriter.writeString(parcel, 7, this.f5557g, false);
        SafeParcelWriter.writeBooleanObject(parcel, 8, Boolean.valueOf(mo3030b()), false);
        SafeParcelWriter.writeParcelable(parcel, 9, mo3038j(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 10, this.f5560j);
        SafeParcelWriter.writeParcelable(parcel, 11, this.f5561k, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
