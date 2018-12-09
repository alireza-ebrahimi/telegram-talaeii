package com.p111h.p112a;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import com.p111h.p112a.p113a.C1971c;
import com.p111h.p112a.p114b.C1977a;
import com.p111h.p112a.p114b.C1978b;
import com.p111h.p112a.p115c.C1985c;
import com.p111h.p112a.p115c.C1987d;
import com.p111h.p112a.p117e.C1994c;
import java.util.concurrent.Executor;

/* renamed from: com.h.a.a */
public class C1976a {
    /* renamed from: a */
    C1965b f5816a = new C19661(this);
    /* renamed from: b */
    private String f5817b;

    /* renamed from: com.h.a.a$b */
    interface C1965b {
        /* renamed from: a */
        void mo3058a(C1977a c1977a, String str, Executor executor, C1979b c1979b);
    }

    /* renamed from: com.h.a.a$1 */
    class C19661 implements C1965b {
        /* renamed from: a */
        final /* synthetic */ C1976a f5805a;

        C19661(C1976a c1976a) {
            this.f5805a = c1976a;
        }

        /* renamed from: a */
        public void mo3058a(final C1977a c1977a, final String str, Executor executor, final C1979b c1979b) {
            this.f5805a.m8942a(executor, new AsyncTask<Void, Void, C1967a>(this) {
                /* renamed from: d */
                final /* synthetic */ C19661 f5804d;

                /* renamed from: a */
                protected C1967a m8935a(Void... voidArr) {
                    try {
                        return new C1967a(C1987d.m8981a(C1994c.m9013a(c1977a), C1985c.m8976a(str).m8975a()), null);
                    } catch (Exception e) {
                        return new C1967a(null, e);
                    }
                }

                /* renamed from: a */
                protected void m8936a(C1967a c1967a) {
                    this.f5804d.f5805a.m8939a(c1967a, c1979b);
                }

                protected /* synthetic */ Object doInBackground(Object[] objArr) {
                    return m8935a((Void[]) objArr);
                }

                protected /* synthetic */ void onPostExecute(Object obj) {
                    m8936a((C1967a) obj);
                }
            });
        }
    }

    /* renamed from: com.h.a.a$a */
    private class C1967a {
        /* renamed from: a */
        final C1978b f5806a;
        /* renamed from: b */
        final Exception f5807b;
        /* renamed from: c */
        final /* synthetic */ C1976a f5808c;

        private C1967a(C1976a c1976a, C1978b c1978b, Exception exception) {
            this.f5808c = c1976a;
            this.f5807b = exception;
            this.f5806a = c1978b;
        }
    }

    public C1976a(String str) {
        m8947a(str);
    }

    /* renamed from: a */
    private void m8939a(C1967a c1967a, C1979b c1979b) {
        if (c1967a.f5806a != null) {
            c1979b.onSuccess(c1967a.f5806a);
        } else if (c1967a.f5807b != null) {
            c1979b.onError(c1967a.f5807b);
        } else {
            c1979b.onError(new RuntimeException("Somehow got neither a token response or an error response"));
        }
    }

    /* renamed from: a */
    private void m8942a(Executor executor, AsyncTask<Void, Void, C1967a> asyncTask) {
        if (executor == null || VERSION.SDK_INT <= 11) {
            asyncTask.execute(new Void[0]);
        } else {
            asyncTask.executeOnExecutor(executor, new Void[0]);
        }
    }

    /* renamed from: b */
    private void m8943b(String str) {
        if (str == null || str.length() == 0) {
            throw new C1971c("Invalid Publishable Key: You must use a valid publishable key to create a token.  For more info, see https://stripe.com/docs/stripe.js.", null, Integer.valueOf(0));
        } else if (str.startsWith("sk_")) {
            throw new C1971c("Invalid Publishable Key: You are using a secret key to create a token, instead of the publishable one. For more info, see https://stripe.com/docs/stripe.js", null, Integer.valueOf(0));
        }
    }

    /* renamed from: a */
    public void m8944a(C1977a c1977a, C1979b c1979b) {
        m8945a(c1977a, this.f5817b, c1979b);
    }

    /* renamed from: a */
    public void m8945a(C1977a c1977a, String str, C1979b c1979b) {
        m8946a(c1977a, str, null, c1979b);
    }

    /* renamed from: a */
    public void m8946a(C1977a c1977a, String str, Executor executor, C1979b c1979b) {
        if (c1977a == null) {
            try {
                throw new RuntimeException("Required Parameter: 'card' is required to create a token");
            } catch (Exception e) {
                c1979b.onError(e);
            }
        } else if (c1979b == null) {
            throw new RuntimeException("Required Parameter: 'callback' is required to use the created token and handle errors");
        } else {
            m8943b(str);
            this.f5816a.mo3058a(c1977a, str, executor, c1979b);
        }
    }

    /* renamed from: a */
    public void m8947a(String str) {
        m8943b(str);
        this.f5817b = str;
    }
}
