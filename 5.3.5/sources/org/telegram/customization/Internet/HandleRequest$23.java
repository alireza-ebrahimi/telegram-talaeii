package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.telegram.customization.util.view.Poll.PollModel;
import utils.app.AppPreferences;
import utils.view.Constants;

class HandleRequest$23 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;

    HandleRequest$23(HandleRequest this$0) {
        this.this$0 = this$0;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        HandleRequest.access$300(this.this$0).onResult((PollModel) new Gson().fromJson((String) object.getItems(), PollModel.class), 13);
    }

    public void onErrorResponse(VolleyError volleyError) {
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -13);
    }

    public Request getRequest() {
        return new StringRequest(0, AppPreferences.getTagBaseUrl(HandleRequest.access$400(this.this$0)) + WebservicePropertis.WS_GET_POLL_URL, this.this$0, this.this$0) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                params.put(Constants.X_SLS_VERSION, String.valueOf(135));
                String deviceId = "550205994";
                String userId = "-1";
                if (deviceId == null || deviceId.length() <= 0) {
                    params.put(Constants.X_SLS_DEVICE_ID, String.valueOf(0));
                } else {
                    params.put(Constants.X_SLS_DEVICE_ID, String.valueOf(deviceId));
                }
                if (userId == null || userId.length() <= 0) {
                    params.put(Constants.X_SLS_USER_ID, String.valueOf(0));
                } else {
                    params.put(Constants.X_SLS_USER_ID, String.valueOf(userId));
                }
                params.put(Constants.X_SLS_AppId, String.valueOf(4));
                try {
                    params.put(Constants.X_SLS_Token, "dVQGpLWTfXBOs7dH2FI37LX+MmI=");
                } catch (Exception e) {
                }
                return params;
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
        return null;
    }

    public String toString() {
        return "WS_GET_POLL";
    }
}
