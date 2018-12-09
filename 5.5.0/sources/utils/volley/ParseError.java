package utils.volley;

public class ParseError extends VolleyError {
    public ParseError(Throwable th) {
        super(th);
    }

    public ParseError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
