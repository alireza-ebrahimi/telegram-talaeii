package utils.volley.toolbox;

import java.io.UnsupportedEncodingException;
import utils.volley.NetworkResponse;
import utils.volley.Request;
import utils.volley.Response;
import utils.volley.Response$ErrorListener;
import utils.volley.Response$Listener;

public class StringRequest extends Request<String> {
    private Response$Listener<String> mListener;
    private final Object mLock;

    public StringRequest(int i, String str, Response$Listener<String> response$Listener, Response$ErrorListener response$ErrorListener) {
        super(i, str, response$ErrorListener);
        this.mLock = new Object();
        this.mListener = response$Listener;
    }

    public StringRequest(String str, Response$Listener<String> response$Listener, Response$ErrorListener response$ErrorListener) {
        this(0, str, response$Listener, response$ErrorListener);
    }

    public void cancel() {
        super.cancel();
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    protected void deliverResponse(String str) {
        synchronized (this.mLock) {
            Response$Listener response$Listener = this.mListener;
        }
        if (response$Listener != null) {
            response$Listener.onResponse(str);
        }
    }

    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        Object str;
        try {
            str = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException e) {
            str = new String(networkResponse.data);
        }
        return Response.success(str, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }
}
