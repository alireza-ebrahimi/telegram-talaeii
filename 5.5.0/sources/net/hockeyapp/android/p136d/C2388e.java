package net.hockeyapp.android.p136d;

import android.content.Context;
import java.io.IOException;
import java.net.URL;
import net.hockeyapp.android.p133b.C2359a;

/* renamed from: net.hockeyapp.android.d.e */
public class C2388e extends C2387d {
    /* renamed from: g */
    private long f8037g;

    public C2388e(Context context, String str, C2359a c2359a) {
        super(context, str, c2359a);
    }

    /* renamed from: a */
    protected Long mo3384a(Void... voidArr) {
        try {
            return Long.valueOf((long) m11805a(new URL(m11811b()), 6).getContentLength());
        } catch (IOException e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }

    /* renamed from: a */
    protected void mo3385a(Long l) {
        this.f8037g = l.longValue();
        if (this.f8037g > 0) {
            this.b.mo3381a(this);
        } else {
            this.b.mo3382a(this, Boolean.valueOf(false));
        }
    }

    /* renamed from: a */
    protected void mo3386a(Integer... numArr) {
    }

    /* renamed from: c */
    public long m11815c() {
        return this.f8037g;
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return mo3384a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        mo3385a((Long) obj);
    }

    protected /* synthetic */ void onProgressUpdate(Object[] objArr) {
        mo3386a((Integer[]) objArr);
    }
}
