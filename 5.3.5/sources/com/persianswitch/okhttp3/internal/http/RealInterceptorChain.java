package com.persianswitch.okhttp3.internal.http;

import com.persianswitch.okhttp3.Connection;
import com.persianswitch.okhttp3.HttpUrl;
import com.persianswitch.okhttp3.Interceptor;
import com.persianswitch.okhttp3.Interceptor.Chain;
import com.persianswitch.okhttp3.Request;
import com.persianswitch.okhttp3.Response;
import java.io.IOException;
import java.util.List;

public final class RealInterceptorChain implements Chain {
    private int calls;
    private final Connection connection;
    private final HttpStream httpStream;
    private final int index;
    private final List<Interceptor> interceptors;
    private final Request request;
    private final StreamAllocation streamAllocation;

    public RealInterceptorChain(List<Interceptor> interceptors, Connection connection, StreamAllocation streamAllocation, HttpStream httpStream, int index, Request request) {
        this.interceptors = interceptors;
        this.connection = connection;
        this.streamAllocation = streamAllocation;
        this.httpStream = httpStream;
        this.index = index;
        this.request = request;
    }

    public Connection connection() {
        return this.connection;
    }

    public StreamAllocation streamAllocation() {
        return this.streamAllocation;
    }

    public HttpStream httpStream() {
        return this.httpStream;
    }

    public Request request() {
        return this.request;
    }

    public Response proceed(Request request) throws IOException {
        return proceed(request, this.connection, this.streamAllocation, this.httpStream);
    }

    public Response proceed(Request request, Connection connection, StreamAllocation streamAllocation, HttpStream httpStream) throws IOException {
        if (this.index >= this.interceptors.size()) {
            throw new AssertionError();
        }
        this.calls++;
        if (this.connection != null && !sameConnection(request.url())) {
            throw new IllegalStateException("network interceptor " + this.interceptors.get(this.index - 1) + " must retain the same host and port");
        } else if (this.connection == null || this.calls <= 1) {
            RealInterceptorChain chain = new RealInterceptorChain(this.interceptors, connection, streamAllocation, httpStream, this.index + 1, request);
            Interceptor interceptor = (Interceptor) this.interceptors.get(this.index);
            Response interceptedResponse = interceptor.intercept(chain);
            if (connection != null && this.index + 1 < this.interceptors.size() && chain.calls != 1) {
                throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
            } else if (interceptedResponse != null) {
                return interceptedResponse;
            } else {
                throw new NullPointerException("interceptor " + interceptor + " returned null");
            }
        } else {
            throw new IllegalStateException("network interceptor " + this.interceptors.get(this.index - 1) + " must call proceed() exactly once");
        }
    }

    private boolean sameConnection(HttpUrl url) {
        return url.host().equals(connection().route().address().url().host()) && url.port() == connection().route().address().url().port();
    }
}
