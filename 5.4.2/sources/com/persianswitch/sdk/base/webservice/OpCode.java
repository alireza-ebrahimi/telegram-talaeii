package com.persianswitch.sdk.base.webservice;

import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

public enum OpCode {
    UNDEFINED_OP_CODE(-1),
    SEND_ACTIVATION_CODE(101),
    REGISTER_APPLICATION(102),
    RE_VERIFICATION_APPLICATION(113),
    PAY_TRANSACTION(209, false, true),
    GET_TRUST_CODE(125, true),
    INQUIRY_MERCHANT(303, true),
    INQUIRY_TRANSACTION(Callback.DEFAULT_SWIPE_ANIMATION_DURATION),
    SYNC_CARDS_BY_SERVER(251, true);
    
    /* renamed from: j */
    private final int f7158j;
    /* renamed from: k */
    private final boolean f7159k;
    /* renamed from: l */
    private final boolean f7160l;

    private OpCode(int i) {
        this.f7158j = i;
        this.f7159k = false;
        this.f7160l = false;
    }

    private OpCode(int i, boolean z) {
        this.f7158j = i;
        this.f7159k = z;
        this.f7160l = false;
    }

    private OpCode(int i, boolean z, boolean z2) {
        this.f7158j = i;
        this.f7159k = z;
        this.f7160l = z2;
    }

    /* renamed from: a */
    public static OpCode m10838a(int i) {
        for (OpCode opCode : values()) {
            if (opCode.m10839a() == i) {
                return opCode;
            }
        }
        return UNDEFINED_OP_CODE;
    }

    /* renamed from: a */
    public int m10839a() {
        return this.f7158j;
    }

    /* renamed from: b */
    public boolean m10840b() {
        return this.f7159k;
    }

    /* renamed from: c */
    public boolean m10841c() {
        return this.f7160l;
    }
}
