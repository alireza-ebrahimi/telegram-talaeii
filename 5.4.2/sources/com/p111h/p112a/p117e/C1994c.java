package com.p111h.p112a.p117e;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import com.p111h.p112a.p114b.C1977a;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/* renamed from: com.h.a.e.c */
public class C1994c {
    /* renamed from: a */
    public static Map<String, Object> m9013a(C1977a c1977a) {
        Map<String, Object> hashMap = new HashMap();
        Map hashMap2 = new HashMap();
        hashMap2.put("number", C1995d.m9016b(c1977a.m8955f()));
        hashMap2.put("cvc", C1995d.m9016b(c1977a.m8956g()));
        hashMap2.put("exp_month", c1977a.m8957h());
        hashMap2.put("exp_year", c1977a.m8958i());
        hashMap2.put("name", C1995d.m9016b(c1977a.m8959j()));
        hashMap2.put(C1797b.CURRENCY, C1995d.m9016b(c1977a.m8966q()));
        hashMap2.put("address_line1", C1995d.m9016b(c1977a.m8960k()));
        hashMap2.put("address_line2", C1995d.m9016b(c1977a.m8961l()));
        hashMap2.put("address_city", C1995d.m9016b(c1977a.m8962m()));
        hashMap2.put("address_zip", C1995d.m9016b(c1977a.m8963n()));
        hashMap2.put("address_state", C1995d.m9016b(c1977a.m8964o()));
        hashMap2.put("address_country", C1995d.m9016b(c1977a.m8965p()));
        Iterator it = new HashSet(hashMap2.keySet()).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (hashMap2.get(str) == null) {
                hashMap2.remove(str);
            }
        }
        hashMap.put("card", hashMap2);
        return hashMap;
    }
}
