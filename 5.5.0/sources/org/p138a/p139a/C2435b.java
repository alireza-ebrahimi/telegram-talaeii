package org.p138a.p139a;

/* renamed from: org.a.a.b */
public class C2435b extends RuntimeException {
    /* renamed from: a */
    Throwable f8144a;

    public C2435b(String str, Throwable th) {
        if (th != null) {
            str = new StringBuffer().append("Exception while initializing ").append(str).append(": ").append(th).toString();
        }
        super(str);
        this.f8144a = th;
    }

    public Throwable getCause() {
        return this.f8144a;
    }
}
