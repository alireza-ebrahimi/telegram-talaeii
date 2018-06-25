package org.telegram.customization.compression.p160a;

import java.nio.ByteOrder;

/* renamed from: org.telegram.customization.compression.a.d */
public enum C2676d {
    ;
    
    /* renamed from: a */
    public static final ByteOrder f8921a = null;
    /* renamed from: b */
    private static final boolean f8922b = false;

    static {
        boolean z;
        f8921a = ByteOrder.nativeOrder();
        String property = System.getProperty("os.arch");
        if (property.equals("i386") || property.equals("x86") || property.equals("amd64") || property.equals("x86_64")) {
            z = true;
        }
        f8922b = z;
    }

    /* renamed from: a */
    public static boolean m12591a() {
        return f8922b;
    }
}
