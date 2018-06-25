package utils.volley.toolbox;

import org.json.JSONObject;
import utils.volley.NetworkResponse;
import utils.volley.ParseError;
import utils.volley.Response;
import utils.volley.Response$ErrorListener;
import utils.volley.Response$Listener;

public class JsonObjectRequest extends JsonRequest<JSONObject> {
    public JsonObjectRequest(int i, String str, JSONObject jSONObject, Response$Listener<JSONObject> response$Listener, Response$ErrorListener response$ErrorListener) {
        super(i, str, jSONObject == null ? null : jSONObject.toString(), response$Listener, response$ErrorListener);
    }

    public JsonObjectRequest(String str, JSONObject jSONObject, Response$Listener<JSONObject> response$Listener, Response$ErrorListener response$ErrorListener) {
        this(jSONObject == null ? 0 : 1, str, jSONObject, response$Listener, response$ErrorListener);
    }

    protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            return Response.success(new JSONObject(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8"))), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (Throwable e) {
            return Response.error(new ParseError(e));
        } catch (Throwable e2) {
            return Response.error(new ParseError(e2));
        }
    }
}
