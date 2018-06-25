package com.persianswitch.sdk.base.security;

import android.os.Build;
import android.os.Process;
import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Provider;
import java.security.SecureRandomSpi;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.SecretKey;
import org.telegram.messenger.exoplayer2.C3446C;

public class AesCbcWithIntegrity {
    /* renamed from: a */
    static final AtomicBoolean f7095a = new AtomicBoolean(false);

    public static class CipherTextIvMac {
        /* renamed from: a */
        private final byte[] f7084a;
        /* renamed from: b */
        private final byte[] f7085b;
        /* renamed from: c */
        private final byte[] f7086c;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            CipherTextIvMac cipherTextIvMac = (CipherTextIvMac) obj;
            return (Arrays.equals(this.f7084a, cipherTextIvMac.f7084a) && Arrays.equals(this.f7085b, cipherTextIvMac.f7085b)) ? Arrays.equals(this.f7086c, cipherTextIvMac.f7086c) : false;
        }

        public int hashCode() {
            return ((((Arrays.hashCode(this.f7084a) + 31) * 31) + Arrays.hashCode(this.f7085b)) * 31) + Arrays.hashCode(this.f7086c);
        }

        public String toString() {
            String encodeToString = Base64.encodeToString(this.f7085b, 2);
            return String.format(encodeToString + ":" + Base64.encodeToString(this.f7086c, 2) + ":" + Base64.encodeToString(this.f7084a, 2), new Object[0]);
        }
    }

    public static final class PrngFixes {
        /* renamed from: a */
        private static final byte[] f7092a = m10715d();

        public static class LinuxPRNGSecureRandom extends SecureRandomSpi {
            /* renamed from: a */
            private static final File f7087a = new File("/dev/urandom");
            /* renamed from: b */
            private static final Object f7088b = new Object();
            /* renamed from: c */
            private static DataInputStream f7089c;
            /* renamed from: d */
            private static OutputStream f7090d;
            /* renamed from: e */
            private boolean f7091e;

            /* renamed from: a */
            private DataInputStream m10710a() {
                DataInputStream dataInputStream;
                synchronized (f7088b) {
                    if (f7089c == null) {
                        try {
                            f7089c = new DataInputStream(new FileInputStream(f7087a));
                        } catch (Throwable e) {
                            throw new SecurityException("Failed to open " + f7087a + " for reading", e);
                        }
                    }
                    dataInputStream = f7089c;
                }
                return dataInputStream;
            }

            /* renamed from: b */
            private OutputStream m10711b() {
                OutputStream outputStream;
                synchronized (f7088b) {
                    if (f7090d == null) {
                        f7090d = new FileOutputStream(f7087a);
                    }
                    outputStream = f7090d;
                }
                return outputStream;
            }

            protected byte[] engineGenerateSeed(int i) {
                byte[] bArr = new byte[i];
                engineNextBytes(bArr);
                return bArr;
            }

            protected void engineNextBytes(byte[] bArr) {
                if (!this.f7091e) {
                    engineSetSeed(PrngFixes.m10713b());
                }
                try {
                    DataInputStream a;
                    synchronized (f7088b) {
                        a = m10710a();
                    }
                    synchronized (a) {
                        a.readFully(bArr);
                    }
                } catch (Throwable e) {
                    throw new SecurityException("Failed to read from " + f7087a, e);
                }
            }

            protected void engineSetSeed(byte[] bArr) {
                try {
                    OutputStream b;
                    synchronized (f7088b) {
                        b = m10711b();
                    }
                    b.write(bArr);
                    b.flush();
                    this.f7091e = true;
                } catch (IOException e) {
                    try {
                        Log.w(PrngFixes.class.getSimpleName(), "Failed to mix seed into " + f7087a);
                    } finally {
                        this.f7091e = true;
                    }
                }
            }
        }

        private static class LinuxPRNGSecureRandomProvider extends Provider {
            public LinuxPRNGSecureRandomProvider() {
                super("LinuxPRNG", 1.0d, "A Linux-specific random number provider that uses /dev/urandom");
                put("SecureRandom.SHA1PRNG", LinuxPRNGSecureRandom.class.getName());
                put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
            }
        }

        private PrngFixes() {
        }

        /* renamed from: b */
        private static byte[] m10713b() {
            try {
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeLong(System.currentTimeMillis());
                dataOutputStream.writeLong(System.nanoTime());
                dataOutputStream.writeInt(Process.myPid());
                dataOutputStream.writeInt(Process.myUid());
                dataOutputStream.write(f7092a);
                dataOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (Throwable e) {
                throw new SecurityException("Failed to generate seed", e);
            }
        }

        /* renamed from: c */
        private static String m10714c() {
            try {
                return (String) Build.class.getField("SERIAL").get(null);
            } catch (Exception e) {
                return null;
            }
        }

        /* renamed from: d */
        private static byte[] m10715d() {
            StringBuilder stringBuilder = new StringBuilder();
            String str = Build.FINGERPRINT;
            if (str != null) {
                stringBuilder.append(str);
            }
            str = m10714c();
            if (str != null) {
                stringBuilder.append(str);
            }
            try {
                return stringBuilder.toString().getBytes(C3446C.UTF8_NAME);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UTF-8 encoding not supported");
            }
        }
    }

    public static class SecretKeys {
        /* renamed from: a */
        private SecretKey f7093a;
        /* renamed from: b */
        private SecretKey f7094b;

        /* renamed from: a */
        public SecretKey m10716a() {
            return this.f7093a;
        }

        /* renamed from: b */
        public SecretKey m10717b() {
            return this.f7094b;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SecretKeys secretKeys = (SecretKeys) obj;
            return this.f7094b.equals(secretKeys.f7094b) ? this.f7093a.equals(secretKeys.f7093a) : false;
        }

        public int hashCode() {
            return ((this.f7093a.hashCode() + 31) * 31) + this.f7094b.hashCode();
        }

        public String toString() {
            return Base64.encodeToString(m10716a().getEncoded(), 2) + ":" + Base64.encodeToString(m10717b().getEncoded(), 2);
        }
    }
}
