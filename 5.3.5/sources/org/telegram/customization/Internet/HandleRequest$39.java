package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import org.telegram.customization.Model.HotgramTheme;
import org.telegram.messenger.ApplicationLoader;
import utils.app.AppPreferences;
import utils.view.Constants;

class HandleRequest$39 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;

    /* renamed from: org.telegram.customization.Internet.HandleRequest$39$2 */
    class C11632 extends TypeToken<ArrayList<HotgramTheme>> {
        C11632() {
        }
    }

    HandleRequest$39(HandleRequest this$0) {
        this.this$0 = this$0;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        try {
            ArrayList<HotgramTheme> themes = (ArrayList) object.getItems();
            if (themes == null || themes.size() <= 0) {
                HandleRequest.access$300(this.this$0).onResult(themes, -26);
                return;
            }
            AppPreferences.setThemeList(ApplicationLoader.applicationContext, new Gson().toJson(themes));
            HandleRequest.access$300(this.this$0).onResult(themes, 26);
        } catch (Exception e) {
            e.printStackTrace();
            BaseResponseModel resObj = new BaseResponseModel();
            resObj.setCode(-1);
            resObj.setMessage("");
            HandleRequest.access$300(this.this$0).onResult(resObj, -26);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        HandleRequest.access$300(this.this$0).onResult(null, -26);
    }

    public Request getRequest() {
        return new BaseStringRequest(0, WebservicePropertis.WS_GET_THEMES, this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }
        };
    }

    public boolean ignoreParsingResponse() {
        return false;
    }

    public Type getClassType() {
        return new C11632().getType();
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_USUAL_API;
    }

    public String toString() {
        return "WS_GET_THEMES";
    }
}
