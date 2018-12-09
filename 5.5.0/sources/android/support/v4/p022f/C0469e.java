package android.support.v4.p022f;

import android.util.Log;
import java.io.Writer;

/* renamed from: android.support.v4.f.e */
public class C0469e extends Writer {
    /* renamed from: a */
    private final String f1245a;
    /* renamed from: b */
    private StringBuilder f1246b = new StringBuilder(128);

    public C0469e(String str) {
        this.f1245a = str;
    }

    /* renamed from: a */
    private void m2015a() {
        if (this.f1246b.length() > 0) {
            Log.d(this.f1245a, this.f1246b.toString());
            this.f1246b.delete(0, this.f1246b.length());
        }
    }

    public void close() {
        m2015a();
    }

    public void flush() {
        m2015a();
    }

    public void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == '\n') {
                m2015a();
            } else {
                this.f1246b.append(c);
            }
        }
    }
}
