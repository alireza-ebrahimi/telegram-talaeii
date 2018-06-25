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
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;

class HandleRequest$11 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ long val$docId;
    final /* synthetic */ long val$localId;
    final /* synthetic */ long val$volumeId;

    HandleRequest$11(HandleRequest this$0, long j, long j2, long j3) {
        this.this$0 = this$0;
        this.val$docId = j;
        this.val$volumeId = j2;
        this.val$localId = j3;
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
            DocAvailableInfo doc = (DocAvailableInfo) new Gson().fromJson(itemsJson, DocAvailableInfo.class);
            if (doc != null) {
                HandleRequest.access$300(this.this$0).onResult(doc.localUrl, 3);
            } else {
                HandleRequest.access$300(this.this$0).onResult(null, -3);
            }
        } catch (Exception e) {
            HandleRequest.access$300(this.this$0).onResult(null, -3);
            e.printStackTrace();
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -3);
    }

    public Request getRequest() {
        String url;
        if (this.val$docId < 1) {
            url = String.format(WebservicePropertis.WS_GET_IMAGE, new Object[]{Long.valueOf(this.val$volumeId), Long.valueOf(this.val$localId)});
        } else {
            url = String.format(WebservicePropertis.WS_GET_DOCUMENT, new Object[]{Long.valueOf(this.val$docId)});
        }
        return new BaseStringRequest(0, url, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
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
        return null;
    }

    public String toString() {
        return "WS_GET_DOCUMENT_OR_IMAGE";
    }
}
