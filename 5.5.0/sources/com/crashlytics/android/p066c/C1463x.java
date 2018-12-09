package com.crashlytics.android.p066c;

/* renamed from: com.crashlytics.android.c.x */
class C1463x implements ai {
    /* renamed from: a */
    private final int f4411a;
    /* renamed from: b */
    private final ai[] f4412b;
    /* renamed from: c */
    private final C1464y f4413c;

    public C1463x(int i, ai... aiVarArr) {
        this.f4411a = i;
        this.f4412b = aiVarArr;
        this.f4413c = new C1464y(i);
    }

    /* renamed from: a */
    public StackTraceElement[] mo1152a(StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr.length <= this.f4411a) {
            return stackTraceElementArr;
        }
        ai[] aiVarArr = this.f4412b;
        int length = aiVarArr.length;
        int i = 0;
        StackTraceElement[] stackTraceElementArr2 = stackTraceElementArr;
        while (i < length) {
            ai aiVar = aiVarArr[i];
            if (stackTraceElementArr2.length <= this.f4411a) {
                break;
            }
            i++;
            stackTraceElementArr2 = aiVar.mo1152a(stackTraceElementArr);
        }
        if (stackTraceElementArr2.length > this.f4411a) {
            stackTraceElementArr2 = this.f4413c.mo1152a(stackTraceElementArr2);
        }
        return stackTraceElementArr2;
    }
}
