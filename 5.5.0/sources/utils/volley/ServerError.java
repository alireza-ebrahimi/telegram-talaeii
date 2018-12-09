package utils.volley;

public class ServerError extends VolleyError {
    public ServerError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
