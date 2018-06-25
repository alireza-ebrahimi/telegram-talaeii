package com.stripe.android.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.wallet.WalletConstants;
import com.stripe.android.exception.APIConnectionException;
import com.stripe.android.exception.APIException;
import com.stripe.android.exception.AuthenticationException;
import com.stripe.android.exception.CardException;
import com.stripe.android.exception.InvalidRequestException;
import com.stripe.android.exception.PermissionException;
import com.stripe.android.exception.RateLimitException;
import com.stripe.android.model.Token;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONObject;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.ui.ChatActivity;

public class StripeApiHandler {
    public static final String CHARSET = "UTF-8";
    private static final String DNS_CACHE_TTL_PROPERTY_NAME = "networkaddress.cache.ttl";
    static final String GET = "GET";
    public static final String LIVE_API_BASE = "https://api.stripe.com";
    static final String POST = "POST";
    private static final SSLSocketFactory SSL_SOCKET_FACTORY = new StripeSSLSocketFactory();
    public static final String TOKENS = "tokens";
    public static final String VERSION = "3.5.0";

    private static final class Parameter {
        public final String key;
        public final String value;

        public Parameter(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface RestMethod {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.stripe.android.model.Token requestToken(java.lang.String r15, java.lang.String r16, java.util.Map<java.lang.String, java.lang.Object> r17, com.stripe.android.net.RequestOptions r18) throws com.stripe.android.exception.AuthenticationException, com.stripe.android.exception.InvalidRequestException, com.stripe.android.exception.APIConnectionException, com.stripe.android.exception.CardException, com.stripe.android.exception.APIException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0003 in list [B:27:0x0076]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        if (r18 != 0) goto L_0x0004;
    L_0x0002:
        r11 = 0;
    L_0x0003:
        return r11;
    L_0x0004:
        r4 = 0;
        r11 = 1;
        r0 = java.lang.Boolean.valueOf(r11);
        r11 = "networkaddress.cache.ttl";	 Catch:{ SecurityException -> 0x0037 }
        r4 = java.security.Security.getProperty(r11);	 Catch:{ SecurityException -> 0x0037 }
        r11 = "networkaddress.cache.ttl";	 Catch:{ SecurityException -> 0x0037 }
        r12 = "0";	 Catch:{ SecurityException -> 0x0037 }
        java.security.Security.setProperty(r11, r12);	 Catch:{ SecurityException -> 0x0037 }
    L_0x001a:
        r1 = r18.getPublishableApiKey();
        r11 = r1.trim();
        r11 = r11.isEmpty();
        if (r11 == 0) goto L_0x003e;
    L_0x0028:
        r11 = new com.stripe.android.exception.AuthenticationException;
        r12 = "No API key provided. (HINT: set your API key using 'Stripe.apiKey = <API-KEY>'. You can generate API keys from the Stripe web interface. See https://stripe.com/api for details or email support@stripe.com if you have questions.";
        r13 = 0;
        r14 = 0;
        r14 = java.lang.Integer.valueOf(r14);
        r11.<init>(r12, r13, r14);
        throw r11;
    L_0x0037:
        r10 = move-exception;
        r11 = 0;
        r0 = java.lang.Boolean.valueOf(r11);
        goto L_0x001a;
    L_0x003e:
        r9 = getStripeResponse(r15, r16, r17, r18);	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r6 = r9.getResponseCode();	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r5 = r9.getResponseBody();	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r7 = 0;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r2 = r9.getResponseHeaders();	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        if (r2 != 0) goto L_0x0082;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
    L_0x0051:
        r8 = 0;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
    L_0x0052:
        if (r8 == 0) goto L_0x0061;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
    L_0x0054:
        r11 = r8.size();	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        if (r11 <= 0) goto L_0x0061;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
    L_0x005a:
        r11 = 0;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r7 = r8.get(r11);	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r7 = (java.lang.String) r7;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
    L_0x0061:
        r11 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        if (r6 < r11) goto L_0x0069;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
    L_0x0065:
        r11 = 300; // 0x12c float:4.2E-43 double:1.48E-321;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        if (r6 < r11) goto L_0x006c;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
    L_0x0069:
        handleAPIError(r5, r6, r7);	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
    L_0x006c:
        r11 = com.stripe.android.net.TokenParser.parseToken(r5);	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r12 = r0.booleanValue();
        if (r12 == 0) goto L_0x0003;
    L_0x0076:
        if (r4 != 0) goto L_0x008d;
    L_0x0078:
        r12 = "networkaddress.cache.ttl";
        r13 = "-1";
        java.security.Security.setProperty(r12, r13);
        goto L_0x0003;
    L_0x0082:
        r11 = "Request-Id";	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r11 = r2.get(r11);	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r11 = (java.util.List) r11;	 Catch:{ JSONException -> 0x0095, all -> 0x00b2 }
        r8 = r11;
        goto L_0x0052;
    L_0x008d:
        r12 = "networkaddress.cache.ttl";
        java.security.Security.setProperty(r12, r4);
        goto L_0x0003;
    L_0x0095:
        r3 = move-exception;
        r11 = 0;
        r12 = r0.booleanValue();
        if (r12 == 0) goto L_0x0003;
    L_0x009d:
        if (r4 != 0) goto L_0x00aa;
    L_0x009f:
        r12 = "networkaddress.cache.ttl";
        r13 = "-1";
        java.security.Security.setProperty(r12, r13);
        goto L_0x0003;
    L_0x00aa:
        r12 = "networkaddress.cache.ttl";
        java.security.Security.setProperty(r12, r4);
        goto L_0x0003;
    L_0x00b2:
        r11 = move-exception;
        r12 = r0.booleanValue();
        if (r12 == 0) goto L_0x00c4;
    L_0x00b9:
        if (r4 != 0) goto L_0x00c5;
    L_0x00bb:
        r12 = "networkaddress.cache.ttl";
        r13 = "-1";
        java.security.Security.setProperty(r12, r13);
    L_0x00c4:
        throw r11;
    L_0x00c5:
        r12 = "networkaddress.cache.ttl";
        java.security.Security.setProperty(r12, r4);
        goto L_0x00c4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.stripe.android.net.StripeApiHandler.requestToken(java.lang.String, java.lang.String, java.util.Map, com.stripe.android.net.RequestOptions):com.stripe.android.model.Token");
    }

    @Nullable
    public static Token createToken(@NonNull Map<String, Object> cardParams, @NonNull RequestOptions options) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
        return requestToken("POST", getApiUrl(), cardParams, options);
    }

    @Nullable
    public static Token retrieveToken(@NonNull RequestOptions options, @NonNull String tokenId) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        try {
            return requestToken("GET", getRetrieveTokenApiUrl(tokenId), null, options);
        } catch (CardException cardException) {
            throw new APIException(cardException.getMessage(), cardException.getRequestId(), cardException.getStatusCode(), cardException);
        }
    }

    static String createQuery(Map<String, Object> params) throws UnsupportedEncodingException, InvalidRequestException {
        StringBuilder queryStringBuffer = new StringBuilder();
        for (Parameter param : flattenParams(params)) {
            if (queryStringBuffer.length() > 0) {
                queryStringBuffer.append("&");
            }
            queryStringBuffer.append(urlEncodePair(param.key, param.value));
        }
        return queryStringBuffer.toString();
    }

    static Map<String, String> getHeaders(RequestOptions options) {
        int i = 0;
        Map<String, String> headers = new HashMap();
        String apiVersion = options.getApiVersion();
        headers.put(HttpRequest.HEADER_ACCEPT_CHARSET, "UTF-8");
        headers.put("Accept", "application/json");
        headers.put("User-Agent", String.format("Stripe/v1 JavaBindings/%s", new Object[]{VERSION}));
        headers.put(HttpRequest.HEADER_AUTHORIZATION, String.format("Bearer %s", new Object[]{options.getPublishableApiKey()}));
        String[] propertyNames = new String[]{"os.name", "os.version", "os.arch", "java.version", "java.vendor", "java.vm.version", "java.vm.vendor"};
        Map<String, String> propertyMap = new HashMap();
        int length = propertyNames.length;
        while (i < length) {
            String propertyName = propertyNames[i];
            propertyMap.put(propertyName, System.getProperty(propertyName));
            i++;
        }
        propertyMap.put("bindings.version", VERSION);
        propertyMap.put("lang", "Java");
        propertyMap.put("publisher", "Stripe");
        headers.put("X-Stripe-Client-User-Agent", new JSONObject(propertyMap).toString());
        if (apiVersion != null) {
            headers.put("Stripe-Version", apiVersion);
        }
        if (options.getIdempotencyKey() != null) {
            headers.put("Idempotency-Key", options.getIdempotencyKey());
        }
        return headers;
    }

    @VisibleForTesting
    static String getApiUrl() {
        return String.format("%s/v1/%s", new Object[]{LIVE_API_BASE, TOKENS});
    }

    @VisibleForTesting
    static String getRetrieveTokenApiUrl(@NonNull String tokenId) {
        return String.format("%s/%s", new Object[]{getApiUrl(), tokenId});
    }

    private static String formatURL(String url, String query) {
        if (query == null || query.isEmpty()) {
            return url;
        }
        String separator = url.contains("?") ? "&" : "?";
        return String.format("%s%s%s", new Object[]{url, separator, query});
    }

    private static HttpURLConnection createGetConnection(String url, String query, RequestOptions options) throws IOException {
        HttpURLConnection conn = createStripeConnection(formatURL(url, query), options);
        conn.setRequestMethod("GET");
        return conn;
    }

    private static HttpURLConnection createPostConnection(String url, String query, RequestOptions options) throws IOException {
        HttpURLConnection conn = createStripeConnection(url, options);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty(HttpRequest.HEADER_CONTENT_TYPE, String.format("application/x-www-form-urlencoded;charset=%s", new Object[]{"UTF-8"}));
        OutputStream outputStream = null;
        try {
            outputStream = conn.getOutputStream();
            outputStream.write(query.getBytes("UTF-8"));
            return conn;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private static HttpURLConnection createStripeConnection(String url, RequestOptions options) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(DefaultLoadControl.DEFAULT_MAX_BUFFER_MS);
        conn.setReadTimeout(80000);
        conn.setUseCaches(false);
        for (Entry<String, String> header : getHeaders(options).entrySet()) {
            conn.setRequestProperty((String) header.getKey(), (String) header.getValue());
        }
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(SSL_SOCKET_FACTORY);
        }
        return conn;
    }

    private static StripeResponse getStripeResponse(String method, String url, Map<String, Object> params, RequestOptions options) throws InvalidRequestException, APIConnectionException, APIException {
        try {
            return makeURLConnectionRequest(method, url, createQuery(params), options);
        } catch (UnsupportedEncodingException e) {
            throw new InvalidRequestException("Unable to encode parameters to UTF-8. Please contact support@stripe.com for assistance.", null, null, Integer.valueOf(0), e);
        }
    }

    private static List<Parameter> flattenParams(Map<String, Object> params) throws InvalidRequestException {
        return flattenParamsMap(params, null);
    }

    private static List<Parameter> flattenParamsList(List<Object> params, String keyPrefix) throws InvalidRequestException {
        List<Parameter> flatParams = new LinkedList();
        String newPrefix = String.format("%s[]", new Object[]{keyPrefix});
        if (params.isEmpty()) {
            flatParams.add(new Parameter(keyPrefix, ""));
        } else {
            for (Object flattenParamsValue : params) {
                flatParams.addAll(flattenParamsValue(flattenParamsValue, newPrefix));
            }
        }
        return flatParams;
    }

    private static List<Parameter> flattenParamsMap(Map<String, Object> params, String keyPrefix) throws InvalidRequestException {
        List<Parameter> flatParams = new LinkedList();
        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                String newPrefix = key;
                if (keyPrefix != null) {
                    newPrefix = String.format("%s[%s]", new Object[]{keyPrefix, key});
                }
                flatParams.addAll(flattenParamsValue(value, newPrefix));
            }
        }
        return flatParams;
    }

    private static List<Parameter> flattenParamsValue(Object value, String keyPrefix) throws InvalidRequestException {
        if (value instanceof Map) {
            return flattenParamsMap((Map) value, keyPrefix);
        }
        if (value instanceof List) {
            return flattenParamsList((List) value, keyPrefix);
        }
        if ("".equals(value)) {
            throw new InvalidRequestException("You cannot set '" + keyPrefix + "' to an empty string. We interpret empty strings as null in requests. You may set '" + keyPrefix + "' to null to delete the property.", keyPrefix, null, Integer.valueOf(0), null);
        } else if (value == null) {
            flatParams = new LinkedList();
            flatParams.add(new Parameter(keyPrefix, ""));
            return flatParams;
        } else {
            flatParams = new LinkedList();
            flatParams.add(new Parameter(keyPrefix, value.toString()));
            return flatParams;
        }
    }

    private static void handleAPIError(String rBody, int rCode, String requestId) throws InvalidRequestException, AuthenticationException, CardException, APIException {
        StripeError stripeError = ErrorParser.parseError(rBody);
        switch (rCode) {
            case ChatActivity.scheduleDownloads /*400*/:
                throw new InvalidRequestException(stripeError.message, stripeError.param, requestId, Integer.valueOf(rCode), null);
            case 401:
                throw new AuthenticationException(stripeError.message, requestId, Integer.valueOf(rCode));
            case WalletConstants.ERROR_CODE_SERVICE_UNAVAILABLE /*402*/:
                throw new CardException(stripeError.message, requestId, stripeError.code, stripeError.param, stripeError.decline_code, stripeError.charge, Integer.valueOf(rCode), null);
            case 403:
                throw new PermissionException(stripeError.message, requestId, Integer.valueOf(rCode));
            case WalletConstants.ERROR_CODE_INVALID_PARAMETERS /*404*/:
                throw new InvalidRequestException(stripeError.message, stripeError.param, requestId, Integer.valueOf(rCode), null);
            case 429:
                throw new RateLimitException(stripeError.message, stripeError.param, requestId, Integer.valueOf(rCode), null);
            default:
                throw new APIException(stripeError.message, requestId, Integer.valueOf(rCode), null);
        }
    }

    private static String urlEncodePair(String k, String v) throws UnsupportedEncodingException {
        return String.format("%s=%s", new Object[]{urlEncode(k), urlEncode(v)});
    }

    private static String urlEncode(String str) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return URLEncoder.encode(str, "UTF-8");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.stripe.android.net.StripeResponse makeURLConnectionRequest(java.lang.String r10, java.lang.String r11, java.lang.String r12, com.stripe.android.net.RequestOptions r13) throws com.stripe.android.exception.APIConnectionException {
        /*
        r7 = 1;
        r5 = 0;
        r0 = 0;
        r6 = -1;
        r8 = r10.hashCode();	 Catch:{ IOException -> 0x0022 }
        switch(r8) {
            case 70454: goto L_0x0048;
            case 2461856: goto L_0x0052;
            default: goto L_0x000b;
        };	 Catch:{ IOException -> 0x0022 }
    L_0x000b:
        r5 = r6;
    L_0x000c:
        switch(r5) {
            case 0: goto L_0x005d;
            case 1: goto L_0x0084;
            default: goto L_0x000f;
        };	 Catch:{ IOException -> 0x0022 }
    L_0x000f:
        r5 = new com.stripe.android.exception.APIConnectionException;	 Catch:{ IOException -> 0x0022 }
        r6 = "Unrecognized HTTP method %s. This indicates a bug in the Stripe bindings. Please contact support@stripe.com for assistance.";
        r7 = 1;
        r7 = new java.lang.Object[r7];	 Catch:{ IOException -> 0x0022 }
        r8 = 0;
        r7[r8] = r10;	 Catch:{ IOException -> 0x0022 }
        r6 = java.lang.String.format(r6, r7);	 Catch:{ IOException -> 0x0022 }
        r5.<init>(r6);	 Catch:{ IOException -> 0x0022 }
        throw r5;	 Catch:{ IOException -> 0x0022 }
    L_0x0022:
        r1 = move-exception;
        r5 = new com.stripe.android.exception.APIConnectionException;	 Catch:{ all -> 0x0041 }
        r6 = "IOException during API request to Stripe (%s): %s Please check your internet connection and try again. If this problem persists, you should check Stripe's service status at https://twitter.com/stripestatus, or let us know at support@stripe.com.";
        r7 = 2;
        r7 = new java.lang.Object[r7];	 Catch:{ all -> 0x0041 }
        r8 = 0;
        r9 = getApiUrl();	 Catch:{ all -> 0x0041 }
        r7[r8] = r9;	 Catch:{ all -> 0x0041 }
        r8 = 1;
        r9 = r1.getMessage();	 Catch:{ all -> 0x0041 }
        r7[r8] = r9;	 Catch:{ all -> 0x0041 }
        r6 = java.lang.String.format(r6, r7);	 Catch:{ all -> 0x0041 }
        r5.<init>(r6, r1);	 Catch:{ all -> 0x0041 }
        throw r5;	 Catch:{ all -> 0x0041 }
    L_0x0041:
        r5 = move-exception;
        if (r0 == 0) goto L_0x0047;
    L_0x0044:
        r0.disconnect();
    L_0x0047:
        throw r5;
    L_0x0048:
        r7 = "GET";
        r7 = r10.equals(r7);	 Catch:{ IOException -> 0x0022 }
        if (r7 == 0) goto L_0x000b;
    L_0x0051:
        goto L_0x000c;
    L_0x0052:
        r5 = "POST";
        r5 = r10.equals(r5);	 Catch:{ IOException -> 0x0022 }
        if (r5 == 0) goto L_0x000b;
    L_0x005b:
        r5 = r7;
        goto L_0x000c;
    L_0x005d:
        r0 = createGetConnection(r11, r12, r13);	 Catch:{ IOException -> 0x0022 }
    L_0x0061:
        r4 = r0.getResponseCode();	 Catch:{ IOException -> 0x0022 }
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r4 < r5) goto L_0x0089;
    L_0x0069:
        r5 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r4 >= r5) goto L_0x0089;
    L_0x006d:
        r5 = r0.getInputStream();	 Catch:{ IOException -> 0x0022 }
        r3 = getResponseBody(r5);	 Catch:{ IOException -> 0x0022 }
    L_0x0075:
        r2 = r0.getHeaderFields();	 Catch:{ IOException -> 0x0022 }
        r5 = new com.stripe.android.net.StripeResponse;	 Catch:{ IOException -> 0x0022 }
        r5.<init>(r4, r3, r2);	 Catch:{ IOException -> 0x0022 }
        if (r0 == 0) goto L_0x0083;
    L_0x0080:
        r0.disconnect();
    L_0x0083:
        return r5;
    L_0x0084:
        r0 = createPostConnection(r11, r12, r13);	 Catch:{ IOException -> 0x0022 }
        goto L_0x0061;
    L_0x0089:
        r5 = r0.getErrorStream();	 Catch:{ IOException -> 0x0022 }
        r3 = getResponseBody(r5);	 Catch:{ IOException -> 0x0022 }
        goto L_0x0075;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.stripe.android.net.StripeApiHandler.makeURLConnectionRequest(java.lang.String, java.lang.String, java.lang.String, com.stripe.android.net.RequestOptions):com.stripe.android.net.StripeResponse");
    }

    private static String getResponseBody(InputStream responseStream) throws IOException {
        String rBody = new Scanner(responseStream, "UTF-8").useDelimiter("\\A").next();
        responseStream.close();
        return rBody;
    }
}
