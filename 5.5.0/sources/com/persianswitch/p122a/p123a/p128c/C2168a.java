package com.persianswitch.p122a.p123a.p128c;

import java.io.File;
import java.io.IOException;

/* renamed from: com.persianswitch.a.a.c.a */
public interface C2168a {
    /* renamed from: a */
    public static final C2168a f6575a = new C21691();

    /* renamed from: com.persianswitch.a.a.c.a$1 */
    static class C21691 implements C2168a {
        C21691() {
        }

        /* renamed from: a */
        public void mo3148a(File file) {
            if (!file.delete() && file.exists()) {
                throw new IOException("failed to delete " + file);
            }
        }

        /* renamed from: a */
        public void mo3149a(File file, File file2) {
            mo3148a(file2);
            if (!file.renameTo(file2)) {
                throw new IOException("failed to rename " + file + " to " + file2);
            }
        }

        /* renamed from: b */
        public boolean mo3150b(File file) {
            return file.exists();
        }

        /* renamed from: c */
        public long mo3151c(File file) {
            return file.length();
        }
    }

    /* renamed from: a */
    void mo3148a(File file);

    /* renamed from: a */
    void mo3149a(File file, File file2);

    /* renamed from: b */
    boolean mo3150b(File file);

    /* renamed from: c */
    long mo3151c(File file);
}
