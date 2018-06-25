package com.crashlytics.android;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import p033b.p034a.p035a.p036a.C1230c;
import p033b.p034a.p035a.p036a.p037a.p039b.C1120o;

public class CrashlyticsInitProvider extends ContentProvider {

    /* renamed from: com.crashlytics.android.CrashlyticsInitProvider$a */
    interface C1325a {
        /* renamed from: a */
        boolean mo1148a(Context context);
    }

    /* renamed from: a */
    boolean m6782a(Context context, C1120o c1120o, C1325a c1325a) {
        return c1120o.m6051b(context) && c1325a.mo1148a(context);
    }

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        Context context = getContext();
        if (m6782a(context, new C1120o(), new C1377b())) {
            try {
                C1230c.m6404a(context, new C1364a());
                C1230c.m6414h().mo1066c("CrashlyticsInitProvider", "CrashlyticsInitProvider initialization successful");
            } catch (IllegalStateException e) {
                C1230c.m6414h().mo1066c("CrashlyticsInitProvider", "CrashlyticsInitProvider initialization unsuccessful");
                return false;
            }
        }
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
