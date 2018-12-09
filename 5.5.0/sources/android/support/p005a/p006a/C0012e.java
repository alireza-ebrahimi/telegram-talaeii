package android.support.p005a.p006a;

import android.content.res.TypedArray;
import org.xmlpull.v1.XmlPullParser;

/* renamed from: android.support.a.a.e */
class C0012e {
    /* renamed from: a */
    public static float m21a(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, float f) {
        return !C0012e.m24a(xmlPullParser, str) ? f : typedArray.getFloat(i, f);
    }

    /* renamed from: a */
    public static int m22a(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, int i2) {
        return !C0012e.m24a(xmlPullParser, str) ? i2 : typedArray.getInt(i, i2);
    }

    /* renamed from: a */
    public static boolean m23a(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, boolean z) {
        return !C0012e.m24a(xmlPullParser, str) ? z : typedArray.getBoolean(i, z);
    }

    /* renamed from: a */
    public static boolean m24a(XmlPullParser xmlPullParser, String str) {
        return xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", str) != null;
    }

    /* renamed from: b */
    public static int m25b(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, int i2) {
        return !C0012e.m24a(xmlPullParser, str) ? i2 : typedArray.getColor(i, i2);
    }
}
