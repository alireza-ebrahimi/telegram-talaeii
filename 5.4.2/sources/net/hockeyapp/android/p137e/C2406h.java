package net.hockeyapp.android.p137e;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/* renamed from: net.hockeyapp.android.e.h */
public class C2406h {
    /* renamed from: a */
    private static final char[] f8094a = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    /* renamed from: b */
    private boolean f8095b = false;
    /* renamed from: c */
    private boolean f8096c = false;
    /* renamed from: d */
    private ByteArrayOutputStream f8097d = new ByteArrayOutputStream();
    /* renamed from: e */
    private String f8098e;

    public C2406h() {
        int i = 0;
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        while (i < 30) {
            stringBuffer.append(f8094a[random.nextInt(f8094a.length)]);
            i++;
        }
        this.f8098e = stringBuffer.toString();
    }

    /* renamed from: a */
    public String m11869a() {
        return this.f8098e;
    }

    /* renamed from: a */
    public void m11870a(String str, String str2) {
        m11873b();
        this.f8097d.write(("Content-Disposition: form-data; name=\"" + str + "\"\r\n").getBytes());
        this.f8097d.write("Content-Type: text/plain; charset=UTF-8\r\n".getBytes());
        this.f8097d.write("Content-Transfer-Encoding: 8bit\r\n\r\n".getBytes());
        this.f8097d.write(str2.getBytes());
        this.f8097d.write(("\r\n--" + this.f8098e + "\r\n").getBytes());
    }

    /* renamed from: a */
    public void m11871a(String str, String str2, InputStream inputStream, String str3, boolean z) {
        m11873b();
        try {
            String str4 = "Content-Type: " + str3 + "\r\n";
            this.f8097d.write(("Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + str2 + "\"\r\n").getBytes());
            this.f8097d.write(str4.getBytes());
            this.f8097d.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                this.f8097d.write(bArr, 0, read);
            }
            this.f8097d.flush();
            if (z) {
                m11874c();
            } else {
                this.f8097d.write(("\r\n--" + this.f8098e + "\r\n").getBytes());
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    public void m11872a(String str, String str2, InputStream inputStream, boolean z) {
        m11871a(str, str2, inputStream, "application/octet-stream", z);
    }

    /* renamed from: b */
    public void m11873b() {
        if (!this.f8096c) {
            this.f8097d.write(("--" + this.f8098e + "\r\n").getBytes());
        }
        this.f8096c = true;
    }

    /* renamed from: c */
    public void m11874c() {
        if (!this.f8095b) {
            try {
                this.f8097d.write(("\r\n--" + this.f8098e + "--\r\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.f8095b = true;
        }
    }

    /* renamed from: d */
    public long m11875d() {
        m11874c();
        return (long) this.f8097d.toByteArray().length;
    }

    /* renamed from: e */
    public String m11876e() {
        return "multipart/form-data; boundary=" + m11869a();
    }

    /* renamed from: f */
    public ByteArrayOutputStream m11877f() {
        m11874c();
        return this.f8097d;
    }
}
