package com.google.android.gms.flags;

public final class Singletons {
    private static Singletons zzach;
    private final FlagRegistry zzaci = new FlagRegistry();
    private final FlagValueProvider zzacj = new FlagValueProvider();

    static {
        setInstance(new Singletons());
    }

    private Singletons() {
    }

    public static FlagRegistry flagRegistry() {
        return zzdm().zzaci;
    }

    public static FlagValueProvider flagValueProvider() {
        return zzdm().zzacj;
    }

    protected static void setInstance(Singletons singletons) {
        synchronized (Singletons.class) {
            zzach = singletons;
        }
    }

    private static Singletons zzdm() {
        Singletons singletons;
        synchronized (Singletons.class) {
            singletons = zzach;
        }
        return singletons;
    }
}
