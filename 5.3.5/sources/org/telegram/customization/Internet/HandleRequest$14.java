package org.telegram.customization.Internet;

import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.customization.Model.RegisterModel;
import utils.view.Constants;

class HandleRequest$14 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ boolean val$callInService;

    HandleRequest$14(HandleRequest this$0, boolean z) {
        this.this$0 = this$0;
        this.val$callInService = z;
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
            if (Integer.parseInt(itemsJson) == 200) {
                HandleRequest.access$300(this.this$0).onResult(Boolean.valueOf(TextUtils.isEmpty(message)), 5);
                return;
            }
            HandleRequest.access$300(this.this$0).onResult(Boolean.valueOf(false), -5);
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -5);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
    }

    public Request getRequest() {
        return new BaseStringRequest(1, WebservicePropertis.WS_REGISTER, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                RegisterModel model = RegisterModel.getInstance();
                model.setFromInfo(HandleRequest$14.this.val$callInService);
                return HandleRequest.getEncondedByteArr(new Gson().toJson(model));
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
        return Constants.KEY_SETTING_REGISTER_API;
    }

    public String toString() {
        return "WS_REGISTER";
    }
}
