package utils.volley;

interface Request$NetworkRequestCompleteListener {
    void onNoUsableResponseReceived(Request<?> request);

    void onResponseReceived(Request<?> request, Response<?> response);
}
