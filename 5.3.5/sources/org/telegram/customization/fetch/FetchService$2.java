package org.telegram.customization.fetch;

import android.content.Intent;

class FetchService$2 implements Runnable {
    final /* synthetic */ FetchService this$0;
    final /* synthetic */ Intent val$intent;

    FetchService$2(FetchService this$0, Intent intent) {
        this.this$0 = this$0;
        this.val$intent = intent;
    }

    public void run() {
        FetchService.access$000(this.this$0).clean();
        long id = this.val$intent.getLongExtra(FetchService.EXTRA_ID, -1);
        switch (this.val$intent.getIntExtra(FetchService.ACTION_TYPE, -1)) {
            case FetchService.ACTION_ENQUEUE /*310*/:
                FetchService.access$400(this.this$0, this.val$intent.getStringExtra(FetchService.EXTRA_URL), this.val$intent.getStringExtra(FetchService.EXTRA_FILE_PATH), this.val$intent.getParcelableArrayListExtra(FetchService.EXTRA_HEADERS), this.val$intent.getIntExtra(FetchService.EXTRA_PRIORITY, 600));
                return;
            case FetchService.ACTION_PAUSE /*311*/:
                FetchService.access$100(this.this$0, id);
                return;
            case FetchService.ACTION_RESUME /*312*/:
                FetchService.access$300(this.this$0, id);
                return;
            case FetchService.ACTION_REMOVE /*313*/:
                FetchService.access$200(this.this$0, id);
                return;
            case FetchService.ACTION_NETWORK /*314*/:
                FetchService.access$500(this.this$0, this.val$intent.getIntExtra(FetchService.EXTRA_NETWORK_ID, 200));
                return;
            case FetchService.ACTION_PROCESS_PENDING /*315*/:
                FetchService.access$700(this.this$0);
                return;
            case FetchService.ACTION_QUERY /*316*/:
                long queryId = this.val$intent.getLongExtra(FetchService.EXTRA_QUERY_ID, -1);
                FetchService.access$800(this.this$0, this.val$intent.getIntExtra(FetchService.EXTRA_QUERY_TYPE, FetchService.QUERY_ALL), queryId, id, this.val$intent.getIntExtra(FetchService.EXTRA_STATUS, -1));
                return;
            case FetchService.ACTION_PRIORITY /*317*/:
                FetchService.access$900(this.this$0, id, this.val$intent.getIntExtra(FetchService.EXTRA_PRIORITY, 600));
                return;
            case FetchService.ACTION_RETRY /*318*/:
                FetchService.access$1000(this.this$0, id);
                return;
            case FetchService.ACTION_REMOVE_ALL /*319*/:
                FetchService.access$1100(this.this$0);
                return;
            case FetchService.ACTION_LOGGING /*320*/:
                FetchService.access$600(this.this$0, this.val$intent.getBooleanExtra(FetchService.EXTRA_LOGGING_ID, true));
                return;
            case FetchService.ACTION_CONCURRENT_DOWNLOADS_LIMIT /*321*/:
                FetchService.access$1200(this.this$0, this.val$intent.getIntExtra(FetchService.EXTRA_CONCURRENT_DOWNLOADS_LIMIT, 1));
                return;
            case FetchService.ACTION_UPDATE_REQUEST_URL /*322*/:
                FetchService.access$1400(this.this$0, id, this.val$intent.getStringExtra(FetchService.EXTRA_URL));
                return;
            case FetchService.ACTION_ON_UPDATE_INTERVAL /*323*/:
                FetchService.access$1300(this.this$0, this.val$intent.getLongExtra(FetchService.EXTRA_ON_UPDATE_INTERVAL, FetchConst.DEFAULT_ON_UPDATE_INTERVAL));
                return;
            case FetchService.ACTION_REMOVE_REQUEST /*324*/:
                FetchService.access$1500(this.this$0, id);
                return;
            case FetchService.ACTION_REMOVE_REQUEST_ALL /*325*/:
                FetchService.access$1600(this.this$0);
                return;
            default:
                FetchService.access$700(this.this$0);
                return;
        }
    }
}
