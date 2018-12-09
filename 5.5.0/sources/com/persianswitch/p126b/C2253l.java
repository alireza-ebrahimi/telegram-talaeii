package com.persianswitch.p126b;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: com.persianswitch.b.l */
public final class C2253l {
    /* renamed from: a */
    static final Logger f6960a = Logger.getLogger(C2253l.class.getName());

    private C2253l() {
    }

    /* renamed from: a */
    public static C2242d m10357a(C2094r c2094r) {
        return new C2254m(c2094r);
    }

    /* renamed from: a */
    public static C2243e m10358a(C2096s c2096s) {
        return new C2256n(c2096s);
    }

    /* renamed from: a */
    private static C2094r m10359a(final OutputStream outputStream, final C2098t c2098t) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        } else if (c2098t != null) {
            return new C2094r() {
                /* renamed from: a */
                public C2098t mo3101a() {
                    return c2098t;
                }

                public void a_(C2244c c2244c, long j) {
                    C2261u.m10423a(c2244c.f6936b, 0, j);
                    while (j > 0) {
                        c2098t.mo3205g();
                        C2257o c2257o = c2244c.f6935a;
                        int min = (int) Math.min(j, (long) (c2257o.f6970c - c2257o.f6969b));
                        outputStream.write(c2257o.f6968a, c2257o.f6969b, min);
                        c2257o.f6969b += min;
                        j -= (long) min;
                        c2244c.f6936b -= (long) min;
                        if (c2257o.f6969b == c2257o.f6970c) {
                            c2244c.f6935a = c2257o.m10398a();
                            C2258p.m10404a(c2257o);
                        }
                    }
                }

                public void close() {
                    outputStream.close();
                }

                public void flush() {
                    outputStream.flush();
                }

                public String toString() {
                    return "sink(" + outputStream + ")";
                }
            };
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    /* renamed from: a */
    public static C2094r m10360a(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        C2098t c = C2253l.m10364c(socket);
        return c.m9424a(C2253l.m10359a(socket.getOutputStream(), c));
    }

    /* renamed from: a */
    private static C2096s m10361a(final InputStream inputStream, final C2098t c2098t) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        } else if (c2098t != null) {
            return new C2096s() {
                /* renamed from: a */
                public long mo3105a(C2244c c2244c, long j) {
                    if (j < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + j);
                    } else if (j == 0) {
                        return 0;
                    } else {
                        try {
                            c2098t.mo3205g();
                            C2257o e = c2244c.m10288e(1);
                            int read = inputStream.read(e.f6968a, e.f6970c, (int) Math.min(j, (long) (8192 - e.f6970c)));
                            if (read == -1) {
                                return -1;
                            }
                            e.f6970c += read;
                            c2244c.f6936b += (long) read;
                            return (long) read;
                        } catch (AssertionError e2) {
                            if (C2253l.m10362a(e2)) {
                                throw new IOException(e2);
                            }
                            throw e2;
                        }
                    }
                }

                /* renamed from: a */
                public C2098t mo3106a() {
                    return c2098t;
                }

                public void close() {
                    inputStream.close();
                }

                public String toString() {
                    return "source(" + inputStream + ")";
                }
            };
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    /* renamed from: a */
    static boolean m10362a(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    /* renamed from: b */
    public static C2096s m10363b(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        C2098t c = C2253l.m10364c(socket);
        return c.m9425a(C2253l.m10361a(socket.getInputStream(), c));
    }

    /* renamed from: c */
    private static C2099a m10364c(final Socket socket) {
        return new C2099a() {
            /* renamed from: a */
            protected IOException mo3108a(IOException iOException) {
                IOException socketTimeoutException = new SocketTimeoutException("timeout");
                if (iOException != null) {
                    socketTimeoutException.initCause(iOException);
                }
                return socketTimeoutException;
            }

            /* renamed from: a */
            protected void mo3109a() {
                try {
                    socket.close();
                } catch (Throwable e) {
                    C2253l.f6960a.log(Level.WARNING, "Failed to close timed out socket " + socket, e);
                } catch (AssertionError e2) {
                    if (C2253l.m10362a(e2)) {
                        C2253l.f6960a.log(Level.WARNING, "Failed to close timed out socket " + socket, e2);
                        return;
                    }
                    throw e2;
                }
            }
        };
    }
}
