package com.persianswitch.sdk.base.log;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

class DefaultLogger implements ILogger {
    DefaultLogger() {
    }

    /* renamed from: a */
    String m10651a(Throwable th) {
        Writer stringWriter = new StringWriter(256);
        PrintWriter printWriter = new PrintWriter(stringWriter, false);
        th.printStackTrace(printWriter);
        printWriter.flush();
        printWriter.close();
        return stringWriter.toString();
    }

    /* renamed from: a */
    public void mo3249a(int i, String str, String str2, Throwable th, Object... objArr) {
        String str3 = (str2 == null || str2.length() != 0) ? str2 : null;
        if (str3 != null) {
            if (objArr.length > 0) {
                str3 = String.format(Locale.US, str3, objArr);
            }
            if (th != null) {
                str3 = str3 + "\n" + m10651a(th);
            }
        } else if (th != null) {
            str3 = m10651a(th);
        } else {
            return;
        }
        switch (i) {
            case 2:
                Log.v(str, str3);
                return;
            case 3:
                Log.d(str, str3);
                return;
            case 4:
                Log.i(str, str3);
                return;
            case 5:
                Log.w(str, str3);
                return;
            case 6:
                Log.e(str, str3);
                return;
            case 7:
                Log.e(str, str3);
                return;
            default:
                return;
        }
    }
}
