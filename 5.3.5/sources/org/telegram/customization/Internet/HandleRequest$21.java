package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.telegram.news.model.News;
import utils.app.AppPreferences;
import utils.view.Constants;

class HandleRequest$21 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ String val$dir;
    final /* synthetic */ String val$lastId;
    final /* synthetic */ int val$limit;
    final /* synthetic */ long val$tagId;

    /* renamed from: org.telegram.customization.Internet.HandleRequest$21$1 */
    class C11401 extends TypeToken<ArrayList<News>> {
        C11401() {
        }
    }

    HandleRequest$21(HandleRequest this$0, long j, int i, String str, String str2) {
        this.this$0 = this$0;
        this.val$tagId = j;
        this.val$limit = i;
        this.val$lastId = str;
        this.val$dir = str2;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        String json = (String) object.getItems();
        Gson gson = new Gson();
        JsonObject jo = ((JsonElement) gson.fromJson(json, JsonElement.class)).getAsJsonObject();
        ArrayList<News> newsList = new ArrayList();
        if (jo.get("result") != null) {
            newsList = (ArrayList) gson.fromJson(jo.get("result").toString(), new C11401().getType());
        }
        HandleRequest.access$300(this.this$0).onResult(newsList, 11);
    }

    public void onErrorResponse(VolleyError volleyError) {
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -11);
    }

    public Request getRequest() {
        return new StringRequest(0, String.format(AppPreferences.getTagBaseUrl(HandleRequest.access$400(this.this$0)) + WebservicePropertis.WS_GET_NEWS_LIST, new Object[]{Long.valueOf(this.val$tagId), Integer.valueOf(this.val$limit), this.val$lastId, this.val$dir, "", "", ""}), this.this$0, this.this$0) {
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
        return "WS_GET_NEWS_LIST";
    }
}
