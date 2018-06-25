package utils.volley;

import utils.volley.Cache.Entry;

public class Response<T> {
    public final Entry cacheEntry;
    public final VolleyError error;
    public boolean intermediate;
    public final T result;

    private Response(T t, Entry entry) {
        this.intermediate = false;
        this.result = t;
        this.cacheEntry = entry;
        this.error = null;
    }

    private Response(VolleyError volleyError) {
        this.intermediate = false;
        this.result = null;
        this.cacheEntry = null;
        this.error = volleyError;
    }

    public static <T> Response<T> error(VolleyError volleyError) {
        return new Response(volleyError);
    }

    public static <T> Response<T> success(T t, Entry entry) {
        return new Response(t, entry);
    }

    public boolean isSuccess() {
        return this.error == null;
    }
}
