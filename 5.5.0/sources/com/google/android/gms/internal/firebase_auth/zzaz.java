package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.common.internal.Preconditions;

public final class zzaz {
    private String zzaf;
    private String zzag;
    private String zzah;
    private String zzbh;
    private String zzbi;
    private String zzbr;
    private boolean zzbt = true;
    private zzbd zzke = new zzbd();
    private zzbd zzkf = new zzbd();

    public final String getDisplayName() {
        return this.zzbh;
    }

    public final String getEmail() {
        return this.zzah;
    }

    public final String getPassword() {
        return this.zzbi;
    }

    public final zzaz zzaa(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzke.zzbc().add(str);
        return this;
    }

    public final zzaz zzab(String str) {
        this.zzag = Preconditions.checkNotEmpty(str);
        return this;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ com.google.android.gms.internal.firebase_auth.zzgt zzao() {
        /*
        r10 = this;
        r4 = 2;
        r3 = 1;
        r2 = 0;
        r6 = new com.google.android.gms.internal.firebase_auth.zzo;
        r6.<init>();
        r0 = r10.zzaf;
        r6.zzaf = r0;
        r0 = r10.zzah;
        r6.zzah = r0;
        r0 = r10.zzbi;
        r6.zzbi = r0;
        r0 = r10.zzbh;
        r6.zzbh = r0;
        r0 = r10.zzbr;
        r6.zzbr = r0;
        r0 = r10.zzke;
        r0 = r0.zzbc();
        r1 = r10.zzke;
        r1 = r1.zzbc();
        r1 = r1.size();
        r1 = new java.lang.String[r1];
        r0 = r0.toArray(r1);
        r0 = (java.lang.String[]) r0;
        r6.zzbu = r0;
        r0 = r10.zzkf;
        r7 = r0.zzbc();
        r0 = r7.size();
        r8 = new int[r0];
        r1 = r2;
    L_0x0043:
        r0 = r7.size();
        if (r1 >= r0) goto L_0x0097;
    L_0x0049:
        r0 = r7.get(r1);
        r0 = (java.lang.String) r0;
        r5 = -1;
        r9 = r0.hashCode();
        switch(r9) {
            case -333046776: goto L_0x006e;
            case 66081660: goto L_0x0063;
            case 1939891618: goto L_0x0084;
            case 1999612571: goto L_0x0079;
            default: goto L_0x0057;
        };
    L_0x0057:
        r0 = r5;
    L_0x0058:
        switch(r0) {
            case 0: goto L_0x008f;
            case 1: goto L_0x0091;
            case 2: goto L_0x0093;
            case 3: goto L_0x0095;
            default: goto L_0x005b;
        };
    L_0x005b:
        r0 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
    L_0x005d:
        r8[r1] = r0;
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x0043;
    L_0x0063:
        r9 = "EMAIL";
        r0 = r0.equals(r9);
        if (r0 == 0) goto L_0x0057;
    L_0x006c:
        r0 = r2;
        goto L_0x0058;
    L_0x006e:
        r9 = "DISPLAY_NAME";
        r0 = r0.equals(r9);
        if (r0 == 0) goto L_0x0057;
    L_0x0077:
        r0 = r3;
        goto L_0x0058;
    L_0x0079:
        r9 = "PASSWORD";
        r0 = r0.equals(r9);
        if (r0 == 0) goto L_0x0057;
    L_0x0082:
        r0 = r4;
        goto L_0x0058;
    L_0x0084:
        r9 = "PHOTO_URL";
        r0 = r0.equals(r9);
        if (r0 == 0) goto L_0x0057;
    L_0x008d:
        r0 = 3;
        goto L_0x0058;
    L_0x008f:
        r0 = r3;
        goto L_0x005d;
    L_0x0091:
        r0 = r4;
        goto L_0x005d;
    L_0x0093:
        r0 = 5;
        goto L_0x005d;
    L_0x0095:
        r0 = 4;
        goto L_0x005d;
    L_0x0097:
        r6.zzbs = r8;
        r0 = r10.zzbt;
        r6.zzbt = r0;
        r0 = r10.zzag;
        r6.zzag = r0;
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzaz.zzao():com.google.android.gms.internal.firebase_auth.zzgt");
    }

    public final String zzt() {
        return this.zzbr;
    }

    public final boolean zzu(String str) {
        Preconditions.checkNotEmpty(str);
        return this.zzkf.zzbc().contains(str);
    }

    public final zzaz zzv(String str) {
        this.zzaf = Preconditions.checkNotEmpty(str);
        return this;
    }

    public final zzaz zzw(String str) {
        if (str == null) {
            this.zzkf.zzbc().add("EMAIL");
        } else {
            this.zzah = str;
        }
        return this;
    }

    public final zzaz zzx(String str) {
        if (str == null) {
            this.zzkf.zzbc().add("PASSWORD");
        } else {
            this.zzbi = str;
        }
        return this;
    }

    public final zzaz zzy(String str) {
        if (str == null) {
            this.zzkf.zzbc().add("DISPLAY_NAME");
        } else {
            this.zzbh = str;
        }
        return this;
    }

    public final zzaz zzz(String str) {
        if (str == null) {
            this.zzkf.zzbc().add("PHOTO_URL");
        } else {
            this.zzbr = str;
        }
        return this;
    }
}
