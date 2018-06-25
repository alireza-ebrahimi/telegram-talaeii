package com.persianswitch.sdk.base.webservice;

public enum OpCode {
    UNDEFINED_OP_CODE(-1),
    SEND_ACTIVATION_CODE(101),
    REGISTER_APPLICATION(102),
    RE_VERIFICATION_APPLICATION(113),
    PAY_TRANSACTION(209, false, true),
    GET_TRUST_CODE(125, true),
    INQUIRY_MERCHANT(303, true),
    INQUIRY_TRANSACTION(250),
    SYNC_CARDS_BY_SERVER(251, true);
    
    private final int code;
    private final boolean isFinancial;
    private final boolean silenceRetry;

    private OpCode(int code) {
        this.code = code;
        this.silenceRetry = false;
        this.isFinancial = false;
    }

    private OpCode(int code, boolean silenceRetry) {
        this.code = code;
        this.silenceRetry = silenceRetry;
        this.isFinancial = false;
    }

    private OpCode(int code, boolean silenceRetry, boolean isFinancial) {
        this.code = code;
        this.silenceRetry = silenceRetry;
        this.isFinancial = isFinancial;
    }

    public static OpCode getByCode(int code) {
        for (OpCode opCode : values()) {
            if (opCode.getCode() == code) {
                return opCode;
            }
        }
        return UNDEFINED_OP_CODE;
    }

    public int getCode() {
        return this.code;
    }

    public boolean isSilenceRetry() {
        return this.silenceRetry;
    }

    public boolean isFinancial() {
        return this.isFinancial;
    }
}
