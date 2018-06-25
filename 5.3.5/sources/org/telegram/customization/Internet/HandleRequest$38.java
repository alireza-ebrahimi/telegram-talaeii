package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.messenger.UserConfig;
import utils.view.Constants;

class HandleRequest$38 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ String val$json;

    HandleRequest$38(HandleRequest this$0, String str) {
        this.this$0 = this$0;
        this.val$json = str;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
    }

    public void onErrorResponse(VolleyError volleyError) {
    }

    public Request getRequest() {
        int userId = 0;
        if (UserConfig.getCurrentUser() != null) {
            userId = UserConfig.getCurrentUser().id;
        }
        return new BaseStringRequest(1, String.format(WebservicePropertis.WS_POST_T_M, new Object[]{Integer.valueOf(userId)}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public String getBodyContentType() {
                return "text/plain";
            }

            public byte[] getBody() throws AuthFailureError {
                return HandleRequest.getEncondedByteArr(HandleRequest$38.this.val$json);
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseStringRequest.getBulkRequestHeaders(this._context);
            }
        };
    }

    public boolean ignoreParsingResponse() {
        return true;
    }

    public Type getClassType() {
        return null;
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_INFO_API;
    }

    public String toString() {
        return "WS_POST_T_M";
    }
}
