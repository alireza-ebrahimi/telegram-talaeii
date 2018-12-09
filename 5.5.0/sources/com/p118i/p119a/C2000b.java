package com.p118i.p119a;

import android.os.Process;
import com.p118i.p119a.C2008d.C2007a;
import com.p118i.p119a.p120a.C1996a;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

/* renamed from: com.i.a.b */
class C2000b extends Thread {
    /* renamed from: a */
    private final BlockingQueue<C2002c> f5878a;
    /* renamed from: b */
    private volatile boolean f5879b = false;
    /* renamed from: c */
    private C2007a f5880c;
    /* renamed from: d */
    private final int f5881d = 4096;
    /* renamed from: e */
    private int f5882e = 0;
    /* renamed from: f */
    private final int f5883f = 5;
    /* renamed from: g */
    private final int f5884g = 416;
    /* renamed from: h */
    private final int f5885h = 307;
    /* renamed from: i */
    private long f5886i;
    /* renamed from: j */
    private long f5887j;
    /* renamed from: k */
    private boolean f5888k = true;
    /* renamed from: l */
    private Timer f5889l;

    C2000b(BlockingQueue<C2002c> blockingQueue, C2007a c2007a) {
        this.f5878a = blockingQueue;
        this.f5880c = c2007a;
    }

    /* renamed from: a */
    private int m9030a(C2002c c2002c, byte[] bArr, InputStream inputStream) {
        try {
            return inputStream.read(bArr);
        } catch (IOException e) {
            if ("unexpected end of stream".equals(e.getMessage())) {
                return -1;
            }
            m9036a(c2002c, 1004, "IOException: Failed reading response");
            return Integer.MIN_VALUE;
        }
    }

    /* renamed from: a */
    private long m9031a(URLConnection uRLConnection, String str, long j) {
        try {
            j = Long.parseLong(uRLConnection.getHeaderField(str));
        } catch (NumberFormatException e) {
        }
        return j;
    }

    /* renamed from: a */
    private void m9033a(final C2002c c2002c) {
        m9034a(c2002c, 128);
        C1997h b = c2002c.m9054b();
        try {
            b.mo3060b();
            this.f5889l.schedule(new TimerTask(this) {
                /* renamed from: b */
                final /* synthetic */ C2000b f5877b;

                public void run() {
                    this.f5877b.m9038a(c2002c, c2002c.m9060g().toString());
                }
            }, (long) b.mo3059a());
        } catch (C2011g e) {
            m9036a(c2002c, 1009, "Connection time out after maximum retires attempted");
        }
    }

    /* renamed from: a */
    private void m9034a(C2002c c2002c, int i) {
        c2002c.m9055b(i);
    }

    /* renamed from: a */
    private void m9035a(C2002c c2002c, int i, long j) {
        this.f5880c.m9069a(c2002c, this.f5886i, j, i);
    }

    /* renamed from: a */
    private void m9036a(C2002c c2002c, int i, String str) {
        this.f5888k = false;
        c2002c.m9055b(32);
        if (c2002c.m9062i()) {
            m9042b(c2002c);
        }
        this.f5880c.m9068a(c2002c, i, str);
        c2002c.m9066m();
    }

    /* renamed from: a */
    private void m9037a(C2002c c2002c, InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[4096];
        this.f5887j = 0;
        c2002c.m9055b(8);
        C1996a.m9021a("Content Length: " + this.f5886i + " for Download Id " + c2002c.m9056c());
        while (!c2002c.m9064k()) {
            int a = m9030a(c2002c, bArr, inputStream);
            if (this.f5886i != -1 && this.f5886i > 0) {
                m9035a(c2002c, (int) ((this.f5887j * 100) / this.f5886i), this.f5887j);
            }
            if (a == -1) {
                m9043c(c2002c);
                return;
            } else if (a == Integer.MIN_VALUE) {
                return;
            } else {
                if (m9040a(c2002c, bArr, a, outputStream)) {
                    this.f5887j += (long) a;
                } else {
                    c2002c.m9066m();
                    m9036a(c2002c, 1001, "Failed writing file");
                    return;
                }
            }
        }
        C1996a.m9021a("Stopping the download as Download Request is cancelled for Downloaded Id " + c2002c.m9056c());
        c2002c.m9066m();
        m9036a(c2002c, 1008, "Download cancelled");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private void m9038a(com.p118i.p119a.C2002c r8, java.lang.String r9) {
        /*
        r7 = this;
        r5 = 5;
        r0 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x005b }
        r0.<init>(r9);	 Catch:{ MalformedURLException -> 0x005b }
        r1 = 0;
        r0 = r0.openConnection();	 Catch:{ SocketTimeoutException -> 0x0187, ConnectTimeoutException -> 0x0184, IOException -> 0x0182 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ SocketTimeoutException -> 0x0187, ConnectTimeoutException -> 0x0184, IOException -> 0x0182 }
        r1 = 0;
        r0.setInstanceFollowRedirects(r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r8.m9054b();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r1.mo3059a();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r0.setConnectTimeout(r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r8.m9054b();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r1.mo3059a();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r0.setReadTimeout(r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r3 = r8.m9065l();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        if (r3 == 0) goto L_0x0065;
    L_0x002d:
        r1 = r3.keySet();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r4 = r1.iterator();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
    L_0x0035:
        r1 = r4.hasNext();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        if (r1 == 0) goto L_0x0065;
    L_0x003b:
        r1 = r4.next();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = (java.lang.String) r1;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2 = r3.get(r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2 = (java.lang.String) r2;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r0.addRequestProperty(r1, r2);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        goto L_0x0035;
    L_0x004b:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x004f:
        r0.printStackTrace();	 Catch:{ all -> 0x0180 }
        r7.m9033a(r8);	 Catch:{ all -> 0x0180 }
        if (r1 == 0) goto L_0x005a;
    L_0x0057:
        r1.disconnect();
    L_0x005a:
        return;
    L_0x005b:
        r0 = move-exception;
        r0 = 1007; // 0x3ef float:1.411E-42 double:4.975E-321;
        r1 = "MalformedURLException: URI passed is malformed.";
        r7.m9036a(r8, r0, r1);
        goto L_0x005a;
    L_0x0065:
        r1 = 4;
        r7.m9034a(r8, r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r0.getResponseCode();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2.<init>();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r3 = "Response code obtained for downloaded Id ";
        r2 = r2.append(r3);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r3 = r8.m9056c();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2 = r2.append(r3);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r3 = " : httpResponse Code ";
        r2 = r2.append(r3);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2 = r2.append(r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2 = r2.toString();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        com.p118i.p119a.p120a.C1996a.m9021a(r2);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        switch(r1) {
            case 200: goto L_0x00c4;
            case 206: goto L_0x00c4;
            case 301: goto L_0x00f1;
            case 302: goto L_0x00f1;
            case 303: goto L_0x00f1;
            case 307: goto L_0x00f1;
            case 416: goto L_0x0155;
            case 500: goto L_0x0175;
            case 503: goto L_0x016a;
            default: goto L_0x0096;
        };	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
    L_0x0096:
        r2 = 1002; // 0x3ea float:1.404E-42 double:4.95E-321;
        r3 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r3.<init>();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r4 = "Unhandled HTTP response:";
        r3 = r3.append(r4);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r3.append(r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r3 = " message:";
        r1 = r1.append(r3);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r3 = r0.getResponseMessage();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r1.append(r3);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r1.toString();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r7.m9036a(r8, r2, r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
    L_0x00be:
        if (r0 == 0) goto L_0x005a;
    L_0x00c0:
        r0.disconnect();
        goto L_0x005a;
    L_0x00c4:
        r1 = 0;
        r7.f5888k = r1;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r7.m9041b(r8, r0);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2 = 1;
        if (r1 != r2) goto L_0x00d7;
    L_0x00ce:
        r7.m9039a(r8, r0);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
    L_0x00d1:
        if (r0 == 0) goto L_0x005a;
    L_0x00d3:
        r0.disconnect();
        goto L_0x005a;
    L_0x00d7:
        r1 = 1006; // 0x3ee float:1.41E-42 double:4.97E-321;
        r2 = "Transfer-Encoding not found as well as can't know size of download, giving up";
        r7.m9036a(r8, r1, r2);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        goto L_0x00d1;
    L_0x00e0:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x00e4:
        r0.printStackTrace();	 Catch:{ all -> 0x0180 }
        r7.m9033a(r8);	 Catch:{ all -> 0x0180 }
        if (r1 == 0) goto L_0x005a;
    L_0x00ec:
        r1.disconnect();
        goto L_0x005a;
    L_0x00f1:
        r1 = r7.f5882e;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        if (r1 >= r5) goto L_0x013e;
    L_0x00f5:
        r1 = r7.f5888k;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        if (r1 == 0) goto L_0x013e;
    L_0x00f9:
        r1 = r7.f5882e;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = r1 + 1;
        r7.f5882e = r1;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = "ContentValues";
        r2 = new java.lang.StringBuilder;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2.<init>();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r3 = "Redirect for downloaded Id ";
        r2 = r2.append(r3);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r3 = r8.m9056c();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2 = r2.append(r3);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r2 = r2.toString();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        com.p118i.p119a.p120a.C1996a.m9022a(r1, r2);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r1 = "Location";
        r1 = r0.getHeaderField(r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r7.m9038a(r8, r1);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        goto L_0x00f1;
    L_0x0128:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x012c:
        r0.printStackTrace();	 Catch:{ all -> 0x0180 }
        r0 = 1004; // 0x3ec float:1.407E-42 double:4.96E-321;
        r2 = "Trouble with low-level sockets";
        r7.m9036a(r8, r0, r2);	 Catch:{ all -> 0x0180 }
        if (r1 == 0) goto L_0x005a;
    L_0x0139:
        r1.disconnect();
        goto L_0x005a;
    L_0x013e:
        r1 = r7.f5882e;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        if (r1 <= r5) goto L_0x00be;
    L_0x0142:
        r1 = r7.f5888k;	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        if (r1 == 0) goto L_0x00be;
    L_0x0146:
        r1 = 1005; // 0x3ed float:1.408E-42 double:4.965E-321;
        r2 = "Too many redirects, giving up";
        r7.m9036a(r8, r1, r2);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        if (r0 == 0) goto L_0x005a;
    L_0x0150:
        r0.disconnect();
        goto L_0x005a;
    L_0x0155:
        r1 = 416; // 0x1a0 float:5.83E-43 double:2.055E-321;
        r2 = r0.getResponseMessage();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r7.m9036a(r8, r1, r2);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        goto L_0x00be;
    L_0x0160:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0164:
        if (r1 == 0) goto L_0x0169;
    L_0x0166:
        r1.disconnect();
    L_0x0169:
        throw r0;
    L_0x016a:
        r1 = 503; // 0x1f7 float:7.05E-43 double:2.485E-321;
        r2 = r0.getResponseMessage();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r7.m9036a(r8, r1, r2);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        goto L_0x00be;
    L_0x0175:
        r1 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r2 = r0.getResponseMessage();	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        r7.m9036a(r8, r1, r2);	 Catch:{ SocketTimeoutException -> 0x004b, ConnectTimeoutException -> 0x00e0, IOException -> 0x0128, all -> 0x0160 }
        goto L_0x00be;
    L_0x0180:
        r0 = move-exception;
        goto L_0x0164;
    L_0x0182:
        r0 = move-exception;
        goto L_0x012c;
    L_0x0184:
        r0 = move-exception;
        goto L_0x00e4;
    L_0x0187:
        r0 = move-exception;
        goto L_0x004f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.i.a.b.a(com.i.a.c, java.lang.String):void");
    }

    /* renamed from: a */
    private void m9039a(C2002c c2002c, HttpURLConnection httpURLConnection) {
        InputStream inputStream;
        Object obj;
        OutputStream fileOutputStream;
        File file;
        FileDescriptor fileDescriptor = null;
        Object obj2 = 1;
        m9042b(c2002c);
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            obj = fileDescriptor;
        } catch (Throwable th) {
            Throwable th2 = th;
            Object obj3 = fileDescriptor;
            obj = fileDescriptor;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                } catch (IOException e3) {
                    e3.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                } catch (Throwable th3) {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e42) {
                            e42.printStackTrace();
                        }
                    }
                }
            }
            if (fileDescriptor != null) {
                fileDescriptor.sync();
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e422) {
                    e422.printStackTrace();
                }
            }
            throw th2;
        }
        try {
            file = new File(c2002c.m9061h().getPath());
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    m9036a(c2002c, 1001, "Error in creating destination file");
                    if (obj2 != null) {
                        try {
                            fileOutputStream = new FileOutputStream(file, true);
                            try {
                                fileDescriptor = ((FileOutputStream) fileOutputStream).getFD();
                            } catch (IOException e5) {
                                e = e5;
                                e.printStackTrace();
                                if (inputStream != null) {
                                    try {
                                        m9036a(c2002c, 1001, "Error in creating input stream");
                                    } catch (Throwable th4) {
                                        th2 = th4;
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        if (fileOutputStream != null) {
                                            fileOutputStream.flush();
                                        }
                                        if (fileDescriptor != null) {
                                            fileDescriptor.sync();
                                        }
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                        throw th2;
                                    }
                                } else if (fileOutputStream != null) {
                                    m9036a(c2002c, 1001, "Error in writing download contents to the destination file");
                                } else {
                                    m9037a(c2002c, inputStream, fileOutputStream);
                                }
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e6) {
                                        e6.printStackTrace();
                                    }
                                }
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.flush();
                                    } catch (IOException e62) {
                                        e62.printStackTrace();
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                                return;
                                            } catch (IOException e622) {
                                                IOException e6222;
                                                e6222.printStackTrace();
                                                return;
                                            }
                                        }
                                        return;
                                    } catch (Throwable th5) {
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (IOException e4222) {
                                                e4222.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                if (fileDescriptor != null) {
                                    fileDescriptor.sync();
                                }
                                if (fileOutputStream == null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e62222) {
                                        e62222.printStackTrace();
                                        return;
                                    }
                                }
                            }
                        } catch (IOException e7) {
                            e62222 = e7;
                            fileOutputStream = fileDescriptor;
                            e62222.printStackTrace();
                            if (inputStream != null) {
                                m9036a(c2002c, 1001, "Error in creating input stream");
                            } else if (fileOutputStream != null) {
                                m9037a(c2002c, inputStream, fileOutputStream);
                            } else {
                                m9036a(c2002c, 1001, "Error in writing download contents to the destination file");
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.flush();
                            }
                            if (fileDescriptor != null) {
                                fileDescriptor.sync();
                            }
                            if (fileOutputStream == null) {
                                fileOutputStream.close();
                            }
                        }
                        if (inputStream != null) {
                            m9036a(c2002c, 1001, "Error in creating input stream");
                        } else if (fileOutputStream != null) {
                            m9036a(c2002c, 1001, "Error in writing download contents to the destination file");
                        } else {
                            m9037a(c2002c, inputStream, fileOutputStream);
                        }
                    } else {
                        obj3 = fileDescriptor;
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                    }
                    if (fileDescriptor != null) {
                        fileDescriptor.sync();
                    }
                    if (fileOutputStream == null) {
                        fileOutputStream.close();
                    }
                }
            }
            obj2 = null;
        } catch (IOException e42222) {
            e42222.printStackTrace();
            m9036a(c2002c, 1001, "Error in creating destination file");
        } catch (Throwable th6) {
            th2 = th6;
            fileOutputStream = fileDescriptor;
            if (inputStream != null) {
                inputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.flush();
            }
            if (fileDescriptor != null) {
                fileDescriptor.sync();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th2;
        }
        if (obj2 != null) {
            obj3 = fileDescriptor;
        } else {
            fileOutputStream = new FileOutputStream(file, true);
            fileDescriptor = ((FileOutputStream) fileOutputStream).getFD();
            if (inputStream != null) {
                m9036a(c2002c, 1001, "Error in creating input stream");
            } else if (fileOutputStream != null) {
                m9037a(c2002c, inputStream, fileOutputStream);
            } else {
                m9036a(c2002c, 1001, "Error in writing download contents to the destination file");
            }
        }
        if (inputStream != null) {
            inputStream.close();
        }
        if (fileOutputStream != null) {
            fileOutputStream.flush();
        }
        if (fileDescriptor != null) {
            fileDescriptor.sync();
        }
        if (fileOutputStream == null) {
            fileOutputStream.close();
        }
    }

    /* renamed from: a */
    private boolean m9040a(C2002c c2002c, byte[] bArr, int i, OutputStream outputStream) {
        try {
            outputStream.write(bArr, 0, i);
            return true;
        } catch (IOException e) {
            m9036a(c2002c, 1001, "IOException when writing download contents to the destination file");
            return false;
        } catch (Exception e2) {
            m9036a(c2002c, 1001, "Exception when writing download contents to the destination file");
            return false;
        }
    }

    /* renamed from: b */
    private int m9041b(C2002c c2002c, HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("Transfer-Encoding");
        this.f5886i = -1;
        if (headerField == null) {
            this.f5886i = m9031a((URLConnection) httpURLConnection, "Content-Length", -1);
        } else {
            C1996a.m9021a("Ignoring Content-Length since Transfer-Encoding is also defined for Downloaded Id " + c2002c.m9056c());
        }
        return this.f5886i != -1 ? 1 : (headerField == null || !headerField.equalsIgnoreCase("chunked")) ? -1 : 1;
    }

    /* renamed from: b */
    private void m9042b(C2002c c2002c) {
        C1996a.m9024b("cleanupDestination() deleting " + c2002c.m9061h().getPath());
        File file = new File(c2002c.m9061h().getPath());
        if (file.exists()) {
            file.delete();
        }
    }

    /* renamed from: c */
    private void m9043c(C2002c c2002c) {
        this.f5880c.m9067a(c2002c);
        c2002c.m9055b(16);
        c2002c.m9066m();
    }

    /* renamed from: a */
    void m9044a() {
        this.f5879b = true;
        interrupt();
    }

    public void run() {
        C2002c c2002c;
        Process.setThreadPriority(10);
        this.f5889l = new Timer();
        while (true) {
            try {
                c2002c = (C2002c) this.f5878a.take();
                try {
                    this.f5882e = 0;
                    this.f5888k = true;
                    C1996a.m9021a("Download initiated for " + c2002c.m9056c());
                    m9034a(c2002c, 2);
                    m9038a(c2002c, c2002c.m9060g().toString());
                } catch (InterruptedException e) {
                    if (this.f5879b) {
                        if (c2002c != null) {
                            c2002c.m9066m();
                            if (c2002c.m9057d() != 16) {
                                m9036a(c2002c, 1008, "Download cancelled");
                            }
                        }
                        this.f5889l.cancel();
                    }
                }
            } catch (InterruptedException e2) {
                c2002c = null;
                if (this.f5879b) {
                    if (c2002c != null) {
                        c2002c.m9066m();
                        if (c2002c.m9057d() != 16) {
                            m9036a(c2002c, 1008, "Download cancelled");
                        }
                    }
                    this.f5889l.cancel();
                }
            }
        }
        if (c2002c != null) {
            c2002c.m9066m();
            if (c2002c.m9057d() != 16) {
                m9036a(c2002c, 1008, "Download cancelled");
            }
        }
        this.f5889l.cancel();
    }
}
