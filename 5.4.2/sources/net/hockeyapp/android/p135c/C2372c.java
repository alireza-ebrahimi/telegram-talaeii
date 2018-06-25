package net.hockeyapp.android.p135c;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import net.hockeyapp.android.C2367a;

/* renamed from: net.hockeyapp.android.c.c */
public class C2372c implements Serializable {
    /* renamed from: a */
    private int f7979a;
    /* renamed from: b */
    private int f7980b;
    /* renamed from: c */
    private String f7981c;
    /* renamed from: d */
    private String f7982d;
    /* renamed from: e */
    private String f7983e;
    /* renamed from: f */
    private String f7984f;

    /* renamed from: net.hockeyapp.android.c.c$1 */
    class C23711 implements FilenameFilter {
        /* renamed from: a */
        final /* synthetic */ C2372c f7978a;

        C23711(C2372c c2372c) {
            this.f7978a = c2372c;
        }

        public boolean accept(File file, String str) {
            return str.equals(this.f7978a.m11743c());
        }
    }

    /* renamed from: a */
    public String m11737a() {
        return this.f7981c;
    }

    /* renamed from: a */
    public void m11738a(int i) {
        this.f7979a = i;
    }

    /* renamed from: a */
    public void m11739a(String str) {
        this.f7981c = str;
    }

    /* renamed from: b */
    public String m11740b() {
        return this.f7982d;
    }

    /* renamed from: b */
    public void m11741b(int i) {
        this.f7980b = i;
    }

    /* renamed from: b */
    public void m11742b(String str) {
        this.f7982d = str;
    }

    /* renamed from: c */
    public String m11743c() {
        return TtmlNode.ANONYMOUS_REGION_ID + this.f7980b + this.f7979a;
    }

    /* renamed from: c */
    public void m11744c(String str) {
        this.f7983e = str;
    }

    /* renamed from: d */
    public void m11745d(String str) {
        this.f7984f = str;
    }

    /* renamed from: d */
    public boolean m11746d() {
        File a = C2367a.m11717a();
        if (!a.exists() || !a.isDirectory()) {
            return false;
        }
        File[] listFiles = a.listFiles(new C23711(this));
        return listFiles != null && listFiles.length == 1;
    }

    public String toString() {
        return "\n" + C2372c.class.getSimpleName() + "\n" + "id         " + this.f7979a + "\n" + "message id " + this.f7980b + "\n" + "filename   " + this.f7981c + "\n" + "url        " + this.f7982d + "\n" + "createdAt  " + this.f7983e + "\n" + "updatedAt  " + this.f7984f;
    }
}
