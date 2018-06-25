package com.google.android.gms.common.logging;

import android.annotation.SuppressLint;
import android.util.Log;
import com.google.android.gms.common.internal.GmsLogger;
import java.util.Locale;

public class Logger {
    private final String mTag;
    private final String zzud;
    private final GmsLogger zzvd;
    private final int zzve;

    private Logger(String str, String str2) {
        this.zzud = str2;
        this.mTag = str;
        this.zzvd = new GmsLogger(str);
        int i = 2;
        while (7 >= i && !Log.isLoggable(this.mTag, i)) {
            i++;
        }
        this.zzve = i;
    }

    public Logger(String str, String... strArr) {
        String str2;
        if (strArr.length == 0) {
            str2 = TtmlNode.ANONYMOUS_REGION_ID;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            for (String str3 : strArr) {
                if (stringBuilder.length() > 1) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(str3);
            }
            stringBuilder.append(']').append(' ');
            str2 = stringBuilder.toString();
        }
        this(str, str2);
    }

    /* renamed from: d */
    public void m8454d(String str, Throwable th, Object... objArr) {
        if (isLoggable(3)) {
            Log.d(this.mTag, format(str, objArr), th);
        }
    }

    /* renamed from: d */
    public void m8455d(String str, Object... objArr) {
        if (isLoggable(3)) {
            Log.d(this.mTag, format(str, objArr));
        }
    }

    /* renamed from: e */
    public void m8456e(String str, Throwable th, Object... objArr) {
        Log.e(this.mTag, format(str, objArr), th);
    }

    /* renamed from: e */
    public void m8457e(String str, Object... objArr) {
        Log.e(this.mTag, format(str, objArr));
    }

    public String elidePii(Object obj) {
        boolean canLogPii = this.zzvd.canLogPii();
        if (obj == null) {
            return "<NULL>";
        }
        String trim = obj.toString().trim();
        if (trim.isEmpty()) {
            return "<EMPTY>";
        }
        if (canLogPii) {
            return trim;
        }
        return String.format("<ELLIDED:%s>", new Object[]{Integer.valueOf(trim.hashCode())});
    }

    protected String format(String str, Object... objArr) {
        if (objArr != null && objArr.length > 0) {
            str = String.format(Locale.US, str, objArr);
        }
        return this.zzud.concat(str);
    }

    public String getTag() {
        return this.mTag;
    }

    /* renamed from: i */
    public void m8458i(String str, Throwable th, Object... objArr) {
        Log.i(this.mTag, format(str, objArr), th);
    }

    /* renamed from: i */
    public void m8459i(String str, Object... objArr) {
        Log.i(this.mTag, format(str, objArr));
    }

    public boolean isLoggable(int i) {
        return this.zzve <= i;
    }

    public boolean isPiiLoggable() {
        return this.zzvd.canLogPii();
    }

    /* renamed from: v */
    public void m8460v(String str, Throwable th, Object... objArr) {
        if (isLoggable(2)) {
            Log.v(this.mTag, format(str, objArr), th);
        }
    }

    /* renamed from: v */
    public void m8461v(String str, Object... objArr) {
        if (isLoggable(2)) {
            Log.v(this.mTag, format(str, objArr));
        }
    }

    /* renamed from: w */
    public void m8462w(String str, Throwable th, Object... objArr) {
        Log.w(this.mTag, format(str, objArr), th);
    }

    /* renamed from: w */
    public void m8463w(String str, Object... objArr) {
        Log.w(this.mTag, format(str, objArr));
    }

    /* renamed from: w */
    public void m8464w(Throwable th) {
        Log.w(this.mTag, th);
    }

    public void wtf(String str, Throwable th, Object... objArr) {
        Log.wtf(this.mTag, format(str, objArr), th);
    }

    @SuppressLint({"WtfWithoutException"})
    public void wtf(String str, Object... objArr) {
        Log.wtf(this.mTag, format(str, objArr));
    }

    public void wtf(Throwable th) {
        Log.wtf(this.mTag, th);
    }
}
