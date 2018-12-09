package utils.volley;

class RequestQueue$1 implements RequestQueue$RequestFilter {
    final /* synthetic */ RequestQueue this$0;
    final /* synthetic */ Object val$tag;

    RequestQueue$1(RequestQueue requestQueue, Object obj) {
        this.this$0 = requestQueue;
        this.val$tag = obj;
    }

    public boolean apply(Request<?> request) {
        return request.getTag() == this.val$tag;
    }
}
