package net.hockeyapp.android.p137e;

import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: net.hockeyapp.android.e.e */
public class C2401e {
    /* renamed from: a */
    private final String f8083a;
    /* renamed from: b */
    private String f8084b;
    /* renamed from: c */
    private String f8085c;
    /* renamed from: d */
    private C2406h f8086d;
    /* renamed from: e */
    private int f8087e = 120000;
    /* renamed from: f */
    private final Map<String, String> f8088f;

    public C2401e(String str) {
        this.f8083a = str;
        this.f8088f = new HashMap();
        this.f8088f.put("User-Agent", "HockeySDK/Android 4.1.3");
    }

    /* renamed from: a */
    private static String m11850a(Map<String, String> map, String str) {
        Iterable arrayList = new ArrayList();
        for (String str2 : map.keySet()) {
            String str3 = (String) map.get(str2);
            String str22 = URLEncoder.encode(str22, str);
            arrayList.add(str22 + "=" + URLEncoder.encode(str3, str));
        }
        return TextUtils.join("&", arrayList);
    }

    /* renamed from: a */
    public HttpURLConnection m11851a() {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.f8083a).openConnection();
        httpURLConnection.setConnectTimeout(this.f8087e);
        httpURLConnection.setReadTimeout(this.f8087e);
        if (VERSION.SDK_INT <= 9) {
            httpURLConnection.setRequestProperty("Connection", "close");
        }
        if (!TextUtils.isEmpty(this.f8084b)) {
            httpURLConnection.setRequestMethod(this.f8084b);
            if (!TextUtils.isEmpty(this.f8085c) || this.f8084b.equalsIgnoreCase("POST") || this.f8084b.equalsIgnoreCase("PUT")) {
                httpURLConnection.setDoOutput(true);
            }
        }
        for (String str : this.f8088f.keySet()) {
            httpURLConnection.setRequestProperty(str, (String) this.f8088f.get(str));
        }
        if (!TextUtils.isEmpty(this.f8085c)) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), C3446C.UTF8_NAME));
            bufferedWriter.write(this.f8085c);
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        if (this.f8086d != null) {
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(this.f8086d.m11875d()));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            bufferedOutputStream.write(this.f8086d.m11877f().toByteArray());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }
        return httpURLConnection;
    }

    /* renamed from: a */
    public C2401e m11852a(String str) {
        this.f8084b = str;
        return this;
    }

    /* renamed from: a */
    public C2401e m11853a(String str, String str2) {
        this.f8088f.put(str, str2);
        return this;
    }

    /* renamed from: a */
    public C2401e m11854a(Map<String, String> map) {
        String a;
        for (String a2 : map.keySet()) {
            String str = (String) map.get(a2);
            if (((long) str.length()) > 4194304) {
                throw new IllegalArgumentException("Form field " + a2 + " size too large: " + str.length() + " - max allowed: " + 4194304);
            }
        }
        try {
            a2 = C2401e.m11850a((Map) map, C3446C.UTF8_NAME);
            m11853a("Content-Type", "application/x-www-form-urlencoded");
            m11856b(a2);
            return this;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /* renamed from: a */
    public C2401e m11855a(Map<String, String> map, Context context, List<Uri> list) {
        try {
            this.f8086d = new C2406h();
            this.f8086d.m11873b();
            for (String str : map.keySet()) {
                this.f8086d.m11870a(str, (String) map.get(str));
            }
            int i = 0;
            while (i < list.size()) {
                Uri uri = (Uri) list.get(i);
                boolean z = i == list.size() + -1;
                this.f8086d.m11872a("attachment" + i, uri.getLastPathSegment(), context.getContentResolver().openInputStream(uri), z);
                i++;
            }
            this.f8086d.m11874c();
            m11853a("Content-Type", "multipart/form-data; boundary=" + this.f8086d.m11869a());
            return this;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /* renamed from: b */
    public C2401e m11856b(String str) {
        this.f8085c = str;
        return this;
    }

    /* renamed from: b */
    public C2401e m11857b(String str, String str2) {
        m11853a("Authorization", "Basic " + C2396b.m11835a((str + ":" + str2).getBytes(), 2));
        return this;
    }
}
