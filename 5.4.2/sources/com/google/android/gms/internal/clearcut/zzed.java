package com.google.android.gms.internal.clearcut;

import java.lang.reflect.Field;
import java.util.Arrays;

final class zzed {
    private final int flags;
    private final Object[] zzmj;
    private final int zzmk;
    private final int zzml;
    private final int zzmm;
    private final int[] zzms;
    private final zzee zznh;
    private Class<?> zzni;
    private final int zznj;
    private final int zznk;
    private final int zznl;
    private final int zznm;
    private final int zznn;
    private final int zzno;
    private int zznp;
    private int zznq;
    private int zznr = Integer.MAX_VALUE;
    private int zzns = Integer.MIN_VALUE;
    private int zznt = 0;
    private int zznu = 0;
    private int zznv = 0;
    private int zznw = 0;
    private int zznx = 0;
    private int zzny;
    private int zznz;
    private int zzoa;
    private int zzob;
    private int zzoc;
    private Field zzod;
    private Object zzoe;
    private Object zzof;
    private Object zzog;

    zzed(Class<?> cls, String str, Object[] objArr) {
        int[] iArr = null;
        this.zzni = cls;
        this.zznh = new zzee(str);
        this.zzmj = objArr;
        this.flags = this.zznh.next();
        this.zznj = this.zznh.next();
        if (this.zznj == 0) {
            this.zznk = 0;
            this.zznl = 0;
            this.zzmk = 0;
            this.zzml = 0;
            this.zznm = 0;
            this.zznn = 0;
            this.zzmm = 0;
            this.zzno = 0;
            this.zzms = null;
            return;
        }
        this.zznk = this.zznh.next();
        this.zznl = this.zznh.next();
        this.zzmk = this.zznh.next();
        this.zzml = this.zznh.next();
        this.zznn = this.zznh.next();
        this.zzmm = this.zznh.next();
        this.zznm = this.zznh.next();
        this.zzno = this.zznh.next();
        int next = this.zznh.next();
        if (next != 0) {
            iArr = new int[next];
        }
        this.zzms = iArr;
        this.zznp = (this.zznk << 1) + this.zznl;
    }

    private static Field zza(Class<?> cls, String str) {
        Field declaredField;
        try {
            declaredField = cls.getDeclaredField(str);
        } catch (NoSuchFieldException e) {
            Field[] declaredFields = cls.getDeclaredFields();
            int length = declaredFields.length;
            int i = 0;
            while (i < length) {
                declaredField = declaredFields[i];
                if (!str.equals(declaredField.getName())) {
                    i++;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            throw new RuntimeException(new StringBuilder(((String.valueOf(str).length() + 40) + String.valueOf(name).length()) + String.valueOf(arrays).length()).append("Field ").append(str).append(" for ").append(name).append(" not found. Known fields are ").append(arrays).toString());
        }
        return declaredField;
    }

    private final Object zzcw() {
        Object[] objArr = this.zzmj;
        int i = this.zznp;
        this.zznp = i + 1;
        return objArr[i];
    }

    private final boolean zzcz() {
        return (this.flags & 1) == 1;
    }

    final boolean next() {
        Object obj = null;
        if (!this.zznh.hasNext()) {
            return false;
        }
        this.zzny = this.zznh.next();
        this.zznz = this.zznh.next();
        this.zzoa = this.zznz & 255;
        if (this.zzny < this.zznr) {
            this.zznr = this.zzny;
        }
        if (this.zzny > this.zzns) {
            this.zzns = this.zzny;
        }
        if (this.zzoa == zzcb.MAP.id()) {
            this.zznt++;
        } else if (this.zzoa >= zzcb.DOUBLE_LIST.id() && this.zzoa <= zzcb.GROUP_LIST.id()) {
            this.zznu++;
        }
        this.zznx++;
        if (zzeh.zzc(this.zznr, this.zzny, this.zznx)) {
            this.zznw = this.zzny + 1;
            this.zznv = this.zznw - this.zznr;
        } else {
            this.zznv++;
        }
        if (((this.zznz & 1024) != 0 ? 1 : null) != null) {
            int[] iArr = this.zzms;
            int i = this.zznq;
            this.zznq = i + 1;
            iArr[i] = this.zzny;
        }
        this.zzoe = null;
        this.zzof = null;
        this.zzog = null;
        if (zzda()) {
            this.zzob = this.zznh.next();
            if (this.zzoa == zzcb.MESSAGE.id() + 51 || this.zzoa == zzcb.GROUP.id() + 51) {
                this.zzoe = zzcw();
            } else if (this.zzoa == zzcb.ENUM.id() + 51 && zzcz()) {
                this.zzof = zzcw();
            }
        } else {
            this.zzod = zza(this.zzni, (String) zzcw());
            if (zzde()) {
                this.zzoc = this.zznh.next();
            }
            if (this.zzoa == zzcb.MESSAGE.id() || this.zzoa == zzcb.GROUP.id()) {
                this.zzoe = this.zzod.getType();
            } else if (this.zzoa == zzcb.MESSAGE_LIST.id() || this.zzoa == zzcb.GROUP_LIST.id()) {
                this.zzoe = zzcw();
            } else if (this.zzoa == zzcb.ENUM.id() || this.zzoa == zzcb.ENUM_LIST.id() || this.zzoa == zzcb.ENUM_LIST_PACKED.id()) {
                if (zzcz()) {
                    this.zzof = zzcw();
                }
            } else if (this.zzoa == zzcb.MAP.id()) {
                this.zzog = zzcw();
                if ((this.zznz & 2048) != 0) {
                    obj = 1;
                }
                if (obj != null) {
                    this.zzof = zzcw();
                }
            }
        }
        return true;
    }

    final int zzcx() {
        return this.zzny;
    }

    final int zzcy() {
        return this.zzoa;
    }

    final boolean zzda() {
        return this.zzoa > zzcb.MAP.id();
    }

    final Field zzdb() {
        int i = this.zzob << 1;
        Object obj = this.zzmj[i];
        if (obj instanceof Field) {
            return (Field) obj;
        }
        Field zza = zza(this.zzni, (String) obj);
        this.zzmj[i] = zza;
        return zza;
    }

    final Field zzdc() {
        int i = (this.zzob << 1) + 1;
        Object obj = this.zzmj[i];
        if (obj instanceof Field) {
            return (Field) obj;
        }
        Field zza = zza(this.zzni, (String) obj);
        this.zzmj[i] = zza;
        return zza;
    }

    final Field zzdd() {
        return this.zzod;
    }

    final boolean zzde() {
        return zzcz() && this.zzoa <= zzcb.GROUP.id();
    }

    final Field zzdf() {
        int i = (this.zzoc / 32) + (this.zznk << 1);
        Object obj = this.zzmj[i];
        if (obj instanceof Field) {
            return (Field) obj;
        }
        Field zza = zza(this.zzni, (String) obj);
        this.zzmj[i] = zza;
        return zza;
    }

    final int zzdg() {
        return this.zzoc % 32;
    }

    final boolean zzdh() {
        return (this.zznz & 256) != 0;
    }

    final boolean zzdi() {
        return (this.zznz & 512) != 0;
    }

    final Object zzdj() {
        return this.zzoe;
    }

    final Object zzdk() {
        return this.zzof;
    }

    final Object zzdl() {
        return this.zzog;
    }
}
