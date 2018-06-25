package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import com.persianswitch.okhttp3.MediaType;
import com.persianswitch.okhttp3.OkHttpClient;
import com.persianswitch.okhttp3.OkHttpClient.Builder;
import com.persianswitch.okhttp3.Request;
import com.persianswitch.okhttp3.RequestBody;
import com.persianswitch.okhttp3.Response;
import com.persianswitch.okhttp3.ResponseBody;
import com.persianswitch.okhttp3.internal.tls.OkHostnameVerifier;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.log.SDKLog;
import com.persianswitch.sdk.base.security.SecurityManager;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.webservice.exception.WSConnectTimeoutException;
import com.persianswitch.sdk.base.webservice.exception.WSHttpStatusException;
import com.persianswitch.sdk.base.webservice.exception.WSParseResponseException;
import com.persianswitch.sdk.base.webservice.exception.WSSocketTimeoutException;
import com.persianswitch.sdk.base.webservice.trust.TrustManagerBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;

public class HttpEngine {
    private static final String TAG = "HttpEngine";
    public static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");
    private static HttpEngine instance;
    private final OkHttpClient idempotentClient;
    private final OkHttpClient nonIdempotentClient;

    private HttpEngine(OkHttpClient idempotentClient, OkHttpClient nonIdempotentClient) {
        this.idempotentClient = idempotentClient;
        this.nonIdempotentClient = nonIdempotentClient;
    }

    public static HttpEngine getInstance(Context context, Config config) {
        if (instance == null) {
            instance = new HttpEngine(provideIdempotentHttpClient(provideTrustManager(context, config)), provideNonIdempotentHttpClient(provideTrustManager(context, config)));
        }
        return instance;
    }

    private static OkHttpClient provideIdempotentHttpClient(X509TrustManager trustManager) {
        return new Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).retryOnConnectionFailure(false).sslSocketFactory(getSslSocketFactory(trustManager), trustManager).hostnameVerifier(new APMBHostNameVerifier(OkHostnameVerifier.INSTANCE)).build();
    }

    private static X509TrustManager provideTrustManager(Context context, Config config) {
        TrustManagerBuilder builder = new TrustManagerBuilder(context);
        try {
            builder.allowCA("cert/root.cer").or().allowCA("cert/inter.cer").or().allowCA("cert/asanNet_ca.cer").or().allowCA("cert/apms_ssl_test_ca.cer").useDefault();
        } catch (Exception e) {
            SDKLog.m36e(TAG, "exception in build custom trust manager.", e, new Object[0]);
        }
        return (X509TrustManager) builder.build();
    }

    private static OkHttpClient provideNonIdempotentHttpClient(X509TrustManager trustManager) {
        return new Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).retryOnConnectionFailure(true).sslSocketFactory(getSslSocketFactory(trustManager), trustManager).hostnameVerifier(new APMBHostNameVerifier(OkHostnameVerifier.INSTANCE)).build();
    }

    private static SSLSocketFactory getSslSocketFactory(TrustManager trustManager) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    public HttpResult postJSON(Context context, String url, String bodyString, boolean silentRetry, byte[] aesKey) {
        OkHttpClient client;
        HttpResult httpResult = new HttpResult();
        String encryptedRequest = "";
        try {
            encryptedRequest = SecurityManager.getInstance(context).encodeRequest(bodyString, aesKey);
        } catch (Exception e) {
            SDKLog.m36e(TAG, "error in encrypt request body", e, new Object[0]);
        }
        if (silentRetry) {
            client = this.nonIdempotentClient;
        } else {
            client = this.idempotentClient;
        }
        Request postRequest = new Request.Builder().url(url).post(RequestBody.create(TEXT, StringUtils.toNonNullString(encryptedRequest))).build();
        Response response = null;
        int i = 3;
        long completeConnectionTimeout = 30000;
        while (i > 0 && completeConnectionTimeout > 0) {
            i--;
            long startTimeMillis = System.currentTimeMillis();
            try {
                response = client.newCall(postRequest).execute();
                httpResult.setException(null);
                completeConnectionTimeout -= System.currentTimeMillis() - startTimeMillis;
                break;
            } catch (IOException e2) {
            } catch (UnknownHostException e3) {
                UnknownHostException unknownHostException = e3;
            } catch (IOException e4) {
                httpResult.setException(new WSSocketTimeoutException());
                completeConnectionTimeout -= System.currentTimeMillis() - startTimeMillis;
            } catch (Throwable th) {
                completeConnectionTimeout -= System.currentTimeMillis() - startTimeMillis;
            }
        }
        if (response != null) {
            httpResult.setHttpStatusCode(response.code());
            if (response.isSuccessful()) {
                ResponseBody responseBody = null;
                try {
                    responseBody = response.body();
                    httpResult.setRawData(SecurityManager.getInstance(context).decodeResponse(responseBody.string(), aesKey));
                    if (responseBody != null) {
                        try {
                            responseBody.close();
                        } catch (Exception e5) {
                        }
                    }
                } catch (Exception e6) {
                    httpResult.setException(new WSParseResponseException());
                    if (responseBody != null) {
                        try {
                            responseBody.close();
                        } catch (Exception e7) {
                        }
                    }
                } catch (Throwable th2) {
                    if (responseBody != null) {
                        try {
                            responseBody.close();
                        } catch (Exception e8) {
                        }
                    }
                }
            } else {
                httpResult.setException(new WSHttpStatusException(httpResult.getHttpStatusCode()));
            }
        }
        return httpResult;
        completeConnectionTimeout -= System.currentTimeMillis() - startTimeMillis;
        httpResult.setException(new WSConnectTimeoutException());
        try {
            Thread.sleep(DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        } catch (InterruptedException e9) {
        }
        completeConnectionTimeout -= System.currentTimeMillis() - startTimeMillis;
    }

    public InputStream getResourceStream(String url) {
        try {
            return this.nonIdempotentClient.newCall(new Request.Builder().url(url).build()).execute().body().byteStream();
        } catch (Exception e) {
            return null;
        }
    }

    public OkHttpClient getIdempotentClient() {
        return this.idempotentClient;
    }

    public OkHttpClient getNonIdempotentClient() {
        return this.nonIdempotentClient;
    }
}
