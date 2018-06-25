package com.squareup.picasso;

public enum MemoryPolicy {
    NO_CACHE(1),
    NO_STORE(2);
    
    final int index;

    static boolean shouldReadFromMemoryCache(int memoryPolicy) {
        return (NO_CACHE.index & memoryPolicy) == 0;
    }

    static boolean shouldWriteToMemoryCache(int memoryPolicy) {
        return (NO_STORE.index & memoryPolicy) == 0;
    }

    private MemoryPolicy(int index) {
        this.index = index;
    }
}
