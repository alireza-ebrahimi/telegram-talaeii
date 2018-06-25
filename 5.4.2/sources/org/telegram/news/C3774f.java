package org.telegram.news;

import android.content.Intent;
import android.util.Log;

/* renamed from: org.telegram.news.f */
public class C3774f {
    /* renamed from: a */
    public static Intent m13891a(String str, String str2, String str3, boolean z) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.addFlags(524288);
        intent.putExtra("android.intent.extra.SUBJECT", str);
        String str4 = TtmlNode.ANONYMOUS_REGION_ID;
        if (z) {
            str2 = str + "\n" + str2 + "\n" + str3 + "\n\n\nآگهی:\nتگ\nhttp://app.tag.ir/tag.apk";
        } else {
            Log.d("sadegh", "is not manuallll");
        }
        intent.putExtra("android.intent.extra.TEXT", str2);
        return Intent.createChooser(intent, "نرم افزار تگ-انتشار خبر");
    }
}
