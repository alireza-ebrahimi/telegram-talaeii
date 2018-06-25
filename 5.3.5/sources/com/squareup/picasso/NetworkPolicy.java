package com.squareup.picasso;

public enum NetworkPolicy {
    NO_CACHE(1),
    NO_STORE(2),
    OFFLINE(4);
    
    final int index;

    public static boolean shouldReadFromDiskCache(int networkPolicy) {
        return (NO_CACHE.index & networkPolicy) == 0;
    }

    public static boolean shouldWriteToDiskCache(int networkPolicy) {
        return (NO_STORE.index & networkPolicy) == 0;
    }

    public static boolean isOfflineOnly(int networkPolicy) {
        return (OFFLINE.index & networkPolicy) != 0;
    }

    private NetworkPolicy(int index) {
        this.index = index;
    }
}
