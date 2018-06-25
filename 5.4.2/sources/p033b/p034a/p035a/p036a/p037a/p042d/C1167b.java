package p033b.p034a.p035a.p036a.p037a.p042d;

import android.content.Context;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;
import p033b.p034a.p035a.p036a.p037a.p039b.C1113k;

/* renamed from: b.a.a.a.a.d.b */
public abstract class C1167b<T> {
    /* renamed from: a */
    protected final Context f3389a;
    /* renamed from: b */
    protected final C1164a<T> f3390b;
    /* renamed from: c */
    protected final C1113k f3391c;
    /* renamed from: d */
    protected final C1168c f3392d;
    /* renamed from: e */
    protected volatile long f3393e;
    /* renamed from: f */
    protected final List<C1169d> f3394f = new CopyOnWriteArrayList();
    /* renamed from: g */
    private final int f3395g;

    /* renamed from: b.a.a.a.a.d.b$1 */
    class C11651 implements Comparator<C1166a> {
        /* renamed from: a */
        final /* synthetic */ C1167b f3386a;

        C11651(C1167b c1167b) {
            this.f3386a = c1167b;
        }

        /* renamed from: a */
        public int m6183a(C1166a c1166a, C1166a c1166a2) {
            return (int) (c1166a.f3388b - c1166a2.f3388b);
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m6183a((C1166a) obj, (C1166a) obj2);
        }
    }

    /* renamed from: b.a.a.a.a.d.b$a */
    static class C1166a {
        /* renamed from: a */
        final File f3387a;
        /* renamed from: b */
        final long f3388b;

        public C1166a(File file, long j) {
            this.f3387a = file;
            this.f3388b = j;
        }
    }

    public C1167b(Context context, C1164a<T> c1164a, C1113k c1113k, C1168c c1168c, int i) {
        this.f3389a = context.getApplicationContext();
        this.f3390b = c1164a;
        this.f3392d = c1168c;
        this.f3391c = c1113k;
        this.f3393e = this.f3391c.mo1024a();
        this.f3395g = i;
    }

    /* renamed from: a */
    private void m6184a(int i) {
        if (!this.f3392d.mo1039a(i, mo1144c())) {
            C1110i.m6007a(this.f3389a, 4, "Fabric", String.format(Locale.US, "session analytics events file is %d bytes, new event is %d bytes, this is over flush limit of %d, rolling it over", new Object[]{Integer.valueOf(this.f3392d.mo1034a()), Integer.valueOf(i), Integer.valueOf(mo1144c())}));
            m6193d();
        }
    }

    /* renamed from: b */
    private void m6185b(String str) {
        for (C1169d a : this.f3394f) {
            try {
                a.mo1128a(str);
            } catch (Throwable e) {
                C1110i.m6009a(this.f3389a, "One of the roll over listeners threw an exception", e);
            }
        }
    }

    /* renamed from: a */
    public long m6186a(String str) {
        long j = 0;
        String[] split = str.split("_");
        if (split.length == 3) {
            try {
                j = Long.valueOf(split[2]).longValue();
            } catch (NumberFormatException e) {
            }
        }
        return j;
    }

    /* renamed from: a */
    protected abstract String mo1142a();

    /* renamed from: a */
    public void m6188a(C1169d c1169d) {
        if (c1169d != null) {
            this.f3394f.add(c1169d);
        }
    }

    /* renamed from: a */
    public void m6189a(T t) {
        byte[] a = this.f3390b.mo1127a(t);
        m6184a(a.length);
        this.f3392d.mo1038a(a);
    }

    /* renamed from: a */
    public void m6190a(List<File> list) {
        this.f3392d.mo1037a((List) list);
    }

    /* renamed from: b */
    protected int mo1143b() {
        return this.f3395g;
    }

    /* renamed from: c */
    protected int mo1144c() {
        return 8000;
    }

    /* renamed from: d */
    public boolean m6193d() {
        boolean z = true;
        String str = null;
        if (this.f3392d.mo1040b()) {
            z = false;
        } else {
            str = mo1142a();
            this.f3392d.mo1036a(str);
            C1110i.m6007a(this.f3389a, 4, "Fabric", String.format(Locale.US, "generated new file %s", new Object[]{str}));
            this.f3393e = this.f3391c.mo1024a();
        }
        m6185b(str);
        return z;
    }

    /* renamed from: e */
    public List<File> m6194e() {
        return this.f3392d.mo1035a(1);
    }

    /* renamed from: f */
    public void m6195f() {
        this.f3392d.mo1037a(this.f3392d.mo1041c());
        this.f3392d.mo1042d();
    }

    /* renamed from: g */
    public void m6196g() {
        List<File> c = this.f3392d.mo1041c();
        int b = mo1143b();
        if (c.size() > b) {
            int size = c.size() - b;
            C1110i.m6008a(this.f3389a, String.format(Locale.US, "Found %d files in  roll over directory, this is greater than %d, deleting %d oldest files", new Object[]{Integer.valueOf(c.size()), Integer.valueOf(b), Integer.valueOf(size)}));
            TreeSet treeSet = new TreeSet(new C11651(this));
            for (File file : c) {
                treeSet.add(new C1166a(file, m6186a(file.getName())));
            }
            List arrayList = new ArrayList();
            Iterator it = treeSet.iterator();
            while (it.hasNext()) {
                arrayList.add(((C1166a) it.next()).f3387a);
                if (arrayList.size() == size) {
                    break;
                }
            }
            this.f3392d.mo1037a(arrayList);
        }
    }
}
