package com.persianswitch.okhttp3.internal.http;

import com.persianswitch.okhttp3.Connection;
import com.persianswitch.okhttp3.HttpUrl;
import com.persianswitch.okhttp3.Interceptor;
import com.persianswitch.okhttp3.Interceptor.Chain;
import com.persianswitch.okhttp3.MediaType;
import com.persianswitch.okhttp3.OkHttpClient;
import com.persianswitch.okhttp3.Request;
import com.persianswitch.okhttp3.Request.Builder;
import com.persianswitch.okhttp3.RequestBody;
import com.persianswitch.okhttp3.Response;
import com.persianswitch.okhttp3.Route;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpRetryException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.telegram.customization.Activities.ScheduleDownloadActivity;

public final class RetryAndFollowUpInterceptor implements Interceptor {
    private volatile boolean canceled;
    private final OkHttpClient client;
    private HttpEngine engine;
    private boolean forWebSocket;

    public RetryAndFollowUpInterceptor(OkHttpClient client) {
        this.client = client;
    }

    public void cancel() {
        this.canceled = true;
        HttpEngine engine = this.engine;
        if (engine != null) {
            engine.streamAllocation.cancel();
        }
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public OkHttpClient client() {
        return this.client;
    }

    public void setForWebSocket(boolean forWebSocket) {
        this.forWebSocket = forWebSocket;
    }

    public boolean isForWebSocket() {
        return this.forWebSocket;
    }

    public StreamAllocation streamAllocation() {
        return this.engine.streamAllocation;
    }

    public Response intercept(Chain chain) throws IOException {
        HttpEngine retryEngine;
        Request request = chain.request();
        RequestBody body = request.body();
        if (body != null) {
            Builder requestBuilder = request.newBuilder();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                requestBuilder.header(HttpRequest.HEADER_CONTENT_TYPE, contentType.toString());
            }
            long contentLength = body.contentLength();
            if (contentLength != -1) {
                requestBuilder.header(HttpRequest.HEADER_CONTENT_LENGTH, Long.toString(contentLength));
                requestBuilder.removeHeader("Transfer-Encoding");
            } else {
                requestBuilder.header("Transfer-Encoding", "chunked");
                requestBuilder.removeHeader(HttpRequest.HEADER_CONTENT_LENGTH);
            }
            request = requestBuilder.build();
        }
        this.engine = new HttpEngine(this.client, request.url(), null, null);
        int followUpCount = 0;
        while (!this.canceled) {
            try {
                Response response = this.engine.proceed(request, (RealInterceptorChain) chain);
                if (false) {
                    this.engine.close(null).release();
                }
                Request followUp = followUpRequest(response);
                if (followUp == null) {
                    if (!this.forWebSocket) {
                        this.engine.streamAllocation.release();
                    }
                    return response;
                }
                StreamAllocation streamAllocation = this.engine.close(response);
                followUpCount++;
                if (followUpCount > 20) {
                    streamAllocation.release();
                    throw new ProtocolException("Too many follow-up requests: " + followUpCount);
                } else if (followUp.body() instanceof UnrepeatableRequestBody) {
                    throw new HttpRetryException("Cannot retry streamed HTTP body", response.code());
                } else {
                    if (!sameConnection(response, followUp.url())) {
                        streamAllocation.release();
                        streamAllocation = null;
                    } else if (streamAllocation.stream() != null) {
                        throw new IllegalStateException("Closing the body of " + response + " didn't close its backing stream. Bad interceptor?");
                    }
                    request = followUp;
                    this.engine = new HttpEngine(this.client, request.url(), streamAllocation, response);
                }
            } catch (RouteException e) {
                retryEngine = recover(e.getLastConnectException(), true, request);
                if (retryEngine != null) {
                    this.engine = retryEngine;
                    if (false) {
                        this.engine.close(null).release();
                    }
                } else {
                    throw e.getLastConnectException();
                }
            } catch (IOException e2) {
                retryEngine = recover(e2, false, request);
                if (retryEngine != null) {
                    this.engine = retryEngine;
                    if (false) {
                        this.engine.close(null).release();
                    }
                } else {
                    throw e2;
                }
            } catch (Throwable th) {
                if (true) {
                    this.engine.close(null).release();
                }
            }
        }
        this.engine.streamAllocation.release();
        throw new IOException("Canceled");
    }

    private HttpEngine recover(IOException e, boolean routeException, Request userRequest) {
        this.engine.streamAllocation.streamFailed(e);
        if (!this.client.retryOnConnectionFailure()) {
            return null;
        }
        if ((!routeException && (userRequest.body() instanceof UnrepeatableRequestBody)) || !isRecoverable(e, routeException) || !this.engine.streamAllocation.hasMoreRoutes()) {
            return null;
        }
        return new HttpEngine(this.client, this.engine.userRequestUrl, this.engine.close(null), this.engine.priorResponse);
    }

    private boolean isRecoverable(IOException e, boolean routeException) {
        boolean z = true;
        if (e instanceof ProtocolException) {
            return false;
        }
        if (e instanceof InterruptedIOException) {
            if (!((e instanceof SocketTimeoutException) && routeException)) {
                z = false;
            }
            return z;
        } else if (((e instanceof SSLHandshakeException) && (e.getCause() instanceof CertificateException)) || (e instanceof SSLPeerUnverifiedException)) {
            return false;
        } else {
            return true;
        }
    }

    private Request followUpRequest(Response userResponse) throws IOException {
        if (userResponse == null) {
            throw new IllegalStateException();
        }
        Route route;
        Connection connection = this.engine.streamAllocation.connection();
        if (connection != null) {
            route = connection.route();
        } else {
            route = null;
        }
        int responseCode = userResponse.code();
        String method = userResponse.request().method();
        switch (responseCode) {
            case ScheduleDownloadActivity.CHECK_CELL2 /*300*/:
            case 301:
            case 302:
            case 303:
                break;
            case StatusLine.HTTP_TEMP_REDIRECT /*307*/:
            case StatusLine.HTTP_PERM_REDIRECT /*308*/:
                if (!(method.equals(HttpRequest.METHOD_GET) || method.equals(HttpRequest.METHOD_HEAD))) {
                    return null;
                }
            case 401:
                return this.client.authenticator().authenticate(route, userResponse);
            case 407:
                Proxy selectedProxy;
                if (route != null) {
                    selectedProxy = route.proxy();
                } else {
                    selectedProxy = this.client.proxy();
                }
                if (selectedProxy.type() == Type.HTTP) {
                    return this.client.proxyAuthenticator().authenticate(route, userResponse);
                }
                throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
            case 408:
                if (userResponse.request().body() instanceof UnrepeatableRequestBody) {
                    return null;
                }
                return userResponse.request();
            default:
                return null;
        }
        if (!this.client.followRedirects()) {
            return null;
        }
        String location = userResponse.header(HttpRequest.HEADER_LOCATION);
        if (location == null) {
            return null;
        }
        HttpUrl url = userResponse.request().url().resolve(location);
        if (url == null) {
            return null;
        }
        if (!url.scheme().equals(userResponse.request().url().scheme()) && !this.client.followSslRedirects()) {
            return null;
        }
        Builder requestBuilder = userResponse.request().newBuilder();
        if (HttpMethod.permitsRequestBody(method)) {
            if (HttpMethod.redirectsToGet(method)) {
                requestBuilder.method(HttpRequest.METHOD_GET, null);
            } else {
                requestBuilder.method(method, null);
            }
            requestBuilder.removeHeader("Transfer-Encoding");
            requestBuilder.removeHeader(HttpRequest.HEADER_CONTENT_LENGTH);
            requestBuilder.removeHeader(HttpRequest.HEADER_CONTENT_TYPE);
        }
        if (!sameConnection(userResponse, url)) {
            requestBuilder.removeHeader(HttpRequest.HEADER_AUTHORIZATION);
        }
        return requestBuilder.url(url).build();
    }

    private boolean sameConnection(Response response, HttpUrl followUp) {
        HttpUrl url = response.request().url();
        return url.host().equals(followUp.host()) && url.port() == followUp.port() && url.scheme().equals(followUp.scheme());
    }
}
