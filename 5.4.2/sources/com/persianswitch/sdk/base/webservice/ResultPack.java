package com.persianswitch.sdk.base.webservice;

import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.persianswitch.sdk.payment.model.TransactionStatus;

public final class ResultPack<T extends WSResponse> {
    /* renamed from: a */
    private TransactionStatus f7161a;
    /* renamed from: b */
    private T f7162b;
    /* renamed from: c */
    private String f7163c;
    /* renamed from: d */
    private WSStatus f7164d;

    /* renamed from: a */
    public ResultPack m10842a(WSStatus wSStatus) {
        this.f7164d = wSStatus;
        return this;
    }

    /* renamed from: a */
    public ResultPack m10843a(T t) {
        this.f7162b = t;
        return this;
    }

    /* renamed from: a */
    public ResultPack m10844a(TransactionStatus transactionStatus) {
        this.f7161a = transactionStatus;
        return this;
    }

    /* renamed from: a */
    public ResultPack m10845a(String str) {
        this.f7163c = str;
        return this;
    }

    /* renamed from: a */
    public TransactionStatus m10846a() {
        return this.f7161a;
    }

    /* renamed from: b */
    public T m10847b() {
        return this.f7162b;
    }
}
