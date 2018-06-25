package utils.volley;

class Request$1 implements Runnable {
    final /* synthetic */ Request this$0;
    final /* synthetic */ String val$tag;
    final /* synthetic */ long val$threadId;

    Request$1(Request request, String str, long j) {
        this.this$0 = request;
        this.val$tag = str;
        this.val$threadId = j;
    }

    public void run() {
        Request.access$000(this.this$0).add(this.val$tag, this.val$threadId);
        Request.access$000(this.this$0).finish(this.this$0.toString());
    }
}
