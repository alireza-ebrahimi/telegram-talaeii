package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.messenger.UserConfig;
import utils.app.AppPreferences;

class HandleRequest$15 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ String val$json;

    HandleRequest$15(HandleRequest this$0, String str) {
        this.this$0 = this$0;
        this.val$json = str;
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
            if (Integer.parseInt(itemsJson) != 200) {
                AppPreferences.setSaveChannelPeriod(HandleRequest.access$400(this.this$0), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppPreferences.setSaveChannelPeriod(HandleRequest.access$400(this.this$0), 0);
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -5);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
    }

    public Request getRequest() {
        int userId = 0;
        if (UserConfig.getCurrentUser() != null) {
            userId = UserConfig.getCurrentUser().id;
        }
        return new BaseStringRequest(1, String.format(WebservicePropertis.WS_GET_SEND_CHANNEL_LIST, new Object[]{Integer.valueOf(userId)}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                return HandleRequest.access$500(HandleRequest$15.this.this$0, HandleRequest$15.this.val$json);
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
        return null;
    }

    public String toString() {
        return "WS_GET_SEND_CHANNEL_LIST";
    }
}
