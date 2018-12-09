package utils.volley.toolbox;

import org.json.JSONArray;
import utils.volley.NetworkResponse;
import utils.volley.ParseError;
import utils.volley.Response;
import utils.volley.Response$ErrorListener;
import utils.volley.Response$Listener;

public class JsonArrayRequest extends JsonRequest<JSONArray> {
    public JsonArrayRequest(int i, String str, JSONArray jSONArray, Response$Listener<JSONArray> response$Listener, Response$ErrorListener response$ErrorListener) {
        super(i, str, jSONArray == null ? null : jSONArray.toString(), response$Listener, response$ErrorListener);
    }

    public JsonArrayRequest(String str, Response$Listener<JSONArray> response$Listener, Response$ErrorListener response$ErrorListener) {
        super(0, str, null, response$Listener, response$ErrorListener);
    }

    protected Response<JSONArray> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            return Response.success(new JSONArray(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8"))), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (Throwable e) {
            return Response.error(new ParseError(e));
        } catch (Throwable e2) {
            return Response.error(new ParseError(e2));
        }
    }
}
