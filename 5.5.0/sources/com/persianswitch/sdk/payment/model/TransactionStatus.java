package com.persianswitch.sdk.payment.model;

import com.persianswitch.sdk.base.webservice.WebService.WSStatus;
import com.persianswitch.sdk.base.webservice.data.WSResponse;

public enum TransactionStatus {
    SUCCESS,
    FAIL,
    UNKNOWN;

    /* renamed from: a */
    public static boolean m11229a(WSStatus wSStatus, WSResponse wSResponse) {
        return wSStatus.m10894a() || wSResponse == null || wSResponse.m10942c().m10852b();
    }
}
