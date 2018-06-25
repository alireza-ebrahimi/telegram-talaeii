package org.telegram.customization.fetch;

import android.os.Handler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.telegram.customization.fetch.p163a.C2736a;
import org.telegram.customization.fetch.p164b.C2738a;
import org.telegram.customization.fetch.p166d.C2751a;
import org.telegram.customization.fetch.p166d.C2752b;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: org.telegram.customization.fetch.d */
final class C2754d implements Runnable {
    /* renamed from: a */
    private final C2752b f9072a;
    /* renamed from: b */
    private final C2736a<String> f9073b;
    /* renamed from: c */
    private final C2742a f9074c;
    /* renamed from: d */
    private final Handler f9075d;
    /* renamed from: e */
    private volatile boolean f9076e;
    /* renamed from: f */
    private HttpURLConnection f9077f;
    /* renamed from: g */
    private InputStream f9078g;
    /* renamed from: h */
    private BufferedReader f9079h;
    /* renamed from: i */
    private String f9080i;

    /* renamed from: org.telegram.customization.fetch.d$a */
    interface C2742a {
        /* renamed from: a */
        void mo3477a(C2752b c2752b);
    }

    /* renamed from: org.telegram.customization.fetch.d$1 */
    class C27491 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2754d f9053a;

        C27491(C2754d c2754d) {
            this.f9053a = c2754d;
        }

        public void run() {
            this.f9053a.f9073b.m12700a(this.f9053a.f9080i, this.f9053a.f9072a);
        }
    }

    /* renamed from: a */
    private void m12761a() {
        this.f9077f = (HttpURLConnection) new URL(this.f9072a.m12746a()).openConnection();
        this.f9077f.setRequestMethod("GET");
        this.f9077f.setReadTimeout(DefaultLoadControl.DEFAULT_MIN_BUFFER_MS);
        this.f9077f.setConnectTimeout(10000);
        this.f9077f.setUseCaches(true);
        this.f9077f.setDefaultUseCaches(true);
        this.f9077f.setInstanceFollowRedirects(true);
        this.f9077f.setDoInput(true);
        for (C2751a c2751a : this.f9072a.m12749c()) {
            this.f9077f.addRequestProperty(c2751a.m12744a(), c2751a.m12745b());
        }
    }

    /* renamed from: b */
    private String m12762b() {
        StringBuilder stringBuilder = new StringBuilder();
        this.f9079h = new BufferedReader(new InputStreamReader(this.f9078g));
        while (true) {
            String readLine = this.f9079h.readLine();
            if (readLine != null && !m12766d()) {
                stringBuilder.append(readLine);
            }
        }
        return m12766d() ? null : stringBuilder.toString();
    }

    /* renamed from: c */
    private void m12765c() {
        try {
            if (this.f9078g != null) {
                this.f9078g.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (this.f9079h != null) {
                this.f9079h.close();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (this.f9077f != null) {
            this.f9077f.disconnect();
        }
    }

    /* renamed from: d */
    private boolean m12766d() {
        return this.f9076e;
    }

    public void run() {
        int responseCode;
        try {
            m12761a();
            this.f9077f.connect();
            responseCode = this.f9077f.getResponseCode();
            if (responseCode != Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                throw new IllegalStateException("SSRV:" + responseCode);
            } else if (m12766d()) {
                throw new C2738a("DIE", -118);
            } else {
                this.f9078g = this.f9077f.getInputStream();
                this.f9080i = m12762b();
                if (!m12766d()) {
                    this.f9075d.post(new C27491(this));
                }
                m12765c();
                this.f9074c.mo3477a(this.f9072a);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseCode = C2741b.m12726a(e.getMessage());
            if (!m12766d()) {
                this.f9075d.post(new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ C2754d f9055b;

                    public void run() {
                        this.f9055b.f9073b.m12699a(responseCode, this.f9055b.f9072a);
                    }
                });
            }
            m12765c();
            this.f9074c.mo3477a(this.f9072a);
        } catch (Throwable th) {
            m12765c();
            this.f9074c.mo3477a(this.f9072a);
        }
    }
}
