package com.persianswitch.sdk.payment.model.res;

import android.content.Context;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import org.json.JSONObject;

abstract class AbsResponse {
    abstract void onConsumeExtraData(Context context, JSONObject jSONObject) throws JsonParseException;

    AbsResponse(Context context, WSResponse response) throws JsonParseException {
        onConsumeExtraData(context, response.getExtraData());
    }
}
