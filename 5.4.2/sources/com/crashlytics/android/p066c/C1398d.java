package com.crashlytics.android.p066c;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

/* renamed from: com.crashlytics.android.c.d */
class C1398d extends FileOutputStream {
    /* renamed from: a */
    public static final FilenameFilter f4239a = new C13971();
    /* renamed from: b */
    private final String f4240b;
    /* renamed from: c */
    private File f4241c;
    /* renamed from: d */
    private File f4242d;
    /* renamed from: e */
    private boolean f4243e = false;

    /* renamed from: com.crashlytics.android.c.d$1 */
    static class C13971 implements FilenameFilter {
        C13971() {
        }

        public boolean accept(File file, String str) {
            return str.endsWith(".cls_temp");
        }
    }

    public C1398d(File file, String str) {
        super(new File(file, str + ".cls_temp"));
        this.f4240b = file + File.separator + str;
        this.f4241c = new File(this.f4240b + ".cls_temp");
    }

    /* renamed from: a */
    public void m7049a() {
        if (!this.f4243e) {
            this.f4243e = true;
            super.flush();
            super.close();
        }
    }

    public synchronized void close() {
        if (!this.f4243e) {
            this.f4243e = true;
            super.flush();
            super.close();
            File file = new File(this.f4240b + ".cls");
            if (this.f4241c.renameTo(file)) {
                this.f4241c = null;
                this.f4242d = file;
            } else {
                String str = TtmlNode.ANONYMOUS_REGION_ID;
                if (file.exists()) {
                    str = " (target already exists)";
                } else if (!this.f4241c.exists()) {
                    str = " (source does not exist)";
                }
                throw new IOException("Could not rename temp file: " + this.f4241c + " -> " + file + str);
            }
        }
    }
}
