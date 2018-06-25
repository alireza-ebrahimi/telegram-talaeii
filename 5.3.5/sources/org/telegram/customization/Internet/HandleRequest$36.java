package org.telegram.customization.Internet;

import android.text.TextUtils;
import android.util.Log;
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
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import org.telegram.customization.Model.ProxyServerModel;
import org.telegram.customization.util.crypto.RSAUtils;
import org.telegram.messenger.ApplicationLoader;
import utils.app.AppPreferences;
import utils.view.Constants;

class HandleRequest$36 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ boolean val$setProxy;

    /* renamed from: org.telegram.customization.Internet.HandleRequest$36$2 */
    class C11592 extends TypeToken<ArrayList<ProxyServerModel>> {
        C11592() {
        }
    }

    HandleRequest$36(HandleRequest this$0, boolean z) {
        this.this$0 = this$0;
        this.val$setProxy = z;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        try {
            HandleRequest.access$602(false);
            JsonObject jo = ((JsonElement) new Gson().fromJson((String) object.getItems(), JsonElement.class)).getAsJsonObject();
            String proxyListStr = null;
            if (jo.get("data") != null) {
                proxyListStr = jo.get("data").toString();
            }
            Log.d("alireza", "alireza" + proxyListStr);
            if (TextUtils.isEmpty(proxyListStr)) {
                HandleRequest.access$300(this.this$0).onResult("", -24);
                return;
            }
            ArrayList<ProxyServerModel> serverProxy = new ArrayList();
            for (String spEncoded : (String[]) new Gson().fromJson(proxyListStr, String[].class)) {
                serverProxy.add((ProxyServerModel) new Gson().fromJson(RSAUtils.decryptAES(spEncoded), ProxyServerModel.class));
            }
            if (serverProxy == null || serverProxy.size() <= 0) {
                HandleRequest.access$300(this.this$0).onResult("", -24);
                return;
            }
            ArrayList<ProxyServerModel> serverModels = new ArrayList();
            Iterator it = serverProxy.iterator();
            while (it.hasNext()) {
                ProxyServerModel proxyServerModel = (ProxyServerModel) it.next();
                if (proxyServerModel != null) {
                    try {
                        proxyServerModel.fillLocalExpireTime();
                        Log.d("alireza", "alireza onresult : proxy recieved " + proxyServerModel.getIp());
                        serverModels.add(proxyServerModel);
                    } catch (Exception e) {
                    }
                }
            }
            if (serverModels.size() > 0) {
                Collections.shuffle(serverModels);
                AppPreferences.setProxyList(HandleRequest.access$400(this.this$0), serverModels);
                AppPreferences.setProxyHealth(ApplicationLoader.applicationContext, true);
            }
            if (this.val$setProxy) {
                SLSProxyHelper.getProxyServer(HandleRequest.access$400(this.this$0));
            }
            HandleRequest.access$300(this.this$0).onResult(serverModels, 24);
        } catch (Exception e2) {
            e2.printStackTrace();
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -24);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        Log.d("LEE", "HandleRequest onErrorResponse");
        HandleRequest.access$602(false);
    }

    public Request getRequest() {
        return new BaseStringRequest(1, WebservicePropertis.WS_GET_PROXY_SERVER, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                ProxyServerModel p = ProxyServerModel.getFromShared();
                ProxyServerModel changedProxy = new ProxyServerModel();
                if (!TextUtils.isEmpty(p.getIp())) {
                    String[] ipPart = p.getIp().split("\\.");
                    if (ipPart.length > 2) {
                        changedProxy.setIp(ipPart[ipPart.length - 2] + "." + ipPart[ipPart.length - 1]);
                    }
                }
                if (!TextUtils.isEmpty(p.getUserName()) && p.getUserName().length() > 2) {
                    changedProxy.setUserName(p.getUserName().substring(0, 2));
                }
                changedProxy.setPassWord(HandleRequest$36.this.this$0.getChangedPassword(p.getPassWord()));
                changedProxy.setPort(p.getPort());
                changedProxy.setPorxyHealth(p.isPorxyHealth());
                changedProxy.setExpireDateSecs(p.getExpireDateSecs());
                return new Gson().toJson(changedProxy).getBytes();
            }
        };
    }

    public boolean ignoreParsingResponse() {
        return true;
    }

    public Type getClassType() {
        return new C11592().getType();
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_LIGHT_PROXY_API;
    }

    public String toString() {
        return "WS_LIGHT_HOST_GET_PROXY";
    }
}
