package com.google.android.gms.internal.clearcut;

final class zzee {
    private final String info;
    private int position = 0;

    zzee(String str) {
        this.info = str;
    }

    final boolean hasNext() {
        return this.position < this.info.length();
    }

    final int next() {
        String str = this.info;
        int i = this.position;
        this.position = i + 1;
        char charAt = str.charAt(i);
        if (charAt < '?') {
            return charAt;
        }
        i = charAt & 8191;
        int i2 = 13;
        while (true) {
            String str2 = this.info;
            int i3 = this.position;
            this.position = i3 + 1;
            char charAt2 = str2.charAt(i3);
            if (charAt2 < '?') {
                return (charAt2 << i2) | i;
            }
            i |= (charAt2 & 8191) << i2;
            i2 += 13;
        }
    }
}
