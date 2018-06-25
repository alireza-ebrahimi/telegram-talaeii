package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import org.telegram.customization.dynamicadapter.data.SlsFilter;
import org.telegram.customization.util.Prefs;
import utils.view.Constants;

class HandleRequest$10 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;

    /* renamed from: org.telegram.customization.Internet.HandleRequest$10$1 */
    class C11261 extends TypeToken<ArrayList<SlsFilter>> {
        C11261() {
        }
    }

    HandleRequest$10(HandleRequest this$0) {
        this.this$0 = this$0;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        try {
            JsonObject jo = ((JsonElement) new Gson().fromJson((String) object.getItems(), JsonElement.class)).getAsJsonObject();
            String itemsJson = null;
            if (jo.get("data") != null) {
                itemsJson = jo.get("data").toString();
            }
            ArrayList<SlsFilter> filters = (ArrayList) new Gson().fromJson(itemsJson, new C11261().getType());
            Prefs.setFilters(HandleRequest.access$400(this.this$0), itemsJson);
            Prefs.setFiltersTime(HandleRequest.access$400(this.this$0), System.currentTimeMillis());
            HandleRequest.access$300(this.this$0).onResult(filters, 2);
        } catch (Exception e) {
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -2);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -2);
    }

    public Request getRequest() {
        return new BaseStringRequest(0, WebservicePropertis.WS_GET_FILTERS, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
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
        return "WS_GET_FILTERS";
    }
}
