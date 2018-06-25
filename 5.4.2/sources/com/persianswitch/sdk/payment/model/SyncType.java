package com.persianswitch.sdk.payment.model;

import com.persianswitch.sdk.base.utils.func.Option;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public final class SyncType {
    /* renamed from: a */
    public static final SyncType f7532a = new SyncType(201, 100, 1);
    /* renamed from: b */
    public static final SyncType f7533b = new SyncType(201, 101, 1);
    /* renamed from: c */
    public static final SyncType[] f7534c = new SyncType[]{f7533b, f7532a};
    /* renamed from: d */
    private final int f7535d;
    /* renamed from: e */
    private final int f7536e;
    /* renamed from: f */
    private final int f7537f;

    private SyncType(int i, int i2, int i3) {
        this.f7535d = i;
        this.f7536e = i2;
        this.f7537f = i3;
    }

    /* renamed from: a */
    public static SyncType m11203a(int i, int i2, int i3) {
        return new SyncType(i, i2, i3);
    }

    /* renamed from: a */
    public static SyncType m11204a(String str) {
        String[] split = str.split("\\.", 3);
        return new SyncType(((Integer) Option.m10775a(StringUtils.m10809e(split[0]), Integer.valueOf(0))).intValue(), ((Integer) Option.m10775a(StringUtils.m10809e(split[1]), Integer.valueOf(0))).intValue(), ((Integer) Option.m10775a(StringUtils.m10809e(split[2]), Integer.valueOf(0))).intValue());
    }

    /* renamed from: a */
    public int m11205a() {
        return this.f7535d;
    }

    /* renamed from: b */
    public int m11206b() {
        return this.f7536e;
    }

    /* renamed from: c */
    public int m11207c() {
        return this.f7537f;
    }

    /* renamed from: d */
    public String m11208d() {
        return StringUtils.m10802a(".", Integer.valueOf(this.f7535d), Integer.valueOf(this.f7536e), Integer.valueOf(this.f7537f));
    }
}
