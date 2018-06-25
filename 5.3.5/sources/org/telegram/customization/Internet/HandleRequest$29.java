package org.telegram.customization.Internet;

import android.text.TextUtils;
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
import java.util.Iterator;
import java.util.Map;
import org.telegram.customization.Application.AppApplication;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.messenger.UserConfig;
import utils.app.AppPreferences;
import utils.view.Constants;

class HandleRequest$29 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ String val$json;

    /* renamed from: org.telegram.customization.Internet.HandleRequest$29$1 */
    class C11501 extends TypeToken<ArrayList<Integer>> {
        C11501() {
        }
    }

    HandleRequest$29(HandleRequest this$0, String str) {
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
            String data = null;
            if (jo.get("code") != null) {
                itemsJson = jo.get("code").toString();
            }
            if (jo.get("message") != null) {
                itemsJson = jo.get("message").toString();
            }
            if (jo.get("data") != null) {
                data = jo.get("data").toString();
            }
            if (Integer.parseInt(itemsJson) == 200) {
                if (!TextUtils.isEmpty(data)) {
                    ArrayList<Integer> dialogIds = new ArrayList();
                    dialogIds = (ArrayList) new Gson().fromJson(data, new C11501().getType());
                    if (dialogIds.size() > 0) {
                        Iterator it = dialogIds.iterator();
                        while (it.hasNext()) {
                            AppApplication.getDatabaseHandler().createOrUpdateDialogStatus(new DialogStatus((long) ((Integer) it.next()).intValue(), true, false));
                        }
                    }
                }
                HandleRequest.access$300(this.this$0).onResult("", 14);
                AppPreferences.setLastSuccessFullyTimeSyncContact(HandleRequest.access$400(this.this$0), System.currentTimeMillis());
                return;
            }
            HandleRequest.access$300(this.this$0).onResult("", -14);
        } catch (Exception e) {
            e.printStackTrace();
            AppPreferences.setSendContactsPeriod(HandleRequest.access$400(this.this$0), 0);
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -14);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
    }

    public Request getRequest() {
        int userId = 0;
        if (UserConfig.getCurrentUser() != null) {
            userId = UserConfig.getCurrentUser().id;
        }
        return new BaseStringRequest(1, String.format(WebservicePropertis.WS_POST_SEND_CONTACTS, new Object[]{Integer.valueOf(userId)}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public String getBodyContentType() {
                return "text/plain";
            }

            public byte[] getBody() throws AuthFailureError {
                return HandleRequest.getEncondedByteArr(HandleRequest$29.this.val$json);
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseStringRequest.getBulkRequestHeaders(this._context);
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
        return "WS_POST_SEND_CONTACTS";
    }
}
