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
import utils.view.Constants;

class HandleRequest$34 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ ArrayList val$categories;

    HandleRequest$34(HandleRequest this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$categories = arrayList;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        try {
            JsonObject jo = ((JsonElement) new Gson().fromJson((String) object.getItems(), JsonElement.class)).getAsJsonObject();
            String itemsJson = null;
            if (jo.get("code") != null) {
                itemsJson = jo.get("code").toString();
            }
            if (jo.get("message") != null) {
                itemsJson = jo.get("message").toString();
            }
            if (Integer.parseInt(itemsJson) == 200) {
                HandleRequest.access$300(this.this$0).onResult("", 20);
            } else {
                HandleRequest.access$300(this.this$0).onResult("", -20);
            }
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -20);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
    }

    public Request getRequest() {
        return new BaseStringRequest(2, String.format(WebservicePropertis.WS_MANAGE_ADS_CHANNEL, new Object[0]), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                return new Gson().toJson(HandleRequest$34.this.val$categories).getBytes();
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
