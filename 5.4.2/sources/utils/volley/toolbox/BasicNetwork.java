package utils.volley.toolbox;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import utils.volley.Cache.Entry;
import utils.volley.Header;
import utils.volley.Network;
import utils.volley.Request;
import utils.volley.RetryPolicy;
import utils.volley.ServerError;
import utils.volley.VolleyError;
import utils.volley.VolleyLog;

public class BasicNetwork implements Network {
    protected static final boolean DEBUG = VolleyLog.DEBUG;
    private static final int DEFAULT_POOL_SIZE = 4096;
    private static final int SLOW_REQUEST_THRESHOLD_MS = 3000;
    private final BaseHttpStack mBaseHttpStack;
    @Deprecated
    protected final HttpStack mHttpStack;
    protected final ByteArrayPool mPool;

    public BasicNetwork(BaseHttpStack baseHttpStack) {
        this(baseHttpStack, new ByteArrayPool(4096));
    }

    public BasicNetwork(BaseHttpStack baseHttpStack, ByteArrayPool byteArrayPool) {
        this.mBaseHttpStack = baseHttpStack;
        this.mHttpStack = baseHttpStack;
        this.mPool = byteArrayPool;
    }

    @Deprecated
    public BasicNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(4096));
    }

    @Deprecated
    public BasicNetwork(HttpStack httpStack, ByteArrayPool byteArrayPool) {
        this.mHttpStack = httpStack;
        this.mBaseHttpStack = new AdaptedHttpStack(httpStack);
        this.mPool = byteArrayPool;
    }

    private static void attemptRetryOnException(String str, Request<?> request, VolleyError volleyError) {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int timeoutMs = request.getTimeoutMs();
        try {
            retryPolicy.retry(volleyError);
            request.addMarker(String.format("%s-retry [timeout=%s]", new Object[]{str, Integer.valueOf(timeoutMs)}));
        } catch (VolleyError e) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{str, Integer.valueOf(timeoutMs)}));
            throw e;
        }
    }

    private static List<Header> combineHeaders(List<Header> list, Entry entry) {
        Set treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        if (!list.isEmpty()) {
            for (Header name : list) {
                treeSet.add(name.getName());
            }
        }
        List<Header> arrayList = new ArrayList(list);
        if (entry.allResponseHeaders != null) {
            if (!entry.allResponseHeaders.isEmpty()) {
                for (Header name2 : entry.allResponseHeaders) {
                    if (!treeSet.contains(name2.getName())) {
                        arrayList.add(name2);
                    }
                }
            }
        } else if (!entry.responseHeaders.isEmpty()) {
            for (Map.Entry entry2 : entry.responseHeaders.entrySet()) {
                if (!treeSet.contains(entry2.getKey())) {
                    arrayList.add(new Header((String) entry2.getKey(), (String) entry2.getValue()));
                }
            }
        }
        return arrayList;
    }

    @Deprecated
    protected static Map<String, String> convertHeaders(Header[] headerArr) {
        Map<String, String> treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headerArr.length; i++) {
            treeMap.put(headerArr[i].getName(), headerArr[i].getValue());
        }
        return treeMap;
    }

    private Map<String, String> getCacheHeaders(Entry entry) {
        if (entry == null) {
            return Collections.emptyMap();
        }
        Map<String, String> hashMap = new HashMap();
        if (entry.etag != null) {
            hashMap.put("If-None-Match", entry.etag);
        }
        if (entry.lastModified <= 0) {
            return hashMap;
        }
        hashMap.put("If-Modified-Since", HttpHeaderParser.formatEpochAsRfc1123(entry.lastModified));
        return hashMap;
    }

    private byte[] inputStreamToBytes(InputStream inputStream, int i) {
        PoolingByteArrayOutputStream poolingByteArrayOutputStream = new PoolingByteArrayOutputStream(this.mPool, i);
        if (inputStream == null) {
            try {
                throw new ServerError();
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
                    }
                }
                this.mPool.returnBuf(null);
                poolingByteArrayOutputStream.close();
            }
        } else {
            byte[] buf = this.mPool.getBuf(1024);
            while (true) {
                int read = inputStream.read(buf);
                if (read == -1) {
                    break;
                }
                poolingByteArrayOutputStream.write(buf, 0, read);
            }
            byte[] toByteArray = poolingByteArrayOutputStream.toByteArray();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
                }
            }
            this.mPool.returnBuf(buf);
            poolingByteArrayOutputStream.close();
            return toByteArray;
        }
    }

    private void logSlowRequests(long j, Request<?> request, byte[] bArr, int i) {
        if (DEBUG || j > 3000) {
            String str = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(j);
            objArr[2] = bArr != null ? Integer.valueOf(bArr.length) : "null";
            objArr[3] = Integer.valueOf(i);
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.d(str, objArr);
        }
    }

    protected void logError(String str, String str2, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        VolleyLog.v("HTTP ERROR(%s) %d ms to fetch %s", new Object[]{str, Long.valueOf(elapsedRealtime - j), str2});
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public utils.volley.NetworkResponse performRequest(utils.volley.Request<?> r21) {
        /*
        r20 = this;
        r18 = android.os.SystemClock.elapsedRealtime();
    L_0x0004:
        r3 = 0;
        r9 = 0;
        r8 = java.util.Collections.emptyList();
        r2 = r21.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x00c3 }
        r0 = r20;
        r2 = r0.getCacheHeaders(r2);	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x00c3 }
        r0 = r20;
        r4 = r0.mBaseHttpStack;	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x00c3 }
        r0 = r21;
        r17 = r4.executeRequest(r0, r2);	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x00c3 }
        r14 = r17.getStatusCode();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r8 = r17.getHeaders();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r2 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        if (r14 != r2) goto L_0x0056;
    L_0x002a:
        r2 = r21.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        if (r2 != 0) goto L_0x0040;
    L_0x0030:
        r2 = new utils.volley.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r3 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        r4 = 0;
        r5 = 1;
        r6 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r6 = r6 - r18;
        r2.<init>(r3, r4, r5, r6, r8);	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
    L_0x003f:
        return r2;
    L_0x0040:
        r16 = combineHeaders(r8, r2);	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r10 = new utils.volley.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r11 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        r12 = r2.data;	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r13 = 1;
        r2 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r14 = r2 - r18;
        r10.<init>(r11, r12, r13, r14, r16);	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r2 = r10;
        goto L_0x003f;
    L_0x0056:
        r2 = r17.getContent();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        if (r2 == 0) goto L_0x0091;
    L_0x005c:
        r3 = r17.getContentLength();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        r0 = r20;
        r13 = r0.inputStreamToBytes(r2, r3);	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
    L_0x0066:
        r2 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0159 }
        r10 = r2 - r18;
        r9 = r20;
        r12 = r21;
        r9.logSlowRequests(r10, r12, r13, r14);	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0159 }
        r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r14 < r2) goto L_0x007b;
    L_0x0077:
        r2 = 299; // 0x12b float:4.19E-43 double:1.477E-321;
        if (r14 <= r2) goto L_0x0095;
    L_0x007b:
        r2 = new java.io.IOException;	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0159 }
        r2.<init>();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0159 }
        throw r2;	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0159 }
    L_0x0081:
        r2 = move-exception;
        r2 = "socket";
        r3 = new utils.volley.TimeoutError;
        r3.<init>();
        r0 = r21;
        attemptRetryOnException(r2, r0, r3);
        goto L_0x0004;
    L_0x0091:
        r2 = 0;
        r13 = new byte[r2];	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0153 }
        goto L_0x0066;
    L_0x0095:
        r2 = new utils.volley.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0159 }
        r5 = 0;
        r6 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0159 }
        r6 = r6 - r18;
        r3 = r14;
        r4 = r13;
        r2.<init>(r3, r4, r5, r6, r8);	 Catch:{ SocketTimeoutException -> 0x0081, MalformedURLException -> 0x00a4, IOException -> 0x0159 }
        goto L_0x003f;
    L_0x00a4:
        r2 = move-exception;
        r3 = new java.lang.RuntimeException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Bad URL ";
        r4 = r4.append(r5);
        r5 = r21.getUrl();
        r4 = r4.append(r5);
        r4 = r4.toString();
        r3.<init>(r4, r2);
        throw r3;
    L_0x00c3:
        r2 = move-exception;
        r4 = r9;
    L_0x00c5:
        if (r3 == 0) goto L_0x0107;
    L_0x00c7:
        r3 = r3.getStatusCode();
        r2 = "Unexpected response code %d for %s";
        r5 = 2;
        r5 = new java.lang.Object[r5];
        r6 = 0;
        r7 = java.lang.Integer.valueOf(r3);
        r5[r6] = r7;
        r6 = 1;
        r7 = r21.getUrl();
        r5[r6] = r7;
        utils.volley.VolleyLog.e(r2, r5);
        if (r4 == 0) goto L_0x0144;
    L_0x00e4:
        r2 = new utils.volley.NetworkResponse;
        r5 = 0;
        r6 = android.os.SystemClock.elapsedRealtime();
        r6 = r6 - r18;
        r2.<init>(r3, r4, r5, r6, r8);
        r4 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        if (r3 == r4) goto L_0x00f8;
    L_0x00f4:
        r4 = 403; // 0x193 float:5.65E-43 double:1.99E-321;
        if (r3 != r4) goto L_0x010d;
    L_0x00f8:
        r3 = "auth";
        r4 = new utils.volley.AuthFailureError;
        r4.<init>(r2);
        r0 = r21;
        attemptRetryOnException(r3, r0, r4);
        goto L_0x0004;
    L_0x0107:
        r3 = new utils.volley.NoConnectionError;
        r3.<init>(r2);
        throw r3;
    L_0x010d:
        r4 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r3 < r4) goto L_0x011b;
    L_0x0111:
        r4 = 499; // 0x1f3 float:6.99E-43 double:2.465E-321;
        if (r3 > r4) goto L_0x011b;
    L_0x0115:
        r3 = new utils.volley.ClientError;
        r3.<init>(r2);
        throw r3;
    L_0x011b:
        r4 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r3 < r4) goto L_0x013e;
    L_0x011f:
        r4 = 599; // 0x257 float:8.4E-43 double:2.96E-321;
        if (r3 > r4) goto L_0x013e;
    L_0x0123:
        r3 = r21.shouldRetryServerErrors();
        if (r3 == 0) goto L_0x0138;
    L_0x0129:
        r3 = "server";
        r4 = new utils.volley.ServerError;
        r4.<init>(r2);
        r0 = r21;
        attemptRetryOnException(r3, r0, r4);
        goto L_0x0004;
    L_0x0138:
        r3 = new utils.volley.ServerError;
        r3.<init>(r2);
        throw r3;
    L_0x013e:
        r3 = new utils.volley.ServerError;
        r3.<init>(r2);
        throw r3;
    L_0x0144:
        r2 = "network";
        r3 = new utils.volley.NetworkError;
        r3.<init>();
        r0 = r21;
        attemptRetryOnException(r2, r0, r3);
        goto L_0x0004;
    L_0x0153:
        r2 = move-exception;
        r4 = r9;
        r3 = r17;
        goto L_0x00c5;
    L_0x0159:
        r2 = move-exception;
        r4 = r13;
        r3 = r17;
        goto L_0x00c5;
        */
        throw new UnsupportedOperationException("Method not decompiled: utils.volley.toolbox.BasicNetwork.performRequest(utils.volley.Request):utils.volley.NetworkResponse");
    }
}
