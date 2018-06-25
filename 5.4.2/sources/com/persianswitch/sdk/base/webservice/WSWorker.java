package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import android.os.AsyncTask;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.Injection.Network;
import com.persianswitch.sdk.base.Injection.ThreadPool;
import com.persianswitch.sdk.base.log.SDKLog;

final class WSWorker extends AsyncTask<Void, Void, HttpResult> {
    /* renamed from: a */
    private final Context f7230a;
    /* renamed from: b */
    private final Config f7231b;
    /* renamed from: c */
    private final String f7232c;
    /* renamed from: d */
    private final String f7233d;
    /* renamed from: e */
    private final boolean f7234e;
    /* renamed from: f */
    private final WSWorkerListener f7235f;
    /* renamed from: g */
    private final byte[] f7236g;
    /* renamed from: h */
    private final long f7237h;

    public static final class Builder {
        /* renamed from: a */
        private Context f7222a;
        /* renamed from: b */
        private Config f7223b;
        /* renamed from: c */
        private String f7224c;
        /* renamed from: d */
        private String f7225d;
        /* renamed from: e */
        private boolean f7226e;
        /* renamed from: f */
        private WSWorkerListener f7227f;
        /* renamed from: g */
        private byte[] f7228g;
        /* renamed from: h */
        private long f7229h;

        public Builder(Context context, Config config) {
            this.f7222a = context;
            this.f7223b = config;
        }

        /* renamed from: a */
        public Builder m10879a(long j) {
            this.f7229h = j;
            return this;
        }

        /* renamed from: a */
        public Builder m10880a(WSWorkerListener wSWorkerListener) {
            this.f7227f = wSWorkerListener;
            return this;
        }

        /* renamed from: a */
        public Builder m10881a(String str) {
            this.f7224c = str;
            return this;
        }

        /* renamed from: a */
        public Builder m10882a(boolean z) {
            this.f7226e = z;
            return this;
        }

        /* renamed from: a */
        public Builder m10883a(byte[] bArr) {
            this.f7228g = bArr;
            return this;
        }

        /* renamed from: a */
        public WSWorker m10884a() {
            return new WSWorker();
        }

        /* renamed from: b */
        public Builder m10885b(String str) {
            this.f7225d = str;
            return this;
        }
    }

    interface WSWorkerListener {
        /* renamed from: a */
        void mo3281a();

        /* renamed from: a */
        void mo3282a(HttpResult httpResult);
    }

    private WSWorker(Builder builder) {
        this.f7230a = builder.f7222a;
        this.f7231b = builder.f7223b;
        this.f7232c = builder.f7224c;
        this.f7233d = builder.f7225d;
        this.f7234e = builder.f7226e;
        this.f7235f = builder.f7227f;
        this.f7236g = builder.f7228g;
        this.f7237h = builder.f7229h;
    }

    /* renamed from: a */
    protected HttpResult m10888a(Void... voidArr) {
        try {
            Thread.sleep(this.f7237h);
        } catch (InterruptedException e) {
            SDKLog.m10661c("WSWorker", "error while wait for time : %d ", Long.valueOf(this.f7237h));
        }
        return Network.m10482a(this.f7230a, this.f7231b).m10824a(this.f7230a, this.f7232c, this.f7233d, this.f7234e, this.f7236g);
    }

    /* renamed from: a */
    public void m10889a() {
        executeOnExecutor(ThreadPool.m10483a(), new Void[0]);
    }

    /* renamed from: a */
    protected void m10890a(HttpResult httpResult) {
        if (this.f7235f != null) {
            this.f7235f.mo3282a(httpResult);
        }
    }

    /* renamed from: b */
    public void m10891b() {
        m10890a(m10888a(new Void[0]));
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m10888a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m10890a((HttpResult) obj);
    }

    protected void onPreExecute() {
        if (this.f7235f != null) {
            this.f7235f.mo3281a();
        }
    }
}
