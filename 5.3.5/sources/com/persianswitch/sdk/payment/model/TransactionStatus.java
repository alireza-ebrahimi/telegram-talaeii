package com.persianswitch.sdk.payment.model;

import android.support.annotation.Nullable;
import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;

public enum TransactionStatus {
    SUCCESS,
    FAIL,
    UNKNOWN;

    public static boolean isUnknown(WSStatus error, @Nullable WSResponse wsResponse) {
        return error.isUnknown() || wsResponse == null || wsResponse.getStatus().isUnknown();
    }
}
