package com.persianswitch.okhttp3.internal.http;

import com.persianswitch.okhttp3.Headers;
import com.persianswitch.okhttp3.MediaType;
import com.persianswitch.okhttp3.ResponseBody;
import com.persianswitch.okio.BufferedSource;
import io.fabric.sdk.android.services.network.HttpRequest;

public final class RealResponseBody extends ResponseBody {
    private final Headers headers;
    private final BufferedSource source;

    public RealResponseBody(Headers headers, BufferedSource source) {
        this.headers = headers;
        this.source = source;
    }

    public MediaType contentType() {
        String contentType = this.headers.get(HttpRequest.HEADER_CONTENT_TYPE);
        return contentType != null ? MediaType.parse(contentType) : null;
    }

    public long contentLength() {
        return OkHeaders.contentLength(this.headers);
    }

    public BufferedSource source() {
        return this.source;
    }
}
