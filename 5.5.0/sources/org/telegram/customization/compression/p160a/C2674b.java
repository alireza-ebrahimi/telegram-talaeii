package org.telegram.customization.compression.p160a;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.telegram.customization.compression.a.b */
public enum C2674b {
    ;
    
    /* renamed from: a */
    private static boolean f8918a;

    /* renamed from: org.telegram.customization.compression.a.b$a */
    private enum C2673a {
        /* renamed from: a */
        public static final C2673a f8911a = null;
        /* renamed from: b */
        public static final C2673a f8912b = null;
        /* renamed from: c */
        public static final C2673a f8913c = null;
        /* renamed from: d */
        public static final C2673a f8914d = null;
        /* renamed from: g */
        private static final /* synthetic */ C2673a[] f8915g = null;
        /* renamed from: e */
        public final String f8916e;
        /* renamed from: f */
        public final String f8917f;

        static {
            f8911a = new C2673a("WINDOWS", 0, "win32", "so");
            f8912b = new C2673a("LINUX", 1, "linux", "so");
            f8913c = new C2673a("MAC", 2, "darwin", "dylib");
            f8914d = new C2673a("SOLARIS", 3, "solaris", "so");
            f8915g = new C2673a[]{f8911a, f8912b, f8913c, f8914d};
        }

        private C2673a(String str, int i, String str2, String str3) {
            this.f8916e = str2;
            this.f8917f = str3;
        }

        public static C2673a valueOf(String str) {
            return (C2673a) Enum.valueOf(C2673a.class, str);
        }

        public static C2673a[] values() {
            return (C2673a[]) f8915g.clone();
        }
    }

    static {
        f8918a = false;
    }

    /* renamed from: a */
    public static synchronized boolean m12572a() {
        boolean z;
        synchronized (C2674b.class) {
            z = f8918a;
        }
        return z;
    }

    /* renamed from: b */
    public static synchronized void m12573b() {
        synchronized (C2674b.class) {
            if (!f8918a) {
                String e = C2674b.m12576e();
                InputStream inputStream = null;
                if (null == null) {
                    throw new UnsupportedOperationException("Unsupported OS/arch, cannot find " + e + ". Please try building from source.");
                }
                File createTempFile;
                FileOutputStream fileOutputStream;
                try {
                    createTempFile = File.createTempFile("liblz4-java", "." + C2674b.m12575d().f8917f);
                    fileOutputStream = new FileOutputStream(createTempFile);
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            try {
                                break;
                            } catch (IOException e2) {
                            }
                        } else {
                            fileOutputStream.write(bArr, 0, read);
                        }
                    }
                    fileOutputStream.close();
                    fileOutputStream = null;
                    System.load(createTempFile.getAbsolutePath());
                    f8918a = true;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                        }
                    }
                    if (createTempFile != null) {
                        if (createTempFile.exists()) {
                            if (f8918a) {
                                createTempFile.deleteOnExit();
                            } else {
                                createTempFile.delete();
                            }
                        }
                    }
                } catch (IOException e4) {
                    throw new ExceptionInInitializerError("Cannot unpack liblz4-java");
                } catch (Throwable th) {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e5) {
                        }
                    }
                    if (createTempFile != null) {
                        if (createTempFile.exists()) {
                            if (f8918a) {
                                createTempFile.deleteOnExit();
                            } else {
                                createTempFile.delete();
                            }
                        }
                    }
                }
            }
        }
    }

    /* renamed from: c */
    private static String m12574c() {
        return System.getProperty("os.arch");
    }

    /* renamed from: d */
    private static C2673a m12575d() {
        String property = System.getProperty("os.name");
        if (property.contains("Linux")) {
            return C2673a.f8912b;
        }
        if (property.contains("Mac")) {
            return C2673a.f8913c;
        }
        if (property.contains("Windows")) {
            return C2673a.f8911a;
        }
        if (property.contains("Solaris") || property.contains("SunOS")) {
            return C2673a.f8914d;
        }
        throw new UnsupportedOperationException("Unsupported operating system: " + property);
    }

    /* renamed from: e */
    private static String m12576e() {
        C2673a d = C2674b.m12575d();
        return "/" + d.f8916e + "/" + C2674b.m12574c() + "/liblz4-java." + d.f8917f;
    }
}
