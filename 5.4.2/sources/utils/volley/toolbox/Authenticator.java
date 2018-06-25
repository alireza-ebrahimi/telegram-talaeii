package utils.volley.toolbox;

public interface Authenticator {
    String getAuthToken();

    void invalidateAuthToken(String str);
}
