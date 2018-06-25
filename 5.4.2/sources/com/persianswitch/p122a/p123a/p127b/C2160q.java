package com.persianswitch.p122a.p123a.p127b;

import com.persianswitch.p122a.C2189a;
import com.persianswitch.p122a.C2221r;
import com.persianswitch.p122a.ab;
import com.persianswitch.p122a.p123a.C2185k;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/* renamed from: com.persianswitch.a.a.b.q */
public final class C2160q {
    /* renamed from: a */
    private final C2189a f6539a;
    /* renamed from: b */
    private final C2185k f6540b;
    /* renamed from: c */
    private Proxy f6541c;
    /* renamed from: d */
    private InetSocketAddress f6542d;
    /* renamed from: e */
    private List<Proxy> f6543e = Collections.emptyList();
    /* renamed from: f */
    private int f6544f;
    /* renamed from: g */
    private List<InetSocketAddress> f6545g = Collections.emptyList();
    /* renamed from: h */
    private int f6546h;
    /* renamed from: i */
    private final List<ab> f6547i = new ArrayList();

    public C2160q(C2189a c2189a, C2185k c2185k) {
        this.f6539a = c2189a;
        this.f6540b = c2185k;
        m9755a(c2189a.m9914a(), c2189a.m9921h());
    }

    /* renamed from: a */
    static String m9754a(InetSocketAddress inetSocketAddress) {
        InetAddress address = inetSocketAddress.getAddress();
        return address == null ? inetSocketAddress.getHostName() : address.getHostAddress();
    }

    /* renamed from: a */
    private void m9755a(C2221r c2221r, Proxy proxy) {
        if (proxy != null) {
            this.f6543e = Collections.singletonList(proxy);
        } else {
            this.f6543e = new ArrayList();
            Collection select = this.f6539a.m9920g().select(c2221r.m10068a());
            if (select != null) {
                this.f6543e.addAll(select);
            }
            this.f6543e.removeAll(Collections.singleton(Proxy.NO_PROXY));
            this.f6543e.add(Proxy.NO_PROXY);
        }
        this.f6544f = 0;
    }

    /* renamed from: a */
    private void m9756a(Proxy proxy) {
        int g;
        String str;
        this.f6545g = new ArrayList();
        String f;
        if (proxy.type() == Type.DIRECT || proxy.type() == Type.SOCKS) {
            f = this.f6539a.m9914a().m10075f();
            g = this.f6539a.m9914a().m10076g();
            str = f;
        } else {
            SocketAddress address = proxy.address();
            if (address instanceof InetSocketAddress) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
                f = C2160q.m9754a(inetSocketAddress);
                g = inetSocketAddress.getPort();
                str = f;
            } else {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + address.getClass());
            }
        }
        if (g < 1 || g > 65535) {
            throw new SocketException("No route to " + str + ":" + g + "; port is out of range");
        }
        if (proxy.type() == Type.SOCKS) {
            this.f6545g.add(InetSocketAddress.createUnresolved(str, g));
        } else {
            List a = this.f6539a.m9915b().mo3159a(str);
            int size = a.size();
            for (int i = 0; i < size; i++) {
                this.f6545g.add(new InetSocketAddress((InetAddress) a.get(i), g));
            }
        }
        this.f6546h = 0;
    }

    /* renamed from: c */
    private boolean m9757c() {
        return this.f6544f < this.f6543e.size();
    }

    /* renamed from: d */
    private Proxy m9758d() {
        if (m9757c()) {
            List list = this.f6543e;
            int i = this.f6544f;
            this.f6544f = i + 1;
            Proxy proxy = (Proxy) list.get(i);
            m9756a(proxy);
            return proxy;
        }
        throw new SocketException("No route to " + this.f6539a.m9914a().m10075f() + "; exhausted proxy configurations: " + this.f6543e);
    }

    /* renamed from: e */
    private boolean m9759e() {
        return this.f6546h < this.f6545g.size();
    }

    /* renamed from: f */
    private InetSocketAddress m9760f() {
        if (m9759e()) {
            List list = this.f6545g;
            int i = this.f6546h;
            this.f6546h = i + 1;
            return (InetSocketAddress) list.get(i);
        }
        throw new SocketException("No route to " + this.f6539a.m9914a().m10075f() + "; exhausted inet socket addresses: " + this.f6545g);
    }

    /* renamed from: g */
    private boolean m9761g() {
        return !this.f6547i.isEmpty();
    }

    /* renamed from: h */
    private ab m9762h() {
        return (ab) this.f6547i.remove(0);
    }

    /* renamed from: a */
    public void m9763a(ab abVar, IOException iOException) {
        if (!(abVar.m9926b().type() == Type.DIRECT || this.f6539a.m9920g() == null)) {
            this.f6539a.m9920g().connectFailed(this.f6539a.m9914a().m10068a(), abVar.m9926b().address(), iOException);
        }
        this.f6540b.m9883a(abVar);
    }

    /* renamed from: a */
    public boolean m9764a() {
        return m9759e() || m9757c() || m9761g();
    }

    /* renamed from: b */
    public ab m9765b() {
        if (!m9759e()) {
            if (m9757c()) {
                this.f6541c = m9758d();
            } else if (m9761g()) {
                return m9762h();
            } else {
                throw new NoSuchElementException();
            }
        }
        this.f6542d = m9760f();
        ab abVar = new ab(this.f6539a, this.f6541c, this.f6542d);
        if (!this.f6540b.m9885c(abVar)) {
            return abVar;
        }
        this.f6547i.add(abVar);
        return m9765b();
    }
}
