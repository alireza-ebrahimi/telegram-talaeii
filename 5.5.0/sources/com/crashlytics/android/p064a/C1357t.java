package com.crashlytics.android.p064a;

import android.os.Bundle;
import com.crashlytics.android.p064a.ad.C1332b;
import com.google.android.gms.actions.SearchIntents;
import com.google.firebase.analytics.FirebaseAnalytics.C1796a;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import p033b.p034a.p035a.p036a.C1230c;

/* renamed from: com.crashlytics.android.a.t */
public class C1357t {
    /* renamed from: a */
    private static final Set<String> f4118a = new HashSet(Arrays.asList(new String[]{"app_clear_data", "app_exception", "app_remove", "app_upgrade", "app_install", "app_update", "firebase_campaign", "error", "first_open", "first_visit", "in_app_purchase", "notification_dismiss", "notification_foreground", "notification_open", "notification_receive", "os_update", "session_start", "user_engagement", "ad_exposure", "adunit_exposure", "ad_query", "ad_activeview", "ad_impression", "ad_click", "screen_view", "firebase_extra_parameter"}));

    /* renamed from: a */
    private Double m6897a(Object obj) {
        String valueOf = String.valueOf(obj);
        return valueOf == null ? null : Double.valueOf(valueOf);
    }

    /* renamed from: a */
    private String m6898a(String str) {
        if (str == null || str.length() == 0) {
            return "fabric_unnamed_event";
        }
        if (f4118a.contains(str)) {
            return "fabric_" + str;
        }
        String replaceAll = str.replaceAll("[^\\p{Alnum}_]+", "_");
        if (replaceAll.startsWith("ga_") || replaceAll.startsWith("google_") || replaceAll.startsWith("firebase_") || !Character.isLetter(replaceAll.charAt(0))) {
            replaceAll = "fabric_" + replaceAll;
        }
        return replaceAll.length() > 40 ? replaceAll.substring(0, 40) : replaceAll;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private java.lang.String m6899a(java.lang.String r6, boolean r7) {
        /*
        r5 = this;
        r3 = 2;
        r2 = 1;
        r0 = 0;
        r1 = -1;
        if (r7 == 0) goto L_0x0011;
    L_0x0006:
        r4 = r6.hashCode();
        switch(r4) {
            case -902468296: goto L_0x002c;
            case 103149417: goto L_0x0037;
            case 1743324417: goto L_0x0021;
            default: goto L_0x000d;
        };
    L_0x000d:
        r4 = r1;
    L_0x000e:
        switch(r4) {
            case 0: goto L_0x0042;
            case 1: goto L_0x0046;
            case 2: goto L_0x004a;
            default: goto L_0x0011;
        };
    L_0x0011:
        r4 = r6.hashCode();
        switch(r4) {
            case -2131650889: goto L_0x00cd;
            case -1183699191: goto L_0x00b3;
            case -938102371: goto L_0x008f;
            case -906336856: goto L_0x0079;
            case -902468296: goto L_0x009a;
            case -389087554: goto L_0x006e;
            case 23457852: goto L_0x0058;
            case 103149417: goto L_0x00a6;
            case 109400031: goto L_0x0084;
            case 196004670: goto L_0x00c0;
            case 1664021448: goto L_0x0063;
            case 1743324417: goto L_0x004e;
            default: goto L_0x0018;
        };
    L_0x0018:
        r0 = r1;
    L_0x0019:
        switch(r0) {
            case 0: goto L_0x00da;
            case 1: goto L_0x00df;
            case 2: goto L_0x00e4;
            case 3: goto L_0x00e9;
            case 4: goto L_0x00ee;
            case 5: goto L_0x00f3;
            case 6: goto L_0x00f8;
            case 7: goto L_0x00fd;
            case 8: goto L_0x0102;
            case 9: goto L_0x0107;
            case 10: goto L_0x010c;
            case 11: goto L_0x0111;
            default: goto L_0x001c;
        };
    L_0x001c:
        r0 = r5.m6898a(r6);
    L_0x0020:
        return r0;
    L_0x0021:
        r4 = "purchase";
        r4 = r6.equals(r4);
        if (r4 == 0) goto L_0x000d;
    L_0x002a:
        r4 = r0;
        goto L_0x000e;
    L_0x002c:
        r4 = "signUp";
        r4 = r6.equals(r4);
        if (r4 == 0) goto L_0x000d;
    L_0x0035:
        r4 = r2;
        goto L_0x000e;
    L_0x0037:
        r4 = "login";
        r4 = r6.equals(r4);
        if (r4 == 0) goto L_0x000d;
    L_0x0040:
        r4 = r3;
        goto L_0x000e;
    L_0x0042:
        r0 = "failed_ecommerce_purchase";
        goto L_0x0020;
    L_0x0046:
        r0 = "failed_sign_up";
        goto L_0x0020;
    L_0x004a:
        r0 = "failed_login";
        goto L_0x0020;
    L_0x004e:
        r2 = "purchase";
        r2 = r6.equals(r2);
        if (r2 == 0) goto L_0x0018;
    L_0x0057:
        goto L_0x0019;
    L_0x0058:
        r0 = "addToCart";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x0061:
        r0 = r2;
        goto L_0x0019;
    L_0x0063:
        r0 = "startCheckout";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x006c:
        r0 = r3;
        goto L_0x0019;
    L_0x006e:
        r0 = "contentView";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x0077:
        r0 = 3;
        goto L_0x0019;
    L_0x0079:
        r0 = "search";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x0082:
        r0 = 4;
        goto L_0x0019;
    L_0x0084:
        r0 = "share";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x008d:
        r0 = 5;
        goto L_0x0019;
    L_0x008f:
        r0 = "rating";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x0098:
        r0 = 6;
        goto L_0x0019;
    L_0x009a:
        r0 = "signUp";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00a3:
        r0 = 7;
        goto L_0x0019;
    L_0x00a6:
        r0 = "login";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00af:
        r0 = 8;
        goto L_0x0019;
    L_0x00b3:
        r0 = "invite";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00bc:
        r0 = 9;
        goto L_0x0019;
    L_0x00c0:
        r0 = "levelStart";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00c9:
        r0 = 10;
        goto L_0x0019;
    L_0x00cd:
        r0 = "levelEnd";
        r0 = r6.equals(r0);
        if (r0 == 0) goto L_0x0018;
    L_0x00d6:
        r0 = 11;
        goto L_0x0019;
    L_0x00da:
        r0 = "ecommerce_purchase";
        goto L_0x0020;
    L_0x00df:
        r0 = "add_to_cart";
        goto L_0x0020;
    L_0x00e4:
        r0 = "begin_checkout";
        goto L_0x0020;
    L_0x00e9:
        r0 = "select_content";
        goto L_0x0020;
    L_0x00ee:
        r0 = "search";
        goto L_0x0020;
    L_0x00f3:
        r0 = "share";
        goto L_0x0020;
    L_0x00f8:
        r0 = "rate_content";
        goto L_0x0020;
    L_0x00fd:
        r0 = "sign_up";
        goto L_0x0020;
    L_0x0102:
        r0 = "login";
        goto L_0x0020;
    L_0x0107:
        r0 = "invite";
        goto L_0x0020;
    L_0x010c:
        r0 = "level_start";
        goto L_0x0020;
    L_0x0111:
        r0 = "level_end";
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.a.t.a(java.lang.String, boolean):java.lang.String");
    }

    /* renamed from: a */
    private void m6900a(Bundle bundle, String str, Double d) {
        Double a = m6897a((Object) d);
        if (a != null) {
            bundle.putDouble(str, a.doubleValue());
        }
    }

    /* renamed from: a */
    private void m6901a(Bundle bundle, String str, Integer num) {
        if (num != null) {
            bundle.putInt(str, num.intValue());
        }
    }

    /* renamed from: a */
    private void m6902a(Bundle bundle, String str, Long l) {
        if (l != null) {
            bundle.putLong(str, l.longValue());
        }
    }

    /* renamed from: a */
    private void m6903a(Bundle bundle, String str, String str2) {
        if (str2 != null) {
            bundle.putString(str, str2);
        }
    }

    /* renamed from: a */
    private void m6904a(Bundle bundle, Map<String, Object> map) {
        for (Entry entry : map.entrySet()) {
            Object value = entry.getValue();
            String b = m6907b((String) entry.getKey());
            if (value instanceof String) {
                bundle.putString(b, entry.getValue().toString());
            } else if (value instanceof Double) {
                bundle.putDouble(b, ((Double) entry.getValue()).doubleValue());
            } else if (value instanceof Long) {
                bundle.putLong(b, ((Long) entry.getValue()).longValue());
            } else if (value instanceof Integer) {
                bundle.putInt(b, ((Integer) entry.getValue()).intValue());
            }
        }
    }

    /* renamed from: b */
    private Bundle m6905b(ad adVar) {
        Bundle bundle = new Bundle();
        if ("purchase".equals(adVar.f4036g)) {
            m6903a(bundle, C1797b.ITEM_ID, (String) adVar.f4037h.get("itemId"));
            m6903a(bundle, C1797b.ITEM_NAME, (String) adVar.f4037h.get("itemName"));
            m6903a(bundle, C1797b.ITEM_CATEGORY, (String) adVar.f4037h.get("itemType"));
            m6900a(bundle, C1797b.VALUE, m6906b(adVar.f4037h.get("itemPrice")));
            m6903a(bundle, C1797b.CURRENCY, (String) adVar.f4037h.get(C1797b.CURRENCY));
        } else if ("addToCart".equals(adVar.f4036g)) {
            m6903a(bundle, C1797b.ITEM_ID, (String) adVar.f4037h.get("itemId"));
            m6903a(bundle, C1797b.ITEM_NAME, (String) adVar.f4037h.get("itemName"));
            m6903a(bundle, C1797b.ITEM_CATEGORY, (String) adVar.f4037h.get("itemType"));
            m6900a(bundle, C1797b.PRICE, m6906b(adVar.f4037h.get("itemPrice")));
            m6900a(bundle, C1797b.VALUE, m6906b(adVar.f4037h.get("itemPrice")));
            m6903a(bundle, C1797b.CURRENCY, (String) adVar.f4037h.get(C1797b.CURRENCY));
            bundle.putLong(C1797b.QUANTITY, 1);
        } else if ("startCheckout".equals(adVar.f4036g)) {
            m6902a(bundle, C1797b.QUANTITY, Long.valueOf((long) ((Integer) adVar.f4037h.get("itemCount")).intValue()));
            m6900a(bundle, C1797b.VALUE, m6906b(adVar.f4037h.get("totalPrice")));
            m6903a(bundle, C1797b.CURRENCY, (String) adVar.f4037h.get(C1797b.CURRENCY));
        } else if ("contentView".equals(adVar.f4036g)) {
            m6903a(bundle, C1797b.CONTENT_TYPE, (String) adVar.f4037h.get("contentType"));
            m6903a(bundle, C1797b.ITEM_ID, (String) adVar.f4037h.get("contentId"));
            m6903a(bundle, C1797b.ITEM_NAME, (String) adVar.f4037h.get("contentName"));
        } else if (C1796a.SEARCH.equals(adVar.f4036g)) {
            m6903a(bundle, C1797b.SEARCH_TERM, (String) adVar.f4037h.get(SearchIntents.EXTRA_QUERY));
        } else if (C1796a.SHARE.equals(adVar.f4036g)) {
            m6903a(bundle, C1797b.METHOD, (String) adVar.f4037h.get(C1797b.METHOD));
            m6903a(bundle, C1797b.CONTENT_TYPE, (String) adVar.f4037h.get("contentType"));
            m6903a(bundle, C1797b.ITEM_ID, (String) adVar.f4037h.get("contentId"));
            m6903a(bundle, C1797b.ITEM_NAME, (String) adVar.f4037h.get("contentName"));
        } else if ("rating".equals(adVar.f4036g)) {
            m6903a(bundle, "rating", String.valueOf(adVar.f4037h.get("rating")));
            m6903a(bundle, C1797b.CONTENT_TYPE, (String) adVar.f4037h.get("contentType"));
            m6903a(bundle, C1797b.ITEM_ID, (String) adVar.f4037h.get("contentId"));
            m6903a(bundle, C1797b.ITEM_NAME, (String) adVar.f4037h.get("contentName"));
        } else if ("signUp".equals(adVar.f4036g)) {
            m6903a(bundle, C1797b.METHOD, (String) adVar.f4037h.get(C1797b.METHOD));
        } else if (C1796a.LOGIN.equals(adVar.f4036g)) {
            m6903a(bundle, C1797b.METHOD, (String) adVar.f4037h.get(C1797b.METHOD));
        } else if ("invite".equals(adVar.f4036g)) {
            m6903a(bundle, C1797b.METHOD, (String) adVar.f4037h.get(C1797b.METHOD));
        } else if ("levelStart".equals(adVar.f4036g)) {
            m6903a(bundle, C1797b.LEVEL_NAME, (String) adVar.f4037h.get("levelName"));
        } else if ("levelEnd".equals(adVar.f4036g)) {
            m6900a(bundle, C1797b.SCORE, m6897a(adVar.f4037h.get(C1797b.SCORE)));
            m6903a(bundle, C1797b.LEVEL_NAME, (String) adVar.f4037h.get("levelName"));
            m6901a(bundle, C1797b.SUCCESS, m6908c((String) adVar.f4037h.get(C1797b.SUCCESS)));
        }
        m6904a(bundle, adVar.f4035f);
        return bundle;
    }

    /* renamed from: b */
    private Double m6906b(Object obj) {
        return ((Long) obj) == null ? null : Double.valueOf(new BigDecimal(((Long) obj).longValue()).divide(C1328a.f4007a).doubleValue());
    }

    /* renamed from: b */
    private String m6907b(String str) {
        if (str == null || str.length() == 0) {
            return "fabric_unnamed_parameter";
        }
        String replaceAll = str.replaceAll("[^\\p{Alnum}_]+", "_");
        if (replaceAll.startsWith("ga_") || replaceAll.startsWith("google_") || replaceAll.startsWith("firebase_") || !Character.isLetter(replaceAll.charAt(0))) {
            replaceAll = "fabric_" + replaceAll;
        }
        return replaceAll.length() > 40 ? replaceAll.substring(0, 40) : replaceAll;
    }

    /* renamed from: c */
    private Integer m6908c(String str) {
        if (str == null) {
            return null;
        }
        return Integer.valueOf(str.equals("true") ? 1 : 0);
    }

    /* renamed from: a */
    public C1356s m6909a(ad adVar) {
        boolean z = true;
        boolean z2 = C1332b.CUSTOM.equals(adVar.f4032c) && adVar.f4034e != null;
        boolean z3 = C1332b.PREDEFINED.equals(adVar.f4032c) && adVar.f4036g != null;
        if (!z2 && !z3) {
            return null;
        }
        Bundle b;
        String str;
        if (z3) {
            b = m6905b(adVar);
        } else {
            Bundle bundle = new Bundle();
            if (adVar.f4035f != null) {
                m6904a(bundle, adVar.f4035f);
            }
            b = bundle;
        }
        if (z3) {
            str = (String) adVar.f4037h.get(C1797b.SUCCESS);
            if (str == null || Boolean.parseBoolean(str)) {
                z = false;
            }
            str = m6899a(adVar.f4036g, z);
        } else {
            str = m6898a(adVar.f4034e);
        }
        C1230c.m6414h().mo1062a("Answers", "Logging event into firebase...");
        return new C1356s(str, b);
    }
}
