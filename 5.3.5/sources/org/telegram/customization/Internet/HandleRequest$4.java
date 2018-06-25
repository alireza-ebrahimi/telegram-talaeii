package org.telegram.customization.Internet;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import java.util.Map;

class HandleRequest$4 extends BaseStringRequest {
    final /* synthetic */ HandleRequest this$0;

    HandleRequest$4(HandleRequest this$0, int method, String url, Listener listener, ErrorListener errorListener, Context context, boolean addMainDomain, String setKey) {
        this.this$0 = this$0;
        super(method, url, listener, errorListener, context, addMainDomain, setKey);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    public byte[] getBody() throws AuthFailureError {
        return HandleRequest.access$100(this.this$0).getRequest().getBody();
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        try {
            return HandleRequest.access$100(this.this$0).getRequest().getHeaders();
        } catch (Exception e) {
            return super.getHeaders();
        }
    }

    public String getBodyContentType() {
        return HandleRequest.access$100(this.this$0).getRequest().getBodyContentType();
    }

    public String getPostBodyContentType() {
        return HandleRequest.access$100(this.this$0).getRequest().getPostBodyContentType();
    }
}
