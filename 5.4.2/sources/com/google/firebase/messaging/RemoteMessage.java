package com.google.firebase.messaging;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.p022f.C0464a;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.Map;

@Class(creator = "RemoteMessageCreator")
@Reserved({1})
public final class RemoteMessage extends AbstractSafeParcelable {
    public static final Creator<RemoteMessage> CREATOR = new C1963g();
    @Field(id = 2)
    /* renamed from: a */
    Bundle f5790a;
    /* renamed from: b */
    private Map<String, String> f5791b;
    /* renamed from: c */
    private C1955a f5792c;

    /* renamed from: com.google.firebase.messaging.RemoteMessage$a */
    public static class C1955a {
        /* renamed from: a */
        private final String f5778a;
        /* renamed from: b */
        private final String f5779b;
        /* renamed from: c */
        private final String[] f5780c;
        /* renamed from: d */
        private final String f5781d;
        /* renamed from: e */
        private final String f5782e;
        /* renamed from: f */
        private final String[] f5783f;
        /* renamed from: g */
        private final String f5784g;
        /* renamed from: h */
        private final String f5785h;
        /* renamed from: i */
        private final String f5786i;
        /* renamed from: j */
        private final String f5787j;
        /* renamed from: k */
        private final String f5788k;
        /* renamed from: l */
        private final Uri f5789l;

        private C1955a(Bundle bundle) {
            this.f5778a = C1960d.m8917a(bundle, "gcm.n.title");
            this.f5779b = C1960d.m8924b(bundle, "gcm.n.title");
            this.f5780c = C1955a.m8909a(bundle, "gcm.n.title");
            this.f5781d = C1960d.m8917a(bundle, "gcm.n.body");
            this.f5782e = C1960d.m8924b(bundle, "gcm.n.body");
            this.f5783f = C1955a.m8909a(bundle, "gcm.n.body");
            this.f5784g = C1960d.m8917a(bundle, "gcm.n.icon");
            this.f5785h = C1960d.m8927d(bundle);
            this.f5786i = C1960d.m8917a(bundle, "gcm.n.tag");
            this.f5787j = C1960d.m8917a(bundle, "gcm.n.color");
            this.f5788k = C1960d.m8917a(bundle, "gcm.n.click_action");
            this.f5789l = C1960d.m8922b(bundle);
        }

        /* renamed from: a */
        private static String[] m8909a(Bundle bundle, String str) {
            Object[] c = C1960d.m8926c(bundle, str);
            if (c == null) {
                return null;
            }
            String[] strArr = new String[c.length];
            for (int i = 0; i < c.length; i++) {
                strArr[i] = String.valueOf(c[i]);
            }
            return strArr;
        }

        /* renamed from: a */
        public String m8910a() {
            return this.f5781d;
        }
    }

    @Constructor
    public RemoteMessage(@Param(id = 2) Bundle bundle) {
        this.f5790a = bundle;
    }

    /* renamed from: a */
    public final String m8911a() {
        return this.f5790a.getString("from");
    }

    /* renamed from: b */
    public final Map<String, String> m8912b() {
        if (this.f5791b == null) {
            this.f5791b = new C0464a();
            for (String str : this.f5790a.keySet()) {
                Object obj = this.f5790a.get(str);
                if (obj instanceof String) {
                    String str2 = (String) obj;
                    if (!(str.startsWith("google.") || str.startsWith("gcm.") || str.equals("from") || str.equals("message_type") || str.equals("collapse_key"))) {
                        this.f5791b.put(str, str2);
                    }
                }
            }
        }
        return this.f5791b;
    }

    /* renamed from: c */
    public final C1955a m8913c() {
        if (this.f5792c == null && C1960d.m8921a(this.f5790a)) {
            this.f5792c = new C1955a(this.f5790a);
        }
        return this.f5792c;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, this.f5790a, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
