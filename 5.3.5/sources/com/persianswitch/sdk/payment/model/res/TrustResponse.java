package com.persianswitch.sdk.payment.model.res;

import android.content.Context;
import com.persianswitch.sdk.base.utils.strings.Jsonable.JsonParseException;
import com.persianswitch.sdk.base.webservice.data.WSResponse;
import com.thin.downloadmanager.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

public final class TrustResponse extends AbsResponse {
    private String mDescription;
    private String mTrustCode;
    private boolean mUssdAvailable;
    private String mUssdDial;
    private boolean mWebAvailable;
    private String mWebURL;

    public TrustResponse(Context context, WSResponse response) throws JsonParseException {
        super(context, response);
    }

    void onConsumeExtraData(Context context, JSONObject extraData) throws JsonParseException {
        try {
            if (extraData.has("ussd")) {
                this.mUssdDial = extraData.getString("ussd");
            }
            if (extraData.has("code")) {
                this.mTrustCode = extraData.getString("code");
            }
            if (extraData.has("url")) {
                this.mWebURL = extraData.getString("url");
            }
            if (extraData.has("desc")) {
                this.mDescription = extraData.getString("desc");
            }
            if (extraData.has("originality") && extraData.getString("originality").length() >= 2) {
                this.mUssdAvailable = BuildConfig.VERSION_NAME.equals(extraData.getString("originality").substring(0, 1));
                this.mWebAvailable = BuildConfig.VERSION_NAME.equals(extraData.getString("originality").substring(1, 2));
            }
        } catch (JSONException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public String getUssdDial() {
        return this.mUssdDial;
    }

    public String getTrustCode() {
        return this.mTrustCode;
    }

    public String getWebURL() {
        return this.mWebURL;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public boolean isUssdAvailable() {
        return this.mUssdAvailable;
    }

    public boolean isWebAvailable() {
        return this.mWebAvailable;
    }
}
