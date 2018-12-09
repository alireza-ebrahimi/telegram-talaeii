package com.persianswitch.p122a;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

/* renamed from: com.persianswitch.a.ab */
public final class ab {
    /* renamed from: a */
    final C2189a f6650a;
    /* renamed from: b */
    final Proxy f6651b;
    /* renamed from: c */
    final InetSocketAddress f6652c;

    public ab(C2189a c2189a, Proxy proxy, InetSocketAddress inetSocketAddress) {
        if (c2189a == null) {
            throw new NullPointerException("address == null");
        } else if (proxy == null) {
            throw new NullPointerException("proxy == null");
        } else if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        } else {
            this.f6650a = c2189a;
            this.f6651b = proxy;
            this.f6652c = inetSocketAddress;
        }
    }

    /* renamed from: a */
    public C2189a m9925a() {
        return this.f6650a;
    }

    /* renamed from: b */
    public Proxy m9926b() {
        return this.f6651b;
    }

    /* renamed from: c */
    public InetSocketAddress m9927c() {
        return this.f6652c;
    }

    /* renamed from: d */
    public boolean m9928d() {
        return this.f6650a.f6647i != null && this.f6651b.type() == Type.HTTP;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ab)) {
            return false;
        }
        ab abVar = (ab) obj;
        return this.f6650a.equals(abVar.f6650a) && this.f6651b.equals(abVar.f6651b) && this.f6652c.equals(abVar.f6652c);
    }

    public int hashCode() {
        return ((((this.f6650a.hashCode() + 527) * 31) + this.f6651b.hashCode()) * 31) + this.f6652c.hashCode();
    }
}
