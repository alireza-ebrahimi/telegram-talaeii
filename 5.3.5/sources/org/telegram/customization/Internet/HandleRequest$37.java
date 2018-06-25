package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.customization.Model.ProxyServerModel;
import utils.view.Constants;

class HandleRequest$37 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;

    HandleRequest$37(HandleRequest this$0) {
        this.this$0 = this$0;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        try {
            if (object.getCode() == 200) {
                ProxyServerModel serverProxy = (ProxyServerModel) new Gson().fromJson(HandleRequest.getDecodedString((String) object.getItems()), ProxyServerModel.class);
                SLSProxyHelper.setProxyDirectly(serverProxy);
                HandleRequest.access$300(this.this$0).onResult(serverProxy, 25);
                return;
            }
            HandleRequest.access$300(this.this$0).onResult("", -24);
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -24);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
    }

    public Request getRequest() {
        return new BaseStringRequest(1, WebservicePropertis.WS_POST_PROXY_SERVER_FAILED, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                return HandleRequest.getEncondedByteArr(new Gson().toJson(ProxyServerModel.getFromShared()));
            }
        };
    }

    public boolean ignoreParsingResponse() {
        return false;
    }

    public Type getClassType() {
        return String.class;
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_PROXY_API;
    }

    public String toString() {
        return "WS_POST_PROXY_SERVER_FAILED";
    }
}
