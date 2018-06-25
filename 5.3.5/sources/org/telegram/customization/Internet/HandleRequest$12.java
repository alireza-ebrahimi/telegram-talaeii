package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import org.telegram.customization.dynamicadapter.data.DocAvailabilityList;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo$DocAvailableInfoAdapterFactory;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import utils.FreeDownload;
import utils.view.Constants;

class HandleRequest$12 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ ArrayList val$docs;

    /* renamed from: org.telegram.customization.Internet.HandleRequest$12$1 */
    class C11291 extends TypeToken<ArrayList<DocAvailableInfo>> {
        C11291() {
        }
    }

    HandleRequest$12(HandleRequest this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$docs = arrayList;
    }

    public RetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(7000, 0, 1.0f);
    }

    public void onResponse(BaseResponseModel object) {
        try {
            JsonObject jo = ((JsonElement) new Gson().fromJson((String) object.getItems(), JsonElement.class)).getAsJsonObject();
            String itemsJson = null;
            if (jo.get("data").getAsJsonObject().get("o") != null) {
                itemsJson = jo.get("data").getAsJsonObject().get("o").toString();
            }
            ArrayList<DocAvailableInfo> availableInfos = (ArrayList) new Gson().fromJson(itemsJson, new C11291().getType());
            String label = jo.get("data").getAsJsonObject().get("lbl").getAsString();
            String freeState = jo.get("data").getAsJsonObject().get("fs").getAsString();
            for (int i = 0; i < availableInfos.size(); i++) {
                ((DocAvailableInfo) availableInfos.get(i)).trafficStatusLabel = label;
                ((DocAvailableInfo) availableInfos.get(i)).freeState = freeState;
            }
            ArrayList<DocAvailableInfo> result = new ArrayList();
            Iterator it = availableInfos.iterator();
            while (it.hasNext()) {
                result.add((DocAvailableInfo) it.next());
            }
            FreeDownload.addDocs(result);
            HandleRequest.access$300(this.this$0).onResult(result, 4);
            HandleRequest.callCount++;
            HandleRequest.msAll += System.currentTimeMillis() - HandleRequest.msChange;
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -4);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        int i = 0;
        while (i < this.val$docs.size()) {
            try {
                ((DocAvailableInfo) this.val$docs.get(i)).exists = false;
                i++;
            } catch (Exception e) {
            }
        }
        FreeDownload.addDocs(this.val$docs);
        BaseResponseModel resObj = new BaseResponseModel();
        resObj.setCode(-1);
        resObj.setMessage("");
        HandleRequest.access$300(this.this$0).onResult(resObj, -4);
    }

    public Request getRequest() {
        return new BaseStringRequest(1, WebservicePropertis.WS_GET_BATCH_IMAGE_DOCUMENT, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                DocAvailabilityList dal = new DocAvailabilityList();
                dal.carrier = WSUtils.getCarrier(this._context);
                dal.gprs = ConnectionsManager.isConnectedMobile(this._context);
                dal.inputs = HandleRequest$12.this.val$docs;
                dal.phone = UserConfig.getCurrentUser().phone;
                dal.userId = (long) UserConfig.getClientUserId();
                dal.versionCode = 135;
                return HandleRequest.access$500(HandleRequest$12.this.this$0, new GsonBuilder().registerTypeAdapterFactory(new DocAvailableInfo$DocAvailableInfoAdapterFactory()).create().toJson(dal));
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
        return Constants.KEY_SETTING_CHECK_URL_API;
    }

    public String toString() {
        return "WS_GET_BATCH_IMAGE_DOCUMENT";
    }
}
