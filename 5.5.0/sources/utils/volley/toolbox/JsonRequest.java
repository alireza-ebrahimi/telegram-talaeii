package utils.volley.toolbox;

import java.io.UnsupportedEncodingException;
import utils.volley.NetworkResponse;
import utils.volley.Request;
import utils.volley.Response;
import utils.volley.Response$ErrorListener;
import utils.volley.Response$Listener;
import utils.volley.VolleyLog;

public abstract class JsonRequest<T> extends Request<T> {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", new Object[]{PROTOCOL_CHARSET});
    private Response$Listener<T> mListener;
    private final Object mLock;
    private final String mRequestBody;

    public JsonRequest(int i, String str, String str2, Response$Listener<T> response$Listener, Response$ErrorListener response$ErrorListener) {
        super(i, str, response$ErrorListener);
        this.mLock = new Object();
        this.mListener = response$Listener;
        this.mRequestBody = str2;
    }

    @Deprecated
    public JsonRequest(String str, String str2, Response$Listener<T> response$Listener, Response$ErrorListener response$ErrorListener) {
        this(-1, str, str2, response$Listener, response$ErrorListener);
    }

    public void cancel() {
        super.cancel();
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    protected void deliverResponse(T t) {
        synchronized (this.mLock) {
            Response$Listener response$Listener = this.mListener;
        }
        if (response$Listener != null) {
            response$Listener.onResponse(t);
        }
    }

    public byte[] getBody() {
        byte[] bArr = null;
        try {
            if (this.mRequestBody != null) {
                bArr = this.mRequestBody.getBytes(PROTOCOL_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", new Object[]{this.mRequestBody, PROTOCOL_CHARSET});
        }
        return bArr;
    }

    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Deprecated
    public byte[] getPostBody() {
        return getBody();
    }

    @Deprecated
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    protected abstract Response<T> parseNetworkResponse(NetworkResponse networkResponse);
}
