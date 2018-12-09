package com.google.p098a.p100b;

import java.math.BigDecimal;

/* renamed from: com.google.a.b.f */
public final class C1728f extends Number {
    /* renamed from: a */
    private final String f5301a;

    public C1728f(String str) {
        this.f5301a = str;
    }

    public double doubleValue() {
        return Double.parseDouble(this.f5301a);
    }

    public float floatValue() {
        return Float.parseFloat(this.f5301a);
    }

    public int intValue() {
        try {
            return Integer.parseInt(this.f5301a);
        } catch (NumberFormatException e) {
            try {
                return (int) Long.parseLong(this.f5301a);
            } catch (NumberFormatException e2) {
                return new BigDecimal(this.f5301a).intValue();
            }
        }
    }

    public long longValue() {
        try {
            return Long.parseLong(this.f5301a);
        } catch (NumberFormatException e) {
            return new BigDecimal(this.f5301a).longValue();
        }
    }

    public String toString() {
        return this.f5301a;
    }
}
