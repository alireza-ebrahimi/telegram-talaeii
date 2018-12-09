package utils.volley.toolbox;

import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import utils.volley.Header;
import utils.volley.Request;

public class HurlStack extends BaseHttpStack {
    private static final int HTTP_CONTINUE = 100;
    private final SSLSocketFactory mSslSocketFactory;
    private final UrlRewriter mUrlRewriter;

    static class UrlConnectionInputStream extends FilterInputStream {
        private final HttpURLConnection mConnection;

        UrlConnectionInputStream(HttpURLConnection httpURLConnection) {
            super(HurlStack.inputStreamFromConnection(httpURLConnection));
            this.mConnection = httpURLConnection;
        }

        public void close() {
            super.close();
            this.mConnection.disconnect();
        }
    }

    public interface UrlRewriter {
        String rewriteUrl(String str);
    }

    public HurlStack() {
        this(null);
    }

    public HurlStack(UrlRewriter urlRewriter) {
        this(urlRewriter, null);
    }

    public HurlStack(UrlRewriter urlRewriter, SSLSocketFactory sSLSocketFactory) {
        this.mUrlRewriter = urlRewriter;
        this.mSslSocketFactory = sSLSocketFactory;
    }

    private static void addBody(HttpURLConnection httpURLConnection, Request<?> request, byte[] bArr) {
        httpURLConnection.setDoOutput(true);
        httpURLConnection.addRequestProperty("Content-Type", request.getBodyContentType());
        DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
        dataOutputStream.write(bArr);
        dataOutputStream.close();
    }

    private static void addBodyIfExists(HttpURLConnection httpURLConnection, Request<?> request) {
        byte[] body = request.getBody();
        if (body != null) {
            addBody(httpURLConnection, request, body);
        }
    }

    static List<Header> convertHeaders(Map<String, List<String>> map) {
        List<Header> arrayList = new ArrayList(map.size());
        for (Entry entry : map.entrySet()) {
            if (entry.getKey() != null) {
                for (String header : (List) entry.getValue()) {
                    arrayList.add(new Header((String) entry.getKey(), header));
                }
            }
        }
        return arrayList;
    }

    private static boolean hasResponseBody(int i, int i2) {
        return (i == 4 || ((100 <= i2 && i2 < Callback.DEFAULT_DRAG_ANIMATION_DURATION) || i2 == 204 || i2 == 304)) ? false : true;
    }

    private static InputStream inputStreamFromConnection(HttpURLConnection httpURLConnection) {
        try {
            return httpURLConnection.getInputStream();
        } catch (IOException e) {
            return httpURLConnection.getErrorStream();
        }
    }

    private HttpURLConnection openConnection(URL url, Request<?> request) {
        HttpURLConnection createConnection = createConnection(url);
        int timeoutMs = request.getTimeoutMs();
        createConnection.setConnectTimeout(timeoutMs);
        createConnection.setReadTimeout(timeoutMs);
        createConnection.setUseCaches(false);
        createConnection.setDoInput(true);
        createConnection.setRequestProperty("Connection", "close");
        if ("https".equals(url.getProtocol()) && this.mSslSocketFactory != null) {
            ((HttpsURLConnection) createConnection).setSSLSocketFactory(this.mSslSocketFactory);
        }
        return createConnection;
    }

    static void setConnectionParametersForRequest(HttpURLConnection httpURLConnection, Request<?> request) {
        switch (request.getMethod()) {
            case -1:
                byte[] postBody = request.getPostBody();
                if (postBody != null) {
                    httpURLConnection.setRequestMethod("POST");
                    addBody(httpURLConnection, request, postBody);
                    return;
                }
                return;
            case 0:
                httpURLConnection.setRequestMethod("GET");
                return;
            case 1:
                httpURLConnection.setRequestMethod("POST");
                addBodyIfExists(httpURLConnection, request);
                return;
            case 2:
                httpURLConnection.setRequestMethod("PUT");
                addBodyIfExists(httpURLConnection, request);
                return;
            case 3:
                httpURLConnection.setRequestMethod("DELETE");
                return;
            case 4:
                httpURLConnection.setRequestMethod("HEAD");
                return;
            case 5:
                httpURLConnection.setRequestMethod("OPTIONS");
                return;
            case 6:
                httpURLConnection.setRequestMethod("TRACE");
                return;
            case 7:
                httpURLConnection.setRequestMethod(HttpClientStack$HttpPatch.METHOD_NAME);
                addBodyIfExists(httpURLConnection, request);
                return;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    protected HttpURLConnection createConnection(URL url) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        return httpURLConnection;
    }

    public HttpResponse executeRequest(Request<?> request, Map<String, String> map) {
        String rewriteUrl;
        Object obj;
        Throwable th;
        String url = request.getUrl();
        HashMap hashMap = new HashMap();
        hashMap.putAll(request.getHeaders());
        hashMap.putAll(map);
        if (this.mUrlRewriter != null) {
            rewriteUrl = this.mUrlRewriter.rewriteUrl(url);
            if (rewriteUrl == null) {
                throw new IOException("URL blocked by rewriter: " + url);
            }
        }
        rewriteUrl = url;
        HttpURLConnection openConnection = openConnection(new URL(rewriteUrl), request);
        try {
            for (String rewriteUrl2 : hashMap.keySet()) {
                openConnection.addRequestProperty(rewriteUrl2, (String) hashMap.get(rewriteUrl2));
            }
            setConnectionParametersForRequest(openConnection, request);
            int responseCode = openConnection.getResponseCode();
            if (responseCode == -1) {
                throw new IOException("Could not retrieve response code from HttpUrlConnection.");
            }
            HttpResponse httpResponse;
            if (hasResponseBody(request.getMethod(), responseCode)) {
                obj = 1;
                try {
                    httpResponse = new HttpResponse(responseCode, convertHeaders(openConnection.getHeaderFields()), openConnection.getContentLength(), new UrlConnectionInputStream(openConnection));
                } catch (Throwable th2) {
                    th = th2;
                    if (obj == null) {
                        openConnection.disconnect();
                    }
                    throw th;
                }
            }
            httpResponse = new HttpResponse(responseCode, convertHeaders(openConnection.getHeaderFields()));
            openConnection.disconnect();
            return httpResponse;
        } catch (Throwable th3) {
            th = th3;
            obj = null;
            if (obj == null) {
                openConnection.disconnect();
            }
            throw th;
        }
    }
}
