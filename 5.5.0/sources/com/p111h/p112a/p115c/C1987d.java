package com.p111h.p112a.p115c;

import com.google.android.gms.wallet.WalletConstants;
import com.p111h.p112a.p113a.C1970b;
import com.p111h.p112a.p113a.C1971c;
import com.p111h.p112a.p113a.C1972d;
import com.p111h.p112a.p113a.C1973e;
import com.p111h.p112a.p113a.C1974f;
import com.p111h.p112a.p113a.C1975g;
import com.p111h.p112a.p114b.C1978b;
import com.p111h.p112a.p115c.C1982b.C1981a;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.ui.ChatActivity;

/* renamed from: com.h.a.c.d */
public class C1987d {
    /* renamed from: a */
    private static final SSLSocketFactory f5861a = new C1989f();

    /* renamed from: com.h.a.c.d$a */
    private static final class C1986a {
        /* renamed from: a */
        public final String f5859a;
        /* renamed from: b */
        public final String f5860b;

        public C1986a(String str, String str2) {
            this.f5859a = str;
            this.f5860b = str2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private static com.p111h.p112a.p114b.C1978b m8980a(java.lang.String r7, java.lang.String r8, java.util.Map<java.lang.String, java.lang.Object> r9, com.p111h.p112a.p115c.C1985c r10) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0005 in list [B:33:0x0078]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r5 = 0;
        r3 = 0;
        if (r10 != 0) goto L_0x0006;
    L_0x0004:
        r0 = r3;
    L_0x0005:
        return r0;
    L_0x0006:
        r0 = 1;
        r0 = java.lang.Boolean.valueOf(r0);
        r1 = "networkaddress.cache.ttl";	 Catch:{ SecurityException -> 0x0038 }
        r1 = java.security.Security.getProperty(r1);	 Catch:{ SecurityException -> 0x0038 }
        r2 = "networkaddress.cache.ttl";	 Catch:{ SecurityException -> 0x00cc }
        r4 = "0";	 Catch:{ SecurityException -> 0x00cc }
        java.security.Security.setProperty(r2, r4);	 Catch:{ SecurityException -> 0x00cc }
        r2 = r1;
        r1 = r0;
    L_0x001d:
        r0 = r10.m8979c();
        r0 = r0.trim();
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0041;
    L_0x002b:
        r0 = new com.h.a.a.c;
        r1 = "No API key provided. (HINT: set your API key using 'Stripe.apiKey = <API-KEY>'. You can generate API keys from the Stripe web interface. See https://stripe.com/api for details or email support@stripe.com if you have questions.";
        r2 = java.lang.Integer.valueOf(r5);
        r0.<init>(r1, r3, r2);
        throw r0;
    L_0x0038:
        r0 = move-exception;
        r1 = r3;
    L_0x003a:
        r0 = java.lang.Boolean.valueOf(r5);
        r2 = r1;
        r1 = r0;
        goto L_0x001d;
    L_0x0041:
        r0 = com.p111h.p112a.p115c.C1987d.m8995b(r7, r8, r9, r10);	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        r4 = r0.m8999a();	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        r5 = r0.m9000b();	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        r0 = r0.m9001c();	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        if (r0 != 0) goto L_0x0084;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
    L_0x0053:
        r0 = r3;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
    L_0x0054:
        if (r0 == 0) goto L_0x00cf;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
    L_0x0056:
        r6 = r0.size();	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        if (r6 <= 0) goto L_0x00cf;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
    L_0x005c:
        r6 = 0;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        r0 = r0.get(r6);	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        r0 = (java.lang.String) r0;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
    L_0x0063:
        r6 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        if (r4 < r6) goto L_0x006b;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
    L_0x0067:
        r6 = 300; // 0x12c float:4.2E-43 double:1.48E-321;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        if (r4 < r6) goto L_0x006e;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
    L_0x006b:
        com.p111h.p112a.p115c.C1987d.m8994a(r5, r4, r0);	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
    L_0x006e:
        r0 = com.p111h.p112a.p115c.C1990g.m9003a(r5);	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        r1 = r1.booleanValue();
        if (r1 == 0) goto L_0x0005;
    L_0x0078:
        if (r2 != 0) goto L_0x008e;
    L_0x007a:
        r1 = "networkaddress.cache.ttl";
        r2 = "-1";
        java.security.Security.setProperty(r1, r2);
        goto L_0x0005;
    L_0x0084:
        r6 = "Request-Id";	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        r0 = r0.get(r6);	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        r0 = (java.util.List) r0;	 Catch:{ JSONException -> 0x0096, all -> 0x00b2 }
        goto L_0x0054;
    L_0x008e:
        r1 = "networkaddress.cache.ttl";
        java.security.Security.setProperty(r1, r2);
        goto L_0x0005;
    L_0x0096:
        r0 = move-exception;
        r0 = r1.booleanValue();
        if (r0 == 0) goto L_0x00a8;
    L_0x009d:
        if (r2 != 0) goto L_0x00ab;
    L_0x009f:
        r0 = "networkaddress.cache.ttl";
        r1 = "-1";
        java.security.Security.setProperty(r0, r1);
    L_0x00a8:
        r0 = r3;
        goto L_0x0005;
    L_0x00ab:
        r0 = "networkaddress.cache.ttl";
        java.security.Security.setProperty(r0, r2);
        goto L_0x00a8;
    L_0x00b2:
        r0 = move-exception;
        r1 = r1.booleanValue();
        if (r1 == 0) goto L_0x00c4;
    L_0x00b9:
        if (r2 != 0) goto L_0x00c5;
    L_0x00bb:
        r1 = "networkaddress.cache.ttl";
        r2 = "-1";
        java.security.Security.setProperty(r1, r2);
    L_0x00c4:
        throw r0;
    L_0x00c5:
        r1 = "networkaddress.cache.ttl";
        java.security.Security.setProperty(r1, r2);
        goto L_0x00c4;
    L_0x00cc:
        r0 = move-exception;
        goto L_0x003a;
    L_0x00cf:
        r0 = r3;
        goto L_0x0063;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.h.a.c.d.a(java.lang.String, java.lang.String, java.util.Map, com.h.a.c.c):com.h.a.b.b");
    }

    /* renamed from: a */
    public static C1978b m8981a(Map<String, Object> map, C1985c c1985c) {
        return C1987d.m8980a("POST", C1987d.m8983a(), (Map) map, c1985c);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private static com.p111h.p112a.p115c.C1988e m8982a(java.lang.String r7, java.lang.String r8, java.lang.String r9, com.p111h.p112a.p115c.C1985c r10) {
        /*
        r3 = 1;
        r0 = 0;
        r1 = 0;
        r2 = -1;
        r4 = r7.hashCode();	 Catch:{ IOException -> 0x0022 }
        switch(r4) {
            case 70454: goto L_0x0048;
            case 2461856: goto L_0x0052;
            default: goto L_0x000b;
        };	 Catch:{ IOException -> 0x0022 }
    L_0x000b:
        r0 = r2;
    L_0x000c:
        switch(r0) {
            case 0: goto L_0x005d;
            case 1: goto L_0x0084;
            default: goto L_0x000f;
        };	 Catch:{ IOException -> 0x0022 }
    L_0x000f:
        r0 = new com.h.a.a.a;	 Catch:{ IOException -> 0x0022 }
        r2 = "Unrecognized HTTP method %s. This indicates a bug in the Stripe bindings. Please contact support@stripe.com for assistance.";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ IOException -> 0x0022 }
        r4 = 0;
        r3[r4] = r7;	 Catch:{ IOException -> 0x0022 }
        r2 = java.lang.String.format(r2, r3);	 Catch:{ IOException -> 0x0022 }
        r0.<init>(r2);	 Catch:{ IOException -> 0x0022 }
        throw r0;	 Catch:{ IOException -> 0x0022 }
    L_0x0022:
        r0 = move-exception;
        r2 = new com.h.a.a.a;	 Catch:{ all -> 0x0041 }
        r3 = "IOException during API request to Stripe (%s): %s Please check your internet connection and try again. If this problem persists, you should check Stripe's service status at https://twitter.com/stripestatus, or let us know at support@stripe.com.";
        r4 = 2;
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x0041 }
        r5 = 0;
        r6 = com.p111h.p112a.p115c.C1987d.m8983a();	 Catch:{ all -> 0x0041 }
        r4[r5] = r6;	 Catch:{ all -> 0x0041 }
        r5 = 1;
        r6 = r0.getMessage();	 Catch:{ all -> 0x0041 }
        r4[r5] = r6;	 Catch:{ all -> 0x0041 }
        r3 = java.lang.String.format(r3, r4);	 Catch:{ all -> 0x0041 }
        r2.<init>(r3, r0);	 Catch:{ all -> 0x0041 }
        throw r2;	 Catch:{ all -> 0x0041 }
    L_0x0041:
        r0 = move-exception;
        if (r1 == 0) goto L_0x0047;
    L_0x0044:
        r1.disconnect();
    L_0x0047:
        throw r0;
    L_0x0048:
        r3 = "GET";
        r3 = r7.equals(r3);	 Catch:{ IOException -> 0x0022 }
        if (r3 == 0) goto L_0x000b;
    L_0x0051:
        goto L_0x000c;
    L_0x0052:
        r0 = "POST";
        r0 = r7.equals(r0);	 Catch:{ IOException -> 0x0022 }
        if (r0 == 0) goto L_0x000b;
    L_0x005b:
        r0 = r3;
        goto L_0x000c;
    L_0x005d:
        r1 = com.p111h.p112a.p115c.C1987d.m8989a(r8, r9, r10);	 Catch:{ IOException -> 0x0022 }
    L_0x0061:
        r2 = r1.getResponseCode();	 Catch:{ IOException -> 0x0022 }
        r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r2 < r0) goto L_0x0089;
    L_0x0069:
        r0 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r2 >= r0) goto L_0x0089;
    L_0x006d:
        r0 = r1.getInputStream();	 Catch:{ IOException -> 0x0022 }
        r0 = com.p111h.p112a.p115c.C1987d.m8984a(r0);	 Catch:{ IOException -> 0x0022 }
    L_0x0075:
        r3 = r1.getHeaderFields();	 Catch:{ IOException -> 0x0022 }
        r4 = new com.h.a.c.e;	 Catch:{ IOException -> 0x0022 }
        r4.<init>(r2, r0, r3);	 Catch:{ IOException -> 0x0022 }
        if (r1 == 0) goto L_0x0083;
    L_0x0080:
        r1.disconnect();
    L_0x0083:
        return r4;
    L_0x0084:
        r1 = com.p111h.p112a.p115c.C1987d.m8997b(r8, r9, r10);	 Catch:{ IOException -> 0x0022 }
        goto L_0x0061;
    L_0x0089:
        r0 = r1.getErrorStream();	 Catch:{ IOException -> 0x0022 }
        r0 = com.p111h.p112a.p115c.C1987d.m8984a(r0);	 Catch:{ IOException -> 0x0022 }
        goto L_0x0075;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.h.a.c.d.a(java.lang.String, java.lang.String, java.lang.String, com.h.a.c.c):com.h.a.c.e");
    }

    /* renamed from: a */
    static String m8983a() {
        return String.format("%s/v1/%s", new Object[]{"https://api.stripe.com", "tokens"});
    }

    /* renamed from: a */
    private static String m8984a(InputStream inputStream) {
        String next = new Scanner(inputStream, C3446C.UTF8_NAME).useDelimiter("\\A").next();
        inputStream.close();
        return next;
    }

    /* renamed from: a */
    private static String m8985a(String str) {
        return str == null ? null : URLEncoder.encode(str, C3446C.UTF8_NAME);
    }

    /* renamed from: a */
    private static String m8986a(String str, String str2) {
        if (str2 == null || str2.isEmpty()) {
            return str;
        }
        String str3 = str.contains("?") ? "&" : "?";
        return String.format("%s%s%s", new Object[]{str, str3, str2});
    }

    /* renamed from: a */
    static String m8987a(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (C1986a c1986a : C1987d.m8998b(map)) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(C1987d.m8996b(c1986a.f5859a, c1986a.f5860b));
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    private static HttpURLConnection m8988a(String str, C1985c c1985c) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(DefaultLoadControl.DEFAULT_MAX_BUFFER_MS);
        httpURLConnection.setReadTimeout(80000);
        httpURLConnection.setUseCaches(false);
        for (Entry entry : C1987d.m8993a(c1985c).entrySet()) {
            httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
        }
        if (httpURLConnection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(f5861a);
        }
        return httpURLConnection;
    }

    /* renamed from: a */
    private static HttpURLConnection m8989a(String str, String str2, C1985c c1985c) {
        HttpURLConnection a = C1987d.m8988a(C1987d.m8986a(str, str2), c1985c);
        a.setRequestMethod("GET");
        return a;
    }

    /* renamed from: a */
    private static List<C1986a> m8990a(Object obj, String str) {
        if (obj instanceof Map) {
            return C1987d.m8992a((Map) obj, str);
        }
        if (obj instanceof List) {
            return C1987d.m8991a((List) obj, str);
        }
        if (TtmlNode.ANONYMOUS_REGION_ID.equals(obj)) {
            throw new C1973e("You cannot set '" + str + "' to an empty string. We interpret empty strings as null in requests. You may set '" + str + "' to null to delete the property.", str, null, Integer.valueOf(0), null);
        } else if (obj == null) {
            r0 = new LinkedList();
            r0.add(new C1986a(str, TtmlNode.ANONYMOUS_REGION_ID));
            return r0;
        } else {
            r0 = new LinkedList();
            r0.add(new C1986a(str, obj.toString()));
            return r0;
        }
    }

    /* renamed from: a */
    private static List<C1986a> m8991a(List<Object> list, String str) {
        List<C1986a> linkedList = new LinkedList();
        String format = String.format("%s[]", new Object[]{str});
        if (list.isEmpty()) {
            linkedList.add(new C1986a(str, TtmlNode.ANONYMOUS_REGION_ID));
        } else {
            for (Object a : list) {
                linkedList.addAll(C1987d.m8990a(a, format));
            }
        }
        return linkedList;
    }

    /* renamed from: a */
    private static List<C1986a> m8992a(Map<String, Object> map, String str) {
        List<C1986a> linkedList = new LinkedList();
        if (map == null) {
            return linkedList;
        }
        for (Entry entry : map.entrySet()) {
            String str2 = (String) entry.getKey();
            Object value = entry.getValue();
            if (str != null) {
                str2 = String.format("%s[%s]", new Object[]{str, str2});
            }
            linkedList.addAll(C1987d.m8990a(value, str2));
        }
        return linkedList;
    }

    /* renamed from: a */
    static Map<String, String> m8993a(C1985c c1985c) {
        int i = 0;
        Map<String, String> hashMap = new HashMap();
        String a = c1985c.m8977a();
        hashMap.put("Accept-Charset", C3446C.UTF8_NAME);
        hashMap.put("Accept", "application/json");
        hashMap.put("User-Agent", String.format("Stripe/v1 JavaBindings/%s", new Object[]{"3.5.0"}));
        hashMap.put("Authorization", String.format("Bearer %s", new Object[]{c1985c.m8979c()}));
        String[] strArr = new String[]{"os.name", "os.version", "os.arch", "java.version", "java.vendor", "java.vm.version", "java.vm.vendor"};
        Map hashMap2 = new HashMap();
        int length = strArr.length;
        while (i < length) {
            String str = strArr[i];
            hashMap2.put(str, System.getProperty(str));
            i++;
        }
        hashMap2.put("bindings.version", "3.5.0");
        hashMap2.put("lang", "Java");
        hashMap2.put("publisher", "Stripe");
        hashMap.put("X-Stripe-Client-User-Agent", new JSONObject(hashMap2).toString());
        if (a != null) {
            hashMap.put("Stripe-Version", a);
        }
        if (c1985c.m8978b() != null) {
            hashMap.put("Idempotency-Key", c1985c.m8978b());
        }
        return hashMap;
    }

    /* renamed from: a */
    private static void m8994a(String str, int i, String str2) {
        C1981a a = C1982b.m8974a(str);
        switch (i) {
            case ChatActivity.scheduleDownloads /*400*/:
                throw new C1973e(a.f5848b, a.f5850d, str2, Integer.valueOf(i), null);
            case 401:
                throw new C1971c(a.f5848b, str2, Integer.valueOf(i));
            case WalletConstants.ERROR_CODE_SERVICE_UNAVAILABLE /*402*/:
                throw new C1972d(a.f5848b, str2, a.f5849c, a.f5850d, a.f5851e, a.f5852f, Integer.valueOf(i), null);
            case 403:
                throw new C1974f(a.f5848b, str2, Integer.valueOf(i));
            case WalletConstants.ERROR_CODE_INVALID_PARAMETERS /*404*/:
                throw new C1973e(a.f5848b, a.f5850d, str2, Integer.valueOf(i), null);
            case 429:
                throw new C1975g(a.f5848b, a.f5850d, str2, Integer.valueOf(i), null);
            default:
                throw new C1970b(a.f5848b, str2, Integer.valueOf(i), null);
        }
    }

    /* renamed from: b */
    private static C1988e m8995b(String str, String str2, Map<String, Object> map, C1985c c1985c) {
        try {
            return C1987d.m8982a(str, str2, C1987d.m8987a((Map) map), c1985c);
        } catch (Throwable e) {
            throw new C1973e("Unable to encode parameters to UTF-8. Please contact support@stripe.com for assistance.", null, null, Integer.valueOf(0), e);
        }
    }

    /* renamed from: b */
    private static String m8996b(String str, String str2) {
        return String.format("%s=%s", new Object[]{C1987d.m8985a(str), C1987d.m8985a(str2)});
    }

    /* renamed from: b */
    private static HttpURLConnection m8997b(String str, String str2, C1985c c1985c) {
        HttpURLConnection a = C1987d.m8988a(str, c1985c);
        a.setDoOutput(true);
        a.setRequestMethod("POST");
        a.setRequestProperty("Content-Type", String.format("application/x-www-form-urlencoded;charset=%s", new Object[]{C3446C.UTF8_NAME}));
        OutputStream outputStream = null;
        try {
            outputStream = a.getOutputStream();
            outputStream.write(str2.getBytes(C3446C.UTF8_NAME));
            return a;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /* renamed from: b */
    private static List<C1986a> m8998b(Map<String, Object> map) {
        return C1987d.m8992a((Map) map, null);
    }
}
