package org.telegram.customization.Internet;

import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.Map;
import org.ir.talaeii.R;
import org.telegram.customization.Model.SettingAndUpdate;
import utils.ObjectWrapper;
import utils.view.Constants;

class HandleRequest$20 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;

    HandleRequest$20(HandleRequest this$0) {
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
            SettingAndUpdate settingAndUpdate = (SettingAndUpdate) new Gson().fromJson(itemsJson, SettingAndUpdate.class);
            ObjectWrapper.saveSetting(settingAndUpdate.getSetting(), HandleRequest.access$400(this.this$0));
            Log.d("alireza ", "alireza setting " + new Gson().toJson(settingAndUpdate));
            HandleRequest.access$300(this.this$0).onResult(settingAndUpdate, 10);
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -10);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -10);
    }

    public Request getRequest() {
        BaseStringRequest request = new BaseStringRequest(0, String.format(WebservicePropertis.WS_GET_SELF_UPDATE, new Object[]{String.valueOf(HandleRequest.access$400(this.this$0).getResources().getInteger(R.integer.APP_ID)), String.valueOf(135), String.valueOf(12)}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }
        };
        request.setShouldCache(false);
        return request;
    }

    public boolean ignoreParsingResponse() {
        return true;
    }

    public Type getClassType() {
        return null;
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_LIGHT_CONFIG_API;
    }

    public String toString() {
        return "WS_LIGHT_HOST_CONFIG";
    }
}
