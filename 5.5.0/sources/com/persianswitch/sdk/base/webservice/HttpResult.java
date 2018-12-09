package com.persianswitch.sdk.base.webservice;

import com.persianswitch.sdk.base.webservice.exception.WSCallException;

public class HttpResult {
    /* renamed from: a */
    private int f7145a;
    /* renamed from: b */
    private WSCallException f7146b;
    /* renamed from: c */
    private String f7147c;

    /* renamed from: a */
    public WSCallException m10826a() {
        return this.f7146b;
    }

    /* renamed from: a */
    public void m10827a(int i) {
        this.f7145a = i;
    }

    /* renamed from: a */
    public void m10828a(WSCallException wSCallException) {
        this.f7146b = wSCallException;
    }

    /* renamed from: a */
    public void m10829a(String str) {
        this.f7147c = str;
    }

    /* renamed from: b */
    public int m10830b() {
        return this.f7145a;
    }

    /* renamed from: c */
    public String m10831c() {
        return this.f7147c;
    }
}
