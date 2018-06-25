package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.customization.Model.Ads.Statistics;
import org.telegram.customization.dynamicadapter.GsonAdapterFactory;
import utils.view.Constants;

class HandleRequest$33 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;

    HandleRequest$33(HandleRequest this$0) {
        this.this$0 = this$0;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        JsonObject jo = ((JsonElement) new Gson().fromJson((String) object.getItems(), JsonElement.class)).getAsJsonObject();
        String itemsJson = null;
        if (jo.get("data") != null) {
            itemsJson = jo.get("data").toString();
        }
        HandleRequest.access$300(this.this$0).onResult((Statistics) new GsonBuilder().registerTypeAdapterFactory(new GsonAdapterFactory()).create().fromJson(itemsJson, Statistics.class), 22);
    }

    public void onErrorResponse(VolleyError volleyError) {
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -22);
    }

    public Request getRequest() {
        return new BaseStringRequest(0, WebservicePropertis.WS_GET_STATISTICS, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
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
        return "WS_GET_HOME";
    }
}
