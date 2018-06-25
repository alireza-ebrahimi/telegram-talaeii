package com.persianswitch.okhttp3;

import java.io.IOException;

public interface Authenticator {
    public static final Authenticator NONE = new C07101();

    /* renamed from: com.persianswitch.okhttp3.Authenticator$1 */
    static class C07101 implements Authenticator {
        C07101() {
        }

        public Request authenticate(Route route, Response response) {
            return null;
        }
    }

    Request authenticate(Route route, Response response) throws IOException;
}
