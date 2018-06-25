package com.persianswitch.sdk.payment.model;

public enum CVV2Status {
    CVV2_REQUIRED(2),
    CVV2_NOT_REQUIRED_STATUS(1);
    
    private final int status;

    private CVV2Status(int status) {
        this.status = status;
    }

    public static CVV2Status getInstance(int status) {
        for (CVV2Status cvv2Status : values()) {
            if (cvv2Status.status == status) {
                return cvv2Status;
            }
        }
        return CVV2_REQUIRED;
    }

    public int getStatus() {
        return this.status;
    }
}
