package com.persianswitch.okhttp3.internal.http;

import com.persianswitch.okhttp3.Address;
import com.persianswitch.okhttp3.CertificatePinner;
import com.persianswitch.okhttp3.Cookie;
import com.persianswitch.okhttp3.CookieJar;
import com.persianswitch.okhttp3.Headers;
import com.persianswitch.okhttp3.HttpUrl;
import com.persianswitch.okhttp3.MediaType;
import com.persianswitch.okhttp3.OkHttpClient;
import com.persianswitch.okhttp3.Protocol;
import com.persianswitch.okhttp3.Request;
import com.persianswitch.okhttp3.Response;
import com.persianswitch.okhttp3.Response.Builder;
import com.persianswitch.okhttp3.ResponseBody;
import com.persianswitch.okhttp3.internal.Internal;
import com.persianswitch.okhttp3.internal.InternalCache;
import com.persianswitch.okhttp3.internal.Util;
import com.persianswitch.okhttp3.internal.Version;
import com.persianswitch.okhttp3.internal.http.CacheStrategy.Factory;
import com.persianswitch.okio.Buffer;
import com.persianswitch.okio.BufferedSink;
import com.persianswitch.okio.BufferedSource;
import com.persianswitch.okio.GzipSource;
import com.persianswitch.okio.Okio;
import com.persianswitch.okio.Sink;
import com.persianswitch.okio.Source;
import com.persianswitch.okio.Timeout;
import com.thin.downloadmanager.BuildConfig;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY = new C07571();
    public static final int MAX_FOLLOW_UPS = 20;
    final OkHttpClient client;
    final Response priorResponse;
    final StreamAllocation streamAllocation;
    private boolean transparentGzip;
    final HttpUrl userRequestUrl;

    /* renamed from: com.persianswitch.okhttp3.internal.http.HttpEngine$1 */
    static class C07571 extends ResponseBody {
        C07571() {
        }

        public MediaType contentType() {
            return null;
        }

        public long contentLength() {
            return 0;
        }

        public BufferedSource source() {
            return new Buffer();
        }
    }

    public HttpEngine(OkHttpClient client, HttpUrl userRequestUrl, StreamAllocation streamAllocation, Response priorResponse) {
        this.client = client;
        this.userRequestUrl = userRequestUrl;
        if (streamAllocation == null) {
            streamAllocation = new StreamAllocation(client.connectionPool(), createAddress(client, userRequestUrl));
        }
        this.streamAllocation = streamAllocation;
        this.priorResponse = priorResponse;
    }

    public Response proceed(Request userRequest, RealInterceptorChain chain) throws IOException {
        Request request = networkRequest(userRequest);
        InternalCache responseCache = Internal.instance.internalCache(this.client);
        Response cacheCandidate = responseCache != null ? responseCache.get(request) : null;
        CacheStrategy cacheStrategy = new Factory(System.currentTimeMillis(), request, cacheCandidate).get();
        Request networkRequest = cacheStrategy.networkRequest;
        Response cacheResponse = cacheStrategy.cacheResponse;
        if (responseCache != null) {
            responseCache.trackResponse(cacheStrategy);
        }
        if (cacheCandidate != null && cacheResponse == null) {
            Util.closeQuietly(cacheCandidate.body());
        }
        if (networkRequest == null && cacheResponse == null) {
            return new Builder().request(userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).sentRequestAtMillis(-1).receivedResponseAtMillis(System.currentTimeMillis()).build();
        }
        if (networkRequest == null) {
            return unzip(cacheResponse.newBuilder().request(userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(cacheResponse)).build());
        }
        HttpStream httpStream = null;
        try {
            Response userResponse;
            httpStream = connect(networkRequest);
            httpStream.setHttpEngine(this);
            Response networkResponse = chain.proceed(networkRequest, this.streamAllocation.connection(), this.streamAllocation, httpStream);
            receiveHeaders(networkResponse.headers());
            if (cacheResponse != null) {
                if (validate(cacheResponse, networkResponse)) {
                    userResponse = cacheResponse.newBuilder().request(userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(cacheResponse.headers(), networkResponse.headers())).cacheResponse(stripBody(cacheResponse)).networkResponse(stripBody(networkResponse)).build();
                    networkResponse.body().close();
                    this.streamAllocation.release();
                    responseCache.trackConditionalCacheHit();
                    responseCache.update(cacheResponse, userResponse);
                    return unzip(userResponse);
                }
                Util.closeQuietly(cacheResponse.body());
            }
            userResponse = networkResponse.newBuilder().request(userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(cacheResponse)).networkResponse(stripBody(networkResponse)).build();
            if (!hasBody(userResponse)) {
                return userResponse;
            }
            return unzip(cacheWritingResponse(maybeCache(userResponse, networkResponse.request(), responseCache), userResponse));
        } finally {
            if (httpStream == null && cacheCandidate != null) {
                Util.closeQuietly(cacheCandidate.body());
            }
        }
    }

    private HttpStream connect(Request networkRequest) throws IOException {
        return this.streamAllocation.newStream(this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis(), this.client.retryOnConnectionFailure(), !networkRequest.method().equals(HttpRequest.METHOD_GET));
    }

    private static Response stripBody(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    private CacheRequest maybeCache(Response userResponse, Request networkRequest, InternalCache responseCache) throws IOException {
        if (responseCache == null) {
            return null;
        }
        if (CacheStrategy.isCacheable(userResponse, networkRequest)) {
            return responseCache.put(userResponse);
        }
        if (!HttpMethod.invalidatesCache(networkRequest.method())) {
            return null;
        }
        try {
            responseCache.remove(networkRequest);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public StreamAllocation close(Response userResponse) {
        if (userResponse != null) {
            Util.closeQuietly(userResponse.body());
        } else {
            this.streamAllocation.streamFailed(null);
        }
        return this.streamAllocation;
    }

    private Response unzip(Response response) throws IOException {
        if (!this.transparentGzip || !HttpRequest.ENCODING_GZIP.equalsIgnoreCase(response.header(HttpRequest.HEADER_CONTENT_ENCODING)) || response.body() == null) {
            return response;
        }
        Source responseBody = new GzipSource(response.body().source());
        Headers strippedHeaders = response.headers().newBuilder().removeAll(HttpRequest.HEADER_CONTENT_ENCODING).removeAll(HttpRequest.HEADER_CONTENT_LENGTH).build();
        return response.newBuilder().headers(strippedHeaders).body(new RealResponseBody(strippedHeaders, Okio.buffer(responseBody))).build();
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals(HttpRequest.METHOD_HEAD)) {
            return false;
        }
        int responseCode = response.code();
        if ((responseCode < 100 || responseCode >= 200) && responseCode != 204 && responseCode != 304) {
            return true;
        }
        if (OkHeaders.contentLength(response) != -1 || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return true;
        }
        return false;
    }

    private Request networkRequest(Request request) throws IOException {
        Request.Builder result = request.newBuilder();
        if (request.header("Host") == null) {
            result.header("Host", Util.hostHeader(request.url(), false));
        }
        if (request.header("Connection") == null) {
            result.header("Connection", "Keep-Alive");
        }
        if (request.header(HttpRequest.HEADER_ACCEPT_ENCODING) == null) {
            this.transparentGzip = true;
            result.header(HttpRequest.HEADER_ACCEPT_ENCODING, HttpRequest.ENCODING_GZIP);
        }
        List<Cookie> cookies = this.client.cookieJar().loadForRequest(request.url());
        if (!cookies.isEmpty()) {
            result.header("Cookie", cookieHeader(cookies));
        }
        if (request.header("User-Agent") == null) {
            result.header("User-Agent", Version.userAgent());
        }
        return result.build();
    }

    private String cookieHeader(List<Cookie> cookies) {
        StringBuilder cookieHeader = new StringBuilder();
        int size = cookies.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                cookieHeader.append("; ");
            }
            Cookie cookie = (Cookie) cookies.get(i);
            cookieHeader.append(cookie.name()).append('=').append(cookie.value());
        }
        return cookieHeader.toString();
    }

    private Response cacheWritingResponse(final CacheRequest cacheRequest, Response response) throws IOException {
        if (cacheRequest == null) {
            return response;
        }
        Sink cacheBodyUnbuffered = cacheRequest.body();
        if (cacheBodyUnbuffered == null) {
            return response;
        }
        final BufferedSource source = response.body().source();
        final BufferedSink cacheBody = Okio.buffer(cacheBodyUnbuffered);
        return response.newBuilder().body(new RealResponseBody(response.headers(), Okio.buffer(new Source() {
            boolean cacheRequestClosed;

            public long read(Buffer sink, long byteCount) throws IOException {
                try {
                    long bytesRead = source.read(sink, byteCount);
                    if (bytesRead == -1) {
                        if (!this.cacheRequestClosed) {
                            this.cacheRequestClosed = true;
                            cacheBody.close();
                        }
                        return -1;
                    }
                    sink.copyTo(cacheBody.buffer(), sink.size() - bytesRead, bytesRead);
                    cacheBody.emitCompleteSegments();
                    return bytesRead;
                } catch (IOException e) {
                    if (!this.cacheRequestClosed) {
                        this.cacheRequestClosed = true;
                        cacheRequest.abort();
                    }
                    throw e;
                }
            }

            public Timeout timeout() {
                return source.timeout();
            }

            public void close() throws IOException {
                if (!(this.cacheRequestClosed || Util.discard(this, 100, TimeUnit.MILLISECONDS))) {
                    this.cacheRequestClosed = true;
                    cacheRequest.abort();
                }
                source.close();
            }
        }))).build();
    }

    private static boolean validate(Response cached, Response network) {
        if (network.code() == 304) {
            return true;
        }
        Date lastModified = cached.headers().getDate(HttpRequest.HEADER_LAST_MODIFIED);
        if (lastModified != null) {
            Date networkLastModified = network.headers().getDate(HttpRequest.HEADER_LAST_MODIFIED);
            if (networkLastModified != null && networkLastModified.getTime() < lastModified.getTime()) {
                return true;
            }
        }
        return false;
    }

    private static Headers combine(Headers cachedHeaders, Headers networkHeaders) throws IOException {
        int i;
        Headers.Builder result = new Headers.Builder();
        int size = cachedHeaders.size();
        for (i = 0; i < size; i++) {
            String fieldName = cachedHeaders.name(i);
            String value = cachedHeaders.value(i);
            if (!("Warning".equalsIgnoreCase(fieldName) && value.startsWith(BuildConfig.VERSION_NAME)) && (!OkHeaders.isEndToEnd(fieldName) || networkHeaders.get(fieldName) == null)) {
                Internal.instance.addLenient(result, fieldName, value);
            }
        }
        size = networkHeaders.size();
        for (i = 0; i < size; i++) {
            fieldName = networkHeaders.name(i);
            if (!HttpRequest.HEADER_CONTENT_LENGTH.equalsIgnoreCase(fieldName) && OkHeaders.isEndToEnd(fieldName)) {
                Internal.instance.addLenient(result, fieldName, networkHeaders.value(i));
            }
        }
        return result.build();
    }

    public void receiveHeaders(Headers headers) throws IOException {
        if (this.client.cookieJar() != CookieJar.NO_COOKIES) {
            List<Cookie> cookies = Cookie.parseAll(this.userRequestUrl, headers);
            if (!cookies.isEmpty()) {
                this.client.cookieJar().saveFromResponse(this.userRequestUrl, cookies);
            }
        }
    }

    private static Address createAddress(OkHttpClient client, HttpUrl url) {
        SSLSocketFactory sslSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        CertificatePinner certificatePinner = null;
        if (url.isHttps()) {
            sslSocketFactory = client.sslSocketFactory();
            hostnameVerifier = client.hostnameVerifier();
            certificatePinner = client.certificatePinner();
        }
        return new Address(url.host(), url.port(), client.dns(), client.socketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, client.proxyAuthenticator(), client.proxy(), client.protocols(), client.connectionSpecs(), client.proxySelector());
    }
}
