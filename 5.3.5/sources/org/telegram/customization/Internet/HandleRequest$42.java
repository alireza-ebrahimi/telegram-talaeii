package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.customization.Model.Payment.User;
import utils.view.Constants;

class HandleRequest$42 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;

    HandleRequest$42(HandleRequest this$0) {
        this.this$0 = this$0;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        User user = (User) object.getItems();
        if (user != null) {
            HandleRequest.access$300(this.this$0).onResult(user, 29);
        } else {
            HandleRequest.access$300(this.this$0).onResult(user, -29);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        HandleRequest.access$300(this.this$0).onResult(null, -29);
    }

    public Request getRequest() {
        return new BaseStringRequest(1, WebservicePropertis.WS_CHECK_REGISTER_STATUS, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public String getBodyContentType() {
                return "text/plain";
            }

            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
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
        return User.class;
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_PAYMENT_API;
    }

    public String toString() {
        return "WS_POST_T_M";
    }
}
