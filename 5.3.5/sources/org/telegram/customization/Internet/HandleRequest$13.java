package org.telegram.customization.Internet;

import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import org.telegram.customization.Model.CheckUrlResponseModel;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo$NewDocAvailableInfoAdapterFactory;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import utils.FreeDownload;
import utils.view.Constants;

class HandleRequest$13 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ ArrayList val$docs;

    HandleRequest$13(HandleRequest this$0, ArrayList arrayList, long j) {
        this.this$0 = this$0;
        this.val$docs = arrayList;
        this.val$dialog_id = j;
    }

    public RetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(7000, 0, 1.0f);
    }

    public void onResponse(BaseResponseModel object) {
        try {
            JsonObject jo = ((JsonElement) new Gson().fromJson((String) object.getItems(), JsonElement.class)).getAsJsonObject();
            String itemsJson = null;
            if (jo.get("data").toString() != null) {
                itemsJson = jo.get("data").toString();
            }
            CheckUrlResponseModel model = (CheckUrlResponseModel) new Gson().fromJson(itemsJson, CheckUrlResponseModel.class);
            if (model != null) {
                ArrayList<DocAvailableInfo> result = new ArrayList();
                for (String key : model.getUrl().keySet()) {
                    DocAvailableInfo docAvailableInfo = null;
                    if (!TextUtils.isEmpty(key)) {
                        if (key.contains(".")) {
                            String[] parts = key.split("\\.");
                            if (parts.length == 2) {
                                docAvailableInfo = new DocAvailableInfo(0, Integer.parseInt(parts[0]), Long.parseLong(parts[1]), 0, true);
                                docAvailableInfo.setLocalUrl((String) model.getUrl().get(key));
                            }
                            if (docAvailableInfo != null) {
                                result.add(docAvailableInfo);
                            }
                        }
                    }
                    docAvailableInfo = new DocAvailableInfo(Long.parseLong(key), 0, 0, 0, true);
                    docAvailableInfo.setLocalUrl((String) model.getUrl().get(key));
                    if (docAvailableInfo != null) {
                        result.add(docAvailableInfo);
                    }
                }
                FreeDownload.addDocs(result);
                FreeDownload.addTags(model.getTag());
                HandleRequest.access$300(this.this$0).onResult(result, 4);
            }
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
        return new BaseStringRequest(1, String.format(WebservicePropertis.WS_GET_BATCH_IMAGE_DOCUMENT_NEW, new Object[]{Boolean.valueOf(ConnectionsManager.isConnectedMobile(HandleRequest.access$400(this.this$0))), WSUtils.getCarrier(HandleRequest.access$400(this.this$0)), Integer.valueOf(UserConfig.getClientUserId()), Long.valueOf(this.val$dialog_id)}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                return HandleRequest.access$500(HandleRequest$13.this.this$0, new GsonBuilder().registerTypeAdapterFactory(new DocAvailableInfo$NewDocAvailableInfoAdapterFactory()).create().toJson(HandleRequest$13.this.val$docs));
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
        return Constants.KEY_SETTING_LIGHT_CHECK_URL_API;
    }

    public String toString() {
        return "WS_LIGHT_HOST_CHECK_URL";
    }
}
