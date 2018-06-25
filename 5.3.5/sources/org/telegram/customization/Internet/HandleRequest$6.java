package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import org.telegram.customization.dynamicadapter.GsonAdapterFactory;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import utils.app.AppPreferences;
import utils.view.Constants;

class HandleRequest$6 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ boolean val$getOlderPost;
    final /* synthetic */ long val$lastId;
    final /* synthetic */ int val$limit;
    final /* synthetic */ long val$mediaType;
    final /* synthetic */ long val$tagId;

    /* renamed from: org.telegram.customization.Internet.HandleRequest$6$1 */
    class C11761 extends TypeToken<ArrayList<ObjBase>> {
        C11761() {
        }
    }

    HandleRequest$6(HandleRequest this$0, long j, long j2, boolean z, int i, long j3) {
        this.this$0 = this$0;
        this.val$lastId = j;
        this.val$tagId = j2;
        this.val$getOlderPost = z;
        this.val$limit = i;
        this.val$mediaType = j3;
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
        HandleRequest.access$300(this.this$0).onResult((ArrayList) new GsonBuilder().registerTypeAdapterFactory(new GsonAdapterFactory()).create().fromJson(itemsJson, new C11761().getType()), 1);
    }

    public void onErrorResponse(VolleyError volleyError) {
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -1);
    }

    public Request getRequest() {
        if (this.val$lastId == 0) {
            AppPreferences.increaseHotPostRandomNumber(HandleRequest.access$400(this.this$0));
        }
        String str = WebservicePropertis.WS_GET_HOME;
        Object[] objArr = new Object[6];
        objArr[0] = Long.valueOf(this.val$tagId);
        objArr[1] = this.val$getOlderPost ? "previous" : Constants.TYPE_DIR_NEXT;
        objArr[2] = Integer.valueOf(this.val$limit);
        objArr[3] = Long.valueOf(this.val$mediaType);
        objArr[4] = Long.valueOf(this.val$lastId);
        objArr[5] = Long.valueOf(AppPreferences.getHotPostRandomNumber(HandleRequest.access$400(this.this$0)));
        return new BaseStringRequest(0, String.format(str, objArr), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
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
