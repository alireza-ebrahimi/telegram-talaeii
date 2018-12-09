package com.persianswitch.p122a;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/* renamed from: com.persianswitch.a.o */
public interface C2212o {
    /* renamed from: a */
    public static final C2212o f6785a = new C22131();

    /* renamed from: com.persianswitch.a.o$1 */
    static class C22131 implements C2212o {
        C22131() {
        }

        /* renamed from: a */
        public List<InetAddress> mo3159a(String str) {
            if (str != null) {
                return Arrays.asList(InetAddress.getAllByName(str));
            }
            throw new UnknownHostException("hostname == null");
        }
    }

    /* renamed from: a */
    List<InetAddress> mo3159a(String str);
}
