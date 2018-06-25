package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.customization.Model.Payment.User;
import utils.view.Constants;

class HandleRequest$41 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ User val$user;

    HandleRequest$41(HandleRequest this$0, User user) {
        this.this$0 = this$0;
        this.val$user = user;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        HandleRequest.access$300(this.this$0).onResult(object, 28);
    }

    public void onErrorResponse(VolleyError volleyError) {
        HandleRequest.access$300(this.this$0).onResult(null, -28);
    }

    public Request getRequest() {
        return new BaseStringRequest(1, WebservicePropertis.WS_REGISTER_SUPPORTER, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public String getBodyContentType() {
                return "Application/json";
            }

            public byte[] getBody() throws AuthFailureError {
                return HandleRequest.access$500(HandleRequest$41.this.this$0, new Gson().toJson(HandleRequest$41.this.val$user));
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseStringRequest.getBulkRequestHeaders(this._context);
            }
        };
    }

    public boolean ignoreParsingResponse() {
        return false;
    }

    public Type getClassType() {
        return null;
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_PAYMENT_API;
    }

    public String toString() {
        return "WS_POST_T_M";
    }
}
