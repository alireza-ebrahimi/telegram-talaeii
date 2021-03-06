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
import java.util.Iterator;
import java.util.Map;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.customization.Model.FilterResponse;
import org.telegram.customization.dynamicadapter.GsonAdapterFactory;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.UserConfig;
import utils.app.AppPreferences;
import utils.view.Constants;

class HandleRequest$25 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ String val$json;

    /* renamed from: org.telegram.customization.Internet.HandleRequest$25$1 */
    class C11451 extends TypeToken<ArrayList<FilterResponse>> {
        C11451() {
        }
    }

    HandleRequest$25(HandleRequest this$0, String str) {
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
            if (Integer.parseInt(itemsJson) == 200) {
                if (jo.get("data") != null) {
                    itemsJson = jo.get("data").toString();
                }
                ArrayList<FilterResponse> filtersChannels = (ArrayList) new GsonBuilder().registerTypeAdapterFactory(new GsonAdapterFactory()).create().fromJson(itemsJson, new C11451().getType());
                ApplicationLoader.databaseHandler.clearFilterStatus();
                Iterator it = filtersChannels.iterator();
                while (it.hasNext()) {
                    FilterResponse filterResponse = (FilterResponse) it.next();
                    DialogStatus dialogStatus = new DialogStatus();
                    dialogStatus.setFilter(true);
                    dialogStatus.setDialogId(filterResponse.getChannelId());
                    ApplicationLoader.databaseHandler.createOrUpdateDialogStatus(dialogStatus);
                }
                AppPreferences.setLastSuccessFullyTimeSyncChannel(HandleRequest.access$400(this.this$0), System.currentTimeMillis());
                HandleRequest.access$300(this.this$0).onResult("", 16);
                return;
            }
            HandleRequest.access$300(this.this$0).onResult("", -16);
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -16);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
    }

    public Request getRequest() {
        int userId = 0;
        if (UserConfig.getCurrentUser() != null) {
            userId = UserConfig.getCurrentUser().id;
        }
        return new BaseStringRequest(1, String.format(WebservicePropertis.WS_POST_SEND_CHANNELS_NEW, new Object[]{Integer.valueOf(userId)}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseStringRequest.getBulkRequestHeaders(this._context);
            }

            public String getBodyContentType() {
                return "text/plain";
            }

            public byte[] getBody() throws AuthFailureError {
                return HandleRequest.getEncondedByteArr(HandleRequest$25.this.val$json);
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
        return Constants.KEY_SETTING_INFO_API;
    }

    public String toString() {
        return "WS_GET_SEND_CHANNEL_LIST";
    }
}
