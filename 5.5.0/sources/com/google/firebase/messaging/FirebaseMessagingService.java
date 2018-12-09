package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.iid.C1926w;
import com.google.firebase.iid.C1945q;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class FirebaseMessagingService extends C1926w {
    /* renamed from: b */
    private static final Queue<String> f5777b = new ArrayDeque(10);

    /* renamed from: a */
    static void m8900a(Bundle bundle) {
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && str.startsWith("google.c.")) {
                it.remove();
            }
        }
    }

    /* renamed from: b */
    static boolean m8901b(Bundle bundle) {
        return bundle == null ? false : "1".equals(bundle.getString("google.c.a.e"));
    }

    /* renamed from: a */
    protected final Intent mo3045a(Intent intent) {
        return C1945q.m8872a().m8874b();
    }

    /* renamed from: a */
    public void m8903a() {
    }

    /* renamed from: a */
    public void mo3491a(RemoteMessage remoteMessage) {
    }

    /* renamed from: a */
    public void m8905a(String str) {
    }

    /* renamed from: a */
    public void m8906a(String str, Exception exception) {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: b */
    public final void mo3046b(android.content.Intent r11) {
        /*
        r10 = this;
        r6 = 3;
        r5 = 2;
        r3 = -1;
        r4 = 1;
        r2 = 0;
        r0 = r11.getAction();
        if (r0 != 0) goto L_0x000e;
    L_0x000b:
        r0 = "";
    L_0x000e:
        r1 = r0.hashCode();
        switch(r1) {
            case 75300319: goto L_0x0040;
            case 366519424: goto L_0x0035;
            default: goto L_0x0015;
        };
    L_0x0015:
        r0 = r3;
    L_0x0016:
        switch(r0) {
            case 0: goto L_0x004b;
            case 1: goto L_0x01cc;
            default: goto L_0x0019;
        };
    L_0x0019:
        r1 = "FirebaseMessaging";
        r2 = "Unknown intent action: ";
        r0 = r11.getAction();
        r0 = java.lang.String.valueOf(r0);
        r3 = r0.length();
        if (r3 == 0) goto L_0x01db;
    L_0x002d:
        r0 = r2.concat(r0);
    L_0x0031:
        android.util.Log.d(r1, r0);
    L_0x0034:
        return;
    L_0x0035:
        r1 = "com.google.android.c2dm.intent.RECEIVE";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0015;
    L_0x003e:
        r0 = r2;
        goto L_0x0016;
    L_0x0040:
        r1 = "com.google.firebase.messaging.NOTIFICATION_DISMISS";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0015;
    L_0x0049:
        r0 = r4;
        goto L_0x0016;
    L_0x004b:
        r0 = "google.message_id";
        r1 = r11.getStringExtra(r0);
        r0 = android.text.TextUtils.isEmpty(r1);
        if (r0 == 0) goto L_0x00c7;
    L_0x0058:
        r0 = 0;
        r0 = com.google.android.gms.tasks.Tasks.forResult(r0);
    L_0x005d:
        r7 = android.text.TextUtils.isEmpty(r1);
        if (r7 == 0) goto L_0x00db;
    L_0x0063:
        r1 = r2;
    L_0x0064:
        if (r1 != 0) goto L_0x0094;
    L_0x0066:
        r1 = "message_type";
        r1 = r11.getStringExtra(r1);
        if (r1 != 0) goto L_0x0072;
    L_0x006f:
        r1 = "gcm";
    L_0x0072:
        r7 = r1.hashCode();
        switch(r7) {
            case -2062414158: goto L_0x012e;
            case 102161: goto L_0x0123;
            case 814694033: goto L_0x0146;
            case 814800675: goto L_0x013a;
            default: goto L_0x0079;
        };
    L_0x0079:
        r2 = r3;
    L_0x007a:
        switch(r2) {
            case 0: goto L_0x0152;
            case 1: goto L_0x0193;
            case 2: goto L_0x0198;
            case 3: goto L_0x01a4;
            default: goto L_0x007d;
        };
    L_0x007d:
        r2 = "FirebaseMessaging";
        r3 = "Received message with unknown type: ";
        r1 = java.lang.String.valueOf(r1);
        r4 = r1.length();
        if (r4 == 0) goto L_0x01c5;
    L_0x008d:
        r1 = r3.concat(r1);
    L_0x0091:
        android.util.Log.w(r2, r1);
    L_0x0094:
        r2 = 1;
        r1 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ ExecutionException -> 0x009c, InterruptedException -> 0x01e2, TimeoutException -> 0x01e5 }
        com.google.android.gms.tasks.Tasks.await(r0, r2, r1);	 Catch:{ ExecutionException -> 0x009c, InterruptedException -> 0x01e2, TimeoutException -> 0x01e5 }
        goto L_0x0034;
    L_0x009c:
        r0 = move-exception;
    L_0x009d:
        r1 = "FirebaseMessaging";
        r0 = java.lang.String.valueOf(r0);
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r2 = r2 + 20;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Message ack failed: ";
        r2 = r3.append(r2);
        r0 = r2.append(r0);
        r0 = r0.toString();
        android.util.Log.w(r1, r0);
        goto L_0x0034;
    L_0x00c7:
        r0 = new android.os.Bundle;
        r0.<init>();
        r7 = "google.message_id";
        r0.putString(r7, r1);
        r7 = com.google.firebase.iid.ap.m8829a(r10);
        r0 = r7.m8831a(r5, r0);
        goto L_0x005d;
    L_0x00db:
        r7 = f5777b;
        r7 = r7.contains(r1);
        if (r7 == 0) goto L_0x010c;
    L_0x00e3:
        r7 = "FirebaseMessaging";
        r7 = android.util.Log.isLoggable(r7, r6);
        if (r7 == 0) goto L_0x0103;
    L_0x00ec:
        r7 = "FirebaseMessaging";
        r8 = "Received duplicate message: ";
        r1 = java.lang.String.valueOf(r1);
        r9 = r1.length();
        if (r9 == 0) goto L_0x0106;
    L_0x00fc:
        r1 = r8.concat(r1);
    L_0x0100:
        android.util.Log.d(r7, r1);
    L_0x0103:
        r1 = r4;
        goto L_0x0064;
    L_0x0106:
        r1 = new java.lang.String;
        r1.<init>(r8);
        goto L_0x0100;
    L_0x010c:
        r7 = f5777b;
        r7 = r7.size();
        r8 = 10;
        if (r7 < r8) goto L_0x011b;
    L_0x0116:
        r7 = f5777b;
        r7.remove();
    L_0x011b:
        r7 = f5777b;
        r7.add(r1);
        r1 = r2;
        goto L_0x0064;
    L_0x0123:
        r4 = "gcm";
        r4 = r1.equals(r4);
        if (r4 == 0) goto L_0x0079;
    L_0x012c:
        goto L_0x007a;
    L_0x012e:
        r2 = "deleted_messages";
        r2 = r1.equals(r2);
        if (r2 == 0) goto L_0x0079;
    L_0x0137:
        r2 = r4;
        goto L_0x007a;
    L_0x013a:
        r2 = "send_event";
        r2 = r1.equals(r2);
        if (r2 == 0) goto L_0x0079;
    L_0x0143:
        r2 = r5;
        goto L_0x007a;
    L_0x0146:
        r2 = "send_error";
        r2 = r1.equals(r2);
        if (r2 == 0) goto L_0x0079;
    L_0x014f:
        r2 = r6;
        goto L_0x007a;
    L_0x0152:
        r1 = r11.getExtras();
        r1 = m8901b(r1);
        if (r1 == 0) goto L_0x015f;
    L_0x015c:
        com.google.firebase.messaging.C1961e.m8930a(r10, r11);
    L_0x015f:
        r1 = r11.getExtras();
        if (r1 != 0) goto L_0x016a;
    L_0x0165:
        r1 = new android.os.Bundle;
        r1.<init>();
    L_0x016a:
        r2 = "android.support.content.wakelockid";
        r1.remove(r2);
        r2 = com.google.firebase.messaging.C1960d.m8921a(r1);
        if (r2 == 0) goto L_0x0189;
    L_0x0176:
        r2 = com.google.firebase.messaging.C1960d.m8916a(r10);
        r2 = r2.m8929c(r1);
        if (r2 != 0) goto L_0x0094;
    L_0x0180:
        r2 = m8901b(r1);
        if (r2 == 0) goto L_0x0189;
    L_0x0186:
        com.google.firebase.messaging.C1961e.m8934d(r10, r11);
    L_0x0189:
        r2 = new com.google.firebase.messaging.RemoteMessage;
        r2.<init>(r1);
        r10.mo3491a(r2);
        goto L_0x0094;
    L_0x0193:
        r10.m8903a();
        goto L_0x0094;
    L_0x0198:
        r1 = "google.message_id";
        r1 = r11.getStringExtra(r1);
        r10.m8905a(r1);
        goto L_0x0094;
    L_0x01a4:
        r1 = "google.message_id";
        r1 = r11.getStringExtra(r1);
        if (r1 != 0) goto L_0x01b4;
    L_0x01ad:
        r1 = "message_id";
        r1 = r11.getStringExtra(r1);
    L_0x01b4:
        r2 = new com.google.firebase.messaging.c;
        r3 = "error";
        r3 = r11.getStringExtra(r3);
        r2.<init>(r3);
        r10.m8906a(r1, r2);
        goto L_0x0094;
    L_0x01c5:
        r1 = new java.lang.String;
        r1.<init>(r3);
        goto L_0x0091;
    L_0x01cc:
        r0 = r11.getExtras();
        r0 = m8901b(r0);
        if (r0 == 0) goto L_0x0034;
    L_0x01d6:
        com.google.firebase.messaging.C1961e.m8933c(r10, r11);
        goto L_0x0034;
    L_0x01db:
        r0 = new java.lang.String;
        r0.<init>(r2);
        goto L_0x0031;
    L_0x01e2:
        r0 = move-exception;
        goto L_0x009d;
    L_0x01e5:
        r0 = move-exception;
        goto L_0x009d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.FirebaseMessagingService.b(android.content.Intent):void");
    }

    /* renamed from: c */
    public final boolean mo3057c(Intent intent) {
        if (!"com.google.firebase.messaging.NOTIFICATION_OPEN".equals(intent.getAction())) {
            return false;
        }
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("pending_intent");
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (CanceledException e) {
                Log.e("FirebaseMessaging", "Notification pending intent canceled");
            }
        }
        if (m8901b(intent.getExtras())) {
            C1961e.m8932b(this, intent);
        }
        return true;
    }
}
