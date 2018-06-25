package net.hockeyapp.android.utils;

public class FeedbackParser {

    private static class FeedbackParserHolder {
        public static final FeedbackParser INSTANCE = new FeedbackParser();

        private FeedbackParserHolder() {
        }
    }

    private FeedbackParser() {
    }

    public static FeedbackParser getInstance() {
        return FeedbackParserHolder.INSTANCE;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public net.hockeyapp.android.objects.FeedbackResponse parseFeedbackResponse(java.lang.String r39) {
        /*
        r38 = this;
        r16 = 0;
        r10 = 0;
        if (r39 == 0) goto L_0x02a0;
    L_0x0005:
        r23 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x02ca }
        r0 = r23;
        r1 = r39;
        r0.<init>(r1);	 Catch:{ JSONException -> 0x02ca }
        r36 = "feedback";
        r0 = r23;
        r1 = r36;
        r15 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02ca }
        r11 = new net.hockeyapp.android.objects.Feedback;	 Catch:{ JSONException -> 0x02ca }
        r11.<init>();	 Catch:{ JSONException -> 0x02ca }
        r36 = "messages";
        r0 = r36;
        r25 = r15.getJSONArray(r0);	 Catch:{ JSONException -> 0x02a6 }
        r24 = 0;
        r14 = 0;
        r36 = r25.length();	 Catch:{ JSONException -> 0x02a6 }
        if (r36 <= 0) goto L_0x021e;
    L_0x0030:
        r24 = new java.util.ArrayList;	 Catch:{ JSONException -> 0x02a6 }
        r24.<init>();	 Catch:{ JSONException -> 0x02a6 }
        r19 = 0;
    L_0x0037:
        r36 = r25.length();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r19;
        r1 = r36;
        if (r0 >= r1) goto L_0x021e;
    L_0x0041:
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "subject";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r30 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "text";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r31 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "oem";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r28 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "model";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r26 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "os_version";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r29 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "created_at";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r8 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "id";
        r20 = r36.getInt(r37);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "token";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r32 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "via";
        r35 = r36.getInt(r37);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "user_string";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r34 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "clean_text";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r7 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "name";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r27 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "app_id";
        r36 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r2 = r36.toString();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r25;
        r1 = r19;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "attachments";
        r22 = r36.optJSONArray(r37);	 Catch:{ JSONException -> 0x02a6 }
        r13 = java.util.Collections.emptyList();	 Catch:{ JSONException -> 0x02a6 }
        if (r22 == 0) goto L_0x01d2;
    L_0x0145:
        r13 = new java.util.ArrayList;	 Catch:{ JSONException -> 0x02a6 }
        r13.<init>();	 Catch:{ JSONException -> 0x02a6 }
        r21 = 0;
    L_0x014c:
        r36 = r22.length();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r21;
        r1 = r36;
        if (r0 >= r1) goto L_0x01d2;
    L_0x0156:
        r0 = r22;
        r1 = r21;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "id";
        r4 = r36.getInt(r37);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r22;
        r1 = r21;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "feedback_message_id";
        r5 = r36.getInt(r37);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r22;
        r1 = r21;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "file_name";
        r18 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r22;
        r1 = r21;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "url";
        r33 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r22;
        r1 = r21;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "created_at";
        r3 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r22;
        r1 = r21;
        r36 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x02a6 }
        r37 = "updated_at";
        r6 = r36.getString(r37);	 Catch:{ JSONException -> 0x02a6 }
        r12 = new net.hockeyapp.android.objects.FeedbackAttachment;	 Catch:{ JSONException -> 0x02a6 }
        r12.<init>();	 Catch:{ JSONException -> 0x02a6 }
        r12.setId(r4);	 Catch:{ JSONException -> 0x02a6 }
        r12.setMessageId(r5);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r18;
        r12.setFilename(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r33;
        r12.setUrl(r0);	 Catch:{ JSONException -> 0x02a6 }
        r12.setCreatedAt(r3);	 Catch:{ JSONException -> 0x02a6 }
        r12.setUpdatedAt(r6);	 Catch:{ JSONException -> 0x02a6 }
        r13.add(r12);	 Catch:{ JSONException -> 0x02a6 }
        r21 = r21 + 1;
        goto L_0x014c;
    L_0x01d2:
        r14 = new net.hockeyapp.android.objects.FeedbackMessage;	 Catch:{ JSONException -> 0x02a6 }
        r14.<init>();	 Catch:{ JSONException -> 0x02a6 }
        r14.setAppId(r2);	 Catch:{ JSONException -> 0x02a6 }
        r14.setCleanText(r7);	 Catch:{ JSONException -> 0x02a6 }
        r14.setCreatedAt(r8);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r20;
        r14.setId(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r26;
        r14.setModel(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r27;
        r14.setName(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r28;
        r14.setOem(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r29;
        r14.setOsVersion(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r30;
        r14.setSubjec(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r31;
        r14.setText(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r32;
        r14.setToken(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r34;
        r14.setUserString(r0);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r35;
        r14.setVia(r0);	 Catch:{ JSONException -> 0x02a6 }
        r14.setFeedbackAttachments(r13);	 Catch:{ JSONException -> 0x02a6 }
        r0 = r24;
        r0.add(r14);	 Catch:{ JSONException -> 0x02a6 }
        r19 = r19 + 1;
        goto L_0x0037;
    L_0x021e:
        r0 = r24;
        r11.setMessages(r0);	 Catch:{ JSONException -> 0x02a6 }
        r36 = "name";
        r0 = r36;
        r36 = r15.getString(r0);	 Catch:{ JSONException -> 0x02a1 }
        r36 = r36.toString();	 Catch:{ JSONException -> 0x02a1 }
        r0 = r36;
        r11.setName(r0);	 Catch:{ JSONException -> 0x02a1 }
    L_0x0235:
        r36 = "email";
        r0 = r36;
        r36 = r15.getString(r0);	 Catch:{ JSONException -> 0x02ac }
        r36 = r36.toString();	 Catch:{ JSONException -> 0x02ac }
        r0 = r36;
        r11.setEmail(r0);	 Catch:{ JSONException -> 0x02ac }
    L_0x0247:
        r36 = "id";
        r0 = r36;
        r36 = r15.getInt(r0);	 Catch:{ JSONException -> 0x02b1 }
        r0 = r36;
        r11.setId(r0);	 Catch:{ JSONException -> 0x02b1 }
    L_0x0255:
        r36 = "created_at";
        r0 = r36;
        r36 = r15.getString(r0);	 Catch:{ JSONException -> 0x02b6 }
        r36 = r36.toString();	 Catch:{ JSONException -> 0x02b6 }
        r0 = r36;
        r11.setCreatedAt(r0);	 Catch:{ JSONException -> 0x02b6 }
    L_0x0267:
        r17 = new net.hockeyapp.android.objects.FeedbackResponse;	 Catch:{ JSONException -> 0x02a6 }
        r17.<init>();	 Catch:{ JSONException -> 0x02a6 }
        r0 = r17;
        r0.setFeedback(r11);	 Catch:{ JSONException -> 0x02c0 }
        r36 = "status";
        r0 = r23;
        r1 = r36;
        r36 = r0.getString(r1);	 Catch:{ JSONException -> 0x02bb }
        r36 = r36.toString();	 Catch:{ JSONException -> 0x02bb }
        r0 = r17;
        r1 = r36;
        r0.setStatus(r1);	 Catch:{ JSONException -> 0x02bb }
    L_0x0287:
        r36 = "token";
        r0 = r23;
        r1 = r36;
        r36 = r0.getString(r1);	 Catch:{ JSONException -> 0x02c5 }
        r36 = r36.toString();	 Catch:{ JSONException -> 0x02c5 }
        r0 = r17;
        r1 = r36;
        r0.setToken(r1);	 Catch:{ JSONException -> 0x02c5 }
    L_0x029d:
        r10 = r11;
        r16 = r17;
    L_0x02a0:
        return r16;
    L_0x02a1:
        r9 = move-exception;
        r9.printStackTrace();	 Catch:{ JSONException -> 0x02a6 }
        goto L_0x0235;
    L_0x02a6:
        r9 = move-exception;
        r10 = r11;
    L_0x02a8:
        r9.printStackTrace();
        goto L_0x02a0;
    L_0x02ac:
        r9 = move-exception;
        r9.printStackTrace();	 Catch:{ JSONException -> 0x02a6 }
        goto L_0x0247;
    L_0x02b1:
        r9 = move-exception;
        r9.printStackTrace();	 Catch:{ JSONException -> 0x02a6 }
        goto L_0x0255;
    L_0x02b6:
        r9 = move-exception;
        r9.printStackTrace();	 Catch:{ JSONException -> 0x02a6 }
        goto L_0x0267;
    L_0x02bb:
        r9 = move-exception;
        r9.printStackTrace();	 Catch:{ JSONException -> 0x02c0 }
        goto L_0x0287;
    L_0x02c0:
        r9 = move-exception;
        r10 = r11;
        r16 = r17;
        goto L_0x02a8;
    L_0x02c5:
        r9 = move-exception;
        r9.printStackTrace();	 Catch:{ JSONException -> 0x02c0 }
        goto L_0x029d;
    L_0x02ca:
        r9 = move-exception;
        goto L_0x02a8;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.hockeyapp.android.utils.FeedbackParser.parseFeedbackResponse(java.lang.String):net.hockeyapp.android.objects.FeedbackResponse");
    }
}
