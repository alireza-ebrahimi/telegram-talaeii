package utils.volley.toolbox;

import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public final class HttpClientStack$HttpPatch extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "PATCH";

    public HttpClientStack$HttpPatch(String str) {
        setURI(URI.create(str));
    }

    public HttpClientStack$HttpPatch(URI uri) {
        setURI(uri);
    }

    public String getMethod() {
        return METHOD_NAME;
    }
}
