package net.hockeyapp.android.p137e;

/* renamed from: net.hockeyapp.android.e.c */
public class C2399c {

    /* renamed from: net.hockeyapp.android.e.c$a */
    private static class C2398a {
        /* renamed from: a */
        public static final C2399c f8081a = new C2399c();
    }

    private C2399c() {
    }

    /* renamed from: a */
    public static C2399c m11838a() {
        return C2398a.f8081a;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    public net.hockeyapp.android.p135c.C2374e m11839a(java.lang.String r34) {
        /*
        r33 = this;
        r3 = 0;
        if (r34 == 0) goto L_0x025e;
    L_0x0003:
        r7 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0239 }
        r0 = r34;
        r7.<init>(r0);	 Catch:{ JSONException -> 0x0239 }
        r2 = "feedback";
        r8 = r7.getJSONObject(r2);	 Catch:{ JSONException -> 0x0239 }
        r9 = new net.hockeyapp.android.c.b;	 Catch:{ JSONException -> 0x0239 }
        r9.<init>();	 Catch:{ JSONException -> 0x0239 }
        r2 = "messages";
        r10 = r8.getJSONArray(r2);	 Catch:{ JSONException -> 0x0239 }
        r2 = 0;
        r4 = r10.length();	 Catch:{ JSONException -> 0x0239 }
        if (r4 <= 0) goto L_0x01d8;
    L_0x0024:
        r2 = new java.util.ArrayList;	 Catch:{ JSONException -> 0x0239 }
        r2.<init>();	 Catch:{ JSONException -> 0x0239 }
        r4 = 0;
        r6 = r4;
    L_0x002b:
        r4 = r10.length();	 Catch:{ JSONException -> 0x0239 }
        if (r6 >= r4) goto L_0x01d8;
    L_0x0031:
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "subject";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r11 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "text";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r12 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "oem";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r13 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "model";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r14 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "os_version";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r15 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "created_at";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r16 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "id";
        r17 = r4.getInt(r5);	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "token";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r18 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "via";
        r19 = r4.getInt(r5);	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "user_string";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r20 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "clean_text";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r21 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "name";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r22 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "app_id";
        r4 = r4.getString(r5);	 Catch:{ JSONException -> 0x0239 }
        r23 = r4.toString();	 Catch:{ JSONException -> 0x0239 }
        r4 = r10.getJSONObject(r6);	 Catch:{ JSONException -> 0x0239 }
        r5 = "attachments";
        r24 = r4.optJSONArray(r5);	 Catch:{ JSONException -> 0x0239 }
        r4 = java.util.Collections.emptyList();	 Catch:{ JSONException -> 0x0239 }
        if (r24 == 0) goto L_0x0191;
    L_0x00fd:
        r4 = new java.util.ArrayList;	 Catch:{ JSONException -> 0x0239 }
        r4.<init>();	 Catch:{ JSONException -> 0x0239 }
        r5 = 0;
    L_0x0103:
        r25 = r24.length();	 Catch:{ JSONException -> 0x0239 }
        r0 = r25;
        if (r5 >= r0) goto L_0x0191;
    L_0x010b:
        r0 = r24;
        r25 = r0.getJSONObject(r5);	 Catch:{ JSONException -> 0x0239 }
        r26 = "id";
        r25 = r25.getInt(r26);	 Catch:{ JSONException -> 0x0239 }
        r0 = r24;
        r26 = r0.getJSONObject(r5);	 Catch:{ JSONException -> 0x0239 }
        r27 = "feedback_message_id";
        r26 = r26.getInt(r27);	 Catch:{ JSONException -> 0x0239 }
        r0 = r24;
        r27 = r0.getJSONObject(r5);	 Catch:{ JSONException -> 0x0239 }
        r28 = "file_name";
        r27 = r27.getString(r28);	 Catch:{ JSONException -> 0x0239 }
        r0 = r24;
        r28 = r0.getJSONObject(r5);	 Catch:{ JSONException -> 0x0239 }
        r29 = "url";
        r28 = r28.getString(r29);	 Catch:{ JSONException -> 0x0239 }
        r0 = r24;
        r29 = r0.getJSONObject(r5);	 Catch:{ JSONException -> 0x0239 }
        r30 = "created_at";
        r29 = r29.getString(r30);	 Catch:{ JSONException -> 0x0239 }
        r0 = r24;
        r30 = r0.getJSONObject(r5);	 Catch:{ JSONException -> 0x0239 }
        r31 = "updated_at";
        r30 = r30.getString(r31);	 Catch:{ JSONException -> 0x0239 }
        r31 = new net.hockeyapp.android.c.c;	 Catch:{ JSONException -> 0x0239 }
        r31.<init>();	 Catch:{ JSONException -> 0x0239 }
        r0 = r31;
        r1 = r25;
        r0.m11738a(r1);	 Catch:{ JSONException -> 0x0239 }
        r0 = r31;
        r1 = r26;
        r0.m11741b(r1);	 Catch:{ JSONException -> 0x0239 }
        r0 = r31;
        r1 = r27;
        r0.m11739a(r1);	 Catch:{ JSONException -> 0x0239 }
        r0 = r31;
        r1 = r28;
        r0.m11742b(r1);	 Catch:{ JSONException -> 0x0239 }
        r0 = r31;
        r1 = r29;
        r0.m11744c(r1);	 Catch:{ JSONException -> 0x0239 }
        r0 = r31;
        r1 = r30;
        r0.m11745d(r1);	 Catch:{ JSONException -> 0x0239 }
        r0 = r31;
        r4.add(r0);	 Catch:{ JSONException -> 0x0239 }
        r5 = r5 + 1;
        goto L_0x0103;
    L_0x0191:
        r5 = new net.hockeyapp.android.c.d;	 Catch:{ JSONException -> 0x0239 }
        r5.<init>();	 Catch:{ JSONException -> 0x0239 }
        r0 = r23;
        r5.m11765k(r0);	 Catch:{ JSONException -> 0x0239 }
        r0 = r21;
        r5.m11763i(r0);	 Catch:{ JSONException -> 0x0239 }
        r0 = r16;
        r5.m11760f(r0);	 Catch:{ JSONException -> 0x0239 }
        r0 = r17;
        r5.m11748a(r0);	 Catch:{ JSONException -> 0x0239 }
        r5.m11757d(r14);	 Catch:{ JSONException -> 0x0239 }
        r0 = r22;
        r5.m11764j(r0);	 Catch:{ JSONException -> 0x0239 }
        r5.m11755c(r13);	 Catch:{ JSONException -> 0x0239 }
        r5.m11759e(r15);	 Catch:{ JSONException -> 0x0239 }
        r5.m11749a(r11);	 Catch:{ JSONException -> 0x0239 }
        r5.m11753b(r12);	 Catch:{ JSONException -> 0x0239 }
        r0 = r18;
        r5.m11761g(r0);	 Catch:{ JSONException -> 0x0239 }
        r0 = r20;
        r5.m11762h(r0);	 Catch:{ JSONException -> 0x0239 }
        r0 = r19;
        r5.m11752b(r0);	 Catch:{ JSONException -> 0x0239 }
        r5.m11750a(r4);	 Catch:{ JSONException -> 0x0239 }
        r2.add(r5);	 Catch:{ JSONException -> 0x0239 }
        r4 = r6 + 1;
        r6 = r4;
        goto L_0x002b;
    L_0x01d8:
        r9.m11734a(r2);	 Catch:{ JSONException -> 0x0239 }
        r2 = "name";
        r2 = r8.getString(r2);	 Catch:{ JSONException -> 0x0234 }
        r2 = r2.toString();	 Catch:{ JSONException -> 0x0234 }
        r9.m11733a(r2);	 Catch:{ JSONException -> 0x0234 }
    L_0x01e9:
        r2 = "email";
        r2 = r8.getString(r2);	 Catch:{ JSONException -> 0x0243 }
        r2 = r2.toString();	 Catch:{ JSONException -> 0x0243 }
        r9.m11735b(r2);	 Catch:{ JSONException -> 0x0243 }
    L_0x01f7:
        r2 = "id";
        r2 = r8.getInt(r2);	 Catch:{ JSONException -> 0x0248 }
        r9.m11732a(r2);	 Catch:{ JSONException -> 0x0248 }
    L_0x0201:
        r2 = "created_at";
        r2 = r8.getString(r2);	 Catch:{ JSONException -> 0x024d }
        r2 = r2.toString();	 Catch:{ JSONException -> 0x024d }
        r9.m11736c(r2);	 Catch:{ JSONException -> 0x024d }
    L_0x020f:
        r2 = new net.hockeyapp.android.c.e;	 Catch:{ JSONException -> 0x0239 }
        r2.<init>();	 Catch:{ JSONException -> 0x0239 }
        r2.m11768a(r9);	 Catch:{ JSONException -> 0x0257 }
        r3 = "status";
        r3 = r7.getString(r3);	 Catch:{ JSONException -> 0x0252 }
        r3 = r3.toString();	 Catch:{ JSONException -> 0x0252 }
        r2.m11767a(r3);	 Catch:{ JSONException -> 0x0252 }
    L_0x0225:
        r3 = "token";
        r3 = r7.getString(r3);	 Catch:{ JSONException -> 0x0259 }
        r3 = r3.toString();	 Catch:{ JSONException -> 0x0259 }
        r2.m11770b(r3);	 Catch:{ JSONException -> 0x0259 }
    L_0x0233:
        return r2;
    L_0x0234:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ JSONException -> 0x0239 }
        goto L_0x01e9;
    L_0x0239:
        r2 = move-exception;
        r32 = r2;
        r2 = r3;
        r3 = r32;
    L_0x023f:
        r3.printStackTrace();
        goto L_0x0233;
    L_0x0243:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ JSONException -> 0x0239 }
        goto L_0x01f7;
    L_0x0248:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ JSONException -> 0x0239 }
        goto L_0x0201;
    L_0x024d:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ JSONException -> 0x0239 }
        goto L_0x020f;
    L_0x0252:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ JSONException -> 0x0257 }
        goto L_0x0225;
    L_0x0257:
        r3 = move-exception;
        goto L_0x023f;
    L_0x0259:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ JSONException -> 0x0257 }
        goto L_0x0233;
    L_0x025e:
        r2 = r3;
        goto L_0x0233;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.hockeyapp.android.e.c.a(java.lang.String):net.hockeyapp.android.c.e");
    }
}
