package com.persianswitch.sdk.payment;

import android.content.Context;
import android.os.Bundle;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.style.HostPersonalizedConfig;
import com.persianswitch.sdk.base.style.PersonalizedConfig;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public final class SDKConfig implements Config {
    /* renamed from: a */
    private static String f7369a = null;

    /* renamed from: a */
    public static String m11029a(Context context) {
        String packageName = context.getPackageName();
        return StringUtils.m10805a("com.persianswitch.sdk.test", packageName) ? "com.persianswitch.sdk.app" : packageName;
    }

    /* renamed from: a */
    public static void m11030a(Bundle bundle) {
        f7369a = bundle.getString("config.url", TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: a */
    public PersonalizedConfig m11031a(int i) {
        return new HostPersonalizedConfig(i);
    }

    /* renamed from: a */
    public String mo3291a() {
        return (!m11036e() || StringUtils.m10803a(f7369a)) ? "https://apms.asanpardakht.net" : f7369a;
    }

    /* renamed from: b */
    public String mo3292b() {
        return "2.0.4";
    }

    /* renamed from: c */
    public String mo3293c() {
        return "10";
    }

    /* renamed from: d */
    public String mo3294d() {
        return "49";
    }

    /* renamed from: e */
    public boolean m11036e() {
        return false;
    }
}
