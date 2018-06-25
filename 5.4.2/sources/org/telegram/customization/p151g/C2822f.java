package org.telegram.customization.p151g;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.util.ArrayList;
import utils.p178a.C3791b;

/* renamed from: org.telegram.customization.g.f */
public class C2822f {
    /* renamed from: a */
    public static final ArrayList<C2821a> f9277a = new ArrayList();

    /* renamed from: org.telegram.customization.g.f$a */
    private static class C2821a {
        /* renamed from: a */
        public String f9275a;
        /* renamed from: b */
        public String f9276b;

        public C2821a() {
            this.f9275a = TtmlNode.ANONYMOUS_REGION_ID;
            this.f9276b = TtmlNode.ANONYMOUS_REGION_ID;
        }

        public C2821a(String str, String str2) {
            this.f9275a = str;
            this.f9276b = str2;
        }
    }

    static {
        f9277a.add(new C2821a("creator", "publisher"));
        f9277a.add(new C2821a("downloadCount", "dl_count"));
        f9277a.add(new C2821a("imageLinkOnServer", "image_link"));
    }

    /* renamed from: a */
    public static int m13155a(Context context) {
        return C3791b.m14004k(context);
    }

    /* renamed from: a */
    public static String m13156a(int i, String str, Context context, String str2, String str3) {
        String replace = str.replace("http://", TtmlNode.ANONYMOUS_REGION_ID).replace("https://", TtmlNode.ANONYMOUS_REGION_ID);
        replace = replace.replace(replace.substring(0, replace.indexOf("/") + 1), TtmlNode.ANONYMOUS_REGION_ID);
        if (str2.contentEquals("set-1067")) {
            if (i > C3791b.m13895B(context, str3).size()) {
                return null;
            }
        } else if (i > 5) {
            return null;
        }
        return replace;
    }

    /* renamed from: b */
    public static String m13157b(Context context) {
        try {
            Object networkOperatorName = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
            if (!TextUtils.isEmpty(networkOperatorName)) {
                return networkOperatorName;
            }
        } catch (Exception e) {
        }
        return TtmlNode.ANONYMOUS_REGION_ID;
    }
}
