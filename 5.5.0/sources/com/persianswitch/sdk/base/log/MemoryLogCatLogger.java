package com.persianswitch.sdk.base.log;

import android.util.Log;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class MemoryLogCatLogger extends DefaultLogger {
    /* renamed from: a */
    Queue<String> f7069a = new LinkedList();

    /* renamed from: a */
    private void m10653a(int i, String str, String str2) {
        if (this.f7069a.size() >= 50) {
            this.f7069a.poll();
        }
        String str3 = "black";
        switch (i) {
            case 3:
                str3 = "blue";
                Log.d(str, str2);
                break;
            case 4:
                str3 = "green";
                break;
            case 5:
                str3 = "yellow";
                break;
            case 6:
                str3 = "red";
                break;
        }
        this.f7069a.add(String.format(Locale.US, "<font color=\"%s\">%s</font>: %s<br/>", new Object[]{str3, str, str2}));
    }

    /* renamed from: a */
    public void mo3249a(int i, String str, String str2, Throwable th, Object... objArr) {
        super.mo3249a(i, str, str2, th, objArr);
        String str3 = (str2 == null || str2.length() != 0) ? str2 : null;
        if (str3 == null) {
            if (th == null) {
                return;
            }
        } else if (objArr.length > 0) {
            str3 = String.format(Locale.US, str3, objArr);
        }
        m10653a(i, str, str3);
    }
}
