package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.messenger.UserConfig;

class HandleRequest$17 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ long val$tagId;

    HandleRequest$17(HandleRequest this$0, long j) {
        this.this$0 = this$0;
        this.val$tagId = j;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        if (object.getCode() == 200) {
            HandleRequest.access$300(this.this$0).onResult(object.getItems(), 8);
        } else {
            HandleRequest.access$300(this.this$0).onResult(null, -8);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        HandleRequest.access$300(this.this$0).onResult(null, -8);
    }

    public Request getRequest() {
        return new BaseStringRequest(0, String.format(WebservicePropertis.WS_IS_FAVORITE_TAG, new Object[]{Integer.valueOf(UserConfig.getCurrentUser().id), Long.valueOf(this.val$tagId)}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
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
        return Boolean.class;
    }

    public String getSetKey() {
        return null;
    }

    public String toString() {
        return "WS_IS_FAVORITE_TAG";
    }
}
