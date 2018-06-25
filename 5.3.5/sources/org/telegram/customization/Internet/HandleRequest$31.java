package org.telegram.customization.Internet;

import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import java.lang.reflect.Type;
import org.telegram.customization.Model.FilterResponse;
import utils.view.Constants;

class HandleRequest$31 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ String val$channelId;
    final /* synthetic */ long val$userId;

    HandleRequest$31(HandleRequest this$0, long j, String str) {
        this.this$0 = this$0;
        this.val$userId = j;
        this.val$channelId = str;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        HandleRequest.access$300(this.this$0).onResult(object.getItems(), 19);
    }

    public void onErrorResponse(VolleyError volleyError) {
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -19);
    }

    public Request getRequest() {
        return new BaseStringRequest(0, String.format(WebservicePropertis.WS_GET_SINGLE_CHECK_FILTER, new Object[]{Long.valueOf(this.val$userId), this.val$channelId}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey());
    }

    public boolean ignoreParsingResponse() {
        return false;
    }

    public Type getClassType() {
        return FilterResponse.class;
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_FILTER_CHANNEL_API;
    }

    public String toString() {
        return "WS_GET_SINGLE_FILTER";
    }
}
