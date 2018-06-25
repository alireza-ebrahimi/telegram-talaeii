package com.persianswitch.sdk.payment.model;

public enum CVV2Status {
    CVV2_REQUIRED(2),
    CVV2_NOT_REQUIRED_STATUS(1);
    
    /* renamed from: c */
    private final int f7471c;

    private CVV2Status(int i) {
        this.f7471c = i;
    }

    /* renamed from: a */
    public static CVV2Status m11120a(int i) {
        for (CVV2Status cVV2Status : values()) {
            if (cVV2Status.f7471c == i) {
                return cVV2Status;
            }
        }
        return CVV2_REQUIRED;
    }

    /* renamed from: a */
    public int m11121a() {
        return this.f7471c;
    }
}
