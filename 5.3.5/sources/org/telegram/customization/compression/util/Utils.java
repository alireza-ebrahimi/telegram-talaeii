package org.telegram.customization.compression.util;

import java.nio.ByteOrder;

public enum Utils {
    ;
    
    public static final ByteOrder NATIVE_BYTE_ORDER = null;
    private static final boolean unalignedAccessAllowed = false;

    static {
        boolean z;
        NATIVE_BYTE_ORDER = ByteOrder.nativeOrder();
        String arch = System.getProperty("os.arch");
        if (arch.equals("i386") || arch.equals("x86") || arch.equals("amd64") || arch.equals("x86_64")) {
            z = true;
        }
        unalignedAccessAllowed = z;
    }

    public static boolean isUnalignedAccessAllowed() {
        return unalignedAccessAllowed;
    }
}
