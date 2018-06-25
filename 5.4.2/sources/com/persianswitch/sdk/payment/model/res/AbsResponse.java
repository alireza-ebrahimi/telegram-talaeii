package com.persianswitch.sdk.payment.model.res;

import android.content.Context;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import org.json.JSONObject;

abstract class AbsResponse {
    AbsResponse(Context context, WSResponse wSResponse) {
        mo3305a(context, wSResponse.m10946g());
    }

    /* renamed from: a */
    abstract void mo3305a(Context context, JSONObject jSONObject);
}
