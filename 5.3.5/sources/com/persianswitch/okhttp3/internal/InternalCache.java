package com.persianswitch.okhttp3.internal;

import com.persianswitch.okhttp3.Request;
import com.persianswitch.okhttp3.Response;
import com.persianswitch.okhttp3.internal.http.CacheRequest;
import com.persianswitch.okhttp3.internal.http.CacheStrategy;
import java.io.IOException;

public interface InternalCache {
    Response get(Request request) throws IOException;

    CacheRequest put(Response response) throws IOException;

    void remove(Request request) throws IOException;

    void trackConditionalCacheHit();

    void trackResponse(CacheStrategy cacheStrategy);

    void update(Response response, Response response2) throws IOException;
}
