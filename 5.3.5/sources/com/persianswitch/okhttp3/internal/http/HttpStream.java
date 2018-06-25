package com.persianswitch.okhttp3.internal.http;

import com.persianswitch.okhttp3.Request;
import com.persianswitch.okhttp3.Response;
import com.persianswitch.okhttp3.Response.Builder;
import com.persianswitch.okhttp3.ResponseBody;
import com.persianswitch.okio.Sink;
import java.io.IOException;

public interface HttpStream {
    public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

    void cancel();

    Sink createRequestBody(Request request, long j) throws IOException;

    void finishRequest() throws IOException;

    ResponseBody openResponseBody(Response response) throws IOException;

    Builder readResponseHeaders() throws IOException;

    void setHttpEngine(HttpEngine httpEngine);

    void writeRequestHeaders(Request request) throws IOException;
}
