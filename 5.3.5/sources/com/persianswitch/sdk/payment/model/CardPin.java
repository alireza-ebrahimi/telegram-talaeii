package com.persianswitch.sdk.payment.model;

public class CardPin {
    private String mCvv2;
    private String mExpirationDateMonth;
    private String mExpirationDateYear;
    private final boolean mIsExpirationEditedByUser;
    private String mPin2;

    public CardPin(String pin2, String cvv2, String expirationDateMonth, String expirationDateYear, boolean isExpirationEditedByUser) {
        this.mPin2 = pin2;
        this.mCvv2 = cvv2;
        this.mExpirationDateMonth = expirationDateMonth;
        this.mExpirationDateYear = expirationDateYear;
        this.mIsExpirationEditedByUser = isExpirationEditedByUser;
    }

    public String toString() {
        return this.mPin2 + ";" + this.mCvv2 + ";" + (this.mIsExpirationEditedByUser ? this.mExpirationDateYear + this.mExpirationDateMonth : "0000") + ",1";
    }
}
