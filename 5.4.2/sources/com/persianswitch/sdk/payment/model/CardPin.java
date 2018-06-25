package com.persianswitch.sdk.payment.model;

public class CardPin {
    /* renamed from: a */
    private final boolean f7472a;
    /* renamed from: b */
    private String f7473b;
    /* renamed from: c */
    private String f7474c;
    /* renamed from: d */
    private String f7475d;
    /* renamed from: e */
    private String f7476e;

    public CardPin(String str, String str2, String str3, String str4, boolean z) {
        this.f7473b = str;
        this.f7474c = str2;
        this.f7475d = str3;
        this.f7476e = str4;
        this.f7472a = z;
    }

    public String toString() {
        return this.f7473b + ";" + this.f7474c + ";" + (this.f7472a ? this.f7476e + this.f7475d : "0000") + ",1";
    }
}
