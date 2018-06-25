package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import org.telegram.customization.Model.Ads.Log;
import utils.view.Constants;

class HandleRequest$35 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ Log val$log;

    HandleRequest$35(HandleRequest this$0, Log log) {
        this.this$0 = this$0;
        this.val$log = log;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        try {
            JsonObject jo = ((JsonElement) new Gson().fromJson((String) object.getItems(), JsonElement.class)).getAsJsonObject();
            String itemsJson = null;
            String message = null;
            if (jo.get("code") != null) {
                itemsJson = jo.get("code").toString();
            }
            if (jo.get("message") != null) {
                message = jo.get("message").toString();
            }
            int code = Integer.parseInt(itemsJson);
            if (code == 200) {
                BaseResponseModel model = new BaseResponseModel();
                model.setCode(code);
                model.setMessage(message);
                HandleRequest.access$300(this.this$0).onResult(model, 23);
                return;
            }
            HandleRequest.access$300(this.this$0).onResult("", -23);
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -23);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
    }

    public Request getRequest() {
        return new BaseStringRequest(1, WebservicePropertis.WS_LOGS_ADS, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                ArrayList<Log> logs = new ArrayList();
                logs.add(HandleRequest$35.this.val$log);
                return HandleRequest.getEncondedByteArr(new Gson().toJson(logs));
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
        return Constants.KEY_SETTING_USUAL_API;
    }

    public String toString() {
        return "WS_POST_SEND_CONTACTS";
    }
}
