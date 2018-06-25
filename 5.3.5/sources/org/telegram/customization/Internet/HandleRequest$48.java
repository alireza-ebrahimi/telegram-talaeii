package org.telegram.customization.Internet;

import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import java.lang.reflect.Type;
import java.util.Map;
import org.telegram.customization.Model.Payment.PaymentLink;
import utils.view.Constants;

class HandleRequest$48 implements HandleRequest$HandleInterface {
    final /* synthetic */ HandleRequest this$0;
    final /* synthetic */ long val$amount;
    final /* synthetic */ String val$description;
    final /* synthetic */ long val$fromTelegramUserId;
    final /* synthetic */ long val$toTelegramUserId;

    HandleRequest$48(HandleRequest this$0, long j, long j2, long j3, String str) {
        this.this$0 = this$0;
        this.val$fromTelegramUserId = j;
        this.val$toTelegramUserId = j2;
        this.val$amount = j3;
        this.val$description = str;
    }

    public RetryPolicy getRetryPolicy() {
        return null;
    }

    public void onResponse(BaseResponseModel object) {
        PaymentLink paymentLink = (PaymentLink) object.getItems();
        if (paymentLink != null && !TextUtils.isEmpty(paymentLink.getPaymentId()) && !TextUtils.isEmpty(paymentLink.getLink())) {
            HandleRequest.access$300(this.this$0).onResult(paymentLink, 32);
        }
    }

    public void onErrorResponse(VolleyError volleyError) {
        HandleRequest.access$300(this.this$0).onResult(null, -32);
    }

    public Request getRequest() {
        return new BaseStringRequest(1, String.format(WebservicePropertis.WS_GENERATE_PAYMENT_LINK, new Object[]{Long.valueOf(this.val$fromTelegramUserId), Long.valueOf(this.val$toTelegramUserId), Long.valueOf(this.val$amount), this.val$description}), this.this$0, this.this$0, HandleRequest.access$400(this.this$0), getSetKey()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            public String getBodyContentType() {
                return "text/plain";
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
        return PaymentLink.class;
    }

    public String getSetKey() {
        return Constants.KEY_SETTING_PAYMENT_API;
    }

    public String toString() {
        return "WS_POST_T_M";
    }
}
