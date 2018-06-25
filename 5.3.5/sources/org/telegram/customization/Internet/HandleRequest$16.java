package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import java.lang.reflect.Type;
import java.util.Map;

class HandleRequest$16 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ String val$json;

    HandleRequest$16(HandleRequest this$0, String str) {
        this.this$0 = this$0;
        this.val$json = str;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        if (object.getCode() == 200) {
            HandleRequest.access$300(this.this$0).onResult(object, 6);
        } else {
            HandleRequest.access$300(this.this$0).onResult("null", -6);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        HandleRequest.access$300(this.this$0).onResult("null", -6);
    }

    public Request getRequest() {
        return new BaseStringRequest(1, "v12/user/saveTags", this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public byte[] getBody() throws AuthFailureError {
                return HandleRequest.access$500(HandleRequest$16.this.this$0, HandleRequest$16.this.val$json);
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
    }

    public boolean ignoreParsingResponse() {
        return false;
    }

    public Type getClassType() {
        return BaseResponseModel.class;
    }

    public String getSetKey() {
        return null;
    }

    public String toString() {
        return "WS_POST_SAVE_TAG";
    }
}
