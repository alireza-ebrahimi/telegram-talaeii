package com.crashlytics.android.p066c;

/* renamed from: com.crashlytics.android.c.y */
class C1464y implements ai {
    /* renamed from: a */
    private final int f4414a;

    public C1464y(int i) {
        this.f4414a = i;
    }

    /* renamed from: a */
    public StackTraceElement[] mo1152a(StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr.length <= this.f4414a) {
            return stackTraceElementArr;
        }
        int i = this.f4414a / 2;
        int i2 = this.f4414a - i;
        Object obj = new StackTraceElement[this.f4414a];
        System.arraycopy(stackTraceElementArr, 0, obj, 0, i2);
        System.arraycopy(stackTraceElementArr, stackTraceElementArr.length - i, obj, i2, i);
        return obj;
    }
}
