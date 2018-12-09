package com.google.android.gms.flags;

import android.os.RemoteException;

public abstract class Flag<T> {
    private final T mDefaultValue;
    private final String mKey;
    private final int zzacb;

    public static class BooleanFlag extends Flag<Boolean> {
        public BooleanFlag(int i, String str, Boolean bool) {
            super(i, str, bool);
        }

        public Boolean get(IFlagProvider iFlagProvider) {
            try {
                return Boolean.valueOf(iFlagProvider.getBooleanFlagValue(getKey(), ((Boolean) getDefault()).booleanValue(), getSource()));
            } catch (RemoteException e) {
                return (Boolean) getDefault();
            }
        }
    }

    public static class IntegerFlag extends Flag<Integer> {
        public IntegerFlag(int i, String str, Integer num) {
            super(i, str, num);
        }

        public Integer get(IFlagProvider iFlagProvider) {
            try {
                return Integer.valueOf(iFlagProvider.getIntFlagValue(getKey(), ((Integer) getDefault()).intValue(), getSource()));
            } catch (RemoteException e) {
                return (Integer) getDefault();
            }
        }
    }

    public static class LongFlag extends Flag<Long> {
        public LongFlag(int i, String str, Long l) {
            super(i, str, l);
        }

        public Long get(IFlagProvider iFlagProvider) {
            try {
                return Long.valueOf(iFlagProvider.getLongFlagValue(getKey(), ((Long) getDefault()).longValue(), getSource()));
            } catch (RemoteException e) {
                return (Long) getDefault();
            }
        }
    }

    public static class StringFlag extends Flag<String> {
        public StringFlag(int i, String str, String str2) {
            super(i, str, str2);
        }

        public String get(IFlagProvider iFlagProvider) {
            try {
                return iFlagProvider.getStringFlagValue(getKey(), (String) getDefault(), getSource());
            } catch (RemoteException e) {
                return (String) getDefault();
            }
        }
    }

    private Flag(int i, String str, T t) {
        this.zzacb = i;
        this.mKey = str;
        this.mDefaultValue = t;
        Singletons.flagRegistry().registerFlag(this);
    }

    public static BooleanFlag define(int i, String str, Boolean bool) {
        return new BooleanFlag(i, str, bool);
    }

    public static IntegerFlag define(int i, String str, int i2) {
        return new IntegerFlag(i, str, Integer.valueOf(i2));
    }

    public static LongFlag define(int i, String str, long j) {
        return new LongFlag(i, str, Long.valueOf(j));
    }

    public static StringFlag define(int i, String str, String str2) {
        return new StringFlag(i, str, str2);
    }

    public static StringFlag defineClientExperimentId(int i, String str) {
        StringFlag define = define(i, str, null);
        Singletons.flagRegistry().registerClientExperimentId(define);
        return define;
    }

    public static StringFlag defineServiceExperimentId(int i, String str) {
        StringFlag define = define(i, str, null);
        Singletons.flagRegistry().registerServiceExperimentId(define);
        return define;
    }

    public T get() {
        return Singletons.flagValueProvider().getFlagValue(this);
    }

    protected abstract T get(IFlagProvider iFlagProvider);

    public T getDefault() {
        return this.mDefaultValue;
    }

    public String getKey() {
        return this.mKey;
    }

    public int getSource() {
        return this.zzacb;
    }
}
