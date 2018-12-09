package utils.volley.toolbox;

import java.util.Map;
import org.apache.http.HttpResponse;
import utils.volley.Request;

@Deprecated
public interface HttpStack {
    HttpResponse performRequest(Request<?> request, Map<String, String> map);
}
