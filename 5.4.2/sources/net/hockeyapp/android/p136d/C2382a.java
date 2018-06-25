package net.hockeyapp.android.p136d;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Queue;
import net.hockeyapp.android.C2367a;
import net.hockeyapp.android.p135c.C2372c;
import net.hockeyapp.android.p137e.C2393a;
import net.hockeyapp.android.p137e.C2400d;
import net.hockeyapp.android.p137e.C2402f;
import net.hockeyapp.android.views.C2424a;

/* renamed from: net.hockeyapp.android.d.a */
public class C2382a {
    /* renamed from: a */
    private Queue<C2380b> f8020a;
    /* renamed from: b */
    private boolean f8021b;

    /* renamed from: net.hockeyapp.android.d.a$1 */
    class C23781 extends Handler {
        /* renamed from: a */
        final /* synthetic */ C2382a f8009a;

        C23781(C2382a c2382a) {
            this.f8009a = c2382a;
        }

        public void handleMessage(Message message) {
            final C2380b c2380b = (C2380b) this.f8009a.f8020a.poll();
            if (!c2380b.m11777c() && c2380b.m11779e()) {
                postDelayed(new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ C23781 f8008b;

                    public void run() {
                        this.f8008b.f8009a.f8020a.add(c2380b);
                        this.f8008b.f8009a.m11789b();
                    }
                }, 3000);
            }
            this.f8009a.f8021b = false;
            this.f8009a.m11789b();
        }
    }

    /* renamed from: net.hockeyapp.android.d.a$a */
    private static class C2379a {
        /* renamed from: a */
        public static final C2382a f8010a = new C2382a();
    }

    /* renamed from: net.hockeyapp.android.d.a$b */
    private static class C2380b {
        /* renamed from: a */
        private final C2372c f8011a;
        /* renamed from: b */
        private final C2424a f8012b;
        /* renamed from: c */
        private boolean f8013c;
        /* renamed from: d */
        private int f8014d;

        private C2380b(C2372c c2372c, C2424a c2424a) {
            this.f8011a = c2372c;
            this.f8012b = c2424a;
            this.f8013c = false;
            this.f8014d = 2;
        }

        /* renamed from: a */
        public C2372c m11774a() {
            return this.f8011a;
        }

        /* renamed from: a */
        public void m11775a(boolean z) {
            this.f8013c = z;
        }

        /* renamed from: b */
        public C2424a m11776b() {
            return this.f8012b;
        }

        /* renamed from: c */
        public boolean m11777c() {
            return this.f8013c;
        }

        /* renamed from: d */
        public boolean m11778d() {
            return this.f8014d > 0;
        }

        /* renamed from: e */
        public boolean m11779e() {
            int i = this.f8014d - 1;
            this.f8014d = i;
            return i >= 0;
        }
    }

    /* renamed from: net.hockeyapp.android.d.a$c */
    private static class C2381c extends AsyncTask<Void, Integer, Boolean> {
        /* renamed from: a */
        private final C2380b f8015a;
        /* renamed from: b */
        private final Handler f8016b;
        /* renamed from: c */
        private File f8017c = C2367a.m11717a();
        /* renamed from: d */
        private Bitmap f8018d = null;
        /* renamed from: e */
        private int f8019e = 0;

        public C2381c(C2380b c2380b, Handler handler) {
            this.f8015a = c2380b;
            this.f8016b = handler;
        }

        /* renamed from: a */
        private URLConnection m11780a(URL url) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "HockeySDK/Android 4.1.3");
            httpURLConnection.setInstanceFollowRedirects(true);
            if (VERSION.SDK_INT <= 9) {
                httpURLConnection.setRequestProperty("connection", "close");
            }
            return httpURLConnection;
        }

        /* renamed from: a */
        private void m11781a() {
            try {
                String c = this.f8015a.m11774a().m11743c();
                C2424a b = this.f8015a.m11776b();
                this.f8019e = C2402f.m11860a(new File(this.f8017c, c));
                this.f8018d = C2402f.m11863a(new File(this.f8017c, c), this.f8019e == 1 ? b.getWidthLandscape() : b.getWidthPortrait(), this.f8019e == 1 ? b.getMaxHeightLandscape() : b.getMaxHeightPortrait());
            } catch (IOException e) {
                e.printStackTrace();
                this.f8018d = null;
            }
        }

        /* renamed from: a */
        private boolean m11782a(String str, String str2) {
            try {
                URLConnection a = m11780a(new URL(str));
                a.connect();
                int contentLength = a.getContentLength();
                String headerField = a.getHeaderField("Status");
                if (headerField != null && !headerField.startsWith("200")) {
                    return false;
                }
                File file = new File(this.f8017c, str2);
                InputStream bufferedInputStream = new BufferedInputStream(a.getInputStream());
                OutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                long j = 0;
                while (true) {
                    int read = bufferedInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    j += (long) read;
                    publishProgress(new Integer[]{Integer.valueOf((int) ((100 * j) / ((long) contentLength)))});
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                bufferedInputStream.close();
                return j > 0;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        /* renamed from: a */
        protected Boolean m11783a(Void... voidArr) {
            C2372c a = this.f8015a.m11774a();
            if (a.m11746d()) {
                C2400d.m11846c("Cached...");
                m11781a();
                return Boolean.valueOf(true);
            }
            C2400d.m11846c("Downloading...");
            boolean a2 = m11782a(a.m11740b(), a.m11743c());
            if (a2) {
                m11781a();
            }
            return Boolean.valueOf(a2);
        }

        /* renamed from: a */
        protected void m11784a(Boolean bool) {
            C2424a b = this.f8015a.m11776b();
            this.f8015a.m11775a(bool.booleanValue());
            if (bool.booleanValue()) {
                b.m11929a(this.f8018d, this.f8019e);
            } else if (!this.f8015a.m11778d()) {
                b.m11930b();
            }
            this.f8016b.sendEmptyMessage(0);
        }

        /* renamed from: a */
        protected void m11785a(Integer... numArr) {
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m11783a((Void[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            m11784a((Boolean) obj);
        }

        protected void onPreExecute() {
        }

        protected /* synthetic */ void onProgressUpdate(Object[] objArr) {
            m11785a((Integer[]) objArr);
        }
    }

    private C2382a() {
        this.f8020a = new LinkedList();
        this.f8021b = false;
    }

    /* renamed from: a */
    public static C2382a m11787a() {
        return C2379a.f8010a;
    }

    /* renamed from: b */
    private void m11789b() {
        if (!this.f8021b) {
            C2380b c2380b = (C2380b) this.f8020a.peek();
            if (c2380b != null) {
                AsyncTask c2381c = new C2381c(c2380b, new C23781(this));
                this.f8021b = true;
                C2393a.m11833a(c2381c);
            }
        }
    }

    /* renamed from: a */
    public void m11791a(C2372c c2372c, C2424a c2424a) {
        this.f8020a.add(new C2380b(c2372c, c2424a));
        m11789b();
    }
}
