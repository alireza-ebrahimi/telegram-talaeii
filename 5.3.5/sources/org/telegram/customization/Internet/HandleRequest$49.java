package org.telegram.customization.Internet;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.customization.dynamicadapter.viewholder.ResponseSettleHelper;
import utils.view.Constants;

class HandleRequest$49 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ long val$endDate;
    final /* synthetic */ int val$pageCount;
    final /* synthetic */ int val$pageIndex;
    final /* synthetic */ long val$startDate;

    HandleRequest$49(HandleRequest this$0, int i, int i2, long j, long j2) {
        this.this$0 = this$0;
        this.val$pageIndex = i;
        this.val$pageCount = i2;
        this.val$startDate = j;
        this.val$endDate = j2;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        ResponseSettleHelper responseSettleHelper = (ResponseSettleHelper) object.getItems();
        if (responseSettleHelper != null) {
            HandleRequest.access$300(this.this$0).onResult(responseSettleHelper.getResponse(), 34);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        HandleRequest.access$300(this.this$0).onResult(null, -34);
    }

    public Request getRequest() {
        return new BaseStringRequest(0, String.format(WebservicePropertis.WS_REPORT_SETTLEMENT, new Object[]{Integer.valueOf(this.val$pageIndex), Integer.valueOf(this.val$pageCount), Long.valueOf(this.val$startDate), Long.valueOf(this.val$endDate)}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public String getBodyContentType() {
                return "Application/json";
            }

            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                return BaseStringRequest.getBulkRequestHeaders(this._context);
            }
        };
    }

    public boolean ignoreParsingResponse() {
        return false;
    }

    public Type getClassType() {
        return ResponseSettleHelper.class;
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_PAYMENT_API;
    }

    public String toString() {
        return "WS_REPORT_SETTLEMENT";
    }
}
